package org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials;

import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;

import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.components.utility.MVPCallback;
import net.oneread.aghanim.mvp.abstractmvp.MVPAbstractModelRecycler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ucomplex.ucomplex.CommonDependencies.FacadeCommon;
import org.ucomplex.ucomplex.CommonDependencies.Network.HttpFactory;
import org.ucomplex.ucomplex.Domain.Materials.MaterialItem;
import org.ucomplex.ucomplex.Modules.Subject.SubjectModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;

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

    public static final String EXTRA_KEY_MY_MATERIALS = "myMaterials";
    public static final String EXTRA_KEY_FILE = "file";
    public static final String EXTRA_KEY_NAME = "name";
    public static final String FILE_URI = "file_uri";
    private List<Pair<List<IRecyclerItem>,String>> mPageHistory;
    private int currentPage = -1;
    private String currentFolder = null;

    public int getCurrentPage() {
        return currentPage;
    }

    public void pageUp() {
        currentPage++;
    }

    public void pageDown() {
        currentPage--;
        if(currentPage<1){
            currentFolder = "null";
        }else {
            currentFolder = getHistory(currentPage).second;
        }
    }

    void addHistory(Pair<List<IRecyclerItem>,String> list) {
        this.mPageHistory.add(list);
    }

    public SubjectMaterialsModel() {
        mPageHistory = new ArrayList<>();
    }

    Pair<List<IRecyclerItem>,String> getHistory(int index) {
        if(index<this.mPageHistory.size()){
            return this.mPageHistory.get(index);
        }else {
            return null;
        }
    }

    int getHistoryCount(){
        return this.mPageHistory.size();
    }

    @Override
    public void loadData(MVPCallback<List<IRecyclerItem>> mvpCallback, Bundle... bundles) {
        List<MaterialItem> files = SubjectModel.getInstance().getFiles();
        boolean myMaterials = bundles[0].getBoolean(EXTRA_KEY_MY_MATERIALS);
        if (getItemCount() == 0 && !myMaterials) {
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
            if (mItems.size() == 0) {
                mvpCallback.onError(new Exception("Нету материалов для отображения"));
            } else {
                mvpCallback.onSuccess(new ArrayList<>(mItems));
            }
        } else {
            String encodedAuth;
            HashMap<String, String> params = new HashMap<>();
            encodedAuth = bundles[0].getString(AUTH_STRING);

            String url = HttpFactory.TEACHERS_FILES_URL;
            if (myMaterials) {
                url = HttpFactory.STUDENTS_FILES_URL;
            }
            if(bundles[0].get(EXTRA_KEY_FOLDER)!=null){
                params.put(EXTRA_KEY_FOLDER, bundles[0].getString(EXTRA_KEY_FOLDER));
            }

            HttpFactory.getInstance().httpVolley(url,
                    encodedAuth,
                    mContext,
                    params,
                    new MVPCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            if (s != null && !s.equals("null")) {
                                List<IRecyclerItem> newItems = processJson(s);
                                mvpCallback.onSuccess(newItems);
                                currentFolder = bundles[0].getString(EXTRA_KEY_FOLDER);
                            }
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            mvpCallback.onError(throwable);
                        }
                    });
        }
    }

    public void uploadFile(String authString, Uri uri, MVPCallback<List<IRecyclerItem>> mvpCallback){
        Bundle bundle = new Bundle();
        bundle.putString(AUTH_STRING, authString);
        bundle.putParcelable(FILE_URI, uri);
        bundle.putString(EXTRA_KEY_FOLDER, currentFolder);
        HttpFactory.uploadFile(bundle, mContext, new MVPCallback<ResponseBody>() {

            @Override
            public void onSuccess(ResponseBody responseBodyResponse) {
                List<IRecyclerItem> list;
                try {
                    list = processJson(responseBodyResponse.string());
                    mvpCallback.onSuccess(list);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                mvpCallback.onError(throwable);
            }
        });
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
                if(jsonFile.has("data")){
                    file.setTime(jsonFile.getString("data"));
                }
                if(!jsonFile.isNull("size")){
                    file.setSize(jsonFile.getInt("size"));
                }
                if(jsonFile.has("time")){
                    file.setTime(jsonFile.getString("time"));
                }
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

    void deleteFile(String auth, String file, MVPCallback<String> mvpCallback) {
        Map<String, String> params = new HashMap<>();
        params.put(EXTRA_KEY_FILE, file);
        HttpFactory.getInstance().httpVolley(HttpFactory.DELETE_FILE_URL,
                auth,
                mContext,
                params,
                new MVPCallback<String>() {

                    @Override
                    public void onSuccess(String result) {
                        mvpCallback.onSuccess(result);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mvpCallback.onError(throwable);
                    }
                });
    }

    void renameFile(String auth, String file, String name, MVPCallback<String> mvpCallback) {
        Map<String, String> params = new HashMap<>();
        params.put(EXTRA_KEY_FILE, file);
        params.put(EXTRA_KEY_NAME, name);
        HttpFactory.getInstance().httpVolley(HttpFactory.RENAME_FILE_URL,
                auth,
                mContext,
                params,
                new MVPCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        mvpCallback.onSuccess(result);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mvpCallback.onError(throwable);
                    }
                });
    }

    public void shareFile(String auth, String file, MVPCallback<String> mvpCallback) {
        HashMap<String, String> params = new HashMap<>();
        params.put(EXTRA_KEY_FILE, file);
        HttpFactory.getInstance().httpVolley(HttpFactory.GET_FILE_ACCESS_URL,
                auth,
                mContext,
                params,
                new MVPCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        mvpCallback.onSuccess(result);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mvpCallback.onError(throwable);
                    }
                });
    }

    private Map<String, String> processShareInfo(String s){
        Map<String, String> keys = null;
        try {
            JSONObject persons = new JSONObject(s).getJSONObject("persons");
            keys = FacadeCommon.parseJsonKV(persons);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return keys;
    }

    void createFolder(String authString, String folderName, MVPCallback<List<IRecyclerItem>> mvpCallback) {
        String url = HttpFactory.CREATE_FOLDER_URL;
        Map<String, String> params = new HashMap<>();
        params.put(EXTRA_KEY_NAME, folderName);
        if(currentPage>0){
            params.put(EXTRA_KEY_FOLDER, currentFolder);
        }

        HttpFactory.getInstance().httpVolley(url,
                authString,
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
