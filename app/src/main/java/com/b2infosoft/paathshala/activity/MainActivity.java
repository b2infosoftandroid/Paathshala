package com.b2infosoft.paathshala.activity;

import android.content.Intent;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.b2infosoft.paathshala.R;
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
import com.mikhaellopez.circularimageview.CircularImageView;

public class MainActivity extends CallBacks {
    View headerView;
    NavigationView navigationView;
    CircularImageView circularImageView;
    TextView profile_name;
    LinearLayout nav_header_layout;
    Active active;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        active = Active.getInstance(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavSelect());

        headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_main, navigationView, true);
        nav_header_layout = (LinearLayout) headerView.findViewById(R.id.nav_header_layout);
        nav_header_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new Admission());
                setTitle("Admission");
            }
        });
        circularImageView = (CircularImageView) headerView.findViewById(R.id.circularImageView);
        circularImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new Dashboard());
                setTitle("Admission");
            }
        });
        profile_name = (TextView) headerView.findViewById(R.id.user_profile_name);
        //profile_name.setText(Active.getValue(this, Tags.STU_NAME));
        if (circularImageView != null) {
            //Toast.makeText(this,circularImageView+"",Toast.LENGTH_SHORT).show();
        }
        replaceFragment(new Dashboard());
        setTitle("Admission");
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
                startActivity(new Intent(MainActivity.this,LoginActivity_1.class));
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
    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title.toString().toUpperCase());
    }
}
abstract class CallBacks extends AppCompatActivity implements  Admission.OnDashboardListener, Parent.OnParentListener, Student.OnStudentListener, Guardian.OnGuardianListener, Complaint.OnComplaintListener, ChangePassword.OnChangePasswordListener, Holiday.OnHolidayFragmentListener, TimeTable.OnTimeTableListener, Attendance.OnAttendanceListener ,Fees.OnFeesListener,MarkSheet.OnMarkSheetListener,Dashboard.OnAdmissionListener{

}

