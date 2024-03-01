package com.example.liksi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;

import com.example.liksi.Adapters.AdapterToDos;
import com.example.liksi.Models.TodoModel;

import java.util.List;
import java.util.stream.Collectors;

public class    todoFragment extends Fragment {
    RecyclerView recyclerView;
    AdapterToDos adapter;
    LinearLayoutManager linearLayoutManager;
    SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_todo, container, false);

        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = view.findViewById(R.id.todosContainer);
        searchView = view.findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(s.length() > 0)
                {
                    List<TodoModel> list = GlobalClass.ToDoList.stream()
                            .filter(todo -> todo.getTodo().contains(s)) // Apply the condition here
                            .collect(Collectors.toList());
                    adapter = new AdapterToDos(getContext(), list);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    SetList();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s.length() > 0) {
                    List<TodoModel> list = GlobalClass.ToDoList.stream()
                            .filter(todo -> todo.getTodo().contains(s)) // Apply the condition here
                            .collect(Collectors.toList());
                    adapter = new AdapterToDos(getContext(), list);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    SetList();
                }
                return true;
            }
        });


        SetList();
        return view;
    }
    private void SetList()
    {
        adapter = new AdapterToDos(getContext(), GlobalClass.ToDoList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}