package com.sairanadheer.bharatagriassignment.util;

import android.app.AlertDialog;
import android.content.Context;

public class Common {

    public static void showAlert(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(Constants.ERROR);
        builder.setMessage(Constants.SOMETHING_WENT_WRONG);
        builder.setPositiveButton(android.R.string.yes, (dialog, which) -> dialog.dismiss());
        builder.setCancelable(false);
        builder.create().show();
    }
}
