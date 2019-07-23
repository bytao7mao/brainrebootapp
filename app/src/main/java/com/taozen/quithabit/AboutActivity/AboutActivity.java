package com.taozen.quithabit.AboutActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.shashank.sony.fancyaboutpagelib.FancyAboutPage;
import com.taozen.quithabit.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        FancyAboutPage fancyAboutPage=findViewById(R.id.fancyaboutpage);
        //fancyAboutPage.setCoverTintColor(Color.BLUE);  //Optional
        fancyAboutPage.setCover(R.drawable.dayshd); //Pass your cover image
        fancyAboutPage.setName("Marius Nicolae");
        fancyAboutPage.setDescription("Google Certified Associate Android Developer | Android App, Game, Web and Software Developer.");
        fancyAboutPage.setAppIcon(R.drawable.cakepop); //Pass your app icon image
        fancyAboutPage.setAppName("Cake Pop Icon Pack");
        fancyAboutPage.setVersionNameAsAppSubTitle("1.2.3");
        fancyAboutPage.setAppDescription("Cake Pop Icon Pack is an icon pack which follows Google's Material Design language.\n\n" +
                "This icon pack uses the material design color palette given by google. Every icon is handcrafted with attention to the smallest details!\n\n"+
                "A fresh new take on Material Design iconography. Cake Pop offers unique, creative and vibrant icons. Spice up your phones home-screen by giving it a fresh and unique look with Polycon.");
        fancyAboutPage.addEmailLink("rebootway@outlook.com");     //Add your email id
//        fancyAboutPage.addFacebookLink("https://www.facebook.com/shashanksinghal02");  //Add your facebook address url
//        fancyAboutPage.addTwitterLink("https://twitter.com/shashank020597");
        fancyAboutPage.addLinkedinLink("https://www.linkedin.com/in/shashank-singhal-a87729b5/");
        fancyAboutPage.addGitHubLink("https://github.com/Shashank02051997");
    }
}
