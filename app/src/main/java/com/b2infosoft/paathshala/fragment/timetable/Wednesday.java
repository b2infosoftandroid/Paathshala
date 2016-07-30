package com.b2infosoft.paathshala.fragment.timetable;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.adapter.TimeTableRecyclerViewAdapter;
import com.b2infosoft.paathshala.model.TimeTableInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Wednesday extends Fragment {


    public Wednesday() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wednesday, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            int mColumnCount = 1;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new TimeTableRecyclerViewAdapter(getContext(),getData()));
        }
        return view;
    }
    public List<TimeTableInfo> getData() {
        List<TimeTableInfo> infoList = new ArrayList<>();
        TimeTableInfo info = new TimeTableInfo();
        info.setSubject("Hindi");
        info.setToTime("9:30");
        info.setFormTime("10:30");
        infoList.add(info);
        info = new TimeTableInfo();
        info.setSubject("English");
        info.setToTime("9:30");
        info.setFormTime("10:30");
        infoList.add(info);
        info = new TimeTableInfo();
        info.setSubject("Math");
        info.setToTime("9:30");
        info.setFormTime("10:30");
        infoList.add(info);
        info = new TimeTableInfo();
        info.setSubject("Sanskrit");
        info.setToTime("9:30");
        info.setFormTime("10:30");
        infoList.add(info);
        info = new TimeTableInfo();
        info.setSubject("Science");
        info.setToTime("9:30");
        info.setFormTime("10:30");
        infoList.add(info);
        info = new TimeTableInfo();
        info.setSubject("General");
        info.setToTime("9:30");
        info.setFormTime("10:30");
        infoList.add(info);
        return infoList;
    }

}
