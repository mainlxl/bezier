package com.mainli.bezier.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.mainli.bezier.utils.Bezier2CircleUtil;

/**
 * Created by Mainli on 2016/12/31.
 * Path路径截取
 */
public class PathAnimView extends View {

    private Path mPath;
    private PathMeasure mPathMeasure;
    private Paint mPaint;
    private float start;
    private float end;

    public PathAnimView(Context context) {
        this(context, null, 0);
    }

    public PathAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.BLUE);

        int r = 100;
        PointF[] tops = Bezier2CircleUtil.obtianTopPoints(new PointF(200, 200), r);
        PointF[] flags = Bezier2CircleUtil.obtianFlagPoints(tops);
        Bezier2CircleUtil.fixHeart(tops, flags);
        Path path = Bezier2CircleUtil.bezier3ToCircle(new Path(), tops, flags);
        mPathMeasure = new PathMeasure(path, true);
        final float length = mPathMeasure.getLength();
        ValueAnimator animator = ValueAnimator.ofFloat(0, length).setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                end = (float) animation.getAnimatedValue();
                start = end - (length / 2 - Math.abs(end - length / 2));
                invalidate();
            }
        });
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.lineTo(0, 0);//解决硬件加速bug 不添加getSegment失效
        mPathMeasure.getSegment(start, end, mPath, true);//截取路径
        canvas.drawPath(mPath, mPaint);
    }
}
