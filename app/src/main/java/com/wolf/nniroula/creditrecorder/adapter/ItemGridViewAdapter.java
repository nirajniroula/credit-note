package com.wolf.nniroula.creditrecorder.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wolf.nniroula.creditrecorder.R;
import com.wolf.nniroula.creditrecorder.model.RecordManager;
import com.wolf.nniroula.creditrecorder.model.SettingManager;
import com.wolf.nniroula.creditrecorder.utils.CircleView;
import com.wolf.nniroula.creditrecorder.utils.CreditUtil;


public class ItemGridViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context mContext;
    private int fragmentPosition;
    private int count = 0;

    public ItemGridViewAdapter(Context context, int fragmentPosition) {
        this.inflater = LayoutInflater.from(context);
        this.mContext = context;
        this.fragmentPosition = fragmentPosition;
    }

    @Override
    public int getCount() {
        return RecordManager.TOTAL_ITEMS;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = this.inflater.inflate(R.layout.item_tag, null);

            holder.tagCurrencyTv = (TextView) convertView.findViewById(R.id.due_currency);
            holder.tagCurrencyTv.setTypeface(CreditUtil.typefaceLatoLight);
            holder.tagCurrencyTv.setText(SettingManager.getInstance().getCurrency());

            holder.tagName = (TextView) convertView.findViewById(R.id.tag_text);
            holder.tagName.setTypeface(CreditUtil.typefaceLatoLight);

            holder.tagLetter = (TextView) convertView.findViewById(R.id.tag_letter);
            holder.tagLetter.setTypeface(CreditUtil.typefaceBig);


            holder.tagDue = (TextView) convertView.findViewById(R.id.tag_due);
            holder.tagDue.setTypeface(CreditUtil.typefaceLatoLight);


            holder.tagDueTv = (TextView) convertView.findViewById(R.id.tag_due_tv);
            holder.tagDueTv.setTypeface(CreditUtil.typefaceLatoLight);


            String tagName = RecordManager.ALL_ITEMS.get(position).getItem_name();
            holder.tagName.setText(tagName);
            holder.tagDue.setText(RecordManager.CREDIT_BY_ITEMS.get(tagName).toString());


            holder.tagLetter.setText(CreditUtil.getFirstLetter(tagName));
            //holder.tagLetter.setTextColor(CreditUtil.getTagColor(tagName));

            holder.circleView = (CircleView) convertView.findViewById(R.id.tag_image);
            holder.circleView.setBitmapResource(CreditUtil.getTagColor(tagName));


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    private class ViewHolder {
        TextView tagName;
        TextView tagLetter;
        TextView tagDue;
        TextView tagDueTv;
        TextView tagCurrencyTv;
        CircleView circleView;

        CardView container;
    }
}
