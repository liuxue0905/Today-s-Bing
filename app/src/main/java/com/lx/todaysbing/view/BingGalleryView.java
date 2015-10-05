package com.lx.todaysbing.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.android.swiperefreshmultipleviews.MultiSwipeRefreshLayout;
import com.lx.todaysbing.R;
import com.lx.todaysbing.activity.BingImageDetailActivity;
import com.lx.todaysbing.adapter.BingGalleryRVCursorAdapter;
import com.lx.todaysbing.event.OnBingGalleryListEvent;
import com.lx.todaysbing.event.OnBingGalleryListOnErrorResponseEvent;
import com.lx.todaysbing.event.OnBingGalleryScrollEvent;
import com.lx.todaysbing.event.OnBingGallerySwipeRefreshLayoutRefreshingEvent;
import com.lx.todaysbing.model.BingGalleryImageDetail;
import com.lx.todaysbing.model.ImageDetail;

import binggallery.chinacloudsites.cn.BingGalleryImageProvider;
import binggallery.chinacloudsites.cn.Image;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by liuxue on 2015/6/15.
 */
public class BingGalleryView extends RelativeLayout implements AdapterView.OnItemClickListener {

    private static final String TAG = "BingGalleryView";

    private String mColor;
    private String mResolution;

    @Bind(R.id.swipeRefreshLayout)
    MultiSwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.recyclerView)
    public RecyclerView mRecyclerView;
//    @Bind(R.id.btnRefresh)
//    ImageButton btnRefresh;
    @Bind(R.id.layoutSnackbar)
    FrameLayout layoutSnackbar;

    private BingGalleryRVCursorAdapter mAdapter;

    public BingGalleryView(Context context) {
        super(context);
        init(context);
    }

    public BingGalleryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BingGalleryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @SuppressLint("NewApi")
    public BingGalleryView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private int mPosition;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
        appCompatActivity.getSupportLoaderManager().initLoader(mPosition, null, mLoaderCallbacks);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
        appCompatActivity.getSupportLoaderManager().destroyLoader(mPosition);
    }

    public void init(Context context) {
        inflate(context, R.layout.view_bing_gallery, this);
        ButterKnife.bind(this);

        mSwipeRefreshLayout.setSwipeableChildren(R.id.recyclerView);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initiateRefresh();
            }
        });

        setRecyclerViewLayoutManager();

        mAdapter = new BingGalleryRVCursorAdapter(getContext(), null, true);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>(){

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new CursorLoader(getContext(), BingGalleryImageProvider.CONTENT_URI, null, null, null, null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            Log.d(TAG, "onLoadFinished() data:" + data);
            if (data != null) {
                Log.d(TAG, "onLoadFinished() data.getCount():" + data.getCount());
            }
            mAdapter.swapCursor(data);
            OnBingGalleryScrollEvent.scrollToPositionWithOffset(mOnBingGalleryScrollEventReceived, mRecyclerView);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            mAdapter.swapCursor(null);
        }
    };

    private void initiateRefresh() {
//        new DummyBackgroundTask().execute();
//        EventBus.getDefault().postSticky(new OnBingGallerySwipeRefreshLayoutRefreshingEvent(true));
        EventBus.getDefault().post(new OnBingGalleryListEvent());
    }

    private void onRefreshComplete(/*Image[] result*/) {
//        mSwipeRefreshLayout.setRefreshing(false);

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

//        bind(mColor, result, mResolution);
    }

    public void setRecyclerViewLayoutManager() {
//        int scrollPosition = 0;
//
//        // If a layout manager has already been set, get current scroll position.
//        if (mRecyclerView.getLayoutManager() != null) {
//            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
//                    .findFirstCompletelyVisibleItemPosition();
//        }

        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), getResources().getInteger(R.integer.bing_gallery_column));

//        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.scrollToPosition(scrollPosition);
    }

    public void bind(int position, String color, String resolution) {
        mPosition = position;
        mColor = color;
        mResolution = resolution;

//        btnRefresh.setVisibility(View.GONE);
//        mRecyclerView.setVisibility(View.VISIBLE);
    }

//    public void bind(String color, Image[] images, String resolution) {
//        bind(color, resolution);
//
//        Log.d(TAG, "bind() images:" + images);
//
////        mAdapter.changeData(images);
////        if (images == null || images.length == 0) {
////            btnRefresh.setVisibility(View.VISIBLE);
////            mRecyclerView.setVisibility(View.GONE);
////        }
//    }

//    @OnClick(R.id.btnRefresh)
//    public void onClickRefresh() {
//        EventBus.getDefault().post(new OnBingGalleryListEvent());
//    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemClick() position:" + position);
        String color = mColor;
        String resolution = mResolution;
        Image image = mAdapter.getItem(position);
        Log.d(TAG, "onItemClick() image:" + image);
        if (image != null) {

            String imageResolution = null;

            if (imageResolution == null) {
                if (binggallery.chinacloudsites.cn.Image.RESOLUTION_CEDE_L.equalsIgnoreCase(image.getMaxpix())) {
                    imageResolution = binggallery.chinacloudsites.cn.Image.RESOLUTION_VALUE_L;
                } else if (binggallery.chinacloudsites.cn.Image.RESOLUTION_CEDE_W.equalsIgnoreCase(image.getMaxpix())) {
                    imageResolution = binggallery.chinacloudsites.cn.Image.RESOLUTION_VALUE_W;
                }
            }

            String[] resolutions = new String[]{Image.RESOLUTION_VALUE_L, Image.RESOLUTION_VALUE_W};
            if (Image.RESOLUTION_VALUE_L.equalsIgnoreCase(image.getMaxpix())) {
                resolutions = new String[]{Image.RESOLUTION_VALUE_L};
            }

            ImageDetail imageDetail = new BingGalleryImageDetail(image, resolutions);

            BingImageDetailActivity.action(getContext(), color, imageDetail, imageResolution);
        }
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                if (mOnBingGalleryScrollEvent == null) {
                    mOnBingGalleryScrollEvent = new OnBingGalleryScrollEvent(mRecyclerView);
                }
                mOnBingGalleryScrollEvent.refresh();
                EventBus.getDefault().postSticky(mOnBingGalleryScrollEvent);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };

    OnBingGalleryScrollEvent mOnBingGalleryScrollEvent;

    OnBingGalleryScrollEvent mOnBingGalleryScrollEventReceived;
    public void onEvent(OnBingGalleryScrollEvent event) {
//        Log.d(TAG, "onEvent() event:" + event);
        mOnBingGalleryScrollEventReceived = event;
        OnBingGalleryScrollEvent.scrollToPositionWithOffset(mOnBingGalleryScrollEventReceived, mRecyclerView);
    }

    public void onEvent(final OnBingGallerySwipeRefreshLayoutRefreshingEvent event) {
        Log.d(TAG, "onEvent() event:" + event);

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(event.isRefreshing());
            }
        });
    }

    public void onEvent(OnBingGalleryListOnErrorResponseEvent event) {
        if (event.error != null) {
            layoutSnackbar.post(new Runnable() {
                @Override
                public void run() {
                    Snackbar snackbar = Snackbar.make(layoutSnackbar, "加载失败", Snackbar.LENGTH_LONG)
                            .setAction("重新加载", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    EventBus.getDefault().post(new OnBingGalleryListEvent());
                                    layoutSnackbar.removeAllViews();
                                }
                            });
                    layoutSnackbar.addView(snackbar.getView());
                }
            });

//            if (mAdapter.getItemCount() == 0) {
//                btnRefresh.setVisibility(View.VISIBLE);
//            } else {
//                btnRefresh.setVisibility(View.GONE);
//            }

        } else {
//            btnRefresh.setVisibility(View.GONE);
            layoutSnackbar.post(new Runnable() {
                @Override
                public void run() {
                    layoutSnackbar.removeAllViews();
                }
            });
        }
    }
}
