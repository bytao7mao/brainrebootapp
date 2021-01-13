package com.taozen.quithabit.options;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;

import com.google.android.material.appbar.MaterialToolbar;
import com.taozen.quithabit.R;

import static com.taozen.quithabit.MainActivity.PRIVACY_POLICY;
import static com.taozen.quithabit.MainActivity.TERMS_AND_CONDITIONS;

public class LegalActivity extends AppCompatActivity implements View.OnClickListener {

    private final int PRIVACY_INTEGER = R.id.privacy_policy;
    private final int TERMS_AND_CONDITIONS_INTEGER = R.id.terms_of_service;
    private final int THIRD_PARTY_LICENCES_INTEGER = R.id.third_party_policy;
    //shared preferences
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    String[] titles;
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal);

        //shared preferences
        preferences = PreferenceManager.getDefaultSharedPreferences(LegalActivity.this);
        editor = preferences.edit();
        titles = getResources().getStringArray(R.array.legal_array);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.legal));
//        toolbar.setTitleTextAppearance(getApplicationContext(), R.style.TitleBarToolbarCustom);
//        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_orange_32dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //third instead of privacy
        TextView listview_title5 = findViewById(R.id.listview_title2);
        listview_title5.setText(titles[0]);
        //privacy instead of terms
        TextView listview_title6 = findViewById(R.id.listview_title3);
        listview_title6.setText(titles[1]);
        //terms instead of third
        TextView listview_titleb = findViewById(R.id.listview_title4);
        listview_titleb.setText(titles[2]);


        ConstraintLayout privacyConstraintLayout = findViewById(PRIVACY_INTEGER);
        ConstraintLayout termsConstraintLayout = findViewById(TERMS_AND_CONDITIONS_INTEGER);
        ConstraintLayout thirdPartyConstraintLayout = findViewById(THIRD_PARTY_LICENCES_INTEGER);



        privacyConstraintLayout.setOnClickListener(this);
        termsConstraintLayout.setOnClickListener(this);
        thirdPartyConstraintLayout.setOnClickListener(this);


    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case PRIVACY_INTEGER:
                final Intent INTENT_PRIVACY = new Intent(Intent.ACTION_VIEW);
                INTENT_PRIVACY.setData(Uri.parse(PRIVACY_POLICY));
                startActivity(INTENT_PRIVACY);
                break;
            case TERMS_AND_CONDITIONS_INTEGER:
                final Intent INTENT_TERMS = new Intent(Intent.ACTION_VIEW);
                INTENT_TERMS.setData(Uri.parse(TERMS_AND_CONDITIONS));
                startActivity(INTENT_TERMS);
                break;
            case THIRD_PARTY_LICENCES_INTEGER:
                //TODO: third party licences like ip app
                final Intent INTENT_THIRD_PARTY = new Intent(LegalActivity.this, ThirdParty.class);
                startActivity(INTENT_THIRD_PARTY);
                break;
        }
    }
}
