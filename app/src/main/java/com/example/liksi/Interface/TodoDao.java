package com.example.liksi.Interface;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.liksi.Models.Category;
import com.example.liksi.Models.TodoModel;

import java.util.List;

@Dao
public interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void AddTodo(TodoModel... todos);

    @Update
    public void UpdateTodo(TodoModel todo);

    @Delete
    public void DeleteTodo(TodoModel todo);

    @Query("SELECT * FROM TodoModel")
    List<TodoModel> getAllTodos();

}
