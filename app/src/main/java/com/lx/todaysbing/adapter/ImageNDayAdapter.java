package com.lx.todaysbing.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.lx.todaysbing.model.Image;
import com.lx.todaysbing.view.BingImageNDayItemView;

import java.util.List;

/**
 * Created by liuxue on 2015/5/9.
 */
public class ImageNDayAdapter extends BaseAdapter {

    private Context context;
    private List<Image> imageList;

    private int mItemHeight = 0;
    private RelativeLayout.LayoutParams mImageViewLayoutParams;

    public ImageNDayAdapter(Context context, List<Image> imageList) {
        this.context = context;
        this.imageList = imageList;
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

        itemView.bind(position, getItem(position));
        return itemView;
    }

    public void setItemHeight(int height) {
        if (height == mItemHeight) {
            return;
        }
        mItemHeight = height;
        mImageViewLayoutParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, mItemHeight);
//        mImageFetcher.setImageSize(height);
        notifyDataSetChanged();
    }

    public void changeData(List<Image> imageList) {
        this.imageList = imageList;
        this.notifyDataSetChanged();
    }
}
