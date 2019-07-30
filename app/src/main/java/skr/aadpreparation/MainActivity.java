package skr.aadpreparation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_notification)
    Button btnNotification;
    @BindView(R.id.btn_job_scheduler)
    Button btnJobScheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_notification)
    public void onCLickNotificationOption(View view) {
        startActivity(new Intent(this, Notification.class));
    }
    @OnClick(R.id.btn_job_scheduler)
    public void onCLickJobScheduler(View view) {
        startActivity(new Intent(this, JobSchedulerActivity.class));
    }
}
