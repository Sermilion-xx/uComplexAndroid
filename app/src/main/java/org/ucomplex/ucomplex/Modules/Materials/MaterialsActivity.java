package org.ucomplex.ucomplex.Modules.Materials;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;

import net.oneread.aghanim.components.base.MVPBaseRecyclerFragment;
import net.oneread.aghanim.components.utility.IFragment;

import org.ucomplex.ucomplex.BaseComponents.BaseRecyclerActivity;
import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.CommonDependencies.FragmentFactory;
import org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsFragment;
import org.ucomplex.ucomplex.R;

import static org.ucomplex.ucomplex.CommonDependencies.Constants.AUTH_STRING;
import static org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsModel.EXTRA_KEY_MY_MATERIALS;

public class MaterialsActivity extends BaseRecyclerActivity {

    private SubjectMaterialsFragment subjectMaterialsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewWithNavDrawer(R.layout.activity_materials);
        setupToolbar(getString(R.string.portfolio));

        Bundle bundle = new Bundle();
        DaggerApplication application = (DaggerApplication) getAppContext();
        bundle.putString(AUTH_STRING, application.getAuthString());
        bundle.putBoolean(EXTRA_KEY_MY_MATERIALS, true);

        subjectMaterialsFragment = (SubjectMaterialsFragment) setupRecyclerFragment(savedInstanceState, SubjectMaterialsFragment.class.getName(), R.id.container);
        setupDrawer();
    }

    @Override
    public void onBackPressed() {
        if (subjectMaterialsFragment.getCurrentPage() > 0) {
            subjectMaterialsFragment.onBackPress();
        } else {
            super.onBackPressed();
        }
    }


    protected IFragment setupRecyclerFragment(Bundle inState, String name, int containerId) {
        IFragment fragment;
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (inState != null) {
            fragment = (MVPBaseRecyclerFragment) manager.getFragment(inState, name);
        } else {
            fragment = FragmentFactory.getFragmentWithName(name, this);
            if (fragment != null) {
                ((SubjectMaterialsFragment) fragment).setContext(this);
                fragment.setOnFragmentLoadedListener(
                        views -> subjectMaterialsFragment.onFragmentVisible(true)
                );
                transaction.add(containerId, (Fragment) fragment, name);
                transaction.commit();
            }
        }
        return fragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
