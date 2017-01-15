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

import com.mainli.bezier.utils.AUXUtil;

/**
 * Created by Mainli on 2016/12/31.
 * 抛物线
 */
public class ParabolaView2 extends View implements View.OnClickListener {
    private PointF mStartPoint;
    private PointF mEndPoint;
    private PointF mFlagPoint;
    private float xRatio = 0.8f;
    private float yRatio = 0.2f;

    private PointF mBallPoint;

    private Path mPath;
    private Paint mPaint;

    private PathMeasure mPathMeasure;
    private float mLength;
    private float[] mPos = new float[2];

    public ParabolaView2(Context context) {
        this(context, null, 0);
    }

    public ParabolaView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParabolaView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mStartPoint = new PointF(100, 100);
        mEndPoint = new PointF(900, 1600);
        mFlagPoint = new PointF(mStartPoint.x + (mEndPoint.x - mStartPoint.x) * xRatio, mStartPoint.y + (mEndPoint.y - mStartPoint.y) * yRatio);
        mBallPoint = new PointF(mStartPoint.x, mStartPoint.y);
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.BLACK);
        setOnClickListener(this);
        mPath.moveTo(mStartPoint.x, mStartPoint.y);
        mPath.quadTo(mFlagPoint.x, mFlagPoint.y, mEndPoint.x, mEndPoint.y);
        mPathMeasure = new PathMeasure(mPath, false);
        mLength = mPathMeasure.getLength();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
        AUXUtil.drawAUXPoint(canvas, "小球", mBallPoint);
    }

    @Override
    public void onClick(View v) {
        System.out.println(mLength);
        ValueAnimator animator = ValueAnimator.ofFloat(0, mLength).setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                mPathMeasure.getPosTan(animatedValue, mPos, null);
                mBallPoint.set(mPos[0], mPos[1]);
                invalidate();
            }
        });
        animator.start();
    }
}
