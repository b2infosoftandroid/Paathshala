package com.b2infosoft.paathshala.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.adapter.HolidayRecyclerViewAdapter;
import com.b2infosoft.paathshala.app.Format;
import com.b2infosoft.paathshala.app.Tags;
import com.b2infosoft.paathshala.app.Urls;
import com.b2infosoft.paathshala.credential.Active;
import com.b2infosoft.paathshala.database.DBHelper;
import com.b2infosoft.paathshala.model.DummyContent;
import com.b2infosoft.paathshala.model.DummyContent.DummyItem;
import com.b2infosoft.paathshala.model.HolidayInfo;
import com.b2infosoft.paathshala.model.TimeTableInfo;
import com.b2infosoft.paathshala.services.Network;
import com.b2infosoft.paathshala.volly.MySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Holiday extends Fragment {
    private static String TAG = Holiday.class.getName();
    private OnHolidayFragmentListener mListener;
    Network network;
    TableLayout t1;
    Format format = Format.getInstance();
    TextView name, from_date, to_date;
    Tags tags = Tags.getInstance();
    Urls urls = Urls.getInstance();
    Active active;
    DBHelper dbHelper;
    List<HolidayInfo> holidayInfos;
    private ProgressDialog progress;

    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public Holiday() {

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static Holiday newInstance(int columnCount) {
        Holiday fragment = new Holiday();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dbHelper = new DBHelper(getActivity());
        network = Network.getInstance(getActivity());
        View view = inflater.inflate(R.layout.fragment_holiday, container, false);
        active = Active.getInstance(getContext());
        t1 = (TableLayout) view.findViewById(R.id.holiday_table);
        List<HolidayInfo> infoList = dbHelper.getHoliday();
        if(infoList.size()==0) {
            getHolidayDetail();
        }else{
            addData(infoList);
        }
        return view;
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

    public interface OnHolidayFragmentListener {
        // TODO: Update argument type and name
        void onHolidayFragmentInteraction(DummyItem item);
    }

    private void getHolidayDetail() {
        if(!network.isInternetAvailable()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put(tags.SESSION_ID, active.getValue(tags.SESSION_ID));
        map.put(tags.SCHOOL_ID, active.getValue(tags.SCHOOL_ID));
        showProgress();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, urls.getUrl(urls.getPath(tags.Holiday), map), null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // Log.d(TAG,response.toString());
                        if (holidayInfos == null) {
                            holidayInfos = new ArrayList<>();
                        }
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
                                    addData(holidayInfos);
                                    dbHelper.deleteHoliday();
                                    dbHelper.setHoliday(holidayInfos);
                                    // TimeTableAdapter listAdapter= new TimeTableAdapter(getContext(),infoList);
                                    // examdetail.setAdapter(listAdapter);
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
                        dismissProgress();
                    }
                });
        jsonObjectRequest.setTag(TAG);
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void addData(List<HolidayInfo> holidayInfos) {
        if (t1.getChildCount() > 0) {
            t1.removeAllViews();
        }
        TableRow tr_head = new TableRow(getContext());
        tr_head.setBackgroundColor(getResources().getColor(R.color.table_head_row));
        name = new TextView(getContext());
        name.setText("HOLIDAY NAME");
        name.setTextColor(getResources().getColor(R.color.app_background));
        name.setPadding(30, 30, 30, 30);
        name.setTypeface(null, Typeface.BOLD);
        tr_head.addView(name);


        from_date = new TextView(getContext());
        from_date.setText("DATE");
        from_date.setTextColor(getResources().getColor(R.color.app_background));
        from_date.setPadding(30, 30, 30, 30);
        from_date.setGravity(Gravity.CENTER);
        from_date.setTypeface(null,Typeface.BOLD);
        tr_head.addView(from_date);

        //to_date = new TextView(getContext());
        //to_date.setText("TO DATE");
        //to_date.setTextColor(getResources().getColor(R.color.app_background));
        //to_date.setPadding(30, 30, 30, 30);
        //tr_head.addView(to_date);
        //to_date.setTypeface(null,Typeface.BOLD);
        t1.addView(tr_head);

        for (int i = 0; i < holidayInfos.size(); i++) {
            HolidayInfo info = holidayInfos.get(i);
            TableRow tr1 = new TableRow(getContext());
            if (i % 2 != 0)
                tr1.setBackgroundColor(getResources().getColor(R.color.complaint_card_1));
            else
                tr1.setBackgroundColor(getResources().getColor(R.color.complaint_card_2));

            name = new TextView(getContext());
            name.setText(info.getName() + System.getProperty("line.separator"));
            name.setMaxWidth(200);
            name.setAllCaps(true);
            //name.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
            name.setTextColor(getResources().getColor(R.color.app_background));
            name.setPadding(30, 30, 30, 0);
            tr1.addView(name);

            from_date = new TextView(getContext());
            from_date.setText(format.getDate(info.getFromDate()) + " TO " + format.getDate(info.getToDate()));
            from_date.setTextColor(getResources().getColor(R.color.app_background));
            from_date.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
            //from_date.setPadding(30, 0, 0, 0);
            tr1.addView(from_date);

            //to_date = new TextView(getContext());
           // to_date.setText(format.getDate(info.getToDate()));
           // to_date.setTextColor(getResources().getColor(R.color.app_background));
           // to_date.setPadding(30, 30, 30, 30);
           // tr1.addView(to_date);

            t1.addView(tr1);

        }
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
    private void textDivide(){
        HolidayInfo info = new HolidayInfo();
        if(info.getName().length() == 14){
            System.getProperty("line.separator");
        }
    }
}
