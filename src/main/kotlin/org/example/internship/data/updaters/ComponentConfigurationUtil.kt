package org.example.internship.data.updaters

import org.example.internship.data.*
import org.example.internship.data.List
import org.example.internship.data.updaters.chapter.InvalidChapterUpdaters
import org.example.internship.data.updaters.chapter.InvalidTextUpdaters
import org.example.internship.data.updaters.chapter.ValidChapterUpdaters
import org.example.internship.data.updaters.chapter.ValidTextUpdaters
import org.example.internship.data.updaters.format.InvalidFormatUpdaters
import org.example.internship.data.updaters.format.ValidFormatUpdaters
import org.example.internship.data.updaters.image.InvalidImageUpdaters
import org.example.internship.data.updaters.image.ValidImageUpdaters
import org.example.internship.data.updaters.link.InvalidLinkUpdaters
import org.example.internship.data.updaters.link.ValidLinkUpdaters
import org.example.internship.data.updaters.list.InvalidListUpdaters
import org.example.internship.data.updaters.list.ValidListUpdaters
import kotlin.random.Random
import kotlin.reflect.KClass

object ComponentConfigurationUtil {
    val validUpdaters = mapOf<KClass<out Component>, Array<out ComponentUpdater<out Component>>>(
        List::class to ValidListUpdaters.values(),
        Image::class to ValidImageUpdaters.values(),
        Format::class to ValidFormatUpdaters.values(),
        Link::class to ValidLinkUpdaters.values(),
        Chapter::class to ValidChapterUpdaters.values(),
        Text::class to ValidTextUpdaters.values(),
    )

    val invalidUpdaters = mapOf<KClass<out Component>, Array<out ComponentUpdater<out Component>>>(
        List::class to InvalidListUpdaters.values(),
        Image::class to InvalidImageUpdaters.values(),
        Format::class to InvalidFormatUpdaters.values(),
        Link::class to InvalidLinkUpdaters.values(),
        Chapter::class to InvalidChapterUpdaters.values(),
        Text::class to InvalidTextUpdaters.values(),
    )

    val validChildrenUpdaters = mapOf<KClass<out Component>, ComponentUpdater<out Component>>(
        List::class to ChildrenUpdaters.BlockElements,
        Image::class to ChildrenUpdaters.Nothing,
        Text::class to ChildrenUpdaters.Nothing,
        Format::class to ChildrenUpdaters.Text,
        Link::class to ChildrenUpdaters.Text,
        Paragraph::class to ChildrenUpdaters.InlineElements,
        Chapter::class to ChildrenUpdaters.BlockElements,
    )

    val invalidChildrenUpdaters = mapOf<KClass<out Component>, ComponentUpdater<Component>>(
        List::class to ChildrenUpdaters.BlockElementsWithErrors,
        Image::class to ChildrenUpdaters.Element,
        Text::class to ChildrenUpdaters.Element,
        Format::class to ChildrenUpdaters.Element,
        Link::class to ChildrenUpdaters.Element,
        Paragraph::class to ChildrenUpdaters.InlineElementsWithErrors,
        Chapter::class to ChildrenUpdaters.BlockElementsWithErrors,
    )

    val terminalChildrenUpdaters = mapOf<KClass<out Component>, ComponentUpdater<Component>>(
        List::class to ChildrenUpdaters.TerminalBlockElement,
        Image::class to ChildrenUpdaters.Nothing,
        Text::class to ChildrenUpdaters.Nothing,
        Format::class to ChildrenUpdaters.Text,
        Link::class to ChildrenUpdaters.Text,
        Paragraph::class to ChildrenUpdaters.TerminalInlineElement,
        Chapter::class to ChildrenUpdaters.TerminalBlockElement,
    )

    val requiresChildren = setOf(Paragraph::class, List::class, Format::class, Link::class, Chapter::class)

    fun generateElements(amount: IntRange, depth: IntRange, withErrors: Boolean = false): MutableList<Component> {
        val result = mutableListOf<Component>()

        repeat(amount.random()) {
            result += getBlockElement()
                .apply {
                    updateProperties(withErrors)
                    generateElements(depth.shiftLeft(), withErrors)
                }
        }

        return result
    }

    private fun Component.generateElements(depth: IntRange, withErrors: Boolean = false) {
        if (depth.any { it < 0 })
            throw IllegalStateException("Depth may be only positive")

        if (depth.last == 0)
            terminalChildrenUpdaters[this::class]?.apply(this)
        else if (withErrors && oneChanceIn(10))
            invalidChildrenUpdaters[this::class]?.apply(this)
        else
            validChildrenUpdaters[this::class]?.apply(this)

        children.forEach { child ->
            child.updateProperties(withErrors)

            val remainingDepth = depth.random()

            if (remainingDepth != 0)
                child.generateElements(depth.shiftLeft().first until remainingDepth, withErrors)
            else if (child::class in requiresChildren)
                child.generateElements(0..0, withErrors)
        }
    }

    private fun Component.updateProperties(withErrors: Boolean = false) {
        if (withErrors && oneChanceIn(10))
            invalidUpdaters[this::class]?.random()?.apply(this)
        else
            validUpdaters[this::class]?.random()?.apply(this)
    }

    fun randomString(): String {
        return Random.nextLong(0, Long.MAX_VALUE).toString(26)
    }

    fun <T> oneOf(vararg values: T): T {
        return values.random()
    }

    fun oneChanceIn(all: Int): Boolean {
        return 0 == Random.nextInt(all)
    }

    fun randomAzString(length: IntRange): String {
        if (length.any { it < 0 })
            throw IllegalStateException("Strings may be of positive length only")

        var result = ""

        repeat(length.random()) {
            result += ('a'..'z').random()
        }

        return result
    }

    fun getBlockElement(): Component {
        return oneOf(List(), Image(), Paragraph(), Chapter())
    }

    fun getInlineElement(): Component {
        return oneOf(Format(), Link(), Text())
    }

    fun getTerminalBlockElement(): Component {
        return Image()
    }

    fun getTerminalInlineElement(): Component {
        return Text()
    }

    fun IntRange.shiftLeft(): IntRange {
        return (first - 1).let { if (it < 0) 0 else it } until last
    }
}