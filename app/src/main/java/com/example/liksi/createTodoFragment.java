package com.example.liksi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liksi.Database.AppDatabase;
import com.example.liksi.Models.CategoryModel;
import com.example.liksi.Models.TodoModel;


public class createTodoFragment extends Fragment {

    int CategoryId = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_create_todo, container, false);
        EditText todo = view.findViewById(R.id.createTodo);
        ImageButton send = view.findViewById(R.id.send);
        Switch priority = view.findViewById(R.id.setPriority);
        Spinner category = view.findViewById(R.id.selectCategory);

        AppDatabase app = AppDatabase.getInstance(getContext());

        ArrayAdapter<CategoryModel> adapter = new ArrayAdapter<CategoryModel>(getContext(), android.R.layout.simple_spinner_item, app.categoryDao().Categories()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                CategoryModel categoryModel = getItem(position);
                if (categoryModel != null) {
                    textView.setText(categoryModel.getName());
                }
                return textView;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                CategoryModel categoryModel = getItem(position);
                if (categoryModel != null) {
                    textView.setText(categoryModel.getName());
                }
                return textView;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                CategoryModel selectedCategoryModel = (CategoryModel) parentView.getItemAtPosition(position);
                CategoryId = selectedCategoryModel.getCatId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TodoModel model = new TodoModel();
                model.setPriority(priority.isChecked());
                model.setTodo(todo.getText().toString());
                model.setCategoryId(CategoryId);
                if(todo.getText().toString().trim().equals(""))
                {
                    Toast.makeText(getContext(), "Task is required!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(CategoryId <= 0)
                {
                    Toast.makeText(getContext(), "Please add category!", Toast.LENGTH_SHORT).show();
                    return;
                }
                AppDatabase app = AppDatabase.getInstance(getContext());
                app.todoDao().AddTodo(model);
                Toast.makeText(getContext(), "Task added.", Toast.LENGTH_SHORT).show();
                todo.setText("");

            }
        });

        return view;
    }
}