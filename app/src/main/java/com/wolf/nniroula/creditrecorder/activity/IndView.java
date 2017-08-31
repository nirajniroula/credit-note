package com.wolf.nniroula.creditrecorder.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.wolf.nniroula.creditrecorder.R;

/**
 * Created by Niraj on 2/3/2015.
 */
public class IndView extends Activity {
    EditText  edDate, edPaid;
    Button save, clear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ind_view);

        edDate = (EditText) findViewById(R.id.editDt);
        edPaid = (EditText) findViewById(R.id.editPd);
        save = (Button) findViewById(R.id.butSave);
        clear = (Button) findViewById(R.id.butClear);


    }

    @Override
    protected void onPause() {
        super.onPause();

        this.finish();
    }

}
