package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class PlayandPause extends View {


    /**
     * 中心点X轴坐标
     */
    private int viewCenterX;

    /**
     * 中心点Y轴坐标
     */
    private int viewCenterY;

    /**
     * 有效长度的一般（View长宽较小者的一半）
     */
    private int viewHalfLength;

    /**
     * 三角形右侧顶点
     */
    private Point pointA = new Point();

    /**
     * 三角形左上顶点
     */
    private Point pointB = new Point();

    /**
     * 三角形左下顶点
     */
    private Point pointC = new Point();

    /**
     * 矩形左边界
     */
    private int RectLeft;

    /**
     * 矩形上边界
     */
    private int RectTOP;

    /**
     * 矩形右边界
     */
    private int RectRight;

    /**
     * 矩形下边界
     */
    private int RectBottom;

    /**
     * 三角形的三条边路径
     */
    private Path path = new Path();

    /**
     * 包围最外侧圆环的矩形
     */
    private RectF rectF = new RectF();

    /**
     * 包围进度圆弧的矩形
     */
    private RectF rectF2 = new RectF();

    /**
     * 进度
     */
    private int progress;

    /**
     * 暂停中还是播放中
     */
    private boolean isPlaying = false;

    /**
     * 是否进行过了测量
     */
    private boolean isMeasured = false;

    /**
     * 画笔颜色
     */
    private int color = 0xff050F02;
    private int colorgrey=0x4DD7D2D5;

    /**
     * 最外侧圆环画笔
     */
    private Paint paintA = new Paint();

    /**
     * 进度圆弧画笔
     */
    private Paint paintB = new Paint();

    /**
     * 暂停开始画笔
     */
    private Paint paintC = new Paint();

    /**
     * 状态监听器
     */
    private OnStatusChangeListener onStatusChangeListener;

    /**
     * 构造器
     */
    public PlayandPause(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!isMeasured) {
            getWidthAndHeight();
            isMeasured = true;
        }
    }

    /**
     * 得到视图等的高度宽度尺寸数据
     */
    private void getWidthAndHeight() {

        int viewHeight = getMeasuredHeight();
        int viewWidth = getMeasuredWidth();
        viewCenterX = viewWidth / 2;
        viewCenterY = viewHeight / 2;
        viewHalfLength = viewHeight < viewWidth ? viewHeight / 2 : viewWidth / 2;

        int paintAwidth = viewHalfLength / 15;
        int paintBwidth = viewHalfLength / 8;

        rectF.left = viewCenterX - (viewHalfLength - paintAwidth / 2);
        rectF.top = viewCenterY - (viewHalfLength - paintAwidth / 2);
        rectF.right = viewCenterX + (viewHalfLength - paintAwidth / 2);
        rectF.bottom = viewCenterY + (viewHalfLength - paintAwidth / 2);

        rectF2.left = viewCenterX - (viewHalfLength - paintBwidth / 2);
        rectF2.top = viewCenterY - (viewHalfLength - paintBwidth / 2);
        rectF2.right = viewCenterX + (viewHalfLength - paintBwidth / 2);
        rectF2.bottom = viewCenterY + (viewHalfLength - paintBwidth / 2);

        paintA.setColor(colorgrey);
        paintA.setStrokeWidth(paintAwidth);
        paintA.setAntiAlias(true);
        paintA.setStyle(Paint.Style.STROKE);

        paintB.setColor(color);
        paintB.setStrokeWidth(paintBwidth);
        paintB.setAntiAlias(true);
        paintB.setStyle(Paint.Style.STROKE);

        paintC.setColor(color);
        paintC.setStrokeWidth(1);
        paintC.setAntiAlias(true);
        paintC.setStyle(Paint.Style.FILL);

        pointA.x = viewCenterX + viewHalfLength / 2;
        pointA.y = viewCenterY;

        double sin = Math.sin(Math.toRadians(60)); // √(3) / 2
        double cos = Math.cos(Math.toRadians(60)); // 1/ 2

        pointB.x = (float) ((viewCenterX - cos * viewHalfLength + viewCenterX) / 2);
        pointB.y = (float) ((viewCenterY - sin * viewHalfLength + viewCenterY) / 2);

        pointC.x = (float) ((viewCenterX - cos * viewHalfLength + viewCenterX) / 2);
        pointC.y = (float) ((viewCenterY + sin * viewHalfLength + viewCenterY) / 2);

        RectLeft = viewCenterX - viewHalfLength / 3;
        RectTOP = viewCenterY - viewHalfLength / 3;
        RectRight = viewCenterX + viewHalfLength / 3;
        RectBottom = viewCenterY + viewHalfLength / 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 画未完成进度的圆环
        canvas.drawArc(rectF, 0, 360, false, paintA);

        // 画已经完成进度的圆弧 从-90度开始，即从圆环顶部开始
        canvas.drawArc(rectF2, -90, progress * 3.6f, false, paintB);

        if (isPlaying) {
            canvas.drawRect(RectLeft, RectTOP, RectRight, RectBottom, paintC);
        } else {
            path.reset();
            path.moveTo(pointA.x, pointA.y);
            path.lineTo(pointB.x, pointB.y);
            path.lineTo(pointC.x, pointC.y);
            path.close();
            canvas.drawPath(path, paintC);
        }
    }

    /**
     * 监听触摸DOWN时间，开始播放，暂停播放
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            isPlaying = !isPlaying;
            invalidate();
            if (isPlaying) {
                onStatusChangeListener.play();
            } else {
                onStatusChangeListener.pause();
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 设置进度 0-100区间
     */
    public void setProgress(int progress) {

        if (progress < 0) {
            progress = 0;
        }
        if (progress > 100) {
            progress = 100;
        }
        this.progress = progress;
        invalidate();
    }

    /**
     * 外界设置播放状态
     */
    public void setPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
        invalidate();
    }

    /**
     * 播放暂停状态监听的接口
     */
    public interface OnStatusChangeListener {

        void play();

        void pause();
    }

    /**
     * 设置监听接口
     */
    public void setOnStatusChangeListener(OnStatusChangeListener onStatusChangeListener) {
        this.onStatusChangeListener = onStatusChangeListener;
    }

    /**
     * 位置信息
     */
    private class Point {
        float x;
        float y;
    }
}

