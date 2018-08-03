package com.jzh.ballking.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import com.jzh.ballking.ui.game.MySurfaceView;
import com.jzh.ballking.utils.VectorUtils;


/**
 * Author:jzh
 * desc:弹珠类
 * Date:2018/06/25 16:33
 * Email:jzh970611@163.com
 */

public class MyMarbles {
    public float x, y, mRotationDegrees;
    public int speed;
    public float r;
    public double vx, vy;

    public MyMarbles(float mRotationDegress, float x, float y) {
        speed = 40;
        this.x = x;
        this.y = y;
        this.r = 15f;
        this.mRotationDegrees = (float) ((90 + mRotationDegress) / 180 * Math.PI);
        vx = speed * Math.cos(mRotationDegrees);
        vy = speed * Math.sin(mRotationDegrees);
    }

    public void draw(Canvas canvas, Paint paint) {
        initPaint(paint);
        canvas.drawCircle(x, y, r, paint);
        logic();
    }


    private void logic() {
        /**
         * 碰壁检测
         */
        vx = x + vx < r + 20 || x + vx > MySurfaceView.screen_W - r - 20 ? -vx : vx;
        vy = y + vy < r + 100 ? -vy : vy;
        x += vx;
        y += vy;
//        mRotationDegrees = (float) Math.atan2(vy, vx);
    }

    /**
     * 是否与障碍物碰撞
     *
     * @param muildexView
     */
    public boolean isCollionWithObstacle(MuildexView muildexView) {
        if (muildexView.type == 1) {
            float x1 = muildexView.x + (muildexView.width - muildexView.strokeWidth) / 2 + muildexView.strokeWidth / 2;
            float y1 = muildexView.y + (muildexView.width - muildexView.strokeWidth) / 2 + muildexView.strokeWidth / 2;
            PointF normal = new PointF(x - x1, y - y1); //小球球心 -> 障碍球心 的 连线 === 以下称之为 法向量
            VectorUtils normal0 = new VectorUtils(normal.x, normal.y).normalize(); //法向量的单位向量 === 以下称之为单位向量
            if (normal.length() <= (r + (muildexView.width - muildexView.strokeWidth) / 2)) {
                //速度在 法向量 上的投影向量 === 以下称之为 分向量Va，反弹后的速度 v1 = v0 - 2Va
                VectorUtils vectorUtils = new VectorUtils((float) vx, (float) vy);
                vectorUtils = vectorUtils.subtract(normal0.scale(2 * normal0.dot(vectorUtils)));
                vx = vectorUtils.x;
                vy = vectorUtils.y;
                x += vx;
                y += vy;
                return true;
            }
        } else if (muildexView.type == 2 || muildexView.type == 3) {
            for (int i = 0; i < muildexView.pointDatas.size(); ++i) {
                boolean b = isCollisionWithLine(muildexView.pointDatas.get(i), muildexView.pointDatas.get((i + 1) == muildexView.pointDatas.size() ? 0 : (i + 1)));
                if (b)
                    return true;
            }
        }
        return false;
    }

    /**
     * 是否与线碰撞
     *
     * @param o
     * @param o2
     */
    private boolean isCollisionWithLine(PointF o, PointF o2) {
        //已知 过定点O(x0, y0)的向量vec2(x1, y1)的直线方程为 Ax + By + C = 0;其中A = y1, B = -x1, C = x1*y0 - x0*y1
        VectorUtils vec2 = new VectorUtils(o.x - o2.x, o.y - o2.y);
        float a = vec2.y,
                b = -vec2.x,
                c = vec2.x * o.y - o.x * vec2.y,
                d = (float) Math.abs((a * x + b * y + c) / Math.sqrt(a * a + b * b)); //点(x, y)到直线  Ax + By + C = 0 的距离 d = |Ax + By + C|/sqrt(A² + B²)
        if (d <= r) { //小于等于球的半径时：判断是否与 线段(向量) 相交于 点p
            VectorUtils p = null;
            boolean is = false;
            if (o.x == o2.x) { // x = o.x
                p = new VectorUtils(o.x, y);
                if (p.y >= (o.y < o2.y ? o.y : o2.y) && p.y <= (o.y > o2.y ? o.y : o2.y)) is = true;
            } else if (o.y == o2.y) { // y = o.y
                p = new VectorUtils(x, o.y);
                if (p.x >= (o.x < o2.x ? o.x : o2.x) && p.x <= (o.x > o2.x ? o.x : o2.x)) is = true;
            } else {
                p = new VectorUtils((b * b * x - a * b * y - a * c) / (a * a + b * b), (a * a * y - a * b * x - b * c) / (a * a + b * b));
                if (p.x >= (o.x < o2.x ? o.x : o2.x) && p.x <= (o.x > o2.x ? o.x : o2.x)) is = true;
            }
            if (is) {
                VectorUtils vectorUtils = new VectorUtils((float) vx, (float) vy);
                VectorUtils p0 = new VectorUtils(x, y).subtract(p).normalize(); //球心到交点的向量 的 单位向量
                vectorUtils = vectorUtils.subtract(p0.scale(2 * p0.dot(vectorUtils)));
                vx = vectorUtils.x;
                vy = vectorUtils.y;
                x += vx;
                y += vy;
                return true;
            }
        }
        return false;
    }

    /**
     * 判断小球是否与加号碰撞
     *
     * @param muildexView
     * @return
     */
    public boolean isCollionAddPoint(MuildexView muildexView) {
        if (muildexView.type == 4 && x >= muildexView.x && x <= muildexView.x + 120 && y > muildexView.y && y < muildexView.y + 120)
            return true;
        return false;
    }


    private void initPaint(Paint paint) {
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
    }
}
