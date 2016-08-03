package com.b2infosoft.paathshala.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.b2infosoft.paathshala.model.DepositInstallment;
import com.b2infosoft.paathshala.model.FeeInstallment;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by rajesh on 8/2/2016.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "paathshala.db";
    Schema schema = new Schema();

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String T1 = "CREATE TABLE " + schema.CITY + "(" + schema.S_NO + " int," + schema.ID + " int," + schema.NAME + " text)";

        String T2 = "CREATE TABLE " + schema.COMPLAINT_LIST + "(" + schema.C_ID + " int," + schema.C_SID + " int," + schema.SUBJECT + " text," + schema.C_DATE + " date," + schema.SCHOOL_ID + " int)";

        String T3 = "CREATE TABLE " + schema.SCHOOL_LIST + "(" + schema.SCHOOLS_ID + " int," + schema.SCHOOLS_NAME + " text," + schema.SCHOOLS_ADDRESS + " text)";

        String T4 = "CREATE TABLE " + schema.SESSION_LIST + "(" + schema.SESSION_ID + " int," + schema.SESSION_YEAR + " text)";

        String T5 = "CREATE TABLE " + schema.STUDENT_INFO + "(" + schema.STU_ID + " int," + schema.STU_PASSWORD + " text," + schema.STU_SID + " int," + schema.STU_ADMIN_DATE + " date," + schema.STU_SR_NO + " int," + schema.STU_DOB + " date," + schema.STU_NAME + " text," + schema.STU_MOBILE + " number," + schema.STU_GENDER + " text," + schema.STU_EMAIL + " text," + schema.STU_MODE + " text," + schema.STU_CLASS + " text,"
                + schema.STU_SECTION + " text," + schema.STU_SESSION_ID + " int," + schema.STU_HOUSE + " text," + schema.STU_ADMIN_TYPE + " text," + schema.STU_BPL + " text," + schema.STU_HANDICAPPED + " text," + schema.STU_NATIONALITY + " text," + schema.STU_CATEGORY + " text," + schema.STU_ORI_ADMIN_DATE + " date," + schema.STU_SESSION_YEAR + " int," + schema.STU_RELIGION + " text," + schema.STU_TYPE + " text,"
                + schema.STU_CAST + " text," + schema.STU_SCHOOL_ID + " int,"
                + schema.STU_FATHER_NAME + " text," + schema.STU_FATHER_INCOME + " int," + schema.STU_FATHER_OCCU + " text," + schema.STU_PER_ADDRESS + " text," + schema.STU_PARENT_MOBILE + " number," + schema.STU_MOTHER_NAME + " text,"
                + schema.STU_GUARDIAN_NAME + " text," + schema.STU_GUARDIAN_MOBILE + " number," + schema.STU_GUARDIAN_PHONE + " number," + schema.STU_GUARDIAN_EMAIL + " text," + schema.STU_REMARK + " text," + schema.STU_CORR_ADDRESS + " text)";

        String T6 = "CREATE TABLE " + schema.MONTH_ATTENDANCE + "(" + schema.ONE + " text," + schema.TWO + " text," + schema.THREE + " text," + schema.FOUR + " text," + schema.FIVE + " text," + schema.SIX + " text," + schema.SEVEN + " text," + schema.EIGHT + " text," + schema.NINE + " text," + schema.TEN + " text," + schema.ELEVEN + " text," + schema.TWELEVE + " text,"
                + schema.THIRTEEN + " text," + schema.FOURTEEN + " text," + schema.FIFTEEN + " text," + schema.SIXTEEN + " text," + schema.SEVENTEEN + " text," + schema.EIGHTEEN + " text," + schema.NINTEEN + " text," + schema.TWENTY + " text," + schema.T_ONE + " text," + schema.T_TWO + " text," + schema.T_THREE + " text," + schema.T_FOUR + " text,"
                + schema.T_FIVE + " text," + schema.T_SIX + " text," + schema.T_SEVEN + " text," + schema.T_EIGHT + " text," + schema.T_NINE + " text," + schema.THIRTY + " text," + schema.THIRTY_ONE + " text," + schema.MONTH_ABSENT + " int," + schema.MONTH_PRESENT + " int," + schema.MONTH_HALF_DAY + " int," + schema.MONTH_LEAVE + " int)";

        String T7 = "CREATE TABLE " + schema.YEAR_ATTENDANCE + "(" + schema.YEAR_SID + " int," + schema.ATTENDANCE_MONTH + " text," + schema.YEAR_TOTAL + " int," + schema.YEAR_ABSENT + " int," + schema.YEAR_HALF_DAY + " int," + schema.YEAR_LEAVE + " int)";

        String T8 = "CREATE TABLE " + schema.EXAM_LIST + "(" + schema.EXAM_NAME + " text)";

        String T9 = "CREATE TABLE " + schema.INSTALLMENT_FEE + "(" + schema.F_ID + " int," + schema.F_SID + " text," + schema.FEE_NAME + " text," + schema.FEE_TYPE + " text," + schema.TOTAL + " float," + schema.DEPOSIT + " float," + schema.DISCOUNT + " float," + schema.BALANCE + " float," + schema.F_SESSION_ID + " int," + schema.F_SCHOOL_ID + " int)";

        String T10 = "CREATE TABLE " + schema.DEPOSIT_FEE + "(" + schema.F_DEPOSIT_ID + " int," + schema.F_DEPOSIT_SID + " int," + schema.DEPOSIT_FEE_NAME + " text," + schema.DEPOSIT_FEE_TYPE + " text," + schema.DEPOSIT_AMOUNT + " float," + schema.DEPOSIT_DISCOUNT + " float," + schema.RECEIPT_NO + " int," + schema.F_PAY_DATE + " date," + schema.F_MODE + " text," + schema.DEPOSIT_SESSION_ID + " int," + schema.DEPOSIT_SCHOOL_ID + " int)";

        String T11 = "CREATE TABLE " + schema.MARKSHEET + "(" + schema.MARKSHEET_ID + " int," + schema.MARKSHEET_SID + " int," + schema.MARKSHEET_EXAM_NAME + " text," + schema.MARKSHEET_SUBJECT_NAME + " text," + schema.MARKSHEET_TMARKS + " float," + schema.MARKSHEET_TMARKS_OBT + " float," + schema.MARKSHEET_PMARKS + " float," + schema.MARKSHEET_PMARKS_OBT + " float," + schema.MARKSHEET_ADD_IN_MARK + " text,"
                + schema.MARKSHEET_ADD_IN_RES + " text," + schema.MARKSHEET_SESSION_ID + " int," + schema.MARKSHEET_SCHOOL_ID + " int," + schema.MARKSHEET_RESULT + " text," + schema.MARKSHEET_DIVISION + " text," + schema.MARKSHEET_PERCENTAGE + " float," + schema.MARKSHEET_TOT_MARKS + " float," + schema.MARKSHEET_TOT_OBT + " float," + schema.MARKSHEET_TYPE + " text)";

        String T12 = "CREATE TABLE " + schema.GRADING + "(" + schema.GRADING_ID + " int," + schema.GRADING_SID + " int," + schema.GRADING_EXAM_NAME + " text," + schema.GRADING_SUBJECT_NAME + " text," + schema.GRADING_TMARKS + " float," + schema.GRADING_TMARKS_OBT + " float," + schema.GRADING_PMARKS + " float," + schema.GRADING_PMARKS_OBT + " float," + schema.GRADING_ADD_IN_MARK + " text,"
                + schema.GRADING_ADD_IN_RES + " text," + schema.GRADING_SESSION_ID + " int," + schema.GRADING_SCHOOL_ID + " int," + schema.GRADING_RESULT + " text," + schema.GRADING_DIVISION + " text," + schema.GRADING_PERCENTAGE + " float," + schema.GRADING_TOT_MARKS + " float," + schema.GRADING_TOT_OBT + " float," + schema.GRADING_MARKSHEET_TYPE + " text)";

        String T13 = "CREATE TABLE " + schema.INHOUSE + "(" + schema.INHOUSE_ID + " int," + schema.INHOUSE_SID + " int," + schema.INHOUSE_SESSION_ID + " int," + schema.INHOUSE_SCHOOL_ID + " int," + schema.INHOUSE_RESULT + " text," + schema.INHOUSE_DIVISION + " text," + schema.INHOUSE_PERCENTAGE + " float," + schema.INHOUSE_TOT_MARKS + " float," + schema.INHOUSE_TOT_OBT + " float," + schema.INHOUSE_MARKSHEET_TYPE + " text)";

        String T14 = "CREATE TABLE " + schema.ALL_EXAM + "(" + schema.EXAM_ID + " int," + schema.EXAM_SID + " int," + schema.ALL_EXAM_NAME + " text," + schema.EXAM_SUBJECT_NAME + " text," + schema.EXAM_TMARKS + " float," + schema.EXAM_TMARKS_OBT + " float," + schema.EXAM_PMARKS + " float," + schema.EXAM_PMARKS_OBT + " float," + schema.EXAM_ADD_IN_MARK + " text,"
                + schema.EXAM_ADD_IN_RES + " text," + schema.EXAM_SESSION_ID + " int," + schema.EXAM_SCHOOL_ID + " int," + schema.EXAM_RESULT + " text," + schema.EXAM_DIVISION + " text," + schema.EXAM_PERCENTAGE + " float," + schema.EXAM_TOT_MARKS + " float," + schema.EXAM_TOT_OBT + " float," + schema.EXAM_MARKSHEET_TYPE + " text)";

        String T15 = "CREATE TABLE " + schema.TIME_TABLE + "(" + schema.SUBJECT_NAME + " text," + schema.EXAM_DATE + " text)";

        db.execSQL(T1);
        db.execSQL(T2);
        db.execSQL(T3);
        db.execSQL(T4);
        db.execSQL(T5);
        db.execSQL(T6);
        db.execSQL(T7);
        db.execSQL(T8);
        db.execSQL(T9);
        db.execSQL(T10);
        db.execSQL(T11);
        db.execSQL(T12);
        db.execSQL(T13);
        db.execSQL(T14);
        db.execSQL(T15);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + schema.CITY);
        db.execSQL("DROP TABLE IF EXISTS " + schema.COMPLAINT_LIST);
        db.execSQL("DROP TABLE IF EXISTS " + schema.SCHOOL_LIST);
        db.execSQL("DROP TABLE IF EXISTS " + schema.SESSION_LIST);
        db.execSQL("DROP TABLE IF EXISTS " + schema.STUDENT_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + schema.MONTH_ATTENDANCE);
        db.execSQL("DROP TABLE IF EXISTS " + schema.YEAR_ATTENDANCE);
        db.execSQL("DROP TABLE IF EXISTS " + schema.EXAM_LIST);
        db.execSQL("DROP TABLE IF EXISTS " + schema.INSTALLMENT_FEE);
        db.execSQL("DROP TABLE IF EXISTS " + schema.DEPOSIT_FEE);
        db.execSQL("DROP TABLE IF EXISTS " + schema.MARKSHEET);
        db.execSQL("DROP TABLE IF EXISTS " + schema.GRADING);
        db.execSQL("DROP TABLE IF EXISTS " + schema.INHOUSE);
        db.execSQL("DROP TABLE IF EXISTS " + schema.ALL_EXAM);
        db.execSQL("DROP TABLE IF EXISTS " + schema.TIME_TABLE);
    }

    /* ----------------- SESSION PART START --------------------- */
    public void setSession(String id, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(schema.SESSION_ID, id);
        values.put(schema.SESSION_YEAR, value);
        db.insert(schema.SESSION_LIST, null, values);
    }

    public void deleteSession() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + schema.SESSION_LIST);
    }

    public Hashtable<String, String> getSession() {
        Hashtable<String, String> data = new Hashtable<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + schema.SESSION_LIST, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(schema.SESSION_ID));
            String session = cursor.getString(cursor.getColumnIndex(schema.SESSION_YEAR));
            data.put(session, id);
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return data;
    }

    /* ----------------- SESSION PART END --------------------- */

    /* ----------------- FEES PART START --------------------- */
    public void setFeesInstallment(List<FeeInstallment> installments) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (FeeInstallment installment : installments) {
            ContentValues values = new ContentValues();
            values.put(schema.F_ID, installment.getId());
            values.put(schema.F_SID, installment.getsId());
            values.put(schema.FEE_NAME, installment.getName());
            values.put(schema.FEE_TYPE, installment.getType());
            values.put(schema.TOTAL, installment.getTotal());
            values.put(schema.DEPOSIT, installment.getDeposit());
            values.put(schema.DISCOUNT, installment.getDiscount());
            values.put(schema.BALANCE, installment.getBalance());
            values.put(schema.F_SESSION_ID, installment.getSessionId());
            values.put(schema.F_SCHOOL_ID, installment.getSchoolId());
            db.insert(schema.INSTALLMENT_FEE, null, values);
        }
    }
    public void deleteInstallments() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + schema.INSTALLMENT_FEE);
    }
    public List<FeeInstallment> getInstallments() {
        List<FeeInstallment> list = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + schema.INSTALLMENT_FEE, null);
        while (cursor.moveToNext()) {
            FeeInstallment installment = new FeeInstallment();
            installment.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(schema.F_ID))));
            installment.setsId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(schema.F_SID))));
            installment.setName(cursor.getString(cursor.getColumnIndex(schema.FEE_NAME)));
            installment.setType(cursor.getString(cursor.getColumnIndex(schema.FEE_TYPE)));
            installment.setTotal(Double.parseDouble(cursor.getString(cursor.getColumnIndex(schema.TOTAL))));
            installment.setDeposit(Double.parseDouble(cursor.getString(cursor.getColumnIndex(schema.DEPOSIT))));
            installment.setDiscount(Double.parseDouble(cursor.getString(cursor.getColumnIndex(schema.DISCOUNT))));
            installment.setBalance(Double.parseDouble(cursor.getString(cursor.getColumnIndex(schema.BALANCE))));
            installment.setSessionId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(schema.SESSION_ID))));
            installment.setSchoolId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(schema.SCHOOL_ID))));
            list.add(installment);
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }

    public void setDepositInstallments(List<DepositInstallment> installments){
        SQLiteDatabase database = this.getWritableDatabase();
        for(DepositInstallment installment:installments){
            ContentValues values = new ContentValues();
            values.put(schema.F_DEPOSIT_ID,installment.getId());
            values.put(schema.F_DEPOSIT_SID,installment.getsId());
            values.put(schema.DEPOSIT_FEE_NAME,installment.getName());
            values.put(schema.DEPOSIT_FEE_TYPE,installment.getType());
            values.put(schema.DEPOSIT_AMOUNT,installment.getAmount());
            values.put(schema.DEPOSIT_AMOUNT,installment.getAmount());
            values.put(schema.DEPOSIT_DISCOUNT,installment.getAmount());
            values.put(schema.RECEIPT_NO,installment.getReceiptNo());
            values.put(schema.F_PAY_DATE,installment.getReceiptDate());
            values.put(schema.F_MODE,installment.getPaymentMode());
            values.put(schema.DEPOSIT_SESSION_ID,installment.getSessionId());
            values.put(schema.DEPOSIT_SCHOOL_ID,installment.getSchoolId());
            database.insert(schema.DEPOSIT_FEE,null,values);
        }
    }
    public void deleteDepositInstallments() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + schema.DEPOSIT_FEE);
    }
    public List<DepositInstallment> getDepositInstallments(){
        List<DepositInstallment> list = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + schema.DEPOSIT_FEE, null);
        while (cursor.moveToNext()) {
            DepositInstallment installment = new DepositInstallment();
            installment.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(schema.F_DEPOSIT_ID))));
            installment.setsId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(schema.F_DEPOSIT_SID))));
            installment.setName(cursor.getString(cursor.getColumnIndex(schema.DEPOSIT_FEE_NAME)));
            installment.setType(cursor.getString(cursor.getColumnIndex(schema.DEPOSIT_FEE_TYPE)));
            installment.setAmount(Double.parseDouble(cursor.getString(cursor.getColumnIndex(schema.DEPOSIT_AMOUNT))));
            installment.setDiscount(Double.parseDouble(cursor.getString(cursor.getColumnIndex(schema.DEPOSIT_DISCOUNT))));
            installment.setReceiptNo(Integer.parseInt(cursor.getString(cursor.getColumnIndex(schema.RECEIPT_NO))));
            installment.setReceiptDate(cursor.getString(cursor.getColumnIndex(schema.F_PAY_DATE)));
            installment.setPaymentMode(cursor.getString(cursor.getColumnIndex(schema.F_MODE)));
            installment.setSessionId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(schema.DEPOSIT_SESSION_ID))));
            installment.setSchoolId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(schema.DEPOSIT_SCHOOL_ID))));
            list.add(installment);
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }
/* ----------------- FEES FORM PART END --------------------- */

/* ----------------- TIME TABLE PART START --------------------- */

/* ----------------- TIME TABLE PART END --------------------- */

}
