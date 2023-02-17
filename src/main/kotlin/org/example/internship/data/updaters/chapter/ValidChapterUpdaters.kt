package org.example.internship.data.updaters.chapter

import org.example.internship.data.Chapter
import org.example.internship.data.updaters.ComponentConfigurationUtil.randomAzString
import org.example.internship.data.updaters.ComponentUpdater

enum class ValidChapterUpdaters(override val updater: Chapter.() -> Unit) : ComponentUpdater<Chapter> {
    Title({
        title = randomAzString(5..15)
    })
}