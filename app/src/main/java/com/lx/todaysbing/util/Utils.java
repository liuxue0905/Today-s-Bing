package com.lx.todaysbing.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.lx.todaysbing.activity.ResolutionActivity;
import com.lx.todaysbing.view.BingImageDetailView;

import java.io.File;
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

//    public static String rebuildImageUrl(Context context, String url) {
//        return rebuildImageUrl(url, getSuggestResolutionStr(context));
//    }

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

    public static boolean hasKitKat() {
        //Android 4.4以上
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static int getStatusBarHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

    private static void setupFakeStatusBarHeight(Activity activity, View fakeStatusBar) {
        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) fakeStatusBar.getLayoutParams();
        if (hasKitKat()) {
            int statusBarHeight = getStatusBarHeight(activity);
            Log.d(TAG, "setupHudLayoutParams() statusBarHeight:" + statusBarHeight);
            params2.height = statusBarHeight;
            fakeStatusBar.setLayoutParams(params2);
        } else {
            params2.height = 0;
            fakeStatusBar.setLayoutParams(params2);
        }
    }

    public static void setupFakeStatusBarHeightOnGlobalLayout(final Activity activity, final View fakeStatusBar) {
//        if (Utils.hasKitKat()) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
////            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        } else {
////            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        }

        fakeStatusBar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {

                Utils.setupFakeStatusBarHeight(activity, fakeStatusBar);

                if (Utils.hasJellyBean()) {
                    fakeStatusBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    fakeStatusBar.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
    }

    public static void deleteExternalStoragePublicPicture(String name) {
        // Create a path where we will place our picture in the user's
        // public pictures directory and delete the file.  If external
        // storage is not currently mounted this will fail.
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File file = new File(path, name);
        file.delete();
    }

    public static boolean hasExternalStoragePublicPicture(String name) {
        // Create a path where we will place our picture in the user's
        // public pictures directory and check if the file exists.  If
        // external storage is not currently mounted this will think the
        // picture doesn't exist.
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File file = new File(path, name);
        return file.exists();
    }

    public static void deleteExternalStoragePrivatePicture(Context context, String name) {
        // Create a path where we will place our picture in the user's
        // public pictures directory and delete the file.  If external
        // storage is not currently mounted this will fail.
        File path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (path != null) {
            File file = new File(path, name);
            file.delete();
        }
    }

    public static boolean hasExternalStoragePrivatePicture(Context context, String name) {
        // Create a path where we will place our picture in the user's
        // public pictures directory and check if the file exists.  If
        // external storage is not currently mounted this will think the
        // picture doesn't exist.
        File path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (path != null) {
            File file = new File(path, name);
            return file.exists();
        }
        return false;
    }

    public static void deleteExternalStoragePrivateFile(Context context, String name) {
        // Get path for the file on external storage.  If external
        // storage is not currently mounted this will fail.
        File file = new File(context.getExternalFilesDir(null), name);
        if (file != null) {
            file.delete();
        }
    }

    public static boolean hasExternalStoragePrivateFile(Context context, String name) {
        // Get path for the file on external storage.  If external
        // storage is not currently mounted this will fail.
        File file = new File(context.getExternalFilesDir(null), name);
        if (file != null) {
            return file.exists();
        }
        return false;
    }

    public static void scanFile(Context context, String path, final MediaScannerConnection.OnScanCompletedListener listener) {
        // Tell the media scanner about the new file so that it is
        // immediately available to the user.
        MediaScannerConnection.scanFile(context,
                new String[]{path}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                        if (listener != null) {
                            listener.onScanCompleted(path, uri);
                        }
                    }
                });
    }

    public static String getColor(Context context) {
        return "#006AC1";
    }

    public static void setColor(Context context) {

    }

    public static String getSubPath(String url) {
        if (url != null && url.length() != 0) {
            return url.substring(url.lastIndexOf('/'));
        }
        return null;
    }
}
