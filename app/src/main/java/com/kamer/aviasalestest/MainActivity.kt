package com.kamer.aviasalestest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val sydney = AirportPoint("SYD", -34.0, 151.0)
    private val moscow = AirportPoint("MOW", 55.752041, 37.617508)
    private val newYork = AirportPoint("NYC", 40.75603, -73.986956)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchView.setOnClickListener { startActivity(ProgressActivity.intent(this, moscow, sydney)) }
        searchView2.setOnClickListener { startActivity(ProgressActivity.intent(this, moscow, newYork)) }
    }
}
