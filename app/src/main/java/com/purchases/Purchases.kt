package com.purchases

import android.app.Application
import com.purchases.mvp.model.Measure

import io.realm.Realm

class Purchases : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAsync { realm ->
            if (realm.where(Measure::class.java).findAll().isEmpty()) {
                realm.createObject(Measure::class.java, "шт")
                realm.createObject(Measure::class.java, "кг")
                realm.createObject(Measure::class.java, "гр")
                realm.createObject(Measure::class.java, "л")
            }
        }
        realm.close()
    }
}