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

    ////////////// ------- USER STRINGS --------- ////////////////////////////

    public final String USER_ROLE = "user_role";
    public final String USER_TYPE = "user_type";
    public final String USER_NAME = "user_name";
    public final String USER_SESSION = "user_session";
    public final String USER_PASSWORD = "user_password";

    //////////  JSON ARRAYS ////////////

    public final String ARR_RESULT="result";
    public final String ARR_SESSION_LIST="SessionList";
    public final String ARR_USER_INFO = "UserInfo";

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

    //http://app.paathshala.net.in/App/CheckUser?UserId=5697&SessionId=8&SchoolId=1&Pwd=1
}
