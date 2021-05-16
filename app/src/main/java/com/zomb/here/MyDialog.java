package com.zomb.here;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;

public class MyDialog extends DialogFragment {
    public static final String COURSE_ADD_DIALOG = "addClass";
    public static final String COURSE_UPDATE_DIALOG = "updateClass";
    public static final String STUDENT_ADD_DIALOG = "addStudent";
    public static final String STUDENT_UPDATE_DIALOG = "updateStudent";
    public static final String STATUS_ADD_DIALOG = "addStatus";
    public static final String STATUS_UPDATE_DIALOG = "updateStatus";

    private int size = 0;
    RadioButton status;
    RadioGroup radioGroup;
    OnClickListener listener;
    TextView title;
    TextView courseTxt;
    Button add, cancel;
    Spinner spinner;

    public interface OnClickListener {
        void onClick(String text);
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = null;
        if (getTag().equals(COURSE_ADD_DIALOG)) {
            dialog = getAddClassDialog();
        }
        if (getTag().equals(COURSE_UPDATE_DIALOG)) {
            dialog = getUpdateClassDialog();
        }
        if (getTag().equals(STUDENT_ADD_DIALOG)) {
            dialog = getAddStudentDialog();
        }
        if (getTag().equals(STUDENT_UPDATE_DIALOG)) {
            dialog = getUpdateStudentDialog();
        }
        if (getTag().equals(STATUS_ADD_DIALOG)) {
            dialog = getAddStatusDialog();
        }
        if (getTag().equals(STATUS_UPDATE_DIALOG)) {
            dialog = getUpdateStatusDialog();
        }

        return dialog;
    }
    //todo: add updateStatus dialog
    private Dialog getUpdateStatusDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);

        title = view.findViewById(R.id.titleDialog);
        title.setText(R.string.update_student);
        EditText edt_student = view.findViewById(R.id.edt01);
        edt_student.setHint(R.string.student_hint);
        EditText edt_02 = view.findViewById(R.id.edt02);
        edt_02.setVisibility(View.GONE);
        add = view.findViewById(R.id.btn_add);
        cancel = view.findViewById(R.id.btn_cancel);

        Spinner spinner = view.findViewById(R.id.sp_courses);
        RadioGroup radioGroup = view.findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                onRadioButtonClicked(checkedId);
                status = view.findViewById(checkedId);

            }
            });
        cancel.setOnClickListener(v -> dismiss());
        add.setText(R.string.update);
        add.setOnClickListener(v -> {
            String studentName = edt_student.getText().toString();
            listener.onClick(studentName);
            dismiss();
        });
        return builder.create();
    }

    //todo: add addStatus dialog
    private Dialog getAddStatusDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);

        title = view.findViewById(R.id.titleDialog);
        title.setText("Student");
        EditText edt_student = view.findViewById(R.id.edt01);
        edt_student.setHint(R.string.student_hint);
        EditText edt_02 = view.findViewById(R.id.edt02);
        edt_02.setVisibility(View.GONE);
        add = view.findViewById(R.id.btn_add);
        cancel = view.findViewById(R.id.btn_cancel);

        Spinner spinner = view.findViewById(R.id.sp_courses);
        RadioGroup radioGroup = view.findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                onRadioButtonClicked(checkedId);
                status = view.findViewById(checkedId);

            }
        });
        cancel.setOnClickListener(v -> dismiss());
        add.setText(R.string.update);
        add.setOnClickListener(v -> {
            String studentName = edt_student.getText().toString();
            listener.onClick(studentName);
            dismiss();
        });
        return builder.create();
    }

    private Dialog getUpdateStudentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);

        title = view.findViewById(R.id.titleDialog);
        title.setText(R.string.update_student);
        EditText edt_class = view.findViewById(R.id.edt01);
        edt_class.setHint("Class Name");
        EditText edt_02 = view.findViewById(R.id.edt02);
        edt_02.setText("");
        edt_02.setVisibility(View.GONE);


        Spinner spinner = view.findViewById(R.id.sp_courses);
//        CursorAdapter<>
//        spinner.setOnItemSelectedListener(this);

        add = view.findViewById(R.id.btn_add);
        cancel = view.findViewById(R.id.btn_cancel);
        RadioGroup radioGroup;
        radioGroup = view.findViewById(R.id.radiogroup);
        radioGroup.setVisibility(view.GONE);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                onRadioButtonClicked(checkedId);
                status = view.findViewById(checkedId);

            }
        });

        cancel.setOnClickListener(v -> dismiss());
        add.setText(R.string.update);
        add.setOnClickListener(v -> {
            String courseName = edt_course.getText().toString();
            listener.onClick(courseName);
            dismiss();
        });

        return builder.create();
    }

    private Dialog getAddStudentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);

        // todo: double check all editText fields for required database data
        title = view.findViewById(R.id.titleDialog);
        title.setText(R.string.add_student);
        EditText edt_class = view.findViewById(R.id.edt01);
        edt_class.setHint(R.string.student_hint);
        EditText edt_02 = view.findViewById(R.id.edt02);
        edt_02.setText("");
        edt_02.setVisibility(View.GONE);
        radioGroup = view.findViewById(R.id.radiogroup);
        radioGroup.setVisibility(view.GONE);

        add = view.findViewById(R.id.btn_add);
        cancel = view.findViewById(R.id.btn_cancel);

        cancel.setOnClickListener(v -> dismiss());
        add.setOnClickListener(v -> {
            String className = edt_class.getText().toString();
            listener.onClick(className);
            dismiss();
        });

        return builder.create();
    }

    private Dialog getUpdateClassDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);

        title = view.findViewById(R.id.titleDialog);
        title.setText(R.string.update_class);
        EditText edt_class = view.findViewById(R.id.edt01);
        edt_class.setHint(R.string.class_hint);
        EditText edt_02 = view.findViewById(R.id.edt02);
        edt_02.setText("");
        edt_02.setVisibility(View.GONE);
        radioGroup = view.findViewById(R.id.radiogroup);
        radioGroup.setVisibility(view.GONE);
        courseTxt = view.findViewById(R.id.tv_course);
        courseTxt.setVisibility(view.GONE);
        spinner = view.findViewById(R.id.sp_courses);
        spinner.setVisibility(view.GONE);
        add = view.findViewById(R.id.btn_add);
        add.setText("Update");
        cancel = view.findViewById(R.id.btn_cancel);

        cancel.setOnClickListener(v -> dismiss());
        add.setOnClickListener(v -> {
            String className = edt_class.getText().toString();
            listener.onClick(className);
            dismiss();
        });

        return builder.create();
    }

    private Dialog getAddClassDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);

        title = view.findViewById(R.id.titleDialog);
        title.setText(R.string.add_class);
        EditText edt_class = view.findViewById(R.id.edt01);
        edt_class.setHint("Class Name");
        EditText edt_02 = view.findViewById(R.id.edt02);
        edt_02.setText("");
        edt_02.setVisibility(View.GONE);
        radioGroup = view.findViewById(R.id.radiogroup);
        radioGroup.setVisibility(view.GONE);
        courseTxt = view.findViewById(R.id.tv_course);
        courseTxt.setVisibility(view.GONE);
        spinner = view.findViewById(R.id.sp_courses);
        spinner.setVisibility(view.GONE);
        add = view.findViewById(R.id.btn_add);
        cancel = view.findViewById(R.id.btn_cancel);

        cancel.setOnClickListener(v -> dismiss());
        add.setOnClickListener(v -> {
            String className = edt_class.getText().toString();
            listener.onClick(className);
            dismiss();
        });

        return builder.create();
    }

    public void onRadioButtonClicked(int checkedId) {

        switch(checkedId) {
            case R.id.present:
                    Toast.makeText(getContext(), "Student is present", Toast.LENGTH_SHORT).show();
                    // todo: implement adding to attendance table
                break;
            case R.id.absent:

                    Toast.makeText(getContext(), "Student is absent", Toast.LENGTH_SHORT).show();
                    // todo: implement adding to attendance table
                break;
            case R.id.tardy:

                    Toast.makeText(getContext(), "Student is tardy", Toast.LENGTH_SHORT).show();
                    // todo: implement adding to attendance database
                break;
        }
    }
}
