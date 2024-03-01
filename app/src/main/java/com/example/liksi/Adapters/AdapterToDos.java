package com.example.liksi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liksi.GlobalClass;
import com.example.liksi.Models.TodoModel;
import com.example.liksi.R;

import org.w3c.dom.Text;

import java.util.List;

public class AdapterToDos extends RecyclerView.Adapter<AdapterToDos.ViewHolder>{
    private static final int PRIORITY_VIEW_TYPE = 1;
    private static final int REGULAR_VIEW_TYPE = 2;
    Context context;
    List<TodoModel> todos;

    public AdapterToDos(Context context, List<TodoModel> todos) {
        this.context = context;
        this.todos = todos;
    }

    @NonNull
    @Override
    public AdapterToDos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == PRIORITY_VIEW_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_priority_template, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_todo_template, parent, false);
        }
        return new ViewHolder(view);
    }
    @Override
    public int getItemViewType(int position) {
        TodoModel toDo = todos.get(position);
        return toDo.isPriority() ? PRIORITY_VIEW_TYPE : REGULAR_VIEW_TYPE;
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterToDos.ViewHolder holder, int position) {
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
