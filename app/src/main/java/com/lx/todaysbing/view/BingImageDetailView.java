package com.lx.todaysbing.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.lx.todaysbing.R;
import com.lx.todaysbing.activity.ResolutionActivity;
import com.lx.todaysbing.model.Image;
import com.lx.todaysbing.util.Utils;

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
    @InjectView(R.id.btnResolution)
    Button btnResolution;

    private Image mImage;

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

        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {

                setupHudLayoutParams();

                if (Utils.hasJellyBean()) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
    }

    private void setupHudLayoutParams() {
        // 1
        DisplayMetrics dm = getResources().getDisplayMetrics();

        RelativeLayout.LayoutParams params = (LayoutParams) layoutHud.getLayoutParams();
        params.width = Math.min(dm.widthPixels, dm.heightPixels);
        layoutHud.setLayoutParams(params);

        // 2.
        Rect frame = new Rect();
        Activity activity = (Activity) getContext();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        Log.d(TAG, "setupHudLayoutParams() statusBarHeight:" + statusBarHeight);

        RelativeLayout.LayoutParams params2 = (LayoutParams) fakeStatusBar.getLayoutParams();
        params2.height = statusBarHeight;
        fakeStatusBar.setLayoutParams(params2);
    }

    public void bind(Image image) {
        mImage = image;

        Glide.with(getContext()).load(Utils.rebuildImageUrl(getContext(), image.url)).into(imageView);
    }

    @OnClick(R.id.btnResolution)
    void onClickResolution() {
        ResolutionActivity.action(getContext());
    }
}
