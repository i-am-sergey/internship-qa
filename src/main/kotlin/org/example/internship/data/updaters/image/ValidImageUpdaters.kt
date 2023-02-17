package org.example.internship.data.updaters.image

import org.example.internship.data.Image
import org.example.internship.data.updaters.ComponentConfigurationUtil.oneOf
import org.example.internship.data.updaters.ComponentUpdater
import kotlin.random.Random

enum class ValidImageUpdaters(override val updater: Image.() -> Unit) : ComponentUpdater<Image> {
    Src({
        src = getRandomPrefix() + Random.nextInt(100) + getRandomValidExtension()
    }),
    SrcDarkSrc({
        src = getRandomPrefix() + Random.nextInt(100) + getRandomValidExtension()
        darkSrc = getRandomPrefix() + Random.nextInt(100) + getRandomValidExtension()
    }),
}

fun getRandomPrefix() : String {
    return oneOf("image", "img", "picture", "graph", "")
}

fun getRandomValidExtension() : String {
    return "." + oneOf("png", "jpg", "jpeg", "svg")
}