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
        var good = data!!.getStringArrayExtra("good")
        /*realm.executeTransactionAsync { realm ->

         var good = realm.where(Good::class.java).equalTo("name",good.name).findFirst()
         if(good == null)
         {
             good = realm.createObject(Good::class.java, good?.name)
         }

     }*/
    }

    private fun onClickAdd() {
        val intent = Intent(this, SearchActivity::class.java)
        startActivityForResult(intent,1)
        /*realm.executeTransactionAsync { realm ->
            var good = realm.createObject(Goods::class.java, "gfhfhfg")
            var purchase = realm.createObject(Purchase::class.java, System.currentTimeMillis())
            purchase.count = 1.0f
            purchase.good = good

            realm.where(Purchases::class.java).equalTo("id", idPurchases)
                    .findFirst()!!
                    .purchase.add(purchase)
        }*/

    }

    private fun onClickAddFavorite() {

    }
}
