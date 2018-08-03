package com.jzh.ballking.ui.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;


import com.jzh.ballking.R;
import com.jzh.ballking.common.Constans;
import com.jzh.ballking.utils.AppLogger;
import com.jzh.ballking.utils.toast.Toasty;
import com.jzh.ballking.widget.MuildexView;
import com.jzh.ballking.widget.MyMarbles;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/**
 * Author:jzh
 * desc:所有view绘画层
 * Date:2018/06/23 11:12
 * Email:jzh970611@163.com
 */

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder mHolder;
    private Canvas canvas;
    private Paint paint;
    /**
     * 屏幕宽高
     */
    public static int screen_H, screen_W;
    /**
     * 是否可以绘画状态符
     */
    private boolean isDrawing;
    /**
     * 背景图
     */
    private Bitmap background;
    /**
     * 箭头图
     */
    private Bitmap game_arrow;
    private String TAG = "TAG";
    /**
     * 旋转角度
     */
    private float mRotationDegrees = -36f;
    /**
     * 发射的中心点xy
     */
    private float sendX, sendY;
    /**
     * 小球个数
     */
    public static int point_accout = 3;
    /**
     * 分数
     */
    private int score = 0;
    /**
     * 刷新屏幕帧数
     **/
    private int TIME_IN_FRAME = 20;
    /**
     * 障碍矢量集合
     */
    private Vector<MuildexView> muildexViews;
    /**
     * 弹球集合
     */
    private Vector<MyMarbles> myMarbles;
    private Context context;
    /**
     * 是否可以移动
     */
    private boolean isCanMove = true;
    /**
     * 多类型行列数
     */
    private int lineCount, columnCount = 2;
    /**
     * 多类型view最小XY坐标
     */
    private float minX, minY;
    /**
     * 是否第一次初始化绘画障碍
     */
    private boolean isFirstDraw = true;
    /**
     * 弹球xy坐标
     */
    private float pointX, pointY;
    /**
     * 弹球定时计数器
     */
    private int accout;
    /**
     * 关卡等级，随着等级的增加，数字也将相应变大
     */
    private int level = 1;

    public MySurfaceView(Context context) {
        super(context);
        this.context = context;
        init();

    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    /**
     * 初始化方法
     */
    private void init() {
        mHolder = this.getHolder();
        mHolder.addCallback(this);//注册holder回调
        initPaint();
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
        initMore();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(0.f);
        paint.setTextSize(30f);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
//        AppLogger.e(TAG, "surfaceCreated");
        screen_H = this.getHeight();
        screen_W = this.getWidth();
        lineCount = (screen_W - 74) / 120;
        minY = screen_H - 150;
        if (isFirstDraw) {
            initNormalMuildexView();
        }
        isDrawing = true;
        sendX = screen_W / 2.0f;
        sendY = 100f + game_arrow.getHeight() / 2.0f;
        pointX = screen_W / 2.0f;
        pointY = 100f + game_arrow.getWidth() / 2;
        new Thread(this).start();
    }


    /**
     * 初始化
     */
    private void initMore() {
        background = BitmapFactory.decodeResource(getResources(), R.drawable.game_background);
        game_arrow = BitmapFactory.decodeResource(getResources(), R.drawable.arrow);
        muildexViews = new Vector<>();
        myMarbles = new Vector<>();
        minX = 100;
    }

    /**
     * 初始化障碍
     */
    private void initNormalMuildexView() {
        Random random = new Random();
        int count = 0;
        for (int i = 0; i < lineCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                if (random.nextInt(20) % 3 == 0) {
                    count++;
                    int type = random.nextInt(3) + 1;
                    /**
                     * 保证控制小球数量，防止小球数量过多
                     */
                    if (point_accout <= 45) {
                        if (random.nextInt(20) % 5 == 0) {
                            type = 4;
                        }
                    }
                    int number = random.nextInt(20 * (level <= 3 ? 1 : level - 2)) + (level <= 4 ? 1 : 20 * level - 5);
                    int degrees = random.nextInt(360) + 0;
                    int color = Constans.colorList[random.nextInt(Constans.colorList.length - 1)];
                    float x = minX + 120 * i;
                    float y = minY - 120 * j;
                    MuildexView muildexView = new MuildexView(type, number, degrees, color, x, y, context);
                    muildexViews.add(muildexView);
                }
            }
        }
        /**
         * 保证至少添加一个
         */
        if (count == 0) {
            int number = random.nextInt(20 * (level <= 3 ? 1 : level - 2)) + (level <= 4 ? 1 : 20 * level - 5);
            muildexViews.add(new MuildexView(1, number, 20, Color.YELLOW, minX + 120 * 3, minY, context));
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                /**
                 * 获取发射的角度
                 */
                mRotationDegrees = (float) getRotationBetweenLines(sendX, sendY, event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                if (isCanMove) {
                    myMarbles.clear();
                    accout = 0;
                    final Timer timer = new Timer();
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            accout++;
                            if (accout <= point_accout)
                                myMarbles.add(new MyMarbles(mRotationDegrees + 36, pointX, pointY));
                            else
                                timer.cancel();

                        }
                    };
                    timer.schedule(task, 0, 200);
                }
                break;
        }
        return true;
    }

    /**
     * 获取两条线的夹角
     *
     * @param centerX 中心角x
     * @param centerY 中心角y
     * @param xInView 屏幕触摸的x
     * @param yInView 屏幕触摸的y
     * @return
     */
    private double getRotationBetweenLines(float centerX, float centerY, float xInView, float yInView) {
        double rotation = 0;
        double k1 = (double) (centerY - centerY) / (centerX * 2 - centerX);
        double k2 = (double) (yInView - centerY) / (xInView - centerX);
        double tmpDegree = Math.atan((Math.abs(k1 - k2)) / (1 + k1 * k2)) / Math.PI * 180;
        if (xInView >= centerX && yInView <= centerY) {  //第一象限及第一象限和第二象限的分界线
            rotation = 275;
        } else if (xInView > centerX && yInView > centerY) { //第二象限
            rotation = 270 + tmpDegree;
        } else if (xInView < centerX && yInView > centerY) { //第三象限
            rotation = 90 - tmpDegree;
        } else if (xInView <= centerX && yInView <= centerY) { //第四象限和第三象限的分界线
            rotation = 85;
        }
        /**
         * 这里-36的原因是初始进行了36的逆时针旋转
         */
        return rotation - 36f;
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        AppLogger.e(TAG, "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isDrawing = false;
        AppLogger.e(TAG, "surfaceDestroyed");
    }

    @Override
    public void run() {
        while (isDrawing) {
            long startTime = System.currentTimeMillis();
            synchronized (mHolder) {
                try {
                    canvas = mHolder.lockCanvas();
                    drawNormal(canvas);
                    drawFirstNormalMuildexView(canvas);
                    logic();
                    drawPoint(canvas);
                } catch (Exception e) {
                    AppLogger.e(TAG, "error=" + e.getMessage());
                } finally {
                    if (canvas != null)
                        mHolder.unlockCanvasAndPost(canvas);
                }
            }
            long endTime = System.currentTimeMillis();
            int diffTime = (int) (endTime - startTime);
            while (diffTime <= TIME_IN_FRAME) {
                diffTime = (int) (System.currentTimeMillis() - startTime);
                Thread.yield();
            }
        }
    }

    /**
     * 逻辑方法
     */
    private void logic() {
        /**
         * 执行弹球的逻辑方法
         */
        for (int i = 0; i < myMarbles.size(); i++) {
            MyMarbles b = myMarbles.get(i);
            for (int j = 0; j < muildexViews.size(); j++) {
                MuildexView muildex = muildexViews.get(j);
                if (b.isCollionAddPoint(muildex)) {
                    point_accout++;
                    muildexViews.remove(j);
                    if (muildex.x < 100) {
                        myMarbles.add(new MyMarbles(0, muildex.x + 40, muildex.y));
                    } else {
                        myMarbles.add(new MyMarbles(0, muildex.x - 40, muildex.y));
                    }
                }
                if (b.isCollionWithObstacle(muildex)) {
                    score++;
                    if (muildex.number > 1) {
                        muildex.number--;
                    } else {
                        muildexViews.remove(j);
                    }
                }
            }
        }
        /**
         * 游戏失败
         */
        if (null != muildexViews && muildexViews.size() > 0) {
            for (int i = 0; i < muildexViews.size(); i++) {
                if (muildexViews.get(i).y <= 60) {
                    // TODO: 失败后要做的事情  自己想怎么实现都行。我这只是个示例。实际上这个吐司是弹不出来的。
                    Toasty.info(context, "You Lose！", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    Random random = new Random();

    /**
     * 绘制弹珠
     *
     * @param canvas
     */
    private void drawPoint(Canvas canvas) {
        if (null != myMarbles && myMarbles.size() > 0) {
            isCanMove = false;
            int count = 0;
            for (int i = 0; i < myMarbles.size(); i++) {
                MyMarbles marbles = myMarbles.get(i);
                marbles.draw(canvas, paint);
                if (marbles.y > screen_H + 40) {
                    count++;
                }
            }
            if (count == myMarbles.size()) {
                isCanMove = true;
                for (int i = 0; i < muildexViews.size(); i++) {
                    muildexViews.get(i).logic();
                }
                myMarbles.clear();
                columnCount = 1;
                if (random.nextInt(20) % 2 == 0) {
                    level++;
                }
                if (level > 16) {
                    level = 10;
                }
                initNormalMuildexView();
            }
        }
    }

    /**
     * 初始化绘制障碍
     *
     * @param canvas
     */
    private void drawFirstNormalMuildexView(Canvas canvas) {
        for (int i = 0; i < muildexViews.size(); i++) {
            muildexViews.get(i).draw(canvas, paint);
        }
        isFirstDraw = false;
    }

    /**
     * 绘画默认背景
     */
    private void drawNormal(Canvas canvas) {
        /**
         * 绘制背景图
         */
        initPaint();
        Rect rect = new Rect(0, 0, screen_W, screen_H);
        canvas.drawBitmap(background, null, rect, paint);
        paint.setColor(Color.WHITE);
        canvas.drawText("得分：", 20, 50, paint);
        paint.setColor(Color.parseColor("#0099cc"));
        paint.setStrokeWidth(7.0f);
        /**
         * 绘制左右两条线
         */
        canvas.drawLine(30f, 100f + game_arrow.getWidth(), 30f, screen_H - 50f, paint);
        canvas.drawLine(screen_W - 30f, 100f + game_arrow.getWidth(), screen_W - 30f, screen_H - 50f, paint);
        paint.setColor(Color.WHITE);
        /**
         * 绘制小球个数
         */
        canvas.drawText("" + point_accout, screen_W / 2 - 10, 80, paint);
        /**
         * 绘制得分
         */
        canvas.drawText("" + score, 110, 50, paint);
        canvas.save();
        /**
         * 画布旋转
         */
        canvas.rotate(mRotationDegrees, screen_W / 2.0f, 100f + game_arrow.getWidth() / 2);
        /**
         * 绘制发射的箭头
         */
        canvas.drawBitmap(game_arrow, screen_W / 2.0f - game_arrow.getWidth() / 2.0f, 100f, paint);
        canvas.restore();
    }

}
