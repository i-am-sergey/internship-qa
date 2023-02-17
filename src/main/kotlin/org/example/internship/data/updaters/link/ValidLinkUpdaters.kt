package org.example.internship.data.updaters.link

import org.example.internship.data.Link
import org.example.internship.data.updaters.ComponentConfigurationUtil.oneOf
import org.example.internship.data.updaters.ComponentConfigurationUtil.randomAzString
import org.example.internship.data.updaters.ComponentUpdater

enum class ValidLinkUpdaters(override val updater: Link.() -> Unit) : ComponentUpdater<Link> {
    Href({
        href = getValidUrl()
    })
}

fun getValidUrl(): String {
    return oneOf("https://", "http://") + randomAzString(5..10) + "." + oneOf("com", "org", "edu")
}