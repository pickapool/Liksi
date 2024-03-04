package com.example.liksi.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.liksi.Database.AppDatabase;
import com.example.liksi.Interface.CategoryDao;
import com.example.liksi.Models.CategoryModel;

import java.util.List;

public class CategoryRepository {
    private CategoryDao dao;
    private List<CategoryModel> allCourses;

    // creating a constructor for our variables
    // and passing the variables to it.
    public CategoryRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        dao = database.categoryDao();
        allCourses = dao.Categories();
    }

    // creating a method to insert the data to our database.
    public void insert(CategoryModel model) {
        new InsertCourseAsyncTask(dao).execute(model);
    }

    // creating a method to update data in database.
    public void update(CategoryModel model) {
        new UpdateCourseAsyncTask(dao).execute(model);
    }

    // creating a method to delete the data in our database.
    public void delete(CategoryModel model) {
        new DeleteCourseAsyncTask(dao).execute(model);
    }

    // below method is to read all the courses.
    public List<CategoryModel> getAllCategories() {
        return allCourses;
    }

    // we are creating a async task method to insert new course.
    private static class InsertCourseAsyncTask extends AsyncTask<CategoryModel, Void, Void> {
        private CategoryDao dao;

        private InsertCourseAsyncTask(CategoryDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(CategoryModel... model) {
            // below line is use to insert our modal in dao.
            dao.AddCategory(model[0]);
            return null;
        }
    }

    // we are creating a async task method to update our course.
    private static class UpdateCourseAsyncTask extends AsyncTask<CategoryModel, Void, Void> {
        private CategoryDao dao;

        private UpdateCourseAsyncTask(CategoryDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(CategoryModel... models) {
            // below line is use to update
            // our modal in dao.
            dao.UpdateCategory(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete course.
    private static class DeleteCourseAsyncTask extends AsyncTask<CategoryModel, Void, Void> {
        private CategoryDao dao;

        private DeleteCourseAsyncTask(CategoryDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(CategoryModel... models) {
            // below line is use to delete
            // our course modal in dao.
            dao.DeleteCategory(models[0]);
            return null;
        }
    }
}

