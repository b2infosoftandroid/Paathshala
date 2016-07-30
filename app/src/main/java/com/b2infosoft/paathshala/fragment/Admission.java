package com.b2infosoft.paathshala.fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.adapter.TabPageAdapter;
import com.b2infosoft.paathshala.app.Fonts;
import com.b2infosoft.paathshala.fragment.dashboard.Guardian;
import com.b2infosoft.paathshala.fragment.dashboard.Parent;
import com.b2infosoft.paathshala.fragment.dashboard.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Admission.OnDashboardListener} interface
 * to handle interaction events.
 * Use the {@link Admission#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Admission extends Fragment implements ViewPager.OnPageChangeListener,TabHost.OnTabChangeListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    Fonts fonts = Fonts.getInstance();

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
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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
//            tv.setTypeface(fonts.getFont(getActivity(), fonts.ROBOTO_MEDIUM));
        }
    }

    public interface OnDashboardListener {
        void onDashboardInteraction(Uri uri);
    }
}
