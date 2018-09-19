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

import com.lx.todaysbing.R;
import com.lx.todaysbing.TodaysBingApplication;
import com.lx.todaysbing.adapter.BingImagesPagerAdapter;
import com.lx.todaysbing.event.OnHPImageArchiveEvent;
import com.lx.todaysbing.event.OnHPImageArchiveFailureEvent;
import com.lx.todaysbing.event.OnHPImageArchivePreLoadEvent;
import com.lx.todaysbing.event.OnHPImageArchiveSuccessEvent;
import com.lx.todaysbing.util.ResolutionUtils;
import com.lx.todaysbing.util.Utils;

import java.util.Arrays;

import bing.com.BingAPI;
import bing.com.HPImageArchive;
import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

    private ViewGroup mViewPagerContainer;
    private HackyViewPager mViewPager;
//    @InjectView(R.id.progressBar)
//    ProgressBar progressBar;

    BingAPI api;
    Call<HPImageArchive> mCallGetHPImageArchive;
    Call<String> mCallList;

    BingImagesPagerAdapter mAdapter;
//        ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
//
//        @Override
//        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            mViewPagerContainer.invalidate();
//            mViewPager.invalidate();
//        }
//
//        @Override
//        public void onPageSelected(int position) {
//
//        }
//
//        @Override
//        public void onPageScrollStateChanged(int state) {
//
//        }
//    };
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

        mViewPagerContainer = view.findViewById(R.id.viewPagerContainer);
        mViewPager = view.findViewById(R.id.viewPager);

        mViewPager.setLocked(true);

        setColor();

        mAdapter = new BingImagesPagerAdapter(getActivity());

        mViewPager.setAdapter(mAdapter);
//        mViewPager.setOffscreenPageLimit(mAdapter.getRealCount());
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setCurrentItem((mAdapter.getCount() / 2) - ((mAdapter.getCount() / 2) % mAdapter.getRealCount()));
        mViewPager.setPageMargin(0);
//        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
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

    private boolean setupViewPagerLayoutParams() {

        if (mViewPagerContainer.getWidth() != 0) {

            ResolutionUtils.Resolution resolution = new ResolutionUtils.Resolution(mResolution);

            int[] wh = Utils.getScaledDSizeByFixHeight(resolution.width, resolution.height, mViewPagerContainer.getWidth(), mViewPagerContainer.getHeight());

            Log.d(TAG, "setupViewPagerLayoutParams() mViewPagerContainer.getWidth()" + mViewPagerContainer.getWidth());
            Log.d(TAG, "setupViewPagerLayoutParams() mViewPagerContainer.getHeight()" + mViewPagerContainer.getHeight());
            Log.d(TAG, "setupViewPagerLayoutParams() resolution:" + resolution);
            Log.d(TAG, "setupViewPagerLayoutParams() wh:" + Arrays.toString(wh));
            Log.d(TAG, "setupViewPagerLayoutParams() Utils.get90PWidth(getActivity()):" + Utils.get90PWidth(getActivity()));

            float pageWidth = (float) Math.min(Utils.get90PWidth(getActivity()), wh[0]) / mViewPagerContainer.getWidth();

            Log.d(TAG, "setupViewPagerLayoutParams() pageWidth:" + pageWidth);

            mAdapter.setPageWidth(pageWidth);

            return true;
        }

        return false;
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
    }


    public void bind(String color, final String mkt) {
        mColor = color;
        mMkt = mkt;

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
            public void onResponse(Call<HPImageArchive> call, Response<HPImageArchive> response) {
                Log.d(TAG, "onResponse()");
                Log.d(TAG, "onResponse() call:" + call);
                Log.d(TAG, "onResponse() response:" + response);
                Log.d(TAG, "onResponse() response.body():" + response.body());
                Log.d(TAG, "onResponse() response.message():" + response.message());

                HPImageArchive hpImageArchive = response.body();

                EventBus.getDefault().removeStickyEvent(OnHPImageArchivePreLoadEvent.class);
                EventBus.getDefault().postSticky(new OnHPImageArchiveSuccessEvent(hpImageArchive));

                mAdapter.changeData(mColor, mMkt, hpImageArchive, mResolution);

                mViewPager.setLocked(false);
            }

            @Override
            public void onFailure(Call<HPImageArchive> call, Throwable t) {
                Log.d(TAG, "onFailure() call:" + call);
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
        void onFragmentInteraction(Uri uri);
    }

    public void onEvent(OnHPImageArchiveEvent event) {
        bind(mColor, mMkt);
    }
}
