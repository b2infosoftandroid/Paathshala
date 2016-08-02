package com.b2infosoft.paathshala.app;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by rajesh on 7/24/2016.
 */
public class Config {
    private final String SERVER_IP = "192.168.0.57";
    public final String SERVER_PATH ="http://app.paathshala.net.in/App/";
    private final Boolean LIVE_SERVER = false;
    private static Config ourInstance = new Config();

    public static Config getInstance() {
        return ourInstance;
    }
    private Config() {

    }
    public String getServerAddress(){
        if(LIVE_SERVER){
            return "";
        }else{
            return "http://".concat(SERVER_IP).concat("/rajesh/paathshala_services/");
        }
    }
    public final String MONTH_NAME[] = {"APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC","JAN","FAB","MAR"};
    public final int MONTH_ID[]   = {3,4,5,6,7,8,9,10,11,0,1,2};
}
