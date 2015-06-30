package com.lx.todaysbing;

import android.app.Application;

import bing.com.BingAPI;

import binggallery.chinacloudsites.cn.BingGalleryAPI;
import binggallery.chinacloudsites.cn.ImageConverter;
import retrofit.RestAdapter;

/**
 * Created by liuxue on 2015/6/23.
 */
public class TodaysBingApplication extends Application {

    private static TodaysBingApplication sInstance;

    private BingAPI mBingAPI;
    private BingGalleryAPI mBingGalleryAPI;

    public static TodaysBingApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        RestAdapter adapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(BingAPI.END_POINT).build();
        mBingAPI = adapter.create(BingAPI.class);

        RestAdapter adapter2 = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(BingGalleryAPI.END_POINT)
                .setConverter(new ImageConverter())
                .build();
        mBingGalleryAPI = adapter2.create(BingGalleryAPI.class);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        sInstance = null;
    }

    public BingAPI getBingAPI() {
        return mBingAPI;
    }

    public BingGalleryAPI getBingGalleryAPI() {
        return mBingGalleryAPI;
    }
}
