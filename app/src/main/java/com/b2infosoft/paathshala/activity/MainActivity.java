package com.b2infosoft.paathshala.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
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
    Menu menu;
    Intent intentService;

    // private Menu optionsMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        active = Active.getInstance(this);
        tags = Tags.getInstance();
        network = Network.getInstance(this);
        intentService = new Intent(MainActivity.this, DBUpdate.class);
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

    private void setProfileImage() {
        if (active.getValue(tags.USER_PROFILE_PIC).length() > 0) {
            circularImageView.setImageBitmap(FileCache.getBitmap(active.getValue(tags.USER_PROFILE_PIC)));
        } else {
            updateImage();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    DBUpdate dbUpdate;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                if (network.isInternetAvailable()) {
//                    intentService.putExtra("MAIN",new MainActivity());
                    Intent intent = new Intent(this, DBUpdate.class);
                    startService(intent);
                    //bindService(intentService, connection, Context.BIND_AUTO_CREATE);
                    // new Update();
                    setRefreshActionButtonState(true);
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(5000);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        setRefreshActionButtonState(false);
                                        Toast.makeText(MainActivity.this,"Successfully Update",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } catch (InterruptedException e) {

                            }
                        }
                    });
                    thread.start();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
                return true;
            // case R.id.menu_refresh1:
            // setRefreshActionButtonState(true);
            // return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            DBUpdate.LocalBinder update = (DBUpdate.LocalBinder) service;
            dbUpdate = update.getServiceInstance();
            dbUpdate.registerClient(MainActivity.this);
            Toast.makeText(MainActivity.this, "Connect To Service", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(MainActivity.this, "Disconnect To Service", Toast.LENGTH_SHORT).show();
        }
    };

    public void setRefreshActionButtonState(final boolean refreshing) {
        //Toast.makeText(getApplication(), "Calling", Toast.LENGTH_SHORT).show();
        if (menu != null) {
            final MenuItem refreshItem = menu.findItem(R.id.menu_refresh);
            if (refreshItem != null) {
                if (refreshing) {
                    refreshItem.setActionView(R.layout.actionbar_indeterminate_progress);
                } else {
                    refreshItem.setActionView(null);
                }
            }
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

    @Override
    public void callBack(boolean b) {
        Toast.makeText(this, "I AM WORKING", Toast.LENGTH_SHORT).show();
        setRefreshActionButtonState(b);
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
            } else if (id == R.id.nav_exit) {
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

    private void updateImage() {
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

/*
    private class Update {
        private final String TAG = DBUpdate.class.getName();
        Tags tags = Tags.getInstance();
        Urls urls = Urls.getInstance();
        Active active;
        DBHelper dbHelper;
        Format format;
        private int total_request = 0;
        private boolean status = false;

        public Update() {
            dbHelper = new DBHelper(getApplicationContext());
            active = Active.getInstance(MainActivity.this);
            format = Format.getInstance();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        fetchInstitute();
                        Thread.sleep(500);
                        fetchCity();
                        Thread.sleep(500);
                        fetchSession();
                        Thread.sleep(500);
                        fetchStudentInfo();
                        Thread.sleep(500);
                        fetchComplaintList();
                        Thread.sleep(500);
                        fetchInstallmentData();
                        Thread.sleep(500);
                        getHolidayDetail();
                        Thread.sleep(500);
                        String arr[] = MainActivity.this.getResources().getStringArray(R.array.exam_type);
                        for (String str : arr) {
                            if (!str.equalsIgnoreCase("Select")) {
                                searchMarkSheet(str);
                                Thread.sleep(500);
                            }
                        }
                        Thread.sleep(500);
                        fetchExamList();
                        String[] session = active.getValue(tags.SESSION).split("-");
                        for (String year : session) {
                            searchYearAttendance(year);
                            Thread.sleep(500);
                        }
                        for (int i = 0; i <= 11; i++) {
                            Thread.sleep(500);
                            if (i <= 2) {
                                searchYearAttendance(session[1], i + "");
                            } else {
                                searchYearAttendance(session[0], i + "");
                            }
                        }

                    }catch (InterruptedException e){

                    }
                }
            }).start();
            active.setKey(tags.LAST_UPDATE, format.getCurrentDate());
        }

        private void fetchInstitute() {
            HashMap<String, String> map = new HashMap<>();
            String url = urls.getUrl(urls.getPath(tags.SCHOOL_LIST), map);
            requestAdd();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            requestComplete();
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
                            requestComplete();
                        }
                    });
            jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
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
            jsonObjectRequest.setTag(TAG);
            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
        }

        private void fetchCity() {
            HashMap<String, String> map = new HashMap<>();
            requestAdd();
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, urls.getUrl(urls.getPath(tags.CITY), map), null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
//                        Log.d(TAG,response.toString());
                            requestComplete();
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
                            requestComplete();
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
            MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsObjRequest);
        }

        private void fetchSession() {
            HashMap<String, String> map = new HashMap<>();
            requestAdd();
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, urls.getUrl(urls.getPath(tags.SESSION_LIST), map), null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            requestComplete();
//                        Log.d(TAG, response.toString());
                            try {
                                if (response.has(tags.ARR_SESSION_LIST)) {
                                    //Log.d("IF", "FOUND");
                                    JSONArray sessionArray = response.getJSONArray(tags.ARR_SESSION_LIST);
                                    dbHelper.deleteSession();
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
                            requestComplete();
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
            MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsObjRequest);
        }

        private void fetchStudentInfo() {
            Urls urls = Urls.getInstance();
            HashMap<String, String> map = new HashMap<>();
            map.put(tags.S_ID, active.getValue(tags.S_ID));
            map.put(tags.SESSION_ID, active.getValue(tags.SESSION_ID));
            map.put(tags.SCHOOL_ID, active.getValue(tags.SCHOOL_ID));
            requestAdd();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, urls.getUrl(urls.getPath(tags.STUDENT_INFO), map), null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            requestComplete();
                            if (response != null) {
                                try {
                                    if (response.has(tags.ARR_STUDENT_INFO)) {
                                        JSONArray jsonArray = response.getJSONArray(tags.ARR_STUDENT_INFO);
                                        JSONObject object = jsonArray.getJSONObject(0);
                                        StudentInfo info = new StudentInfo();
                                        if (object.has(tags.S_STU_PHOTO)) {
                                            info.setStudentPhoto(object.getString(tags.S_STU_PHOTO));
                                        }
                                        if (object.has(tags.S_INFO_ID)) {
                                            info.setId(Integer.parseInt(object.getString(tags.S_INFO_ID)));
                                        }
                                        if (object.has(tags.S_INFO_SID)) {
                                            info.setsId(Integer.parseInt(object.getString(tags.S_INFO_SID)));
                                        }
                                        if (object.has(tags.S_INFO_SR_NO)) {
                                            info.setSrNo(object.getInt(tags.S_INFO_SR_NO));
                                        }
                                        if (object.has(tags.S_INFO_ADMIN_DATE)) {
                                            info.setAdminDate(object.getString(tags.S_INFO_ADMIN_DATE));
                                        }
                                        if (object.has(tags.S_INFO_DOB)) {
                                            info.setDob(object.getString(tags.S_INFO_DOB));
                                        }
                                        if (object.has(tags.S_INFO_STU_NAME)) {
                                            info.setName(object.getString(tags.S_INFO_STU_NAME));
                                        }
                                        if (object.has(tags.S_INFO_MOBILE)) {
                                            info.setMobile(object.getString(tags.S_INFO_MOBILE));
                                        }
                                        if (object.has(tags.S_INFO_GENDER)) {
                                            info.setGender(object.getString(tags.S_INFO_GENDER));
                                        }
                                        if (object.has(tags.S_INFO_EMAIL)) {
                                            info.setEmail(object.getString(tags.S_INFO_EMAIL));
                                        }
                                        if (object.has(tags.S_INFO_MODE)) {
                                            info.setMode(object.getString(tags.S_INFO_MODE));
                                        }
                                        if (object.has(tags.S_INFO_CLASS)) {
                                            info.setStu_class(object.getString(tags.S_INFO_CLASS));
                                        }
                                        if (object.has(tags.S_INFO_FACULTY)) {
                                            info.setFaculty(object.getString(tags.S_INFO_FACULTY));
                                        }
                                        if (object.has(tags.S_INFO_SECTION)) {
                                            info.setSection(object.getString(tags.S_INFO_SECTION));
                                        }
                                        if (object.has(tags.S_INFO_FATHER_NAME)) {
                                            info.setfName(object.getString(tags.S_INFO_FATHER_NAME));
                                        }
                                        if (object.has(tags.S_INFO_FATHER_INCOME)) {
                                            info.setfIncome(object.getDouble(tags.S_INFO_FATHER_INCOME));
                                        }
                                        if (object.has(tags.S_INFO_PERMANENT_ADD)) {
                                            info.setPerAddress(object.getString(tags.S_INFO_PERMANENT_ADD));
                                        }
                                        if (object.has(tags.S_INFO_PARENT_MOBILE)) {
                                            info.setParentMobile(object.getString(tags.S_INFO_PARENT_MOBILE));
                                        }
                                        if (object.has(tags.S_INFO_GUARDIAN_NAME)) {
                                            info.setGuardianName(object.getString(tags.S_INFO_GUARDIAN_NAME));
                                        }
                                        if (object.has(tags.S_INFO_CORR_ADD)) {
                                            info.setCorrAddress(object.getString(tags.S_INFO_CORR_ADD));
                                        }
                                        if (object.has(tags.S_INFO_MOTHER_NAME)) {
                                            info.setmName(object.getString(tags.S_INFO_MOTHER_NAME));
                                        }
                                        if (object.has(tags.S_INFO_GUARDIAN_MOBILE)) {
                                            info.setGuardianMobile(object.getString(tags.S_INFO_GUARDIAN_MOBILE));
                                        }
                                        if (object.has(tags.S_INFO_GUARDIAN_PHONE)) {
                                            info.setGuardianPhone(object.getString(tags.S_INFO_GUARDIAN_PHONE));
                                        }
                                        if (object.has(tags.S_INFO_REMARK)) {
                                            info.setRemark(object.getString(tags.S_INFO_REMARK));
                                        }
                                        if (object.has(tags.S_INFO_IMAGE)) {
                                            String url = object.getString(tags.S_INFO_IMAGE).toString();
                                            info.setStuImage(url);
                                        }
                                        if (object.has(tags.S_SESSION_ID)) {
                                            info.setSessionId(object.getInt(tags.S_SESSION_ID));
                                        }
                                        if (object.has(tags.S_SCHOOL_ID)) {
                                            info.setSchoolId(object.getInt(tags.S_SCHOOL_ID));
                                        }
                                        if (object.has(tags.S_INFO_HOUSE)) {
                                            info.setHouse(object.getString(tags.S_INFO_HOUSE));
                                        }
                                        if (object.has(tags.S_INFO_ADMIN_TYPE)) {
                                            info.setAdminType(object.getString(tags.S_INFO_ADMIN_TYPE));
                                        }
                                        if (object.has(tags.S_INFO_CATEGORY)) {
                                            info.setCategory(object.getString(tags.S_INFO_CATEGORY));
                                        }
                                        if (object.has(tags.S_INFO_OCCUPATION)) {
                                            info.setfOccupation(object.getString(tags.S_INFO_OCCUPATION));
                                        }
                                        if (object.has(tags.S_SESSION_YEAR)) {
                                            info.setSessionYear(object.getString(tags.S_SESSION_YEAR));
                                        }
                                        if (object.has(tags.S_INFO_ORI_ADMIN_DATE)) {
                                            info.setOriAdminDate(object.getString(tags.S_INFO_ORI_ADMIN_DATE));
                                        }
                                        if (object.has(tags.S_INFO_NATIONALITY)) {
                                            info.setNationality(object.getString(tags.S_INFO_NATIONALITY));
                                        }
                                        if (object.has(tags.S_INFO_RELIGION)) {
                                            info.setReligion(object.getString(tags.S_INFO_RELIGION));
                                        }
                                        if (object.has(tags.S_INFO_HANDICAPPED)) {
                                            info.setHandicap(object.getString(tags.S_INFO_HANDICAPPED));
                                        }
                                        if (object.has(tags.S_INFO_STU_TYPE)) {
                                            info.setType(object.getString(tags.S_INFO_STU_TYPE));
                                        }

                                        if (object.has(tags.S_INFO_BPL)) {
                                            info.setBpl(object.getString(tags.S_INFO_BPL));
                                        }
                                        if (object.has(tags.S_INFO_STU_CAST)) {
                                            info.setCast(object.getString(tags.S_INFO_STU_CAST));
                                        }
                                        if (object.has(tags.S_INFO_URL)) {
                                            info.setUrl(object.getString(tags.S_INFO_URL));
                                        }
                                        dbHelper.deleteStudentInfo();
                                        dbHelper.setStudentInfo(info);
                                    }
                                } catch (Exception e) {
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, error.toString());
                            requestComplete();

                        }
                    });
            jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
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
            jsonObjectRequest.setTag(TAG);
            MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);
        }

        private void fetchComplaintList() {
            HashMap<String, String> map = new HashMap<>();
            map.put(tags.S_ID, active.getValue(tags.S_ID));
            map.put(tags.SCHOOL_ID, active.getValue(tags.SCHOOL_ID));
            String url = urls.getUrl(urls.getPath(tags.COMPLAINT_LIST), map);
            requestAdd();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            requestComplete();
                            List<ComplaintInfo> complaintInfoList = new ArrayList<>();
                            if (response != null) {
                                try {
                                    //Log.d(TAG,response+"");
                                    if (response.has(tags.ARR_COMPLAINT_DETAIL)) {
                                        JSONArray jsonArray = response.getJSONArray(tags.ARR_COMPLAINT_DETAIL);
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject object = jsonArray.getJSONObject(i);
                                            ComplaintInfo info = new ComplaintInfo();
                                            if (object.has(tags.COMP_ID)) {
                                                info.setId(object.getInt(tags.COMP_ID));
                                            }
                                            if (object.has(tags.COMP_SID)) {
                                                info.setsId(object.getInt(tags.COMP_SID));
                                            }
                                            if (object.has(tags.COMP_SCHOOL_ID)) {
                                                info.setSchoolId(object.getInt(tags.COMP_SCHOOL_ID));
                                            }
                                            if (object.has(tags.COMP_HISTORY_SUBJECT)) {
                                                info.setSubject(object.getString(tags.COMP_HISTORY_SUBJECT));
                                            }
                                            if (object.has(tags.COMP_HISTORY_DETAIL)) {
                                                info.setDetail(object.getString(tags.COMP_HISTORY_DETAIL));
                                            }
                                            if (object.has(tags.COMP_HISTORY_DATE)) {
                                                info.setCdate(object.getString(tags.COMP_HISTORY_DATE));
                                            }
                                            complaintInfoList.add(info);
                                        }
                                        dbHelper.deleteComplaint();
                                        dbHelper.setComplaint(complaintInfoList);
                                    }

                                } catch (Exception e) {
                                    Log.e(TAG, e.getMessage());
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, error.toString());
                            requestComplete();
                        }
                    });
            jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
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
            jsonObjectRequest.setTag(TAG);
            MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);
        }

        private void fetchInstallmentData() {
            Urls urls = Urls.getInstance();
            HashMap<String, String> map = new HashMap<>();
            map.put(tags.SCHOOL_ID, active.getValue(tags.SCHOOL_ID));
            map.put(tags.SESSION_ID, active.getValue(tags.SESSION_ID));
            map.put(tags.S_ID, active.getValue(tags.S_ID));
            requestAdd();
            final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, urls.getUrl(urls.getPath(tags.FEES_LEDGER), map), null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            requestComplete();
                            try {
                                if (response.has(tags.ARR_FEES_DETAILS)) {
                                    JSONArray result = response.getJSONArray(tags.ARR_FEES_DETAILS);
                                    List<FeeInstallment> installments = new ArrayList<>();
                                    for (int i = 0; i < result.length(); i++) {
                                        FeeInstallment installment = new FeeInstallment();
                                        JSONObject object = result.getJSONObject(i);
                                        if (object.has(tags.FEES_ID)) {

                                        }
                                        if (object.has(tags.FEES_NAME)) {
                                            installment.setName(object.getString(tags.FEES_NAME));
                                        }
                                        if (object.has(tags.FEES_TYPE)) {
                                            installment.setType(object.getString(tags.FEES_TYPE));
                                        }
                                        if (object.has(tags.FEES_TOTAL)) {
                                            installment.setTotal(object.getDouble(tags.FEES_TOTAL));
                                        }
                                        if (object.has(tags.FEES_DEPOSIT)) {
                                            installment.setDeposit(object.getDouble(tags.FEES_DEPOSIT));
                                        }
                                        if (object.has(tags.FEES_DISCOUNT)) {
                                            installment.setDiscount(object.getDouble(tags.FEES_DISCOUNT));
                                        }
                                        if (object.has(tags.FEES_BALANCE)) {
                                            installment.setBalance(object.getDouble(tags.FEES_BALANCE));
                                        }
                                        installments.add(installment);
                                    }
                                    dbHelper.deleteInstallments();
                                    dbHelper.setFeesInstallment(installments);
                                }
                                if (response.has(tags.ARR_FEES_DETAILS_DEPOSIT)) {
                                    JSONArray result = response.getJSONArray(tags.ARR_FEES_DETAILS_DEPOSIT);
                                    List<DepositInstallment> installments = new ArrayList<>();
                                    for (int i = 0; i < result.length(); i++) {
                                        DepositInstallment installment = new DepositInstallment();
                                        JSONObject object = result.getJSONObject(i);
                                        if (object.has(tags.FEES_ID)) {

                                        }
                                        if (object.has(tags.FEES_NAME)) {
                                            installment.setName(object.getString(tags.FEES_NAME));
                                        }
                                        if (object.has(tags.FEES_TYPE)) {
                                            installment.setType(object.getString(tags.FEES_TYPE));
                                        }
                                        if (object.has(tags.FEES_AMOUNT)) {
                                            installment.setAmount(object.getDouble(tags.FEES_AMOUNT));
                                        }
                                        if (object.has(tags.FEES_RECEIPT_NO)) {
                                            installment.setReceiptNo(object.getInt(tags.FEES_RECEIPT_NO));
                                        }
                                        if (object.has(tags.FEES_PAY_DATE)) {
                                            installment.setReceiptDate(object.getString(tags.FEES_PAY_DATE));
                                        }
                                        if (object.has(tags.FEES_MODE)) {
                                            installment.setPaymentMode(object.getString(tags.FEES_MODE));
                                        }
                                        installments.add(installment);
                                    }
                                    dbHelper.deleteDepositInstallments();
                                    dbHelper.setDepositInstallments(installments);
                                }
                            } catch (Exception e) {

                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, error.toString());
                            requestComplete();
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
            MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsObjRequest);

        }

        private void getHolidayDetail() {
            HashMap<String, String> map = new HashMap<>();
            map.put(tags.SESSION_ID, active.getValue(tags.SESSION_ID));
            map.put(tags.SCHOOL_ID, active.getValue(tags.SCHOOL_ID));
            requestAdd();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, urls.getUrl(urls.getPath(tags.Holiday), map), null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            // Log.d(TAG,response.toString());
                            requestComplete();
                            List<HolidayInfo> holidayInfos = new ArrayList<>();
                            if (response != null) {
                                try {
                                    if (response.has(tags.ARR_HOLIDAY_DETAIL)) {
                                        JSONArray jsonArray = response.getJSONArray(tags.ARR_HOLIDAY_DETAIL);
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject object = jsonArray.getJSONObject(i);
                                            HolidayInfo info = new HolidayInfo();
                                            if (object.has(tags.HOLIDAY_NAME)) {
                                                info.setName(object.getString(tags.HOLIDAY_NAME));
                                            }
                                            if (object.has(tags.HOLIDAY_DATE_FROM)) {
                                                info.setFromDate(object.getString(tags.HOLIDAY_DATE_FROM));
                                            }
                                            if (object.has(tags.HOLIDAY_DATE_TO)) {
                                                info.setToDate(object.getString(tags.HOLIDAY_DATE_TO));
                                            }
                                            holidayInfos.add(info);
                                        }
                                        dbHelper.deleteHoliday();
                                        dbHelper.setHoliday(holidayInfos);
                                    }
                                } catch (Exception e) {
                                }
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, error.toString());
                            requestComplete();
                        }
                    });
            jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
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
            jsonObjectRequest.setTag(TAG);
            MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);
        }

        private void searchMarkSheet(final String type) {
            Urls urls = Urls.getInstance();
            HashMap<String, String> map = new HashMap<>();
            map.put(tags.SCHOOL_ID, active.getValue(tags.SCHOOL_ID));
            map.put(tags.SESSION_ID, active.getValue(tags.SESSION_ID));
            map.put(tags.S_ID, active.getValue(tags.S_ID));
            map.put(tags.M_EXAM_TYPE, type);
            requestAdd();
            final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, urls.getUrl(urls.getPath(tags.MARKS_SHEET), map), null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            requestComplete();
                            //Log.d(TAG, response.toString());
                            try {
                                if (response.has(tags.ARR_MARKS_DETAIL)) {
                                    JSONArray jsonArray = response.getJSONArray(tags.ARR_MARKS_DETAIL);
                                    List<Marks> marksList = new ArrayList<>();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        Marks marks = new Marks();
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        if (object.has(tags.FEES_ID)) {
                                            marks.setId(object.getInt(tags.FEES_ID));
                                        }
                                        if (object.has(tags.M_EXAM_NAME)) {
                                            marks.setExamName(object.getString(tags.M_EXAM_NAME));
                                        }
                                        if (object.has(tags.M_SUBJECT_NAME)) {
                                            marks.setSubjectName(object.getString(tags.M_SUBJECT_NAME));
                                        }
                                        if (object.has(tags.M_T_MARKS)) {
                                            marks.setTMarks(object.getDouble(tags.M_T_MARKS));
                                        }
                                        if (object.has(tags.M_T_MARKS_OBT)) {
                                            marks.setTMarksObt(object.getDouble(tags.M_T_MARKS_OBT));
                                        }
                                        if (object.has(tags.M_P_MARKS)) {
                                            marks.setPMarks(object.getDouble(tags.M_P_MARKS));
                                        }
                                        if (object.has(tags.M_P_MARKS_OBT)) {
                                            marks.setPMarksObt(object.getDouble(tags.M_P_MARKS_OBT));
                                        }
                                        if (object.has(tags.M_ADD_IN_MARK)) {
                                            marks.setAddInMark(object.getString(tags.M_ADD_IN_MARK));
                                        }
                                        if (object.has(tags.M_ADD_IN_RES)) {
                                            marks.setAddInRes(object.getString(tags.M_ADD_IN_RES));
                                        }
                                        marks.setSearchType(type);
                                        marksList.add(marks);
                                    }
                                    dbHelper.deleteMarkSheetDetails(type);
                                    dbHelper.setMarkSheetDetails(marksList);
                                }
                                if (response.has(tags.ARR_MARK_SHEET)) {
                                    JSONArray jsonArray = response.getJSONArray(tags.ARR_MARK_SHEET);
                                    List<Result> resultList = new ArrayList<>();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        Result result = new Result();
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        if (object.has(tags.M_ID)) {
                                            result.setId(object.getInt(tags.M_ID));
                                        }
                                        if (object.has(tags.M_RESULT)) {
                                            result.setResult(object.getString(tags.M_RESULT));
                                        }
                                        if (object.has(tags.M_DIVISION)) {
                                            result.setDivision(object.getString(tags.M_DIVISION));
                                        }
                                        if (object.has(tags.M_PERCENTAGE)) {
                                            result.setPercentage(object.getDouble(tags.M_PERCENTAGE));
                                        }
                                        if (object.has(tags.M_TOT_MARKS)) {
                                            result.setTotalMarks(object.getDouble(tags.M_TOT_MARKS));
                                        }
                                        if (object.has(tags.M_TOTAL_OBT)) {
                                            result.setTotalObtain(object.getDouble(tags.M_TOTAL_OBT));
                                        }
                                        if (object.has(tags.M_MARK_SHEET_TYPE)) {
                                            result.setMarkSheetType(object.getString(tags.M_MARK_SHEET_TYPE));
                                        }
                                        result.setSearchType(type);
                                        resultList.add(result);
                                    }
                                    dbHelper.deleteMarkSheet(type);
                                    dbHelper.setMarkSheet(resultList);
                                }
                            } catch (Exception e) {
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, error.toString());
                            requestComplete();
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
            MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsObjRequest);
        }

        private void fetchExamList() {
            HashMap<String, String> map = new HashMap<>();
            map.put(tags.SESSION_ID, active.getValue(tags.SESSION_ID));
            map.put(tags.SCHOOL_ID, active.getValue(tags.SCHOOL_ID));
            requestAdd();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, urls.getUrl(urls.getPath(tags.EXAM_LIST), map), null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Log.d(TAG,response.toString());
                            requestComplete();
                            List<String> list = new ArrayList<>();
                            if (response != null) {
                                try {
                                    if (response.has(tags.ARR_EXAM_SESSION_LIST)) {
                                        JSONArray jsonArray = response.getJSONArray(tags.ARR_EXAM_SESSION_LIST);
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject object = jsonArray.getJSONObject(i);
                                            if (object.has(tags.EXAM_NAME)) {
                                                list.add(object.getString(tags.EXAM_NAME));
                                            }
                                        }
                                        dbHelper.deleteExamType();
                                        dbHelper.setExamType(list);
                                        updateTimeTable(list);
                                    }
                                } catch (Exception e) {
                                }
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, error.toString());
                            requestComplete();

                        }
                    });
            jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
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
            jsonObjectRequest.setTag(TAG);
            MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);
        }

        private void getData(final String str) {
            HashMap<String, String> map = new HashMap<>();
            map.put(tags.S_ID, active.getValue(tags.S_ID));
            map.put(tags.SESSION_ID, active.getValue(tags.SESSION_ID));
            map.put(tags.SCHOOL_ID, active.getValue(tags.SCHOOL_ID));
            map.put(tags.TIME_TABLE_EXAM_NAME, str);
            requestAdd();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, urls.getUrl(urls.getPath(tags.EXAM_TIME_TABLE), map), null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            // Log.d("response",response.toString());
                            requestComplete();
                            List<TimeTableInfo> infoList = new ArrayList<>();
                            if (response != null) {
                                try {
                                    if (response.has(tags.ARR_EXAM_TIME_TABLE)) {
                                        JSONArray jsonArray = response.getJSONArray(tags.ARR_EXAM_TIME_TABLE);
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject object = jsonArray.getJSONObject(i);
                                            TimeTableInfo info = new TimeTableInfo();
                                            if (object.has(tags.TIME_TABLE_SUBJECT_NAME)) {
                                                info.setSubject(object.getString(tags.TIME_TABLE_SUBJECT_NAME));
                                            }
                                            if (object.has(tags.TIME_TABLE_EXAM_DATE)) {
                                                info.setDate(object.getString(tags.TIME_TABLE_EXAM_DATE));
                                            }
                                            info.setExamName(str);
                                            infoList.add(info);
                                        }
                                        dbHelper.deleteTimeTable(str);
                                        dbHelper.setTimeTable(infoList);
                                    }
                                } catch (Exception e) {

                                }
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, error.toString());
                            requestComplete();
                        }
                    });
            jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
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
            jsonObjectRequest.setTag(TAG);
            MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);
        }

        private void updateTimeTable(List<String> strings) {
            for (String string : strings)
                getData(string);
        }

        private void searchYearAttendance(final String search) {
            Urls urls = Urls.getInstance();
            HashMap<String, String> map = new HashMap<>();
            map.put(tags.SCHOOL_ID, active.getValue(tags.SCHOOL_ID));
            map.put(tags.SESSION_ID, active.getValue(tags.SESSION_ID));
            map.put(tags.S_ID, active.getValue(tags.S_ID));
            map.put(tags.YEAR_YEAR, search);
            //showProgress();
            requestAdd();
            String url = urls.getUrl(urls.getPath(tags.YEAR_ATTENDANCE), map);
            final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
//                        Log.d(TAG, response.toString());
                            requestComplete();
                            try {
                                if (response.has(tags.ARR_TOTAL_YEAR_ATTENDANCE)) {
                                    JSONArray result = response.getJSONArray(tags.ARR_TOTAL_YEAR_ATTENDANCE);
                                    List<YearInfo> yearInfos = new ArrayList<>();
                                    for (int i = 0; i < result.length(); i++) {
                                        YearInfo year = new YearInfo();
                                        JSONObject object = result.getJSONObject(i);
                                        if (object.has(tags.S_ID)) {
                                            year.setId(object.getInt(tags.S_ID));
                                        }
                                        if (object.has(tags.YEAR_MONTH)) {
                                            year.setMonth(object.getString(tags.YEAR_MONTH));
                                        }
                                        if (object.has(tags.YEAR_PRESENT)) {
                                            year.setPresent(object.getInt(tags.YEAR_PRESENT));
                                        }
                                        if (object.has(tags.YEAR_ABSENT)) {
                                            year.setAbsent(object.getInt(tags.YEAR_ABSENT));
                                        }
                                        if (object.has(tags.YEAR_HALF_DAY)) {
                                            year.setHalfDay(object.getInt(tags.YEAR_HALF_DAY));
                                        }
                                        if (object.has(tags.YEAR_LEAVE)) {
                                            year.setLeave(object.getInt(tags.YEAR_LEAVE));
                                        }
                                        if (object.has(tags.YEAR_TOTAL)) {
                                            year.setTotal(object.getInt(tags.YEAR_TOTAL));
                                        }
                                        year.setYear(Integer.parseInt(search));
                                        yearInfos.add(year);
                                    }
                                    dbHelper.deleteYearAttendance(search);
                                    dbHelper.setYearAttendance(yearInfos);
                                }
                            } catch (Exception e) {
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, error.toString());
                            requestComplete();
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
            MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsObjRequest);
        }

        private void searchYearAttendance(final String year, final String month_1) {
            Urls urls = Urls.getInstance();
            HashMap<String, String> map = new HashMap<>();
            map.put(tags.SCHOOL_ID, active.getValue(tags.SCHOOL_ID));
            map.put(tags.SESSION_ID, active.getValue(tags.SESSION_ID));
            map.put(tags.S_ID, active.getValue(tags.S_ID));
            map.put(tags.YEAR_YEAR, year);
            map.put(tags.YEAR_MONTH, month_1);
            String url = urls.getUrl(urls.getPath(tags.MONTH_ATTENDANCE), map);
            requestAdd();
            final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            requestComplete();
                            MonthInfo month = new MonthInfo();
                            month.setMonth(Integer.parseInt(month_1));
                            month.setYear(Integer.parseInt(year));
                            try {
                                if (response.has(tags.ARR_TOTAL_MONTH_ATTENDANCE)) {
                                    JSONArray result = response.getJSONArray(tags.ARR_TOTAL_MONTH_ATTENDANCE);
                                    JSONObject object = result.getJSONObject(0);
                                    if (object.has(tags.ONE)) {
                                        month.setOne(object.getString(tags.ONE));
                                    }
                                    if (object.has(tags.TWO)) {
                                        month.setTwo(object.getString(tags.TWO));
                                    }
                                    if (object.has(tags.THREE)) {
                                        month.setThree(object.getString(tags.THREE));
                                    }
                                    if (object.has(tags.FOUR)) {
                                        month.setFour(object.getString(tags.FOUR));
                                    }
                                    if (object.has(tags.FIVE)) {
                                        month.setFive(object.getString(tags.FIVE));
                                    }
                                    if (object.has(tags.SIX)) {
                                        month.setSix(object.getString(tags.SIX));
                                    }
                                    if (object.has(tags.SEVEN)) {
                                        month.setSeven(object.getString(tags.SEVEN));
                                    }
                                    if (object.has(tags.EIGHT)) {
                                        month.setEight(object.getString(tags.EIGHT));
                                    }
                                    if (object.has(tags.NINE)) {
                                        month.setNine(object.getString(tags.NINE));
                                    }
                                    if (object.has(tags.TEN)) {
                                        month.setTen(object.getString(tags.TEN));
                                    }
                                    if (object.has(tags.ELEVEN)) {
                                        month.setEleven(object.getString(tags.ELEVEN));
                                    }
                                    if (object.has(tags.TWELVE)) {
                                        month.setTwelve(object.getString(tags.TWELVE));
                                    }
                                    if (object.has(tags.THIRTEEN)) {
                                        month.setThirteen(object.getString(tags.THIRTEEN));
                                    }
                                    if (object.has(tags.FOURTEEN)) {
                                        month.setFourteen(object.getString(tags.FOURTEEN));
                                    }
                                    if (object.has(tags.FIFTEEN)) {
                                        month.setFifteen(object.getString(tags.FIFTEEN));
                                    }
                                    if (object.has(tags.SIXTEEN)) {
                                        month.setSixteen(object.getString(tags.SIXTEEN));
                                    }
                                    if (object.has(tags.SEVENTEEN)) {
                                        month.setSeventeen(object.getString(tags.SEVENTEEN));
                                    }
                                    if (object.has(tags.EIGHTEEN)) {
                                        month.setEighteen(object.getString(tags.EIGHTEEN));
                                    }
                                    if (object.has(tags.NINETEEN)) {
                                        month.setNineteen(object.getString(tags.NINETEEN));
                                    }
                                    if (object.has(tags.TWENTY)) {
                                        month.setTwenty(object.getString(tags.TWENTY));
                                    }
                                    if (object.has(tags.TONE)) {
                                        month.setTOne(object.getString(tags.TONE));
                                    }
                                    if (object.has(tags.TTWO)) {
                                        month.setTTwo(object.getString(tags.TTWO));
                                    }
                                    if (object.has(tags.TThree)) {
                                        month.setTThree(object.getString(tags.TThree));
                                    }
                                    if (object.has(tags.TFOUR)) {
                                        month.setTFour(object.getString(tags.TFOUR));
                                    }
                                    if (object.has(tags.TFIVE)) {
                                        month.setTFive(object.getString(tags.TFIVE));
                                    }
                                    if (object.has(tags.TSIX)) {
                                        month.setTSix(object.getString(tags.TSIX));
                                    }
                                    if (object.has(tags.TSEVEN)) {
                                        month.setTSeven(object.getString(tags.TSEVEN));
                                    }
                                    if (object.has(tags.TEIGHT)) {
                                        month.setTEight(object.getString(tags.TEIGHT));
                                    }
                                    if (object.has(tags.TNINE)) {
                                        month.setTNine(object.getString(tags.TNINE));
                                    }
                                    if (object.has(tags.TTEN)) {
                                        month.setTTen(object.getString(tags.TTEN));
                                    }
                                    if (object.has(tags.TELEVEN)) {
                                        month.setTEleven(object.getString(tags.TELEVEN));
                                    }
                                    if (object.has(tags.MONTH_ABSENT)) {
                                        month.setAbsent(object.getInt(tags.MONTH_ABSENT));
                                    }
                                    if (object.has(tags.MONTH_PRESENT)) {
                                        month.setPresent(object.getInt(tags.MONTH_PRESENT));
                                    }
                                    if (object.has(tags.MONTH_HALF_DAY)) {
                                        month.setHalfDay(object.getInt(tags.MONTH_HALF_DAY));
                                    }
                                    if (object.has(tags.MONTH_LEAVE)) {
                                        month.setLeave(object.getInt(tags.MONTH_LEAVE));
                                    }
                                }

                            } catch (Exception e) {
                            }
                            dbHelper.deleteMonthAttendance(month.getMonth() + "", month.getYear() + "");
                            dbHelper.setMonthAttendance(month);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, error.toString());
                            requestComplete();
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
            MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsObjRequest);
        }
        private void requestAdd() {
            total_request++;
            if (!status) {
                status = true;
                setRefreshActionButtonState(status);
            }
        }

        private void requestComplete() {
            total_request--;
            if (total_request == 0) {
                status = false;
                setRefreshActionButtonState(status);
            }
        }
    }
    */
}

abstract class CallBacks extends AppCompatActivity implements Admission.OnDashboardListener, Parent.OnParentListener, Student.OnStudentListener, Guardian.OnGuardianListener, Complaint.OnComplaintListener, ChangePassword.OnChangePasswordListener, Holiday.OnHolidayFragmentListener, TimeTable.OnTimeTableListener, Attendance.OnAttendanceListener, Fees.OnFeesListener, MarkSheet.OnMarkSheetListener, Dashboard.OnAdmissionListener, DBUpdate.CallBack {

}

