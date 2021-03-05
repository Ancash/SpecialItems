package de.ancash.specialitems.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Timer {

	
	private double sec_before;
	
	private double f_sec;
	
	public void start() {
		String ms = getCurrentTimeStampMillis();
		sec_before = Double.valueOf(ms);
	}
	
	public void stop() {
		String ms = getCurrentTimeStampMillis();
		double d = Double.valueOf(ms);
		f_sec = d-sec_before;
	}
	
	public void reset() {
		f_sec = 0;
	}
	
	public Double getTime() {
		return f_sec;
	}
	
	public static String getCurrentTimeStampMillis() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("ss.SSS");
		Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}
}
