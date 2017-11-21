package com.purchases.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import com.purchases.R
import com.purchases.mvp.model.Good
import com.purchases.mvp.model.Measure
import com.purchases.mvp.model.Purchase
import com.purchases.mvp.model.Purchases
import com.purchases.mvp.presenter.PurchasePresenter
import com.purchases.mvp.view.PurchaseView
import com.purchases.ui.adapter.PurchaseAdapter
import io.realm.Realm


class EditPurchaseActivity : MvpActivity<PurchaseView, PurchasePresenter>(), PurchaseView {
    lateinit var realm: Realm
    private lateinit var recyclerView: RecyclerView
    private var idPurchases: Long = 0


    override fun createPresenter(): PurchasePresenter {
        return PurchasePresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_purchase)

        idPurchases = intent.getLongExtra("purchases", 0)

        realm = Realm.getDefaultInstance()
        recyclerView = findViewById<View>(R.id.edit_purchase) as RecyclerView
        setUpRecyclerView()


        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        findViewById<View>(R.id.fabAddPurchase).setOnClickListener {
            onClickAdd()
        }

        findViewById<View>(R.id.fabAddFavoritePurchase).setOnClickListener {
            onClickAddFavorite()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

    }

    private fun setUpRecyclerView() {
        val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = PurchaseAdapter(this, realm.where(Purchases::class.java).equalTo("id", idPurchases).findFirst()!!.purchase)
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val goodName = data!!.getStringExtra("goods")
        val count = data!!.getFloatExtra("count",0f)
        val measure = data!!.getStringExtra("measure")
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

    private fun onClickAdd() {
        val intent = Intent(this, SearchActivity::class.java)
        startActivityForResult(intent, 1)
    }

    private fun onClickAddFavorite() {

    }
}
