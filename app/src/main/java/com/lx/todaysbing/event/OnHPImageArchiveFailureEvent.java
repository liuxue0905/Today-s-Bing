package com.lx.todaysbing.event;

/**
 * Created by liuxue on 2015/6/29.
 */
public class OnHPImageArchiveFailureEvent {

    private final String error;

    public OnHPImageArchiveFailureEvent(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
