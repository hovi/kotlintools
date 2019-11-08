package eu.karelhovorka.tools.youtube

typealias YoutubeVideoUrl = String

val YoutubeVideoUrl.isValid: Boolean
    get() = isValidYoutube(this)

val YoutubeVideoUrl.id: String
    get() = youtubeId(this)

fun YoutubeVideoUrl.validate() {
    validateYoutubeUrl(this)
}

val YoutubeVideoUrl.mobile: YoutubeVideoUrl
    get() {
        return "https://youtu.be/${id}"
    }

val YoutubeVideoUrl.embed: YoutubeVideoUrl
    get() {
        return "https://www.youtube.com/embed/${id}"
    }

const val YOUTUBE_ID_LENGTH = 11

val YOUTUBE_REGEX =
    "(?:(?:https?:)?//)?(?:(?:www\\.|m\\.)?youtube(?:-nocookie)?\\.com|youtu\\.?be)/(?:watch\\?v=|embed/|v/)?(?<id>[\\w\\-]{10,12})[^&?]*.*".toRegex()

fun isValidYoutube(url: YoutubeVideoUrl): Boolean {
    return url.matches(YOUTUBE_REGEX) && youtubeId(url).length == YOUTUBE_ID_LENGTH
}

fun validateYoutubeUrl(url: YoutubeVideoUrl) {
    val id = youtubeId(url)
    val isValid = isValidYoutube(url)
    require(isValid) { "url is not valid: $url id: $id" }
}

fun matchIndexOrNull(url: YoutubeVideoUrl, index: Int): String? {
    val matchResult = YOUTUBE_REGEX.matchEntire(url)
    matchResult?.apply {
        if (groupValues.size >= index + 1) {
            return groupValues[index]
        }
    }
    return null
}

fun youtubeIdOrNull(url: YoutubeVideoUrl): String? {
    return matchIndexOrNull(url, 1)
}


fun youtubeId(url: YoutubeVideoUrl): String {
    return youtubeIdOrNull(url) ?: error("id not found for url: $url")
}




