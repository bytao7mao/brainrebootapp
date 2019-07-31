package com.taozen.quithabit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.taozen.quithabit.Obj.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AchievmentsRanksActivity extends AppCompatActivity {

    // Array of strings for ListView Title
    String[] listviewTitle = new String[]{
            "ListView Title 1", "ListView Title 2", "ListView Title 3", "ListView Title 4",
            "ListView Title 5", "ListView Title 6", "ListView Title 7", "ListView Title 8",
            "ListView Title 8", "ListView Title 8", "ListView Title 8", "ListView Title 8",
    "last title chevron"};

    int[] listviewImage = new int[]{
            R.mipmap.chevron7, R.mipmap.chevron8, R.mipmap.chevron9, R.mipmap.chevron11,
            R.mipmap.chevron16, R.mipmap.chevron17, R.mipmap.chevron18, R.mipmap.chevron10,
            R.mipmap.chevron3, R.mipmap.chevron4, R.mipmap.chevron5, R.mipmap.chevron6,
    R.mipmap.chevron12};

    String[] listviewShortDescription = new String[]{
            "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description",
            "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description",
            "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description",
    "last chevron"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievments);

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
        ListView androidListView = (ListView) findViewById(R.id.list_view);
        androidListView.setAdapter(simpleAdapter);

    }
}
