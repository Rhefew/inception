package com.rhefew.cocdrive;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by rodrigo on 18/09/14.
 */
public class Print {

    public static void dialog(Context context, Object message){

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message.toString());
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }
}
