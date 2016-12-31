package com.mainli.bezier.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import com.mainli.bezier.utils.AUXUtil;
import com.mainli.bezier.utils.BezierUtil;

/**
 * Created by Mainli on 2016/12/31.
 */

public class BezierPathAnimView extends View implements View.OnClickListener {
    private PointF mStartPoint;
    private PointF mEndPoint;
    private PointF mFlagPoint;
    private float xRatio = 0.8f;
    private float yRatio = 0.2f;

    private PointF mBallPoint;

    private Path mPath;
    private Paint mPaint;

    public BezierPathAnimView(Context context) {
        this(context, null, 0);
    }

    public BezierPathAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierPathAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mStartPoint = new PointF(100, 100);
        mEndPoint = new PointF(600, 800);
        mFlagPoint = new PointF(mStartPoint.x + (mEndPoint.x - mStartPoint.x) * xRatio, mStartPoint.y + (mEndPoint.y - mStartPoint.y) * yRatio);
        mBallPoint = new PointF(mStartPoint.x, mStartPoint.y);
        mPath = new Path();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.BLACK);
        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.moveTo(mStartPoint.x,mStartPoint.y);
        mPath.quadTo(mFlagPoint.x,mFlagPoint.y,mEndPoint.x,mEndPoint.y);
        canvas.drawPath(mPath,mPaint);
        AUXUtil.drawAUXPoint(canvas, "小球", mBallPoint);
    }

    @Override
    public void onClick(View v) {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1).setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mBallPoint = BezierUtil.CalculateBezierPointForQuadratic((float) animation.getAnimatedValue(), mStartPoint, mFlagPoint, mEndPoint);
                invalidate();
            }
        });
        animator.start();
    }
}
