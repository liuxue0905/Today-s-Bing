package com.lx.todaysbing.event;

import com.android.volley.VolleyError;

/**
 * Created by liuxue on 2015/6/27.
 */
public class OnBingGalleryListOnErrorResponseEvent {

    public VolleyError error;

    public OnBingGalleryListOnErrorResponseEvent(VolleyError error) {
        this.error = error;
    }
}
