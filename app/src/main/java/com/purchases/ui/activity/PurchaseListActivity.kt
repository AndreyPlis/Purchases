package com.purchases.ui.activity


import android.content.*
import android.os.*
import android.support.v7.widget.*
import android.view.*
import android.widget.*
import com.hannesdorfmann.mosby3.mvp.*
import com.purchases.R
import com.purchases.mvp.model.*
import com.purchases.mvp.presenter.*
import com.purchases.mvp.view.*
import com.purchases.ui.adapter.*
import com.purchases.ui.dialog.*
import io.realm.*
import java.util.*


class PurchaseListActivity : MvpActivity<PurchaseListView, PurchaseListPresenter>(), PurchaseListView, PurchaseListDialog.NoticeDialogListener {
    lateinit var realm: Realm
    private lateinit var recyclerView: RecyclerView


    override fun createPresenter(): PurchaseListPresenter {
        return PurchaseListPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchases)

        realm = Realm.getDefaultInstance()
        recyclerView = findViewById<View>(R.id.recycle_view_purchases) as RecyclerView
        setUpRecyclerView()


        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        findViewById<View>(R.id.fab_purchases).setOnClickListener {
            onClickAdd()
        }
    }

    private fun setUpRecyclerView() {
        val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = PurchaseListAdapter(this, realm.where(PurchaseList::class.java).findAll().sort("name"))
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }


    private fun onClickAdd() {
        val f = PurchaseListDialog()
        f.show(supportFragmentManager, "dialog")
    }

    fun editPurchases(purchase: PurchaseList) {
        val intent = Intent(this, EditPurchaseActivity::class.java)
        intent.putExtra("purchaseList", purchase.id)
        startActivity(intent)
    }

    fun buyPurchases(purchase: PurchaseList) {
        val intent = Intent(this, BuyPurchaseActivity::class.java)
        intent.putExtra("purchaseList", purchase.id)
        startActivity(intent)
    }

    fun addToFavorite(purchase: PurchaseList) {
        val id = purchase.id
        realm.executeTransactionAsync { realm ->
            val ps = realm.where(PurchaseList::class.java).equalTo("id", id).findFirst()
            var favorites = realm.createObject(FavoriteList::class.java, UUID.randomUUID().toString())
            favorites.name = ps!!.name
            val pur: RealmList<Purchase> = RealmList()
            for (tpurchase in ps.purchases) {
                val p = realm.createObject(Purchase::class.java, UUID.randomUUID().toString())
                p.count = tpurchase.count
                p.good = tpurchase.good
                p.measure = tpurchase.measure
                pur.add(p)
            }
            favorites.purchase = pur
        }
    }

    override fun onDialogNegativeClick(dialog: PurchaseListDialog) {

    }

    override fun onDialogPositiveClick(dialog: PurchaseListDialog) {
        val description = dialog.dialog.findViewById<View>(R.id.editText2) as TextView
        presenter.createPurchases(realm, description.text.toString())
    }
}
