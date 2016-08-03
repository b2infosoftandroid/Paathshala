package com.b2infosoft.paathshala.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.adapter.GetIdRecyclerViewAdapter;
import com.b2infosoft.paathshala.app.Tags;
import com.b2infosoft.paathshala.app.Urls;
import com.b2infosoft.paathshala.credential.Active;
import com.b2infosoft.paathshala.model.InstituteInfo;
import com.b2infosoft.paathshala.volly.MySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetInstituteId extends AppCompatActivity {

    private static String TAG=GetInstituteId.class.getName();

    private static RecyclerView recyclerView;
    private static GetIdRecyclerViewAdapter adapter;
    Tags tags= Tags.getInstance();
    Urls urls=Urls.getInstance();
    Active active;
    List<InstituteInfo> institute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_id);

        active = Active.getInstance(getApplicationContext());
        recyclerView=(RecyclerView)findViewById(R.id.recycle_view);
       // recyclerView.setHasFixedSize(false);
        fetchId();
    }

    private void fetchId(){
        HashMap<String,String> map=new HashMap<>();
        String url =urls.getUrl(urls.getPath(tags.SCHOOL_LIST),map) ;
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest
                (Request.Method.GET,url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if(institute== null){
                            institute = new ArrayList<>();
                        }
                        if(response!=null){
                            try {
                                if (response.has(tags.ARR_INSTITUTE_ID)) {
                                    JSONArray jsonArray = response.getJSONArray(tags.ARR_INSTITUTE_ID);
                                    for(int i=0;i<jsonArray.length();i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        InstituteInfo getId= new InstituteInfo();
                                        if(object.has(tags.INSTITUTE_ID)){
                                           getId.setId(object.getString(tags.INSTITUTE_ID));
                                        }
                                        if(object.has(tags.INSTITUTE_CITY_ID)){
                                            getId.setCityId(object.getString(tags.INSTITUTE_CITY_ID));
                                        }
                                        if(object.has(tags.INSTITUTE_NAME)){
                                            getId.setName(object.getString(tags.INSTITUTE_NAME));
                                        }
                                        if(object.has(tags.INSTITUTE_ACTIVE)){
                                            getId.setActive(object.getString(tags.INSTITUTE_ACTIVE));
                                        }
                                        institute.add(getId);

                                    }
                                    adapter = new GetIdRecyclerViewAdapter(institute);

                                    recyclerView.setAdapter(adapter);

                                }
                            }catch (Exception e){

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
