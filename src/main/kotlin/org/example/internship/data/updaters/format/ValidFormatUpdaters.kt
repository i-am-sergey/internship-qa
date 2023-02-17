package org.example.internship.data.updaters.format

import org.example.internship.data.Format
import org.example.internship.data.updaters.ComponentUpdater
import kotlin.random.Random

enum class ValidFormatUpdaters(override val updater: Format.() -> Unit) : ComponentUpdater<Format> {
    Bold({
        style = FormatStyle.Bold.toString()
    }),
    Italic({
        style = FormatStyle.Italic.toString()
    }),
    Color({
        style = FormatStyle.Color.toString()
        color = getColorCode()
    }),
}

fun getColorCode() : String {
    return Random.nextInt(0xffffff)
        .let { "%06x".format(it) }
}