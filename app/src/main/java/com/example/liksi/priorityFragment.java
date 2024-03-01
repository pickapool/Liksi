package com.example.liksi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liksi.Adapters.AdapterPriorities;
import com.example.liksi.Adapters.AdapterToDos;
import com.example.liksi.Models.TodoModel;

import java.util.List;
import java.util.stream.Collectors;


public class priorityFragment extends Fragment {
    RecyclerView recyclerView;
    AdapterPriorities adapter;
    LinearLayoutManager linearLayoutManager;

    public priorityFragment() {
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
        View view = inflater.inflate(R.layout.fragment_priority, container, false);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = view.findViewById(R.id.priorities);

        List<TodoModel> priorities = GlobalClass.ToDoList.stream()
                .filter(TodoModel::isPriority) // Apply the condition here
                .collect(Collectors.toList());

        adapter = new AdapterPriorities(getContext(), priorities);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }
}