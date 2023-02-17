package org.example.internship.data.updaters.chapter

import org.example.internship.data.Chapter
import org.example.internship.data.updaters.ComponentUpdater

enum class InvalidChapterUpdaters(override val updater: Chapter.() -> Unit) : ComponentUpdater<Chapter> {
    NoTitle({
    })
}