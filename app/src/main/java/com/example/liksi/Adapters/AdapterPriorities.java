package com.example.liksi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liksi.Models.TodoModel;
import com.example.liksi.R;

import java.util.List;

public class AdapterPriorities extends RecyclerView.Adapter<AdapterPriorities.ViewHolder>{

    Context context;
    List<TodoModel> todos;

    public AdapterPriorities(Context context, List<TodoModel> todos) {
        this.context = context;
        this.todos = todos;
    }
    @NonNull
    @Override
    public AdapterPriorities.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_priority_template,null);
        return new AdapterPriorities.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPriorities.ViewHolder holder, int position) {
        TodoModel todo = todos.get(position);
        holder.task.setText(todo.getTodo());
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView task;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            task = itemView.findViewById(R.id.task);
        }
    }
}
