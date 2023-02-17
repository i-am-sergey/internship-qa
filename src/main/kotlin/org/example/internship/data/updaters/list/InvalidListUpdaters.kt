package org.example.internship.data.updaters.list

import org.example.internship.data.List
import org.example.internship.data.updaters.ComponentConfigurationUtil.randomString
import org.example.internship.data.updaters.ComponentUpdater
import kotlin.random.Random

enum class InvalidListUpdaters(override val updater: List.() -> Unit) : ComponentUpdater<List> {
    InvalidStyle({
        style = randomString()
    }),

    NumericalBulletType({
        style = ListStyle.Numerical.toString()
        bulletType = ListBulletType.values().random().toString()
    }),
    NumericalInvalidBulletType({
        style = ListStyle.Numerical.toString()
        bulletType = randomString()
    }),

    BulletStartWith({
        style = ListStyle.Bullet.toString()
        startWith = Random.nextInt().toString()
    }),
    BulletInvalidStartWith({
        style = ListStyle.Bullet.toString()
        startWith = randomString()
    }),
    BulletInvalidStyle({
        style = ListStyle.Bullet.toString()
        bulletType = randomString()
    }),

    BulletWithTypeStartWith({
        style = ListStyle.Bullet.toString()
        bulletType = ListBulletType.values().random().toString()
        startWith = randomString()
    }),

    BulletTypeStartWith({
        bulletType = ListBulletType.values().random().toString()
        startWith = randomString()
    }),
}