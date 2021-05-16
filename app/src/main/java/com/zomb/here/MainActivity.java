package com.zomb.here;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String user = "Human Teacher";
    private TextView greeting;
    private TextView date;
    private AppCompatButton classes;
    private AppCompatButton students;
    private AppCompatButton reports;
    private MyCalendar calendar;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        greeting = findViewById(R.id.tv_greeting);
        greeting.setText("Greetings, " + user + "!");
        calendar = new MyCalendar();

        classes = findViewById(R.id.btn_classes);
        classes.setOnClickListener(v -> showClasses());

        students = findViewById(R.id.btn_students);
        students.setOnClickListener(v -> showStudents());

        reports = findViewById(R.id.btn_reports);
        reports.setOnClickListener(v -> showReports());
        setToolbar();
        date = toolbar.findViewById(R.id.toolbar_date);
        setSupportActionBar(toolbar);

    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar_main);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        date = toolbar.findViewById(R.id.toolbar_date);
        ImageButton back = toolbar.findViewById(R.id.btn_back);
        ImageButton calendarBtn = toolbar.findViewById(R.id.btn_calendar);

        title.setText(R.string.app_title);
        date.setText(calendar.getDate());
        back.setVisibility(View.INVISIBLE);
        calendarBtn.setVisibility(View.INVISIBLE);
    }

    private void showClasses() {
        Intent intent = new Intent(this, CourseActivity.class);
        startActivity(intent);
    }

    private void showStudents() {
        Intent intent = new Intent(this, StudentActivity.class);
        startActivity(intent);
    }

    private void showReports() {
//        Intent intent = new Intent(this, ReportActivity.class);
//        startActivity(intent);

    }
}