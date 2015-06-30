package com.lx.todaysbing.event;

import retrofit.RetrofitError;

/**
 * Created by liuxue on 2015/6/29.
 */
public class OnHPImageArchiveFailureEvent {

    private final RetrofitError error;

    public OnHPImageArchiveFailureEvent(RetrofitError error) {
        this.error = error;
    }

    public RetrofitError getError() {
        return error;
    }
}
