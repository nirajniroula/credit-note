package com.wolf.nniroula.creditrecorder.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wolf.nniroula.creditrecorder.R;
import com.wolf.nniroula.creditrecorder.adapter.RecordAdapter;
import com.wolf.nniroula.creditrecorder.dbhelper.Recorder;
import com.wolf.nniroula.creditrecorder.dialogs.ProfileDialog;
import com.wolf.nniroula.creditrecorder.dialogs.SettingDialog;
import com.wolf.nniroula.creditrecorder.model.RecordModel;
import com.wolf.nniroula.creditrecorder.ui.interfaces.MyDialogListener;
import com.wolf.nniroula.creditrecorder.utils.PreferenceCredit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Niraj on 1/29/2015.
 */
public class ToView extends AppCompatActivity implements SearchView.OnQueryTextListener, MyDialogListener {

    private RecyclerView mRecyclerView;
    private RecordAdapter mAdapter;
    private List<RecordModel> mContactModel;
    private PreferenceCredit preferenceCredit;
    private TextView emptyText;
    private static final String EMPTY_TEXT = "NO RECORDS FOUND !";
    private Recorder db;
    private boolean doubleBackToExitPressedOnce = false;
    private FloatingActionButton add;
    protected static ToView fa;

    private static final String TAG = "ToView";
    private static final String DATABASE_NAME = "MillRecord.db";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sql_out);

        fa = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.to_view_name);
        try {
            db = new Recorder(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        preferenceCredit = new PreferenceCredit(this);


        emptyText = (TextView) findViewById(R.id.empty_text);
        add = (FloatingActionButton) findViewById(R.id.floatAdd);

        // The number of Columns
        final StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecordAdapter(this);

        mContactModel = new ArrayList<>();
        mContactModel = mAdapter.readCleanRecords();

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(onItemClickListener);

        handleEmptyText();


//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), MyActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 || dy < 0 && add.isShown()) {
                    add.hide();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    add.show();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Touch again to exit.", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void onStop() {
        super.onStop();
        // Disconnect the client from Google Drive
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Create the Drive API instance
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.my, menu);

        final MenuItem item = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        invalidateOptionsMenu();
                        mAdapter.setFilter(mContactModel);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        menu.removeGroup(R.id.menu_group_others);
                        return true; // Return true to expand action view
                    }


                });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        List<String> currency = Arrays.asList(getResources().getStringArray(R.array.pref_currency_keys));


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showSettingDialog();
            return true;
        }

        if (id == R.id.currency) {

            final ArrayAdapter<String> adp = new ArrayAdapter<String>(ToView.this,
                    android.R.layout.simple_selectable_list_item, currency);

            final Spinner sp = new Spinner(ToView.this);
            sp.setLayoutParams(new LinearLayout.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            sp.setAdapter(adp);
            sp.setSelection(adp.getPosition(preferenceCredit.getCurrency()), true);

            AlertDialog.Builder builder =
                    new AlertDialog.Builder(ToView.this, R.style.myDialog);
            builder.setTitle("Currency");
            builder.setView(sp);
            builder.setCancelable(true);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    preferenceCredit.setCurrency(sp.getSelectedItem().toString());
                    sp.setSelection(sp.getSelectedItemPosition(), true);

                    dialog.dismiss();
                }
            });
            builder.create().show();
            return true;
        }

        if (id == R.id.about) {
            final TextView textView = new TextView(ToView.this);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            textView.setLayoutParams(new LinearLayout.LayoutParams
                    (RecyclerView.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setText(R.string.about_link);
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(ToView.this, R.style.myDialog);
            builder.setTitle("Credit Note");
            builder.setIcon(R.drawable.credit_logo);
            builder.setMessage(R.string.about);
            builder.setView(textView);
            builder.setCancelable(true);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mContactModel = mAdapter.readCleanRecords();
        final List<RecordModel> filteredModelList = filter(mContactModel, newText);
        mAdapter.setFilter(filteredModelList);
        mContactModel = filteredModelList;
        handleEmptyText();
        return true;
    }

    private List<RecordModel> filter(List<RecordModel> models, String query) {
        query = query.toLowerCase();

        final List<RecordModel> filteredModelList = new ArrayList<>();
        for (RecordModel model : models) {
            final String text = model.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    RecordAdapter.OnItemClickListener onItemClickListener = new RecordAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {

            showProfileDialog(mContactModel.get(position));

        }
    };

    MyDialogListener onDialogCloseListener = new MyDialogListener() {
        @Override
        public void handleDialogDismiss(DialogInterface dialogInterface) {
            mContactModel = mAdapter.readCleanRecords();
            mAdapter.setFilter(mContactModel);
            handleEmptyText();
        }
    };

    public void showProfileDialog(RecordModel cMod) {
        FragmentManager fm = getSupportFragmentManager();
        ProfileDialog pd = new ProfileDialog();
        pd.setInfo(cMod, this);
        pd.show(fm, "dialogProfile");
        pd.DismissListener(onDialogCloseListener);
    }

    //TODO:Settings
    public void showSettingDialog() {
        FragmentManager fm = getSupportFragmentManager();
        SettingDialog sd = new SettingDialog();
        sd.setInfo(this);
        sd.show(fm, "dialogSettings");
    }

    @Override
    public void handleDialogDismiss(DialogInterface dialogInterface) {

    }

    public void handleEmptyText() {
        if (mAdapter.getItemCount() == 0) {
            emptyText.setText(EMPTY_TEXT);
        } else {
            emptyText.setText("");
        }
    }

    private File getDbPath() {
        return this.getDatabasePath(DATABASE_NAME);
    }


}
