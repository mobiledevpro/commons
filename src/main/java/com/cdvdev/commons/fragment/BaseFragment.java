package com.cdvdev.commons.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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
        initPresenters();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        return populateView(view, savedInstanceState);
    }

    protected abstract View populateView(View layoutView, @Nullable Bundle savedInstanceState);
}
