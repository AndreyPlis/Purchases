package com.purchases.view

/**
 * Created by User on 024 24.10.17.
 */

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ListPurchasesView : MvpView {
    fun showDialog()
    fun hideDialog()
}