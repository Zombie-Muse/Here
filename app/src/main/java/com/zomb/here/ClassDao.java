package com.zomb.here;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ClassDao {
    @Query("SELECT * FROM Class WHERE id = :id")
    public Class getClass(long id);

    @Query("SELECT * FROM Class WHERE className = :className")
    public Class getClassByName(String className);

    @Query("SELECT * FROM Class ORDER BY className")
    public List<Class> getClasses();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertClass(Class mClass);

    @Update
    public void updateClass(Class mClass);

    @Delete
    public void deleteClass(Class mClass);
}
