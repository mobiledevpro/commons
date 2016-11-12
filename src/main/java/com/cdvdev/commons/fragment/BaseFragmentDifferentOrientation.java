package com.cdvdev.commons.fragment;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdvdev.commons.activity.IBaseActivity;

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

    @LayoutRes
    public abstract int getLayoutResId();

    public abstract Bundle saveStateForPopulateView();

    public abstract void restoreStateForPopulateView(@Nullable Bundle savedState);

    public abstract View populateView(View layoutView);

    public boolean isLayoutDifferentForOrientation() {
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSavedInstanceState = savedInstanceState;
        //remove fragment view from container
        if (container != null) {
            container.removeAllViewsInLayout();
        }
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
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = getActivity();
        @StringRes int titleResId = getAppBarTitle();
        @ColorRes int colorResId = getAppBarColor();

        if (!(activity instanceof IBaseActivity)) {
            if (colorResId > 0)
                throw new UnsupportedOperationException("Your activity should extends from 'com.cdvdev.commons.activity.BaseActivity' for set AppBar color");
            if (titleResId > 0)
                throw new UnsupportedOperationException("Your activity should extends from 'com.cdvdev.commons.activity.BaseActivity' for set AppBar title");
        } else {
            if (titleResId > 0) ((IBaseActivity) activity).setAppBarTitle(titleResId);
            if (colorResId > 0) ((IBaseActivity) activity).setAppBarColor(colorResId);
        }

    }

    public
    @StringRes
    int getAppBarTitle() {
        return 0;
    }

    public
    @ColorRes
    int getAppBarColor() {
        return 0;
    }
}
