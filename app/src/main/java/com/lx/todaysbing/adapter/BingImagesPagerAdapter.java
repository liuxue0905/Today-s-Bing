package com.lx.todaysbing.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.lx.todaysbing.model.HPImageArchive;
import com.lx.todaysbing.view.BingImageNDayView;
import com.lx.todaysbing.view.BingImageTodayView;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by liuxue on 2015/5/11.
 */
public class BingImagesPagerAdapter extends PagerAdapter {

    Context context;
    String mMkt;
    HPImageArchive mHpImageArchive;
    String mResolurtion;

    public BingImagesPagerAdapter(Context context) {
        this.context = context;
    }

    public void changeData(String mkt, HPImageArchive hpImageArchive, String resolution) {
        mMkt = mkt;
        mHpImageArchive = hpImageArchive;
        mResolurtion = resolution;
        this.notifyDataSetChanged();
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
        int realPosition = position % 2;
        if (realPosition == 0) {
            BingImageTodayView view = new BingImageTodayView(context);
            view.bind(mMkt, mHpImageArchive, mResolurtion);
            container.addView(view);
            return view;
        } else if (realPosition == 1) {
            BingImageNDayView view = new BingImageNDayView(context);
            view.bind(mHpImageArchive, mResolurtion);
            container.addView(view);
            EventBus.getDefault().registerSticky(view);
            return view;
        }
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
//        ((ViewPager)container).removeView((View) object);
//        if (object instanceof BingImageNDayView) {
//            BingImageNDayView view = (BingImageNDayView) object;
//            viewList.remove(view);
//        }
        EventBus.getDefault().unregister(object);
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
//        return super.getItemPosition(object);
        return POSITION_NONE;
    }

}
