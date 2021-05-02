package com.zomb.here;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StudentDao {
    @Query("SELECT * FROM Student WHERE id = :id")
    public Student getStudent(long id);

    @Query("SELECT * FROM Student WHERE studentName = :studentName")
    public Student getStudentName(String studentName);

    @Query("SELECT * FROM Student WHERE classId = :classId ORDER BY id")
    public List<Student> getStudents(long classId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertStudent (Student student);

    @Update
    public void updateStudent(Student student);

    @Delete
    public void deleteStudent(Student student);
}
