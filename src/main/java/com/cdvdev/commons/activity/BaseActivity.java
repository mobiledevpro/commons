package com.cdvdev.commons.activity;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;

/**
 * Base Class for all Activities
 * <p/>
 * Created by Dmitriy V. Chernysh on 23.05.16.
 * dmitriy.chernysh@gmail.com
 */
public class BaseActivity extends AppCompatActivity implements IBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void setAppBarColor(@ColorRes int colorResId) {
    }

    @Override
    public void setAppBarTitle(@StringRes int titleResId) {
    }
}
