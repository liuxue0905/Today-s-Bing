package com.lx.todaysbing.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.View;

import com.lx.todaysbing.R;

/**
 * Created by liuxue on 2015/10/23.
 */
@Deprecated
public class DrawableCompatUtils {

    public static void setBackground(View view, Drawable background) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(background);
        } else {
            view.setBackgroundDrawable(background);
        }
    }

    public static ColorStateList getColorStateList(String color) {
        if (color == null || color.length() == 0) {
            return null;
        }

        int colorWithAlpha = Color.parseColor(color);
        int[][] states = new int[][] {
                new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled},  // pressed
                new int[] { android.R.attr.state_focused, android.R.attr.state_enabled},  // default
                new int[] { android.R.attr.state_selected, android.R.attr.state_enabled},  // default
                new int[] {}
        };

        int[] colors = new int[] {
                colorWithAlpha,
                colorWithAlpha,
                colorWithAlpha,
                /*Color.parseColor("#00000000")*/colorWithAlpha
        };

        ColorStateList csl = new ColorStateList(states, colors);

        return csl;
    }

    public static ColorStateList genColorStateList(String color) {
        if (color == null || color.length() == 0) {
            return null;
        }

        int colorWithAlpha = ColorUtils.setAlphaComponent(Color.parseColor(color), 31);
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled},  // pressed
                new int[]{android.R.attr.state_focused, android.R.attr.state_enabled},  // default
                new int[]{android.R.attr.state_selected, android.R.attr.state_enabled},  // default
                new int[]{}
        };

        int[] colors = new int[]{
                colorWithAlpha,
                colorWithAlpha,
                colorWithAlpha,
                Color.parseColor("#00000000")
        };

        ColorStateList csl = new ColorStateList(states, colors);

        return csl;
    }

    public static Drawable selectableItemBackground(Context context, String color) {
        return setTintList(context, R.drawable.item_background_material, color);
    }

    public static Drawable setTintList(Context context, int layerDrawableResId, String color) {
        LayerDrawable bg = (LayerDrawable) ResourcesCompat.getDrawable(context.getResources(), layerDrawableResId, null);
        return setTintList(context, bg, color);
    }

    public static Drawable setTintList(Context context, Drawable background, String color) {
        ColorStateList csl = genColorStateList(color);

        LayerDrawable bg = (LayerDrawable) background;
        Log.d("LX", "bg.getNumberOfLayers():" + bg.getNumberOfLayers());
        Drawable bgWrap = DrawableCompat.wrap(bg.getDrawable(0));
        DrawableCompat.setTintMode(bgWrap, PorterDuff.Mode.SRC_IN);
        DrawableCompat.setTintList(bgWrap, csl);
        return bg;
    }
}
