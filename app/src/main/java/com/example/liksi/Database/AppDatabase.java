package com.example.liksi.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.liksi.Interface.CategoryDao;
import com.example.liksi.Interface.TodoDao;
import com.example.liksi.Models.CategoryModel;
import com.example.liksi.Models.TodoModel;

@Database(entities = {CategoryModel.class, TodoModel.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase{
    private static AppDatabase instance;
    public abstract CategoryDao categoryDao();
    public abstract TodoDao todoDao();
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "liksi")
                    .allowMainThreadQueries()
                    .addMigrations(MIGRATION_3_4)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Migration strategy goes here, if needed
        }
    };

    public void clearAllData() {
        instance.clearAllTables();
    }
}
