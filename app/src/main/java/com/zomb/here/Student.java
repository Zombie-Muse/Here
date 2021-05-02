package com.zomb.here;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Class.class, parentColumns = "id", childColumns = "classId", onDelete = CASCADE))
public class Student {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long studentId;

    @ColumnInfo(name = "studentName")
    private String studentName;

    @ColumnInfo(name = "studentStatus")
    private String studentStatus;

    @ColumnInfo(name = "classId")
    private long classId;

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(String studentStatus) {
        this.studentStatus = studentStatus;
    }

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }
}
