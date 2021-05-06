package com.zomb.here;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Date;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "attendance.db";
    private static final int DB_VERSION = 1;

    // SUBJECT TABLE (CLASS TABLE)
    private static final String SUBJECT_TABLE_NAME = "subjects";
    private static final String SUBJECT_ID = "_SUB_ID";
    private static final String SUBJECT_NAME_KEY = "SUBJECT_NAME";

    private static final String CREATE_SUBJECT_TABLE =
            "CREATE TABLE " + SUBJECT_TABLE_NAME + "(" +
                    SUBJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    SUBJECT_NAME_KEY + " TEXT NOT NULL, " +
                    " UNIQUE (" + SUBJECT_NAME_KEY + ")" + ");";

    //  STUDENT TABLE
    private static final String STUDENT_TABLE_NAME = "students";
    private static final String STUDENT_ID = "_SID";
    private static final String STUDENT_NAME_KEY = "STUDENT_NAME";

    private static final String CREATE_STUDENT_TABLE =
            "CREATE TABLE " + STUDENT_TABLE_NAME + "(" +
                    STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    SUBJECT_ID + " INTEGER NOT NULL, " +
                    STUDENT_NAME_KEY + " TEXT NOT NULL, " +
                    " FOREIGN KEY (" + SUBJECT_ID + ") REFERENCES " +
                    SUBJECT_TABLE_NAME + "(" + SUBJECT_ID + ")" + ");";

    // ATTENDANCE TABLE
    private static final String ATTENDANCE_TABLE_NAME = "attendance";
    private static final String ATTENDANCE_ID = "_AID";
    private static final String ATTENDANCE_DATE = "ATTEND_DATE";
    private static final String ATTENDANCE_PRESENT = "ATTEND_PRESENT";
    private static final String ATTENDANCE_TARDY = "ATTEND_TARDY";

    private static final String CREATE_ATTENDANCE_TABLE =
            "CREATE TABLE " + ATTENDANCE_TABLE_NAME + "(" +
                    ATTENDANCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    ATTENDANCE_DATE + " TEXT NOT NULL, " +
                    ATTENDANCE_PRESENT + " TEXT NOT NULL, " +
                    ATTENDANCE_TARDY + " TEXT, " +
                    STUDENT_NAME_KEY + " TEXT, " +
                    " FOREIGN KEY (" + STUDENT_ID + ") REFERENCES " +
                    STUDENT_TABLE_NAME + "(" + STUDENT_ID + ")" + ");";


    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDENT_TABLE);
        db.execSQL(CREATE_SUBJECT_TABLE);
        db.execSQL(CREATE_ATTENDANCE_TABLE);
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
        if (oldVersion < 2) {
            // todo: add code to update tables
        }
    }
}
