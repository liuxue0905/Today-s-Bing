package com.lx.todaysbing.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lx.todaysbing.R;
import com.lx.todaysbing.activity.BingImageDetailActivity;
import com.lx.todaysbing.activity.MarketActivity;
import com.lx.todaysbing.event.OnHPImageArchiveEvent;
import com.lx.todaysbing.event.OnHPImageArchiveFailureEvent;
import com.lx.todaysbing.event.OnHPImageArchivePreLoadEvent;
import com.lx.todaysbing.event.OnHPImageArchiveSuccessEvent;
import com.lx.todaysbing.model.BingImageDetail;
import com.lx.todaysbing.model.ImageDetail;
import com.lx.todaysbing.umeng.MobclickAgentHelper;
import com.lx.todaysbing.util.ResolutionUtils;
import com.lx.todaysbing.util.Utils;
import com.umeng.analytics.MobclickAgent;

import java.util.Arrays;

import bing.com.HPImageArchive;
import bing.com.Image;
import de.greenrobot.event.EventBus;

/**
 * Created by liuxue on 2015/5/8.
 */
public class BingImageTodayView extends RelativeLayout {

    private static final String TAG = "BingImageTodayView";

    private String mMkt;
    private Image mImage;
    private String mResolution;
    private String mColor;

    public TextView tvCopyRightLeft;
    public TextView tvCopyRightRight;
    private ImageView imageView;
    private ImageView imageError;
    private View layoutCopyright;
//    @Bind(R.id.iv_mkt)
//    ImageView ivMkt;
    private TextView tvMkt;
    private ProgressBar progressBar;
    private ImageButton btnRefresh;

    public BingImageTodayView(Context context) {
        super(context);
        init(context);
    }

    public BingImageTodayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BingImageTodayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BingImageTodayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void init(Context context) {
        inflate(context, R.layout.view_bing_image_today, this);

        tvCopyRightLeft = findViewById(R.id.tv_copyright_left);
        tvCopyRightRight = findViewById(R.id.tv_copyright_right);
        imageView = findViewById(R.id.iv);
        imageError = findViewById(R.id.image_error);
        layoutCopyright = findViewById(R.id.layout_copyright);
        tvMkt = findViewById(R.id.tv_mkt);
        progressBar = findViewById(R.id.progressBar);
        btnRefresh = findViewById(R.id.btnRefresh);

        layoutCopyright.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mImage == null)
                    return;


                String[] resolutions = getContext().getResources().getStringArray(R.array.resolution);
                ImageDetail imageDetail = new BingImageDetail(mImage, resolutions, mMkt);
                BingImageDetailActivity.action(getContext(), mColor, imageDetail, mResolution);

                MobclickAgent.onEvent(getContext(), MobclickAgentHelper.BingImageToday.EVENT_ID_BINGIMAGETODAY_DETAIL);
            }
        });
        tvMkt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                MarketActivity.action((Activity)getContext(), MarketActivity.REQUEST_CODE, mMkt);

                MobclickAgent.onEvent(getContext(), MobclickAgentHelper.BingImageToday.EVENT_ID_BINGIMAGETODAY_MKT);
            }
        });
        btnRefresh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new OnHPImageArchiveEvent());
            }
        });
        imageError.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                bind(mColor, mMkt, mImage, mResolution);
            }
        });

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(null);
        AppCompatActivity activity = (AppCompatActivity) getContext();
        activity.setSupportActionBar(mToolbar);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {
                if (mResolution == null) {
                    return;
                }

                setupImageViewLayoutParams();

                if (Utils.hasJellyBean()) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
    }

    private void setColor() {
        Log.d(TAG, "setColor()");
//        Palette
//        LayerDrawable layerDrawable = new LayerDrawable();
//        Drawable d = DrawableCompat.wrap(tvMkt.getCompoundDrawables()[0]);
        Drawable d = DrawableCompat.wrap(getResources().getDrawable(R.drawable.appbar_globe_wire_dark));
        DrawableCompat.setTint(d, Color.parseColor(mColor));
        tvMkt.setCompoundDrawablesWithIntrinsicBounds(d, null, null, null);
        tvMkt.setTextColor(Color.parseColor(mColor));

//        DrawableCompatUtils.setBackground(tvMkt, DrawableCompatUtils.selectableItemBackground(getContext(), mColor));
//        DrawableCompatUtils.setBackground(layoutCopyright, DrawableCompatUtils.selectableItemBackground(getContext(), mColor));

        Drawable dd = DrawableCompat.wrap(progressBar.getIndeterminateDrawable());
        DrawableCompat.setTint(dd, Color.parseColor(mColor));
        progressBar.setProgressDrawable(dd);
    }

    private void setupImageViewLayoutParams() {
        ResolutionUtils.Resolution resolution = new ResolutionUtils.Resolution(mResolution);
        int[] wh = Utils.getScaledDSizeByFixHeight(resolution.width, resolution.height, this.getWidth(), this.getHeight());

        Log.d(TAG, "setupViewPagerLayoutParams() this.getWidth()" + this.getWidth());
        Log.d(TAG, "setupViewPagerLayoutParams() this.getHeight()" + this.getHeight());
        Log.d(TAG, "setupViewPagerLayoutParams() resolution:" + resolution);
        Log.d(TAG, "setupViewPagerLayoutParams() wh:" + Arrays.toString(wh));

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) imageView.getLayoutParams();
        params.width = wh[0];
        imageView.setLayoutParams(params);
    }

    public void bind(String color, String mkt, HPImageArchive hpImageArchive, String resolurtion) {
        Image image = null;
        if (hpImageArchive != null) {
            image = hpImageArchive.images.get(0);
        }
        bind(color, mkt, image, resolurtion);
    }

    private void bind(String color, String mkt, Image image, String resolurtion) {
        Log.d(TAG, "bind() mkt:" + mkt);
        Log.d(TAG, "bind() image:" + image);
        Log.d(TAG, "bind() resolurtion:" + resolurtion);
        mColor = color;
        mMkt = mkt;
        mImage = image;
        mResolution = resolurtion;

//        setupImageViewLayoutParams();
        setColor();

        tvMkt.setText(Utils.getMarket(getContext(), mkt));

        if (image == null) {
            tvCopyRightLeft.setText(null);
            tvCopyRightRight.setText(null);
//            imageView.setImageDrawable(null);
            return;
        }

        layoutCopyright.setVisibility(View.VISIBLE);

        String[] copyrightParts = image.getSplitCopyright();
        tvCopyRightLeft.setText(copyrightParts[0]);
        tvCopyRightRight.setText(copyrightParts[1]);

        imageError.setVisibility(View.GONE);

        Glide.with(getContext())
                .load(Image.rebuildImageUrl(image, resolurtion))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        imageError.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        imageError.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);

        btnRefresh.setVisibility(View.GONE);
    }

    public void onEvent(OnHPImageArchivePreLoadEvent event) {
        btnRefresh.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void onEvent(OnHPImageArchiveSuccessEvent event) {

        bind(mColor, mMkt, event.getHPImageArchive(), mResolution);

        HPImageArchive hpImageArchive = event.getHPImageArchive();
        if (hpImageArchive == null
                || hpImageArchive.images == null
                || hpImageArchive.images.size() == 0) {
            btnRefresh.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        } else {
            btnRefresh.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }
    }

    public void onEvent(OnHPImageArchiveFailureEvent event) {
        btnRefresh.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
}
