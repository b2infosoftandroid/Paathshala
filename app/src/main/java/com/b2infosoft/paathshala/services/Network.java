package com.b2infosoft.paathshala.services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by rajesh on 8/6/2016.
 */
public class Network {
    private Context context;

    public Network(Context context) {
        this.context = context;
    }

    public static Network getInstance(Context context) {
        return new Network(context);
    }

    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
