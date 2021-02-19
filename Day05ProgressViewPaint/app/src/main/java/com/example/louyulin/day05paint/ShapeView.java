package com.example.louyulin.day05paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class ShapeView extends View {
   private Shape currentShape = Shape.Circle;
   Paint paint;
   Path path;

    public ShapeView(Context context) {
        this(context , null);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (currentShape){
            case Circle:
                int center = getWidth()/2;
                paint.setColor(getResources().getColor(R.color.colorAccent));
                canvas.drawCircle(center,center,center,paint);
                break;
            case Tringle:
                //画三角用path 来画
                if (path == null){
                    path = new Path();
                    path.moveTo(getWidth()/2 , 0);
                    path.lineTo(0, (float) ((getWidth()/2)*Math.sqrt(3)));
                    path.lineTo(getWidth(), (float) ((getWidth()/2)*Math.sqrt(3)));
                    //闭合!
                    path.close();
                }
                paint.setColor(getResources().getColor(R.color.colorPrimaryDark));
                canvas.drawPath(path,paint);
                break;
            case Square:
                paint.setColor(getResources().getColor(R.color.colorPrimary));
                canvas.drawRect(0,0,getWidth(),getHeight(),paint);
                break;
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(Math.min(width,height),Math.min(width,height));
    }

    public void exchanged() {
        switch (currentShape){
            case Circle:
                currentShape = Shape.Square;
                break;
            case Square:
                currentShape = Shape.Tringle;
                break;
            case Tringle:
                currentShape = Shape.Circle;
                break;
        }
        invalidate();
    }

    public enum Shape {
        Circle,
        Square,
        Tringle
    }
}
