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

    private int mOffset = 12;
    private int mHeightOne = 55+mOffset;
    private int mHeightTwo = 0+mOffset;
    private int mHeightThree = 55+mOffset;
    private int mHeightFour = 28+mOffset;
    private int mHeightFive = 55+mOffset;

    private int mLeftViewWidth = 64;


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
//        int paintProgressWidthPx = Utils.dip2px(context, paintProgressWidth);
        int paintProgressWidthPx = 27;

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
        totalMovedLength = mViewWidth - bitmapWidth - mLeftViewWidth;
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
            mPathOne.moveTo(mLeftViewWidth,mHeightOne);
            mPathOne.lineTo(mLeftViewWidth+currentMovedLentgh,mHeightOne);
            canvas.drawPath(mPathOne,mPaintOne);
        }else if(progressFloat >= 0.2 && progressFloat < 0.3){

            mPathOne.moveTo(mLeftViewWidth,mHeightOne);
            mPathOne.lineTo(mLeftViewWidth+totalMovedLength*0.2f,mHeightOne);
            canvas.drawPath(mPathOne,mPaintOne);

            mPathTwo.moveTo(mLeftViewWidth+totalMovedLength*0.2f,mHeightTwo);
            mPathTwo.lineTo(mLeftViewWidth+currentMovedLentgh,mHeightTwo);
            canvas.drawPath(mPathTwo,mPaintTwo);
        }else if(progressFloat >= 0.3 && progressFloat < 0.4){

            mPathOne.moveTo(mLeftViewWidth,mHeightOne);
            mPathOne.lineTo(mLeftViewWidth+totalMovedLength*0.2f,mHeightOne);
            canvas.drawPath(mPathOne,mPaintOne);

            mPathTwo.moveTo(mLeftViewWidth+totalMovedLength*0.2f,mHeightTwo);
            mPathTwo.lineTo(mLeftViewWidth+totalMovedLength*0.3f,mHeightTwo);
            canvas.drawPath(mPathTwo,mPaintTwo);

            mPathThree.moveTo(mLeftViewWidth+totalMovedLength*0.3f,mHeightThree);
            mPathThree.lineTo(mLeftViewWidth+currentMovedLentgh,mHeightThree);

            canvas.drawPath(mPathThree,mPaintThree);
        }else if(progressFloat >= 0.4 && progressFloat < 0.7){

            mPathOne.moveTo(mLeftViewWidth,mHeightOne);
            mPathOne.lineTo(mLeftViewWidth+totalMovedLength*0.2f,mHeightOne);
            canvas.drawPath(mPathOne,mPaintOne);

            mPathTwo.moveTo(mLeftViewWidth+totalMovedLength*0.2f,mHeightTwo);
            mPathTwo.lineTo(mLeftViewWidth+totalMovedLength*0.3f,mHeightTwo);
            canvas.drawPath(mPathTwo,mPaintTwo);

            mPathThree.moveTo(mLeftViewWidth+totalMovedLength*0.3f,mHeightThree);
            mPathThree.lineTo(mLeftViewWidth+totalMovedLength*0.4f,mHeightThree);
            canvas.drawPath(mPathThree,mPaintThree);

            mPathFour.moveTo(mLeftViewWidth+totalMovedLength*0.4f,mHeightFour);
            mPathFour.lineTo(mLeftViewWidth+currentMovedLentgh,mHeightFour);

            canvas.drawPath(mPathFour,mPaintFour);
        }else if(progressFloat >= 0.7 && progressFloat <= 1.0){

            mPathOne.moveTo(mLeftViewWidth,mHeightOne);
            mPathOne.lineTo(mLeftViewWidth+totalMovedLength*0.2f,mHeightOne);
            canvas.drawPath(mPathOne,mPaintOne);

            mPathTwo.moveTo(mLeftViewWidth+totalMovedLength*0.2f,mHeightTwo);
            mPathTwo.lineTo(mLeftViewWidth+totalMovedLength*0.3f,mHeightTwo);
            canvas.drawPath(mPathTwo,mPaintTwo);

            mPathThree.moveTo(mLeftViewWidth+totalMovedLength*0.3f,mHeightThree);
            mPathThree.lineTo(mLeftViewWidth+totalMovedLength*0.4f,mHeightThree);
            canvas.drawPath(mPathThree,mPaintThree);

            mPathFour.moveTo(mLeftViewWidth+totalMovedLength*0.4f,mHeightFour);
            mPathFour.lineTo(mLeftViewWidth+totalMovedLength*0.7f,mHeightFour);
            canvas.drawPath(mPathFour,mPaintFour);

            mPathFive.moveTo(mLeftViewWidth+totalMovedLength*0.7f,mHeightFive);
            mPathFive.lineTo(mLeftViewWidth+currentMovedLentgh,mHeightFive);

            canvas.drawPath(mPathFive,mPaintFive);
        }

        canvas.drawBitmap(mBitmap,mLeftViewWidth+currentMovedLentgh,0,paintPointer);

    }

    /**
     * @param progress 外部传进来的当前进度
     */
    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }
}
