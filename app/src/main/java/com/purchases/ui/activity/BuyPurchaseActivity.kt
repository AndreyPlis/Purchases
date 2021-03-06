package com.purchases.ui.activity

import android.os.*
import android.support.v7.widget.*
import android.view.*
import com.hannesdorfmann.mosby3.mvp.*
import com.purchases.R
import com.purchases.mvp.model.*
import com.purchases.mvp.presenter.*
import com.purchases.mvp.view.*
import com.purchases.ui.adapter.*
import io.realm.*

class BuyPurchaseActivity : MvpActivity<PurchaseView, PurchasePresenter>(), PurchaseView {

    lateinit var realm: Realm
    private lateinit var recyclerView: RecyclerView
    private var idPurchases: String = ""


    override fun createPresenter(): PurchasePresenter {
        return PurchasePresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_purchase)

        idPurchases = intent.getStringExtra("purchaseList")

        realm = Realm.getDefaultInstance()
        recyclerView = findViewById<View>(R.id.buy_purchase) as RecyclerView
        setUpRecyclerView()


        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }


    private fun setUpRecyclerView() {
        val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = mLayoutManager
        val p = realm.where(PurchaseList::class.java).equalTo("id", idPurchases).findFirst()!!
        val ps = p.purchases.sort("good.name")
        recyclerView.adapter = BuyPurchaseAdapter(this, ps)
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }


    fun deletePurchase(purchase: Purchase) {
        presenter.deletePurchase(realm, purchase)
    }
}
