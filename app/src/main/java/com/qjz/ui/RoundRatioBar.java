package com.qjz.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.qjz.roundratiobar.R;


public class RoundRatioBar extends View{

    //开始角度
    private int mStartAngle;
    //底色旋转角度
    private int mRotateAngle;
    //背景色
    private int mBottomColor;
    //前景色
    private int mPaintColor;
    //文字颜色
    private int mTextColor;
    //宽度
    private int mPaintWidth;
    //画笔
    private Paint mPaint;
    //圆的半径
    private int mRadius;
    //圆的外接多边形
    private RectF mRect;
    //视图的宽和高
    private int mWidth;
    private int mHeight;
    //标题文本
    private String mTitleText;
    //值文本
    private String mValueText;

    public RoundRatioBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        //从xml布局中获取属性值
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RoundRatioBar);
        mStartAngle=typedArray.getInt(R.styleable.RoundRatioBar_start_angle,140);
        mRotateAngle = typedArray.getInt(R.styleable.RoundRatioBar_rotate_angle,0);
        mPaintColor = typedArray.getColor(R.styleable.RoundRatioBar_paint_color, Color.GREEN);
        mTextColor = typedArray.getColor(R.styleable.RoundRatioBar_text_color, Color.BLACK);
        mPaintWidth = (int)typedArray.getDimensionPixelSize(R.styleable.RoundRatioBar_paint_width,6);
        mBottomColor = typedArray.getColor(R.styleable.RoundRatioBar_bottom_color, Color.GRAY);
        mRadius = typedArray.getDimensionPixelSize(R.styleable.RoundRatioBar_round_radius,20);
        mTitleText = typedArray.getString(R.styleable.RoundRatioBar_title_text);
        mValueText = typedArray.getString(R.styleable.RoundRatioBar_value_text);
        if (mValueText == null){
            mValueText="0";
        }
    }

    /**
     * 初始化绘图画笔
     */
    private void initDraw(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mBottomColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mPaintWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获取测量模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //获取测量大小
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //如果为确定大小值，则圆的半径为宽度/2
        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
            mHeight = heightSize;
        }

        //如果为wrap_content 那么View大小为圆的半径大小*2
        if(widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST){
            mWidth = (mRadius+mPaintWidth)*2;
            mHeight = (mRadius+mPaintWidth)*2;
        }

        //设置视图的大小
        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initDraw();

        mRect = new RectF(mPaintWidth, mPaintWidth, 2*mRadius+mPaintWidth, 2*mRadius+mPaintWidth);
        //绘制底色
        canvas.drawArc(mRect, mStartAngle, 360-(mStartAngle-90)*2, false, mPaint);

        //绘制主色
        mPaint.setColor(mPaintColor);
        canvas.drawArc(mRect, mStartAngle, mRotateAngle, false, mPaint);

        //绘制具体值
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(mTextColor);
        textPaint.setTextSize(ChangeDpToPx(this.getContext(),40f));
        Rect mBound1 = new Rect();
        textPaint.getTextBounds(mValueText, 0, mValueText.length(), mBound1);
        canvas.drawText(mValueText, mRadius+mPaintWidth-mBound1.width()/2, mRadius+mBound1.height()/2, textPaint);

        //绘制底部类型
        textPaint.setTextSize(ChangeDpToPx(this.getContext(),20f));
        Rect mBound2 = new Rect();
        textPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound2);
        canvas.drawText(mTitleText, mRadius+mPaintWidth-mBound2.width()/2, mRadius*2.0f, textPaint);
    }

    /**
     * 单位px转换为dp
     * @param context
     * @param pxValue
     * @return
     */
    private float ChangePxToDp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxValue / scale + 0.5f);
    }

    /**
     * 单位dp转换为px
     * @param context
     * @param dpValue
     * @return
     */
    private float ChangeDpToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (dpValue * scale + 0.5f);
    }

    public int getPaintColor() {
        return mPaintColor;
    }

    public void setPaintColor(int mPaintColor) {
        this.mPaintColor = mPaintColor;
        invalidate();
        requestLayout();
    }

    public String getmValueText() {
        return mValueText;
    }

    public void setmValueText(String mValueText) {
        this.mValueText = mValueText;
        invalidate();
        requestLayout();
    }

    public int getmTextColor() {
        return mTextColor;
    }

    public void setmTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
        invalidate();
        requestLayout();
    }

    public int getmRotateAngle() {
        return mRotateAngle;
    }

    public void setmRotateAngle(int mRotateAngle) {
        this.mRotateAngle = mRotateAngle;
        invalidate();
        requestLayout();
    }
}
