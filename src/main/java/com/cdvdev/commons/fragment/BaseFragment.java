package com.cdvdev.commons.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (getOptionsMenuResId() > 0) {
            inflater.inflate(getOptionsMenuResId(), menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    protected abstract View populateView(View layoutView, @Nullable Bundle savedInstanceState);

    @MenuRes
    protected int getOptionsMenuResId() {
        return 0;
    }
}
