package com.lx.todaysbing;

import android.app.Application;

import bing.com.BingAPI;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

//import cn.sharesdk.framework.ShareSDK;

/**
 * Created by liuxue on 2015/6/23.
 */
public class TodaysBingApplication extends Application {

    private static TodaysBingApplication sInstance;

    /**
     * A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher.
     */
    public static final boolean ENCRYPTED = false;

    private BingAPI mBingAPI;

    public static TodaysBingApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

//        ShareSDK.initSDK(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        sInstance = null;
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

}
