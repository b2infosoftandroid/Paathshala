package com.b2infosoft.paathshala.app;

import android.content.Context;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by rajesh on 7/24/2016.
 */

public class Urls {
    private final String TAG = Urls.class.getName();

    private static Urls ourInstance = new Urls();
    private Config config = Config.getInstance();

    public static Urls getInstance() {
        return ourInstance;
    }

    private Urls() {

    }

    public String getStringRequest() {
        return config.getServerAddress().concat("string_request.php");
    }
}
