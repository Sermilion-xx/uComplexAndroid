package org.ucomplex.ucomplex.Modules.UserProfile;

import android.os.Bundle;

import com.google.gson.Gson;

import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.components.utility.MVPCallback;
import net.oneread.aghanim.mvp.abstractmvp.MVPAbstractModelRecycler;

import org.ucomplex.ucomplex.CommonDependencies.Network.HttpFactory;
import org.ucomplex.ucomplex.Domain.Users.Teacher;
import org.ucomplex.ucomplex.Modules.Users.UserItem;

import java.util.HashMap;
import java.util.List;

import static org.ucomplex.ucomplex.CommonDependencies.Constants.AUTH_STRING;
import static org.ucomplex.ucomplex.Modules.UserProfile.UserProfileActivity.EXTRA_KEY_USER_ID;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 07/02/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class UserProfileModel extends MVPAbstractModelRecycler<String, List<IRecyclerItem>> {

    @Override
    public void loadData(MVPCallback<List<IRecyclerItem>> mvpCallback, Bundle... bundle) {
        String encodedAuth;
        encodedAuth = bundle[0].getString(AUTH_STRING);
        bundle[0].getString(EXTRA_KEY_USER_ID);
        HttpFactory.getInstance().httpVolley(HttpFactory.GET_USER_URL+bundle[0].getString(EXTRA_KEY_USER_ID),
                encodedAuth,
                mContext,
                null,
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
        Teacher user = gson.fromJson(s, Teacher.class);
        return null;
    }
}
