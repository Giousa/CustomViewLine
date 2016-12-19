package com.giou.customprogressview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
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
     * 画中间的百分比文字的画笔
     */
    private Paint paintPointer = new Paint();

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

    private Paint mPaintOne = new Paint();
    private Paint mPaintTwo = new Paint();
    private Paint mPaintThree = new Paint();
    private Paint mPaintFour = new Paint();
    private Paint mPaintFive = new Paint();

    private Path mPathOne;
    private Path mPathTwo;
    private Path mPathThree;
    private Path mPathFour;
    private Path mPathFive;


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

        Shader mShaderOne = new LinearGradient(0,0,40,60,new int[] {Color.BLUE,Color.YELLOW},null, Shader.TileMode.REPEAT);
        mPaintOne.setShader(mShaderOne);

//        mPaintOne.setColor(Color.BLUE);
        mPaintOne.setStrokeWidth(paintProgressWidthPx);
        mPaintOne.setAntiAlias(true);
        mPaintOne.setStyle(Paint.Style.STROKE);

        mPaintTwo.setColor(Color.YELLOW);
        mPaintTwo.setStrokeWidth(paintProgressWidthPx);
        mPaintTwo.setAntiAlias(true);
        mPaintTwo.setStyle(Paint.Style.STROKE);

        mPaintThree.setColor(Color.RED);
        mPaintThree.setStrokeWidth(paintProgressWidthPx);
        mPaintThree.setAntiAlias(true);
        mPaintThree.setStyle(Paint.Style.STROKE);

        mPaintFour.setColor(Color.GRAY);
        mPaintFour.setStrokeWidth(paintProgressWidthPx);
        mPaintFour.setAntiAlias(true);
        mPaintFour.setStyle(Paint.Style.STROKE);

        mPaintFive.setColor(Color.BLACK);
        mPaintFive.setStrokeWidth(paintProgressWidthPx);
        mPaintFive.setAntiAlias(true);
        mPaintFive.setStyle(Paint.Style.STROKE);

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
        totalMovedLength = mViewWidth - bitmapWidth;
        Log.d(TAG,"mViewWidth="+mViewWidth+" mViewHeight="+mViewHeight);//100 440


        //若是将路径放入OnDraw方法中，则再次点击开始，可以重头绘制
        mPathOne = new Path();
        mPathTwo = new Path();
        mPathThree = new Path();
        mPathFour = new Path();
        mPathFive = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //得到float型进度
        float progressFloat = progress / 100.0f;

        //当前指针移动的长度
        float currentMovedLentgh = totalMovedLength * progressFloat;
        Log.d(TAG,"progressFloat="+progressFloat+"    currentMovedLentgh="+currentMovedLentgh);

        if(progressFloat >= 0 && progressFloat < 0.2){
            mCurrentHeight = 20;
            mPathOne.moveTo(0,mCurrentHeight);
            mPathOne.lineTo(currentMovedLentgh,mCurrentHeight);
            canvas.drawPath(mPathOne,mPaintOne);
        }else if(progressFloat >= 0.2 && progressFloat < 0.3){
            mCurrentHeight = 50;

            mPathOne.moveTo(0,20);
            mPathOne.lineTo(totalMovedLength*0.2f,20);
            canvas.drawPath(mPathOne,mPaintOne);

            mPathTwo.moveTo(totalMovedLength*0.2f,mCurrentHeight);
            mPathTwo.lineTo(currentMovedLentgh,mCurrentHeight);
            canvas.drawPath(mPathTwo,mPaintTwo);
        }else if(progressFloat >= 0.3 && progressFloat < 0.4){
            mCurrentHeight = 80;

            mPathOne.moveTo(0,20);
            mPathOne.lineTo(totalMovedLength*0.2f,20);
            canvas.drawPath(mPathOne,mPaintOne);

            mPathTwo.moveTo(totalMovedLength*0.2f,50);
            mPathTwo.lineTo(totalMovedLength*0.3f,50);
            canvas.drawPath(mPathTwo,mPaintTwo);

            mPathThree.moveTo(totalMovedLength*0.3f,mCurrentHeight);
            mPathThree.lineTo(currentMovedLentgh,mCurrentHeight);

            canvas.drawPath(mPathThree,mPaintThree);
        }else if(progressFloat >= 0.4 && progressFloat < 0.7){
            mCurrentHeight = 20;

            mPathOne.moveTo(0,20);
            mPathOne.lineTo(totalMovedLength*0.2f,20);
            canvas.drawPath(mPathOne,mPaintOne);

            mPathTwo.moveTo(totalMovedLength*0.2f,50);
            mPathTwo.lineTo(totalMovedLength*0.3f,50);
            canvas.drawPath(mPathTwo,mPaintTwo);

            mPathThree.moveTo(totalMovedLength*0.3f,80);
            mPathThree.lineTo(totalMovedLength*0.4f,80);
            canvas.drawPath(mPathThree,mPaintThree);

            mPathFour.moveTo(totalMovedLength*0.4f,mCurrentHeight);
            mPathFour.lineTo(currentMovedLentgh,mCurrentHeight);

            canvas.drawPath(mPathFour,mPaintFour);
        }else if(progressFloat >= 0.7 && progressFloat <= 1.0){
            mCurrentHeight = 50;

            mPathOne.moveTo(0,20);
            mPathOne.lineTo(totalMovedLength*0.2f,20);
            canvas.drawPath(mPathOne,mPaintOne);

            mPathTwo.moveTo(totalMovedLength*0.2f,50);
            mPathTwo.lineTo(totalMovedLength*0.3f,50);
            canvas.drawPath(mPathTwo,mPaintTwo);

            mPathThree.moveTo(totalMovedLength*0.3f,80);
            mPathThree.lineTo(totalMovedLength*0.4f,80);
            canvas.drawPath(mPathThree,mPaintThree);

            mPathFour.moveTo(totalMovedLength*0.4f,20);
            mPathFour.lineTo(totalMovedLength*0.7f,20);
            canvas.drawPath(mPathFour,mPaintFour);

            mPathFive.moveTo(totalMovedLength*0.7f,mCurrentHeight);
            mPathFive.lineTo(currentMovedLentgh,mCurrentHeight);

            canvas.drawPath(mPathFive,mPaintFive);
        }

        canvas.drawBitmap(mBitmap,currentMovedLentgh,0,paintPointer);

    }

    /**
     * @param progress 外部传进来的当前进度
     */
    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }
}
