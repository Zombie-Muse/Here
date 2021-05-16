package com.zomb.here;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Date;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "attendance.db";
    private static final int DB_VERSION = 2;

    // COURSE TABLE (CLASS TABLE)
    private static final String COURSE_TABLE_NAME = "COURSE_TABLE";
    public static final String COURSE_ID = "_SUB_ID";
    public static final String COURSE_NAME_KEY = "COURSE_NAME";

    private static final String CREATE_COURSE_TABLE =
            "CREATE TABLE " + COURSE_TABLE_NAME + "(" +
                    COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COURSE_NAME_KEY + " TEXT NOT NULL, " +
                    "UNIQUE (" + COURSE_NAME_KEY + ")" + ");";

    private static final String DROP_COURSE_TABLE = "DROP TABLE IF EXISTS " + COURSE_TABLE_NAME;
    private static final String SELECT_COURSE_TABLE = "SELECT * FROM " + COURSE_TABLE_NAME;

    //  STUDENT TABLE
    private static final String STUDENT_TABLE_NAME = "STUDENT_TABLE";
    public static final String STUDENT_ID = "_SID";
    public static final String STUDENT_NAME_KEY = "STUDENT_NAME";

    private static final String CREATE_STUDENT_TABLE =
            "CREATE TABLE " + STUDENT_TABLE_NAME + "(" +
                    STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COURSE_ID + " INTEGER, " +
                    STUDENT_NAME_KEY + " TEXT NOT NULL, " +
                    "FOREIGN KEY (" + COURSE_ID + ") REFERENCES " +
                    COURSE_TABLE_NAME + "(" + COURSE_ID + ")" + ");";

    private static final String DROP_STUDENT_TABLE = "DROP TABLE IF EXISTS " + STUDENT_TABLE_NAME;
    private static final String SELECT_STUDENT_TABLE = "SELECT * FROM " + STUDENT_TABLE_NAME;
    private static final String SELECT_COURSE_STUDENT = "SELECT * FROM " + STUDENT_TABLE_NAME +
            " WHERE " + COURSE_ID + "=?";



    // ATTENDANCE TABLE
    private static final String ATTENDANCE_TABLE_NAME = "ATTENDANCE";
    private static final String ATTENDANCE_ID = "_AID";
    private static final String ATTENDANCE_DATE_KEY = "ATTEND_DATE";
    private static final String ATTENDANCE_STATUS_KEY = "ATTEND_STATUS";

    private static final String CREATE_ATTENDANCE_TABLE =
            "CREATE TABLE " + ATTENDANCE_TABLE_NAME + "(" +
                    ATTENDANCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    STUDENT_ID + " INTEGER NOT NULL, " +
                    ATTENDANCE_DATE_KEY + " DATE NOT NULL, " +
                    ATTENDANCE_STATUS_KEY + " TEXT NOT NULL, " +
                    " UNIQUE (" + STUDENT_ID + "," + ATTENDANCE_DATE_KEY + ")," +
                    "FOREIGN KEY (" + STUDENT_ID + ") REFERENCES " +
                    STUDENT_TABLE_NAME + " (" + STUDENT_ID + ")" + ");";

    private static final String DROP_ATTENDANCE_TABLE = "DROP TABLE IF EXISTS " + COURSE_TABLE_NAME;
    private static final String SELECT_ATTENDANCE_TABLE = "SELECT * FROM " + COURSE_TABLE_NAME;

    // STUDENT COURSES TABLE
    private static final String STUDENT_COURSES_TABLE_NAME = "STUDENT_COURSES_TABLE";
    public static final String STUDENT_COURSES_ID = "STUDENT_COURSES_ID";

    private static final String CREATE_STUDENT_COURSES_TABLE =
            "CREATE TABLE " + STUDENT_COURSES_TABLE_NAME + "(" +
                    STUDENT_COURSES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    STUDENT_ID + " INTEGER NOT NULL, " +
                    COURSE_ID + " INTEGER, " +
                    ATTENDANCE_ID + " INTEGER, " +
                    "FOREIGN KEY (" + STUDENT_ID + ") REFERENCES " +
                    STUDENT_TABLE_NAME + "(" + STUDENT_ID + "), " +
                    "FOREIGN KEY (" + COURSE_ID + ") REFERENCES " +
                    COURSE_TABLE_NAME + "(" + COURSE_ID + ")" + ");";
    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDENT_TABLE);
        db.execSQL(CREATE_COURSE_TABLE);
        db.execSQL(CREATE_ATTENDANCE_TABLE);
        db.execSQL(CREATE_STUDENT_COURSES_TABLE);
    }

    private static void addStudent(SQLiteDatabase db, String studentName) {

        ContentValues studentValues = new ContentValues();

        studentValues.put("studentName", studentName);

        db.insert("students", null, studentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
       try {
           db.execSQL(DROP_COURSE_TABLE);
           db.execSQL(DROP_STUDENT_TABLE);
           db.execSQL(DROP_ATTENDANCE_TABLE);
       } catch (SQLException e) {
           e.printStackTrace();
       }
    }

    public long addClass(String className) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COURSE_NAME_KEY, className);

        return database.insert(COURSE_TABLE_NAME, null, values);
    }

    public Cursor getClassTable() {
       SQLiteDatabase database = this.getReadableDatabase();
       return database.rawQuery(SELECT_COURSE_TABLE, null);
    }

    public long updateClass(int classId, String className) {
       SQLiteDatabase database = this.getWritableDatabase();
       ContentValues values = new ContentValues();
       values.put(COURSE_NAME_KEY, className);

       return database.update(COURSE_TABLE_NAME, values, COURSE_ID + "=?", new String[]{String.valueOf(classId)});
    }

    public long deleteClass(int classId) {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.delete(COURSE_TABLE_NAME, COURSE_ID + "=?", new String[]{String.valueOf(classId)});
    }

    public long addStudent(String studentName) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STUDENT_NAME_KEY, studentName);

        return database.insert(STUDENT_TABLE_NAME, null, values);
    }

    public Cursor getStudentTable() {
       SQLiteDatabase database = this.getReadableDatabase();
       return database.rawQuery(SELECT_STUDENT_TABLE, null);
    }

    public long updateStudent(int studentId, String studentName) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STUDENT_NAME_KEY, studentName);

        return database.update(STUDENT_TABLE_NAME,values,STUDENT_ID + "=?", new String[]{String.valueOf(studentId)});
    }

    public long deleteStudent(int studentId) {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.delete(STUDENT_TABLE_NAME, STUDENT_ID + "=?", new String[]{String.valueOf(studentId)});
    }

    public long addAttendance(int studentId, String date, String status) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STUDENT_ID, studentId);
        values.put(ATTENDANCE_DATE_KEY, date);
        values.put(ATTENDANCE_STATUS_KEY, status);

        return database.insert(ATTENDANCE_TABLE_NAME, null, values);
    }

    public long updateAttendance(int studentId, String date, String status) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ATTENDANCE_STATUS_KEY, status);
        String whereClause = ATTENDANCE_DATE_KEY + "='" + date + "'AND " + STUDENT_ID + "=" + studentId;

        return database.update(ATTENDANCE_TABLE_NAME, values, whereClause, null);
    }

    public String getAttendance(int studentId, String date) {
        String status = null;
        SQLiteDatabase database = this.getReadableDatabase();

        String whereClause = ATTENDANCE_DATE_KEY + "='" + date + "'AND " + STUDENT_ID + "=" + studentId;
        Cursor cursor = database.query(ATTENDANCE_TABLE_NAME, null, whereClause, null, null, null, null);
        if (cursor.moveToFirst()) {
            status = cursor.getString(cursor.getColumnIndex(ATTENDANCE_ID));
        }
        return status;
    }
}
