package com.b2infosoft.paathshala.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.adapter.TabPageAdapter;
import com.b2infosoft.paathshala.app.Fonts;
import com.b2infosoft.paathshala.app.Tags;
import com.b2infosoft.paathshala.app.Urls;
import com.b2infosoft.paathshala.credential.Active;
import com.b2infosoft.paathshala.database.DBHelper;
import com.b2infosoft.paathshala.fragment.dashboard.Guardian;
import com.b2infosoft.paathshala.fragment.dashboard.Parent;
import com.b2infosoft.paathshala.fragment.dashboard.Student;
import com.b2infosoft.paathshala.model.StudentInfo;
import com.b2infosoft.paathshala.volly.MySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Admission.OnDashboardListener} interface
 * to handle interaction events.
 * Use the {@link Admission#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Admission extends Fragment implements ViewPager.OnPageChangeListener, TabHost.OnTabChangeListener {
    Fonts fonts = Fonts.getInstance();
    Active active;
    Tags tags;
    DBHelper dbHelper;
    private final static String TAG = Admission.class.getName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ViewPager viewPager;
    private TabHost tabHost;
    private TabPageAdapter tabPageAdapter;
    private String[] tabs = {"STUDENT", "PARENTS", "GUARDIANS"};
    private List<Fragment> fragmentList;
    private ProgressDialog progress;


    private OnDashboardListener mListener;

    public Admission() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Admission.
     */

    // TODO: Rename and change types and number of parameters
    public static Admission newInstance(String param1, String param2) {
        Admission fragment = new Admission();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        active = Active.getInstance(getActivity());
        tags = Tags.getInstance();
        dbHelper = new DBHelper(getActivity());

        final View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        fragmentList = new ArrayList<>();
        fragmentList.add(new Student());
        fragmentList.add(new Parent());
        fragmentList.add(new Guardian());

        tabHost = (TabHost) view.findViewById(android.R.id.tabhost);
        tabHost.setup();
        for (String tab : tabs) {
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(tab);
            tabSpec.setIndicator(tab);
            tabSpec.setContent(new TabHost.TabContentFactory() {
                @Override
                public View createTabContent(String tag) {
                    View view1 = new View(getActivity().getApplicationContext());
                    view1.setMinimumHeight(0);
                    view1.setMinimumWidth(0);
                    return view1;
                }
            });
            tabHost.addTab(tabSpec);
        }
        tabHost.setOnTabChangedListener(this);
        tabPageAdapter = new TabPageAdapter(getChildFragmentManager(), fragmentList);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        viewPager.setAdapter(tabPageAdapter);
        viewPager.setOnPageChangeListener(this);
        viewPager.setCurrentItem(0);
        tabHost.setCurrentTab(0);
        setSelectedTabColor();
        StudentInfo info = dbHelper.getStudentInfo();
        if(info==null) {
            fetchStudentInfo();
        }else{
            Toast.makeText(getContext(),"UPDATE",Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onDashboardInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDashboardListener) {
            mListener = (OnDashboardListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDashboardListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tabHost.setCurrentTab(position);
        setSelectedTabColor();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String tabId) {
        int selected_item = tabHost.getCurrentTab();
        viewPager.setCurrentItem(selected_item);
    }

    private void setSelectedTabColor() {
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
            tv.setTextColor(Color.parseColor("#ffffff"));
            //tv.setTextSize(10);
            // tv.setTypeface(fonts.getFont(getActivity(), fonts.ROBOTO_MEDIUM));
        }
    }

    public interface OnDashboardListener {
        void onDashboardInteraction(Uri uri);
    }
    private void fetchStudentInfo() {
        Urls urls = Urls.getInstance();
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
                                        active.setKey(tags.S_INFO_STU_NAME, object.getString(tags.S_INFO_STU_NAME));
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
                                        info.setPerAddress(object.getString(tags.S_INFO_FATHER_NAME));
                                    }
                                    if (object.has(tags.S_INFO_FATHER_INCOME)) {
                                        info.setfIncome(object.getDouble(tags.S_INFO_FATHER_INCOME));
                                    }
                                    if (object.has(tags.S_INFO_PERMANENT_ADD)) {
                                        info.setPerAddress(object.getString(tags.S_INFO_PERMANENT_ADD));
                                    }
                                    if (object.has(tags.S_INFO_PARENT_MOBILE)) {
                                        info.setPerAddress(object.getString(tags.S_INFO_PARENT_MOBILE));
                                    }
                                    if (object.has(tags.S_INFO_GUARDIAN_NAME)) {
                                        info.setGuardianName(object.getString(tags.S_INFO_GUARDIAN_NAME));
                                    }
                                    if (object.has(tags.S_INFO_CORR_ADD)) {
                                        info.setCorrAddress(object.getString(tags.S_INFO_CORR_ADD));
                                    }
                                    if (object.has(tags.S_INFO_MOTHER_NAME)) {
                                        info.setmName(object.getString(tags.S_INFO_MOTHER_NAME));
                                    }if (object.has(tags.S_INFO_GUARDIAN_MOBILE)) {
                                        info.setGuardianMobile(object.getString(tags.S_INFO_GUARDIAN_MOBILE));
                                    }if (object.has(tags.S_INFO_GUARDIAN_PHONE)) {
                                        info.setGuardianPhone(object.getString(tags.S_INFO_GUARDIAN_PHONE));
                                    }if (object.has(tags.S_INFO_REMARK)) {
                                        info.setRemark(object.getString(tags.S_INFO_REMARK));
                                    }
                                    if (object.has(tags.S_INFO_IMAGE)) {
                                        String url = object.getString(tags.S_INFO_IMAGE).toString();
                                        active.setKey(tags.S_INFO_IMAGE, url);
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

                                dismissProgress();
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
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }
    private void showProgress() {
        progress = new ProgressDialog(getActivity());
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
