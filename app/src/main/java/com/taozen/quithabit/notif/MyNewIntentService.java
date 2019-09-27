package com.taozen.quithabit.notif;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.core.app.NotificationManagerCompat;

import com.taozen.quithabit.MainActivity;
import com.taozen.quithabit.R;

public class MyNewIntentService extends IntentService {
    private static final int NOTIFICATION_ID = 3;
    private static final String TAG = MyNewIntentService.class.getSimpleName();

    public MyNewIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Did you smoke today ?");
        builder.setContentText("Check in now !");
        builder.setSmallIcon(R.mipmap.leaf);
        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //to be able to launch your activity from the notification
        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(NOTIFICATION_ID, notificationCompat);

    }


}
