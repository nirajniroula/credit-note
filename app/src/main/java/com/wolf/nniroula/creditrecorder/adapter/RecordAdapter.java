package com.wolf.nniroula.creditrecorder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wolf.nniroula.creditrecorder.R;
import com.wolf.nniroula.creditrecorder.activity.CreditApplication;
import com.wolf.nniroula.creditrecorder.model.ItemModel;
import com.wolf.nniroula.creditrecorder.model.PaidModel;
import com.wolf.nniroula.creditrecorder.model.RecordManager;
import com.wolf.nniroula.creditrecorder.model.RecordModel;
import com.wolf.nniroula.creditrecorder.model.SettingManager;
import com.wolf.nniroula.creditrecorder.utils.CreditUtil;
import com.wolf.nniroula.creditrecorder.utils.FormatLargeNumber;
import com.wolf.nniroula.creditrecorder.utils.PreferenceCredit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by nniroula on 5/18/16.
 */

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {

    private static final String TABLE_CONTACTS = "contacts";
    private OnItemClickListener mItemClickListener;
    private Context mContext;
    public static List<RecordModel> mRecordModel = null;
    private ArrayList<RecordModel> cleanRecordModel = null;
    private ArrayList<PaidModel> paidModel = null;
    private ArrayList<ItemModel> itemModel = null;
    private PreferenceCredit preferenceCredit;
    private static final int SORT_BY_NAME = 1;
    private static final int SORT_BY_CREDIT = 2;


    // Record Adapter's Constructor.
    public RecordAdapter(Context context) {
        super();

        setHasStableIds(true);
        this.mContext = context;
        mRecordModel = new ArrayList<>();
        mRecordModel = RecordManager.ALL_RECORDS;

    }

    public ArrayList<RecordModel> readCleanRecords() {

        cleanRecordModel = RecordManager.ALL_SINGLE_RECORDS;
        if (SettingManager.getInstance().getSort().equals("Name")) {
            sorted(SORT_BY_NAME);

        } else if (SettingManager.getInstance().getSort().equals("Credit")) {
            sorted(SORT_BY_CREDIT);
        }
        return cleanRecordModel;

    }

    private void sorted(int sortType) {
        switch (sortType) {
            case 1:
                Collections.sort(cleanRecordModel, new Comparator<RecordModel>() {
                    public int compare(RecordModel r1, RecordModel r2) {
                        return r1.getName().compareTo(r2.getName());
                    }
                });
                break;
            case 2:
                Collections.sort(cleanRecordModel, new Comparator<RecordModel>() {
                    public int compare(RecordModel r1, RecordModel r2) {
                        return r1.getPrice().compareTo(r2.getPrice());
                    }
                });

                Collections.reverse(cleanRecordModel);
                break;

        }
    }


    //The layout items definition and functionality goes here.
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView Name;
        private TextView Currency;
        private TextView Item;
        private TextView Credit;
        private ImageView Check;
        private LinearLayout contactHolder;

        private ViewHolder(View itemView) {
            super(itemView);

            Item = (TextView) itemView.findViewById(R.id.text_item);
            Item.setTypeface(CreditUtil.typefaceLatoLight);

            Name = (TextView) itemView.findViewById(R.id.text_name);
            Name.setTypeface(CreditUtil.typefaceLatoLight);

            Credit = (TextView) itemView.findViewById(R.id.text_baki_amt);
            Credit.setTypeface(CreditUtil.typefaceLatoLight);

            Currency = (TextView) itemView.findViewById(R.id.text_nrs);
            Currency.setTypeface(CreditUtil.typefaceLatoLight);

            Check = (ImageView) itemView.findViewById(R.id.check);
            preferenceCredit = new PreferenceCredit(mContext);


            contactHolder = (LinearLayout) itemView.findViewById(R.id.listing);
            contactHolder.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }

    }


    //OnClick handling.
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    //These overridden function below here helps in designing the layout of the recyclerView with required condition.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sql_content, parent, false);
        return new ViewHolder(view);

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Double Due = cleanRecordModel.get(position).getPrice() - RecordManager.getPaid(cleanRecordModel.get(position).getName());

        if (SettingManager.getInstance().getIsColorRemind()) {
            if (Due > SettingManager.getInstance().getDueLimit()) {
                holder.Name.setTextColor(SettingManager.getInstance().getRemindColor());
                holder.Item.setTextColor(SettingManager.getInstance().getRemindColor());
            } else {
                holder.Name.setTextColor(CreditApplication.getAppContext().getResources().getColor(R.color.colorExtra2));
                holder.Item.setTextColor(CreditApplication.getAppContext().getResources().getColor(R.color.colorExtra1));
            }
        }

        holder.Currency.setText(SettingManager.getInstance().getCurrency() + " ");
        holder.Name.setText(cleanRecordModel.get(position).getName());
        holder.Item.setText(cleanRecordModel.get(position).getItem());
        holder.Credit.setText(FormatLargeNumber.formattedNumber(Due));
        if (Due != 0.0) {
            holder.Check.setVisibility(View.INVISIBLE);
        } else {
            holder.Check.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return cleanRecordModel.size();
    }


    //Function to Display require item according to search.
    public void setFilter(List<RecordModel> contactModels) {
        cleanRecordModel = new ArrayList<>();
        cleanRecordModel.addAll(contactModels);
        notifyDataSetChanged();
    }
}

