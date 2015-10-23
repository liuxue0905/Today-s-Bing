package com.lx.todaysbing.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;

/**
 * Created by liuxue on 2015/10/23.
 */
public class DrawableCompatUtils {

    @SuppressWarnings("deprecation")
    public static void setBackground(View view, Drawable background) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(background);
        } else {
            view.setBackgroundDrawable(background);
        }
    }

    public static Drawable setTintList(Context context, int layerDrawableResId) {
        String primaryColor = "00FF00";
        String primaryColorDark = "00FFFF";

        int[][] states = new int[][] {
                new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled},  // pressed
                new int[] { android.R.attr.state_focused, android.R.attr.state_enabled},  // default
                new int[] { android.R.attr.state_selected, android.R.attr.state_enabled},  // default
                new int[] {}
        };

        int[] colors = new int[] {
                Color.parseColor(String.format("#11%s", primaryColorDark)),
                Color.parseColor(String.format("#11%s", primaryColor)),
                Color.parseColor(String.format("#11%s", primaryColor)),
                Color.parseColor("#00000000")
        };

        ColorStateList csl = new ColorStateList(states, colors);

        LayerDrawable bg = (LayerDrawable) ResourcesCompat.getDrawable(context.getResources(), layerDrawableResId, null);
        Drawable bgWrap = DrawableCompat.wrap(bg.getDrawable(0));
//        DrawableCompat.setTintMode(bgWrap, PorterDuff.Mode.OVERLAY);
//        DrawableCompat.setTintList(bgWrap, context.getResources().getColorStateList(R.color.selectable_white_item));
        DrawableCompat.setTintList(bgWrap, csl);
//        DrawableCompat.setTintList(bgWrap, new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.RED}));
        return bg;
    }
}
