package com.lx.todaysbing.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.lx.todaysbing.TodaysBingApplication;
import com.lx.todaysbing.view.BingGalleryItemView;
import com.lx.todaysbing.widget.RecyclerViewCursorAdapter;

import binggallery.chinacloudsites.cn.BingGalleryImage;
import binggallery.chinacloudsites.cn.BingGalleryImageDao;
import binggallery.chinacloudsites.cn.Image;

/**
 * Created by liuxue on 2015/6/21.
 */
public class BingGalleryRVCursorAdapter extends RecyclerViewCursorAdapter {

    private static final String TAG = "BGRVCursorAdapter";

    AdapterView.OnItemClickListener mOnItemClickListener;
    private Context context;
    private String color;

    public BingGalleryRVCursorAdapter(Context context, Cursor c) {
        super(context, c);
        init(context);
    }

    public BingGalleryRVCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        init(context);
    }

    public BingGalleryRVCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        init(context);
    }

    BingGalleryImageDao mBingGalleryImageDao;

    private void init(Context context) {
        this.context = context;
        mBingGalleryImageDao = TodaysBingApplication.getInstance().getBingGalleryImageDao();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mOnItemClickListener = listener;
        this.context = context;
    }

    public BingGalleryImage getItem(int position) {
        Log.d(TAG, "getItem() position:" + position);
        Cursor cursor = (Cursor) super.getItem(position);
        return mBingGalleryImageDao.readEntity(cursor, 0);
    }

    @Override
    public View newView(ViewGroup parent, int viewType) {
//        return null;

        View v = new BingGalleryItemView(context);
        return v;
    }

    @Override
    public void bindView(final View view, Context context, Cursor cursor) {
        final int position = cursor.getPosition();

        ((BingGalleryItemView) view).bind(position, color, getItem(position));

        ((BingGalleryItemView) view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "a Element " + position + " clicked.");
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(null, view, position, position);
                }
            }
        });
    }

    public void setColor(String color) {
        this.color = color;
        this.notifyDataSetChanged();
    }
}
