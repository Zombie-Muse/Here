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

    private String user = "Christy";
    TextView greeting;
    AppCompatButton classes;
    AppCompatButton students;
    AppCompatButton reports;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        greeting = findViewById(R.id.tv_greeting);
        greeting.setText("Greetings, " + user + "!");

        classes = findViewById(R.id.btn_classes);
        classes.setOnClickListener(v -> showClasses());

        students = findViewById(R.id.btn_students);
        students.setOnClickListener(v -> showStudents());

        reports = findViewById(R.id.btn_reports);
        reports.setOnClickListener(v -> showReports());
        setToolbar();
        setSupportActionBar(toolbar);

    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar_main);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        ImageButton back = toolbar.findViewById(R.id.btn_back);
        ImageButton save = toolbar.findViewById(R.id.btn_save);

        title.setText("Here!");
        back.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);
    }

    private void showClasses() {
        Intent intent = new Intent(this, ClassActivity.class);
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