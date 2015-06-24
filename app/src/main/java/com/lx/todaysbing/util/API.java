package com.lx.todaysbing.util;

import bing.com.HPImageArchive;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by liuxue on 2015/5/8.
 */
public interface API {

//    public static final String END_POINT = "http://www.bing.com";
    public static final String END_POINT = "http://global.bing.com";

    //    http://www.bing.com/HPImageArchive.aspx?format=xml&idx=0&n=1&mkt=en-US   &video=1
    @GET("/HPImageArchive.aspx")
    HPImageArchive getHPImageArchive(@Query("format") String format, @Query("idx") int idx, @Query("n") int n, @Query("mkt") String mkt, @Query("video") int video);

    @GET("/HPImageArchive.aspx")
    void getHPImageArchive(@Query("format") String format, @Query("idx") int idx, @Query("n") int n, @Query("mkt") String mkt, @Query("video") int video, Callback<HPImageArchive> callback);
}
