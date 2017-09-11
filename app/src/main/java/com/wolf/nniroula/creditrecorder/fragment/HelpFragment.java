package com.wolf.nniroula.creditrecorder.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.squareup.picasso.Picasso;
import com.wolf.nniroula.creditrecorder.R;
import com.wolf.nniroula.creditrecorder.utils.CreditUtil;


public class HelpFragment extends Fragment {

    private ObservableScrollView mScrollView;

    public static HelpFragment newInstance() {
        HelpFragment fragment = new HelpFragment();
        return fragment;
    }

    private Activity activity;
    private Context mContext;

    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;
    private ImageView imageView6;
    private ImageView imageView7;
    private ImageView imageView8;
    private ImageView imageView9;

    private TextView title;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private TextView textView6;
    private TextView textView7;
    private TextView textView8;
    private TextView textView9;
    private TextView textView10;
    private TextView foot;

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
        return inflater.inflate(R.layout.fragment_help_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mScrollView = (ObservableScrollView) view.findViewById(R.id.scrollView);
        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView, null);

        int width = CreditUtil.getInstance().getScreenWidth(mContext) - CreditUtil.getInstance().changeDpToPx(24);
        int height = width * 653 / 1280;

        title = (TextView) view.findViewById(R.id.title);
        title.setTypeface(CreditUtil.getInstance().typefaceLatoLight);

        imageView1 = (ImageView) view.findViewById(R.id.help_credit_image_1);
        ViewGroup.LayoutParams layoutParams = imageView1.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        Picasso.with(mContext)
                .load(R.drawable.help0)
                .resize(width, height)
                .centerCrop()
                .into(imageView1);
        textView2 = (TextView) view.findViewById(R.id.help_credit_content_2);
        textView2.setTypeface(CreditUtil.getInstance().typefaceLatoLight);

        imageView2 = (ImageView) view.findViewById(R.id.help_credit_image_2);
        layoutParams = imageView2.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        Picasso.with(mContext)
                .load(R.drawable.help1)
                .resize(width, height)
                .centerCrop()
                .into(imageView2);
        textView3 = (TextView) view.findViewById(R.id.help_credit_content_3);
        textView3.setTypeface(CreditUtil.getInstance().typefaceLatoLight);

        imageView3 = (ImageView) view.findViewById(R.id.help_credit_image_3);
        layoutParams = imageView3.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        Picasso.with(mContext)
                .load(R.drawable.help2)
                .resize(width, height)
                .centerCrop()
                .into(imageView3);
        textView4 = (TextView) view.findViewById(R.id.help_credit_content_4);
        textView4.setTypeface(CreditUtil.getInstance().typefaceLatoLight);

        imageView4 = (ImageView) view.findViewById(R.id.help_credit_image_4);
        layoutParams = imageView4.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        Picasso.with(mContext)
                .load(R.drawable.help3)
                .resize(width, height)
                .centerCrop()
                .into(imageView4);
        textView5 = (TextView) view.findViewById(R.id.help_credit_content_5);
        textView5.setTypeface(CreditUtil.getInstance().typefaceLatoLight);

        imageView5 = (ImageView) view.findViewById(R.id.help_credit_image_5);
        layoutParams = imageView5.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        Picasso.with(mContext)
                .load(R.drawable.help4)
                .resize(width, height)
                .centerCrop()
                .into(imageView5);
        textView6 = (TextView) view.findViewById(R.id.help_credit_content_6);
        textView6.setTypeface(CreditUtil.getInstance().typefaceLatoLight);

        imageView6 = (ImageView) view.findViewById(R.id.help_credit_image_6);
        layoutParams = imageView6.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        Picasso.with(mContext)
                .load(R.drawable.help5)
                .resize(width, height)
                .centerCrop()
                .into(imageView6);
        textView7 = (TextView) view.findViewById(R.id.help_credit_content_7);
        textView7.setTypeface(CreditUtil.getInstance().typefaceLatoLight);

        imageView7 = (ImageView) view.findViewById(R.id.help_credit_image_7);
        layoutParams = imageView7.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        Picasso.with(mContext)
                .load(R.drawable.help6)
                .resize(width, height)
                .centerCrop()
                .into(imageView7);
        textView8 = (TextView) view.findViewById(R.id.help_credit_content_8);
        textView8.setTypeface(CreditUtil.getInstance().typefaceLatoLight);

        imageView8 = (ImageView) view.findViewById(R.id.help_credit_image_8);
        layoutParams = imageView8.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        Picasso.with(mContext)
                .load(R.drawable.help7)
                .resize(width, height)
                .centerCrop()
                .into(imageView8);
        textView9 = (TextView) view.findViewById(R.id.help_credit_content_9);
        textView9.setTypeface(CreditUtil.getInstance().typefaceLatoLight);

        imageView9 = (ImageView) view.findViewById(R.id.help_credit_image_9);
        layoutParams = imageView9.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        Picasso.with(mContext)
                .load(R.drawable.help8)
                .resize(width, height)
                .centerCrop()
                .into(imageView9);
        textView10 = (TextView) view.findViewById(R.id.help_credit_content_10);
        textView10.setTypeface(CreditUtil.getInstance().typefaceLatoLight);

        foot = (TextView) view.findViewById(R.id.foot);
        foot.setTypeface(CreditUtil.getInstance().typefaceLatoLight);
    }

}
