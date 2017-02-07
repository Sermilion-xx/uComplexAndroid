package org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials;

import android.app.Notification;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import org.ucomplex.ucomplex.CommonDependencies.FacadeMedia;
import org.ucomplex.ucomplex.Modules.Subject.SubjectActivity;
import org.ucomplex.ucomplex.R;

import java.io.IOException;

import static org.ucomplex.ucomplex.CommonDependencies.Constants.PREFIX;
import static org.ucomplex.ucomplex.CommonDependencies.Constants.UC_ACTION_DOWNLOAD_CLICKED;
import static org.ucomplex.ucomplex.CommonDependencies.Constants.UC_ACTION_DOWNLOAD_COMPLETE;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 25/01/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class NotificationService extends Service {

    public static final String EXTRA_TITLE = "notification_title";
    public static final String EXTRA_BODY = "notification_body";
    private static final int NOTIFY_ID = 1;
    public static final String ACTION_NOTIFY = PREFIX + "Notify";
    public static final String EXTRA_LARGE_ICON = "smallIcon";

    public int onStartCommand(Intent intent, int flags, int startId) {
        String mTitle = intent.getStringExtra(EXTRA_TITLE);
        String mMessage = intent.getStringExtra(EXTRA_BODY);
        Bitmap largeIcon = null;
        if(intent.hasExtra(EXTRA_LARGE_ICON)) {
            try {
                Uri largeIconUri = intent.getParcelableExtra(EXTRA_LARGE_ICON);
//                largeIcon = MediaStore.Images.Media.getBitmap(this.getContentResolver(), largeIconUri);
                largeIcon = FacadeMedia.getThumbnail(largeIconUri,this, 100);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Notification notification;
        notification = downloadStartedNotification(mTitle, mMessage, largeIcon);
        startForeground(NOTIFY_ID, notification);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        filter.addAction(UC_ACTION_DOWNLOAD_COMPLETE);
        filter.addAction(UC_ACTION_DOWNLOAD_CLICKED);
        registerReceiver(receiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Notification downloadStartedNotification(String title, String message, Bitmap largeIcon) {
        Intent intent = new Intent(this, SubjectActivity.class);
        intent.setAction(ACTION_NOTIFY);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_BODY, message);
        NotificationCompat.Builder builder = initBasicBuilder(title, message, intent);
        if(largeIcon==null){
            largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_u);
        }
        builder.setLargeIcon(largeIcon);
        return builder.build();
    }

    private NotificationCompat.Builder initBasicBuilder(String title, String text, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.u)
                .setContentTitle(title)
                .setContentText(text);
        if (intent != null) {
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
            taskStackBuilder.addNextIntentWithParentStack(intent);
        }
        return builder;
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(UC_ACTION_DOWNLOAD_COMPLETE) || action.equals(UC_ACTION_DOWNLOAD_CLICKED)) {
                stopSelf();
            }
        }
    };
}
