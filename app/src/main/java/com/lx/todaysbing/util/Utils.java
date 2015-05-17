package com.lx.todaysbing.util;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liuxue on 2015/5/10.
 */
public class Utils {

    private static final String TAG = "Utils";

    public static String[] splitCopyRight(String copyright) {
        String[] ret = new String[2];

        if (copyright != null && copyright.length() != 0) {
            Pattern p = Pattern.compile("(.*)\\((\\u00a9 .+)\\)");
            Matcher m = p.matcher(copyright);
            if (m.find()) {
                ret[0] = m.group(1);
                ret[1] = m.group(2);
            }
        }

        return ret;
    }

    public static String getSuggestResolutionStr(Context context) {
        return getSuggestResolution(context).getResolutionString();
    }

    public static ResolutionUtils.Resolution getSuggestResolution(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return ResolutionUtils.getSuggestResolution(context, new ResolutionUtils.Resolution(dm.widthPixels, dm.heightPixels));
    }

    public static String rebuildImageUrl(Context context, String url) {
        return rebuildImageUrl(url, getSuggestResolutionStr(context));
    }

    //[a-zA-z]+://[^\s]*_(\d+x\d+).jpg$
    public static String rebuildImageUrl(String url, String suggestResolutionString) {
        Pattern p = Pattern.compile("(\\d+x\\d+)");
        Matcher m = p.matcher(url);
        if (m.find()) {
            return m.replaceAll(suggestResolutionString);
        }
        return url;
    }

    public static int get90PWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        Log.d(TAG, "get10PercentWidth() dm.widthPixels：" + dm.widthPixels);
        int suggestRightMargin = (int) (dm.widthPixels - dm.widthPixels / 10.0 + 0.5);
        Log.d(TAG, "get10PercentWidth() suggestRightMargin：" + suggestRightMargin);
        return suggestRightMargin;
    }

    public static int[] getScaledDSizeByFixHeight(int dwidth, int dheight, int vwidth, int vheight) {
        float scale = (float) vheight / (float) dheight;

        int dwidthScaled = (int) ((dwidth * scale) + 0.5f);
        int dheightScaled = vheight;

        return new int[]{dwidthScaled, dheightScaled};
    }

    public static boolean hasFroyo() {
        // Can use static final constants like FROYO, declared in later versions
        // of the OS since they are inlined at compile time. This is guaranteed behavior.
        //2.2以上
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    public static boolean hasGingerbread() {
        //2.3以上
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    public static boolean hasHoneycomb() {
        //3.0以上
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean hasHoneycombMR1() {
        //3.1以上
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean hasJellyBean() {
        //4.1以上
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }
}
