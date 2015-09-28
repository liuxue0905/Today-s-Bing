package com.lx.todaysbing.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

/**
 * Created by liuxue on 2015/6/20.
 */
public class ScaleRelativeLayout extends RelativeLayout {

    private static final String TAG = ScaleRelativeLayout.class.getCanonicalName();

//    private int mTouchSlop;

    private Animator anim1;
    private Animator anim2;

    private int mHeight;
    private int mWidth;

    private float mX, mY;

    private static final float SCALE = 0.97f;
    private static int DURATION = 60;

    private Handler mHandler = new Handler();

    public ScaleRelativeLayout(Context context) {
        super(context);
        init(context);
    }

    public ScaleRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScaleRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @SuppressLint("NewApi")
    public ScaleRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
//        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        PropertyValuesHolder scaleXPropertyValuesHolder1 = PropertyValuesHolder.ofFloat(
                "scaleX", 1f, SCALE);
        PropertyValuesHolder scaleYPropertyValuesHolder1 = PropertyValuesHolder.ofFloat(
                "scaleY", 1f, SCALE);
        anim1 = ObjectAnimator.ofPropertyValuesHolder(this, scaleXPropertyValuesHolder1,
                scaleYPropertyValuesHolder1);
        anim1.setDuration(DURATION);
        anim1.setInterpolator(new LinearInterpolator());

        PropertyValuesHolder scaleXPropertyValuesHolder2 = PropertyValuesHolder.ofFloat(
                "scaleX", SCALE, 1f);
        PropertyValuesHolder scaleYPropertyValuesHolder2 = PropertyValuesHolder.ofFloat(
                "scaleY", SCALE, 1f);
        anim2 = ObjectAnimator.ofPropertyValuesHolder(this, scaleXPropertyValuesHolder2,
                scaleYPropertyValuesHolder2);
        anim2.setDuration(DURATION);
        anim2.setInterpolator(new LinearInterpolator());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = getHeight() - getPaddingTop() - getPaddingBottom();
        mWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        mX = getX();
        mY = getY();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent()");

        final float x = event.getX();
        final float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent() ACTION_DOWN");
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        anim2.end();
                        anim1.start();
                    }
                });
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onTouchEvent() ACTION_MOVE");
                Log.d(TAG, "onTouchEvent() pointInView:" + pointInViewOverride(x, y));
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onTouchEvent() ACTION_UP");
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        anim1.end();
                        anim2.start();
                    }
                });
                callOnClick();
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "onTouchEvent() ACTION_CANCEL");
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        anim1.end();
                        anim2.start();
                    }
                });
                break;
        }
        return true;
//        return super.onTouchEvent(event);
    }

//    protected boolean innerImageView(float x, float y) {
//
//        if (x >= mX && y <= mX + mWidth) {
//            if (y >= mY && y <= mY + mHeight) {
//                return true;
//            }
//        }
//        return false;
//    }

    boolean pointInViewOverride(float localX, float localY) {
        int mRight = getRight();
        int mLeft = getLeft();
        int mBottom = getBottom();
        int mTop = getTop();
        return localX >= 0 && localX < (mRight - mLeft)
                && localY >= 0 && localY < (mBottom - mTop);
    }
}
