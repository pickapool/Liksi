package com.example.liksi.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.liksi.Interface.CategoryDao;
import com.example.liksi.Interface.TodoDao;
import com.example.liksi.Models.Category;
import com.example.liksi.Models.TodoModel;

@Database(entities = {Category.class, TodoModel.class}, version = 2)
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
}
