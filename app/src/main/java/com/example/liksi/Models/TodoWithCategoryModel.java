package com.example.liksi.Models;

import androidx.room.Embedded;

public class TodoWithCategoryModel {
    @Embedded
    public TodoModel todoModel;

    @Embedded
    public CategoryModel categoryModel;
}
