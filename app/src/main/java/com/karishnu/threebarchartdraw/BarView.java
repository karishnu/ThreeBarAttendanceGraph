package com.karishnu.threebarchartdraw;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class BarView extends View{

    private Rect rectangle_first, rectangle_second, rectangle_third;
    int first_perc, second_perc, third_perc;
    private Paint paintFirst, paintSecond, paintThird;
    String redColor = "#f45531";
    String yellowColor = "#f2e653";
    String greenColor = "#21ce99";
    Path bottomLinePath;
    Paint bottomLinePaint;
    int padding = 100;
    int x1, x2, y1, y2;
    int avail_height, avail_width;
    int text_size;
    Paint text1Paint, text2Paint, text3Paint;

    //Percentage text positions
    int left_1, top_1, right_1, left_2, top_2, right_2, left_3, top_3, right_3;

    public BarView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);

        TypedArray ta = context.obtainStyledAttributes(attributeSet, R.styleable.BarView, 0, 0);
        try {
            text_size = ta.getInt(R.styleable.BarView_percentageTextSize, 52);
        } finally {
            ta.recycle();
        }

        bottomLinePath = new Path();

        bottomLinePaint = new Paint();
        bottomLinePaint.setARGB(255, 0, 0,0);
        bottomLinePaint.setStyle(Paint.Style.STROKE);
        bottomLinePaint.setStrokeWidth(6);
        bottomLinePaint.setPathEffect(new DashPathEffect(new float[] {25,10}, 0));

        text1Paint = new Paint();
        text1Paint.setColor(Color.BLACK);
        text1Paint.setTextSize(text_size);
        text1Paint.setTextAlign(Paint.Align.CENTER);

        text2Paint = new Paint();
        text2Paint.setColor(Color.BLACK);
        text2Paint.setTextSize(text_size);
        text2Paint.setTextAlign(Paint.Align.CENTER);

        text3Paint = new Paint();
        text3Paint.setColor(Color.BLACK);
        text3Paint.setTextSize(text_size);
        text3Paint.setTextAlign(Paint.Align.CENTER);

        //Bitmap upArrow = Bitmap.createBitmap()
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawRect(rectangle_first, paintFirst);
        canvas.drawRect(rectangle_second, paintSecond);
        canvas.drawRect(rectangle_third, paintThird);
        canvas.drawPath(bottomLinePath, bottomLinePaint);
        canvas.drawText(first_perc + "%", left_1 + (float) (0.05*avail_width), top_1 - 15, text1Paint);
        canvas.drawText(second_perc + "%", left_2 + (float) (0.05*avail_width), top_2 - 15, text2Paint);
        canvas.drawText(third_perc + "%", left_3 + (float) (0.05*avail_width), top_3 - 15, text3Paint);
    }

    public void setPercentage(int first_perc, int second_perc, int third_perc){

        paintFirst = new Paint();
        paintSecond = new Paint();
        paintThird = new Paint();

        setColorPercentage(paintFirst, text1Paint, first_perc);
        setColorPercentage(paintSecond, text2Paint, second_perc);
        setColorPercentage(paintThird, text3Paint, third_perc);

        this.first_perc = first_perc;
        this.second_perc = second_perc;
        this.third_perc = third_perc;

        invalidate();
    }

    protected void setColorPercentage(Paint paint,Paint text, int percentage){
        if(percentage<75){
            paint.setColor(Color.parseColor(redColor));
            text.setColor(Color.parseColor(redColor));
        }
        else if(percentage>=75 && percentage <80){
            paint.setColor(Color.parseColor(yellowColor));
            text.setColor(Color.parseColor(yellowColor));
        }
        else if(percentage>=80){
            paint.setColor(Color.parseColor(greenColor));
            text.setColor(Color.parseColor(greenColor));
        }
    }

    protected void drawShapes(int first, int second, int third){
        double first_height = avail_height * first/100.00;
        double second_height = avail_height * second/100.00;
        double third_height = avail_height * third/100.00;

         left_1 = (int) Math.round(x1 + 0.2*avail_width);
         top_1 = (int) Math.round(y1 + avail_height - first_height);
         right_1 = (int) Math.round(x1 + 0.3*avail_width);

        rectangle_first = new Rect(left_1,top_1,right_1,y1 + avail_height);

         left_2 = (int) Math.round(x1 + 0.5*avail_width);
         top_2 = (int) Math.round(y1 + avail_height - second_height);
         right_2 = (int) Math.round(x1 + 0.6*avail_width);

        rectangle_second = new Rect(left_2,top_2,right_2,y1 + avail_height);

         left_3 = (int) Math.round(x1 + 0.8*avail_width);
         top_3 = (int) Math.round(y1 + avail_height - third_height);
         right_3 = (int) Math.round(x1 + 0.9*avail_width);

        rectangle_third = new Rect(left_3,top_3,right_3,y1 + avail_height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        y1 = padding;
        x1 = padding;
        y2 = getMeasuredHeight() - (padding);
        x2 = getMeasuredWidth() - (padding);

        avail_height = getMeasuredHeight() - (2*padding);
        avail_width = getMeasuredWidth() - (2*padding);

        drawShapes(first_perc,second_perc,third_perc);

        bottomLinePath.moveTo(x1, y2);
        bottomLinePath.lineTo(x2, y2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        this.setMeasuredDimension(parentWidth, parentHeight);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
