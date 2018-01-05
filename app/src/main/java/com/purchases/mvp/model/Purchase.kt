package com.purchases.mvp.model


import io.realm.*
import io.realm.annotations.*
import java.util.*

open class Purchase(

        @PrimaryKey var id: String = UUID.randomUUID().toString(),
        var good: Good? = null,
        var measure: Measure? = null,
        var count: Float = 0.0f


) : RealmObject()


