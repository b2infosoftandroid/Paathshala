package com.b2infosoft.paathshala.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.app.Tags;
import com.b2infosoft.paathshala.app.Urls;
import com.b2infosoft.paathshala.credential.Active;
import com.b2infosoft.paathshala.services.DBUpdate;
import com.b2infosoft.paathshala.services.Network;
import com.b2infosoft.paathshala.volly.MySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class ForgotPassword extends AppCompatActivity {

    private static String TAG=ForgotPassword.class.getName();
    Network network;
    Tags tags= Tags.getInstance();
    Urls urls= Urls.getInstance();
    Active active;
    EditText user_id,school_id;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        network = Network.getInstance(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        active = Active.getInstance(getApplicationContext());
        user_id = (EditText)findViewById(R.id.forgot_pass_user_id);
        school_id = (EditText)findViewById(R.id.forgot_pass_schl_id);
        b1 = (Button)findViewById(R.id.forgot_password_button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              checkBlank();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkBlank(){
        String user =  user_id.getText().toString();
        String schl =  school_id.getText().toString();
         user_id.setError(null);
        school_id.setError(null);

        if(user_id.length() == 0){
            user_id.setError("Please Enter Your Id");
            user_id.requestFocus();
            return;
        }
        if(school_id.length() == 0){
            school_id.setError("Please Enter Your School Id");
            school_id.requestFocus();
            return;
        }
        if(!network.isInternetAvailable()) {
            Toast.makeText(this,getResources().getString(R.string.no_internet_connection),Toast.LENGTH_SHORT).show();
            return;
        }
        sendPassword(user,schl);
    }

    private void sendPassword(String user,String schl){
        HashMap<String,String> map=new HashMap<>();
        map.put(tags.FORGOT_PASS_USER_ID,user);
        map.put(tags.FORGOT_PASS_SCHOOL_ID,schl);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest
                (Request.Method.GET,urls.getUrl(urls.getPath(tags.FORGOT_PASSWORD),map), null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if(response!=null){
                            try {
                                if (response.has(tags.ARR_FORGOT_PASSWORD)) {
                                    JSONArray jsonArray = response.getJSONArray(tags.ARR_FORGOT_PASSWORD);
                                    JSONObject object = jsonArray.getJSONObject(0);
                                    if(object.has(tags.FORGOT_PASS_STATUS)){
                                        String str = object.getString(tags.FORGOT_PASS_STATUS);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                                        builder.setTitle("Forgot Password");
                                        builder.setMessage(str);
                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                        builder.create().show();
                                        //Toast.makeText(getApplicationContext(),str,Toast.LENGTH_LONG).show();
                                    }

                                }
                            }catch (Exception e){
                                Log.e(TAG,e.getMessage());
                            }
                        }
                    }
                },new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
                    }
                });
        jsonObjectRequest.setTag(TAG);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
}
