package com.purchases.mvp.presenter

import com.hannesdorfmann.mosby3.mvp.*
import com.purchases.mvp.model.*
import com.purchases.mvp.view.*
import io.realm.*

/**
 * Created by User on 004 04.01.18.
 */
class FavoritesPresenter :  MvpBasePresenter<PurchasesView>() {

    fun addPurchase(realm: Realm,purchases: Long, favirote: Long) {
        realm.executeTransactionAsync { realm ->
            val fav =  realm.where(Favorites::class.java).equalTo("id", favirote).findFirst()!!
            val pur =  realm.where(Purchases::class.java).equalTo("id", purchases).findFirst()!!

            var k = 0
            for(purchase in fav.purchase)
            {
                val p = realm.createObject(Purchase::class.java,System.currentTimeMillis() + k++)
                p.count = purchase.count
                p.good = purchase.good
                p.measure = purchase.measure
                pur.purchase.add(p)
            }
        }
    }

    fun deleteFavorite(realm: Realm, favirote: Long) {
        realm.executeTransactionAsync { realm ->
             realm.where(Favorites::class.java).equalTo("id", favirote)
                     .findFirst()!!
                     .deleteFromRealm()
        }
    }
}