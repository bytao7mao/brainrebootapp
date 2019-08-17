package com.taozen.quithabit.cardActivities;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.taozen.quithabit.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AchievmentsActivity extends AppCompatActivity {

    public static void main(String[] args) { }

    // Array of strings for ListView Title
    String[] listviewTitle = new String[] {
            "Bronze LvL I", "Bronze LvL II", "Bronze LvL III", "Bronze LvL IV",
            "Silver LvL I", "Silver LvL II", "Silver LvL III", "Silver LvL IV",
            "Gold LvL I", "Gold LvL II", "Gold LvL III", "Gold LvL IV", "Gold Nova Master"};

    int[] listviewImage = new int[] {
            R.mipmap.chevron7, R.mipmap.chevron8, R.mipmap.chevron9, R.mipmap.chevron11,
            R.mipmap.chevron16, R.mipmap.chevron17, R.mipmap.chevron18, R.mipmap.chevron10,
            R.mipmap.chevron3, R.mipmap.chevron4, R.mipmap.chevron5, R.mipmap.chevron6, R.mipmap.chevron12};

    String[] listviewShortDescription = new String[] {
            "Android ListView Short Bronze LvL I", "Android ListView Short Bronze LvL II", "Android ListView Short Bronze LvL III", "Android ListView Short Bronze LvL IV",
            "Android ListView Short Silver LvL I", "Android ListView Short Silver LvL II", "Android ListView Short Silver LvL III", "Android ListView Short Silver LvL IV",
            "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description",
    "Only for those who are worthy!"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievments);

        getWindow().setStatusBarColor(ContextCompat.getColor(AchievmentsActivity.this, R.color.white));
        //TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        List<HashMap<String, String>> aList = new ArrayList<>();

        for (int i = 0; i < listviewImage.length; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_title", listviewTitle[i]);
            hm.put("listview_discription", listviewShortDescription[i]);
            hm.put("listview_image", Integer.toString(listviewImage[i]));
            aList.add(hm);
        }

        String[] from = {"listview_image", "listview_title", "listview_discription"};
        int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_short_description};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.list_item, from, to);
        ListView androidListView = findViewById(R.id.list_view);
        androidListView.setAdapter(simpleAdapter);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
