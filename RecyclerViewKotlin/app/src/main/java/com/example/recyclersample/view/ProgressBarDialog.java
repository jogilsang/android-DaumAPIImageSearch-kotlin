package com.example.recyclersample.view;

import android.app.Dialog;
import android.content.Context;

import com.example.recyclersample.R;


/**
 * Created by EunsilJo on 2018. 1. 6..
 */

public class ProgressBarDialog extends Dialog {

    public static ProgressBarDialog getInstance(Context context) {
        return new ProgressBarDialog(context, R.style.ProgressBarDialog);
    }

    public ProgressBarDialog(Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.kakaoimage_dialog_progress_bar);
        setCancelable(false);
    }
}

