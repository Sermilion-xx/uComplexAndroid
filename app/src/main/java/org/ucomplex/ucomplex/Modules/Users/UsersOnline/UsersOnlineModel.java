package org.ucomplex.ucomplex.Modules.Users.UsersOnline;

import android.os.Bundle;

import com.google.gson.Gson;

import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.components.utility.MVPCallback;
import net.oneread.aghanim.mvp.abstractmvp.MVPAbstractModelRecycler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ucomplex.ucomplex.CommonDependencies.Network.HttpFactory;
import org.ucomplex.ucomplex.Modules.Users.UserItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.ucomplex.ucomplex.CommonDependencies.Constants.AUTH_STRING;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 03/02/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class UsersOnlineModel extends MVPAbstractModelRecycler<String, List<IRecyclerItem>> {

    public static final String USERS_START = "start";

    @Override
    public void loadData(MVPCallback<List<IRecyclerItem>> mvpCallback, Bundle... bundle) {
        String encodedAuth = bundle[0].getString(AUTH_STRING);
        HashMap<String, String> params = new HashMap<>();
        if(bundle[0].containsKey(USERS_START)){
            params.put(USERS_START, bundle[0].getString(USERS_START));
        }
        HttpFactory.getInstance().httpVolley(HttpFactory.ONLINE_USERS_URL,
                encodedAuth,
                mContext,
                params,
                new MVPCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        List<IRecyclerItem> newItems = processJson(s);
                        mvpCallback.onSuccess(newItems);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mvpCallback.onError(throwable);
                    }
                });
    }

    @Override
    public List<IRecyclerItem> processJson(String s) {
        Gson gson = new Gson();
        try {
            JSONArray jsonObject = new JSONObject(s).getJSONArray("users");
            UserItem[] users = gson.fromJson(jsonObject.toString(), UserItem[].class);
            return new ArrayList<>(Arrays.asList(users));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
