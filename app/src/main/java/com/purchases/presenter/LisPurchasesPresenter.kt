package com.purchases.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.purchases.view.ListPurchasesView

/**
 * Created by User on 024 24.10.17.
 */

@InjectViewState
class ListPurchasesPresenter : MvpPresenter<ListPurchasesView>() {
    fun onShowDialogClick() {
        viewState.showDialog()
    }

    fun onHideDialog() {
        viewState.hideDialog()
    }
}