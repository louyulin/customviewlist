package com.example.louyulin.day03qqstepview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class QQStepView extends View {
    private int outerColor = Color.RED;
    private int innerColor = Color.BLUE;
    private int borderWidth = 20 ;
    private int stepTextSize = 5;
    private int stepTextColor = Color.RED;
    private Paint outPaint;
    private Paint innerPaint;
    private Paint textPaint;
    private int stepMax = 3500;
    private int currentStep = 0;

    public QQStepView(Context context) {
        this(context , null);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        /**
         * 思路步骤:
         * 1 分析效果
         * 2 确定自定义属性编写attrs xml
         * 3 在布局中使用
         * 4 在自定义view中获取自定义属性
         * 5 重写onMeasure
         * 6 画外圆弧 内圆弧 还有文字
         * 7 其他
         */

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);
        outerColor = array.getColor(R.styleable.QQStepView_outerColor,outerColor);
        innerColor = array.getColor(R.styleable.QQStepView_innerColor,innerColor);
        borderWidth = (int) array.getDimension(R.styleable.QQStepView_borderWidth , borderWidth);
        stepTextSize = array.getDimensionPixelSize(R.styleable.QQStepView_stepTextSize , stepTextSize);
        stepTextColor = array.getColor(R.styleable.QQStepView_stepTextColor , stepTextColor);
        array.recycle();

        outPaint = new Paint();
        outPaint.setAntiAlias(true);
        outPaint.setStrokeWidth(borderWidth);
        outPaint.setColor(outerColor);
        outPaint.setStrokeCap(Paint.Cap.ROUND);
        outPaint.setStyle(Paint.Style.STROKE);//画空心

        innerPaint = new Paint();
        innerPaint.setAntiAlias(true);
        innerPaint.setStrokeWidth(borderWidth);
        innerPaint.setColor(innerColor);
        innerPaint.setStrokeCap(Paint.Cap.ROUND);
        innerPaint.setStyle(Paint.Style.STROKE);//画空心

        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(stepTextColor);
        textPaint.setTextSize(stepTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //宽度高度不一致的时候取最小的
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width > height ? height:width ,width > height ? height : width);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        //画外圆弧  分析 圆弧闭合 并且边缘被遮盖
        RectF rectF = new RectF(borderWidth /2 ,borderWidth /2,
                getWidth() - borderWidth / 2,
                getHeight() - borderWidth / 2);
        canvas.drawArc(rectF , 135 , 270 , false ,
                outPaint);

        //画内圆弧 不能写成死的 百分比 使用者从外面传的
        if (stepMax == 0){
            return;
        }
        float sweepAngle = (float)currentStep / stepMax;
        canvas.drawArc(rectF , 135 , sweepAngle*270 ,
                false , innerPaint);

        //画文字
        String str = currentStep + "";
        Rect textBounds = new Rect();
        textPaint.getTextBounds(str, 0 ,str.length() ,
                textBounds);
        int dx = getWidth() / 2  - textBounds.width() / 2;
        //基线
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int dy = (fontMetrics.bottom  - fontMetrics.top)/2 - fontMetrics.bottom;
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(str , dx , baseLine , textPaint);
    }

    //写几个方法动起来
    public synchronized void setStepMax(int stepMax){
        this.stepMax = stepMax;
    }

    public synchronized void setCurrentStep (int currentStep){
        this.currentStep = currentStep;
        //不断绘制
        invalidate();
    }
}
