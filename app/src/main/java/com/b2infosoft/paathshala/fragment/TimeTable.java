package com.b2infosoft.paathshala.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.fragment.timetable.Friday;
import com.b2infosoft.paathshala.fragment.timetable.Monday;
import com.b2infosoft.paathshala.fragment.timetable.Saturday;
import com.b2infosoft.paathshala.fragment.timetable.Sunday;
import com.b2infosoft.paathshala.fragment.timetable.Thursday;
import com.b2infosoft.paathshala.fragment.timetable.Wednesday;

public class TimeTable extends Fragment {
    private OnTimeTableListener mListener;

    public TimeTable() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_table, container, false);

        viewPager = (ViewPager)view.findViewById(R.id.viewPager);

        viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onTimeTableInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTimeTableListener) {
            mListener = (OnTimeTableListener) context;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnTimeTableListener {
        // TODO: Update argument type and name
        void onTimeTableInteraction(Uri uri);
    }
    private class ViewPagerAdapter extends FragmentPagerAdapter{
        final int PAGE_COUNT = 7;
        // Tab Titles
        private String tabtitles[] = new String[] { "Monday", "Tuesday", "Wednesday","Thursday","Friday","Saturday","Sunday"};
        Context context;
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return  new Monday();
                case 1:
                    return  new Thursday();
                case 2:
                    return  new Wednesday();
                case 3:
                    return  new Thursday();
                case 4:
                    return  new Friday();
                case 5:
                    return  new Saturday();
                case 6:
                    return  new Sunday();
            }
            return null;
        }
        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return tabtitles[position];
        }
    }
}
