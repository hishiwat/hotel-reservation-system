/*
 * Copyright(C) 2007-2013 National Institute of Informatics, All rights reserved.
 */
package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Utility class for date<br>
 * 
 */
public class DateUtil {

	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy/MM/dd");
	static {
		FORMATTER.setLenient(false);
	}

	public static Date createDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * Adjusting time of specified date data to 00:00:00
	 */
	public static Date getMidnightDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return createDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar
				.get(Calendar.DATE));
	}

	public static String convertToString(Date date) {
		return FORMATTER.format(date);
	}

	public static Date convertToDate(String dateStr) {
		Date result = null;
		try {
			if (dateStr != null) {
				result = FORMATTER.parse(dateStr);
			}
		}
		catch (ParseException e) {
		}
		return result;
	}
}
