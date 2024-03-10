package com.example.liksi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liksi.Adapters.AdapterCategory;
import com.example.liksi.Adapters.AdapterToDos;
import com.example.liksi.Database.AppDatabase;
import com.example.liksi.Models.CategoryModel;
import com.example.liksi.Models.TodoModel;
import com.example.liksi.Models.TodoWithCategoryModel;

import java.util.List;

public class tasksOnCat extends Fragment {

    CategoryModel cat;
    AdapterToDos adapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    TextView title;
    public tasksOnCat(CategoryModel cat) {
        this.cat = cat;
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tasks_on_cat, container, false);

        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = view.findViewById(R.id.tasks);
        title = view.findViewById(R.id.ctname);
        title.setText(cat.getName());

        AppDatabase appDatabase = AppDatabase.getInstance(getContext());
        List<TodoWithCategoryModel> todoModelList = appDatabase.todoDao().getAllTodosCategory(cat.getCatId());

        adapter = new AdapterToDos(getContext(), todoModelList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }
}