package com.purchases.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by User on 023 23.10.17.
 */

open class Goods(
         @PrimaryKey var name: String = ""

) : RealmObject() {
}