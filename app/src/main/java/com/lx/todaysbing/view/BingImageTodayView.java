package com.lx.todaysbing.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lx.todaysbing.R;
import com.lx.todaysbing.activity.BingImageDetailActivity;
import com.lx.todaysbing.activity.MarketActivity;
import com.lx.todaysbing.model.HPImageArchive;
import com.lx.todaysbing.model.Image;
import com.lx.todaysbing.util.ResolutionUtils;
import com.lx.todaysbing.util.Utils;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by liuxue on 2015/5/8.
 */
public class BingImageTodayView extends RelativeLayout {

    private static final String TAG = "BingImageTodayView";

    private String mMkt;
    private Image mImage;
    private String mResolution;
    private String mColor;

    @InjectView(R.id.tv_copyright_left)
    public TextView tvCopyRightLeft;
    @InjectView(R.id.tv_copyright_right)
    public TextView tvCopyRightRight;
    @InjectView(R.id.iv)
    ImageView imageView;
    @InjectView(R.id.layout_copyright)
    View layoutCopyright;
    @InjectView(R.id.iv_mkt)
    ImageView ivMkt;
    @InjectView(R.id.tv_mkt)
    TextView tvMkt;

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
        ButterKnife.inject(this);

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {
                setupImageViewLayoutParams();

                if (Utils.hasJellyBean()) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });

        setColor();
    }

    private void setColor() {
        mColor = "#006AC1";

        Drawable d = DrawableCompat.wrap(ivMkt.getDrawable());
        DrawableCompat.setTint(d, Color.parseColor(mColor));
        ivMkt.setImageDrawable(d);

        tvMkt.setTextColor(Color.parseColor(mColor));
    }

    private void setupImageViewLayoutParams() {
        ResolutionUtils.Resolution suggestResolution = Utils.getSuggestResolution(getContext());
        int[] wh = Utils.getScaledDSizeByFixHeight(suggestResolution.width, suggestResolution.height, this.getWidth(), this.getHeight());

        Log.d(TAG, "setupViewPagerLayoutParams() this.getWidth()" + this.getWidth());
        Log.d(TAG, "setupViewPagerLayoutParams() this.getHeight()" + this.getHeight());
        Log.d(TAG, "setupViewPagerLayoutParams() suggestResolution:" + suggestResolution);
        Log.d(TAG, "setupViewPagerLayoutParams() wh:" + Arrays.toString(wh));

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) imageView.getLayoutParams();
        params.width = wh[0];
        imageView.setLayoutParams(params);
    }

    public void bind(String mkt, HPImageArchive hpImageArchive, String resolurtion) {
        if (hpImageArchive != null) {
            bind(mkt, hpImageArchive.images.get(0), resolurtion);
        }
    }

    public void bind(String mkt, Image image, String resolurtion) {
        Log.d(TAG, "bind() mkt:" + mkt);
        Log.d(TAG, "bind() image:" + image);
        mImage = image;
        mResolution = resolurtion;
        mMkt = mkt;

        tvMkt.setText(Utils.getMarket(getContext(), mkt));

        String[] copyrightParts = Utils.splitCopyRight(image.copyright);
        tvCopyRightLeft.setText(copyrightParts[0]);
        tvCopyRightRight.setText(copyrightParts[1]);

        Glide.with(getContext()).load(Utils.rebuildImageUrl(image, resolurtion)).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    @OnClick(R.id.layout_copyright)
    void onClickLayoutCopyright() {
        BingImageDetailActivity.action(getContext(), mImage, mResolution);
    }

    @OnClick(R.id.tv_mkt)
    void onClickMkt() {
        MarketActivity.action((Activity)getContext(), MarketActivity.REQUEST_CODE, mMkt);
    }
}
