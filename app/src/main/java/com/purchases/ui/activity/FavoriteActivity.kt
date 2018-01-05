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

class FavoriteActivity : MvpActivity<PurchaseListView, FavoriteListPresenter>(), PurchaseListView {

    lateinit var realm: Realm
    private lateinit var recyclerView: RecyclerView
    var idPurchases: Long = 0

    override fun createPresenter(): FavoriteListPresenter {
        return FavoriteListPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        idPurchases = intent.getLongExtra("purchaseList", 0)

        realm = Realm.getDefaultInstance()
        recyclerView = findViewById<View>(R.id.recyclerFavorite) as RecyclerView
        setUpRecyclerView()


        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun setUpRecyclerView() {
        val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = FavoriteAdapter(this, realm.where(FavoriteList::class.java).findAll()
        )
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
