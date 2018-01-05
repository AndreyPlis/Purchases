package com.purchases.mvp.model


import io.realm.*
import io.realm.annotations.*

open class Purchase(

        @PrimaryKey var id: Long = 0,
        var good: Good? = null,
        var measure: Measure? = null,
        var count: Float = 0.0f


) : RealmObject() {
}


