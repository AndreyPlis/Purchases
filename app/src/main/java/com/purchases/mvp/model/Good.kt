package com.purchases.mvp.model

import io.realm.*
import io.realm.annotations.*


open class Good(
        @PrimaryKey var name: String = ""

) : RealmObject() {
}