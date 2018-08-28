package com.lx.todaysbing.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.lx.todaysbing.R;
import com.lx.todaysbing.activity.ResolutionActivity;
import com.lx.todaysbing.model.ImageDetail;
import com.lx.todaysbing.umeng.MobclickAgentHelper;
import com.lx.todaysbing.util.Utils;
import com.lx.todaysbing.widget.DownloadManagerResolver;
import com.umeng.analytics.MobclickAgent;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by liuxue on 2015/5/9.
 */
public class BingImageDetailView extends RelativeLayout implements Toolbar.OnMenuItemClickListener, PhotoViewAttacher.OnViewTapListener {

    private static final String TAG = BingImageDetailView.class.getCanonicalName();

    private View mLayoutToobarTop;
    private View mLayoutToobarBottom;
    private Toolbar mToolbarTop;
    private Toolbar mToolbarBottom;

    private View fakeStatusBar;
    private PhotoView imageView;
    private ProgressBar progressBar;
    private Button btnResolution;
    private TextView tvEnabledRotation;
    private BingImageDetailCopyInfoView mBingImageDetailCopyInfoView;
    private ImageView mImageErrorView;

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

        mLayoutToobarTop = findViewById(R.id.layout_toobar_top);
        mLayoutToobarBottom = findViewById(R.id.layout_toolbar_bottom);
        mToolbarTop = findViewById(R.id.toolbarTop);
        mToolbarBottom = findViewById(R.id.toolbarBottom);

        fakeStatusBar = findViewById(R.id.fakeStatusBar);
        imageView = findViewById(R.id.iv);
        progressBar = findViewById(R.id.progressBar);
        btnResolution = findViewById(R.id.btnResolution);
        tvEnabledRotation = findViewById(R.id.tvEnabledRotation);
        mBingImageDetailCopyInfoView = findViewById(R.id.image_detail_copy_info);
        mImageErrorView = findViewById(R.id.image_error);

        btnResolution.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClickResolution()");
                ResolutionActivity.action((Activity) getContext(), ResolutionActivity.REQUEST_CODE, mImageDetail.resolutions, mResolution);

                MobclickAgent.onEvent(getContext(), MobclickAgentHelper.BingImageDetail.EVENT_ID_BINGIMAGENDAY_RESOLUTION);
            }
        });
        mBingImageDetailCopyInfoView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(mImageDetail.getShareUrl(mResolution)));
                getContext().startActivity(intent);
            }
        });
        mImageErrorView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                updateViews();
            }
        });

        Utils.setupFakeStatusBarHeightOnGlobalLayout((Activity) getContext(), fakeStatusBar);

        setupLayoutToobarTopParams();

        imageView.setOnViewTapListener(this);
    }

    protected void setColor() {
        btnResolution.setTextColor(Color.parseColor(mColor));
//        DrawableCompatUtils.setBackground(btnResolution, DrawableCompatUtils.selectableItemBackground(getContext(), mColor));

        Drawable d = DrawableCompat.wrap(progressBar.getIndeterminateDrawable());
        DrawableCompat.setTint(d, Color.parseColor(mColor));
        progressBar.setProgressDrawable(d);
    }

    private void setupLayoutToobarTopParams() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        RelativeLayout.LayoutParams params = (LayoutParams) mLayoutToobarTop.getLayoutParams();
        params.width = Math.min(dm.widthPixels, dm.heightPixels);
        mLayoutToobarTop.setLayoutParams(params);
    }

    public void bind(String color, String resolution, ImageDetail imageDetail) {
        Log.d(TAG, "bind() color:" + color);
        Log.d(TAG, "bind() resolution:" + resolution);
        Log.d(TAG, "bind() imageDetail:" + imageDetail);

        mColor = color;
        mResolution = resolution;
        mImageDetail = imageDetail;

        updateViews();
    }

    private void updateViews() {
        setColor();
        btnResolution.setText(mResolution);

        progressBar.setVisibility(View.VISIBLE);
        mImageErrorView.setVisibility(View.GONE);
        imageView.setScale(1.0F, false);
        Glide.with(getContext())
                .load(mImageDetail.getImageUrl(mResolution))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        mImageErrorView.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        mImageErrorView.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);

        mToolbarBottom.setNavigationIcon(null);
        mToolbarBottom.getMenu().clear();
        mToolbarBottom.inflateMenu(R.menu.menu_detail_bottom);
        mToolbarBottom.setOnMenuItemClickListener(this);
        mToolbarTop.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) getContext()).finish();
            }
        });

        mShareGroupVisible = false;

        mBingImageDetailCopyInfoView.bind(mColor, mImageDetail);
    }

    void onClickHpcDown() {
        save(mImageDetail.getImageUrl(mResolution), mImageDetail.title, mImageDetail.description);
    }

    void onClickLocation() {
        MobclickAgent.onEvent(getContext(), MobclickAgentHelper.BingImageDetail.EVENT_ID_BINGIMAGENDAY_LOCATION);
    }

    private void onClickHpcCopyInfo() {
//        Snackbar snackbar = Snackbar.make(this, mImageDetail.copyRight, Snackbar.LENGTH_LONG)
//                .setAction(android.R.string.ok, null);
////        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
////        snackbarLayout.getMessageView().setMaxLines(Integer.MAX_VALUE);
//        snackbar.show();

        mBingImageDetailCopyInfoView.setVisibility(mBingImageDetailCopyInfoView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

    protected void save(String url, String title, String description) {
        MobclickAgent.onEvent(getContext(), MobclickAgentHelper.BingImageDetail.EVENT_ID_BINGIMAGENDAY_SAVE);

        String subPath = Utils.getSubPath(url);

        boolean has = Utils.hasExternalStoragePublicPicture(subPath);
        if (has) {
//            Toast.makeText(getContext(), R.string.image_detail_downloaded, Toast.LENGTH_LONG).show();
            Snackbar.make(this, R.string.image_detail_downloaded, Snackbar.LENGTH_LONG)
                    .setAction(android.R.string.ok, null)
                    .show();
            return;
        }

        if (DownloadManagerResolver.resolve(getContext(), url)) {
            saveByDownloadManager(url, subPath, title, description);
        }
    }

    private void saveByDownloadManager(String url, String subPath, String title, String description) {
        try {
            DownloadManager manager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url))
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, subPath)
                    .setTitle(title)
                    .setDescription(description)
                    .setMimeType(MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url)))
                    .setVisibleInDownloadsUi(true)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.allowScanningByMediaScanner();
            manager.enqueue(request);

            Snackbar.make(this, R.string.image_detail_download_start, Snackbar.LENGTH_LONG)
                    .setAction(android.R.string.ok, null)
                    .show();
        } catch (Exception e) {
            e.printStackTrace();

            Snackbar.make(this, R.string.image_detail_download_failures, Snackbar.LENGTH_LONG)
                    .setAction(android.R.string.ok, null)
                    .show();
        }
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
        } else if (itemId == R.id.action_hpc_share) {
            onClickShare();
            return true;
        } else if (itemId == R.id.action_hpc_wechat) {
            onClickHpcWechat();
            return true;
        } else if (itemId == R.id.action_hpc_qzone) {
            onClickHpcQzone();
            return true;
        }

        return false;
    }

    private void onClickHpcQzone() {
        Log.d(TAG, "onClickHpcQzone()");
        Log.d(TAG, "onClickHpcQzone() mImageDetail.getShareUrl()：" + mImageDetail.getShareUrl(mResolution));

//        share(QZone.NAME);
        Glide.with(getContext())
                .load(mImageDetail.getImageUrl(mResolution))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        Log.d(TAG, "onResourceReady() resource:" + resource);
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);

                        Log.d(TAG, "onLoadFailed()");
                        Toast.makeText(getContext(), R.string.share_error_image_path_fail, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void onClickHpcWechat() {
        Log.d(TAG, "onClickHpcWechat()");
        Log.d(TAG, "onClickHpcWechat() mImageDetail.getShareUrl()：" + mImageDetail.getShareUrl(mResolution));

//        share(WechatMoments.NAME);
        Glide.with(getContext())
                .load(mImageDetail.getImageUrl(mResolution))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        Log.d(TAG, "onResourceReady() resource:" + resource);
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);

                        Log.d(TAG, "onLoadFailed()");
                        Toast.makeText(getContext(), R.string.share_error_image_path_fail, Toast.LENGTH_SHORT).show();
                    }
                });

//        Glide.with(getContext())
//                .load(mImageDetail.getImageUrl(mResolution))
//                .asBitmap()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(new SimpleTarget<Bitmap>(150, 150) {});
    }

    private boolean mShareGroupVisible = false;

    private void onClickShare() {
        Log.d(TAG, "onClickShare()");

        mShareGroupVisible = !mShareGroupVisible;
        mToolbarBottom.getMenu().setGroupVisible(R.id.group_share, mShareGroupVisible);
    }

//    private void onOneKeyShare() {
//        final OnekeyShare oks = new OnekeyShare();
//        oks.setTitle(mImageDetail.copyRight);
//        oks.setTitleUrl(mImageDetail.getShareUrl(mResolution));
//        oks.setText(mImageDetail.copyRight);
//        oks.setImageUrl(mImageDetail.getImageUrl(mResolution));
//        oks.setUrl(mImageDetail.getShareUrl(mResolution));
//        oks.setSite("今日必应壁纸");
//        oks.setSiteUrl("http://shouji.baidu.com/software/item?docid=7826820");
//        oks.setSilent(false);
//        oks.setShareFromQQAuthSupport(/*shareFromQQLogin*/true);
//
////        oks.setTheme(OnekeyShareTheme.SKYBLUE);
//        oks.setTheme(OnekeyShareTheme.CLASSIC);
//
//        // 令编辑页面显示为Dialog模式
//        oks.setDialogMode();
//
//        // 在自动授权时可以禁用SSO方式
////        oks.disableSSOWhenAuthorize();
//
//        // 去除注释，则快捷分享的操作结果将通过OneKeyShareCallback回调
//        //oks.setCallback(new OneKeyShareCallback());
//
//        // 为EditPage设置一个背景的View
////        oks.setEditPageBackground(getPage());
//        oks.show(getContext());
//    }

    @Override
    public void onViewTap(View view, float x, float y) {
        mLayoutToobarTop.setVisibility(mLayoutToobarTop.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        mLayoutToobarBottom.setVisibility(mLayoutToobarBottom.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

//    private void share(String platform, String imagePath) {
//        Platform.ShareParams sp = new Platform.ShareParams();
//        sp.setTitle(mImageDetail.copyRight);
//        sp.setTitleUrl(mImageDetail.getShareUrl(mResolution));
//        sp.setText(mImageDetail.copyRight);
//        sp.setImageUrl(mImageDetail.getShareImageUrl());
//        sp.setImagePath(imagePath);
//        sp.setUrl(mImageDetail.getShareUrl(mResolution));
//        sp.setShareType(Platform.SHARE_WEBPAGE);
//
//        Platform plat = ShareSDK.getPlatform(platform);
//        plat.setPlatformActionListener(/*this*/mPlatformActionListener);
//        plat.share(sp);
//    }

//    PlatformActionListener mPlatformActionListener = new PlatformActionListener() {
//        @Override
//        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//            Log.d(TAG, "onComplete()");
//            Toast.makeText(platform.getContext(), R.string.share_success, Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onError(Platform platform, int i, Throwable throwable) {
//            Log.d(TAG, "onError()");
//            if (!platform.isClientValid()) {
//                Toast.makeText(platform.getContext(), R.string.share_error_client_invalid, Toast.LENGTH_SHORT).show();
//                return;
//            }
//            Toast.makeText(platform.getContext(), R.string.share_error_fail, Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onCancel(Platform platform, int i) {
//            Log.d(TAG, "onCancel()");
//            Toast.makeText(platform.getContext(), R.string.share_error_cancel, Toast.LENGTH_SHORT).show();
//        }
//    };
}
