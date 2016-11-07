package org.ucomplex.ucomplex.Utility;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import org.json.JSONException;
import org.json.JSONObject;
import org.ucomplex.ucomplex.Interfaces.OnTaskCompleteListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Sermilion on 03/11/2016.
 */

public class RefreshService extends IntentService {

    private static final String REFRESH_DOMAIN = "/user/my_news?mobile=1";
    private Context context = this;
    private OnTaskCompleteListener listener;

    public RefreshService() {
        super("Message update");
        listener = (OnTaskCompleteListener) getBaseContext();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;

    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(context!=null){
//                    Common.fetchMyNews(context);
                }else{
                    this.cancel();
                }
            }
        }, 0, 5000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


//    public static void fetchMyNews(final Context context) {
//        if (context != null) {
//            new AsyncTask<Void, Void, String>() {
//
//                @Override
//                protected String doInBackground(Void... params) {
//                    String url = HttpFactory.BASE_URL+REFRESH_DOMAIN;
//                    return HttpFactory.httpPost(url, FacadePreferences.getLoginDataFromPref(context), null);
//                }
//
//                @Override
//                protected void onPostExecute(String jsonData) {
//                    super.onPostExecute(jsonData);
//
//                    if (jsonData != null) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(jsonData);
//                            JSONObject messagesJson = jsonObject.getJSONObject("messages");
//                            if (messagesJson != null) {
//                                ArrayList<String> fromMessagesStr = Common.getKeys(messagesJson);
//                                fromMessagesStr.remove("sum");
//                                fromMessages.clear();
//                                for (String from : fromMessagesStr) {
//                                    fromMessages.add(Integer.valueOf(from));
//                                }
//                                if (newMesg != messagesJson.getInt("sum")) {
//                                    Common.newMesg = messagesJson.getInt("sum");
//                                    Intent broadcast = new Intent();
//                                    broadcast.setAction("org.ucomplex.newMessageListBroadcast");
//                                    broadcast.putExtra("newMessage", Common.newMesg);
//                                    context.sendBroadcast(broadcast);
//                                }
//                                Common.newMesg = messagesJson.getInt("sum");
//                                Common.newUsr = jsonObject.getBoolean("friends_req");
//                                Intent broadcast = new Intent();
//                                broadcast.setAction("org.ucomplex.newMessageMenuBroadcast");
//                                broadcast.putExtra("newFriend", Common.newUsr);
//                                broadcast.putExtra("newMessage", Common.newMesg);
//                                context.sendBroadcast(broadcast);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }.execute();
//        }
//    }


}
