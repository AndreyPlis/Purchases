package com.purchases.mvp.presenter

import com.hannesdorfmann.mosby3.mvp.*
import com.purchases.mvp.model.*
import com.purchases.mvp.view.*
import io.realm.*

class PurchasePresenter : MvpBasePresenter<PurchaseView>() {
    fun createPurchase(realm: Realm, goodName: String, measure: String, count: Float, idPurchases: String) {
        realm.executeTransactionAsync { realm ->

            var good = realm.where(Good::class.java).equalTo("name", goodName).findFirst()
            if (good == null) {
                good = realm.createObject(Good::class.java, goodName)
            }
            val purchase = Purchase()
            purchase.good = good
            purchase.count = count
            purchase.measure = realm.where(Measure::class.java).equalTo("name", measure).findFirst()

            val purchases = realm.where(PurchaseList::class.java).equalTo("id", idPurchases).findFirst()
            val ps = purchases!!.purchases
            for (purchase2 in ps) {
                if (purchase2.good == purchase.good && purchase2.measure == purchase.measure) {
                    purchase2.count += purchase.count
                    return@executeTransactionAsync
                }
            }
            realm.copyToRealm(purchase)
            ps.add(purchase)
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

    fun updatePurchase(realm: Realm, item: Purchase, count: Float, measure: Measure) {
        var id = item.id
        var name = measure.name
        realm.executeTransactionAsync { realm ->
            var purchase = realm.where(Purchase::class.java).equalTo("id", id).findFirst()
            purchase!!.count = count
            purchase.measure = realm.where(Measure::class.java).equalTo("name", name).findFirst()
        }
    }
}