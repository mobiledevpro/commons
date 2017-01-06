package com.cdvdev.commons.helpers;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom text watcher for EditText fields
 * <p>
 * Created by Dmitriy V. Chernysh on 06.01.17.
 * dmitriy.chernysh@gmail.com
 * <p>
 * www.mobile-dev.pro
 */

public class EditTextWatcher implements TextWatcher {

    private List<EditText> mEditTextList;
    private CallbacksAdapter mCallbacksAdapter;

    private interface ICallbacks {
        void beforeTextChanged(CharSequence s, int start, int count, int after);

        void onTextChanged(CharSequence s, int start, int before, int count);

        void afterTextChanged(EditText editText);
    }

    public EditTextWatcher() {
    }

    public EditTextWatcher(CallbacksAdapter mCallbacksAdapter) {
        this.mCallbacksAdapter = mCallbacksAdapter;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (mCallbacksAdapter != null) {
            mCallbacksAdapter.beforeTextChanged(s, start, count, after);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mCallbacksAdapter != null) {
            mCallbacksAdapter.onTextChanged(s, start, before, count);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mEditTextList != null) {
            for (EditText field : mEditTextList) {
                if (field == null) continue;
                if (field.getText().hashCode() == s.hashCode()) {
                    if (mCallbacksAdapter != null) {
                        mCallbacksAdapter.afterTextChanged(field);
                    }
                }
            }
        }

    }

    public void applyTo(EditText... list) {
        if (list.length == 0) return;
        mEditTextList = new ArrayList<>();

        for (EditText field : list) {
            if (field == null) continue;
            mEditTextList.add(field);
            field.addTextChangedListener(this);
        }
    }

    public void applyTo(List<EditText> list) {
        mEditTextList = list;
        for (EditText field : list) {
            if (field == null) continue;
            field.addTextChangedListener(this);
        }
    }

    public static class CallbacksAdapter implements ICallbacks {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(EditText editText) {

        }
    }
}
