package com.b2infosoft.paathshala.activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.app.Urls;

public class LoginActivity_1 extends AppCompatActivity {
    StringRequest stringRequest; // Assume this exists.
    RequestQueue requestQueue;  // Assume this exists.

    Button login,forgot_password;
    private final String TAG = LoginActivity_1.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_1);
        MyButton button = new MyButton();
        login = (Button)findViewById(R.id.login_button_login);
        login.setOnClickListener(button);
        forgot_password = (Button)findViewById(R.id.login_button_forgot_password);
        forgot_password.setOnClickListener(button);
    }
    private void loginSuccess(){
        startActivity(new Intent(this, MainActivity.class));
    }

    private void selectSession(){
        final CharSequence[] items = { "2001-2002", "2002-2003", "2003-2004","2004-2005","2005-2006" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Session");
        builder.setSingleChoiceItems(items,-1, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        AlertDialog prepare_radio_dialog = (AlertDialog) alert;
        ListView list_radio = prepare_radio_dialog.getListView();
        for (int i = 0; i < list_radio.getCount(); i++) {
            list_radio.setItemChecked(i, false);
        }
        prepare_radio_dialog.show();
    }
    private class MyButton implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.login_button_login:
                    loginSuccess();
                    break;
                case R.id.login_button_forgot_password:
                    requestBuilder();
                    break;
            }
        }
    }
    private void requestBuilder(){
        Urls urls = Urls.getInstance();
        requestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.GET, urls.getStringRequest(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getBaseContext(),response,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setTag(TAG);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(requestQueue!=null){
            requestQueue.cancelAll(TAG);
        }
    }
}
