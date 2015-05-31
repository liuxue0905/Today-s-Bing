package com.lx.todaysbing.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.lx.todaysbing.R;
import com.lx.todaysbing.activity.BingImageDetailActivity;
import com.lx.todaysbing.adapter.ImageNDayAdapter;
import com.lx.todaysbing.adapter.ImageNDayRecyclerViewAdapter;
import com.lx.todaysbing.event.OnScrollEvent;
import com.lx.todaysbing.model.HPImageArchive;
import com.lx.todaysbing.model.Image;
import com.lx.todaysbing.umeng.MobclickAgentHelper;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * Created by liuxue on 2015/5/8.
 */
public class BingImageNDayView extends RelativeLayout implements AdapterView.OnItemClickListener/*, AbsListView.OnScrollListener */{

    private static final String TAG = "BingImageNDayView";

    @InjectView(R.id.recyclerView)
    public RecyclerView mRecyclerView;
    private ImageNDayRecyclerViewAdapter mAdapter;

    private String mResolution;

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

    public void bind(HPImageArchive hpImageArchive, String resolution) {
        if (hpImageArchive != null) {
            bind(new ArrayList<Image>(hpImageArchive.images.subList(1, hpImageArchive.images.size())), resolution);
        }
    }

    public void bind(List<Image> imageList, String resolution) {
        mResolution = resolution;

//        mAdapter = new ImageNDayAdapter(getContext(), imageList, resolution);
//        mListView.setAdapter(mAdapter);
//        mListView.setOnItemClickListener(this);
//        mListView.setOnScrollListener(this);

        mAdapter = new ImageNDayRecyclerViewAdapter(getContext(), imageList, resolution);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemClick() position:" + position);
        Image image = mAdapter.getItem(position);
        String resolution = mResolution;
        Log.d(TAG, "onItemClick() image:" + image);
        if (image != null) {
            BingImageDetailActivity.action(getContext(), image, resolution);
        }

        Map<String, String> map = new HashMap<>();
        map.put("position", String.valueOf(position));
        MobclickAgent.onEvent(getContext(), MobclickAgentHelper.BingImageNDay.EVENT_ID_BINGIMAGENDAY_ONITEMCLICK, map);
    }

    public void setRecyclerViewLayoutManager() {
//        int scrollPosition = 0;
//
//        // If a layout manager has already been set, get current scroll position.
//        if (mRecyclerView.getLayoutManager() != null) {
//            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
//                    .findFirstCompletelyVisibleItemPosition();
//        }

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.scrollToPosition(scrollPosition);
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                if (mOnScrollEvent == null) {
                    mOnScrollEvent = new OnScrollEvent(mRecyclerView);
                }
                mOnScrollEvent.refresh();
                EventBus.getDefault().postSticky(mOnScrollEvent);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };

    OnScrollEvent mOnScrollEvent;

    public void onEvent(OnScrollEvent event) {
        Log.d(TAG, "onEvent() event:" + event);

        if (event.recyclerView != mRecyclerView) {
            LinearLayoutManager linearLayoutManager2 = ((LinearLayoutManager) mRecyclerView.getLayoutManager());
            linearLayoutManager2.scrollToPositionWithOffset(event.position, event.offset);
        }
        //        mLayoutManager.scrollToPositionWithOffset();
    }
}
