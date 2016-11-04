package com.cdvdev.commons.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * Base Dialog Fragment
 * <p>
 * Created by Dmitriy V. Chernysh on 04.11.16.
 * dmitriy.chernysh@gmail.com
 * <p>
 * www.mobile-dev.pro
 */

public abstract class CustomDialogFragment extends DialogFragment {

    @LayoutRes
    public abstract int getLayoutResId();

    @StyleRes
    public abstract int getDialogAnimationStyle();

    public abstract boolean isLayoutDifferentForOrientation();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //remove fragment view from container
        if (container != null) {
            container.removeAllViewsInLayout();
        }
        View view = inflater.inflate(getLayoutResId(), container, false);
        return populateView(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        @StyleRes int animationStyle = getDialogAnimationStyle();

        if (animationStyle > 0) {
            Window window = getDialog().getWindow();
            if (window != null) {
                window.getAttributes().windowAnimations = animationStyle;
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (!isLayoutDifferentForOrientation()) return;
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT || newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            //switch portrait or landscape layout on configuration changed
            //remove fragment view from container
            ViewGroup container = (ViewGroup) getView();
            if (container != null) {
                container.removeAllViewsInLayout();
            }
            View view = inflater.inflate(getLayoutResId(), container, true);
            populateView(view);
        }
    }


    public abstract View populateView(View layoutView);

}
