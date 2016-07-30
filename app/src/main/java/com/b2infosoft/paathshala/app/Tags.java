package com.b2infosoft.paathshala.app;

/**
 * Created by rajesh on 7/17/2016.
 */
public class Tags {
    private static Tags instance = new Tags();
    private Tags() {

    }
    public static Tags getInstance() {
        return instance;
    }

    public final String RESPONSE = "response";
    public final String RESPONSE_PASS ="Pass";
    public final String RESPONSE_FAIL ="Fail";
    public final int SUCCESS = 1;
    public final int UN_SUCCESS = 0;

    ///////////////  ----------  USER ACTION   ----------- //////////////

    public final String CHECK_USER ="CheckUser";
    public final String SESSION_LIST ="Sessions/1";
    public final String STUDENT_INFO ="StudentInfo";

    ////////////// ------- USER STRINGS --------- ////////////////////////////

    public final String USER_ROLE = "user_role";
    public final String USER_TYPE = "user_type";
    public final String USER_NAME = "user_name";
    public final String USER_SESSION = "user_session";
    public final String USER_PASSWORD = "user_password";

    //////////  JSON ARRAYS ////////////

    public final String ARR_RESULT="result";
    public final String ARR_SESSION_LIST="SessionList";
    public final String ARR_STUDENT_INFO="StudentInfo";
    public final String ARR_USER_INFO="UserInfo";


    /////////   LOGIN FORM USER VALID    /////////
    public final String USER_ID ="UserId";
    public final String SESSION_ID ="SessionId";
    public final String SESSION_YEAR ="SessionYear";
    public final String SCHOOL_ID ="SchoolId";
    public final String PWD_ID = "Pwd";
    public final String S_ID = "Sid";
    public final String COLUMN_1 = "Column1";

    ////////   STUDENT INFO  ///////////////////
    public final String S_INFO_ID = "Id";
    public final String S_INFO_SID = "SID";
    public final String S_INFO_S_PASSWORD = "SPassword";
    public final String S_INFO_SR_NO = "SrNo";
    public final String S_INFO_ADMIN_DATE = "AdminDate";
    public final String S_INFO_DOB = "Dob";
    public final String S_INFO_STU_NAME = "StuName";
    public final String S_INFO_MOBILE = "MobileNo";
    public final String S_INFO_GENDER = "Gender";
    public final String S_INFO_EMAIL = "Email";
    public final String S_INFO_MODE = "Mode";
    public final String S_INFO_CLASS = "Class";
    public final String S_INFO_FACULTY = "Faculty";
    public final String S_INFO_SECTION = "Section";
    public final String S_INFO_HOUSE = "House";
    public final String S_INFO_ADMIN_TYPE = "AdminType";
    public final String S_INFO_CATEGORY = "Category";
    public final String S_INFO_ORI_ADMIN_DATE = "OriAdminDate";
    public final String S_INFO_NATIONALITY = "Nationality";
    public final String S_INFO_RELIGION = "Religion";
    public final String S_INFO_HANDICAPPED = "Handicapped";
    public final String S_INFO_STU_TYPE = "StuType";
    public final String S_INFO_BPL = "BPL";
    public final String S_INFO_STU_CAST = "StudCast";

    public final String S_INFO_FATHER_NAME = "FatherName";
    public final String S_INFO_FATHER_INCOME = "FatherIncome";
    public final String S_INFO_PARMANENT_ADD = "ParmanentAdd";
    public final String S_INFO_PARENT_MOBILE = "parentmobile";
    public final String S_INFO_MOTHER_NAME = "mothername";
    public final String S_INFO_OCCUPATION = "Occupation";

    public final String S_INFO_GUARDIAN_NAME = "GuarName";
    public final String S_INFO_GUARDIAN_EMAIL = "GuarEmail";
    public final String S_INFO_CORR_ADD = "CorrsAdd";
    public final String S_INFO_GUARDIAN_MOBILE = "GuarMobileNo";
    public final String S_INFO_GUARDIAN_PHONE = "GuarPhone";
    public final String S_INFO_REMARK = "remark";

    //http://app.paathshala.net.in/App/CheckUser?UserId=5697&SessionId=8&SchoolId=1&Pwd=1
}
