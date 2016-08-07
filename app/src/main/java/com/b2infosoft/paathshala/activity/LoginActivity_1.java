package com.b2infosoft.paathshala.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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
import com.b2infosoft.paathshala.app.Validation;
import com.b2infosoft.paathshala.credential.Active;
import com.b2infosoft.paathshala.database.DBHelper;
import com.b2infosoft.paathshala.model.City;
import com.b2infosoft.paathshala.model.InstituteInfo;
import com.b2infosoft.paathshala.model.StudentInfo;
import com.b2infosoft.paathshala.services.Network;
import com.b2infosoft.paathshala.volly.MySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class LoginActivity_1 extends AppCompatActivity {
    Active active;
    Urls urls;
    MySingleton mySingleton;
    Network network;
    Tags tags = Tags.getInstance();
    StringRequest stringRequest; // Assume this exists.
    RequestQueue requestQueue;  // Assume this exists.
    Button login, forgot_password;
    TextView get_id;
    private final String TAG = LoginActivity_1.class.getName();
    private Hashtable<String, String> sessionList;
    /* UI  */
    private EditText session_list, login_institute_id, login_student_scholar_no, login_password_1;
    private ProgressDialog progress = null;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        active = Active.getInstance(this);

        Active active= Active.getInstance(getApplicationContext());
        if(active.isLogin()) {
            startActivity(new Intent(LoginActivity_1.this, MainActivity.class));
            finish();
        }
        setContentView(R.layout.activity_login_1);
        urls = Urls.getInstance();
        network = Network.getInstance(this);
        mySingleton = MySingleton.getInstance(this);
        requestQueue = mySingleton.getRequestQueue();
        dbHelper = new DBHelper(this);
        MyButton button = new MyButton();
        login = (Button) findViewById(R.id.login_button_login);
        login.setOnClickListener(button);
        forgot_password = (Button) findViewById(R.id.login_button_forgot_password);
        forgot_password.setOnClickListener(button);
        login_institute_id = (EditText) findViewById(R.id.login_institute_id);
        login_student_scholar_no = (EditText) findViewById(R.id.login_student_scholar_no);
        login_password_1 = (EditText) findViewById(R.id.login_password_1);
        get_id = (TextView) findViewById(R.id.get_institute_id);
        get_id.setOnClickListener(button);

        session_list = (EditText) findViewById(R.id.login_session);
        session_list.setOnClickListener(button);

        sessionList = new Hashtable<>();
        sessionList = dbHelper.getSession();
        if (sessionList.size() == 0) {
            fetchSession();
        }

        List<City> cities = dbHelper.getCity();
        if (cities.size() == 0) {
            fetchCity();
        }
        List<InstituteInfo> infoList = dbHelper.getInstitute();
        if (infoList.size() == 0) {
            fetchInstitute();
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        sessionList = new Hashtable<>();
        sessionList = dbHelper.getSession();
        if (sessionList.size() == 0) {
            fetchSession();
        }

        List<City> cities = dbHelper.getCity();
        if (cities.size() == 0) {
            fetchCity();
        }
        List<InstituteInfo> infoList = dbHelper.getInstitute();
        if (infoList.size() == 0) {
            fetchInstitute();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        sessionList = new Hashtable<>();
        sessionList = dbHelper.getSession();
        if (sessionList.size() == 0) {
            fetchSession();
        }

        List<City> cities = dbHelper.getCity();
        if (cities.size() == 0) {
            fetchCity();
        }
        List<InstituteInfo> infoList = dbHelper.getInstitute();
        if (infoList.size() == 0) {
            fetchInstitute();
        }

    }

    private void getId() {
        startActivity(new Intent(this, GetInstituteId.class));
    }

    private void forgotPassword() {
        startActivity(new Intent(this, ForgotPassword.class));
    }

    private void loginSuccess() {
        if (!network.isInternetAvailable()) {
            Toast.makeText(this, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put(tags.S_ID, active.getValue(tags.S_ID));
        map.put(tags.SESSION_ID, active.getValue(tags.SESSION_ID));
        map.put(tags.SCHOOL_ID, active.getValue(tags.SCHOOL_ID));
        showProgress();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, urls.getUrl(urls.getPath(tags.STUDENT_INFO), map), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                if (response.has(tags.ARR_STUDENT_INFO)) {
                                    JSONArray jsonArray = response.getJSONArray(tags.ARR_STUDENT_INFO);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        StudentInfo info = new StudentInfo();
                                        if (object.has(tags.S_STU_PHOTO)) {
                                            info.setStudentPhoto(object.getString(tags.S_STU_PHOTO));
                                        }
                                        if (object.has(tags.S_INFO_STU_NAME)) {
                                            info.setName(object.getString(tags.S_INFO_STU_NAME));
                                        }
                                        active.setKey(tags.S_STU_PHOTO, info.getStudentPhoto());
                                        active.setKey(tags.S_INFO_STU_NAME, info.getName());
                                    }
                                }
                                dismissProgress();
                                doLogin();
                            } catch (Exception e) {

                            }
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
                    }
                });
        jsonObjectRequest.setTag(TAG);
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }

    private void doLogin() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    String[] items = {};
    private void selectSession() {
        //CharSequence[] items = {"2001-2002", "2002-2003", "2003-2004", "2004-2005", "2005-2006"};
        if (sessionList != null) {
            List<String> list = new ArrayList<>(sessionList.keySet());
            Collections.sort(list);
            items = new String[list.size()];
            int i = 0;
            for (String string : list) {
                items[i++] = string;
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Session");
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (sessionList != null && sessionList.size() > 0 && items.length >0) {
                    ListView lw = ((AlertDialog) dialog).getListView();
                    Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                    session_list.setText(checkedItem.toString());
                } else {
                    Toast.makeText(LoginActivity_1.this,"Please Select Session",Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private class MyButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_button_login:
                    //loginSuccess();
                    loginUser();
                    break;
                case R.id.login_button_forgot_password:
                    //requestBuilder();
                    forgotPassword();
                    break;
                case R.id.login_session:
                    selectSession();
                    break;
                case R.id.get_institute_id:
                    getId();
                    break;
            }
        }
    }

    private void loginUser() {
        Validation validation = Validation.getInstance();
        final String institute_id = login_institute_id.getText().toString();
        final String session = session_list.getText().toString();
        String user_id = login_student_scholar_no.getText().toString();
        String password = login_password_1.getText().toString();

        login_institute_id.setError(null);
        session_list.setError(null);
        login_student_scholar_no.setError(null);
        login_password_1.setError(null);

        if (!validation.isInstituteID(institute_id)) {
            login_institute_id.setError("Invalid Institute ID");
            login_institute_id.requestFocus();
            return;
        }
        if (!validation.isSession(session)) {
            session_list.setError("Invalid Session");
            session_list.requestFocus();
            return;
        }
        if (!validation.isScholarID(user_id)) {
            login_student_scholar_no.setError("Invalid Scholar ID");
            login_student_scholar_no.requestFocus();
            return;
        }
        if (!validation.isPassword(password)) {
            login_password_1.setError("Invalid password");
            login_password_1.requestFocus();
            return;
        }
        if(!network.isInternetAvailable()){
            Toast.makeText(this,getResources().getString(R.string.no_internet_connection),Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put(tags.SCHOOL_ID, institute_id);
        map.put(tags.SESSION_ID, sessionList.get(session));
        map.put(tags.USER_ID, user_id);
        map.put(tags.PWD_ID, password);
        showProgress();
        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, urls.getUrl(urls.getPath(tags.CHECK_USER), map), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d(TAG, response.toString());
                        try {
                            if (response.has(tags.ARR_RESULT)) {
                                JSONArray array = response.getJSONArray(tags.ARR_RESULT);
                                JSONObject object = array.getJSONObject(0);
                                if (object.has(tags.RESPONSE)) {
                                    String res = object.getString(tags.RESPONSE);
                                    if (res.equals(tags.RESPONSE_PASS)) {

                                    }
                                }
                            }
                            if (response.has(tags.ARR_USER_INFO)) {
                                JSONArray array = response.getJSONArray(tags.ARR_USER_INFO);
                                JSONObject object = array.getJSONObject(0);
                                if (object.has(tags.S_ID)) {
                                    String s_id = object.getString(tags.S_ID);
                                    active.setKey(tags.S_ID, s_id);
                                    active.setKey(tags.SCHOOL_ID, institute_id);
                                    active.setKey(tags.SESSION_ID, sessionList.get(session));
                                    active.setKey(tags.SESSION, session);
                                    active.setLogin();
                                    dismissProgress();
                                    loginSuccess();
                                }
                                if (object.has(tags.COLUMN_1)) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity_1.this);
                                    builder.setTitle("Alert");
                                    builder.setMessage("Id & Password is wrong.");
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    builder.create().show();
                                }
                                dismissProgress();
                            }
                        } catch (Exception e) {
                            dismissProgress();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
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
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
    }

    private void fetchSession() {
        if(!network.isInternetAvailable()){
            Toast.makeText(this,getResources().getString(R.string.no_internet_connection),Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, urls.getUrl(urls.getPath(tags.SESSION_LIST), map), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            if (response.has(tags.ARR_RESULT)) {
                                JSONArray jsonArray = response.getJSONArray(tags.ARR_RESULT);
                                JSONObject object = jsonArray.getJSONObject(0);
                                if (object.has(tags.RESPONSE)) {
                                    String res = object.getString(tags.RESPONSE);
                                    if (res.equals(tags.RESPONSE_PASS)) {

                                    }
                                }
                            }
                            if (response.has(tags.ARR_SESSION_LIST)) {
                                //Log.d("IF", "FOUND");
                                JSONArray sessionArray = response.getJSONArray(tags.ARR_SESSION_LIST);
                                if (sessionList == null) {
                                    sessionList = new Hashtable<>();
                                    dbHelper.deleteSession();
                                } else {
                                    sessionList.clear();
                                    dbHelper.deleteSession();
                                }
                                for (int i = 0; i < sessionArray.length(); i++) {
                                    JSONObject object = sessionArray.getJSONObject(i);
                                    //Log.d("SESSION", object.toString());
                                    String id = null;
                                    String year = null;
                                    if (object.has(tags.SESSION_ID)) {
                                        id = object.getString(tags.SESSION_ID);
                                    }
                                    if (object.has(tags.SESSION_YEAR)) {
                                        year = object.getString(tags.SESSION_YEAR);
                                    }
                                    if (id != null && year != null) {
                                        sessionList.put(year, id);
                                        dbHelper.setSession(id, year);
                                    }
                                }

                            } else {
                                Log.d("ELSE", "NOT FOUND");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(TAG, e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
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

    private void fetchCity() {
        if(!network.isInternetAvailable()){
            Toast.makeText(this,getResources().getString(R.string.no_internet_connection),Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, urls.getUrl(urls.getPath(tags.CITY), map), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.d(TAG,response.toString());
                        List<City> cities = new ArrayList<>();
                        try {
                            if (response.has(tags.ARR_CITY)) {
                                JSONArray sessionArray = response.getJSONArray(tags.ARR_CITY);
                                for (int i = 0; i < sessionArray.length(); i++) {
                                    JSONObject object = sessionArray.getJSONObject(i);
                                    City city = new City();
                                    if (object.has(tags.CITY_ID)) {
                                        city.setId(object.getInt(tags.CITY_ID));
                                    }
                                    if (object.has(tags.CITY_NAME)) {
                                        city.setName(object.getString(tags.CITY_NAME));
                                    }
                                    cities.add(city);
                                }
                            }
                            dbHelper.deleteCity();
                            dbHelper.setCity(cities);
                        } catch (Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
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

    private void fetchInstitute() {
        if(!network.isInternetAvailable()){
            Toast.makeText(this,getResources().getString(R.string.no_internet_connection),Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        String url = urls.getUrl(urls.getPath(tags.SCHOOL_LIST), map);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<InstituteInfo> institute = new ArrayList<>();
                        if (response != null) {
                            try {
                                if (response.has(tags.ARR_INSTITUTE_ID)) {
                                    JSONArray jsonArray = response.getJSONArray(tags.ARR_INSTITUTE_ID);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        InstituteInfo getId = new InstituteInfo();
                                        if (object.has(tags.INSTITUTE_ID)) {
                                            getId.setId(object.getInt(tags.INSTITUTE_ID));
                                        }
                                        if (object.has(tags.INSTITUTE_CITY_ID)) {
                                            getId.setCityId(object.getInt(tags.INSTITUTE_CITY_ID));
                                        }
                                        if (object.has(tags.INSTITUTE_NAME)) {
                                            getId.setName(object.getString(tags.INSTITUTE_NAME));
                                        }
                                        if (object.has(tags.INSTITUTE_ACTIVE)) {
                                            getId.setActive(object.getString(tags.INSTITUTE_ACTIVE));
                                        }
                                        institute.add(getId);
                                    }
                                    dbHelper.deleteInstitute();
                                    dbHelper.setInstitute(institute);
                                }
                            } catch (Exception e) {

                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
                    }
                });
        jsonObjectRequest.setTag(TAG);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
    }

    private void showProgress() {
        progress = new ProgressDialog(LoginActivity_1.this);
        progress.setMessage("Please Wait...");
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();
    }

    private void dismissProgress() {
        if (progress != null) {
            progress.dismiss();
        }
    }
}
