package com.lx.todaysbing.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lx.todaysbing.R;

import binggallery.chinacloudsites.cn.Image;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liuxue on 2015/6/21.
 */
public class BingGalleryItemView extends RelativeLayout {

    private static final String TAG = BingGalleryItemView.class.getCanonicalName();

    @Bind(R.id.cardview)
    CardView mCardView;
//    @Bind(R.id.root)
//    public View rootView;
    @Bind(R.id.iv)
    public ImageView imageView;
    @Bind(R.id.tv_copyright_left)
    TextView tvCopyRightLeft;
    @Bind(R.id.tv_copyright_right)
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
        ButterKnife.bind(this);
    }

    public void bind(int position, String color, Image image) {
        Log.d(TAG, "bind() position:" + position);
        Log.d(TAG, "bind() color:" + color);
        Log.d(TAG, "bind() image:" + image);
        Log.d(TAG, "bind() image.getMinpixUrl():" + image.getMinpixUrl());

        setupColor();

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
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        return false;
                    }
                })
                .into(imageView);
    }

    private void setupColor() {
//        RippleDrawable rd;
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//        } else {
//        }
//        mCardView.getForegroundTintList();
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
//        super.setOnClickListener(l);
        mCardView.setOnClickListener(l);
    }
}
