package com.mainli.bezier.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

/**
 * Created by Mainli on 2016/12/30.
 * 辅助绘制
 */
public class AUXUtil {
    static Paint mPatinPoint;

    static {
        //黑点+文字画笔
        mPatinPoint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPatinPoint.setColor(Color.BLACK);
        mPatinPoint.setStyle(Paint.Style.FILL);
        mPatinPoint.setTextSize(16);
    }

    public static void drawAUXPoint(Canvas canvas, String name, PointF point) {
        canvas.drawCircle(point.x, point.y, 4, mPatinPoint);
        canvas.drawText(name, point.x, point.y - 6, mPatinPoint);
    }

    public static void drawAUXPoint(Canvas canvas, String name, Point point) {
        canvas.drawCircle(point.x, point.y, 4, mPatinPoint);
        canvas.drawText(name, point.x, point.y - 6, mPatinPoint);
    }
}
