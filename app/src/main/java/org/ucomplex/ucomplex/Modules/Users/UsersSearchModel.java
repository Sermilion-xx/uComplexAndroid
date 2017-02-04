package org.ucomplex.ucomplex.Modules.Users;

import android.os.Bundle;

import com.google.gson.Gson;

import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.components.utility.MVPCallback;
import net.oneread.aghanim.mvp.abstractmvp.MVPAbstractModelRecycler;

import org.ucomplex.ucomplex.CommonDependencies.Network.HttpFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.ucomplex.ucomplex.CommonDependencies.Constants.AUTH_STRING;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 04/02/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class UsersSearchModel extends MVPAbstractModelRecycler<String, List<IRecyclerItem>> {

    public static final String EXTRA_KEY_NAME = "name";

    @Override
    public void loadData(MVPCallback<List<IRecyclerItem>> mvpCallback, Bundle... bundle) {
        String encodedAuth = bundle[0].getString(AUTH_STRING);
        HashMap<String, String> params = new HashMap<>();
        params.put(EXTRA_KEY_NAME, Integer.toString(bundle[0].getInt(EXTRA_KEY_NAME)));

        HttpFactory.getInstance().httpVolley(HttpFactory.USERS_SEARCH_URL,
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
        UserItem[] users = gson.fromJson(s,  UserItem[].class);
        return Arrays.asList(users);
    }
}
