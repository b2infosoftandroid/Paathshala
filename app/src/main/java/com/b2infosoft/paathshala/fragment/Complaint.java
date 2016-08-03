package com.b2infosoft.paathshala.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.b2infosoft.paathshala.R;
import com.b2infosoft.paathshala.app.Tags;
import com.b2infosoft.paathshala.app.Urls;
import com.b2infosoft.paathshala.credential.Active;
import com.b2infosoft.paathshala.volly.MySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Complaint.OnComplaintListener} interface
 * to handle interaction events.
 * Use the {@link Complaint#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Complaint extends Fragment {

    private static String TAG=Complaint.class.getName();

    Tags tags= Tags.getInstance();
    Urls urls= Urls.getInstance();
    Active active;
    EditText title,body;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
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

        title = (EditText)view.findViewById(R.id.complaint_title);
        body = (EditText)view.findViewById(R.id.complaint_body);
       String s1 = title.getText().toString();
       String s2 = body.getText().toString();
        sendData(s1,s2);
        return  view;
    }

    public void sendData(String s1,String s2){
        HashMap<String,String> map=new HashMap<>();
        map.put(tags.S_ID,active.getValue(tags.S_ID));
        map.put(tags.COMP_SUBJECT,s1);
        map.put(tags.COMP_DETAILS,s2);
        map.put(tags.SCHOOL_ID,active.getValue(tags.SCHOOL_ID));
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest
                (Request.Method.GET,urls.getUrl(urls.getPath(tags.COMPLAINTS),map), null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if(response!=null){
                            try {
                                if (response.has(tags.ARR_COMPLAINTS)) {
                                    JSONArray jsonArray = response.getJSONArray(tags.ARR_COMPLAINTS);
                                    for(int i=0;i<jsonArray.length();i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        if(object.has(tags.COMP_STATUS)){
                                         String str = object.getString(tags.COMP_STATUS);
                                            Toast.makeText(getContext(),str,Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            }catch (Exception e){

                            }
                        }
                    }
                },new Response.ErrorListener() {

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
}
