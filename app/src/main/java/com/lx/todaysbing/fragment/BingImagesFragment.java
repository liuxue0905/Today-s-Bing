package com.lx.todaysbing.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lx.todaysbing.R;
import com.lx.todaysbing.TodaysBingApplication;
import com.lx.todaysbing.adapter.BingImagesPagerAdapter;
import com.lx.todaysbing.event.OnBingGalleryListEvent;
import com.lx.todaysbing.event.OnBingGalleryListOnErrorResponseEvent;
import com.lx.todaysbing.event.OnBingGallerySwipeRefreshLayoutRefreshingEvent;
import com.lx.todaysbing.event.OnHPImageArchiveEvent;
import com.lx.todaysbing.event.OnHPImageArchiveFailureEvent;
import com.lx.todaysbing.event.OnHPImageArchivePreLoadEvent;
import com.lx.todaysbing.event.OnHPImageArchiveSuccessEvent;
import com.lx.todaysbing.util.ResolutionUtils;
import com.lx.todaysbing.util.Utils;

import java.util.Arrays;

import bing.com.BingAPI;
import bing.com.HPImageArchive;
import binggallery.chinacloudsites.cn.BingGalleryImageDao;
import binggallery.chinacloudsites.cn.BingGalleryImageProvider;
import binggallery.chinacloudsites.cn.Image;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import uk.co.senab.photoview.sample.HackyViewPager;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnBingImagesFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BingImagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BingImagesFragment extends Fragment {

    private static final String TAG = "BingImagesFragment";

    //    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_COLOR = "color";
    private static final String ARG_MKT = "mkt";

    @Bind(R.id.viewPagerContainer)
    ViewGroup mViewPagerContainer;
    @Bind(R.id.viewPager)
    HackyViewPager mViewPager;
//    @InjectView(R.id.progressBar)
//    ProgressBar progressBar;

    BingAPI api;
    Call<HPImageArchive> mCallGetHPImageArchive;
    Call<String> mCallList;
    StringRequest mStringRequestImageList;

    BingImagesPagerAdapter mAdapter;
    ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mViewPagerContainer.invalidate();
//            mViewPager.invalidate();
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private OnBingImagesFragmentInteractionListener mListener;

    private String mMkt;
    private String mResolution;
    private String mColor;

    public BingImagesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * <p>
     * //     * @param param1 Parameter 1.
     * //     * @param param2 Parameter 2.
     *
     * @return A new instance of fragment TodaysBingFragment.
     */
    public static BingImagesFragment newInstance(String color, String mkt) {
        BingImagesFragment fragment = new BingImagesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_COLOR, color);
        args.putString(ARG_MKT, mkt);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColor = getArguments().getString(ARG_COLOR);
            mMkt = getArguments().getString(ARG_MKT);//"zh-CN";
            mResolution = Utils.getSuggestResolution(getActivity());
        }

        EventBus.getDefault().register(this);

        api = TodaysBingApplication.getInstance().getBingAPI();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bing_images, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mViewPager.setLocked(true);

        setColor();

        mAdapter = new BingImagesPagerAdapter(getActivity());

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(mAdapter.getRealCount());
        mViewPager.setCurrentItem((mAdapter.getCount() / 2) - ((mAdapter.getCount() / 2) % mAdapter.getRealCount()));
        mViewPager.setPageMargin(0);
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
        mViewPagerContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mViewPager.dispatchTouchEvent(event);
            }
        });

        mViewPagerContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {
                setupViewPagerLayoutParams();

                if (Utils.hasJellyBean()) {
                    mViewPagerContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    mViewPagerContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });

        bind(mColor, mMkt);

//        initBingGalleryList(false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void setColor() {
//        Drawable d = DrawableCompat.wrap(progressBar.getIndeterminateDrawable());
//        DrawableCompat.setTint(d, Color.parseColor(mColor));
//        progressBar.setProgressDrawable(d);
    }

    private void setupViewPagerLayoutParams() {
        ResolutionUtils.Resolution resolution = new ResolutionUtils.Resolution(mResolution);

        int[] wh = Utils.getScaledDSizeByFixHeight(resolution.width, resolution.height, mViewPagerContainer.getWidth(), mViewPagerContainer.getHeight());

        Log.d(TAG, "setupViewPagerLayoutParams() mViewPagerContainer.getWidth()" + mViewPagerContainer.getWidth());
        Log.d(TAG, "setupViewPagerLayoutParams() mViewPagerContainer.getHeight()" + mViewPagerContainer.getHeight());
        Log.d(TAG, "setupViewPagerLayoutParams() resolution:" + resolution);
        Log.d(TAG, "setupViewPagerLayoutParams() wh:" + Arrays.toString(wh));

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mViewPager.getLayoutParams();
        params.rightMargin = mViewPagerContainer.getWidth() - Math.min(Utils.get90PWidth(getActivity()), wh[0]);
        mViewPager.setLayoutParams(params);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnBingImagesFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnBingImagesFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);

        if (mCallGetHPImageArchive != null) {
            mCallGetHPImageArchive.cancel();
        }

        if (mCallList != null) {
            mCallList.cancel();
        }

        if (mStringRequestImageList != null) {
            mStringRequestImageList.cancel();
        }
    }


    public void bind(String color, final String mkt) {
        mColor = color;
        mMkt = mkt;
        mResolution = mResolution;
        Log.d(TAG, "bind() color:" + color);
        Log.d(TAG, "bind() mkt:" + mkt);
        Log.d(TAG, "bind() resolution:" + mResolution);

        mAdapter.changeData(mColor, mMkt, null, mResolution);

        setupViewPagerLayoutParams();

        setColor();

        EventBus.getDefault().removeStickyEvent(OnHPImageArchiveFailureEvent.class);
        EventBus.getDefault().postSticky(new OnHPImageArchivePreLoadEvent());

        mCallGetHPImageArchive = api.getHPImageArchive("js", 0, 7, mkt, 1);
        mCallGetHPImageArchive.enqueue(new Callback<HPImageArchive>() {
            @Override
            public void onResponse(Response<HPImageArchive> response, Retrofit retrofit) {
                Log.d(TAG, "onResponse()");
                Log.d(TAG, "onResponse() response:" + response);
                Log.d(TAG, "onResponse() retrofit:" + retrofit);
                Log.d(TAG, "onResponse() response.body():" + response.body());
                Log.d(TAG, "onResponse() response.message():" + response.message());

                HPImageArchive hpImageArchive = response.body();

                EventBus.getDefault().removeStickyEvent(OnHPImageArchivePreLoadEvent.class);
                EventBus.getDefault().postSticky(new OnHPImageArchiveSuccessEvent(hpImageArchive));

                mAdapter.changeData(mColor, mMkt, hpImageArchive, mResolution);

                mViewPager.setLocked(false);

                initBingGalleryList(false);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, "onFailure() t:" + t);

                EventBus.getDefault().removeStickyEvent(OnHPImageArchivePreLoadEvent.class);
                EventBus.getDefault().postSticky(new OnHPImageArchiveFailureEvent(t.getMessage()));

                mViewPager.setLocked(true);

//                Snackbar.make(getView(), R.string.bing_images_failure, Snackbar.LENGTH_SHORT)
//                        .show();
            }
        });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnBingImagesFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    private void initBingGalleryList(boolean isNeedRefresh) {
        Log.d(TAG, "initBingGalleryList() isNeedRefresh:" + isNeedRefresh);

        EventBus.getDefault().postSticky(new OnBingGalleryListOnErrorResponseEvent(null));

        if (!isNeedRefresh) {
            BingGalleryImageDao bingGalleryImageDao = TodaysBingApplication.getInstance().getBingGalleryImageDao();
//            List<Image> imageList = bingGalleryImageDao.loadAll();
            long count = bingGalleryImageDao.count();

            Log.d(TAG, "initBingGalleryList() db count:" + count);
            if (count != 0) {
                return;
            }
        }

        EventBus.getDefault().postSticky(new OnBingGallerySwipeRefreshLayoutRefreshingEvent(true));

//        BingGalleryAPI api = TodaysBingApplication.getInstance().getBingGalleryAPI();
//        mCallList = api.list();
//        mCallList.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Response<String> response, Retrofit retrofit) {
//                Log.d(TAG, "onResponse()");
//                Log.d(TAG, "onResponse() response:" + response);
//                Log.d(TAG, "onResponse() retrofit:" + retrofit);
//                Log.d(TAG, "onResponse() response.body():" + response.body());
//                Log.d(TAG, "onResponse() response.message():" + response.message());
//
//                Image[] images = Image.parse(response.body());
//
//                if (images != null && images.length != 0) {
//                    BingGalleryImageDao bingGalleryImageDao = TodaysBingApplication.getInstance().getBingGalleryImageDao();
//                    bingGalleryImageDao.deleteAll();
//                    bingGalleryImageDao.insertInTx(images);
//
//                    getActivity().getContentResolver().notifyChange(BingGalleryImageProvider.CONTENT_URI, null);
//
//                    EventBus.getDefault().postSticky(new OnBingGallerySwipeRefreshLayoutRefreshingEvent(false));
//                    EventBus.getDefault().postSticky(new OnBingGalleryListOnErrorResponseEvent(null));
//                } else {
//                    EventBus.getDefault().postSticky(new OnBingGallerySwipeRefreshLayoutRefreshingEvent(false));
//                    EventBus.getDefault().postSticky(new OnBingGalleryListOnErrorResponseEvent(""));
//                }
//
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                Log.d(TAG, "onFailure() t:" + t);
//                //                Snackbar.make(getView(), "加载失败", Snackbar.LENGTH_SHORT)
////                        .show();
//
//                EventBus.getDefault().postSticky(new OnBingGallerySwipeRefreshLayoutRefreshingEvent(false));
//                EventBus.getDefault().postSticky(new OnBingGalleryListOnErrorResponseEvent(t.getMessage()));
//            }
//        });

        mStringRequestImageList = new StringRequest(Request.Method.GET, Image.API_LIST, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse() response:" + response);
                Image[] images = Image.parse(response);

                if (images != null && images.length != 0) {
                    BingGalleryImageDao bingGalleryImageDao = TodaysBingApplication.getInstance().getBingGalleryImageDao();
                    bingGalleryImageDao.deleteAll();
                    bingGalleryImageDao.insertInTx(images);

                    getActivity().getContentResolver().notifyChange(BingGalleryImageProvider.CONTENT_URI, null);

                    EventBus.getDefault().postSticky(new OnBingGallerySwipeRefreshLayoutRefreshingEvent(false));
                    EventBus.getDefault().postSticky(new OnBingGalleryListOnErrorResponseEvent(null));
                } else {
                    EventBus.getDefault().postSticky(new OnBingGallerySwipeRefreshLayoutRefreshingEvent(false));
                    EventBus.getDefault().postSticky(new OnBingGalleryListOnErrorResponseEvent(""));
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse() error:" + error);
                EventBus.getDefault().postSticky(new OnBingGallerySwipeRefreshLayoutRefreshingEvent(false));
                EventBus.getDefault().postSticky(new OnBingGalleryListOnErrorResponseEvent(error.getMessage()));
            }
        });
        RequestQueue mQueue = Volley.newRequestQueue(getContext());
        mQueue.add(mStringRequestImageList);
    }

    public void onEvent(OnBingGalleryListEvent event) {
        initBingGalleryList(true);
    }

    public void onEvent(OnHPImageArchiveEvent event) {
        bind(mColor, mMkt);
    }
}
