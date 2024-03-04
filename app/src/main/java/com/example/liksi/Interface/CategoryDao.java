package com.example.liksi.Interface;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.liksi.Models.CategoryModel;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void AddCategory(CategoryModel... cats);

    @Update
    public void UpdateCategory(CategoryModel cats);

    @Delete
    public void DeleteCategory(CategoryModel cats);

    @Query("SELECT * FROM CategoryModel")
    List<CategoryModel> Categories();

}
