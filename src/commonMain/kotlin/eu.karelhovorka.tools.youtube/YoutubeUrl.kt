package eu.karelhovorka.tools.youtube

typealias YoutubeVideoUrl = String

val YoutubeVideoUrl.isValid: Boolean
    get() = isValidYoutube(this)

val YoutubeVideoUrl.id: String
    get() = youtubeId(this)

val YoutubeVideoUrl.idOrNull: String?
    get() = youtubeIdOrNull(this)

fun YoutubeVideoUrl.validate() {
    validateYoutubeUrl(this)
}

val YoutubeVideoUrl.short: YoutubeVideoUrl
    get() = shortUrl(id)

val YoutubeVideoUrl.embed: YoutubeVideoUrl
    get() = embedUrl(id)

val YoutubeVideoUrl.normal: YoutubeVideoUrl
    get() = normalUrl(id)

const val YOUTUBE_ID_LENGTH = 11

val YOUTUBE_REGEX =
    "(?:(?:https?:)?//)?(?:(?:www\\.|m\\.)?youtube(?:-nocookie)?\\.com|youtu\\.?be)/(?:attribution_link\\?[au]=[^/]*/)?(?:watch\\?v=|embed/|v/|watch/)?(?<id>[\\w\\-]{10,12})[^&?]*.*".toRegex()

fun isValidYoutube(url: YoutubeVideoUrl?): Boolean {
    return url != null && url.matches(YOUTUBE_REGEX) && youtubeId(url).length == YOUTUBE_ID_LENGTH
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

fun shortUrl(id: String): String {
    return "https://youtu.be/${id}"
}

fun embedUrl(id: String): String {
    return "https://www.youtube.com/embed/${id}"
}

fun normalUrl(id: String): String {
    return "https://www.youtube.com/watch?v=${id}"
}



