package com.zomb.here;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.security.ConfirmationAlreadyPresentingException;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class StudentActivity extends AppCompatActivity {
    Toolbar toolbar;
    private TextView greeting;
    private TextView date;
    private FloatingActionButton floatingActionButton;
    private RecyclerView studentList;
    private StudentAdapter studentAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<StudentItem> studentItems = new ArrayList<>();
    private ArrayList<ClassItem> classItems = new ArrayList<>();
    private DbHelper dbHelper;
    private MyCalendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        greeting = findViewById(R.id.tv_add_student_text);
        dbHelper = new DbHelper(this);
        floatingActionButton = findViewById(R.id.btn_add_student);
        floatingActionButton.setOnClickListener(v -> showAddDialog());
        calendar = new MyCalendar();
        loadData();

        studentList = findViewById(R.id.rv_student_list);
        layoutManager = new LinearLayoutManager(this);
        studentList.setLayoutManager(layoutManager);
        studentAdapter = new StudentAdapter(this, studentItems);
        studentList.setAdapter(studentAdapter);
        studentAdapter.setOnItemClickListener(position -> gotoItemActivity(position));
        setToolbar();
        date = toolbar.findViewById(R.id.toolbar_date);
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar_student);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        date = toolbar.findViewById(R.id.toolbar_date);
        ImageButton back = toolbar.findViewById(R.id.btn_back);
        ImageButton calendarBtn = toolbar.findViewById(R.id.btn_calendar);

        title.setText(R.string.your_students);
        date.setText(calendar.getDate());
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(v -> onBackPressed());
        calendarBtn.setVisibility(View.VISIBLE);
        calendarBtn.setOnClickListener(v -> showCalendar());
    }

    private void showCalendar() {
        MyCalendar calendar = new MyCalendar();
        calendar.show(getSupportFragmentManager(), "");
        calendar.setOnCalendarClickListener(this::onCalendarClicked);
    }

    private void onCalendarClicked(int year, int month, int day) {
        calendar.setDate(year, month, day);
        date.setText(calendar.getDate());

    }
    private void gotoItemActivity(int position) {
        Intent intent = new Intent(this, AttendanceActivity.class);
        intent.putExtra("studentName", studentItems.get(position).getStudentName());
        intent.putExtra("position", position);
//        intent.putExtra("className", classItems.get(position).getclassName());
//        intent.putExtra("classPosition", position);
        startActivity(intent);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                showUpdateDialog(item.getGroupId());
                break;
            case 1:
                deleteStudent(item.getGroupId());
                break;
        }
        return super.onContextItemSelected(item);
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

    private void showUpdateDialog(int position) {
    MyDialog dialog = new MyDialog();
    dialog.show(getSupportFragmentManager(), MyDialog.STUDENT_UPDATE_DIALOG);
    dialog.setListener((studentName) -> updateStudent(position, studentName));
    }

    private void updateStudent(int position, String studentName) {
        dbHelper.updateStudent(studentItems.get(position).getStudentId(), studentName);
        studentItems.get(position).setStudentName(studentName);
        studentAdapter.notifyItemChanged(position);
    }

    private void deleteStudent(int position) {
        dbHelper.deleteStudent(studentItems.get(position).getStudentId());
        studentItems.remove(position);
        studentAdapter.notifyItemRemoved(position);
    }
}
