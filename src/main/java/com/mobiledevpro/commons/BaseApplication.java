package com.mobiledevpro.commons;

import android.app.Application;
import android.content.Context;

/**
 * Base class for Application main class
 * <p>
 * Created by Dmitriy V. Chernysh
 *
 * https://instagr.am/mobiledevpro
 * #MobileDevPro
 */

public class BaseApplication extends Application {
    private static BaseApplication sApp;

    @Override
    public void onCreate() {
        super.onCreate();
        if (sApp == null) {
            sApp = this;
        }
    }

    public static Context getAppContext() {
        return sApp.getApplicationContext();
    }
}
