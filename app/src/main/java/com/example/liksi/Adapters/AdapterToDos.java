package com.example.liksi.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liksi.Database.AppDatabase;
import com.example.liksi.GlobalClass;
import com.example.liksi.Models.TodoModel;
import com.example.liksi.Models.TodoWithCategoryModel;
import com.example.liksi.R;

import org.w3c.dom.Text;

import java.util.List;

public class AdapterToDos extends RecyclerView.Adapter<AdapterToDos.ViewHolder>{
    private static final int PRIORITY_VIEW_TYPE = 1;
    private static final int REGULAR_VIEW_TYPE = 2;
    Context context;
    List<TodoWithCategoryModel> todos;

    public AdapterToDos(Context context, List<TodoWithCategoryModel> todos) {
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
        TodoWithCategoryModel toDo = todos.get(position);
        return toDo.todoModel.isPriority() ? PRIORITY_VIEW_TYPE : REGULAR_VIEW_TYPE;
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterToDos.ViewHolder holder, int position) {
        TodoWithCategoryModel todo = todos.get(position);
        holder.task.setText(todo.todoModel.getTodo());
        holder.timeAlarm.setText(todo.todoModel.getAlarm());
        SetAlarmicon(holder, todo);
        holder.alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(todo.todoModel.isAlarm())
                {

                    todo.todoModel.isAlarm = false;

                } else {
                    todo.todoModel.isAlarm = true;
                }
                AppDatabase appDatabase = AppDatabase.getInstance(view.getContext());
                appDatabase.todoDao().UpdateTodo(todo.todoModel);
                SetAlarmicon(holder, todo);
            }
        });
        if(todo.categoryModel != null)
        {
            holder.cat.setText(todo.categoryModel.getName());
            //Toast.makeText(context, String.valueOf(todo.categoryModel.getName()), Toast.LENGTH_SHORT).show();
        }
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
                                    todos = app.todoDao().getTodosWithCategory();
                                    holder.checkBox.setChecked(false);
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
    private void SetAlarmicon(ViewHolder holder, TodoWithCategoryModel todo)
    {
        if(todo.todoModel.isAlarm())
        {
            holder.alarm.setImageResource(R.drawable.baseline_alarm_on_24);
        } else {
            holder.alarm.setImageResource(R.drawable.baseline_alarm_off_24);
        }
    }
    @Override
    public int getItemCount() {
        return todos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView task, cat, timeAlarm;
        ImageView alarm;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            task = itemView.findViewById(R.id.task);
            cat = itemView.findViewById(R.id.categoryName);
            alarm = itemView.findViewById(R.id.onOffAlarm);
            timeAlarm = itemView.findViewById(R.id.timeAlarm);
        }
    }
}
