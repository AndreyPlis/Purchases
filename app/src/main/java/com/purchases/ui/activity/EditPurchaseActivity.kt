package com.purchases.ui.activity

import android.content.*
import android.os.*
import android.support.v7.widget.*
import android.view.*
import com.hannesdorfmann.mosby3.mvp.*
import com.purchases.R
import com.purchases.mvp.model.*
import com.purchases.mvp.presenter.*
import com.purchases.mvp.view.*
import com.purchases.ui.adapter.*
import com.purchases.ui.dialog.*
import io.realm.*


class EditPurchaseActivity : MvpActivity<PurchaseView, PurchasePresenter>(), PurchaseView, GoodsDialog.NoticeDialogListener {

    lateinit var realm: Realm
    private lateinit var recyclerView: RecyclerView
    private var idPurchases: Long = 0


    override fun createPresenter(): PurchasePresenter {
        return PurchasePresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_purchase)

        idPurchases = intent.getLongExtra("purchaseList", 0)

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
        recyclerView.adapter = PurchaseAdapter(this, realm.where(PurchaseList::class.java).equalTo("id", idPurchases).findFirst()!!.purchase)
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null) {
            val goodName = data.getStringExtra("goods")
            val count = data.getFloatExtra("count", 0f)
            val measure = data.getStringExtra("measure")
            presenter.createPurchase(realm, goodName, measure, count, idPurchases)
        }
    }


    fun deletePurchase(purchase: Purchase) {
        presenter.deletePurchase(realm, purchase)
    }

    fun editPurchase(purchase: Purchase) {
        val f = GoodsDialog()
        f.realm = realm
        f.good = purchase.good
        f.count = purchase.count
        f.measure = purchase.measure!!.name
        f.purchase = purchase
        f.show(supportFragmentManager, "dialog")
    }

    private fun onClickAdd() {
        val intent = Intent(this, SearchActivity::class.java)
        startActivityForResult(intent, 1)
    }

    private fun onClickAddFavorite() {
        val intent = Intent(this, FavoriteActivity::class.java)
        intent.putExtra("purchaseList", idPurchases)
        startActivity(intent)
    }

    override fun onDialogPositiveClick(good: Good, purchase: Purchase?, count: Float, measure: Measure) {
        presenter.updatePurchase(realm, purchase!!, count, measure)
    }

    override fun onDialogNegativeClick() {
    }
}
