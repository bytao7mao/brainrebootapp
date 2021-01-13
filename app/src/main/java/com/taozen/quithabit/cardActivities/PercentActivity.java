package com.taozen.quithabit.cardActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.circularprogressbar.CircularProgressBar;
import com.taozen.quithabit.BuildConfig;
import com.taozen.quithabit.CongratulationsActivity;
import com.taozen.quithabit.MainActivity;
import com.taozen.quithabit.R;
import com.taozen.quithabit.Screenshoot;
import com.taozen.quithabit.options.OptionsActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.taozen.quithabit.MainActivity.BLOODLEVEL;
import static com.taozen.quithabit.MainActivity.BREATHLEVEL;
import static com.taozen.quithabit.MainActivity.ENERGYLEVEL;
import static com.taozen.quithabit.MainActivity.HEARTLEVEL;
import static com.taozen.quithabit.MainActivity.STRING_PERCENT;

public class PercentActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName() + "TAOMAO";

    @BindView(R.id.txtProgressIdForEnergy)
    TextView energyLevels;
    @BindView(R.id.txtProgressIdForHeart)
    TextView heartLevels;
    @BindView(R.id.txtProgressIdForBlood)
    TextView bloodLevels;
    @BindView(R.id.txtProgressIdForBreath)
    TextView breathLevels;
    @BindView(R.id.progress_bar_energy)
    CircularProgressBar energy;
    @BindView(R.id.progress_bar_heart)
    CircularProgressBar heart;
    @BindView(R.id.progress_bar_breath)
    CircularProgressBar breath;
    @BindView(R.id.progress_bar_blood)
    CircularProgressBar blood;

    @BindView(R.id.constraintLayout)
    ConstraintLayout percentLayout;


    //shared pref
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percent);
        ButterKnife.bind(PercentActivity.this);

        //shared pref
        preferences = PreferenceManager.getDefaultSharedPreferences(PercentActivity.this);
        editor = preferences.edit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.health));

//        toolbar.inflateMenu(R.menu.congrats_menu);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_orange_32dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        try {
            if (preferences.contains(ENERGYLEVEL)) {
                int energyInteger = preferences.getInt(ENERGYLEVEL, 0);
                energyLevels.setText(String.valueOf(energyInteger) + STRING_PERCENT);
                energy.setProgress(energyInteger);
            }
            if (preferences.contains(HEARTLEVEL)) {
                int heartInteger = preferences.getInt(HEARTLEVEL, 0);
                heartLevels.setText(String.valueOf(heartInteger) + STRING_PERCENT);
                heart.setProgress(heartInteger);
            }
            if (preferences.contains(BLOODLEVEL)) {
                int bloodInteger = preferences.getInt(BLOODLEVEL, 0);
                bloodLevels.setText(String.valueOf(bloodInteger) + STRING_PERCENT);
                blood.setProgress(bloodInteger);
            }
            if (preferences.contains(BREATHLEVEL)) {
                int breathInteger = preferences.getInt(BREATHLEVEL, 0);
                breathLevels.setText(String.valueOf(breathInteger) + STRING_PERCENT);
                breath.setProgress(breathInteger);
            }
        } catch (Exception e) {e.printStackTrace();}

//        findViewById(R.id.shareCongrats).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(PercentActivity.this, getResources().getString(R.string.congrats_for_share), Toast.LENGTH_SHORT).show();
//                Bitmap b = Screenshoot.takescreenshot(percentLayout);
//                getShareScreenShoot(b);
//            }
//        });

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
