package com.mobiledevpro.commons.activity;

/**
 * Base activity without toolbar (fullscreen activity)
 * <p>
 * Created by Dmitriy V. Chernysh
 *
 * https://instagr.am/mobiledevpro
 * #MobileDevPro
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
