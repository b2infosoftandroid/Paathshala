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
import android.widget.TextView;

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
import com.b2infosoft.paathshala.volly.MySingleton;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Student.OnStudentListener} interface
 * to handle interaction events.
 * Use the {@link Student#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Student extends Fragment {

    private static String TAG = Student.class.getName();

    Active active;
    Tags tags = Tags.getInstance();
    DBHelper dbHelper;
    EditText register_no, sr_no, house, dob, stu_class, section, category, religion, handicap, type, mobile, gender, mode;
    EditText faculty, admission_type, cast, nationality, bpl, email, ori_date, admin_date;
    TextView student_name;
    CircularImageView student_image;
    Fonts fonts = Fonts.getInstance();
    Urls urls = Urls.getInstance();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnStudentListener mListener;

    public Student() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Student.
     */
    // TODO: Rename and change types and number of parameters
    public static Student newInstance(String param1, String param2) {
        Student fragment = new Student();
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
        View view = inflater.inflate(R.layout.fragment_student, container, false);
        student_image = (CircularImageView) view.findViewById(R.id.student_info_circularImageView);
        register_no = (EditText) view.findViewById(R.id.student_info_register_no);
        sr_no = (EditText) view.findViewById(R.id.student_info_sr_no);
        house = (EditText) view.findViewById(R.id.student_info_house);
        dob = (EditText) view.findViewById(R.id.student_info_birth_date);
        ori_date = (EditText) view.findViewById(R.id.student_info_ori_admin_date);
        admin_date = (EditText) view.findViewById(R.id.student_info_admin_date);
        stu_class = (EditText) view.findViewById(R.id.stu_class);
        section = (EditText) view.findViewById(R.id.stu_section);
        category = (EditText) view.findViewById(R.id.stu_category);
        religion = (EditText) view.findViewById(R.id.stu_religion);
        handicap = (EditText) view.findViewById(R.id.stu_handicapped);
        type = (EditText) view.findViewById(R.id.stu_type);
        mobile = (EditText) view.findViewById(R.id.stu_mobile);
        gender = (EditText) view.findViewById(R.id.stu_gender);
        mode = (EditText) view.findViewById(R.id.stu_mode);
        faculty = (EditText) view.findViewById(R.id.stu_faculty);
        admission_type = (EditText) view.findViewById(R.id.stu_admission_type);
        cast = (EditText) view.findViewById(R.id.stu_cast);
        nationality = (EditText) view.findViewById(R.id.stu_nationality);
        bpl = (EditText) view.findViewById(R.id.stu_bpl);
        email = (EditText) view.findViewById(R.id.stu_email);
        student_name = (TextView) view.findViewById(R.id.student_info_name);
        //setFonts();
        StudentInfo info = dbHelper.getStudentInfo();
        if (info == null) {
            fetchStudentInfo();
        } else {
            updateInfo(info);
        }
        return view;
    }

    private void fetchStudentInfo() {
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
                                        if (object.has(tags.S_INFO_IMAGE)) {
                                            //student_image.set(object.get(tags.S_INFO_IMAGE));
                                            active.setKey(tags.S_INFO_IMAGE, object.getString(tags.S_INFO_IMAGE).toString());

                                        }
                                        if (object.has(tags.S_INFO_DOB)) {
                                            info.setDob(object.getString(tags.S_INFO_DOB));
                                        }
                                        if (object.has(tags.S_INFO_STU_NAME)) {
                                            active.setKey(tags.S_INFO_STU_NAME, object.getString(tags.S_INFO_STU_NAME));
                                            info.setName(object.getString(tags.S_INFO_STU_NAME));
                                        }
                                        if (object.has(tags.S_INFO_HOUSE)) {
                                            info.setHouse(object.getString(tags.S_INFO_HOUSE));
                                        }
                                        if (object.has(tags.S_INFO_SECTION)) {
                                            info.setSection(object.getString(tags.S_INFO_SECTION));
                                        }
                                        if (object.has(tags.S_INFO_CLASS)) {
                                            info.setStu_class(object.getString(tags.S_INFO_CLASS));
                                        }
                                        if (object.has(tags.S_INFO_NATIONALITY)) {
                                            info.setNationality(object.getString(tags.S_INFO_NATIONALITY));
                                        }
                                        if (object.has(tags.S_INFO_BPL)) {
                                            info.setBpl(object.getString(tags.S_INFO_BPL));
                                        }
                                        if (object.has(tags.S_INFO_CATEGORY)) {
                                            info.setCategory(object.getString(tags.S_INFO_CATEGORY));
                                        }
                                        if (object.has(tags.S_INFO_STU_CAST)) {
                                            info.setCast(object.getString(tags.S_INFO_STU_CAST));
                                        }
                                        if (object.has(tags.S_INFO_FACULTY)) {
                                            info.setFaculty(object.getString(tags.S_INFO_FACULTY));
                                        }
                                        if (object.has(tags.S_INFO_ORI_ADMIN_DATE)) {
                                            info.setOriAdminDate(object.getString(tags.S_INFO_ORI_ADMIN_DATE));
                                        }
                                        if (object.has(tags.S_INFO_ADMIN_DATE)) {
                                            info.setAdminDate(object.getString(tags.S_INFO_ADMIN_DATE));
                                        }
                                        if (object.has(tags.S_INFO_GENDER)) {
                                            info.setGender(object.getString(tags.S_INFO_GENDER));
                                        }
                                        if (object.has(tags.S_INFO_MODE)) {
                                            info.setMode(object.getString(tags.S_INFO_MODE));
                                        }
                                        if (object.has(tags.S_INFO_EMAIL)) {
                                            info.setEmail(object.getString(tags.S_INFO_EMAIL));
                                        }
                                        if (object.has(tags.S_INFO_RELIGION)) {
                                            info.setReligion(object.getString(tags.S_INFO_RELIGION));
                                        }
                                        if (object.has(tags.S_INFO_HANDICAPPED)) {
                                            info.setHandicap(object.getString(tags.S_INFO_HANDICAPPED));
                                        }
                                        if (object.has(tags.S_INFO_STU_TYPE)) {
                                            info.setType(object.getString(tags.S_INFO_STU_TYPE));
                                        }
                                        if (object.has(tags.S_INFO_MOBILE)) {
                                            info.setMobile(object.getString(tags.S_INFO_MOBILE));
                                        }
                                        if (object.has(tags.S_INFO_SR_NO)) {
                                            info.setSrNo(object.getInt(tags.S_INFO_SR_NO));
                                        }
                                        if (object.has(tags.S_INFO_ADMIN_TYPE)) {
                                            info.setAdminType(object.getString(tags.S_INFO_ADMIN_TYPE));
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
            mListener.onStudentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStudentListener) {
            mListener = (OnStudentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStudentListener");
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
    public interface OnStudentListener {
        // TODO: Update argument type and name
        void onStudentInteraction(Uri uri);
    }

    private void setFonts() {
        student_name.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_MEDIUM));
        register_no.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_REGULAR));
        sr_no.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_REGULAR));
        house.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_REGULAR));
        dob.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_REGULAR));

        stu_class.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_REGULAR));
        section.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_REGULAR));
        category.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_REGULAR));
        religion.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_REGULAR));
        handicap.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_REGULAR));
        type.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_REGULAR));
        mobile.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_REGULAR));
        gender.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_REGULAR));
        mode.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_REGULAR));
        faculty.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_REGULAR));
        admission_type.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_REGULAR));
        cast.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_REGULAR));
        nationality.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_REGULAR));
        bpl.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_REGULAR));
        email.setTypeface(fonts.getFont(getContext(), fonts.ROBOTO_REGULAR));

    }

    private void updateInfo(StudentInfo info) {
        student_name.setText(info.getName());
        sr_no.setText(info.getSrNo()+"");
        ori_date.setText(info.getOriAdminDate());
        admin_date.setText(info.getAdminDate());
        register_no.setText(info.getRegisterNo());
        house.setText(info.getHouse());
        handicap.setText(info.getHandicap());
        dob.setText(info.getDob());
        stu_class.setText(info.getStu_class());
        section.setText(info.getSection());
        cast.setText(info.getCast());
        category.setText(info.getCategory());
        religion.setText(info.getReligion());
        type.setText(info.getType());
        mobile.setText(info.getMobile());
        gender.setText(info.getGender());
        mode.setText(info.getMode());
        faculty.setText(info.getFaculty());
        admission_type.setText(info.getAdminType());
        nationality.setText(info.getNationality());
        bpl.setText(info.getBpl());
        email.setText(info.getEmail());
    }
}
