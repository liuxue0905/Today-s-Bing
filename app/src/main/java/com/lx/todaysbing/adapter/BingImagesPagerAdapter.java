package com.lx.todaysbing.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.lx.todaysbing.model.HPImageArchive;
import com.lx.todaysbing.view.BingImageNDayView;
import com.lx.todaysbing.view.BingImageTodayView;

/**
 * Created by liuxue on 2015/5/11.
 */
public class BingImagesPagerAdapter extends PagerAdapter {

    Context context;
    String mMkt;
    HPImageArchive hpImageArchive;
    String mResolurtion;

    public BingImagesPagerAdapter(Context context) {
        this.context = context;
    }

    public void changeData(String mkt, HPImageArchive hpImageArchive, String resolution) {
        mMkt = mkt;
        this.hpImageArchive = hpImageArchive;
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
            BingImageTodayView bitv = new BingImageTodayView(context);
            bitv.bind(mMkt, hpImageArchive, mResolurtion);
            container.addView(bitv);
            return bitv;
        } else if (realPosition == 1) {
            BingImageNDayView binv = new BingImageNDayView(context);
            binv.bind(hpImageArchive, mResolurtion);
            container.addView(binv);
            return binv;
        }
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
//        ((ViewPager)container).removeView((View) object);
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
//        return super.getItemPosition(object);
        return POSITION_NONE;
    }
}
