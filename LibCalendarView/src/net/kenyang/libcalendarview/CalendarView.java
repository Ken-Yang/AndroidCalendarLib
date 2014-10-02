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
import java.util.Date;
import java.util.Locale;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CalendarView extends LinearLayout implements  android.view.View.OnClickListener{
	
	private TextView tvTitle = null;
	private TextView tvSelected = null;
	private CalendarRow rowHeader = null;
	private CalendarGrid calendarGrid = null;

	public interface OnSelectedListener{
	    /**
	     *
	     * @param date
	     * @param month
	     * @param year
	     */
        public abstract void onSelected(int date, int month, int year);
	}

	public static class TagKey {
	    public static final int keyDateStr = R.id.keyDateStr;
	    public static final int keyDateObj = R.id.keyDateObj;
	}

	private OnSelectedListener listener = null;


	public int iSelectedDate  = 0;
	public int iSelectedMonth = 0;
	public int iSelectedYear  = 0;


	final private DateFormat formatWeek  = new SimpleDateFormat("EEE",Locale.getDefault());
	final private DateFormat formatMonth = new SimpleDateFormat("MMM yyyy",Locale.getDefault());
	
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
	
	public void fnSetOnSelectedListener( OnSelectedListener listener) {
	    this.listener = listener;
	}

	public void fnGenerate(Calendar calendar){
		calendar = (Calendar) calendar.clone();
		int iOriginDay = calendar.get(Calendar.DAY_OF_WEEK);
		
		Calendar today = Calendar.getInstance(Locale.getDefault());
		iSelectedDate = today.get(Calendar.DATE);
		iSelectedMonth = today.get(Calendar.MONTH);
		iSelectedYear = calendar.get(Calendar.YEAR);
		
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
				    RelativeLayout rootView = (RelativeLayout)cr.getChildAt(j);
				    TextView tvTmp = ((TextView)rootView.getChildAt(0));
					View v = (View)rootView.getChildAt(1);

					if (calendar.get(Calendar.MONTH)==iCurrentMonth && calendar.get(Calendar.DAY_OF_WEEK)==(j+1)) {
						tvTmp.setVisibility(View.VISIBLE);
						int iDate = calendar.get(Calendar.DATE);
						v.setVisibility(View.VISIBLE);
						tvTmp.setText(String.valueOf(iDate));
						tvTmp.setTag(TagKey.keyDateStr, (iCurrentMonth+1)+"/"+iDate+"/"+iSelectedYear);
						tvTmp.setTag(TagKey.keyDateObj, calendar.getTime());
						tvTmp.setOnClickListener(this);
						
						// if date match today's date
						if (iDate == iSelectedDate && iCurrentMonth== iSelectedMonth){
							tvTmp.setSelected(true);
							tvSelected = tvTmp;
						}
						calendar.set(Calendar.DATE, iStartDate+=1);
					} else {
					    tvTmp.setClickable(false);
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
	
	public String fnGetSelectedDate() {
	    return tvSelected.getTag(TagKey.keyDateStr).toString();
	}

	public Date fnGetSelectedDateObj() {
	    return (Date) tvSelected.getTag(TagKey.keyDateObj);
	}

	public void fnSetTitleVisibility(int visibility) {
	    tvTitle.setVisibility(View.GONE);
	}
	
	public void fnSetTitle(String strValue){
		tvTitle.setText(strValue);
	}

	public String fnGetTitle() {
	    return tvTitle.getText().toString();
	}

    @Override
    public void onClick(View v) {
        if (tvSelected!=null) {
            tvSelected.setSelected(false);
        }
        v.setSelected(true);
        tvSelected = (TextView) v;
        final String szTmp[] = tvSelected.getTag(TagKey.keyDateStr).toString().split("/");
        iSelectedDate = Integer.parseInt(szTmp[0]);
        iSelectedMonth = Integer.parseInt(szTmp[1]);

        if (listener!=null) {
            listener.onSelected(iSelectedDate,iSelectedMonth,iSelectedYear);
        }

    }

}
