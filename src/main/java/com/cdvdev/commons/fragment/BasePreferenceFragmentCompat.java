package com.cdvdev.commons.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.cdvdev.commons.R;
import com.cdvdev.commons.helpers.BaseResourcesHelper;

/**
 * Base Fragment for Preferences
 * <p>
 * Created by Dmitriy V. Chernysh on 24.02.17.
 * dmitriy.chernysh@gmail.com
 * <p>
 * www.mobile-dev.pro
 * <p>
 * #MobileDevPro
 */

public abstract class BasePreferenceFragmentCompat extends PreferenceFragmentCompat {

    private View mCurrentFrameView;

    @ColorRes
    protected abstract int getFragmentBackgroundColor();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT ||
                newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            resizeFrameView();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCurrentFrameView = ((ViewGroup) view).getChildAt(0);
        int color = getFragmentBackgroundColor();

        //set background color for this fragment
        if (mCurrentFrameView != null && color > 0) {
            mCurrentFrameView.setBackgroundColor(
                    BaseResourcesHelper.getColorCompatible(getActivity(), color)
            );
        }

        resizeFrameView();
    }

    @Override
    public void onStop() {
        //hide keyboard if it shown
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
        super.onStop();
    }

    /**
     * Resize frame view (for tablets)
     */
    private void resizeFrameView() {
        if (mCurrentFrameView == null) return;
        //set a new width for settings list if it's a Tablet in landscape mode
        boolean isTablet = getResources().getBoolean(R.bool.is_tablet);
        int[] displaySize = BaseResourcesHelper.getDisplaySize(getActivity());
        if (isTablet) {
            if (mCurrentFrameView != null) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                layoutParams.width = displaySize[0] > displaySize[1] ? displaySize[1] : displaySize[0];
                layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                mCurrentFrameView.setLayoutParams(layoutParams);
            }
        }
    }
}
