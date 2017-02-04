package org.ucomplex.ucomplex.Modules.Users;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import net.oneread.aghanim.components.base.MVPViewBaseFragment;
import net.oneread.aghanim.components.utility.IRecyclerItem;
import net.oneread.aghanim.components.utility.MVPCallback;
import net.oneread.aghanim.components.utility.OnClickStrategy;
import net.oneread.aghanim.components.utility.RecyclerOnClickListener;
import net.oneread.aghanim.mvp.abstractmvp.MVPAbstractPresenterRecycler;
import net.oneread.aghanim.mvp.recyclermvp.MVPModelRecycler;

import org.ucomplex.ucomplex.BaseComponents.DaggerApplication;
import org.ucomplex.ucomplex.CommonDependencies.Constants;
import org.ucomplex.ucomplex.CommonDependencies.FacadeCommon;
import org.ucomplex.ucomplex.CommonDependencies.FacadeMedia;
import org.ucomplex.ucomplex.CommonDependencies.MVPUtility;
import org.ucomplex.ucomplex.CommonDependencies.Network.HttpFactory;
import org.ucomplex.ucomplex.Modules.Users.UsersOnline.UsersOnlineModel;
import org.ucomplex.ucomplex.R;

import java.util.List;

import static org.ucomplex.ucomplex.CommonDependencies.Constants.AUTH_STRING;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 03/02/2017.
 * Project: uComplex_v_2
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class UsersPresenter extends MVPAbstractPresenterRecycler<String> {

    public static final int TYPE_USER = 0;
    public static final int TYPE_FOOTER = 1;
    private boolean hasMoreItems;


    @Override
    public int getItemViewType(int position) {
        return position == getItemCount() - 1 && hasMoreItems? TYPE_FOOTER : TYPE_USER;
    }


    @Override
    public void loadData(Bundle... bundle) {
        ((MVPViewBaseFragment) getView()).showProgress();
        mModel.loadData(new MVPCallback<List<IRecyclerItem>>() {
            @Override
            public void onSuccess(List<IRecyclerItem> o) {
                if (o.size() > 20) {
                    addEmptyElement(o);
                    hasMoreItems = true;
                }else {
                    hasMoreItems = false;
                }
                if (getItemCount() > 0) {
                    ((MVPModelRecycler) mModel).remove(((MVPModelRecycler) mModel).getItemCount() - 1);
                    ((MVPViewBaseFragment) getView()).notifyItemRemoved(((MVPModelRecycler) mModel).getItemCount() - 1);
                    addMoreToRecyclerView(o);
                } else {
                    populateRecyclerView(o);
                }
                ((MVPViewBaseFragment) getView()).hideProgress();
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                populateRecyclerView(MVPUtility.initNoContent());
                ((MVPViewBaseFragment) getView()).hideProgress();
            }

            void addEmptyElement(List<IRecyclerItem> o) {
                o.add(new IRecyclerItem() {
                    @Override
                    public boolean isEmpty() {
                        return true;
                    }
                });
            }
        }, bundle);
    }

    @Override
    public UserViewHolder createViewHolder(ViewGroup parent, int viewType) {
        View viewRow;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        int tempLayout = MVPUtility.isAvailableListViewItem((MVPModelRecycler) mModel, getActivityContext(), 0);
        tempLayout = MVPUtility.resolveLayout(tempLayout,
                viewType,
                (viewType1) -> viewType1 == 0 ? R.layout.list_item_users : R.layout.list_item_footer);
        viewRow = inflater.inflate(tempLayout, parent, false);
        setCreator((view, i) -> new UserViewHolder(view, viewType));
        UserViewHolder holder = (UserViewHolder) this.creator.getViewHolder(viewRow, tempLayout);
        if (tempLayout != R.layout.list_item_no_internet && tempLayout != R.layout.list_item_no_content) {
            if (viewType == TYPE_FOOTER) {
                setupLoadMoreOnClickListener(holder);
            } else {
                setupOnClickListener(holder);
            }
        }
        return holder;
    }

    private void setupOnClickListener(UserViewHolder holder) {
        RecyclerOnClickListener clickListener = new RecyclerOnClickListener();
        OnClickStrategy strategy = view -> {
            UserItem item = (UserItem) ((MVPModelRecycler) mModel).getItem(holder.getAdapterPosition());

        };
        clickListener.setStrategy(strategy);
        holder.getClickArea().setOnClickListener(clickListener);
    }

    private void setupLoadMoreOnClickListener(UserViewHolder holder) {
        RecyclerOnClickListener clickListener = new RecyclerOnClickListener();
        OnClickStrategy strategy = view -> {
            Bundle bundle = new Bundle();
            bundle.putString(UsersOnlineModel.USERS_START, String.valueOf(getItemCount() - 1));
            DaggerApplication application = (DaggerApplication) getAppContext();
            bundle.putString(AUTH_STRING, application.getAuthString());
            loadData(bundle);
        };
        clickListener.setStrategy(strategy);
        holder.getLoadMore().setOnClickListener(clickListener);
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final UserViewHolder aHolder = (UserViewHolder) viewHolder;
        IRecyclerItem userItem = ((MVPModelRecycler) mModel).getItem(position);
        if (!aHolder.allNullElements() && userItem instanceof UserItem) {
            UserItem item = (UserItem) userItem;
            processImageView(aHolder, item);
            aHolder.getName().setText(item.getName());
            aHolder.getType().setText(FacadeCommon.getStringUserType(getActivityContext(), item.getType()));
            aHolder.getMenuButton().setOnClickListener(v -> {

            });
        }

    }

    private void processImageView(UserViewHolder aHolder, UserItem item) {
        Drawable textDrawable;
        if (item.getPhoto() == 0) {
            textDrawable = FacadeMedia.getTextDrawable(item.getId(),
                    item.getName(),
                    getActivityContext());
            aHolder.getProfileImage().setImageDrawable(textDrawable);
        } else {
            if (item.getBitmap() == null) {
                Glide.with(getActivityContext())
                        .load(HttpFactory.LOAD_PHOTO_URL + item.getCode() + Constants.IMAGE_FORMAT)
                        .into(aHolder.getProfileImage());
            } else {
                aHolder.getProfileImage().setImageBitmap(item.getBitmap());
            }
        }
    }
}
