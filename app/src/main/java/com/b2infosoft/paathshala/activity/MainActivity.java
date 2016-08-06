package com.b2infosoft.paathshala.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.app.FileCache;
import com.b2infosoft.paathshala.app.Tags;
import com.b2infosoft.paathshala.credential.Active;
import com.b2infosoft.paathshala.fragment.Admission;
import com.b2infosoft.paathshala.fragment.Dashboard;
import com.b2infosoft.paathshala.fragment.Attendance;
import com.b2infosoft.paathshala.fragment.ChangePassword;
import com.b2infosoft.paathshala.fragment.Complaint;
import com.b2infosoft.paathshala.fragment.Fees;
import com.b2infosoft.paathshala.fragment.Holiday;
import com.b2infosoft.paathshala.fragment.MarkSheet;
import com.b2infosoft.paathshala.fragment.TimeTable;
import com.b2infosoft.paathshala.fragment.dashboard.Guardian;
import com.b2infosoft.paathshala.fragment.dashboard.Parent;
import com.b2infosoft.paathshala.fragment.dashboard.Student;
import com.b2infosoft.paathshala.model.DummyContent;
import com.b2infosoft.paathshala.services.DBUpdate;
import com.b2infosoft.paathshala.services.Network;
import com.b2infosoft.paathshala.volly.MySingleton;
import com.mikhaellopez.circularimageview.CircularImageView;

public class MainActivity extends CallBacks {
    View headerView;
    NavigationView navigationView;
    CircularImageView circularImageView;
    TextView profile_name;
    LinearLayout nav_header_layout;
    Active active;
    Tags tags;
    Network network;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        active = Active.getInstance(this);
        tags = Tags.getInstance();
        network = Network.getInstance(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavSelect());

        headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_main, navigationView, false);
        nav_header_layout = (LinearLayout) headerView.findViewById(R.id.nav_header_layout);
        circularImageView = (CircularImageView) headerView.findViewById(R.id.circularImageView);
        circularImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new Dashboard());
                setTitle("Dashboard");
            }
        });
        profile_name = (TextView) headerView.findViewById(R.id.user_profile_name);
        profile_name.setText(active.getValue(tags.S_INFO_STU_NAME));
        setProfileImage();
        navigationView.removeHeaderView(headerView);
        navigationView.addHeaderView(headerView);
        replaceFragment(new Dashboard());
        setTitle("Dashboard");
    }
    private void setProfileImage(){
        if(active.getValue(tags.USER_PROFILE_PIC).length()>0){
            circularImageView.setImageBitmap(FileCache.getBitmap(active.getValue(tags.USER_PROFILE_PIC)));
        }else{
            updateImage();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                if(network.isInternetAvailable()) {
                    Intent intent = new Intent(this, DBUpdate.class);
                    startService(intent);
                }else{
                    Toast.makeText(this,getResources().getString(R.string.no_internet_connection),Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onGuardianInteraction(Uri uri) {

    }

    @Override
    public void onParentInteraction(Uri uri) {

    }

    @Override
    public void onStudentInteraction(Uri uri) {

    }

    @Override
    public void onDashboardInteraction(Uri uri) {

    }

    @Override
    public void onChangePasswordInteraction(Uri uri) {

    }

    @Override
    public void onComplaintInteraction(Uri uri) {

    }

    @Override
    public void onHolidayFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public void onTimeTableInteraction(Uri uri) {

    }

    @Override
    public void onAttendanceInteraction(Uri uri) {

    }

    @Override
    public void onFeesInteraction(Uri uri) {

    }

    @Override
    public void onAdmissionInteraction(Uri uri) {

    }

    @Override
    public void onMarkSheetInteraction(Uri uri) {

    }

    private class NavSelect implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.nav_admission) {
                replaceFragment(new Admission());
                setTitle("Dashboard");
            } else if (id == R.id.nav_attendance) {
                replaceFragment(new Attendance());
                setTitle("Attendance");
            } else if (id == R.id.nav_complaint) {
                replaceFragment(new Complaint());
                setTitle("Complaint");
            } else if (id == R.id.nav_time_table) {
                replaceFragment(new TimeTable());
                setTitle("Time Table");
            } else if (id == R.id.nav_change_password) {
                replaceFragment(new ChangePassword());
                setTitle("Change Password");
            } else if (id == R.id.nav_fee_ledger) {
                replaceFragment(new Fees());
                setTitle("Fees Ledger");
            } else if (id == R.id.nav_marksheet) {
                replaceFragment(new MarkSheet());
                setTitle("Marks Sheet");
            } else if (id == R.id.nav_holiday) {
                replaceFragment(new Holiday());
                setTitle("Holiday");
            } else if(id==R.id.nav_exit){
                active.setLogOut();
                startActivity(new Intent(MainActivity.this, LoginActivity_1.class));
                finish();
            }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    }

    public void replaceFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_content, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
    private void updateImage(){
        String url = active.getValue(tags.S_STU_PHOTO);
        // Retrieves an image specified by the URL, displays it in the UI.
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        //imageView.setImageBitmap(bitmap);
                        active.setKey(tags.USER_PROFILE_PIC, FileCache.encodeToBase64(bitmap));
                        setProfileImage();
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        //imageView.setImageResource(R.drawable.user);
                    }
                });
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(request);
    }
    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title.toString().toUpperCase());
    }
}
abstract class CallBacks extends AppCompatActivity implements  Admission.OnDashboardListener, Parent.OnParentListener, Student.OnStudentListener, Guardian.OnGuardianListener, Complaint.OnComplaintListener, ChangePassword.OnChangePasswordListener, Holiday.OnHolidayFragmentListener, TimeTable.OnTimeTableListener, Attendance.OnAttendanceListener ,Fees.OnFeesListener,MarkSheet.OnMarkSheetListener,Dashboard.OnAdmissionListener{

}

