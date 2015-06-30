package binggallery.chinacloudsites.cn;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by liuxue on 2015/6/30.
 */
public interface BingGalleryAPI {

    public static final String END_POINT = "http://binggallery.chinacloudsites.cn/api/image";

    @GET("/list")
    Image[] list();

    @GET("/list")
    void list(Callback<Image[]> callback);
}
