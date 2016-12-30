package com.mainli.bezier.view;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by Mainli on 2016/12/28.
 */
public class WaveView extends View {
    private int mWitch;
    private int mHeight;

    private Path mPath;
    private Paint mPaintBezier;

    private Point mFlagPoint1;
    private Point mFlagPoint2;

    private int mWaveLenght = 100;
    private int mWaveHeight = 25;
    private int mWaveCount;

    private int mWaterHeight = 0;
    private int mWateroffset = 0;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaintBezier = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintBezier.setStrokeWidth(2f);
        mPaintBezier.setColor(0xff6495ED);
        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWitch = w;
        mHeight = h;
        mWaveCount = (int) Math.ceil(w * 1.0f / mWaveLenght);
        mWaterHeight = mHeight / 2;
        mFlagPoint1 = new Point(mWitch / 4, mWaterHeight);
        mFlagPoint2 = new Point(mWitch * 3 / 4, mWaterHeight);
        ValueAnimator wAnim = ValueAnimator.ofInt(0, mWaveLenght);
        wAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mWateroffset = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        wAnim.setRepeatMode(ValueAnimator.RESTART);
        wAnim.setRepeatCount(ValueAnimator.INFINITE);
        wAnim.setDuration(500);
        ValueAnimator hAnim = ValueAnimator.ofInt(mHeight, 0);
        hAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mWaterHeight = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        hAnim.setDuration(10000);
        AnimatorSet anim = new AnimatorSet();
        anim.setInterpolator(new LinearInterpolator());
        anim.play(hAnim).with(wAnim);
        anim.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(0, mWaterHeight);
        for (int i = -1; i < mWaveCount + 1; i++) {
            mPath.quadTo(mWaveLenght / 4 + i * mWaveLenght + mWateroffset, mWaterHeight - mWaveHeight, mWaveLenght / 2 + i * mWaveLenght + mWateroffset, mWaterHeight);
            mPath.quadTo(mWaveLenght * 3 / 4 + i * mWaveLenght + mWateroffset, mWaterHeight + mWaveHeight, mWaveLenght + i * mWaveLenght + mWateroffset, mWaterHeight);
        }
        mPath.lineTo(mWitch, mHeight);
        mPath.lineTo(0, mHeight);
        mPath.close();
        canvas.drawPath(mPath, mPaintBezier);

    }
}
