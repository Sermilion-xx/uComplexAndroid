package org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.components.utility.MVPCallback;
import net.oneread.aghanim.components.utility.OnClickStrategy;
import net.oneread.aghanim.components.utility.RecyclerOnClickListener;
import net.oneread.aghanim.mvp.abstractmvp.MVPAbstractPresenterRecycler;
import net.oneread.aghanim.mvp.basemvp.MVPModel;
import net.oneread.aghanim.mvp.recyclermvp.MVPModelRecycler;

import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.CommonDependencies.FacadeCommon;
import org.ucomplex.ucomplex.CommonDependencies.Network.HttpFactory;
import org.ucomplex.ucomplex.CommonDependencies.Network.InputStreamVolleyRequest;
import org.ucomplex.ucomplex.CommonDependencies.MVPUtility;
import org.ucomplex.ucomplex.CommonDependencies.NotificationService;
import org.ucomplex.ucomplex.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import static org.ucomplex.ucomplex.CommonDependencies.Constants.AUTH_STRING;
import static org.ucomplex.ucomplex.CommonDependencies.NotificationService.DOWNLOAD_COMPLETE;
import static org.ucomplex.ucomplex.CommonDependencies.NotificationService.EXTRA_BODY;
import static org.ucomplex.ucomplex.CommonDependencies.NotificationService.EXTRA_TITLE;
import static org.ucomplex.ucomplex.CommonDependencies.NotificationService.EXTRA_URI;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 18/01/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class SubjectMaterialsPresenter extends MVPAbstractPresenterRecycler<String> implements Response.Listener<byte[]>, Response.ErrorListener {

    static final int TYPE_FILE = 0;
    static final int TYPE_FOLDER = 1;
    static final String EXTRA_KEY_FOLDER = "folder";
    private static final String EXTRA_KEY_GET_FOLDER = "get_folder";
    private String filename;
    private InputStreamVolleyRequest request;



    private void pageUp() {
        ((SubjectMaterialsModel) mModel).pageUp();
    }

    void pageDown() {
        ((SubjectMaterialsModel) mModel).pageDown();
        populateRecyclerView(getHistory(((SubjectMaterialsModel) mModel).getCurrentPage()));
    }

    int getCurrentPage() {
        return ((SubjectMaterialsModel) mModel).getCurrentPage();
    }

    private void addHistory(List<IRecyclerItem> list) {
        ((SubjectMaterialsModel) mModel).addHistory(list);
    }

    private List<IRecyclerItem> getHistory(int index) {
        return ((SubjectMaterialsModel) mModel).getHistory(index);
    }

    @Override
    public void setModel(MVPModel<String, List<IRecyclerItem>> models, Bundle... bundle) {
        this.mModel = models;
        this.mModel.setContext(this.getActivityContext());
    }

    private void setupOnClickListener(SubjectMaterialsViewHolder holder, int viewType) {
        RecyclerOnClickListener clickListener = new RecyclerOnClickListener();
        OnClickStrategy strategy = view -> {
            SubjectMaterialsItem item = (SubjectMaterialsItem) ((MVPModelRecycler) mModel).getItem(holder.getAdapterPosition());
            if (viewType == TYPE_FILE) {
                if(ContextCompat.checkSelfPermission(getActivityContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(getActivityContext(),getActivityContext().getString(R.string.need_storage_permissions), Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getActivityContext(),getActivityContext().getString(R.string.file_download_started),Toast.LENGTH_SHORT).show();
                filename = item.getAddress() + "." + item.getType();
                startNotificationService(filename, "Загрузка файла началась.", false, null);
                String mUrl = HttpFactory.DOWNLOAD_MATERIAL_URL;
                mUrl = mUrl + item.getOwnersId() + "/" + filename;
                request = new InputStreamVolleyRequest(Request.Method.GET, mUrl, this, this, null);
                RequestQueue mRequestQueue = Volley.newRequestQueue(getAppContext(),
                        new HurlStack());
                mRequestQueue.add(request);

            } else {
                Bundle bundle = new Bundle();
                bundle.putString(AUTH_STRING, ((DaggerApplication) getAppContext()).getAuthString());
                bundle.putString(EXTRA_KEY_FOLDER, item.getAddress());
                bundle.putBoolean(EXTRA_KEY_GET_FOLDER, true);
                loadData(bundle);
            }
        };
        clickListener.setStrategy(strategy);
        holder.mClickArea.setOnClickListener(clickListener);
    }

    private void startNotificationService(String filename, String message, boolean downloadComplete, Uri fileUri) {
        Intent notificationIntent = new Intent(getActivityContext(), NotificationService.class);
        notificationIntent.putExtra(EXTRA_TITLE, filename);
        notificationIntent.putExtra(EXTRA_BODY, message);
        if(downloadComplete){
            notificationIntent.putExtra(DOWNLOAD_COMPLETE, true);
            notificationIntent.putExtra(EXTRA_URI, fileUri);
        }
        getActivityContext().startService(notificationIntent);
    }


    @Override
    public SubjectMaterialsViewHolder createViewHolder(ViewGroup parent, int viewType) {
        View viewRow;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        int tempLayout = MVPUtility.isAvailableListViewItem((MVPModelRecycler) mModel, getActivityContext(), 0);
        MVPUtility.LayoutResolveStrategy layoutResolveStrategy = viewType1 -> {
            int temp = -1;
            if (itemLayout != R.layout.list_item_no_content && itemLayout != R.layout.list_item_no_internet) {
                switch (viewType1) {
                    case TYPE_FILE:
                        temp = R.layout.list_item_material_file;
                        break;
                    case TYPE_FOLDER:
                        temp = R.layout.list_item_material_folder;
                        break;
                }
            }
            return temp;
        };
        tempLayout = MVPUtility.resolveLayout(tempLayout, viewType, layoutResolveStrategy);
        viewRow = inflater.inflate(tempLayout, parent, false);
        setCreator((view, i) -> new SubjectMaterialsViewHolder(view, viewType));
        SubjectMaterialsViewHolder holder = (SubjectMaterialsViewHolder) this.creator.getViewHolder(viewRow, tempLayout);
        setupOnClickListener(holder, viewType);
        holder.mMenuButton.setOnClickListener(view -> {

        });
        holder.mMenuButton.setVisibility(View.GONE);
        return holder;
    }

    @Override
    public void loadData(Bundle... bundle) {
        if (bundle.length > 0 && !bundle[0].containsKey(EXTRA_KEY_GET_FOLDER)) {
            populateRecyclerView(((SubjectMaterialsModel) mModel).getHistory(getItemCount()));
        } else {
            mModel.loadData(new MVPCallback<List<IRecyclerItem>>() {
                @Override
                public void onSuccess(List<IRecyclerItem> o) {
                    populateRecyclerView(o);
                    addHistory(o);
                    pageUp();
                }

                @Override
                public void onError(Throwable throwable) {
                    throwable.printStackTrace();
                }
            }, bundle);
        }
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        SubjectMaterialsViewHolder holder = (SubjectMaterialsViewHolder) viewHolder;
        SubjectMaterialsItem item = (SubjectMaterialsItem) ((MVPModelRecycler) mModel).getItem(position);
        if (item != null) {
            holder.mFileName.setText(item.getName());
            holder.mFileTime.setText(FacadeCommon.makeDate(item.getTime()));
            switch (getItemViewType(position)) {
                case TYPE_FILE:
                    holder.mSize.setText(FacadeCommon.readableFileSize(item.getSize(), false));
                    break;
                case TYPE_FOLDER:
                    holder.mOwnersName.setText(item.getOwnersName());
                    break;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        SubjectMaterialsItem item = (SubjectMaterialsItem) ((MVPModelRecycler) mModel).getItem(position);
        if (item.getType().equals("f")) {
            return TYPE_FOLDER;
        }
        return TYPE_FILE;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
        Toast.makeText(getActivityContext(), getActivityContext().getString(R.string.error_loadig_file), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(byte[] response) {
        try {
            if (response!=null) {
                String[] tempName = request.getUrl().split("/");
                String name = tempName[tempName.length-1];
                File file =new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), name);
                FileOutputStream out = new FileOutputStream(file.getPath());
                out.write(response);
                out.close();
                //if(sdk>24)
                Uri fileUri = FileProvider.getUriForFile(getActivityContext(), getAppContext().getPackageName() + ".provider", file);
                //else Uri.parce(file)
                Toast.makeText(getActivityContext(),getActivityContext().getString(R.string.file_saved_to_downloads),Toast.LENGTH_LONG).show();
                startNotificationService(filename, getActivityContext().getString(R.string.file_download_complete), true, fileUri);
            }
        } catch (Exception e) {
            Toast.makeText(getActivityContext(),getActivityContext().getString(R.string.error_loadig_file),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
