package com.lx.todaysbing.util;

import android.util.Log;

import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import retrofit.Converter;

/**
 * Created by liuxue on 2015/10/18.
 */
public class StringResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private static final java.lang.String TAG = StringResponseBodyConverter.class.getCanonicalName();

    @Override
    public T convert(ResponseBody value) throws IOException {
        Log.d(TAG, "convert() value.contentLength():" + value.contentLength());
        Log.d(TAG, "convert() value.contentType():" + value.contentType());
        return (T) value.string();
    }
}
