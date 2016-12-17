package com.cdvdev.commons.activity;

/**
 * Base activity without toolbar (fullscreen activity)
 * <p>
 * Created by Dmitriy V. Chernysh on 17.12.16.
 * dmitriy.chernysh@gmail.com
 * <p>
 * www.mobile-dev.pro
 */

public abstract class BaseActivityFullScreen extends BaseActivity {

    @Override
    protected void initToolbar() {
        //no toolbar for this activity
    }

    @Override
    protected boolean isFullScreenActivity() {
        return true;
    }
}
