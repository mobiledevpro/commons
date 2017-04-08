package com.cdvdev.commons.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.cdvdev.commons.R;
import com.cdvdev.commons.helpers.BaseResourcesHelper;

/**
 * Base Class for all Activities
 * <p/>
 * Created by Dmitriy V. Chernysh on 23.05.16.
 * dmitriy.chernysh@gmail.com
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity {

    protected FragmentManager mFragmentManager;

    protected ActionBar mActionBar;

    protected abstract void initPresenters();

    protected abstract void initToolbar();

    @LayoutRes
    protected abstract int getLayoutResId();

    protected abstract void populateView(View layoutView);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set start activity animation
        if (getStartEnterAnimation() > 0 || getStartExitAnimation() > 0) {
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
        if (getFinishEnterAnimation() > 0 || getFinishExitAnimation() > 0) {
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
    public void setAppBarColor(@ColorRes int colorResId) {
        BaseResourcesHelper.setStatusBarColor(this, colorResId);
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
            if (!TextUtils.isEmpty(subTitleString)) {
                //set appbar min height
                findViewById(R.id.appbar).setMinimumHeight(
                        (int) BaseResourcesHelper.dpToPx(this, 72)
                );
            } else {
                //remove appbar min height
                findViewById(R.id.appbar).setMinimumHeight(0);
            }

            mActionBar.setSubtitle(subTitleString);
        }
    }

    @Override
    public void setHomeAsUpIndicatorIcon(@DrawableRes int drawable) {
        //by default
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeAsUpIndicator(drawable);
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
}
