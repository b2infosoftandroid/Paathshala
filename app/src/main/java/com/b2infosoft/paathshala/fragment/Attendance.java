package com.b2infosoft.paathshala.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
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
import com.b2infosoft.paathshala.adapter.ViewPagerAdapter;
import com.b2infosoft.paathshala.fragment.attendance.Month;
import com.b2infosoft.paathshala.fragment.attendance.Year;
import com.b2infosoft.paathshala.fragment.dashboard.Guardian;
import com.b2infosoft.paathshala.fragment.dashboard.Parent;
import com.b2infosoft.paathshala.fragment.dashboard.Student;

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
public class Attendance extends Fragment {
    View view;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnAttendanceListener mListener;

    private ViewPager viewPager;
    private String[] tabs = {"MONTH","YEAR"};
    private TabLayout tabLayout;

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
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new Month(), tabs[0]);
        adapter.addFragment(new Year(), tabs[1]);
        viewPager.setAdapter(adapter);
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

    public interface OnAttendanceListener {
        void onAttendanceInteraction(Uri uri);
    }

}
