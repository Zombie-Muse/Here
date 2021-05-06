package com.zomb.here;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ClassActivity extends AppCompatActivity {
    Toolbar toolbar;
    private FloatingActionButton floatingActionButton;
    private RecyclerView classList;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Class> classes = new ArrayList<>();
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        dbHelper = new DbHelper(this);
        floatingActionButton = findViewById(R.id.btn_add_class);
        floatingActionButton.setOnClickListener(v -> showAddDialog());

        setToolbar();
    }

    private void showAddDialog() {

    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar_class);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        ImageButton back = toolbar.findViewById(R.id.btn_back);
        ImageButton save = toolbar.findViewById(R.id.btn_save);

        title.setText("Your Classes");
        back.setVisibility(View.VISIBLE);
        save.setVisibility(View.VISIBLE);

        back.setOnClickListener(v -> onBackPressed());

    }

    private void gotoItemActivity(int position) {
        Intent intent = new Intent(this, AttendanceActivity.class);
    }
}
