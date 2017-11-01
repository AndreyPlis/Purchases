package com.purchases.ui.activity


import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import com.purchases.R
import com.purchases.mvp.model.Purchases
import com.purchases.mvp.presenter.ListPurchasesPresenter
import com.purchases.mvp.view.ListPurchasesView
import com.purchases.ui.adapter.PurchasesAdapter
import com.purchases.ui.dialog.ListPurchasesDialog
import io.realm.Realm


class PurchasesActivity : MvpActivity<ListPurchasesView, ListPurchasesPresenter>(), ListPurchasesView, ListPurchasesDialog.NoticeDialogListener {
    lateinit var realm: Realm
    private lateinit var recyclerView: RecyclerView


    override fun createPresenter(): ListPurchasesPresenter {
        return ListPurchasesPresenter()
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
        val f = ListPurchasesDialog()
        f.show(supportFragmentManager, "dialog")
    }

    fun editPurchases(purchase: Purchases) {
        /*val intent = Intent(this, PurchasesActivity::class.java)
        startActivity(intent)*/
    }

    fun buyPurchases(purchase: Purchases) {
        /*val intent = Intent(this, PurchasesActivity::class.java)
        startActivity(intent)*/
    }

    override fun onDialogNegativeClick(dialog: ListPurchasesDialog) {

    }

    override fun onDialogPositiveClick(dialog: ListPurchasesDialog) {
        val description = dialog.dialog.findViewById<View>(R.id.textView4) as TextView
        presenter.createPurchases(realm, description.text.toString())
    }
}
