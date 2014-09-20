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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CalendarView extends LinearLayout {
	
	private TextView tvTitle = null;
	private TextView tvSelected = null;
	private CalendarRow rowHeader = null;
	private CalendarGrid calendarGrid = null;

	private DateFormat formatWeek  = new SimpleDateFormat("EEE",Locale.getDefault());
	private DateFormat formatMonth = new SimpleDateFormat("MMM yyyy",Locale.getDefault());
	
	public CalendarView(Context cxt, AttributeSet attrs) {
		super(cxt, attrs);
		View.inflate(cxt, R.layout.month, this);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		rowHeader 		= (CalendarRow) findViewById(R.id.crHeader);
		calendarGrid 	= (CalendarGrid) findViewById(R.id.calendarGrid);
		tvTitle 		= (TextView) findViewById(R.id.tvTitle);
	}
	
	public void fnGenerate(Calendar calendar, OnClickListener listener){
		calendar = (Calendar) calendar.clone();
		int iOriginDay = calendar.get(Calendar.DAY_OF_WEEK);
		
		Calendar today = Calendar.getInstance(Locale.getDefault());
		int iTodayDate = today.get(Calendar.DATE);
		int iTodayMonth = today.get(Calendar.MONTH);
		
		fnSetTitle(formatMonth.format(calendar.getTime()));
		
		// create header
		for (int i = 0; i < 7; i++) {
			calendar.set(Calendar.DAY_OF_WEEK, i+1);
			((TextView)rowHeader.getChildAt(i)).setText(formatWeek.format(calendar.getTime()));
		}
		calendar.set(Calendar.DAY_OF_WEEK, iOriginDay);
		
		// fill date
		int iStartDate = 1;
		int iCurrentMonth = calendar.get(Calendar.MONTH);

		calendar.set(Calendar.DATE, iStartDate);
		
		
		for (int i = 0; i < 6; i++) {
			CalendarRow cr = (CalendarRow) calendarGrid.getChildAt(i);
			
			if (calendar.get(Calendar.WEEK_OF_MONTH)<i){
				cr.setVisibility(GONE);
			} else {
				cr.setVisibility(VISIBLE);
				for (int j = 0; j < 7; j++) {
					TextView tvTmp = ((TextView)cr.getChildAt(j));
					if (calendar.get(Calendar.MONTH)==iCurrentMonth && calendar.get(Calendar.DAY_OF_WEEK)==(j+1)) {
						tvTmp.setVisibility(View.VISIBLE);
						int iDate = calendar.get(Calendar.DATE);
						tvTmp.setText(String.valueOf(iDate));
						tvTmp.setTag((iCurrentMonth+1)+"/"+iDate);

						if (listener!=null){
							tvTmp.setOnClickListener(listener);
						}
						
						// if date match today's date
						if (iDate == iTodayDate && iCurrentMonth== iTodayMonth){
							tvTmp.setSelected(true);
							tvSelected = tvTmp;
						}
						calendar.set(Calendar.DATE, iStartDate+=1);
					} else {
						tvTmp.setVisibility(View.VISIBLE);
					}
				}
			}
		}
		
	}
	
	public void fnSetSelected(View view) {
	    tvSelected.setSelected(false);
	    view.setSelected(true);
	    tvSelected = (TextView) view;
	}
	

	public void fnSetTitleVisibility(int visibility) {
	    tvTitle.setVisibility(View.GONE);
	}
	
	public void fnSetTitle(String strValue){
		tvTitle.setText(strValue);
	}

}
