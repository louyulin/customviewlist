package com.example.louyulin.day06ratingbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class RaingBar extends View {
    private Bitmap normalBitmap , focusBitmap;
    private int gradeNumber = 5;
    private int mCurrentGrade = 0;

    public void setmCurrentGrade(int mCurrentGrade) {
        this.mCurrentGrade = mCurrentGrade;
        invalidate();
    }

    public RaingBar(Context context) {
        super(context);
    }

    public RaingBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.RaingBar);
        int starNormalId = array.getResourceId(R.styleable.RaingBar_starNormal, 0);
        int startFocusId = array.getResourceId(R.styleable.RaingBar_starFocus, 0);
        gradeNumber = array.getResourceId(R.styleable.RaingBar_gradeNumber , gradeNumber);
        array.recycle();

        normalBitmap = BitmapFactory.decodeResource(getResources(),starNormalId);
        focusBitmap = BitmapFactory.decodeResource(getResources(),startFocusId);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //高度 一张图片的高度
        int height = focusBitmap.getHeight();
        int width = gradeNumber * focusBitmap.getWidth();//加间隔
        setMeasuredDimension(width , height);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        for (int i = 0; i < gradeNumber; i++) {
            //i * 星星的宽度
            int x = i * focusBitmap.getWidth();

            //结合第二步 触摸的时候 currentGrade的值是不断变化的

            if (mCurrentGrade > i){
                canvas.drawBitmap(focusBitmap ,x , 0 , null);
            }else {
                canvas.drawBitmap(normalBitmap ,x , 0 , null);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float movex = event.getX();//相对于当前控件x的位置
                Log.d("RaingBar", "moveX: "+ movex);

                int currentGrade = (int)(movex/focusBitmap.getWidth()+1);

                if (currentGrade < 0){
                    currentGrade = 0;
                }

                if (currentGrade > gradeNumber){
                    currentGrade = gradeNumber;
                }
                if (currentGrade == mCurrentGrade){
                    return true;
                }

                mCurrentGrade = currentGrade;
                invalidate();
                break;
        }
        return true;
    }
}
