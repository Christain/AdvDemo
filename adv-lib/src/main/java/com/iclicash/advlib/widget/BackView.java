package com.iclicash.advlib.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.iclicash.advlib.util.AppUtil;

/**
 * 自定义TitleBar返回键(<)
 */
public class BackView extends View {

    //默认颜色
    private int color = 0xFF757575;
    private Paint mPaint;
    private int mWidth, mHeight;

    public BackView(Context context) {
        super(context);
        init(context);
    }

    public BackView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BackView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(AppUtil.dip2px(context, 1));
    }

    /**
     * 自定义颜色
     *
     * @param color
     */
    public void setColor(int color) {
        this.color = color;
        if (mPaint != null) {
            mPaint.setColor(color);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(mWidth / 3, mHeight / 2, mWidth * 2 / 3, mHeight / 3, mPaint);
        canvas.drawLine(mWidth / 3, mHeight / 2, mWidth * 2 / 3, mHeight * 2 / 3, mPaint);
    }
}
