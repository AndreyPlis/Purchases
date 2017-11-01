package com.purchases.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.purchases.R
import com.purchases.mvp.model.Purchase
import com.purchases.ui.activity.EditPurchaseActivity
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

class PurchaseAdapter(private val activity: EditPurchaseActivity, data: OrderedRealmCollection<Purchase>) : RealmRecyclerViewAdapter<Purchase, PurchaseAdapter.MyViewHolder>(data, true) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_purchases, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // holder.name.text = data!![position].name
        holder.purchase = data!![position]

    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnLongClickListener, View.OnClickListener {

        var name: TextView = view.findViewById(R.id.textView)
        lateinit var purchase: Purchase

        init {
            view.setOnLongClickListener(this)
            view.setOnClickListener(this)
        }

        override fun onLongClick(v: View): Boolean {
            return true
        }

        override fun onClick(view: View) {
        }
    }
}