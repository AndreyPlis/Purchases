package com.purchases.ui.activity

import android.app.*
import android.content.*
import android.os.*
import android.support.v7.app.*
import android.support.v7.widget.*
import android.view.*
import com.purchases.R
import com.purchases.mvp.model.*
import com.purchases.ui.adapter.*
import com.purchases.ui.dialog.*
import io.realm.*


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
        handleQuery("")
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
        val collection: RealmResults<Good>
        if (query.isEmpty()) {
            collection = realm.where(Good::class.java).findAll()
        } else {
            collection = realm.where(Good::class.java).contains("name", query).findAll()
        }

        val goods: MutableList<Good> = ArrayList()
        if (collection.isLoaded) {
            goods.addAll(collection)
        }

        if (goods.isEmpty() && !query.isEmpty())
            goods.add(Good(query))

        adapter.goods.clear()
        adapter.goods.addAll(goods)
        adapter.notifyDataSetChanged()
    }

    override fun onClicked(good: Good) {
        val f = GoodsDialog()
        f.realm = realm
        f.good = good
        f.show(supportFragmentManager, "dialog")
    }

    override fun onDialogPositiveClick(good: Good, purchase: Purchase?, count: Float, measure: Measure) {
        val intent = Intent(this, EditPurchaseActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        intent.putExtra("goods", good.name)
        intent.putExtra("count", count)
        intent.putExtra("measure", measure.name)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onDialogNegativeClick() {
    }
}
