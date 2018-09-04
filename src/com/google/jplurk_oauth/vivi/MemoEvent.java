package com.google.jplurk_oauth.vivi;

import java.util.GregorianCalendar;

public class MemoEvent {
	private GregorianCalendar calendar;
	private long plurk_id;
	public MemoEvent(int year,int month,int date,int hour,int minute,long id){
		calendar = new GregorianCalendar();
		calendar.set(year, month, date,hour,minute);
		plurk_id = id;
	}
	public long getID(){
		return plurk_id;
	}
	public GregorianCalendar getCalendar(){
		return calendar;
	}
}
