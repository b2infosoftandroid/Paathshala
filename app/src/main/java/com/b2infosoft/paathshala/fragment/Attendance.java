package com.b2infosoft.paathshala.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.adapter.AttendanceCalenderAdapter;
import com.b2infosoft.paathshala.adapter.TabPageAdapter;
import com.b2infosoft.paathshala.fragment.attendance.Month;
import com.b2infosoft.paathshala.fragment.attendance.Year;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Attendance.OnAttendanceListener} interface
 * to handle interaction events.
 * Use the {@link Attendance#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Attendance extends Fragment implements ViewPager.OnPageChangeListener,TabHost.OnTabChangeListener{
    View view;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnAttendanceListener mListener;

    private ViewPager viewPager;
    private TabHost tabHost;
    private TabPageAdapter tabPageAdapter;
    private String[] tabs = {"MONTH","YEAR"};
    private List<Fragment> fragmentList;


    public Attendance() {
        // Required empty public constructor
    }
    public static Attendance newInstance(String param1, String param2) {
        Attendance fragment = new Attendance();
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
        view = inflater.inflate(R.layout.fragment_attendance, container, false);
        fragmentList = new ArrayList<>();
        fragmentList.add(new Month());
        fragmentList.add(new Year());

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


        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onAttendanceInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAttendanceListener) {
            mListener = (OnAttendanceListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
    public interface OnAttendanceListener {
        void onAttendanceInteraction(Uri uri);
    }

}
