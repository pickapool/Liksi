package com.example.liksi.Interface;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.liksi.Models.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void AddCategory(Category... cats);

    @Update
    public void UpdateCategory(Category cats);

    @Delete
    public void DeleteCategory(Category cats);

    @Query("SELECT * FROM Category")
    List<Category> Categories();

}
