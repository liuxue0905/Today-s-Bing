package com.lx.todaysbing.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lx.todaysbing.R;
import com.lx.todaysbing.model.ImageDetail;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liuxue on 2015/9/27.
 */
public class BingImageDetailCopyInfoView extends LinearLayout {

    @Bind(R.id.tv_copyright_left)
    TextView mCopyRightLeftTV;
    @Bind(R.id.tv_copyright_right)
    TextView mCopyRightRightTV;

    public BingImageDetailCopyInfoView(Context context) {
        super(context);
        init(context);
    }

    public BingImageDetailCopyInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BingImageDetailCopyInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BingImageDetailCopyInfoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.image_detail_copy_info, this);
        ButterKnife.bind(this);

        setUnderLineTextFlag(mCopyRightLeftTV);
    }

    public void bind(ImageDetail imageDetail) {
        mCopyRightLeftTV.setText(imageDetail.copyRightLeft);
        mCopyRightRightTV.setText(imageDetail.copyRightRight);
    }

    public static void setUnderLineTextFlag(TextView textView) {
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
    }
}
