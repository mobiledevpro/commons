package com.cdvdev.commons.activity;

import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Base Class for all Activities
 * <p/>
 * Created by Dmitriy V. Chernysh on 23.05.16.
 * dmitriy.chernysh@gmail.com
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity {

    protected FragmentManager mFragmentManager;

    protected abstract void initPresenters();

    protected abstract void initToolbar();

    protected abstract void createView();

    protected abstract void populateView();

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
        createView();

        //setup toolbar
        initToolbar();

        mFragmentManager = getSupportFragmentManager();

        //setup presenters (if there is)
        initPresenters();
        //populate view
        populateView();
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
    }

    @Override
    public void setAppBarTitle(@NonNull String titleString) {
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
