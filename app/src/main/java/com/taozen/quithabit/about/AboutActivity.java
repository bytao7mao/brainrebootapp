package com.taozen.quithabit.about;

import android.graphics.Color;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import com.shashank.sony.fancyaboutpagelib.FancyAboutPage;
import com.taozen.quithabit.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getWindow().setStatusBarColor(ContextCompat.getColor(AboutActivity.this, R.color.white));
        FancyAboutPage fancyAboutPage=findViewById(R.id.fancyaboutpage);
        fancyAboutPage.setCoverTintColor(Color.GREEN);  //Optional
        fancyAboutPage.setCover(R.drawable.bgday); //Pass your cover image
        fancyAboutPage.setName("Marius Nicolae");
        fancyAboutPage.setDescription("Android Developer | Software Developer | Java Enthusiast");
        fancyAboutPage.setAppIcon(R.drawable.android); //Pass your app icon image
        fancyAboutPage.setAppName("Brain Reboot App");
        fancyAboutPage.setVersionNameAsAppSubTitle("0.4a");
        fancyAboutPage.setAppDescription("Brain Reboot App is a motivational app that tracks your daily struggle to stop your habit.\n\n" +
                "This app also tracks daily saved amount of money and have daily motivational quotes.\n"+
                "I have to mention a special list of authors from which i borrowed design from:\n\n"+

                "App icon made by @Roundicons from www.flaticon.com;\n" +
                "Savings icon made by @Eucalyp from www.flaticon.com;\n" +
                "Rank icons made by @Dimitry Miroliubov/Smashicons from www.flaticon.com;\n" +
                "Card icons made by @icongeek26 from www.flaticon.com;\n" +
                "Counter icon made by @starline from www.flaticon.com but made some modifications by myself." +
                "\nAs you can see, i did not added any advertising campaign because, i, myself, dislike ads.\n"+
                "\nIf you enjoyed this app, please support it by sharing it with friends or other people.\n" +
                "\n\nThank you!");
        fancyAboutPage.addEmailLink("rebootway@outlook.com");     //Add your email id
//        fancyAboutPage.addFacebookLink("https://www.facebook.com/shashanksinghal02");  //Add your facebook address url
//        fancyAboutPage.addTwitterLink("https://twitter.com/shashank020597");
        fancyAboutPage.addLinkedinLink("https://www.linkedin.com/in/nicolae-marius-37b344144/");
        fancyAboutPage.addGitHubLink("https://github.com/bytao7mao");
    }

}
