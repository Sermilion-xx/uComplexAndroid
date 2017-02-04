package org.ucomplex.ucomplex.Modules.Users;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.ucomplex.ucomplex.Interfaces.IViewHolder;
import org.ucomplex.ucomplex.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 03/02/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class UserViewHolder extends RecyclerView.ViewHolder implements IViewHolder{

    private CircleImageView mProfileImage;
    private TextView mName;
    private TextView mType;
    private Button mMenuButton;
    private Button mLoadMore;
    private RelativeLayout mClickArea;

    public UserViewHolder(View itemView, int viewType) {
        super(itemView);
        if(viewType == UsersPresenter.TYPE_USER){
            mProfileImage = (CircleImageView) itemView.findViewById(R.id.profileImage);
            mName = (TextView) itemView.findViewById(R.id.name);
            mType = (TextView) itemView.findViewById(R.id.type);
            mMenuButton = (Button) itemView.findViewById(R.id.menu_button);
            mClickArea = (RelativeLayout) itemView.findViewById(R.id.clickArea);
        }else if(viewType == UsersPresenter.TYPE_FOOTER) {
            mLoadMore = (Button) itemView.findViewById(R.id.loadMoreButton);
        }
    }


    public CircleImageView getProfileImage() {
        return mProfileImage;
    }

    public TextView getName() {
        return mName;
    }

    public TextView getType() {
        return mType;
    }

    public Button getMenuButton() {
        return mMenuButton;
    }

    public Button getLoadMore() {
        return mLoadMore;
    }

    public RelativeLayout getClickArea() {
        return mClickArea;
    }

    @Override
    public boolean allNullElements() {
        return mProfileImage == null &&
                mName == null &&
                mMenuButton == null &&
                mType == null;
    }
}
