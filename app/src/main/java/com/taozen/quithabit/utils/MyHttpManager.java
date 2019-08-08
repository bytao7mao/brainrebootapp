package com.taozen.quithabit.utils;

import android.net.http.AndroidHttpClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

public class MyHttpManager {
    public static String getData(String uri){
        AndroidHttpClient androidHttpClient = AndroidHttpClient.newInstance("AndroidAgent");
        HttpGet request = new HttpGet(uri);
        HttpResponse response;
        try {
            //if everything works correctly
            response = androidHttpClient.execute(request);
            //return the response into string type chars (Human readable characters)
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            //in every situation(good or bad) we close the client
            androidHttpClient.close();
        }

    }
}
