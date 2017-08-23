package com.wolf.nniroula.creditrecorder.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.ContextThemeWrapper;
import android.widget.EditText;

import com.wolf.nniroula.creditrecorder.R;

/**
 * Created by root on 7/21/17.
 */

public class EditTextDialogUtils {

    public static AlertDialog.Builder EditTextDialog(String title, String message, Context context) {
        AlertDialog.Builder alert = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.myDialog));
        //AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title); //Set Alert dialog title here
        alert.setMessage(message); //Message here
        alert.setCancelable(false);
        return alert;
    }

    public static EditText TextField(Context context) {
        EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setTextColor(Color.BLACK);
        return input;
    }
}
