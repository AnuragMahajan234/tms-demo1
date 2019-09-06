/**
 * 
 */
package org.yash.rms.json.mapper;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yash.rms.dto.TimesheetSubmissionDTO;
import org.yash.rms.dto.UserActivityViewDTO;
import org.yash.rms.form.NewTimeSheet;
import org.yash.rms.util.Constants;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

/**
 * @author arpan.badjatiya
 * 
 */
@Component("UserActivityJsonMapper")
public class UserActivityJsonMapper {

	private static final Logger logger = LoggerFactory
			.getLogger(UserActivityJsonMapper.class);

	public String toJsonArray(Collection<NewTimeSheet> collection) {
		try {
			return new JSONSerializer()
					.include("id", 
							"resourceAllocId",
							"activityId",
							"customActivityId",
							"module",
							"subModule",
							"ticketNo",
							"approveStatus",
							"submitStatus",
							"d1Hours", "d2Hours", "d3Hours", "d4Hours", "d5Hours", "d6Hours", "d7Hours", 
							"d1Comment", "d2Comment", "d3Comment", "d4Comment", "d5Comment", "d6Comment", "d7Comment")
					.exclude("*").serialize(collection);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("EXCEPTION OCCURED IN MAPPING NewTimeSheet LIST TO JSON ARRAY "
					+ ex.getMessage());
			return "";
		}
	}
	
	public String toTimesheetJsonArray(Collection<TimesheetSubmissionDTO> collection) {
		try {
			return new JSONSerializer()
					.include("id", 
							"employeeId",
							"projectId",
							"projectName", 
							"resourceAllocationId", 
							"weekEndDate", 
							"weekStartDate", 
							"totalHours", 
							"status")
					.transform(new DateTransformer(Constants.DATE_PATTERN_4),Date.class)
					.exclude("*").serialize(collection);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("EXCEPTION OCCURED IN MAPPING TimesheetSubmissionDTO LIST TO JSON ARRAY "
					+ ex.getMessage());
			return "";
		}
	}
	public String toUserActicityViewDTOJsonArray(Collection<UserActivityViewDTO> collection) {
		try {
			return new JSONSerializer()
					.include("id", "employeeIdHidden", "employeeId.employeeId",
							"employeeId.yashEmpId", "resourceAllocId.id",
							"resourceAllocId.allocEndDate",
							"resourceAllocId.allocStartDate",
							"resourceAllocId.projectId.projectName",
							"weekEndDate",
							"approveStatus", "repHrsForProForWeekEndDate",
							 "activityId.activityId", "activityId.activityType" , "module","subModule","ticketNo", "d1Hours", 
							"d2Hours", "d3Hours", "d4Hours", "d5Hours",
							"d6Hours", "d7Hours", "d1Comment", "d2Comment",
							"d3Comment", "d4Comment", "d5Comment", "d6Comment",
							"d7Comment", "weekStartDate", 
							"weekStartDateHidden", "weekEndDateHidden",
							 "viewFlag","rejectionRemarks","ticketPriority","ticketStatus")
					.exclude("*")
					.transform(new DateTransformer(Constants.DATE_PATTERN_4),
							Date.class).serialize(collection);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("EXCEPTION OCCURED IN MAPPING USER ACTIVITY LIST TO JSON ARRAY "
					+ ex.getMessage());
			return "";
		}
	}
	
}
