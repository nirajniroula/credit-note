package com.wolf.nniroula.creditrecorder.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wolf.nniroula.creditrecorder.dbhelper.Recorder;
import com.wolf.nniroula.creditrecorder.R;
import com.wolf.nniroula.creditrecorder.utils.FormatLargeNumber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nniroula on 5/18/16.
 */
public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {

    private static final String TABLE_CONTACTS = "contacts";
    private Recorder db;
    private OnItemClickListener mItemClickListener;
    private Context mContext;
    public static List<RecordModel> mRecordModel = null;
    private List<RecordModel> cleanRecordModel = null;
    private List<PaidModel> PaidModel = null;
    private List<ItemModel> ItemModel = null;
    private PreferenceCredit preferenceCredit;


    // Record Adapter's Constructor.
    public RecordAdapter(Context context) {
        super();

        this.mContext = context;
        db = new Recorder(mContext);
        this.mRecordModel = new ArrayList<>();
        mRecordModel = db.getAllEntries();
        Log.e("msg", "constructor");

    }

    public List<RecordModel> readCleanRecords() {
        this.mRecordModel = new ArrayList<>();
        mRecordModel = db.getAllEntries();
        cleanRecordModel = new ArrayList<>();
        cleanRecordModel = checkDuplicateList();
        checkDuplicateList();
        Log.e("msg", "readRecords");
        return cleanRecordModel;

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
            Name = (TextView) itemView.findViewById(R.id.text_name);
            Credit = (TextView) itemView.findViewById(R.id.text_baki_amt);
            Currency = (TextView) itemView.findViewById(R.id.text_nrs);

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

    //Check whether there are more than 1 records for a user.
    private List<RecordModel> checkDuplicateList() {
        List<RecordModel> cleanRecordModel;
        String Items;
        cleanRecordModel = mRecordModel;
        for (int j = 0; j < cleanRecordModel.size(); j++) {
            //Log.e("sRemoves", mRecordModel.get(j).getName());
            Items = cleanRecordModel.get(j).getItem();
            for (int k = j + 1; k < cleanRecordModel.size(); k++) {
                if (j != k && (cleanRecordModel.get(k).getName()).equals(cleanRecordModel.get(j).getName())) {
                    if (!cleanRecordModel.get(j).getItem().contains(cleanRecordModel.get(k).getItem())) {

                        Items = Items + cleanRecordModel.get(k).getItem();

                    }
                    cleanRecordModel.get(j).setPrice(cleanRecordModel.get(j).getPrice() + cleanRecordModel.get(k).getPrice());
                    cleanRecordModel.remove(k);
                } else {
                    // Log.e("Not Removed", cleanRecordModel.get(k).getName());
                }
            }
            cleanRecordModel.get(j).setItem(managedItems(Items));

        }
        return cleanRecordModel;
    }

    private String managedItems(String allItems) {
        ItemModel = db.getAllPrices();
        String filteredItems = "";
        for (int i = 0; i < ItemModel.size(); i++) {
            if (allItems.contains(ItemModel.get(i).getItem_name()))
                filteredItems = filteredItems + " + " + ItemModel.get(i).getItem_name();
        }
        if (filteredItems.startsWith(" + ")) filteredItems = filteredItems.substring(3);
        return filteredItems;
    }

    public Double getPaid(String name) {
        Double totalPaid = 0.00;
        PaidModel = db.getAllIndPaid(name);
        for (int i = 0; i < PaidModel.size(); i++) {
            totalPaid = totalPaid + PaidModel.get(i).getPaid();
        }
        db.close();
        return totalPaid;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.Currency.setText(preferenceCredit.getCurrency() + " ");
        holder.Name.setText(cleanRecordModel.get(position).getName());
        holder.Item.setText(cleanRecordModel.get(position).getItem());
        Double Credit = cleanRecordModel.get(position).getPrice() - getPaid(cleanRecordModel.get(position).getName());
        holder.Credit.setText(FormatLargeNumber.formattedNumber(Credit));
        if (Credit != 0.0) {
            Log.e("Credit", "No Credit");
            holder.Check.setVisibility(View.INVISIBLE);
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
        Log.e("Notify", "Bhayo");
        notifyDataSetChanged();
    }


}

