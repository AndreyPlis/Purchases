package com.purchases.ui.activity


import android.content.*
import android.os.*
import android.support.v7.widget.*
import android.view.*
import android.widget.*
import com.hannesdorfmann.mosby3.mvp.*
import com.purchases.R
import com.purchases.mvp.model.*
import com.purchases.mvp.model.Purchases
import com.purchases.mvp.presenter.*
import com.purchases.mvp.view.*
import com.purchases.ui.adapter.*
import com.purchases.ui.dialog.*
import io.realm.*


class PurchasesActivity : MvpActivity<PurchasesView, PurchasesPresenter>(), PurchasesView, PurchasesDialog.NoticeDialogListener {
    lateinit var realm: Realm
    private lateinit var recyclerView: RecyclerView


    override fun createPresenter(): PurchasesPresenter {
        return PurchasesPresenter()
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
        recyclerView.adapter = PurchasesAdapter(this, realm.where(Purchases::class.java).findAll())
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }


    private fun onClickAdd() {
        val f = PurchasesDialog()
        f.show(supportFragmentManager, "dialog")
    }

    fun editPurchases(purchase: Purchases) {
        val intent = Intent(this, EditPurchaseActivity::class.java)
        intent.putExtra("purchases", purchase.id)
        startActivity(intent)
    }

    fun buyPurchases(purchase: Purchases) {
        val intent = Intent(this, BuyPurchaseActivity::class.java)
        intent.putExtra("purchases", purchase.id)
        startActivity(intent)
    }

    fun addToFavorite(purchase: Purchases) {
        val id = purchase.id
        realm.executeTransactionAsync { realm ->
            val ps = realm.where(Purchases::class.java).equalTo("id",id).findFirst()
            var favorites = realm.createObject(Favorites::class.java, System.currentTimeMillis())
            favorites.name = ps!!.name
            favorites.dateUpdate = ps.dateUpdate
            val pur : RealmList<Purchase> = RealmList()
            var k = 0
            for(tpurchase in ps.purchase)
            {
                val p = realm.createObject(Purchase::class.java,System.currentTimeMillis() + k++)
                p.count = tpurchase.count
                p.good = tpurchase.good
                p.measure = tpurchase.measure
                pur.add(p)
            }
            favorites.purchase = pur
        }
    }

    override fun onDialogNegativeClick(dialog: PurchasesDialog) {

    }

    override fun onDialogPositiveClick(dialog: PurchasesDialog) {
        val description = dialog.dialog.findViewById<View>(R.id.textView4) as TextView
        presenter.createPurchases(realm, description.text.toString())
    }
}
