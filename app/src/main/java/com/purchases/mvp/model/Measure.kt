package com.purchases.mvp.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by User on 023 23.10.17.
 */


open class Measure(@PrimaryKey var name: String = "") : RealmObject() {
}