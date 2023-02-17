package org.example.internship.data.updaters.list

import org.example.internship.data.List
import org.example.internship.data.updaters.ComponentUpdater
import kotlin.random.Random
import kotlin.random.nextInt

enum class ValidListUpdaters(override val updater: List.() -> Unit) : ComponentUpdater<List> {
    Bullet({
        style = ListStyle.Bullet.toString()
    }),
    Numerical({
        style = ListStyle.Numerical.toString()
    }),
    Plain({
        style = ListStyle.Plain.toString()
    }),

    BulletCircle({
        style = ListStyle.Bullet.toString()
        bulletType = ListBulletType.Circle.toString()
    }),
    BulletSquare({
        style = ListStyle.Bullet.toString()
        bulletType = ListBulletType.Square.toString()
    }),

    NumericalStartWith({
        style = ListStyle.Numerical.toString()
        startWith = Random.nextInt(0..100).toString()
    }),
}