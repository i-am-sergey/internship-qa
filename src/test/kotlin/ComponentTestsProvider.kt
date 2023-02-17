import org.example.internship.ComponentsReader
import org.example.internship.data.*
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import java.util.stream.Stream
import kotlin.collections.List
import org.example.internship.data.List as ListComponent

abstract class ComponentTestsProvider : ArgumentsProvider {
    private fun performTests(): List<TestResult> {
        return components
            .flatMap { it.getDescendantElementsAndSelf() }
            .flatMap { validateComponent(it) }
    }

    abstract class ComponentValidator<C : Component>(open val description: String) {
        abstract val validator: C.() -> Boolean

        fun validate(c: Component): TestResult {
            return TestResult(c, description, (c as C).validator())
        }
    }

    class ParagraphValidator(description: String, override val validator: Paragraph.() -> Boolean) :
        ComponentValidator<Paragraph>(description)

    class ListValidator(description: String, override val validator: ListComponent.() -> Boolean) :
        ComponentValidator<ListComponent>(description)

    class FormatValidator(description: String, override val validator: Format.() -> Boolean) :
        ComponentValidator<Format>(description)

    class ImageValidator(description: String, override val validator: Image.() -> Boolean) :
        ComponentValidator<Image>(description)

    class LinkValidator(description: String, override val validator: Link.() -> Boolean) :
        ComponentValidator<Link>(description)

    class ChapterValidator(description: String, override val validator: Chapter.() -> Boolean) :
        ComponentValidator<Chapter>(description)

    class TextValidator(description: String, override val validator: Text.() -> Boolean) :
        ComponentValidator<Text>(description)

    companion object {
        val paragraph = listOf(
            ParagraphValidator("Paragraph has at least one child") { children.isNotEmpty() },
            ParagraphValidator("Paragraph children are inline") { children.all { it is InlineComponent } },
        )

        val list = listOf(
            ListValidator("List has at least one child") { children.isNotEmpty() },
            ListValidator("List children are block") { children.none { it is InlineComponent } },
            ListValidator("List type is known") { style in setOf("bullet", "numerical", "plain") },
            ListValidator("List properties are compatible") {
                if (style != "numerical")
                    startWith == null
                else if (style != "bullet")
                    bulletType == null
                else
                    true
            },
            ListValidator("List with bullets has valid bullet type") {
                if (style == "bullet")
                    bulletType == null || bulletType in setOf("circle", "square")
                else
                    true
            },
            ListValidator("List with numerical type has valid startWith") {
                if (style == "numerical")
                    startWith == null || startWith!!.matches("\\d+".toRegex())
                else
                    true
            },
        )

        val format = listOf(
            FormatValidator("Format has at least one child") { children.isNotEmpty() },
            FormatValidator("Format children are inline") { children.all { it is Text } },
            FormatValidator("Format type is known") { style in setOf("bold", "italic", "color") },
            FormatValidator("Format with type color has valid color specified") {
                if (style == "color")
                    color != null && color!!.matches("[0-9a-fA-F]{6}".toRegex())
                else
                    true
            }
        )

        val image = listOf(
            ImageValidator("Image has no children") { children.isEmpty() },
            ImageValidator("Image has src specified") { src != null },
            ImageValidator("Image source and dark source files are supported") {
                val srcValidator: (String?) -> Boolean = {
                    it?.let {
                        it.substringAfterLast(".") in setOf("png", "jpg", "jpeg", "svg")
                    } ?: true
                }

                srcValidator(src) && srcValidator(darkSrc)
            }
        )

        val link = listOf(
            LinkValidator("Link has at least one child") { children.isNotEmpty() },
            LinkValidator("Link children are text") { children.all { it is Text } },
            LinkValidator("Link has valid href") {
                href != null && href!!.let { it.startsWith("https://") || it.startsWith("http://") }
            },
        )

        val chapter = listOf(
            ChapterValidator("Chapter has at least one child") { children.isNotEmpty() },
            ChapterValidator("Chapter children are block") { children.none { it is InlineComponent } },
            ChapterValidator("Chapter has title") { title != null }
        )

        val text = listOf(
            TextValidator("Text has no children") { children.isEmpty() },
            TextValidator("Text has value") { value != null },
        )

        fun <C : Component> validateComponent(component: C): List<TestResult> {
            val validators = when (component) {
                is Paragraph -> paragraph
                is ListComponent -> list
                is Format -> format
                is Image -> image
                is Link -> link
                is Chapter -> chapter
                is Text -> text
                else -> emptyList()
            }

            return validators.map {
                it.validate(component)
            }
        }

        fun Component.getDescendantElementsAndSelf(): List<Component> {
            return listOf(this) + getDescendantElements()
        }

        fun Component.getDescendantElements(): List<Component> {
            return (children + children.flatMap { it.getDescendantElements() }).toList()
        }
    }

    /*
    * Please do not edit the code below
    */

    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
        return performTests().map { Arguments.of(it) }.stream()
    }

    abstract val components: List<Component>

    class SafeComponents : ComponentTestsProvider() {
        override val components: List<Component>
            get() = ComponentsReader.getSampleComponents()
    }

    class UnsafeComponents : ComponentTestsProvider() {
        override val components: List<Component>
            get() = ComponentsReader.getUnsafeComponents()
    }
}

