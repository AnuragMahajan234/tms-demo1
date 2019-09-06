/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.6.30-log : Database - rms_prod_4.0
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`rms_prod_4.0` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `rms_prod_4.0`;

/*Table structure for table `bu` */

DELIMITER $$

USE `rms_prod_4.0`$$

DROP VIEW IF EXISTS `bu`$$

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `bu` AS (
SELECT
  `o`.`id`   AS `id`,
  CONCAT(`p`.`name`,'-',`o`.`name`) AS `name`,
  CONCAT(`p`.`name`,'-',`o`.`name`) AS `CODE`,
  `p`.`name` AS `BgName`,
  `o`.`name` AS `BuName`
FROM (`org_hierarchy` `o`
   JOIN `org_hierarchy` `p`
     ON ((`o`.`parent_id` = `p`.`id`)))
WHERE (`o`.`parent_id` <> (SELECT
                             `org_hierarchy`.`id`
                           FROM `org_hierarchy`
                           WHERE (`org_hierarchy`.`name` = 'Organization'))))$$

DELIMITER ;

/*Table structure for table `rephrs_weekenddate` */


/*Table structure for table `resource_allocation_project` */


/*Table structure for table `timehrs_admin_resource_data` */


/*Table structure for table `timehrs_admin_view` */


/*Table structure for table `timehrs_employeeid_projectname` */


/*Table structure for table `timehrs_total_billhrs_planhrs` */


/*Table structure for table `timehrs_totalrephrs` */


/*Table structure for table `user_activity_view` */


/*Table structure for table `vw_dm_project_visibilty` */


/*Table structure for table `vw_get_first_user_activity` */


/*Table structure for table `vw_get_last_user_activity` */


/*Table structure for table `vw_get_max_user_activity_date` */


/*Table structure for table `vw_get_min_max_user_acitivity_for_resource` */


/*Table structure for table `vw_get_min_user_activity_date` */


/*Table structure for table `vw_muneer_report` */


/*Table structure for table `vw_resource_alloc_activity_details` */


/*Table structure for table `vw_rm_report` */


/*Table structure for table `vw_rm_support` */


/*Table structure for table `vw_skill_ratings` */


/*Table structure for table `vw_skill_ratings_primaryskills` */


/*Table structure for table `vw_skill_ratings_secondaryskills` */


/*Table structure for table `vw_user_activity` */


/*Table structure for table `vw_user_activity_rms_app` */


/*View structure for view bu */

/*!50001 DROP TABLE IF EXISTS `bu` */;
/*!50001 DROP VIEW IF EXISTS `bu` */;

/*!50001 */
 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `bu` AS (select `o`.`id` AS `id`,concat(`p`.`name`,'-',`o`.`name`) AS `name`,concat(`p`.`name`,'-',`o`.`name`) AS `CODE`,`p`.`name` AS `BgName`,`o`.`name` AS `BuName` from (`org_hierarchy` `o` join `org_hierarchy` `p` on((`o`.`parent_id` = `p`.`id`))) where (`o`.`parent_id` <> (select `org_hierarchy`.`id` from `org_hierarchy` where (`org_hierarchy`.`name` = 'Organization')))) ;

/*View structure for view rephrs_weekenddate */

/*!50001 DROP TABLE IF EXISTS `rephrs_weekenddate` */;
/*!50001 DROP VIEW IF EXISTS `rephrs_weekenddate` */;

/*!50001 */
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `rephrs_weekenddate` AS select `ua`.`employee_id` AS `employee_id`,`ua`.`week_end_date` AS `week_end_date`,`ua`.`resource_alloc_id` AS `resource_alloc_id`,sum(((((((if(isnull(`ua`.`D1_hours`),0,`ua`.`D1_hours`) + if(isnull(`ua`.`D2_hours`),0,`ua`.`D2_hours`)) + if(isnull(`ua`.`D3_hours`),0,`ua`.`D3_hours`)) + if(isnull(`ua`.`D4_hours`),0,`ua`.`D4_hours`)) + if(isnull(`ua`.`D5_hours`),0,`ua`.`D5_hours`)) + if(isnull(`ua`.`D6_hours`),0,`ua`.`D6_hours`)) + if(isnull(`ua`.`D7_hours`),0,`ua`.`D7_hours`))) AS `repHrsPro` from `vw_user_activity` `ua` where ((`ua`.`week_end_date` = `ua`.`week_end_date`) and (`ua`.`resource_alloc_id` = `ua`.`resource_alloc_id`)) group by `ua`.`employee_id`,`ua`.`week_end_date`,`ua`.`resource_alloc_id` ;

/*View structure for view resource_allocation_project */

/*!50001 DROP TABLE IF EXISTS `resource_allocation_project` */;
/*!50001 DROP VIEW IF EXISTS `resource_allocation_project` */;

/*!50001*/ 
 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `resource_allocation_project` AS (select `ra`.`id` AS `id`,`p`.`project_name` AS `project_name` from (`resource_allocation` `ra` join `project` `p`) where (`ra`.`project_id` = `p`.`id`)) ;

/*View structure for view timehrs_admin_resource_data */

/*!50001 DROP TABLE IF EXISTS `timehrs_admin_resource_data` */;
/*!50001 DROP VIEW IF EXISTS `timehrs_admin_resource_data` */;

/*!50001 */ 
 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `timehrs_admin_resource_data` AS (select `res`.`employee_id` AS `employee_id`,`res`.`yash_emp_id` AS `yash_emp_id`,concat_ws(' ',`res`.`first_name`,`res`.`last_name`) AS `full_name`,`des`.`designation_name` AS `designation_name`,`g`.`grade` AS `grade`,`res`.`planned_capacity` AS `planned_capacity`,`res`.`actual_capacity` AS `actual_capacity`,`res`.`date_of_joining` AS `date_of_joining`,ifnull(`p`.`project_name`,'') AS `current_project` from (((`resource` `res` join `designations` `des` on((`res`.`designation_id` = `des`.`id`))) join `grade` `g` on((`res`.`grade_id` = `g`.`id`))) left join `project` `p` on((`res`.`current_project_id` = `p`.`id`)))) ;

/*View structure for view timehrs_admin_view */

/*!50001 DROP TABLE IF EXISTS `timehrs_admin_view` */;
/*!50001 DROP VIEW IF EXISTS `timehrs_admin_view` */;

/*!50001 */ 
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `timehrs_admin_view` AS (select `res`.`employee_id` AS `employee_id`,`res`.`yash_emp_id` AS `yash_emp_id`,`res`.`full_name` AS `full_name`,`res`.`designation_name` AS `designation_name`,`res`.`grade` AS `grade`,`res`.`planned_capacity` AS `planned_capacity`,`res`.`actual_capacity` AS `actual_capacity`,0 AS `planned_hrs_sum`,0 AS `rephrs`,0 AS `billed_hrs_sum`,`res`.`date_of_joining` AS `date_of_joining`,`res`.`current_project` AS `current_project` from `timehrs_admin_resource_data` `res`) ;

/*View structure for view timehrs_employeeid_projectname */

/*!50001 DROP TABLE IF EXISTS `timehrs_employeeid_projectname` */;
/*!50001 DROP VIEW IF EXISTS `timehrs_employeeid_projectname` */;

/*!50001 */
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `timehrs_employeeid_projectname` AS (select distinct `ra`.`employee_id` AS `employee_id`,`p`.`project_name` AS `project_name`,`ra`.`alloc_end_date` AS `alloc_end_date` from (`resource_allocation` `ra` join `project` `p`) where (`ra`.`project_id` = `p`.`id`)) ;

/*View structure for view timehrs_total_billhrs_planhrs */

/*!50001 DROP TABLE IF EXISTS `timehrs_total_billhrs_planhrs` */;
/*!50001 DROP VIEW IF EXISTS `timehrs_total_billhrs_planhrs` */;

/*!50001 */ 
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `timehrs_total_billhrs_planhrs` AS (select `th`.`resource_id` AS `resource_id`,sum(if(isnull(`th`.`billed_hrs`),0,`th`.`billed_hrs`)) AS `billed_hrs_sum`,sum(if(isnull(`th`.`planned_hrs`),0,`th`.`planned_hrs`)) AS `planned_hrs_sum` from `timehrs` `th` group by `th`.`resource_id`) ;

/*View structure for view timehrs_totalrephrs */

/*!50001 DROP TABLE IF EXISTS `timehrs_totalrephrs` */;
/*!50001 DROP VIEW IF EXISTS `timehrs_totalrephrs` */;

/*!50001 */ 
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `timehrs_totalrephrs` AS (select `ua`.`employee_id` AS `employee_id`,sum(((((((if(isnull(`ua`.`D1_hours`),0,`ua`.`D1_hours`) + if(isnull(`ua`.`D2_hours`),0,`ua`.`D2_hours`)) + if(isnull(`ua`.`D3_hours`),0,`ua`.`D3_hours`)) + if(isnull(`ua`.`D4_hours`),0,`ua`.`D4_hours`)) + if(isnull(`ua`.`D5_hours`),0,`ua`.`D5_hours`)) + if(isnull(`ua`.`D6_hours`),0,`ua`.`D6_hours`)) + if(isnull(`ua`.`D7_hours`),0,`ua`.`D7_hours`))) AS `rephrs` from `vw_user_activity` `ua` group by `ua`.`employee_id`) ;

/*View structure for view user_activity_view */

/*!50001 DROP TABLE IF EXISTS `user_activity_view` */;
/*!50001 DROP VIEW IF EXISTS `user_activity_view` */;

/*!50001 */ 
 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `user_activity_view` AS select `ua`.`employee_id` AS `employee_id`,`ua`.`week_start_date` AS `week_start_date`,`ua`.`week_end_date` AS `week_end_date`,`ua`.`resource_alloc_id` AS `resource_alloc_id`,`ua`.`STATUS` AS `approve_status`,`rap`.`project_name` AS `project_name`,`rephrs_weekenddate`.`repHrsPro` AS `repHrsPro` from ((`vw_user_activity` `ua` left join `rephrs_weekenddate` on(((`ua`.`employee_id` = `rephrs_weekenddate`.`employee_id`) and (`ua`.`week_end_date` = `rephrs_weekenddate`.`week_end_date`) and (`ua`.`resource_alloc_id` = `rephrs_weekenddate`.`resource_alloc_id`)))) left join `resource_allocation_project` `rap` on((`ua`.`resource_alloc_id` = `rap`.`id`))) ;

/*View structure for view vw_dm_project_visibilty */

/*!50001 DROP TABLE IF EXISTS `vw_dm_project_visibilty` */;
/*!50001 DROP VIEW IF EXISTS `vw_dm_project_visibilty` */;

/*!50001 */ 
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_dm_project_visibilty` AS (select distinct `ra`.`employee_id` AS `employee_id`,`p`.`project_name` AS `project_name`,`ra`.`alloc_end_date` AS `alloc_end_date`,`p`.`offshore_del_mgr` AS `offshore_del_mgr`,`p`.`project_end_date` AS `project_end_date` from (`project` `p` left join `resource_allocation` `ra` on((`ra`.`project_id` = `p`.`id`)))) ;

/*View structure for view vw_get_first_user_activity */

/*!50001 DROP TABLE IF EXISTS `vw_get_first_user_activity` */;
/*!50001 DROP VIEW IF EXISTS `vw_get_first_user_activity` */;

/*!50001 */
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_get_first_user_activity` AS select (case when (`a`.`D1_hours` is not null) then `a`.`week_start_date` when (`a`.`D2_hours` is not null) then (`a`.`week_start_date` + interval 1 day) when (`a`.`D3_hours` is not null) then (`a`.`week_start_date` + interval 2 day) when (`a`.`D4_hours` is not null) then (`a`.`week_start_date` + interval 3 day) when (`a`.`D5_hours` is not null) then (`a`.`week_start_date` + interval 4 day) when (`a`.`D6_hours` is not null) then (`a`.`week_start_date` + interval 5 day) when (`a`.`D7_hours` is not null) then `a`.`week_end_date` end) AS `first_activity_date`,`b`.`res_alloc_id` AS `resource_alloc_id` from (`vw_user_activity` `a` join `vw_get_min_user_activity_date` `b`) where ((`a`.`resource_alloc_id` = `b`.`res_alloc_id`) and (`a`.`week_start_date` = `b`.`week_start_date`));

/*View structure for view vw_get_last_user_activity */

/*!50001 DROP TABLE IF EXISTS `vw_get_last_user_activity` */;
/*!50001 DROP VIEW IF EXISTS `vw_get_last_user_activity` */;

/*!50001 */
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_get_last_user_activity` AS select (case when (`a`.`D7_hours` is not null) then `a`.`week_end_date` when (`a`.`D6_hours` is not null) then (`a`.`week_end_date` - interval 1 day) when (`a`.`D5_hours` is not null) then (`a`.`week_end_date` - interval 2 day) when (`a`.`D4_hours` is not null) then (`a`.`week_end_date` - interval 3 day) when (`a`.`D3_hours` is not null) then (`a`.`week_end_date` - interval 4 day) when (`a`.`D2_hours` is not null) then (`a`.`week_end_date` - interval 5 day) when (`a`.`D1_hours` is not null) then `a`.`week_end_date` end) AS `last_activity_date`,`b`.`res_alloc_id` AS `resource_alloc_id` from (`vw_user_activity` `a` join `vw_get_max_user_activity_date` `b`) where ((`a`.`resource_alloc_id` = `b`.`res_alloc_id`) and (`a`.`week_end_date` = `b`.`week_end_date`));

/*View structure for view vw_get_max_user_activity_date */

/*!50001 DROP TABLE IF EXISTS `vw_get_max_user_activity_date` */;
/*!50001 DROP VIEW IF EXISTS `vw_get_max_user_activity_date` */;

/*!50001 */
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_get_max_user_activity_date` AS select max(`uad`.`week_end_date`) AS `week_end_date`,`ua`.`res_alloc_id` AS `res_alloc_id` from (`user_activity_detail` `uad` join `user_activity` `ua` on((`uad`.`time_sheet_id` = `ua`.`id`))) group by `ua`.`res_alloc_id`;

/*View structure for view vw_get_min_max_user_acitivity_for_resource */

/*!50001 DROP TABLE IF EXISTS `vw_get_min_max_user_acitivity_for_resource` */;
/*!50001 DROP VIEW IF EXISTS `vw_get_min_max_user_acitivity_for_resource` */;

/*!50001 */ 
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_get_min_max_user_acitivity_for_resource` AS select `min_date`.`resource_alloc_id` AS `resource_alloc_id`,`min_date`.`first_activity_date` AS `first_activity_date`,`max_date`.`last_activity_date` AS `last_activity_date` from (`vw_get_first_user_activity` `min_date` join `vw_get_last_user_activity` `max_date` on((`min_date`.`resource_alloc_id` = `max_date`.`resource_alloc_id`))) ;

/*View structure for view vw_get_min_user_activity_date */

/*!50001 DROP TABLE IF EXISTS `vw_get_min_user_activity_date` */;
/*!50001 DROP VIEW IF EXISTS `vw_get_min_user_activity_date` */;

/*!50001 */
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_get_min_user_activity_date` AS select `uad`.`time_sheet_id` AS `id`,min(`uad`.`week_start_date`) AS `week_start_date`,`ua`.`res_alloc_id` AS `res_alloc_id` from (`user_activity_detail` `uad` join `user_activity` `ua` on((`uad`.`time_sheet_id` = `ua`.`id`))) group by `ua`.`res_alloc_id` ;

/*View structure for view vw_muneer_report */

/*!50001 DROP TABLE IF EXISTS `vw_muneer_report` */;
/*!50001 DROP VIEW IF EXISTS `vw_muneer_report` */;

/*!50001 */
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `vw_muneer_report` AS (select `r`.`yash_emp_id` AS `Yash_Emp_Id`,concat(trim(`r`.`first_name`),' ',trim(`r`.`last_name`)) AS `Employee_Name`,`r`.`email_id` AS `Emp_Email_ID`,`d`.`designation_name` AS `Designation`,`g`.`grade` AS `Grade`,`r`.`date_of_joining` AS `Date_Of_Joining`,`r`.`release_date` AS `Release_Date`,`l`.`location` AS `Base_Location`,`paylocation`.`location` AS `Current_Location`,`b`.`CODE` AS `Parent_Bu`,`cb`.`CODE` AS `Currnet_Bu`,`ow`.`ownership_name` AS `Ownership`,concat(trim(`irm1`.`first_name`),' ',trim(`irm1`.`last_name`)) AS `Current_RM1`,concat(trim(`irm2`.`first_name`),' ',trim(`irm2`.`last_name`)) AS `Current_RM2`,`p`.`project_name` AS `Primary_Project`,`ra`.`curProj` AS `Current_Project_Flag`,`cus`.`customer_name` AS `Customer_Name`,`pbu`.`CODE` AS `Project_Bu`,`ra`.`alloc_start_date` AS `Allocation_Start_Date`,`allo_ty`.`allocationtype` AS `Allocation_Type`,`r`.`transfer_date` AS `Transfer_Date`,`v`.`visa` AS `Visa`,`sk1`.`skill` AS `Primary_skill`,`sk2`.`skill` AS `Secondary_skill`,`r`.`customer_id_detail` AS `Customer_Id`,`r`.`lastupdated_id` AS `LastUpdated_ID`,`r`.`lastupdated_timestamp` AS `Last_TimeStamp`,`t`.`name` AS `TEAM_NAME`,`ra`.`alloc_remarks` AS `REMARKS`,`c`.`skill` AS `COMPETENCY`,(to_days(curdate()) - to_days(`ra`.`alloc_start_date`)) AS `Difference`,`r`.`bu_id` AS `Bu_Id`,`r`.`current_bu_id` AS `Current_Bu_Id`,`r`.`current_project_id` AS `Current_Project_Id`,`r`.`location_id` AS `Location_id`,`p`.`id` AS `Project_ID`,`p`.`bu_id` AS `Project_Bu_ID`,`ra`.`alloc_end_date` AS `Resource_Allocation_End_Date`,`allo_ty`.`id` AS `Allocation_Type_Id`,`ra`.`employee_id` AS `Employee_Id`,`allo_ty`.`Priority` AS `Allocation_Priority`,`r`.`employee_id` AS `RES_EMP_ID`,`ra`.`id` AS `ALLOC_ID` from ((((((((((((((((((((((`resource` `r` left join `designations` `d` on((`r`.`designation_id` = `d`.`id`))) left join `grade` `g` on((`r`.`grade_id` = `g`.`id`))) left join `location` `l` on((`r`.`location_id` = `l`.`id`))) left join `location` `paylocation` on((`r`.`payroll_location` = `paylocation`.`id`))) left join `bu` `b` on((`r`.`bu_id` = `b`.`id`))) left join `bu` `cb` on((`r`.`current_bu_id` = `cb`.`id`))) left join `ownership` `ow` on((`r`.`ownership` = `ow`.`id`))) left join `resource` `irm1` on((`r`.`current_reporting_manager` = `irm1`.`employee_id`))) left join `resource` `irm2` on((`r`.`current_reporting_manager_two` = `irm2`.`employee_id`))) left join `resource_allocation` `ra` on((`ra`.`employee_id` = `r`.`employee_id`))) left join `project` `p` on((`p`.`id` = `ra`.`project_id`))) left join `bu` `pbu` on((`p`.`bu_id` = `pbu`.`id`))) left join `customer` `cus` on((`p`.`customer_name_id` = `cus`.`id`))) left join `bu` `rbu` on((`p`.`bu_id` = `rbu`.`id`))) left join `allocationtype` `allo_ty` on((`ra`.`allocation_type_id` = `allo_ty`.`id`))) left join `visa` `v` on((`r`.`visa_id` = `v`.`id`))) left join `skill_resource_primary` `skp` on((`r`.`employee_id` = `skp`.`resource_id`))) left join `skill_resource_secondary` `skp2` on((`r`.`employee_id` = `skp2`.`resource_id`))) left join `skills` `sk1` on((`skp`.`skill_id` = `sk1`.`id`))) left join `skills` `sk2` on((`skp2`.`skill_id` = `sk2`.`id`))) left join `competency` `c` on((`c`.`id` = `r`.`competency`))) left join `team` `t` on((`t`.`id` = `p`.`team_id`)))) ;

/*View structure for view vw_resource_alloc_activity_details */

/*!50001 DROP TABLE IF EXISTS `vw_resource_alloc_activity_details` */;
/*!50001 DROP VIEW IF EXISTS `vw_resource_alloc_activity_details` */;

/*!50001 */
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_resource_alloc_activity_details` AS select `ra`.`project_id` AS `project_id`,`ua`.`resource_alloc_id` AS `resource_alloc_id`,`ua`.`first_activity_date` AS `first_activity_date`,`ua`.`last_activity_date` AS `last_activity_date` from (`resource_allocation` `ra` join `vw_get_min_max_user_acitivity_for_resource` `ua`) where (`ra`.`id` = `ua`.`resource_alloc_id`) ;

/*View structure for view vw_rm_report */

/*!50001 DROP TABLE IF EXISTS `vw_rm_report` */;
/*!50001 DROP VIEW IF EXISTS `vw_rm_report` */;

/*!50001 */
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_rm_report` AS (select `r`.`yash_emp_id` AS `Yash_Emp_Id`,concat(trim(`r`.`first_name`),' ',trim(`r`.`last_name`)) AS `Employee_Name`,`r`.`email_id` AS `Emp_Email_ID`,`d`.`designation_name` AS `Designation`,`g`.`grade` AS `Grade`,`r`.`date_of_joining` AS `Date_Of_Joining`,`r`.`release_date` AS `Release_Date`,`l`.`location` AS `Base_Location`,`paylocation`.`location` AS `Current_Location`,`b`.`CODE` AS `Parent_Bu`,`cb`.`CODE` AS `Currnet_Bu`,`ow`.`ownership_name` AS `Ownership`,concat(trim(`irm1`.`first_name`),' ',trim(`irm1`.`last_name`)) AS `Current_RM1`,concat(trim(`irm2`.`first_name`),' ',trim(`irm2`.`last_name`)) AS `Current_RM2`,`p`.`project_name` AS `Primary_Project`,`ra`.`curProj` AS `Current_Project_Flag`,`cus`.`customer_name` AS `Customer_Name`,`pbu`.`CODE` AS `Project_Bu`,`ra`.`alloc_start_date` AS `Allocation_Start_Date`,`allo_ty`.`allocationtype` AS `Allocation_Type`,`r`.`transfer_date` AS `Transfer_Date`,`v`.`visa` AS `Visa`,`sk1`.`skill` AS `Primary_skill`,`sk2`.`skill` AS `Secondary_skill`,`r`.`customer_id_detail` AS `Customer_Id`,`r`.`lastupdated_id` AS `LastUpdated_ID`,`r`.`lastupdated_timestamp` AS `Last_TimeStamp`,`r`.`bu_id` AS `Bu_Id`,`r`.`current_bu_id` AS `Current_Bu_Id`,`r`.`current_project_id` AS `Current_Project_Id`,`r`.`location_id` AS `Location_id`,`p`.`id` AS `Project_ID`,`p`.`bu_id` AS `Project_Bu_ID`,`ra`.`alloc_end_date` AS `Resource_Allocation_End_Date`,`allo_ty`.`id` AS `Allocation_Type_Id`,`ra`.`employee_id` AS `Employee_Id`,`allo_ty`.`Priority` AS `Allocation_Priority`,`r`.`employee_id` AS `RES_EMP_ID`,`ra`.`id` AS `ALLOC_ID`,`comp`.`skill` AS `competency_skill`,`ra`.`resource_type` AS `Resource_Type`,(case when ((`r`.`upload_Resume` is not null) and (`r`.`upload_Resume` <> '')) then 'Y' else 'N' end) AS `ResumeYN`,(case when ((`r`.`upload_tef` is not null) and (`r`.`upload_tef` <> '')) then 'Y' else 'N' end) AS `TefYN` from (((((((((((((((((((((`resource` `r` left join `designations` `d` on((`r`.`designation_id` = `d`.`id`))) left join `grade` `g` on((`r`.`grade_id` = `g`.`id`))) left join `location` `l` on((`r`.`location_id` = `l`.`id`))) left join `location` `paylocation` on((`r`.`payroll_location` = `paylocation`.`id`))) left join `bu` `b` on((`r`.`bu_id` = `b`.`id`))) left join `bu` `cb` on((`r`.`current_bu_id` = `cb`.`id`))) left join `ownership` `ow` on((`r`.`ownership` = `ow`.`id`))) left join `resource` `irm1` on((`r`.`current_reporting_manager` = `irm1`.`employee_id`))) left join `resource` `irm2` on((`r`.`current_reporting_manager_two` = `irm2`.`employee_id`))) left join `resource_allocation` `ra` on((`ra`.`employee_id` = `r`.`employee_id`))) left join `project` `p` on((`p`.`id` = `ra`.`project_id`))) left join `bu` `pbu` on((`p`.`bu_id` = `pbu`.`id`))) left join `customer` `cus` on((`p`.`customer_name_id` = `cus`.`id`))) left join `bu` `rbu` on((`p`.`bu_id` = `rbu`.`id`))) left join `allocationtype` `allo_ty` on((`ra`.`allocation_type_id` = `allo_ty`.`id`))) left join `visa` `v` on((`r`.`visa_id` = `v`.`id`))) left join `skill_resource_primary` `skp` on((`r`.`employee_id` = `skp`.`resource_id`))) left join `skill_resource_secondary` `skp2` on((`r`.`employee_id` = `skp2`.`resource_id`))) left join `skills` `sk1` on((`skp`.`skill_id` = `sk1`.`id`))) left join `skills` `sk2` on((`skp2`.`skill_id` = `sk2`.`id`))) left join `competency` `comp` on((`r`.`competency` = `comp`.`id`)))) ;

/*View structure for view vw_rm_support */

/*!50001 DROP TABLE IF EXISTS `vw_rm_support` */;
/*!50001 DROP VIEW IF EXISTS `vw_rm_support` */;

/*!50001 */
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_rm_support` AS (select `r`.`employee_id` AS `employee_id`,`r`.`id` AS `id`,`r`.`allocation_type_id` AS `Allocation_Type_Id`,`r`.`curProj` AS `curProj`,`a`.`Priority` AS `Priority`,`r`.`alloc_start_date` AS `alloc_start_date` from (`resource_allocation` `r` join `allocationtype` `a`) where ((isnull(`r`.`alloc_end_date`) or (`r`.`alloc_end_date` >= curdate())) and (`r`.`allocation_type_id` = `a`.`id`))) ;

/*View structure for view vw_skill_ratings */

/*!50001 DROP TABLE IF EXISTS `vw_skill_ratings` */;
/*!50001 DROP VIEW IF EXISTS `vw_skill_ratings` */;

/*!50001 */
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_skill_ratings` AS (select `primskill`.`resource_id` AS `employee_id`,`primskill`.`Primary_skill` AS `Primary_skill`,ifnull(`secskill`.`Secondary_skill`,'') AS `Secondary_skill` from (`vw_skill_ratings_primaryskills` `primskill` left join `vw_skill_ratings_secondaryskills` `secskill` on((`primskill`.`resource_id` = `secskill`.`resource_id`)))) ;

/*View structure for view vw_skill_ratings_primaryskills */

/*!50001 DROP TABLE IF EXISTS `vw_skill_ratings_primaryskills` */;
/*!50001 DROP VIEW IF EXISTS `vw_skill_ratings_primaryskills` */;

/*!50001 */
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_skill_ratings_primaryskills` AS (select `srp`.`resource_id` AS `resource_id`,'Primary' AS `SkillType`,group_concat(`sk`.`skill` order by `sk`.`skill` ASC separator ',') AS `Primary_skill` from (`skill_resource_primary` `srp` join `skills` `sk`) where (`sk`.`id` = `srp`.`skill_id`) group by `srp`.`resource_id`,'Primary') ;

/*View structure for view vw_skill_ratings_secondaryskills */

/*!50001 DROP TABLE IF EXISTS `vw_skill_ratings_secondaryskills` */;
/*!50001 DROP VIEW IF EXISTS `vw_skill_ratings_secondaryskills` */;

/*!50001 */
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_skill_ratings_secondaryskills` AS (select `srs`.`resource_id` AS `resource_id`,'Secondary' AS `SkillType`,group_concat(`sk`.`skill` order by `sk`.`skill` ASC separator ',') AS `Secondary_skill` from (`skill_resource_secondary` `srs` join `skills` `sk`) where (`sk`.`id` = `srs`.`skill_id`) group by `srs`.`resource_id`,'Secondary') ;

/*View structure for view vw_user_activity */

/*!50001 DROP TABLE IF EXISTS `vw_user_activity` */;
/*!50001 DROP VIEW IF EXISTS `vw_user_activity` */;

/*!50001 */
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_user_activity` AS select `ua`.`id` AS `ID`,`ra`.`employee_id` AS `employee_id`,`ua`.`activity_id` AS `ACTIVITY_ID`,`ua`.`module` AS `MODULE`,`ua`.`res_alloc_id` AS `resource_alloc_id`,`uad`.`d1_hours` AS `D1_hours`,`uad`.`d2_hours` AS `D2_hours`,`uad`.`d3_hours` AS `D3_hours`,`uad`.`d4_hours` AS `D4_hours`,`uad`.`d5_hours` AS `D5_hours`,`uad`.`d6_hours` AS `D6_hours`,`uad`.`d7_hours` AS `D7_hours`,`uad`.`d1_comment` AS `D1_comment`,`uad`.`d2_comment` AS `D2_comment`,`uad`.`d3_comment` AS `D3_comment`,`uad`.`d4_comment` AS `D4_comment`,`uad`.`d5_comment` AS `D5_comment`,`uad`.`d6_comment` AS `D6_comment`,`uad`.`d7_comment` AS `D7_comment`,`ua`.`status` AS `STATUS`,`uad`.`week_start_date` AS `week_start_date`,`uad`.`week_end_date` AS `week_end_date`,`ua`.`custom_activity_id` AS `custom_activity_id`,`ua`.`approved_by` AS `approved_by`,`ua`.`approval_pending_from` AS `approval_pending_from` from ((`resource_allocation` `ra` join `user_activity` `ua` on((`ra`.`id` = `ua`.`res_alloc_id`))) left join `user_activity_detail` `uad` on((`ua`.`id` = `uad`.`time_sheet_id`))) ;

/*View structure for view vw_user_activity_rms_app */

/*!50001 DROP TABLE IF EXISTS `vw_user_activity_rms_app` */;
/*!50001 DROP VIEW IF EXISTS `vw_user_activity_rms_app` */;

/*!50001 */
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_user_activity_rms_app` AS select `ua`.`id` AS `ID`,`ra`.`employee_id` AS `employee_id`,`ua`.`activity_id` AS `ACTIVITY_ID`,`ua`.`custom_activity_id` AS `CUSTOM_ACTIVITY_ID`,`ua`.`module` AS `MODULE`,`ua`.`ticket_no` AS `TICKET_NO`,`ua`.`res_alloc_id` AS `resource_alloc_id`,`uad`.`d1_hours` AS `D1_hours`,`uad`.`d2_hours` AS `D2_hours`,`uad`.`d3_hours` AS `D3_hours`,`uad`.`d4_hours` AS `D4_hours`,`uad`.`d5_hours` AS `D5_hours`,`uad`.`d6_hours` AS `D6_hours`,`uad`.`d7_hours` AS `D7_hours`,`uad`.`d1_comment` AS `D1_comment`,`uad`.`d2_comment` AS `D2_comment`,`uad`.`d3_comment` AS `D3_comment`,`uad`.`d4_comment` AS `D4_comment`,`uad`.`d5_comment` AS `D5_comment`,`uad`.`d6_comment` AS `D6_comment`,`uad`.`d7_comment` AS `D7_comment`,`ua`.`status` AS `STATUS`,`uad`.`week_start_date` AS `week_start_date`,`uad`.`week_end_date` AS `week_end_date`,`ua`.`rejection_remarks` AS `rejection_remarks`,`ua`.`approve_code` AS `approve_code`,`ua`.`reject_code` AS `reject_code`,`ua`.`sub_module` AS `SUB_MODULE`,`ua`.`ticket_priority` AS `ticket_priority`,`ua`.`ticket_status` AS `ticket_status` from ((`resource_allocation` `ra` join `user_activity` `ua` on((`ra`.`id` = `ua`.`res_alloc_id`))) left join `user_activity_detail` `uad` on((`ua`.`id` = `uad`.`time_sheet_id`))) ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
