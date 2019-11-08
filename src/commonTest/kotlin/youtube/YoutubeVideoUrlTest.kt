package eu.karelhovorka.tools.youtube

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


val validUrls = """
http://www.youtube.com/watch?v=0zM3nApSvMg&feature=feedrec_grec_index
http://www.youtube.com/v/0zM3nApSvMg?fs=1&amp;hl=en_US&amp;rel=0
http://www.youtube.com/watch?v=0zM3nApSvMg#t=0m10s
http://www.youtube.com/embed/0zM3nApSvMg?rel=0
http://www.youtube.com/watch?v=0zM3nApSvMg
http://youtu.be/0zM3nApSvMg
//www.youtube-nocookie.com/embed/0zM3nApSvMg
http://www.youtube.com/watch?v=cKZDdG9FTKY&feature=channel
http://www.youtube.com/watch?v=yZ-K7nCVnBI&playnext_from=TL&videos=osPknwzXEas&feature=sub
http://youtu.be/6dwqZw0j_jY
http://www.youtube.com/watch?v=6dwqZw0j_jY&feature=youtu.be
http://youtu.be/afa-5HQHiAs
http://www.youtube.com/watch?v=cKZDdG9FTKY&feature=channel
http://www.youtube.com/watch?v=yZ-K7nCVnBI&playnext_from=TL&videos=osPknwzXEas&feature=sub
http://www.youtube.com/embed/nas1rJpm7wY?rel=0
http://www.youtube.com/watch?v=peFZbP64dsU
http://youtube.com/v/dQw4w9WgXcQ?feature=youtube_gdata_player
http://www.youtube.com/watch?v=dQw4w9WgXcQ&feature=youtube_gdata_player
http://youtube.com/watch?v=dQw4w9WgXcQ&feature=youtube_gdata_player
http://youtu.be/dQw4w9WgXcQ?feature=youtube_gdata_player'
""".trimIndent()

class YoutubeVideoUrlTest {
    @Test
    fun isValid() {
        validateUrl("youtu.be/NLqAF9hrVbY")
        validateUrl("http://youtu.be/NLqAF9hrVbY")
        validateUrl("https://youtu.be/NLqAF9hrVbY")
        validateUrl("http://m.youtube.com/embed/NLqAF9hrVbY")
        validateUrl("http://www.youtube.com/embed/NLqAF9hrVbY")
        validateUrl("http://www.youtube.com/v/NLqAF9hrVbY?fs=1&hl=en_US")
        validateUrl("http://www.youtube.com/watch?v=NLqAF9hrVbY")
        validateUrl("http://m.youtube.com/watch?v=NLqAF9hrVbY")
        validateUrl("http://youtube.com/watch?v=NLqAF9hrVbY")
        validateUrl("http://www.youtube.com/watch?v=JYArUl0TzhA&feature=featured")
        validUrls.trim().lines().forEach { line -> validateUrl(line) }
    }

    @Test
    fun isInValid() {
        isInvalid("asdf://youtu.be/NLqAF9hrVbY")
        isInvalid("https://youtu.be/asdf")
        isInvalid("http://m.youtube.com/embed/NLqAF9hrVb")
        isInvalid("http://www.youtube.com/asdf/NLqAF9hrVbY")
        isInvalid("http://asdf.youtube.com/v/NLqAF9hrVbY?fs=1&hl=en_US")
        isInvalid("http://www.youtube.com/watch?v=asdf")
        isInvalid("http://www.youtube.com/watch?v=asdf&feature=featured")
    }

    private fun validateUrl(url: YoutubeVideoUrl) {
        url.validate()
    }

    private fun isInvalid(url: YoutubeVideoUrl) {
        assertFalse(url.isValid)
    }

    @Test
    fun validateId() {
        validateId("http://youtu.be/NLqAF9hrVbY", "NLqAF9hrVbY")
        validateId("http://www.youtube.com/embed/NLqAF9hrVbY", "NLqAF9hrVbY")
        validateId("http://www.youtube.com/v/NLqAF9hrVbY?fs=1&hl=en_US", "NLqAF9hrVbY")
        validateId("http://www.youtube.com/watch?v=NLqAF9hrVbY", "NLqAF9hrVbY")
        validateId("http://www.youtube.com/watch?v=JYArUl0TzhA&feature=featured", "JYArUl0TzhA")
        validateId("http://m.youtube.com/watch?v=JYArUl0TzhA&feature=featured", "JYArUl0TzhA")

    }

    @Test
    fun testPlaylists() {
        validatePlaylist("https://www.youtube.com/watch?v=pFS4zYWxzNA&list=PL1F9CA2A03CF286C2&", "PL1F9CA2A03CF286C2")
        validatePlaylist("https://youtu.be/oTJRivZTMLs?list=PLToa5JuFMsXTNkrLJ", "PLToa5JuFMsXTNkrLJ")
        validatePlaylist(
            "https://www.youtube.com/watch?v=4jduuQh-Uho&list=PLBOh8f9FoHHjOz0vGrD20WcTtJar-LOrw&index=3",
            "PLBOh8f9FoHHjOz0vGrD20WcTtJar-LOrw"
        )
    }

    private fun validatePlaylist(url: YoutubeVideoUrl, expectedPlaylistId: String) {
        assertTrue(url.isValid)
        //TODO:
    }

    private fun validateId(url: YoutubeVideoUrl, expectedId: String) {
        val id = url.id
        assertEquals(YOUTUBE_ID_LENGTH, id.length)
        assertEquals(id, expectedId)
        url.validate()
        assertEquals(url.id, url.mobile.id)
        assertEquals(url.id, url.embed.id)
        assertEquals(url.mobile.id, url.embed.id)
    }


}