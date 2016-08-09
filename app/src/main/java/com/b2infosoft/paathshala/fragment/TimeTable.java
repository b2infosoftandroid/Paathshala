package com.b2infosoft.paathshala.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.adapter.TimeTableAdapter;
import com.b2infosoft.paathshala.app.Format;
import com.b2infosoft.paathshala.app.Tags;
import com.b2infosoft.paathshala.app.Urls;
import com.b2infosoft.paathshala.credential.Active;
import com.b2infosoft.paathshala.database.DBHelper;
import com.b2infosoft.paathshala.fragment.timetable.Friday;
import com.b2infosoft.paathshala.fragment.timetable.Monday;
import com.b2infosoft.paathshala.fragment.timetable.Saturday;
import com.b2infosoft.paathshala.fragment.timetable.Sunday;
import com.b2infosoft.paathshala.fragment.timetable.Thursday;
import com.b2infosoft.paathshala.fragment.timetable.Wednesday;
import com.b2infosoft.paathshala.model.TimeTableInfo;
import com.b2infosoft.paathshala.services.Network;
import com.b2infosoft.paathshala.volly.MySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TimeTable extends Fragment {

    private static String TAG = TimeTable.class.getName();
    Network network;
    private OnTimeTableListener mListener;
    TableLayout t1;
    TextView exam_list, sub_name, sub_exm_date;
    Spinner spinner;
    Format format = Format.getInstance();
    ListView examdetail;
    Tags tags = Tags.getInstance();
    Urls urls = Urls.getInstance();
    Active active;
    List<String> ExamType;
    List<TimeTableInfo> infoList;
    private DBHelper dbHelper;
    private ProgressDialog progress;
    float textSize;

    public TimeTable() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_table, container, false);
        network = Network.getInstance(getActivity());
        dbHelper = new DBHelper(getActivity());
        active = Active.getInstance(getContext());
        exam_list = (TextView) view.findViewById(R.id.exam_type);

        spinner = (Spinner) view.findViewById(R.id.exam_list_spinner);
        t1 = (TableLayout) view.findViewById(R.id.time_table_list);
        List<String> stringList = dbHelper.getExamType();
        if (stringList.size() == 0) {
            fetchExamList();
        } else {
            updateExamType(stringList);
        }
        // viewPager = (ViewPager)view.findViewById(R.id.viewPager);

        //   viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));

        return view;
    }

    private void updateExamType(List<String> stringList) {
        spinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, stringList));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getContext(),"success",Toast.LENGTH_LONG).show();
                String value = spinner.getSelectedItem().toString();
                List<TimeTableInfo> infoList = dbHelper.getTimeTable(value);
                if (infoList.size() == 0) {
                    getData(value);
                } else {
                    addData(infoList);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        void onTimeTableInteraction(Uri uri);
    }

    private void fetchExamList() {
        if (!network.isInternetAvailable()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put(tags.SESSION_ID, active.getValue(tags.SESSION_ID));
        map.put(tags.SCHOOL_ID, active.getValue(tags.SCHOOL_ID));
        showProgress();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, urls.getUrl(urls.getPath(tags.EXAM_LIST), map), null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // Log.d(TAG,response.toString());
                        if (ExamType == null) {
                            ExamType = new ArrayList<>();
                        }
                        if (response != null) {
                            try {
                                if (response.has(tags.ARR_EXAM_SESSION_LIST)) {
                                    JSONArray jsonArray = response.getJSONArray(tags.ARR_EXAM_SESSION_LIST);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        String examname = "";
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        if (object.has(tags.EXAM_NAME)) {
                                            examname = object.getString(tags.EXAM_NAME);
                                        }
                                        ExamType.add(examname);
                                    }
                                    updateExamType(ExamType);
                                    dbHelper.deleteExamType();
                                    dbHelper.setExamType(ExamType);
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


    public void getData(final String str) {
        if(!network.isInternetAvailable()) {
            Toast.makeText(getActivity(),getResources().getString(R.string.no_internet_connection),Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put(tags.S_ID, active.getValue(tags.S_ID));
        map.put(tags.SESSION_ID, active.getValue(tags.SESSION_ID));
        map.put(tags.SCHOOL_ID, active.getValue(tags.SCHOOL_ID));
        map.put(tags.TIME_TABLE_EXAM_NAME, str);
        showProgress();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, urls.getUrl(urls.getPath(tags.EXAM_TIME_TABLE), map), null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // Log.d("response",response.toString());
                        infoList = new ArrayList<>();

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
                                    addData(infoList);
                                    dbHelper.deleteTimeTable(str);
                                    dbHelper.setTimeTable(infoList);
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
                    }
                });
        jsonObjectRequest.setTag(TAG);
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void addData(List<TimeTableInfo> infolist) {
        if (t1.getChildCount() > 0) {
            t1.removeAllViews();
        }
        TableRow tr_head = new TableRow(getContext());
        tr_head.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        sub_name = new TextView(getContext());
        sub_name.setText("SUBJECT NAME");
        sub_name.setTextColor(getResources().getColor(R.color.app_background));
        sub_name.setPadding(30, 30, 30, 30);
        sub_name.setTypeface(null, Typeface.BOLD);
        tr_head.addView(sub_name);


        sub_exm_date = new TextView(getContext());
        sub_exm_date.setText("EXAM DATE & TIME");
        sub_exm_date.setTextColor(getResources().getColor(R.color.app_background));
        sub_exm_date.setPadding(30, 30, 30, 30);
        //sub_exm_date.setGravity(Gravity.CENTER);
        sub_exm_date.setTypeface(null, Typeface.BOLD);
        tr_head.addView(sub_exm_date);
        t1.addView(tr_head);

        for (int i = 0; i < infolist.size(); i++) {
            TimeTableInfo info = infolist.get(i);
            TableRow tr1 = new TableRow(getContext());
            if (i % 2 != 0)
                tr1.setBackgroundColor(getResources().getColor(R.color.not_in_current_month_date));

            sub_name = new TextView(getContext());
            sub_name.setText(info.getSubject());
            sub_name.setTextColor(getResources().getColor(R.color.colorAccent));
            sub_name.setPadding(30, 30, 30, 30);
            // sub_name.setGravity(Gravity.CENTER);
            tr1.addView(sub_name);


            sub_exm_date = new TextView(getContext());
            sub_exm_date.setText(format.getDate(info.getDate()));
            sub_exm_date.setAllCaps(true);
            sub_exm_date.setTextColor(getResources().getColor(R.color.colorAccent));
            sub_exm_date.setPadding(30, 30, 30, 30);
            tr1.addView(sub_exm_date);

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
}
