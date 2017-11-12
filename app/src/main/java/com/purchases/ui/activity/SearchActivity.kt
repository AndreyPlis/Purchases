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
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.purchases.R
import com.purchases.mvp.model.Good
import com.purchases.ui.adapter.SearchAdapter
import io.realm.Realm


class SearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener, SortedListAdapter.Callback {
    override fun onQueryTextChange(newText: String): Boolean {
        if (!newText.isEmpty())
            handleQuery(newText)
        return false
    }

    override fun onEditFinished() {
    }

    override fun onEditStarted() {
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        return false
    }

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
        super.onCreateOptionsMenu(menu)
        return true
    }

    private fun setUpRecyclerView() {
        val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = mLayoutManager
        val adapter = SearchAdapter(this, COMPARATOR)
        recyclerView.adapter = adapter
        adapter.addCallback(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun handleIntent(intent: Intent?) {

        if (Intent.ACTION_SEARCH == intent?.action) {
            handleQuery(intent.getStringExtra(SearchManager.QUERY))
        }
    }

    private fun handleQuery(query: String) {
        val adapter = recyclerView.adapter as SearchAdapter
        val collection = realm.where(Good::class.java).equalTo("name", query).findAll()
        val collection2 = collection.toMutableList()
        if (collection.isLoaded && collection2.isEmpty())
            collection2.add(Good(query))
        adapter.edit()
                .removeAll()
                .add(collection2)
                .commit()
    }


    private val COMPARATOR = SortedListAdapter.ComparatorBuilder<Good>()
            .setOrderForModel(Good::class.java, object : Comparator<Good> {
                override fun compare(a: Good, b: Good): Int {
                    return Integer.signum(0)
                }
            })
            .build()

}
