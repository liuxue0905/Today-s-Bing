package com.lx.todaysbing.util;

import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit.Converter;

/**
 * Created by liuxue on 2015/10/18.
 */
public class StringConverterFactory extends Converter.Factory {

    private static final String TAG = StringConverterFactory.class.getCanonicalName();

    public static StringConverterFactory create() {
        Log.d(TAG, "create()");
        return new StringConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
        Log.d(TAG, "fromResponseBody()");
//        return super.fromResponseBody(type, annotations);
        return new StringResponseBodyConverter<>();
    }

    @Override
    public Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotations) {
        Log.d(TAG, "toRequestBody()");
//        return super.toRequestBody(type, annotations);
        return new StringRequestBodyConverter<>();
    }
}
