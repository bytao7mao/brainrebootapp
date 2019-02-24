package com.taozen.quithabit

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_progress.*
import java.util.*

class ProgressKotlinActivity : AppCompatActivity() {

    companion object {
        private val TAG: String =
                ProgressKotlinActivity::class.java.simpleName
    }

    internal var dataIsSet = false
    internal var valueOfpercent: String? = null
    internal var tip: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)
        ButterKnife.bind(this)
        Log.d("TAGG", "onCreate created ")
        checkIsDataSet()

        progressTextViewId.text
        apiText.text
        tvErrorId.text
        loadingProgressId.progress
        percentImageId

        getFirebaseData()

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    checkIsDataSet()
                }
            }
        }, 100, 1000)

        //get value from MainActivity of percent
        getValueOfPercent()
        //change the coresponding image for percent
        conditionForImagePercent()
    }

    override fun onResume() {
        super.onResume()
        checkIsDataSet()
    }

    private fun getFirebaseData() {
        AsyncTask.execute {
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("books").child("2")
            val valueEventListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (ds in dataSnapshot.children) {
                        val key = ds.getValue(String::class.java)
                        Log.d("TAGG", " retrieved some data " + key!!)
                    }
                    Log.d("TAGG", " retrieved some data " + dataSnapshot.getValue(String::class.java)!!)
                    tip = dataSnapshot.getValue(String::class.java)
                    apiText.setText(tip)
                    dataIsSet = true
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            }
            myRef.addValueEventListener(valueEventListener)
        }
    }

    private fun getValueOfPercent() {
        try {
            val intent = intent
            if (intent != null) {
                valueOfpercent = intent.getIntExtra("pro", 0).toString()
                Log.d("progress", "value of percent $valueOfpercent")
                progressTextViewId.setText(valueOfpercent + "%")
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

    }

    private fun conditionForImagePercent() {
        getValueOfPercent()
        if (Integer.parseInt(valueOfpercent) < 11) {
            percentImageId.setImageResource(R.drawable.ten)
        } else if (Integer.parseInt(valueOfpercent) < 21) {
            percentImageId.setImageResource(R.drawable.twenty)
        } else if (Integer.parseInt(valueOfpercent) < 36) {
            percentImageId.setImageResource(R.drawable.thirty)
        } else if (Integer.parseInt(valueOfpercent) < 51) {
            percentImageId.setImageResource(R.drawable.fifty)
        } else if (Integer.parseInt(valueOfpercent) < 66) {
            percentImageId.setImageResource(R.drawable.sixty)
        } else if (Integer.parseInt(valueOfpercent) < 76) {
            percentImageId.setImageResource(R.drawable.seventeen)
        } else if (Integer.parseInt(valueOfpercent) < 96) {
            percentImageId.setImageResource(R.drawable.eightfive)
        } else if (Integer.parseInt(valueOfpercent) == 100) {
            percentImageId.setImageResource(R.drawable.hundred)
        }
    }

    private fun checkIsDataSet(){
        if (!dataIsSet) {
            loadingProgressId.visibility = View.VISIBLE
            percentImageId.visibility = View.INVISIBLE
            tvErrorId.visibility = View.INVISIBLE
            apiText.visibility = View.INVISIBLE
        } else {
            tvErrorId.visibility = View.INVISIBLE
            apiText.visibility = View.VISIBLE
            percentImageId.visibility = View.VISIBLE
            loadingProgressId.visibility = View.INVISIBLE
        }
    }
}