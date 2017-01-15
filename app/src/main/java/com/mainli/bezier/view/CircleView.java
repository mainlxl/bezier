package com.mainli.bezier.view;

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

/**
 * Created by Mainli on 2016/12/30.
 */

public class CircleView extends View {
    private int mWitch;
    private int mHeight;

    private PointF mC;//圆心
    private int r = 200;//半径

    private PointF[] circleTops;//顶点坐标
    private PointF[] circleFlag;//控制点坐标 顺序为顺时针从最上边开始

    private Path mPath;
    private Paint mPatin;
    private Paint mPatinPoint;

    private Paint mPaintBlack;

    public CircleView(Context context) {
        this(context, null, 0);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //贝塞尔画笔
        mPatin = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPatin.setColor(Color.RED);
        mPatin.setStyle(Paint.Style.STROKE);
        mPatin.setStrokeWidth(10);
        //黑点+文字画笔
        mPatinPoint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPatinPoint.setColor(Color.BLACK);
        mPatinPoint.setStyle(Paint.Style.FILL);
        mPatinPoint.setTextSize(16);
        //验证圆画笔
        mPaintBlack = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBlack.setColor(0x88000000);
        mPaintBlack.setStyle(Paint.Style.STROKE);
        mPaintBlack.setStrokeWidth(10);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWitch = w;
        mHeight = h;
        mC = new PointF(mWitch / 2, mHeight / 2);
        circleTops = Bezier2CircleUtil.obtianTopPoints(mC, r);
        circleFlag = Bezier2CircleUtil.obtianFlagPoints(circleTops);
        mPath = Bezier2CircleUtil.bezier3ToCircle(new Path(), circleTops, circleFlag);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.scale(1, -1);翻转坐标系
        canvas.drawPath(mPath, mPatin);
        //验证圆绘制
        canvas.drawCircle(mC.x, mC.y, r, mPaintBlack);

        //辅助点绘制
        for (int i = 0; i < circleTops.length; i++) {
            AUXUtil.drawAUXPoint(canvas, "顶点" + i, circleTops[i]);
        }
        for (int i = 0; i < circleFlag.length; i++) {
            AUXUtil.drawAUXPoint(canvas, "控制点" + i, circleFlag[i]);
        }
        AUXUtil.drawAUXPoint(canvas, "圆心", mC);
    }
}
