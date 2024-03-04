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
    public TodoModel(){}

    public TodoModel(int todoId, String todo, boolean isPriority, int categoryId) {
        this.todoId = todoId;
        this.todo = todo;
        this.isPriority = isPriority;
        this.categoryId = categoryId;
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
