package com.lx.todaysbing.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.lx.todaysbing.R;

public class FitRatioImageView extends ImageView {
    //默认是正方形
    private float activeType = 1.0f;

    public FitRatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.FitRatioImageView);

        activeType = a.getFloat(R.styleable.FitRatioImageView_widthToheight,
                1.0f);
        a.recycle();
    }

    public FitRatioImageView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);

        int width = measureWidth(widthMeasureSpec, heightMeasureSpec);
        int height = measureHeight(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureWidth(int widthMeasureSpec, int heightMeasureSpec) {
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        int result = 0;
        // 表示我们使用了fill_parent
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        // 表示我们使用了wrap_parent
        else if (specMode == MeasureSpec.AT_MOST || specMode == MeasureSpec.UNSPECIFIED) {
            int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
            //如高是fill_parent
            if (heightSpecMode == MeasureSpec.EXACTLY) {
                //将高设置为宽的一半
                result = (int) (MeasureSpec.getSize(heightMeasureSpec) * activeType);
            } else {
                result = specSize;
            }
        }

        return result;
    }

    private int measureHeight(int widthMeasureSpec, int heightMeasureSpec) {

        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);

        int result = 0;
        // 表示我们使用了fill_parent
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        // 表示我们使用了wrap_parent
        else if (specMode == MeasureSpec.AT_MOST || specMode == MeasureSpec.UNSPECIFIED) {
            int withSpecMode = MeasureSpec.getMode(widthMeasureSpec);
            //如宽是fill_parent
            if (withSpecMode == MeasureSpec.EXACTLY) {
                //将高设置为宽的一半
                result = (int) (MeasureSpec.getSize(widthMeasureSpec) * activeType);
            } else {
                result = specSize;
            }
        }
        return result;
    }

}
