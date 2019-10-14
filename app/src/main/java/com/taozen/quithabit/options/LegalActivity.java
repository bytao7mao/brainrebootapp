package com.taozen.quithabit.options;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.taozen.quithabit.R;

import java.util.Objects;

import static com.taozen.quithabit.MainActivity.PRIVACY_POLICY;
import static com.taozen.quithabit.MainActivity.TERMS_AND_CONDITIONS;

public class LegalActivity extends AppCompatActivity {

    private int resetProgressInteger = 0;
    private int privacyInteger = 1;
    private int termsAndConditionsInteger = 2;
    private int thirdPartyLicencesInteger = 3;
    private int feedbackInteger = 4;
    private int aboutInteger = 5;

    String[] titles;
    String[] descriptions = new String[] {
            "", "", "", "", "", "version 0.9 (100)"};

    //shared pref
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    ListView mListView;

    //fonts
    static Typeface montSerratBoldTypeface;
    static Typeface montSerratItallicTypeface;
    static Typeface montSerratLightTypeface;
    static Typeface montSerratMediumTypeface;
    static Typeface montSerratSemiBoldTypeface;
    static Typeface montSerratExtraBoldTypeface;
    static Typeface montSerratSimpleBoldTypeface;
    static Typeface montSerratThinItalicTypeface;
    static Typeface montSerratMediumItalicTypeface;
    static Typeface montSerratSemiBoldItalicTypeface;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        //shared pref
        preferences = PreferenceManager.getDefaultSharedPreferences(LegalActivity.this);
        editor = preferences.edit();
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Settings");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        titles = getResources().getStringArray(R.array.options_array);
        mListView = findViewById(R.id.list_view);
        LegalActivity.CustomAdapterListView customAdapterListView = new LegalActivity.CustomAdapterListView(this, titles, descriptions);
        mListView.setAdapter(customAdapterListView);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    class CustomAdapterListView extends BaseAdapter {

        String [] titles, descriptions;
        Context context;
        private LayoutInflater inflater=null;
        CustomAdapterListView(LegalActivity mainActivity, String[] prgmNameList, String[] descriptions) {
            // TODO Auto-generated constructor stub
            this.descriptions=descriptions;
            titles=prgmNameList;
            context=mainActivity;
            inflater = ( LayoutInflater )context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        private void setFontBold(TextView textViewBold, TextView textViewNormal){
            montSerratBoldTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Black.ttf");
            montSerratItallicTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Italic.ttf");
            montSerratLightTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Light.ttf");
            montSerratMediumTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Medium.ttf");
            montSerratSemiBoldTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-SemiBold.ttf");
            montSerratExtraBoldTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-ExtraBold.ttf");
            montSerratSimpleBoldTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Bold.ttf");
            montSerratThinItalicTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-ThinItalic.ttf");
            montSerratMediumItalicTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-MediumItalic.ttf");
            montSerratSemiBoldItalicTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-SemiBoldItalic.ttf");

            textViewBold.setTypeface(montSerratBoldTypeface);
            textViewNormal.setTypeface(montSerratMediumItalicTypeface);
        }
        private void setFontBoldForDialog(TextView textViewBold, TextView textViewNormal){
            montSerratBoldTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Black.ttf");
            montSerratItallicTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Italic.ttf");
            montSerratLightTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Light.ttf");
            montSerratMediumTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Medium.ttf");
            montSerratSemiBoldTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-SemiBold.ttf");
            montSerratExtraBoldTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-ExtraBold.ttf");
            montSerratSimpleBoldTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Bold.ttf");
            montSerratThinItalicTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-ThinItalic.ttf");
            montSerratMediumItalicTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-MediumItalic.ttf");
            montSerratSemiBoldItalicTypeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-SemiBoldItalic.ttf");

            textViewBold.setTypeface(montSerratSimpleBoldTypeface);
            textViewNormal.setTypeface(montSerratMediumItalicTypeface);
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            LegalActivity.Holder holder=new LegalActivity.Holder();
            View view;
            view = inflater.inflate(R.layout.list_item_options, null);
            if (view == null) {
                view = View.inflate(parent.getContext(), R.layout.list_item_options, null);
            }
            holder.tv = view.findViewById(R.id.listview_title);
            holder.desc = view.findViewById(R.id.listview_desc);
            holder.tv.setText(titles[position]);
            holder.desc.setText(descriptions[position]);
            setFontBold(holder.tv, holder.desc);
            if (position==5){
                setFontBoldForDialog(holder.tv, holder.desc);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == privacyInteger) {
                        final Intent INTENT = new Intent(Intent.ACTION_VIEW);
                        INTENT.setData(Uri.parse(PRIVACY_POLICY));
                        startActivity(INTENT);
                    } else if (position == termsAndConditionsInteger) {
                        final Intent INTENT = new Intent(Intent.ACTION_VIEW);
                        INTENT.setData(Uri.parse(TERMS_AND_CONDITIONS));
                        startActivity(INTENT);
                    } else if (position == thirdPartyLicencesInteger) {
                        //TODO: third party licences like ip app

                    } else if (position == feedbackInteger) {
                        final Intent INTENT = new Intent(Intent.ACTION_SEND);
                        INTENT.setType("message/rfc822");
                        INTENT.putExtra(Intent.EXTRA_EMAIL  , new String[]{"rebootway@outlook.com"});
                        INTENT.putExtra(Intent.EXTRA_SUBJECT, "feedback quithabit");
                        INTENT.putExtra(Intent.EXTRA_TEXT   , "");
                        try {
                            startActivity(Intent.createChooser(INTENT, "Send mail..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(LegalActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                        }
                    } else if (position == resetProgressInteger) {
                        dialogForResetForced();
                    } else if (position == aboutInteger) {
                        final Intent INTENT = new Intent(LegalActivity.this, AboutActivity.class);
                        startActivity(INTENT);
                    }
                }
            });
            return view;
        }
    }
    public class Holder {
        TextView tv, desc;
    }
    private void dialogForResetForced(){
        new BottomDialog.Builder(this)
                .setTitle("Your progress will reset permanently!")
                .setContent("Are you sure ?")
                .setPositiveText(R.string.YES)
                .setNegativeText(R.string.NO)
                .setPositiveBackgroundColorResource(R.color.colorPrimary)
                .setPositiveTextColorResource(android.R.color.white)
                .onNegative(new BottomDialog.ButtonCallback() {
                    @Override
                    public void onClick(@NonNull BottomDialog bottomDialog) {

                    }
                })
                .onPositive(new BottomDialog.ButtonCallback() {
                    @Override
                    public void onClick(@NonNull BottomDialog bottomDialog) {
                        clearAppData();
                    }
                }).show();
    }
    private void clearAppData() {
        try {
            // clearing app data
            if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
                // note: it has a return value!
                ((ActivityManager) Objects.requireNonNull(getSystemService(ACTIVITY_SERVICE))).clearApplicationUserData();
            } else {
                String packageName = getApplicationContext().getPackageName();
                Runtime runtime = Runtime.getRuntime();
                runtime.exec("Your progress has been reset! "+packageName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


