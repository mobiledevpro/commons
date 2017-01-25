package com.cdvdev.commons.fragment.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

/**
 * Common alert dialog
 * <p>
 * Created by Dmitriy V. Chernysh on 25.01.17.
 * dmitriy.chernysh@gmail.com
 * <p>
 * www.mobile-dev.pro
 */

public class AlertDialogFragment extends DialogFragment {
    private static final String KEY_DIALOG_DATA = "dialog.data";

    public static AlertDialogFragment newInstance(AlertDialogData data) {

        Bundle args = new Bundle();
        args.putParcelable(KEY_DIALOG_DATA, data);

        AlertDialogFragment fragment = new AlertDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialogData data = getArguments().getParcelable(KEY_DIALOG_DATA);
        String title = data != null ? data.getTitle() : "";
        String message = data != null ? data.getMessage() : "";
        String btnPositive = data != null ? data.getPositiveButtonText() : "";
        String btnNegative = data != null ? data.getNegativeButtonText() : "";
        int styleResId = data != null ? data.getStyleResId() : 0;
        final AlertDialogData.AlertDialogListenerAdapter listener = data != null ? data.getDialogListenerAdapter() : null;

        AlertDialog.Builder builder;
        if (styleResId > 0) {
            builder = new AlertDialog.Builder(getActivity(), styleResId);
        } else {
            builder = new AlertDialog.Builder(getActivity());
        }

        if (!title.equals("")) {
            builder.setTitle(title);
        }
        builder.setMessage(message);

        if (!TextUtils.isEmpty(btnPositive)) {
            builder.setPositiveButton(
                    btnPositive,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (listener != null) {
                                listener.onPositiveButtonClick();
                            }
                        }
                    }
            );
        }

        if (!TextUtils.isEmpty(btnNegative)) {
            builder.setNegativeButton(
                    btnNegative,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (listener != null) {
                                listener.onNegativeButtonClick();
                            }
                        }
                    }
            );
        }


        return builder.create();
    }

}
