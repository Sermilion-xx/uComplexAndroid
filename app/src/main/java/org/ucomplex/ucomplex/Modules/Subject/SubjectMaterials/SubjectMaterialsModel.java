package org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials;

import android.os.Bundle;

import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.components.utility.MVPCallback;
import net.oneread.aghanim.mvp.abstractmvp.MVPAbstractModelRecycler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ucomplex.ucomplex.CommonDependencies.Network.HttpFactory;
import org.ucomplex.ucomplex.Domain.Materials.MaterialItem;
import org.ucomplex.ucomplex.Modules.Subject.SubjectModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.ucomplex.ucomplex.CommonDependencies.Constants.AUTH_STRING;
import static org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsPresenter.EXTRA_KEY_FOLDER;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 18/01/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class SubjectMaterialsModel extends MVPAbstractModelRecycler<String, List<IRecyclerItem>> {

    private List<List<IRecyclerItem>> mPageHistory;
    private int currentPage=-1;

    public int getCurrentPage() {
        return currentPage;
    }

    public void pageUp(){
        currentPage++;
    }

    public void pageDown(){
        currentPage--;
    }

    int getHistoryCount() {
        return mPageHistory.size();
    }

    void addHistory(List<IRecyclerItem> list) {
        this.mPageHistory.add(list);
    }

    public SubjectMaterialsModel() {
        mPageHistory = new ArrayList<>();
    }

    List<IRecyclerItem> getHistory(int index) {
        return this.mPageHistory.get(index);
    }

    void swapHistory(int currentPage) {
        mItems = mPageHistory.get(currentPage);
    }

    @Override
    public void loadData(MVPCallback<List<IRecyclerItem>> mvpCallback, Bundle... bundles) {
        List<MaterialItem> files = SubjectModel.getInstance().getFiles();
        if (getItemCount() == 0) {
            try {
                for (MaterialItem item : files) {
                    SubjectMaterialsItem materialsItem = new SubjectMaterialsItem();
                    materialsItem.setAddress(item.getAddress());
                    materialsItem.setTime(item.getTime());
                    materialsItem.setName(item.getName());
                    materialsItem.setOwnersName(item.getOwner().getName());
                    materialsItem.setOwnersId(item.getOwner().getId());
                    materialsItem.setSize(item.getSize());
                    materialsItem.setType(item.getType());
                    materialsItem.setId(item.getId());
                    mItems.add(materialsItem);
                }
            } catch (Exception e) {
                mvpCallback.onError(e);
            }
            mvpCallback.onSuccess(new ArrayList<>(mItems));
        } else {
            String encodedAuth;
            HashMap<String, String> params = new HashMap<>();
            encodedAuth = bundles[0].getString(AUTH_STRING);
            params.put(EXTRA_KEY_FOLDER, bundles[0].getString(EXTRA_KEY_FOLDER));

            HttpFactory.getInstance().httpVolley(HttpFactory.TEACHERS_FILES_URL,
                    encodedAuth,
                    mContext,
                    params,
                    new MVPCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            if (s != null && !s.equals("null")) {
                                List<IRecyclerItem> newItems = processJson(s);
                                mvpCallback.onSuccess(newItems);
                            }
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            mvpCallback.onError(throwable);
                        }
                    });
        }
    }

    @Override
    public List<IRecyclerItem> processJson(String s) {
        List<IRecyclerItem> files = new ArrayList<>();
        try {
            JSONArray filesArray = new JSONObject(s).getJSONArray("files");
            for (int j = 0; j < filesArray.length(); j++) {
                JSONObject jsonFile = filesArray.getJSONObject(j);
                SubjectMaterialsItem file = new SubjectMaterialsItem();
                file.setId(jsonFile.getString("id"));
                file.setName(jsonFile.getString("name"));
                file.setAddress(jsonFile.getString("address"));
                file.setTime(jsonFile.getString("data"));
                file.setSize(jsonFile.getInt("size"));
                file.setTime(jsonFile.getString("time"));
                file.setType(jsonFile.getString("type"));
                file.setOwnersId(jsonFile.getInt("owner"));
                files.add(file);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return files;
    }
}
