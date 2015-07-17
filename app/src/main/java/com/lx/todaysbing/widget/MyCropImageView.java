package com.lx.todaysbing.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.cesards.cropimageview.CropImageView;

/**
 * Created by liuxue on 2015/7/17.
 */
public class MyCropImageView extends CropImageView {
    public MyCropImageView(Context context) {
        super(context);
    }

    public MyCropImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCropImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyCropImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected boolean setFrame(int l, int t, int r, int b) {
//        if (!isInEditMode()) {
//            if (getDrawable() != null) {
//                return super.setFrame(l, t, r, b);
//            }
//        } else {
//            if (getDrawable() != null) {
//                return super.setFrame(l, t, r, b);
//            }
//        }
//        return false;
//        if (isInEditMode() || getDrawable() == null) {
//            return false;
//        }
        return super.setFrame(l, t, r, b);
    }
}
