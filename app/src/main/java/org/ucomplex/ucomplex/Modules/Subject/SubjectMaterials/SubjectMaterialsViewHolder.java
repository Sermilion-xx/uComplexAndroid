package org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.ucomplex.ucomplex.R;

import static org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsPresenter.TYPE_FILE;
import static org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsPresenter.TYPE_FOLDER;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 18/01/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

class SubjectMaterialsViewHolder extends RecyclerView.ViewHolder {

    TextView mFileName;
    TextView mSize;
    TextView mFileCount;
    TextView mOwnersName;
    Button mMenuButton;
    RelativeLayout mClickArea;

    SubjectMaterialsViewHolder(View itemView, int viewType) {
        super(itemView);
        mClickArea = (RelativeLayout) itemView.findViewById(R.id.clickArea);
        mFileName = (TextView) itemView.findViewById(R.id.file_name);
        mMenuButton = (Button) itemView.findViewById(R.id.file_menu_button);

        switch (viewType) {
            case TYPE_FILE:
                mSize = (TextView) itemView.findViewById(R.id.file_time);
                break;
            case TYPE_FOLDER:
                mFileCount = (TextView) itemView.findViewById(R.id.file_count);
                break;
        }
    }
}
