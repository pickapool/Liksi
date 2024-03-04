package com.example.liksi.ViewModal;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.liksi.Models.CategoryModel;
import com.example.liksi.Repositories.CategoryRepository;

import java.util.List;

public class CategoryViewModal  extends AndroidViewModel {
    private CategoryRepository repository;

    // below line is to create a variable for live
    // data where all the courses are present.
    private List<CategoryModel> allCategories;

    public CategoryViewModal(@NonNull Application application) {
        super(application);
        repository = new CategoryRepository(application);
        allCategories = repository.getAllCategories();
    }
    public void insert(CategoryModel model) {
        repository.insert(model);
    }

    public void update(CategoryModel model) {
        repository.update(model);
    }

    public void delete(CategoryModel model) {
        repository.delete(model);
    }
    public List<CategoryModel> GetAllCategories() {
        return allCategories;
    }
}
