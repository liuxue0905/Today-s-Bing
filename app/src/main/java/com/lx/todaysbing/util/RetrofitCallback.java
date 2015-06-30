package com.lx.todaysbing.util;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by liuxue on 2015/6/28.
 */
public abstract class RetrofitCallback<T> implements Callback<T> {
    @Override
    public abstract void success(T o, Response response);

    @Override
    public abstract void failure(RetrofitError error);

    boolean isCanceld = false;
    public void cancel() {
        isCanceld = true;
    }
    public boolean isCanceld() {
        return isCanceld;
    }
}
