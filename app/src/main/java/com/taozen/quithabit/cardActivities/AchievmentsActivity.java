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
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
    String[] titles = new String[] {
            "Recruit lvl I", "Recruit lvl II", "Recruit lvl III", "Recruit Master",
            "Silver lvl I", "Silver lvl II", "Silver lvl III", "Silver Nova Master",
            "Gold lvl I", "Gold lvl II", "Gold lvl III", "Gold lvl IV", "Gold Nova Master"};

    int[] images = new int[] {
            R.mipmap.chevron7, R.mipmap.chevron8, R.mipmap.chevron9, R.mipmap.chevron11,
            R.mipmap.chevron16, R.mipmap.chevron17, R.mipmap.chevron18, R.mipmap.chevron10,
            R.mipmap.chevron3, R.mipmap.chevron4, R.mipmap.chevron5, R.mipmap.chevron6, R.mipmap.chevron12};

    String[] descriptions = new String[] {
            "Android ListView Short Bronze LvL I", "Android ListView Short Bronze LvL II", "Android ListView Short Bronze LvL III", "Android ListView Short Bronze LvL IV",
            "Android ListView Short Silver LvL I", "Android ListView Short Silver LvL II", "Android ListView Short Silver LvL III", "Android ListView Short Silver LvL IV",
            "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description",
    "Only for those who are worthy!"};

    //shared pref
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    HashMap<String, String> hm;
//    SimpleAdapter simpleAdapter;
    ListView mListView;

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

//        List<HashMap<String, String>> aList = new ArrayList<>();
//
//        for (int i = 0; i < images.length; i++) {
//            hm = new HashMap<>();
//            hm.put("listview_title", titles[i]);
//            hm.put("listview_discription", descriptions[i]);
//            hm.put("listview_image", Integer.toString(images[i]));
//            aList.add(hm);
//        }

        mListView = findViewById(R.id.list_view);
        CustomAdapterListView customAdapterListView = new CustomAdapterListView();
        mListView.setAdapter(customAdapterListView);

//        if (preferences.contains("rank")){
//            if (Objects.requireNonNull(preferences.getString("rank", "")).equalsIgnoreCase("Recruit")){
//
//            }
//        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    class CustomAdapterListView extends BaseAdapter {

        public int getCount() {
            return images.length;
        }
        @Override
        public Object getItem(int position) {
            return position;
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            @SuppressLint("ViewHolder")
            View view = getLayoutInflater().inflate(R.layout.list_item, null);

            ImageView mImageView = view.findViewById(R.id.listview_image);
            TextView mTxtView = view.findViewById(R.id.listview_item_title);
            mImageView.setImageResource(images[position]);
            mTxtView.setText(titles[position]);

                    if (preferences.contains("rank")){
            if (Objects.requireNonNull(preferences.getString("rank", "")).equalsIgnoreCase("Recruit")){
                mImageView.setAlpha(0.2f);
                mTxtView.setAlpha(0.2f);
            }
        }

            return view;
        }
    }

}
