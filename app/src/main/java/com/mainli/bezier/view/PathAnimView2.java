package com.mainli.bezier.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import com.mainli.bezier.utils.Bezier2CircleUtil;

/**
 * Created by Mainli on 2017/1/15.
 */

public class PathAnimView2 extends View {
    private Paint mPaint;
    private Path mCirclePath;
    private int mW;
    private int mH;
    private PathMeasure mPathMeasure;
    private DashPathEffect mDashPathEffect;

    public PathAnimView2(Context context) {
        this(context, null, 0);
    }

    public PathAnimView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathAnimView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(2);
        mCirclePath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mW = w;
        mH = h;
        Bezier2CircleUtil.BezierCircle circle = Bezier2CircleUtil.createCircle(new PointF(mW / 2, mH / 2), 100);
        Bezier2CircleUtil.fixHeart(circle);
        Bezier2CircleUtil.bezier3ToCircle(mCirclePath, circle);
        mPathMeasure = new PathMeasure(mCirclePath, true);
        float length = mPathMeasure.getLength();

        final float[] intervals = {length, length};
        ValueAnimator animator = ValueAnimator.ofFloat(0, length).setDuration(1000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPaint.setPathEffect(new DashPathEffect(intervals, (float) animation.getAnimatedValue()));
                invalidate();
            }
        });
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mCirclePath, mPaint);
    }
}
