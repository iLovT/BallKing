package com.jzh.ballking.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.ColorInt;


import com.jzh.ballking.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:jzh
 * desc:多类型障碍view
 * Date:2018/06/26 14:58
 * Email:jzh970611@163.com
 */

public class MuildexView {
    public int type = 1;
    public int number = 1;
    public float mRotationDegrees = 0.f;
    public int color = Color.RED;
    public float x, y;
    private Bitmap add_pint;
    public float width;
    /**
     * 外弧宽
     */
    public float strokeWidth;
    /**
     * 装载所有点的集合
     */
    public List<PointF> pointDatas;

    /**
     * @param type             view类型 1代表圆形，2三角形，3正方形，4增加小球
     * @param number           view里面的数字
     * @param mRotationDegrees 旋转角度
     * @param color            颜色
     * @param x                x坐标
     * @param y                y坐标
     * @param context
     */
    public MuildexView(int type, int number, float mRotationDegrees, @ColorInt int color, float x, float y, Context context) {
        this.type = type;
        this.number = number;
        this.mRotationDegrees = mRotationDegrees;
        this.color = color;
        this.x = x;
        this.y = y;
        this.strokeWidth = 15f;
        this.width = 80f;
        pointDatas = new ArrayList<>();
        add_pint = BitmapFactory.decodeResource(context.getResources(), R.drawable.add_point);
    }

    public void logic() {
        y -= width + 20;
    }


    private void initPaint(Paint paint) {
        paint.setColor(color);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
    }

    public void draw(Canvas canvas, Paint paint) {
        initPaint(paint);
        switch (type) {
            case 1://圆形
                float r = (width - strokeWidth) / 2;
                canvas.drawCircle(x + r + strokeWidth / 2, y + r + strokeWidth / 2, r, paint);
                pointDatas.clear();
                pointDatas.add(new PointF(x + r + strokeWidth / 2, y + r + strokeWidth / 2));
                initTextPaint(paint);
                if (number < 10) {
                    canvas.drawText("" + number, x + strokeWidth / 2 + 20, y + strokeWidth / 2 + r + 10, paint);
                } else if (number >= 10 && number < 100) {
                    canvas.drawText("" + number, x + strokeWidth / 2 + 10, y + strokeWidth / 2 + r + 10, paint);
                } else if (number >= 100) {
                    paint.setTextSize(24f);
                    canvas.drawText("" + number, x + strokeWidth / 2 + 10, y + strokeWidth / 2 + r + 10, paint);
                }
                break;
            case 2://三角形
                Path path = new Path();
                float height = (float) Math.sqrt(Math.pow(width, 2) - Math.pow(width / 2, 2));
                /**
                 * 计算绕某点顺时针旋转θ度后的位置
                 * 其中x,y为原坐标点，rx0,ry0为旋转原点，a为逆时针旋转角度
                 *     x0= (x - rx0)*cos(a) - (y - ry0)*sin(a) + rx0 ;
                 *     y0= (x - rx0)*sin(a) + (y - ry0)*cos(a) + ry0 ;
                 */
                float x0 = -(y - (y + height / 2)) * (float) Math.sin(-mRotationDegrees) + x + width / 2;
                float y0 = (y - (y + height / 2)) * (float) Math.cos(-mRotationDegrees) + y + height / 2;
                float x1 = ((x + width) - (x + width / 2)) * (float) Math.cos(-mRotationDegrees) - ((y + height) - (y + height / 2)) * (float) Math.sin(-mRotationDegrees) + x + width / 2;
                float y1 = ((x + width) - (x + width / 2)) * (float) Math.sin(-mRotationDegrees) + ((y + height) - (y + height / 2)) * (float) Math.cos(-mRotationDegrees) + y + height / 2;
                float x2 = (x - (x + width / 2)) * (float) Math.cos(-mRotationDegrees) - ((y + height) - (y + height / 2)) * (float) Math.sin(-mRotationDegrees) + x + width / 2;
                float y2 = (x - (x + width / 2)) * (float) Math.sin(-mRotationDegrees) + ((y + height) - (y + height / 2)) * (float) Math.cos(-mRotationDegrees) + y + height / 2;
                pointDatas.clear();
                pointDatas.add(new PointF(x0, y0));
                pointDatas.add(new PointF(x1, y1));
                pointDatas.add(new PointF(x2, y2));
                path.moveTo(x0, y0);
                path.lineTo(x1, y1);
                path.lineTo(x2, y2);
                path.close();
                canvas.drawPath(path, paint);
                initTextPaint(paint);
                if (number >= 100) {
                    paint.setTextSize(20f);
                }
                canvas.drawText("" + number, x + width / 2 - strokeWidth, y + width / 2 + strokeWidth / 2, paint);
                break;
            case 3://正方形
                Path path1 = new Path();
                float rx0 = (-width / 2) * (float) Math.cos(-mRotationDegrees) - (-width / 2) * (float) Math.sin(-mRotationDegrees) + x + width / 2;
                float ry0 = (-width / 2) * (float) Math.sin(-mRotationDegrees) + (-width / 2) * (float) Math.cos(-mRotationDegrees) + y + width / 2;
                float rx1 = (width / 2) * (float) Math.cos(-mRotationDegrees) - (-width / 2) * (float) Math.sin(-mRotationDegrees) + x + width / 2;
                float ry1 = (width / 2) * (float) Math.sin(-mRotationDegrees) + (-width / 2) * (float) Math.cos(-mRotationDegrees) + y + width / 2;
                float rx2 = (width / 2) * (float) Math.cos(-mRotationDegrees) - (width / 2) * (float) Math.sin(-mRotationDegrees) + x + width / 2;
                float ry2 = (width / 2) * (float) Math.sin(-mRotationDegrees) + (width / 2) * (float) Math.cos(-mRotationDegrees) + y + width / 2;
                float rx3 = (-width / 2) * (float) Math.cos(-mRotationDegrees) - (width / 2) * (float) Math.sin(-mRotationDegrees) + x + width / 2;
                float ry3 = (-width / 2) * (float) Math.sin(-mRotationDegrees) + (width / 2) * (float) Math.cos(-mRotationDegrees) + y + width / 2;
                pointDatas.clear();
                pointDatas.add(new PointF(rx0, ry0));
                pointDatas.add(new PointF(rx1, ry1));
                pointDatas.add(new PointF(rx2, ry2));
                pointDatas.add(new PointF(rx3, ry3));
                path1.moveTo(rx0, ry0);
                path1.lineTo(rx1, ry1);
                path1.lineTo(rx2, ry2);
                path1.lineTo(rx3, ry3);
                path1.close();
                canvas.drawPath(path1, paint);
                initTextPaint(paint);
                if (number >= 100) {
                    paint.setTextSize(24f);
                }
                canvas.drawText("" + number, x + width / 4 + strokeWidth / 2, y + width / 2 + strokeWidth / 2, paint);
                break;
            case 4://增加小球
                canvas.drawBitmap(add_pint, x, y, paint);
                break;
        }
    }

    /**
     * 初始化文字所用paint
     *
     * @param paint
     */
    private void initTextPaint(Paint paint) {
        paint.setAntiAlias(true);
        paint.setStrokeWidth(0.f);
        paint.setTextSize(30f);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
    }
}
