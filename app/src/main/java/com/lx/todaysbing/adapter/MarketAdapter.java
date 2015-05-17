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
public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.ViewHolder> {

    private static final String TAG = "MarketAdapter";

    private final Context context;
    private final String[] markets;

    public MarketAdapter(Context context) {
        this.context = context;
        this.markets = context.getResources().getStringArray(R.array.market);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_market, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String market = this.markets[position];
        holder.getTvMkt().setText(market);
    }

    @Override
    public int getItemCount() {
        return this.markets.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvMkt;

        public ViewHolder(View itemView) {
            super(itemView);
            // Define click listener for the ViewHolder's View.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getPosition() + " clicked.");
                }
            });
            tvMkt = (TextView) itemView.findViewById(R.id.tv_mkt);
        }

        public TextView getTvMkt() {
            return tvMkt;
        }
    }
}
