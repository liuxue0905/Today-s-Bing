package com.lx.todaysbing.util;

import android.content.Context;

import com.lx.todaysbing.R;

/**
 * Created by liuxue on 2015/5/13.
 */
public class ResolutionUtils {

//    public static String[] getResolutionList(Context context) {
//        return context.getResources().getStringArray(R.array.resolution);
//    }

    public static String getSuggestResolution(Context context, String currentResolutionStr) {
        Resolution currentResolution = new Resolution(currentResolutionStr);
        String[] resolutionArray = context.getResources().getStringArray(R.array.resolution);

        Resolution suggestResolution = null;
        int abs = Integer.MAX_VALUE;

        for (String resolutionString : resolutionArray) {
            Resolution resolution = new Resolution(resolutionString);

            if (resolution.isPortrait() == currentResolution.isPortrait()
                    && resolution.isLandscape() == resolution.isLandscape()) {

                int tempAbs = Math.abs(resolution.getPixels() - currentResolution.getPixels());

                if (tempAbs < abs) {
                    suggestResolution = resolution;
                    abs = tempAbs;
                }
            }
        }

        return suggestResolution.getResolutionString();
    }

//    public static Resolution getSuggestResolution(Context context, Resolution currentResolution) {
//        Resolution suggestResolution = null;
//        int abs = 0;
//
//        String[] resolutionStringArray = context.getResources().getStringArray(R.array.resolution);
//        for (String resolutionString : resolutionStringArray) {
//            Resolution resolution = new Resolution(resolutionString);
//
//            if (resolution.isPortrait() == currentResolution.isPortrait()
//                    && resolution.isLandscape() == resolution.isLandscape()) {
//
//                int tempAbs = Math.abs(resolution.getPixels() - currentResolution.getPixels());
//
//
//                if (resolution.getPixels() <= currentResolution.getPixels()) {
//                    if (suggestResolution == null) {
//                        suggestResolution = resolution;
//                    }
//                    if (resolution.getPixels() >= suggestResolution.getPixels()) {
//                        suggestResolution = resolution;
//                    }
//                }
//            }
//        }
//
//        return null;
//    }

    public static class Resolution {

        public int width;
        public int height;

        public Resolution(int width, int height) {
            this.width = width;
            this.height = height;
        }

        /**
         * @param resolution width x height
         */
        public Resolution(String resolution) {
            String[] wh = resolution.split("x");
            this.width = Integer.parseInt(wh[0]);
            this.height = Integer.parseInt(wh[1]);
        }

        public static int compare(Resolution a, Resolution b) {
            return (a.getPixels() < b.getPixels()) ? -1 : ((a.getPixels() > b.getPixels()) ? 1 : 0);
        }

        public int getPixels() {
            return width * height;
        }

        public boolean isLandscape() {
            return width > height;
        }

        public boolean isPortrait() {
            return width < height;
        }

        @Override
        public String toString() {
            return "ImageItem{" +
                    "width=" + width +
                    ", height=" + height +
                    '}';
        }

        public String getResolutionString() {
            return width + "x" + height;
        }
    }
}
