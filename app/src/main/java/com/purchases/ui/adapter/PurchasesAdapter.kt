package com.purchases.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import com.purchases.R
import com.purchases.mvp.model.Purchases
import com.purchases.ui.activity.PurchasesActivity
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter


class PurchasesAdapter(private val activity: PurchasesActivity, data: OrderedRealmCollection<Purchases>) : RealmRecyclerViewAdapter<Purchases, PurchasesAdapter.MyViewHolder>(data, true) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_purchases, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = data!![position].name
        holder.purchases = data!![position]

    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnLongClickListener, View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        override fun onMenuItemClick(item: MenuItem?): Boolean {
            when (item!!.itemId) {
                R.id.menuBuy -> {
                    activity.buyPurchases(purchases)
                }
                R.id.menyDelete -> {
                    activity.presenter.deletePurchases(activity.realm, purchases)
                }
            }
            return false
        }

        var name: TextView = view.findViewById(R.id.textView)
        lateinit var purchases: Purchases

        init {
            view.setOnLongClickListener(this)
            view.setOnClickListener(this)
        }

        override fun onLongClick(v: View): Boolean {
            val popup = PopupMenu(v.context, v)
            popup.inflate(R.menu.menu_purchases)
            popup.setOnMenuItemClickListener(this)
            popup.show()

            return true
        }

        override fun onClick(view: View) {
            activity.editPurchases(purchases)
        }
    }
}