
package org.yash.rms.namedquery;

import java.util.Date;
import java.util.List;

public class RmsNamedQuery {

	public static String getPrimarySkillByResourceQuery() {

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT res.employee_id AS Employee_id ,skp.skill_id AS Skill_Id,skp.skill AS Skill_Name,skp.primaryExperience AS primaryExperience");
		sql.append(" ,skp.skill_type AS Skill_Type ,skp.name AS Rating_Name,skp.id AS Rating_Id,skp.skpID As skill_pk_id FROM resource AS res");
		sql.append(" JOIN (SELECT srp.id as skpID,srp.resource_id,srp.skill_id,s.skill,s.skill_type,rt.name,rt.id,srp.primaryExperience FROM skill_resource_primary AS srp");
		sql.append(" JOIN Skills AS s ON s.id = srp.skill_id LEFT JOIN rating AS rt ON rt.id = srp.rating ) AS skp");
		sql.append(" ON res.employee_id = skp.resource_id WHERE res.employee_id=:employeeId ORDER BY res.employee_id  ASC");
		return sql.toString();
	}

	public static String getPrimarySkillByUserProfileQuery() {

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT usp.id,skp.skill_id AS Skill_Id, skp.skill AS skill_name, skp.skill_type AS skill_type,");
		sql.append(" skp.name AS rating_name, skp.id AS rating_id,skp.skillPkId AS skill_Pk_Id FROM user_profile usp");
		sql.append(" JOIN (SELECT spr.id AS skillPkId ,spr.profile_id,spr.skill_id,s.skill,s.skill_type,rt.name,rt.id FROM skill_profile_primary AS spr");
		sql.append(" JOIN skills AS s ON s.id=spr.skill_id LEFT JOIN rating AS rt ON rt.id = spr.rating) AS skp ");
		sql.append(" ON usp.id= skp.profile_id WHERE usp.yash_emp_id=:employeeId ORDER BY usp.id ASC ");
		sql.append("");
		return sql.toString();
	}

	public static String getSecondarySkillByUserProfilesQuery() {

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT usp.id,skp.skill_id AS Skill_Id, skp.skill AS skill_name, skp.skill_type AS skill_type, ");
		sql.append(" skp.name AS rating_name, skp.id AS rating_id,skp.skillPkId As skill_Pk_Id FROM `user_profile` usp");
		sql.append(" JOIN (SELECT spr.id AS skillPkId,spr.profile_id,spr.skill_id,s.skill,s.skill_type,rt.name,rt.id FROM  `skill_profile_secondary` AS spr");
		sql.append(" JOIN `skills` AS s ON s.id=spr.skill_id LEFT JOIN `rating` AS rt ON rt.id = spr.rating) AS skp");
		sql.append(" ON usp.id= skp.profile_id  WHERE usp.yash_emp_id=:employeeId ORDER BY usp.id ASC");
		sql.append("");
		return sql.toString();

	}

	public static String getSecondarySkillByResourceQuery() {

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT res.employee_id AS Employee_id ,skp.skill_id AS Skill_Id,skp.skill AS Skill_Name,skp.secondaryExperience AS SecondaryExperience,");
		sql.append(" skp.skill_type AS Skill_Type ,skp.name AS Rating_Name,skp.id AS Rating_Id,skp.skpID As skill_pk_id FROM resource AS res");
		sql.append(" JOIN (SELECT srp.id as skpID,srp.resource_id,srp.skill_id,s.skill,s.skill_type,rt.name,rt.id,srp.secondaryExperience FROM `skill_resource_secondary` AS srp");
		sql.append(" JOIN Skills AS s ON s.id = srp.skill_id LEFT JOIN rating AS rt ON rt.id = srp.rating ) AS skp ");
		sql.append(" ON res.employee_id = skp.resource_id WHERE res.employee_id=:employeeId ORDER BY res.employee_id  ASC");
		sql.append("");
		return sql.toString();
	}

	/**** My Ticket Pending Status
	 * 
	 * @param regionIDs ****/
	public static String getMyPerformanceCountFiltered(List<Integer> regionIDs) { // 1**

		StringBuffer sql = new StringBuffer();

		sql.append("SELECT emp_id, module, assignee_name,COUNT(CASE WHEN acknowledged_date IS NULL AND group_name = 'Tier 2' THEN '' END) AS acknowledged_count,COUNT(CASE WHEN (problem_status_flag = 'No' AND group_name = 'Tier 2')THEN problem_status_flag END) AS problem_management_count, COUNT(CASE WHEN (req_complete_flag = 'No' AND (acknowledged_date IS NOT NULL OR acknowledged_date <> '0000-00-00 00:00:00')AND group_name = 'Tier 2') THEN req_complete_flag END) AS req_complete_flag_count, COUNT(CASE WHEN (analysis_complete_flag = 'No' AND (req_complete_flag = 'Yes' OR req_complete_flag = 'N.A.') AND group_name = 'Tier 2') THEN analysis_complete_flag    END) AS analysis_complete_flag_count, COUNT(CASE WHEN (solution_developed_flag = 'No' AND (analysis_complete_flag = 'Yes' OR analysis_complete_flag = 'N.A.') AND group_name = 'Tier 2' ) THEN solution_developed_flag END ) AS solution_developed_flag_count, COUNT(CASE WHEN (solution_review_flag = 'No' AND (solution_developed_flag = 'Yes' OR solution_developed_flag = 'N.A.') AND group_name = 'Tier 2')THEN solution_review_flag END) AS solution_review_flag_count, COUNT(CASE WHEN (solution_accepted_flag = 'No' AND (solution_ready_for_review = 'Yes' OR solution_ready_for_review = 'N.A.')AND group_name = 'Tier 2') THEN solution_accepted_flag END) AS solution_accepted_flag_count,COUNT(CASE WHEN (customer_approval_flag = 'No' AND (solution_accepted_flag = 'Yes' OR solution_accepted_flag = 'N.A.') AND group_name = 'Tier 2') THEN customer_approval_flag END) AS customer_approval_flag_count ,COUNT(CASE WHEN (`group_name` != 'Tier 2' )THEN group_name END) AS group_count  , COUNT(CASE WHEN (reviewer_id =:employee_id AND solution_review_date IS NULL AND solution_ready_for_review = 'Yes' AND group_name = 'Tier 2') THEN '' END) AS for_my_review_count, COUNT(CASE WHEN (solution_review_date IS NULL AND solution_ready_for_review = 'Yes' AND `group_name` = 'Tier 2') THEN '' END) AS sent_for_review_count FROM ca_ticket WHERE  (priority=:priority OR :priority ='-1') AND (module=:module OR :module= '-1' ) AND (landscape_id=:landscape OR :landscape='-1' ) AND (emp_id=:assignee OR :assignee= '-1' )AND (reviewer_id=:reviewer OR :reviewer= '-1' ) AND ((DATE(creation_date)>=:fromTime OR :fromTime IS NULL) AND (DATE(creation_date)<:toTime OR :toTime IS NULL)) AND emp_id = :employee_id");
		if (regionIDs.size() > 0) {
			sql.append(" AND region in (:region)");
		}
		sql.append(" GROUP BY module,emp_id");
		return sql.toString();
	}

	/**** My Ticket Pending Status ****/

	// (Shikhi :: adding code for- My review)
	public static String getMyPerformanceCount() {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT emp_id, module, assignee_name,COUNT(CASE WHEN acknowledged_date IS NULL AND `group_name` = 'Tier 2' THEN '' END) AS acknowledged_count,COUNT(CASE WHEN (problem_status_flag = 'No' AND `group_name` = 'Tier 2')THEN problem_status_flag END) AS problem_management_count, COUNT(CASE WHEN (req_complete_flag = 'No' AND (acknowledged_date IS NOT NULL OR `acknowledged_date` <> '0000-00-00 00:00:00')AND `group_name` = 'Tier 2') THEN req_complete_flag END) AS req_complete_flag_count, COUNT(CASE WHEN (analysis_complete_flag = 'No' AND (req_complete_flag = 'Yes' OR req_complete_flag = 'N.A.') AND `group_name` = 'Tier 2') THEN analysis_complete_flag    END) AS analysis_complete_flag_count, COUNT(CASE WHEN (solution_developed_flag = 'No' AND (analysis_complete_flag = 'Yes' OR analysis_complete_flag = 'N.A.') AND `group_name` = 'Tier 2' ) THEN solution_developed_flag END ) AS solution_developed_flag_count, COUNT(CASE WHEN (solution_review_flag = 'No' AND (solution_developed_flag = 'Yes' OR solution_developed_flag = 'N.A.') AND `group_name` = 'Tier 2')THEN solution_review_flag END) AS solution_review_flag_count, COUNT(CASE WHEN (solution_accepted_flag = 'No' AND (solution_ready_for_review = 'Yes' OR solution_ready_for_review = 'N.A.')AND `group_name` = 'Tier 2') THEN solution_accepted_flag END) AS solution_accepted_flag_count,COUNT(CASE WHEN (customer_approval_flag = 'No' AND (solution_accepted_flag = 'Yes' OR solution_accepted_flag = 'N.A.') AND `group_name` = 'Tier 2') THEN customer_approval_flag END) AS customer_approval_flag_count ,COUNT(CASE WHEN (`group_name` != 'Tier 2' )THEN group_name END) AS group_count, COUNT(CASE WHEN (reviewer_id =:employee_id AND solution_review_date IS NULL AND solution_ready_for_review = 'Yes' AND `group_name` = 'Tier 2') THEN '' END) AS for_my_review_count, COUNT(CASE WHEN (solution_review_date IS NULL AND solution_ready_for_review = 'Yes' AND `group_name` = 'Tier 2') THEN '' END) AS sent_for_review_count FROM ca_ticket WHERE emp_id =:employee_id GROUP BY module,  emp_id ");
		return sql.toString();
	}

	/**** Team Ticket Pending Status
	 * 
	 * @param regionIDs ****/
	public static String getMyTeamPerformanceCountFiltered(List<Integer> regionIDs) {

		StringBuffer sql = new StringBuffer();

		sql.append("SELECT emp_id, module, assignee_name,COUNT( CASE WHEN acknowledged_date IS NULL AND `group_name` = 'Tier 2' THEN '' END ) AS acknowledged_count, COUNT( CASE WHEN ( problem_status_flag = 'No' AND `group_name` = 'Tier 2'  ) THEN problem_status_flag   END  ) AS problem_management_count, COUNT( CASE WHEN ( req_complete_flag = 'No' AND ( acknowledged_date IS NOT NULL OR `acknowledged_date` <> '0000-00-00 00:00:00' ) AND `group_name` = 'Tier 2')THEN req_complete_flag   END ) AS req_complete_flag_count, COUNT(CASE WHEN ( analysis_complete_flag = 'No'  AND ( req_complete_flag = 'Yes'  OR req_complete_flag = 'N.A.'  )  AND `group_name` = 'Tier 2'  )  THEN analysis_complete_flag   END ) AS analysis_complete_flag_count, COUNT( CASE WHEN ( solution_developed_flag = 'No'  AND ( analysis_complete_flag = 'Yes' OR analysis_complete_flag = 'N.A.' )   AND `group_name` = 'Tier 2'  )THEN solution_developed_flag END ) AS solution_developed_flag_count, COUNT(  CASE  WHEN (  solution_review_flag = 'No'  AND ( solution_developed_flag = 'Yes' OR solution_developed_flag = 'N.A.' ) AND `group_name` = 'Tier 2'  ) THEN solution_review_flag END  ) AS solution_review_flag_count, COUNT(CASE WHEN (solution_accepted_flag = 'No'  AND (  solution_ready_for_review = 'Yes' OR solution_ready_for_review = 'N.A.'  )AND `group_name` = 'Tier 2' )THEN solution_accepted_flag   END) AS solution_accepted_flag_count, COUNT(  CASE  WHEN (  customer_approval_flag = 'No'  AND (solution_accepted_flag = 'Yes'  OR solution_accepted_flag = 'N.A.')   AND `group_name` = 'Tier 2'  )  THEN customer_approval_flag  END ) AS customer_approval_flag_count, COUNT( CASE WHEN (`group_name` != 'Tier 2') THEN group_name   END ) AS group_count, COUNT(CASE WHEN (solution_review_date IS NULL AND solution_ready_for_review = 'Yes' AND `group_name` = 'Tier 2') THEN '' END) AS for_review_count FROM resource re LEFT JOIN ca_ticket ca ON ( re.employee_id = ca.emp_id ) INNER JOIN project p ON p.id = ca.module WHERE (((current_reporting_manager =:employee_id OR current_reporting_manager_two =:employee_id) AND (re.employee_id <> ca.`reviewer_id`)) OR (module IN (SELECT id FROM project WHERE offshore_del_mgr =:employee_id))) AND (`priority`=:priority OR :priority ='-1') AND (module=:module OR :module= '-1' ) AND (landscape_id=:landscape OR :landscape= '-1' ) AND (emp_id=:assignee OR :assignee= '-1' )AND (reviewer_id=:reviewer OR :reviewer= '-1' ) AND  ((DATE(creation_date)>=:fromTime OR :fromTime IS NULL) AND (DATE(creation_date)<:toTime OR :toTime IS NULL))");
		if (regionIDs.size() > 0) {
			sql.append(" AND region in (:region)");
		}
		sql.append(" GROUP BY module,emp_id");
		return sql.toString();

	}

	/**** Team Ticket Pending Status ****/
	// Shikhi :: code added for _mY review
	public static String getMyTeamPerformanceCount() {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT emp_id, module, assignee_name,COUNT( CASE WHEN acknowledged_date IS NULL AND `group_name` = 'Tier 2' THEN '' END ) AS acknowledged_count, COUNT( CASE WHEN ( problem_status_flag = 'No' AND `group_name` = 'Tier 2'  ) THEN problem_status_flag   END  ) AS problem_management_count, COUNT( CASE WHEN ( req_complete_flag = 'No' AND ( acknowledged_date IS NOT NULL OR `acknowledged_date` <> '0000-00-00 00:00:00' ) AND `group_name` = 'Tier 2')THEN req_complete_flag   END ) AS req_complete_flag_count, COUNT(CASE WHEN ( analysis_complete_flag = 'No'  AND ( req_complete_flag = 'Yes'  OR req_complete_flag = 'N.A.'  )  AND `group_name` = 'Tier 2'  )  THEN analysis_complete_flag   END ) AS analysis_complete_flag_count, COUNT( CASE WHEN ( solution_developed_flag = 'No'  AND ( analysis_complete_flag = 'Yes' OR analysis_complete_flag = 'N.A.' )   AND `group_name` = 'Tier 2'  )THEN solution_developed_flag END ) AS solution_developed_flag_count, COUNT(  CASE  WHEN (  solution_review_flag = 'No'  AND ( solution_developed_flag = 'Yes' OR solution_developed_flag = 'N.A.' ) AND `group_name` = 'Tier 2'  ) THEN solution_review_flag END  ) AS solution_review_flag_count, COUNT(CASE WHEN (solution_accepted_flag = 'No'  AND (  solution_ready_for_review = 'Yes' OR solution_ready_for_review = 'N.A.'  )AND `group_name` = 'Tier 2' )THEN solution_accepted_flag   END) AS solution_accepted_flag_count, COUNT(  CASE  WHEN (  customer_approval_flag = 'No'  AND (solution_accepted_flag = 'Yes'  OR solution_accepted_flag = 'N.A.')   AND `group_name` = 'Tier 2'  )  THEN customer_approval_flag  END ) AS customer_approval_flag_count, COUNT( CASE WHEN (`group_name` != 'Tier 2') THEN group_name   END ) AS group_count, COUNT(CASE WHEN (solution_review_date IS NULL AND solution_ready_for_review = 'Yes' AND `group_name` = 'Tier 2') THEN '' END) AS for_review_count FROM resource re LEFT JOIN ca_ticket ca ON ( re.employee_id = ca.emp_id ) INNER JOIN project p ON p.id = ca.module WHERE (current_reporting_manager =:employee_id OR current_reporting_manager_two =:employee_id) AND (re.employee_id <> ca.`reviewer_id`) OR module IN (SELECT id FROM project WHERE offshore_del_mgr =:employee_id)GROUP BY module, emp_id ");

		return sql.toString();
	}

	/******** My Team Performance *************/
	public static String getMyTeamPerformanceStatus() {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT ca.*  FROM resource re LEFT JOIN ca_ticket ca ON (re.employee_id=ca.emp_id OR re.employee_id=ca.reviewer_id) INNER JOIN project p ON p.id=ca.module WHERE (current_reporting_manager=:currentReportingManager OR current_reporting_manager_two=:currentReportingManagerTwo ) AND (re.employee_id<>ca.`reviewer_id`) OR module IN (SELECT id FROM project WHERE offshore_del_mgr=:offshoreDelMgr) AND group_name = 'Tier 2' ");

		return sql.toString();
	}

	/******** My Team Performance *************/
	public static String getMyTeamPerformanceStatusFiltered(List<Integer> regionIDs) {

		StringBuffer sql = new StringBuffer();

		sql.append("SELECT DISTINCT ca.* FROM resource re LEFT JOIN ca_ticket ca ON   (re.employee_id=ca.emp_id OR re.employee_id=ca.reviewer_id) INNER JOIN  project p ON p.id=ca.module   WHERE  (priority =:priority OR :priority='-1') AND  (emp_id =:assignee OR :assignee='-1')  AND (landscape_id =:landscape OR :landscape='-1') AND (module =:module OR :module='-1')  AND(reviewer_id =:reviewer OR :reviewer='-1')   AND ((Date(creation_date)>=:fromTime OR :fromTime IS NULL) AND (Date(creation_date)<:toTime OR :toTime IS NULL))  AND ((current_reporting_manager=:currentReportingManager OR current_reporting_manager_two=:currentReportingManagerTwo )  OR (module IN (SELECT id FROM project WHERE offshore_del_mgr=:offshoreDelMgr)))");
		if (regionIDs.size() > 0) {
			sql.append(" AND region in (:region)");
		}
		return sql.toString();
	}

	public static String findModuleByEmployee() {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM Project WHERE id IN (SELECT project_id FROM `resource_allocation` WHERE `employee_id` =:employee_id) AND module_ost IS NOT NULL");
		return sql.toString();
	}

	public static String getTicketsForReview() {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ca.* FROM resource re LEFT JOIN ca_ticket ca ON (re.employee_id=ca.emp_id OR re.employee_id=ca.reviewer_id) INNER JOIN project p ON p.id=ca.module WHERE (`current_reporting_manager`=:employeeId OR current_reporting_manager_two=:employeeId OR employee_id=:employeeId ) OR module IN (SELECT id FROM project WHERE `offshore_del_mgr`=:employeeId)");
		return sql.toString();
	}

	public static String getEmployeeByModuleQuery() {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM resource  WHERE `employee_id` IN (SELECT `employee_id` FROM `resource_allocation` WHERE project_id =:projectId)");
		return sql.toString();
	}

	public static String getAllRegionIdsfromRegionName() {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id FROM region WHERE region_name =:regionName");
		return sql.toString();
	}

	public static String getForMyReviewCount() {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT emp_id, module,COUNT(CASE WHEN ( solution_review_date IS NULL AND solution_ready_for_review = 'Yes' AND `group_name` = 'Tier 2') THEN '' END ) AS for_my_review_count FROM ca_ticket WHERE reviewer_id =:employeeId AND emp_id!=:employeeId GROUP BY module,emp_id");
		return sql.toString();
	}

	public static String getForMyReviewCountFiltered(List<Integer> regionIDs) {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT emp_id, module,COUNT( CASE WHEN (   solution_review_date IS NULL AND solution_ready_for_review = 'Yes'  AND `group_name` = 'Tier 2'   ) THEN ''  END ) AS for_my_review_count FROM ca_ticket WHERE  ( priority = :priority OR :priority = '-1' ) AND (module = :module OR :module = '-1') AND ( landscape_id = :landscape OR :landscape = '-1' ) AND ( emp_id = :assignee OR :assignee = '-1' ) AND ( reviewer_id = :reviewer OR :reviewer = '-1' ) AND ( ( DATE(creation_date) >= :fromTime OR :fromTime IS NULL ) AND ( DATE(creation_date) < :toTime OR :toTime IS NULL ) ) AND (reviewer_id =:employeeId AND emp_id!=:employeeId)");
		if (regionIDs.size() > 0) {
			sql.append(" AND region in (:region)");
		}
		sql.append(" GROUP BY module,emp_id");
		return sql.toString();
	}

	public static String getallUserInfoStatus() {

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT re.employee_id,first_name,last_name, COUNT( CASE  WHEN STATUS = 'P'   AND ua.id IN (SELECT ua.id FROM `user_activity` ua, resource_allocation ra ");
		sql.append(" WHERE ua.`res_alloc_id` = ra.`id` AND ra.`employee_id` = :empID   GROUP BY ua.creation_timestamp )   THEN ua.`id` END) AS Pending, ");
		sql.append(" COUNT(   CASE  WHEN STATUS = 'A'   AND ua.id IN (SELECT ua.id FROM `user_activity` ua, resource_allocation ra   WHERE ua.`res_alloc_id` = ra.`id`  AND ra.`employee_id` = :empID ");
		sql.append(" GROUP BY ua.creation_timestamp ) THEN ua.`id`  END ) AS Approved, COUNT(  CASE  WHEN STATUS = 'R'   AND ua.id IN (SELECT ua.id FROM `user_activity` ua, resource_allocation ra  ");
		sql.append(" WHERE ua.`res_alloc_id` = ra.`id`  AND ra.`employee_id` = :empID  GROUP BY ua.creation_timestamp ) THEN ua.`id`  END) AS Rejected,COUNT(    CASE    WHEN STATUS = 'P'       AND ua.id IN (SELECT ua.id FROM `user_activity` ua, resource_allocation ra  WHERE ua.`res_alloc_id` = ra.`id` AND ra.`employee_id` = :empID  GROUP BY ua.creation_timestamp ) THEN ua.`id` ");
		sql.append(" END) + COUNT(CASE WHEN STATUS = 'A'   AND ua.id IN (SELECT ua.id FROM `user_activity` ua, resource_allocation ra  WHERE ua.`res_alloc_id` = ra.`id`  AND ra.`employee_id` = :empID  ");
		sql.append(" GROUP BY ua.creation_timestamp ) THEN ua.`id` END) + COUNT(  CASE  WHEN STATUS = 'R'   AND ua.id IN (SELECT ua.id AS saved FROM `user_activity` ua, resource_allocation ra  ");
		sql.append(" WHERE ua.`res_alloc_id` = ra.`id` AND ra.`employee_id` = :empID  GROUP BY ua.creation_timestamp ) THEN ua.`id` END) AS Submitted,(  SELECT COUNT(pq.saved) FROM (SELECT ua.id AS saved FROM `user_activity` ua, resource_allocation ra  WHERE ua.`res_alloc_id` = ra.`id` AND ra.`employee_id` = :empID  GROUP BY ua.creation_timestamp) pq) AS Saved FROM  `user_activity` ua, resource_allocation ra, resource re  WHERE ua.`res_alloc_id` = ra.`id`  AND re.employee_id = ra.`employee_id`  AND re.employee_id = :empID ");
		return sql.toString();
	}

	public static String getallTimeSheetStatus() {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM ((SELECT DISTINCT week_end_date,STATUS FROM `vw_user_activity` ua INNER JOIN `resource_allocation` ra ON ua.`resource_alloc_id`=ra.`id` ");
		sql.append("WHERE STATUS='A' AND ra.`employee_id`=:empID AND week_end_date IN (SELECT week_end_date FROM ( (SELECT DISTINCT week_end_date FROM `vw_user_activity` u ");
		sql.append("INNER JOIN resource_allocation r ON u.resource_alloc_id=r.id WHERE STATUS='A' AND r.employee_id =:empID ORDER BY week_end_date DESC LIMIT 4)tab)) ");
		sql.append("ORDER BY week_end_date DESC) UNION (SELECT DISTINCT week_end_date,STATUS FROM `vw_user_activity` ua INNER JOIN `resource_allocation` ra ON ua.`resource_alloc_id`=ra.`id` ");
		sql.append("WHERE STATUS='P' AND ra.`employee_id`=:empID AND week_end_date IN (SELECT week_end_date FROM ( (SELECT DISTINCT week_end_date FROM `vw_user_activity` u ");
		sql.append("INNER JOIN resource_allocation r ON u.resource_alloc_id=r.id WHERE STATUS='P' AND r.employee_id =:empID ORDER BY week_end_date DESC LIMIT 4)tab)) ");
		sql.append("ORDER BY week_end_date DESC) UNION (SELECT DISTINCT week_end_date,STATUS FROM `vw_user_activity` ua INNER JOIN `resource_allocation` ra ");
		sql.append("ON ua.`resource_alloc_id`=ra.`id` WHERE STATUS='R' AND ra.`employee_id`=:empID AND week_end_date IN (SELECT week_end_date FROM ( (SELECT DISTINCT week_end_date FROM ");
		sql.append("`vw_user_activity` u ");
		sql.append("INNER JOIN resource_allocation r ON u.resource_alloc_id=r.id  WHERE STATUS='R' AND r.employee_id =:empID ORDER BY week_end_date DESC LIMIT 4)tab)) ");
		sql.append("ORDER BY week_end_date DESC) UNION  (SELECT DISTINCT week_end_date,STATUS FROM `vw_user_activity` ua INNER JOIN `resource_allocation` ra ON ua.`resource_alloc_id`=ra.`id` ");
		sql.append("WHERE STATUS='N' AND ra.`employee_id`=:empID AND week_end_date IN (SELECT week_end_date FROM ( (SELECT DISTINCT week_end_date FROM `vw_user_activity` u ");
		sql.append("INNER JOIN resource_allocation r ON u.resource_alloc_id=r.id  WHERE STATUS='N' AND r.employee_id =:empID ORDER BY week_end_date DESC LIMIT 4)tab)) ");
		sql.append("ORDER BY week_end_date DESC) )tabg ORDER BY week_end_date DESC ");
		return sql.toString();
	}

	public static String getallstatusoflastfourMonth() {

		StringBuffer sql = new StringBuffer();

		sql.append("SELECT week_end_date,COUNT(CASE WHEN Approved_status= 'Approved' THEN Approved_status END ) AS Approved_status_count,COUNT(CASE WHEN Pending_status= 'Pending' THEN Pending_status END ) AS Pending_status_count,");
		sql.append("COUNT(CASE WHEN Not_Submitted_status= 'Not Submitted' THEN Not_Submitted_status END ) AS Not_Submitted_status_count,COUNT(CASE WHEN Rejected_status= 'Rejected' THEN Rejected_status END ) AS Rejected_status_count FROM");
		sql.append("(SELECT week_end_date,CASE WHEN ua.status = 'A' THEN 'Approved' END AS Approved_status,CASE WHEN ua.status = 'P' THEN 'Pending' END AS Pending_status,");
		sql.append("CASE WHEN ua.status = 'N' THEN 'Not Submitted' END AS Not_Submitted_status,CASE WHEN ua.status = 'R' THEN 'Rejected' END AS Rejected_status FROM vw_user_activity ua INNER JOIN resource_allocation ra ON ua.resource_alloc_id = ra.`id` ");
		sql.append("WHERE ra.employee_id =:empID AND week_end_date >= '2015/08/18' AND `week_end_date`<= (SELECT DATE_ADD(NOW(), INTERVAL 1 WEEK))  ORDER BY week_end_date DESC)tab GROUP BY week_end_date ORDER BY week_end_date DESC");
		return sql.toString();
	}

	public static String getalltodolist() {

		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT cal.date  FROM (SELECT (DATE(NOW()) - INTERVAL c.number DAY) AS DATE FROM ( ");
		sql.append(" SELECT singles + tens + hundreds number FROM ( SELECT 0 singles UNION ALL SELECT   1 UNION ALL SELECT   2 UNION ALL SELECT   3 ");
		sql.append(" UNION ALL SELECT   4 UNION ALL SELECT   5 UNION ALL SELECT   6 UNION ALL SELECT   7 UNION ALL SELECT   8 UNION ALL SELECT   9) singles JOIN ");
		sql.append(" (SELECT 0 tens  UNION ALL SELECT  10 UNION ALL SELECT  20 UNION ALL SELECT  30 UNION ALL SELECT  40 UNION ALL SELECT  50 UNION ALL SELECT  60 ");
		sql.append(" UNION ALL SELECT  70 UNION ALL SELECT  80 UNION ALL SELECT  90) tens  JOIN (SELECT 0 hundreds UNION ALL SELECT  100 UNION ALL SELECT  200 UNION ALL SELECT  300");
		sql.append(" UNION ALL SELECT  400 UNION ALL SELECT  500 UNION ALL SELECT  600 UNION ALL SELECT  700 UNION ALL SELECT  800 UNION ALL SELECT  900");
		sql.append(" ) hundreds ORDER BY number DESC) c ) cal WHERE DAYOFWEEK(cal.date)='7' AND YEAR(cal.date) BETWEEN YEAR(DATE(NOW()))-1 AND YEAR(DATE(NOW())) ");
		sql.append(" AND cal.date NOT IN ((SELECT DISTINCT week_end_date FROM `vw_user_activity` ua INNER JOIN `resource_allocation` ra ON ua.`resource_alloc_id`=ra.`id` ");
		sql.append(" WHERE ra.`employee_id`=:empID AND week_end_date  IN (SELECT week_end_date FROM ( (SELECT DISTINCT week_end_date FROM `vw_user_activity` u ");
		sql.append(" INNER JOIN resource_allocation r ON u.resource_alloc_id=r.id WHERE r.employee_id =:empID ORDER BY week_end_date DESC )tab)) ORDER BY week_end_date DESC)) AND cal.date>= (SELECT date_of_joining FROM resource WHERE employee_id=:empID) ORDER BY cal.date DESC  LIMIT 4 ");
		return sql.toString();
	}

	public static String getManagerBillingStatus() {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM (SELECT p.project_name ,SUM(IFNULL(d1_hours,0)+IFNULL(d2_hours,0)+IFNULL(d3_hours,0)+IFNULL(d4_hours,0)+IFNULL(d5_hours,0)+IFNULL(d6_hours,0)+IFNULL(d7_hours,0)) AS actual_hrs ");
		sql.append(",SUM(`billed_hrs`) AS billing_hrs ,SUM(CASE WHEN billable <>1 THEN IFNULL(d1_hours,0)+IFNULL(d2_hours,0)+IFNULL(d3_hours,0)+IFNULL(d4_hours,0)+IFNULL(d5_hours,0)+IFNULL(d6_hours,0)+IFNULL(d7_hours,0) END ) AS non_billing_hrs ");
		sql.append("FROM `vw_user_activity` ua INNER JOIN `resource_allocation` ra ON ua.`resource_alloc_id`=ra.`id` INNER JOIN project p ON p.id=ra.`project_id` ");
		sql.append("INNER JOIN `timehrs` t ON ua.`resource_alloc_id`=t.`resource_alloc_id` WHERE (p.offshore_del_mgr=:empID) GROUP BY p.project_name)tab ");
		return sql.toString();
	}

	public static String getDeliveryManagerBillingStatus() {

		StringBuffer sql = new StringBuffer();

		sql.append("SELECT * FROM ( SELECT p.project_name, SUM(IFNULL(d1_hours,0)+IFNULL(d2_hours,0)+IFNULL(d3_hours,0)+IFNULL(d4_hours,0)+IFNULL(d5_hours,0)+IFNULL(d6_hours,0)+IFNULL(d7_hours,0)) AS actual_hrs ");
		sql.append(",SUM(`billed_hrs`) AS billing_hrs ,SUM(CASE WHEN billable <>1 THEN IFNULL(d1_hours,0)+IFNULL(d2_hours,0)+IFNULL(d3_hours,0)+IFNULL(d4_hours,0)+IFNULL(d5_hours,0)+IFNULL(d6_hours,0)+IFNULL(d7_hours,0) END ) AS non_billing_hrs ");
		sql.append("FROM `vw_user_activity` ua INNER JOIN resource re ON ua.`employee_id`=re.`employee_id` INNER JOIN `resource_allocation` ra ON ra.`employee_id`=re.`employee_id` AND ra.id=ua.`resource_alloc_id` ");
		sql.append("INNER JOIN project p ON p.id=ra.`project_id` INNER JOIN `timehrs` t ON t.resource_id = re.employee_id AND t.`resource_alloc_id` = ra.id AND t.`week_ending_date`=ua.`week_end_date` ");
		sql.append("WHERE (ra.alloc_end_date >NOW() OR ra.alloc_end_date IS NULL) AND (p.project_name IN (:projectName)) GROUP BY p.project_name )tab ");

		return sql.toString();

	}

	public static String getDeliveryManagerResourceBillingStatus() {

		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT project_name,COUNT(CASE WHEN Billable_status= 'Billable' THEN Billable_status END ) AS billable_resources ,COUNT(CASE WHEN Billable_status= 'OutBound' THEN Billable_status END ) ");
		sql.append(" + COUNT(CASE WHEN Billable_status= 'NonBillable' THEN Billable_status END ) AS non_billable_resources ");
		sql.append(" FROM (SELECT DISTINCT p.project_name,CONCAT (re.first_name,' ' ,re.last_name) AS Employee ,CASE WHEN ra.allocation_type_id IN (2,3) ");
		sql.append(" THEN 'Billable' WHEN ra.allocation_type_id=10 THEN 'OutBound' ELSE 'NonBillable'  ");
		sql.append(" END AS Billable_status FROM resource re INNER JOIN resource_allocation ra ON re.`employee_id`=ra.`employee_id` INNER JOIN ");
		sql.append(" project p ON ra.`project_id`=p.id WHERE (ra.alloc_end_date >NOW() OR ra.alloc_end_date IS NULL) AND p.project_name  ");
		sql.append(" IN (:projectName)) tab GROUP BY  ");
		sql.append(" project_name ORDER BY project_name ");

		return sql.toString();

	}

	public static String getDeliveryManagerResourceBillingStatusList() {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT project_name,COUNT(CASE WHEN Billable_status= 'Billable' THEN Billable_status END ) AS Bill,COUNT(CASE WHEN Billable_status= 'NonBillable' THEN Billable_status END ) AS nonBill ");
		sql.append("FROM (SELECT DISTINCT p.project_name,CONCAT (re.first_name,' ' ,re.last_name) AS Employee ,CASE WHEN ra.`billable` =1 THEN 'Billable' ELSE 'NonBillable' END AS Billable_status FROM resource re ");
		sql.append("INNER JOIN resource_allocation ra ON re.`employee_id`=ra.`employee_id` INNER JOIN project p ON ra.`project_id`=p.id WHERE p.offshore_del_mgr=:empID ");
		sql.append(") tab GROUP BY project_name ORDER BY project_name ");
		return sql.toString();

	}

	public static String getDeliveryManagerTimesheetStatusForDeliveryManager() {

		StringBuffer sql = new StringBuffer();
		sql.append("  SELECT * FROM ((SELECT week_end_date,STATUS,COUNT(DISTINCT ua.employee_id) FROM `vw_user_activity` ua ");
		sql.append("INNER JOIN resource re ON ua.`employee_id`=re.`employee_id` INNER JOIN resource_allocation ra ON ra.employee_id=re.`employee_id` ");
		sql.append("INNER JOIN project p ON p.id=ra.`project_id`  WHERE ua.`employee_id` IN (:empIds) AND  ua.`resource_alloc_id` ");
		sql.append("IN ((SELECT `id` FROM `resource_allocation` WHERE employee_id IN(SELECT employee_id FROM `resource` WHERE employee_id IN ");
		sql.append("(:empIds)AND(`current_reporting_manager`=:currentUser OR `current_reporting_manager_two`=:currentUser)) OR project_id IN (SELECT id FROM project WHERE offshore_del_mgr=:currentUser UNION SELECT `project_id` FROM `resource_allocation` WHERE (behalf_Manager =1 AND employee_id=:currentUser))))");
		sql.append("AND STATUS='P' AND week_end_date IN (SELECT week_end_date FROM ( (SELECT DISTINCT week_end_date FROM `vw_user_activity` WHERE STATUS='P'  ORDER BY week_end_date DESC)tab)) ");
		sql.append("GROUP BY  week_end_date,STATUS ORDER BY week_end_date DESC LIMIT 4)  UNION (SELECT week_end_date,STATUS,COUNT(DISTINCT ua.employee_id) ");
		sql.append("FROM `vw_user_activity` ua INNER JOIN resource re ON ua.`employee_id`=re.`employee_id` INNER JOIN resource_allocation ra ");
		sql.append("ON ra.employee_id=re.`employee_id` INNER JOIN project p ON p.id=ra.`project_id`  ");
		sql.append("WHERE ua.`employee_id` IN (:empIds) AND  ua.`resource_alloc_id` IN (SELECT  id FROM resource_allocation WHERE project_id IN(SELECT id FROM project WHERE offshore_del_mgr=:currentUser))");
		sql.append("AND  STATUS='A' AND week_end_date IN (SELECT week_end_date FROM ((SELECT DISTINCT week_end_date FROM `vw_user_activity` WHERE STATUS='A'  ORDER BY week_end_date DESC)tab)) ");
		sql.append("GROUP BY  week_end_date,STATUS ORDER BY week_end_date DESC LIMIT 4) UNION (SELECT week_end_date,STATUS,COUNT(DISTINCT ua.employee_id) ");
		sql.append("FROM `vw_user_activity` ua INNER JOIN resource re  ON ua.`employee_id`=re.`employee_id` INNER JOIN resource_allocation ra ");
		sql.append("ON ra.employee_id=re.`employee_id` INNER JOIN project p ON p.id=ra.`project_id`   ");
		sql.append("WHERE ua.`employee_id` IN (:empIds) AND  ua.`resource_alloc_id` IN (SELECT  id FROM resource_allocation WHERE project_id IN(SELECT id FROM project WHERE offshore_del_mgr=:currentUser))");
		sql.append("AND  STATUS='N' AND week_end_date IN (SELECT week_end_date FROM ( (SELECT DISTINCT week_end_date FROM `vw_user_activity` WHERE STATUS='N'  ORDER BY week_end_date DESC)tab)) ");
		sql.append("GROUP BY  week_end_date,STATUS ORDER BY week_end_date DESC LIMIT 4) ");
		sql.append("UNION (SELECT week_end_date,STATUS,COUNT(DISTINCT ua.employee_id) FROM `vw_user_activity` ua INNER JOIN resource re ");
		sql.append("ON ua.`employee_id`=re.`employee_id` INNER JOIN resource_allocation ra ON ra.employee_id=re.`employee_id` INNER JOIN project p ON ");
		sql.append("p.id=ra.`project_id` WHERE ua.`employee_id` IN (:empIds) AND  ua.`resource_alloc_id` IN (SELECT  id FROM resource_allocation WHERE project_id IN(SELECT id FROM project WHERE offshore_del_mgr=:currentUser)) ");
		sql.append("AND STATUS='R' AND week_end_date IN (SELECT week_end_date FROM ( (SELECT DISTINCT week_end_date FROM `vw_user_activity` WHERE STATUS='R'  ORDER BY week_end_date DESC)tab)) ");
		sql.append("GROUP BY  week_end_date,STATUS ORDER BY week_end_date DESC LIMIT 4))tabg ORDER BY week_end_date DESC ");
		return sql.toString();
	}

	public static String getDeliveryManagerTimesheetStatus() {

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM ((SELECT week_end_date,STATUS,COUNT(DISTINCT ua.employee_id) FROM `vw_user_activity` ua ");
		sql.append("INNER JOIN resource re ON ua.`employee_id`=re.`employee_id` INNER JOIN resource_allocation ra ON ra.employee_id=re.`employee_id` ");
		sql.append("INNER JOIN project p ON p.id=ra.`project_id`  WHERE ua.`employee_id` IN (:empIds) AND  ua.`resource_alloc_id` IN (SELECT  id FROM resource_allocation WHERE project_id IN(SELECT id FROM project WHERE offshore_del_mgr=:currentUser UNION SELECT `project_id` FROM `resource_allocation` WHERE (behalf_Manager =1 AND employee_id=:currentUser))) ");
		sql.append("AND STATUS='P' AND week_end_date IN (SELECT week_end_date FROM ( (SELECT DISTINCT week_end_date FROM `vw_user_activity` WHERE STATUS='P'  ORDER BY week_end_date DESC)tab)) ");
		sql.append("GROUP BY  week_end_date,STATUS ORDER BY week_end_date DESC LIMIT 4) UNION (SELECT week_end_date,STATUS,COUNT(DISTINCT ua.employee_id) ");
		sql.append("FROM `vw_user_activity` ua INNER JOIN resource re ON ua.`employee_id`=re.`employee_id` INNER JOIN resource_allocation ra ");
		sql.append("ON ra.employee_id=re.`employee_id` INNER JOIN project p ON p.id=ra.`project_id`  ");
		sql.append("WHERE ua.`employee_id` IN (:empIds) AND  ua.`resource_alloc_id` IN (SELECT  id FROM resource_allocation WHERE project_id IN(SELECT id FROM project WHERE offshore_del_mgr=:currentUser))");
		sql.append("AND  STATUS='A' AND week_end_date IN (SELECT week_end_date FROM ((SELECT DISTINCT week_end_date FROM `vw_user_activity` WHERE STATUS='A'  ORDER BY week_end_date DESC)tab)) ");
		sql.append("GROUP BY  week_end_date,STATUS ORDER BY week_end_date DESC LIMIT 4) UNION (SELECT week_end_date,STATUS,COUNT(DISTINCT ua.employee_id) ");
		sql.append("FROM `vw_user_activity` ua INNER JOIN resource re  ON ua.`employee_id`=re.`employee_id` INNER JOIN resource_allocation ra ");
		sql.append("ON ra.employee_id=re.`employee_id` INNER JOIN project p ON p.id=ra.`project_id`   ");
		sql.append("WHERE ua.`employee_id` IN (:empIds) AND  ua.`resource_alloc_id` IN (SELECT  id FROM resource_allocation WHERE project_id IN(SELECT id FROM project WHERE offshore_del_mgr=:currentUser))");
		sql.append("AND  STATUS='N' AND week_end_date IN (SELECT week_end_date FROM ( (SELECT DISTINCT week_end_date FROM `vw_user_activity` WHERE STATUS='N'  ORDER BY week_end_date DESC)tab)) ");
		sql.append("GROUP BY  week_end_date,STATUS ORDER BY week_end_date DESC LIMIT 4) ");
		sql.append("UNION (SELECT week_end_date,STATUS,COUNT(DISTINCT ua.employee_id) FROM `vw_user_activity` ua INNER JOIN resource re ");
		sql.append("ON ua.`employee_id`=re.`employee_id` INNER JOIN resource_allocation ra ON ra.employee_id=re.`employee_id` INNER JOIN project p ON ");
		sql.append("p.id=ra.`project_id` WHERE ua.`employee_id` IN (:empIds) AND  ua.`resource_alloc_id` IN (SELECT  id FROM resource_allocation WHERE project_id IN(SELECT id FROM project WHERE offshore_del_mgr=:currentUser)) ");
		sql.append("AND STATUS='R' AND week_end_date IN (SELECT week_end_date FROM ( (SELECT DISTINCT week_end_date FROM `vw_user_activity` WHERE STATUS='R'  ORDER BY week_end_date DESC)tab)) ");
		sql.append("GROUP BY  week_end_date,STATUS ORDER BY week_end_date DESC LIMIT 4))tabg ORDER BY week_end_date DESC ");
		return sql.toString();

	}

	public static String getTimesheetcompliance() {

		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT DISTINCT week_end_date,COUNT(DISTINCT u.employee_id) FROM `vw_user_activity` u, resource_allocation ra, project p ");
		sql.append(" WHERE u.resource_alloc_id=ra.id ");
		sql.append(" AND p.id=ra.project_id ");
		sql.append(" AND u.employee_id IN (:empIds)  ");
		sql.append(" AND week_end_date IN (:date,DATE_SUB(:date,INTERVAL 7 DAY),DATE_SUB(:date,INTERVAL 14 DAY),DATE_SUB(:date,INTERVAL 21 DAY)) ");

		sql.append(" GROUP BY `week_end_date` ORDER BY week_end_date DESC ");
		return sql.toString();

	}

	// PL Report: START

		public static String getResignedResourceQuery(boolean includeResource) {

		StringBuffer sql = new StringBuffer();

			sql.append(" SELECT r.yash_emp_id,CONCAT(TRIM(r.first_name),' ',TRIM(r.last_name)) AS NAME,d.designation_name,b.name AS BU_NAME,p.project_name,g.grade,ra.alloc_start_date, ");
			sql.append(" ra.alloc_end_date,r.release_date,ra.alloc_percentage FROM resource r,designations d,competency c,grade g,resource_allocation ra,allocationtype a,customer cu,project p, ");
			sql.append(" bu b WHERE r.designation_id = d.id AND r.competency = c.id AND r.grade_id = g.id AND r.employee_id = ra.employee_id AND ra.allocation_type_id = a.id  ");
			sql.append(" AND ra.project_id = p.id AND p.customer_name_id = cu.id and p.bu_id = b.id AND p.bu_id IN (:buId) AND ((r.release_date <=:endDate) and (r.release_date>=:startDate)) and ");
			sql.append(" (((ra.alloc_start_date <= :endDate) AND (ra.alloc_end_date >= :startDate)) OR ((ra.alloc_start_date <= :endDate) AND (ra.alloc_end_date IS NULL)) OR ");
			sql.append(" ((ra.alloc_start_date >= :startDate) AND (ra.alloc_start_date <= :endDate)))");
			sql.append(" AND ra.`curProj` IN (:projectTypeIds)  ");
			if(includeResource){
				sql.append(" UNION ");
				
				sql.append(" SELECT r.yash_emp_id,CONCAT(TRIM(r.first_name),' ',TRIM(r.last_name)) AS NAME,d.designation_name,b.name AS BU_NAME,p.project_name,g.grade,ra.alloc_start_date, ");
				sql.append(" ra.alloc_end_date,r.release_date,ra.alloc_percentage FROM resource r,designations d,competency c,grade g,resource_allocation ra,allocationtype a,customer cu,project p, ");
				sql.append(" bu b WHERE r.designation_id = d.id AND r.competency = c.id AND r.grade_id = g.id AND r.employee_id = ra.employee_id AND ra.allocation_type_id = a.id  ");
				sql.append(" AND ra.project_id = p.id AND p.customer_name_id = cu.id and ra.bu_id = b.id AND ra.bu_id IN (:buId) AND ((r.release_date <=:endDate) and (r.release_date>=:startDate)) and ");
				sql.append(" (((ra.alloc_start_date <= :endDate) AND (ra.alloc_end_date >= :startDate)) OR ((ra.alloc_start_date <= :endDate) AND (ra.alloc_end_date IS NULL)) OR ");
				sql.append(" ((ra.alloc_start_date >= :startDate) AND (ra.alloc_start_date <= :endDate)))");
				sql.append(" AND ra.`curProj` IN (:projectTypeIds)  ");
			}
			sql.append(" ORDER BY NAME ");
			return sql.toString();
		}
		
		public static String getBUResourcesQuery(boolean includeResource) {

		StringBuffer sql = new StringBuffer();

			sql.append(" SELECT r.yash_emp_id,CONCAT(TRIM(r.first_name),' ',TRIM(r.last_name)) AS NAME,d.designation_name,b.name AS bu_name,p.project_name,c.skill,l1.location AS base_location,  ");
			sql.append(" l2.location AS current_location,cu.customer_name,r.date_of_joining,g.grade,DATE((CASE WHEN ra.alloc_start_date < :startDate  ");
			sql.append(" THEN :startDate ELSE ra.alloc_start_date END )) AS DummyStartDate,DATE((CASE WHEN ra.alloc_end_date IS NULL THEN :endDate WHEN ra.alloc_end_date < :endDate THEN ra.alloc_end_date  ");
			sql.append(" ELSE :endDate END )) AS DummyEndDate,a.allocationtype,ra.alloc_start_date,ra.alloc_end_date,ra.alloc_percentage FROM designations d,Ownership ow,bu b,competency c,  ");
			sql.append(" grade g,resource_allocation ra,allocationtype a,project p,customer cu,resource r INNER JOIN location l1 ON l1.id = r.location_id INNER JOIN location l2 ON l2.id = r.payroll_location  ");
			sql.append(" WHERE r.designation_id = d.id AND r.competency = c.id AND r.grade_id = g.id AND r.ownership = ow.id AND r.employee_id = ra.employee_id AND ra.allocation_type_id = a.id AND ra.project_id = p.id   ");
			sql.append(" AND p.customer_name_id = cu.id AND p.bu_id = b.id AND ow.ownership_name <> 'Loan' AND r.employee_category <> 2 AND r.yash_emp_id <> '%C%' AND p.bu_id IN (:buId) AND (((ra.alloc_start_date <= :endDate) AND (ra.alloc_end_date >= :startDate)) OR  ");
			sql.append(" ((ra.alloc_start_date <= :endDate) AND (ra.alloc_end_date IS NULL)) OR ((ra.alloc_start_date >= :startDate) AND (ra.alloc_start_date <= :endDate))) ");
			sql.append(" AND ra.`curProj` IN (:projectTypeIds) ");
			
			if(includeResource){
				sql.append(" UNION ");
				sql.append(" SELECT r.yash_emp_id,CONCAT(TRIM(r.first_name),' ',TRIM(r.last_name)) AS NAME,d.designation_name,b.name AS bu_name,p.project_name,c.skill,l1.location AS base_location, ");
				sql.append(" l2.location AS current_location,cu.customer_name,r.date_of_joining,g.grade,DATE((CASE WHEN ra.alloc_start_date < :startDate  ");
				sql.append(" THEN :startDate ELSE ra.alloc_start_date END )) AS DummyStartDate,DATE((CASE WHEN ra.alloc_end_date IS NULL THEN :endDate WHEN ra.alloc_end_date < :endDate THEN ra.alloc_end_date  ");
				sql.append(" ELSE :endDate END )) AS DummyEndDate,a.allocationtype,ra.alloc_start_date,ra.alloc_end_date,ra.alloc_percentage FROM designations d,Ownership ow,bu b,competency c, ");
				sql.append(" grade g,resource_allocation ra,project p,customer cu,allocationtype a,resource r INNER JOIN location l1 ON l1.id = r.location_id INNER JOIN location l2 ON l2.id = r.payroll_location  ");
				sql.append(" WHERE r.employee_id = ra.`employee_id` AND r.designation_id = d.id AND r.competency = c.id AND r.grade_id = g.id AND r.ownership = ow.id AND ra.project_id = p.id AND ra.bu_id = b.id" );
				sql.append(" AND ra.allocation_type_id = a.id AND ra.bu_id IN (:buId) AND ra.curProj IN (:projectTypeIds) AND p.customer_name_id = cu.id AND  (((ra.alloc_start_date <= :endDate) AND (ra.alloc_end_date >= :startDate)) OR  ");
				sql.append(" ((ra.alloc_start_date <= :endDate) AND (ra.alloc_end_date IS NULL)) OR ((ra.alloc_start_date >= :startDate) AND (ra.alloc_start_date <= :endDate))) ");
				sql.append(" AND ow.ownership_name <> 'Loan' AND r.employee_category <> 2 AND r.yash_emp_id <> '%C%'" );

			}
			sql.append(" ORDER BY NAME  ");
			return sql.toString();
		}

		public static String getContractResourceQuery(boolean includeResource) {

		StringBuffer sql = new StringBuffer();

			sql.append(" SELECT r.yash_emp_id,CONCAT(TRIM(r.first_name),' ',TRIM(r.last_name)) AS NAME,bu.name AS BG_Name,c.skill,d.designation_name,r.date_of_joining,l2.location AS 'Current Location',  ");
			sql.append(" p.project_name,ra.alloc_start_date,ra.alloc_end_date,ra.alloc_percentage FROM designations d,competency c,grade g,resource_allocation ra,allocationtype a,project p,customer cu,bu bu,  ");
			sql.append(" resource r INNER JOIN location l1 ON l1.id = r.location_id INNER JOIN location l2 ON l2.id = r.payroll_location WHERE r.designation_id = d.id and r.`bu_id` = bu.`id`  ");
			sql.append(" AND r.competency = c.id AND r.grade_id = g.id AND r.employee_id = ra.employee_id AND ra.allocation_type_id = a.id AND ra.project_id = p.id AND p.customer_name_id = cu.id   ");
			sql.append(" AND p.bu_id IN (:buId) AND (r.employee_category = 2 OR r.yash_emp_id LIKE '%C%') AND (((ra.alloc_start_date <= :endDate) AND (ra.`alloc_end_date` >= :startDate)) OR ((ra.`alloc_start_date`   ");
			sql.append(" <= :endDate) AND (ra.`alloc_end_date` IS NULL)) OR ((ra.alloc_start_date >= :startDate) AND (ra.`alloc_start_date` <= :endDate)))  ");
			sql.append(" AND ra.`curProj` IN (:projectTypeIds) ");
			
			if(includeResource){
				sql.append(" UNION ");
				
				sql.append(" SELECT r.yash_emp_id,CONCAT(TRIM(r.first_name),' ',TRIM(r.last_name)) AS NAME,bu.name AS BG_Name,c.skill,d.designation_name,r.date_of_joining,l2.location AS 'Current Location',  ");
				sql.append(" p.project_name,ra.alloc_start_date,ra.alloc_end_date,ra.alloc_percentage FROM designations d,competency c,grade g,resource_allocation ra,allocationtype a,project p,customer cu,bu bu,  ");
				sql.append(" resource r INNER JOIN location l1 ON l1.id = r.location_id INNER JOIN location l2 ON l2.id = r.payroll_location WHERE r.designation_id = d.id and r.`bu_id` = bu.`id`  ");
				sql.append(" AND r.competency = c.id AND r.grade_id = g.id AND r.employee_id = ra.employee_id AND ra.allocation_type_id = a.id AND ra.project_id = p.id AND p.customer_name_id = cu.id   ");
				sql.append(" AND ra.bu_id IN (:buId) AND (r.employee_category = 2 OR r.yash_emp_id LIKE '%C%') AND (((ra.alloc_start_date <= :endDate) AND (ra.`alloc_end_date` >= :startDate)) OR ((ra.`alloc_start_date`   ");
				sql.append(" <= :endDate) AND (ra.`alloc_end_date` IS NULL)) OR ((ra.alloc_start_date >= :startDate) AND (ra.`alloc_start_date` <= :endDate)))  ");
				sql.append(" AND ra.`curProj` IN (:projectTypeIds) ");
			}

			sql.append(" ORDER BY NAME ");
			return sql.toString();
		}

		public static String getBorrowedResources(boolean includeResource) {

		StringBuffer sql = new StringBuffer();

			sql.append(" SELECT r.yash_emp_id,CONCAT(TRIM(r.first_name),' ',TRIM(r.last_name)) AS NAME,p.project_name,b1.name AS 'bu_id',b2.name AS 'bu_dept',d.designation_name, ");
			sql.append(" c.skill,r.date_of_joining,g.grade,a.allocationtype,ra.alloc_start_date,ra.alloc_end_date,cu.customer_name,l1.location AS 'Base Location',l2.location AS  ");
			sql.append(" 'Current Location',ra.alloc_percentage FROM designations d,Ownership ow,competency c,grade g,resource_allocation ra,allocationtype a,project p,customer cu,bu b1,bu b2,resource r  ");
			sql.append(" INNER JOIN location l1 ON l1.id = r.location_id INNER JOIN location l2 ON l2.id = r.payroll_location WHERE r.designation_id = d.id AND r.competency = c.id  ");
			sql.append(" AND r.ownership=ow.id AND ow.ownership_name='Loan' AND r.grade_id = g.id AND r.employee_id = ra.employee_id AND ra.allocation_type_id = a.id  ");
			sql.append(" AND ra.project_id = p.id AND b1.id = p.bu_id AND b2.id = r.bu_id AND p.customer_name_id = cu.id AND p.bu_id IN (:buId) AND ");
			sql.append(" r.current_bu_id IN (:buId) AND ");
			sql.append(" (((ra.alloc_start_date <= :endDate) AND (ra.alloc_end_date >= :startDate)) OR ((ra.alloc_start_date <= :endDate) AND (ra.alloc_end_date IS NULL))  ");
			sql.append(" OR ((ra.alloc_start_date >= :startDate) AND (ra.alloc_start_date <= :endDate))) ");
			sql.append(" AND ra.`curProj` IN (:projectTypeIds) ");
			
			if(includeResource){
				sql.append(" UNION ");
				sql.append(" SELECT r.yash_emp_id,CONCAT(TRIM(r.first_name),' ',TRIM(r.last_name)) AS NAME,p.project_name,b1.name AS 'bu_id',b2.name AS 'bu_dept',d.designation_name, ");
				sql.append(" c.skill,r.date_of_joining,g.grade,a.allocationtype,ra.alloc_start_date,ra.alloc_end_date,cu.customer_name,l1.location AS 'Base Location',l2.location AS  ");
				sql.append(" 'Current Location',ra.alloc_percentage FROM designations d,Ownership ow,competency c,grade g,resource_allocation ra,allocationtype a,project p,customer cu,bu b1,bu b2,resource r  ");
				sql.append(" INNER JOIN location l1 ON l1.id = r.location_id INNER JOIN location l2 ON l2.id = r.payroll_location WHERE r.designation_id = d.id AND r.competency = c.id  ");
				sql.append(" AND r.ownership=ow.id AND ow.ownership_name='Loan' AND r.grade_id = g.id AND r.employee_id = ra.employee_id AND ra.allocation_type_id = a.id  ");
				sql.append(" AND ra.project_id = p.id AND b1.id = ra.bu_id AND b2.id = r.bu_id AND p.customer_name_id = cu.id AND ra.bu_id IN (:buId) AND ");
				sql.append(" r.current_bu_id IN (:buId) AND ");
				sql.append(" (((ra.alloc_start_date <= :endDate) AND (ra.alloc_end_date >= :startDate)) OR ((ra.alloc_start_date <= :endDate) AND (ra.alloc_end_date IS NULL))  ");
				sql.append(" OR ((ra.alloc_start_date >= :startDate) AND (ra.alloc_start_date <= :endDate))) ");
				sql.append(" AND ra.`curProj` IN (:projectTypeIds)  ");
			}

			sql.append(" ORDER BY NAME");
			return sql.toString();

	}

	public static String getPLReportPivot() {

		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT p.project_name,ra.alloc_end_date,(SELECT COUNT(r.employee_id) FROM Ownership ow,resource_allocation ra,allocationtype a,project p,resource r   ");
		sql.append(" WHERE r.ownership = ow.id AND r.employee_id = ra.employee_id AND ra.allocation_type_id = a.id AND ra.project_id = p.id AND p.bu_id IN (:buId)   ");
		sql.append(" AND ra.project_id IN (:projectId) AND ra.allocation_type_id IN (2, 3) AND ((( ra.alloc_start_date <= :endDate) AND (ra.alloc_end_date >= :startDate))   ");
		sql.append(" OR ((ra.alloc_start_date <= :endDate) AND (ra.alloc_end_date IS NULL)) OR ((ra.alloc_start_date >= :startDate)   ");
		sql.append(" AND (ra.alloc_start_date <= :endDate)))) AS EMP_Active,  ");
		sql.append(" (SELECT COUNT(r.yash_emp_id) FROM resource r,resource_allocation ra,allocationtype a,project p WHERE r.employee_id = ra.employee_id   ");
		sql.append(" AND ra.allocation_type_id = a.id AND ra.project_id = p.id AND p.`id` IN (:projectId) AND ((r.release_date <= :endDate) AND (r.release_date >= :startDate))   ");
		sql.append(" AND (((ra.alloc_start_date <=:endDate) AND (ra.alloc_end_date >=:startDate)) OR ((ra.alloc_start_date <=:endDate) AND (ra.alloc_end_date IS NULL))   ");
		sql.append(" OR ((ra.alloc_start_date >= :startDate) AND (ra.alloc_start_date <= :endDate)))) AS EMP_Released,  ");
		sql.append(" COUNT(CASE WHEN (TO_DAYS(`ra`.`alloc_start_date`) - TO_DAYS(r.`date_of_joining`)) >= 15 THEN 1 ELSE NULL END) AS External,  ");
		sql.append(" COUNT(CASE WHEN ( TO_DAYS(`ra`.`alloc_start_date`) - TO_DAYS(r.`date_of_joining`)) <= 15 THEN 1 ELSE NULL END) AS Internal   ");
		sql.append(" FROM project p,`resource_allocation` ra,`resource` r WHERE ra.`project_id` = p.`id` AND ra.`employee_id` = r.`employee_id` AND p.bu_id IN (:buId)   ");
		sql.append(" AND ra.`project_id` IN (:projectId) AND ((r.release_date <= :endDate) AND (r.release_date >= :startDate)) AND (((ra.alloc_start_date <= :endDate)  ");
		sql.append(" AND (ra.alloc_end_date >= :startDate)) OR ((ra.alloc_start_date <= :endDate) AND (ra.alloc_end_date IS NULL))   ");
		sql.append(" OR ((ra.alloc_start_date >= :startDate) AND (ra.alloc_start_date <= :endDate))) GROUP BY p.`project_name`  ");

		return sql.toString();

	}

	public static String getUnAllocatedRMReportResource(String releaseDateCondition, String coulmnName) {

		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT  " + coulmnName + " FROM vw_rm_report rm WHERE rm.ALLOC_ID IN (SELECT temptt.res_alloc_id FROM (SELECT t.employee_id,MIN(t.Priority) Priority,MAX(t.res_alloc_id) res_alloc_id  ");
		sql.append(" FROM (SELECT employee_id,Priority,id res_alloc_id,alloc_start_date FROM vw_rm_support WHERE employee_id NOT IN (SELECT employee_id FROM vw_rm_support WHERE curProj = 1  ");
		sql.append(" AND alloc_start_date <= CURRENT_DATE) OR employee_id IN (SELECT employee_id FROM  vw_rm_support WHERE employee_id NOT IN (SELECT employee_id FROM vw_rm_support v  ");
		sql.append(" WHERE curProj = 1) GROUP BY employee_id HAVING COUNT(*) = 1) AND alloc_start_date <= CURRENT_DATE) t,(SELECT employee_id,MIN(Priority) Priority FROM vw_rm_support t  ");
		sql.append(" WHERE alloc_start_date <= CURDATE() GROUP BY employee_id) t1 WHERE t.employee_id = t1.employee_id AND t.Priority = t1.Priority GROUP BY t.employee_id) temptt  ");
		sql.append(" GROUP BY employee_id) AND Project_ID IN (:projIdList) AND (Bu_Id IN (:orgIdList) OR Current_Bu_Id IN (:orgIdList)  ");
		sql.append(" OR Project_Bu_ID IN (:orgIdList)) AND " + releaseDateCondition + " GROUP BY rm.Yash_Emp_Id  ");
		sql.append("  UNION");
		sql.append("  SELECT  " + coulmnName + " FROM  VW_RM_REPORT  WHERE RES_EMP_ID NOT IN (SELECT employee_id FROM resource_allocation) ");
		sql.append("  AND ( Bu_Id IN (:orgIdList) OR Current_Bu_Id IN (:orgIdList) OR Project_Bu_ID IN (:orgIdList) ) ");
		sql.append("  AND " + releaseDateCondition + " GROUP BY Yash_Emp_Id");
		sql.append("  UNION");
		sql.append("  SELECT  " + coulmnName + " FROM vw_rm_report WHERE ALLOC_ID IN (SELECT tt1.ID FROM (SELECT COUNT(*) AS COUNT, ID  FROM `resource_allocation` WHERE alloc_start_date <= CURRENT_DATE GROUP BY `employee_id`) tt1 ");
		sql.append("  INNER JOIN (SELECT COUNT(*) AS COUNT, ID  FROM resource_allocation WHERE alloc_end_date <= CURRENT_DATE GROUP BY employee_id) tt2 ON tt1.ID = tt2.ID ");
		sql.append("  WHERE tt1.count = tt2.count) ");
		sql.append("  AND ( Bu_Id IN (:orgIdList) OR Current_Bu_Id IN (:orgIdList) OR Project_Bu_ID IN (:orgIdList) ) ");
		sql.append("  AND " + releaseDateCondition + " GROUP BY Yash_Emp_Id ORDER BY Employee_Name");

		return sql.toString();

	}

	public static String getDiscrepancyRMReportResource(String releaseDateCondition, String coulmnName) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT " + coulmnName + " FROM vw_rm_report rm WHERE rm.ALLOC_ID IN (SELECT temptt.res_alloc_id FROM (SELECT t.employee_id,MIN(t.Priority) Priority,MAX(t.res_alloc_id) res_alloc_id  ");
		sql.append(" FROM (SELECT employee_id,Priority,id res_alloc_id,alloc_start_date FROM vw_rm_support WHERE employee_id NOT IN (SELECT employee_id FROM vw_rm_support WHERE curProj = 1  ");
		sql.append(" AND alloc_start_date <= CURRENT_DATE) OR employee_id IN (SELECT employee_id FROM  vw_rm_support WHERE employee_id NOT IN (SELECT employee_id FROM vw_rm_support v  ");
		sql.append(" WHERE curProj = 1) GROUP BY employee_id HAVING COUNT(*) = 1) AND alloc_start_date <= CURRENT_DATE) t,(SELECT employee_id,MIN(Priority) Priority FROM vw_rm_support t  ");
		sql.append(" WHERE alloc_start_date <= CURDATE() GROUP BY employee_id) t1 WHERE t.employee_id = t1.employee_id AND t.Priority = t1.Priority GROUP BY t.employee_id) temptt  ");
		sql.append(" GROUP BY employee_id) AND Project_ID IN (:projIdList) AND (Bu_Id IN (:orgIdList) OR Current_Bu_Id IN (:orgIdList)  ");
		sql.append(" OR Project_Bu_ID IN (:orgIdList)) AND " + releaseDateCondition + " AND ( Resource_Allocation_End_Date <= CURDATE() ) GROUP BY rm.Yash_Emp_Id  ");
		sql.append("  UNION");
		sql.append("  SELECT  " + coulmnName + " FROM  VW_RM_REPORT  WHERE RES_EMP_ID NOT IN (SELECT employee_id FROM resource_allocation) ");
		sql.append("  AND ( Bu_Id IN (:orgIdList) OR Current_Bu_Id IN (:orgIdList) OR Project_Bu_ID IN (:orgIdList) ) ");
		sql.append("  AND " + releaseDateCondition + " GROUP BY Yash_Emp_Id");
		sql.append("  UNION");
		sql.append("  SELECT  " + coulmnName + " FROM vw_rm_report WHERE ALLOC_ID IN (SELECT tt1.ID FROM (SELECT COUNT(*) AS COUNT, ID  FROM `resource_allocation` WHERE alloc_start_date <= CURRENT_DATE GROUP BY `employee_id`) tt1 ");
		sql.append("  INNER JOIN (SELECT COUNT(*) AS COUNT, ID  FROM resource_allocation WHERE alloc_end_date <= CURRENT_DATE GROUP BY employee_id) tt2 ON tt1.ID = tt2.ID ");
		sql.append("  WHERE tt1.count = tt2.count) ");
		sql.append("  AND ( Bu_Id IN (:orgIdList) OR Current_Bu_Id IN (:orgIdList) OR Project_Bu_ID IN (:orgIdList) ) ");
		sql.append("  AND " + releaseDateCondition + " AND ( Resource_Allocation_End_Date <= CURDATE() ) GROUP BY Yash_Emp_Id ORDER BY Employee_Name");
		return sql.toString();
	}
	public static String getMuneerReportResource() {

		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT *, (SELECT SUM(alloc_percentage) FROM resource_allocation WHERE (alloc_end_date is null or DATE(alloc_end_date)>=now()) and employee_id = RES_EMP_ID GROUP BY employee_id) AS percentTotal FROM vw_muneer_report WHERE (CASE WHEN Release_Date >= :weekDate THEN Release_Date IS NOT NULL ELSE Release_Date IS NULL END) ");
		sql.append(" AND (Bu_Id IN (:orgIdList) OR Current_Bu_Id IN (:orgIdList) OR Project_Bu_ID IN (:orgIdList)) AND Project_ID IN (:projIdList) ");
		sql.append(" AND Current_Project_Flag IN (:reportIdList) AND (Resource_Allocation_End_Date IS NUll OR Resource_Allocation_End_Date>=:weekDate) ");
		sql.append(" AND Allocation_Start_Date <= :weekDate GROUP BY Yash_Emp_Id ");
		
		sql.append(" UNION SELECT *, (SELECT SUM(ra.alloc_percentage) FROM resource_allocation ra, vw_muneer_unallocated_resource_report vwu WHERE (ra.alloc_end_date is null or DATE(ra.alloc_end_date)>=now()) and ra.employee_id = vwu.RES_EMP_ID GROUP BY ra.employee_id) AS percentTotal FROM vw_muneer_unallocated_resource_report WHERE (CASE WHEN Release_Date >= :weekDate THEN Release_Date IS NOT NULL ELSE Release_Date IS NULL END) ");
		sql.append(" AND (Bu_Id IN (:orgIdList) OR Current_Bu_Id IN (:orgIdList))  GROUP BY Yash_Emp_Id ");
		


		return sql.toString();

	}

	public static String getMuneerReportResourceClient() {

		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT temp.customer_name, temp.allocation_type,COUNT(allocation_type) FROM (SELECT * FROM vw_muneer_report WHERE  ");
		sql.append(" (CASE WHEN Release_Date >= :weekDate THEN Release_Date IS NOT NULL ELSE Release_Date IS NULL END) ");
		sql.append("AND (Project_ID IN (:projIdList)) AND (Bu_Id IN (:orgIdList) OR Current_Bu_Id IN (:orgIdList) OR Project_Bu_ID IN (:orgIdList))  ");
		sql.append(" AND Current_Project_Flag IN (1) AND (Resource_Allocation_End_Date IS NUll OR Resource_Allocation_End_Date>=:weekDate) AND  ");
		sql.append(" Allocation_Start_Date <= :weekDate GROUP BY Yash_Emp_Id) temp GROUP BY temp.customer_name,temp.allocation_type ");

		return sql.toString();

	}

	public static String getUnAllocatedMuneerReportResource() {

		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT * FROM vw_muneer_report rm WHERE (CASE WHEN Release_Date >= :weekDate THEN Release_Date IS NOT NULL ELSE Release_Date IS NULL END) AND rm.ALLOC_ID IN ");
		sql.append(" (SELECT temptt.res_alloc_id FROM (SELECT t.employee_id,MIN(t.Priority) Priority,MAX(t.res_alloc_id) res_alloc_id  ");
		sql.append(" FROM (SELECT employee_id,Priority,id res_alloc_id,alloc_start_date FROM vw_rm_support WHERE employee_id NOT IN (SELECT employee_id FROM vw_rm_support WHERE curProj = 1  ");
		sql.append(" AND alloc_start_date <= :weekDate) OR employee_id IN (SELECT employee_id FROM  vw_rm_support WHERE employee_id NOT IN (SELECT employee_id FROM vw_rm_support v  ");
		sql.append(" WHERE curProj = 1) GROUP BY employee_id HAVING COUNT(*) = 1) AND alloc_start_date <= :weekDate) t,(SELECT employee_id,MIN(Priority) Priority FROM vw_rm_support t  ");
		sql.append(" WHERE alloc_start_date <= :weekDate GROUP BY employee_id) t1 WHERE t.employee_id = t1.employee_id AND t.Priority = t1.Priority GROUP BY t.employee_id) temptt  ");
		sql.append(" GROUP BY employee_id) AND Project_ID IN (:projIdList) AND (Bu_Id IN (:orgIdList) OR Current_Bu_Id IN (:orgIdList)  ");
		sql.append(" OR Project_Bu_ID IN (:orgIdList)) AND Allocation_Start_Date <= :weekDate GROUP BY rm.Yash_Emp_Id  ");
		sql.append("  UNION");
		sql.append("  SELECT * FROM  vw_muneer_report  WHERE (CASE WHEN Release_Date >= :weekDate THEN Release_Date IS NOT NULL ELSE Release_Date IS NULL END)  ");
		sql.append("  AND RES_EMP_ID NOT IN (SELECT employee_id FROM resource_allocation) ");
		sql.append("  AND ( Bu_Id IN (:orgIdList) OR Current_Bu_Id IN (:orgIdList) OR Project_Bu_ID IN (:orgIdList) ) ");
		sql.append("  GROUP BY Yash_Emp_Id");
		//added new union
		sql.append("  UNION");
		sql.append("  SELECT * FROM  vw_muneer_report  WHERE ( Release_Date >= :weekDate OR Release_Date IS NULL) ");
		sql.append("  AND RES_EMP_ID NOT IN (SELECT employee_id FROM resource_allocation WHERE ");
		sql.append("  (alloc_end_date>=:weekDate OR alloc_end_date IS NULL) AND curProj=1) ");
		sql.append("  AND ( Bu_Id IN (:orgIdList)) ");
		sql.append("  GROUP BY Yash_Emp_Id");	
		sql.append("  UNION");
		sql.append("  SELECT * FROM vw_muneer_report WHERE (CASE WHEN Release_Date >= :weekDate THEN Release_Date IS NOT NULL ELSE Release_Date IS NULL END) AND ALLOC_ID IN ");
		sql.append("  (SELECT tt1.ID FROM (SELECT COUNT(*) AS COUNT, ID  FROM `resource_allocation` WHERE alloc_start_date <= :weekDate AND alloc_end_date >= :weekDate GROUP BY `employee_id`) tt1 ");
		sql.append("  INNER JOIN (SELECT COUNT(*) AS COUNT, ID  FROM resource_allocation WHERE alloc_end_date >= :weekDate GROUP BY employee_id) tt2 ON tt1.ID = tt2.ID ");
		sql.append("  WHERE tt1.count = tt2.count) ");
		sql.append("  AND ( Bu_Id IN (:orgIdList) OR Current_Bu_Id IN (:orgIdList) OR Project_Bu_ID IN (:orgIdList) ) ");
		sql.append("  GROUP BY Yash_Emp_Id ");
		// query added for date before start_alloc_date
sql.append("  UNION");
sql.append(" SELECT  * FROM vw_muneer_report WHERE ( CASE WHEN Release_Date >= :weekDate THEN Release_Date IS NOT NULL  ELSE Release_Date IS NULL  END )AND ALLOC_ID IN");
sql.append(" (SELECT  tt1.ID  FROM (SELECT  COUNT(*) AS COUNT, ID  FROM `resource_allocation`  WHERE alloc_start_date > :weekDate AND( alloc_end_date >= :weekDate OR `alloc_end_date` IS NULL) GROUP BY `employee_id`) tt1 ");
sql.append(" INNER JOIN (SELECT COUNT(*) AS COUNT,ID FROM resource_allocation WHERE alloc_end_date >= :weekDate OR `alloc_end_date` IS NULL GROUP BY employee_id) tt2 ON tt1.ID = tt2.ID ");
sql.append("  WHERE tt1.count = tt2.count) ");
sql.append("  AND ( Bu_Id IN (:orgIdList) OR Current_Bu_Id IN (:orgIdList) OR Project_Bu_ID IN (:orgIdList) ) ");
sql.append("  GROUP BY Yash_Emp_Id ORDER BY Employee_Name");

		return sql.toString();

	}

	public static String getReleasedMuneerReportResource() {

		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT *, (SELECT SUM(alloc_percentage) FROM resource_allocation WHERE (alloc_end_date is null or DATE(alloc_end_date)>=now()) and employee_id = RES_EMP_ID GROUP BY employee_id) AS percentTotal FROM vw_muneer_report WHERE  Project_ID IN (:projIdList) AND (Bu_Id IN (:orgIdList) OR Current_Bu_Id IN (:orgIdList)   ");
		sql.append(" OR Project_Bu_ID IN (:orgIdList)) AND (Release_Date <= CURDATE()) GROUP BY Yash_Emp_Id  ");
		
		sql.append(" UNION SELECT *,  (SELECT SUM(ra.alloc_percentage) FROM resource_allocation ra, vw_muneer_unallocated_resource_report vwu WHERE (ra.alloc_end_date is null or DATE(ra.alloc_end_date)>=now()) and ra.employee_id = vwu.RES_EMP_ID GROUP BY ra.employee_id) AS percentTotal FROM vw_muneer_unallocated_resource_report WHERE  (Bu_Id IN (:orgIdList) OR Current_Bu_Id IN (:orgIdList)   ");
		sql.append(") AND (Release_Date <= CURDATE()) GROUP BY Yash_Emp_Id  ");


		return sql.toString();

	}
	
	public static String getReleasedMuneerReportResourceClient() {

		StringBuffer sql = new StringBuffer();

		 sql.append(" SELECT counttemp.customer_name, counttemp.allocation_type,COUNT(allocation_type) FROM (SELECT * FROM vw_muneer_report WHERE  ");
		 sql.append(" Project_ID IN (:projIdList) AND (Bu_Id IN (:orgIdList) OR Current_Bu_Id IN (:orgIdList)    ");
		 sql.append(" OR Project_Bu_ID IN (:orgIdList)) AND (Release_Date <= CURDATE()) GROUP BY Yash_Emp_Id)counttemp GROUP BY counttemp.customer_name,counttemp.allocation_type   ");

		return sql.toString();

	}

	public static String getCountForUnAllocatedMuneerReportResource() {

		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT counttemp.customer_name, counttemp.allocation_type,COUNT(allocation_type) FROM (SELECT * FROM vw_muneer_report rm WHERE  ");
		sql.append(" (CASE WHEN Release_Date >= :weekDate THEN Release_Date IS NOT NULL ELSE Release_Date IS NULL END) AND rm.ALLOC_ID IN  ");
		sql.append(" (SELECT temptt.res_alloc_id FROM (SELECT t.employee_id,MIN(t.Priority) Priority,MAX(t.res_alloc_id) res_alloc_id   ");
		sql.append(" FROM (SELECT employee_id,Priority,id res_alloc_id,alloc_start_date FROM vw_rm_support WHERE employee_id NOT IN (SELECT employee_id FROM vw_rm_support WHERE curProj = 1  ");
		sql.append(" AND alloc_start_date <= :weekDate) OR employee_id IN (SELECT employee_id FROM  vw_rm_support WHERE employee_id NOT IN (SELECT employee_id FROM vw_rm_support v   ");
		sql.append(" WHERE curProj = 1) GROUP BY employee_id HAVING COUNT(*) = 1) AND alloc_start_date <= :weekDate) t,(SELECT employee_id,MIN(Priority) Priority FROM vw_rm_support t   ");
		sql.append(" WHERE alloc_start_date <= :weekDate GROUP BY employee_id) t1 WHERE t.employee_id = t1.employee_id AND t.Priority = t1.Priority GROUP BY t.employee_id) temptt   ");
		sql.append(" GROUP BY employee_id) AND Project_ID IN (:projIdList) AND (Bu_Id IN (:orgIdList) OR Current_Bu_Id IN (:orgIdList)   ");
		sql.append(" OR Project_Bu_ID IN (:orgIdList)) AND Allocation_Start_Date <= :weekDate GROUP BY rm.Yash_Emp_Id   ");
		sql.append(" UNION ");
		sql.append(" SELECT * FROM  vw_muneer_report  WHERE (CASE WHEN Release_Date >= :weekDate THEN Release_Date IS NOT NULL ELSE Release_Date IS NULL END)   ");
		sql.append(" AND RES_EMP_ID NOT IN (SELECT employee_id FROM resource_allocation)  ");
		sql.append(" AND ( Bu_Id IN (:orgIdList) OR Current_Bu_Id IN (:orgIdList) OR Project_Bu_ID IN (:orgIdList) )  ");
		sql.append(" GROUP BY Yash_Emp_Id ");
		sql.append(" UNION ");
		sql.append(" SELECT * FROM vw_muneer_report WHERE (CASE WHEN Release_Date >= :weekDate THEN Release_Date IS NOT NULL ELSE Release_Date IS NULL END) AND ALLOC_ID IN  ");
		sql.append(" (SELECT tt1.ID FROM (SELECT COUNT(*) AS COUNT, ID  FROM `resource_allocation` WHERE alloc_start_date <= :weekDate AND alloc_end_date >= :weekDate   ");
		sql.append(" GROUP BY `employee_id`) tt1 INNER JOIN (SELECT COUNT(*) AS COUNT, ID  FROM resource_allocation WHERE alloc_end_date >= :weekDate GROUP BY employee_id) tt2  ");
		sql.append(" ON tt1.ID = tt2.ID WHERE tt1.count = tt2.count)  ");
		sql.append(" AND ( Bu_Id IN (:orgIdList) OR Current_Bu_Id IN (:orgIdList) OR Project_Bu_ID IN (:orgIdList) )  ");
		sql.append(" GROUP BY Yash_Emp_Id)counttemp GROUP BY counttemp.customer_name,counttemp.allocation_type ");

		return sql.toString();

	}

	public static String getTeamReportQuery() {

		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT temp.Team_Name,temp.project_id,temp.Team_Id,temp.Project_Name,temp.Week_Start_Date,temp.Week_End_Date,temp.Employee_NAME, ");
		sql.append(" temp.Yash_Employee_id,SUM(temp.NonProductiveHrs),SUM(temp.ProductiveHrs),temp.planned_hrs,temp.billed_hrs ");
		sql.append(" FROM (SELECT t.`name` AS Team_Name,CONCAT(TRIM(r.first_name),' ',TRIM(r.last_name)) AS Employee_NAME, ");
		sql.append(" r.`yash_emp_id` AS Yash_Employee_id,thrs.resource_id AS resource_id,p.`project_name` AS Project_Name,p.id AS project_id,t.id AS Team_Id, ");
		sql.append(" uad.`week_start_date` AS Week_Start_Date,uad.`week_end_date` AS Week_End_Date,  ");
		sql.append(" thrs.`planned_hrs` AS planned_hrs,thrs.`billed_hrs` AS billed_hrs,thrs.resource_alloc_id, ");
		sql.append(" thrs.`week_ending_date` AS week_ending_date,IFNULL(a.productive, 0) + IFNULL(c.productive, 0) combined_productive, ");
		sql.append(" CASE IFNULL(a.productive, 0) +  ");
		sql.append(" IFNULL(c.productive, 0) WHEN 0 THEN IFNULL(uad.`d1_hours`, 0) + IFNULL(uad.`d2_hours`, 0) +  ");
		sql.append(" IFNULL(uad.`d3_hours`, 0) + IFNULL(uad.`d4_hours`, 0) + IFNULL(uad.`d5_hours`, 0) +  ");
		sql.append(" IFNULL(uad.`d6_hours`, 0) + IFNULL(uad.`d7_hours`, 0) ELSE 0 END NonProductiveHrs, ");
		sql.append(" CASE IFNULL(a.productive, 0) + IFNULL(c.productive, 0) WHEN 1 THEN IFNULL(uad.`d1_hours`, 0) +  ");
		sql.append(" IFNULL(uad.`d2_hours`, 0) + IFNULL(uad.`d3_hours`, 0) + IFNULL(uad.`d4_hours`, 0) +  ");
		sql.append(" IFNULL(uad.`d5_hours`, 0) + IFNULL(uad.`d6_hours`, 0) + IFNULL(uad.`d7_hours`, 0) ELSE 0 END ProductiveHrs, ");
		sql.append(" uad.`d1_hours`,uad.`d2_hours`,uad.`d3_hours`,    uad.`d4_hours`,uad.`d5_hours`,uad.`d6_hours`, ");
		sql.append(" uad.`d7_hours` FROM user_activity ua INNER JOIN user_activity_detail uad ON  ");
		sql.append(" ua.`id` = uad.`time_sheet_id` INNER JOIN resource_allocation ra ON ra.id = ua.res_alloc_id  ");
		sql.append(" INNER JOIN resource r ON r.employee_id = ra.employee_id INNER JOIN project p ON p.id = ra.project_id  ");
		sql.append(" INNER JOIN team t ON t.id = p.team_id LEFT JOIN activity a ON ua.`activity_id` = a.id  ");
		sql.append(" LEFT JOIN custom_activity c ON ua.`custom_activity_id` = c.`id` LEFT JOIN timehrs thrs  ");
		sql.append(" ON (thrs.`resource_alloc_id` = ra.id AND thrs.`week_ending_date` = uad.`week_end_date`)  ");
		sql.append(" WHERE uad.`week_start_date` >= :weekStartDate AND uad.`week_end_date` <= :weekEndDate AND t.id in(:teamId) GROUP BY  ");
		sql.append(" uad.`time_sheet_id`,thrs.resource_id) temp GROUP BY temp.Yash_Employee_id,temp.Week_End_Date, temp.project_id ORDER BY temp.week_end_date ASC");

		sql.append("");

		return sql.toString();
	}
	
	public static String getNewTeamReportQuery() {

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT res.Team_Name, res.project_id,res.Team_Id,res.Project_Name,res.Week_Start_Date,res.Week_End_Date,res.Employee_NAME,");
		sql.append(" res.Yash_Employee_id, CASE  WHEN(res.Week_End_Date IS NULL) THEN NULL ELSE SUM(res.NonProductiveHrs) END AS NonProductiveHrs,");
		sql.append(" CASE  WHEN(res.Week_End_Date IS NULL) THEN NULL ELSE SUM(res.ProductiveHrs) END AS ProductiveHrs,");
		sql.append(" CASE  WHEN(res.Week_End_Date IS NULL) THEN NULL ELSE SUM(res.planned_hrs) END AS planned_hrs,");
		sql.append(" CASE  WHEN(res.Week_End_Date IS NULL) THEN NULL ELSE SUM(res.billed_hrs) END AS billed_hrs FROM ");
		
		sql.append(" ( SELECT DISTINCT tmp.Team_Name,tmp.Employee_NAME, tmp.Yash_Employee_id,thrs.resource_id AS resource_id,");
		sql.append(" tmp.Project_Name ,tmp.project_id ,tmp.Team_Id, thrs.`planned_hrs` AS planned_hrs,thrs.`billed_hrs` AS billed_hrs,");
		sql.append(" thrs.resource_alloc_id, thrs.`week_ending_date` AS week_ending_date,IFNULL(a.productive, 0) + IFNULL(c.productive, 0) combined_productive, ");
		sql.append(" CASE IFNULL(a.productive, 0) +  IFNULL(c.productive, 0) WHEN 0 THEN IFNULL(uad.`d1_hours`, 0) + IFNULL(uad.`d2_hours`, 0) + "); 
		sql.append(" IFNULL(uad.`d3_hours`, 0) + IFNULL(uad.`d4_hours`, 0) + IFNULL(uad.`d5_hours`, 0) +   IFNULL(uad.`d6_hours`, 0) + IFNULL(uad.`d7_hours`, 0) ELSE 0 END NonProductiveHrs, ");
		sql.append(" CASE IFNULL(a.productive, 0) + IFNULL(c.productive, 0) WHEN 1 THEN IFNULL(uad.`d1_hours`, 0) +  IFNULL(uad.`d2_hours`, 0) + IFNULL(uad.`d3_hours`, 0) + ");
		sql.append(" IFNULL(uad.`d4_hours`, 0) + IFNULL(uad.`d5_hours`, 0) + IFNULL(uad.`d6_hours`, 0) + IFNULL(uad.`d7_hours`, 0) ELSE 0 END ProductiveHrs,");
		sql.append(" uad.`d1_hours`,uad.`d2_hours`,uad.`d3_hours`,    uad.`d4_hours`,uad.`d5_hours`,uad.`d6_hours`,	uad.`d7_hours` , CASE  WHEN ");
		sql.append(" ( uad.`week_start_date`>= :weekStartDate AND uad.`week_end_date`<= :weekEndDate  ) THEN uad.`week_start_date` ELSE NULL END AS Week_Start_Date,"); 
		sql.append(" CASE WHEN ( uad.`week_start_date`>= :weekStartDate AND uad.`week_end_date`<= :weekEndDate  ) THEN uad.`week_end_date` ELSE NULL END AS Week_End_Date ");
		sql.append(" FROM	(SELECT ra.id AS res_alloc_id , t.`name` AS Team_Name,CONCAT(TRIM(r.first_name),' ',TRIM(r.last_name)) AS Employee_NAME, ");
		sql.append(" r.`yash_emp_id` AS Yash_Employee_id,r.employee_id AS resource_id,p.`project_name` AS Project_Name,p.id AS project_id, t.id AS Team_Id FROM ");
		sql.append(" team t JOIN project p ON  t.id = p.team_id JOIN resource_allocation ra  ON p.id = ra.project_id JOIN resource r ON r.employee_id = ra.employee_id ");
		sql.append(" WHERE t.id IN(:teamId) AND (((ra.alloc_start_date <= :weekEndDate) AND (ra.alloc_end_date >= :weekStartDate)) OR ((ra.alloc_start_date <= :weekEndDate) AND ");
		sql.append(" (ra.alloc_end_date IS NULL)) OR ((ra.alloc_start_date >= :weekStartDate) AND (ra.alloc_start_date <= :weekEndDate)))  ) tmp LEFT JOIN ");
		sql.append(" user_activity ua ON ua.res_alloc_id = tmp.res_alloc_id LEFT JOIN user_activity_detail uad ON  ua.`id` = uad.`time_sheet_id`");
		sql.append(" LEFT JOIN activity a ON ua.`activity_id` = a.id LEFT JOIN custom_activity c ON ua.`custom_activity_id` = c.`id` LEFT JOIN timehrs thrs  ");
		sql.append(" ON (thrs.`resource_alloc_id` = tmp.res_alloc_id AND thrs.`week_ending_date` = uad.`week_end_date`)");  
		sql.append(" ) res GROUP BY  res.Yash_Employee_id,res.Week_End_Date, res.project_id ORDER BY res.week_end_date ASC");

		sql.append("");

		return sql.toString();
	}
	
	public static String getResourceDataByTeamQuery(){
		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT DISTINCT r.yash_emp_id, t.id team_id, t.name team_name, p.id project_id, p.project_name, ");
		sql.append(" CONCAT(TRIM(r.first_name),' ',TRIM(r.last_name)) AS Employee_NAME FROM team t JOIN project p ");
		sql.append("ON p.`team_id` = t.`id` ");
		sql.append(" JOIN resource_allocation ra "); 
		sql.append(" ON ra.`project_id` = p.`id` ");
		sql.append(" JOIN resource r ON r.employee_id = ra.`employee_id` ");
		sql.append(" WHERE t.id IN( :teamId) AND ra.curProj = 1");

		sql.append("");

		return sql.toString();
	}
	public static String getModuleReportQuery(){
		StringBuffer sql = new StringBuffer();
		  
		  
sql.append(" SELECT  temp.module, temp.Project_Name, temp.Week_Start_Date, temp.Week_End_Date, temp.Employee_NAME, temp.Yash_Employee_id, temp.planned_hrs, SUM(temp.NonProductiveHrs), ");
sql.append(" SUM(temp.ProductiveHrs), temp.billed_hrs,temp.project_id FROM (SELECT t.`name` AS Team_Name,  CONCAT(TRIM(r.first_name),' ',TRIM(r.last_name)) AS Employee_NAME,r.`yash_emp_id` AS Yash_Employee_id, ");
sql.append(" thrs.resource_id AS resource_id,p.`project_name` AS Project_Name,p.id AS project_id,t.id AS Team_Id, uad.`week_start_date` AS Week_Start_Date, uad.`week_end_date` AS Week_End_Date, ");
sql.append(" thrs.`planned_hrs` AS planned_hrs,thrs.`billed_hrs` AS billed_hrs, thrs.resource_alloc_id,thrs.`week_ending_date` AS week_ending_date, CASE ");
sql.append(" WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=0 AND  uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)) ");
sql.append(" AND uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))) ");
sql.append(" THEN  IFNULL(uad.d1_hours, 0) + IFNULL(uad.d2_hours, 0) + IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0)  ");

sql.append(" WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=0 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),0,uad.`week_start_date`) ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))=0 ");
sql.append(" AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
sql.append("THEN  IFNULL(uad.d1_hours, 0) +IFNULL(d2_hours, 0) + IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0) "); 

sql.append(" WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=0 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),0,uad.`week_start_date`) ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))=1 ");
sql.append(" AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
sql.append(" THEN  IFNULL(uad.d2_hours, 0) + IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0)  ");

sql.append(" WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=0 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),0,uad.`week_start_date`) ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))=2 ");
sql.append(" AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
sql.append(" THEN  IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0)  ");

sql.append(" WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=0 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),0,uad.`week_start_date`) ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))=3 ");
sql.append(" AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
sql.append(" THEN  IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0)  ");

sql.append(" WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=0 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),0,uad.`week_start_date`) ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))=4 ");
sql.append(" AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
sql.append(" THEN  IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0)  ");
sql.append(" WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=0 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),0,uad.`week_start_date`) ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))=5 ");
sql.append(" AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
sql.append(" THEN  IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0)  ");

sql.append(" WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=0 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),0,uad.`week_start_date`) ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))=6 ");
sql.append(" AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
sql.append(" THEN  IFNULL(uad.d7_hours, 0)  ");


sql.append(" WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=0 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),uad.`week_start_date`,0) ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
		  	
sql.append(" AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
sql.append(" AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))>-6 ");
sql.append(" AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))=-1 ");
sql.append(" THEN  IFNULL(d1_hours, 0) + IFNULL(uad.d2_hours, 0) + IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0) ");

sql.append(" WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=0 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),uad.`week_start_date`,0) ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
		  	
sql.append(" AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
sql.append(" AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))>-6 ");
sql.append(" AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))=-2 ");
sql.append(" THEN  IFNULL(d1_hours, 0) + IFNULL(uad.d2_hours, 0) + IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) ");

sql.append("WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=0 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),uad.`week_start_date`,0) ");
sql.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
		  	
sql.append(" AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
sql.append(" AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))>-6 ");
sql.append(" AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))=-3 ");
sql.append(" THEN  IFNULL(d1_hours, 0) + IFNULL(uad.d2_hours, 0) + IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0)  ");

sql.append(" WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=0 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),uad.`week_start_date`,0) ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
		  	
sql.append(" AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
sql.append(" AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))>-6 ");
sql.append(" AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))=-4 ");
sql.append(" THEN  IFNULL(d1_hours, 0) + IFNULL(uad.d2_hours, 0) + IFNULL(uad.d3_hours, 0)  ");

sql.append(" WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=0 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),uad.`week_start_date`,0) ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
		  	
sql.append(" AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
sql.append(" AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))>-6 ");
sql.append(" AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))=-5 ");
sql.append(" THEN  IFNULL(d1_hours, 0) + IFNULL(uad.d2_hours, 0) ELSE 0	END NonProductiveHrs,CASE ");
sql.append(" WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=1 AND  uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)) ");
sql.append(" AND uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))) ");
sql.append(" THEN  IFNULL(uad.d1_hours, 0) + IFNULL(uad.d2_hours, 0) + IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0)  ");

		  	
sql.append(" WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=1 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),0,uad.`week_start_date`) ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))=0 ");
sql.append(" AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
sql.append(" THEN  IFNULL(uad.d1_hours, 0) +IFNULL(d2_hours, 0) + IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0)  ");

sql.append(" WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=1 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),0,uad.`week_start_date`) ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))=1 ");
sql.append(" AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
sql.append(" THEN  IFNULL(uad.d2_hours, 0) + IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0)  ");

sql.append(" WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=1 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),0,uad.`week_start_date`) ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))=2 ");
sql.append(" AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
sql.append(" THEN  IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0)  ");

sql.append(" WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=1 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),0,uad.`week_start_date`) ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))=3 ");
sql.append(" AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
sql.append(" THEN  IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0)  ");

sql.append(" WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=1 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),0,uad.`week_start_date`) ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))=4 ");
sql.append(" AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
sql.append(" THEN  IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0)  ");

sql.append(" WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=1 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),0,uad.`week_start_date`) ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))=5 ");
sql.append(" AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
sql.append(" THEN  IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0)  ");

sql.append(" WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=1 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),0,uad.`week_start_date`) ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))=6 ");
sql.append(" AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
sql.append(" THEN  IFNULL(uad.d7_hours, 0)  ");


sql.append(" WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=1 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),uad.`week_start_date`,0) ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
		  	
sql.append(" AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
sql.append(" AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))=1 ");
sql.append(" THEN  IFNULL(d1_hours, 0) + IFNULL(uad.d2_hours, 0) + IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0) ");

sql.append(" WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=1 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),uad.`week_start_date`,0) ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
		  	
sql.append(" AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
sql.append(" AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))>=-6 ");
sql.append(" AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))=-6 ");
sql.append(" THEN  IFNULL(d1_hours, 0) + IFNULL(uad.d2_hours, 0) + IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0) ");

sql.append(" WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=1 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),uad.`week_start_date`,0) ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
		  	
sql.append(" AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
sql.append(" AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))>-6 ");
sql.append(" AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))=-1 ");
sql.append(" THEN  IFNULL(d1_hours, 0) + IFNULL(uad.d2_hours, 0) + IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0) ");
sql.append(" WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=1 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),uad.`week_start_date`,0) ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
		  	
sql.append(" AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
sql.append(" AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))>-6 ");
sql.append(" AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))=-2 ");
sql.append(" THEN  IFNULL(d1_hours, 0) + IFNULL(uad.d2_hours, 0) + IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) ");

sql.append(" WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=1 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),uad.`week_start_date`,0) ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
		  	
sql.append(" AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
sql.append(" AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))>-6 ");
sql.append(" AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))=-3 ");
sql.append(" THEN  IFNULL(d1_hours, 0) + IFNULL(uad.d2_hours, 0) + IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0)  ");

sql.append(" WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=1 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),uad.`week_start_date`,0) ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
		  	
sql.append(" AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
sql.append(" AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))>-6 ");
sql.append(" AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))=-4 ");
sql.append(" THEN  IFNULL(d1_hours, 0) + IFNULL(uad.d2_hours, 0) + IFNULL(uad.d3_hours, 0)  ");

sql.append(" WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=1 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),uad.`week_start_date`,0) ");
sql.append(" AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
		  	
sql.append(" AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
sql.append(" AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))>-6 ");
sql.append(" AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))=-5 ");
sql.append(" THEN  IFNULL(d1_hours, 0) + IFNULL(uad.d2_hours, 0) ELSE 0 	END ProductiveHrs, ");
		   
sql.append(" uad.`d1_hours`,  uad.`d2_hours`,uad.`d3_hours`, uad.`d4_hours`, uad.`d5_hours`, uad.`d6_hours`, uad.`d7_hours` , ua.module AS module FROM user_activity ua "); 
sql.append(" INNER JOIN user_activity_detail uad ON ua.`id` = uad.`time_sheet_id` INNER JOIN resource_allocation ra  ON ra.id = ua.res_alloc_id  INNER JOIN resource r  ");
sql.append(" ON r.employee_id = ra.employee_id INNER JOIN project p  ON p.id = ra.project_id  LEFT JOIN team t ON t.id = p.team_id LEFT JOIN activity a ON ua.`activity_id` = a.id "); 
sql.append(" LEFT JOIN custom_activity c ON ua.`custom_activity_id` = c.`id` LEFT JOIN timehrs thrs ON (thrs.`resource_alloc_id` = ra.id AND thrs.`week_ending_date` = uad.`week_end_date` ");
sql.append(" ) WHERE uad.`week_start_date` >=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0))  ");
sql.append(" AND uad.`week_end_date`<=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))) ");
		      
sql.append(" AND p.id IN (:projectId) GROUP BY uad.`time_sheet_id`, thrs.resource_id) temp GROUP BY temp.Yash_Employee_id, temp.Week_End_Date, temp.project_id ,module ORDER BY temp.week_end_date ASC ");
		
		return sql.toString();
	}

	public static String getCustomizedModuleQuery() {
		
		StringBuffer customizedModuleQuery = new StringBuffer();
		
			customizedModuleQuery.append("SELECT  temp.module, temp.Project_Name, temp.Week_Start_Date, temp.Week_End_Date, temp.Employee_NAME, temp.Yash_Employee_id, SUM(temp.NonProductiveHrs),  SUM(temp.ProductiveHrs), temp.planned_hrs, temp.billed_hrs FROM (SELECT t.`name` AS Team_Name,  CONCAT(TRIM(r.first_name),' ',TRIM(r.last_name)) AS Employee_NAME,r.`yash_emp_id` AS Yash_Employee_id,  thrs.resource_id AS resource_id,p.`project_name` AS Project_Name,p.id AS project_id,t.id AS Team_Id, uad.`week_start_date` AS Week_Start_Date, uad.`week_end_date` AS Week_End_Date,  thrs.`planned_hrs` AS planned_hrs,thrs.`billed_hrs` AS billed_hrs, thrs.resource_alloc_id,thrs.`week_ending_date` AS week_ending_date, CASE  WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=0 AND  uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)) ");
			customizedModuleQuery.append("AND uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))) ");
			customizedModuleQuery.append("THEN  IFNULL(uad.d1_hours, 0) + IFNULL(uad.d2_hours, 0) + IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0) ");
			
			customizedModuleQuery.append("WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=0 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),0,uad.`week_start_date`) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))=0 ");
			customizedModuleQuery.append("AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`)" );
			customizedModuleQuery.append("THEN  IFNULL(uad.d1_hours, 0) +IFNULL(d2_hours, 0) + IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0) ");
			
			customizedModuleQuery.append("WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=0 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),0,uad.`week_start_date`) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))=1 ");
			customizedModuleQuery.append("AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
			customizedModuleQuery.append("THEN  IFNULL(uad.d2_hours, 0) + IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0) ");
			
			customizedModuleQuery.append("WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=0 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),0,uad.`week_start_date`) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))=2 ");
			customizedModuleQuery.append("AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
			customizedModuleQuery.append("THEN  IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0) ");
			
			customizedModuleQuery.append("WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=0 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),0,uad.`week_start_date`) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))=3 ");
			customizedModuleQuery.append("AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
			customizedModuleQuery.append("THEN  IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0) ");
			
			customizedModuleQuery.append("WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=0 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),0,uad.`week_start_date`) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))=4 ");
			customizedModuleQuery.append("AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
			customizedModuleQuery.append("THEN  IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0)  WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=0 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),0,uad.`week_start_date`) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))=5 ");
			customizedModuleQuery.append("AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
			customizedModuleQuery.append("THEN  IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0) ");
			
			customizedModuleQuery.append("WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=0 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),0,uad.`week_start_date`) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))=6 ");
			customizedModuleQuery.append("AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
			customizedModuleQuery.append("THEN  IFNULL(uad.d7_hours, 0) ");
			
			
			customizedModuleQuery.append("WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=0 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),uad.`week_start_date`,0) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
			
			customizedModuleQuery.append("AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))>-6 ");
			customizedModuleQuery.append("AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))=-1 ");
			customizedModuleQuery.append("THEN  IFNULL(d1_hours, 0) + IFNULL(uad.d2_hours, 0) + IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0) ");
			
			customizedModuleQuery.append("WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=0 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),uad.`week_start_date`,0) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
			
			customizedModuleQuery.append("AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))>-6 ");
			customizedModuleQuery.append("AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))=-2 ");
			customizedModuleQuery.append("THEN  IFNULL(d1_hours, 0) + IFNULL(uad.d2_hours, 0) + IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) ");
			
			customizedModuleQuery.append("WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=0 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),uad.`week_start_date`,0) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
			
			customizedModuleQuery.append("AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))>-6 ");
			customizedModuleQuery.append("AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))=-3 ");
			customizedModuleQuery.append("THEN  IFNULL(d1_hours, 0) + IFNULL(uad.d2_hours, 0) + IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0) ");
			
			customizedModuleQuery.append("WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=0 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),uad.`week_start_date`,0) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
			
			customizedModuleQuery.append("AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))>-6 ");
			customizedModuleQuery.append("AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))=-4 ");
			customizedModuleQuery.append("THEN  IFNULL(d1_hours, 0) + IFNULL(uad.d2_hours, 0) + IFNULL(uad.d3_hours, 0) ");
			
			customizedModuleQuery.append("WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=0 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),uad.`week_start_date`,0) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
			
			customizedModuleQuery.append("AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))>-6 ");
			customizedModuleQuery.append("AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))=-5 ");
			customizedModuleQuery.append("THEN  IFNULL(d1_hours, 0) + IFNULL(uad.d2_hours, 0) ELSE 0    END NonProductiveHrs,CASE ");
			customizedModuleQuery.append("WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=1 AND  uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)) ");
			customizedModuleQuery.append("AND uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))) ");
			customizedModuleQuery.append("THEN  IFNULL(uad.d1_hours, 0) + IFNULL(uad.d2_hours, 0) + IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0) ");
			
			
			customizedModuleQuery.append("WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=1 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),0,uad.`week_start_date`) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))=0 ");
			customizedModuleQuery.append("AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
			customizedModuleQuery.append("THEN  IFNULL(uad.d1_hours, 0) +IFNULL(d2_hours, 0) + IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0) ");
			
			customizedModuleQuery.append("WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=1 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),0,uad.`week_start_date`) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))=1 ");
			customizedModuleQuery.append("AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
			customizedModuleQuery.append("THEN  IFNULL(uad.d2_hours, 0) + IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0) ");
			
			customizedModuleQuery.append("WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=1 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),0,uad.`week_start_date`) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))=2 ");
			customizedModuleQuery.append("AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
			customizedModuleQuery.append("THEN  IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0) ");
			
			customizedModuleQuery.append("WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=1 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),0,uad.`week_start_date`) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))=3 ");
			customizedModuleQuery.append("AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
			customizedModuleQuery.append("THEN  IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0) ");
			
			customizedModuleQuery.append("WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=1 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),0,uad.`week_start_date`) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))=4 ");
			customizedModuleQuery.append("AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
			customizedModuleQuery.append("THEN  IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0) ");
			
			customizedModuleQuery.append("WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=1 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),0,uad.`week_start_date`) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))=5 ");
			customizedModuleQuery.append("AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
			customizedModuleQuery.append("THEN  IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0) ");
			
			customizedModuleQuery.append("WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=1 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),0,uad.`week_start_date`) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))=6 ");
			customizedModuleQuery.append("AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
			customizedModuleQuery.append("THEN  IFNULL(uad.d7_hours, 0) ");
			
			
			customizedModuleQuery.append("WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=1 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),uad.`week_start_date`,0) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
			
			customizedModuleQuery.append("AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))=1 ");
			customizedModuleQuery.append("THEN  IFNULL(d1_hours, 0) + IFNULL(uad.d2_hours, 0) + IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0) ");
			
			customizedModuleQuery.append("WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=1 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),uad.`week_start_date`,0) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
			
			customizedModuleQuery.append("AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))>=-6 ");
			customizedModuleQuery.append("AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))=-6 ");
			customizedModuleQuery.append("THEN  IFNULL(d1_hours, 0) + IFNULL(uad.d2_hours, 0) + IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0) + IFNULL(uad.d7_hours, 0) ");
			
			customizedModuleQuery.append("WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=1 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),uad.`week_start_date`,0) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
			
			customizedModuleQuery.append("AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))>-6 ");
			customizedModuleQuery.append("AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))=-1 ");
			customizedModuleQuery.append("THEN  IFNULL(d1_hours, 0) + IFNULL(uad.d2_hours, 0) + IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) + IFNULL(uad.d6_hours, 0)  WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=1 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),uad.`week_start_date`,0) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
			
			customizedModuleQuery.append("AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))>-6 ");
			customizedModuleQuery.append("AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))=-2 ");
			customizedModuleQuery.append("THEN  IFNULL(d1_hours, 0) + IFNULL(uad.d2_hours, 0) + IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0) + IFNULL(uad.d5_hours, 0) ");
			
			customizedModuleQuery.append("WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=1 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),uad.`week_start_date`,0) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
			
			customizedModuleQuery.append("AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))>-6 ");
			customizedModuleQuery.append("AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))=-3 ");
			customizedModuleQuery.append("THEN  IFNULL(d1_hours, 0) + IFNULL(uad.d2_hours, 0) + IFNULL(uad.d3_hours, 0) + IFNULL(uad.d4_hours, 0) ");
			
			customizedModuleQuery.append("WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=1 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),uad.`week_start_date`,0) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
			
			customizedModuleQuery.append("AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))>-6 ");
			customizedModuleQuery.append("AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))=-4 ");
			customizedModuleQuery.append("THEN  IFNULL(d1_hours, 0) + IFNULL(uad.d2_hours, 0) + IFNULL(uad.d3_hours, 0) ");
			
			customizedModuleQuery.append("WHEN IFNULL(a.productive, 0) + IFNULL(c.productive, 0)=1 AND  IF(uad.`week_start_date`!=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)),uad.`week_start_date`,0) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekStartDate,SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)))<7 ");
			
			customizedModuleQuery.append("AND IF(uad.`week_end_date`!=(SUBDATE(:weekEndDate, -IF(((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0))!=6,((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)),0))),uad.`week_end_date`,uad.`week_end_date`) ");
			customizedModuleQuery.append("AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))>-6 ");
			customizedModuleQuery.append("AND DATEDIFF(:weekEndDate,(SUBDATE(:weekEndDate, -((WEEKDAY(SUBDATE(:weekEndDate, IF(WEEKDAY(:weekEndDate)+1!=7,WEEKDAY(:weekEndDate)+1,0))))-IF(WEEKDAY(:weekEndDate)+1!=6,WEEKDAY(:weekEndDate)+1,0)))))=-5 ");
			customizedModuleQuery.append("THEN  IFNULL(d1_hours, 0) + IFNULL(uad.d2_hours, 0) ELSE 0    END ProductiveHrs, ");
			
			customizedModuleQuery.append("uad.`d1_hours`,  uad.`d2_hours`,uad.`d3_hours`, uad.`d4_hours`, uad.`d5_hours`, uad.`d6_hours`, uad.`d7_hours` , ua.module AS module FROM user_activity ua  INNER JOIN user_activity_detail uad ON ua.`id` = uad.`time_sheet_id` INNER JOIN resource_allocation ra  ON ra.id = ua.res_alloc_id  INNER JOIN resource r  ON r.employee_id = ra.employee_id INNER JOIN project p  ON p.id = ra.project_id  LEFT JOIN team t ON t.id = p.team_id LEFT JOIN activity a ON ua.`activity_id` = a.id  LEFT JOIN custom_activity c ON ua.`custom_activity_id` = c.`id` LEFT JOIN timehrs thrs ON (thrs.`resource_alloc_id` = ra.id AND thrs.`week_ending_date` = uad.`week_end_date` ");
			customizedModuleQuery.append(") WHERE uad.`week_start_date` >=SUBDATE(:weekStartDate, IF(WEEKDAY(:weekStartDate)+1!=7,WEEKDAY(:weekStartDate)+1,0)) ");
			customizedModuleQuery.append("AND uad.`week_end_date` <= ( ");
			customizedModuleQuery.append(" SUBDATE( ");
			customizedModuleQuery.append(":weekEndDate, ");
			customizedModuleQuery.append(" - IF( ");
			customizedModuleQuery.append(" ( ");
			customizedModuleQuery.append("( ");
			customizedModuleQuery.append("WEEKDAY( ");
			customizedModuleQuery.append(" SUBDATE( ");
			customizedModuleQuery.append(":weekEndDate, ");
			customizedModuleQuery.append("IF( ");
			customizedModuleQuery.append("WEEKDAY(:weekEndDate) + 1 != 7, ");
			customizedModuleQuery.append("WEEKDAY(:weekEndDate) + 1, ");
			customizedModuleQuery.append("0 ");
			customizedModuleQuery.append(") ");
			customizedModuleQuery.append(") ");
			customizedModuleQuery.append(") ");
			customizedModuleQuery.append(") - IF( ");
			customizedModuleQuery.append(" WEEKDAY(:weekEndDate) + 1 != 6, ");
			customizedModuleQuery.append("WEEKDAY(:weekEndDate) + 1, ");
			customizedModuleQuery.append("0 ");
			customizedModuleQuery.append(") ");
			customizedModuleQuery.append(") != 6, ");
			customizedModuleQuery.append("( ");
			customizedModuleQuery.append("( ");
			customizedModuleQuery.append("WEEKDAY( ");
			customizedModuleQuery.append("SUBDATE( ");
			customizedModuleQuery.append(":weekEndDate, ");
			customizedModuleQuery.append("IF( ");
			customizedModuleQuery.append("WEEKDAY(:weekEndDate) + 1 != 7, ");
			customizedModuleQuery.append("WEEKDAY(:weekEndDate) + 1, ");
			customizedModuleQuery.append("0 ");
			customizedModuleQuery.append(") ");
			customizedModuleQuery.append(") ");
			customizedModuleQuery.append(") ");
			customizedModuleQuery.append(") - IF( ");
			customizedModuleQuery.append("WEEKDAY(:weekEndDate) + 1 != 6, ");
			customizedModuleQuery.append("WEEKDAY(:weekEndDate) + 1, ");
			customizedModuleQuery.append("0 ");
			customizedModuleQuery.append(") ");
			customizedModuleQuery.append("), ");
			customizedModuleQuery.append("0 ");
			customizedModuleQuery.append(") ");
			customizedModuleQuery.append(") ");
			customizedModuleQuery.append(") ");
			
			customizedModuleQuery.append("AND p.id IN (:projectId) GROUP BY uad.`time_sheet_id`, thrs.resource_id) temp GROUP BY temp.Yash_Employee_id, temp.Week_End_Date, temp.project_id ,module ORDER BY temp.week_end_date ASC ");
		
		return customizedModuleQuery.toString();
	}
	
	public static String getResourcesForCustomizeReportQuery() {

		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT r.yash_emp_id,CONCAT(TRIM(r.first_name),' ',TRIM(r.last_name)) AS NAME,d.designation_name,b.name AS bu_name,p.project_name,c.skill,l1.location AS base_location,  ");
		sql.append(" l2.location AS current_location,cu.customer_name,r.date_of_joining,g.grade,DATE((CASE WHEN ra.alloc_start_date < :startDate  ");
		sql.append(" THEN :startDate ELSE ra.alloc_start_date END )) AS DummyStartDate,DATE((CASE WHEN ra.alloc_end_date IS NULL THEN :endDate WHEN ra.alloc_end_date < :endDate THEN ra.alloc_end_date  ");
		sql.append(" ELSE :endDate END )) AS DummyEndDate,a.allocationtype,ra.alloc_start_date,ra.alloc_end_date,MONTHNAME(:startDate) FROM designations d,Ownership ow,bu b,competency c,  ");
		sql.append(" grade g,resource_allocation ra,allocationtype a,project p,customer cu,resource r INNER JOIN location l1 ON l1.id = r.location_id INNER JOIN location l2 ON l2.id = r.payroll_location  ");
		sql.append(" WHERE r.designation_id = d.id AND r.competency = c.id AND r.grade_id = g.id AND r.ownership = ow.id AND r.employee_id = ra.employee_id AND ra.allocation_type_id = a.id AND ra.project_id = p.id   ");
		sql.append(" AND p.customer_name_id = cu.id AND p.bu_id = b.id AND ow.ownership_name <> 'Loan' AND r.employee_category <> 2 AND r.yash_emp_id <> '%C%' AND (p.bu_id = '24' OR r.bu_id='24') AND (((ra.alloc_start_date <= :endDate) AND (ra.alloc_end_date >= :startDate)) OR  ");
		sql.append(" ((ra.alloc_start_date <= :endDate) AND (ra.alloc_end_date IS NULL)) OR ((ra.alloc_start_date >= :startDate) AND (ra.alloc_start_date <= :endDate))) AND ra.`allocation_type_id` IN (1,6,4) AND r.`grade_id` <> 7 ORDER BY NAME   ");

		return sql.toString();
	}
	
	public static String getBillableResourcesForCustomizeReportQuery() {

		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT r.yash_emp_id,CONCAT(TRIM(r.first_name),' ',TRIM(r.last_name)) AS NAME,d.designation_name,b.name AS bu_name,p.project_name,c.skill,l1.location AS base_location,  ");
		sql.append(" l2.location AS current_location,cu.customer_name,r.date_of_joining,g.grade,DATE((CASE WHEN ra.alloc_start_date < :startDate  ");
		sql.append(" THEN :startDate ELSE ra.alloc_start_date END )) AS DummyStartDate,DATE((CASE WHEN ra.alloc_end_date IS NULL THEN :endDate WHEN ra.alloc_end_date < :endDate THEN ra.alloc_end_date  ");
		sql.append(" ELSE :endDate END )) AS DummyEndDate,a.allocationtype,ra.alloc_start_date,ra.alloc_end_date,MONTHNAME(:startDate) FROM designations d,Ownership ow,bu b,competency c,  ");
		sql.append(" grade g,resource_allocation ra,allocationtype a,project p,customer cu,resource r INNER JOIN location l1 ON l1.id = r.location_id INNER JOIN location l2 ON l2.id = r.payroll_location  ");
		sql.append(" WHERE r.designation_id = d.id AND r.competency = c.id AND r.grade_id = g.id AND r.ownership = ow.id AND r.employee_id = ra.employee_id AND ra.allocation_type_id = a.id AND ra.project_id = p.id   ");
		sql.append(" AND p.customer_name_id = cu.id AND p.bu_id = b.id AND ow.ownership_name <> 'Loan' AND r.employee_category <> 2 AND r.yash_emp_id <> '%C%' AND (p.bu_id = '24' OR r.bu_id='24') AND (((ra.alloc_start_date <= :endDate) AND (ra.alloc_end_date >= :startDate)) OR  ");
		sql.append(" ((ra.alloc_start_date <= :endDate) AND (ra.alloc_end_date IS NULL)) OR ((ra.alloc_start_date >= :startDate) AND (ra.alloc_start_date <= :endDate))) AND ra.`allocation_type_id` IN (2,3,5,7) AND r.`grade_id` <> 7 ORDER BY NAME   ");

		return sql.toString();
	}
	
	public static String getNewJoineeResourcesForCustomizeReportQuery(String allocationType) {

		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT r.yash_emp_id,CONCAT(TRIM(r.first_name),' ',TRIM(r.last_name)) AS NAME,d.designation_name,b.name AS bu_name,p.project_name,c.skill,l1.location AS base_location,  ");
		sql.append(" l2.location AS current_location,cu.customer_name,r.date_of_joining,g.grade,DATE((CASE WHEN ra.alloc_start_date < :startDate  ");
		sql.append(" THEN :startDate ELSE ra.alloc_start_date END )) AS DummyStartDate,DATE((CASE WHEN ra.alloc_end_date IS NULL THEN :endDate WHEN ra.alloc_end_date < :endDate THEN ra.alloc_end_date  ");
		sql.append(" ELSE :endDate END )) AS DummyEndDate,a.allocationtype,ra.alloc_start_date,ra.alloc_end_date,MONTHNAME(:startDate) FROM designations d,Ownership ow,bu b,competency c,  ");
		sql.append(" grade g,resource_allocation ra,allocationtype a,project p,customer cu,resource r INNER JOIN location l1 ON l1.id = r.location_id INNER JOIN location l2 ON l2.id = r.payroll_location  ");
		sql.append(" WHERE r.designation_id = d.id AND r.competency = c.id AND r.grade_id = g.id AND r.ownership = ow.id AND r.employee_id = ra.employee_id AND ra.allocation_type_id = a.id AND ra.project_id = p.id   ");
		sql.append(" AND p.customer_name_id = cu.id AND p.bu_id = b.id AND ow.ownership_name <> 'Loan' AND r.employee_category <> 2 AND r.yash_emp_id <> '%C%' AND (p.bu_id = '24' OR r.bu_id='24') AND (((ra.alloc_start_date <= :endDate) AND (ra.alloc_end_date >= :startDate)) OR  ");
		sql.append(" ((ra.alloc_start_date <= :endDate) AND (ra.alloc_end_date IS NULL)) OR ((ra.alloc_start_date >= :startDate) AND (ra.alloc_start_date <= :endDate))) AND r.`grade_id` <> 7  AND r.`date_of_joining` BETWEEN :startDate AND :endDate "+ allocationType +" ORDER BY NAME   ");

		return sql.toString();
	}
	
	
	public static String getResourcesFromBenchToProject() {

		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT r.yash_emp_id,CONCAT(TRIM(r.first_name),' ',TRIM(r.last_name)) AS NAME,d.designation_name,b.name AS bu_name,p.project_name,c.skill,l1.location AS base_location,  ");
		sql.append(" l2.location AS current_location,cu.customer_name,r.date_of_joining,g.grade,DATE((CASE WHEN ra.alloc_start_date < :startDate  ");
		sql.append(" THEN :startDate ELSE ra.alloc_start_date END )) AS DummyStartDate,DATE((CASE WHEN ra.alloc_end_date IS NULL THEN :endDate WHEN ra.alloc_end_date < :endDate THEN ra.alloc_end_date  ");
		sql.append(" ELSE :endDate END )) AS DummyEndDate,a.allocationtype,ra.alloc_start_date,ra.alloc_end_date,MONTHNAME(:startDate) FROM designations d,Ownership ow,bu b,competency c,  ");
		sql.append(" grade g,resource_allocation ra,allocationtype a,project p,customer cu,resource r INNER JOIN location l1 ON l1.id = r.location_id INNER JOIN location l2 ON l2.id = r.payroll_location  ");
		sql.append(" WHERE r.designation_id = d.id AND r.competency = c.id AND r.grade_id = g.id AND r.ownership = ow.id AND r.employee_id = ra.employee_id AND ra.allocation_type_id = a.id AND ra.project_id = p.id   ");
		sql.append(" AND p.customer_name_id = cu.id AND p.bu_id = b.id AND ow.ownership_name <> 'Loan' AND r.employee_category <> 2 AND r.yash_emp_id <> '%C%' AND (((ra.alloc_start_date <= :endDate) AND (ra.alloc_end_date >= :startDate)) OR  ");
		sql.append(" ((ra.alloc_start_date <= :endDate) AND (ra.alloc_end_date IS NULL)) OR ((ra.alloc_start_date >= :startDate) AND (ra.alloc_start_date <= :endDate))) AND ra.`allocation_type_id` IN (2,3,5,7) AND r.`grade_id` <> 7 AND r.`employee_id` IN ");
		sql.append(" (SELECT DISTINCT r.employee_id FROM Ownership ow,bu b, grade g,resource_allocation ra,allocationtype a,project p,customer cu,resource r");
		sql.append(" WHERE r.grade_id = g.id AND r.ownership = ow.id AND r.employee_id = ra.employee_id AND ra.allocation_type_id = a.id AND ra.project_id = p.id   ");
		sql.append(" AND p.customer_name_id = cu.id AND p.bu_id = b.id AND ow.ownership_name <> 'Loan' AND r.employee_category <> 2 AND r.yash_emp_id <> '%C%' AND r.bu_id = '24' AND (((ra.alloc_start_date <= :endDate) AND (ra.alloc_end_date >= :startDate)) OR  ");
		sql.append(" ((ra.alloc_start_date <= :endDate) AND (ra.alloc_end_date IS NULL)) OR ((ra.alloc_start_date >= :startDate) AND (ra.alloc_start_date <= :endDate))) AND ra.`allocation_type_id` IN (1,6,4) AND r.`grade_id` <> 7)");	
		sql.append("ORDER BY NAME ");

		return sql.toString();
	}
	
	public static String getReleasedResourcesOfBG4BU5() {

		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT r.yash_emp_id,CONCAT(TRIM(r.first_name),' ',TRIM(r.last_name)) AS NAME,d.designation_name,b.name AS bu_name,p.project_name,c.skill,l1.location AS base_location,  ");
		sql.append(" l2.location AS current_location,cu.customer_name,r.date_of_joining,g.grade,DATE((CASE WHEN ra.alloc_start_date < :startDate  ");
		sql.append(" THEN :startDate ELSE ra.alloc_start_date END )) AS DummyStartDate,DATE((CASE WHEN ra.alloc_end_date IS NULL THEN :endDate WHEN ra.alloc_end_date < :endDate THEN ra.alloc_end_date  ");
		sql.append(" ELSE :endDate END )) AS DummyEndDate,a.allocationtype,ra.alloc_start_date,ra.alloc_end_date,MONTHNAME(:startDate) FROM designations d,Ownership ow,bu b,competency c,  ");
		sql.append(" grade g,resource_allocation ra,allocationtype a,project p,customer cu,resource r INNER JOIN location l1 ON l1.id = r.location_id INNER JOIN location l2 ON l2.id = r.payroll_location  ");
		sql.append(" WHERE r.designation_id = d.id AND r.competency = c.id AND r.grade_id = g.id AND r.ownership = ow.id AND r.employee_id = ra.employee_id AND ra.allocation_type_id = a.id AND ra.project_id = p.id   ");
		sql.append(" AND p.customer_name_id = cu.id AND p.bu_id = b.id AND ow.ownership_name <> 'Loan' AND r.employee_category <> 2 AND r.yash_emp_id <> '%C%' AND (((ra.alloc_start_date <= :endDate) AND (ra.alloc_end_date >= :startDate)) OR  ");
		sql.append(" ((ra.alloc_start_date <= :endDate) AND (ra.alloc_end_date IS NULL)) OR ((ra.alloc_start_date >= :startDate) AND (ra.alloc_start_date <= :endDate))) AND r.`grade_id` <> 7 AND r.release_date BETWEEN :startDate AND :endDate ");	
		sql.append("ORDER BY NAME ");

		return sql.toString();
	}

	public static String getResourceTransferToBG4BU5() {

		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT tbl3.yash_emp_id,tbl3.NAME, tbl3.designation_name, tbl3.ttl_bu_name ,tbl3.bu_name ,tbl3.Transfer_Month ,tbl3.project_name ,tbl3.skill ,");
		sql.append(" tbl3.base_location ,tbl3.current_location ,tbl3.customer_name ,tbl3.allocationtype ,tbl3.transfer_date ,tbl3.alloc_start_date ,tbl3.alloc_end_date"); 
		sql.append(" FROM( SELECT *,SUBSTRING_INDEX(all_bu_name, ',', 2) AS ttl_bu_name FROM ( SELECT *, GROUP_CONCAT(bu_name) AS all_bu_name FROM( ");
		sql.append(" SELECT r.yash_emp_id,CONCAT(TRIM(r.first_name),' ',TRIM(r.last_name)) AS NAME,d.designation_name,MONTHNAME(:startDate) AS Transfer_Month, b.name AS bu_name,");
		sql.append("CASE  WHEN  ra.alloc_end_date IS  NULL THEN p.project_name");   
		sql.append(" WHEN (rltd.`transfer_date` IS NOT NULL AND rltd.transfer_date <= ra.alloc_end_date)  THEN p.project_name ");
		sql.append("WHEN (rltd.`transfer_date` IS NULL AND rltd.creation_timestamp <= ra.alloc_end_date) THEN p.project_name");
		sql.append(" ELSE '' 	END AS project_name,");
		sql.append("c.skill,l1.location AS base_location, ");  
		sql.append(" l2.location AS current_location,cu.customer_name,a.allocationtype,rltd.`transfer_date`,ra.alloc_start_date,ra.alloc_end_date,rltd.event_id");
		sql.append(" FROM resource r  ");
		sql.append(" INNER JOIN location l1 ON l1.id = r.location_id "); 
		sql.append(" INNER JOIN location l2 ON l2.id = r.payroll_location ");  
		sql.append(" INNER JOIN resource_ln_tr_dtl rltd ON rltd.employee_id = r.employee_id "); 
		sql.append(" INNER JOIN designations d ON r.designation_id = d.id  ");
		sql.append(" INNER JOIN  competency c ON r.competency = c.id  ");
		sql.append(" INNER JOIN grade g ON r.grade_id = g.id  ");
		sql.append(" INNER JOIN  Ownership ow ON r.ownership = ow.id "); 
		sql.append(" INNER JOIN resource_allocation ra ON r.employee_id = ra.employee_id "); 
		sql.append(" INNER JOIN  allocationtype a ON ra.allocation_type_id = a.id  ");
		sql.append(" INNER JOIN  project p ON ra.project_id = p.id    ");
		sql.append(" INNER JOIN  customer cu ON p.customer_name_id = cu.id "); 
		sql.append(" INNER JOIN  bu b ON rltd.bu_id = b.id  ");
		sql.append(" WHERE r.employee_category <> 2 AND r.yash_emp_id <> '%C%' "); 
		sql.append(" AND (((ra.alloc_start_date <= :endDate) AND (ra.alloc_end_date >= :startDate)) OR ((ra.alloc_start_date <= :endDate) AND (ra.alloc_end_date IS NULL)) OR ((ra.alloc_start_date >=:startDate) "); 
		sql.append(" AND (ra.alloc_start_date <= :endDate))) AND rltd.`creation_timestamp` BETWEEN :startDate AND :endDate AND r.`grade_id` <> 7 AND (rltd.`event_id` IS NULL OR  rltd.event_id IN (6,7,3,-1)) AND ra.`curProj` = 1");
		sql.append(" GROUP BY rltd.creation_timestamp ,rltd.event_id");
		sql.append(" ORDER BY  yash_emp_id DESC,rltd.`id` DESC,rltd.`creation_timestamp` DESC ");
		sql.append(" ) tbl  ");
		sql.append(" GROUP BY yash_emp_id "); 
		sql.append(" HAVING COUNT(yash_emp_id) >=2 AND event_id <> -1 ");
		sql.append(" ORDER BY tbl.alloc_start_date DESC ");
		sql.append(" )tbl2 ) tbl3 WHERE tbl3.ttl_bu_name LIKE 'BG4-BU5,%' ORDER BY tbl3.NAME ASC ");

		return sql.toString();
	}

	public static String getResourceTransferFromBG4BU5() {

		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT tbl3.yash_emp_id,tbl3.NAME, tbl3.designation_name, tbl3.ttl_bu_name ,tbl3.bu_name ,tbl3.Transfer_Month ,tbl3.project_name ,tbl3.skill ,");
		sql.append(" tbl3.base_location ,tbl3.current_location ,tbl3.customer_name ,tbl3.allocationtype ,tbl3.transfer_date ,tbl3.alloc_start_date ,tbl3.alloc_end_date"); 
		sql.append(" FROM( SELECT *,SUBSTRING_INDEX(all_bu_name, ',', 2) AS ttl_bu_name FROM ( SELECT *, GROUP_CONCAT(bu_name) AS all_bu_name FROM( ");
		sql.append(" SELECT r.yash_emp_id,CONCAT(TRIM(r.first_name),' ',TRIM(r.last_name)) AS NAME,d.designation_name,MONTHNAME(:startDate) AS Transfer_Month, b.name AS bu_name,");
		sql.append("CASE  WHEN  ra.alloc_end_date IS  NULL THEN p.project_name");   
		sql.append(" WHEN (rltd.`transfer_date` IS NOT NULL AND rltd.transfer_date <= ra.alloc_end_date)  THEN p.project_name ");
		sql.append("WHEN (rltd.`transfer_date` IS NULL AND rltd.creation_timestamp <= ra.alloc_end_date) THEN p.project_name");
		sql.append(" ELSE '' 	END AS project_name,");
		sql.append("c.skill,l1.location AS base_location, ");  
		sql.append(" l2.location AS current_location,cu.customer_name,a.allocationtype,rltd.`transfer_date`,ra.alloc_start_date,ra.alloc_end_date,rltd.event_id");
		sql.append(" FROM resource r  ");
		sql.append(" INNER JOIN location l1 ON l1.id = r.location_id "); 
		sql.append(" INNER JOIN location l2 ON l2.id = r.payroll_location ");  
		sql.append(" INNER JOIN resource_ln_tr_dtl rltd ON rltd.employee_id = r.employee_id "); 
		sql.append(" INNER JOIN designations d ON r.designation_id = d.id  ");
		sql.append(" INNER JOIN  competency c ON r.competency = c.id  ");
		sql.append(" INNER JOIN grade g ON r.grade_id = g.id  ");
		sql.append(" INNER JOIN  Ownership ow ON r.ownership = ow.id "); 
		sql.append(" INNER JOIN resource_allocation ra ON r.employee_id = ra.employee_id "); 
		sql.append(" INNER JOIN  allocationtype a ON ra.allocation_type_id = a.id  ");
		sql.append(" INNER JOIN  project p ON ra.project_id = p.id    ");
		sql.append(" INNER JOIN  customer cu ON p.customer_name_id = cu.id "); 
		sql.append(" INNER JOIN  bu b ON rltd.bu_id = b.id  ");
		sql.append(" WHERE r.employee_category <> 2 AND r.yash_emp_id <> '%C%' "); 
		sql.append(" AND (((ra.alloc_start_date <= :endDate) AND (ra.alloc_end_date >= :startDate)) OR ((ra.alloc_start_date <= :endDate) AND (ra.alloc_end_date IS NULL)) OR ((ra.alloc_start_date >=:startDate) "); 
		sql.append(" AND (ra.alloc_start_date <= :endDate))) AND rltd.`creation_timestamp` BETWEEN :startDate AND :endDate AND r.`grade_id` <> 7 AND (rltd.`event_id` IS NULL OR  rltd.event_id IN (6,7,3,-1)) AND ra.`curProj` = 1");
		sql.append(" GROUP BY rltd.creation_timestamp,rltd.event_id ");
		sql.append(" ORDER BY  yash_emp_id DESC,rltd.`id` DESC,rltd.`creation_timestamp` DESC ");
		sql.append(" ) tbl  ");
		sql.append(" GROUP BY yash_emp_id "); 
		sql.append(" HAVING COUNT(yash_emp_id) >=2 AND event_id <> -1 ");
		sql.append(" ORDER BY tbl.alloc_start_date DESC ");
		sql.append(" )tbl2 ) tbl3 WHERE tbl3.ttl_bu_name LIKE '%,BG4-BU5' ORDER BY tbl3.NAME ASC ");
		return sql.toString();
	}

	public static String getResourceReportBySelectedHeaders(String selectedHeaders) {
		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT " + selectedHeaders + " FROM vw_muneer_report  ");
		//sql.append(" SELECT " + selectedHeaders + " FROM vw_muneer_report WHERE "); 
		//sql.append(" (CASE  WHEN Release_Date >= :weekDate THEN Release_Date IS NOT NULL ELSE Release_Date IS NULL END AND");  
		/*sql.append(" (Bu_Id IN (:orgIdList) ");
		sql.append("OR Current_Bu_Id IN (:orgIdList) ");
		sql.append("OR Project_Bu_ID IN (:orgIdList)) ");
		sql.append("AND Project_ID IN (:projIdList) ");*/
		//sql.append("AND Current_Project_Flag IN (:reportId) ");
		/*sql.append("AND (Resource_Allocation_End_Date IS NULL ");
		sql.append("OR Resource_Allocation_End_Date>=:weekDate) ");
		sql.append("AND Allocation_Start_Date <= :weekDate ");*/
		sql.append(" WHERE `Yash_Emp_Id`  LIKE '1_____%' AND `Date_Of_Joining` IS NOT NULL  ");
		/*sql.append("AND (Bu_Id IN (:orgIdList)) ");
		sql.append("OR Current_Bu_Id IN (:orgIdList) ");
		sql.append("OR Project_Bu_ID IN (:orgIdList)) "); 
		sql.append("AND Project_ID IN (:projIdList) ");
		sql.append("AND Current_Project_Flag IN (:reportId) ");*/
		sql.append("GROUP BY `yash_emp_id` ORDER BY `yash_emp_id`   LIMIT 50");
		return sql.toString();

	}

	public static String getDBReportData(Integer id, String role, List<Integer> customerList,
			List<Integer> groupList, String status, List<String> hiringUnits, List<String> reqUnits)  {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT SUM(sr.req_resources) as noOfResources,c.id as custId,  sr.status as status, rr.date_of_Indent,rr.id as requestId,SUM(sr.open) as open,SUM(sr.closed) as closed,  ");
		sql.append(" sr.id as skillId,c.customer_name as clientName  "); 
		sql.append(" FROM request_requisition rr, customer c,skill_request_requisition sr  ");
		sql.append(" WHERE rr.customer_id=c.id AND rr.id=sr.request_id  ");
		/*if(startDate!=null){
			sql.append(" AND rr.date_of_Indent >=:startDate");
		}
		if(endDate != null ){
			sql.append(" AND rr.date_of_Indent <=:endDate");
		}*/
		if(customerList!=null){
			sql.append(" AND c.id in (:customerList) ");
		}
		if(groupList!=null){
			sql.append(" AND rr.group_id in (:groupList) ");
		}
		/*if(status==null || status.equalsIgnoreCase("A")){
			sql.append(" AND (sr.status is NULL OR (sr.status <> 'Closed')) ");
		}else{
			sql.append(" AND  sr.status like 'Closed'");
		}*/
		if(hiringUnits!=null){
			sql.append(" AND rr.hiring_bgbu IN (:hiringUnits) ");
		}
		if(reqUnits!=null){
			sql.append(" AND  rr.project_bu IN (:reqUnits) ");
		}
		
		if (!role.equalsIgnoreCase("ROLE_ADMIN")) {
			sql.append(" AND rr.emp_id = :empid");
		}
		sql.append(" GROUP BY c.customer_name");
	
		return sql.toString();
	}
	
	public static String getInfoReportQuery()
	{
		StringBuffer sql = new StringBuffer();
		/*sql.append("SELECT u.emp_id, u.emp_name, u.process_status,  emp.yempid , u.date_of_joining, u.email_id, u.designation,des.designation_name AS rmsDesignation, u.base_location AS infoBaseLoc,  ");
		sql.append(" baseLocation.location AS rmsBaseLoc, u.current_location AS infoCurLoc, currentLocation.location AS rmsCurLoc, u.business_group AS infobg, u.business_unit AS infobu, ");
		sql.append(" u.IRM_name, CONCAT( TRIM(rmsirm.first_name), ' ', TRIM(rmsirm.last_name) ) AS  rmsIRM, u.SRM_name, CONCAT( TRIM(rmssrm.first_name), ' ', TRIM(rmssrm.last_name) ) AS  rmsSRM ,");
		sql.append("u.insertion_timestamp, u.modified_name,  u.IRM_code, u.SRM_code, rmsirm.yash_emp_id AS rmsIrmId, rmssrm.yash_emp_id AS rmsSrmId, emp.ProjectBGBU,  CONCAT(u.business_group,'-',u.business_unit) AS infoResourceBGBU, emp.ResourceBGBU AS rmsResourceBGBU, ");
		sql.append("CONCAT(infoirmBG.name,'-',infoirmBu.name) AS infoIRMBGBU,CONCAT(infosrmBG.name,'-',infosrmBu.name) AS infoSRMBGBU, ");
		sql.append("CONCAT(rmsirmbg.name,'-',rmsirmbu.name) AS rmsIRMBGBU,CONCAT(rmssrmbg.name,'-',rmssrmbu.name) AS rmsSRMBGBU,  emp.resbg, emp.resbu ");
		sql.append(	" FROM info_active_employee u LEFT JOIN resource_project_BGBU emp ON u.emp_id = emp.yempid ");
		sql.append("LEFT JOIN  resource irmemp ON u.irm_code = irmemp.yash_emp_id LEFT JOIN org_hierarchy infoirmBu ON irmemp.bu_id=infoirmBu.id ");
		sql.append(	"LEFT JOIN org_hierarchy infoirmBG ON infoirmBu.parent_id=infoirmBG.id ");
		sql.append("LEFT JOIN  resource srmemp ON u.srm_code = srmemp.yash_emp_id LEFT JOIN org_hierarchy infosrmBu ON srmemp.bu_id=infosrmBu.id ");
		sql.append(	"LEFT JOIN org_hierarchy infosrmBG ON infosrmBu.parent_id=infosrmBG.id ");
		sql.append("LEFT JOIN resource rmsirm ON emp.rmsirmid=rmsirm.employee_id LEFT JOIN org_hierarchy rmsirmbu ON rmsirm.bu_id=rmsirmbu.id ");
		sql.append(	"LEFT JOIN org_hierarchy rmsirmbg ON rmsirmbu.parent_id=rmsirmbg.id ");
		sql.append("LEFT JOIN resource rmssrm ON emp.rmssrmid=rmssrm.employee_id LEFT JOIN org_hierarchy rmssrmbu ON rmssrm.bu_id=rmssrmbu.id ");
		sql.append("LEFT JOIN org_hierarchy rmssrmbg ON rmssrmbu.parent_id=rmssrmbg.id LEFT JOIN designations des ON emp.did = des.id ");
		sql.append(	"LEFT JOIN location baseLocation ON emp.lid = baseLocation.id LEFT JOIN location currentLocation ON emp.plid = currentLocation.id ");
		*/
				
		sql.append(" SELECT  u.emp_id, u.emp_name, u.process_status,  IF(emp.yempid, 'EXISTING', 'NEW') AS ResType, u.date_of_joining, u.email_id, u.designation,des.designation_name AS rmsDesignation, u.base_location AS infoBaseLoc,  ");
		sql.append(" baseLocation.location AS rmsBaseLoc, u.current_location AS infoCurLoc, currentLocation.location AS rmsCurLoc, u.business_group AS infobg, u.business_unit AS infobu, u.IRM_name, CONCAT( TRIM(rmsirm.first_name), ' ', TRIM(rmsirm.last_name) ) AS  rmsIRM, u.SRM_name, CONCAT( TRIM(rmssrm.first_name), ' ', TRIM(rmssrm.last_name) ) AS  rmsSRM ,	u.insertion_timestamp, u.modified_name,  u.IRM_code, u.SRM_code, rmsirm.yash_emp_id AS rmsIrmId, rmssrm.yash_emp_id AS rmsSrmId, emp.ProjectBGBU,  CONCAT(u.business_group,'-',u.business_unit) AS infoResourceBGBU, emp.ResourceBGBU AS rmsResourceBGBU, ");
		sql.append(" CONCAT(infoirmBG.name,'-',infoirmBu.name) AS infoIRMBGBU,CONCAT(infosrmBG.name,'-',infosrmBu.name) AS infoSRMBGBU, ");
		sql.append(" CONCAT(rmsirmbg.name,'-',rmsirmbu.name) AS rmsIRMBGBU,CONCAT(rmssrmbg.name,'-',rmssrmbu.name) AS rmsSRMBGBU,  emp.resbg, emp.resbu "); 
		sql.append(" FROM info_active_employee u LEFT JOIN resource_project_bgbu emp ON u.emp_id = emp.yempid ");
		sql.append(" LEFT JOIN  resource irmemp ON u.irm_code = irmemp.yash_emp_id LEFT JOIN org_hierarchy infoirmBu ON irmemp.bu_id=infoirmBu.id ");
		sql.append(" LEFT JOIN org_hierarchy infoirmBG ON infoirmBu.parent_id=infoirmBG.id ");
		sql.append(" LEFT JOIN  resource srmemp ON u.srm_code = srmemp.yash_emp_id LEFT JOIN org_hierarchy infosrmBu ON srmemp.bu_id=infosrmBu.id  ");
		sql.append(" LEFT JOIN org_hierarchy infosrmBG ON infosrmBu.parent_id=infosrmBG.id  ");
		sql.append(" LEFT JOIN resource rmsirm ON emp.rmsirmid=rmsirm.employee_id LEFT JOIN org_hierarchy rmsirmbu ON rmsirm.bu_id=rmsirmbu.id  ");
		sql.append(" LEFT JOIN org_hierarchy rmsirmbg ON rmsirmbu.parent_id=rmsirmbg.id  ");
		sql.append(" LEFT JOIN resource rmssrm ON emp.rmssrmid=rmssrm.employee_id LEFT JOIN org_hierarchy rmssrmbu ON rmssrm.bu_id=rmssrmbu.id  ");
		sql.append(" LEFT JOIN org_hierarchy rmssrmbg ON rmssrmbu.parent_id=rmssrmbg.id LEFT JOIN designations des ON emp.did = des.id  ");
		sql.append(" LEFT JOIN location baseLocation ON emp.lid = baseLocation.id LEFT JOIN location currentLocation ON emp.plid = currentLocation.id  ");
		sql.append(" where emp.curproj=1 || emp.yempId is null ");
	
		sql.append(" union ");
		sql.append(" SELECT u.emp_id, u.emp_name,   u.process_status, IF(a.id, 'EXISTING', 'NEW') AS ResType, u.date_of_joining, u.email_id, u.designation,des.designation_name AS rmsDesignation, u.base_location AS infoBaseLoc, ");
		sql.append(" baseLocation.location AS rmsBaseLoc, u.current_location AS infoCurLoc, currentLocation.location AS rmsCurLoc, u.business_group AS infobg, u.business_unit AS infobu, ");
		sql.append(" u.IRM_name,  CONCAT( TRIM(rmsirm.first_name), ' ', TRIM(rmsirm.last_name) ) AS  rmsIRM, u.SRM_name, CONCAT( TRIM(rmssrm.first_name), ' ', TRIM(rmssrm.last_name) ) AS  rmsSRM , ");
		sql.append(" u.insertion_timestamp, u.modified_name,  u.IRM_code, u.SRM_code, rmsirm.yash_emp_id AS rmsIrmId, rmssrm.yash_emp_id AS rmsSrmId, 'NA' AS ProjectBGBU,  CONCAT(u.business_group,'-',u.business_unit) AS infoResourceBGBU, CONCAT(rmsbg.name,'-',rmsbu.name) AS rmsResourceBGBU, ");
		sql.append(" CONCAT(infoirmBG.name,'-',infoirmBu.name) AS infoIRMBGBU,CONCAT(infosrmBG.name,'-',infosrmBu.name) AS infoSRMBGBU, ");
		sql.append(" CONCAT(rmsirmbg.name,'-',rmsirmbu.name) AS rmsIRMBGBU,CONCAT(rmssrmbg.name,'-',rmssrmbu.name) AS rmsSRMBGBU,  rmsbg.name AS resbg, rmsbu.name AS resbu  ");
		sql.append(" FROM info_active_employee u JOIN  ");
		sql.append(" (SELECT  ");
		sql.append(" r.yash_emp_id AS id,r.current_reporting_manager AS rmsirmid, r.current_reporting_manager_two AS rmssrmid, ");
		sql.append(" r.designation_id AS did,r.location_id AS lid, r.payroll_location AS plid, r.bu_id AS bid FROM resource r ");
		sql.append(" WHERE NOT EXISTS(SELECT  ra.employee_id  FROM resource_allocation ra WHERE ra.employee_id = r.employee_id AND ra.curProj = 1)) ");	
		sql.append(" a ON u.emp_id=a.id  ");
		sql.append(" LEFT JOIN org_hierarchy rmsBu ON a.bid=rmsBu.id "); 
		sql.append(" LEFT JOIN org_hierarchy rmsbg ON rmsBu.parent_id=rmsbg.id "); 
		sql.append(" LEFT JOIN  resource irmemp ON u.irm_code = irmemp.yash_emp_id LEFT JOIN org_hierarchy infoirmBu ON irmemp.bu_id=infoirmBu.id "); 
		sql.append(" LEFT JOIN org_hierarchy infoirmBG ON infoirmBu.parent_id=infoirmBG.id  ");
		sql.append(" LEFT JOIN  resource srmemp ON u.srm_code = srmemp.yash_emp_id LEFT JOIN org_hierarchy infosrmBu ON srmemp.bu_id=infosrmBu.id "); 
		sql.append(" LEFT JOIN org_hierarchy infosrmBG ON infosrmBu.parent_id=infosrmBG.id  ");
		sql.append(" LEFT JOIN designations des ON a.did = des.id LEFT JOIN location baseLocation ON a.lid = baseLocation.id "); 
		sql.append(" LEFT JOIN location currentLocation ON a.plid = currentLocation.id  ");
		sql.append(" LEFT JOIN resource rmsirm ON a.rmsirmid=rmsirm.employee_id LEFT JOIN org_hierarchy rmsirmbu ON rmsirm.bu_id=rmsirmbu.id "); 
		sql.append(" LEFT JOIN org_hierarchy rmsirmbg ON rmsirmbu.parent_id=rmsirmbg.id  ");
		sql.append(" LEFT JOIN resource rmssrm ON a.rmssrmid=rmssrm.employee_id LEFT JOIN org_hierarchy rmssrmbu ON rmssrm.bu_id=rmssrmbu.id "); 
		sql.append(" LEFT JOIN org_hierarchy rmssrmbg ON rmssrmbu.parent_id=rmssrmbg.id  ");

	
		return sql.toString();
	}
	

																																																												
}
