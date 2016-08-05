package com.b2infosoft.paathshala.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.b2infosoft.paathshala.model.ComplaintInfo;
import com.b2infosoft.paathshala.model.DepositInstallment;
import com.b2infosoft.paathshala.model.FeeInstallment;
import com.b2infosoft.paathshala.model.HolidayInfo;
import com.b2infosoft.paathshala.model.Marks;
import com.b2infosoft.paathshala.model.MonthInfo;
import com.b2infosoft.paathshala.model.Result;
import com.b2infosoft.paathshala.model.StudentInfo;
import com.b2infosoft.paathshala.model.TimeTableInfo;
import com.b2infosoft.paathshala.model.YearInfo;

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

        String T2 = "CREATE TABLE " + schema.COMPLAINT_LIST + "(" + schema.C_ID + " int," + schema.C_SID + " int," + schema.SUBJECT + " text," + schema.C_DETAILS + " text," + schema.C_DATE + " date," + schema.SCHOOL_ID + " int)";

        String T3 = "CREATE TABLE " + schema.SCHOOL_LIST + "(" + schema.SCHOOLS_ID + " int," + schema.SCHOOLS_NAME + " text," + schema.SCHOOLS_ADDRESS + " text)";

        String T4 = "CREATE TABLE " + schema.SESSION_LIST + "(" + schema.SESSION_ID + " int," + schema.SESSION_YEAR + " text)";

        String T5 = "CREATE TABLE " + schema.STUDENT_INFO + "(" + schema.STU_ID + " int,"+ schema.STU_PHOTO + " text," + schema.STU_PASSWORD + " text," + schema.STU_SID + " int," + schema.STU_ADMIN_DATE + " date," + schema.STU_SR_NO + " int," + schema.STU_DOB + " date," + schema.STU_NAME + " text," + schema.STU_MOBILE + " number," + schema.STU_GENDER + " text," + schema.STU_EMAIL + " text," + schema.STU_MODE + " text," + schema.STU_CLASS + " text,"
                + schema.STU_SECTION + " text," + schema.STU_SESSION_ID + " int," + schema.STU_HOUSE + " text," + schema.STU_ADMIN_TYPE + " text," + schema.STU_BPL + " text," + schema.STU_HANDICAPPED + " text," + schema.STU_NATIONALITY + " text," + schema.STU_CATEGORY + " text," + schema.STU_ORI_ADMIN_DATE + " date," + schema.STU_SESSION_YEAR + " int," + schema.STU_RELIGION + " text," + schema.STU_TYPE + " text,"
                + schema.STU_CAST + " text," + schema.STU_SCHOOL_ID + " int," + schema.STU_FACULTY + " text,"
                + schema.STU_FATHER_NAME + " text," + schema.STU_FATHER_INCOME + " int," + schema.STU_FATHER_OCCU + " text," + schema.STU_PER_ADDRESS + " text," + schema.STU_PARENT_MOBILE + " number," + schema.STU_MOTHER_NAME + " text,"
                + schema.STU_GUARDIAN_NAME + " text," + schema.STU_GUARDIAN_MOBILE + " number," + schema.STU_GUARDIAN_PHONE + " number," + schema.STU_GUARDIAN_EMAIL + " text," + schema.STU_REMARK + " text," + schema.STU_CORR_ADDRESS + " text)";

        String T6 = "CREATE TABLE " + schema.MONTH_ATTENDANCE + "(" + schema.MONTH + " int," + schema.YEAR + " int," + schema.ONE + " text," + schema.TWO + " text," + schema.THREE + " text," + schema.FOUR + " text," + schema.FIVE + " text," + schema.SIX + " text," + schema.SEVEN + " text," + schema.EIGHT + " text," + schema.NINE + " text," + schema.TEN + " text," + schema.ELEVEN + " text," + schema.TWELEVE + " text,"
                + schema.THIRTEEN + " text," + schema.FOURTEEN + " text," + schema.FIFTEEN + " text," + schema.SIXTEEN + " text," + schema.SEVENTEEN + " text," + schema.EIGHTEEN + " text," + schema.NINTEEN + " text," + schema.TWENTY + " text," + schema.T_ONE + " text," + schema.T_TWO + " text," + schema.T_THREE + " text," + schema.T_FOUR + " text,"
                + schema.T_FIVE + " text," + schema.T_SIX + " text," + schema.T_SEVEN + " text," + schema.T_EIGHT + " text," + schema.T_NINE + " text," + schema.THIRTY + " text," + schema.THIRTY_ONE + " text," + schema.MONTH_ABSENT + " int," + schema.MONTH_PRESENT + " int," + schema.MONTH_HALF_DAY + " int," + schema.MONTH_LEAVE + " int)";

        String T7 = "CREATE TABLE " + schema.YEAR_ATTENDANCE + "(" + schema.YEAR_SID + " int," + schema.ATTENDANCE_MONTH + " text," + schema.YEAR_TOTAL + " int,"+ schema.YEAR_PRESENT + " int," + schema.YEAR_ABSENT + " int," + schema.YEAR_HALF_DAY + " int," + schema.YEAR_LEAVE + " int," + schema.YEAR + " int)";

        String T8 = "CREATE TABLE " + schema.EXAM_LIST + "(" + schema.EXAM_NAME + " text)";

        String T9 = "CREATE TABLE " + schema.INSTALLMENT_FEE + "(" + schema.F_ID + " int," + schema.F_SID + " text," + schema.FEE_NAME + " text," + schema.FEE_TYPE + " text," + schema.TOTAL + " float," + schema.DEPOSIT + " float," + schema.DISCOUNT + " float," + schema.BALANCE + " float," + schema.F_SESSION_ID + " int," + schema.F_SCHOOL_ID + " int)";

        String T10 = "CREATE TABLE " + schema.DEPOSIT_FEE + "(" + schema.F_DEPOSIT_ID + " int," + schema.F_DEPOSIT_SID + " int," + schema.DEPOSIT_FEE_NAME + " text," + schema.DEPOSIT_FEE_TYPE + " text," + schema.DEPOSIT_AMOUNT + " float," + schema.DEPOSIT_DISCOUNT + " float," + schema.RECEIPT_NO + " int," + schema.F_PAY_DATE + " date," + schema.F_MODE + " text," + schema.DEPOSIT_SESSION_ID + " int," + schema.DEPOSIT_SCHOOL_ID + " int)";

        String T11 = "CREATE TABLE " + schema.MARK_SHEET_DETAILS + "(" + schema.MARKSHEET_ID + " int," + schema.MARKSHEET_SID + " int," + schema.MARKSHEET_EXAM_NAME + " text," + schema.MARKSHEET_SUBJECT_NAME + " text," + schema.MARKSHEET_TMARKS + " float," + schema.MARKSHEET_TMARKS_OBT + " float," + schema.MARKSHEET_PMARKS + " float," + schema.MARKSHEET_PMARKS_OBT + " float," + schema.MARKSHEET_ADD_IN_MARK + " text,"
                + schema.MARKSHEET_ADD_IN_RES + " text," + schema.MARKSHEET_SESSION_ID + " int," + schema.MARKSHEET_SCHOOL_ID + " int," + schema.MARKSHEET_SEARCH_TYPE + " text)";
        String T12 = "CREATE TABLE " + schema.MARK_SHEET + "(" + schema.MARKSHEET_ID + " int," + schema.MARKSHEET_SID + " int," + schema.MARKSHEET_RESULT + " text," + schema.MARKSHEET_DIVISION + " text," + schema.MARKSHEET_PERCENTAGE + " float," + schema.MARKSHEET_TOT_MARKS + " float," + schema.MARKSHEET_TOT_OBT + " float," + schema.MARKSHEET_SESSION_ID + " int," + schema.MARKSHEET_SCHOOL_ID + " int," + schema.MARKSHEET_TYPE + " text," + schema.MARKSHEET_SEARCH_TYPE + " text)";

        String T13 = "CREATE TABLE " + schema.TIME_TABLE + "(" + schema.SUBJECT_NAME + " text," + schema.EXAM_DATE + " text," + schema.EXAM_NAME + " text)";
        String T14 = "CREATE TABLE " + schema.HOLIDAY_TABLE + "(" + schema.HOLIDAY_NAME + " text," + schema.HOLIDAY_DATE_FROM + " text," + schema.HOLIDAY_DATE_TO + " text)";
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
        db.execSQL("DROP TABLE IF EXISTS " + schema.MARK_SHEET);
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

    public void setDepositInstallments(List<DepositInstallment> installments) {
        SQLiteDatabase database = this.getWritableDatabase();
        for (DepositInstallment installment : installments) {
            ContentValues values = new ContentValues();
            values.put(schema.F_DEPOSIT_ID, installment.getId());
            values.put(schema.F_DEPOSIT_SID, installment.getsId());
            values.put(schema.DEPOSIT_FEE_NAME, installment.getName());
            values.put(schema.DEPOSIT_FEE_TYPE, installment.getType());
            values.put(schema.DEPOSIT_AMOUNT, installment.getAmount());
            values.put(schema.DEPOSIT_AMOUNT, installment.getAmount());
            values.put(schema.DEPOSIT_DISCOUNT, installment.getAmount());
            values.put(schema.RECEIPT_NO, installment.getReceiptNo());
            values.put(schema.F_PAY_DATE, installment.getReceiptDate());
            values.put(schema.F_MODE, installment.getPaymentMode());
            values.put(schema.DEPOSIT_SESSION_ID, installment.getSessionId());
            values.put(schema.DEPOSIT_SCHOOL_ID, installment.getSchoolId());
            database.insert(schema.DEPOSIT_FEE, null, values);
        }
    }

    public void deleteDepositInstallments() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + schema.DEPOSIT_FEE);
    }

    public List<DepositInstallment> getDepositInstallments() {
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

    /* ----------------- MARK SHEETS PART START --------------------- */
    public void setMarkSheet(List<Result> list) {
        SQLiteDatabase database = this.getWritableDatabase();
        for (Result result : list) {
            ContentValues values = new ContentValues();
            values.put(schema.MARKSHEET_ID, result.getId());
            values.put(schema.MARKSHEET_SID, result.getsId());
            values.put(schema.MARKSHEET_RESULT, result.getResult());
            values.put(schema.MARKSHEET_DIVISION, result.getDivision());
            values.put(schema.MARKSHEET_PERCENTAGE, result.getPercentage());
            values.put(schema.MARKSHEET_TOT_MARKS, result.getTotalMarks());
            values.put(schema.MARKSHEET_TOT_OBT, result.getTotalObtain());
            values.put(schema.MARKSHEET_TYPE, result.getMarkSheetType());
            values.put(schema.MARKSHEET_SCHOOL_ID, result.getSchoolId());
            values.put(schema.MARKSHEET_SESSION_ID, result.getSessionId());
            values.put(schema.MARKSHEET_SEARCH_TYPE, result.getSearchType());
            database.insert(schema.MARK_SHEET, null, values);
        }
    }

    public void deleteMarkSheet(String string) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + schema.MARK_SHEET + " WHERE " + schema.MARKSHEET_SEARCH_TYPE + " = '" + string + "';");
    }

    public List<Result> getMarkSheet(String string) {
        List<Result> list = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + schema.MARK_SHEET + " WHERE " + schema.MARKSHEET_SEARCH_TYPE + " = '" + string + "';", null);
        while (cursor.moveToNext()) {
            Result result = new Result();
            result.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(schema.MARKSHEET_ID))));
            result.setsId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(schema.MARKSHEET_SID))));
            result.setResult(cursor.getString(cursor.getColumnIndex(schema.MARKSHEET_RESULT)));
            result.setDivision(cursor.getString(cursor.getColumnIndex(schema.MARKSHEET_DIVISION)));
            result.setPercentage(Double.parseDouble(cursor.getString(cursor.getColumnIndex(schema.MARKSHEET_PERCENTAGE))));
            result.setTotalMarks(Double.parseDouble(cursor.getString(cursor.getColumnIndex(schema.MARKSHEET_TOT_MARKS))));
            result.setTotalObtain(Double.parseDouble(cursor.getString(cursor.getColumnIndex(schema.MARKSHEET_TOT_OBT))));
            result.setMarkSheetType(cursor.getString(cursor.getColumnIndex(schema.MARKSHEET_TYPE)));
            result.setSessionId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(schema.MARKSHEET_SCHOOL_ID))));
            result.setSchoolId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(schema.MARKSHEET_SESSION_ID))));
            result.setSearchType(cursor.getString(cursor.getColumnIndex(schema.MARKSHEET_SEARCH_TYPE)));
            list.add(result);
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }

    public void setMarkSheetDetails(List<Marks> list) {
        SQLiteDatabase database = this.getWritableDatabase();
        for (Marks marks : list) {
            ContentValues values = new ContentValues();
            values.put(schema.MARKSHEET_ID, marks.getId());
            values.put(schema.MARKSHEET_SID, marks.getsId());
            values.put(schema.MARKSHEET_EXAM_NAME, marks.getExamName());
            values.put(schema.MARKSHEET_SUBJECT_NAME, marks.getSubjectName());
            values.put(schema.MARKSHEET_TMARKS, marks.getTMarks());
            values.put(schema.MARKSHEET_TMARKS_OBT, marks.getTMarksObt());
            values.put(schema.MARKSHEET_PMARKS, marks.getPMarks());
            values.put(schema.MARKSHEET_PMARKS_OBT, marks.getPMarksObt());
            values.put(schema.MARKSHEET_ADD_IN_MARK, marks.getAddInMark());
            values.put(schema.MARKSHEET_ADD_IN_RES, marks.getAddInRes());
            values.put(schema.MARKSHEET_SCHOOL_ID, marks.getSchoolId());
            values.put(schema.MARKSHEET_SESSION_ID, marks.getSessionId());
            values.put(schema.MARKSHEET_SEARCH_TYPE, marks.getSearchType());
            database.insert(schema.MARK_SHEET_DETAILS, null, values);
        }
    }

    public void deleteMarkSheetDetails(String string) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + schema.MARK_SHEET_DETAILS + " WHERE " + schema.MARKSHEET_SEARCH_TYPE + " = '" + string + "';");
    }

    public List<Marks> getMarkSheetDetails(String string) {
        List<Marks> list = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + schema.MARK_SHEET_DETAILS + " WHERE " + schema.MARKSHEET_SEARCH_TYPE + " = '" + string + "';", null);
        while (cursor.moveToNext()) {
            Marks marks = new Marks();
            marks.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(schema.MARKSHEET_ID))));
            marks.setsId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(schema.MARKSHEET_SID))));
            marks.setExamName(cursor.getString(cursor.getColumnIndex(schema.MARKSHEET_EXAM_NAME)));
            marks.setSubjectName(cursor.getString(cursor.getColumnIndex(schema.MARKSHEET_SUBJECT_NAME)));
            marks.setTMarks(Double.parseDouble(cursor.getString(cursor.getColumnIndex(schema.MARKSHEET_TMARKS))));
            marks.setTMarksObt(Double.parseDouble(cursor.getString(cursor.getColumnIndex(schema.MARKSHEET_TMARKS_OBT))));
            marks.setPMarks(Double.parseDouble(cursor.getString(cursor.getColumnIndex(schema.MARKSHEET_PMARKS))));
            marks.setPMarksObt(Double.parseDouble(cursor.getString(cursor.getColumnIndex(schema.MARKSHEET_PMARKS_OBT))));
            marks.setAddInMark(cursor.getString(cursor.getColumnIndex(schema.MARKSHEET_ADD_IN_MARK)));
            marks.setAddInRes(cursor.getString(cursor.getColumnIndex(schema.MARKSHEET_ADD_IN_RES)));
            marks.setSessionId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(schema.MARKSHEET_SCHOOL_ID))));
            marks.setSchoolId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(schema.MARKSHEET_SESSION_ID))));
            marks.setSearchType(cursor.getString(cursor.getColumnIndex(schema.MARKSHEET_SEARCH_TYPE)));
            list.add(marks);
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }

/* ----------------- MARK SHEETS PART END --------------------- */

    /* ----------------- EXAM PART START --------------------- */
    public void setExamType(List<String> stringList) {
        SQLiteDatabase database = this.getWritableDatabase();
        for (String value : stringList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(schema.EXAM_NAME, value);
            database.insert(schema.EXAM_LIST, null, contentValues);
        }
    }

    public void deleteExamType() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + schema.EXAM_LIST);
    }

    public List<String> getExamType() {
        List<String> list = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + schema.EXAM_LIST, null);
        while (cursor.moveToNext()) {
            list.add(cursor.getString(cursor.getColumnIndex(schema.EXAM_NAME)));
        }
        return list;
    }

    public void setTimeTable(List<TimeTableInfo> infoList) {
        SQLiteDatabase database = this.getWritableDatabase();
        for (TimeTableInfo info : infoList) {
            ContentValues values = new ContentValues();
            values.put(schema.SUBJECT_NAME, info.getSubject());
            values.put(schema.EXAM_DATE, info.getDate());
            values.put(schema.EXAM_NAME, info.getExamName());
            database.insert(schema.TIME_TABLE, null, values);
        }
    }

    public List<TimeTableInfo> getTimeTable(String string) {
        List<TimeTableInfo> infoList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + schema.TIME_TABLE + " WHERE " + schema.EXAM_NAME + " = '" + string + "';", null);
        while (cursor.moveToNext()) {
            TimeTableInfo info = new TimeTableInfo();
            info.setSubject(cursor.getString(cursor.getColumnIndex(schema.SUBJECT_NAME)));
            info.setDate(cursor.getString(cursor.getColumnIndex(schema.EXAM_DATE)));
            info.setExamName(cursor.getString(cursor.getColumnIndex(schema.EXAM_NAME)));
            infoList.add(info);
        }
        return infoList;
    }

    public void deleteTimeTable(String string) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + schema.TIME_TABLE + " WHERE " + schema.EXAM_NAME + " = '" + string + "';");
    }

    /* ----------------- EXAM PART  END--------------------- */
/* ----------------- STUDENT INFO START--------------------- */
    public void setStudentInfo(StudentInfo info) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(schema.STU_PHOTO, info.getStudentPhoto());
        values.put(schema.STU_ID, info.getId());
        values.put(schema.STU_PASSWORD, info.getPassword());
        values.put(schema.STU_SID, info.getsId());
        values.put(schema.STU_ADMIN_DATE, info.getAdminDate());
        values.put(schema.STU_SR_NO, info.getSrNo());
        values.put(schema.STU_DOB, info.getDob());
        values.put(schema.STU_FACULTY, info.getFaculty());
        values.put(schema.STU_NAME, info.getName());
        values.put(schema.STU_MOBILE, info.getMobile());
        values.put(schema.STU_GENDER, info.getGender());
        values.put(schema.STU_EMAIL, info.getEmail());
        values.put(schema.STU_MODE, info.getMode());
        values.put(schema.STU_CLASS, info.getStu_class());
        values.put(schema.STU_SECTION, info.getSection());
        values.put(schema.STU_SESSION_ID, info.getSessionId());
        values.put(schema.STU_HOUSE, info.getHouse());
        values.put(schema.STU_ADMIN_TYPE, info.getAdminType());
        values.put(schema.STU_BPL, info.getBpl());
        values.put(schema.STU_HANDICAPPED, info.getHandicap());
        values.put(schema.STU_NATIONALITY, info.getNationality());
        values.put(schema.STU_CATEGORY, info.getCategory());
        values.put(schema.STU_ORI_ADMIN_DATE, info.getOriAdminDate());
        values.put(schema.STU_SESSION_YEAR, info.getSessionYear());
        values.put(schema.STU_RELIGION, info.getReligion());
        values.put(schema.STU_TYPE, info.getType());
        values.put(schema.STU_CAST, info.getCast());
        values.put(schema.STU_SCHOOL_ID, info.getSchoolId());
        values.put(schema.STU_FATHER_NAME, info.getfName());
        values.put(schema.STU_FATHER_INCOME, info.getfIncome());
        values.put(schema.STU_FATHER_OCCU, info.getfOccupation());
        values.put(schema.STU_PER_ADDRESS, info.getPerAddress());
        values.put(schema.STU_PARENT_MOBILE, info.getParentMobile());
        values.put(schema.STU_MOTHER_NAME, info.getmName());
        values.put(schema.STU_GUARDIAN_NAME, info.getGuardianName());
        values.put(schema.STU_GUARDIAN_MOBILE, info.getGuardianMobile());
        values.put(schema.STU_GUARDIAN_PHONE, info.getGuardianPhone());
        values.put(schema.STU_GUARDIAN_EMAIL, info.getGuardianEmail());
        values.put(schema.STU_REMARK, info.getRemark());
        values.put(schema.STU_CORR_ADDRESS, info.getCorrAddress());
        database.insert(schema.STUDENT_INFO, null, values);
    }

    public void deleteStudentInfo() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + schema.STUDENT_INFO);
    }

    public StudentInfo getStudentInfo() {
        StudentInfo info = null;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + schema.STUDENT_INFO, null);
        while (cursor.moveToNext()) {
            info = new StudentInfo();
            info.setId(cursor.getInt(cursor.getColumnIndex(schema.STU_ID)));
            info.setStudentPhoto(cursor.getString(cursor.getColumnIndex(schema.STU_PHOTO)));
            info.setPassword(cursor.getString(cursor.getColumnIndex(schema.STU_PASSWORD)));
            info.setAdminDate(cursor.getString(cursor.getColumnIndex(schema.STU_ADMIN_DATE)));
            info.setSrNo(cursor.getInt(cursor.getColumnIndex(schema.STU_SR_NO)));
            info.setDob(cursor.getString(cursor.getColumnIndex(schema.STU_DOB)));
            info.setName(cursor.getString(cursor.getColumnIndex(schema.STU_NAME)));
            info.setFaculty(cursor.getString(cursor.getColumnIndex(schema.STU_FACULTY)));
            info.setMobile(cursor.getString(cursor.getColumnIndex(schema.STU_MOBILE)));
            info.setGender(cursor.getString(cursor.getColumnIndex(schema.STU_GENDER)));
            info.setEmail(cursor.getString(cursor.getColumnIndex(schema.STU_EMAIL)));
            info.setMode(cursor.getString(cursor.getColumnIndex(schema.STU_MODE)));
            info.setStu_class(cursor.getString(cursor.getColumnIndex(schema.STU_CLASS)));
            info.setSection(cursor.getString(cursor.getColumnIndex(schema.STU_SECTION)));
            info.setSessionId(cursor.getInt(cursor.getColumnIndex(schema.STU_SESSION_ID)));
            info.setHouse(cursor.getString(cursor.getColumnIndex(schema.STU_HOUSE)));
            info.setAdminType(cursor.getString(cursor.getColumnIndex(schema.STU_ADMIN_TYPE)));
            info.setBpl(cursor.getString(cursor.getColumnIndex(schema.STU_BPL)));
            info.setHandicap(cursor.getString(cursor.getColumnIndex(schema.STU_HANDICAPPED)));
            info.setNationality(cursor.getString(cursor.getColumnIndex(schema.STU_NATIONALITY)));
            info.setCategory(cursor.getString(cursor.getColumnIndex(schema.STU_CATEGORY)));
            info.setOriAdminDate(cursor.getString(cursor.getColumnIndex(schema.STU_ORI_ADMIN_DATE)));
            info.setSessionYear(cursor.getString(cursor.getColumnIndex(schema.STU_SESSION_YEAR)));
            info.setReligion(cursor.getString(cursor.getColumnIndex(schema.STU_RELIGION)));
            info.setType(cursor.getString(cursor.getColumnIndex(schema.STU_TYPE)));
            info.setCast(cursor.getString(cursor.getColumnIndex(schema.STU_CAST)));
            info.setSchoolId(cursor.getInt(cursor.getColumnIndex(schema.STU_SCHOOL_ID)));
            info.setfName(cursor.getString(cursor.getColumnIndex(schema.STU_FATHER_NAME)));
            info.setfIncome(cursor.getDouble(cursor.getColumnIndex(schema.STU_FATHER_INCOME)));
            info.setfOccupation(cursor.getString(cursor.getColumnIndex(schema.STU_FATHER_OCCU)));
            info.setPerAddress(cursor.getString(cursor.getColumnIndex(schema.STU_PER_ADDRESS)));
            info.setParentMobile(cursor.getString(cursor.getColumnIndex(schema.STU_PARENT_MOBILE)));
            info.setmName(cursor.getString(cursor.getColumnIndex(schema.STU_MOTHER_NAME)));
            info.setGuardianName(cursor.getString(cursor.getColumnIndex(schema.STU_GUARDIAN_NAME)));
            info.setGuardianMobile(cursor.getString(cursor.getColumnIndex(schema.STU_GUARDIAN_MOBILE)));
            info.setGuardianPhone(cursor.getString(cursor.getColumnIndex(schema.STU_GUARDIAN_PHONE)));
            info.setGuardianEmail(cursor.getString(cursor.getColumnIndex(schema.STU_GUARDIAN_EMAIL)));
            info.setRemark(cursor.getString(cursor.getColumnIndex(schema.STU_REMARK)));
            info.setCorrAddress(cursor.getString(cursor.getColumnIndex(schema.STU_CORR_ADDRESS)));
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return info;
    }
    /* ----------------- STUDENT INFO END--------------------- */

    /* ----------------- HOLIDAY DAY START--------------------- */
    public void setHoliday(List<HolidayInfo> infoList) {
        SQLiteDatabase database = this.getWritableDatabase();
        for (HolidayInfo info : infoList) {
            ContentValues values = new ContentValues();
            values.put(schema.HOLIDAY_NAME, info.getName());
            values.put(schema.HOLIDAY_DATE_FROM, info.getFromDate());
            values.put(schema.HOLIDAY_DATE_TO, info.getToDate());
            database.insert(schema.HOLIDAY_TABLE, null, values);
        }
    }

    public List<HolidayInfo> getHoliday() {
        List<HolidayInfo> infoList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + schema.HOLIDAY_TABLE, null);
        while (cursor.moveToNext()) {
            HolidayInfo info = new HolidayInfo();
            info.setName(cursor.getString(cursor.getColumnIndex(schema.HOLIDAY_NAME)));
            info.setFromDate(cursor.getString(cursor.getColumnIndex(schema.HOLIDAY_DATE_FROM)));
            info.setToDate(cursor.getString(cursor.getColumnIndex(schema.HOLIDAY_DATE_TO)));
            infoList.add(info);
        }
        return infoList;
    }

    public void deleteHoliday() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + schema.HOLIDAY_TABLE);
    }

    /* ----------------- HOLIDAY DAY END--------------------- */
/* ----------------- ATTENDANCE PART START--------------------- */
    public void setYearAttendance(List<YearInfo> infoList) {
        SQLiteDatabase database = this.getWritableDatabase();
        for (YearInfo info : infoList) {
            ContentValues values = new ContentValues();
            values.put(schema.YEAR_SID, info.getId());
            values.put(schema.ATTENDANCE_MONTH, info.getMonth());
            values.put(schema.YEAR_TOTAL, info.getTotal());
            values.put(schema.YEAR_PRESENT,info.getPresent());
            values.put(schema.YEAR_ABSENT, info.getAbsent());
            values.put(schema.YEAR_HALF_DAY, info.getHalfDay());
            values.put(schema.YEAR_LEAVE, info.getLeave());
            values.put(schema.YEAR, info.getYear());
            database.insert(schema.YEAR_ATTENDANCE, null, values);
        }
    }

    public List<YearInfo> getYearAttendance() {
        List<YearInfo> infoList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + schema.YEAR_ATTENDANCE + " ORDER BY " + schema.YEAR, null);
        while (cursor.moveToNext()) {
            YearInfo info = new YearInfo();
            info.setId(cursor.getInt(cursor.getColumnIndex(schema.YEAR_SID)));
            info.setMonth(cursor.getString(cursor.getColumnIndex(schema.ATTENDANCE_MONTH)));
            info.setTotal(cursor.getInt(cursor.getColumnIndex(schema.YEAR_TOTAL)));
            info.setPresent(cursor.getInt(cursor.getColumnIndex(schema.YEAR_PRESENT)));
            info.setAbsent(cursor.getInt(cursor.getColumnIndex(schema.YEAR_ABSENT)));
            info.setHalfDay(cursor.getInt(cursor.getColumnIndex(schema.YEAR_HALF_DAY)));
            info.setLeave(cursor.getInt(cursor.getColumnIndex(schema.YEAR_LEAVE)));
            info.setYear(cursor.getInt(cursor.getColumnIndex(schema.YEAR)));
            infoList.add(info);
        }
        return infoList;
    }

    public void deleteYearAttendance(String string) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + schema.YEAR_ATTENDANCE + " WHERE " + schema.YEAR + " = '" + string + "';");
    }

    public void setMonthAttendance(MonthInfo info) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(schema.MONTH, info.getMonth());
        values.put(schema.YEAR, info.getYear());
        values.put(schema.ONE, info.getOne());
        values.put(schema.TWO, info.getTwo());
        values.put(schema.THREE, info.getThree());
        values.put(schema.FOUR, info.getFour());
        values.put(schema.FIVE, info.getFive());
        values.put(schema.SIX, info.getSix());
        values.put(schema.SEVEN, info.getSeven());
        values.put(schema.EIGHT, info.getEight());
        values.put(schema.NINE, info.getNine());
        values.put(schema.TEN, info.getTen());
        values.put(schema.ELEVEN, info.getEleven());
        values.put(schema.TWELEVE, info.getTwelve());
        values.put(schema.THIRTEEN, info.getThirteen());
        values.put(schema.FOURTEEN, info.getFourteen());
        values.put(schema.FIFTEEN, info.getFifteen());
        values.put(schema.SIXTEEN, info.getSixteen());
        values.put(schema.SEVENTEEN, info.getSeventeen());
        values.put(schema.EIGHTEEN, info.getEighteen());
        values.put(schema.NINTEEN, info.getNineteen());
        values.put(schema.TWENTY, info.getTwenty());
        values.put(schema.T_ONE, info.getTOne());
        values.put(schema.T_TWO, info.getTTwo());
        values.put(schema.T_THREE, info.getTThree());
        values.put(schema.T_FOUR, info.getTFour());
        values.put(schema.T_FIVE, info.getTFive());
        values.put(schema.T_SIX, info.getTSix());
        values.put(schema.T_SEVEN, info.getTSeven());
        values.put(schema.T_EIGHT, info.getTEight());
        values.put(schema.T_NINE, info.getTNine());
        values.put(schema.THIRTY, info.getTTen());
        values.put(schema.THIRTY_ONE, info.getTEleven());
        values.put(schema.MONTH_ABSENT, info.getAbsent());
        values.put(schema.MONTH_PRESENT, info.getPresent());
        values.put(schema.MONTH_HALF_DAY, info.getHalfDay());
        values.put(schema.MONTH_LEAVE, info.getLeave());
        database.insert(schema.MONTH_ATTENDANCE, null, values);
    }

    public MonthInfo getMonthAttendance(String month ,String year) {
        MonthInfo info = null;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + schema.MONTH_ATTENDANCE+ " WHERE " + schema.MONTH_ATTENDANCE+"."+schema.YEAR + " = " + year + " AND "+ schema.MONTH+" ='"+month+"';", null);
        while (cursor.moveToNext()) {
            info= new MonthInfo();
            info.setMonth(cursor.getInt(cursor.getColumnIndex(schema.MONTH)));
            info.setYear(cursor.getInt(cursor.getColumnIndex(schema.YEAR)));
            info.setOne(cursor.getString(cursor.getColumnIndex(schema.ONE)));
            info.setTwo(cursor.getString(cursor.getColumnIndex(schema.TWO)));
            info.setThree(cursor.getString(cursor.getColumnIndex(schema.THREE)));
            info.setFour(cursor.getString(cursor.getColumnIndex(schema.FOUR)));
            info.setFive(cursor.getString(cursor.getColumnIndex(schema.FIVE)));
            info.setSix(cursor.getString(cursor.getColumnIndex(schema.SIX)));
            info.setSeven(cursor.getString(cursor.getColumnIndex(schema.SEVEN)));
            info.setEight(cursor.getString(cursor.getColumnIndex(schema.EIGHT)));
            info.setNine(cursor.getString(cursor.getColumnIndex(schema.NINE)));
            info.setTen(cursor.getString(cursor.getColumnIndex(schema.TEN)));
            info.setEleven(cursor.getString(cursor.getColumnIndex(schema.ELEVEN)));
            info.setTwelve(cursor.getString(cursor.getColumnIndex(schema.TWELEVE)));
            info.setThirteen(cursor.getString(cursor.getColumnIndex(schema.THIRTEEN)));
            info.setFourteen(cursor.getString(cursor.getColumnIndex(schema.FOURTEEN)));
            info.setFifteen(cursor.getString(cursor.getColumnIndex(schema.FIFTEEN)));
            info.setSixteen(cursor.getString(cursor.getColumnIndex(schema.SIXTEEN)));
            info.setSeventeen(cursor.getString(cursor.getColumnIndex(schema.SEVENTEEN)));
            info.setEighteen(cursor.getString(cursor.getColumnIndex(schema.EIGHTEEN)));
            info.setNineteen(cursor.getString(cursor.getColumnIndex(schema.NINTEEN)));
            info.setTwenty(cursor.getString(cursor.getColumnIndex(schema.TWENTY)));
            info.setTOne(cursor.getString(cursor.getColumnIndex(schema.T_ONE)));
            info.setTTwo(cursor.getString(cursor.getColumnIndex(schema.T_TWO)));
            info.setTThree(cursor.getString(cursor.getColumnIndex(schema.T_THREE)));
            info.setTFour(cursor.getString(cursor.getColumnIndex(schema.T_FOUR)));
            info.setTFive(cursor.getString(cursor.getColumnIndex(schema.T_FIVE)));
            info.setTSix(cursor.getString(cursor.getColumnIndex(schema.T_SIX)));
            info.setTSeven(cursor.getString(cursor.getColumnIndex(schema.T_SEVEN)));
            info.setTEight(cursor.getString(cursor.getColumnIndex(schema.T_EIGHT)));
            info.setTNine(cursor.getString(cursor.getColumnIndex(schema.T_NINE)));
            info.setTTen(cursor.getString(cursor.getColumnIndex(schema.THIRTY)));
            info.setTEleven(cursor.getString(cursor.getColumnIndex(schema.THIRTY_ONE)));
            info.setAbsent(cursor.getInt(cursor.getColumnIndex(schema.MONTH_ABSENT)));
            info.setPresent(cursor.getInt(cursor.getColumnIndex(schema.MONTH_PRESENT)));
            info.setHalfDay(cursor.getInt(cursor.getColumnIndex(schema.MONTH_HALF_DAY)));
            info.setLeave(cursor.getInt(cursor.getColumnIndex(schema.MONTH_LEAVE)));
        }
        return info;
    }
    public void deleteMonthAttendance(String month ,String year) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + schema.MONTH_ATTENDANCE + " WHERE " + schema.YEAR + " = " + year + " AND "+ schema.MONTH+" ='"+month+"';");
    }
/* ----------------- ATTENDANCE PART END--------------------- */
/* ----------------- COMPLAINT PART START--------------------- */
public void setComplaint(List<ComplaintInfo> infoList) {
    SQLiteDatabase database = this.getWritableDatabase();
    for (ComplaintInfo info : infoList) {
        ContentValues values = new ContentValues();
        values.put(schema.C_ID, info.getId());
        values.put(schema.C_SID, info.getsId());
        values.put(schema.SUBJECT, info.getSubject());
        values.put(schema.C_DETAILS, info.getDetail());
        values.put(schema.C_DATE, info.getCdate());
        values.put(schema.SCHOOL_ID, info.getSchoolId());
        database.insert(schema.COMPLAINT_LIST, null, values);
    }
}

    public List<ComplaintInfo> getComplaint() {
        List<ComplaintInfo> infoList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + schema.COMPLAINT_LIST+ " ORDER BY " + schema.C_ID +" DESC", null);
        while (cursor.moveToNext()) {
            ComplaintInfo info = new ComplaintInfo();
            info.setsId(cursor.getInt(cursor.getColumnIndex(schema.C_ID)));
            info.setsId(cursor.getInt(cursor.getColumnIndex(schema.C_SID)));
            info.setSubject(cursor.getString(cursor.getColumnIndex(schema.SUBJECT)));
            info.setDetail(cursor.getString(cursor.getColumnIndex(schema.C_DETAILS)));
            info.setCdate(cursor.getString(cursor.getColumnIndex(schema.C_DATE)));
            info.setSchoolId(cursor.getInt(cursor.getColumnIndex(schema.SCHOOL_ID)));
            infoList.add(info);
        }
        return infoList;
    }

    public void deleteComplaint() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + schema.COMPLAINT_LIST);
    }

/* ----------------- COMPLAINT PART END--------------------- */
}
