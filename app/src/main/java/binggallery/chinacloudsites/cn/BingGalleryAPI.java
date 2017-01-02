package binggallery.chinacloudsites.cn;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by liuxue on 2015/6/30.
 */
public interface BingGalleryAPI {

    String END_POINT = "http://binggallery.chinacloudsites.cn/api/image/";

    @GET("list")
    Call<String> list();
}
