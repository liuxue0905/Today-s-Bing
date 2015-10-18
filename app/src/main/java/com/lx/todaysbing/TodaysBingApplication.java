package com.lx.todaysbing;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.lx.todaysbing.util.LoggingInterceptor;
import com.lx.todaysbing.util.StringConverterFactory;
import com.squareup.okhttp.OkHttpClient;

import bing.com.BingAPI;
import binggallery.chinacloudsites.cn.BingGalleryAPI;
import binggallery.chinacloudsites.cn.BingGalleryImageDao;
import binggallery.chinacloudsites.cn.BingGalleryImageProvider;
import binggallery.chinacloudsites.cn.DaoMaster;
import binggallery.chinacloudsites.cn.DaoSession;
import cn.sharesdk.framework.ShareSDK;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

//import binggallery.chinacloudsites.cn.ImageConverter;
//import retrofit.RestAdapter;

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

        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new LoggingInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BingAPI.END_POINT).addConverterFactory(GsonConverterFactory.create()).client(client).build();
        mBingAPI = retrofit.create(BingAPI.class);

        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(BingGalleryAPI.END_POINT).addConverterFactory(StringConverterFactory.create()).client(client).build();
        mBingGalleryAPI = retrofit2.create(BingGalleryAPI.class);

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
