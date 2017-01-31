package org.ucomplex.ucomplex.Modules.Materials;

import android.app.Fragment;
import android.os.Bundle;

import net.oneread.aghanim.components.utility.IRecyclerItem;

import org.ucomplex.ucomplex.BaseComponents.BaseActivity;
import org.ucomplex.ucomplex.BaseComponents.BaseRecyclerActivity;
import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsModel;
import org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsPresenter;
import org.ucomplex.ucomplex.R;

import java.util.List;

import javax.inject.Inject;

import static org.ucomplex.ucomplex.CommonDependencies.Constants.AUTH_STRING;
import static org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsModel.EXTRA_KEY_MY_MATERIALS;

public class MaterialsActivity extends BaseRecyclerActivity {

    @Inject
    public void setPresenter(SubjectMaterialsPresenter presenter) {
        super.mPresenter = presenter;
    }

    @Inject
    public void setModel(SubjectMaterialsModel model) {
        super.mModel = model;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((DaggerApplication) getApplication()).getMaterialsDiComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentViewWithNavDrawer(R.layout.activity_materials);
        setupToolbar(getResourceString(R.string.portfolio));
        //mvp
        Bundle bundle = new Bundle();
        DaggerApplication application = (DaggerApplication) getAppContext();
        bundle.putString(AUTH_STRING, application.getAuthString());
        bundle.putBoolean(EXTRA_KEY_MY_MATERIALS, true);

        mFragment = setupFragment(this,
                savedInstanceState,
                bundle,
                R.layout.fragment_recycler,
                R.id.recyclerView,
                R.id.progressBar,
                R.id.container);

        mFragment.setOnFragmentLoadedListener(views -> {
            setupMVP(MaterialsActivity.this, BaseActivity.class);
            mPresenter.loadData(bundle);
            setupDrawer();
        });
    }
}
