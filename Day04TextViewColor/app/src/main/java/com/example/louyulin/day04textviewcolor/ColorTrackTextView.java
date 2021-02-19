package com.example.louyulin.day04textviewcolor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;


@SuppressLint("AppCompatCustomView")
public class ColorTrackTextView extends TextView {
    private Paint originPaint;
    private Paint changedPaint;
    private float currentProgress;
    private Direction direction = Direction.LEFT_TORIGHT;
    public enum Direction{
        LEFT_TORIGHT,RIGHT_TOLEFT
    }

    public ColorTrackTextView(Context context) {
        this(context , null);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.ColorTrackTextView);
        int originColor = array.getColor(R.styleable.ColorTrackTextView_originColor , Color.BLACK);
        int changedColor = array.getColor(R.styleable.ColorTrackTextView_changedColor , Color.RED);

        originPaint = getPaintByColor(originColor);
        changedPaint = getPaintByColor(changedColor);

        array.recycle();
    }

    private Paint getPaintByColor(int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        //防抖动
        paint.setDither(true);
        //设置字体大小
        paint.setTextSize(getTextSize());
        return paint;
    }

    //一个文字两种颜色
    //利用 clipRect方法 可以裁剪
    //根据进度把值算出来
    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas); 自己画 注释父类方法

        int middle = (int) (currentProgress*getWidth());

        if (direction == Direction.LEFT_TORIGHT){
            drawText(canvas , originPaint , middle, getWidth());
            drawText(canvas , changedPaint , 0 , middle);
        }else {
            drawText(canvas , originPaint , 0, getWidth() - middle);
            drawText(canvas , changedPaint , getWidth() - middle , getWidth());
        }

    }


    public void drawText(Canvas canvas , Paint paint , int start , int end){
        //计算基线
        String str = getText().toString();
        Rect bounds = new Rect();
        originPaint.getTextBounds(str , 0 ,
                str.length() , bounds);
        int x = getWidth() / 2 - bounds.width() / 2 ;
        Paint.FontMetricsInt fontMetricsInt = originPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 -
                fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;

        //绘制不变色
        canvas.save();
        Rect rect = new Rect(start , 0 , end , getHeight());
        canvas.clipRect(rect);
        canvas.drawText(str , x , baseLine , paint);
        canvas.restore();
    }

    public void setDirection(Direction direction){
        this.direction = direction;
    }

    public void setmCurrentProgress(float currentProgress){
        this.currentProgress = currentProgress;
        invalidate();
    }
}
