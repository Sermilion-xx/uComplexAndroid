package org.ucomplex.ucomplex.Modules.RoleSelect;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.ucomplex.ucomplex.R;
import org.ucomplex.ucomplex.Utility.Constants;

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

    private BitmapDrawable createBitmapDrawbale(int position){
        if(position>5){
            position = 1;
        }
        return new BitmapDrawable(BitmapFactory.decodeResource(mContext.getResources(),
                Constants.colorsUserSelect[position]));
    }

    void setupSelector(int position){
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[] {android.R.attr.state_pressed}, createBitmapDrawbale(position));
        states.addState(new int[] {android.R.attr.state_focused}, createBitmapDrawbale(position+1));
        mView.setBackground(states);
    }

    private void setupViews(View view) {
        roleName = (TextView) view.findViewById(R.id.roleTitle);
        roleIcon = (CircleImageView) view.findViewById(R.id.roleIcon);
    }

}
