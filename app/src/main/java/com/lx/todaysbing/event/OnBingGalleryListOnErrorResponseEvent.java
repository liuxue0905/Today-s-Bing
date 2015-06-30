package com.lx.todaysbing.event;

import retrofit.RetrofitError;

/**
 * Created by liuxue on 2015/6/27.
 */
public class OnBingGalleryListOnErrorResponseEvent {

//    public VolleyError error;

    public RetrofitError error;

//    public OnBingGalleryListOnErrorResponseEvent(VolleyError error) {
//        this.error = error;
//    }

    public OnBingGalleryListOnErrorResponseEvent(RetrofitError error) {
        this.error = error;
    }
}
