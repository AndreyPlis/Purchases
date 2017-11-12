package com.purchases.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.NumberPicker
import com.purchases.R
import com.purchases.mvp.model.Good
import com.purchases.mvp.model.Measure
import io.realm.Realm

class GoodsDialog : DialogFragment() {

    private lateinit var mListener: NoticeDialogListener
    var realm: Realm? = null
    var good: Good? = null

    interface NoticeDialogListener {
        fun onDialogPositiveClick(good: Good, count: Float, measure: Measure)

        fun onDialogNegativeClick()
    }


    override fun onAttach(activity: Context) {
        super.onAttach(activity)
        mListener = activity as NoticeDialogListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val customView = activity.layoutInflater.inflate(R.layout.dialog_goods, null)
        val picker = customView.findViewById<View>(R.id.numberPicker) as NumberPicker
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(good?.name)
        builder.setView(customView)


        val collection = realm?.where(Measure::class.java)!!.findAll()

        val measures: MutableList<String> = ArrayList()

        for (measure in collection) {
            measures.add(measure.name)
        }

        picker.minValue = 0
        var max = collection.count() - 1
        if (max < 0)
            max = 0
        picker.maxValue = max
        picker.displayedValues = measures.toTypedArray()
        picker.wrapSelectorWheel = false


        builder.setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, id ->
            mListener.onDialogPositiveClick(good!!, 1.1f, collection[picker.value]!!)
        })
        builder.setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialog, id -> mListener.onDialogNegativeClick() })
        return builder.create()
    }
}