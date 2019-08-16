package com.taozen.quithabit.cardActivities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import butterknife.ButterKnife
import com.taozen.quithabit.R
import kotlinx.android.synthetic.main.contentfaillogs.*
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.ArrayList


class FailLogsActivity : AppCompatActivity() {

//    //shared pref
//    private var preferences: SharedPreferences? = null
//    private var editor: SharedPreferences.Editor? = null


    val b = StringBuilder()

    //shared pref
    val sharedpref: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this@FailLogsActivity)
    var editor = sharedpref.edit()

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fail_logs)
        ButterKnife.bind(this@FailLogsActivity)

        //shared pref
        val sharedpref: SharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this@FailLogsActivity)
        var editor = sharedpref.edit()


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        Objects.requireNonNull<ActionBar>(supportActionBar).setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        Log.d("SHOW", " " + b.toString() + " prefs: " + sharedpref.getString("oka", "null"))
//        logtxtVwId.text
        getArrayListFromMain(editor, sharedpref)
        logtxtVwId.append(sharedpref.getString("oka", "null"))

        window.statusBarColor = ContextCompat.getColor(this@FailLogsActivity, R.color.white)
    }

    //fetching value from MainActivity
    private fun getArrayListFromMain(editor: SharedPreferences.Editor, sharedpref: SharedPreferences) {

            val intent: Intent = intent
        val list: List<String>
        list = intent.getStringArrayListExtra("arr")

            for (item in list){
                Log.d("TAOZ10", " item: " + item)
                b.append(item + "\n")

//             logtxtVwId.text = (b.toString()) //= item.toString()
        }
        editor.putString("oka", b.toString())
        editor.apply()
//        if (sharedpref.contains("oka")){
//            b.append(sharedpref.getString("oka", ""))
//        }


    }//fetching value from main activity[END]

    override fun onDestroy() {
        super.onDestroy()
        editor.putString("oka", b.toString())
        editor.apply()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
