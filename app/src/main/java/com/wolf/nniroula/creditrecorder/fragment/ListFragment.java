package com.wolf.nniroula.creditrecorder.fragment;

/**
 * Created by Niraj Niroula on 8/26/17.
 */

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.wolf.nniroula.creditrecorder.R;
import com.wolf.nniroula.creditrecorder.adapter.RecordAdapter;
import com.wolf.nniroula.creditrecorder.controller.HomeController;
import com.wolf.nniroula.creditrecorder.dialogs.ProfileDialog;
import com.wolf.nniroula.creditrecorder.model.RecordManager;
import com.wolf.nniroula.creditrecorder.model.RecordModel;
import com.wolf.nniroula.creditrecorder.ui.interfaces.MyDialogListener;
import com.wolf.nniroula.creditrecorder.utils.MyEditText;
import com.wolf.nniroula.creditrecorder.utils.PreferenceCredit;

import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.ArrayList;
import java.util.List;


public class ListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecordAdapter mAdapter;
    private List<RecordModel> mContactModel;
    private PreferenceCredit preferenceCredit;
    private TextView emptyText;
    private static final String EMPTY_TEXT = "NO RECORDS FOUND !";
    private MyEditText searchView;
    private ObservableScrollView mScrollView;
    private AdView mAdView;

    private TextView textSearch;
    private MaterialIconView searchIcon;


    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();

        return fragment;
    }

    private Activity activity;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_sqlview, container, false);

        preferenceCredit = new PreferenceCredit(mContext);
        mAdView = (AdView) view.findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice("531B50B5552B5DDE6C89191B029E1E2B").build();
        mAdView.loadAd(adRequest);

        emptyText = (TextView) view.findViewById(R.id.empty_text);

        // The number of Columns
        layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);


        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new RecordAdapter(mContext);

        mContactModel = new ArrayList<>();
        mContactModel = mAdapter.readCleanRecords();

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(onItemClickListener);


        handleEmptyText();

        searchView = (MyEditText) view.findViewById(R.id.auto_text);
        searchView.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                mContactModel = mAdapter.readCleanRecords();
                final List<RecordModel> filteredModelList = filter(mContactModel, s + "");
                mAdapter.setFilter(filteredModelList);
                mContactModel = filteredModelList;
                handleEmptyText();
            }
        });

        AlphaAnimation animation1 = new AlphaAnimation(0.2f, 1.0f);
        animation1.setDuration(1000);
        view.setAlpha(1f);
        view.startAnimation(animation1);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mScrollView = (ObservableScrollView) view.findViewById(R.id.scrollView);
        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView, null);


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

            if (ProfileDialog.USER_NAME != null) {

                HomeController.addInterface.onAddClick();
            }
            RecordManager.initRecords();
            mContactModel = mAdapter.readCleanRecords();
            mAdapter.setFilter(mContactModel);
            mAdapter.notifyDataSetChanged();
            handleEmptyText();
        }
    };

    public void showProfileDialog(RecordModel cMod) {
        FragmentManager fm = getFragmentManager();
        ProfileDialog pd = new ProfileDialog();
        pd.setInfo(cMod, activity);
        pd.show(fm, "dialogProfile");
        pd.DismissListener(onDialogCloseListener);
    }

    public void handleEmptyText() {
        if (mAdapter.getItemCount() == 0) {
            emptyText.setText(EMPTY_TEXT);
        } else {
            emptyText.setText("");
        }
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

    @Override
    public void onResume() {
        mAdapter.notifyDataSetChanged();
        if (mAdView != null) {
            mAdView.resume();
        }
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }
}

