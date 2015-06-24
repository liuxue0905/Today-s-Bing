package com.lx.todaysbing.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lx.todaysbing.R;

import bing.com.Image;

import com.lx.todaysbing.util.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by liuxue on 2015/5/9.
 */
public class BingImageNDayItemView extends RelativeLayout {

    @InjectView(R.id.root)
    public View rootView;

    @InjectView(R.id.iv)
    public ImageView imageView;
    @InjectView(R.id.tv_copyright_left)
    public TextView tvCopyRightLeft;
    @InjectView(R.id.tv_copyright_right)
    public TextView tvCopyRightRight;
    @InjectView(R.id.tvNDaysAgo)
    public TextView tvNDaysAgo;

    private Image mImage;
    private String mResolution;

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

    @SuppressLint("NewApi")
    public BingImageNDayItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void init(Context context) {
        inflate(context, R.layout.item_bing_image_nday, this);
        ButterKnife.inject(this);

    }

    public void bind(int position, Image image, String resolution) {
        mImage = image;
        mResolution = resolution;

        String[] copyrightParts = image.getSplitCopyright();
        tvCopyRightLeft.setText(copyrightParts[0]);
        tvCopyRightRight.setText(copyrightParts[1]);

        setupTvNDaysAgo(position);

        Glide.with(getContext())
                .load(Image.rebuildImageUrl(image, resolution))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(imageView);
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
