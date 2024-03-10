package com.example.liksi.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.liksi.Database.AppDatabase;
import com.example.liksi.Models.TodoModel;
import com.example.liksi.Models.TodoWithCategoryModel;
import com.example.liksi.R;
import com.example.liksi.home;

import android.os.Handler;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class NotificationService extends Service {

    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    private static final long INTERVAL_MS = 60 * 1000; // 30 seconds
    private Handler handler = new Handler();

    public  NotificationService(){}
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startRepeatingTask();
        return START_STICKY;
    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // Execute database query here
            try {
                AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
                List<TodoWithCategoryModel> todoList = appDatabase.todoDao().getTodosWithCategory();

                Calendar currentTime = Calendar.getInstance();
                if (todoList.size() > 0) {
                    for (TodoWithCategoryModel todo : todoList) {
                        if (todo.todoModel.isAlarm() && isAlarmTimeMatchesCurrentTime(todo.todoModel, currentTime)) {
                            // Trigger notification
                            createNotification(todo);
                        }
                    }
                }
            }catch (Exception ee)
            {
                //AppDatabase.getInstance(getApplicationContext()).clearAllData();
                System.out.println("error99" + ee.getMessage());
            }

            handler.postDelayed(this, INTERVAL_MS);
        }
    };
    private boolean isAlarmTimeMatchesCurrentTime(TodoModel todo, Calendar currentTime) {
        String alarmTime = todo.getAlarm();

        String[] alarmTimeParts = alarmTime.split(":");
        int alarmHour = Integer.parseInt(alarmTimeParts[0]);
        int alarmMinute = Integer.parseInt(alarmTimeParts[1]);

        int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentTime.get(Calendar.MINUTE);

        //String values = String.format("%s - %s , %s - %s", alarmHour, alarmMinute, currentHour, currentMinute);
        //Toast.makeText(this, values, Toast.LENGTH_SHORT).show();

        return alarmHour == currentHour && alarmMinute == currentMinute;
    }
    private void createNotification (TodoWithCategoryModel todo) {
        Intent resultIntent = new Intent(this, home.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        resultIntent.setAction(Intent.ACTION_MAIN);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService( NOTIFICATION_SERVICE ) ;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext() , default_notification_channel_id ) ;
        mBuilder.setContentTitle( "Liksi" ) ;
        mBuilder.setContentText( "You have a task todo!" ) ;
        mBuilder.setSmallIcon(R.drawable.liksi_icon);
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setAutoCancel( true ) ;

        long[] vibrationPattern = {0,3000}; // Vibrate for 1 second, then pause for 1 second, repeat
        mBuilder.setVibrate(vibrationPattern);

        String longText = String.format("Task: %s\nCategory: %s\nPriority: %s", todo.todoModel.getTodo(), todo.categoryModel.getName(), todo.todoModel.isPriority()? "Yes" : "No");
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.bigText(longText);
        mBuilder.setStyle(bigTextStyle);

        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis () , mBuilder.build()) ;
    }

    void startRepeatingTask() {
        runnable.run();
    }

    void stopRepeatingTask() {
        handler.removeCallbacks(runnable);
    }

    private void triggerNotification(String title, String message) {
        // Code to trigger notification goes here
        // You can use NotificationManager to show notifications
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }
}
