package com.lx.todaysbing.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lx.todaysbing.R;

/**
 * Created by liuxue on 2015/5/13.
 */
public class ResolutionAdapter extends RecyclerView.Adapter<ResolutionAdapter.ViewHolder> {

    private static final String TAG = "ResolutionAdapter";

    private final Context context;
    private final String[] resolutions;

    public ResolutionAdapter(Context context) {
        this.context = context;
        this.resolutions = context.getResources().getStringArray(R.array.resolution);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_resolution, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String resolution = this.resolutions[position];
        holder.getTvResolution().setText(resolution);
    }

    @Override
    public int getItemCount() {
        return this.resolutions.length;
    }

//    public String getItem(int position) {
//        return this.resolutions[position];
//    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvResolution;

        public ViewHolder(View itemView) {
            super(itemView);
            // Define click listener for the ViewHolder's View.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getPosition() + " clicked.");
                }
            });
            tvResolution = (TextView) itemView.findViewById(R.id.tvResolution);
        }

        public TextView getTvResolution() {
            return tvResolution;
        }
    }
}
