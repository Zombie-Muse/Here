package com.zomb.here;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Class {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long classId;

    @NonNull
    @ColumnInfo(name = "className")
    private String className;

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
