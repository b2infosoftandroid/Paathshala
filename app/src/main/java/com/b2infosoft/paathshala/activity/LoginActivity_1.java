package com.b2infosoft.paathshala.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.app.Tags;
import com.b2infosoft.paathshala.app.Urls;
import com.b2infosoft.paathshala.volly.MySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

public class LoginActivity_1 extends AppCompatActivity  {
    Urls urls;
    MySingleton mySingleton;
    Tags tags = Tags.getInstance();
    StringRequest stringRequest; // Assume this exists.
    RequestQueue requestQueue;  // Assume this exists.
    Button login, forgot_password;
    private final String TAG = LoginActivity_1.class.getName();
    private Hashtable<String,String> sessionList;

    /* UI  */
    private EditText session_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_1);
        urls = Urls.getInstance();
        mySingleton = MySingleton.getInstance(this);
        requestQueue = mySingleton.getRequestQueue();
        MyButton button = new MyButton();
        login = (Button) findViewById(R.id.login_button_login);
        login.setOnClickListener(button);
        forgot_password = (Button) findViewById(R.id.login_button_forgot_password);
        forgot_password.setOnClickListener(button);

        session_list = (EditText)findViewById(R.id.login_session);
        session_list.setOnClickListener(button);
        fetchSession();
    }

    private void loginSuccess() {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void selectSession() {

        //CharSequence[] items = {"2001-2002", "2002-2003", "2003-2004", "2004-2005", "2005-2006"};
        String [] items={};
        if(sessionList!=null){
            Set<String> stringSet = sessionList.keySet();
            items = new String[stringSet.size()];
            Iterator<String> iterator = stringSet.iterator();
            int i=0;
            while(iterator.hasNext()){
                items[i++] = iterator.next();
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Session");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog prepare_radio_dialog = (AlertDialog) builder.create();;
        ListView list_radio = prepare_radio_dialog.getListView();
        for (int i = 0; i < list_radio.getCount(); i++) {
            list_radio.setItemChecked(i, false);
        }
        prepare_radio_dialog.show();
    }

    private class MyButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_button_login:
                    loginSuccess();
                    break;
                case R.id.login_button_forgot_password:
                    //requestBuilder();
                    break;
                case R.id.login_session:
                    selectSession();
                    break;
            }
        }
    }

    private void fetchSession() {
        HashMap<String, String> map = new HashMap<>();
        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, urls.getUrl(urls.getPath(tags.SESSION_LIST), map), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG,response.toString());
                        try {
                            if (response.has(tags.ARR_RESULT)) {
                                JSONArray result = response.getJSONArray(tags.ARR_RESULT);
                                JSONObject object = result.getJSONObject(0);
                                if(object.has(tags.RESPONSE)){
                                    String res = object.getString(tags.RESPONSE);
                                    if(res.equals(tags.RESPONSE_PASS)){

                                    }
                                }
                            }
                            if(response.has(tags.ARR_SESSION_LIST)){
                                JSONArray sessionArray = response.getJSONArray(tags.ARR_SESSION_LIST);
                                if(sessionList==null){
                                    sessionList = new Hashtable<>();
                                }else{
                                    sessionList.clear();
                                }
                                for(int i=0;i<sessionArray.length();i++){
                                    JSONObject object = sessionArray.getJSONObject(i);
                                    String id =null;
                                    String year = null;
                                    if(object.has(tags.SESSION_ID)){
                                        id = object.getString(tags.SESSION_ID);
                                    }
                                    if(object.has(tags.SESSION_YEAR)){
                                        year = object.getString(tags.SESSION_YEAR);
                                    }
                                    if(id!=null && year !=null){
                                        sessionList.put(year,id);
                                    }
                                }
                            }
                        }catch (Exception e){

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG,error.toString());
                    }
                });
        jsObjRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        jsObjRequest.setTag(TAG);
        //requestQueue.add(jsObjRequest);
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
    }

    private void requestBuilder() {
        requestQueue = Volley.newRequestQueue(this);
        HashMap<String, String> map = new HashMap<>();
        map.put(tags.USER_ID, "5697");
        map.put(tags.SESSION_ID, "8");
        map.put(tags.SCHOOL_ID, "1");
        map.put(tags.PWD_ID, "1");

        //Log.d("URL",urls.getUrl(urls.getPath(tags.CHECK_USER),map));

        stringRequest = new StringRequest(Request.Method.GET, urls.getUrl(urls.getPath(tags.CHECK_USER), map), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                Toast.makeText(getBaseContext(), response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
                Toast.makeText(getBaseContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setTag(TAG);
        requestQueue.add(stringRequest);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
    }
}
