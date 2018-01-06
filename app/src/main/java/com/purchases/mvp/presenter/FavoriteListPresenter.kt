package com.purchases.mvp.presenter

import com.hannesdorfmann.mosby3.mvp.*
import com.purchases.mvp.model.*
import com.purchases.mvp.view.*
import io.realm.*


class FavoriteListPresenter : MvpBasePresenter<PurchaseListView>() {

    fun addPurchase(realm: Realm, purchases: String, favorite: String) {
        realm.executeTransactionAsync { realm ->
            val fav = realm.where(FavoriteList::class.java).equalTo("id", favorite).findFirst()!!
            val pur = realm.where(PurchaseList::class.java).equalTo("id", purchases).findFirst()!!

            for (purchase in fav.purchase) {
                val p = Purchase()
                p.count = purchase.count
                p.good = purchase.good
                p.measure = purchase.measure

                var duplicated = false
                for (purchase2 in pur.purchases) {
                    if (purchase2.good == p.good && purchase2.measure == p.measure) {
                        purchase2.count += p.count
                        duplicated = true
                        break
                    }
                }
                if (duplicated)
                    continue
                realm.copyToRealm(p)
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