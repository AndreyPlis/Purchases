package com.purchases.ui.activity


import android.content.Intent
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

import com.purchases.mvp.presenter.PurchasesPresenter
import com.purchases.mvp.view.PurchasesView
import com.purchases.ui.adapter.PurchasesAdapter
import com.purchases.ui.dialog.PurchasesDialog
import io.realm.Realm


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

    override fun onDialogNegativeClick(dialog: PurchasesDialog) {

    }

    override fun onDialogPositiveClick(dialog: PurchasesDialog) {
        val description = dialog.dialog.findViewById<View>(R.id.textView4) as TextView
        presenter.createPurchases(realm, description.text.toString())
    }
}
