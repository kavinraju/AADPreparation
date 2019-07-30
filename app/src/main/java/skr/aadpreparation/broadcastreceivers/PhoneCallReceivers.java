package skr.aadpreparation.broadcastreceivers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import skr.aadpreparation.MainActivity;
import skr.aadpreparation.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class PhoneCallReceivers extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 101;
    private static final String PRIMARY_CHANNEL_ID_0 = "broadcast_receiver_phone_call";
    private static final String TAG = PhoneCallReceivers.class.getSimpleName();
    private NotificationManager mNotificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, " onReceive");

        Bundle bundle = intent.getExtras();
        if (bundle != null){
            String state =  intent.getAction();//bundle.getString(TelephonyManager.EXTRA_STATE);
            Log.d(TAG, state);
            if (TelephonyManager.ACTION_PHONE_STATE_CHANGED.equals(state)){
                String phoneNumber = bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                createNotificationChannel(context);
                sendNotification(phoneNumber,context);
                Log.d(TAG, phoneNumber + " calling you");
            }
        }
    }

    //Helper Methods
    public void createNotificationChannel(Context context){

        mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            // Channel 0
            NotificationChannel channel_0 = new NotificationChannel(
                    PRIMARY_CHANNEL_ID_0,
                    "Broadcast Receiver for Phone Call",
                    NotificationManager.IMPORTANCE_HIGH);

            channel_0.enableLights(true);
            channel_0.setLightColor(Color.GREEN);
            channel_0.enableVibration(true);
            channel_0.setDescription("Hello! This is the description of Notification Channel of Phone Call Broad Cast");

            mNotificationManager.createNotificationChannel(channel_0);
        }
    }

    public void sendNotification(String phoneNumber, Context context){
        Intent notifyIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,PRIMARY_CHANNEL_ID_0)
                .setContentTitle("Call with:")
                .setContentText("Phone Number: " + phoneNumber)
                .setColor(Color.RED)
                .setSmallIcon(R.drawable.ic_live_tv_black_24dp)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(false);

        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
