package com.lx.todaysbing.util;


import android.util.Log;

import com.squareup.okhttp.RequestBody;

import java.io.IOException;

import retrofit.Converter;

/**
 * Created by liuxue on 2015/10/18.
 */
public class StringRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final String TAG = StringRequestBodyConverter.class.getCanonicalName();

    @Override
    public RequestBody convert(T value) throws IOException {
        Log.d(TAG, "convert()");
        return null;
    }
}
