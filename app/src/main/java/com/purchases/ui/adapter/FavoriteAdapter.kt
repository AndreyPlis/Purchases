package com.purchases.ui.adapter

import android.support.v7.widget.*
import android.view.*
import android.widget.*
import com.purchases.R
import com.purchases.mvp.model.*
import com.purchases.ui.activity.*
import io.realm.*


class FavoriteAdapter(private val activity: FavoriteActivity, data: OrderedRealmCollection<FavoriteList>) : RealmRecyclerViewAdapter<FavoriteList, FavoriteAdapter.MyViewHolder>(data, true) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_purchases, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = data!![position].name
        holder.purchases = data!![position]

    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnLongClickListener, View.OnClickListener {
        var enabled = true
        var name: TextView = view.findViewById(R.id.textView)
        lateinit var purchases: FavoriteList

        init {
            view.setOnLongClickListener(this)
            view.setOnClickListener(this)
        }

        override fun onLongClick(v: View): Boolean {
            activity.presenter.deleteFavorite(activity.realm, purchases.id)
            return true
        }

        override fun onClick(view: View) {
            if (enabled) {
                activity.presenter.addPurchase(activity.realm, activity.idPurchases, purchases.id)
                enabled = false
                name.isEnabled = enabled
            }
        }
    }
}