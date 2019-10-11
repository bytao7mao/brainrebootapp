package com.taozen.quithabit.about;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import com.shashank.sony.fancyaboutpagelib.FancyAboutPage;
import com.taozen.quithabit.R;

import java.util.Objects;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        FancyAboutPage fancyAboutPage=findViewById(R.id.fancyaboutpage);
        fancyAboutPage.setCoverTintColor(Color.GREEN);  //Optional
        fancyAboutPage.setCover(R.drawable.bgday);
        fancyAboutPage.setName("Marius Nicolae");
        fancyAboutPage.setDescription(getResources().getString(R.string.my_profile_description));
        fancyAboutPage.setAppIcon(R.drawable.android);
        fancyAboutPage.setAppName(getResources().getString(R.string.app_name));
        fancyAboutPage.setVersionNameAsAppSubTitle("0.5b");
        fancyAboutPage.setAppDescription(getResources().getString(R.string.about_app_description));
        fancyAboutPage.addEmailLink("rebootway@outlook.com");
        fancyAboutPage.addLinkedinLink("https://www.linkedin.com/in/nicolae-marius-37b344144/");
        fancyAboutPage.addGitHubLink("https://github.com/bytao7mao");
//        fancyAboutPage.addFacebookLink("");
//        fancyAboutPage.addTwitterLink("");
    }

}
