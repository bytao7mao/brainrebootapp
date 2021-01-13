package com.taozen.quithabit;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class CongratulationsActivity extends AppCompatActivity {

    //shared pref
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    ConstraintLayout constraintLayout;
    MaterialCardView materialCardView;
    ImageView imageView;
    MaterialTextView textView;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulations);

        //shared pref
        preferences = PreferenceManager.getDefaultSharedPreferences(CongratulationsActivity.this);
        editor = preferences.edit();
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.achievement));
        constraintLayout = findViewById(R.id.constraintLayout);
        imageView = findViewById(R.id.activity_achievements_last_achievement_img);
        materialCardView = findViewById(R.id.achievementCardV);
        textView = findViewById(R.id.materialTextViewBottom);

        toolbar.inflateMenu(R.menu.congrats_menu);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_orange_32dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.shareCongrats).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CongratulationsActivity.this, getResources().getString(R.string.congrats_for_share), Toast.LENGTH_SHORT).show();
                Bitmap b = Screenshoot.takescreenshot(materialCardView);
                getShareScreenShoot(b);
            }
        });

        checkRankAndSetImageForRankAndSubText();




    }

    private void checkRankAndSetImageForRankAndSubText() {
        //using API from splash
        final Intent INTENT = getIntent();
        final String NAME = INTENT.getStringExtra("rankimg");

        if (Objects.requireNonNull(NAME).equalsIgnoreCase("oneday")){
            imageView.setBackgroundResource(R.drawable.ic_oneday);
            textView.setText(getResources().getString(R.string.share_progress_with_friends_text));
        }else if (Objects.requireNonNull(NAME).equalsIgnoreCase("oneweek")) {
            imageView.setBackgroundResource(R.drawable.ic_oneweek);
            textView.setText(getResources().getString(R.string.share_progress_with_friends_text));
        } else {
            imageView.setBackgroundResource(R.drawable.ic_qdate);
            textView.setText(getResources().getString(R.string.quit_date_text_congrats));
        }

    }
    private void getShareScreenShoot(Bitmap b) {
        //                backgroundImgWall.setImageBitmap(b);
        Uri uri = null;
        try {
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "to-share.png");
            FileOutputStream stream = new FileOutputStream(file);
            b.compress(Bitmap.CompressFormat.PNG, 90, stream);
            stream.close();
            uri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            if (BuildConfig.DEBUG) {
                Log.d("CONGRATSACTIVITY", "IOException while trying to write file for sharing: " + e.getMessage());
            }
        }

        //Convert to byte array
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.my_achievement_share_text));
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            startActivity(Intent.createChooser(intent, getResources().getString(R.string.share_progress)));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_app_available), Toast.LENGTH_SHORT).show();
        }
    }
}
