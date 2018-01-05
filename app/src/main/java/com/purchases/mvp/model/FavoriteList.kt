package com.purchases.mvp.model


import io.realm.*
import io.realm.annotations.*
import java.util.*

open class FavoriteList(

        @PrimaryKey var id: Long = 0,
        var name: String = "",

        var dateUpdate: Date = Date(),

        var purchase: RealmList<Purchase> = RealmList()


) : RealmObject() {
    constructor() : this(System.currentTimeMillis())
}