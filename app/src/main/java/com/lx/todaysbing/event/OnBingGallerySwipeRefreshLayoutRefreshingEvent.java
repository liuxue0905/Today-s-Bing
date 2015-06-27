package com.lx.todaysbing.event;

/**
 * Created by liuxue on 2015/6/26.
 */
public class OnBingGallerySwipeRefreshLayoutRefreshingEvent {
    public boolean refreshing = false;

    public OnBingGallerySwipeRefreshLayoutRefreshingEvent(boolean refreshing) {
        this.refreshing = refreshing;
    }

    @Override
    public String toString() {
        return "OnBingGallerySwipeRefreshLayoutRefreshingEvent{" +
                "refreshing=" + refreshing +
                '}';
    }
}
