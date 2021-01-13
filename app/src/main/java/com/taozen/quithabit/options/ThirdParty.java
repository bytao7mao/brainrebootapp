package com.taozen.quithabit.options;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import com.taozen.quithabit.R;

public class ThirdParty extends AppCompatActivity {

    String[] descArray, titles;
    //shared pref
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    ListView mListView;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_party);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.third_party_licences));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_orange_32dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //shared pref
        preferences = PreferenceManager.getDefaultSharedPreferences(ThirdParty.this);
        editor = preferences.edit();
        descArray = getResources().getStringArray(R.array.titles_arr_for_third_party);
        titles = getResources().getStringArray(R.array.titles_arr_for_third_party_titles);
        int count = titles.length;

        mListView = findViewById(R.id.list_view_third_party);
        mListView.setDivider(null);
        ThirdParty.CustomAdapterListView customAdapterListView =
                new ThirdParty.CustomAdapterListView(titles, descArray);
        mListView.setAdapter(customAdapterListView);


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    class CustomAdapterListView extends BaseAdapter {
        final String [] titlesArr;
        final String [] descArr;

        private LayoutInflater inflater=null;
        CustomAdapterListView(String[] titlesList, String[] descList) {
            // TODO Auto-generated constructor stub
            titlesArr = titlesList;
            descArr = descList;

            inflater = (LayoutInflater) getApplicationContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return titles.length;
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
            ThirdParty.ViewHolder holder=new ThirdParty.ViewHolder();
            View view;
            view = inflater.inflate(R.layout.list_item_third_party, null);
            if (view == null) {
                view = View.inflate(parent.getContext(), R.layout.list_item_third_party, null);
            }
            holder.tv = view.findViewById(R.id.achievements_lv_title_tv);
            holder.highlightJsView = view.findViewById(R.id.highlight_view);

            holder.tv.setText(titlesArr[position]);
            holder.highlightJsView.setText(descArr[position]);

            return view;
        }

    }
    public class ViewHolder {
        TextView tv;
        TextView highlightJsView;
    }
}
