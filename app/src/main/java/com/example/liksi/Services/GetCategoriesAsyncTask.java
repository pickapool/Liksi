package com.example.liksi.Services;
import android.os.AsyncTask;

import com.example.liksi.Database.DatabaseHelper;
import com.example.liksi.Models.CategoryModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class GetCategoriesAsyncTask extends AsyncTask<Void, Void, List<CategoryModel>> {
    private DatabaseHelper databaseHelper;
    private OnCategoriesLoadedListener listener;

    public GetCategoriesAsyncTask(DatabaseHelper databaseHelper, OnCategoriesLoadedListener listener) {
        this.databaseHelper = databaseHelper;
        this.listener = listener;
    }

    @Override
    protected List<CategoryModel> doInBackground(Void... voids) {
        try {
            return databaseHelper.getAllCategories().get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onPostExecute(List<CategoryModel> categories) {
        super.onPostExecute(categories);
        if (listener != null) {
            listener.onCategoriesLoaded(categories);
        }
    }

    public interface OnCategoriesLoadedListener {
        void onCategoriesLoaded(List<CategoryModel> categories);
    }
}
