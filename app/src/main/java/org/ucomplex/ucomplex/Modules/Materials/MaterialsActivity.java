package org.ucomplex.ucomplex.Modules.Materials;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import net.oneread.aghanim.components.base.MVPBaseRecyclerFragment;
import net.oneread.aghanim.components.utility.IFragment;

import org.ucomplex.ucomplex.BaseComponents.BaseRecyclerActivity;
import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.CommonDependencies.FacadeCommon;
import org.ucomplex.ucomplex.CommonDependencies.FacadeMedia;
import org.ucomplex.ucomplex.CommonDependencies.FragmentFactory;
import org.ucomplex.ucomplex.CommonDependencies.Network.HttpFactory;
import org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsFragment;
import org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsPresenter;
import org.ucomplex.ucomplex.R;

import static org.ucomplex.ucomplex.CommonDependencies.Constants.AUTH_STRING;
import static org.ucomplex.ucomplex.CommonDependencies.FacadeCommon.REQUEST_EXTERNAL_STORAGE;
import static org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsModel.EXTRA_KEY_MY_MATERIALS;

public class MaterialsActivity extends BaseRecyclerActivity {

    private static final int GALLERY_KITKAT_INTENT_CALLED = 0;
    private static final int GALLERY_INTENT_CALLED = 1;
    private String authString;

    private SubjectMaterialsFragment subjectMaterialsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewWithNavDrawer(R.layout.activity_materials);
        setupToolbar(getString(R.string.portfolio));

        Bundle bundle = new Bundle();
        DaggerApplication application = (DaggerApplication) getAppContext();
        authString = application.getAuthString();
        bundle.putString(AUTH_STRING, authString);
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
            case R.id.my_files_add_file:
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.System.canWrite(mPresenter.getActivityContext())) {
                        FacadeCommon.checkPermissions(this);
                    } else {
                        pickImage();
                    }
                } else {
                    pickImage();
                }
                return true;
            case R.id.my_files_add_folder:
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void pickImage() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.pick_document)), GALLERY_INTENT_CALLED);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_materials, menu);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        if (null == data) return;
        Uri originalUri = null;
        if (requestCode == GALLERY_INTENT_CALLED) {
            originalUri = data.getData();
        } else if (requestCode == GALLERY_KITKAT_INTENT_CALLED) {
            originalUri = data.getData();
            this.grantUriPermission(FacadeMedia.PACKAGE, originalUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        if (originalUri != null) {
            if (FacadeCommon.isNetworkConnected(this)) {
                ((SubjectMaterialsPresenter)mPresenter).uploadFile(authString, originalUri);
            } else {
                Toast.makeText(this, "Проверте интернет соединение", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Ошибка выбора файла", Toast.LENGTH_LONG).show();
        }
        this.revokeUriPermission(originalUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(this, "Вы не разрешили доступ к пямяти. Выгрузка файлов будет не возможна.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


}
