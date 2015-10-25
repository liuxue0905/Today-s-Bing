package com.lx.todaysbing.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lx.todaysbing.R;

import bing.com.Image;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liuxue on 2015/5/9.
 */
public class BingImageNDayItemView extends RelativeLayout {

    @Bind(R.id.cardview)
    CardView mCardView;

    @Bind(R.id.iv)
    public ImageView imageView;
    @Bind(R.id.tv_copyright_left)
    public TextView tvCopyRightLeft;
    @Bind(R.id.tv_copyright_right)
    public TextView tvCopyRightRight;
    @Bind(R.id.tvNDaysAgo)
    public TextView tvNDaysAgo;

    @Bind(R.id.selector)
    View mSelectorView;

    private Image mImage;
    private String mResolution;
    private String mColor;

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
        ButterKnife.bind(this);
    }

    public void bind(int position, String color, Image image, String resolution) {
        mImage = image;
        mResolution = resolution;
        mColor = color;

        setColor();

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

        Drawable drawable = DrawableCompat.wrap(mSelectorView.getBackground());
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
        DrawableCompat.setTint(drawable, Color.parseColor("#00ffff"));
        mSelectorView.setBackgroundDrawable(drawable);
    }

    private void setColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            mCardView.setForegroundTintList(DrawableCompatUtils.getColorStateList(mColor));
//            mCardView.setForegroundTintMode(PorterDuff.Mode.DST_IN);
        }
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

    @Override
    public void setOnClickListener(OnClickListener l) {
//        super.setOnClickListener(l);
        mCardView.setOnClickListener(l);
    }
}
