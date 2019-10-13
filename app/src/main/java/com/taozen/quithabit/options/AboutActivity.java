package com.taozen.quithabit.options;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.shashank.sony.fancyaboutpagelib.FancyAboutPage;
import com.taozen.quithabit.R;

import java.util.Objects;

import static com.taozen.quithabit.MainActivity.TERMS_AND_CONDITIONS;

public class AboutActivity extends AppCompatActivity {
    public static final String TERMS = "https://bytao7mao.github.io/quithabit/legal/terms_and_conditions.html";
    public static final String LICENCES = "https://bytao7mao.github.io/quithabit/legal/licences.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        TextView tv_terms_and_conditions = findViewById(R.id.tv_terms_and_conditions);
        tv_terms_and_conditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent INTENT = new Intent(Intent.ACTION_VIEW);
                INTENT.setData(Uri.parse(TERMS));
                startActivity(INTENT);
            }
        });
        TextView tv_licences = findViewById(R.id.tv_third_party_licences_link);
        tv_licences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent INTENT = new Intent(Intent.ACTION_VIEW);
                INTENT.setData(Uri.parse(LICENCES));
                startActivity(INTENT);
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
