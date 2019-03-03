package com.taozen.quithabit.ProgressCard

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import butterknife.BindView
import butterknife.ButterKnife
import com.taozen.quithabit.R
import kotlinx.android.synthetic.main.activity_fail_logs.*


class FailLogsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fail_logs)
        ButterKnife.bind(this@FailLogsActivity)

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
}
