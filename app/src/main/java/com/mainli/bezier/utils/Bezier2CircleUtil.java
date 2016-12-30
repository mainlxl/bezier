package com.mainli.bezier.utils;

import android.graphics.Path;
import android.graphics.PointF;

/**
 * Created by Mainli on 2016/12/30.
 * 贝塞尔曲线绘制圆
 * 参考:http://stackoverflow.com/questions/1734745/how-to-create-circle-with-b%C3%A9zier-curves
 */
public final class Bezier2CircleUtil {
    public static final float C = 0.551915024494f; // 一个常量，用来计算绘制圆形贝塞尔曲线控制点的位置
//---------------------------------圆顶点计算------------------------------------------------

    /**
     * @param r 半径
     * @return 顶点坐标集合(圆心为(0, 0)顺时针从最顶上开始)
     */
    public static PointF[] obtianTopPoints(float r) {
        return obtianTopPoints(new PointF(0f, 0f), r);
    }

    /**
     * @param point 圆心
     * @param r     半径
     * @return 顶点坐标集合(顺时针从最顶上开始)
     */
    public static PointF[] obtianTopPoints(PointF point, float r) {
        PointF[] circleTops = new PointF[4];//圆的顶点坐标 顺序为顺时针从最上边开始
        circleTops[0] = new PointF(point.x, point.y + r);
        circleTops[1] = new PointF(point.x + r, point.y);
        circleTops[2] = new PointF(point.x, -r + point.y);
        circleTops[3] = new PointF(-r + point.x, point.y);
        return circleTops;
    }
//-----------------------------------控制点计算-----------------------------------------------------

    public static PointF[] obtianFlagPoints(PointF[] tops, int r) {
        float mDifference = r * C;        // 圆形的控制点与数据点的差值
        PointF[] points = new PointF[8];//圆的顶点坐标 顺序为顺时针从最上边开始
        points[0] = new PointF(tops[0].x + mDifference, tops[0].y);//右上控制点
        points[1] = new PointF(tops[1].x, tops[1].y + mDifference);

        points[2] = new PointF(tops[1].x, tops[1].y - mDifference);//右下控制点
        points[3] = new PointF(tops[2].x + mDifference, tops[2].y);

        points[4] = new PointF(tops[2].x - mDifference, tops[2].y);//左下控制点
        points[5] = new PointF(tops[3].x, tops[3].y - mDifference);

        points[6] = new PointF(tops[3].x, tops[3].y + mDifference);//左上控制点
        points[7] = new PointF(tops[0].x - mDifference, tops[0].y);


        return points;
    }
//-----------------------------------路径链接-----------------------------------------------------

    /**
     * @param path       路径
     * @param circleTops 顶点坐标
     * @param circleFlag 控制点坐标
     * @return path 路径
     */
    public static Path bezier3ToCircle(Path path, PointF[] circleTops, PointF[] circleFlag) {
        path.moveTo(circleTops[0].x, circleTops[0].y);
        for (int i = 0; i < 4; i++) {
            path.cubicTo(circleFlag[2 * i].x, circleFlag[2 * i].y, circleFlag[2 * i + 1].x, circleFlag[2 * i + 1].y, circleTops[(i + 1) % 4].x, circleTops[(i + 1) % 4].y);
        }
        return path;
    }
}
