package com.github.hovi.kotlintools.dom

import org.w3c.dom.Image
import org.w3c.dom.events.Event
import kotlin.js.Promise

fun preloadImage(
    url: String
): Promise<Event> {
    //TODO: check API, onerror parameters
    return Promise { resolve, reject ->
        val img = Image()
        img.src = url
        img.onload = {
            resolve(it)
        }
        img.onerror = { errorMsg, url, lineNumber, column, errorObj ->
            reject(errorMsg)
        }
    }
}
