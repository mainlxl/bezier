package com.mainli.bezier.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by Mainli on 2016/12/28.
 */
public class Bezier3ViewAnimtor extends View implements View.OnClickListener {
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

    public Bezier3ViewAnimtor(Context context) {
        this(context, null);
    }

    public Bezier3ViewAnimtor(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Bezier3ViewAnimtor(Context context, AttributeSet attrs, int defStyleAttr) {
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
        setOnClickListener(this);
    }

    private ValueAnimator mAnimator;
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWitch = w;
        mHeight = h;
        mFlagPoint1 = new Point(mWitch / 4, mHeight / 2);
        mFlagPoint2 = new Point(mWitch * 3 / 4, mHeight / 2);
        mAnimator = new ValueAnimator().ofInt(mHeight / 2,mHeight-8).setDuration(5000);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFlagPoint1.y= (int) animation.getAnimatedValue();
                mFlagPoint2.y=(int) animation.getAnimatedValue();
                invalidate();
            }
        });
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

    @Override
    public void onClick(View v) {
        mAnimator.start();
    }
}
