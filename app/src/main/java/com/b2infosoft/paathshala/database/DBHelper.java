package com.b2infosoft.paathshala.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rajesh on 8/2/2016.
 */

public class DBHelper extends SQLiteOpenHelper{

    Schema schema= new Schema();

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String T1 = "CREATE TABLE "+ schema.CITY +"("+schema.S_NO+" int," + schema.ID+" int,"+schema.NAME+" text)";

        String T2 = "CREATE TABLE "+ schema.COMPLAINT_LIST +"("+schema.C_ID+" int," + schema.C_SID+" int,"+schema.SUBJECT+" text,"+ schema.C_DATE+" date,"+ schema.SCHOOL_ID+" int)";

        String T3 = "CREATE TABLE "+ schema.SCHOOL_LIST +"("+schema.SCHOOLS_ID+" int," + schema.SCHOOLS_NAME+" text,"+schema.SCHOOLS_ADDRESS+" text)";

        String T4 = "CREATE TABLE "+ schema.SESSION_LIST +"("+schema.SESSION_ID+" int," + schema.SESSION_YEAR+" int)";

        String T5 = "CREATE TABLE "+ schema.STUDENT_INFO +"("+schema.STU_ID+" int," + schema.STU_PASSWORD+" text,"+schema.STU_SID+" int,"+ schema.STU_ADMIN_DATE+" date,"+ schema.STU_SR_NO+" int,"+schema.STU_DOB+" date," + schema.STU_NAME+" text,"+schema.STU_MOBILE+" number,"+ schema.STU_GENDER+" text,"+schema.STU_EMAIL+" text," + schema.STU_MODE+" text,"+schema.STU_CLASS+" text,"
                                   + schema.STU_SECTION+" text,"+schema.STU_SESSION_ID+" int," + schema.STU_HOUSE+" text,"+schema.STU_ADMIN_TYPE+" text,"+ schema.STU_BPL+" text,"+ schema.STU_HANDICAPPED+" text,"+ schema.STU_NATIONALITY+" text,"+schema.STU_CATEGORY+" text," + schema.STU_ORI_ADMIN_DATE+" date,"+schema.STU_SESSION_YEAR+" int,"+ schema.STU_RELIGION+" text,"+ schema.STU_TYPE+" text,"
                                   + schema.STU_CAST+" text,"+schema.STU_SCHOOL_ID+" int,"
                                   + schema.STU_FATHER_NAME+" text,"+ schema.STU_FATHER_INCOME+" int,"+ schema.STU_FATHER_OCCU+" text,"+schema.STU_PER_ADDRESS+" text," + schema.STU_PARENT_MOBILE+" number,"+schema.STU_MOTHER_NAME+" text,"
                                   + schema.STU_GUARDIAN_NAME+" text,"+ schema.STU_GUARDIAN_MOBILE+" number,"+ schema.STU_GUARDIAN_PHONE+" number,"+schema.STU_GUARDIAN_EMAIL+" text," + schema.STU_REMARK+" text,"+schema.STU_CORR_ADDRESS+" text)";

        String T6 = "CREATE TABLE "+ schema.MONTH_ATTENDANCE +"("+schema.ONE+" int," + schema.TWO+" int,"+schema.THREE+" int,"+ schema.FOUR+" int,"+ schema.FIVE+" int,"+schema.SIX+" int," + schema.SEVEN+" int,"+schema.EIGHT+" int,"+ schema.NINE+" int,"+schema.TEN+" int," + schema.ELEVEN+" int,"+schema.TWELEVE+" int,"
                                    + schema.THIRTEEN+" int,"+schema.FOURTEEN+" int," + schema.FIFTEEN+" int,"+schema.SIXTEEN+" int,"+ schema.SEVENTEEN+" int,"+ schema.EIGHTEEN+" int,"+ schema.NINTEEN+" int,"+schema.TWENTY+" int," + schema.T_ONE+" int,"+schema.T_TWO+" int,"+ schema.T_THREE+" int,"+ schema.T_FOUR+" int,"
                                    + schema.T_FIVE+" int,"+schema.T_SIX+" int,"+ schema.T_SEVEN+" int,"+ schema.T_EIGHT+" int,"+ schema.T_NINE+" int,"+schema.THIRTY+" int," + schema.THIRTY_ONE+" int,"+schema.MONTH_ABSENT+" int,"+ schema.MONTH_PRESENT+" int,"+ schema.MONTH_HALF_DAY+" int,"+ schema.MONTH_LEAVE+" int)";

        String T7 = "CREATE TABLE "+ schema.YEAR_ATTENDANCE +"("+schema.YEAR_SID+" int," + schema.ATTENDANCE_MONTH+" text,"+schema.YEAR_TOTAL+" int,"+ schema.YEAR_ABSENT+" int,"+ schema.YEAR_HALF_DAY+" int,"+ schema.YEAR_LEAVE+" int)";

        String T8 = "CREATE TABLE "+ schema.EXAM_LIST +"("+schema.EXAM_NAME+" text)";

        String T9 = "CREATE TABLE "+ schema.INSTALLMENT_FEE +"("+schema.SR_NO+" int," + schema.F_ID+" int,"+schema.F_SID+" text,"+ schema.FEE_NAME+" text,"+ schema.FEE_TYPE+" text,"+schema.TOTAL+" float," + schema.DEPOSIT+" float,"+schema.DISCOUNT+" float,"+ schema.BALANCE+" float,"+schema.F_SESSION_ID+" int," + schema.F_SCHOOL_ID+" int)";

        String T10 = "CREATE TABLE "+ schema.DEPOSIT_FEE +"("+schema.DEPOSIT_SR_NO+" int,"+ schema.F_DEPOSIT_ID+" int,"+schema.F_DEPOSIT_SID+" int," + schema.DEPOSIT_FEE_NAME+" text,"+schema.DEPOSIT_FEE_TYPE+" text,"+ schema.DEPOSIT_AMOUNT+" float,"+ schema.DEPOSIT_DISCOUNT+" float,"+ schema.RECEIPT_NO+" int,"+schema.F_PAY_DATE+" date," + schema.F_MODE+" text,"+schema.DEPOSIT_SESSION_ID+" int," + schema.DEPOSIT_SCHOOL_ID+" int)";

        String T11 = "CREATE TABLE "+ schema.MARKSHEET +"("+schema.MARKSHEET_ID+" int," + schema.MARKSHEET_SID+" int,"+schema.MARKSHEET_EXAM_NAME+" text,"+ schema.MARKSHEET_SUBJECT_NAME+" text,"+ schema.MARKSHEET_TMARKS+" float,"+schema.MARKSHEET_TMARKS_OBT+" float," + schema.MARKSHEET_PMARKS+" float,"+schema.MARKSHEET_PMARKS_OBT+" float,"+ schema.MARKSHEET_ADD_IN_MARK+" text,"
                                     +schema.MARKSHEET_ADD_IN_RES+" text," + schema.MARKSHEET_SESSION_ID+" int,"+schema.MARKSHEET_SCHOOL_ID+" int,"+ schema.MARKSHEET_RESULT+" text,"+schema.MARKSHEET_DIVISION+" text," + schema.MARKSHEET_PERCENTAGE+" float,"+schema.MARKSHEET_TOT_MARKS+" float,"+ schema.MARKSHEET_TOT_OBT+" float,"+ schema.MARKSHEET_TYPE+" text)";

        String T12 = "CREATE TABLE "+ schema.GRADING +"("+schema.GRADING_ID+" int," + schema.GRADING_SID+" int,"+schema.GRADING_EXAM_NAME+" text,"+ schema.GRADING_SUBJECT_NAME+" text,"+ schema.GRADING_TMARKS+" float,"+schema.GRADING_TMARKS_OBT+" float," + schema.GRADING_PMARKS+" float,"+schema.GRADING_PMARKS_OBT+" float,"+ schema.GRADING_ADD_IN_MARK+" text,"
                                 +schema.GRADING_ADD_IN_RES+" text," + schema.GRADING_SESSION_ID+" int,"+schema.GRADING_SCHOOL_ID+" int,"+ schema.GRADING_RESULT+" text,"+schema.GRADING_DIVISION+" text," + schema.GRADING_PERCENTAGE+" float,"+schema.GRADING_TOT_MARKS+" float,"+ schema.GRADING_TOT_OBT+" float,"+ schema.GRADING_MARKSHEET_TYPE+" text)";

        String T13 = "CREATE TABLE "+ schema.INHOUSE +"("+schema.INHOUSE_ID+" int," + schema.INHOUSE_SID+" int,"+ schema.INHOUSE_SESSION_ID+" int,"+schema.INHOUSE_SCHOOL_ID+" int,"+ schema.INHOUSE_RESULT+" text,"+schema.INHOUSE_DIVISION+" text," + schema.INHOUSE_PERCENTAGE+" float,"+schema.INHOUSE_TOT_MARKS+" float,"+ schema.INHOUSE_TOT_OBT+" float,"+ schema.INHOUSE_MARKSHEET_TYPE+" text)";

        String T14 = "CREATE TABLE "+ schema.ALL_EXAM +"("+schema.EXAM_ID+" int," + schema.EXAM_SID+" int,"+schema.ALL_EXAM_NAME+" text,"+ schema.EXAM_SUBJECT_NAME+" text,"+ schema.EXAM_TMARKS+" float,"+schema.EXAM_TMARKS_OBT+" float," + schema.EXAM_PMARKS+" float,"+schema.EXAM_PMARKS_OBT+" float,"+ schema.EXAM_ADD_IN_MARK+" text,"
                                     +schema.EXAM_ADD_IN_RES+" text," + schema.EXAM_SESSION_ID+" int,"+schema.EXAM_SCHOOL_ID+" int,"+ schema.EXAM_RESULT+" text,"+schema.EXAM_DIVISION+" text," + schema.EXAM_PERCENTAGE+" float,"+schema.EXAM_TOT_MARKS+" float,"+ schema.EXAM_TOT_OBT+" float,"+ schema.EXAM_MARKSHEET_TYPE+" text)";

        String T15 = "CREATE TABLE "+ schema.TIME_TABLE +"("+schema.SUBJECT_NAME+" text," + schema.EXAM_DATE+" date)";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
