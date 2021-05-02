package com.zomb.here;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Student.class, Class.class}, version = 1)
public abstract class AttendanceDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "here.db";

    private static AttendanceDatabase attendanceDatabase;

    public static AttendanceDatabase getInstance(Context context) {
        if (attendanceDatabase == null) {
            attendanceDatabase = Room.databaseBuilder(context, AttendanceDatabase.class, DATABASE_NAME).build();  // took out allowMainThreadQueries() before .build()... will it work?
        }

        return attendanceDatabase;
    }

    public abstract StudentDao studentDao();
    public abstract ClassDao classDao();


}
