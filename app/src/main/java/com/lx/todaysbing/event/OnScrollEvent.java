package com.lx.todaysbing.event;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by liuxue on 2015/5/30.
 */
public class OnScrollEvent {

    private static final String TAG = "OnScrollEvent";

    public RecyclerView recyclerView;

    public int position;
    public int offset;

    public OnScrollEvent(RecyclerView view) {
        this.recyclerView = view;
        refresh();
    }

    public void refresh() {
        if (recyclerView == null) {
            return;
        }
        LinearLayoutManager linearLayoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());

        int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        position = firstVisibleItemPosition;
        RecyclerView.ViewHolder viewHolderFirstVisibleItemPosition = recyclerView.findViewHolderForAdapterPosition(firstVisibleItemPosition);
        Log.d(TAG, "OnScrollEvent() firstVisibleItemPosition:" + firstVisibleItemPosition);
        if (viewHolderFirstVisibleItemPosition != null && viewHolderFirstVisibleItemPosition.itemView != null) {
            int top = viewHolderFirstVisibleItemPosition.itemView.getTop();
            Log.d(TAG, "OnScrollEvent() viewHolderFirstVisibleItemPosition top:" + top);
            offset = top;
        }

//                int firstCompletelyVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
//                RecyclerView.ViewHolder viewHolderFirstCompletelyVisibleItemPosition = recyclerView.findViewHolderForAdapterPosition(firstCompletelyVisibleItemPosition);
//                Log.d(TAG, "onScrollStateChanged() firstCompletelyVisibleItemPosition:" + firstCompletelyVisibleItemPosition);
//                if (viewHolderFirstCompletelyVisibleItemPosition != null && viewHolderFirstCompletelyVisibleItemPosition.itemView != null) {
//                    int offset = viewHolderFirstCompletelyVisibleItemPosition.itemView.getTop();
//                    Log.d(TAG, "onScrollStateChanged() viewHolderFirstCompletelyVisibleItemPosition offset:" + offset);
//                }
    }

    @Override
    public String toString() {
        return "OnScrollEvent{" +
                "recyclerView=" + recyclerView +
                ", position=" + position +
                ", offset=" + offset +
                '}';
    }
}
