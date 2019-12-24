package com.mobiledevpro.commons.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.fragment.app.DialogFragment;

/**
 * Base Dialog Fragment
 * <p>
 * Created by Dmitriy V. Chernysh
 *
 * https://instagr.am/mobiledevpro
 * #MobileDevPro
 */

public abstract class CustomDialogFragment extends DialogFragment {

    private Bundle mSavedInstanceState;

    @LayoutRes
    public abstract int getLayoutResId();

    public abstract View populateView(View layoutView);

    public abstract boolean isLayoutDifferentForOrientation();

    @StyleRes
    public int getDialogAnimationStyle() {
        return 0;
    }

    @Nullable
    public Bundle saveStateForPopulateView() {
        return null;
    }

    public void restoreStateForPopulateView(@Nullable Bundle savedState) {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        @StyleRes int animationStyle = getDialogAnimationStyle();

        if (animationStyle != 0 && getDialog() != null) {
            Window window = getDialog().getWindow();
            if (window != null) {
                window.getAttributes().windowAnimations = animationStyle;
            }
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
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

}
