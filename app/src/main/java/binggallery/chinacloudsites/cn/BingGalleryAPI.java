package binggallery.chinacloudsites.cn;

import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.http.GET;

/**
 * Created by liuxue on 2015/6/30.
 */
public interface BingGalleryAPI {

    public static final String END_POINT = "http://binggallery.chinacloudsites.cn/api/image";

    @GET("/list")
    Call<String> list();
}
