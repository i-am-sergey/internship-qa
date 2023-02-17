package org.example.internship.data.updaters.chapter

import org.example.internship.data.Text
import org.example.internship.data.updaters.ComponentUpdater

enum class InvalidTextUpdaters(override val updater: Text.() -> Unit) : ComponentUpdater<Text> {
    NoValue({
    })
}