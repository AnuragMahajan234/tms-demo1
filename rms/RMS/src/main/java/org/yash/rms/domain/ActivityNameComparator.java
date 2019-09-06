package org.yash.rms.domain;

import java.util.Comparator;

public class ActivityNameComparator implements Comparator<Activity>{

	public int compare(Activity activity1, Activity activity2) {
		
		return activity1.getActivityName().compareTo(activity2.getActivityName());
	}

}
