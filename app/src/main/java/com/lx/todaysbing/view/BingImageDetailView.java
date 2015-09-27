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
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
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
import com.lx.todaysbing.model.ImageDetail;
import com.lx.todaysbing.umeng.MobclickAgentHelper;
import com.lx.todaysbing.util.Utils;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liuxue on 2015/5/9.
 */
public class BingImageDetailView extends RelativeLayout implements Toolbar.OnMenuItemClickListener {

    private static final String TAG = "BingImageDetailView";

    @Bind(R.id.toolbarTop)
    Toolbar mToolbarTop;
    @Bind(R.id.toolbarBottom)
    Toolbar mToolbarBottom;

    @Bind(R.id.layoutHud)
    View layoutHud;
    @Bind(R.id.fakeStatusBar)
    View fakeStatusBar;
    @Bind(R.id.iv)
    ImageView imageView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.btnResolution)
    Button btnResolution;
    @Bind(R.id.tvEnabledRotation)
    TextView tvEnabledRotation;

//    CopyInfoHolder mCopyInfoHolder;

//    private Fragment mFragment;
    private String mColor;
    private String mResolution;
    private ImageDetail mImageDetail;


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
        ButterKnife.bind(this);

        Utils.setupFakeStatusBarHeightOnGlobalLayout((Activity) getContext(), fakeStatusBar);

        setupHudLayoutParams();

//        mCopyInfoHolder = new CopyInfoHolder(findViewById(R.id.image_detail_copy_info));
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

    public void bind(String color, String resolution, ImageDetail imageDetail) {
        Log.d(TAG, "bind() imageDetail:" + imageDetail);
        Log.d(TAG, "bind() resolution:" + resolution);

//        mFragment = fragment;
        mColor = color;
        mResolution = resolution;
        mImageDetail = imageDetail;

        updateViews();
    }

    private void updateViews() {
        setColor();
        btnResolution.setText(mResolution);

        progressBar.setVisibility(View.VISIBLE);
        Glide.with(getContext())
                .load(mImageDetail.getImageUrl(mResolution))
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

//        mToolbarTop.setNavigationIcon();
        mToolbarBottom.getMenu().clear();
        mToolbarBottom.inflateMenu(R.menu.menu_detail_bottom);
        mToolbarBottom.setOnMenuItemClickListener(this);
    }

    @OnClick(R.id.btnResolution)
    void onClickResolution() {
        Log.d(TAG, "onClickResolution()");
        ResolutionActivity.action((Activity) getContext(), ResolutionActivity.REQUEST_CODE, mImageDetail.resolutions, mResolution);

        MobclickAgent.onEvent(getContext(), MobclickAgentHelper.BingImageDetail.EVENT_ID_BINGIMAGENDAY_RESOLUTION);
    }

    void onClickHpcDown() {
        save(mImageDetail.getImageUrl(mResolution), mImageDetail.title, mImageDetail.description);
    }

    void onClickLocation() {
        MobclickAgent.onEvent(getContext(), MobclickAgentHelper.BingImageDetail.EVENT_ID_BINGIMAGENDAY_LOCATION);
    }

    private void onClickHpcCopyInfo() {
        Snackbar snackbar = Snackbar.make(this, mImageDetail.copyRight, Snackbar.LENGTH_LONG)
                .setAction(android.R.string.ok, null);
//        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
//        snackbarLayout.getMessageView().setMaxLines(Integer.MAX_VALUE);
        snackbar.show();
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


    public void onResolutionChanged(String resolution) {
        mResolution = resolution;
        updateViews();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_hpc_down) {
            onClickHpcDown();
            return true;
        } else if (itemId == R.id.action_hpc_copy_info) {
            onClickHpcCopyInfo();
            return true;
        }

        return false;
    }

//    class CopyInfoHolder {
//
//        private final View view;
//
//        @Bind(R.id.tv_copyright_left)
//        TextView mCopyRightLeftTV;
//        @Bind(R.id.tv_copyright_right)
//        TextView mCopyRightRightTV;
//
//        public CopyInfoHolder(View view) {
//            this.view = view;
//            ButterKnife.bind(this, view);
//        }
//
//        public void bind() {
//
//        }
//    }
}
