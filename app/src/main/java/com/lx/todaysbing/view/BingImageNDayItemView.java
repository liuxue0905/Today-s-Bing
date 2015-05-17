package com.lx.todaysbing.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lx.todaysbing.R;
import com.lx.todaysbing.model.Image;
import com.lx.todaysbing.util.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by liuxue on 2015/5/9.
 */
public class BingImageNDayItemView extends RelativeLayout {

    @InjectView(R.id.iv)
    public ImageView imageView;
    @InjectView(R.id.tv_copyright_left)
    public TextView tvCopyRightLeft;
    @InjectView(R.id.tv_copyright_right)
    public TextView tvCopyRightRight;
    @InjectView(R.id.tvNDaysAgo)
    public TextView tvNDaysAgo;

    public BingImageNDayItemView(Context context) {
        super(context);
        init(context);
    }

    public BingImageNDayItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BingImageNDayItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BingImageNDayItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void init(Context context) {
        inflate(context, R.layout.item_bing_image_nday, this);
        ButterKnife.inject(this);

    }

    public void bind(int position, Image image) {
        String[] copyrightParts = Utils.splitCopyRight(image.copyright);
        tvCopyRightLeft.setText(copyrightParts[0]);
        tvCopyRightRight.setText(copyrightParts[1]);

        setupTvNDaysAgo(position);

        Glide.with(getContext()).load(Utils.rebuildImageUrl(getContext(), image.url)).into(imageView);
    }

    private void setupTvNDaysAgo(int position) {
        Resources resources = getResources();

        String text = null;
        if (position == 0) {
            text = resources.getString(R.string.n_day_ago_1);
        } else {
            text = resources.getString(R.string.n_day_ago_n, position + 1);
        }
        tvNDaysAgo.setText(text);
    }
}
