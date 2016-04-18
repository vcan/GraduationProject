package com.zszdevelop.planman.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.zszdevelop.planman.R;

/**
 * Created by shengzhong on 16/1/22.
 */

public class AlertUtils {

    public static void alert(Context context, String title, String message, final AlertListener listener) {
        alert(context, title, message, listener, false);
    }

    public static void alert(Context context, String title, String message, final AlertListener listener, boolean negavite) {
        alert(context, title, message, listener, null, negavite);
    }

    public static void alert(Context context, String title, String message,
                             final AlertListener confirmlistener, final AlertListener negaviteListener, boolean negavite) {

        alert(context, title, message, R.string.confirm, R.string.cancel, confirmlistener, negaviteListener, negavite);
    }

    public static void alert(Context context, String title, String message, String okMessage ,String negaviteMessage,
                             final AlertListener confirmlistener, final AlertListener negaviteListener, boolean negavite) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);

        if (!TextUtils.isEmpty(title)) {

            dialog.setTitle(title);

        }

        if (!TextUtils.isEmpty(message)) {
            dialog.setMessage(message);
        }


        //dialog.setIcon(R.drawable.infoicon);
        dialog.setPositiveButton(okMessage, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (confirmlistener != null) {
                    confirmlistener.confirmClick();
                }
            }
        });

        if (negavite) {
            dialog.setNegativeButton(negaviteMessage, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (negaviteListener != null) {
                        negaviteListener.confirmClick();
                    }
                }
            });
        }
        dialog.show();
    }

    public static void alert(Context context, String title, String message, int okMessage ,int negaviteMessage,
                             final AlertListener confirmlistener, final AlertListener negaviteListener, boolean negavite) {
        alert(context, title, message, context.getResources().getString(okMessage),
                context.getResources().getString(negaviteMessage), confirmlistener, negaviteListener, negavite);
    }

    public static interface AlertListener {
        public void confirmClick();
    }
}
