package org.example.internship.data.updaters.link

import org.example.internship.data.Link
import org.example.internship.data.updaters.ComponentConfigurationUtil.randomString
import org.example.internship.data.updaters.ComponentUpdater

enum class InvalidLinkUpdaters(override val updater: Link.() -> Unit) : ComponentUpdater<Link> {
    Href({
        href = randomString()
    })
}