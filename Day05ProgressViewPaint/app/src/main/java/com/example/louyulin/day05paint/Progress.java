package com.example.louyulin.day05paint;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class Progress extends View {
    private int innerBackground = Color.RED;
    private int outerBackground = Color.RED;
    private int roundWidth = 5;
    private int progressTextSize = 15;
    private int progressTextColor = Color.RED;
    private Paint innerPaint;
    private Paint outerPaint , textPaint;
    private int max = 100;
    private int progress = 30;

    public Progress(Context context) {
        this(context , null);
    }

    public Progress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.Progress);
        innerBackground = array.getColor(R.styleable.Progress_innerBackground,innerBackground);
        outerBackground = array.getColor(R.styleable.Progress_outerBackground,outerBackground);
        roundWidth = (int) array.getDimension(R.styleable.Progress_roundWidth,dp2px(roundWidth));
        progressTextSize = (int) array.getDimensionPixelSize(R.styleable.Progress_progressTextSize,sp2px(progressTextSize));
        progressTextColor =  array.getColor(R.styleable.Progress_progressTextColor,progressTextColor);
        array.recycle();

        innerPaint = new Paint();
        innerPaint.setAntiAlias(true);
        innerPaint.setColor(innerBackground);
        innerPaint.setStrokeWidth(roundWidth);
        innerPaint.setStyle(Paint.Style.STROKE);

        outerPaint = new Paint();
        outerPaint.setAntiAlias(true);
        outerPaint.setColor(outerBackground);
        outerPaint.setStrokeWidth(roundWidth);
        outerPaint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(progressTextColor);
        textPaint.setTextSize(progressTextSize);
    }


    private int sp2px(int progressTextSize) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                progressTextSize , getResources().getDisplayMetrics());
    }

    private float dp2px(int i) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                i , getResources().getDisplayMetrics());
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //保证是正方形
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(Math.min(width,height),Math.min(width,height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //先画内圈
        int center = getWidth()/2;
        canvas.drawCircle(center , center , center - roundWidth/2 , innerPaint);

        //外
        RectF rectF = new RectF(0 + roundWidth/2 , 0 + roundWidth/2 ,
                getWidth()-roundWidth/2  , getHeight()-roundWidth/2 );
        if (progress == 0){
            return;
        }
        float parcent = (float) progress/max;
        canvas.drawArc(rectF , 0 , parcent*360 ,false,outerPaint);
        //文字
        String text = (int)(parcent*100) + "%";

        Rect textBouns = new Rect();
        textPaint.getTextBounds(text , 0 , text.length() , textBouns);
        int x  = getWidth() / 2 - textBouns.width() / 2;

        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        int dy = fontMetricsInt.bottom - fontMetricsInt.top/2 - fontMetricsInt.bottom;
        int baseLineY = getHeight()/2 + dy;

        canvas.drawText(text,x , baseLineY , textPaint);

    }

    //给几个方法
    public synchronized void  setMax(int max){
        if (max < 0){
            return;
        }
        this.max = max;
    }

    public synchronized void  setProgress(int progress){
        if (max < 0){
            return;
        }
        this.progress = progress;
        invalidate();
    }
}
