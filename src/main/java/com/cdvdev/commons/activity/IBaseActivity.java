package com.cdvdev.commons.activity;

import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

/**
 * Interface for Base Activity
 * <p>
 * Created by Dmitriy V. Chernysh on 31.10.16.
 * dmitriy.chernysh@gmail.com
 * <p>
 * www.mobile-dev.pro
 */

public interface IBaseActivity {
    void setAppBarTitle(@StringRes int titleResId);

    void setAppBarColor(@ColorRes int colorResId);
}

