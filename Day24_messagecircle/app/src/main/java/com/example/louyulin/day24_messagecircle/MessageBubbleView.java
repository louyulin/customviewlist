package com.example.louyulin.day24_messagecircle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class MessageBubbleView extends View {
    private PointF mFixactionPoint , mDragPoint;
    //拖拽圆的半径
    private  int mDrageRadius = 10;
   private Paint mPaint;
   //固定圆的初始半径
   private int mFixactionRadiusMax = 7;
   private int mFixactionRadius = 7;
   private int mFixactionRadiusMini = 3;

    public MessageBubbleView(Context context) {
        this(context , null);
    }

    public MessageBubbleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public MessageBubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDrageRadius = dip2px(mDrageRadius);
        mFixactionRadius = dip2px(mFixactionRadius);
        mFixactionRadiusMax = dip2px(mFixactionRadiusMax);
        mFixactionRadiusMini = dip2px(mFixactionRadiusMini);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    private int dip2px(int drageRadius) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP , drageRadius,getResources().getDisplayMetrics());
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //手指按下 要去指定当前的位置
                float downx = event.getX();
                float downy = event.getY();
                initPoint(downx,downy);
                break;
            case MotionEvent.ACTION_MOVE:
                float movex = event.getX();
                float movey = event.getY();
                upDateDragePoint(movex , movey);
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        invalidate();
        return true;
    }

    //初始化位置
    private void initPoint(float downx , float downy){
        mFixactionPoint = new PointF(downx , downy);
        mDragPoint = new PointF(downx , downy);
    }

    //更新拖拽点的位置
    private void upDateDragePoint(float movex, float movey) {
        mDragPoint.x = movex;
        mDragPoint.y = movey;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画拖拽圆
        if (mDragPoint == null || mFixactionPoint == null){
            return;
        }
        canvas.drawCircle(mDragPoint.x , mDragPoint.y ,mDrageRadius,mPaint );

        Path bazerPath = getBazerPath(canvas);
        if (bazerPath != null){
            //画固定圆
            canvas.drawCircle(mFixactionPoint.x , mFixactionPoint.y ,mFixactionRadius,mPaint );
            //画曲线
            canvas.drawPath(bazerPath ,mPaint );
        }



    }

    //获取贝塞尔的路径
    private Path getBazerPath(Canvas canvas) {
        double distance = getDistance(mDragPoint , mFixactionPoint);
        mFixactionRadius = (int) (mFixactionRadiusMax - distance/40);
        if (mFixactionRadius < mFixactionRadiusMini){
            // 超过一定距离 贝塞尔和固定圆都不要画了
            return null;
        }
        Path bezeierPath = new Path();

        // 求角 a
        // 求斜率
        float dy = (mDragPoint.y-mFixactionPoint.y);
        float dx = (mDragPoint.x-mFixactionPoint.x);
        float tanA = dy/dx;
        // 求角a
        double arcTanA = Math.atan(tanA);

        // p0
        float p0x = (float) (mFixactionPoint.x + mFixactionRadius*Math.sin(arcTanA));
        float p0y = (float) (mFixactionPoint.y - mFixactionRadius*Math.cos(arcTanA));

        // p1
        float p1x = (float) (mDragPoint.x + mDrageRadius*Math.sin(arcTanA));
        float p1y = (float) (mDragPoint.y - mDrageRadius*Math.cos(arcTanA));

        // p2
        float p2x = (float) (mDragPoint.x - mDrageRadius*Math.sin(arcTanA));
        float p2y = (float) (mDragPoint.y + mDrageRadius*Math.cos(arcTanA));

        // p3
        float p3x = (float) (mFixactionPoint.x - mFixactionRadius*Math.sin(arcTanA));
        float p3y = (float) (mFixactionPoint.y + mFixactionRadius*Math.cos(arcTanA));

        // 拼装 贝塞尔的曲线路径
        bezeierPath.moveTo(p0x,p0y); // 移动
        // 两个点
        PointF controlPoint = getControlPoint();
        // 画了第一条  第一个点（控制点,两个圆心的中心点），终点
        bezeierPath.quadTo(controlPoint.x,controlPoint.y,p1x,p1y);

        // 画第二条
        bezeierPath.lineTo(p2x,p2y); // 链接到
        bezeierPath.quadTo(controlPoint.x,controlPoint.y,p3x,p3y);
        bezeierPath.close();

        return bezeierPath;
    }

    //获取两个圆的距离
    private double getDistance(PointF dragPoint, PointF fixactionPoint) {
        //开平方根
        return  Math.sqrt((dragPoint.x-fixactionPoint.x)*(dragPoint.x-fixactionPoint.x)+(dragPoint.y-fixactionPoint.y)*(dragPoint.y-fixactionPoint.y));
    }

    public PointF getControlPoint() {
        return new PointF((mDragPoint.x+mFixactionPoint.x)/2,(mDragPoint.y+mFixactionPoint.y)/2);
    }
}
