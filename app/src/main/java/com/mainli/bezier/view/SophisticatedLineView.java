package com.mainli.bezier.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Mainli on 2016/12/28.
 */
public class SophisticatedLineView extends View {
    public SophisticatedLineView(Context context) {
        this(context, null, 0);
    }

    public SophisticatedLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SophisticatedLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Paint mPaint;
    private Path mPath;

    public void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPath = new Path();
        mPath.reset();
        mLastPoint = new PointF(0, 0);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
    }
    private PointF mLastPoint;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(event.getX(), event.getY());
                mLastPoint.set(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                mPath.quadTo(mLastPoint.x,mLastPoint.y,(x + mLastPoint.x) / 2, (y + mLastPoint.y) / 2);
                mLastPoint.set(event.getX(), event.getY());
                invalidate();
                break;
        }
        return true;
    }
}

