package com.lx.todaysbing;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import bing.com.BingAPI;
import binggallery.chinacloudsites.cn.BingGalleryAPI;
import binggallery.chinacloudsites.cn.BingGalleryImageDao;
import binggallery.chinacloudsites.cn.BingGalleryImageProvider;
import binggallery.chinacloudsites.cn.DaoMaster;
import binggallery.chinacloudsites.cn.DaoSession;
import binggallery.chinacloudsites.cn.ImageConverter;
import cn.sharesdk.framework.ShareSDK;
import retrofit.RestAdapter;

/**
 * Created by liuxue on 2015/6/23.
 */
public class TodaysBingApplication extends Application {

    private static TodaysBingApplication sInstance;

    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private BingGalleryImageDao bingGalleryImageDao;

    private BingAPI mBingAPI;
    private BingGalleryAPI mBingGalleryAPI;

    public static TodaysBingApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "bing-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        BingGalleryImageProvider.daoSession = daoSession;
        bingGalleryImageDao = daoSession.getBingGalleryImageDao();

        RestAdapter adapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(BingAPI.END_POINT).build();
        mBingAPI = adapter.create(BingAPI.class);

        RestAdapter adapter2 = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(BingGalleryAPI.END_POINT)
                .setConverter(new ImageConverter())
                .build();
        mBingGalleryAPI = adapter2.create(BingGalleryAPI.class);

        ShareSDK.initSDK(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        sInstance = null;
    }

    public BingGalleryImageDao getBingGalleryImageDao() {
        return bingGalleryImageDao;
    }

    public BingAPI getBingAPI() {
        return mBingAPI;
    }

    public BingGalleryAPI getBingGalleryAPI() {
        return mBingGalleryAPI;
    }

}
