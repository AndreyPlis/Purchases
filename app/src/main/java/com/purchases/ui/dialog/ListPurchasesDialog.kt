package com.purchases.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.purchases.R


class ListPurchasesDialog : DialogFragment() {

    private lateinit var mListener: NoticeDialogListener

    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: ListPurchasesDialog)

        fun onDialogNegativeClick(dialog: ListPurchasesDialog)
    }


    override fun onAttach(activity: Context) {
        super.onAttach(activity)
        mListener = activity as NoticeDialogListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val customView = activity.layoutInflater.inflate(R.layout.dialog_purchases, null)
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Новый список продуктов")
        builder.setView(customView)
        builder.setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, id -> mListener.onDialogPositiveClick(this@ListPurchasesDialog) })
        builder.setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialog, id -> mListener.onDialogNegativeClick(this@ListPurchasesDialog) })

        return builder.create()
    }

}