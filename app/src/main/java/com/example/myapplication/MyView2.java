package com.example.myapplication;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import androidx.annotation.Nullable;


import com.freedom.lauzy.playpauseviewlib.R.styleable;

public class MyView2 extends View {
    private int mWidth;
    private int mHeight;
    private Paint mPaint;
    private Path mLeftPath;
    private Path mRightPath;
    private float mGapWidth;
    private float mProgress;
    private Rect mRect;
    private boolean isPlaying;
    private float mRectWidth;
    private float mRectHeight;
    private float mRectLT;
    private float mRadius;
    private int mBgColor = 77;
    private int mBtnColor = 77;
    private int mDirection;
    private float mPadding;
    private int mAnimDuration;


    private MyView2.PlayPauseListener mPlayPauseListener;
    public MyView2(Context context) {
        super(context);
        this.mDirection = MyView2.Direction.POSITIVE.value;
        this.mAnimDuration = 200;
    }

    public MyView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mDirection = MyView2.Direction.POSITIVE.value;
        this.mAnimDuration = 200;
        this.init(context, attrs);
    }

    public MyView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mDirection = MyView2.Direction.POSITIVE.value;
        this.mAnimDuration = 200;
        this.init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mLeftPath = new Path();
        this.mRightPath = new Path();
        this.mRect = new Rect();
        TypedArray ta = context.obtainStyledAttributes(attrs, styleable.PlayPauseView);
        this.mBgColor = ta.getColor(styleable.PlayPauseView_bg_color, 77);
        this.mBtnColor = ta.getColor(styleable.PlayPauseView_btn_color, 77);
        this.mGapWidth = (float)ta.getDimensionPixelSize(styleable.PlayPauseView_gap_width, this.dp2px(context, 0.0F));
        this.mPadding = (float)ta.getDimensionPixelSize(styleable.PlayPauseView_space_padding, this.dp2px(context, 0.0F));
        this.mDirection = ta.getInt(styleable.PlayPauseView_anim_direction, MyView2.Direction.POSITIVE.value);
        this.mAnimDuration = ta.getInt(styleable.PlayPauseView_anim_duration, 200);
        ta.recycle();
        this.setLayerType(1, (Paint)null);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mWidth = MeasureSpec.getSize(widthMeasureSpec);
        this.mHeight = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == 1073741824) {
            this.mWidth = Math.min(this.mWidth, this.mHeight);
        } else {
            this.mWidth = this.dp2px(this.getContext(), 50.0F);
        }

        if (heightMode == 1073741824) {
            this.mHeight = Math.min(this.mWidth, this.mHeight);
        } else {
            this.mHeight = this.dp2px(this.getContext(), 50.0F);
        }

        this.mWidth = this.mHeight = Math.min(this.mWidth, this.mHeight);
        this.setMeasuredDimension(this.mWidth, this.mHeight);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = this.mHeight = w;
        this.initValue();
    }

    private void initValue() {
        this.mRadius = (float)(this.mWidth / 2);
        this.mPadding = this.getSpacePadding() == 0.0F ? this.mRadius / 3.0F : this.getSpacePadding();
        if ((double)this.getSpacePadding() > (double)this.mRadius / Math.sqrt(2.0D) || this.mPadding < 0.0F) {
            this.mPadding = this.mRadius / 3.0F;
        }

        float space = (float)((double)this.mRadius / Math.sqrt(2.0D) - (double)this.mPadding);
        this.mRectLT = this.mRadius - space;
        float rectRB = this.mRadius + space;
        this.mRect.top = (int)this.mRectLT;
        this.mRect.bottom = (int)rectRB;
        this.mRect.left = (int)this.mRectLT;
        this.mRect.right = (int)rectRB;
        this.mRectWidth = 2.0F * space;
        this.mRectHeight = 2.0F * space;
        this.mGapWidth = this.getGapWidth() != 0.0F ? this.getGapWidth() : this.mRectWidth / 3.0F;
        this.mProgress = this.isPlaying ? 0.0F : 1.0F;
        this.mAnimDuration = this.getAnimDuration() < 0 ? 200 : this.getAnimDuration();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mLeftPath.rewind();
        this.mRightPath.rewind();
        this.mPaint.setColor(this.mBgColor);
        canvas.drawCircle((float)(this.mWidth / 2), (float)(this.mHeight / 2), this.mRadius, this.mPaint);
        float distance = this.mGapWidth * (1.0F - this.mProgress);
        float barWidth = this.mRectWidth / 2.0F - distance / 2.0F;
        float leftLeftTop = barWidth * this.mProgress;
        float rightLeftTop = barWidth + distance;
        float rightRightTop = 2.0F * barWidth + distance;
        float rightRightBottom = rightRightTop - barWidth * this.mProgress;
        this.mPaint.setColor(this.mBtnColor);
        this.mPaint.setStyle(Style.FILL);
        if (this.mDirection == MyView2.Direction.NEGATIVE.value) {
            this.mLeftPath.moveTo(this.mRectLT, this.mRectLT);
            this.mLeftPath.lineTo(leftLeftTop + this.mRectLT, this.mRectHeight + this.mRectLT);
            this.mLeftPath.lineTo(barWidth + this.mRectLT, this.mRectHeight + this.mRectLT);
            this.mLeftPath.lineTo(barWidth + this.mRectLT, this.mRectLT);
            this.mLeftPath.close();
            this.mRightPath.moveTo(rightLeftTop + this.mRectLT, this.mRectLT);
            this.mRightPath.lineTo(rightLeftTop + this.mRectLT, this.mRectHeight + this.mRectLT);
            this.mRightPath.lineTo(rightRightBottom + this.mRectLT, this.mRectHeight + this.mRectLT);
            this.mRightPath.lineTo(rightRightTop + this.mRectLT, this.mRectLT);
            this.mRightPath.close();
        } else {
            this.mLeftPath.moveTo(leftLeftTop + this.mRectLT, this.mRectLT);
            this.mLeftPath.lineTo(this.mRectLT, this.mRectHeight + this.mRectLT);
            this.mLeftPath.lineTo(barWidth + this.mRectLT, this.mRectHeight + this.mRectLT);
            this.mLeftPath.lineTo(barWidth + this.mRectLT, this.mRectLT);
            this.mLeftPath.close();
            this.mRightPath.moveTo(rightLeftTop + this.mRectLT, this.mRectLT);
            this.mRightPath.lineTo(rightLeftTop + this.mRectLT, this.mRectHeight + this.mRectLT);
            this.mRightPath.lineTo(rightLeftTop + this.mRectLT + barWidth, this.mRectHeight + this.mRectLT);
            this.mRightPath.lineTo(rightRightBottom + this.mRectLT, this.mRectLT);
            this.mRightPath.close();
        }

        canvas.save();
        canvas.translate(this.mRectHeight / 8.0F * this.mProgress, 0.0F);
        float progress = this.isPlaying ? 1.0F - this.mProgress : this.mProgress;
        int corner = this.mDirection == MyView2.Direction.NEGATIVE.value ? -90 : 90;
        float rotation = this.isPlaying ? (float)corner * (1.0F + progress) : (float)corner * progress;
        canvas.rotate(rotation, (float)this.mWidth / 2.0F, (float)this.mHeight / 2.0F);
        canvas.drawPath(this.mLeftPath, this.mPaint);
        canvas.drawPath(this.mRightPath, this.mPaint);
        canvas.restore();
    }

    public ValueAnimator getPlayPauseAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(new float[]{this.isPlaying ? 1.0F : 0.0F, this.isPlaying ? 0.0F : 1.0F});
        valueAnimator.setDuration((long)this.mAnimDuration);
        valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                MyView2.this.mProgress = (Float)animation.getAnimatedValue();
                MyView2.this.invalidate();
            }
        });
        return valueAnimator;
    }

    public void play() {
        if (this.getPlayPauseAnim() != null) {
            this.getPlayPauseAnim().cancel();
        }

        this.setPlaying(true);
        this.getPlayPauseAnim().start();
    }

    public void pause() {
        if (this.getPlayPauseAnim() != null) {
            this.getPlayPauseAnim().cancel();
        }

        this.setPlaying(false);
        this.getPlayPauseAnim().start();
    }

    public void setPlayPauseListener(MyView2.PlayPauseListener playPauseListener) {
        this.mPlayPauseListener = playPauseListener;
        this.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (MyView2.this.isPlaying()) {
                    MyView2.this.pause();
                    if (null != MyView2.this.mPlayPauseListener) {
                        MyView2.this.mPlayPauseListener.pause();
                    }
                } else {
                    MyView2.this.play();
                    if (null != MyView2.this.mPlayPauseListener) {
                        MyView2.this.mPlayPauseListener.play();
                    }
                }

            }
        });
    }

    public int dp2px(Context context, float dpVal) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int)(density * dpVal + 0.5F);
    }

    public boolean isPlaying() {
        return this.isPlaying;
    }

    public void setPlaying(boolean playing) {
        this.isPlaying = playing;
    }

    public void setGapWidth(float gapWidth) {
        this.mGapWidth = gapWidth;
    }

    public float getGapWidth() {
        return this.mGapWidth;
    }

    public int getBgColor() {
        return this.mBgColor;
    }

    public int getBtnColor() {
        return this.mBtnColor;
    }

    public int getDirection() {
        return this.mDirection;
    }

    public void setBgColor(int bgColor) {
        this.mBgColor = bgColor;
    }

    public void setBtnColor(int btnColor) {
        this.mBtnColor = btnColor;
    }

    public void setDirection(MyView2.Direction direction) {
        this.mDirection = direction.value;
    }

    public float getSpacePadding() {
        return this.mPadding;
    }

    public void setSpacePadding(float padding) {
        this.mPadding = padding;
    }

    public int getAnimDuration() {
        return this.mAnimDuration;
    }

    public void setAnimDuration(int animDuration) {
        this.mAnimDuration = animDuration;
    }

    public static enum Direction {
        POSITIVE(1),
        NEGATIVE(2);

        int value;

        private Direction(int value) {
            this.value = value;
        }
    }

    public interface PlayPauseListener {
        void play();

        void pause();
    }

}
