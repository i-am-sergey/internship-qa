package org.example.internship.data.updaters.format

enum class FormatStyle {
    Bold, Italic, Color;

    override fun toString(): String {
        return name.lowercase()
    }
}