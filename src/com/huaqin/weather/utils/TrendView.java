package com.huaqin.weather.utils;

import java.util.ArrayList;
import java.util.List;

import com.huaqin.weather.activity.R;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
/**
 * 天气趋势折线图
 * 
 *
 */
public class TrendView extends View {

	private Paint mPointPaint;
	private Paint mTextPaint;
	private Paint mLinePaint1;
	private Paint mLinePaint2;
	private Paint mLinePaint3;
	private Paint mbackLinePaint;

	private int x[] = new int[4];
	private float radius = 8;
	private int h;
	private List<Integer> topTem;
	private List<Integer> lowTem;
	private Bitmap[] topBmps;
	private Bitmap[] lowBmps;
	
	private Context c;

	public TrendView(Context context) {
		super(context);
		this.c = context;
		init();
	}
	public TrendView(Context context, AttributeSet attr) {
		super(context, attr);
		this.c = context;
		init();
	}
	private void init(){
		topBmps = new Bitmap[4];
		lowBmps = new Bitmap[4];
		
		topTem = new ArrayList<Integer>();
		lowTem = new ArrayList<Integer>();
		
		mbackLinePaint = new Paint();
		mbackLinePaint.setColor(Color.WHITE);

		mPointPaint = new Paint();
		mPointPaint.setAntiAlias(true);
		mPointPaint.setColor(Color.WHITE);

		mLinePaint1 = new Paint();
		mLinePaint1.setColor(Color.BLUE);
		mLinePaint1.setAntiAlias(true);
		mLinePaint1.setStrokeWidth(4);
		mLinePaint1.setStyle(Style.FILL);
		
		mLinePaint2 = new Paint();
		mLinePaint2.setColor(Color.YELLOW);
		mLinePaint2.setAntiAlias(true);
		mLinePaint2.setStrokeWidth(4);
		mLinePaint2.setStyle(Style.FILL);
		
		mLinePaint3 = new Paint();
		mLinePaint3.setColor(Color.WHITE);
		
		mLinePaint3.setAntiAlias(true);
		mLinePaint3.setStrokeWidth(4);
		mLinePaint3.setStyle(Style.FILL);

		mTextPaint = new Paint();
		mTextPaint.setAntiAlias(true);
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setTextSize(40F);
		mTextPaint.setTextAlign(Align.CENTER); 
	}
	public void setPosition(int a, int b, int c, int d) {
		x[0] = a;
		x[1] = b;
		x[2] = c;
		x[3] = d;
	}

	public void setWidthHeight(int w, int h){
		x[0] = w/8;
		x[1] = w*3/8;
		x[2] = w*5/8;
		x[3] = w*7/8;
		
		this.h = h;
	}
	public void setTemperature(List<Integer> top, List<Integer> low){
		this.topTem = top;
		this.lowTem = low;
		
		postInvalidate();
	}
	
	public void setBitmap(){
		/** 通过图片id获得Bitmap  **/
		
		topBmps[0] = BitmapFactory.decodeResource(getResources(), R.drawable.sunny64px);
		topBmps[1] = BitmapFactory.decodeResource(getResources(), R.drawable.sunny64px);
		topBmps[2] = BitmapFactory.decodeResource(getResources(), R.drawable.sunny64px);
		topBmps[3] = BitmapFactory.decodeResource(getResources(), R.drawable.sunny64px);
		
		lowBmps[0] = topBmps[0];
		lowBmps[1] = topBmps[1];
		lowBmps[2] = topBmps[2];
		lowBmps[3] = topBmps[3];
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		float space = 0f;
		float space1 = 0f;
		int temspace = 8;
		
		FontMetrics fontMetrics = mTextPaint.getFontMetrics();  
		// 计算文字高度  
		//float fontHeight = fontMetrics.bottom - fontMetrics.top;  
		float fontHeight = fontMetrics.bottom - fontMetrics.top+300;  
		int h = this.h/2;
		int h2 = (int) (h - fontHeight/2);
		int h3 = (int) (h - fontHeight - Constants.picSize);
		 
		int h4 = (int) (h + fontHeight);
		int h5 = (int) (h + fontHeight);
		
		for (int i = 0; i < topTem.size(); i++) {
			space = ( - topTem.get(i)) * temspace+100;
			if(topTem.get(i) != 100){
				if (i != topTem.size() - 1) {
					space1 = ( - topTem.get(i+1)) * temspace+100;
					canvas.drawLine(x[i], h + space+30-570, x[i+1], h + space1+30-570, mLinePaint1);
				}
				
				//canvas.drawBitmap(topBmps[i], x[i]-topBmps[i].getWidth()/2, h3 + space-100, null);
				canvas.drawText(topTem.get(i) + "°", x[i], h2 + space-570, mTextPaint);
				canvas.drawCircle(x[i], h + space+30-570, radius, mPointPaint);
				
			}
		}

		for (int i = 0; i < lowTem.size(); i++) {
			space = (-lowTem.get(i)) * temspace;
			if (i != lowTem.size() - 1) {
				space1 = ( - lowTem.get(i+1)) * temspace;
				canvas.drawLine(x[i], h + space-570, x[i+1], h + space1-570, mLinePaint2);
			} 
			canvas.drawText(lowTem.get(i) + "°", x[i], h4 + space-170-570, mTextPaint);
			canvas.drawCircle(x[i], h + space-570, radius, mPointPaint);
			//canvas.drawBitmap(lowBmps[i], x[i]-lowBmps[i].getWidth()/2, h + space-130, null);
		}
		canvas.drawLine(0, h + space-230, 1500, h + space1-230, mLinePaint3);
	}

}
