package com.b2infosoft.paathshala.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.adapter.ComplaintRecyclerViewAdapter;
import com.b2infosoft.paathshala.app.Format;
import com.b2infosoft.paathshala.app.Tags;
import com.b2infosoft.paathshala.app.Urls;
import com.b2infosoft.paathshala.credential.Active;
import com.b2infosoft.paathshala.database.DBHelper;
import com.b2infosoft.paathshala.model.ComplaintInfo;
import com.b2infosoft.paathshala.volly.MySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Complaint.OnComplaintListener} interface
 * to handle interaction events.
 * Use the {@link Complaint#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Complaint extends Fragment {

    private static String TAG = Complaint.class.getName();

    Tags tags = Tags.getInstance();
    Urls urls = Urls.getInstance();
    Active active;
    DBHelper dbHelper;
    Format format;
    EditText title, body;
    Button b1, b2;
    FloatingActionButton new_complaint;
    private ProgressDialog progress = null;
    RecyclerView rv;
    List<ComplaintInfo> complaintInfoList;
    private static ComplaintRecyclerViewAdapter adapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnComplaintListener mListener;

    public Complaint() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Complaint.
     */
    // TODO: Rename and change types and number of parameters
    public static Complaint newInstance(String param1, String param2) {
        Complaint fragment = new Complaint();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complaint, container, false);
        active = Active.getInstance(getContext());
        dbHelper = new DBHelper(getActivity());
        format = Format.getInstance();
        rv = (RecyclerView) view.findViewById(R.id.complaint_recycler_view);
        new_complaint = (FloatingActionButton) view.findViewById(R.id.new_complaint_btn);
        new_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newComplaintSend();
            }
        });

        List<ComplaintInfo> infoList = dbHelper.getComplaint();
        if (infoList.size() == 0) {
            fetchComplaintList();
        } else {
            updateComplaint(infoList);
        }
        return view;
    }

    private void newComplaintSend() {

        final Dialog dialog = new Dialog(getContext());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialog);
        title = (EditText) dialog.findViewById(R.id.complaint_title);
        body = (EditText) dialog.findViewById(R.id.complaint_body);
        body.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        body.setSingleLine(false);

        b1 = (Button) dialog.findViewById(R.id.complaint_button);
        b2 = (Button) dialog.findViewById(R.id.complaint_cancel);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBlank();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void updateComplaint(List<ComplaintInfo> infoList) {
        adapter = new ComplaintRecyclerViewAdapter(infoList);
        rv.setAdapter(adapter);
    }

    private void fetchComplaintList() {
        HashMap<String, String> map = new HashMap<>();
        map.put(tags.S_ID, active.getValue(tags.S_ID));
        map.put(tags.SCHOOL_ID, active.getValue(tags.SCHOOL_ID));
        String url = urls.getUrl(urls.getPath(tags.COMPLAINT_LIST), map);
        showProgress();
        //Log.d(TAG,url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        complaintInfoList = new ArrayList<>();
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
                                    updateComplaint(complaintInfoList);
                                    dbHelper.deleteComplaint();
                                    dbHelper.setComplaint(complaintInfoList);
                                    dismissProgress();
                                }

                            } catch (Exception e) {
                                dismissProgress();
                                //Log.e(TAG,e.getMessage());
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

    private void checkBlank() {
        String s1 = title.getText().toString();
        String s2 = body.getText().toString();
        title.setError(null);
        body.setError(null);

        if (title.length() == 0) {
            title.setError("Please Enter Subject");
            title.requestFocus();
            return;
        }
        if (body.length() == 0) {
            body.setError("Please Enter Description");
            body.requestFocus();
            return;
        }
        sendData(s1, s2);
    }

    public void sendData(String s1, String s2) {
        HashMap<String, String> map = new HashMap<>();
        map.put(tags.S_ID, active.getValue(tags.S_ID));
        map.put(tags.COMP_SUBJECT, s1);
        map.put(tags.COMP_DETAILS, s2);
        map.put(tags.SCHOOL_ID, active.getValue(tags.SCHOOL_ID));
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, urls.getUrl(urls.getPath(tags.COMPLAINTS), map), null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                //Log.d(TAG,response+"");
                                if (response.has(tags.ARR_COMPLAINTS)) {
                                    JSONArray jsonArray = response.getJSONArray(tags.ARR_COMPLAINTS);
                                    JSONObject object = jsonArray.getJSONObject(0);
                                    if (object.has(tags.COMP_STATUS)) {
                                        String str = object.getString(tags.COMP_STATUS);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setTitle("Complaint");
                                        builder.setMessage(str);
                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                        builder.create().show();
                                        // Toast.makeText(getContext(),str,Toast.LENGTH_LONG).show();
                                    }

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
                    }
                });
        jsonObjectRequest.setTag(TAG);
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onComplaintInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnComplaintListener) {
            mListener = (OnComplaintListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnComplaintListener");
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
    public interface OnComplaintListener {
        // TODO: Update argument type and name
        void onComplaintInteraction(Uri uri);
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
