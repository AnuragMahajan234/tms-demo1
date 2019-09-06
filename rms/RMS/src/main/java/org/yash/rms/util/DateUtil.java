package org.yash.rms.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {
	private static final String DATE_FORMAT = "MM/dd/yyyy";
	private static final String dateFormat = "dd/MM/yyyy";
	private static final String timeFormat = "HH:mm";
	/**
	 * @param date
	 *            String
	 * @return String
	 */
	public static String GetFormatedDateForDisplay(Date date) {
		String strOutDate = new String();
		if (date == null) {
			return strOutDate;
		}
		try {
			strOutDate = new SimpleDateFormat(DATE_FORMAT).format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strOutDate;
	}

	public static String getStringDate(Date date){
		String strDate = new SimpleDateFormat(dateFormat).format(date);
		return strDate;
	}
	
	public static String getStringTime(Date date){
		String strTime = new SimpleDateFormat(timeFormat).format(date);
		return strTime;
	}
	
	public static Date getDate(String strDate) {
		Date date = null;
		try {
			date = new SimpleDateFormat(DATE_FORMAT).parse(strDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	
	// to calculate week start and end date of complete weeks between selected dates 
		public static Map<String, Date> calculateCompleteWeek(Date startDate, Date endDate) {
			Map<String, Date> dates = new HashMap<String, Date>();
			
			Calendar date1 = Calendar.getInstance();
			date1.setTime(startDate);
			
			Calendar date2 = Calendar.getInstance();
			date2.setTime(endDate);
			
			Date weekStartDate = new Date();
			
			Date weekEndDate = new Date();

			while (date1.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
			    date1.add(Calendar.DATE, 1);
			    
			   // weekStartDate = date1.getTime();
			}
			
			while (date2.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
				date2.add(Calendar.DATE, -1);
			    
			  //  weekEndDate = date2.getTime();
			}
			
			
			System.out.println(weekStartDate + "  " +weekEndDate);
			dates.put("weekStartDate", weekStartDate);
			dates.put("weekEndDate", weekEndDate);
			dates.put("startDate", startDate);
			dates.put("endDate", endDate);
			return dates;
		}
		
		public static java.sql.Date getTodaysDate() {
			java.sql.Date date = null;
			try {
				long millis = System.currentTimeMillis();
				date = new java.sql.Date(millis);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return date;
		}
		public static String getDateInDD_MMM_yyyyFormat(Date date)
		{
			if(null!=date){
			  DateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_PATTERN_4);
	          String dateIn_DD_MMM_yyyyFormat = dateFormatter.format(date);
	          return dateIn_DD_MMM_yyyyFormat;
			}
			return "";
		}
		
		public static Date getDate(String strDate, String dateFormat) {
			Date date = null;
			try {
				date = new SimpleDateFormat(dateFormat).parse(strDate);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return date;
		}
		
		public static String getCurrentDateTime(Date date, String dateFormate){
			if(date == null)
				date = new Date();
			DateFormat dtf = new SimpleDateFormat(dateFormate);
			return dtf.format(date);
		}
		
		public static Date getNextOrBackDate(int futherDays){
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_YEAR, futherDays);
			return calendar.getTime();
		}
		
		public static Date getNextOrBackDate(Date date, int futherDays){
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_YEAR, futherDays);
			return calendar.getTime();
		}
		
		public static String getNextOrBackDate(Date date , String dateFormate, int futherDays){
			if(date == null)
				date = new Date();
			if(dateFormate == null || dateFormate.isEmpty())
				throw new IllegalArgumentException("Date Formate found" + dateFormate);
			String dateStr = "";
			try {
				final Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.DAY_OF_YEAR, futherDays);
				date = calendar.getTime();
				DateFormat dateFormatter = new SimpleDateFormat(dateFormate);
				dateStr = dateFormatter.format(date);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			return dateStr;
		}
		
		
}
