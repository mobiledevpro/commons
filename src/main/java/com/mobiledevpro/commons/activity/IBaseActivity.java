package com.mobiledevpro.commons.activity;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

/**
 * Interface for Base Activity
 * <p>
 * Created by Dmitriy V. Chernysh
 *
 * https://instagr.am/mobiledevpro
 * #MobileDevPro
 */

public interface IBaseActivity {
    void setAppBarTitle(@NonNull String titleString);

    void setAppBarSubTitle(@NonNull String subTitleString);

    void setStatusBarColor(@ColorRes int colorResId);

    void setAppBarColor(@ColorRes int colorResId);

    void setHomeAsUpIndicatorIcon(@DrawableRes int drawable);
}

