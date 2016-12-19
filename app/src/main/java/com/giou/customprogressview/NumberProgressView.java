package com.giou.customprogressview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/19
 * Email:65489469@qq.com
 */
public class NumberProgressView extends View {

    private final String TAG = NumberProgressView.class.getSimpleName();

    /**
     * 进度条画笔的宽度（dp）
     */
    private int paintProgressWidth = 10;

    /**
     * 左侧已完成进度条的颜色
     */
    private int paintLeftColor = 0xff67aae4;

    /**
     * Contxt
     */
    private Context context;

    /**
     * 主线程传过来进程 0 - 100
     */
    private int progress;

    /**
     * 得到自定义视图的宽度
     */
    private int mViewWidth;

    /**
     * 得到自定义视图的Y轴中心点
     */
    private int viewCenterY;

    /**
     * 画左边已完成进度条的画笔
     */
    private Paint paintleft = new Paint();

    /**
     * 画中间的百分比文字的画笔
     */
    private Paint paintText = new Paint();

    /**
     * 要画的文字的宽度
     */
    private int textWidth;

    /**
     * 画文字时底部的坐标
     */
    private float textBottomY;

    /**
     * 包裹文字的矩形
     */
    private Rect rect = new Rect();

    /**
     * 文字总共移动的长度（即从0%到100%文字左侧移动的长度）
     */
    private int totalMovedLength;
    private int mViewHeight;

    /**
     * 当前进度条高度
     */
    private int mCurrentHeight = 20;
    private Bitmap mBitmap;


    public NumberProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        // 构造器中初始化数据
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {

        //设置进度条画笔的宽度
        int paintProgressWidthPx = Utils.dip2px(context, paintProgressWidth);

        // 已完成进度条画笔的属性
        paintleft.setColor(paintLeftColor);
        paintleft.setStrokeWidth(paintProgressWidthPx);
        paintleft.setAntiAlias(true);
        paintleft.setStyle(Paint.Style.STROKE);

    }

    /**
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player_speed_pointer);
        int bitmapWidth = mBitmap.getWidth();

        //得到自定义视图的高度
        mViewHeight = getMeasuredHeight();
        mViewWidth = getMeasuredWidth();
        viewCenterY = mViewHeight / 2;
        totalMovedLength = mViewWidth - bitmapWidth;
        Log.d(TAG,"mViewWidth="+mViewWidth+" mViewHeight="+mViewHeight);//100 440
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path path = new Path();

        //得到float型进度
        float progressFloat = progress / 100.0f;

        //当前文字移动的长度
        float currentMovedLentgh = totalMovedLength * progressFloat;
        Log.d(TAG,"progressFloat="+progressFloat+"    currentMovedLentgh="+currentMovedLentgh);

        if(progressFloat >= 0 && progressFloat < 0.2){
            mCurrentHeight = 20;
            path.moveTo(0,mCurrentHeight);
            path.lineTo(currentMovedLentgh,mCurrentHeight);
        }else if(progressFloat >= 0.2 && progressFloat < 0.3){
            mCurrentHeight = 50;

            path.moveTo(0,20);
            path.lineTo(totalMovedLength*0.2f,20);

            path.moveTo(totalMovedLength*0.2f,mCurrentHeight);
            path.lineTo(currentMovedLentgh,mCurrentHeight);

        }else if(progressFloat >= 0.3 && progressFloat < 0.4){
            mCurrentHeight = 80;

            path.moveTo(0,20);
            path.lineTo(totalMovedLength*0.2f,20);

            path.moveTo(totalMovedLength*0.2f,50);
            path.lineTo(totalMovedLength*0.3f,50);

            path.moveTo(totalMovedLength*0.3f,mCurrentHeight);
            path.lineTo(currentMovedLentgh,mCurrentHeight);
        }else if(progressFloat >= 0.4 && progressFloat < 0.7){
            mCurrentHeight = 20;

            path.moveTo(0,20);
            path.lineTo(totalMovedLength*0.2f,20);

            path.moveTo(totalMovedLength*0.2f,50);
            path.lineTo(totalMovedLength*0.3f,50);

            path.moveTo(totalMovedLength*0.3f,80);
            path.lineTo(totalMovedLength*0.4f,80);

            path.moveTo(totalMovedLength*0.4f,mCurrentHeight);
            path.lineTo(currentMovedLentgh,mCurrentHeight);
        }else if(progressFloat >= 0.7 && progressFloat <= 1.0){
            mCurrentHeight = 50;

            path.moveTo(0,20);
            path.lineTo(totalMovedLength*0.2f,20);

            path.moveTo(totalMovedLength*0.2f,50);
            path.lineTo(totalMovedLength*0.3f,50);

            path.moveTo(totalMovedLength*0.3f,80);
            path.lineTo(totalMovedLength*0.4f,80);

            path.moveTo(totalMovedLength*0.4f,20);
            path.lineTo(totalMovedLength*0.7f,20);

            path.moveTo(totalMovedLength*0.7f,mCurrentHeight);
            path.lineTo(currentMovedLentgh,mCurrentHeight);
        }

        canvas.drawPath(path,paintleft);

        canvas.drawBitmap(mBitmap,currentMovedLentgh,0,paintText);

    }

    /**
     * @param progress 外部传进来的当前进度
     */
    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }
}
