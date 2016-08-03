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
    public final String FEES_LEDGER ="FeesLedger";
    public final String EXAM_LIST ="ExamList";
    public final String EXAM_TIME_TABLE ="ExamTimeTable";
    public final String MARKS_SHEET ="Marksheet";
    public final String SCHOOL_LIST ="SchoolList ";
    public final String YEAR_ATTENDANCE ="MonthReg";
    public final String MONTH_ATTENDANCE ="AttnReg";
    public final String COMPLAINTS ="Complaints";
    public final String CHANGE_PASSWORD ="ChangePassword";
    public final String FORGOT_PASSWORD ="ForgotPassword";

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
    public final String ARR_FEES_DETAILS="FeesDetail";
    public final String ARR_FEES_DETAILS_DEPOSIT="DepositDetail";
    public final String ARR_EXAM_SESSION_LIST="SessionList";
    public final String ARR_EXAM_TIME_TABLE="ExamTimeTable";
    public final String ARR_MARKS_DETAIL="MarksDetail";
    public final String ARR_MARK_SHEET="Marksheet";
    public final String ARR_INSTITUTE_ID="Schools";
    public final String ARR_TOTAL_YEAR_ATTENDANCE="TotalYearAttendance";
    public final String ARR_TOTAL_MONTH_ATTENDANCE="AttendanceRegister";
    public final String ARR_COMPLAINTS="Complaints";
    public final String ARR_CHANGE_PASSWORD="Password";
    public final String ARR_FORGOT_PASSWORD="Password";


    /////////   LOGIN FORM USER VALID    /////////
    public final String SESSION = "session";
    public final String USER_ID ="UserId";
    public final String SESSION_ID ="SessionId";
    public final String SESSION_YEAR ="SessionYear";
    public final String SCHOOL_ID ="SchoolId";
    public final String PWD_ID = "Pwd";
    public final String S_ID = "Sid";
    public final String COLUMN_1 = "Column1";
    public final String TIME_TABLE_EXAM_NAME = "ExamName";

    ////////   STUDENT INFO  ///////////////////
    public final String S_INFO_IMAGE = "StuImage";
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

    //////////   FEES INSTALLMENTS  ////////////
    public final String FEES_ID = "Id";
    public final String FEES_NAME = "FeesName";
    public final String FEES_TYPE = "FeesType";
    public final String FEES_TOTAL = "Total";
    public final String FEES_DEPOSIT = "Deposit";
    public final String FEES_DISCOUNT = "Discount";
    public final String FEES_BALANCE = "Balance";
    public final String FEES_AMOUNT = "Amount";
    public final String FEES_RECEIPT_NO = "ReceiptNo";
    public final String FEES_PAY_DATE = "PayDate";
    public final String FEES_MODE = "Mode";

    /////////////  EXAM LIST   ///////////
    public final String EXAM_NAME = "ExamName";

    /////////    EXAM TIME TABLE   //////////
    public final String TIME_TABLE_SUBJECT_NAME ="SubjectName";
    public final String TIME_TABLE_EXAM_DATE ="ExamDate";

    ///////////  MARKS SHEET    ///////////////
    public final String M_ID = "id";
    public final String M_EXAM_TYPE = "examType";
    public final String M_EXAM_NAME = "ExamName";
    public final String M_SUBJECT_NAME = "SubjectName";
    public final String M_T_MARKS = "TMarks";
    public final String M_T_MARKS_OBT = "TMarksObt";
    public final String M_P_MARKS = "PMarks";
    public final String M_P_MARKS_OBT = "PMarksObt";
    public final String M_ADD_IN_MARK = "AddInMark";
    public final String M_ADD_IN_RES = "AddinRes";

    public final String M_RESULT = "Result";
    public final String M_DIVISION = "Division";
    public final String M_PERCENTAGE = "Percentage";
    public final String M_TOT_MARKS = "TotMarks";
    public final String M_TOTAL_OBT = "TotalObt";
    public final String M_MARK_SHEET_TYPE = "MarksheetType";


    //////////     LOGIN INSTITUTE ID  ////////////
    public final String INSTITUTE_ID = "Id";
    public final String INSTITUTE_NAME = "SchoolName";
    public final String INSTITUTE_ADDRESS = "SchoolAddress";
    public final String INSTITUTE_ACTIVE = "Active";

    ///////////   YEAR ATTENDANCE /////////
    public final String YEAR_YEAR = "Year";
    public final String YEAR_MONTH = "Month";
    public final String YEAR_TOTAL = "Total";
    public final String YEAR_PRESENT = "Present";
    public final String YEAR_ABSENT = "Absent";
    public final String YEAR_HALF_DAY = "Half Day";
    public final String YEAR_LEAVE = "Leave";

    ////////////  MONTH ATTENDANCE  ////////////

    public final String ONE ="One";
    public final String TWO ="Two";
    public final String THREE ="Three";
    public final String FOUR ="Four";
    public final String FIVE ="Five";
    public final String SIX ="Six";
    public final String SEVEN ="Seven";
    public final String EIGHT ="Eight";
    public final String NINE ="Nine";
    public final String TEN ="Ten";
    public final String ELEVEN ="Eleven";
    public final String TWELVE ="Tweleve";
    public final String THIRTEEN ="Thirteen";
    public final String FOURTEEN ="fourteen";
    public final String FIFTEEN ="fifteen";
    public final String SIXTEEN ="sixteen";
    public final String SEVENTEEN ="Seventeen";
    public final String EIGHTEEN ="Eighteen";
    public final String NINETEEN ="Ninteen";
    public final String TWENTY ="twenty";
    public final String TONE ="TOne";
    public final String TTWO ="TTwo";
    public final String TThree ="TThree";
    public final String TFOUR ="TFour";
    public final String TFIVE ="TFive";
    public final String TSIX ="TSix";
    public final String TSEVEN ="TSeven";
    public final String TEIGHT ="TEight";
    public final String TNINE ="TNine";
    public final String TTEN ="TTen";
    public final String TELEVEN ="TEleven";
    public final String MONTH_ABSENT="Absents";
    public final String MONTH_PRESENT="Presents";
    public final String MONTH_LEAVE="Leave";
    public final String MONTH_HALF_DAY="HalfDay";

    /////////////   COMPLAINT ////////////
    public final String COMP_SUBJECT = "Subject";
    public final String COMP_DETAILS = "Details";
    public final String COMP_STATUS = "Status";

    ////////////////     CHANGE PASSWORD    ///////////////
    public final String OLD_PASSWORD = "OldPassword";
    public final String NEW_PASSWORD = "NewPassword";
    public final String PASSWORD_STATUS = "Status";

    //////////// FORGOT PASSWORD   /////////////////
    public final String FORGOT_PASS_USER_ID = "UserId";
    public final String FORGOT_PASS_SCHOOL_ID = "SchoolId";
    public final String FORGOT_PASS_STATUS = "Status";


}
