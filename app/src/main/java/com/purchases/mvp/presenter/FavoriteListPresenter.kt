package com.purchases.mvp.presenter

import com.hannesdorfmann.mosby3.mvp.*
import com.purchases.mvp.model.*
import com.purchases.mvp.view.*
import io.realm.*
import java.util.*


class FavoriteListPresenter : MvpBasePresenter<PurchaseListView>() {

    fun addPurchase(realm: Realm, purchases: String, favorite: String) {
        realm.executeTransactionAsync { realm ->
            val fav = realm.where(FavoriteList::class.java).equalTo("id", favorite).findFirst()!!
            val pur = realm.where(PurchaseList::class.java).equalTo("id", purchases).findFirst()!!

            for (purchase in fav.purchase) {
                val p = realm.createObject(Purchase::class.java, UUID.randomUUID().toString())
                p.count = purchase.count
                p.good = purchase.good
                p.measure = purchase.measure
                pur.purchases.add(p)
            }
        }
    }

    fun deleteFavorite(realm: Realm, favorite: String) {
        realm.executeTransactionAsync { realm ->
            realm.where(FavoriteList::class.java).equalTo("id", favorite)
                    .findFirst()!!
                    .deleteFromRealm()
        }
    }
}