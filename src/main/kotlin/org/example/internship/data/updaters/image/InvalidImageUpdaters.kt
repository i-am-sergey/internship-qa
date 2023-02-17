package org.example.internship.data.updaters.image

import org.example.internship.data.Image
import org.example.internship.data.updaters.ComponentConfigurationUtil.oneOf
import org.example.internship.data.updaters.ComponentUpdater
import kotlin.random.Random

enum class InvalidImageUpdaters(override val updater: Image.() -> Unit) : ComponentUpdater<Image> {
    Src({
        src = getRandomPrefix() + Random.nextInt(100) + getRandomInvalidExtension()
    }),

    ValidSrcInvalidDarkSrc({
        src = getRandomPrefix() + Random.nextInt(100) + getRandomValidExtension()
        darkSrc = getRandomPrefix() + Random.nextInt(100) + getRandomInvalidExtension()
    }),
    InvalidSrcValidDarkSrc({
        src = getRandomPrefix() + Random.nextInt(100) + getRandomInvalidExtension()
        darkSrc = getRandomPrefix() + Random.nextInt(100) + getRandomValidExtension()
    }),

    DarkSrcOnly({
        darkSrc = getRandomPrefix() + Random.nextInt(100) + getRandomValidExtension()
    }),
    InvalidDarkSrc({
        darkSrc = getRandomPrefix() + Random.nextInt(100) + getRandomInvalidExtension()
    })
}

fun getRandomInvalidExtension() : String {
    return "." + oneOf("html", "bmp", "mp4")
}