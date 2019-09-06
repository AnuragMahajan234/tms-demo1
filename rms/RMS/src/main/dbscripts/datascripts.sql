
/*Data for the table `designations` */

INSERT  INTO `designations`(`id`,`designation_name`,`created_id`,`creation_timestamp`,`lastupdated_id`,`lastupdated_timestamp`) VALUES (1,'SSE',NULL,NULL,NULL,'2017-02-15 15:24:00');

/*Data for the table `grade` */

INSERT  INTO `grade`(`id`,`grade`,`created_id`,`creation_timestamp`,`lastupdated_id`,`lastupdated_timestamp`) VALUES (1,'E2',NULL,NULL,NULL,'2017-02-15 15:24:40');

/*Data for the table `location` */

INSERT  INTO `location`(`id`,`location`,`created_id`,`creation_timestamp`,`lastupdated_id`,`lastupdated_timestamp`,`locationhr_email_id`) VALUES (1,'Indore',NULL,NULL,NULL,'2017-02-15 15:46:38','manali.mandloi@yash.com');

/*Data for the table `org_hierarchy` */
INSERT  INTO `org_hierarchy`(`id`,`name`,`description`,`parent_id`,`creation_timestamp`,`active`,`user`,`lastupdated_id`,`lastupdated_timestamp`,`created_id`,`bussiness_head`) VALUES (1,'ORGANIZATION','Organization',NULL,NULL,1,NULL,NULL,NULL,NULL,NULL);

INSERT  INTO `org_hierarchy`(`id`,`name`,`description`,`parent_id`,`creation_timestamp`,`active`,`user`,`lastupdated_id`,`lastupdated_timestamp`,`created_id`,`bussiness_head`) VALUES (2,'BG4','BG4',1,NULL,1,NULL,NULL,'2017-02-15 15:29:35',NULL,NULL);

INSERT  INTO `org_hierarchy`(`id`,`name`,`description`,`parent_id`,`creation_timestamp`,`active`,`user`,`lastupdated_id`,`lastupdated_timestamp`,`created_id`,`bussiness_head`) VALUES (3,'BU8','BU8',2,NULL,1,NULL,NULL,'2017-02-15 15:28:34',NULL,NULL);
  
/*Data for the table `ownership` */

INSERT  INTO `ownership`(`id`,`ownership_name`,`created_id`,`creation_timestamp`,`lastupdated_id`,`lastupdated_timestamp`) VALUES (1,'Owned',NULL,NULL,NULL,'2017-02-15 15:25:41');

/*Data for the table `resource` */

INSERT  INTO `resource`(`upload_image`,`rejoining_flag`,`employee_id`,`actual_capacity`,`award_recognition`,`confirmation_date`,`contact_number`,`contact_number_three`,`contact_number_two`,`customer_id_detail`,`date_of_joining`,`email_id`,`last_appraisal`,`penultimate_appraisal`,`planned_capacity`,`profit_centre`,`release_date`,`resume_file_name`,`transfer_date`,`visa_valid`,`yash_emp_id`,`bu_id`,`current_project_id`,`current_reporting_manager`,`current_reporting_manager_two`,`designation_id`,`grade_id`,`location_id`,`ownership`,`payroll_location`,`visa_id`,`USER_ROLE`,`user_name`,`first_name`,`last_name`,`current_bu_id`,`timesheet_comment_optional`,`created_id`,`creation_timestamp`,`lastupdated_id`,`lastupdated_timestamp`,`middle_name`,`bGHComments`,`bGH_Name`,`hRComments`,`hR_Name`,`bGComment_timestamp`,`bUComment_timestamp`,`hRComment_timestamp`,`event_id`,`bUComments`,`bU_Name`,`employee_category`,`competency`,`end_transfer_date`,`report_user_id`,`resignation_date`) VALUES (NULL,'N',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2017-02-01','maya.parmar@yash.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1003379',3,NULL,NULL,NULL,1,1,1,1,1,NULL,'ROLE_ADMIN','maya.parmar','Maya','Parmar',3,NULL,NULL,NULL,NULL,'2017-02-15 15:47:40',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
