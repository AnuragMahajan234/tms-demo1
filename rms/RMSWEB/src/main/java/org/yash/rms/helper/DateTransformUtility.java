package org.yash.rms.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTransformUtility {

	public static String convertFromDateToString(Date dateField) {
		String formattedDate = null;
		if (dateField != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			formattedDate = dateFormat.format(dateField);
		}
		return formattedDate;
	}

}
