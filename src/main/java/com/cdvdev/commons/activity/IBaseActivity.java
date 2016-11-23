package com.cdvdev.commons.activity;

import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;

/**
 * Interface for Base Activity
 * <p>
 * Created by Dmitriy V. Chernysh on 31.10.16.
 * dmitriy.chernysh@gmail.com
 * <p>
 * www.mobile-dev.pro
 */

public interface IBaseActivity {
    void setAppBarTitle(@NonNull String titleString);

    void setAppBarColor(@ColorRes int colorResId);
}

