package com.purchases.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.view.View
import butterknife.ButterKnife
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.arellomobile.mvp.presenter.ProvidePresenterTag
import com.purchases.R
import com.purchases.presenter.ListPurchasesPresenter
import com.purchases.view.ListPurchasesView
import android.widget.EditText
import butterknife.BindView



class ListPurchasesActivity : AppCompatActivity(), ListPurchasesView {


    @InjectPresenter(type = PresenterType.GLOBAL)
    lateinit var dialogPresenter: ListPurchasesPresenter

    var alertDialog: AlertDialog? = null


    @ProvidePresenterTag(presenterClass = ListPurchasesPresenter::class, type = PresenterType.GLOBAL)
    fun provideDialogPresenterTag(): String = "Hello"

    @ProvidePresenter(type = PresenterType.GLOBAL)
    fun provideDialogPresenter() = ListPurchasesPresenter()

    override fun hideDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_purchases)

        findViewById<View>(R.id.fab_purchases).setOnClickListener { dialogPresenter.onShowDialogClick() }

    }
}
