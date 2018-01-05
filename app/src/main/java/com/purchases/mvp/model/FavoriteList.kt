package com.purchases.mvp.model


import io.realm.*
import io.realm.annotations.*
import java.util.*

open class FavoriteList(

        @PrimaryKey var id: String = UUID.randomUUID().toString(),
        var name: String = "",

        var dateUpdate: Date = Date(),

        var purchase: RealmList<Purchase> = RealmList()


) : RealmObject()