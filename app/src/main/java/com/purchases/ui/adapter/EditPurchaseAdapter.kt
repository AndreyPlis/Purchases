package com.purchases.ui.adapter

import android.support.v7.widget.*
import android.view.*
import android.widget.*
import com.purchases.R
import com.purchases.mvp.model.*
import com.purchases.ui.activity.*
import io.realm.*
import java.math.*

class EditPurchaseAdapter(private val activity: EditPurchaseActivity, data: OrderedRealmCollection<Purchase>) : RealmRecyclerViewAdapter<Purchase, EditPurchaseAdapter.MyViewHolder>(data, true) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_purchase, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val purchase = data!![position]
        holder.name.text = purchase.good?.name
        val count = purchase.count
        val bd = BigDecimal(count.toDouble())
        if (bd.scale() == 0) {
            holder.count.text = (purchase.count.toInt().toString() + " " + purchase.measure?.name)
        } else {
            holder.count.text = (purchase.count.toString() + " " + purchase.measure?.name)
        }
        holder.purchase = purchase

    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnLongClickListener, View.OnClickListener {

        var name: TextView = view.findViewById(R.id.textView3)
        var count: TextView = view.findViewById(R.id.textView5)
        lateinit var purchase: Purchase

        init {
            view.setOnLongClickListener(this)
            view.setOnClickListener(this)
        }

        override fun onLongClick(v: View): Boolean {
            activity.editPurchase(purchase)
            return true
        }

        override fun onClick(view: View) {
            activity.deletePurchase(purchase)
        }
    }
}