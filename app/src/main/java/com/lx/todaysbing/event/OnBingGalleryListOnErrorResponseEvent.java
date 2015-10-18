package com.lx.todaysbing.event;

/**
 * Created by liuxue on 2015/6/27.
 */
public class OnBingGalleryListOnErrorResponseEvent {

//    public VolleyError error;

    public String error;

//    public OnBingGalleryListOnErrorResponseEvent(VolleyError error) {
//        this.error = error;
//    }

    public OnBingGalleryListOnErrorResponseEvent(String error) {
        this.error = error;
    }
}
