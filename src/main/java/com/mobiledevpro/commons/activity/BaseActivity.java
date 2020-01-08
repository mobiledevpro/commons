package com.mobiledevpro.commons.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.mobiledevpro.commons.R;
import com.mobiledevpro.commons.helpers.BaseResourcesHelper;

import androidx.annotation.AnimRes;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;

/**
 * Base Class for all Activities
 * <p/>
 * Created by Dmitriy V. Chernysh
 *
 * https://instagr.am/mobiledevpro
 * #MobileDevPro
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    protected FragmentManager mFragmentManager;

    protected ActionBar mActionBar;

    protected abstract void initPresenters();

    protected abstract void initToolbar();

    @LayoutRes
    protected abstract int getLayoutResId();

    protected abstract void populateView(View layoutView);

    protected abstract boolean isAdjustFontScaleToNormal();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (isAdjustFontScaleToNormal()) {
            adjustFontScaleToNormal(getResources().getConfiguration());
        }
        super.onCreate(savedInstanceState);
        //set start activity animation
        if (getStartEnterAnimation() != 0 || getStartExitAnimation() != 0) {
            overridePendingTransition(
                    getStartEnterAnimation(),
                    getStartExitAnimation());
        }
        //set fullscreen mode
        if (isFullScreenActivity()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                getSupportActionBar().hide();
            }
        }

        //setup view
        setContentView(getLayoutResId());

        //setup presenters (if there is)
        initPresenters();

        //setup toolbar
        initToolbar();

        mActionBar = getSupportActionBar();

        mFragmentManager = getSupportFragmentManager();

        //populate view
        View layoutView = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        populateView(layoutView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStop() {
        //hide keyboard if it shown
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
        super.onStop();
    }

    @Override
    public void finish() {
        super.finish();
        //setup finish activity animation
        if (getFinishEnterAnimation() != 0 || getFinishExitAnimation() != 0) {
            overridePendingTransition(
                    getFinishEnterAnimation(),
                    getFinishExitAnimation()
            );
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void setStatusBarColor(@ColorRes int colorResId) {
        BaseResourcesHelper.setStatusBarColor(this, colorResId);
    }

    @Override
    public void setAppBarColor(int colorResId) {
        if (mActionBar == null) return;
        mActionBar.setBackgroundDrawable(
                new ColorDrawable(
                        BaseResourcesHelper.getColorCompatible(this, colorResId)
                )
        );
    }

    @Override
    public void setAppBarTitle(@NonNull String titleString) {
        //by default
        if (mActionBar != null) {
            mActionBar.setTitle(titleString);
        }
    }

    @Override
    public void setAppBarSubTitle(@NonNull String subTitleString) {
        //by default
        if (mActionBar != null) {
            View view = findViewById(R.id.appbar);
            if (view != null) {
                if (!TextUtils.isEmpty(subTitleString)) {
                    //set appbar min height
                    view.setMinimumHeight(
                            (int) BaseResourcesHelper.dpToPx(this, 72)
                    );
                } else {
                    //remove appbar min height
                    view.setMinimumHeight(0);
                }
            }

            mActionBar.setSubtitle(subTitleString);
        }
    }

    @Override
    public void setHomeAsUpIndicatorIcon(@DrawableRes int drawable) {
        //by default
        if (mActionBar != null) {
            if (drawable != 0) {
                mActionBar.setDisplayHomeAsUpEnabled(true);
                mActionBar.setHomeAsUpIndicator(drawable);
            } else {
                mActionBar.setDisplayHomeAsUpEnabled(false);
            }
        }
    }

    /**
     * Start activity enter animation (current activity will be animated on starting)
     *
     * @return Animation resource id
     */
    @AnimRes
    protected int getStartEnterAnimation() {
        return 0;
    }

    /**
     * Start activity exit animation (previous activity will be animated on finishing)
     *
     * @return Animation resource id
     */
    @AnimRes
    protected int getStartExitAnimation() {
        return 0;
    }

    /**
     * Finish activity enter animation (new activity will be animated on starting)
     *
     * @return Animation resource id
     */
    @AnimRes
    protected int getFinishEnterAnimation() {
        return 0;
    }

    /**
     * Finish activity exit animation (current activity will be animated on finishing)
     *
     * @return Animation resource id
     */
    @AnimRes
    protected int getFinishExitAnimation() {
        return 0;
    }

    protected boolean isFullScreenActivity() {
        return false;
    }

    /**
     * This method helps to ignore a device font scale.
     * NOTE: User has ability to change font scale in device settings,
     * in this case our font will be scaled too in the app
     *
     * @param configuration
     */
    private void adjustFontScaleToNormal(Configuration configuration) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        if (wm == null) return;

        int densityDpiStable = Configuration.DENSITY_DPI_UNDEFINED;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            densityDpiStable = DisplayMetrics.DENSITY_DEVICE_STABLE; //480
        }

        //Device may has different screen resolution modes.
        //As example, Samsung S8: 422 in FHD+, 562 in WQHD+
        int xDpi = (int) getResources().getDisplayMetrics().xdpi;

        //round it to bigger value
        if (xDpi > DisplayMetrics.DENSITY_XXHIGH) {
            xDpi = DisplayMetrics.DENSITY_XXXHIGH; //640
        } else if (xDpi > DisplayMetrics.DENSITY_XHIGH) {
            xDpi = DisplayMetrics.DENSITY_XXHIGH; //480
        } else if (xDpi > DisplayMetrics.DENSITY_HIGH) {
            xDpi = DisplayMetrics.DENSITY_XHIGH; //320
        }

        if (xDpi > 0)
            densityDpiStable = xDpi;

        int densityDpiDefault = DisplayMetrics.DENSITY_DEFAULT;
        float densityDefault = (float) densityDpiStable / densityDpiDefault;

        //ignore system zoom setting, set zoom by default
        //NOTE: the smaller densityDpiStable the zoom is smaller
        configuration.densityDpi = densityDpiStable;

        //Set font scale by default
        configuration.fontScale = (float) 1.00;
        //ignore system scaling for fonts, set scale factor by default
        metrics.density = densityDefault;
        metrics.scaledDensity = configuration.fontScale * densityDefault;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);
    }
}
