package com.lx.todaysbing.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lx.todaysbing.R;
import com.lx.todaysbing.model.ImageDetail;
import com.lx.todaysbing.view.BingImageDetailView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BingImageDetailFragment.OnBingImageDetailFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BingImageDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BingImageDetailFragment extends Fragment {

    private static final String ARG_COLOR = "color";
    private static final String ARG_IMAGE_DETAIL = "Image";
    private static final String ARG_RESOLUTION = "Resolution";

    private BingImageDetailView mBingImageDetailView;

    private String mColor;
    private String mResolution;
    private ImageDetail mImageDetail;

    private OnBingImageDetailFragmentInteractionListener mListener;

    public BingImageDetailFragment() {
        // Required empty public constructor
    }

    public static BingImageDetailFragment newInstance(String color, ImageDetail imageDetail, String resolution, String imageResolution) {
        BingImageDetailFragment fragment = new BingImageDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_COLOR, color);
        args.putSerializable(ARG_IMAGE_DETAIL, (java.io.Serializable) imageDetail);
        args.putString(ARG_RESOLUTION, resolution);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColor = getArguments().getString(ARG_COLOR);
            mImageDetail = (ImageDetail) getArguments().getSerializable(ARG_IMAGE_DETAIL);
            mResolution = getArguments().getString(ARG_RESOLUTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bing_image_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBingImageDetailView = view.findViewById(R.id.viewBingImageDetailView);

        bind();
    }

//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnBingImageDetailFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnBingImageDetailFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void bind() {
        mBingImageDetailView.bind(mColor, mResolution, mImageDetail);
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
    public interface OnBingImageDetailFragmentInteractionListener {
        public void onBingImageDetailFragmentInteraction(Uri uri);
    }

    public void onResolutionChanged(String resolution) {
        mBingImageDetailView.onResolutionChanged(resolution);
    }
}
