package com.purchases.mvp.presenter


import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import com.purchases.mvp.model.Purchases
import com.purchases.mvp.view.PurchasesView
import io.realm.Realm


class PurchasesPresenter : MvpBasePresenter<PurchasesView>() {
    fun createPurchases(realm: Realm, description: String) {
        realm.executeTransactionAsync { realm ->
            var purchases = realm.createObject(Purchases::class.java, System.currentTimeMillis())
            purchases.name = description
        }
    }

    fun deletePurchases(realm: Realm, item: Purchases) {
        val id = item.id
        realm.executeTransactionAsync { realm ->
            realm.where(Purchases::class.java).equalTo("id", id)
                    .findFirst()!!
                    .deleteFromRealm()
        }
    }

}