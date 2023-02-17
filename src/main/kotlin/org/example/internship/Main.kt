package org.example.internship

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.example.internship.ComponentsReader.json
import org.example.internship.data.*
import org.example.internship.data.List
import org.example.internship.data.updaters.ComponentConfigurationUtil
import java.nio.file.Files
import java.nio.file.Path

fun main(args: Array<String>) {
    repeat(3) {
        val components = ComponentConfigurationUtil.generateElements(3..10, 3..10, true)
        val encodeToString = json.encodeToString(components)

        Files.writeString(Path.of("$it.json"), encodeToString)
    }

}