package com.taozen.quithabit.Intro;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;
import com.taozen.quithabit.MainActivity;
import com.taozen.quithabit.R;

public class IntroActivity extends AppIntro {

    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            i = new Intent(IntroActivity.this, MainActivity.class);
        } catch (NullPointerException e) {e.printStackTrace();}

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
        sliderPage2.setBgColor(Color.GREEN);
        addSlide(AppIntroFragment.newInstance(sliderPage));
        addSlide(AppIntroFragment.newInstance(sliderPage2));
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        startActivity(i);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startActivity(i);
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }


}
