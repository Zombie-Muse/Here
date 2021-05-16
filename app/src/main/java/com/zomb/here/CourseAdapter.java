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

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    ArrayList<CourseItem> courseItems;
    Context context;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CourseAdapter(Context context, ArrayList<CourseItem> courseItems) {
        this.courseItems = courseItems;
        this.context = context;
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView courseName;

        public CourseViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            courseName = itemView.findViewById(R.id.tv_course_name);
            itemView.setOnClickListener(v -> onItemClickListener.onClick(getAdapterPosition()));
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(getAdapterPosition(), 0,0,"EDIT");
            menu.add(getAdapterPosition(),1,0,"DELETE");
        }
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View courseView = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        return new CourseViewHolder(courseView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
    holder.courseName.setText(courseItems.get(position).getCourseName());
    }

    @Override
    public int getItemCount() {
        return courseItems.size();
    }

}
