package org.example.internship.data.updaters

import org.example.internship.data.Component

interface ComponentUpdater<C : Component> {
    val updater: C.() -> Unit

    fun apply(c: Component) {
        (c as C).updater()
    }
}