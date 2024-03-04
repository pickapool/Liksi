package com.example.liksi.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liksi.Database.AppDatabase;
import com.example.liksi.Models.TodoModel;
import com.example.liksi.Models.TodoWithCategoryModel;
import com.example.liksi.R;

import java.util.List;
import java.util.stream.Collectors;

public class AdapterPriorities extends RecyclerView.Adapter<AdapterPriorities.ViewHolder>{

    Context context;
    List<TodoWithCategoryModel> todos;

    public AdapterPriorities(Context context, List<TodoWithCategoryModel> todos) {
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
        TodoWithCategoryModel todo = todos.get(position);
        holder.task.setText(todo.todoModel.getTodo());
        holder.cat.setText(todo.categoryModel.getName());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(false);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Finished task will be deleted.");
                    builder.setPositiveButton("Confirm",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    AppDatabase app = AppDatabase.getInstance(context);
                                    app.todoDao().DeleteTodo(todo.todoModel);
                                    todos = app.todoDao().getTodosWithCategory().stream()
                                            .filter( e -> e.todoModel.isPriority() == true) // Apply the condition here
                                            .collect(Collectors.toList());
                                    notifyDataSetChanged();
                                }
                            });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            holder.checkBox.setChecked(false);
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView task,cat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            task = itemView.findViewById(R.id.task);
            cat = itemView.findViewById(R.id.categoryName);
        }
    }
}
