package com.fei.circleloadingview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

/**
 * @ClassName: CircleView
 * @Description: java类作用描述
 * @Author: Fei
 * @CreateDate: 2021-01-16 11:57
 * @UpdateUser: 更新者
 * @UpdateDate: 2021-01-16 11:57
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CircleView extends View {

    private int mColor = Color.RED;
    private Paint mPaint;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setColor(mColor);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        canvas.drawCircle(cx, cy, cx, mPaint);
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(@ColorInt int mColor) {
        this.mColor = mColor;
        if (mPaint != null) {
            mPaint.setColor(mColor);
            invalidate();
        }
    }

}
