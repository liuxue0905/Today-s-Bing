package bing.com;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by liuxue on 2015/5/8.
 */
public interface BingAPI {

//    public static final String END_POINT = "http://www.bing.com";
    public static final String END_POINT = "http://global.bing.com";

    //    http://www.bing.com/HPImageArchive.aspx?format=xml&idx=0&n=1&mkt=en-US   &video=1
    @GET("/HPImageArchive.aspx")
    Call<HPImageArchive> getHPImageArchive(@Query("format") String format, @Query("idx") int idx, @Query("n") int n, @Query("mkt") String mkt, @Query("video") int video);
}
