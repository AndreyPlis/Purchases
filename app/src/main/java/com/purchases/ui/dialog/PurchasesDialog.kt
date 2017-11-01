package com.purchases.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.purchases.R


class PurchasesDialog : DialogFragment() {

    private lateinit var mListener: NoticeDialogListener

    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: PurchasesDialog)

        fun onDialogNegativeClick(dialog: PurchasesDialog)
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
        builder.setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, id -> mListener.onDialogPositiveClick(this@PurchasesDialog) })
        builder.setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialog, id -> mListener.onDialogNegativeClick(this@PurchasesDialog) })

        return builder.create()
    }

}