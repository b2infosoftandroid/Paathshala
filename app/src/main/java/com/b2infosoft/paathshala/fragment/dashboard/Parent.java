package com.b2infosoft.paathshala.fragment.dashboard;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.b2infosoft.paathshala.app.Fonts;
import com.b2infosoft.paathshala.app.Tags;
import com.b2infosoft.paathshala.app.Urls;
import com.b2infosoft.paathshala.credential.Active;
import com.b2infosoft.paathshala.database.DBHelper;
import com.b2infosoft.paathshala.model.StudentInfo;
import com.b2infosoft.paathshala.services.Network;
import com.b2infosoft.paathshala.volly.MySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Parent.OnParentListener} interface
 * to handle interaction events.
 * Use the {@link Parent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Parent extends Fragment {

    private static String TAG = Parent.class.getName();

    EditText f_name, m_name, f_occupation, f_income, mobile, address;
    Fonts fonts = Fonts.getInstance();
    Tags tags = Tags.getInstance();
    Urls urls = Urls.getInstance();
    Active active;
    DBHelper dbHelper;
    Network network;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnParentListener mListener;

    public Parent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Parent.
     */
    // TODO: Rename and change types and number of parameters
    public static Parent newInstance(String param1, String param2) {
        Parent fragment = new Parent();
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
        active = Active.getInstance(getContext());
        dbHelper = new DBHelper(getActivity());
        network = Network.getInstance(getActivity());
        View view = inflater.inflate(R.layout.fragment_parent, container, false);
        f_name = (EditText) view.findViewById(R.id.parent_father_name);
        m_name = (EditText) view.findViewById(R.id.parent_mother_name);
        f_occupation = (EditText) view.findViewById(R.id.parent_father_occupation);
        f_income = (EditText) view.findViewById(R.id.parent_father_income);
        mobile = (EditText) view.findViewById(R.id.parent_mobile);
        address = (EditText) view.findViewById(R.id.parent_address);
        // setFonts();
        StudentInfo info = dbHelper.getStudentInfo();
        if (info == null) {
            fetchParentInfo();
        } else {
            updateInfo(info);
        }
        return view;
    }

    private void fetchParentInfo() {
        if(!network.isInternetAvailable()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put(tags.S_ID, active.getValue(tags.S_ID));
        map.put(tags.SESSION_ID, active.getValue(tags.SESSION_ID));
        map.put(tags.SCHOOL_ID, active.getValue(tags.SCHOOL_ID));
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, urls.getUrl(urls.getPath(tags.STUDENT_INFO), map), null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                if (response.has(tags.ARR_STUDENT_INFO)) {
                                    JSONArray jsonArray = response.getJSONArray(tags.ARR_STUDENT_INFO);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        StudentInfo info = new StudentInfo();
                                        if (object.has(tags.S_INFO_FATHER_NAME)) {
                                            info.setfName(object.getString(tags.S_INFO_FATHER_NAME));
                                        }
                                        if (object.has(tags.S_INFO_MOTHER_NAME)) {
                                            info.setmName(object.getString(tags.S_INFO_MOTHER_NAME));
                                        }
                                        if (object.has(tags.S_INFO_PERMANENT_ADD)) {
                                            info.setPerAddress(object.getString(tags.S_INFO_PERMANENT_ADD));
                                        }
                                        if (object.has(tags.S_INFO_PARENT_MOBILE)) {
                                            info.setParentMobile(object.getString(tags.S_INFO_PARENT_MOBILE));
                                        }
                                        if (object.has(tags.S_INFO_OCCUPATION)) {
                                            info.setfOccupation(object.getString(tags.S_INFO_OCCUPATION));
                                        }
                                        if (object.has(tags.S_INFO_FATHER_INCOME)) {
                                            info.setfIncome(object.getDouble(tags.S_INFO_FATHER_INCOME));
                                        }
                                        updateInfo(info);
                                    }
                                }
                            } catch (Exception e) {

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
            mListener.onParentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnParentListener) {
            mListener = (OnParentListener) context;
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
    public interface OnParentListener {
        // TODO: Update argument type and name
        void onParentInteraction(Uri uri);
    }

    private void setFonts() {
        f_name.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_REGULAR));
        m_name.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_REGULAR));
        f_occupation.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_REGULAR));
        f_income.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_REGULAR));
        mobile.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_REGULAR));
        address.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_REGULAR));
    }

    private void updateInfo(StudentInfo info) {
        f_name.setText(info.getfName());
        m_name.setText(info.getmName());
        f_income.setText(info.getfIncome() + "");
        f_occupation.setText(info.getfOccupation());
        mobile.setText(info.getParentMobile());
        address.setText(info.getPerAddress());
    }
}
