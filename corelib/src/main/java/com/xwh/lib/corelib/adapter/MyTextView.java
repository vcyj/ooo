package com.xwh.lib.corelib.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;


import bodyfast.zerofasting.fastic.corelib.R;

@SuppressLint("AppCompatCustomView")
public class MyTextView extends TextView {

    private LinearGradient mLinearGradient;
    private Matrix mGradientMatrix;
    private Paint mPaint;
    private int mViewWidth = 0;
    private int mTranslate = 0;
    boolean streamState = false;

    private boolean mAnimating = true;

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setStreamState(boolean state) {
        streamState = state;
    }

    int Colorl = R.color.text_d4;

    @Override
    public void setTextColor(int color) {
        super.setTextColor(color);
        Colorl = color;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (!streamState) return;
        int[] ints = {0x66000000, 0xff000000, 0x66000000};
        if (mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0) {
                mPaint = getPaint();


                mLinearGradient = new LinearGradient(-mViewWidth, 0, 0, 0,
                        ints,
                        new float[]{0.5f, 0.8f, 1}, Shader.TileMode.CLAMP);
                mPaint.setShader(mLinearGradient);
                mGradientMatrix = new Matrix();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!streamState) return;
        if (mAnimating && mGradientMatrix != null) {
            mTranslate += mViewWidth / 10;
            if (mTranslate > 2 * mViewWidth) {
                mTranslate = -mViewWidth;
            }
            mGradientMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            postInvalidateDelayed(50);
        }
    }

}