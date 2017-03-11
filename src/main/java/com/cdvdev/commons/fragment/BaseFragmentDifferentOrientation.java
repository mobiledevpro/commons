package com.cdvdev.commons.fragment;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import com.cdvdev.commons.R;
import com.cdvdev.commons.activity.IBaseActivity;
import com.cdvdev.commons.helpers.BaseResourcesHelper;

/**
 * Base fragment with support the orientation change
 * <p>
 * Created by Dmitriy V. Chernysh on 04.11.16.
 * dmitriy.chernysh@gmail.com
 * <p>
 * www.mobile-dev.pro
 */

public abstract class BaseFragmentDifferentOrientation extends Fragment {

    private Bundle mSavedInstanceState;
    private View mCurrentFragmentLayout;

    @LayoutRes
    protected abstract int getLayoutResId();

    protected abstract Bundle saveStateForPopulateView();

    protected abstract void restoreStateForPopulateView(@Nullable Bundle savedState);

    protected abstract View populateView(View layoutView);

    protected abstract void initPresenters();

    protected boolean isLayoutDifferentForOrientation() {
        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(getOptionsMenuResId() > 0);
        initPresenters();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSavedInstanceState = savedInstanceState;
        View view = populateView(inflater.inflate(getLayoutResId(), container, false));
        //apply saved state
        restoreStateForPopulateView(mSavedInstanceState);
        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (!isLayoutDifferentForOrientation()) return;
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT || newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //save view state
            Bundle savedState = saveStateForPopulateView();

            LayoutInflater inflater = LayoutInflater.from(getActivity());
            //switch portrait or landscape layout on configuration changed
            //remove fragment view from container
            ViewGroup container = (ViewGroup) getView();
            if (container != null) {
                container.removeAllViewsInLayout();
            }
            //populate view
            View view = inflater.inflate(getLayoutResId(), container, true);
            populateView(view);
            //apply saved state
            restoreStateForPopulateView(savedState != null ? savedState : mSavedInstanceState);

            resizeFrameView();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = getActivity();
        String titleString = getAppBarTitleString();
        @StringRes int titleResId = getAppBarTitle();
        @ColorRes int colorResId = getAppBarColor();
        @DrawableRes int homeIcon = getHomeAsUpIndicatorIcon();

        if (!(activity instanceof IBaseActivity)) {
            if (colorResId > 0)
                throw new UnsupportedOperationException("Your activity should extends from 'com.cdvdev.commons.activity.BaseActivity' for set AppBar color");
            if (titleResId > 0 || !titleString.equals(""))
                throw new UnsupportedOperationException("Your activity should extends from 'com.cdvdev.commons.activity.BaseActivity' for set AppBar title");
            if (homeIcon > 0)
                throw new UnsupportedOperationException("Your activity should extends from 'com.cdvdev.commons.activity.BaseActivity' for set home indicator icon");
        } else {

            if (titleResId > 0) {
                ((IBaseActivity) activity).setAppBarTitle(activity.getResources().getString(titleResId));
            } else if (!titleString.equals("")) {
                ((IBaseActivity) activity).setAppBarTitle(titleString);
            }
            if (colorResId > 0) ((IBaseActivity) activity).setAppBarColor(colorResId);

            if (homeIcon > 0) ((IBaseActivity) activity).setHomeAsUpIndicatorIcon(homeIcon);
        }

        if (isTabletLandscapeAdaptive()) {
            mCurrentFragmentLayout = view;
            resizeFrameView();
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (getOptionsMenuResId() > 0) {
            inflater.inflate(getOptionsMenuResId(), menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @StringRes
    protected int getAppBarTitle() {
        return 0;
    }

    @NonNull
    protected String getAppBarTitleString() {
        return "";
    }

    @ColorRes
    protected int getAppBarColor() {
        return 0;
    }

    @MenuRes
    protected int getOptionsMenuResId() {
        return 0;
    }

    @DrawableRes
    protected int getHomeAsUpIndicatorIcon() {
        return 0;
    }

    protected boolean isTabletLandscapeAdaptive() {
        return false;
    }

    /**
     * Resize frame view (for tablets)
     */
    private void resizeFrameView() {
        if (mCurrentFragmentLayout == null) return;
        //set a new width for settings list if it's a Tablet in landscape mode
        boolean isTablet = getResources().getBoolean(R.bool.is_tablet);
        int[] displaySize = BaseResourcesHelper.getDisplaySize(getActivity());
        if (isTablet) {
            //if parent view is not FrameLayout will be exception on set layout params
            ViewParent parentView = mCurrentFragmentLayout.getParent();
            if (!(parentView instanceof FrameLayout)) {
                mCurrentFragmentLayout = ((ViewGroup) mCurrentFragmentLayout).getChildAt(0);
            }

            if (!(mCurrentFragmentLayout instanceof FrameLayout)) return;

            if (mCurrentFragmentLayout != null) {
                // if (mCurrentFragmentLayout instanceof FrameLayout) {
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT);
                layoutParams.width = displaySize[0] > displaySize[1] ? displaySize[1] : displaySize[0];
                layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                mCurrentFragmentLayout.setLayoutParams(layoutParams);
            }
        }
    }
}
