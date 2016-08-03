package com.b2infosoft.paathshala.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.b2infosoft.paathshala.volly.MySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChangePassword.OnChangePasswordListener} interface
 * to handle interaction events.
 * Use the {@link ChangePassword#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePassword extends Fragment {

    private static String TAG=ChangePassword.class.getName();

    Tags tags= Tags.getInstance();
    Urls urls= Urls.getInstance();
    Active active;
    EditText password_old,password_new,password_confirm_new;
    Button update_password;
    Fonts fonts = Fonts.getInstance();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnChangePasswordListener mListener;

    public ChangePassword() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangePassword.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangePassword newInstance(String param1, String param2) {
        ChangePassword fragment = new ChangePassword();
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
        View  view = inflater.inflate(R.layout.fragment_change_password, container, false);
        active = Active.getInstance(getContext());
        password_old=(EditText)view.findViewById(R.id.change_old_password);
        password_new=(EditText)view.findViewById(R.id.change_new_password);
        password_confirm_new=(EditText)view.findViewById(R.id.change_confirm_new_password);
        update_password=(Button)view.findViewById(R.id.change_password_button);
        update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old_pass = password_old.getText().toString();
                String new_pass = password_new.getText().toString();
                String confirm_new_pass = password_confirm_new.getText().toString();
                if(new_pass.equals(confirm_new_pass)) {
                    newPassword(old_pass, new_pass);
                }else {
                    Toast.makeText(getContext(),"Confirm Password and New Password are different",Toast.LENGTH_LONG).show();
                }
            }
        });
      //  setFonts();
        return  view;
    }

    private void newPassword(String p_old, String p_new){
        HashMap<String,String> map=new HashMap<>();
        map.put(tags.S_ID,active.getValue(tags.S_ID));
        map.put(tags.OLD_PASSWORD,p_old);
        map.put(tags.NEW_PASSWORD,p_new);
        map.put(tags.SCHOOL_ID,active.getValue(tags.SCHOOL_ID));
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest
                (Request.Method.GET,urls.getUrl(urls.getPath(tags.CHANGE_PASSWORD),map), null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if(response!=null){
                            try {
                                if (response.has(tags.ARR_CHANGE_PASSWORD)) {
                                    JSONArray jsonArray = response.getJSONArray(tags.ARR_CHANGE_PASSWORD);
                                    JSONObject object = jsonArray.getJSONObject(0);
                                    if(object.has(tags.PASSWORD_STATUS)){
                                        String str = object.getString(tags.PASSWORD_STATUS);
                                        Toast.makeText(getContext(),str,Toast.LENGTH_LONG).show();
                                    }

                                }
                            }catch (Exception e){
                                Log.e(TAG,e.getMessage());
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
            mListener.onChangePasswordInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnChangePasswordListener) {
            mListener = (OnChangePasswordListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnChangePasswordListener");
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnChangePasswordListener {
        // TODO: Update argument type and name
        void onChangePasswordInteraction(Uri uri);
    }

    private void setFonts(){
        password_old.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_REGULAR));
        password_new.setTypeface(fonts.getFont(getContext(),fonts.ROBOTO_REGULAR));
        password_confirm_new.setTypeface(fonts.getFont(getContext(),fonts.ROBOTO_REGULAR));
        update_password.setTypeface(fonts.getFont(getContext(),fonts.ROBOTO_MEDIUM));
    }

}
