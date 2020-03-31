package com.example.lab7

import android.os.Bundle
import android.support.v7.app.AppCompatActivity



class MainActivity : AppCompatActivity() {


    val manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ShowFragmentOne()
    }

    public fun ShowFragmentOne(){
        val transaction = manager.beginTransaction()
        val fragment = FragmentOne()
        transaction.replace(R.id.fragment_holder,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    public fun ShowFragmentTwo(){
        val transaction = manager.beginTransaction()
        val fragment = FragmentTwo()
        transaction.replace(R.id.fragment_holder,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
