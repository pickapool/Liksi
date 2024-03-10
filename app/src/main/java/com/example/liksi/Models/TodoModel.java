package com.example.liksi.Models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = CategoryModel.class,
        parentColumns = "catId",
        childColumns = "categoryId",
        onDelete = ForeignKey.CASCADE))
public class TodoModel {
    @PrimaryKey(autoGenerate = true)
    public int todoId;
    public String todo;
    public boolean isPriority;
    public int categoryId;
    public String alarm;
    public boolean isAlarm;
    public TodoModel(){}

    public TodoModel(int todoId, String todo, boolean isPriority, int categoryId, String alarm, boolean isAlarm) {
        this.todoId = todoId;
        this.todo = todo;
        this.isPriority = isPriority;
        this.categoryId = categoryId;
        this.alarm = alarm;
        this.isAlarm = isAlarm;
    }

    public boolean isAlarm() {
        return isAlarm;
    }

    public void setAlarm(boolean alarm) {
        isAlarm = alarm;
    }

    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }

    public int getTodoId() {
        return todoId;
    }

    public void setTodoId(int todoId) {
        this.todoId = todoId;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public boolean isPriority() {
        return isPriority;
    }

    public void setPriority(boolean priority) {
        isPriority = priority;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
