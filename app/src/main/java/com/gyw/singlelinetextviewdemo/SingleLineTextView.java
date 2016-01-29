package com.gyw.singlelinetextviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * 单行的TextView
 */
public class SingleLineTextView extends TextView {

    private String textContent;
    private int textSize;
    private int textColor;
    private Paint paint;

    private float textWidth;
    private float textHeight;


    public SingleLineTextView(Context context) {
        this(context, null);
    }

    public SingleLineTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SingleLineTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.SingleLineTextView, defStyle, 0);

        //获取文本
        textContent = ta.getString(R.styleable.SingleLineTextView_textContent);
        //字体大小
        textSize = ta.getDimensionPixelSize(R.styleable.SingleLineTextView_textSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        //字体颜色
        textColor = ta.getColor(R.styleable.SingleLineTextView_textColor, Color.BLACK);

        ta.recycle();

        initPaint();



    }

    //初始化画笔
    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        //获取文本的宽度
        textWidth = paint.measureText(textContent);
        //文本的高度
        textHeight = paint.descent() - paint.ascent();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthsize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthsize;
        } else {
            width = getPaddingLeft() + (int)(textWidth+0.5) + getPaddingRight();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = getPaddingTop() + (int)(textHeight+0.5) + getPaddingBottom();
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //如果文本的宽度大于显示的宽度,将字体大小减小
        while (textWidth > getWidth()) {
            paint.setTextSize(textSize--);
            textWidth = paint.measureText(textContent);
        }

        //画出文本
        float textBaseline = (getHeight() - (paint.descent() + paint.ascent())) / 2.0f;
        canvas.drawText(textContent, (getWidth() -  textWidth) / 2.0f, textBaseline, paint);
    }
}
