package com.example.viewday01;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class TextView extends View {

    private String mText;
    private  int mTextSize = 15;
    private int mTextColor = Color.BLACK;

    private Paint mPaint;

    //构造函数会在代码new的时候调用
    public TextView(Context context) {
        this(context , null);
    }

    //在布局中使用
    public TextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs , 0);
    }

    //在布局中,但是会有自定义style
    public TextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextView);
        mText = typedArray.getString(R.styleable.TextView_dareentext);
        mTextColor = typedArray.getColor(R.styleable.TextView_dareentextColor , mTextColor);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.TextView_dareentestSize ,px2sp(mTextSize) );

        typedArray.recycle();

        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
    }

    /**
     * 自定义View的测量方法
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //布局的宽高都是在这个方法指定  需要测量

        //获取宽高的模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //AT_MOST 在布局中指定了wrap_content
        //EXACTLY 在布局中指定了确切的值 100 dp  match_parent
        //UNSPECIFIED 能有多大就尽可能的大  很少用到  listview scrollview等


        //1.确定的值 这个时候不需要计算
        int width = MeasureSpec.getSize(widthMeasureSpec);

        //2如果是wrapcontent 需要计算
        if(widthMode == MeasureSpec.AT_MOST){
            //计算宽度  与字体的宽高有关 用画笔来测量
            Rect bounds = new Rect();
            mPaint.getTextBounds(mText , 0 , mText.length() , bounds );
            width = bounds.width() + getPaddingLeft() + getPaddingRight();
        }

        int height = MeasureSpec.getSize(heightMeasureSpec);

        if(heightMode == MeasureSpec.AT_MOST){
            //计算宽度  与字体的宽高有关 用画笔来测量
            Rect bounds = new Rect();
            mPaint.getTextBounds(mText , 0 , mText.length() , bounds );
            height = bounds.height() + getPaddingTop() + getPaddingBottom();
        }


        setMeasuredDimension(width , height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画canvas  x 开始的位置 y 基线 (baseLine)
        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
        //dy 代表 高度的一般到baseLine的距离
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top)/ 2  - fontMetricsInt.bottom;
        int baseLine = getHeight()/2 + dy;

        int x = getPaddingLeft();

        canvas.drawText(mText , x , baseLine , mPaint);
    }


    /**
     * 事件分发处理跟用户交互,手指触摸
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:

                break;
        }

        return super.onTouchEvent(event);
    }


    private int px2sp(int mTextSize) {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP , mTextSize ,
                getResources().getDisplayMetrics());
    }
}
