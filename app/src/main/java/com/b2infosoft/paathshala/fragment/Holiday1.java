package com.b2infosoft.paathshala.fragment;

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
import com.b2infosoft.paathshala.adapter.HolidayRecyclerViewAdapter;
import com.b2infosoft.paathshala.model.DummyContent.DummyItem;
import com.b2infosoft.paathshala.model.HolidayInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnHolidayFragmentListener}
 * interface.
 */
public class Holiday1 extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnHolidayFragmentListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public Holiday1() {

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static Holiday1 newInstance(int columnCount) {
        Holiday1 fragment = new Holiday1();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_holiday, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new HolidayRecyclerViewAdapter(getData()));
        }
        return view;
    }

    public List<HolidayInfo> getData() {
        int holiday_type[] = {
                R.drawable.ic_rakshabandhan,
                R.drawable.ic_holi_icon,
                R.drawable.ic_dipawali,
                R.drawable.ic_independance_day,
                R.drawable.ic_no_image_available
        };
        List<HolidayInfo> infoList = new ArrayList<>();
        HolidayInfo info = new HolidayInfo();
        info.setIcon(holiday_type[0]);
        info.setName("Rakhi");
        info.setToDate("21/07/2016");
        info.setFromDate("21/07/2016");
        infoList.add(info);
        info = new HolidayInfo();
        info.setToDate("21/07/2016");
        info.setIcon(holiday_type[1]);
        info.setName("Holi");
        infoList.add(info);
        info = new HolidayInfo();
        info.setToDate("21/07/2016");
        info.setIcon(holiday_type[2]);
        info.setName("Dipawali");
        infoList.add(info);
        info = new HolidayInfo();
        info.setToDate("21/07/2016");
        info.setIcon(holiday_type[3]);
        info.setName("Independence Day");
        infoList.add(info);
        info = new HolidayInfo();
        info.setToDate("21/07/2016");
        info.setIcon(holiday_type[4]);
        info.setName("Summer");
        infoList.add(info);

        return infoList;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnHolidayFragmentListener) {
            mListener = (OnHolidayFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnHolidayFragmentListener");
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
    public interface OnHolidayFragmentListener {
        // TODO: Update argument type and name
        void onHolidayFragmentInteraction(DummyItem item);
    }
}
