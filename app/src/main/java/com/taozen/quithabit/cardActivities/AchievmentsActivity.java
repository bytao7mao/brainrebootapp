package com.taozen.quithabit.cardActivities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.media.Image;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
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

    public static void main(String[] args) { }

    // Array of strings for ListView Title
    String[] titles;
    int[] images;

//    String[] descriptions = new String[] {
//            "Android ListView Short Bronze LvL I", "Android ListView Short Bronze LvL II", "Android ListView Short Bronze LvL III", "Android ListView Short Bronze LvL IV",
//            "Android ListView Short Silver LvL I", "Android ListView Short Silver LvL II", "Android ListView Short Silver LvL III", "Android ListView Short Silver LvL IV",
//            "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description", "Only for those who are worthy!"};

    //shared pref
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    ListView mListView;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievments);

        titles = getResources().getStringArray(R.array.titles_arr_for_ranks);
        TypedArray tArray = getResources().obtainTypedArray(
                R.array.images_arr_for_ranks);

        int count = tArray.length();
        images = new int[count];
        for (int i = 0; i < images.length; i++) {
            images[i] = tArray.getResourceId(i, 0);
        }
        //Recycles the TypedArray, to be re-used by a later caller.
        //After calling this function you must not ever touch the typed array again.
        tArray.recycle();

        //shared pref
        preferences = PreferenceManager.getDefaultSharedPreferences(AchievmentsActivity.this);
        editor = preferences.edit();

        getWindow().setStatusBarColor(ContextCompat.getColor(AchievmentsActivity.this, R.color.white));
        //TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mListView = findViewById(R.id.list_view);
        CustomAdapterListView customAdapterListView = new CustomAdapterListView(this, titles, images);
        mListView.setAdapter(customAdapterListView);

        if (preferences.contains("rank")){
            if (Objects.requireNonNull(preferences.getString("rank", "")).equalsIgnoreCase("Recruit")) {

            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    class CustomAdapterListView extends BaseAdapter {
        String [] result;
        Context context;
        int [] imageId;
        private LayoutInflater inflater=null;
        public CustomAdapterListView(AchievmentsActivity mainActivity, String[] prgmNameList, int[] prgmImages) {
            // TODO Auto-generated constructor stub
            result=prgmNameList;
            context=mainActivity;
            imageId=prgmImages;
            inflater = ( LayoutInflater )context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

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
            Holder holder=new Holder();
            View view;
            view = inflater.inflate(R.layout.list_item, null);
            holder.img = view.findViewById(R.id.listview_image);
            holder.tv = view.findViewById(R.id.listview_item_title);
            holder.img.setImageResource(images[position]);
            holder.tv.setText(titles[position]);

            if (preferences.contains("rank")){
                if (Objects.requireNonNull(preferences.getString("rank", "")).equalsIgnoreCase("recruit")) {
                    if (position > 0){
                        holder.img.setAlpha(0.2f);
                    }
                } else if(Objects.requireNonNull(preferences.getString("rank", "")).equalsIgnoreCase("recruit II")) {
                    if (position > 1){
                        holder.img.setAlpha(0.2f);
                    }
                }else if(Objects.requireNonNull(preferences.getString("rank", "")).equalsIgnoreCase("recruit III")) {
                    if (position > 3){
                        holder.img.setAlpha(0.2f);
                    }
                }else if(Objects.requireNonNull(preferences.getString("rank", "")).equalsIgnoreCase("silver")) {
                    if (position > 4){
                        holder.img.setAlpha(0.2f);
                    }
                }else if(Objects.requireNonNull(preferences.getString("rank", "")).equalsIgnoreCase("silver II")) {
                    if (position > 5){
                        holder.img.setAlpha(0.2f);
                    }
                }else if(Objects.requireNonNull(preferences.getString("rank", "")).equalsIgnoreCase("silver III")) {
                    if (position > 7){
                        holder.img.setAlpha(0.2f);
                    }
                }else if(Objects.requireNonNull(preferences.getString("rank", "")).equalsIgnoreCase("Gold")) {
                    if (position > 8){
                        holder.img.setAlpha(0.2f);
                    }
                }else if(Objects.requireNonNull(preferences.getString("rank", "")).equalsIgnoreCase("Gold I")) {
                    if (position > 9){
                        holder.img.setAlpha(0.2f);
                    }
                }else if(Objects.requireNonNull(preferences.getString("rank", "")).equalsIgnoreCase("Gold II")) {
                    if (position > 10){
                        holder.img.setAlpha(0.2f);
                    }
                } else if(Objects.requireNonNull(preferences.getString("rank", "")).equalsIgnoreCase("Gold III")) {
                    if (position > 11){
                        holder.img.setAlpha(0.2f);
                    }
                }
            }
            return view;
        }
    }
    public class Holder {
        TextView tv;
        ImageView img;
    }

}
