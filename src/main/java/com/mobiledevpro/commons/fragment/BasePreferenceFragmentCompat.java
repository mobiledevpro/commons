package com.mobiledevpro.commons.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.mobiledevpro.commons.R;
import com.mobiledevpro.commons.helpers.BaseResourcesHelper;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;

/**
 * Base Fragment for Preferences
 * <p>
 * Created by Dmitriy V. Chernysh
 *
 * https://instagr.am/mobiledevpro
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
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT ||
                newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            resizeFrameView();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
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
        if (getActivity() != null) {
            //hide keyboard if it shown
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            View view = getActivity().getCurrentFocus();
            if (view != null && inputManager != null) {
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        }
        super.onStop();
    }

    /**
     * Resize frame view (for tablets)
     */
    private void resizeFrameView() {
        if (mCurrentFrameView == null) return;
        if (getActivity() == null) return;
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
