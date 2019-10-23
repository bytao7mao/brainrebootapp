package com.taozen.quithabit.cardActivities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import androidx.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.taozen.quithabit.R;

import java.util.Objects;

import static com.taozen.quithabit.MainActivity.FIVE;
import static com.taozen.quithabit.MainActivity.ONE;
import static com.taozen.quithabit.MainActivity.RANK;
import static com.taozen.quithabit.MainActivity.TEN;
import static com.taozen.quithabit.MainActivity.ZERO;

public class AchievmentsActivity extends AppCompatActivity {

    // Array of strings for ListView Title
    String[] titles;
    int[] images;

    String[] descriptionsArray = new String[] {
            "You can do it!", "Your own way!", "Control yourself!", "The right way!", "The zen way!",
            "Zen apprentice", "Zen monk!", "Keep up the way!", "Almost there!",
            "There is no going back!", "A new life!"};

    String[] daysArray = new String[] {
            "1 Day!", "1 Week!", "1 Month!", "2 Months!",
            "3 Months!", "4 Months!", "5 Months!", "6 Months!",
            "7 Months!", "9 Months!", "300 Days!"};

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

        mListView = findViewById(R.id.list_view);
        CustomAdapterListView customAdapterListView =
                new CustomAdapterListView(this, titles, daysArray, images, descriptionsArray);
        mListView.setAdapter(customAdapterListView);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Achievements");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    class CustomAdapterListView extends BaseAdapter {
        String [] titlesArr, descriptionsArr, daysArr;
        Context context;
        int [] imageId;
        private LayoutInflater inflater=null;
        CustomAdapterListView(AchievmentsActivity achievmentsActivity,
                              String[] prgmNameList,String[] days,
                              int[] prgmImages, String[] descriptions) {
            // TODO Auto-generated constructor stub
            descriptionsArr =descriptions;
            titlesArr =prgmNameList;
            context=achievmentsActivity;
            daysArr =days;
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
            ViewHolder holder=null;
            View view = convertView;

                if (view == null) {
                    holder = new ViewHolder();
                    view = View.inflate(parent.getContext(), R.layout.list_item_achievments, null);
                    holder.img = view.findViewById(R.id.listview_image);
                    holder.tv = view.findViewById(R.id.listview_item_title);
                    holder.desc = view.findViewById(R.id.listview_item_short_description);
                    holder.days = view.findViewById(R.id.tv_progress);
                    holder.img.setImageResource(images[position]);
                    holder.tv.setText(titlesArr[position]);
                    holder.desc.setText(descriptionsArr[position]);
                    holder.days.setText(daysArr[position]);

                    view.setTag(holder);

                if (preferences.contains(RANK)){
                    if (Objects.requireNonNull(preferences.getString(RANK, "")).equalsIgnoreCase("wolf")) {
                        if (position > ZERO){
                            holder.img.setAlpha(0.2f);
                            holder.tv.setAlpha(0.2f);
                            holder.desc.setAlpha(0.2f);
                            holder.days.setAlpha(0.2f);
                        }
                    } else if(Objects.requireNonNull(preferences.getString(RANK, "")).equalsIgnoreCase("squirrel")) {
                        if (position > ONE){
                            holder.img.setAlpha(0.2f);
                            holder.tv.setAlpha(0.2f);
                            holder.desc.setAlpha(0.2f);
                            holder.days.setAlpha(0.2f);
                        }
                    } else if(Objects.requireNonNull(preferences.getString(RANK, "")).equalsIgnoreCase("badger")) {
                        if (position > ONE+1){
                            holder.img.setAlpha(0.2f);
                            holder.tv.setAlpha(0.2f);
                            holder.desc.setAlpha(0.2f);
                            holder.days.setAlpha(0.2f);
                        }
                    }else if(Objects.requireNonNull(preferences.getString(RANK, "")).equalsIgnoreCase("bear")) {
                        if (position > ONE+2+1){
                            holder.img.setAlpha(0.2f);
                            holder.tv.setAlpha(0.2f);
                            holder.desc.setAlpha(0.2f);
                            holder.days.setAlpha(0.2f);
                        }
                    }else if(Objects.requireNonNull(preferences.getString(RANK, "")).equalsIgnoreCase("wild bear")) {
                        if (position > ONE+3+1){
                            holder.img.setAlpha(0.2f);
                            holder.tv.setAlpha(0.2f);
                            holder.desc.setAlpha(0.2f);
                            holder.days.setAlpha(0.2f);
                        }
                    }else if(Objects.requireNonNull(preferences.getString(RANK, "")).equalsIgnoreCase("deer")) {
                        if (position > FIVE+1){
                            holder.img.setAlpha(0.2f);
                            holder.tv.setAlpha(0.2f);
                            holder.desc.setAlpha(0.2f);
                            holder.days.setAlpha(0.2f);
                        }
                    }else if(Objects.requireNonNull(preferences.getString(RANK, "")).equalsIgnoreCase("boar")) {
                        if (position > FIVE+2+1){
                            holder.img.setAlpha(0.2f);
                            holder.tv.setAlpha(0.2f);
                            holder.desc.setAlpha(0.2f);
                            holder.days.setAlpha(0.2f);
                        }
                    }else if(Objects.requireNonNull(preferences.getString(RANK, "")).equalsIgnoreCase("elk")) {
                        if (position > FIVE+3+1){
                            holder.img.setAlpha(0.2f);
                            holder.tv.setAlpha(0.2f);
                            holder.desc.setAlpha(0.2f);
                            holder.days.setAlpha(0.2f);
                        }
                    }else if(Objects.requireNonNull(preferences.getString(RANK, "")).equalsIgnoreCase("beaver")) {
                        if (position > TEN){
                            holder.img.setAlpha(0.2f);
                            holder.tv.setAlpha(0.2f);
                            holder.desc.setAlpha(0.2f);
                            holder.days.setAlpha(0.2f);
                        }
                    }else if(Objects.requireNonNull(preferences.getString(RANK, "")).equalsIgnoreCase("fox")) {
                        if (position > TEN+1){
                            holder.img.setAlpha(0.2f);
                            holder.tv.setAlpha(0.2f);
                            holder.desc.setAlpha(0.2f);
                            holder.days.setAlpha(0.2f);
                        }
                    } else if(Objects.requireNonNull(preferences.getString(RANK, "")).equalsIgnoreCase("lynx")) {
                        if (position > TEN+1+1){
                            holder.img.setAlpha(0.2f);
                            holder.tv.setAlpha(0.2f);
                            holder.desc.setAlpha(0.2f);
                            holder.days.setAlpha(0.2f);
                        }
                    }
                }
            } else {
                    holder = (ViewHolder) view.getTag();
                }
            return view;
        }
    }
    public class ViewHolder {
        TextView tv, desc, days;
        ImageView img;
    }

}
