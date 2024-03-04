package com.example.liksi.Interface;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.liksi.Models.TodoModel;

import java.util.List;
@Dao
public interface TodoModelDao {
    @Query("SELECT * FROM TodoModel")
    List<TodoModel> getAllTodoModels();

    @Insert
    void insert(TodoModel todoModel);
    // Other CRUD operations as needed
}
