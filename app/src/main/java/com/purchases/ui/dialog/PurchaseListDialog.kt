package com.purchases.ui.dialog

import android.app.*
import android.content.*
import android.os.*
import android.support.v4.app.DialogFragment
import com.purchases.*


class PurchaseListDialog : DialogFragment() {

    private lateinit var mListener: NoticeDialogListener

    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: PurchaseListDialog)

        fun onDialogNegativeClick(dialog: PurchaseListDialog)
    }


    override fun onAttach(activity: Context) {
        super.onAttach(activity)
        mListener = activity as NoticeDialogListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val customView = activity!!.layoutInflater.inflate(R.layout.dialog_purchases, null)
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Новый список продуктов")
        builder.setView(customView)
        builder.setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, id -> mListener.onDialogPositiveClick(this@PurchaseListDialog) })
        builder.setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialog, id -> mListener.onDialogNegativeClick(this@PurchaseListDialog) })

        return builder.create()
    }

}