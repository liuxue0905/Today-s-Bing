package com.lx.todaysbing.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lx.todaysbing.R;
import com.lx.todaysbing.activity.ResolutionActivity;
import com.lx.todaysbing.umeng.MobclickAgentHelper;
import com.lx.todaysbing.util.Utils;
import com.umeng.analytics.MobclickAgent;

import bing.com.Image;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by liuxue on 2015/5/9.
 */
public class BingImageDetailView extends RelativeLayout {

    private static final String TAG = "BingImageDetailView";


    @InjectView(R.id.layoutHud)
    View layoutHud;
    @InjectView(R.id.fakeStatusBar)
    View fakeStatusBar;
    @InjectView(R.id.iv)
    ImageView imageView;
    @InjectView(R.id.progressBar)
    ProgressBar progressBar;
//    @InjectView(R.id.tvResolution)
//    TextView tvResolution;
    @InjectView(R.id.btnResolution)
    Button btnResolution;
    @InjectView(R.id.tvEnabledRotation)
    TextView tvEnabledRotation;
//    @InjectView(R.id.btnSave)
//    Button btnSave;
    @InjectView(R.id.btnLocation)
    Button btnLocation;

    private Image mImage;
    protected String mResolution;
    protected String mImageResolution;
    protected String mColor;

    public BingImageDetailView(Context context) {
        super(context);
        init(context);
    }

    public BingImageDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BingImageDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @SuppressLint("NewApi")
    public BingImageDetailView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void init(Context context) {
        inflate(context, R.layout.view_bing_image_detail, this);
        ButterKnife.inject(this);

        Utils.setupFakeStatusBarHeightOnGlobalLayout((Activity) getContext(), fakeStatusBar);

        setupHudLayoutParams();
    }

    protected void setColor() {
        btnResolution.setTextColor(Color.parseColor(mColor));

        Drawable d = DrawableCompat.wrap(progressBar.getIndeterminateDrawable());
        DrawableCompat.setTint(d, Color.parseColor(mColor));
        progressBar.setProgressDrawable(d);
    }

    private void setupHudLayoutParams() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        RelativeLayout.LayoutParams params = (LayoutParams) layoutHud.getLayoutParams();
        params.width = Math.min(dm.widthPixels, dm.heightPixels);
        layoutHud.setLayoutParams(params);
    }

    public void bind(String color, Image image, String resolution, String imageResolution) {
        Log.d(TAG, "bind() image:" + image);
        Log.d(TAG, "bind() resolution:" + resolution);
        Log.d(TAG, "bind() imageResolution:" + imageResolution);

        tvEnabledRotation.setAlpha(1.0f);

        mColor = color;
        mImage = image;
        mResolution = resolution;
        if (imageResolution == null) {
            imageResolution = resolution;
        }
        mImageResolution = imageResolution;

        setColor();
        btnResolution.setText(mImageResolution);

        progressBar.setVisibility(View.VISIBLE);
        Glide.with(getContext())
                .load(Image.rebuildImageUrl(image, imageResolution))
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
        MobclickAgent.onEvent(getContext(), MobclickAgentHelper.BingImageDetail.EVENT_ID_BINGIMAGENDAY_RESOLUTION);

        String[] resolutions = getContext().getResources().getStringArray(R.array.resolution);
        ResolutionActivity.action((Activity) getContext(), ResolutionActivity.REQUEST_CODE, resolutions, mResolution);
    }

    @OnClick(R.id.btnSave)
    void onClickSave() {
        Image image = mImage;
        String imageResolution = mImageResolution;

        String url = Image.rebuildImageUrl(image, imageResolution);
        save(url, image.copyright, image.copyright);
    }

    @OnClick(R.id.btnLocation)
    void onClickLocation() {
        MobclickAgent.onEvent(getContext(), MobclickAgentHelper.BingImageDetail.EVENT_ID_BINGIMAGENDAY_LOCATION);
    }

    protected void save(String url, String title, String description) {
        MobclickAgent.onEvent(getContext(), MobclickAgentHelper.BingImageDetail.EVENT_ID_BINGIMAGENDAY_SAVE);

        String subPath = Utils.getSubPath(url);

        DownloadManager manager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        boolean has = Utils.hasExternalStoragePublicPicture(subPath);
        if (has) {
//            Toast.makeText(getContext(), R.string.image_detail_downloaded, Toast.LENGTH_LONG).show();
            Snackbar.make(this, R.string.image_detail_downloaded, Snackbar.LENGTH_LONG)
                    .setAction(android.R.string.ok, null)
                    .show();
            return;
        }

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, subPath);
        request.setTitle(title);
        request.setDescription(description);
        request.setMimeType(MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url)));
        request.allowScanningByMediaScanner();
        request.setVisibleInDownloadsUi(true);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        manager.enqueue(request);

//        Toast.makeText(getContext(), getContext().getString(R.string.image_detail_download_start), Toast.LENGTH_LONG).show();
        Snackbar.make(this, R.string.image_detail_download_start, Snackbar.LENGTH_LONG)
                .setAction(android.R.string.ok, null)
                .show();

//        NotificationManager mNotificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
//
//        android.support.v4.app.NotificationCompat v4NotificationCompat;
//        android.support.v7.app.NotificationCompat v7NotificationCompat;
//
//        Notification notification = new NotificationCompat.Builder(getContext())
//                .setAutoCancel(false)
//                .setContentTitle(null)
//                .setContentText(null)
//                .setContentInfo(null)
//                .build();
//
//        mNotificationManager.notify(null, 0, notification);
    }
}
