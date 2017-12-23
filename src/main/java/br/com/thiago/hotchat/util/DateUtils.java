package br.com.thiago.hotchat.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public final class DateUtils {

	public static final String FIRST_HOUR_DAY = "00:00:00";
	public static final String LAST_HOUR_DAY = "23:59:59";

	public static final String FORMAT_DATE = "dd-MM-yyyy HH:mm:ss";

	public static final String EMPTY = " ";

	public static Date formatFirstDateDay(String date) throws ParseException {
		String dateHour = addDateFirstHourDay(date);
		return formatDate(dateHour);
	}

	public static Date formatLastDateDay(String date) throws ParseException {
		String dateHour = addDateLastHourDay(date);
		return formatDate(dateHour);
	}

	public static Date formatDate(String date) throws ParseException {
		DateFormat df = new SimpleDateFormat(FORMAT_DATE);
		return df.parse(date);
	}

	public static boolean isStartDateAfterEndDate(Date dateStart, Date dateEnd) {
		return dateStart.after(dateEnd);
	}

	private static String addDateFirstHourDay(String date) {
		return StringUtils.join(date, EMPTY, FIRST_HOUR_DAY);
	}

	private static String addDateLastHourDay(String date) {
		return StringUtils.join(date, EMPTY, LAST_HOUR_DAY);
	}

}
