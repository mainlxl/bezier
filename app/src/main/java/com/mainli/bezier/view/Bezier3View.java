package com.mainli.bezier.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Mainli on 2016/12/28.
 */
public class Bezier3View extends View {
    private int mWitch;
    private int mHeight;
    private Path mPath;
    private Paint mPaintBezier;
    private Paint mPaintText;
    private Paint mPaintText2;
    private Paint mPaintLine;
    private Point mFlagPoint1;
    private Point mFlagPoint2;
    private int mRadius = 8;

    public Bezier3View(Context context) {
        this(context, null);
    }

    public Bezier3View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Bezier3View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaintBezier = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setStyle(Paint.Style.STROKE);
        mPaintBezier.setStrokeWidth(3f);
        mPaintBezier.setColor(Color.RED);

        mPaintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintLine.setStyle(Paint.Style.STROKE);
        mPaintLine.setStrokeWidth(1f);

        mPaintText = new Paint();
        mPaintText.setTextSize(16);
        mPaintText2 = new Paint();
        mPaintText2.setTextSize(16);
        mPaintText2.setTextAlign(Paint.Align.RIGHT);
        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWitch = w;
        mHeight = h;
        mFlagPoint1 = new Point(mWitch / 4, mHeight / 2);
        mFlagPoint2 = new Point(mWitch * 3 / 4, mHeight / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(mRadius, mHeight / 2);
        mPath.cubicTo(mFlagPoint1.x, mFlagPoint1.y, mFlagPoint2.x, mFlagPoint2.y, mWitch - mRadius, mHeight / 2);
        canvas.drawPath(mPath, mPaintBezier);

        canvas.drawLine(mRadius, mHeight / 2, mFlagPoint1.x, mFlagPoint1.y, mPaintLine);
        canvas.drawLine(mFlagPoint1.x, mFlagPoint1.y, mFlagPoint2.x, mFlagPoint2.y, mPaintLine);
        canvas.drawLine(mWitch - mRadius, mHeight / 2, mFlagPoint2.x, mFlagPoint2.y, mPaintLine);

        canvas.drawText("起点", 0, mHeight / 2 - mPaintText.ascent() + mRadius, mPaintText);
        canvas.drawText("控制点1", mFlagPoint1.x, mFlagPoint1.y - mRadius, mPaintText);
        canvas.drawText("控制点2", mFlagPoint2.x, mFlagPoint2.y - mRadius, mPaintText);
        canvas.drawText("终点", mWitch, mHeight / 2 - mPaintText.ascent() + mRadius, mPaintText2);

        canvas.drawCircle(mRadius, mHeight / 2, mRadius, mPaintText);
        canvas.drawCircle(mFlagPoint1.x, mFlagPoint1.y, mRadius, mPaintText);
        canvas.drawCircle(mFlagPoint2.x, mFlagPoint2.y, mRadius, mPaintText);
        canvas.drawCircle(mWitch - mRadius, mHeight / 2, mRadius, mPaintText);
    }

    boolean isSecondPoint = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {//检测多点触控
            case MotionEvent.ACTION_POINTER_DOWN:
                isSecondPoint = true;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                isSecondPoint = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (isSecondPoint) {
                    mFlagPoint1.x = (int) event.getX(0);
                    mFlagPoint1.y = (int) event.getY(0);
                    mFlagPoint2.x = (int) event.getX(1);
                    mFlagPoint2.y = (int) event.getY(1);
                }
                invalidate();
                break;
        }
        return true;
    }
}
