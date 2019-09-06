package org.yash.rms.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.yash.rms.dto.InfogramActiveResourceDTO;

@Component
public class InfogramResourceHelper {
	
public  InfogramActiveResourceDTO convert(List<Map<String, Object>> InfogramActiveResourceDTO) {
		
		ListIterator<Map<String, Object>> iterator = InfogramActiveResourceDTO.listIterator();
		InfogramActiveResourceDTO activeResourceDTO = new InfogramActiveResourceDTO();
		while (iterator.hasNext()) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			Set requestSet = map.entrySet();
			Iterator requestIterator = requestSet.iterator();
			while (requestIterator.hasNext()) {
				Map.Entry mapEntry = (Map.Entry) requestIterator.next();
				
				if(mapEntry.getKey().equals("employeeId")) {
					activeResourceDTO.setEmployeeId(mapEntry.getValue().toString());
				}else if(mapEntry.getKey().equals("firstName")){
					activeResourceDTO.setFirstName((mapEntry.getValue().toString()));
				}else if(mapEntry.getKey().equals("lastName")){
					activeResourceDTO.setLastName(mapEntry.getValue().toString());
				}else if(mapEntry.getKey().equals("middleName")){
					activeResourceDTO.setMiddleName(mapEntry.getValue().toString());
				}else if (mapEntry.getKey().equals("dateOfJoining")) {
					DateFormat format = new SimpleDateFormat("MM/DD/YYYY");
					Date date;
					try {
						date = format.parse(mapEntry.getValue().toString());
						activeResourceDTO.setDateOfJoining(date);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if (mapEntry.getKey().equals("grade")) {
					activeResourceDTO.setGrade(mapEntry.getValue().toString());
					
				} else if (mapEntry.getKey().equals("designation")) {
					
					activeResourceDTO.setDesignation(mapEntry.getValue().toString());
					
				} else if (mapEntry.getKey().equals("emailId")) {
					
					activeResourceDTO.setEmailId(mapEntry.getValue().toString());
					
				} else if (mapEntry.getKey().equals("baseLocation")) {
					
					activeResourceDTO.setBaseLocation(mapEntry.getValue().toString());
					
				} else if (mapEntry.getKey().equals("businessGroup")) {
					
					activeResourceDTO.setBusinessGroup(mapEntry.getValue().toString());
					
				} else if (mapEntry.getKey().equals("businessUnit")) {
					
					activeResourceDTO.setBusinessUnit(mapEntry.getValue().toString());
					
				} else if (mapEntry.getKey().equals("irmEmployeeId")) {
					
					activeResourceDTO.setIrmEmployeeId(mapEntry.getValue().toString());
					
				} else if (mapEntry.getKey().equals("srmEmployeeId")) {
					
					activeResourceDTO.setSrmEmployeeId(mapEntry.getValue().toString());
					
				} else if (mapEntry.getKey().equals("buhEmployeeId")) {
					
					activeResourceDTO.setBuhEmployeeId(mapEntry.getValue().toString());
					
				} else if (mapEntry.getKey().equals("resignedDate")) {
					DateFormat format = new SimpleDateFormat("MM/DD/YYYY");
					Date date;
					try {
						date = format.parse(mapEntry.getValue().toString());
						activeResourceDTO.setResignedDate(date);
					} catch (ParseException e) {
				
					}
				} 				
			} 
		}
		
		return activeResourceDTO;
	}

}
