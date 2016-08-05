package com.b2infosoft.paathshala.database;

/**
 * Created by rajesh on 8/2/2016.
 */
public class Schema {

    ///////////      TABLE NAMES    ///////////////
    public final String CITY="city";
    public final String COMPLAINT_LIST="complaint";
    public final String SCHOOL_LIST="school";
    public final String SESSION_LIST="session";
    public final String STUDENT_INFO="student";
    public final String MONTH_ATTENDANCE="attendance";
    public final String YEAR_ATTENDANCE="year_attendance";
    public final String HOLIDAY="holiday";
    public final String EXAM_LIST="exam";
    public final String INSTALLMENT_FEE="installment";
    public final String DEPOSIT_FEE="deposit";
    public final String GRADING="grading";
    public final String INHOUSE="inhouse";
    public final String ALL_EXAM="examtype";
    public final String TIME_TABLE="timetable";
    public final String MARK_SHEET ="mark_sheet";
    public final String MARK_SHEET_DETAILS ="mark_sheet_details";
    public final String  HOLIDAY_TABLE = "holiday";

    ///////////       CITY TABLE             //////////////
    public final String S_NO="sno";
    public final String ID="id";
    public final String NAME="name";

    ///////////       COMPLAINT LIST  TABLE      //////////////
    public final String C_ID="id";
    public final String C_SID="s_id";
    public final String SUBJECT="subject_name";
    public final String C_DATE="c_date";
    public final String C_DETAILS="Details";
    public final String SCHOOL_ID="school_id";

    ///////////       SESSION LIST TABLE   //////////////
    public final String SESSION_ID="id";
    public final String SESSION_YEAR="year";

    ////////////    STUDENT TABLE  //////////////
    public final String STU_PHOTO="photo";
    public final String STU_ID="id";
    public final String STU_PASSWORD="password";
    public final String STU_SID="s_id";
    public final String STU_ADMIN_DATE="admin_date";
    public final String STU_SR_NO="sr_no";
    public final String STU_DOB="dob";
    public final String STU_NAME="name";
    public final String STU_MOBILE="mobile";
    public final String STU_GENDER="gender";
    public final String STU_EMAIL="email";
    public final String STU_MODE="mode";
    public final String STU_CLASS="class";
    public final String STU_SECTION="section";
    public final String STU_FACULTY="faculty";
    public final String STU_SESSION_ID="session_id";
    public final String STU_HOUSE="house";
    public final String STU_ADMIN_TYPE="admin_type";
    public final String STU_BPL="bpl";
    public final String STU_HANDICAPPED="handicapped";
    public final String STU_NATIONALITY="nationality";
    public final String STU_CATEGORY="category";
    public final String STU_ORI_ADMIN_DATE="ori_admin_date";
    public final String STU_SESSION_YEAR="session_year";
    public final String STU_RELIGION="religion";
    public final String STU_TYPE="stu_type";
    public final String STU_CAST="cast";
    public final String STU_SCHOOL_ID="school_id";

    public final String STU_FATHER_NAME="father_name";
    public final String STU_FATHER_INCOME="income";
    public final String STU_FATHER_OCCU="occupation";
    public final String STU_PER_ADDRESS="permanent_address";
    public final String STU_PARENT_MOBILE="mobile_parent";
    public final String STU_MOTHER_NAME="mother_name";

    public final String STU_GUARDIAN_NAME="guardian_name";
    public final String STU_GUARDIAN_MOBILE="mobile_guardian";
    public final String STU_GUARDIAN_PHONE="phone";
    public final String STU_GUARDIAN_EMAIL="email_gurdian";
    public final String STU_REMARK="remark";
    public final String STU_CORR_ADDRESS="corr_address";

    /////////////// EXAM LIST TABLE    ///////////////
    public final String EXAM_NAME="name";

    ////////////   TIME TABLE   //////////////
    public final String SUBJECT_NAME="subject_name";
    public final String EXAM_DATE="date";

    ////////////////    INSTALLMENT FEE TABLE    ////////////////
    public final String SR_NO="sr_no";
    public final String F_ID="id";
    public final String F_SID="sid";
    public final String FEE_NAME="fee_name";
    public final String FEE_TYPE="type";
    public final String TOTAL="total";
    public final String DEPOSIT="deposit";
    public final String DISCOUNT="discount";
    public final String BALANCE="balance";
    public final String F_SESSION_ID="session_id";
    public final String F_SCHOOL_ID="school_id";

   ///////////////    DEPOSIT FEE TABLE /////////////
    public final String DEPOSIT_SR_NO="sr_no";
    public final String F_DEPOSIT_ID="id";
    public final String F_DEPOSIT_SID="sid";
    public final String DEPOSIT_FEE_NAME="fee_name";
    public final String DEPOSIT_FEE_TYPE="type";
    public final String DEPOSIT_AMOUNT="amount";
    public final String DEPOSIT_DISCOUNT="discount";
    public final String RECEIPT_NO="receipt_no";
    public final String F_PAY_DATE="receipt_date";
    public final String F_MODE="mode";
    public final String DEPOSIT_SESSION_ID="session_id";
    public final String DEPOSIT_SCHOOL_ID="school_id";

    //////////////  MARK_SHEET TABLE    ///////////////
    public final String MARKSHEET_ID="id";
    public final String MARKSHEET_SID="sid";
    public final String MARKSHEET_EXAM_NAME="name";
    public final String MARKSHEET_SUBJECT_NAME="subject_name";
    public final String MARKSHEET_TMARKS="t_marks";
    public final String MARKSHEET_TMARKS_OBT="t_marks_obt";
    public final String MARKSHEET_PMARKS="p_marks";
    public final String MARKSHEET_PMARKS_OBT="p_marks_obt";
    public final String MARKSHEET_ADD_IN_MARK="add_in_mark";
    public final String MARKSHEET_ADD_IN_RES="add_in_res";
    public final String MARKSHEET_SESSION_ID="session_id";
    public final String MARKSHEET_SCHOOL_ID="school_id";

    public final String MARKSHEET_SEARCH_TYPE = "searching_type";
    public final String MARKSHEET_RESULT="result";
    public final String MARKSHEET_DIVISION="division";
    public final String MARKSHEET_PERCENTAGE="percentage";
    public final String MARKSHEET_TOT_MARKS="tot_marks";
    public final String MARKSHEET_TOT_OBT="tot_obt";
    public final String MARKSHEET_TYPE="marksheet_type";

    //////////////////   MONTH ATTENDANCE  TABLE  ////////////////
    public final String MONTH = "month";
    public final String ONE="one";
    public final String TWO="two";
    public final String THREE="three";
    public final String FOUR="four";
    public final String FIVE="five";
    public final String SIX="six";
    public final String SEVEN="seven";
    public final String EIGHT="eight";
    public final String NINE="nine";
    public final String TEN="ten";
    public final String ELEVEN="eleven";
    public final String TWELEVE="twelve";
    public final String THIRTEEN="thirteen";
    public final String FOURTEEN="fourteen";
    public final String FIFTEEN="fifteen";
    public final String SIXTEEN="sixteen";
    public final String SEVENTEEN="seventeen";
    public final String EIGHTEEN="eighteen";
    public final String NINTEEN="nineteen";
    public final String TWENTY="twenty";
    public final String T_ONE="t_one";
    public final String T_TWO="t_two";
    public final String T_THREE="t_three";
    public final String T_FOUR="t_four";
    public final String T_FIVE="t_five";
    public final String T_SIX="t_six";
    public final String T_SEVEN="t_seven";
    public final String T_EIGHT="t_eight";
    public final String T_NINE="t_nine";
    public final String THIRTY="t_ten";
    public final String THIRTY_ONE="t_eleven";
    public final String MONTH_ABSENT="absent";
    public final String MONTH_PRESENT="present";
    public final String MONTH_HALF_DAY="half_day";
    public final String MONTH_LEAVE="leave";


    //////////////////   YEAR ATTENDANCE  TABLE  ////////////////
    public final String YEAR = "years";
    public final String YEAR_SID="id";
    public final String ATTENDANCE_MONTH="month";
    public final String YEAR_PRESENT="present";
    public final String YEAR_TOTAL="total";
    public final String YEAR_ABSENT="absent";
    public final String YEAR_HALF_DAY="half_day";
    public final String YEAR_LEAVE="leave";


    //////////////////   SCHOOL LIST TABLE  ////////////////
    public final String SCHOOLS_ID="id";
    public final String SCHOOLS_NAME="name";
    public final String SCHOOLS_ADDRESS="address";

    ////////////// HOLIDAY TABLE  ////////////
    public final String HOLIDAY_NAME = "name";
    public final String HOLIDAY_DATE_TO = "to_date";
    public final String HOLIDAY_DATE_FROM = "from_date";

}
