package com.purchases.mvp.model

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by User on 023 23.10.17.
 */

open class Good(@PrimaryKey var name: String = "") : RealmObject(), SortedListAdapter.ViewModel {
    override fun <T : Any?> isContentTheSameAs(item: T): Boolean {
        return isSameModelAs(item)
    }

    override fun <T : Any?> isSameModelAs(item: T): Boolean {
        if (item is Good) {
            val good = item as Good
            return good.name == name
        }
        return false
    }
}
