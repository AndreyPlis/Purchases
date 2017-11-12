package com.purchases.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.purchases.R
import com.purchases.mvp.model.Good


class SearchAdapter(context: Context, comparator: Comparator<Good>) : SortedListAdapter<Good>(context, Good::class.java, comparator) {
    override fun onCreateViewHolder(p0: LayoutInflater, parent: ViewGroup, p2: Int): ViewHolder<out Good> {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_good, parent, false)
        return MyViewHolder(itemView)
    }

    inner class MyViewHolder(view: View) : SortedListAdapter.ViewHolder<Good>(view), View.OnClickListener {
        override fun performBind(item: Good) {
            name.text = item.name
            good = item
        }
        var name: TextView = view.findViewById(R.id.textView2)
        lateinit var good: Good

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(view: View) {
        }
    }
}