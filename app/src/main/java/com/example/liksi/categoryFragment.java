package com.example.liksi;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.liksi.Adapters.AdapterCategory;
import com.example.liksi.Database.AppDatabase;
import com.example.liksi.Models.CategoryModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.stream.Collectors;

public class categoryFragment extends Fragment {
    RecyclerView recyclerView;
    AdapterCategory adapter;
    LinearLayoutManager linearLayoutManager;
    SearchView searchView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        FloatingActionButton btn = view.findViewById(R.id.fab);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog lDialog = new Dialog(getContext());
                WindowManager.LayoutParams lWindowParams = new WindowManager.LayoutParams();
                lWindowParams.copyFrom(lDialog.getWindow().getAttributes());
                lWindowParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                lWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lWindowParams.horizontalMargin = (int) getResources().getDimension(R.dimen.dialog_margin);
                lDialog.setContentView(R.layout.fragment_create_category);
                lDialog.setCancelable(true);

                Drawable backgroundDrawable = ContextCompat.getDrawable(getContext(), R.drawable.dialog_background);
                lDialog.getWindow().setBackgroundDrawable(backgroundDrawable);

                lDialog.getWindow().getDecorView().setPadding(
                        (int) getResources().getDimension(R.dimen.dialog_margin), 0,
                        (int) getResources().getDimension(R.dimen.dialog_margin), 0
                );

                lDialog.show();
                lDialog.getWindow().setAttributes(lWindowParams);

                EditText cate = lDialog.findViewById(R.id.categoryname);
                EditText des = lDialog.findViewById(R.id.categorydesc);
                Button save = lDialog.findViewById(R.id.button);

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CategoryModel cat = new CategoryModel();
                        cat.setName(cate.getText().toString());
                        cat.setDescription(des.getText().toString());
                        if(cate.getText().toString().trim().equals("") || des.getText().toString().trim().equals(""))
                        {
                            Toast.makeText(getContext(), "All fields are required!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        try {
                            AppDatabase db = AppDatabase.getInstance(getContext());
                            db.categoryDao().AddCategory(cat);
                        } catch (Exception ee)
                        {
                            System.out.println("1992-" + ee.getMessage());
                        }
                        lDialog.dismiss();
                        SetList();
                    }
                });

            }
        });

        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = view.findViewById(R.id.container);
        searchView = view.findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(s.length() > 0)
                {
                    AppDatabase db = AppDatabase.getInstance(getContext());
                    List<CategoryModel> list = db.categoryDao().Categories().stream()
                            .filter(todo -> todo.getName().contains(s)) // Apply the condition here
                            .collect(Collectors.toList());
                    adapter = new AdapterCategory(getContext(), list);
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
                    AppDatabase db = AppDatabase.getInstance(getContext());
                    List<CategoryModel> list = db.categoryDao().Categories().stream()
                            .filter(todo -> todo.getName().contains(s)) // Apply the condition here
                            .collect(Collectors.toList());
                    adapter = new AdapterCategory(getContext(), list);
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
        try {
            AppDatabase db = AppDatabase.getInstance(getContext());
            adapter = new AdapterCategory(getContext(), db.categoryDao().Categories());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            adapter.setOnItemClickListener(new AdapterCategory.OnItemClickListener() {
                @Override
                public void onItemRightDrawableClick(CategoryModel category) {
                    // Perform fragment change here using category information if needed
                    // For example:
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, new tasksOnCat(category));
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        }catch (Exception ee)
        {
            System.out.println("99911-" + ee.getMessage());
        }
    }
}