package com.mobiledevpro.commons.fragment.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

/**
 * Common alert dialog
 * <p>
 * Created by Dmitriy V. Chernysh
 * <p>
 * https://instagr.am/mobiledevpro
 * #MobileDevPro
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

        AlertDialogData data = getArguments() != null ? (AlertDialogData) getArguments().getParcelable(KEY_DIALOG_DATA) : null;
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
