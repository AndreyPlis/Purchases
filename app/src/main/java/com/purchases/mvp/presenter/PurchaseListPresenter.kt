package com.purchases.mvp.presenter


import com.hannesdorfmann.mosby3.mvp.*
import com.purchases.mvp.model.*
import com.purchases.mvp.view.*
import io.realm.*
import java.util.*


class PurchaseListPresenter : MvpBasePresenter<PurchaseListView>() {
    fun createPurchases(realm: Realm, description: String) {
        realm.executeTransactionAsync { realm ->
            var purchases = realm.createObject(PurchaseList::class.java, UUID.randomUUID().toString())
            purchases.name = description
        }
    }

    fun deletePurchases(realm: Realm, item: PurchaseList) {
        val id = item.id
        realm.executeTransactionAsync { realm ->
            realm.where(PurchaseList::class.java).equalTo("id", id)
                    .findFirst()!!
                    .deleteFromRealm()
        }
    }

}