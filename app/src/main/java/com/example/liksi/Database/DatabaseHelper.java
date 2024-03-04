package com.example.liksi.Database;

import android.content.Context;
import androidx.room.Room;

import com.example.liksi.Models.CategoryModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DatabaseHelper {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final AppDatabase appDatabase;

    public DatabaseHelper(Context context) {
        appDatabase = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, "liksi")
                .build();
    }

    public Future<List<CategoryModel>> getAllCategories() {
        return executorService.submit(() -> appDatabase.categoryDao().Categories());
    }

    public Future<Void> insertCategory(CategoryModel category) {
        return executorService.submit(() -> {
            appDatabase.categoryDao().AddCategory(category);
            return null;
        });
    }

    // Make sure to shutdown the executorService when no longer needed
    public void shutdownExecutorService() {
        executorService.shutdown();
    }
}
