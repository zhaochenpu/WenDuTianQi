package com.wendu.wendutianqi.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PointF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.wendu.wendutianqi.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

public class SmoothLineChartEquallySpaced extends View {
	private  int CHART_COLOR =0xfff44336;
	private  int CHART_COLOR2 = 0xFF0099CC;
	private static final int CIRCLE_SIZE = 4;
	private static final int STROKE_SIZE = 1;
	private static final float SMOOTHNESS = 0.35f; // the higher the smoother, but don't go over 0.5
	
	private final Paint mPaint;
	private final Paint mPaint2;
	private final Path mPath;
	private final Path mPath2;
	private final float mCircleSize;
	private final float mStrokeSize;
	private final float mBorder;
	
	private int[] mValues;
	private int[] mValues2;
	private float mMinY;
	private float mMaxY;
	private float mMinY2;
	private float mMaxY2;
	private Context mcontext;

	public SmoothLineChartEquallySpaced(Context context) {
		this(context, null, 0);
	}

	public SmoothLineChartEquallySpaced(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		mcontext=context;
	}

	public SmoothLineChartEquallySpaced(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mcontext=context;
		float scale = context.getResources().getDisplayMetrics().density;
		
		mCircleSize = scale * CIRCLE_SIZE;
		mStrokeSize = scale * STROKE_SIZE;
		mBorder = mCircleSize;
		
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setStrokeWidth(mStrokeSize);
		mPaint2 = new Paint();
		mPaint2.setAntiAlias(true);
		mPaint2.setStrokeWidth(mStrokeSize);
		mPath = new Path();
		mPath2=new Path();
	}
	
	public void setData(int[] values,int[] values2) {
		mValues = values;
		mValues2 = values2;
		if (values != null && values.length > 0) {
			mMaxY = values[0];
//			mMinY = values[0];
			for (float y : values) {
				if (y > mMaxY){
					mMaxY = y;
				}
//				else if (y < mMinY){
//					mMinY = y;
//				}
			}
		}
		if (values2 != null && values2.length > 0) {
//			mMaxY2 = values[0];
			mMinY2 = values[0];
			for (float y : values) {
//				if (y > mMaxY2){
//					mMaxY2 = y;
//				}else
				if (y < mMinY2){
					mMinY2 = y;
				}
			}
		}
		invalidate();
	}
	
	public void draw(Canvas canvas) {
		super.draw(canvas);
		
		if (mValues == null || mValues.length == 0)
			return;

		int size = mValues.length;

//		final float dX = mValues.length > 1 ? mValues.length-1  : (2);
		final float dY = (mMaxY-mMinY) > 0 ? (mMaxY-mMinY) : (2);

		final float height = getMeasuredHeight() - 2*mBorder;
//		final float width = getMeasuredWidth() - 2*mBorder-getMeasuredWidth()/size;
		mPath.reset();
		mPath2.reset();
		// calculate point coordinates
		List<PointF> points = new ArrayList<PointF>(size);
		List<PointF> points2 = new ArrayList<PointF>(size);
		for (int i=0; i<size; i++) {
			float x =i*getMeasuredWidth()/size+(getMeasuredWidth()/size)/2;
			float y = mBorder + height - (mValues[i]-mMinY)*height/dY+DisplayUtil.sp2px(mcontext,12);
			float y2 = mBorder + height - (mValues2[i]-mMinY)*height/dY+DisplayUtil.sp2px(mcontext,12);
			points.add(new PointF(x,y));
			points2.add(new PointF(x,y2));
		}

		// calculate smooth path

		mDraw(size,points,canvas,mPath,mPaint,CHART_COLOR,mValues);
		mDraw(size,points2,canvas,mPath2,mPaint2,CHART_COLOR2,mValues2);

//		// draw area
//		if (size > 0) {
//			mPaint.setStyle(Style.FILL);
//			mPaint.setColor((CHART_COLOR & 0xFFFFFF) | 0x10000000);
//			mPath.lineTo(points.get(size-1).x, height+mBorder);
//			mPath.lineTo(points.get(0).x, height+mBorder);
//			mPath.close();
//			canvas.drawPath(mPath, mPaint);
//		}

	}


	private void mDraw(int size,List<PointF> points,Canvas canvas,Path mPath,Paint paint, int COLOR,int[] values){
		mPath.moveTo(points.get(0).x, points.get(0).y);
		float lX = 0, lY = 0;
		for (int i=1; i<size; i++) {
			PointF p = points.get(i);	// current point
			// first control point
			PointF p0 = points.get(i-1);	// previous point
			float x1 = p0.x + lX;
			float y1 = p0.y + lY;

			// second control point
			PointF p1 = points.get(i+1 < size ? i+1 : i);	// next point
			lX = (p1.x-p0.x)/2*SMOOTHNESS;		// (lX,lY) is the slope of the reference line
			lY = (p1.y-p0.y)/2*SMOOTHNESS;
			float x2 = p.x - lX;
			float y2 = p.y - lY;

			// add line
			mPath.cubicTo(x1,y1,x2, y2, p.x, p.y);
		}
		paint.setColor(COLOR);
		paint.setStyle(Style.STROKE);
		canvas.drawPath(mPath, paint);

		// draw circles
		paint.setColor(COLOR);
		paint.setStyle(Style.FILL_AND_STROKE);
		for (PointF point : points) {
			canvas.drawCircle(point.x, point.y, mCircleSize/2, paint);
		}
		TextPaint textPaint = new TextPaint();
		float sp10=DisplayUtil.sp2px(mcontext,10);
		textPaint.setTextSize(sp10);
		textPaint.setColor(COLOR);
		if(values==mValues){
			for(int i=0;i<values.length;i++){
				canvas.drawText(values[i]+"°",points.get(i).x-sp10/2,points.get(i).y-mCircleSize,textPaint);
			}
		}else{
			for(int i=0;i<values.length;i++){
				canvas.drawText(values[i]+"°",points.get(i).x-sp10/2,points.get(i).y+3*mCircleSize,textPaint);
			}
		}

		paint.setStyle(Style.FILL);
		paint.setColor(Color.WHITE);
		for (PointF point : points) {
			canvas.drawCircle(point.x, point.y, (mCircleSize-mStrokeSize)/2, paint);
		}

	}
}
