package com.lx.todaysbing.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.lx.todaysbing.R;
import com.lx.todaysbing.activity.BingImageDetailActivity;
import com.lx.todaysbing.adapter.ImageNDayRecyclerViewAdapter;
import com.lx.todaysbing.event.OnBingImageNDayScrollEvent;
import com.lx.todaysbing.model.BingImageDetail;
import com.lx.todaysbing.model.ImageDetail;
import com.lx.todaysbing.umeng.MobclickAgentHelper;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bing.com.HPImageArchive;
import bing.com.Image;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by liuxue on 2015/5/8.
 */
public class BingImageNDayView extends RelativeLayout implements AdapterView.OnItemClickListener/*, AbsListView.OnScrollListener */ {

    private static final String TAG = "BingImageNDayView";

    @Bind(R.id.recyclerView)
    public RecyclerView mRecyclerView;

    private ImageNDayRecyclerViewAdapter mAdapter;

    private String mColor;
    private String mMkt;
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
        ButterKnife.bind(this);

        setRecyclerViewLayoutManager();
    }

    public void bind(String color, String mkt, HPImageArchive hpImageArchive, String resolution) {
        if (hpImageArchive != null) {
            bind(color, mkt, new ArrayList<Image>(hpImageArchive.images.subList(1, hpImageArchive.images.size())), resolution);
        }
    }

    public void bind(String color, String mkt, List<Image> imageList, String resolution) {
        mColor = color;
        mMkt = mkt;
        mResolution = resolution;

        mAdapter = new ImageNDayRecyclerViewAdapter(getContext(), imageList, resolution, mColor);
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
            String[] resolutions = getContext().getResources().getStringArray(R.array.resolution);
            ImageDetail imageDetail = new BingImageDetail(image, resolutions, mMkt);
            BingImageDetailActivity.action(getContext(), mColor, imageDetail, resolution);
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

        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), getResources().getInteger(R.integer.bing_image_nday_column));

        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.scrollToPosition(scrollPosition);
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                if (mOnBingImageNDayScrollEvent == null) {
                    mOnBingImageNDayScrollEvent = new OnBingImageNDayScrollEvent(mRecyclerView);
                }
                mOnBingImageNDayScrollEvent.refresh();
                EventBus.getDefault().postSticky(mOnBingImageNDayScrollEvent);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };

    OnBingImageNDayScrollEvent mOnBingImageNDayScrollEvent;

    public void onEvent(OnBingImageNDayScrollEvent event) {
        Log.d(TAG, "onEvent() event:" + event);
        OnBingImageNDayScrollEvent.scrollToPositionWithOffset(event, mRecyclerView);
    }
}
