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

import static android.view.View.MeasureSpec.AT_MOST;
import static android.view.View.MeasureSpec.EXACTLY;
import static android.view.View.MeasureSpec.makeMeasureSpec;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class CalendarRow extends ViewGroup {

	private int iColumnWidth = 0;
	private int iColumnHeight = 0;
//	private int iOldScreenWidth;
	
	public CalendarRow(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int iScreenWidth 	= MeasureSpec.getSize(widthMeasureSpec);
		
		/**
		 * Because in the new version, we support marker, we need to recalculate
		 */
//		// do not calculate size twice
//		if (iOldScreenWidth == iScreenWidth && iColumnHeight != 0) {
//			setMeasuredDimension(getMeasuredWidth(), iColumnHeight);
//			return;
//		}
		
		iColumnWidth 			= iScreenWidth / 7 ;
		int iMeasureWidth 		= makeMeasureSpec(iColumnWidth, EXACTLY);
		int iMeasureHeight 		= (getId()==R.id.crHeader) ? makeMeasureSpec(iColumnWidth, AT_MOST) : iMeasureWidth;
		
		// setting each columns size
		View viewChild = null;
		for (int i = 0; i < 7; i++) {
			viewChild = getChildAt(i);
			viewChild.measure(iMeasureWidth, iMeasureHeight);
		}
		iColumnHeight = viewChild.getMeasuredHeight();
		
		// setting a whole row size
		setMeasuredDimension(iScreenWidth, iColumnHeight);
	}

	@Override
	protected void onLayout(boolean bChanged, int iLeft, int iTop, int iRight, int iBottom) {
		int iColumnHeight = iBottom - iTop;
		
		for (int i = 0; i < 7; i++) {
	        View v = getChildAt(i);
	        
	        v.layout(iColumnWidth*i,
	                    0, 
	                    (i+1) * iColumnWidth ,
	                    iColumnHeight);			 // bottom
		}
	}

}
