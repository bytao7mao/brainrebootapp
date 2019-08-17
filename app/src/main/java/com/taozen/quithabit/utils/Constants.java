package com.taozen.quithabit.utils;

import android.util.Log;

public class Constants {

    private static Constants obj;

    private Constants() {
        // restrict instantiation
    }

    //fake getInstance method
    public static Constants getInstance() {
        if (obj==null)
//            obj = new Constants();
            Log.e("QUIHABITERR", "HAHA FOOL, YOU CANNOT INSTANTIATE CONSTANTS CLASS LOL");
        return null;
    }

    public interface SharedPreferences{
        String SAVINGS_FINAL = "SAVINGS_FINAL";
        String CHALLENGES_STRING = "CHALLENGES_FINAL";
        String CLICKED = "CLICKED";
        String COUNTER = "COUNTER";
        String INITIAL_CIGG_PER_DAY = "CIGARETTES_INITIAL";
        String MODIFIED_CIGG_PER_DAY = "CIGARETTES_MODIFIED";
        String LIFEREGAINED = "LIFEREGAINED";
        String DAYOFPRESENT = "DAYOFPRESENT";
        String PRESENTDAY = "PRESENTDAY";
        String HOUR_OF_FIRSLAUNCH_SP = "FIRSTHOUR";
    }



}
