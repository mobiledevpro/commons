package com.mobiledevpro.commons.fragment.dialog;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StyleRes;

/**
 * Model class for Alert Dialog
 * <p>
 * Created by Dmitriy V. Chernysh on 25.01.17.
 * dmitriy.chernysh@gmail.com
 * <p>
 * www.mobile-dev.pro
 */

public class AlertDialogData implements Parcelable {

    private String mTitle;
    private String mMessage;
    private String mPositiveButtonText;
    private String mNegativeButtonText;
    private AlertDialogListenerAdapter mDialogListenerAdapter;
    private int mStyleResId;

    private interface Listener extends Parcelable {
        void onPositiveButtonClick();

        void onNegativeButtonClick();
    }

    /**
     * Main constructor
     *
     * @param title                 Dialog Title
     * @param message               Dialog Message
     * @param positiveButtonText    Positive button message
     * @param negativeButtonText    Negative button message
     * @param dialogListenerAdapter Buttons Listener
     */
    public AlertDialogData(String title, String message, String positiveButtonText, String negativeButtonText, AlertDialogListenerAdapter dialogListenerAdapter) {
        mTitle = title;
        mMessage = message;
        mPositiveButtonText = positiveButtonText;
        mNegativeButtonText = negativeButtonText;
        mDialogListenerAdapter = dialogListenerAdapter;
    }

    /**
     * Constructor for Parcel
     *
     * @param source Parcel
     */
    private AlertDialogData(Parcel source) {
        mTitle = source.readString();
        mMessage = source.readString();
        mPositiveButtonText = source.readString();
        mNegativeButtonText = source.readString();
        mDialogListenerAdapter = source.readParcelable(Listener.class.getClassLoader());
    }

    public String getTitle() {
        return mTitle;
    }

    public String getMessage() {
        return mMessage;
    }

    public String getPositiveButtonText() {
        return mPositiveButtonText;
    }

    public String getNegativeButtonText() {
        return mNegativeButtonText;
    }

    public AlertDialogListenerAdapter getDialogListenerAdapter() {
        return mDialogListenerAdapter;
    }

    @StyleRes
    public int getStyleResId() {
        return mStyleResId;
    }

    public void setStyleResId(@StyleRes int styleResId) {
        mStyleResId = styleResId;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mMessage);
        dest.writeString(mPositiveButtonText);
        dest.writeString(mNegativeButtonText);
        dest.writeParcelable(mDialogListenerAdapter, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AlertDialogData> CREATOR = new Creator<AlertDialogData>() {
        @Override
        public AlertDialogData createFromParcel(Parcel source) {
            return new AlertDialogData(source);
        }

        @Override
        public AlertDialogData[] newArray(int size) {
            return new AlertDialogData[size];
        }
    };


    /**
     * Listener Adapter for dialog actions
     */
    public static class AlertDialogListenerAdapter implements Listener {

        public static final Creator<AlertDialogListenerAdapter> CREATOR = new Creator<AlertDialogListenerAdapter>() {

            public AlertDialogListenerAdapter createFromParcel(Parcel source) {
                return new AlertDialogListenerAdapter(source);
            }

            @Override
            public AlertDialogListenerAdapter[] newArray(int size) {
                return new AlertDialogListenerAdapter[size];
            }
        };

        public AlertDialogListenerAdapter() {
        }

        public AlertDialogListenerAdapter(Parcel source) {
        }

        @Override
        public void onPositiveButtonClick() {

        }

        @Override
        public void onNegativeButtonClick() {

        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }
    }
}
