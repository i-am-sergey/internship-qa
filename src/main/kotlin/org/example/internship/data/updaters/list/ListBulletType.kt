package org.example.internship.data.updaters.list

enum class ListBulletType {
    Circle, Square;

    override fun toString(): String {
        return name.lowercase()
    }
}