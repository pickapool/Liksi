package com.example.liksi.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.liksi.Interface.CategoryDao;
import com.example.liksi.Interface.TodoDao;
import com.example.liksi.Models.CategoryModel;
import com.example.liksi.Models.TodoModel;

@Database(entities = {CategoryModel.class, TodoModel.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase{
    private static AppDatabase instance;
    public abstract CategoryDao categoryDao();
    public abstract TodoDao todoDao();
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "liksi")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public void clearAllData() {
        instance.clearAllTables();
    }
}
