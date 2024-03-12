package com.example.liksi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liksi.Database.AppDatabase;
import com.example.liksi.Models.TodoWithCategoryModel;
import com.example.liksi.Services.NotificationService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(view ->{
            Intent i = new Intent(this, home.class);
            startActivity(i);
        });


        textView = findViewById(R.id.quote);

        Intent serviceIntent = new Intent(MainActivity.this, NotificationService.class);
        startService(serviceIntent);
        runnable.run();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // Execute database query here
            try {
                Random random = new Random();
                int randomIndex = random.nextInt(Quotes().size());
                String randomQuote = Quotes().get(randomIndex);
                textView.setText(randomQuote);
            }catch (Exception ee)
            {
                System.out.println("error998" + ee.getMessage());
            }

            handler.postDelayed(this, 5000);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        // Check if the dark_mode preference is not set
        if (!sharedPreferences.contains("dark_mode")) {
            // Set the default mode (e.g., light mode)
            boolean defaultModeIsDark = false; // Set to true for dark mode, false for light mode
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("dark_mode", defaultModeIsDark);
            editor.apply();
        }


        // Apply the theme based on the current preference
        boolean isDarkMode = sharedPreferences.getBoolean("dark_mode", false);
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

    }
    private List<String> Quotes()
    {
        List<String> lis = new ArrayList<>();

        lis.add("“Pleasure in the job puts perfection in the work.”\n- Aristotle");
        lis.add("“It is not the mountain we conquer, but ourselves.”\n- Sir Edmund Hillary");
        lis.add("“Time is gold.”\n-enricowru");
        lis.add("“Time is the school in which we learn, time is the fire in which we burn.”]\n- Delmore Schwartz");
        lis.add("“Productivity is being able to do things that you were never able to do before.”\n- Franz Kafka");
        lis.add("“Time is not refundable; use it with intention.”\n- Unknown");
        lis.add("“The best way to predict the future is to create it.”\n- Peter Drucker");
        lis.add("“The secret of getting ahead is getting started.”\n- Mark Twain");
        lis.add("“Don't watch the clock; do what it does. Keep going.”\n- Sam Levenson");
        lis.add("“Don't count the days, make the days count.”\n- Muhammad Ali");
        lis.add("“The future depends on what you do today.”\n- Mahatma Gandhi");

        return lis;
    }
}