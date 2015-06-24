package com.lx.todaysbing.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lx.todaysbing.R;
import com.lx.todaysbing.view.BingGalleryImageDetailView;
import com.lx.todaysbing.view.BingImageDetailView;

import binggallery.chinacloudsites.cn.Image;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BingGalleryImageDetailFragment.OnBingImageDetailFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BingGalleryImageDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BingGalleryImageDetailFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_COLOR = "color";
    private static final String ARG_IMAGE = "Image";
    private static final String ARG_RESOLUTION = "Resolution";

    @InjectView(R.id.viewBingImageDetailView)
    BingGalleryImageDetailView mBingImageDetailView;

    private String mColor;
    private Image mImage;
    private String mResolution;

    private OnBingImageDetailFragmentInteractionListener mListener;

    public BingGalleryImageDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param image Parameter 1.
     * @return A new instance of fragment BingImageDetailFragment.
     */
    public static BingGalleryImageDetailFragment newInstance(String color, Image image, String resolution) {
        BingGalleryImageDetailFragment fragment = new BingGalleryImageDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_COLOR, color);
        args.putSerializable(ARG_IMAGE, (java.io.Serializable) image);
        args.putString(ARG_RESOLUTION, resolution);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColor = getArguments().getString(ARG_COLOR);
            mImage = (Image) getArguments().getSerializable(ARG_IMAGE);
            mResolution = getArguments().getString(ARG_RESOLUTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bing_gallery_image_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);

        bind(mImage, mResolution);
    }

//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            mListener = (OnBingImageDetailFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnBingImageDetailFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void bind(Image image, String resolution) {
        mImage = image;
        mResolution = resolution;
        mBingImageDetailView.bind(mColor, mImage, mResolution);
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
        // TODO: Update argument type and name
        public void onBingImageDetailFragmentInteraction(Uri uri);
    }

}
