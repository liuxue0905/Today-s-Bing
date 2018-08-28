package com.lx.todaysbing.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lx.todaysbing.R;

/**
 * Created by liuxue on 2015/5/13.
 */
public class MarketListViewAdapter extends BaseAdapter {

    private static final String TAG = "MarketListViewAdapter";

    private final Context context;
    private final String[] markets;

    public MarketListViewAdapter(Context context) {
        this.context = context;
        this.markets = context.getResources().getStringArray(R.array.market);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_market, parent, false);
//        return new ViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        String market = this.markets[position];
//        holder.getTvMkt().setText(market);
//    }
//
//    @Override
//    public int getItemCount() {
//        return this.markets.length;
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//
//        private final TextView tvMkt;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            // Define click listener for the ViewHolder's View.
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d(TAG, "Element " + getPosition() + " clicked.");
//                }
//            });
//            tvMkt = (TextView) itemView.findViewById(R.id.tv_mkt);
//        }
//
//        public TextView getTvMkt() {
//            return tvMkt;
//        }
//    }
}
