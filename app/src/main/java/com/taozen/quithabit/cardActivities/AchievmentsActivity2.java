package com.taozen.quithabit.cardActivities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.taozen.quithabit.R;
import com.taozen.quithabit.utils.CustomAdapterListView;

import java.util.Objects;

public class AchievmentsActivity2 extends AppCompatActivity {
    public String[] itemValueArray={"item 1","item 2","item 3","item 4","item 5","item 6","item 7","item 8","item 9","item 10","item 11","item 12","item 13","item 14","item 15","item 16","item 17","item 18","item 19","item 20"};
    public static boolean[] tickMarkVisibileListPosition=new boolean[20];
    //Allocating size for the Boolean set all element to Boolean.false by default. It is defined static, so that the same variable can be accessed from adapter class without creating object for MainActivity.Java.
    public ListView listviewMain;
    public int listPosition;

    //shared pref
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievments);
        //shared pref
        preferences = PreferenceManager.getDefaultSharedPreferences(AchievmentsActivity2.this);
        editor = preferences.edit();

        listviewMain= findViewById(R.id.list_view);

        listviewMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                listPosition = position - listviewMain.getFirstVisiblePosition();
//                if (listviewMain.getChildAt(listPosition).findViewById(R.id.listview_image).getVisibility() == View.INVISIBLE) {
//                    listviewMain.getChildAt(listPosition).findViewById(R.id.listview_image).setVisibility(View.VISIBLE);
//                    tickMarkVisibileListPosition[position]=Boolean.TRUE;
//                }
//                else {
//                    listviewMain.getChildAt(listPosition).findViewById(R.id.listview_image).setVisibility(View.INVISIBLE);
//                    tickMarkVisibileListPosition[position]=Boolean.FALSE;
//                }
//                listviewMain.getChildAt(listPosition).setSelected(true);

                listviewMain.getChildAt(0).setBackgroundColor(getResources().getColor(R.color.blueColorLed));
            }
        });
//        listviewMain.getChildAt(0).setBackgroundColor(getResources().getColor(R.color.greenColorLed));

        CustomAdapterListView myadapter=new CustomAdapterListView(this,itemValueArray);
        listviewMain.setAdapter(myadapter);
//        listviewMain.getChildAt(0).setBackgroundColor(getResources().getColor(R.color.blueColorLed));
        if (preferences.contains("rank")){
//            if (Objects.requireNonNull(preferences.getString("rank", "default")).equals("Recruit")){
//                listPosition=listview.getFirstVisiblePosition-position;
//                listviewMain.getchildAt(listposition).findViewById(R.id.item).setVisibility(View.Gone);
//            }
        }

    }
}
