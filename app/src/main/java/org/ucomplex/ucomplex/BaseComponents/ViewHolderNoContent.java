package org.ucomplex.ucomplex.BaseComponents;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.ucomplex.ucomplex.R;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 04/12/2016.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class ViewHolderNoContent extends RecyclerView.ViewHolder {

    public ViewHolderNoContent(View itemView) {
        super(itemView);
        itemView.findViewById(R.id.imageView);
    }
}
