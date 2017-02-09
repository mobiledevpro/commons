package com.cdvdev.commons.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdvdev.commons.activity.IBaseActivity;

/**
 * Base fragment class
 * <p>
 * Created by Dmitriy V. Chernysh on 04.11.16.
 * dmitriy.chernysh@gmail.com
 * <p>
 * www.mobile-dev.pro
 */

public abstract class BaseFragment extends Fragment {

    @LayoutRes
    protected abstract int getLayoutResId();

    protected abstract void initPresenters();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(getOptionsMenuResId() > 0);
        initPresenters();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        return populateView(view, savedInstanceState);
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

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (getOptionsMenuResId() > 0) {
            inflater.inflate(getOptionsMenuResId(), menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    protected abstract View populateView(View layoutView, @Nullable Bundle savedInstanceState);

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

}
