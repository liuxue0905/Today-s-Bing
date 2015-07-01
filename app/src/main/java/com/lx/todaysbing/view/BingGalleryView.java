package com.lx.todaysbing.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.android.swiperefreshmultipleviews.MultiSwipeRefreshLayout;
import com.lx.todaysbing.R;
import com.lx.todaysbing.TodaysBingApplication;
import com.lx.todaysbing.activity.BingGalleryImageDetailActivity;
import com.lx.todaysbing.adapter.BingGalleryRecyclerViewAdapter;
import com.lx.todaysbing.event.OnBingGalleryListEvent;
import com.lx.todaysbing.event.OnBingGalleryListOnErrorResponseEvent;
import com.lx.todaysbing.event.OnBingGalleryListOnResponseEvent;
import com.lx.todaysbing.event.OnBingGalleryScrollEvent;
import com.lx.todaysbing.event.OnBingGallerySwipeRefreshLayoutRefreshingEvent;

import java.util.List;

import binggallery.chinacloudsites.cn.BingGalleryImageDao;
import binggallery.chinacloudsites.cn.BingGalleryImageProvider;
import binggallery.chinacloudsites.cn.Image;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by liuxue on 2015/6/15.
 */
public class BingGalleryView extends RelativeLayout implements AdapterView.OnItemClickListener/*, LoaderManager.LoaderCallbacks<Cursor>*/ {

    private static final String TAG = "BingGalleryView";

    private String mColor;
    private String mResolution;

    @InjectView(R.id.swipeRefreshLayout)
    MultiSwipeRefreshLayout mSwipeRefreshLayout;
    @InjectView(R.id.recyclerView)
    public RecyclerView mRecyclerView;
    @InjectView(R.id.btnRefresh)
    ImageButton btnRefresh;
    @InjectView(R.id.layoutSnackbar)
    FrameLayout layoutSnackbar;

    private BingGalleryRecyclerViewAdapter mAdapter;

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

    @Override
    protected void onAttachedToWindow() {
        Log.d(TAG, "onAttachedToWindow()");
        super.onAttachedToWindow();

//        AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
//        appCompatActivity.getSupportLoaderManager().initLoader(0, null, this);

        getContext().getContentResolver().registerContentObserver(BingGalleryImageProvider.CONTENT_URI, true, mContentObserver);
    }

    @Override
    protected void onDetachedFromWindow() {
        Log.d(TAG, "onDetachedFromWindow()");
        super.onDetachedFromWindow();

//        AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
//        appCompatActivity.getSupportLoaderManager().destroyLoader(0);

        getContext().getContentResolver().unregisterContentObserver(mContentObserver);
    }

    public void init(Context context) {
        inflate(context, R.layout.view_bing_gallery, this);
        ButterKnife.inject(this);

        mSwipeRefreshLayout.setSwipeableChildren(R.id.recyclerView);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initiateRefresh();
            }
        });

        setRecyclerViewLayoutManager();

        mAdapter = new BingGalleryRecyclerViewAdapter(getContext(), null);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);

        Log.d(TAG, "init() fillData()");
        fillData();
    }

    private void fillData() {
        BingGalleryImageDao bingGalleryImageDao = TodaysBingApplication.getInstance().getBingGalleryImageDao();
        List<Image> imageList = bingGalleryImageDao.loadAll();
        Image[] images = imageList != null ? imageList.toArray(new Image[]{}) : null;
        Log.d(TAG, "fillData() images:" + images);
        mAdapter.changeData(images);
        if (images == null || images.length == 0) {
            btnRefresh.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
    }

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

//    /**
//     * Dummy {@link AsyncTask} which simulates a long running task to fetch new cheeses.
//     */
//    private class DummyBackgroundTask extends AsyncTask<Void, Void, Image[]> {
//
//        static final int TASK_DURATION = 3 * 1000; // 3 seconds
//
//        @Override
//        protected Image[] doInBackground(Void... params) {
//            // Sleep for a small amount of time to simulate a background-task
//            try {
//                Thread.sleep(TASK_DURATION);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Image[] result) {
//            super.onPostExecute(result);
//
//            // Tell the Fragment that the refresh has completed
//            onRefreshComplete(result);
//        }
//
//    }

    public void setRecyclerViewLayoutManager() {
//        int scrollPosition = 0;
//
//        // If a layout manager has already been set, get current scroll position.
//        if (mRecyclerView.getLayoutManager() != null) {
//            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
//                    .findFirstCompletelyVisibleItemPosition();
//        }

        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);

        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.scrollToPosition(scrollPosition);
    }

    public void bind(String color, String resolution) {
        mColor = color;
        mResolution = resolution;

        btnRefresh.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
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

    @OnClick(R.id.btnRefresh)
    public void onClickRefresh() {
        EventBus.getDefault().post(new OnBingGalleryListEvent());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemClick() position:" + position);
        String color = mColor;
        String resolution = mResolution;
        Image image = mAdapter.getItem(position);
        Log.d(TAG, "onItemClick() image:" + image);
        if (image != null) {
            BingGalleryImageDetailActivity.action(getContext(), color, image, resolution);
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

    public void onEvent(OnBingGalleryScrollEvent event) {
//        Log.d(TAG, "onEvent() event:" + event);

        if (event.recyclerView != mRecyclerView) {
            GridLayoutManager gridLayoutManager = ((GridLayoutManager) mRecyclerView.getLayoutManager());
            gridLayoutManager.scrollToPositionWithOffset(event.position, event.offset);
        }
        //        mLayoutManager.scrollToPositionWithOffset();
    }

    public void onEvent(final OnBingGallerySwipeRefreshLayoutRefreshingEvent event) {
        Log.d(TAG, "onEvent() event:" + event);

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(event.refreshing);
            }
        });
    }

    public void onEvent(OnBingGalleryListOnErrorResponseEvent event) {
        EventBus.getDefault().postSticky(new OnBingGallerySwipeRefreshLayoutRefreshingEvent(false));
        if (event.error != null) {
            mSwipeRefreshLayout.post(new Runnable() {
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
        } else {
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    layoutSnackbar.removeAllViews();
                }
            });
        }
    }

    private ContentObserver mContentObserver = new ContentObserver(new Handler()) {
        @Override
        public boolean deliverSelfNotifications() {
            return super.deliverSelfNotifications();
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);

            Log.d(TAG, "onChange() fillData()");
            fillData();
        }
    };

}
