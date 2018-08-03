package com.jzh.ballking.utils;

import android.graphics.PointF;

/**
 * Author:jzh
 * desc:矢量工具类
 * Date:2018/07/02 16:02
 * Email:jzh970611@163.com
 */

public class VectorUtils {
    public float x, y;

    public VectorUtils(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public VectorUtils copy() {
        return new VectorUtils(this.x, this.y);
    }

    public float length() {
        return (float) Math.sqrt(this.sqrLength());
    }

    public float sqrLength() {
        return this.x * this.x + this.y * this.y;
    }

    public VectorUtils normalize() {
        float inv = 1 / length();
        return new VectorUtils(this.x * inv, this.y * inv);
    }

    public VectorUtils negate() {
        return new VectorUtils(-this.x, -this.y);
    }

    public VectorUtils add(PointF v) {
        return new VectorUtils(this.x + v.x, this.y + v.y);
    }

    public VectorUtils subtract(VectorUtils v) {
        return new VectorUtils(this.x - v.x, this.y - v.y);
    }

    public VectorUtils scale(float n) {
        return new VectorUtils(this.x * n, this.y * n);
    }

    public float dot(VectorUtils v) {
        return this.x * v.x + this.y * v.y;
    }

    public VectorUtils cross() {
        return new VectorUtils(-this.y, -this.x);
    }
}
