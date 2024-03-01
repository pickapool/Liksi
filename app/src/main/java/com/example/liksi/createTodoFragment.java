package com.example.liksi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.liksi.Models.Category;
import com.example.liksi.Models.TodoModel;

import java.util.ArrayList;


public class createTodoFragment extends Fragment {


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

        ArrayList<String> categoryNames = new ArrayList<>();
        for (Category cat1 : GlobalClass.ListOfCategories) {
            categoryNames.add(cat1.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categoryNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TodoModel model = new TodoModel();
                model.setPriority(priority.isChecked());
                model.setTodo(todo.getText().toString());

                Category cat = new Category();
                cat.setName(category.getSelectedItem().toString());

                GlobalClass.ToDoList.add(model);


                Toast.makeText(getContext(), "Task added.", Toast.LENGTH_SHORT).show();
                todo.setText("");

            }
        });

        return view;
    }
}