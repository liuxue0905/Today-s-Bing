/*
* Copyright (C) 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.lx.todaysbing.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import bing.com.Image;
import com.lx.todaysbing.view.BingImageNDayItemView;

import java.util.List;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class ImageNDayRecyclerViewAdapter extends RecyclerView.Adapter<ImageNDayRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";
    AdapterView.OnItemClickListener mOnItemClickListener;
    private Context context;
    private List<Image> mDataSet;
    private String mResolution;
    private String mColor;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     * @param resolution
     */
    public ImageNDayRecyclerViewAdapter(Context context, List<Image> dataSet, String resolution, String color) {
        this.context = context;
        mDataSet = dataSet;
        mResolution = resolution;
        mColor = color;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
//        View v = LayoutInflater.from(viewGroup.getContext())
//                .inflate(R.layout.item_bing_image_nday, viewGroup, false);
        View v = new BingImageNDayItemView(context);

//        return new ViewHolder(v);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
//        viewHolder.getTextView().setText(mDataSet[position]);

        ((BingImageNDayItemView) viewHolder.itemView).bind(position, mColor, mDataSet.get(position), mResolution);

        final ViewHolder fViewHolder = viewHolder;
        ((BingImageNDayItemView) viewHolder.itemView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Element " + position + " clicked.");
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(null, fViewHolder.itemView, position, position);
                }
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet != null ? mDataSet.size() : 0;
    }

    public Image getItem(int position) {
        return mDataSet != null ? mDataSet.get(position) : null;
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
                    Log.d(TAG, "Element " + getPosition() + " clicked.");
                }
            });
//            textView = (TextView) v.findViewById(R.id.textView);
        }

//        public TextView getTextView() {
//            return textView;
//        }
    }
}
