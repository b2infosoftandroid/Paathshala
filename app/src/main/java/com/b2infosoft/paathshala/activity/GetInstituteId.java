package com.b2infosoft.paathshala.activity;

import android.app.ProgressDialog;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.adapter.GetIdRecyclerViewAdapter_1;
import com.b2infosoft.paathshala.app.Tags;
import com.b2infosoft.paathshala.app.Urls;
import com.b2infosoft.paathshala.credential.Active;
import com.b2infosoft.paathshala.database.DBHelper;
import com.b2infosoft.paathshala.model.InstituteInfo;
import java.util.List;

public class GetInstituteId extends AppCompatActivity {

    private static String TAG = GetInstituteId.class.getName();
    private DBHelper dbHelper;
    private static RecyclerView recyclerView;
    Tags tags = Tags.getInstance();
    Urls urls = Urls.getInstance();
    Active active;
    private ProgressDialog progress = null;
    List<InstituteInfo> institute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_institute_id);
        dbHelper = new DBHelper(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_1);
        Spinner spinner = (Spinner) toolbar.findViewById(R.id.choose_city_spinner);
        setSpinnerData(spinner);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        active = Active.getInstance(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
    }

    private void updateInstitute(List<InstituteInfo> infoList) {
        GetIdRecyclerViewAdapter_1 adapter = new GetIdRecyclerViewAdapter_1(infoList);
        recyclerView.setAdapter(adapter);
    }

    public void setSpinnerData(final Spinner spinner) {
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
