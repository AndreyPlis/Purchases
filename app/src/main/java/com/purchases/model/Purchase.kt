package com.purchases.model

/**
 * Created by User on 023 23.10.17.
 */

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Purchase(

        @PrimaryKey var id: Long = 0,
        var good: Goods? = null,
        var measure : Measure? = null,
        var count : Float = 0.0f


) : RealmObject() {

    constructor() : this(System.currentTimeMillis())
}


