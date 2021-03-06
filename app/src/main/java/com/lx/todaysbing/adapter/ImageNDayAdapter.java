package com.lx.todaysbing.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lx.todaysbing.view.BingImageNDayItemView;

import java.util.List;

import bing.com.Image;

/**
 * Created by liuxue on 2015/5/9.
 */
public class ImageNDayAdapter extends BaseAdapter {

    private Context context;
    String mColor;
    private List<Image> imageList;
    private String mResolution;

    public ImageNDayAdapter(Context context, String color, List<Image> imageList, String resolution) {
        this.context = context;
        mColor = color;
        this.imageList = imageList;
        mResolution = resolution;
    }

    @Override
    public int getCount() {
        return this.imageList != null ? this.imageList.size() : 0;
    }

    @Override
    public Image getItem(int position) {
        return imageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BingImageNDayItemView itemView = (BingImageNDayItemView) convertView;
        if (itemView == null) {
            itemView = new BingImageNDayItemView(context);
        }

        // Check the height matches our calculated column width
//        if (itemView.imageView.getLayoutParams().height != mItemHeight) {
//            itemView.imageView.setLayoutParams(mImageViewLayoutParams);
//        }

        itemView.bind(position, mColor, getItem(position), mResolution);
        return itemView;
    }

    public void changeData(List<Image> imageList) {
        this.imageList = imageList;
        this.notifyDataSetChanged();
    }
}
