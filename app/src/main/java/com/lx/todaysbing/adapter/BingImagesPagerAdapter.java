package com.lx.todaysbing.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import bing.com.HPImageArchive;

import com.lx.todaysbing.event.OnBingGalleryScrollEvent;
import com.lx.todaysbing.event.OnBingImageNDayScrollEvent;
import com.lx.todaysbing.view.BingGalleryView;
import com.lx.todaysbing.view.BingImageNDayView;
import com.lx.todaysbing.view.BingImageTodayView;

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

    public BingImagesPagerAdapter(Context context) {
        this.context = context;
//        initViews();
    }

    public void changeData(String color, String mkt, HPImageArchive hpImageArchive, String resolution) {
        mColor = color;
        mMkt = mkt;
        mHpImageArchive = hpImageArchive;
        mResolurtion = resolution;
        this.notifyDataSetChanged();

        EventBus.getDefault().postSticky(new OnBingImageNDayScrollEvent(null));
        EventBus.getDefault().postSticky(new OnBingGalleryScrollEvent(null));
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
            view.bind(mColor, mHpImageArchive, mResolurtion);
            container.addView(view);
            EventBus.getDefault().registerSticky(view);
            return view;
        } else if (realPosition == 2) {
            BingGalleryView view = new BingGalleryView(context);
            view.bind(position, mColor, mResolurtion);
            container.addView(view);
            EventBus.getDefault().registerSticky(view);
            return view;
        }
        return super.instantiateItem(container, position);
    }

//    private View[] mViews = new View[3];
//
//    private void initViews() {
//        BingImageTodayView view0 = new BingImageTodayView(context);
////        view0.bind(mColor, mMkt, mHpImageArchive, mResolurtion);
//        EventBus.getDefault().registerSticky(view0);
//        mViews[0] = view0;
//
//        BingImageNDayView view1 = new BingImageNDayView(context);
////        view1.bind(mColor, mHpImageArchive, mResolurtion);
//        EventBus.getDefault().registerSticky(view1);
//        mViews[1] = view1;
//
//        BingGalleryView view2 = new BingGalleryView(context);
////        view2.bind(0, mColor, mResolurtion);
//        EventBus.getDefault().registerSticky(view2);
//        mViews[2] = view2;
//    }
//
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        int realPosition = position % getRealCount();
//        if (realPosition == 0) {
//            BingImageTodayView view = (BingImageTodayView) mViews[0];
//            view.bind(mColor, mMkt, mHpImageArchive, mResolurtion);
//            if (view.getParent() == null) {
//                container.addView(view);
//            }
//        } else if (realPosition == 1) {
//            BingImageNDayView view = (BingImageNDayView) mViews[1];
//            view.bind(mColor, mHpImageArchive, mResolurtion);
//            if (view.getParent() == null) {
//                container.addView(view);
//            }
//        } else if (realPosition == 2) {
//            BingGalleryView view = (BingGalleryView) mViews[2];
//            view.bind(position, mColor, mResolurtion);
//            if (view.getParent() == null) {
//                container.addView(view);
//            }
//        }
//        return mViews[realPosition];
//    }

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

    public int getRealCount() {
        return 3;
    }
}
