package com.taozen.quithabit.cardActivities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.taozen.quithabit.MainActivity;
import com.taozen.quithabit.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AchievmentsActivity extends AppCompatActivity {

//    public static void main(String[] args) { }

    // Array of strings for ListView Title
    String[] listviewTitle = new String[] {
            "Recruit lvl I", "Recruit lvl II", "Recruit lvl III", "Recruit Master",
            "Silver lvl I", "Silver lvl II", "Silver lvl III", "Silver Nova Master",
            "Gold lvl I", "Gold lvl II", "Gold lvl III", "Gold lvl IV", "Gold Nova Master"};

    int[] listviewImage = new int[] {
            R.mipmap.chevron7, R.mipmap.chevron8, R.mipmap.chevron9, R.mipmap.chevron11,
            R.mipmap.chevron16, R.mipmap.chevron17, R.mipmap.chevron18, R.mipmap.chevron10,
            R.mipmap.chevron3, R.mipmap.chevron4, R.mipmap.chevron5, R.mipmap.chevron6, R.mipmap.chevron12};

    String[] listviewShortDescription = new String[] {
            "Android ListView Short Bronze LvL I", "Android ListView Short Bronze LvL II", "Android ListView Short Bronze LvL III", "Android ListView Short Bronze LvL IV",
            "Android ListView Short Silver LvL I", "Android ListView Short Silver LvL II", "Android ListView Short Silver LvL III", "Android ListView Short Silver LvL IV",
            "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description",
    "Only for those who are worthy!"};

    //shared pref
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    HashMap<String, String> hm;
    SimpleAdapter simpleAdapter;
    ListView androidListView;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievments);

        //shared pref
        preferences = PreferenceManager.getDefaultSharedPreferences(AchievmentsActivity.this);
        editor = preferences.edit();

        getWindow().setStatusBarColor(ContextCompat.getColor(AchievmentsActivity.this, R.color.white));
        //TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        List<HashMap<String, String>> aList = new ArrayList<>();

        for (int i = 0; i < listviewImage.length; i++) {
            hm = new HashMap<>();
            hm.put("listview_title", listviewTitle[i]);
            hm.put("listview_discription", listviewShortDescription[i]);
            hm.put("listview_image", Integer.toString(listviewImage[i]));
            aList.add(hm);
        }

        String[] from = {"listview_image", "listview_title", "listview_discription"};
        int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_short_description};

        simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.list_item, from, to);
        test();
        androidListView = findViewById(R.id.list_view);
        androidListView.setAdapter(simpleAdapter);

        if (preferences.contains("rank")){
            if (Objects.requireNonNull(preferences.getString("rank", "")).equalsIgnoreCase("Recruit")){

            }
        }
    }

    private void test(){
        SimpleAdapter.ViewBinder binder = new SimpleAdapter.ViewBinder() {
            @SuppressLint("ResourceType")
            @Override
            public boolean setViewValue(View view, Object object, String value) {
                Log.d("QUITHABIT10", "view= "+view);
                Log.d("QUITHABIT10", "view.toString()= "+ view.toString());
                Log.d("QUITHABIT10", "view.getId()= "+ view.getId());
                Log.d("QUITHABIT10", "view.getVisibility()= "+ view.getVisibility());
                Log.d("QUITHABIT10", "view.equals((TextView) view.findViewById(R.id. textView_5))= "+ view.equals((TextView) view.findViewById(R.id.listview_item_title)));

//                System.out.println("view= "+view);
//                System.out.println("view.toString()= "+ view.toString());
//                System.out.println("view.getId()= "+ view.getId());
//                System.out.println("view.getVisibility()= "+ view.getVisibility());
//                System.out.println("view.equals((TextView) view.findViewById(R.id. textView_5))= "+ view.equals((TextView) view.findViewById(R.id.listview_item_title)));
                if (view.equals(view.findViewById(R.id.listview_item_title)) && view.getId() == 2131230917 ) {
                    Log.d("QUITHABIT10", "2131230915 OKOKKOKOKOKOKOK");
                    TextView textView_five = view.findViewById(R.id.listview_item_title);
                    textView_five.setText("dsadsadsa");
                    //Change color/answer/etc for textView_5
                }

                //OR
                if (view instanceof TextView) {
                    //Do stuff
                    return true;
                }
                return false;
            }
        };
        simpleAdapter.setViewBinder(binder);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
