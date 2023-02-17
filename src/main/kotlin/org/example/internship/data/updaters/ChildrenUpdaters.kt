package org.example.internship.data.updaters

import org.example.internship.data.Component
import org.example.internship.data.Text
import org.example.internship.data.updaters.ComponentConfigurationUtil.getBlockElement
import org.example.internship.data.updaters.ComponentConfigurationUtil.getInlineElement
import org.example.internship.data.updaters.ComponentConfigurationUtil.getTerminalBlockElement
import org.example.internship.data.updaters.ComponentConfigurationUtil.getTerminalInlineElement
import org.example.internship.data.updaters.ComponentConfigurationUtil.oneChanceIn
import org.example.internship.data.updaters.ComponentConfigurationUtil.oneOf
import kotlin.random.Random
import kotlin.random.nextInt

enum class ChildrenUpdaters(override val updater: Component.() -> Unit) : ComponentUpdater<Component> {
    Nothing({

    }),

    Text({
        children += Text()
    }),

    Element({
        children += oneOf(getBlockElement(), getInlineElement())
    }),

    BlockElements({
        repeat(Random.nextInt(1..10)) {
            children += getBlockElement()
        }
    }),

    InlineElements({
        repeat(Random.nextInt(1..10)) {
            children += getInlineElement()
        }
    }),

    BlockElementsWithErrors({
        repeat(Random.nextInt(1..10)) {
            children += if (oneChanceIn(10)) getBlockElement() else getInlineElement()
        }
    }),

    InlineElementsWithErrors({
        repeat(Random.nextInt(1..10)) {
            children += if (oneChanceIn(10)) getInlineElement() else getBlockElement()
        }
    }),

    TerminalBlockElement({
        children += getTerminalBlockElement()
    }),
    TerminalInlineElement({
        children += getTerminalInlineElement()
    })
}