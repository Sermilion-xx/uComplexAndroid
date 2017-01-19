package org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials;

import android.os.Bundle;

import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.components.utility.MVPCallback;
import net.oneread.aghanim.mvp.abstractmvp.MVPAbstractModelRecycler;

import org.ucomplex.ucomplex.Domain.Materials.MaterialItem;
import org.ucomplex.ucomplex.Modules.Subject.SubjectModel;

import java.util.List;

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

    @Override
    public void loadData(MVPCallback<List<IRecyclerItem>> mvpCallback, Bundle... bundles) {
        List<MaterialItem> files = SubjectModel.getInstance().getFiles();
        try {
            for (MaterialItem item : files) {
                SubjectMaterialsItem materialsItem = new SubjectMaterialsItem();
                materialsItem.setAddress(item.getAddress());
                materialsItem.setTime(item.getTime());
                materialsItem.setName(item.getName());
                materialsItem.setOwnersName(item.getOwner().getName());
                materialsItem.setSize(item.getSize());
                materialsItem.setType(item.getType());
                materialsItem.setId(item.getId());
                mItems.add(materialsItem);
            }
        }catch (Exception e){
            mvpCallback.onError(e);
        }
        mvpCallback.onSuccess(mItems);
    }

    @Override
    public List<IRecyclerItem> processJson(String s) {
        throw new UnsupportedOperationException("Method does not need to be used here.");
    }
}
