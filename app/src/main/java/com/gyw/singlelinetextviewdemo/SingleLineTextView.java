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

public class SingleLineTextView extends TextView {

	private String textContent;
	private int textSize;
	private Paint paint;
	private Rect rect;
	

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

		final int N = ta.getIndexCount();

		for (int i = 0; i < N; i++) {
			int attr = ta.getIndex(i);

			switch (attr) {
			
			case R.styleable.SingleLineTextView_textContent:
				textContent = ta.getString(attr);
				break;

			case R.styleable.SingleLineTextView_textSize:
				textSize = ta.getDimensionPixelSize(attr, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
				break;
			}
		}

		ta.recycle(); 
		
		paint = new Paint();

		rect = new Rect();
		paint.setTextSize(textSize);
		paint.getTextBounds(textContent, 0, textContent.length(), rect);

		Log.d("gyw ", "rect.width() :" + rect.width());
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
			width = getPaddingLeft() + rect.width() + getPaddingRight();
		}

		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else {
			height = getPaddingTop() + rect.height() + getPaddingBottom();
		}

		setMeasuredDimension(width, height);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Log.d("gyw ", "getWidth() :" + getWidth());
		paint.setColor(Color.TRANSPARENT);
		canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);

		paint.setColor(Color.BLACK);

		Log.d("gyw ", "width "+paint.measureText(textContent) + " rect.width() : " + rect.width());

		while(rect.width() > getWidth()) {
			paint.setTextSize(textSize--);
			paint.getTextBounds(textContent, 0, textContent.length(), rect);
		}

		float textHeight = paint.descent() + paint.ascent();
		float textBaseline = (getHeight() - textHeight) / 2.0f;
		canvas.drawText(textContent, (getWidth()  - rect.width()) / 2.0f, textBaseline , paint);
	}
}
