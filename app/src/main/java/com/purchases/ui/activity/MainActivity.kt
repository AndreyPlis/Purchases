package com.purchases.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.purchases.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onListPurchases(view: View)
    {
        val intent = Intent(this, PurchasesActivity::class.java)
        startActivity(intent)
    }

    fun onPurchases(view: View)
    {

    }

    fun onFavorites(view: View)
    {

    }
}
