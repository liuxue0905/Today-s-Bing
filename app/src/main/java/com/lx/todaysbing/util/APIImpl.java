package com.lx.todaysbing.util;

import com.lx.todaysbing.model.HPImageArchive;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.Query;

/**
 * Created by liuxue on 2015/5/8.
 */
public class APIImpl implements API {

    private API service;

    public APIImpl() {
        RestAdapter adapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(END_POINT).build();
        service = adapter.create(API.class);
    }

    public HPImageArchive getHPImageArchive(@Query("n") int n, @Query("mkt") String mkt) {
        return getHPImageArchive("js", 0, n, mkt, 1);
    }

    public void getHPImageArchive(@Query("n") int n, @Query("mkt") String mkt, Callback<HPImageArchive> callback) {
        getHPImageArchive("js", 0, n, mkt, 1, callback);
    }

    @Override
    public HPImageArchive getHPImageArchive(@Query("format") String format, @Query("idx") int idx, @Query("n") int n, @Query("mkt") String mkt, @Query("video") int video) {
        return service.getHPImageArchive(format, idx, n, mkt, video);
    }

    @Override
    public void getHPImageArchive(@Query("format") String format, @Query("idx") int idx, @Query("n") int n, @Query("mkt") String mkt, @Query("video") int video, Callback<HPImageArchive> callback) {
        service.getHPImageArchive(format, idx, n, mkt, video, callback);
    }

    boolean isCanceld = false;
    public void cancel() {
        isCanceld = true;
    }
    public boolean isCanceld() {
        return isCanceld;
    }
}
