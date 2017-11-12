package com.purchases.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.purchases.R
import com.purchases.mvp.model.Good


class SearchAdapter(listener: Listener) : RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {
    private val mListener: Listener = listener

    var goods: MutableList<Good> = ArrayList()

    interface Listener {
        fun onClicked(good: Good)
    }


    override fun getItemCount(): Int {
        return goods.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_good, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = goods[position].name
        holder.good = goods[position]

    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        var name: TextView = view.findViewById(R.id.textView2)
        lateinit var good: Good

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            mListener.onClicked(good)
        }
    }
}