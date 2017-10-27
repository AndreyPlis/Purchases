package com.purchases.ui.adapter

import android.widget.TextView
import com.purchases.model.Purchase
import android.view.View.OnLongClickListener
import android.support.v7.widget.RecyclerView
import sun.security.krb5.Confounder.intValue
import javax.swing.Spring.scale
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.purchases.R
import io.realm.OrderedRealmCollection
import com.purchases.ui.activity.ListPurchasesActivity
import io.realm.RealmRecyclerViewAdapter



/**
 * Created by User on 024 24.10.17.
 */

class PurchasesAdapter(private val activity: ListPurchasesActivity, data: OrderedRealmCollection<Purchase>) : RealmRecyclerViewAdapter<Purchase, PurchasesAdapter.MyViewHolder>(data, true) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj = data!![position]
        holder.data = obj
        holder.name.setText(obj.getGoods().getName())
    }

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnLongClickListener, View.OnClickListener {
        var name: TextView
        var count: TextView
        var measure: TextView
        var data: Purchase? = null

        init {
            name = view.findViewById(R.id.list_item_name)
            count = view.findViewById(R.id.list_item_count)
            measure = view.findViewById(R.id.editText2)
            view.setOnLongClickListener(this)
            view.setOnClickListener(this)
        }

        override fun onLongClick(v: View): Boolean {
          //  activity.deleteItem(data)
            return true
        }

        override fun onClick(view: View) {
        //    activity.editItem(data)
        }
    }
}