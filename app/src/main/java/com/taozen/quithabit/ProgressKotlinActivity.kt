package com.taozen.quithabit

import android.content.Context
import android.content.SharedPreferences
import android.net.wifi.WifiManager
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import butterknife.ButterKnife
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_progress.*
import java.util.*
import kotlin.reflect.KClass
import android.net.NetworkInfo
import android.net.ConnectivityManager
import android.preference.PreferenceManager


class ProgressKotlinActivity : AppCompatActivity() {

    companion object {
        private val TAG: String =
                ProgressKotlinActivity::class.java.simpleName
    }

    internal var dataIsSet = false
    internal var valueOfpercent: String? = null
    internal var tip: String? = ""

    //shared pref
    internal lateinit var preferences: SharedPreferences
    internal lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)
        ButterKnife.bind(this)
        Log.d("TAGG", "onCreate created ")


        //shared pref
        preferences = PreferenceManager.getDefaultSharedPreferences(this@ProgressKotlinActivity)
        editor = preferences.edit()
//        checkIsDataSet()

        progressTextViewId.text
        apiText.text
        tvErrorId
        loadingProgressId.progress

        percentImgId

        MyAsyncTask().execute()

        //reference of java class MainActivity class in kotlin
        val cls: Class<MainActivity> = MainActivity::class.java
        //reference of kotlin class
        val cls2: KClass<DialogKotlin> = DialogKotlin::class


//        Timer().scheduleAtFixedRate(object : TimerTask() {
//            override fun run() {
//                runOnUiThread {
//                    checkConn()
//                }
//            }
//        }, 100, 1000)

        //get value from MainActivity of percent
        getValueOfPercent()
        //change the coresponding image for percent
//        conditionForImagePercent()
        condForImage()
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null
    }

    override fun onResume() {
        super.onResume()
//        checkConn()
//        checkIsDataSet()
    }

    private fun getValueOfPercent() {
        try {
            val intent = intent
            if (intent != null) {
                valueOfpercent = intent.getIntExtra("pro", 0).toString()
                Log.d("progress", "value of percent $valueOfpercent")
                progressTextViewId.text = valueOfpercent + "%"
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

    }


    //old method
//    private fun conditionForImagePercent() {
//        getValueOfPercent()
//        if (Integer.parseInt(valueOfpercent) < 11) {
//            percentImageId.setImageResource(R.drawable.ten)
//        } else if (Integer.parseInt(valueOfpercent) < 21) {
//            percentImageId.setImageResource(R.drawable.twenty)
//        } else if (Integer.parseInt(valueOfpercent) < 36) {
//            percentImageId.setImageResource(R.drawable.thirty)
//        } else if (Integer.parseInt(valueOfpercent) < 51) {
//            percentImageId.setImageResource(R.drawable.fifty)
//        } else if (Integer.parseInt(valueOfpercent) < 66) {
//            percentImageId.setImageResource(R.drawable.sixty)
//        } else if (Integer.parseInt(valueOfpercent) < 76) {
//            percentImageId.setImageResource(R.drawable.seventeen)
//        } else if (Integer.parseInt(valueOfpercent) < 96) {
//            percentImageId.setImageResource(R.drawable.eightfive)
//        } else if (Integer.parseInt(valueOfpercent) == 100) {
//            percentImageId.setImageResource(R.drawable.hundred)
//        }
//    }

    private fun condForImage(){
        var level = 0
        getValueOfPercent()
        if (Integer.parseInt(valueOfpercent) < 11) {
            level += 10
        } else if (Integer.parseInt(valueOfpercent) < 21) {
            level += 20
        } else if (Integer.parseInt(valueOfpercent) < 36) {
            level += 30
        } else if (Integer.parseInt(valueOfpercent) < 51) {
            level += 50
        } else if (Integer.parseInt(valueOfpercent) < 66) {
            level += 60
        } else if (Integer.parseInt(valueOfpercent) < 76) {
            level += 70
        } else if (Integer.parseInt(valueOfpercent) < 96) {
            level += 90
        } else if (Integer.parseInt(valueOfpercent) == 100) {
            level += 100
        }
        percentImgId.setImageLevel(level)
    }

//    private fun checkIsDataSet(){
//        if (!dataIsSet) {
//            loadingProgressId.visibility = View.VISIBLE
//            percentImgId.visibility = View.INVISIBLE
//            tvErrorId.visibility = View.INVISIBLE
//            apiText.visibility = View.INVISIBLE
//        } else {
//            tvErrorId.visibility = View.INVISIBLE
//            apiText.visibility = View.VISIBLE
//            percentImgId.visibility = View.VISIBLE
//            loadingProgressId.visibility = View.INVISIBLE
//        }
//        checkWifi()
//
//    }

    internal inner class MyFailureListener : OnFailureListener {
        override fun onFailure(exception: Exception) {
            val errorMessage = exception.message
            Log.d("TAGG", "error could not retrieved" + errorMessage)
            // test the errorCode and errorMessage, and handle accordingly
        }
    }
//    private fun checkWifi(){
//        val wifi = applicationContext
//                .getSystemService(Context.WIFI_SERVICE) as WifiManager
//        if (wifi.isWifiEnabled || isNetworkAvailable()) {
//            //wifi is enabled
//            Log.d("TAGG", "data retrieved")
//        } else {
//            Log.d("TAGG", "data not retrieved")
//            loadingProgressId.visibility = View.INVISIBLE
//            dataIsSet = true
//            percentImgId.visibility = View.INVISIBLE
//            tvErrorId.visibility = View.INVISIBLE
//            apiText.visibility = View.VISIBLE
//        }
//    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    private fun getFirebaseValues(){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("books").child("2")
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val key = ds.getValue(String::class.java)
                    Log.d("TAGG", " retrieved some data " + key!!)
                }
                Log.d("TAGG", " retrieved some data " + dataSnapshot.getValue(String::class.java)!!)

//                if(!isNetworkAvailable()) {
//                    tip = "no connection"
//                    apiText.text = tip
//                }else{
//                    loadingProgressId.visibility = View.INVISIBLE
//                    tip = dataSnapshot.getValue(String::class.java)
//                    apiText.text = tip
//                    editor.putString("tip", tip)
//                    editor.apply()
//                }
                tip = dataSnapshot.getValue(String::class.java)
                Log.d("Firebasetao", " tip = $tip")
                editor.putString("tip", tip)
                editor.apply()

            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        myRef.addValueEventListener(valueEventListener)
    }

    private fun checkConn(){
        if (isNetworkAvailable()){
            tvErrorId.visibility = View.INVISIBLE
        } else {
            tip = "no connection"
            apiText.text = tip
            editor.putString("tip", tip)
            editor.apply()
            percentImgId.visibility = View.INVISIBLE
            tvErrorId.visibility = View.VISIBLE
        }
    }

    internal inner class MyAsyncTask : AsyncTask<Void, Void, Void>() {
        override fun onPreExecute() {
            apiText.text = "Starting to fetch data from firebase ..."
            loadingProgressId.setVisibility(View.VISIBLE)
            percentImgId.setVisibility(View.INVISIBLE)
            tvErrorId.setVisibility(View.INVISIBLE)
        }

        override fun doInBackground(vararg voids: Void): Void? {
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            getFirebaseValues()
            return null
        }

        override fun onPostExecute(result: Void?) {
            loadingProgressId.setVisibility(View.INVISIBLE)
//            loadingProgressId.visibility = View.INVISIBLE
//            if (isNetworkAvailable()) {
//                percentImgId.visibility = View.VISIBLE
//            } else {
//                percentImgId.visibility = View.INVISIBLE

            if (isNetworkConnected()) {
                tip = preferences.getString("tip", tip)
                Log.d("Firebasetao", "connected")
                apiText.text = tip
                percentImgId.setVisibility(View.VISIBLE)
                tvErrorId.setVisibility(View.INVISIBLE)
            } else {
                tip = "not connected ... sorry :("
                Log.d("Firebasetao", "not connected")
                apiText.text = tip
                percentImgId.setVisibility(View.INVISIBLE)
                tvErrorId.setVisibility(View.VISIBLE)
            }

        }
    }
}




















//    private fun getFirebaseData() {
//        AsyncTask.execute {
//            val database = FirebaseDatabase.getInstance()
//            val myRef = database.getReference("books").child("2")
////            myRef.onDisconnect().setValue("I disconnected")
//            val valueEventListener = object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    for (ds in dataSnapshot.children) {
//                        val key = ds.getValue(String::class.java)
//                        Log.d("TAGG", " retrieved some data " + key!!)
//                    }
//                    Log.d("TAGG", " retrieved some data " + dataSnapshot.getValue(String::class.java)!!)
//                    tip = dataSnapshot.getValue(String::class.java)
//                    apiText.text = tip
//                    dataIsSet = true
//                }
//                override fun onCancelled(databaseError: DatabaseError) {
//                    println("loadPost:onCancelled ${databaseError.toException()}")
//                    databaseError.details
//                    Log.d("firebasetao", "error: " + databaseError.toException())
//                }
//
//            }
//            myRef.addValueEventListener(valueEventListener)
//        }
//    }