package com.lx.todaysbing.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lx.todaysbing.R;
import com.lx.todaysbing.umeng.MobclickAgentHelper;
import com.umeng.analytics.MobclickAgent;

import binggallery.chinacloudsites.cn.Image;
import butterknife.OnClick;

/**
 * Created by liuxue on 2015/5/9.
 */
public class BingGalleryImageDetailView extends BingImageDetailView {

    private static final String TAG = "BGImageDetailView";

    private Image mImage;

    public BingGalleryImageDetailView(Context context) {
        super(context);
    }

    public BingGalleryImageDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BingGalleryImageDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BingGalleryImageDetailView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void bind(String color, Image image, String resolution) {
        Log.d(TAG, "bind() image:" + image);
        Log.d(TAG, "bind() resolution:" + resolution);

        mColor = color;
        mImage = image;
        mResolution = resolution;

        setColor();
        String resolutionStr = null;
        if (Image.RESOLUTION_CEDE_L.equalsIgnoreCase(image.getMaxpix())) {
            resolutionStr = Image.RESOLUTION_VALUE_L;
        } else if (Image.RESOLUTION_CEDE_W.equalsIgnoreCase(image.getMaxpix())) {
            resolutionStr = Image.RESOLUTION_VALUE_W;
        }
        btnResolution.setText(resolutionStr);

        progressBar.setVisibility(View.VISIBLE);
        Glide.with(getContext())
                .load(image.getMaxpixUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.no_image)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);
    }

    @OnClick(R.id.btnResolution)
    void onClickResolution() {

    }

    @OnClick(R.id.btnSave)
    void onClickSave() {
        Image image = mImage;
        String resolution = mResolution;
        String url = image.getMaxpixUrl();
        save(url, image.getCopyright(), image.getCopyright());

    }

    @OnClick(R.id.btnLocation)
    void onClickLocation() {
        MobclickAgent.onEvent(getContext(), MobclickAgentHelper.BingImageDetail.EVENT_ID_BINGIMAGENDAY_LOCATION);
    }

}