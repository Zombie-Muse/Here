package com.zomb.here;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CourseActivity extends AppCompatActivity {
    Toolbar toolbar;
    private TextView greeting;
    private TextView date;
    private FloatingActionButton floatingActionButton;
    private RecyclerView classList;
    private CourseAdapter courseAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<CourseItem> courseItems = new ArrayList<>();
    private DbHelper dbHelper;
    private MyCalendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        greeting = findViewById(R.id.tv_add_class_text);
        dbHelper = new DbHelper(this);
        floatingActionButton = findViewById(R.id.btn_add_class);
        floatingActionButton.setOnClickListener(v -> showAddDialog());
        calendar = new MyCalendar();
        loadData();

        classList = findViewById(R.id.rv_class_list);
        layoutManager = new LinearLayoutManager(this);
        classList.setLayoutManager(layoutManager);
        courseAdapter = new CourseAdapter(this, courseItems);
        classList.setAdapter(courseAdapter);
        courseAdapter.setOnItemClickListener(position -> gotoItemActivity(position));

        setToolbar();
        date = toolbar.findViewById(R.id.toolbar_date);
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar_course);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        date = toolbar.findViewById(R.id.toolbar_date);
        ImageButton back = toolbar.findViewById(R.id.btn_back);
        ImageButton calendarBtn = toolbar.findViewById(R.id.btn_calendar);

        title.setText(R.string.your_classes);
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

    private void loadData() {
        Cursor cursor = dbHelper.getClassTable();
        courseItems.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DbHelper.COURSE_ID));
            String courseName = cursor.getString(cursor.getColumnIndex(DbHelper.COURSE_NAME_KEY));

            courseItems.add(new CourseItem(id, courseName));
            if (!courseItems.isEmpty()) {
                greeting.setVisibility(View.GONE);
            }
        }
    }

    private void showAddDialog() {
        MyDialog dialog = new MyDialog();
        dialog.show(getSupportFragmentManager(), MyDialog.COURSE_ADD_DIALOG);
        dialog.setListener((className) -> addCourse(courseName));
    }

    private void addCourse(String courseName) {
        int classId = (int) dbHelper.addClass(courseName);
        CourseItem courseItem = new CourseItem(classId, courseName);
        courseItems.add(courseItem);
        courseAdapter.notifyDataSetChanged();
    }


    private void gotoItemActivity(int position) {
        Intent intent = new Intent(this, StudentActivity.class);
        intent.putExtra("className", courseItems.get(position).getClassName());
        intent.putExtra("position", position);
        setResult(RESULT_OK, intent);
        startActivity(intent);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                showUpdateDialog(item.getGroupId());
                break;
            case 1:
                deleteCourse(item.getGroupId());
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void showUpdateDialog(int position) {
        MyDialog dialog = new MyDialog();
        dialog.show(getSupportFragmentManager(), MyDialog.COURSE_UPDATE_DIALOG);
        dialog.setListener((className) -> updateClass(position, courseName));
    }

    private void updateCourse(int position, String courseName) {
        dbHelper.updateClass(courseItems.get(position).getCourseId(), courseName);
        courseItems.get(position).setCourseName(courseName);
        courseAdapter.notifyItemChanged(position);
    }

    private void deleteCourse(int position) {
        dbHelper.deleteCourse(courseItems.get(position).getCourseId());
        courseItems.remove(position);
        courseAdapter.notifyItemRemoved(position);
    }
}
