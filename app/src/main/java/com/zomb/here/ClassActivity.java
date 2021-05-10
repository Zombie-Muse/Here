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

public class ClassActivity extends AppCompatActivity {
    Toolbar toolbar;
    private TextView greeting;
    private TextView date;
    private FloatingActionButton floatingActionButton;
    private RecyclerView classList;
    private ClassAdapter classAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ClassItem> classItems = new ArrayList<>();
    private DbHelper dbHelper;
    private MyCalendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        greeting = findViewById(R.id.tv_add_class_text);
        dbHelper = new DbHelper(this);
        floatingActionButton = findViewById(R.id.btn_add_class);
        floatingActionButton.setOnClickListener(v -> showAddDialog());
        calendar = new MyCalendar();
        loadData();

        classList = findViewById(R.id.rv_class_list);
        layoutManager = new LinearLayoutManager(this);
        classList.setLayoutManager(layoutManager);
        classAdapter = new ClassAdapter(this, classItems);
        classList.setAdapter(classAdapter);
        classAdapter.setOnItemClickListener(position -> gotoItemActivity(position));

        setToolbar();
        date = toolbar.findViewById(R.id.toolbar_date);
    }

    private void loadData() {
        Cursor cursor = dbHelper.getClassTable();
        classItems.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DbHelper.SUBJECT_ID));
            String className = cursor.getString(cursor.getColumnIndex(DbHelper.SUBJECT_NAME_KEY));

            classItems.add(new ClassItem(id, className));
            if (!classItems.isEmpty()) {
                greeting.setVisibility(View.GONE);
            }
        }
    }

    private void showAddDialog() {
        MyDialog dialog = new MyDialog();
        dialog.show(getSupportFragmentManager(), MyDialog.CLASS_ADD_DIALOG);
        dialog.setListener((className) -> addClass(className));
    }

    private void addClass(String className) {
        int classId = (int) dbHelper.addClass(className);
        ClassItem classItem = new ClassItem(classId, className);
        classItems.add(classItem);
        classAdapter.notifyDataSetChanged();
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar_class);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        date = toolbar.findViewById(R.id.toolbar_date);
        ImageButton back = toolbar.findViewById(R.id.btn_back);
        ImageButton calendarBtn = toolbar.findViewById(R.id.btn_calendar);

        title.setText(R.string.your_classes);
        date.setText(calendar.getDate());
        back.setVisibility(View.VISIBLE);
        calendarBtn.setVisibility(View.VISIBLE);

        back.setOnClickListener(v -> onBackPressed());
    }

    private void gotoItemActivity(int position) {
        Intent intent = new Intent(this, StudentActivity.class);
        intent.putExtra("className", classItems.get(position).getClassName());
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                showUpdateDialog(item.getGroupId());
                break;
            case 1:
                deleteClass(item.getGroupId());
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void showUpdateDialog(int position) {
        MyDialog dialog = new MyDialog();
        dialog.show(getSupportFragmentManager(), MyDialog.CLASS_UPDATE_DIALOG);
        dialog.setListener((className) -> updateClass(position, className));
    }

    private void updateClass(int position, String className) {
        dbHelper.updateClass(classItems.get(position).getClassId(), className);
        classItems.get(position).setClassName(className);
        classAdapter.notifyItemChanged(position);
    }

    private void deleteClass(int position) {
        dbHelper.deleteClass(classItems.get(position).getClassId());
        classItems.remove(position);
        classAdapter.notifyItemRemoved(position);
    }
}
