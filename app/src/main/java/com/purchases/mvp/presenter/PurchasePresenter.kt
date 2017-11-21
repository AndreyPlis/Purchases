package com.purchases.mvp.presenter

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import com.purchases.mvp.model.Good
import com.purchases.mvp.model.Measure
import com.purchases.mvp.model.Purchase
import com.purchases.mvp.model.Purchases
import com.purchases.mvp.view.PurchaseView
import io.realm.Realm

class PurchasePresenter : MvpBasePresenter<PurchaseView>() {
    fun createPurchase(realm: Realm, goodName: String, measure: String, count: Float, idPurchases: Long) {
        realm.executeTransactionAsync { realm ->

            var good = realm.where(Good::class.java).equalTo("name", goodName).findFirst()
            if (good == null) {
                good = realm.createObject(Good::class.java, goodName)
            }
            var purchase = realm.createObject(Purchase::class.java, System.currentTimeMillis())
            purchase.good = good
            purchase.count = count
            purchase.measure = realm.where(Measure::class.java).equalTo("name", measure).findFirst()

            var purchases = realm.where(Purchases::class.java).equalTo("id", idPurchases).findFirst()
            purchases!!.purchase.add(purchase)
        }
    }

    fun deletePurchase(realm: Realm, item: Purchase) {
        val id = item.id
        realm.executeTransactionAsync { realm ->
            realm.where(Purchase::class.java).equalTo("id", id)
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