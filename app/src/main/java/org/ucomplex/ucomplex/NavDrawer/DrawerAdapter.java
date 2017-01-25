package org.ucomplex.ucomplex.NavDrawer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.CommonDependencies.Network.HttpFactory;
import org.ucomplex.ucomplex.Modules.Events.EventsActivity;
import org.ucomplex.ucomplex.Modules.Events.EventsModel;
import org.ucomplex.ucomplex.Modules.Login.LoginActivityView;
//import org.ucomplex.ucomplex.Modules.Subject.SubjectActivity;
//import org.ucomplex.ucomplex.Modules.SubjectsList.SubjectsListActivity;
import org.ucomplex.ucomplex.Modules.SubjectsList.SubjectsListActivity;
import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.CommonDependencies.FacadeMedia;
import org.ucomplex.ucomplex.CommonDependencies.FacadePreferences;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Sermilion on 02/11/2016.
 */

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {

    private static final int TYPE_0 = 0;
    private static final int TYPE_1 = 1;
    private ArrayList<DrawerListItem> mItems;
    private Activity mContext;

    public DrawerAdapter(ArrayList<DrawerListItem> items, Activity context) {
        mItems = items;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = viewType == 0 ? R.layout.list_item_menu_header : R.layout.list_item_menu;
        View inflatedView = LayoutInflater.from(parent.getContext()) .inflate(layout, parent, false);
        return new ViewHolder(inflatedView, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_0 : TYPE_1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DrawerListItem row = mItems.get(position);
        holder.bindListRow(row);
    }



    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private ImageView mIconImageView;
        private TextView mTextView1;
        private TextView mTextView2;
        private CircleImageView mProfileImageView;
        private CircleImageView mRolesImageView;

        ViewHolder(View view, int viewType) {
            super(view);
            if(viewType == 0){
                mTextView1 = (TextView) view.findViewById(R.id.name);
                mTextView2 = (TextView) view.findViewById(R.id.role);
                mProfileImageView = (CircleImageView) view.findViewById(R.id.profileImage);
                mRolesImageView = (CircleImageView) view.findViewById(R.id.roleImage);
            }else{
                mIconImageView = (ImageView) view.findViewById(R.id.icon);
                mTextView1 = (TextView) view.findViewById(R.id.title);
            }
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        void bindListRow(DrawerListItem row) {
            if(getItemViewType() == 0){
                mTextView1.setText(row.getTitle1());
                mTextView2.setText(row.getTitle2());
                if(row.getProfileBitmapCode() == null){
                    Drawable textDrawable = FacadeMedia.getTextDrawable(row.getId(), row.getTitle1(), mContext);
                    mProfileImageView.setImageBitmap(FacadeMedia.drawableToBitmap(textDrawable));
                }else {
                    String url = HttpFactory.PROFILE_IMAGE_URL + row.getProfileBitmapCode() + Constants.IMAGE_FORMAT_JPG;
                    RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                    ImageRequest imageRequest = new ImageRequest(url, response -> mProfileImageView.setImageBitmap(response), 0, 0, null, null);
                    requestQueue.add(imageRequest);
                }
            }else{
                mTextView1.setText(row.getTitle1());
                mIconImageView.setImageResource(row.getIcon());
            }
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if(position==getItemCount()-1){
                logout();
            }else if(position==0){
                //TODO: go to profile
            }else if(position==1){
                EventsModel.INITIAL_EVENTS_LOADED = true;
                onDrawerItemPressed(EventsActivity.class);
                Intent intent = new Intent(Constants.EVENTS_REFRESH_BROADCAST);
                intent.putExtra(EventsActivity.ACTION_RELOAD_EVENTS, true);
                mContext.sendBroadcast(intent);
            }else if(position==2){
                onDrawerItemPressed(SubjectsListActivity.class);
            }else if(position==3){
//                onDrawerItemPressed(SubjectActivity.class);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }

    public void logout() {
        FacadePreferences.clearPref(mContext);
        DaggerApplication application = (DaggerApplication)mContext.getApplication();
        application.setSharedUser(null);
        application.setAuthString("");
        mContext.startActivity(new Intent(mContext, LoginActivityView.class));
        mContext.finish();
    }

    private void onDrawerItemPressed(Class<? extends Activity> activity) {
        if(!(mContext.getClass() == activity)) {
            mContext.startActivity(new Intent(mContext, activity));
        }else {
            mContext.onBackPressed();
        }
    }
}
