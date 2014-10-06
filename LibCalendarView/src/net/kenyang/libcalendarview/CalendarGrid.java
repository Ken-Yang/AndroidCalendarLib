/*
 * Copyright 2013 Ken Yang
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 *   
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package net.kenyang.libcalendarview;

import static android.view.View.MeasureSpec.EXACTLY;
import static android.view.View.MeasureSpec.makeMeasureSpec;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class CalendarGrid extends ViewGroup {

	private Paint dividerPaint = null;
//	private int iOldScreenWidth;
	public CalendarGrid(Context context, AttributeSet attrs) {
		super(context, attrs);
		dividerPaint = new Paint();
		dividerPaint.setStrokeWidth(1);
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		
		ViewGroup calendarRow = (ViewGroup) getChildAt(0);
		int iTop = calendarRow.getTop();
		int iBottom = getBottom();
		
		// draw right border
		for (int i = 0; i < 7; i++) {
			int iX = calendarRow.getChildAt(i).getRight() ;
			canvas.drawLine(iX, iTop, iX, iBottom, dividerPaint);
		}

		// draw left border
		canvas.drawLine(0, iTop, 0, iBottom, dividerPaint);

		// draw top border
		for (int i = 0; i < 6; i++) {
			calendarRow = (ViewGroup) getChildAt(i);
			iTop = calendarRow.getTop();
			canvas.drawLine(0, iTop, calendarRow.getRight()-4, iTop, dividerPaint);
		}

		// draw bottom border
		iBottom = calendarRow.getBottom();
		canvas.drawLine(0, iBottom-1, calendarRow.getRight()-4, iBottom-1, dividerPaint);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		int iScreenWidth = MeasureSpec.getSize(widthMeasureSpec);

		/**
		 * Because in the new version, we support marker, we need to recalculate
		 */
//		// do not calculate size twice
//		if (iOldScreenWidth == iScreenWidth) {
//			setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
//			return;
//		}
		
//		iOldScreenWidth = iScreenWidth;
		int iColumnSize = iScreenWidth / 7;
		int iGridHeight = 0; // used for record the grid height

		int iMeasureWidth = makeMeasureSpec(iScreenWidth, EXACTLY);
		int iMeasureHeight = makeMeasureSpec(iColumnSize, EXACTLY);
		
		for (int i = 0; i < 6; i++) {
			View viewChild = getChildAt(i);
			if (viewChild.getVisibility() == VISIBLE) {
				viewChild.measure(iMeasureWidth, iMeasureHeight);
				iGridHeight += viewChild.getMeasuredHeight();
			}
		}
		
		// setting the whole grid size
		setMeasuredDimension(iScreenWidth, iGridHeight);

	}

	@Override
	protected void onLayout(boolean changed, int iLeft, int iTop, int iRight, int iBottom) {
		iTop = 0;
		for (int i = 0; i < 6; i++) {
			View child = getChildAt(i);
			int iRowHeight = child.getMeasuredHeight();
			child.layout(iLeft, iTop, iRight, iTop += iRowHeight);
		}
	}

}
