package com.mainli.bezier.view;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
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
import com.mainli.bezier.utils.Bezier2CircleUtil;
import com.mainli.bezier.utils.DrawTextVerticalHelp;

/**
 * Created by Mainli on 2016/12/30.
 */
public class Circle2HeartView extends View {
    private int mWitch;
    private int mHeight;

    private PointF mC;//圆心
    private int r = 100;//半径

    private PointF[] circleTops;//顶点坐标
    private PointF[] circleFlag;//控制点坐标 顺序为顺时针从最上边开始

    private Path mPath;
    private Paint mPatin;
    private Paint mPatinText;


    private float mDuration = 1000;                     // 变化总时长
    private float mCurrent = 0;                         // 当前已进行时长
    private float mCount = 100;                         // 将时长总共划分多少份
    private float mPiece = mDuration / mCount;            // 每一份的时长


    public Circle2HeartView(Context context) {
        this(context, null, 0);
    }

    public Circle2HeartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    ObjectAnimator scale;

    public Circle2HeartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //贝塞尔画笔
        mPatin = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPatin.setColor(Color.RED);
        mPatin.setStyle(Paint.Style.STROKE);
        mPatin.setStrokeWidth(3);
        mPatinText = new Paint();
        mPatinText.setColor(Color.RED);
        mPatinText.setTextSize(50);
        mPatinText.setTextAlign(Paint.Align.CENTER);
        mPath = new Path();
        scale = ObjectAnimator.ofPropertyValuesHolder(this, PropertyValuesHolder.ofFloat("ScaleX", 1, 1.1f), PropertyValuesHolder.ofFloat("ScaleY", 1, 1.1f)).setDuration(500);
        scale.setRepeatMode(ValueAnimator.REVERSE);
        scale.setRepeatCount(ValueAnimator.INFINITE);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWitch = w;
        mHeight = h;
        mC = new PointF(mWitch / 2, mHeight / 2);
        circleTops = Bezier2CircleUtil.obtianTopPoints(mC, r);
        circleFlag = Bezier2CircleUtil.obtianFlagPoints(circleTops, r);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        canvas.drawPath(Bezier2CircleUtil.bezier3ToCircle(mPath, circleTops, circleFlag), mPatin);
        mCurrent += mPiece;
        if (mCurrent < mDuration) {//动画部分
            circleTops[2].offset(0, (130f / 200) * r / mCount);

            float v = (20f / 200) * r / mCount;
            circleTops[1].offset(-v, 0);
            circleTops[3].offset(v, 0);

            float v1 = (60f / 200) * r / mCount;
            circleFlag[0].offset(0, -v1);
            circleFlag[7].offset(0, -v1);

            float v2 = (40f / 200) * r / mCount;
            circleFlag[1].offset(-v2, -v1 / 2);
            circleFlag[6].offset(v2, -v1 / 2);
            postInvalidateDelayed((long) mPiece);
        }
        if (mCurrent >= (mDuration - mPiece)) {//在圆心处画字
            DrawTextVerticalHelp.center(canvas, "心", mC, mPatinText);
            if (!scale.isRunning()) scale.start();
        }

        //辅助绘制
        for (int i = 0; i < circleTops.length; i++) {
            AUXUtil.drawAUXPoint(canvas, "顶点" + i, circleTops[i]);
        }
        for (int i = 0; i < circleFlag.length; i++) {
            AUXUtil.drawAUXPoint(canvas, "控制点" + i, circleFlag[i]);
        }
        AUXUtil.drawAUXPoint(canvas, "圆心", mC);
    }
}
