package org.ucomplex.ucomplex.Modules.RoleSelect;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.ucomplex.ucomplex.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 08/11/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

class RoleViewHolder extends RecyclerView.ViewHolder {

    TextView        roleName;
    CircleImageView roleIcon;
    private View    mView;
    private Context mContext;


    RoleViewHolder(View view, Context context){
        super(view);
        setupViews(view);
        this.mContext = context;
        mView = view;
    }

    private void setupViews(View view) {
        roleName = (TextView) view.findViewById(R.id.roleTitle);
        roleIcon = (CircleImageView) view.findViewById(R.id.roleIcon);
    }

}
