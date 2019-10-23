package com.taozen.quithabit.utils;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.os.Build;

public class LocaleUtils {
    @SuppressWarnings("deprecation")
    public static java.util.Locale getSystemLocaleLegacy(Configuration config){
        return config.locale;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static java.util.Locale getSystemLocale(Configuration config){
        return config.getLocales().get(0);
    }

    @SuppressWarnings("deprecation")
    public static void setSystemLocaleLegacy(Configuration config, java.util.Locale locale){
        config.locale = locale;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static void setSystemLocale(Configuration config, java.util.Locale locale){
        config.setLocale(locale);
    }
}
