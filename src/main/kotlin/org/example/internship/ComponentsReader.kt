package org.example.internship

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.example.internship.data.*
import java.nio.file.Files
import java.nio.file.Path

object ComponentsReader {
    private val module = SerializersModule {
        polymorphic(Component::class) {
            subclass(org.example.internship.data.List::class)
            subclass(Paragraph::class)
            subclass(Chapter::class)
            subclass(Image::class)
            subclass(Format::class)
            subclass(Link::class)
            subclass(Text::class)
        }
    }

    val json = Json {
        serializersModule = module
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    fun getSampleComponents(): List<Component> {
        val result = mutableListOf<Component>()

        repeat(3) {
            result += json.decodeFromString<List<Component>>(Files.readString(Path.of("wd/$it.json")))
        }

        return result.toList()
    }

    fun getSafeComponents(): List<Component> {
        return listOf()
    }

    fun getUnsafeComponents(): List<Component> {
        return listOf()
    }
}