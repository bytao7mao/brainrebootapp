package com.taozen.quithabit.cardClasses

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import butterknife.ButterKnife
import com.taozen.quithabit.R
import kotlinx.android.synthetic.main.contentfaillogs.*
import java.util.*


class FailLogsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fail_logs)
        ButterKnife.bind(this@FailLogsActivity)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        Objects.requireNonNull<ActionBar>(supportActionBar).setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        logtxtVwId.text
        getValueOfPercent()
        window.statusBarColor = ContextCompat.getColor(this@FailLogsActivity, R.color.white)
    }

    //fetching value from MainActivity
    private fun getValueOfPercent() {
        try {
            val intent = intent
            if (intent != null) {
                val getTimeStampString = intent.getStringExtra("log").toString()
                Log.d("progress", "value of percent $getTimeStampString")
                logtxtVwId.text = "time of failure: " + getTimeStampString
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }//fetching value from main activity[END]

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
