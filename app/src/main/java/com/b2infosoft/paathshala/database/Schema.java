package com.b2infosoft.paathshala.database;

/**
 * Created by rajesh on 8/2/2016.
 */
public class Schema {

    ///////////      TABLE NAMES    ///////////////
    private static final String CITY="city";
    private static final String COMPLAINT_LIST="complaint";
    private static final String SCHOOL_LIST="school";
    private static final String SESSION_LIST="session";
    private static final String STUDENT_INFO="student";
    private static final String MONTH_ATTENDANCE="attendance";
    private static final String YEAR_ATTENDANCE="year_attendance";
    private static final String HOLIDAY="holiday";
    private static final String EXAM_LIST="exam";
    private static final String FEE_LEDGER="fees";
    private static final String MARKSHEET="marksheet";
    private static final String GRADING="grading";
    private static final String INHOUSE="inhouse";
    private static final String ALL_EXAM="examtype";
    private static final String TIME_TABLE="timetable";


    ///////////       CITY TABLE             //////////////
    private static final String S_NO="sno.";
    private static final String ID="id";
    private static final String NAME="name";

    ///////////       COMPLAINT LIST  TABLE      //////////////
    private static final String C_ID="id";
    private static final String C_SID="s_id";
    private static final String SUBJECT="subject_name";
    private static final String C_DATE="c_date";
    private static final String SCHOOL_ID="school_id";

    ///////////       SESSION LIST TABLE   //////////////
    private static final String SESSION_ID="id";
    private static final String SESSION_YEAR="year";

    ////////////    STUDENT TABLE  //////////////
    private static final String STU_PHOTO="photo";
    private static final String STU_ID="id";
    private static final String STU_PASSWORD="password";
    private static final String STU_SID="s_id";
    private static final String STU_ADMIN_DATE="admin_date";
    private static final String STU_SR_NO="sr_no";
    private static final String STU_DOB="dob";
    private static final String STU_NAME="name";
    private static final String STU_MOBILE="mobile";
    private static final String STU_GENDER="gender";
    private static final String STU_EMAIL="email";
    private static final String STU_MODE="mode";
    private static final String STU_CLASS="class";
    private static final String STU_SECTION="section";
    private static final String STU_SESSION_ID="session_id";
    private static final String STU_HOUSE="house";
    private static final String STU_ADMIN_TYPE="admin_type";
    private static final String STU_BPL="bpl";
    private static final String STU_HANDICAPPED="handicapped";
    private static final String STU_NATIONALITY="nationality";
    private static final String STU_CATEGORY="category";
    private static final String STU_ORI_ADMIN_DATE="ori_admin_date";
    private static final String STU_SESSION_YEAR="session_year";
    private static final String STU_RELIGION="religion";
    private static final String STU_TYPE="stu_type";
    private static final String STU_CAST="cast";
    private static final String STU_SCHOOL_ID="school_id";

    private static final String STU_FATHER_NAME="father_name";
    private static final String STU_FATHER_INCOME="income";
    private static final String STU_FATHER_OCCU="occupation";
    private static final String STU_PER_ADDRESS="permanent_address";
    private static final String STU_PARENT_MOBILE="mobile_no";
    private static final String STU_MOTHER_NAME="mother_name";

    private static final String STU_GUARDIAN_NAME="guardian_name";
    private static final String STU_GUARDIAN_MOBILE="mobile";
    private static final String STU_GUARDIAN_PHONE="phone";
    private static final String STU_GUARDIAN_EMAIL="email";
    private static final String STU_REMARK="remark";
    private static final String STU_CORR_ADDRESS="corr_address";

    /////////////// EXAM LIST TABLE    ///////////////
    private static final String EXAM_NAME="name";

    ////////////   TIME TABLE   //////////////
    private static final String SUBJECT_NAME="subject_name";
    private static final String EXAM_DATE="date";

    ////////////////    FEE LEDGER TABLE    ////////////////
    private static final String SR_NO="sr_no";
    private static final String F_ID="id";
    private static final String F_SID="sid";
    private static final String FEE_NAME="fee_name";
    private static final String FEE_TYPE="type";
    private static final String TOTAL="total";
    private static final String DEPOSIT="deposit";
    private static final String DISCOUNT="discount";
    private static final String BALANCE="balance";
    private static final String F_SESSION_ID="session_id";
    private static final String F_SCHOOL_ID="school_id";

    private static final String DEPOSIT_SR_NO="sr_no";
    private static final String F_DEPOSIT_ID="id";
    private static final String F_DEPOSIT_SID="sid";
    private static final String DEPOSIT_FEE_NAME="fee_name";
    private static final String DEPOSIT_FEE_TYPE="type";
    private static final String DEPOSIT_AMOUNT="amount";
    private static final String DEPOSIT_DISCOUNT="discount";
    private static final String RECEIPT_NO="receipt_no";
    private static final String F_PAY_DATE="receipt_date";
    private static final String F_MODE="mode";
    private static final String DEPOSIT_SESSION_ID="session_id";
    private static final String DEPOSIT_SCHOOL_ID="school_id";

   ///////////////////  ALL EXAM TABLE  ////////////////
    private static final String EXAM_ID="id";
    private static final String EXAM_SID="sid";
    private static final String ALL_EXAM_NAME="name";
    private static final String EXAM_SUBJECT_NAME="subject_name";
    private static final String EXAM_TMARKS="t_marks";
    private static final String EXAM_TMARKS_OBT="t_marks_obt";
    private static final String EXAM_PMARKS="p_marks";
    private static final String EXAM_PMARKS_OBT="p_marks_obt";
    private static final String EXAM_ADD_IN_MARK="add_in_mark";
    private static final String EXAM_ADD_IN_RES="add_in_res";
    private static final String EXAM_SESSION_ID="session_id";
    private static final String EXAM_SCHOOL_ID="school_id";

    private static final String EXAM_RESULT="result";
    private static final String EXAM_DIVISION="division";
    private static final String EXAM_PERCENTAGE="percentage";
    private static final String EXAM_TOT_MARKS="tot_marks";
    private static final String EXAM_TOT_OBT="tot_obt";
    private static final String EXAM_MARKSHEET_TYPE="marksheet_type";

    //////////////  MARKSHEET TABLE    ///////////////
    private static final String MARKSHEET_ID="id";
    private static final String MARKSHEET_SID="sid";
    private static final String MARKSHEET_EXAM_NAME="name";
    private static final String MARKSHEET_SUBJECT_NAME="subject_name";
    private static final String MARKSHEET_TMARKS="t_marks";
    private static final String MARKSHEET_TMARKS_OBT="t_marks_obt";
    private static final String MARKSHEET_PMARKS="p_marks";
    private static final String MARKSHEET_PMARKS_OBT="p_marks_obt";
    private static final String MARKSHEET_ADD_IN_MARK="add_in_mark";
    private static final String MARKSHEET_ADD_IN_RES="add_in_res";
    private static final String MARKSHEET_SESSION_ID="session_id";
    private static final String MARKSHEET_SCHOOL_ID="school_id";

    private static final String MARKSHEET_RESULT="result";
    private static final String MARKSHEET_DIVISION="division";
    private static final String MARKSHEET_PERCENTAGE="percentage";
    private static final String MARKSHEET_TOT_MARKS="tot_marks";
    private static final String MARKSHEET_TOT_OBT="tot_obt";
    private static final String MARKSHEET_TYPE="marksheet_type";

    ///////////////    INHOUSE TABLE  /////////////////
    private static final String INHOUSE_ID="id";
    private static final String INHOUSE_SID="sid";
    private static final String INHOUSE_SESSION_ID="session_id";
    private static final String INHOUSE_SCHOOL_ID="school_id";

    private static final String INHOUSE_RESULT="result";
    private static final String INHOUSE_DIVISION="division";
    private static final String INHOUSE_PERCENTAGE="percentage";
    private static final String INHOUSE_TOT_MARKS="tot_marks";
    private static final String INHOUSE_TOT_OBT="tot_obt";
    private static final String INHOUSE_TYPE="marksheet_type";

    //////////////////////     GRADING TABLE   ////////////////
    private static final String GRADING_ID="id";
    private static final String GRADING_SID="sid";
    private static final String GRADING_EXAM_NAME="name";
    private static final String GRADING_SUBJECT_NAME="subject_name";
    private static final String GRADING_TMARKS="t_marks";
    private static final String GRADING_TMARKS_OBT="t_marks_obt";
    private static final String GRADING_PMARKS="p_marks";
    private static final String GRADING_PMARKS_OBT="p_marks_obt";
    private static final String GRADING_ADD_IN_MARK="add_in_mark";
    private static final String GRADING_ADD_IN_RES="add_in_res";
    private static final String GRADING_SESSION_ID="session_id";
    private static final String GRADING_SCHOOL_ID="school_id";

    private static final String GRADING_RESULT="result";
    private static final String GRADING_DIVISION="division";
    private static final String GRADING_PERCENTAGE="percentage";
    private static final String GRADING_TOT_MARKS="tot_marks";
    private static final String GRADING_TOT_OBT="tot_obt";
    private static final String GRADING_TYPE="marksheet_type";

    //////////////////   MONTH ATTENDANCE  TABLE  ////////////////
    private static final String ONE="absent";
    private static final String TWO="absent";
    private static final String THREE="absent";
    private static final String FOUR="absent";
    private static final String FIVE="absent";
    private static final String SIX="absent";
    private static final String SEVEN="absent";
    private static final String EIGHT="absent";
    private static final String NINE="absent";
    private static final String TEN="absent";
    private static final String ELEVEN="absent";
    private static final String TWELEVE="absent";
    private static final String THIRTEEN="absent";
    private static final String FOURTEEN="absent";
    private static final String FIFTEEN="absent";
    private static final String SIXTEEN="absent";
    private static final String SEVENTEEN="absent";
    private static final String EIGHTEEN="absent";
    private static final String NINTEEN="absent";
    private static final String TWENTY="absent";
    private static final String T_ONE="absent";
    private static final String T_TWO="absent";
    private static final String T_THREE="absent";
    private static final String T_FOUR="absent";
    private static final String T_FIVE="absent";
    private static final String T_SIX="absent";
    private static final String T_SEVEN="absent";
    private static final String T_EIGHT="absent";
    private static final String T_NINE="absent";
    private static final String THIRTY="absent";
    private static final String THIRTY_ONE="absent";
    private static final String MONTH_ABSENT="absent";
    private static final String MONTH_PRESENT="absent";
    private static final String MONTH_HALF_DAY="half_day";
    private static final String MONTH_LEAVE="leave";


    //////////////////   YEAR ATTENDANCE  TABLE  ////////////////
    private static final String YEAR_SID="id";
    private static final String ATTENDANCE_MONTH="month";
    private static final String YEAR_TOTAL="total";
    private static final String YEAR_ABSENT="absent";
    private static final String YEAR_HALF_DAY="half_day";
    private static final String YEAR_LEAVE="leave";


    //////////////////   SCHOOL LIST TABLE  ////////////////
    private static final String SCHOOLS_ID="id";
    private static final String SCHOOLS_NAME="name";
    private static final String SCHOOLS_ADDRESS="address";

}
