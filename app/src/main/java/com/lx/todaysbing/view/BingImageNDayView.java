package com.lx.todaysbing.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.lx.todaysbing.R;
import com.lx.todaysbing.activity.BingImageDetailActivity;
import com.lx.todaysbing.adapter.ImageNDayRecyclerViewAdapter;
import com.lx.todaysbing.model.HPImageArchive;
import com.lx.todaysbing.model.Image;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by liuxue on 2015/5/8.
 */
public class BingImageNDayView extends RelativeLayout {

    private static final String TAG = "BingImageNDayView";
    private AdapterView.OnItemClickListener mOnItemClickList = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d(TAG, "onItemClick() position:" + position);
            Image image = mAdapter.getItem(position);
            Log.d(TAG, "onItemClick() image:" + image);
            if (image != null) {
                BingImageDetailActivity.action(getContext(), image);
            }
        }
    };
    @InjectView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private ImageNDayRecyclerViewAdapter mAdapter;

    public BingImageNDayView(Context context) {
        super(context);
        init(context);
    }

    public BingImageNDayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BingImageNDayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BingImageNDayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void init(Context context) {
        inflate(context, R.layout.view_bing_image_nday, this);
        ButterKnife.inject(this);

        setRecyclerViewLayoutManager();
    }

    public void bind(HPImageArchive hpImageArchive) {
        if (hpImageArchive != null) {
            bind(new ArrayList<Image>(hpImageArchive.images.subList(1, hpImageArchive.images.size())));
        }
    }

    public void bind(List<Image> imageList) {
        mAdapter = new ImageNDayRecyclerViewAdapter(getContext(), imageList);
        mAdapter.setOnItemClickListener(mOnItemClickList);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setRecyclerViewLayoutManager() {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);

    }
}
