package com.b2infosoft.paathshala.credential;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rajesh on 7/12/2016.
 */

public class Active {
    private static final String USER_CREDENTIALS="dmr_rajesh";
    private static final int PRIVATE_KEY=0;
    private static final String DEVICE_LOGIN="device_login";
    private static final boolean STATUS[]={false,true};

    public static void setKey(Context context,String key,String value){
        SharedPreferences setting= context.getSharedPreferences(USER_CREDENTIALS,PRIVATE_KEY);
        setting.edit()
                .putString(key,value)
                .commit();
    }
    public static String getValue(Context context,String key){
        SharedPreferences setting= context.getSharedPreferences(USER_CREDENTIALS,0);
        return setting.getString(key,"");
    }
    public static void setLogin(Context context){
        SharedPreferences setting= context.getSharedPreferences(USER_CREDENTIALS, PRIVATE_KEY);
        setting.edit()
                .putBoolean(DEVICE_LOGIN, STATUS[1])
                .commit();
    }
    public static void setLogOut(Context context){
        SharedPreferences setting= context.getSharedPreferences(USER_CREDENTIALS,PRIVATE_KEY);
        setting.edit()
                .putBoolean(DEVICE_LOGIN, STATUS[0])
                .commit();
    }
    public static boolean isLogin(Context context){
        SharedPreferences setting= context.getSharedPreferences(USER_CREDENTIALS,0);
        return setting.getBoolean(DEVICE_LOGIN,STATUS[0]);
    }
}
