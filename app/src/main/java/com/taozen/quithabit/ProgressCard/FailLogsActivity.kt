package com.taozen.quithabit.ProgressCard

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.util.Log
import butterknife.BindView
import butterknife.ButterKnife
import com.taozen.quithabit.R
import kotlinx.android.synthetic.main.activity_fail_logs.*
import kotlinx.android.synthetic.main.contentfaillogs.*
import java.util.*


class FailLogsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fail_logs)
        ButterKnife.bind(this@FailLogsActivity)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        Objects.requireNonNull<ActionBar>(supportActionBar).setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        logtxtVwId.text
        getValueOfPercent()
    }

    //fetching value from MainActivity
    private fun getValueOfPercent() {
        try {
            val intent = intent
            if (intent != null) {
                val getTimeStampString = intent.getStringExtra("log").toString()
                Log.d("progress", "value of percent $getTimeStampString")
                logtxtVwId.setText("time of failure: " + getTimeStampString)
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
