package com.lx.todaysbing.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.lx.todaysbing.event.OnBingImageNDayScrollEvent;
import com.lx.todaysbing.view.BingImageNDayView;
import com.lx.todaysbing.view.BingImageTodayView;

import bing.com.HPImageArchive;
import de.greenrobot.event.EventBus;

/**
 * Created by liuxue on 2015/5/11.
 */
public class BingImagesPagerAdapter extends PagerAdapter {

    Context context;
    String mColor;
    String mMkt;
    HPImageArchive mHpImageArchive;
    String mResolurtion;

    private float pageWidth = 0.9f;

    public BingImagesPagerAdapter(Context context) {
        this.context = context;
//        initViews();
    }

    @Override
    public float getPageWidth(int position) {
        return pageWidth;
    }

    public void changeData(String color, String mkt, HPImageArchive hpImageArchive, String resolution) {
        mColor = color;
        mMkt = mkt;
        mHpImageArchive = hpImageArchive;
        mResolurtion = resolution;
        this.notifyDataSetChanged();

        EventBus.getDefault().postSticky(new OnBingImageNDayScrollEvent(null));
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = position % getRealCount();
        if (realPosition == 0) {
            BingImageTodayView view = new BingImageTodayView(context);
            view.bind(mColor, mMkt, mHpImageArchive, mResolurtion);
            container.addView(view);
            EventBus.getDefault().registerSticky(view);
            return view;
        } else if (realPosition == 1) {
            BingImageNDayView view = new BingImageNDayView(context);
            view.bind(mColor, mMkt, mHpImageArchive, mResolurtion);
            container.addView(view);
            EventBus.getDefault().registerSticky(view);
            return view;
        }
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        EventBus.getDefault().unregister(object);
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public int getRealCount() {
        return 2;
    }

    public void setPageWidth(float pageWidth) {
        this.pageWidth = pageWidth;
        this.notifyDataSetChanged();
    }
}
