package com.gregashby.aimsio.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for converting strings to dates based on different date
 * resolutions and format requirements.
 * 
 * For example, if a dateResolution is "Month", then only the year and month
 * should be expected in strings. As well, the MySql format for date strings is
 * different than for java (e.g. %Y-%m vs yyyy-MM).
 *
 */
public class DateHandler {

	public final static String DEFAULT_RESOLUTION = "Day";

	public static String getDateFormatForJava(String resolution) {
		resolution = resolution == null ? "" : resolution;
		switch (resolution) {
		case "Year":
			return "yyyy";
		case "Month":
			return "yyyy-MM";
		case "Day":
			return "yyyy-MM-dd";
		default:
			return "yyyy-MM-dd";
		}
	}

	public static String getDateFormatForSql(String resolution) {
		resolution = resolution == null ? "" : resolution;
		switch (resolution) {
		case "Year":
			return "%Y";
		case "Month":
			return "%Y-%m";
		case "Day":
			return "%Y-%m-%d";
		default:
			return "%Y-%m-%d";
		}
	}

	public static String getDayAsString(Date date) {
		return new SimpleDateFormat(getDateFormatForJava("Day")).format(date);
	}

	public static Date convertDayString(String dateStr) throws ParseException {
		return dateStr == null ? null : new SimpleDateFormat(getDateFormatForJava("Day")).parse(dateStr);
	}

	public static Date convertStringToDate(String resolution, String dateString) throws ParseException {
		return new SimpleDateFormat(getDateFormatForJava(resolution)).parse(dateString);
	}
}