package com.lx.todaysbing.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lx.todaysbing.R;

import binggallery.chinacloudsites.cn.Image;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by liuxue on 2015/6/21.
 */
public class BingGalleryItemView extends RelativeLayout {

    private static final String TAG = "BingGalleryItemView";
    @InjectView(R.id.root)
    public View rootView;
    @InjectView(R.id.iv)
    public ImageView imageView;
    @InjectView(R.id.tv_copyright_left)
    TextView tvCopyRightLeft;
    @InjectView(R.id.tv_copyright_right)
    public TextView tvCopyRightRight;

    public BingGalleryItemView(Context context) {
        super(context);
        init(context);
    }

    public BingGalleryItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BingGalleryItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @SuppressLint("NewApi")
    public BingGalleryItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.item_bing_gallery, this);
        ButterKnife.inject(this);
    }

    public void bind(int position, Image image) {
        Log.d(TAG, "bind() position:" + position);
        Log.d(TAG, "bind() image:" + image);
        Log.d(TAG, "bind() image.getMinpixUrl():" + image.getMinpixUrl());

        String[] copyrightParts = image.getSplitCopyright();
        if (copyrightParts != null) {
            tvCopyRightLeft.setText(copyrightParts[0]);
            tvCopyRightRight.setText(copyrightParts[1]);
        } else {
            tvCopyRightLeft.setText(null);
            tvCopyRightRight.setText(null);
        }

        Glide.with(getContext())
                .load(image.getMinpixUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(imageView);
    }
}