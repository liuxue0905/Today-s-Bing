package com.lx.todaysbing.event;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by liuxue on 2015/5/30.
 */
public class OnBingGalleryScrollEvent {

    private static final String TAG = "OnBNDayScrollEvent";

    public RecyclerView recyclerView;

    public int position;
    public int offset;

    public OnBingGalleryScrollEvent(RecyclerView view) {
        this.recyclerView = view;
        refresh();
    }

    public void refresh() {
        if (recyclerView == null) {
            return;
        }
        GridLayoutManager layoutManager = ((GridLayoutManager) recyclerView.getLayoutManager());

        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        position = firstVisibleItemPosition;
        RecyclerView.ViewHolder viewHolderFirstVisibleItemPosition = recyclerView.findViewHolderForAdapterPosition(firstVisibleItemPosition);
//        Log.d(TAG, "OnBingImageNDayScrollEvent() firstVisibleItemPosition:" + firstVisibleItemPosition);
        if (viewHolderFirstVisibleItemPosition != null && viewHolderFirstVisibleItemPosition.itemView != null) {
            int top = viewHolderFirstVisibleItemPosition.itemView.getTop();
//            Log.d(TAG, "OnBingImageNDayScrollEvent() viewHolderFirstVisibleItemPosition top:" + top);
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
        return "OnBingImageNDayScrollEvent{" +
                "recyclerView=" + recyclerView +
                ", position=" + position +
                ", offset=" + offset +
                '}';
    }

    public static void scrollToPositionWithOffset(OnBingGalleryScrollEvent event, RecyclerView recyclerView) {
        if (event == null) {
            return;
        }
        if (event.recyclerView != recyclerView) {
            GridLayoutManager layoutManager = ((GridLayoutManager) recyclerView.getLayoutManager());
            layoutManager.scrollToPositionWithOffset(event.position, event.offset);
        }
    }
}
