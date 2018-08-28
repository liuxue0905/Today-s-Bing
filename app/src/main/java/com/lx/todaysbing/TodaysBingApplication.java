package com.lx.todaysbing;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

import bing.com.BingAPI;
import binggallery.chinacloudsites.cn.BingGalleryAPI;
import binggallery.chinacloudsites.cn.BingGalleryImageDao;
import binggallery.chinacloudsites.cn.BingGalleryImageProvider;
import binggallery.chinacloudsites.cn.DaoMaster;
import binggallery.chinacloudsites.cn.DaoSession;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

//import cn.sharesdk.framework.ShareSDK;

//import binggallery.chinacloudsites.cn.ImageConverter;
//import retrofit.RestAdapter;

/**
 * Created by liuxue on 2015/6/23.
 */
public class TodaysBingApplication extends Application {

    private static TodaysBingApplication sInstance;

    /**
     * A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher.
     */
    public static final boolean ENCRYPTED = false;

    private DaoSession daoSession;

    private BingAPI mBingAPI;
    private BingGalleryAPI mBingGalleryAPI;

    public static TodaysBingApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // greendao
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "bing-db-encrypted" : "bing-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

        BingGalleryImageProvider.daoSession = daoSession;

        sInstance = this;

//        ShareSDK.initSDK(this);
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        sInstance = null;
    }

    public BingGalleryImageDao getBingGalleryImageDao() {
        return daoSession.getBingGalleryImageDao();
    }

    public BingAPI getBingAPI() {
        if (mBingAPI == null) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // add your other interceptors

            // add logging as last interceptor
            httpClient.addInterceptor(logging);  // <-- this is the important line!

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BingAPI.END_POINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            mBingAPI = retrofit.create(BingAPI.class);
        }

        return mBingAPI;
    }

    public BingGalleryAPI getBingGalleryAPI() {

        if (mBingGalleryAPI == null) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // add your other interceptors

            // add logging as last interceptor
            httpClient.addInterceptor(logging);  // <-- this is the important line!

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BingGalleryAPI.END_POINT)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            mBingGalleryAPI = retrofit.create(BingGalleryAPI.class);
        }

        return mBingGalleryAPI;
    }

}
