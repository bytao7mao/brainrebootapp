package com.taozen.quithabit.cardActivities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;

import androidx.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.taozen.quithabit.BuildConfig;
import com.taozen.quithabit.R;

import java.util.Objects;

import static com.taozen.quithabit.MainActivity.DAYZEN;
import static com.taozen.quithabit.MainActivity.RANK;
import static com.taozen.quithabit.MainActivity.TEN;

public class AchievmentsActivity extends AppCompatActivity {

    // Array of strings for ListView Title
    String[] titles;
    int[] images;

//    final String[] descriptionsArray = new String[] {
//            "A beautiful journey!",
//            "You can do it!", "Your own way!", "Control yourself!", "The right way!",
//            "The zen way!",
//            "Zen apprentice", "Zen monk!", "Keep up the way!", "Almost there!",
//            "There is no going back!", "A new life!"};

    //shared pref
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    ImageView imageView;

    ListView mListView;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievments);
        titles = getResources().getStringArray(R.array.titles_arr_for_motivation);
        TypedArray tArray = getResources().obtainTypedArray(
                R.array.images_arr_for_ranks);

        final String[] daysArray = new String[] {
                getResources().getString(R.string.quit_date_),
                getResources().getString(R.string.one_day_), getResources().getString(R.string.one_week_),
                getResources().getString(R.string.one_month_), getResources().getString(R.string.two_months_),
                getResources().getString(R.string.three_months_), getResources().getString(R.string.four_months_),
                getResources().getString(R.string.five_months_), getResources().getString(R.string.six_months_),
                getResources().getString(R.string.seven_months_), getResources().getString(R.string.nine_months_),
                getResources().getString(R.string.three_hundred_days_), getResources().getString(R.string.one_year_),
                getResources().getString(R.string.four_hundred_days_), getResources().getString(R.string.five_hundred_days_),
                getResources().getString(R.string.six_hundred_days_), getResources().getString(R.string.seven_hundred_days_),
                getResources().getString(R.string.eight_hundred_days_), getResources().getString(R.string.nine_hundred_days_),
                getResources().getString(R.string.one_thousand__days_)};

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

        imageView = findViewById(R.id.activity_achievements_last_achievement_img);

        mListView = findViewById(R.id.list_view);
        CustomAdapterListView customAdapterListView =
                new CustomAdapterListView(this, titles, daysArray, images);
        mListView.setAdapter(customAdapterListView);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.achievements));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_orange_32dp);
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
        final String [] titlesArr;
        final String [] daysArr;
        final Context context;
        final int [] imageId;
        private LayoutInflater inflater=null;
        CustomAdapterListView(AchievmentsActivity achievmentsActivity,
                              String[] prgmNameList,String[] days,
                              int[] prgmImages) {
            // TODO Auto-generated constructor stub
            titlesArr =prgmNameList;
            context=achievmentsActivity;
            daysArr =days;
            imageId=prgmImages;
            inflater = (LayoutInflater)context.
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
            ViewHolder holder=new ViewHolder();
            View view;
            view = inflater.inflate(R.layout.list_item_achievments, null);
                if (view == null) {
                    view = View.inflate(parent.getContext(), R.layout.list_item_achievments, null);
                }
                holder.img = view.findViewById(R.id.achievements_lv_img);
                holder.tv = view.findViewById(R.id.achievements_lv_title_tv);
                holder.days = view.findViewById(R.id.achievements_lv_subtitle_tv);
                holder.img.setImageResource(images[position]);
                holder.tv.setText(titlesArr[position]);
                holder.days.setText(daysArr[position]);

                if (position == 0) {
                    holder.tv.setText(preferences.getString("qday", "none"));
                }

                if (BuildConfig.DEBUG){
                    Log.d(DAYZEN, "rank from ach: " + preferences.getString(RANK, ""));
                }
                if (preferences.contains(RANK)){
                    if (Objects.requireNonNull(preferences.getString(RANK, "")).equalsIgnoreCase("wolf")) {
                        if (position > 1){
                            holder.img.setAlpha(0.2f);
                            holder.tv.setAlpha(0.2f);
                            holder.days.setAlpha(0.2f);
                            imageView.setBackgroundResource(R.drawable.ic_oneday);
                        }
                    } else if(Objects.requireNonNull(preferences.getString(RANK, "")).equalsIgnoreCase("squirrel")) {
                        if (position > 2){
                            holder.img.setAlpha(0.2f);
                            holder.tv.setAlpha(0.2f);
                            holder.days.setAlpha(0.2f);
                            imageView.setBackgroundResource(R.drawable.ic_oneweek);
                        }
                    } else if(Objects.requireNonNull(preferences.getString(RANK, "")).equalsIgnoreCase("badger")) {
                        if (position > 3){
                            holder.img.setAlpha(0.2f);
                            holder.tv.setAlpha(0.2f);
                            holder.days.setAlpha(0.2f);
                            imageView.setBackgroundResource(R.drawable.ic_onemonth);
                        }
                    }else if(Objects.requireNonNull(preferences.getString(RANK, "")).equalsIgnoreCase("bear")) {
                        if (position > 4){
                            holder.img.setAlpha(0.2f);
                            holder.tv.setAlpha(0.2f);
                            holder.days.setAlpha(0.2f);
                            imageView.setBackgroundResource(R.drawable.ic_twomonths);
                        }
                    }else if(Objects.requireNonNull(preferences.getString(RANK, "")).equalsIgnoreCase("wild bear")) {
                        if (position > 5){
                            holder.img.setAlpha(0.2f);
                            holder.tv.setAlpha(0.2f);
                            holder.desc.setAlpha(0.2f);
                            holder.days.setAlpha(0.2f);
                            imageView.setBackgroundResource(R.drawable.ic_threemonths);
                        }
                    }else if(Objects.requireNonNull(preferences.getString(RANK, "")).equalsIgnoreCase("deer")) {
                        if (position > 6){
                            holder.img.setAlpha(0.2f);
                            holder.tv.setAlpha(0.2f);
                            holder.days.setAlpha(0.2f);
                            imageView.setBackgroundResource(R.drawable.ic_fourmonths);
                        }
                    }else if(Objects.requireNonNull(preferences.getString(RANK, "")).equalsIgnoreCase("boar")) {
                        if (position > 7){
                            holder.img.setAlpha(0.2f);
                            holder.tv.setAlpha(0.2f);
                            holder.days.setAlpha(0.2f);
                            imageView.setBackgroundResource(R.drawable.ic_fivemonths);
                        }
                    }else if(Objects.requireNonNull(preferences.getString(RANK, "")).equalsIgnoreCase("elk")) {
                        if (position > 8){
                            holder.img.setAlpha(0.2f);
                            holder.tv.setAlpha(0.2f);
                            holder.days.setAlpha(0.2f);
                            imageView.setBackgroundResource(R.drawable.ic_sixmonths);
                        }
                    }else if(Objects.requireNonNull(preferences.getString(RANK, "")).equalsIgnoreCase("beaver")) {
                        if (position > 9) {
                            holder.img.setAlpha(0.2f);
                            holder.tv.setAlpha(0.2f);
                            holder.days.setAlpha(0.2f);
                            imageView.setBackgroundResource(R.drawable.ic_sevenmonths);
                        }
                    } else if(Objects.requireNonNull(preferences.getString(RANK, "")).equalsIgnoreCase("fox")) {
                        if (position > TEN){
                            holder.img.setAlpha(0.2f);
                            holder.tv.setAlpha(0.2f);
                            holder.days.setAlpha(0.2f);
                            imageView.setBackgroundResource(R.drawable.ic_ninemonths);
                        }
                    } else if(Objects.requireNonNull(preferences.getString(RANK, "")).equalsIgnoreCase("lynx")) {
                        if (position > TEN+1) {
                            holder.img.setAlpha(0.2f);
                            holder.tv.setAlpha(0.2f);
                            holder.days.setAlpha(0.2f);
                            imageView.setBackgroundResource(R.drawable.ic_threehundreddays);
                        }
                    } else if(Objects.requireNonNull(preferences.getString(RANK, "")).equalsIgnoreCase("eagle")) {
                        if (position > TEN +2) {
                            holder.img.setAlpha(0.2f);
                            holder.tv.setAlpha(0.2f);
                            holder.days.setAlpha(0.2f);
                            imageView.setBackgroundResource(R.drawable.ic_oneyear);
                        }
                    } else if(Objects.requireNonNull(preferences.getString(RANK, "")).equalsIgnoreCase("elephant")) {
                        if (position > TEN +3) {
                            holder.img.setAlpha(0.2f);
                            holder.tv.setAlpha(0.2f);
                            holder.days.setAlpha(0.2f);
                            imageView.setBackgroundResource(R.drawable.ic_fourhundreddays);
                        }
                    } else if(Objects.requireNonNull(preferences.getString(RANK, "")).equalsIgnoreCase("leopard")) {
                    if (position > TEN +4) {
                        holder.img.setAlpha(0.2f);
                        holder.tv.setAlpha(0.2f);
                        holder.days.setAlpha(0.2f);
                        imageView.setBackgroundResource(R.drawable.ic_fivehundreddays);
                        }
                    } else if(Objects.requireNonNull(preferences.getString(RANK, "")).equalsIgnoreCase("buffalo")) {
                        if (position > TEN +5) {
                            holder.img.setAlpha(0.2f);
                            holder.tv.setAlpha(0.2f);
                            holder.days.setAlpha(0.2f);
                            imageView.setBackgroundResource(R.drawable.ic_sixhundreddays);
                        }
                    } else if(Objects.requireNonNull(preferences.getString(RANK, "")).equalsIgnoreCase("shark")) {
                        if (position > TEN +6) {
                            holder.img.setAlpha(0.2f);
                            holder.tv.setAlpha(0.2f);
                            holder.days.setAlpha(0.2f);
                            imageView.setBackgroundResource(R.drawable.ic_sevenhundreddays);
                        }
                    } else if(Objects.requireNonNull(preferences.getString(RANK, "")).equalsIgnoreCase("panther")) {
                        if (position > TEN +7) {
                            holder.img.setAlpha(0.2f);
                            holder.tv.setAlpha(0.2f);
                            holder.days.setAlpha(0.2f);
                            imageView.setBackgroundResource(R.drawable.ic_eighthundreddays);
                        }
                    } else if(Objects.requireNonNull(preferences.getString(RANK, "")).equalsIgnoreCase("tiger")) {
                        if (position > TEN +8) {
                            holder.img.setAlpha(0.2f);
                            holder.tv.setAlpha(0.2f);
                            holder.days.setAlpha(0.2f);
                            imageView.setBackgroundResource(R.drawable.ic_ninehundreddays);
                        }
                    } else if(Objects.requireNonNull(preferences.getString(RANK, "")).equalsIgnoreCase("lion")) {
                        if (position > TEN + TEN-1) {
                            holder.img.setAlpha(0.2f);
                            holder.tv.setAlpha(0.2f);
                            holder.days.setAlpha(0.2f);
                            imageView.setBackgroundResource(R.drawable.ic_onethousanddays);
                        }
                    }
                }
            return view;
        }
    }
    public class ViewHolder {
        TextView tv, desc, days;
        ImageView img;
    }

}
