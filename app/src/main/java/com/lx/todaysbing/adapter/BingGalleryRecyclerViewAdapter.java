package com.lx.todaysbing.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.lx.todaysbing.view.BingGalleryItemView;

import binggallery.chinacloudsites.cn.Image;

/**
 * Created by liuxue on 2015/6/21.
 */
public class BingGalleryRecyclerViewAdapter extends RecyclerView.Adapter<BingGalleryRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "BGRecyclerViewAdapter";

    AdapterView.OnItemClickListener mOnItemClickListener;
    private Context context;

    private Image[] images;

    public BingGalleryRecyclerViewAdapter(Context context, Image[] images) {
        this.context = context;
        this.images = images;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view.
//        View v = LayoutInflater.from(viewGroup.getContext())
//                .inflate(R.layout.item_bing_image_nday, viewGroup, false);
        View v = new BingGalleryItemView(context);

//        return new ViewHolder(v);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
//        viewHolder.getTextView().setText(mDataSet[position]);

        ((BingGalleryItemView) holder.itemView).bind(position, getItem(position));

        final ViewHolder fViewHolder = holder;
        ((BingGalleryItemView) holder.itemView).rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "a Element " + position + " clicked.");
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(null, fViewHolder.itemView, position, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return images != null ? images.length : 0;
    }

    public Image getItem(int position) {
        return images[position];
    }

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

//        private final TextView textView;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "b Element " + getPosition() + " clicked.");
                }
            });
//            textView = (TextView) v.findViewById(R.id.textView);
        }

//        public TextView getTextView() {
//            return textView;
//        }
    }
}
