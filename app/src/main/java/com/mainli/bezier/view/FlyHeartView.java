package com.mainli.bezier.view;

import android.animation.Animator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;

import com.mainli.bezier.R;
import com.mainli.bezier.utils.BezierUtil;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by mobimagic on 2017/2/27.
 */

public class FlyHeartView extends View implements View.OnClickListener {
    Bitmap bitmap;
    Paint criPaint = null;
    private LinkedList<Heart> data = new LinkedList<Heart>();

    public FlyHeartView(Context context) {
        this(context, null, 0);
    }

    public FlyHeartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlyHeartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        criPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    private Bitmap creatHeart(@ColorInt int color) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawBitmap(bitmap, 0, 0, criPaint);
        canvas.drawColor(color, PorterDuff.Mode.SRC_IN);
        canvas.setBitmap(null);
        return newBitmap;
    }

    private ValueAnimator valueAnimator;
    PointF startPoint;
    PointF endPoint;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        startPoint = new PointF(w / 2, 0);
        endPoint = new PointF(w / 2, h);
        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Heart h : data) {
            canvas.drawBitmap(bitmap, h.getPointF().x - bitmap.getWidth() / 2, h.getPointF().y - bitmap.getHeight(), criPaint);
        }
    }

    @Override
    public void onClick(View v) {
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();
        Random random = new Random();
        final Heart heart = new Heart();
        heart.setBitmap(creatHeart(Color.GREEN));
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new A(new PointF(-w* random.nextFloat(), h * random.nextFloat()), new PointF(w* random.nextFloat()+w, h * random.nextFloat())),endPoint, startPoint).setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF animatedValue = (PointF) animation.getAnimatedValue();
                heart.setPointF(animatedValue);
                invalidate();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {


            }

            @Override
            public void onAnimationEnd(Animator animation) {
                data.remove(heart);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();
        heart.setValueAnimator(valueAnimator);
        data.addLast(heart);
    }

    class Heart {
        private Bitmap mBitmap;
        private PointF mPointF;
        private ValueAnimator mValueAnimator;

        public ValueAnimator getValueAnimator() {
            return mValueAnimator;
        }

        public void setValueAnimator(ValueAnimator valueAnimator) {
            mValueAnimator = valueAnimator;
        }

        public Bitmap getBitmap() {
            return mBitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            mBitmap = bitmap;
        }

        public PointF getPointF() {
            return mPointF;
        }

        public void setPointF(PointF pointF) {
            mPointF = pointF;
        }
    }

    class A implements TypeEvaluator<PointF> {
        private PointF flag1;
        private PointF flag2;

        public A(PointF flag1, PointF flag2) {
            this.flag1 = flag1;
            this.flag2 = flag2;
        }

        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            return BezierUtil.CalculateBezierPointForCubic(fraction, startValue, flag1, flag2, endValue);
        }
    }
}
