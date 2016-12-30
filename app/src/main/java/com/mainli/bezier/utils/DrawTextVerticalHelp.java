package com.mainli.bezier.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

/**
 * Created by Mainli on 2016/12/30.
 */

public class DrawTextVerticalHelp {
    //--------------------------------绘制顶边对其文字---------------------------------------------------
    public static void top(Canvas canvas, String text, Point point, Paint paint) {
        top(canvas, text, point.x, point.y, paint);
    }

    public static void top(Canvas canvas, String text, PointF point, Paint paint) {
        top(canvas, text, point.x, point.y, paint);
    }

    public static void top(Canvas canvas, String text, float x, float y, Paint paint) {
        canvas.drawText(text, x, y - paint.ascent(), paint);
    }

    //--------------------------------绘制中间对其文字---------------------------------------------------
    public static void center(Canvas canvas, String text, Point point, Paint paint) {
        center(canvas, text, point.x, point.y, paint);
    }

    public static void center(Canvas canvas, String text, PointF point, Paint paint) {
        center(canvas, text, point.x, point.y, paint);
    }

    public static void center(Canvas canvas, String text, float x, float y, Paint paint) {
        canvas.drawText(text, x, y - (paint.ascent() + paint.descent()) / 2, paint);
    }

    //--------------------------------绘制底边对其文字---------------------------------------------------
    public static void bottom(Canvas canvas, String text, Point point, Paint paint) {
        bottom(canvas, text, point.x, point.y, paint);
    }

    public static void bottom(Canvas canvas, String text, PointF point, Paint paint) {
        bottom(canvas, text, point.x, point.y, paint);
    }

    public static void bottom(Canvas canvas, String text, float x, float y, Paint paint) {
        canvas.drawText(text, x, y + paint.descent(), paint);
    }

}
