package com.cdvdev.commons.activity;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Base Class for all Activities
 * <p/>
 * Created by Dmitriy V. Chernysh on 23.05.16.
 * dmitriy.chernysh@gmail.com
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity {

    protected FragmentManager mFragmentManager;

    protected abstract void initToolbar();

    @LayoutRes
    protected abstract int getLayoutResId();

    protected abstract void populateView();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        initToolbar();

        mFragmentManager = getSupportFragmentManager();

        populateView();
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
}
