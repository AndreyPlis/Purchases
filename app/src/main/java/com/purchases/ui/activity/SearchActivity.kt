package com.purchases.ui.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import com.purchases.R
import com.purchases.mvp.model.Good
import com.purchases.mvp.model.Measure
import com.purchases.ui.adapter.SearchAdapter
import com.purchases.ui.dialog.GoodsDialog
import io.realm.Realm


class SearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener, SearchAdapter.Listener, GoodsDialog.NoticeDialogListener {


    lateinit var realm: Realm
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        realm = Realm.getDefaultInstance()
        recyclerView = findViewById<View>(R.id.search_view) as RecyclerView
        setUpRecyclerView()

        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        setIntent(intent)
        handleIntent(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(this)
        return true
    }

    private fun setUpRecyclerView() {
        val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = mLayoutManager
        val adapter = SearchAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    override fun onQueryTextChange(newText: String): Boolean {
        if (!newText.isEmpty())
            handleQuery(newText)
        return false
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        return false
    }

    private fun handleIntent(intent: Intent?) {

        if (Intent.ACTION_SEARCH == intent?.action) {
            handleQuery(intent.getStringExtra(SearchManager.QUERY))
        }
    }

    private fun handleQuery(query: String) {
        val adapter = recyclerView.adapter as SearchAdapter
        val collection = realm.where(Good::class.java).contains("name", query).findAll()

        val goods: MutableList<Good> = ArrayList()
        if (collection.isLoaded) {
            goods.addAll(collection)
        }

        if (goods.isEmpty())
            goods.add(Good(query))

        adapter.goods.clear()
        adapter.goods.addAll(goods)
        adapter.notifyDataSetChanged()
    }

    override fun onClicked(good: Good) {
        val f = GoodsDialog()
        f.realm = realm
        f.show(supportFragmentManager, "dialog")
    }

    override fun onDialogPositiveClick(good: Good, count: Float, measure: Measure) {
        realm.executeTransactionAsync { realm ->

            var good = realm.where(Good::class.java).equalTo("name",good.name).findFirst()
            if(good == null)
            {
                good = realm.createObject(Good::class.java, good?.name)
            }

        }
    }

    override fun onDialogNegativeClick() {
    }
}
