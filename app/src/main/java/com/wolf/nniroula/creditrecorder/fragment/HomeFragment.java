package com.wolf.nniroula.creditrecorder.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.wolf.nniroula.creditrecorder.R;
import com.wolf.nniroula.creditrecorder.adapter.ItemGridViewAdapter;
import com.wolf.nniroula.creditrecorder.model.RecordManager;
import com.wolf.nniroula.creditrecorder.model.SettingManager;
import com.wolf.nniroula.creditrecorder.ui.MyGridView;
import com.wolf.nniroula.creditrecorder.ui.RisingNumberView;
import com.wolf.nniroula.creditrecorder.utils.CreditUtil;


public class HomeFragment extends Fragment {

    private ObservableScrollView mScrollView;
    public MyGridView myGridView;
    private ItemGridViewAdapter itemAdapter = null;

    private RisingNumberView records;
    private TextView recordTv;
    private RisingNumberView credit;
    private TextView creditTv;
    private RisingNumberView debit;
    private TextView debitTv;
    private TextView duesTv;
    private TextView creditCurrencyTv;
    private TextView debitCurrencyTv;


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();

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
        RecordManager.initRecords();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_view, container, false);

        myGridView = (MyGridView) view.findViewById(R.id.gridview);
        itemAdapter = new ItemGridViewAdapter(activity, 0);
        if (RecordManager.TOTAL_ITEMS == 1) {
            myGridView.setNumColumns(1);
        } else if (RecordManager.TOTAL_ITEMS == 2) {
            myGridView.setNumColumns(2);
        } else {
            myGridView.setNumColumns(3);
        }

        myGridView.setAdapter(itemAdapter);

        AlphaAnimation animation1 = new AlphaAnimation(0.2f, 1.0f);
        animation1.setDuration(500);
        view.setAlpha(1f);
        view.startAnimation(animation1);

        duesTv = (TextView) view.findViewById(R.id.dues_tv);

        recordTv = (TextView) view.findViewById(R.id.record_tv);
        records = (RisingNumberView) view.findViewById(R.id.record_text);

        creditTv = (TextView) view.findViewById(R.id.credit_tv);
        credit = (RisingNumberView) view.findViewById(R.id.credit_text);

        debitTv = (TextView) view.findViewById(R.id.debit_tv);
        debit = (RisingNumberView) view.findViewById(R.id.debit_text);

        creditCurrencyTv = (TextView) view.findViewById(R.id.credit_currency);

        debitCurrencyTv = (TextView) view.findViewById(R.id.debit_currency);

        initTextComponents();

        return view;
    }

    private void initTextComponents() {

        creditCurrencyTv.setTypeface(CreditUtil.typefaceLatoLight);
        creditCurrencyTv.setText(SettingManager.getInstance().getCurrency());

        debitCurrencyTv.setTypeface(CreditUtil.typefaceLatoLight);
        debitCurrencyTv.setText(SettingManager.getInstance().getCurrency());

        duesTv.setTypeface(CreditUtil.typefaceLatoLight);

        records.setTypeface(CreditUtil.typefaceLatoLight);
        recordTv.setTypeface(CreditUtil.typefaceLatoLight);
        records.withNumber(RecordManager.TOTAL_RECORDS).setDuration(3000).start();

        credit.setTypeface(CreditUtil.typefaceLatoLight);
        creditTv.setTypeface(CreditUtil.typefaceLatoLight);
        credit.withNumber(Float.parseFloat(RecordManager.TOTAL_CREDITS.toString())).setDuration(3000).start();

        debit.setTypeface(CreditUtil.typefaceLatoLight);
        debitTv.setTypeface(CreditUtil.typefaceLatoLight);
        debit.withNumber(Float.parseFloat(RecordManager.TOTAL_DEBITS.toString())).setDuration(3000).start();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mScrollView = (ObservableScrollView) view.findViewById(R.id.scrollView);
        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView, null);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
