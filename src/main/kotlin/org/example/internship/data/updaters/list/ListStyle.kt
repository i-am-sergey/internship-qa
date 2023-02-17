package org.example.internship.data.updaters.list

enum class ListStyle {
    Bullet, Numerical, Plain;

    override fun toString(): String {
        return name.lowercase()
    }
}