package com.bs.dental.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import com.bs.dental.MainActivity;
import com.bs.dental.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Arif Islam on 12-Sep-17.
 * nopStation | Brain Station-23
 * http://www.nop-station.com/
 */

public class MessagingService extends FirebaseMessagingService {
    private static final String TAG             = "FCM_MSG";
    public static final  int    REQUEST_CODE    = 0;
    public static final  int    NOTIFICATION_ID = 1;
    
    public static final String TITLE     = "title";
    public static final String BODY      = "body";
    public static final String BANNER    = "banner";
    public static final String ITEM_TYPE = "itemType";
    public static final String ITEM_ID   = "itemId";
    
    public static final int ITEM_PRODUCT  = 1;
    public static final int ITEM_CATEGORY = 2;
    
    
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        
        if (remoteMessage.getData().size() > 0) {
            sendNotification(remoteMessage.getData());
        }
    }
    
    
    private void sendNotification(Map<String, String> data) {
        String title = data.get(TITLE);
        String summery = data.get(BODY);
        String bigPicture = data.get(BANNER);
        
        Intent intent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        for (String key : data.keySet()) {
            bundle.putString(key, data.get(key));
        }
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);
        
        PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        
        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setSmallIcon(R.drawable.ic_notification)
                .setColor(getResources().getColor(R.color.accent))
                .setContentTitle(title)
                .setContentText(summery)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        
        Bitmap bitmap = null;
        try {
            bitmap = Picasso.with(this).load(bigPicture).get();
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        
        if (bitmap != null) {
            NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
            style.setBigContentTitle(title);
            style.setSummaryText(summery);
            style.bigPicture(bitmap);
            notificationBuilder.setStyle(style);
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
        
    }
}
