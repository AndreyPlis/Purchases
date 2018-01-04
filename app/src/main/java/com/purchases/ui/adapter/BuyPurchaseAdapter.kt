package com.purchases.ui.adapter

import android.support.v7.widget.*
import android.view.*
import android.widget.*
import com.purchases.R
import com.purchases.mvp.model.*
import com.purchases.ui.activity.*
import io.realm.*

/**
 * Created by User on 004 04.01.18.
 */
class BuyPurchaseAdapter(private val activity: BuyPurchaseActivity, data: OrderedRealmCollection<Purchase>) : RealmRecyclerViewAdapter<Purchase, BuyPurchaseAdapter.MyViewHolder>(data, true) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_purchase, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = data!![position].good?.name
        holder.count.text = (data!![position].count.toString() +" " + data!![position].measure?.name)
        holder.purchase = data!![position]

    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        var name: TextView = view.findViewById(R.id.textView3)
        var count: TextView = view.findViewById(R.id.textView5)
        lateinit var purchase: Purchase

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            activity.deletePurchase(purchase)
        }
    }
}