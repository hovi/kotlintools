package com.github.hovi.kotlintools.dom.textfit

import com.github.hovi.kotlintools.dom.createHtmlElement
import com.github.hovi.kotlintools.dom.queryHtmlSelector
import com.github.hovi.kotlintools.dom.queryHtmlSelectorAll
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLSpanElement
import kotlin.browser.document


data class TextFitterSettings(
    val lengthUnit: String = "em",
    val minSize: Double = 0.2,
    val maxSize: Double = 30.0,
    val step: Double = 0.01,
    val innerClass: String = INNER_CLASS
) {
    companion object {
        const val INNER_CLASS = "textfit-inner-span"
    }

}

data class PixelSize(
    val height: Int,
    val width: Int
) {
    fun isWithin(pixelSize: PixelSize): Boolean {
        return height < pixelSize.height && width < pixelSize.width
    }
}

class TextFitter(val selector: String, val settings: TextFitterSettings) {


    fun fitAll(selector: String = this.selector) {
        document.queryHtmlSelectorAll<HTMLElement>(selector).forEach(::fit)
    }

    private fun fit(element: HTMLElement) {
        val innerElement = if (settings.innerClass in element.innerHTML) {
            element.queryHtmlSelector<HTMLSpanElement>(".${settings.innerClass}")
        } else {
            document.createHtmlElement<HTMLSpanElement>().apply {
                element.innerHTML = ""
                this.innerHTML = element.innerHTML
                element.appendChild(this)
            }
        }

        val pixelSize = PixelSize(
            height = element.offsetHeight,
            width = element.offsetWidth
        )
        var i: Double = settings.maxSize
        innerElement.style.fontSize = "${i}${settings.lengthUnit}"
        var innerSize = PixelSize(
            height = innerElement.scrollHeight,
            width = innerElement.scrollWidth
        )
        while (i > settings.minSize && !innerSize.isWithin(pixelSize) ) {
            i -= settings.step
            innerElement.style.fontSize = "${i}${settings.lengthUnit}"
            innerSize = PixelSize(
                height = innerElement.scrollHeight,
                width = innerElement.scrollWidth
            )
        }



    }

}