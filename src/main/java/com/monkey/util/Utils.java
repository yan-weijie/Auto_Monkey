package com.monkey.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {
	
	public static String getTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		Calendar cal = Calendar.getInstance();
		String time = format.format(cal.getTime()).substring(0,19);
		
		return time;
	}
}
