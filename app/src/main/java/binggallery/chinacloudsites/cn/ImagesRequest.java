package binggallery.chinacloudsites.cn;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A canned request for retrieving the response body at a given URL as a String.
 */
public class ImagesRequest extends Request<Image[]> {
    private final Listener<Image[]> mListener;

    /**
     * Creates a new request with the given method.
     *
     * @param method the request {@link Method} to use
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public ImagesRequest(int method, String url, Listener<Image[]> listener,
                         ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }

    /**
     * Creates a new GET request.
     *
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public ImagesRequest(String url, Listener<Image[]> listener, ErrorListener errorListener) {
        this(Method.GET, url, listener, errorListener);
    }

    @Override
    protected void deliverResponse(Image[] response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<Image[]> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
//        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
        return Response.success(Image.parse(parsed), HttpHeaderParser.parseCacheHeaders(response));
    }
}
