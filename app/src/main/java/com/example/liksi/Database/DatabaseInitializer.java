package com.example.liksi.Database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseInitializer {
    private static AppDatabase  appDatabase;

    public static AppDatabase  getAppDatabase(Context context) {
        if (appDatabase == null) {
            // Create a new Room database builder instance
            appDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "liksi")
                    // Add migration strategy if needed
                    //.addMigrations(MIGRATION_1_2)
                    // Enable destructive migration if needed
                    //.fallbackToDestructiveMigration()
                    .build();
        }
        return appDatabase;
    }
}
