package com.github.hovi.kotlintools.youtube

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri


fun watchYoutubeVideoUri(id: String): Uri {
    return try {
        Uri.parse("vnd.youtube:$id")
    } catch (exception: ActivityNotFoundException) {
        Uri.parse(YoutubeVideoUrl.normalUrl(id).raw)
    }
}

fun watchYoutubeVideoIntent(id: String): Intent {
    return Intent(Intent.ACTION_VIEW, watchYoutubeVideoUri(id))
}

fun Activity.watchYoutubeVideo(url: YoutubeVideoUrl) {
    url.idOrNull?.let { id ->
        startActivity(watchYoutubeVideoIntent(id))
    }
}