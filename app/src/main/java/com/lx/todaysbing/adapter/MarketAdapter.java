package com.lx.todaysbing.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.TextView;

import com.lx.todaysbing.R;
import com.lx.todaysbing.activity.MarketActivity;

/**
 * Created by liuxue on 2015/5/13.
 */
public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.ViewHolder> {

    private static final String TAG = "MarketAdapter";

    private final Context context;
    private final String[] markets;

    private AdapterView.OnItemClickListener onItemClickListener;

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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String market = this.markets[position];
        holder.getTvMkt().setText(market);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Element " + position + " clicked.");
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(null, holder.itemView, position, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.markets.length;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public String getItem(int position) {
        return this.markets[position];
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
