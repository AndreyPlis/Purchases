package com.purchases

import android.app.Application

import io.realm.Realm

class Purchases : Application() {


    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}