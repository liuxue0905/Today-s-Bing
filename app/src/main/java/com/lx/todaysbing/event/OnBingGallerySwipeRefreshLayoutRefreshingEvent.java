package com.lx.todaysbing.event;

/**
 * Created by liuxue on 2015/6/26.
 */
public class OnBingGallerySwipeRefreshLayoutRefreshingEvent {
    private boolean refreshing = false;

    public OnBingGallerySwipeRefreshLayoutRefreshingEvent(boolean refreshing) {
        this.refreshing = refreshing;
    }

    public boolean isRefreshing() {
        return refreshing;
    }

    @Override
    public String toString() {
        return "OnBingGallerySwipeRefreshLayoutRefreshingEvent{" +
                "refreshing=" + refreshing +
                '}';
    }
}
