package org.example.internship.data.updaters.chapter

import org.example.internship.data.Text
import org.example.internship.data.updaters.ComponentConfigurationUtil.randomAzString
import org.example.internship.data.updaters.ComponentUpdater

enum class ValidTextUpdaters(override val updater: Text.() -> Unit) : ComponentUpdater<Text> {
    Value({
        value = randomAzString(5..15)
    })
}