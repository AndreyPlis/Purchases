package com.purchases.model

/**
 * Created by User on 023 23.10.17.
 */

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Favorites(

        @PrimaryKey var id: Long = 0,
        var name: String = "",

        var dateUpdate: Date = Date(),

        var purchase: RealmList<Purchase> = RealmList()


) : RealmObject() {
    constructor() : this(System.currentTimeMillis())
}