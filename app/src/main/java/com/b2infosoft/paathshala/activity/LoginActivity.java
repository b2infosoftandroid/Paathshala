package com.b2infosoft.paathshala.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.b2infosoft.paathshala.Custom.MyProgressDialog;
import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.app.Fonts;
import com.b2infosoft.paathshala.app.Tags;
import com.b2infosoft.paathshala.model.Login;
import com.b2infosoft.paathshala.server.Server;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    Tags tags = Tags.getInstance();
    private static final String Tag = LoginActivity.class.getName();
    EditText username, role, dob, password;
    Button login;
    TextView forgot_password;
    Fonts fonts = Fonts.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        boolean device = false;
        if(device){
            loginSuccess();
        }
        username = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);
        dob = (EditText) findViewById(R.id.login_dob);
        role = (EditText) findViewById(R.id.login_role);

        login=(Button)findViewById(R.id.login_button);
        forgot_password=(TextView)findViewById(R.id.login_forgot_password);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginSuccess();
            }
        });
    }
    private void attemptLogin(){
        String mUserName = username.getText().toString();
        String mUserPassword = password.getText().toString();
        String mUserDob = dob.getText().toString();
        String mUserRole = role.getText().toString();

        username.setError(null);
        password.setError(null);
        dob.setError(null);
        role.setError(null);

        if(mUserName.length()==0){
            username.setError("Invalid username");
            username.requestFocus();
            return;
        }if(mUserPassword.length()==0){
            password.setError("Invalid password");
            password.requestFocus();
            return;
        }if(mUserDob.length()==0){
            dob.setError("Invalid DOB");
            dob.requestFocus();
            return;
        }if(mUserRole.length()==0){
            role.setError("Invalid role");
            role.requestFocus();
            return;
        }
        Login login = new Login();
        login.setId(mUserName);
        login.setPassword(mUserPassword);
        login.setSession(mUserDob);
        login.setRole(mUserRole);
        new LoginAttempt(login).execute();
    }
    private void loginSuccess(){
        startActivity(new Intent(this,MainActivity.class));
    }
    private void setFonts() {
        username.setTypeface(fonts.getFont(this, fonts.ROBOTO_REGULAR));
        password.setTypeface(fonts.getFont(this, fonts.ROBOTO_REGULAR));
        dob.setTypeface(fonts.getFont(this, fonts.ROBOTO_REGULAR));
        role.setTypeface(fonts.getFont(this, fonts.ROBOTO_REGULAR));
        login.setTypeface(fonts.getFont(this, fonts.ROBOTO_MEDIUM));
        forgot_password.setTypeface(fonts.getFont(this, fonts.ROBOTO_MEDIUM));
    }
    private class LoginAttempt extends AsyncTask<String,String,String>{
        private Login login;

        public LoginAttempt(Login login) {
            this.login = login;
        }
        MyProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            dialog = new MyProgressDialog(LoginActivity.this);
            dialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(Tag,s);
            if(dialog!=null){
                dialog.dismiss();
            }
        }
        @Override
        protected String doInBackground(String... params) {
            //TODO: LoginActivity add login web service URL
            Server server = new Server("");
            HashMap<String,String> map = new HashMap<>();

            map.put(tags.USER_ID,login.getId());
            map.put(tags.USER_PASSWORD,login.getPassword());
            map.put(tags.USER_ROLE,login.getRole());
            map.put(tags.USER_SESSION,login.getSession());
            try {
               return  server.doPost(map);
            }catch (Exception e){
                e.printStackTrace();
                Log.d(Tag,e.getMessage());
            }
            return null;
        }
    }
}
