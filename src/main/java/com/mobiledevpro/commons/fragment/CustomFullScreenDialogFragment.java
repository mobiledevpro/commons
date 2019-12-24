package com.mobiledevpro.commons.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * Custom dialog fragment with no title bar and with translucent
 * <p>
 * Created by Dmitriy V. Chernysh
 *
 * https://instagr.am/mobiledevpro
 * #MobileDevPro
 */

public abstract class CustomFullScreenDialogFragment extends CustomDialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
    }
}
