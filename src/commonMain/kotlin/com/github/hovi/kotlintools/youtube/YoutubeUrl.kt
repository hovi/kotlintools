package com.github.hovi.kotlintools.youtube

inline class YoutubeVideoUrl(val raw: String) {

    val isValid: Boolean
        get() = isValidYoutube(raw)

    val id: String
        get() = youtubeId(raw)

    val idOrNull: String?
        get() = youtubeIdOrNull(raw)

    fun validate() {
        validateYoutubeUrl(raw)
    }

    val short: YoutubeVideoUrl
        get() = shortUrl(id)

    val embed: YoutubeVideoUrl
        get() = embedUrl(id)

    val normal: YoutubeVideoUrl
        get() = normalUrl(id)

    companion object {
        fun shortUrl(id: String): YoutubeVideoUrl {
            return YoutubeVideoUrl("https://youtu.be/${id}")
        }

        fun embedUrl(id: String): YoutubeVideoUrl {
            return YoutubeVideoUrl("https://www.youtube.com/embed/${id}")
        }

        fun normalUrl(id: String): YoutubeVideoUrl {
            return YoutubeVideoUrl("https://www.youtube.com/watch?v=${id}")
        }
    }
}

const val YOUTUBE_ID_LENGTH = 11

val YOUTUBE_REGEX =
    "(?:(?:https?:)?//)?(?:(?:www\\.|m\\.)?youtube(?:-nocookie)?\\.com|youtu\\.?be)/(?:attribution_link\\?[au]=[^/]*/)?(?:watch\\?v=|embed/|v/|watch/)?([\\w\\-]{10,12})[^&?]*.*".toRegex()

fun isValidYoutube(url: String?): Boolean {
    return url != null && url.matches(YOUTUBE_REGEX) && youtubeId(url).length == YOUTUBE_ID_LENGTH
}

fun validateYoutubeUrl(url: String) {
    val id = youtubeId(url)
    val isValid = isValidYoutube(url)
    require(isValid) { "url is not valid: $url id: $id" }
}

fun matchIndexOrNull(url: String, index: Int): String? {
    val matchResult = YOUTUBE_REGEX.matchEntire(url)
    matchResult?.apply {
        if (groupValues.size >= index + 1) {
            return groupValues[index]
        }
    }
    return null
}

fun youtubeIdOrNull(url: String): String? {
    return matchIndexOrNull(url, 1)
}


fun youtubeId(url: String): String {
    return youtubeIdOrNull(url) ?: error("id not found for url: $url")
}