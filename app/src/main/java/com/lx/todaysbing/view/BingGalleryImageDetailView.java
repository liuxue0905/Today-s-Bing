package com.lx.todaysbing.view;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.lx.todaysbing.activity.ResolutionActivity;
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
    private String mImageResolution;

    public BingGalleryImageDetailView(Context context) {
        super(context);
    }

    public BingGalleryImageDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BingGalleryImageDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("NewApi")
    public BingGalleryImageDetailView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void bind(String color, Image image, String resolution, String imageResolution) {
        Log.d(TAG, "bind() image:" + image);
        Log.d(TAG, "bind() resolution:" + resolution);
        Log.d(TAG, "bind() imageResolution:" + imageResolution);

        mColor = color;
        mImage = image;
        mResolution = resolution;

        if (imageResolution == null) {
            if (Image.RESOLUTION_CEDE_L.equalsIgnoreCase(image.getMaxpix())) {
                imageResolution = Image.RESOLUTION_VALUE_L;
            } else if (Image.RESOLUTION_CEDE_W.equalsIgnoreCase(image.getMaxpix())) {
                imageResolution = Image.RESOLUTION_VALUE_W;
            }
        }
        mImageResolution = imageResolution;

        setColor();
        btnResolution.setText(mImageResolution);

        String url = image.getMaxpixUrl();
        if (Image.RESOLUTION_VALUE_L.equalsIgnoreCase(imageResolution)) {
            url = image.getImageUrl(Image.RESOLUTION_CEDE_L);
        } else if (Image.RESOLUTION_VALUE_W.equalsIgnoreCase(imageResolution)) {
            url = image.getImageUrl(Image.RESOLUTION_CEDE_W);
        }
        Log.d(TAG, "bind() url:" + url);

        progressBar.setVisibility(View.VISIBLE);
        Glide.with(getContext())
                .load(url)
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
//        Serializable obj = (Serializable) TodaysBingApplication.getInstance().getBingGalleryImageDao().loadAll();
//        File cacheDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//        ACache.get(cacheDir).put("Cache" + System.currentTimeMillis(), obj);

        String[] resolutions = new String[]{Image.RESOLUTION_VALUE_L, Image.RESOLUTION_VALUE_W};
        if (Image.RESOLUTION_VALUE_L.equalsIgnoreCase(mImage.getMaxpix())) {
            resolutions = new String[]{Image.RESOLUTION_VALUE_L};
        }
        ResolutionActivity.action((Activity) getContext(), ResolutionActivity.REQUEST_CODE, resolutions, mResolution);
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