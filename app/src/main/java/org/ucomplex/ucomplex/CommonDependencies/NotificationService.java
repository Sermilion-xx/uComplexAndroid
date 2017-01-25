package org.ucomplex.ucomplex.CommonDependencies;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Pair;

import org.ucomplex.ucomplex.Modules.Subject.SubjectActivity;
import org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsPresenter;
import org.ucomplex.ucomplex.R;

import java.util.ArrayList;

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
    public static final String ACTION_NOTIFY = "org.ucomplex.ucomplexandroid.Notify";

    private Notification notification;
    private String mFileName;
    private String mTitle;
    private String mMessage;

    public NotificationService(){

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand (Intent intent, int flags, int startId) {
        mTitle = intent.getStringExtra(EXTRA_TITLE);
        mMessage = intent.getStringExtra(EXTRA_BODY);
        notification = showDownloadNotification(mTitle, mMessage);
        startForeground(NOTIFY_ID, notification);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    ArrayList<Pair<String, String>> notificatinItems = new ArrayList<>();
    private Notification showDownloadNotification(String title, String message){
        String text  = message;
        notificatinItems.add(new Pair<>(title, text));
        Intent intent = new Intent(this, SubjectActivity.class);
        intent.setAction(ACTION_NOTIFY);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_BODY, text);
        NotificationCompat.Builder builder = initBasicBuilder(title, text, intent);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_u));
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
//            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
        }
        return builder;
    }

}
