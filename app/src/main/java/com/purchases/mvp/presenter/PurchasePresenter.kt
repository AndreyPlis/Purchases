package com.purchases.mvp.presenter

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import com.purchases.mvp.model.Goods
import com.purchases.mvp.model.Measure
import com.purchases.mvp.model.Purchase
import com.purchases.mvp.model.Purchases
import com.purchases.mvp.view.PurchaseView
import io.realm.Realm

class PurchasePresenter : MvpBasePresenter<PurchaseView>() {
    fun createPurchase(realm: Realm, good: Goods, measure : Measure, count : Float ) {

        realm.executeTransactionAsync { realm ->
            var purchase = realm.createObject(Purchase::class.java, System.currentTimeMillis())
            purchase.good = good
            purchase.measure = measure
            purchase.count =count
        }
    }

    fun deletePurchase(realm: Realm, item: Purchase) {
        val id = item.id
        realm.executeTransactionAsync { realm ->
            realm.where(Purchases::class.java).equalTo("id", id)
                    .findFirst()!!
                    .deleteFromRealm()
        }
    }

    fun updatePurchase(realm: Realm, item: Purchase) {
        val id = item.id
        realm.executeTransactionAsync { realm ->
            realm.where(Purchases::class.java).equalTo("id", id)
                    .findFirst()!!
                    .deleteFromRealm()
        }
    }
}