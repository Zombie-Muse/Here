package com.zomb.here;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    ArrayList<StudentItem> studentItems;
    Context context;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public StudentAdapter(Context context, ArrayList<StudentItem> studentItems) {
        this.studentItems = studentItems;
        this.context = context;
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView studentName;

        public StudentViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            studentName = itemView.findViewById(R.id.tv_student_name);
            itemView.setOnClickListener(v -> onItemClickListener.onClick(getAdapterPosition()));
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(getAdapterPosition(), 0, 0, "EDIT");
            menu.add(getAdapterPosition(), 1, 0, "DELETE");
        }
    }
    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View studentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        return new StudentViewHolder(studentView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.StudentViewHolder holder, int position) {
        holder.studentName.setText(studentItems.get(position).getStudentName());
    }

    @Override
    public int getItemCount() {
        return studentItems.size();
    }

}
