package com.taozen.quithabit.intro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;
import com.taozen.quithabit.R;
import com.taozen.quithabit.SplashActivity;

public class IntroActivity extends AppIntro {
    //shared pref
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //shared pref
        preferences = PreferenceManager.getDefaultSharedPreferences(IntroActivity.this);
        editor = preferences.edit();


        getWindow().setStatusBarColor(ContextCompat.getColor(IntroActivity.this, R.color.white));
        // Instead of fragments, you can also use our default slide.
        // Just create a `SliderPage` and provide title, description, background and image.
        // AppIntro will do the rest.
        SliderPage sliderPage = new SliderPage();
        sliderPage.setTitle("Bla bla title");
        sliderPage.setDescription("description");
        sliderPage.setImageDrawable(R.drawable.common_google_signin_btn_icon_dark_focused);
        sliderPage.setBgColor(Color.GREEN);
        SliderPage sliderPage2 = new SliderPage();
        sliderPage2.setTitle("Bla bla title");
        sliderPage2.setDescription("description");
        sliderPage2.setImageDrawable(R.drawable.common_google_signin_btn_icon_dark_focused);
        sliderPage2.setBgColor(Color.BLUE);
        SliderPage sliderPage3 = new SliderPage();
        sliderPage3.setTitle("Bla bla title");
        sliderPage3.setDescription("description");
        sliderPage3.setImageDrawable(R.drawable.common_google_signin_btn_icon_dark_focused);
        sliderPage3.setBgColor(Color.RED);
        addSlide(AppIntroFragment.newInstance(sliderPage));
        addSlide(AppIntroFragment.newInstance(sliderPage2));
        addSlide(AppIntroFragment.newInstance(sliderPage3));
    }

//    private void showDialog() {
//        //choose your habit ------------
//        AlertDialog.Builder habitAlert = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogTheme));
//        final EditText editTextForChoosingHabit = new EditText(IntroActivity.this);
//        habitAlert.setMessage("How much money do you spend per day ?");
//        habitAlert.setTitle("MONEY!");
//        habitAlert.setView(editTextForChoosingHabit);
//        habitAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                //What ever you want to do with the value
//                Editable editM = editTextForChoosingHabit.getText();
//                int moneyInt = Integer.parseInt(editM.toString());
//                editor.putInt(MONEYPERDAY, moneyInt);
//                editor.apply();
//                Log.d("INTROTAO", "money saved in INTROACTIVITY ? :  " + editM);
//            }
//        });
//        habitAlert.show();
//    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent i = new Intent(IntroActivity.this, SplashActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent i = new Intent(IntroActivity.this, SplashActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }


}
