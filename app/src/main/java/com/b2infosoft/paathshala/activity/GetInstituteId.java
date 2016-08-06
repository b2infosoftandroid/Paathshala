package com.b2infosoft.paathshala.activity;

import android.app.ProgressDialog;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.adapter.GetIdRecyclerViewAdapter;
import com.b2infosoft.paathshala.adapter.GetIdRecyclerViewAdapter_1;
import com.b2infosoft.paathshala.app.Tags;
import com.b2infosoft.paathshala.app.Urls;
import com.b2infosoft.paathshala.credential.Active;
import com.b2infosoft.paathshala.database.DBHelper;
import com.b2infosoft.paathshala.model.InstituteInfo;
import com.b2infosoft.paathshala.volly.MySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetInstituteId extends AppCompatActivity {

    private static String TAG = GetInstituteId.class.getName();
    private DBHelper dbHelper;
    private static RecyclerView recyclerView;
    private static GetIdRecyclerViewAdapter adapter;
    Tags tags = Tags.getInstance();
    Urls urls = Urls.getInstance();
    Active active;
    private ProgressDialog progress = null;
    List<InstituteInfo> institute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_get_id);
        dbHelper = new DBHelper(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        active = Active.getInstance(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        //fetchId();
    }
    private void updateInstitute(List<InstituteInfo> infoList){
        GetIdRecyclerViewAdapter_1 adapter = new GetIdRecyclerViewAdapter_1(infoList);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_institute, menu);
        MenuItem item = menu.findItem(R.id.menu_city_spinner);
        final Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        List<String> strings = dbHelper.getCityName();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, strings);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String city = spinner.getSelectedItem().toString();
                List<InstituteInfo> infoList = dbHelper.getInstitute(city);
                updateInstitute(infoList);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void searchData(String city) {

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

    private void fetchId() {
        HashMap<String, String> map = new HashMap<>();
        String url = urls.getUrl(urls.getPath(tags.SCHOOL_LIST), map);
        showProgress();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        institute = new ArrayList<>();
                        if (response != null) {
                            try {
                                if (response.has(tags.ARR_INSTITUTE_ID)) {
                                    JSONArray jsonArray = response.getJSONArray(tags.ARR_INSTITUTE_ID);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        InstituteInfo getinfo = new InstituteInfo();
                                        if (object.has(tags.INSTITUTE_ID)) {
                                            getinfo.setId(object.getInt(tags.INSTITUTE_ID));
                                        }
                                        if (object.has(tags.INSTITUTE_CITY_ID)) {
                                            getinfo.setCityId(object.getInt(tags.INSTITUTE_CITY_ID));
                                        }
                                        if (object.has(tags.INSTITUTE_NAME)) {
                                            getinfo.setName(object.getString(tags.INSTITUTE_NAME));
                                        }
                                        if (object.has(tags.INSTITUTE_ACTIVE)) {
                                            getinfo.setActive(object.getString(tags.INSTITUTE_ACTIVE));
                                        }
                                        institute.add(getinfo);

                                    }
                                    adapter = new GetIdRecyclerViewAdapter(institute);
                                    recyclerView.setAdapter(adapter);
                                    dismissProgress();
                                }

                            } catch (Exception e) {
                                dismissProgress();
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

    private void showProgress() {
        progress = new ProgressDialog(this);
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
