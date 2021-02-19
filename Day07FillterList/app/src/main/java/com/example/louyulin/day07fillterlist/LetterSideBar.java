package com.example.louyulin.day07fillterlist;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class LetterSideBar extends View {
    private Paint mPaint;
    //定义26个字母
    public static String[] mLetters = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};
    private String mMCurrentTouchLetter = "";
    private int mColor;
    private  TouchLetterListener mTouchLetterListener;

    public void setTouchLetterListener(TouchLetterListener touchLetterListener) {
        mTouchLetterListener = touchLetterListener;
    }

    public LetterSideBar(Context context) {
        super(context);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LetterSideBar);
        mColor = array.getColor(R.styleable.LetterSideBar_textcolor , Color.BLACK);
        int textSize = (int) array.getDimension(R.styleable.LetterSideBar_textsize , 15);
        array.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(sp2px(textSize));
        mPaint.setColor(mColor);
    }

    private float sp2px(int sp) {
        return TypedValue.
                applyDimension(TypedValue.COMPLEX_UNIT_DIP,sp,getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //计算制定宽度 左右padding 字母宽度(取决于画笔)
        float textWidth = mPaint.measureText("W");
        int width = getPaddingLeft() + getPaddingRight() +  (int)textWidth;

        //高度可以直接获取
        int hight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width,hight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int itemHight = getHeight()/ mLetters.length;
        for (int i = 0 ; i < mLetters.length ; i ++) {
            //知道每个字母的中心位置
            int letterCenterY = i*itemHight + itemHight/2 + getPaddingTop();
            //基线
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
            int dy = (int) ((fontMetrics.bottom - fontMetrics.top)/2 -  fontMetrics.bottom);
            int baseLine = letterCenterY + dy;

            float textWidth = mPaint.measureText(mLetters[i]);
            int x = (int) (getWidth()/2 -textWidth/2);//x 只绘制在最中间 宽度一半减文字一半

            //当前字符 高亮
            if (mMCurrentTouchLetter.equals(mLetters[i])){
                mPaint.setColor(Color.RED);
                canvas.drawText(mLetters[i] , x , baseLine , mPaint);
            }else {
                mPaint.setColor(mColor);
                canvas.drawText(mLetters[i] , x , baseLine , mPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //计算出当前触摸的字母  获取当前的位置
                int currentMoveY = (int) event.getY();
                int itemHight = getHeight() / mLetters.length;
                int currentPosition = currentMoveY/itemHight;
                if (currentPosition> 26){
                    currentPosition = 26;
                }
                mMCurrentTouchLetter = mLetters[currentPosition];
                mTouchLetterListener.touchLetter(mMCurrentTouchLetter);
                invalidate();//刷新
                break;
            case MotionEvent.ACTION_UP:
                mTouchLetterListener.upLetter();
                break;
        }
        return true;
    }

    public interface TouchLetterListener{
        public void touchLetter(String letter);
        public void upLetter();
    }
}
