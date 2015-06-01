package com.lx.todaysbing.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lx.todaysbing.R;
import com.lx.todaysbing.adapter.BingImagesPagerAdapter;
import com.lx.todaysbing.model.HPImageArchive;
import com.lx.todaysbing.util.API;
import com.lx.todaysbing.util.APIImpl;
import com.lx.todaysbing.util.ResolutionUtils;
import com.lx.todaysbing.util.Utils;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


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
    private static final String ARG_RESOLUTION = "resolution";
//
//    private String mParam1;
//    private String mParam2;

    private static final int TOTAL_COUNT = 3;

    @InjectView(R.id.viewPagerContainer)
    ViewGroup mViewPagerContainer;
    @InjectView(R.id.viewPager)
    ViewPager mViewPager;
    @InjectView(R.id.progressBar)
    ProgressBar progressBar;

    BingImagesPagerAdapter mAdapter;
    ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mViewPagerContainer.invalidate();
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
     * <p/>
     * //     * @param param1 Parameter 1.
     * //     * @param param2 Parameter 2.
     *
     * @return A new instance of fragment TodaysBingFragment.
     */
    public static BingImagesFragment newInstance(String color, String mkt/*, String resolution*/) {
        BingImagesFragment fragment = new BingImagesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_COLOR, color);
        args.putString(ARG_MKT, mkt);
//        args.putString(ARG_RESOLUTION, resolution);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
            mColor = getArguments().getString(ARG_COLOR);
            mMkt = getArguments().getString(ARG_MKT);//"zh-CN";
//            mResolution = getArguments().getString(ARG_RESOLUTION);//Utils.getSuggestResolution(getActivity());
            mResolution = Utils.getSuggestResolution(getActivity());
        }
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
        ButterKnife.inject(this, view);

        setColor();

        mAdapter = new BingImagesPagerAdapter(getActivity());

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(TOTAL_COUNT);
        mViewPager.setCurrentItem(mAdapter.getCount() / 2 - mAdapter.getCount() % 2);
        mViewPager.setPageMargin(0);
        mViewPager.setOnPageChangeListener(mOnPageChangeListener);
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
    }

    private void setColor() {
        Drawable d = DrawableCompat.wrap(progressBar.getIndeterminateDrawable());
        DrawableCompat.setTint(d, Color.parseColor(mColor));
        progressBar.setProgressDrawable(d);
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

//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnBingImagesFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
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
        if (api != null) {
            api.cancel();
        }
    }

    APIImpl api;

    public void bind(String color, String mkt) {
        mColor = color;
        mMkt = mkt;
        mResolution = mResolution;
        Log.d(TAG, "bind() color:" + color);
        Log.d(TAG, "bind() mkt:" + mkt);
        Log.d(TAG, "bind() resolution:" + mResolution);

        mAdapter.changeData(mColor, mMkt, null, mResolution);

        setupViewPagerLayoutParams();

        setColor();

        if (api != null) {
            api.cancel();
        }
        api = new APIImpl();
        progressBar.setVisibility(View.VISIBLE);
        api.getHPImageArchive("js", 0, 7, mkt, 1, new Callback<HPImageArchive>() {
            @Override
            public void success(HPImageArchive hpImageArchive, Response response) {
                progressBar.setVisibility(View.GONE);
                if (api.isCanceld()) {
                    return;
                }
                mAdapter.changeData(mColor, mMkt, hpImageArchive, mResolution);
            }

            @Override
            public void failure(RetrofitError error) {
                progressBar.setVisibility(View.GONE);
                if (api.isCanceld()) {
                    return;
                }
                Toast.makeText(getActivity(), R.string.bing_images_failure, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnBingImagesFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }
}
