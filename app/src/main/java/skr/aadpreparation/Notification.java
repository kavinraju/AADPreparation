package skr.aadpreparation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/*
    //Channel 0
    1) Normal Notification
    2) Notification with Actions

    //Channel 1
    3) Style Notification - 1) Big Text Style 2) Big Picture Style 3) Inbox Style 4) Media Style
                         5) Messaging Style 6) Direct Reply Style

    //Channel 3
    4) Notification Groups
    5) Custom Notification

 */
public class Notification extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG = Notification.class.getSimpleName();

    private static final String REPLY_NOTIFICATION_KEY = "notification_reply";
    private static final String PRIMARY_CHANNEL_ID_0 = "primary_notification_channel_zero";
    private static final String PRIMARY_CHANNEL_ID_1 = "primary_notification_channel_one";
    private static final String PRIMARY_CHANNEL_ID_2 = "primary_notification_channel_two";

    private static final String ACTION_UPDATE_NOTIFICATION_0 = "skr.aadpreparation.notify.ACTION_UPDATE_NOTIFICATION_ZERO";
    private static final String ACTION_UPDATE_NOTIFICATION_1 = "skr.aadpreparation.notify.ACTION_UPDATE_NOTIFICATION_ONE";
    private static final String ACTION_UPDATE_NOTIFICATION_2 = "skr.aadpreparation.notify.ACTION_UPDATE_NOTIFICATION_TWO";

    private static final int NOTIFICATION_ID_0 = 0;
    private static final int NOTIFICATION_ID_1 = 1;
    private static final int NOTIFICATION_ID_2 = 2;

    private NotificationManager mNotificationManager;
    private NotificationReceiver notificationReceiver = new NotificationReceiver();

    static List<Message> messageList = new ArrayList<>();

    private Button btnNotify, btnUpdate, btnCancel, btnBigPictureStyleNotify, btnBigTextStyleNotify, btnInboxStyleNotify,
                    btnDirectReplyStyle;
    private TextView tvNotify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        btnNotify = findViewById(R.id.btn_notification);
        btnUpdate = findViewById(R.id.update);
        btnCancel = findViewById(R.id.cancel);
        btnBigPictureStyleNotify = findViewById(R.id.btn_big_picture_style_notification);
        btnBigTextStyleNotify = findViewById(R.id.btn_big_text_style_notification);
        btnInboxStyleNotify = findViewById(R.id.btn_inbox_style_notification);
        btnDirectReplyStyle = findViewById(R.id.btn_direct_reply_style_notification);
        tvNotify = findViewById(R.id.tvNotification);

        createNotificationChannel();

        messageList.add(new Message("Hey Bin!", "Jim"));
        messageList.add(new Message("Hey!",null));
        messageList.add(new Message("How are you guys","Rim"));


        setNotificationButtonState(true, false, false);
        btnNotify.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnInboxStyleNotify.setOnClickListener(this);
        btnBigPictureStyleNotify.setOnClickListener(this);
        btnBigTextStyleNotify.setOnClickListener(this);
        btnDirectReplyStyle.setOnClickListener(this);

        // Register Broadcast Receiver to receive tap actions on the notification in channel 0
        registerReceiver(notificationReceiver,new IntentFilter(ACTION_UPDATE_NOTIFICATION_0));
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(notificationReceiver);
        super.onDestroy();
    }

    //Helper Methods
    public void createNotificationChannel(){

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            // Channel 0
            NotificationChannel channel_0 = new NotificationChannel(
                    PRIMARY_CHANNEL_ID_0,
                    "Channel 0",
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel_0.enableLights(true);
            channel_0.setLightColor(Color.GREEN);
            channel_0.enableVibration(true);
            channel_0.setDescription("Hello! This is the description of Notification Channel 0");

            //Channel 1
            NotificationChannel channel_1 = new NotificationChannel(
                    PRIMARY_CHANNEL_ID_1,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH);
            channel_1.enableLights(true);
            channel_1.setLightColor(Color.RED);
            channel_1.enableVibration(true);
            channel_1.setDescription("Hello! This is the description of Notification Channel 1");

            //Channel 1
            NotificationChannel channel_2 = new NotificationChannel(
                    PRIMARY_CHANNEL_ID_1,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH);
            channel_2.enableLights(true);
            channel_2.setLightColor(Color.CYAN);
            channel_2.enableVibration(true);
            channel_2.setDescription("Hello! This is the description of Notification Channel 2");

            mNotificationManager.createNotificationChannel(channel_0);
            mNotificationManager.createNotificationChannel(channel_1);
            mNotificationManager.createNotificationChannel(channel_2);
        }
    }


    public NotificationCompat.Builder getNotificationBuilderOfChannel1(){

        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID_1,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(this,PRIMARY_CHANNEL_ID_1)
                .setContentTitle("This is your Content Title")
                .setContentText("This is your Content Text")
                .setColor(Color.RED)
                .setSmallIcon(R.drawable.ic_live_tv_black_24dp)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);
    }

    public NotificationCompat.Builder getNotificationBuilderOfChannel2(){

        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID_2,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(this,PRIMARY_CHANNEL_ID_2)
                .setContentTitle("This is your Content Title")
                .setContentText("This is your Content Text")
                .setColor(Color.YELLOW)
                .setSmallIcon(R.drawable.ic_live_tv_black_24dp)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);
    }

    public void sendBigTextStyleNotification(){

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.movie_reel);

        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID_1,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,PRIMARY_CHANNEL_ID_1)
                .setContentTitle("This is your Content Title")
                .setContentText("This is your Content Text")
                .setColor(Color.RED)
                .setSmallIcon(R.drawable.ic_live_tv_black_24dp)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true)
                .setLargeIcon(bitmap)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(getString(R.string.big_text_notification))
                        .setBigContentTitle("Big Content Title")
                        .setSummaryText("Summary Text")
                );

        mNotificationManager.notify(NOTIFICATION_ID_1, builder.build());
    }

    public void sendBigPictureStyleNotification(){

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.g_cloud_study_jam);

        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID_1,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,PRIMARY_CHANNEL_ID_1)
                .setContentTitle("This is your Content Title")
                .setContentText("This is your Content Text")
                .setColor(Color.RED)
                .setSmallIcon(R.drawable.ic_live_tv_black_24dp)
                .setLargeIcon(bitmap)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);

        builder.setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(bitmap)
                .bigLargeIcon(null)
                .setBigContentTitle("Big Content Title")
        );
        mNotificationManager.notify(NOTIFICATION_ID_1, builder.build());
    }

    public void sendInboxStyleNotification(){

        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID_1,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,PRIMARY_CHANNEL_ID_1)
                .setContentTitle("This is your Content Title")
                .setContentText("This is your Content Text")
                .setColor(Color.RED)
                .setSmallIcon(R.drawable.ic_live_tv_black_24dp)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine("Hey!")
                        .addLine("How are you doing Alley?")
                        .addLine("I am coming to your home town tomorrow, are you available at 10 A.M?")
                        .setSummaryText("Summary Text")
                        .setBigContentTitle("Inbox Style Notification")
                );

        mNotificationManager.notify(NOTIFICATION_ID_1, builder.build());
    }

    public void sendMediaStyleNotification(){
        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID_1,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =  new NotificationCompat.Builder(this,PRIMARY_CHANNEL_ID_1)
                .setContentTitle("This is your Content Title")
                .setContentText("This is your Content Text")
                .setColor(Color.RED)
                .setSmallIcon(R.drawable.ic_live_tv_black_24dp)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);
    }

    public static void sendDirectReplyStyleNotification(Context context){

        Intent notifyIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID_1,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteInput remoteInput = new RemoteInput.Builder(REPLY_NOTIFICATION_KEY)
                .setLabel("Reply..")
                .build();

        Intent replyIntent = new Intent(context, NotificationReceiver.class);
        PendingIntent replyPendingIntent = PendingIntent.getBroadcast(context,
                NOTIFICATION_ID_1, replyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(
                R.drawable.ic_send,
                "Reply",
                replyPendingIntent).addRemoteInput(remoteInput).build();

        NotificationCompat.MessagingStyle messagingStyle = new NotificationCompat.MessagingStyle("Me");
        messagingStyle.setConversationTitle("Gang Chat");

        for (Message message: messageList){
            NotificationCompat.MessagingStyle.Message msg =
                    new NotificationCompat.MessagingStyle.Message(message.getMessage(),message.getTimestamp(),message.getSender());
            messagingStyle.addMessage(msg);
        }

        NotificationCompat.Builder builder =  new NotificationCompat.Builder(context,PRIMARY_CHANNEL_ID_1)
                .setColor(Color.GREEN)
                .setSmallIcon(R.drawable.ic_live_tv_black_24dp)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setStyle(messagingStyle)
                .addAction(replyAction)
                .setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(NOTIFICATION_ID_1, builder.build());

    }
    public void sendNotification(){
        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID_0,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,PRIMARY_CHANNEL_ID_0)
                .setContentTitle("This is your Content Title")
                .setContentText("This is your Content Text")
                .setColor(Color.GREEN)
                .setSmallIcon(R.drawable.ic_live_tv_black_24dp)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);
        mNotificationManager.notify(NOTIFICATION_ID_0, builder.build());
    }

    public void sendNotificationWithAction(){

        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID_0,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,PRIMARY_CHANNEL_ID_0)
                .setContentTitle("This is your Content Title")
                .setContentText("This is your Content Text")
                .setColor(Color.GREEN)
                .setSmallIcon(R.drawable.ic_live_tv_black_24dp)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);

        Intent updateIntent = new Intent();
        updateIntent.setAction(ACTION_UPDATE_NOTIFICATION_0);
        updateIntent.putExtra("key","Hello Man! I am from BroadCast Receiver");

        PendingIntent pendingIntentAction1 = PendingIntent.getBroadcast(
                this,NOTIFICATION_ID_0, updateIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(R.drawable.ic_update, "Update", pendingIntentAction1);


        Intent updateIntent2 = new Intent();
        updateIntent2.setAction(ACTION_UPDATE_NOTIFICATION_2);
        updateIntent2.putExtra("key1","I am from BroadCast Receiver Two");

        PendingIntent pendingIntentAction2 = PendingIntent.getBroadcast(
                this,NOTIFICATION_ID_0, updateIntent2,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(R.drawable.ic_update, "Click 2", pendingIntentAction2);

        mNotificationManager.notify(NOTIFICATION_ID_0, builder.build());
    }

    public void updateNotification() {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.g_cloud_study_jam);
        Bitmap bitmapLargeIcon = BitmapFactory.decodeResource(getResources(),R.drawable.movie_reel);

        NotificationCompat.Builder builder = getNotificationBuilderOfChannel1();
        builder.setStyle(new NotificationCompat.BigPictureStyle()
                                                .bigPicture(bitmap)
                                                .bigLargeIcon(bitmapLargeIcon)
                                                .setBigContentTitle("Updated!")
                        );
        mNotificationManager.notify(NOTIFICATION_ID_1, builder.build());

        setNotificationButtonState(true, false, true);
    }

    public void cancelNotification() {
        mNotificationManager.cancel(NOTIFICATION_ID_0);

        setNotificationButtonState(true, false, false);
    }

    void setNotificationButtonState(Boolean isNotifyEnabled,
                                    Boolean isUpdateEnabled,
                                    Boolean isCancelEnabled) {
        btnNotify.setEnabled(isNotifyEnabled);
        btnUpdate.setEnabled(isUpdateEnabled);
        btnCancel.setEnabled(isCancelEnabled);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_notification:
                sendNotificationWithAction();
                setNotificationButtonState(false, true, true);
                break;
            case R.id.update:
                updateNotification();
                break;
            case R.id.cancel:
                cancelNotification();
                break;
            case R.id.btn_inbox_style_notification:
                sendInboxStyleNotification();
                break;
            case R.id.btn_big_picture_style_notification:
                sendBigPictureStyleNotification();
                break;
            case R.id.btn_big_text_style_notification:
                sendBigTextStyleNotification();
                break;
            case R.id.btn_direct_reply_style_notification:
                sendDirectReplyStyleNotification(this);
                break;
        }
    }

    public class NotificationReceiver extends BroadcastReceiver{

        public NotificationReceiver(){

        }

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("Receiver","OnReceive");
            /*
                This is for Direct Reply Notification
            */
            Bundle bundle = RemoteInput.getResultsFromIntent(intent);
            if (bundle!=null){
                CharSequence replyFromNotification = bundle.getCharSequence(REPLY_NOTIFICATION_KEY);
                Message sender = new Message(replyFromNotification,null);
                Notification.messageList.add(sender);

                Log.d("Receiver",replyFromNotification.toString());

                Notification.sendDirectReplyStyleNotification(context);
            }else {
                Log.d("Receiver","NLL");
            }

            /*
                This is for Notifiction with Action
            */
            String action = intent.getAction();
            if (ACTION_UPDATE_NOTIFICATION_0.equals(action)){

                updateNotification();

                if (intent.getStringExtra("key") != null) {
                    tvNotify.setText(intent.getStringExtra("key"));
                    Toast.makeText(context, intent.getStringExtra("key"), Toast.LENGTH_SHORT).show();
                    Log.d(LOG, ACTION_UPDATE_NOTIFICATION_0);
                }

            }else if (ACTION_UPDATE_NOTIFICATION_2.equals(action)){
                if (intent.getStringExtra("key1") != null) {
                    tvNotify.setText(intent.getStringExtra("key1"));
                    Toast.makeText(context, intent.getStringExtra("key1"), Toast.LENGTH_SHORT).show();
                    Log.d(LOG, ACTION_UPDATE_NOTIFICATION_2);
                }
            }
        }
    }
}
