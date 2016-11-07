package org.ucomplex.ucomplex.NavDrawer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.ucomplex.ucomplex.Interfaces.ClickListener;
import org.ucomplex.ucomplex.Model.DrawerListItem;
import org.ucomplex.ucomplex.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Sermilion on 02/11/2016.
 */

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {

    private static ClickListener clickListener;
    private static int TYPE_0 = 0;
    private static int TYPE_1 = 1;
    private        ArrayList<DrawerListItem> mItems;

    public DrawerAdapter(ArrayList<DrawerListItem> items) {
        mItems = items;
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

        public ViewHolder(View view, int viewType) {
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
        }

        public void bindListRow(DrawerListItem row) {
            if(getItemViewType() == 0){
                mTextView1.setText(row.getTitle1());
                mTextView2.setText(row.getTitle2());
                mProfileImageView.setImageBitmap(row.getProfileBitmap());
            }else{
                mTextView1.setText(row.getTitle1());
                mIconImageView.setImageResource(row.getIcon());
            }
        }

        @Override
        public void onClick(View view) {

        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }
}
