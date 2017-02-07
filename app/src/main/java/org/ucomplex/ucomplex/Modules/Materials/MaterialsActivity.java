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
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import net.oneread.aghanim.components.base.MVPBaseRecyclerFragment;
import net.oneread.aghanim.components.utility.IFragment;

import org.ucomplex.ucomplex.BaseComponents.BaseRecyclerActivity;
import org.ucomplex.ucomplex.CommonDependencies.FacadeCommon;
import org.ucomplex.ucomplex.CommonDependencies.FacadeMedia;
import org.ucomplex.ucomplex.CommonDependencies.FragmentFactory;
import org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsFragment;
import org.ucomplex.ucomplex.Modules.Subject.SubjectMaterials.SubjectMaterialsPresenter;
import org.ucomplex.ucomplex.R;

import static org.ucomplex.ucomplex.CommonDependencies.FacadeCommon.REQUEST_EXTERNAL_STORAGE;

public class MaterialsActivity extends BaseRecyclerActivity {

    private static final int GALLERY_KITKAT_INTENT_CALLED = 0;
    private static final int GALLERY_INTENT_CALLED = 1;
    private static final String TAG_MATERIALS_FRAGMENT = SubjectMaterialsFragment.class.getName();

    private SubjectMaterialsFragment subjectMaterialsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewWithNavDrawer(R.layout.activity_materials);
        setupToolbar(getString(R.string.portfolio));

        subjectMaterialsFragment = (SubjectMaterialsFragment) setupRecyclerFragment(
                savedInstanceState,
                TAG_MATERIALS_FRAGMENT,
                R.id.container);

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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getFragmentManager().putFragment(outState, TAG_MATERIALS_FRAGMENT, subjectMaterialsFragment);
        super.onSaveInstanceState(outState);
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
                fragment.setOnFragmentLoadedListener(views -> {
                            ((SubjectMaterialsPresenter) subjectMaterialsFragment.getPresenter()).setMyFiles(true);
                            subjectMaterialsFragment.onFragmentVisible(true);
                        }
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
                    if (FacadeCommon.checkStoragePermissions(this)) {
                        pickImage();
                    }
                } else {
                    pickImage();
                }
                return true;
            case R.id.my_files_add_folder:
                showCreateFolderDialog();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    protected void showCreateFolderDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.dialog_input, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);
        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        editText.setTextColor(ContextCompat.getColor(this, R.color.color_uc_ListTextColorPrimary));
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton(MaterialsActivity.this.getString(R.string.ok), (dialog, id) -> {
                    if (FacadeCommon.isNetworkConnected(MaterialsActivity.this)) {
                        String folderName = editText.getText().toString();
                        if (subjectMaterialsFragment != null) {
                            ((SubjectMaterialsPresenter) subjectMaterialsFragment.getPresenter()).createFolder(folderName);
                        }
                    } else {
                        Toast.makeText(MaterialsActivity.this, MaterialsActivity.this.getString(R.string.error_check_internet), Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton(MaterialsActivity.this.getString(R.string.cancel),
                        (dialog, id) -> dialog.cancel());
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
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
                ((SubjectMaterialsPresenter) subjectMaterialsFragment.getPresenter()).uploadFile(originalUri);
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
                    pickImage();
                } else {
                    Toast.makeText(this, "Вы не разрешили доступ к пямяти. Выгрузка файлов будет не возможна.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


}
