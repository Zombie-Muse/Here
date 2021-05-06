package com.zomb.here;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;

public class MyDialog extends DialogFragment {
    public static final String CLASS_ADD_DIALOG = "addClass";
    public static final String CLASS_UPDATE_DIALOG = "updateClass";
    public static final String STUDENT_ADD_DIALOG = "addStudent";
    public static final String STUDENT_UPDATE_DIALOG = "updateStudent";

    private int size = 0;
    OnClickListener listener;
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
        if (getTag().equals(CLASS_ADD_DIALOG)) {
            dialog = getAddClassDialog();
        }
        if (getTag().equals(CLASS_UPDATE_DIALOG)) {
            dialog = getUpdateClassDialog();
        }
        if (getTag().equals(STUDENT_ADD_DIALOG)) {
            dialog = getAddStudentDialog();
        }
        if (getTag().equals(STUDENT_UPDATE_DIALOG)) {
            dialog = getUpdateStudentDialog();
        }

        return dialog;
    }

    private Dialog getUpdateStudentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);

        TextView title = view.findViewById(R.id.titleDialog);
        title.setText(R.string.update_class);
        EditText edt_class = view.findViewById(R.id.edt01);
        edt_class.setHint("Class Name");
        EditText edt_02 = view.findViewById(R.id.edt02);
        edt_02.setText("");
        edt_02.setVisibility(View.GONE);

        Button add = view.findViewById(R.id.btn_add_class);
        Button cancel = view.findViewById(R.id.btn_cancel);

        cancel.setOnClickListener(v -> dismiss());
        add.setOnClickListener(v -> {
            String className = edt_class.getText().toString();
            listener.onClick(className);
            dismiss();
        });

        return builder.create();
    }

    private Dialog getAddStudentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);

        // todo: double check all editText fields for required database data
        TextView title = view.findViewById(R.id.titleDialog);
        title.setText(R.string.add_student);
        EditText edt_class = view.findViewById(R.id.edt01);
        edt_class.setHint(R.string.student_hint);
        EditText edt_02 = view.findViewById(R.id.edt02);
        edt_02.setText("");
        edt_02.setVisibility(View.GONE);

        Button add = view.findViewById(R.id.btn_add_student);
        add.setText(R.string.update);
        Button cancel = view.findViewById(R.id.btn_cancel);

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

        TextView title = view.findViewById(R.id.titleDialog);
        title.setText(R.string.update_class);
        EditText edt_class = view.findViewById(R.id.edt01);
        edt_class.setHint(R.string.class_hint);
        EditText edt_02 = view.findViewById(R.id.edt02);
        edt_02.setText("");
        edt_02.setVisibility(View.GONE);

        Button add = view.findViewById(R.id.btn_add_class);
        Button cancel = view.findViewById(R.id.btn_cancel);

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

        TextView title = view.findViewById(R.id.titleDialog);
        title.setText(R.string.add_class);
        EditText edt_class = view.findViewById(R.id.edt01);
        edt_class.setHint("Class Name");
        EditText edt_02 = view.findViewById(R.id.edt02);
        edt_02.setText("");
        edt_02.setVisibility(View.GONE);

        Button add = view.findViewById(R.id.btn_add_class);
        add.setText(R.string.update);
        Button cancel = view.findViewById(R.id.btn_cancel);

        cancel.setOnClickListener(v -> dismiss());
        add.setOnClickListener(v -> {
            String className = edt_class.getText().toString();
            listener.onClick(className);
            dismiss();
        });

        return builder.create();
    }
}
