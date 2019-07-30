package skr.aadpreparation.backgroundservices;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import skr.aadpreparation.MainActivity;
import skr.aadpreparation.R;

public class ScheduleJob extends JobService {

    private static final String TAG = ScheduleJob.class.getSimpleName();
    private static final String PRIMARY_CHANNEL_ID_0 = "schedule_job_notification_0";
    private static final int NOTIFICATION_ID_0 = 100;

    private boolean jobCancelled = false;
    private NotificationManager mNotificationManager;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "onStartJob");
        createNotificationChannel();
        doBackgroundWork(jobParameters);
        return false;
    }

    private void doBackgroundWork(final JobParameters jobParameters) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i<10;i++){
                    Log.d(TAG, "background process: " + i);
                    sendNotification(i+1);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG, "Finished Background process");
                jobFinished(jobParameters, false);
            }
        }).start();

    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        /*
          onStopJob is called when the job gets terminated unexpectedly.
         */
        Log.d(TAG, "onStopJob ");
        jobCancelled = true;

        //This return boolean indicates if we want to reschedule the job or not
        return true;
    }

    //Helper Methods
    public void createNotificationChannel(){

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            // Channel 0
            NotificationChannel channel_0 = new NotificationChannel(
                    PRIMARY_CHANNEL_ID_0,
                    "Service using Job scheduler notifications",
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel_0.enableLights(true);
            channel_0.setLightColor(Color.GREEN);
            channel_0.enableVibration(true);
            channel_0.setDescription("Hello! This is the description of Notification Channel of Job Scheduler");

            mNotificationManager.createNotificationChannel(channel_0);
        }
    }

    public void sendNotification(int count){
        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID_0,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,PRIMARY_CHANNEL_ID_0)
                .setContentTitle("Job Schedule count")
                .setContentText("Count: " + count)
                .setColor(Color.RED)
                .setSmallIcon(R.drawable.ic_live_tv_black_24dp)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setOngoing(true)
                .setAutoCancel(false);

        if (count == 10)
            builder.setOngoing(false).setAutoCancel(true);

        mNotificationManager.notify(NOTIFICATION_ID_0, builder.build());
    }
}
