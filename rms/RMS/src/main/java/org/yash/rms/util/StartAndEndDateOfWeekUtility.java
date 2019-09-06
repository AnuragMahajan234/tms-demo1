package org.yash.rms.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class StartAndEndDateOfWeekUtility {
	 private static final String DATE_FORMAT = "MM/dd/yyyy";

	 public static void main(String args[]){
		 Date inputStartDate = null;
		 Date inputEndDate = null;

		 //	 java.sql.Date sql = new java.sql.Date(parsed.getTime());


		 try {
			 inputStartDate = (Date) new SimpleDateFormat(DATE_FORMAT).parse("01/07/2019");
		 } catch (ParseException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		 }


		 //	inputEndDate=Date.valueOf("2019-03-31");
		 try {
			 inputEndDate = (Date) new SimpleDateFormat(DATE_FORMAT).parse("03/31/2019");
		 } catch (ParseException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		 }

		 getStartAndEndDateMap(inputStartDate, inputEndDate);
	 }
	 public static Map<Date, Date> getStartAndEndDateMap(Date inputStartDate, Date inputEndDate) {

			LocalDate localStartDate = inputStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					//LocalDate.parse(startDate, formatter);
			LocalDate localEndDate = inputEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					//LocalDate.parse(endDate, formatter);

			Map<Date, Date> weekStartAndEndDates = new LinkedHashMap<Date, Date>();
			
		    while(localStartDate.isBefore(localEndDate)){
		    	LocalDate endDate = localStartDate.plusDays(6);
		    	LocalDate startDate =localStartDate;
		    	
		    	
		    	 java.sql.Date sqlEndDate = java.sql.Date.valueOf(endDate);
		    	 java.sql.Date sqlStartDate = java.sql.Date.valueOf(startDate);
		    	 
				System.out.println("Start Date " + sqlEndDate );
					weekStartAndEndDates.put( sqlEndDate,  sqlStartDate);
					
				
				 
		    	LocalDate localDate = localStartDate.plusDays(7);
		    	localStartDate = localDate;
		    }
		    
		    return weekStartAndEndDates;
		  }
	}
