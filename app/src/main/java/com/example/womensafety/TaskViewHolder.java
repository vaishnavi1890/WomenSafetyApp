package com.example.womensafety;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class TaskViewHolder extends RecyclerView.ViewHolder {
    private TextView loctext;

    public TaskViewHolder(View itemView) {
        super(itemView);
        loctext = itemView.findViewById(R.id.loc);

    }

    public void bind(Task task) {
        loctext.setText(task.getLocation());

    }
}
