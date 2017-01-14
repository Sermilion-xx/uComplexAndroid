package org.ucomplex.ucomplex.Modules.RoleSelect;

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

public class RoleViewHolder extends RecyclerView.ViewHolder {

    TextView        roleName;
    CircleImageView roleIcon;
    int             position;


    public RoleViewHolder(View view){
        super(view);
        setupViews(view);
        view.setTag(this);
    }

    private void setupViews(View view) {
        roleName = (TextView) view.findViewById(R.id.roleTitle);
        roleIcon = (CircleImageView) view.findViewById(R.id.roleIcon);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getItemPosition() {
        return position;
    }
}
