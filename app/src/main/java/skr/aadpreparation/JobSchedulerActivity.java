package skr.aadpreparation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import skr.aadpreparation.backgroundservices.ScheduleJob;

public class JobSchedulerActivity extends AppCompatActivity {

    private static final int JOB_SCHEDULE_ID = 302;
    private static final String TAG = JobSchedulerActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_scheduler);
        ButterKnife.bind(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @OnClick(R.id.btn_start_job)
    public void onCLickStartJob(View view) {

        ComponentName componentName = new ComponentName(this, ScheduleJob.class);
        JobInfo jobInfo = new JobInfo.Builder(JOB_SCHEDULE_ID, componentName)
                .setRequiresCharging(false)
                //.setRequiresDeviceIdle(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build();

        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int result = jobScheduler.schedule(jobInfo);
        if (result == JobScheduler.RESULT_SUCCESS)
            Log.d(TAG, "Job has been Scheduled");
        else
            Log.d(TAG, "Job has been Scheduled");
    }

    @OnClick(R.id.btn_stop_job)
    public void onCLickStopJob(View view) {
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(JOB_SCHEDULE_ID);
        Log.d(TAG, "Job has been Cancelled");
    }
}
