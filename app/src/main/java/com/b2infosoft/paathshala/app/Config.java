package com.b2infosoft.paathshala.app;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by rajesh on 7/24/2016.
 */
public class Config {
    private final String SERVER_IP = "192.168.0.57";
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

}
