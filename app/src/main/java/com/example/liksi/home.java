package com.example.liksi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class home extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        bottomNavigationView.setItemActiveIndicatorHeight((int) (90 * getResources().getDisplayMetrics().density));
        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                new todoFragment()).commit();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    if (item.getItemId() == R.id.todobutton) {
                        selectedFragment = new todoFragment();
                    } else if (item.getItemId() == R.id.categorybutton) {
                        selectedFragment = new categoryFragment();
                    } else if (item.getItemId() == R.id.addbutton) {
                        selectedFragment = new createTodoFragment();
                    } else if (item.getItemId() == R.id.prioritybutton) {
                        selectedFragment = new priorityFragment();
                    } else if (item.getItemId() == R.id.settingsbutton) {
                        selectedFragment = new settingsFragment();
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.container,
                            selectedFragment).commit();

                    return true;
                }

            };
}