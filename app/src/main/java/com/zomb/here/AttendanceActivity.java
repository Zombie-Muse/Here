package com.zomb.here;

import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AttendanceActivity extends AppCompatActivity {
    Toolbar toolbar;
    private TextView greeting;
    private TextView date;
    private FloatingActionButton floatingActionButton;
    private RecyclerView studentList;
    private StudentAdapter studentAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<StudentItem> studentItems = new ArrayList<>();
    private DbHelper dbHelper;
    private MyCalendar calendar;
    private String courseName;
    private boolean isClass;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_attendance);

        courseName = getIntent().getStringExtra("className");

        greeting = findViewById(R.id.tv_add_student_text);
        dbHelper = new DbHelper(this);
        floatingActionButton = findViewById(R.id.btn_add_student);
        floatingActionButton.setOnClickListener(v -> showAddDialog());
        calendar = new MyCalendar();
        loadData();
    }

    private void loadData() {
        Cursor cursor = dbHelper.getStudentTable();
        studentItems.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DbHelper.STUDENT_ID));
            String studentName = cursor.getString(cursor.getColumnIndex(DbHelper.STUDENT_NAME_KEY));
            studentItems.add(new StudentItem(id, studentName));
            if (!studentItems.isEmpty()) {
                greeting.setVisibility(View.GONE);
            }
        }
    }

    private void showAddDialog() {
        MyDialog dialog = new MyDialog();
        dialog.show(getSupportFragmentManager(), MyDialog.STUDENT_ADD_DIALOG);
        dialog.setListener((studentName) -> addStudent(studentName));
    }

    private void addStudent(String studentName) {
        int studentId = (int) dbHelper.addStudent(studentName);
        StudentItem studentItem = new StudentItem(studentId, studentName);
        studentItems.add(studentItem);
        studentAdapter.notifyDataSetChanged();
    }
}
