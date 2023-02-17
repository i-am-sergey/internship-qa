package org.example.internship.data.updaters.format

import org.example.internship.data.Format
import org.example.internship.data.updaters.ComponentConfigurationUtil.oneOf
import org.example.internship.data.updaters.ComponentConfigurationUtil.randomString
import org.example.internship.data.updaters.ComponentUpdater

enum class InvalidFormatUpdaters(override val updater: Format.() -> Unit) : ComponentUpdater<Format> {
    BoldOrItalicWithColor({
        style = oneOf(FormatStyle.Bold, FormatStyle.Italic).toString()
        color = getColorCode()
    }),
    BoldOrItalicWithInvalidColor({
        style = oneOf(FormatStyle.Bold, FormatStyle.Italic).toString()
        color = getInvalidColorCode()
    }),
    InvalidColor({
        style = FormatStyle.Color.toString()
        color = getInvalidColorCode()
    }),

    InvalidStyle({
        style = randomString()
    }),
}

fun getInvalidColorCode(): String {
    val length = (1..10).random()
    return randomString().padStart(length).substring(length)
}