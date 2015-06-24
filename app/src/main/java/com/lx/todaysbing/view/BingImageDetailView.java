package com.lx.todaysbing.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lx.todaysbing.R;
import com.lx.todaysbing.activity.ResolutionActivity;
import bing.com.Image;
import com.lx.todaysbing.umeng.MobclickAgentHelper;
import com.lx.todaysbing.util.Utils;
import com.umeng.analytics.MobclickAgent;

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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
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

    public void bind(String color, Image image, String resolution) {
        Log.d(TAG, "bind() image:" + image);
        Log.d(TAG, "bind() resolution:" + resolution);

        tvEnabledRotation.setAlpha(1.0f);

        mColor = color;
        mImage = image;
        mResolution = resolution;

        setColor();
        btnResolution.setText(resolution);

        Glide.with(getContext())
                .load(Image.rebuildImageUrl(image, resolution))
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

        ResolutionActivity.action((Activity) getContext(), ResolutionActivity.REQUEST_CODE, mResolution);
    }

    @OnClick(R.id.btnSave)
    void onClickSave() {
        Image image = mImage;
        String resolution = mResolution;
        String url = Image.rebuildImageUrl(image, resolution);
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
            Toast.makeText(getContext(), R.string.image_detail_downloaded, Toast.LENGTH_LONG).show();
            return;
        }

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, subPath);
        request.setTitle(title);
        request.setDescription(description);
        request.allowScanningByMediaScanner();
        request.setVisibleInDownloadsUi(true);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        manager.enqueue(request);

        Toast.makeText(getContext(), getContext().getString(R.string.image_detail_download_start), Toast.LENGTH_LONG).show();
    }
}
