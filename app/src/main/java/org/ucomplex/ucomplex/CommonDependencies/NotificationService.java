package org.ucomplex.ucomplex.CommonDependencies;

import android.app.Notification;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import org.ucomplex.ucomplex.Modules.Subject.SubjectActivity;
import org.ucomplex.ucomplex.R;

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
    public static final String EXTRA_URI = "uriString";
    public static final String DOWNLOAD_COMPLETE = "downloadComplete";


    public int onStartCommand(Intent intent, int flags, int startId) {
        String mTitle = intent.getStringExtra(EXTRA_TITLE);
        String mMessage = intent.getStringExtra(EXTRA_BODY);
        Notification notification = null;
//        if (intent.hasExtra(DOWNLOAD_COMPLETE)) {
//            Uri uri = intent.getParcelableExtra(EXTRA_URI);
////            notification = downloadCompleteNotification(mTitle, mMessage, uri);
//        } else {
        notification = downloadStartedNotification(mTitle, mMessage);
//        }
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

    private Notification downloadStartedNotification(String title, String message) {
        Intent intent = new Intent(this, SubjectActivity.class);
        intent.setAction(ACTION_NOTIFY);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_BODY, message);
        NotificationCompat.Builder builder = initBasicBuilder(title, message, intent);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_u));
        return builder.build();
    }

//    private Notification downloadCompleteNotification(String title, String message, Uri uri) {
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.setDataAndType(uri, "*/*");
//        Intent chooser = Intent.createChooser(intent, getResources().getString(R.string.open_file_with));
//        NotificationCompat.Builder builder = initBasicBuilder(title, message, intent);
//        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_u));
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, chooser, 0);
//        builder.setContentIntent(pendingIntent);
//        return builder.build();
//    }

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
