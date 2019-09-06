
/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`rms_local` /*!40100 DEFAULT CHARACTER SET latin1 */;

/*Table structure for table `123` */

CREATE TABLE `123` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `additional_comments` varchar(255) DEFAULT NULL,
  `billable` int(11) DEFAULT NULL,
  `career_growth_plan` varchar(255) DEFAULT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `desirable_skills` varchar(255) DEFAULT NULL,
  `experience` varchar(255) DEFAULT NULL,
  `fulfilled` varchar(255) DEFAULT NULL,
  `key_interviewers_one` varchar(255) DEFAULT NULL,
  `key_interviewers_two` varchar(255) DEFAULT NULL,
  `key_scanners` varchar(255) DEFAULT NULL,
  `req_resources` int(11) DEFAULT NULL,
  `primary_skills` varchar(255) DEFAULT NULL,
  `remaining` varchar(255) DEFAULT NULL,
  `responsibilities` varchar(255) DEFAULT NULL,
  `target_companies` varchar(255) DEFAULT NULL,
  `time_frame` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `designation` int(11) NOT NULL,
  `request_id` int(11) NOT NULL,
  `skills` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKD7A282A15F631D31` (`skills`),
  KEY `FKD7A282A1BF838761` (`designation`),
  KEY `FKD7A282A1D08AAC4D` (`request_id`),
  KEY `FKD7A282A1410461C8` (`billable`),
  CONSTRAINT `FKD7A282A1410461C8` FOREIGN KEY (`billable`) REFERENCES `allocationtype` (`id`),
  CONSTRAINT `FKD7A282A15F631D31` FOREIGN KEY (`skills`) REFERENCES `skills` (`id`),
  CONSTRAINT `FKD7A282A1BF838761` FOREIGN KEY (`designation`) REFERENCES `designations` (`id`),
  CONSTRAINT `FKD7A282A1D08AAC4D` FOREIGN KEY (`request_id`) REFERENCES `request` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `activity` */

CREATE TABLE `activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_name` varchar(256) NOT NULL,
  `mandatory` tinyint(1) NOT NULL DEFAULT '0',
  `type` varchar(256) NOT NULL,
  `max` int(11) NOT NULL DEFAULT '0',
  `format` varchar(256) DEFAULT NULL,
  `productive` tinyint(1) NOT NULL DEFAULT '0',
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique` (`activity_name`)
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=latin1;

/*Table structure for table `all_timesheet_activity` */

CREATE TABLE `all_timesheet_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `actvty_type` varchar(10) DEFAULT NULL,
  `custom_activity_id` int(11) DEFAULT NULL,
  `deactive_flag` tinyint(1) DEFAULT NULL,
  `projectid` int(11) DEFAULT NULL,
  `default_activity_id` int(11) DEFAULT NULL,
  KEY `id` (`id`),
  KEY `fk_projectid` (`projectid`),
  KEY `fk_custom` (`custom_activity_id`),
  KEY `fk_activity` (`default_activity_id`),
  CONSTRAINT `fk_activity` FOREIGN KEY (`custom_activity_id`) REFERENCES `custom_activity` (`id`),
  CONSTRAINT `fk_custom` FOREIGN KEY (`default_activity_id`) REFERENCES `activity` (`id`),
  CONSTRAINT `fk_projectid` FOREIGN KEY (`projectid`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `allocation_type_new` */

CREATE TABLE `allocation_type_new` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `allocationtype_1` varchar(256) DEFAULT NULL,
  `new_allocationtype` varchar(500) DEFAULT NULL,
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `allocationtype` */

CREATE TABLE `allocationtype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `allocationtype` varchar(256) NOT NULL,
  `allocation_old` varchar(256) DEFAULT NULL,
  `is_default` tinyint(1) NOT NULL DEFAULT '0',
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Priority` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique` (`allocationtype`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;

/*Table structure for table `bak_4nov_15_mails_configuration` */

CREATE TABLE `bak_4nov_15_mails_configuration` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `project_id` int(10) DEFAULT NULL,
  `confg_id` int(10) DEFAULT NULL,
  `role_id` int(10) DEFAULT NULL,
  `mail_to` tinyint(1) DEFAULT '0',
  `mail_cc` tinyint(1) DEFAULT '0',
  `mail_bcc` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `bgadmin_access_rights` */

CREATE TABLE `bgadmin_access_rights` (
  `status` int(11) DEFAULT '0',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` int(11) DEFAULT NULL,
  `org_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_RESOURCE` (`resource_id`),
  KEY `FK_ORANIZATION` (`org_id`),
  CONSTRAINT `FK_ORANIZATION` FOREIGN KEY (`org_id`) REFERENCES `org_hierarchy` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_RESOURCE` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`employee_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `bgadmin_access_rights_aud` */

CREATE TABLE `bgadmin_access_rights_aud` (
  `id` int(11) NOT NULL,
  `REV` int(11) NOT NULL,
  `REVTYPE` tinyint(4) NOT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `org_id` int(11) DEFAULT NULL,
  `action` varchar(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `billingscale` */

CREATE TABLE `billingscale` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `billing_rate` int(5) NOT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `billingrate_unique` (`billing_rate`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `bu_old` */

CREATE TABLE `bu_old` (
  `id` int(11) NOT NULL DEFAULT '0',
  `name` text NOT NULL,
  `CODE` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `ca_ticket` */

CREATE TABLE `ca_ticket` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `module` int(10) DEFAULT NULL,
  `reviewer_id` int(20) DEFAULT NULL,
  `emp_id` int(10) DEFAULT NULL,
  `assignee_name` varchar(1000) DEFAULT NULL,
  `status` varchar(1000) DEFAULT NULL,
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `resolve_date` timestamp NULL DEFAULT NULL,
  `priority` varchar(100) DEFAULT NULL,
  `area_category` varchar(2000) DEFAULT NULL,
  `primary_group` varchar(1000) DEFAULT NULL,
  `ticket_number` bigint(20) DEFAULT NULL,
  `reported_by_name` varchar(1000) DEFAULT NULL,
  `description` varchar(5000) DEFAULT NULL,
  `landscape_id` int(10) DEFAULT NULL,
  `unit_id` int(10) DEFAULT NULL,
  `region` int(11) DEFAULT NULL,
  `sla_missed` varchar(10) DEFAULT NULL,
  `aging` varchar(10) DEFAULT NULL,
  `days_open` varchar(100) DEFAULT NULL,
  `creation_date` timestamp NULL DEFAULT NULL,
  `acknowledged_date` timestamp NULL DEFAULT NULL,
  `required_completed_date` timestamp NULL DEFAULT NULL,
  `analysis_completed_date` timestamp NULL DEFAULT NULL,
  `solution_review_date` timestamp NULL DEFAULT NULL,
  `solution_developed_date` timestamp NULL DEFAULT NULL,
  `solution_accepted_date` timestamp NULL DEFAULT NULL,
  `close_pending_date` timestamp NULL DEFAULT NULL,
  `process` varchar(256) DEFAULT NULL,
  `sub_process` varchar(256) DEFAULT NULL,
  `rootcause` varchar(256) DEFAULT NULL,
  `zreq_no` int(10) DEFAULT '0',
  `parent_ticket_no` int(10) DEFAULT '0',
  `comment` varchar(1000) DEFAULT NULL,
  `req_complete_flag` varchar(10) DEFAULT 'No',
  `analysis_complete_flag` varchar(10) DEFAULT 'No',
  `solution_review_flag` varchar(10) DEFAULT 'No',
  `solution_developed_flag` varchar(10) DEFAULT 'No',
  `solution_accepted_flag` varchar(10) DEFAULT 'No',
  `customer_approval_flag` varchar(10) DEFAULT 'No',
  `reopen_frequency` int(10) DEFAULT '0',
  `solution_ready_for_review` varchar(10) DEFAULT 'No',
  `problem_status_flag` varchar(10) DEFAULT 'No',
  `t3_status_flag` varchar(10) DEFAULT 'No',
  `defect_status_flag` varchar(10) DEFAULT 'No',
  `crop_status_flag` varchar(10) DEFAULT 'No',
  `rework_status_flag` varchar(10) DEFAULT 'No',
  `justified_problem_management` varchar(256) DEFAULT 'No',
  `group_name` varchar(256) DEFAULT NULL,
  `reason_for_hopping` int(11) DEFAULT NULL,
  `justified_hopping` varchar(10) DEFAULT 'No',
  `reason_for_slamissed` int(11) DEFAULT NULL,
  `reason_for_reopen` int(11) DEFAULT NULL,
  `affected_end_user_id` varchar(1000) DEFAULT NULL,
  `affected_end_username` varchar(1000) DEFAULT NULL,
  `updated_by` varchar(1000) DEFAULT NULL,
  `requirement_analysis_hours` float DEFAULT NULL,
  `unit_testing_hours` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_resource_by` (`emp_id`),
  KEY `module` (`module`),
  KEY `reviewer_id` (`reviewer_id`),
  KEY `landscape_id` (`landscape_id`),
  CONSTRAINT `ca_ticket_ibfk_1` FOREIGN KEY (`emp_id`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `ca_ticket_ibfk_2` FOREIGN KEY (`module`) REFERENCES `project` (`id`),
  CONSTRAINT `ca_ticket_ibfk_3` FOREIGN KEY (`reviewer_id`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `ca_ticket_ibfk_4` FOREIGN KEY (`landscape_id`) REFERENCES `landscape` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3594 DEFAULT CHARSET=utf8;

/*Table structure for table `ca_ticket_discrepencies` */

CREATE TABLE `ca_ticket_discrepencies` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `ticket_number` varchar(256) DEFAULT NULL,
  `recf_id` varchar(1000) DEFAULT NULL,
  `emp_id` int(10) DEFAULT NULL,
  `reviewer_id` int(10) DEFAULT NULL,
  `module` int(10) DEFAULT NULL,
  `unit_id` int(10) DEFAULT NULL,
  `region` varchar(256) DEFAULT NULL,
  `priority` varchar(100) DEFAULT NULL,
  `reopen_frequency` varchar(256) DEFAULT NULL,
  `description` varchar(5000) DEFAULT NULL,
  `landscape_id` int(10) DEFAULT NULL,
  `parent_ticket_no` varchar(256) DEFAULT NULL,
  `rootcause` varchar(256) DEFAULT NULL,
  `creation_date` timestamp NULL DEFAULT NULL,
  `solution_ready_for_review` varchar(10) DEFAULT 'No',
  `acknowledged_date` timestamp NULL DEFAULT NULL,
  `required_completed_date` timestamp NULL DEFAULT NULL,
  `analysis_completed_date` timestamp NULL DEFAULT NULL,
  `solution_developed_date` timestamp NULL DEFAULT NULL,
  `solution_review_date` timestamp NULL DEFAULT NULL,
  `solution_accepted_date` timestamp NULL DEFAULT NULL,
  `close_pending_date` timestamp NULL DEFAULT NULL,
  `updated_date` timestamp NULL DEFAULT NULL,
  `req_complete_flag` varchar(10) DEFAULT 'No',
  `analysis_complete_flag` varchar(10) DEFAULT 'No',
  `solution_developed_flag` varchar(10) DEFAULT 'No',
  `solution_review_flag` varchar(10) DEFAULT 'No',
  `solution_accepted_flag` varchar(10) DEFAULT 'No',
  `customer_approval_flag` varchar(10) DEFAULT 'No',
  `problem_status_flag` varchar(10) DEFAULT 'No',
  PRIMARY KEY (`id`),
  KEY `module` (`module`),
  KEY `landscape_id` (`landscape_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `competency` */

CREATE TABLE `competency` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `skill` varchar(256) NOT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=latin1;

/*Table structure for table `configuration_detail` */

CREATE TABLE `configuration_detail` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `type_id` (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `crop_ost` */

CREATE TABLE `crop_ost` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) DEFAULT NULL,
  `module` varchar(200) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `source` varchar(500) DEFAULT NULL,
  `benefit_type` varchar(500) DEFAULT NULL,
  `total_business_hrs_saved` int(11) DEFAULT NULL,
  `total_IT_hours_saved` int(11) DEFAULT NULL,
  `savings_in_USD` varchar(500) DEFAULT NULL,
  `justified` varchar(30) DEFAULT 'No',
  `ca_ticket_id` int(10) DEFAULT NULL,
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `ca_ticket_id` (`ca_ticket_id`),
  CONSTRAINT `crop_ost_ibfk_1` FOREIGN KEY (`ca_ticket_id`) REFERENCES `ca_ticket` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `currency` */

CREATE TABLE `currency` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `currency_name` varchar(256) NOT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique` (`currency_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Table structure for table `custom_activity` */

CREATE TABLE `custom_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) DEFAULT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `mandatory` tinyint(1) DEFAULT NULL,
  `max` int(11) DEFAULT NULL,
  `format` varchar(256) DEFAULT NULL,
  `productive` tinyint(1) DEFAULT NULL,
  `activity_name` varchar(256) DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `activity_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `project_id` (`project_id`),
  KEY `activity_id` (`activity_id`),
  CONSTRAINT `project_id` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=158 DEFAULT CHARSET=latin1;

/*Table structure for table `customer` */

CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_manager` varchar(256) NOT NULL,
  `account_manager_contact_number` decimal(14,0) DEFAULT NULL,
  `customer_address` varchar(256) DEFAULT NULL,
  `customer_name` varchar(256) NOT NULL,
  `geography` varchar(256) NOT NULL,
  `code` varchar(256) NOT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=167 DEFAULT CHARSET=latin1;

/*Table structure for table `customer_aud` */

CREATE TABLE `customer_aud` (
  `id` int(11) NOT NULL,
  `REV` int(11) NOT NULL,
  `REVTYPE` tinyint(4) DEFAULT NULL,
  `account_manager` varchar(256) DEFAULT NULL,
  `account_manager_contact_number` decimal(19,2) DEFAULT NULL,
  `code` varchar(256) DEFAULT NULL,
  `customer_address` varchar(256) DEFAULT NULL,
  `customer_name` varchar(256) DEFAULT NULL,
  `geography` varchar(256) DEFAULT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `action` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`id`,`REV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `customer_po` */

CREATE TABLE `customer_po` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) DEFAULT NULL,
  `position` varchar(256) NOT NULL,
  `description` varchar(256) DEFAULT NULL,
  `status` varchar(256) NOT NULL,
  `customer_number` varchar(256) DEFAULT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `cu_po_fk` (`customer_id`),
  CONSTRAINT `cu_po_fk` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `customer_po_aud` */

CREATE TABLE `customer_po_aud` (
  `id` int(11) NOT NULL,
  `REV` int(11) NOT NULL,
  `REVTYPE` tinyint(4) DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `position` varchar(256) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `status` varchar(256) DEFAULT NULL,
  `customer_number` varchar(256) DEFAULT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`,`REV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `dashboard_temp` */

CREATE TABLE `dashboard_temp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_bg_bu` varchar(100) DEFAULT NULL,
  `project_id` varchar(100) DEFAULT NULL,
  `project_name` varchar(100) DEFAULT NULL,
  `billable_resources` varchar(100) DEFAULT '0',
  `non_billable_resources` varchar(100) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `dashboard_temp_second` */

CREATE TABLE `dashboard_temp_second` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_bg_bu` varchar(100) DEFAULT NULL,
  `project_id` varchar(100) DEFAULT NULL,
  `project_name` varchar(100) DEFAULT NULL,
  `manager_id` varchar(100) DEFAULT NULL,
  `actual_hrs` varchar(100) DEFAULT '0',
  `billing_hrs` varchar(100) DEFAULT '0',
  `non_billing_hrs` varchar(100) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `dashboard_widgets` */

CREATE TABLE `dashboard_widgets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `employee_id` int(11) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `week_end_date` date DEFAULT NULL,
  `STATUS` varchar(100) DEFAULT '0',
  `employee_count` int(100) DEFAULT '0',
  `manager_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `default_project` */

CREATE TABLE `default_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bu_id` int(11) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `allocation_type_id` int(11) DEFAULT NULL,
  `creation_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `defect_log_ost` */

CREATE TABLE `defect_log_ost` (
  `ca_ticket_id` int(10) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `zreq_ca_no` int(11) DEFAULT NULL,
  `defect_type` varchar(100) DEFAULT NULL,
  `defect_description` varchar(1000) DEFAULT NULL,
  `internal_external` varchar(100) DEFAULT NULL,
  `defect_category` varchar(100) DEFAULT NULL,
  `severity` varchar(100) DEFAULT NULL,
  `defect_status` varchar(100) DEFAULT NULL,
  `identified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `identified_by` varchar(256) DEFAULT NULL,
  `identified_phase` varchar(200) DEFAULT NULL,
  `module_name` varchar(200) DEFAULT NULL,
  `injected_phase` varchar(200) DEFAULT NULL,
  `work_product_name` varchar(200) DEFAULT NULL,
  `reopened` varchar(30) DEFAULT 'No',
  `requirement_id_ticket` varchar(100) DEFAULT NULL,
  `defect_root_cause` varchar(500) DEFAULT NULL,
  `category_of_root_cause` varchar(200) DEFAULT NULL,
  `resolved_by` varchar(300) DEFAULT NULL,
  `resolved_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `closed_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `ca_ticket_id` (`ca_ticket_id`),
  CONSTRAINT `defect_log_ost_ibfk_1` FOREIGN KEY (`ca_ticket_id`) REFERENCES `ca_ticket` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `designations` */

CREATE TABLE `designations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `designation_name` varchar(256) NOT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique` (`designation_name`)
) ENGINE=InnoDB AUTO_INCREMENT=134 DEFAULT CHARSET=latin1;

/*Table structure for table `employee_category` */

CREATE TABLE `employee_category` (
  `employeecategory_name` varchar(256) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creation_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `created_id` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Table structure for table `engagementmodel` */

CREATE TABLE `engagementmodel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `engagement_model_name` varchar(256) NOT NULL,
  `timesheet_compulsory` char(1) DEFAULT 'Y',
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique` (`engagement_model_name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

/*Table structure for table `event` */

CREATE TABLE `event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `event_name` varchar(256) DEFAULT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `description` varchar(256) DEFAULT NULL,
  `employee_id` char(1) DEFAULT 'N',
  `transfer_date` char(1) DEFAULT 'N',
  `bu_id` char(1) DEFAULT 'N',
  `current_reporting_manager` char(1) DEFAULT 'N',
  `current_reporting_two` char(1) DEFAULT 'N',
  `designation_id` char(1) DEFAULT 'N',
  `grade_id` char(1) DEFAULT 'N',
  `location_id` char(1) DEFAULT 'N',
  `ownership` char(1) DEFAULT 'N',
  `payroll_location` char(1) DEFAULT 'N',
  `current_bu_id` char(1) DEFAULT 'N',
  `yash_emp_id` char(1) DEFAULT 'N',
  `email_id` char(1) DEFAULT 'N',
  `contact_number` char(1) DEFAULT 'N',
  `contact_number_two` char(1) DEFAULT 'N',
  `date_Of_Joining` char(1) DEFAULT 'N',
  `confirmation_Date` char(1) DEFAULT 'N',
  `last_Appraisal` char(1) DEFAULT 'N',
  `penultimate_Appraisal` char(1) DEFAULT 'N',
  `release_Date` char(1) DEFAULT 'N',
  `bGHComments` char(1) DEFAULT 'N',
  `bGH_Name` char(1) DEFAULT 'N',
  `hRComments` char(1) DEFAULT 'N',
  `hR_Name` char(1) DEFAULT 'N',
  `bGComment_timestamp` char(1) DEFAULT 'N',
  `bUComment_timestamp` char(1) DEFAULT 'N',
  `hRComment_timestamp` char(1) DEFAULT 'N',
  `bUComments` char(1) DEFAULT 'N',
  `bU_Name` char(1) DEFAULT 'N',
  `employee_category` char(1) DEFAULT 'N',
  `end_transfer_date` char(1) DEFAULT 'N',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

/*Table structure for table `grade` */

CREATE TABLE `grade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `grade` varchar(10) NOT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Unique` (`grade`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

/*Table structure for table `hopping_reason_ost` */

CREATE TABLE `hopping_reason_ost` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `reason` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `invoice_by` */

CREATE TABLE `invoice_by` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Table structure for table `job_post` */

CREATE TABLE `job_post` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_type_id` int(11) DEFAULT NULL,
  `no_of_position` int(11) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `job_name` varchar(256) DEFAULT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `project_id` (`project_id`),
  KEY `job_type_id` (`job_type_id`),
  CONSTRAINT `job_post_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `job_post_ibfk_2` FOREIGN KEY (`job_type_id`) REFERENCES `job_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

/*Table structure for table `job_type` */

CREATE TABLE `job_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `jobtype` varchar(257) NOT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Table structure for table `jobpost_skill_primary` */

CREATE TABLE `jobpost_skill_primary` (
  `skill_id` int(11) NOT NULL,
  `jobpost_id` int(11) NOT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`skill_id`,`jobpost_id`),
  KEY `jobpost_id` (`jobpost_id`),
  CONSTRAINT `jobpost_skill_primary_ibfk_1` FOREIGN KEY (`jobpost_id`) REFERENCES `job_post` (`id`),
  CONSTRAINT `jobpost_skill_primary_ibfk_2` FOREIGN KEY (`skill_id`) REFERENCES `skills` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `jobpost_skill_secondary` */

CREATE TABLE `jobpost_skill_secondary` (
  `skill_id` int(11) NOT NULL,
  `jobpost_id` int(11) NOT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`skill_id`,`jobpost_id`),
  KEY `jobpost_id` (`jobpost_id`),
  CONSTRAINT `jobpost_skill_secondary_ibfk_3` FOREIGN KEY (`jobpost_id`) REFERENCES `job_post` (`id`),
  CONSTRAINT `jobpost_skill_secondary_ibfk_4` FOREIGN KEY (`skill_id`) REFERENCES `skills` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `landscape` */

CREATE TABLE `landscape` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `landscapeName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;

/*Table structure for table `location` */

CREATE TABLE `location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `location` varchar(100) NOT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `locationhr_email_id` varchar(256) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Unique_location` (`location`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;

/*Table structure for table `mail_configuration_roles` */

CREATE TABLE `mail_configuration_roles` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `role` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `mails_configuration` */

CREATE TABLE `mails_configuration` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `project_id` int(10) DEFAULT NULL,
  `confg_id` int(10) DEFAULT NULL,
  `role_id` int(10) DEFAULT NULL,
  `mail_to` tinyint(1) DEFAULT '0',
  `mail_cc` tinyint(1) DEFAULT '0',
  `mail_bcc` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `module` */

CREATE TABLE `module` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `module_name` varchar(256) NOT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique` (`module_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `org_hierarchy` */

CREATE TABLE `org_hierarchy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  `description` varchar(256) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `active` tinyint(1) NOT NULL,
  `user` char(1) DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_id` varchar(256) DEFAULT NULL,
  `bussiness_head` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ORGANIZATION` (`parent_id`),
  KEY `IDX_Name` (`name`),
  KEY `bussiness_head` (`bussiness_head`),
  CONSTRAINT `FK_ORGANIZATION` FOREIGN KEY (`parent_id`) REFERENCES `org_hierarchy` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `org_hierarchy_ibfk_1` FOREIGN KEY (`bussiness_head`) REFERENCES `resource` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=latin1;

/*Table structure for table `org_hierarchy_aud` */

CREATE TABLE `org_hierarchy_aud` (
  `id` int(11) NOT NULL,
  `REV` int(11) NOT NULL,
  `REVTYPE` tinyint(4) DEFAULT NULL,
  `name` varchar(256) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `creation_date` date DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `user` char(1) DEFAULT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `action` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`id`,`REV`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `ownership` */

CREATE TABLE `ownership` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ownership_name` varchar(256) NOT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique` (`ownership_name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Table structure for table `phaseengactvty_mapping` */

CREATE TABLE `phaseengactvty_mapping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phase_id` int(11) DEFAULT NULL,
  `engmt_id` int(11) DEFAULT NULL,
  `acty_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_Phse_Engmt_Acty` (`phase_id`,`engmt_id`,`acty_id`),
  KEY `fk_phase_id` (`phase_id`),
  KEY `fk_engmt_id` (`engmt_id`),
  KEY `fk_acvty_id` (`acty_id`),
  CONSTRAINT `fk_acvty_id` FOREIGN KEY (`acty_id`) REFERENCES `activity` (`id`),
  CONSTRAINT `fk_engmt_id` FOREIGN KEY (`engmt_id`) REFERENCES `engagementmodel` (`id`),
  CONSTRAINT `fk_phase_id` FOREIGN KEY (`phase_id`) REFERENCES `phasemaster` (`phaseid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `phasemaster` */

CREATE TABLE `phasemaster` (
  `phaseid` int(11) NOT NULL AUTO_INCREMENT,
  `phasename` varchar(900) DEFAULT NULL,
  PRIMARY KEY (`phaseid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `process_ost` */

CREATE TABLE `process_ost` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `module_id` int(10) DEFAULT NULL,
  `process_name` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `module_id` (`module_id`),
  CONSTRAINT `process_ost_ibfk_1` FOREIGN KEY (`module_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `project` */

CREATE TABLE `project` (
  `team_id` int(11) DEFAULT NULL,
  `bu_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_contacts` varchar(256) DEFAULT NULL,
  `deere` tinyint(1) NOT NULL,
  `offshore_del_mgr` int(11) DEFAULT NULL,
  `onsite_del_mgr` varchar(256) DEFAULT NULL,
  `planned_proj_cost` int(11) DEFAULT NULL,
  `planned_proj_size` int(11) DEFAULT NULL,
  `project_kick_off` date DEFAULT NULL,
  `project_name` varchar(256) NOT NULL,
  `customer_name_id` int(11) NOT NULL,
  `engagement_model_id` int(11) DEFAULT NULL,
  `project_tracking_currency_id` int(11) DEFAULT NULL,
  `project_end_date` date DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `project_category_id` int(11) DEFAULT NULL,
  `project_methodology_id` int(11) DEFAULT NULL,
  `invoice_by_id` int(11) DEFAULT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `module_ost` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_project_name` (`project_name`),
  KEY `FKED904B192FB1D0CB` (`engagement_model_id`),
  KEY `FKED904B1939E0DD3C` (`customer_name_id`),
  KEY `FKED904B19D2474568` (`project_tracking_currency_id`),
  KEY `FK_project_offshore_del_manager` (`offshore_del_mgr`),
  KEY `FK_project_category` (`project_category_id`),
  KEY `FK_project_methodology` (`project_methodology_id`),
  KEY `FK_project_invoice_by` (`invoice_by_id`),
  KEY `FK_org_heirarchy` (`bu_id`),
  KEY `project_ibfk_3` (`team_id`),
  CONSTRAINT `fk_bu_id` FOREIGN KEY (`bu_id`) REFERENCES `org_hierarchy` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_currentcy_id` FOREIGN KEY (`project_tracking_currency_id`) REFERENCES `currency` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `FK_del_mgr` FOREIGN KEY (`offshore_del_mgr`) REFERENCES `resource` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_eng_mod_id` FOREIGN KEY (`engagement_model_id`) REFERENCES `engagementmodel` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `FK_project` FOREIGN KEY (`customer_name_id`) REFERENCES `customer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_project_invoice_by` FOREIGN KEY (`invoice_by_id`) REFERENCES `invoice_by` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `project_ibfk_1` FOREIGN KEY (`project_category_id`) REFERENCES `project_category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `project_ibfk_2` FOREIGN KEY (`project_methodology_id`) REFERENCES `project_methodology` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `project_ibfk_3` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=902 DEFAULT CHARSET=latin1;

/*Table structure for table `project_aud` */

CREATE TABLE `project_aud` (
  `id` int(11) NOT NULL,
  `REV` int(11) NOT NULL,
  `REVTYPE` tinyint(4) DEFAULT NULL,
  `customer_contacts` varchar(256) DEFAULT NULL,
  `deere` bit(1) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `onsite_del_mgr` varchar(256) DEFAULT NULL,
  `planned_proj_cost` int(11) DEFAULT NULL,
  `planned_proj_size` int(11) DEFAULT NULL,
  `project_end_date` date DEFAULT NULL,
  `project_kick_off` date DEFAULT NULL,
  `project_name` varchar(256) DEFAULT NULL,
  `bu_id` int(11) DEFAULT NULL,
  `customer_name_id` int(11) DEFAULT NULL,
  `engagement_model_id` int(11) DEFAULT NULL,
  `invoice_by_id` int(11) DEFAULT NULL,
  `offshore_del_mgr` int(11) DEFAULT NULL,
  `project_category_id` int(11) DEFAULT NULL,
  `project_methodology_id` int(11) DEFAULT NULL,
  `project_tracking_currency_id` int(11) DEFAULT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `action` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`id`,`REV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `project_category` */

CREATE TABLE `project_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(256) NOT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Table structure for table `project_methodology` */

CREATE TABLE `project_methodology` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `methodology` varchar(256) NOT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Table structure for table `project_module` */

CREATE TABLE `project_module` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `module_name` varchar(100) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `active` tinyint(4) DEFAULT NULL,
  `created_id` varchar(100) DEFAULT NULL,
  `creation_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastupdated_id` varchar(100) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `project_module_ibfk_1` (`project_id`),
  CONSTRAINT `project_module_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `project_po` */

CREATE TABLE `project_po` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NOT NULL,
  `cost` int(11) DEFAULT NULL,
  `account_name` varchar(256) DEFAULT NULL,
  `po_number` varchar(256) DEFAULT NULL,
  `issue_date` date DEFAULT NULL,
  `validUpto_date` date DEFAULT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `FK_project_po` (`project_id`),
  CONSTRAINT `FK_project_po` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `project_po_aud` */

CREATE TABLE `project_po_aud` (
  `id` int(11) NOT NULL,
  `REV` int(11) NOT NULL,
  `REVTYPE` tinyint(4) DEFAULT NULL,
  `account_name` varchar(256) DEFAULT NULL,
  `cost` int(11) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `po_number` varchar(256) DEFAULT NULL,
  `issue_date` date DEFAULT NULL,
  `validUpto_date` date DEFAULT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `action` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`id`,`REV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `racfid` */

CREATE TABLE `racfid` (
  `racfid` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `rateid` */

CREATE TABLE `rateid` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rateid` varchar(256) NOT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique` (`rateid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `rating` */

CREATE TABLE `rating` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `region` */

CREATE TABLE `region` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `unit_id` int(11) DEFAULT NULL,
  `region_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_unit` (`unit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `release_note` */

CREATE TABLE `release_note` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ReleaseDate` datetime DEFAULT NULL,
  `ReleaseNumber` varchar(50) DEFAULT NULL,
  `Description` varchar(10000) DEFAULT NULL,
  `Type` varchar(40) DEFAULT NULL COMMENT '1=Enhancement, 2=Defect',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `reopen_reason_ost` */

CREATE TABLE `reopen_reason_ost` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `reopen_reason` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `reports_users` */

CREATE TABLE `reports_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Table structure for table `request` */

CREATE TABLE `request` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sent_mail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `request_requisition` */

CREATE TABLE `request_requisition` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `indentor_comments` varchar(255) DEFAULT NULL,
  `date_of_Indent` datetime DEFAULT NULL,
  `project_bu` varchar(255) DEFAULT NULL,
  `project_name` varchar(255) DEFAULT NULL,
  `emp_id` int(11) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `sent_mail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK414EF28FB1E33D66D` (`emp_id`),
  KEY `FK414EF28F52A6800D` (`project_id`),
  CONSTRAINT `request_requisition_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `request_requisition_ibfk_2` FOREIGN KEY (`emp_id`) REFERENCES `resource` (`employee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Table structure for table `resource` */

CREATE TABLE `resource` (
  `upload_image` longblob,
  `rejoining_flag` char(10) DEFAULT 'N',
  `employee_id` int(11) NOT NULL AUTO_INCREMENT,
  `actual_capacity` int(11) DEFAULT NULL,
  `award_recognition` varchar(256) DEFAULT NULL,
  `confirmation_date` date DEFAULT NULL,
  `contact_number` varchar(256) DEFAULT NULL,
  `contact_number_three` varchar(256) DEFAULT NULL,
  `contact_number_two` varchar(256) DEFAULT NULL,
  `customer_id_detail` varchar(256) DEFAULT NULL,
  `date_of_joining` date NOT NULL,
  `email_id` varchar(256) NOT NULL,
  `last_appraisal` date DEFAULT NULL,
  `penultimate_appraisal` date DEFAULT NULL,
  `planned_capacity` int(11) DEFAULT NULL,
  `profit_centre` varchar(256) DEFAULT NULL,
  `release_date` date DEFAULT NULL,
  `resume_file_name` varchar(256) DEFAULT NULL,
  `transfer_date` date DEFAULT NULL,
  `visa_valid` date DEFAULT NULL,
  `yash_emp_id` varchar(256) NOT NULL,
  `bu_id` int(11) NOT NULL,
  `current_project_id` int(11) DEFAULT NULL,
  `current_reporting_manager` int(11) DEFAULT NULL,
  `current_reporting_manager_two` int(11) DEFAULT NULL,
  `designation_id` int(11) NOT NULL,
  `grade_id` int(11) NOT NULL,
  `location_id` int(11) NOT NULL,
  `ownership` int(11) NOT NULL,
  `payroll_location` int(11) DEFAULT NULL,
  `visa_id` int(11) DEFAULT NULL,
  `USER_ROLE` varchar(256) DEFAULT 'ROLE_USER',
  `user_name` varchar(256) DEFAULT NULL,
  `first_name` varchar(256) NOT NULL,
  `last_name` varchar(256) NOT NULL,
  `current_bu_id` int(11) NOT NULL,
  `timesheet_comment_optional` char(1) DEFAULT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `middle_name` varchar(256) DEFAULT NULL,
  `bGHComments` varchar(1000) DEFAULT NULL,
  `bGH_Name` int(11) DEFAULT NULL,
  `hRComments` varchar(1000) DEFAULT NULL,
  `hR_Name` int(11) DEFAULT NULL,
  `bGComment_timestamp` timestamp NULL DEFAULT NULL,
  `bUComment_timestamp` timestamp NULL DEFAULT NULL,
  `hRComment_timestamp` timestamp NULL DEFAULT NULL,
  `event_id` int(11) DEFAULT NULL,
  `bUComments` varchar(1000) DEFAULT NULL,
  `bU_Name` int(11) DEFAULT NULL,
  `employee_category` int(11) DEFAULT NULL,
  `competency` int(11) DEFAULT NULL,
  `end_transfer_date` date DEFAULT NULL,
  `report_user_id` int(11) DEFAULT NULL,
  `resignation_date` date DEFAULT NULL,
  PRIMARY KEY (`employee_id`),
  UNIQUE KEY `yash_emp_id` (`yash_emp_id`),
  KEY `FKEBABC40E405EDAD4` (`current_reporting_manager`),
  KEY `FKEBABC40E387C38A` (`visa_id`),
  KEY `FKEBABC40E66D08E4A` (`bu_id`),
  KEY `FKEBABC40E17D51630` (`current_project_id`),
  KEY `FKEBABC40EBB3D670A` (`location_id`),
  KEY `FKEBABC40E13FD36EA` (`grade_id`),
  KEY `FKEBABC40EFCB16F2F` (`designation_id`),
  KEY `FKEBABC40E839B3161` (`current_reporting_manager_two`),
  KEY `FKEBABC40EFAA5DAB4` (`payroll_location`),
  KEY `FKEBABC40EF976176E` (`ownership`),
  KEY `F1` (`current_bu_id`),
  KEY `bGH_Name` (`bGH_Name`),
  KEY `bU_Name` (`bU_Name`),
  KEY `hR_Name` (`hR_Name`),
  KEY `event_id` (`event_id`),
  KEY `employee_category` (`employee_category`),
  KEY `competency` (`competency`),
  KEY `fk_report_user_id` (`report_user_id`),
  CONSTRAINT `FKEBABC40E13FD36EA` FOREIGN KEY (`grade_id`) REFERENCES `grade` (`id`),
  CONSTRAINT `FKEBABC40E17D51630` FOREIGN KEY (`current_project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `FKEBABC40E387C38A` FOREIGN KEY (`visa_id`) REFERENCES `visa` (`id`),
  CONSTRAINT `FKEBABC40EBB3D670A` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`),
  CONSTRAINT `FKEBABC40EF976176E` FOREIGN KEY (`ownership`) REFERENCES `ownership` (`id`),
  CONSTRAINT `FKEBABC40EFAA5DAB4` FOREIGN KEY (`payroll_location`) REFERENCES `location` (`id`),
  CONSTRAINT `FKEBABC40EFCB16F2F` FOREIGN KEY (`designation_id`) REFERENCES `designations` (`id`),
  CONSTRAINT `FK_CURRENTBU` FOREIGN KEY (`current_bu_id`) REFERENCES `org_hierarchy` (`id`),
  CONSTRAINT `FK_MANAGER` FOREIGN KEY (`current_reporting_manager`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `FK_PARENTBU` FOREIGN KEY (`bu_id`) REFERENCES `org_hierarchy` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_report_user_id` FOREIGN KEY (`report_user_id`) REFERENCES `reports_users` (`id`),
  CONSTRAINT `FK_resource_rm2` FOREIGN KEY (`current_reporting_manager_two`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `resource_ibfk_1` FOREIGN KEY (`bGH_Name`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `resource_ibfk_2` FOREIGN KEY (`bU_Name`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `resource_ibfk_3` FOREIGN KEY (`hR_Name`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `resource_ibfk_4` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`),
  CONSTRAINT `resource_ibfk_5` FOREIGN KEY (`employee_category`) REFERENCES `employee_category` (`id`),
  CONSTRAINT `resource_ibfk_6` FOREIGN KEY (`competency`) REFERENCES `competency` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4927 DEFAULT CHARSET=latin1;

/*Table structure for table `resource_allocation` */

CREATE TABLE `resource_allocation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `employee_id` int(11) NOT NULL,
  `allocation_seq` int(11) DEFAULT NULL,
  `alloc_start_date` date DEFAULT NULL,
  `alloc_end_date` date DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `alloc_block` tinyint(1) DEFAULT NULL,
  `alloc_remarks` varchar(256) DEFAULT NULL,
  `curProj` tinyint(1) DEFAULT NULL,
  `billable` tinyint(1) DEFAULT NULL,
  `allocHrs` int(11) DEFAULT NULL,
  `rate_id` int(11) DEFAULT NULL,
  `allocation_type_id` int(11) DEFAULT NULL,
  `ownership_id` int(11) DEFAULT NULL,
  `allocated_by` int(11) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `behalf_manager` tinyint(1) DEFAULT '0',
  `designation_id` int(11) DEFAULT NULL,
  `grade_id` int(11) DEFAULT NULL,
  `bu_id` int(11) DEFAULT NULL,
  `current_bu_id` int(11) DEFAULT NULL,
  `location_id` int(11) DEFAULT NULL,
  `current_reporting_manager` int(11) DEFAULT NULL,
  `current_reporting_manager_two` int(11) DEFAULT NULL,
  `ownership_transfer_date` date DEFAULT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `employee_id` (`employee_id`),
  KEY `project_id` (`project_id`),
  KEY `rate_id` (`rate_id`),
  KEY `allocation_type_id` (`allocation_type_id`),
  KEY `ownership_id` (`ownership_id`),
  KEY `FK_resource_allocation_by` (`allocated_by`),
  KEY `FK_resource_updated_by` (`updated_by`),
  KEY `FK_resource_allocation_designation` (`designation_id`),
  KEY `FK_resource_allocation_grade` (`grade_id`),
  KEY `FK_resource_allocation_parentBu` (`bu_id`),
  KEY `FK_resource_allocation_currentBu` (`current_bu_id`),
  KEY `FK_resource_allocation_location` (`location_id`),
  KEY `FK_resource_allocation-RM1` (`current_reporting_manager`),
  KEY `FK_resource_allocation_RM2` (`current_reporting_manager_two`),
  CONSTRAINT `FK_resource_allocation-RM1` FOREIGN KEY (`current_reporting_manager`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `FK_resource_allocation_by` FOREIGN KEY (`allocated_by`) REFERENCES `resource` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_resource_allocation_currentBu` FOREIGN KEY (`current_bu_id`) REFERENCES `org_hierarchy` (`id`),
  CONSTRAINT `FK_resource_allocation_designation` FOREIGN KEY (`designation_id`) REFERENCES `designations` (`id`),
  CONSTRAINT `FK_resource_allocation_grade` FOREIGN KEY (`grade_id`) REFERENCES `grade` (`id`),
  CONSTRAINT `FK_resource_allocation_location` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`),
  CONSTRAINT `FK_resource_allocation_parentBu` FOREIGN KEY (`bu_id`) REFERENCES `org_hierarchy` (`id`),
  CONSTRAINT `FK_resource_allocation_RM2` FOREIGN KEY (`current_reporting_manager_two`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `FK_resource_updated_by` FOREIGN KEY (`updated_by`) REFERENCES `resource` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `resource_allocation_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `resource` (`employee_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `resource_allocation_ibfk_2` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `resource_allocation_ibfk_4` FOREIGN KEY (`ownership_id`) REFERENCES `ownership` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `resource_allocation_ibfk_5` FOREIGN KEY (`allocation_type_id`) REFERENCES `allocationtype` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9266 DEFAULT CHARSET=latin1;

/*Table structure for table `resource_allocation_aud` */

CREATE TABLE `resource_allocation_aud` (
  `ownership_transfer_date` date DEFAULT NULL,
  `bu_id` int(11) DEFAULT NULL,
  `grade_id` int(11) DEFAULT NULL,
  `designation_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `REV` int(11) NOT NULL,
  `current_reporting_manager_two` int(11) DEFAULT NULL,
  `current_reporting_manager` int(11) DEFAULT NULL,
  `location_id` int(11) DEFAULT NULL,
  `current_bu_id` int(11) DEFAULT NULL,
  `REVTYPE` tinyint(4) DEFAULT NULL,
  `alloc_block` bit(1) DEFAULT NULL,
  `alloc_end_date` date DEFAULT NULL,
  `allocHrs` int(11) DEFAULT NULL,
  `alloc_remarks` varchar(256) DEFAULT NULL,
  `alloc_start_date` date DEFAULT NULL,
  `allocation_seq` int(11) DEFAULT NULL,
  `billable` tinyint(1) DEFAULT NULL,
  `curProj` tinyint(1) DEFAULT NULL,
  `allocated_by` int(11) DEFAULT NULL,
  `allocation_type_id` int(11) DEFAULT NULL,
  `employee_id` int(11) DEFAULT NULL,
  `ownership_id` int(11) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `rate_id` int(11) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `behalf_manager` tinyint(1) DEFAULT '0',
  `action` varchar(11) DEFAULT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`,`REV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `resource_aud` */

CREATE TABLE `resource_aud` (
  `employee_id` int(11) NOT NULL,
  `REV` int(11) NOT NULL,
  `REVTYPE` tinyint(4) DEFAULT NULL,
  `actual_capacity` int(11) DEFAULT NULL,
  `award_recognition` varchar(256) DEFAULT NULL,
  `confirmation_date` date DEFAULT NULL,
  `contact_number` varchar(256) DEFAULT NULL,
  `contact_number_three` varchar(256) DEFAULT NULL,
  `contact_number_two` varchar(256) DEFAULT NULL,
  `customer_id_detail` varchar(256) DEFAULT NULL,
  `date_of_joining` date DEFAULT NULL,
  `email_id` varchar(256) DEFAULT NULL,
  `employee_name` varchar(256) DEFAULT NULL,
  `last_appraisal` date DEFAULT NULL,
  `penultimate_appraisal` date DEFAULT NULL,
  `planned_capacity` int(11) DEFAULT NULL,
  `primary_skills` varchar(256) DEFAULT NULL,
  `profit_centre` varchar(256) DEFAULT NULL,
  `release_date` date DEFAULT NULL,
  `resume_file_name` varchar(256) DEFAULT NULL,
  `secondary_skills` varchar(256) DEFAULT NULL,
  `transfer_date` date DEFAULT NULL,
  `USER_ROLE` varchar(256) DEFAULT NULL,
  `visa_valid` date DEFAULT NULL,
  `yash_emp_id` varchar(256) DEFAULT NULL,
  `bu_id` int(11) DEFAULT NULL,
  `current_project_id` int(11) DEFAULT NULL,
  `current_reporting_manager` int(11) DEFAULT NULL,
  `current_reporting_manager_two` int(11) DEFAULT NULL,
  `designation_id` int(11) DEFAULT NULL,
  `grade_id` int(11) DEFAULT NULL,
  `location_id` int(11) DEFAULT NULL,
  `ownership` int(11) DEFAULT NULL,
  `payroll_location` int(11) DEFAULT NULL,
  `visa_id` int(11) DEFAULT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `current_bu_id` int(11) DEFAULT NULL,
  `timesheet_comment_optional` char(1) DEFAULT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `action` varchar(11) DEFAULT NULL,
  `middle_name` varbinary(256) DEFAULT NULL,
  `bGHComments` varchar(256) DEFAULT NULL,
  `bGH_Name` int(11) DEFAULT NULL,
  `bUComments` varchar(256) DEFAULT NULL,
  `bU_Name` int(11) DEFAULT NULL,
  `hRComments` varchar(256) DEFAULT NULL,
  `hR_Name` int(11) DEFAULT NULL,
  `bGComment_timestamp` timestamp NULL DEFAULT NULL,
  `bUComment_timestamp` timestamp NULL DEFAULT NULL,
  `hRComment_timestamp` timestamp NULL DEFAULT NULL,
  `event_id` int(11) DEFAULT NULL,
  `competency` int(11) DEFAULT NULL,
  `employee_category` int(11) DEFAULT NULL,
  `end_transfer_date` date DEFAULT NULL,
  PRIMARY KEY (`employee_id`,`REV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `resource_ln_tr_dtl` */

CREATE TABLE `resource_ln_tr_dtl` (
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `employee_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `transfer_date` date DEFAULT NULL,
  `bu_id` int(11) DEFAULT NULL,
  `current_reporting_manager` int(11) DEFAULT NULL,
  `current_reporting_two` int(11) DEFAULT NULL,
  `designation_id` int(11) DEFAULT NULL,
  `grade_id` int(11) DEFAULT NULL,
  `location_id` int(11) DEFAULT NULL,
  `ownership` int(11) DEFAULT NULL,
  `payroll_location` int(11) DEFAULT NULL,
  `current_bu_id` int(11) DEFAULT NULL,
  `yash_emp_id` varchar(256) DEFAULT NULL,
  `email_id` varchar(256) DEFAULT NULL,
  `contact_number` varchar(256) DEFAULT NULL,
  `contact_number_two` varchar(256) DEFAULT NULL,
  `date_Of_Joining` date DEFAULT NULL,
  `confirmation_Date` date DEFAULT NULL,
  `last_Appraisal` date DEFAULT NULL,
  `penultimate_Appraisal` date DEFAULT NULL,
  `release_Date` date DEFAULT NULL,
  `bGHComments` varchar(1000) DEFAULT NULL,
  `bGH_Name` int(11) DEFAULT NULL,
  `hRComments` varchar(1000) DEFAULT NULL,
  `hR_Name` int(11) DEFAULT NULL,
  `bGComment_timestamp` timestamp NULL DEFAULT NULL,
  `bUComment_timestamp` timestamp NULL DEFAULT NULL,
  `hRComment_timestamp` timestamp NULL DEFAULT NULL,
  `event_id` int(11) DEFAULT NULL,
  `bUComments` varchar(1000) DEFAULT NULL,
  `bU_Name` int(11) DEFAULT NULL,
  `employee_category` int(11) DEFAULT NULL,
  `end_transfer_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Location` (`location_id`),
  KEY `FK_Resource_LT` (`employee_id`),
  KEY `FK_BU_LT` (`bu_id`),
  KEY `FK_Current_RM1_LT` (`current_reporting_manager`),
  KEY `FK_Current_RM2_LT` (`current_reporting_two`),
  KEY `FK_Designation_LT` (`designation_id`),
  KEY `FK_grade_LT` (`grade_id`),
  KEY `FK_ownership_LT` (`ownership`),
  KEY `FK_Payroll_lcation_LT` (`payroll_location`),
  KEY `FK_Current_bu_id_LT` (`current_bu_id`),
  KEY `bGH_Name` (`bGH_Name`),
  KEY `bU_Name` (`bU_Name`),
  KEY `hR_Name` (`hR_Name`),
  KEY `event_id` (`event_id`),
  KEY `employee_category` (`employee_category`),
  CONSTRAINT `FK_BU_LT` FOREIGN KEY (`bu_id`) REFERENCES `org_hierarchy` (`id`),
  CONSTRAINT `FK_Current_bu_id_LT` FOREIGN KEY (`current_bu_id`) REFERENCES `org_hierarchy` (`id`),
  CONSTRAINT `FK_Current_RM1_LT` FOREIGN KEY (`current_reporting_manager`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `FK_Current_RM2_LT` FOREIGN KEY (`current_reporting_two`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `FK_Designation_LT` FOREIGN KEY (`designation_id`) REFERENCES `designations` (`id`),
  CONSTRAINT `FK_grade_LT` FOREIGN KEY (`grade_id`) REFERENCES `grade` (`id`),
  CONSTRAINT `FK_Location` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`),
  CONSTRAINT `FK_ownership_LT` FOREIGN KEY (`ownership`) REFERENCES `ownership` (`id`),
  CONSTRAINT `FK_Payroll_lcation_LT` FOREIGN KEY (`payroll_location`) REFERENCES `location` (`id`),
  CONSTRAINT `FK_Resource_LT` FOREIGN KEY (`employee_id`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `resource_ln_tr_dtl_ibfk_1` FOREIGN KEY (`bGH_Name`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `resource_ln_tr_dtl_ibfk_2` FOREIGN KEY (`bU_Name`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `resource_ln_tr_dtl_ibfk_3` FOREIGN KEY (`hR_Name`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `resource_ln_tr_dtl_ibfk_4` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`),
  CONSTRAINT `resource_ln_tr_dtl_ibfk_5` FOREIGN KEY (`employee_category`) REFERENCES `employee_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `resource_token` */

CREATE TABLE `resource_token` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(256) NOT NULL,
  `token` varchar(256) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `revinfo` */

CREATE TABLE `revinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `rework_ost` */

CREATE TABLE `rework_ost` (
  `ca_ticket_id` int(10) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rework_type` varchar(500) DEFAULT NULL,
  `start_date_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `end_date_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `hours` int(11) DEFAULT NULL,
  `justified` varchar(200) DEFAULT 'No',
  PRIMARY KEY (`id`),
  KEY `ca_ticket_id` (`ca_ticket_id`),
  CONSTRAINT `rework_ost_ibfk_1` FOREIGN KEY (`ca_ticket_id`) REFERENCES `ca_ticket` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `root_cause_ost` */

CREATE TABLE `root_cause_ost` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `root_cause` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `root_cause_ost_old` */

CREATE TABLE `root_cause_ost_old` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `root_cause` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `seskill_resource_secondary` */

CREATE TABLE `seskill_resource_secondary` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `skill_id` int(11) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_skill_resource_2` (`resource_id`),
  KEY `skill_id` (`skill_id`),
  CONSTRAINT `seskill_resource_secondary_ibfk_1` FOREIGN KEY (`skill_id`) REFERENCES `skills` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `seskill_resource_secondary_ibfk_2` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`employee_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `skill_profile_primary` */

CREATE TABLE `skill_profile_primary` (
  `skill_id` int(11) DEFAULT NULL,
  `profile_id` int(11) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '0',
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_skill_profile_1` (`profile_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `skill_profile_secondary` */

CREATE TABLE `skill_profile_secondary` (
  `skill_id` int(11) DEFAULT NULL,
  `profile_id` int(11) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '0',
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_skill_profile_secondary` (`profile_id`),
  KEY `skill_id` (`skill_id`),
  CONSTRAINT `skill_profile_secondary_ibfk_1` FOREIGN KEY (`profile_id`) REFERENCES `user_profile` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `skill_profile_secondary_ibfk_2` FOREIGN KEY (`skill_id`) REFERENCES `skills` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `skill_request_requisition` */

CREATE TABLE `skill_request_requisition` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `additional_comments` varchar(255) DEFAULT NULL,
  `billable` int(11) DEFAULT NULL,
  `career_growth_plan` varchar(255) DEFAULT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `desirable_skills` varchar(255) DEFAULT NULL,
  `experience` varchar(255) DEFAULT NULL,
  `fulfilled` varchar(255) DEFAULT NULL,
  `key_interviewers_one` varchar(255) DEFAULT NULL,
  `key_interviewers_two` varchar(255) DEFAULT NULL,
  `key_scanners` varchar(255) DEFAULT NULL,
  `req_resources` int(11) DEFAULT NULL,
  `primary_skills` varchar(255) DEFAULT NULL,
  `remaining` varchar(255) DEFAULT NULL,
  `responsibilities` varchar(255) DEFAULT NULL,
  `target_companies` varchar(255) DEFAULT NULL,
  `time_frame` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `designation` int(11) NOT NULL,
  `request_id` int(11) NOT NULL,
  `skills` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `billable` (`billable`),
  KEY `designation` (`designation`),
  KEY `request_id` (`request_id`),
  KEY `skill_request_requisition_ibfk_2` (`skills`),
  CONSTRAINT `skill_request_requisition_ibfk_1` FOREIGN KEY (`billable`) REFERENCES `allocationtype` (`id`),
  CONSTRAINT `skill_request_requisition_ibfk_2` FOREIGN KEY (`skills`) REFERENCES `competency` (`id`),
  CONSTRAINT `skill_request_requisition_ibfk_3` FOREIGN KEY (`designation`) REFERENCES `designations` (`id`),
  CONSTRAINT `skill_request_requisition_ibfk_4` FOREIGN KEY (`request_id`) REFERENCES `request_requisition` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=latin1;

/*Table structure for table `skill_request_resource` */

CREATE TABLE `skill_request_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `external_resource_name` varchar(255) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `skill_request_id` int(11) NOT NULL,
  `status_id` int(11) DEFAULT NULL,
  `allocation_id` int(11) DEFAULT NULL,
  `allocation_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7D50D7EC48EAF467` (`resource_id`),
  KEY `FK7D50D7ECC1B8B964` (`skill_request_id`),
  KEY `FK7D50D7EC90A65CA6` (`status_id`),
  KEY `FK7D50D7ECCB7AF8C1` (`allocation_id`),
  CONSTRAINT `FK7D50D7EC48EAF467` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `FK7D50D7EC90A65CA6` FOREIGN KEY (`status_id`) REFERENCES `skill_resource_status_type` (`id`),
  CONSTRAINT `FK7D50D7ECC1B8B964` FOREIGN KEY (`skill_request_id`) REFERENCES `skill_request_requisition` (`id`),
  CONSTRAINT `FK7D50D7ECCB7AF8C1` FOREIGN KEY (`allocation_id`) REFERENCES `allocationtype` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `skill_resource_primary` */

CREATE TABLE `skill_resource_primary` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `skill_id` int(11) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_skill_resource_1` (`resource_id`),
  KEY `skill_id` (`skill_id`),
  CONSTRAINT `skill_resource_primary_ibfk_1` FOREIGN KEY (`skill_id`) REFERENCES `skills` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `skill_resource_primary_ibfk_2` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`employee_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `skill_resource_secondary` */

CREATE TABLE `skill_resource_secondary` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `skill_id` int(11) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_skill_resource_2` (`resource_id`),
  KEY `skill_id` (`skill_id`),
  CONSTRAINT `skill_resource_secondary_ibfk_1` FOREIGN KEY (`skill_id`) REFERENCES `skills` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `skill_resource_secondary_ibfk_2` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`employee_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `skill_resource_status_type` */

CREATE TABLE `skill_resource_status_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status_name` varchar(255) DEFAULT NULL,
  `status_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

/*Table structure for table `skills` */

CREATE TABLE `skills` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `skill` varchar(256) NOT NULL,
  `skill_type` varchar(256) NOT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=729 DEFAULT CHARSET=latin1;

/*Table structure for table `slamissed_reason_ost` */

CREATE TABLE `slamissed_reason_ost` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `sla_missed` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `solution_review_ost` */

CREATE TABLE `solution_review_ost` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `ca_ticket_id` int(10) DEFAULT NULL,
  `landscape` varchar(500) DEFAULT NULL,
  `module` varchar(500) DEFAULT NULL,
  `reviewer` varchar(500) DEFAULT NULL,
  `review_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ca_ticket_number` int(11) DEFAULT NULL,
  `assignee` varchar(500) DEFAULT NULL,
  `is_the_issue_understanding_correct` varchar(30) DEFAULT 'No',
  `alternate_solution` varchar(1000) DEFAULT NULL,
  `is_sol_appropriate` varchar(30) DEFAULT 'No',
  `agree_with_rca` varchar(300) DEFAULT 'No',
  `rating` varchar(500) DEFAULT NULL,
  `comments` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ca_ticket_id` (`ca_ticket_id`),
  CONSTRAINT `solution_review_ost_ibfk_1` FOREIGN KEY (`ca_ticket_id`) REFERENCES `ca_ticket` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `subprocess_ost` */

CREATE TABLE `subprocess_ost` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `process_id` int(10) DEFAULT NULL,
  `subprocess_name` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `process_id` (`process_id`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `t3_contribution_screen_ost` */

CREATE TABLE `t3_contribution_screen_ost` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ticket_no` int(11) DEFAULT NULL,
  `module` varchar(500) DEFAULT NULL,
  `date_contacted` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `description` varchar(900) DEFAULT NULL,
  `reason_for_help_from_t3` varchar(1000) DEFAULT NULL,
  `no_of_hours_taken` int(11) DEFAULT NULL,
  `justified` varchar(10) DEFAULT 'No',
  `ca_ticket_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ca_ticket_id` (`ca_ticket_id`),
  CONSTRAINT `t3_contribution_screen_ost_ibfk_1` FOREIGN KEY (`ca_ticket_id`) REFERENCES `ca_ticket` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `team` */

CREATE TABLE `team` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `project_manager` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `project_manager` (`project_manager`),
  CONSTRAINT `team_ibfk_1` FOREIGN KEY (`project_manager`) REFERENCES `resource` (`employee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=latin1;

/*Table structure for table `team_view_access` */

CREATE TABLE `team_view_access` (
  `team_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  `created_id` varchar(20) DEFAULT NULL,
  `creation_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`team_id`,`resource_id`),
  KEY `FK_EMPLOYEE` (`resource_id`),
  CONSTRAINT `FK_EMPLOYEE` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `FK_TEAM` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `timehrs` */

CREATE TABLE `timehrs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `week_ending_date` date NOT NULL,
  `planned_hrs` double(22,2) DEFAULT NULL,
  `billed_hrs` double(22,2) DEFAULT NULL,
  `remarks` varchar(1000) DEFAULT NULL,
  `resource_id` int(11) NOT NULL,
  `resource_alloc_id` int(11) NOT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `resource_id` (`resource_id`),
  KEY `FK_timehrs` (`resource_alloc_id`),
  CONSTRAINT `FK_timehrs` FOREIGN KEY (`resource_alloc_id`) REFERENCES `resource_allocation` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `timehrs_ibfk_5` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`employee_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `timehrs_aud` */

CREATE TABLE `timehrs_aud` (
  `id` int(11) NOT NULL,
  `REV` int(11) NOT NULL,
  `REVTYPE` tinyint(4) DEFAULT NULL,
  `billed_hrs` double(22,0) DEFAULT NULL,
  `planned_hrs` double(22,0) DEFAULT NULL,
  `remarks` varchar(256) DEFAULT NULL,
  `week_ending_date` date DEFAULT NULL,
  `resource_alloc_id` int(11) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `action` varchar(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `unit_ost` */

CREATE TABLE `unit_ost` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `unit_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `user_activity` */

CREATE TABLE `user_activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_id` int(11) DEFAULT NULL,
  `module` varchar(250) NOT NULL,
  `ticket_no` varchar(256) DEFAULT NULL,
  `res_alloc_id` int(11) NOT NULL,
  `status` char(1) NOT NULL DEFAULT 'N',
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `rejection_remarks` varchar(1000) DEFAULT NULL,
  `approve_code` varchar(20) DEFAULT NULL,
  `reject_code` varchar(20) DEFAULT NULL,
  `custom_activity_id` int(11) DEFAULT NULL,
  `approved_by` int(11) NOT NULL DEFAULT '0',
  `approval_pending_from` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_user_activity` (`activity_id`),
  KEY `FK_user_activity_resalloc` (`res_alloc_id`),
  KEY `fk_custum_actvity` (`custom_activity_id`),
  CONSTRAINT `fk_custum_actvity` FOREIGN KEY (`custom_activity_id`) REFERENCES `custom_activity` (`id`),
  CONSTRAINT `fk_resalloc` FOREIGN KEY (`res_alloc_id`) REFERENCES `resource_allocation` (`id`),
  CONSTRAINT `user_activity_ibfk_1` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=227209 DEFAULT CHARSET=utf8;

/*Table structure for table `user_activity_aud` */

CREATE TABLE `user_activity_aud` (
  `id` int(11) NOT NULL,
  `REV` int(11) NOT NULL,
  `REVTYPE` tinyint(4) DEFAULT NULL,
  `module` varchar(256) DEFAULT NULL,
  `activity_id` int(11) DEFAULT NULL,
  `employee_id` int(11) DEFAULT NULL,
  `resource_alloc_id` int(11) DEFAULT NULL,
  `submit_status` char(1) DEFAULT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `action` varchar(11) DEFAULT NULL,
  `approve_code` varchar(20) DEFAULT NULL,
  `reject_code` varchar(20) DEFAULT NULL,
  `custom_activity_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`,`REV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `user_activity_detail` */

CREATE TABLE `user_activity_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `time_sheet_id` bigint(20) NOT NULL,
  `d1_hours` double(10,2) DEFAULT NULL,
  `d2_hours` double(10,2) DEFAULT NULL,
  `d3_hours` double(10,2) DEFAULT NULL,
  `d4_hours` double(10,2) DEFAULT NULL,
  `d5_hours` double(10,2) DEFAULT NULL,
  `d6_hours` double(10,2) DEFAULT NULL,
  `d7_hours` double(10,2) DEFAULT NULL,
  `d1_comment` varchar(1000) DEFAULT NULL,
  `d2_comment` varchar(1000) DEFAULT NULL,
  `d3_comment` varchar(1000) DEFAULT NULL,
  `d4_comment` varchar(1000) DEFAULT NULL,
  `d5_comment` varchar(1000) DEFAULT NULL,
  `d6_comment` varchar(1000) DEFAULT NULL,
  `d7_comment` varchar(1000) DEFAULT NULL,
  `week_start_date` date NOT NULL,
  `week_end_date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `NW_USR_ACTY_DTL_FK1` (`time_sheet_id`),
  KEY `date_idx` (`week_start_date`,`week_end_date`),
  CONSTRAINT `FK_time_sheet_id_` FOREIGN KEY (`time_sheet_id`) REFERENCES `user_activity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `user_activity_detail_aud` */

CREATE TABLE `user_activity_detail_aud` (
  `id` bigint(20) NOT NULL,
  `REV` int(11) NOT NULL,
  `REVTYPE` tinyint(4) NOT NULL,
  `time_sheet_id` bigint(20) NOT NULL,
  `d1_hours` double(10,2) DEFAULT NULL,
  `d2_hours` double(10,2) DEFAULT NULL,
  `d3_hours` double(10,2) DEFAULT NULL,
  `d4_hours` double(10,2) DEFAULT NULL,
  `d5_hours` double(10,2) DEFAULT NULL,
  `d6_hours` double(10,2) DEFAULT NULL,
  `d7_hours` double(10,2) DEFAULT NULL,
  `d1_comment` varchar(256) DEFAULT NULL,
  `d2_comment` varchar(256) DEFAULT NULL,
  `d3_comment` varchar(256) DEFAULT NULL,
  `d4_comment` varchar(256) DEFAULT NULL,
  `d5_comment` varchar(256) DEFAULT NULL,
  `d6_comment` varchar(256) DEFAULT NULL,
  `d7_comment` varchar(256) DEFAULT NULL,
  `week_start_date` date NOT NULL,
  `week_end_date` date NOT NULL,
  `action` varchar(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `user_notification` */

CREATE TABLE `user_notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `employee_id` int(11) NOT NULL,
  `msg` varchar(256) NOT NULL,
  `is_read` tinyint(1) NOT NULL DEFAULT '0',
  `msg_type` varchar(256) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_user_notification_resource` (`employee_id`),
  CONSTRAINT `FK_user_notification_resource` FOREIGN KEY (`employee_id`) REFERENCES `resource` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `user_profile` */

CREATE TABLE `user_profile` (
  `upload_image` longblob,
  `deny_code` varchar(20) DEFAULT NULL,
  `approval_code` varchar(20) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `yash_emp_id` varchar(256) NOT NULL,
  `first_name` varchar(256) DEFAULT NULL,
  `last_name` varchar(256) DEFAULT NULL,
  `contact_number_one` varchar(256) DEFAULT NULL,
  `contact_number_two` varchar(256) DEFAULT NULL,
  `email_id` varchar(256) DEFAULT NULL,
  `customer_id_detail` varchar(256) DEFAULT NULL,
  `resume_file_name` varchar(256) DEFAULT NULL,
  `logical_delete` varchar(10) DEFAULT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `middle_name` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=891 DEFAULT CHARSET=utf8;

/*Table structure for table `visa` */

CREATE TABLE `visa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `visa` varchar(256) NOT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Visa` (`visa`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/* Trigger structure for table `bgadmin_access_rights` */

DELIMITER $$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `bgadmin_access_trigger_insert` AFTER INSERT ON `bgadmin_access_rights` FOR EACH ROW BEGIN
DECLARE `revision` INT ;
DECLARE id_exists BOOLEAN ;
  SELECT 
    REV,
    1 INTO @revision,
    id_exists 
  FROM
    bgadmin_access_rights_aud 
  WHERE bgadmin_access_rights_aud.id = NEW.id ;
 IF @id_exists = 1 
  THEN SET revision = @revision + 1 ;
  ELSE SET revision = 1 ;
 END IF ;
INSERT INTO `bgadmin_access_rights_aud` (
    `id`,
    `action`,
    `REV`,
    `resource_id`,
    `org_id`    
     ) 
  VALUES
    (
      NEW.id,
      'INSERT',
      `revision`,
      NEW .`resource_id`,
      NEW .`org_id`   
         
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `bgadmin_access_rights` */

DELIMITER $$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `bgadmin_access_trigger_update` AFTER UPDATE ON `bgadmin_access_rights` FOR EACH ROW BEGIN
 DECLARE `revision` INT ;
  
  SELECT 
    max(REV)
     INTO @revision
   
  FROM
   bgadmin_access_rights_aud
  WHERE bgadmin_access_rights_aud.id = NEW.id;
if @revision IS null then
	set revision = 1;
 Else
	SET revision = @revision + 1 ;
END IF;
  
  
  
  
  
 INSERT INTO `bgadmin_access_rights_aud` (
    `id`,
    `action`,
    `REV`,
    `resource_id`,
    `org_id` 
  ) 
  VALUES
    (
      NEW.id,
      'UPDATE',
      `revision`,
      NEW .`resource_id`,
      NEW .`org_id`   
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `bgadmin_access_rights` */

DELIMITER $$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `bgadmin_access_trigger_delete` AFTER DELETE ON `bgadmin_access_rights` FOR EACH ROW BEGIN
 DECLARE `revision` INT ;
  SELECT 
    COUNT(*) INTO @revision 
  FROM
    bgadmin_access_rights_aud 
  WHERE bgadmin_access_rights_aud.id = old.id ;
  
  SET revision = @revision + 1 ;
  
  
  
 INSERT INTO `bgadmin_access_rights_aud` (
    `id`,
    `action`,
    `REV`,
    `resource_id`,
    `org_id` 
  ) 
  VALUES
    (
      old .id,
      'DELETE',
      `revision`,
         OLD .`resource_id`,
      OLD .`org_id`   
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `customer` */

DELIMITER $$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `customer_trigger_insert` AFTER INSERT ON `customer` FOR EACH ROW BEGIN
  DECLARE `revision` INT ;
  DECLARE id_exists BOOLEAN ;
  SELECT 
    REV,
    1 INTO @revision,
    id_exists 
  FROM
    customer_aud 
  WHERE customer_aud.id = NEW.id ;
  IF @id_exists = 1 
  THEN SET revision = @revision + 1 ;
  ELSE SET revision = 1 ;
  END IF ;
  INSERT INTO `customer_aud` (
    `id`,
    `action`,
    `REV`,
    `account_manager`,
    `account_manager_contact_number`,
    `customer_address`,
    `customer_name`,
    `geography`,
    `code`,
    `created_id`,
    `creation_timestamp`,
    `lastupdated_id`,
    `lastupdated_timestamp`
  ) 
  VALUES
    (
      NEW.id,
      'INSERT',
      `revision`,
      NEW .`account_manager`,
      NEW .`account_manager_contact_number`,
      NEW .`customer_address`,
      NEW .`customer_name`,
      NEW .`geography`,
      NEW .`code`,
      NEW .`created_id`,
      NEW .`creation_timestamp`,
      NEW .`lastupdated_id`,
      NEW .`lastupdated_timestamp`
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `customer` */

DELIMITER $$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `customer_trigger_update` AFTER UPDATE ON `customer` FOR EACH ROW BEGIN
  DECLARE `revision` INT ;
  
  SELECT 
    max(REV)
     INTO @revision
   
  FROM
    customer_aud 
  WHERE customer_aud.id = NEW.id;
  
if @revision IS null then
	set revision = 1;
 Else
	SET revision = @revision + 1 ;
END IF;
  
  
  
  
  
  INSERT INTO `customer_aud` (
    `id`,
    `action`,
    `REV`,
    `account_manager`,
    `account_manager_contact_number`,
    `customer_address`,
    `customer_name`,
    `geography`,
    `code`,
    `created_id`,
    `creation_timestamp`,
    `lastupdated_id`,
    `lastupdated_timestamp`
  ) 
  VALUES
    (
      NEW.id,
      'UPDATE',
      `revision`,
      NEW .`account_manager`,
      NEW .`account_manager_contact_number`,
      NEW .`customer_address`,
      NEW .`customer_name`,
      NEW .`geography`,
      NEW .`code`,
      NEW .`created_id`,
      NEW .`creation_timestamp`,
      NEW .`lastupdated_id`,
      NEW .`lastupdated_timestamp`
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `customer` */

DELIMITER $$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `customer_trigger_delete` AFTER DELETE ON `customer` FOR EACH ROW BEGIN
  DECLARE `revision` INT ;
  SELECT 
    COUNT(*) INTO @revision 
  FROM
    customer_aud 
  WHERE customer_aud.id = old.id ;
  
  SET revision = @revision + 1 ;
  
  
  
  INSERT INTO `customer_aud` (
    `id`,
    `action`,
    `REV`,
    `account_manager`,
    `account_manager_contact_number`,
    `customer_address`,
    `customer_name`,
    `geography`,
    `code`,
    `created_id`,
    `creation_timestamp`,
    `lastupdated_id`,
    `lastupdated_timestamp`
  ) 
  VALUES
    (
      old.id,
      'DELETE',
      `revision`,
      OLD .`account_manager`,
      OLD .`account_manager_contact_number`,
      OLD .`customer_address`,
      OLD .`customer_name`,
      OLD .`geography`,
      OLD .`code`,
      OLD .`created_id`,
      OLD .`creation_timestamp`,
      OLD .`lastupdated_id`,
      OLD .`lastupdated_timestamp`
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `org_hierarchy` */

DELIMITER $$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `org_hierarchy_trigger_insert` AFTER INSERT ON `org_hierarchy` FOR EACH ROW BEGIN
  DECLARE `revision` INT ;
  DECLARE id_exists BOOLEAN ;
  SELECT 
    REV,
    1 INTO @revision,
    id_exists 
  FROM
    org_hierarchy_aud 
  WHERE org_hierarchy_aud.id = NEW.id ;
  IF @id_exists = 1 
  THEN SET revision = @revision + 1 ;
  ELSE SET revision = 1 ;
  END IF ;
  INSERT INTO `org_hierarchy_aud` (
    `id`,
    `action`,
    `REV`,
    `name`,
    description,
    `parent_id`,
    `creation_date`,
    `active`,
    `user`,
    
    `created_id`,
    `lastupdated_id`,
    `lastupdated_timestamp`
  ) 
  VALUES
    (
      NEW.id,
      'INSERT',
      `revision`,
    new . `name`,
    new . `description`,
    new . parent_id,
    
    new . `active`,
    new . `user`,
    NEW .`created_id`,
      new . `creation_timestamp`,
      NEW .`lastupdated_id`,
      NEW .`lastupdated_timestamp`
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `org_hierarchy` */

DELIMITER $$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `org_hierarchy_trigger_update` AFTER UPDATE ON `org_hierarchy` FOR EACH ROW BEGIN
  DECLARE `revision` INT ;
  
  SELECT 
    MAX(REV)
     INTO @revision
    
  FROM
    org_hierarchy_aud 
  WHERE org_hierarchy_aud.id = NEW.id;
  
  IF @revision IS NULL THEN
	SET revision = 1;
 ELSE
	SET revision = @revision + 1 ;
END IF;
  
  
  
  INSERT INTO `org_hierarchy_aud` (
    `id`,
    `action`,
    `REV`,
   `name`,
   `description`,
   `parent_id`,
   `active`,
   `user`,
   `created_id`,
    `creation_date`,
    `lastupdated_id`,
    `lastupdated_timestamp`
  ) 
  VALUES
    (
    NEW.id,
      'UPDATE',
      `revision`,
    new . `name`,
   new .`description`,
   new .`parent_id`,
   new . `active`,
   new .`user`,
  
      NEW .`created_id`,
      NEW .`creation_timestamp`,
      NEW .`lastupdated_id`,
      NEW .`lastupdated_timestamp`
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `org_hierarchy` */

DELIMITER $$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `org_hierarchy_trigger_delete` AFTER DELETE ON `org_hierarchy` FOR EACH ROW BEGIN
  DECLARE `revision` INT ;
  SELECT 
    COUNT(*) INTO @revision 
  FROM
    `org_hierarchy_aud` 
  WHERE org_hierarchy_aud.id = old.id ;
  
  SET revision = @revision + 1 ;
  
  
  
  INSERT INTO `org_hierarchy_aud` (
    `id`,
    `action`,
    `REV`,
   `name`,
   `description`,
   `parent_id`,
   `active`,
   `user`,
    `created_id`,
    `creation_date`,
    `lastupdated_id`,
    `lastupdated_timestamp`
  ) 
  VALUES
    (
      old.id,
      'DELETE',
      `revision`,
    old .  `name`,
   old . `description`,
   OLD . `parent_id`,
   OLD . `active`,
    OLD .`user`,
      OLD .`created_id`,
      OLD .`creation_timestamp`,
      OLD .`lastupdated_id`,
      OLD .`lastupdated_timestamp`
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `project` */

DELIMITER $$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `project_trigger_insert` AFTER INSERT ON `project` FOR EACH ROW BEGIN
  DECLARE `revision` INT ;
  DECLARE id_exists BOOLEAN ;
  SELECT 
    REV,
    1 INTO @revision,
    id_exists 
  FROM
    `project_aud` 
  WHERE project_aud.id = NEW.id ;
  IF @id_exists = 1 
  THEN SET revision = @revision + 1 ;
  ELSE SET revision = 1 ;
  END IF ;
  INSERT INTO `project_aud` (
    id,
    `action`,
    `REV`,
     `customer_contacts`,
     deere,
     `description`,
     onsite_del_mgr,
     `planned_proj_cost`,
     `planned_proj_size`,
     `project_end_date`,
     `project_kick_off`,
     `project_name`,
     `bu_id`,
     `customer_name_id`,
     `engagement_model_id`,
     `invoice_by_id`,
     `offshore_del_mgr`,
     `project_category_id`,
     `project_methodology_id`,
     `project_tracking_currency_id`,
     `created_id`,
    `creation_timestamp`,
    lastupdated_id,
    lastupdated_timestamp
  ) 
  VALUES
    (
     NEW . id,
      'INSERT',
      `revision`,
    NEW .`customer_contacts`,
     NEW . deere,
     NEW . `description`,
      NEW .onsite_del_mgr,
     NEW . `planned_proj_cost`,
     NEW . `planned_proj_size`,
     NEW . `project_end_date`,
     NEW . `project_kick_off`,
     NEW . `project_name`,
     NEW . `bu_id`,
     NEW . `customer_name_id`,
     NEW . `engagement_model_id`,
     NEW . `invoice_by_id`,
      NEW .`offshore_del_mgr`,
      NEW .`project_category_id`,
      NEW .`project_methodology_id`,
      NEW .`project_tracking_currency_id`,
    
   NEW . `created_id`,
    NEW .`creation_timestamp`,
    NEW .lastupdated_id,
   NEW . lastupdated_timestamp
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `project` */

DELIMITER $$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `project_trigger_update_before` BEFORE UPDATE ON `project` FOR EACH ROW BEGIN
IF (NEW . `project_end_date` <> OLD . `project_end_date`) OR(OLD . `project_end_date` IS NULL AND NEW . `project_end_date` IS NOT NULL) THEN
UPDATE `resource_allocation` SET `alloc_end_date` =  NEW . `project_end_date` WHERE `project_id` = NEW . `id` AND (`alloc_end_date`> NEW . `project_end_date` OR `alloc_end_date` IS NULL);
END IF;
IF (NEW . `project_kick_off` <> OLD . `project_kick_off`) OR(OLD . `project_kick_off` IS NULL AND NEW . `project_kick_off` IS NOT NULL) THEN
UPDATE `resource_allocation` SET `alloc_start_date` =  NEW . `project_kick_off` WHERE `project_id` = NEW . `id` AND (`alloc_start_date`< NEW . `project_kick_off`);
UPDATE `resource_allocation` SET `alloc_end_date` =  NEW . `project_kick_off` WHERE `project_id` = NEW . `id` AND (`alloc_end_date`< NEW . `project_kick_off`);
END IF;
END */$$


DELIMITER ;

/* Trigger structure for table `project` */

DELIMITER $$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `project_trigger_update` AFTER UPDATE ON `project` FOR EACH ROW BEGIN
  DECLARE `revision` INT ;
  
  SELECT 
    MAX(REV)
     INTO @revision
    
  FROM
    `project_aud` 
  WHERE project_aud.id = NEW.id;
  
  IF @revision IS NULL THEN
	SET revision = 1;
 ELSE
	SET revision = @revision + 1 ;
END IF;
  
  
  
  INSERT INTO `project_aud` (
    id,
    `action`,
    `REV`,
     `customer_contacts`,
     deere,
     `description`,
     onsite_del_mgr,
     `planned_proj_cost`,
     `planned_proj_size`,
     `project_end_date`,
     `project_kick_off`,
     `project_name`,
     `bu_id`,
     `customer_name_id`,
     `engagement_model_id`,
     `invoice_by_id`,
     `offshore_del_mgr`,
     `project_category_id`,
     `project_methodology_id`,
     `project_tracking_currency_id`,
     `created_id`,
    `creation_timestamp`,
    lastupdated_id,
    lastupdated_timestamp
  ) 
  VALUES
    (
     NEW . id,
      'UPDATE',
      `revision`,
    NEW .`customer_contacts`,
     NEW . deere,
     NEW . `description`,
      NEW .onsite_del_mgr,
     NEW . `planned_proj_cost`,
     NEW . `planned_proj_size`,
     NEW . `project_end_date`,
     NEW . `project_kick_off`,
     NEW . `project_name`,
     NEW . `bu_id`,
     NEW . `customer_name_id`,
     NEW . `engagement_model_id`,
     NEW . `invoice_by_id`,
      NEW .`offshore_del_mgr`,
      NEW .`project_category_id`,
      NEW .`project_methodology_id`,
      NEW .`project_tracking_currency_id`,
    
   NEW . `created_id`,
    NEW .`creation_timestamp`,
    NEW .lastupdated_id,
   NEW . lastupdated_timestamp
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `project` */

DELIMITER $$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `project_trigger_delete` AFTER DELETE ON `project` FOR EACH ROW BEGIN
  DECLARE `revision` INT ;
  SELECT 
    COUNT(*) INTO @revision 
  FROM
    `project_aud` 
  WHERE project_aud.id = old.id ;
  
  SET revision = @revision + 1 ;
  
  
  
  INSERT INTO `project_aud` (
    id,
    `action`,
    `REV`,
     `customer_contacts`,
     deere,
     `description`,
     onsite_del_mgr,
     `planned_proj_cost`,
     `planned_proj_size`,
     `project_end_date`,
     `project_kick_off`,
     `project_name`,
     `bu_id`,
     `customer_name_id`,
     `engagement_model_id`,
     `invoice_by_id`,
     `offshore_del_mgr`,
     `project_category_id`,
     `project_methodology_id`,
     `project_tracking_currency_id`,
     `created_id`,
    `creation_timestamp`,
    lastupdated_id,
    lastupdated_timestamp
  ) 
  VALUES
    (
     OLD . id,
      'DELETE',
      `revision`,
    OLD .`customer_contacts`,
     OLD . deere,
     OLD . `description`,
      OLD .onsite_del_mgr,
     OLD . `planned_proj_cost`,
     OLD . `planned_proj_size`,
     OLD . `project_end_date`,
     OLD . `project_kick_off`,
     OLD . `project_name`,
     OLD . `bu_id`,
     OLD . `customer_name_id`,
     OLD . `engagement_model_id`,
     OLD . `invoice_by_id`,
      OLD .`offshore_del_mgr`,
      OLD .`project_category_id`,
      OLD .`project_methodology_id`,
      OLD .`project_tracking_currency_id`,
    
   OLD . `created_id`,
    OLD .`creation_timestamp`,
    OLD .lastupdated_id,
   OLD . lastupdated_timestamp
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `project_po` */

DELIMITER $$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `project_po_trigger_insert` AFTER INSERT ON `project_po` FOR EACH ROW BEGIN
  DECLARE `revision` INT ;
  DECLARE id_exists BOOLEAN ;
  SELECT 
    REV,
    1 INTO @revision,
    id_exists 
  FROM
    project_po_aud 
  WHERE project_po_aud.id = NEW.id ;
  IF @id_exists = 1 
  THEN SET revision = @revision + 1 ;
  ELSE SET revision = 1 ;
  END IF ;
  INSERT INTO `project_po_aud` (
    `id`,
    `action`,
    `REV`,
   `account_name`,
   `cost`,
   `project_id`,
   `po_number`,
   issue_date,
   `validUpto_date`,
    `created_id`,
    `creation_timestamp`,
    lastupdated_id,
    lastupdated_timestamp
  ) 
  VALUES
    (
      NEW.id,
      'INSERT',
      `revision`,
  new . `account_name`,
   new .`cost`,
   new .`project_id`,
   new .`po_number`,
   new . issue_date,
   new . `validUpto_date`,
    NEW . `created_id`,
   NEW .  `creation_timestamp`,
   NEW .  lastupdated_id,
   NEW .  lastupdated_timestamp
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `project_po` */

DELIMITER $$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `project_po_trigger_update` AFTER UPDATE ON `project_po` FOR EACH ROW BEGIN
  DECLARE `revision` INT ;
  
  SELECT 
    MAX(REV)
     INTO @revision
    
  FROM
    project_po_aud 
  WHERE project_po_aud.id = NEW.id;
  
  IF @revision IS NULL THEN
	SET revision = 1;
 ELSE
	SET revision = @revision + 1 ;
END IF;
  
  
  
  INSERT INTO `project_po_aud` (
    `id`,
    `action`,
    `REV`,
   `account_name`,
   `cost`,
   `project_id`,
   `po_number`,
   issue_date,
   `validUpto_date`,
    `created_id`,
    `creation_timestamp`,
    lastupdated_id,
    lastupdated_timestamp
  ) 
  VALUES
    (
      NEW.id,
      'UPDATE',
      `revision`,
  new . `account_name`,
   new .`cost`,
   new .`project_id`,
   new .`po_number`,
   new . issue_date,
   new . `validUpto_date`,
    NEW . `created_id`,
   NEW .  `creation_timestamp`,
   NEW .  lastupdated_id,
   NEW .  lastupdated_timestamp
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `project_po` */

DELIMITER $$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `project_po_trigger_delete` AFTER DELETE ON `project_po` FOR EACH ROW BEGIN
  DECLARE `revision` INT ;
  SELECT 
    COUNT(*) INTO @revision 
  FROM
    `project_po_aud` 
  WHERE project_po_aud.id = old.id ;
  
  SET revision = @revision + 1 ;
  
  
  
  INSERT INTO `project_po_aud` (
    `id`,
    `action`,
    `REV`,
   `account_name`,
   `cost`,
   `project_id`,
   `po_number`,
   issue_date,
   `validUpto_date`,
    `created_id`,
    `creation_timestamp`,
    lastupdated_id,
    lastupdated_timestamp
  ) 
  VALUES
    (
      OLD.id,
      'DELETE',
      `revision`,
  OLD . `account_name`,
   OLD .`cost`,
   OLD .`project_id`,
   OLD .`po_number`,
   OLD . issue_date,
   OLD . `validUpto_date`,
    OLD . `created_id`,
   OLD .  `creation_timestamp`,
   OLD .  lastupdated_id,
   OLD .  lastupdated_timestamp
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `resource` */

DELIMITER $$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `resource_trigger_insert` AFTER INSERT ON `resource` FOR EACH ROW BEGIN
  DECLARE `revision` INT ;
  DECLARE id_exists BOOLEAN ;
  SELECT 
    REV,
    1 INTO @revision,
    id_exists 
  FROM
    resource_aud 
  WHERE resource_aud.employee_id = NEW.employee_id ;
  IF @id_exists = 1 
  THEN SET revision = @revision + 1 ;
  ELSE SET revision = 1 ;
  END IF ;
  INSERT INTO `resource_aud` (
    `employee_id`,
    `action`,
    `REV`,
     `actual_capacity`,
     `award_recognition`,
     `confirmation_date`,
     `contact_number`,
     `contact_number_three`,
     `contact_number_two`,
     `customer_id_detail`,
     date_of_joining,
     `email_id`,
    
     `last_appraisal`,
     `penultimate_appraisal`,
     `planned_capacity`,
    
     `profit_centre`,
     `release_date`,
     resume_file_name,
   
     `transfer_date`,
     `USER_ROLE`,
     `visa_valid`,
     yash_emp_id,
     `bu_id`,
     `current_project_id`,
     `current_reporting_manager`,
     `current_reporting_manager_two`,
     `designation_id`,
     `grade_id`,
     `location_id`,
     `ownership`,
     `payroll_location`,
     `visa_id`,
     `user_name`,
     `first_name`,
     `last_name`,
     `current_bu_id`,
     `timesheet_comment_optional`,
    `created_id`,
    `creation_timestamp`,
    `middle_name`,
    lastupdated_id,
    lastupdated_timestamp
  ) 
  VALUES
    (
     new . employee_id,
      'INSERT',
      `revision`,
 NEW .`actual_capacity`,
    NEW . `award_recognition`,
    NEW . `confirmation_date`,
    NEW . `contact_number`,
    NEW . `contact_number_three`,
   NEW .  `contact_number_two`,
   NEW .  `customer_id_detail`,
   NEW .  date_of_joining,
   NEW .  `email_id`,
 
   NEW .  `last_appraisal`,
    NEW . `penultimate_appraisal`,
    NEW . `planned_capacity`,
    
     NEW .`profit_centre`,
    NEW . `release_date`,
    NEW . resume_file_name,
    
     NEW .`transfer_date`,
    NEW . `USER_ROLE`,
    NEW . `visa_valid`,
    NEW . yash_emp_id,
    NEW . `bu_id`,
   NEW .  `current_project_id`,
   NEW .  `current_reporting_manager`,
    NEW . `current_reporting_manager_two`,
    NEW . `designation_id`,
    NEW . `grade_id`,
    NEW . `location_id`,
    NEW . `ownership`,
    NEW . `payroll_location`,
    NEW . `visa_id`,
   NEW .  `user_name`,
   NEW .  `first_name`,
   NEW .  `last_name`,
   NEW .  `current_bu_id`,
    NEW . `timesheet_comment_optional`,
   NEW . `created_id`,
    NEW .`creation_timestamp`,
    NEW . `middle_name`,
    NEW .lastupdated_id,
   NEW . lastupdated_timestamp
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `resource` */

DELIMITER $$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `resource_trigger_update` AFTER UPDATE ON `resource` FOR EACH ROW BEGIN
  DECLARE `revision` INT ;
  
  SELECT 
    MAX(REV)
     INTO @revision
    
  FROM
    resource_aud 
  WHERE resource_aud.`employee_id` = NEW.employee_id;
  
  IF @revision IS NULL THEN
	SET revision = 1;
 ELSE
	SET revision = @revision + 1 ;
END IF;
  
  
  
  INSERT INTO `resource_aud` (
    `employee_id`,
    `action`,
    `REV`,
     `actual_capacity`,
     `award_recognition`,
     `confirmation_date`,
     `contact_number`,
     `contact_number_three`,
     `contact_number_two`,
     `customer_id_detail`,
     date_of_joining,
     `email_id`,
    
     `last_appraisal`,
     `penultimate_appraisal`,
     `planned_capacity`,
    
     `profit_centre`,
     `release_date`,
     resume_file_name,
   
     `transfer_date`,
     `USER_ROLE`,
     `visa_valid`,
     yash_emp_id,
     `bu_id`,
     `current_project_id`,
     `current_reporting_manager`,
     `current_reporting_manager_two`,
     `designation_id`,
     `grade_id`,
     `location_id`,
     `ownership`,
     `payroll_location`,
     `visa_id`,
     `user_name`,
     `first_name`,
     `last_name`,
     `current_bu_id`,
     `timesheet_comment_optional`,
    `created_id`,
    `creation_timestamp`,
    `middle_name`,
    lastupdated_id,
    lastupdated_timestamp
  ) 
  VALUES
    (
     NEW . employee_id,
      'UPDATE',
      `revision`,
 NEW .`actual_capacity`,
    NEW . `award_recognition`,
    NEW . `confirmation_date`,
    NEW . `contact_number`,
    NEW . `contact_number_three`,
   NEW .  `contact_number_two`,
   NEW .  `customer_id_detail`,
   NEW .  date_of_joining,
   NEW .  `email_id`,
 
   NEW .  `last_appraisal`,
    NEW . `penultimate_appraisal`,
    NEW . `planned_capacity`,
    
     NEW .`profit_centre`,
    NEW . `release_date`,
    NEW . resume_file_name,
    
     NEW .`transfer_date`,
    NEW . `USER_ROLE`,
    NEW . `visa_valid`,
    NEW . yash_emp_id,
    NEW . `bu_id`,
   NEW .  `current_project_id`,
   NEW .  `current_reporting_manager`,
    NEW . `current_reporting_manager_two`,
    NEW . `designation_id`,
    NEW . `grade_id`,
    NEW . `location_id`,
    NEW . `ownership`,
    NEW . `payroll_location`,
    NEW . `visa_id`,
   NEW .  `user_name`,
   NEW .  `first_name`,
   NEW .  `last_name`,
   NEW .  `current_bu_id`,
    NEW . `timesheet_comment_optional`,
   NEW . `created_id`,
    NEW .`creation_timestamp`,
    NEW . `middle_name`,
    NEW .lastupdated_id,
   NEW . lastupdated_timestamp
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `resource` */

DELIMITER $$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `resource_trigger_delete` AFTER DELETE ON `resource` FOR EACH ROW BEGIN
  DECLARE `revision` INT ;
  SELECT 
    COUNT(*) INTO @revision 
  FROM
    `resource_aud` 
  WHERE resource_aud.employee_id = old.employee_id ;
  
  SET revision = @revision + 1 ;
  
  
  
  INSERT INTO `resource_aud` (
    `employee_id`,
    `action`,
    `REV`,
     `actual_capacity`,
     `award_recognition`,
     `confirmation_date`,
     `contact_number`,
     `contact_number_three`,
     `contact_number_two`,
     `customer_id_detail`,
     date_of_joining,
     `email_id`,
    
     `last_appraisal`,
     `penultimate_appraisal`,
     `planned_capacity`,
    
     `profit_centre`,
     `release_date`,
     resume_file_name,
   
     `transfer_date`,
     `USER_ROLE`,
     `visa_valid`,
     yash_emp_id,
     `bu_id`,
     `current_project_id`,
     `current_reporting_manager`,
     `current_reporting_manager_two`,
     `designation_id`,
     `grade_id`,
     `location_id`,
     `ownership`,
     `payroll_location`,
     `visa_id`,
     `user_name`,
     `first_name`,
     `last_name`,
     `current_bu_id`,
     `timesheet_comment_optional`,
    `created_id`,
    `creation_timestamp`,
    `middle_name`,
    lastupdated_id,
    lastupdated_timestamp
  ) 
  VALUES
    (
     OLD . employee_id,
      'DELETE',
      `revision`,
 OLD .`actual_capacity`,
    OLD . `award_recognition`,
    OLD . `confirmation_date`,
    OLD . `contact_number`,
    OLD . `contact_number_three`,
   OLD .  `contact_number_two`,
   OLD .  `customer_id_detail`,
   OLD .  date_of_joining,
   OLD .  `email_id`,
 
   OLD .  `last_appraisal`,
    OLD . `penultimate_appraisal`,
    OLD . `planned_capacity`,
    
     OLD .`profit_centre`,
    OLD . `release_date`,
    OLD . resume_file_name,
    
     OLD .`transfer_date`,
    OLD . `USER_ROLE`,
    OLD . `visa_valid`,
    OLD . yash_emp_id,
    OLD . `bu_id`,
   OLD .  `current_project_id`,
   OLD .  `current_reporting_manager`,
    OLD . `current_reporting_manager_two`,
    OLD . `designation_id`,
    OLD . `grade_id`,
    OLD . `location_id`,
    OLD . `ownership`,
    OLD . `payroll_location`,
    OLD . `visa_id`,
   OLD .  `user_name`,
   OLD .  `first_name`,
   OLD .  `last_name`,
   OLD .  `current_bu_id`,
    OLD . `timesheet_comment_optional`,
   OLD . `created_id`,
    OLD .`creation_timestamp`,
    OLD .`middle_name`,
    OLD .lastupdated_id,
   OLD . lastupdated_timestamp
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `resource_allocation` */

DELIMITER $$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `resource_allocation_trigger_insert` AFTER INSERT ON `resource_allocation` FOR EACH ROW BEGIN
  DECLARE `revision` INT ;
  DECLARE id_exists BOOLEAN ;
  SELECT 
    REV,
    1 INTO @revision,
    id_exists 
  FROM
    resource_allocation_aud 
  WHERE resource_allocation_aud.id = NEW.id ;
  IF @id_exists = 1 
  THEN SET revision = @revision + 1 ;
  ELSE SET revision = 1 ;
  END IF ;
  INSERT INTO `resource_allocation_aud` (
    `id`,
    `action`,
    `REV`,
   `alloc_block`,
   `alloc_end_date`,
   `allocHrs`,
   `alloc_remarks`,
   alloc_start_date,
   `allocation_seq`,
   `billable`,
   `curProj`,
   `allocated_by`,
   `allocation_type_id`,
   `employee_id`,
   `ownership_id`,
   `project_id`,
   `rate_id`,
   `updated_by`,
   `behalf_manager`,
`designation_id`,`grade_id`,`bu_id`,`current_bu_id`,`location_id`,`current_reporting_manager`,`current_reporting_manager_two`,`ownership_transfer_date`,
 
    `created_id`,
    `creation_timestamp`,
    lastupdated_id,
    lastupdated_timestamp
  ) 
  VALUES
    (
      NEW.id,
      'INSERT',
      `revision`,
 NEW .`alloc_block`,
   NEW .`alloc_end_date`,
   NEW . `allocHrs`,
   NEW .`alloc_remarks`,
   NEW . alloc_start_date,
   NEW .`allocation_seq`,
   NEW . `billable`,
   NEW .`curProj`,
   NEW .`allocated_by`,
   NEW .`allocation_type_id`,
   NEW .`employee_id`,
   NEW .`ownership_id`,
   NEW .`project_id`,
   NEW .`rate_id`,
   NEW .`updated_by`,
   NEW .`behalf_manager`,
NEW .`designation_id`,NEW .`grade_id`,NEW .`bu_id`,NEW .`current_bu_id`,NEW .`location_id`,NEW .`current_reporting_manager`,NEW .`current_reporting_manager_two`,NEW .`ownership_transfer_date`,
 
   NEW . `created_id`,
    NEW .`creation_timestamp`,
    NEW .lastupdated_id,
   NEW . lastupdated_timestamp
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `resource_allocation` */

DELIMITER $$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `resource_allocation_trigger_update` AFTER UPDATE ON `resource_allocation` FOR EACH ROW BEGIN
  DECLARE `revision` INT ;
  
  SELECT 
    MAX(REV)
     INTO @revision
    
  FROM
    resource_allocation_aud 
  WHERE resource_allocation_aud.id = NEW.id;
  
  IF @revision IS NULL THEN
	SET revision = 1;
 ELSE
	SET revision = @revision + 1 ;
END IF;
  
  
  
 
  INSERT INTO `resource_allocation_aud` (
    `id`,
    `action`,
    `REV`,
   `alloc_block`,
   `alloc_end_date`,
   `allocHrs`,
   `alloc_remarks`,
   alloc_start_date,
   `allocation_seq`,
   `billable`,
   `curProj`,
   `allocated_by`,
   `allocation_type_id`,
   `employee_id`,
   `ownership_id`,
   `project_id`,
   `rate_id`,
   `updated_by`,
   `behalf_manager`,
`designation_id`,`grade_id`,`bu_id`,`current_bu_id`,`location_id`,`current_reporting_manager`,`current_reporting_manager_two`,`ownership_transfer_date`,
 
    `created_id`,
    `creation_timestamp`,
    lastupdated_id,
    lastupdated_timestamp
  ) 
  VALUES
    (
      NEW.id,
      'UPDATE',
      `revision`,
 NEW .`alloc_block`,
   NEW .`alloc_end_date`,
   NEW . `allocHrs`,
   NEW .`alloc_remarks`,
   NEW . alloc_start_date,
   NEW .`allocation_seq`,
   NEW . `billable`,
   NEW .`curProj`,
   NEW .`allocated_by`,
   NEW .`allocation_type_id`,
   NEW .`employee_id`,
   NEW .`ownership_id`,
   NEW .`project_id`,
   NEW .`rate_id`,
   NEW .`updated_by`,
   NEW .`behalf_manager`,
NEW .`designation_id`,NEW .`grade_id`,NEW .`bu_id`,NEW .`current_bu_id`,NEW .`location_id`,NEW .`current_reporting_manager`,NEW .`current_reporting_manager_two`,NEW .`ownership_transfer_date`,
   NEW . `created_id`,
    NEW .`creation_timestamp`,
    NEW .lastupdated_id,
   NEW . lastupdated_timestamp
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `resource_allocation` */

DELIMITER $$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `resource_allocation_trigger_delete` AFTER DELETE ON `resource_allocation` FOR EACH ROW BEGIN
  DECLARE `revision` INT ;
  SELECT 
    COUNT(*) INTO @revision 
  FROM
    `resource_allocation_aud` 
  WHERE resource_allocation_aud.id = old.id ;
  
  SET revision = @revision + 1 ;
  
  
  
  INSERT INTO `resource_allocation_aud` (
    `id`,
    `action`,
    `REV`,
   `alloc_block`,
   `alloc_end_date`,
   `allocHrs`,
   `alloc_remarks`,
   alloc_start_date,
   `allocation_seq`,
   `billable`,
   `curProj`,
   `allocated_by`,
   `allocation_type_id`,
   `employee_id`,
   `ownership_id`,
   `project_id`,
   `rate_id`,
   `updated_by`,
   `behalf_manager`,
`designation_id`,`grade_id`,`bu_id`,`current_bu_id`,`location_id`,`current_reporting_manager`,`current_reporting_manager_two`,`ownership_transfer_date`,
 
    `created_id`,
    `creation_timestamp`,
    lastupdated_id,
    lastupdated_timestamp
  ) 
  VALUES
    (
      OLD.id,
      'DELETE',
      `revision`,
 OLD .`alloc_block`,
   OLD .`alloc_end_date`,
   OLD . `allocHrs`,
   OLD .`alloc_remarks`,
   OLD . alloc_start_date,
   OLD .`allocation_seq`,
   OLD . `billable`,
   OLD .`curProj`,
   OLD .`allocated_by`,
   OLD .`allocation_type_id`,
   OLD .`employee_id`,
   OLD .`ownership_id`,
   OLD .`project_id`,
   OLD .`rate_id`,
   OLD .`updated_by`,
   OLD .`behalf_manager`,
OLD .`designation_id`,OLD .`grade_id`,OLD .`bu_id`,OLD .`current_bu_id`,OLD .`location_id`,OLD .`current_reporting_manager`,OLD .`current_reporting_manager_two`,OLD .`ownership_transfer_date`,
 
   OLD . `created_id`,
    OLD .`creation_timestamp`,
    OLD .lastupdated_id,
   OLD . lastupdated_timestamp
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `timehrs` */

DELIMITER $$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `timehrs_trigger_insert` AFTER INSERT ON `timehrs` FOR EACH ROW BEGIN
  DECLARE `revision` INT ;
  DECLARE id_exists BOOLEAN ;
  SELECT 
    REV,
    1 INTO @revision,
    id_exists 
  FROM
    timehrs_aud 
  WHERE timehrs_aud.id = NEW.id ;
  IF @id_exists = 1 
  THEN SET revision = @revision + 1 ;
  ELSE SET revision = 1 ;
  END IF ;
  INSERT INTO `timehrs_aud` (
    `id`,
    `action`,
    `REV`,
`week_ending_date`,
    billed_hrs,
    `planned_hrs`,
    `resource_alloc_id`,
    resource_id,
    `created_id`,
    `creation_timestamp`,
    lastupdated_id,
    lastupdated_timestamp
  ) 
  VALUES
    (
     NEW . id,
      'INSERT',
      `revision`,
NEW . 	`week_ending_date`,
  NEW .  billed_hrs,
  NEW .   `planned_hrs`,
   NEW .  `resource_alloc_id`,
  NEW .   resource_id,
   NEW . `created_id`,
    NEW .`creation_timestamp`,
    NEW .lastupdated_id,
   NEW . lastupdated_timestamp
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `timehrs` */

DELIMITER $$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `timehrs_trigger_update` AFTER UPDATE ON `timehrs` FOR EACH ROW BEGIN
  DECLARE `revision` INT ;
  
  SELECT 
    MAX(REV)
     INTO @revision
    
  FROM
    timehrs_aud 
  WHERE timehrs_aud.id = NEW.id;
  
  IF @revision IS NULL THEN
	SET revision = 1;
 ELSE
	SET revision = @revision + 1 ;
END IF;
  
  
  
  
  INSERT INTO `timehrs_aud` (
    `id`,
    `action`,
    `REV`,
	`week_ending_date`,
    billed_hrs,
    `planned_hrs`,
    `resource_alloc_id`,
    resource_id,
    `created_id`,
    `creation_timestamp`,
    lastupdated_id,
    lastupdated_timestamp
  ) 
  VALUES
    (
     NEW . id,
      'UPDATE',
      `revision`,
NEW . `week_ending_date`,
  NEW .  billed_hrs,
  NEW .   `planned_hrs`,
   NEW .  `resource_alloc_id`,
  NEW .   resource_id,
   NEW . `created_id`,
    NEW .`creation_timestamp`,
    NEW .lastupdated_id,
   NEW . lastupdated_timestamp
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `timehrs` */

DELIMITER $$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `timehrs_trigger_delete` AFTER DELETE ON `timehrs` FOR EACH ROW BEGIN
  DECLARE `revision` INT ;
  SELECT 
    COUNT(*) INTO @revision 
  FROM
    `timehrs_aud` 
  WHERE timehrs_aud.id = old.id ;
  
  SET revision = @revision + 1 ;
  
  
  
  INSERT INTO `timehrs_aud` (
    `id`,
    `action`,
    `REV`,
`week_ending_date`,
    billed_hrs,
    `planned_hrs`,
    `resource_alloc_id`,
    resource_id,
    `created_id`,
    `creation_timestamp`,
    lastupdated_id,
    lastupdated_timestamp
  ) 
  VALUES
    (
     OLD . id,
      'DELETE',
      `revision`,
OLD . `week_ending_date`,
  OLD .  billed_hrs,
  OLD .   `planned_hrs`,
   OLD .  `resource_alloc_id`,
  OLD .   resource_id,
   OLD . `created_id`,
    OLD .`creation_timestamp`,
    OLD .lastupdated_id,
   OLD . lastupdated_timestamp
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `user_activity` */

DELIMITER $$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `user_activity_trigger_insert` AFTER INSERT ON `user_activity` FOR EACH ROW BEGIN
  DECLARE `revision` INT ;
  DECLARE id_exists BOOLEAN ;
  SELECT 
    REV,
    1 INTO @revision,
    id_exists 
  FROM
    user_activity_aud 
  WHERE user_activity_aud.id = NEW.id ;
  IF @id_exists = 1 
  THEN SET revision = @revision + 1 ;
  ELSE SET revision = 1 ;
  END IF ;
  INSERT INTO `user_activity_aud` (
    `id`,
    `action`,
    `REV`,
   `module`,
   `activity_id`,
    `custom_activity_id`,
   resource_alloc_id,
   submit_status,
    `created_id`,
    `creation_timestamp`,
    lastupdated_id,
    lastupdated_timestamp
  ) 
  VALUES
    (
     NEW . id,
      'INSERT',
      `revision`,
  NEW . module,
  NEW .   `activity_id`,
  NEW .  `custom_activity_id`,
   NEW .  `res_alloc_id`,
  NEW .   `status`,
   NEW . `created_id`,
    NEW .`creation_timestamp`,
    NEW .lastupdated_id,
   NEW . lastupdated_timestamp
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `user_activity` */

DELIMITER $$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `user_activity_trigger_update` AFTER UPDATE ON `user_activity` FOR EACH ROW BEGIN
  DECLARE `revision` INT ;
  
  SELECT 
    MAX(REV)
     INTO @revision
    
  FROM
    user_activity_aud 
  WHERE user_activity_aud.id = NEW.id;
  -- IF @id_exists = 1 THEN
  IF @revision IS NULL THEN
 SET revision = 1;
 ELSE
 SET revision = @revision + 1 ;
END IF;
  -- ELSE 
  -- SET revision = 1; 
  -- END IF;
  
  INSERT INTO `user_activity_aud` (
    `id`,
    `action`,
    `REV`,
   `module`,
   `activity_id`,
   `custom_activity_id`,
   resource_alloc_id,
   submit_status,
    `created_id`,
    `creation_timestamp`,
    lastupdated_id,
    lastupdated_timestamp
  ) 
  VALUES
    (
     NEW . id,
      'UPDATE',
      `revision`,
  NEW . module,
  NEW .   `activity_id`,
  NEW . `custom_activity_id`,
   NEW .  `res_alloc_id`,
  NEW .   `status`,
   NEW . `created_id`,
    NEW .`creation_timestamp`,
    NEW .lastupdated_id,
   NEW . lastupdated_timestamp
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `user_activity` */

DELIMITER $$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `user_activity_trigger_delete` AFTER DELETE ON `user_activity` FOR EACH ROW BEGIN
  DECLARE `revision` INT ;
  SELECT 
    COUNT(*) INTO @revision 
  FROM
    `user_activity_aud` 
  WHERE user_activity_aud.id = old.id ;
  -- IF @id_exists = 1 THEN
  SET revision = @revision + 1 ;
  -- ELSE 
  -- SET revision = 1; 
  -- END IF;
  INSERT INTO `user_activity_aud` (
    `id`,
    `action`,
    `REV`,
   `module`,
   `activity_id`,
    `custom_activity_id`,
   resource_alloc_id,
   submit_status,
    `created_id`,
    `creation_timestamp`,
    lastupdated_id,
    lastupdated_timestamp
  ) 
  VALUES
    (
     OLD . id,
      'DELETE',
      `revision`,
  OLD . module,
  OLD .   `activity_id`,
 OLD .  `custom_activity_id`,
   OLD .  `res_alloc_id`,
  OLD .   `status`,
   OLD . `created_id`,
    OLD .`creation_timestamp`,
    OLD .lastupdated_id,
   OLD . lastupdated_timestamp
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `user_activity_detail` */

DELIMITER $$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `user_activity_detail_trigger_insert` AFTER INSERT ON `user_activity_detail` FOR EACH ROW BEGIN
DECLARE `revision` INT ;
DECLARE id_exists BOOLEAN ;
  SELECT 
    REV,
    1 INTO @revision,
    id_exists 
  FROM
    user_activity_detail_aud 
  WHERE user_activity_detail_aud.id = NEW.id ;
 IF @id_exists = 1 
  THEN SET revision = @revision + 1 ;
  ELSE SET revision = 1 ;
 END IF ;
INSERT INTO `user_activity_detail_aud` (
    `id`,
    `action`,
    `REV`,
   `time_sheet_id`,
    `d1_hours`,
    `d2_hours`,
    `d3_hours`,
    `d4_hours`,
    `d5_hours`,
    `d6_hours`,
    `d7_hours`,
    `d1_comment`,
    `d2_comment`,
    `d3_comment`,
    `d4_comment`,`d5_comment`,`d6_comment`,`d7_comment`,`week_start_date`,`week_end_date` 
     ) 
  VALUES
    (
      NEW.id,
      'INSERT',
      `revision`,
        NEW . `time_sheet_id`,
    NEW .`d1_hours`,
    NEW .`d2_hours`,
    NEW .`d3_hours`,
    NEW .`d4_hours`,
    NEW .`d5_hours`,
    NEW .`d6_hours`,
    NEW .`d7_hours`,
    NEW .`d1_comment`,
    NEW .`d2_comment`,
    NEW .`d3_comment`,
    NEW .`d4_comment`,
NEW .`d5_comment`,
NEW .`d6_comment`,
NEW .`d7_comment`,
NEW .`week_start_date`,
NEW .`week_end_date`   
         
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `user_activity_detail` */

DELIMITER $$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `user_activity_detail_trigger_update` AFTER UPDATE ON `user_activity_detail` FOR EACH ROW BEGIN
DECLARE `revision` INT ;
  
  SELECT 
    max(REV)
     INTO @revision
   
  FROM
   user_activity_detail_aud
  WHERE user_activity_detail_aud.id = NEW.id;
if @revision IS null then
	set revision = 1;
 Else
	SET revision = @revision + 1 ;
END IF;
  
  
  
  
  
INSERT INTO `user_activity_detail_aud` (
    `id`,
    `action`,
    `REV`,
    `time_sheet_id`,
    `d1_hours`,
    `d2_hours`,
    `d3_hours`,
    `d4_hours`,
    `d5_hours`,
    `d6_hours`,
    `d7_hours`,
    `d1_comment`,
    `d2_comment`,
    `d3_comment`,
    `d4_comment`,`d5_comment`,`d6_comment`,`d7_comment`,`week_start_date`,`week_end_date`
  ) 
  VALUES
    (
      NEW.id,
      'UPDATE',
      `revision`,
      NEW . `time_sheet_id`,
    NEW .`d1_hours`,
    NEW .`d2_hours`,
    NEW .`d3_hours`,
    NEW .`d4_hours`,
    NEW .`d5_hours`,
    NEW .`d6_hours`,
    NEW .`d7_hours`,
    NEW .`d1_comment`,
    NEW .`d2_comment`,
    NEW .`d3_comment`,
    NEW .`d4_comment`,
NEW .`d5_comment`,
NEW .`d6_comment`,
NEW .`d7_comment`,
NEW .`week_start_date`,
NEW .`week_end_date`  
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `user_activity_detail` */

DELIMITER $$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `user_activity_detail_trigger_delete` AFTER DELETE ON `user_activity_detail` FOR EACH ROW BEGIN
DECLARE `revision` INT ;
  SELECT 
    COUNT(*) INTO @revision 
  FROM
    user_activity_detail_aud 
  WHERE user_activity_detail_aud.id = old.id ;
  
  SET revision = @revision + 1 ;
  
  
  
INSERT INTO `user_activity_detail_aud` (
    `id`,
    `action`,
    `REV`,
    `time_sheet_id`,
    `d1_hours`,
    `d2_hours`,
    `d3_hours`,
    `d4_hours`,
    `d5_hours`,
    `d6_hours`,
    `d7_hours`,
    `d1_comment`,
    `d2_comment`,
    `d3_comment`,
    `d4_comment`,`d5_comment`,`d6_comment`,`d7_comment`,`week_start_date`,`week_end_date`
  ) 
  VALUES
    (
      old .id,
      'DELETE',
      `revision`,
         OLD .`time_sheet_id`,
    OLD .`d1_hours`,
    OLD .`d2_hours`,
    OLD .`d3_hours`,
    OLD .`d4_hours`,
    OLD .`d5_hours`,
    OLD .`d6_hours`,
    OLD .`d7_hours`,
    OLD .`d1_comment`,
    OLD .`d2_comment`,
    OLD .`d3_comment`,
    OLD .`d4_comment`,
OLD .`d5_comment`,
OLD .`d6_comment`,
OLD .`d7_comment`,
OLD .`week_start_date`,
OLD .`week_end_date`   
    ) ;
END */$$


DELIMITER ;

/*!50106 set global event_scheduler = 1*/;

/* Event structure for event `event_set_current_project` */

DELIMITER $$

/*!50106 CREATE DEFINER=`root`@`localhost` EVENT `event_set_current_project` ON SCHEDULE EVERY 1 DAY STARTS '2016-06-11 13:44:00' ON COMPLETION NOT PRESERVE ENABLE DO BEGIN
	 CALL `usp_set_current_project`();
	END */$$
DELIMITER ;

/* Event structure for event `event_set_dashboard_data` */

DELIMITER $$

/*!50106 CREATE DEFINER=`root`@`%` EVENT `event_set_dashboard_data` ON SCHEDULE EVERY 1 DAY STARTS '2016-06-11 13:43:00' ON COMPLETION NOT PRESERVE ENABLE DO BEGIN
	 CALL `prepare_project_data`();
	 CALL `prepare_project_data_second`();
	 CALL `prepare_project_widget_data`();
	END */$$
DELIMITER ;

/* Procedure structure for procedure `prepare_project_data` */

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `prepare_project_data`()
BEGIN
DROP TABLE IF EXISTS `dashboard_temp`;
CREATE TABLE dashboard_temp (id INT NOT NULL AUTO_INCREMENT, project_bg_bu VARCHAR(100) DEFAULT NULL, project_id VARCHAR(100) DEFAULT NULL, project_name VARCHAR(100) DEFAULT NULL, billable_resources VARCHAR(100) DEFAULT 0, non_billable_resources VARCHAR(100) DEFAULT 0, PRIMARY KEY (id));
INSERT INTO dashboard_temp (project_bg_bu,project_id,project_name,billable_resources,non_billable_resources) SELECT NAME, id, project_name,
COUNT(CASE WHEN Billable_status= 'Billable' THEN Billable_status END ) AS billable_resources ,
COUNT(CASE WHEN Billable_status= 'OutBound' THEN Billable_status END )
+ COUNT(CASE WHEN Billable_status= 'NonBillable' THEN Billable_status END ) AS non_billable_resources
FROM (SELECT DISTINCT p.project_name, b.name, p.id, CONCAT (re.first_name,' ' ,re.last_name) AS Employee ,
CASE WHEN ra.allocation_type_id IN (2,3) THEN 'Billable' ELSE 'NonBillable'
END AS Billable_status FROM resource re INNER JOIN resource_allocation ra ON re.`employee_id`=ra.`employee_id` 
                                         INNER JOIN project p ON ra.`project_id`=p.id 
                                         INNER JOIN bu b ON b.`id` = p.`bu_id`
                                         WHERE (ra.alloc_end_date >= NOW() OR ra.alloc_end_date IS NULL)) tab GROUP BY project_name ORDER BY project_name;
INSERT INTO dashboard_temp (project_bg_bu,project_id,project_name,billable_resources,non_billable_resources) SELECT b.name,p.id, p.project_name,0,0
FROM project p, bu b WHERE b.`id` = p.`bu_id` AND (project_end_date IS NULL OR project_end_date >= NOW()) AND p.id NOT IN (SELECT p.id FROM resource re INNER JOIN resource_allocation ra ON re.`employee_id`=ra.`employee_id` 
                                         INNER JOIN project p ON ra.`project_id`=p.id 
                                         INNER JOIN bu b ON b.`id` = p.`bu_id`
                                         WHERE (ra.alloc_end_date >= NOW() OR ra.alloc_end_date IS NULL));  
END */$$
DELIMITER ;

/* Procedure structure for procedure `prepare_project_data_second` */

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `prepare_project_data_second`()
BEGIN
DROP TABLE IF EXISTS `dashboard_temp_second`;
CREATE TABLE dashboard_temp_second (id INT NOT NULL AUTO_INCREMENT, project_bg_bu VARCHAR(100) DEFAULT NULL, project_id VARCHAR(100) DEFAULT NULL, project_name VARCHAR(100) DEFAULT NULL, manager_id VARCHAR(100) DEFAULT NULL ,actual_hrs VARCHAR(100) DEFAULT 0, billing_hrs VARCHAR(100) DEFAULT 0, non_billing_hrs VARCHAR(100) DEFAULT 0, PRIMARY KEY (id)) ENGINE=INNODB DEFAULT CHARSET=utf8;
INSERT INTO dashboard_temp_second (project_bg_bu,project_id,project_name,manager_id,actual_hrs,billing_hrs,non_billing_hrs) SELECT b.name,p.id,p.project_name,p.`offshore_del_mgr`, 
SUM(IFNULL(d1_hours,0)+IFNULL(d2_hours,0)+IFNULL(d3_hours,0)+IFNULL(d4_hours,0)+IFNULL(d5_hours,0)+IFNULL(d6_hours,0)+IFNULL(d7_hours,0)) AS actual_hrs, 
SUM(`billed_hrs`) AS billing_hrs,
SUM(CASE WHEN billable <>1 THEN IFNULL(d1_hours,0)+IFNULL(d2_hours,0)+IFNULL(d3_hours,0)+IFNULL(d4_hours,0)+IFNULL(d5_hours,0)+IFNULL(d6_hours,0)+IFNULL(d7_hours,0) END ) AS non_billing_hrs 
FROM `vw_user_activity` ua INNER JOIN `resource_allocation` ra ON ra.id=ua.`resource_alloc_id` 
                                           INNER JOIN project p ON p.id=ra.`project_id` 
                                           INNER JOIN bu b ON b.`id` = p.`bu_id`
                                           INNER JOIN `timehrs` t ON t.`resource_alloc_id` = ra.id AND t.`week_ending_date`=ua.`week_end_date`
                                           WHERE (p.project_end_date IS NULL OR p.project_end_date >= NOW())
                                           GROUP BY p.project_name ORDER BY project_name;
INSERT INTO dashboard_temp_second (project_bg_bu,project_id,project_name,manager_id,actual_hrs,billing_hrs,non_billing_hrs) SELECT b.name,p.id,p.project_name,p.`offshore_del_mgr`,0,0,0 FROM project p, bu b WHERE p.id NOT IN (SELECT p.id
FROM `vw_user_activity` ua INNER JOIN `resource_allocation` ra ON ra.id=ua.`resource_alloc_id` 
                                           INNER JOIN project p ON p.id=ra.`project_id` 
                                           INNER JOIN bu b ON b.`id` = p.`bu_id`
                                           INNER JOIN `timehrs` t ON t.`resource_alloc_id` = ra.id AND t.`week_ending_date`=ua.`week_end_date`) 
                                           AND (project_end_date IS NULL OR project_end_date >= NOW()) AND b.`id` = p.`bu_id` ORDER BY project_name;  
END */$$
DELIMITER ;

/* Procedure structure for procedure `prepare_project_widget_data` */

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` PROCEDURE `prepare_project_widget_data`()
BEGIN
DROP TABLE IF EXISTS dashboard_widgets;
CREATE TABLE dashboard_widgets (id INT NOT NULL AUTO_INCREMENT, employee_id INT(11) DEFAULT NULL, project_id INT(11) DEFAULT NULL, week_end_date DATE DEFAULT NULL, STATUS VARCHAR(100) DEFAULT 0, employee_count INT(100) DEFAULT 0,manager_id INT(11) DEFAULT 0, PRIMARY KEY (id)) ENGINE=INNODB DEFAULT CHARSET=utf8;
INSERT INTO dashboard_widgets(employee_id, project_id, week_end_date, STATUS, employee_count, manager_id) SELECT * FROM((SELECT re.`employee_id`, p.id, week_end_date,STATUS,COUNT(DISTINCT ua.employee_id), p.offshore_del_mgr 
FROM `vw_user_activity` ua 
INNER JOIN resource re ON ua.`employee_id`=re.`employee_id` 
INNER JOIN resource_allocation ra ON ra.employee_id=re.`employee_id` 
INNER JOIN project p ON p.id=ra.`project_id`
AND STATUS='P'
AND week_end_date IN (SELECT week_end_date FROM ((SELECT DISTINCT week_end_date FROM `vw_user_activity` WHERE STATUS='P' ORDER BY week_end_date DESC)tab)) 
GROUP BY  week_end_date,STATUS 
ORDER BY week_end_date DESC)
UNION
(SELECT re.`employee_id`, p.id, week_end_date,STATUS,COUNT(DISTINCT ua.employee_id), p.offshore_del_mgr 
FROM `vw_user_activity` ua 
INNER JOIN resource re ON ua.`employee_id`=re.`employee_id` 
INNER JOIN resource_allocation ra ON ra.employee_id=re.`employee_id` 
INNER JOIN project p ON p.id=ra.`project_id`
AND STATUS='A' 
AND week_end_date IN (SELECT week_end_date FROM ((SELECT DISTINCT week_end_date FROM `vw_user_activity` WHERE STATUS='A' ORDER BY week_end_date DESC)tab)) 
GROUP BY  week_end_date,STATUS 
ORDER BY week_end_date DESC)
UNION
(SELECT re.`employee_id`, p.id, week_end_date,STATUS,COUNT(DISTINCT ua.employee_id), p.offshore_del_mgr 
FROM `vw_user_activity` ua 
INNER JOIN resource re ON ua.`employee_id`=re.`employee_id` 
INNER JOIN resource_allocation ra ON ra.employee_id=re.`employee_id` 
INNER JOIN project p ON p.id=ra.`project_id`
AND STATUS='N' 
AND week_end_date IN (SELECT week_end_date FROM ((SELECT DISTINCT week_end_date FROM `vw_user_activity` WHERE STATUS='N' ORDER BY week_end_date DESC)tab)) 
GROUP BY  week_end_date,STATUS 
ORDER BY week_end_date DESC)
UNION
(SELECT re.`employee_id`, p.id, week_end_date,STATUS,COUNT(DISTINCT ua.employee_id), p.offshore_del_mgr 
FROM `vw_user_activity` ua 
INNER JOIN resource re ON ua.`employee_id`=re.`employee_id` 
INNER JOIN resource_allocation ra ON ra.employee_id=re.`employee_id` 
INNER JOIN project p ON p.id=ra.`project_id`
AND STATUS='R' 
AND week_end_date IN (SELECT week_end_date FROM ((SELECT DISTINCT week_end_date FROM `vw_user_activity` WHERE STATUS='R' ORDER BY week_end_date DESC)tab)) 
GROUP BY  week_end_date,STATUS 
ORDER BY week_end_date DESC))tabg; 
END */$$
DELIMITER ;

/* Procedure structure for procedure `usp_rpt_Employee_Planed_Actual_Bill_hrs` */

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_rpt_Employee_Planed_Actual_Bill_hrs`()
BEGIN
SELECT DISTINCT resource_yash_emp_id,employee_id,project_name,week_end_date 
,designation_name,grade,contact_number,email_id,profit_centre,release_date,location,BU,
planned_hrs,billed_hrs,
project_end_date,
 (d1+d2+d3+d4+d5+d6+d7) AS actual_hrs
FROM (
  
SELECT user_activity.employee_id,week_end_date,project.id,project.project_name,project.project_end_date,
CONCAT('(',resource.`first_name`,resource.`last_name`,') ',resource.`yash_emp_id`) AS resource_yash_emp_id,
designations.designation_name,
grade.grade,resource.contact_number,resource.email_id,resource.profit_centre,resource.release_date,L.location,bu.name AS BU,
SUM(IFNULL(d1_hours,0)) AS d1, 
SUM(IFNULL(d2_hours,0)) AS  d2 ,
SUM(IFNULL(d3_hours,0)) AS  d3 ,
SUM(IFNULL(d4_hours,0)) AS  d4 ,
SUM(IFNULL(d5_hours,0)) AS  d5,
SUM(IFNULL(d6_hours,0)) AS  d6,
SUM(IFNULL(d7_hours,0)) AS  d7
 FROM 
 
     `resource_allocation` resource_allocation 
    
     INNER JOIN `resource` resource ON resource_allocation.`employee_id` = resource.`employee_id`
INNER JOIN bu ON resource.bu_id=bu.id
     INNER JOIN location L ON resource.location_id=L.id
     INNER JOIN grade ON resource.grade_id=grade.id
     INNER JOIN `user_activity` user_activity ON resource_allocation.`id` = user_activity.`resource_alloc_id`
     INNER JOIN  `activity` ON user_activity.activity_id=activity.id
     INNER JOIN `project` project ON resource.`current_project_id` = project.`id`
     INNER JOIN `designations` designations ON resource.`designation_id` = designations.`id`
    
 GROUP BY user_activity.employee_id,project.id,user_activity.week_end_date) 
 tab 
LEFT JOIN timehrs th ON th.resource_id= employee_id
  AND th.week_ending_date=week_end_date;
  
END */$$
DELIMITER ;

/* Procedure structure for procedure `usp_rpt_WeeklyTimeSheet` */

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_rpt_WeeklyTimeSheet`()
BEGIN 
SELECT resource_yash_emp_id,employee_id,activity_id,activity_name,project_name,week_start_date,week_end_date,weekdate,user_activity_hours,user_activity_comments,module FROM (
(SELECT DISTINCT CONCAT('(',resource.`first_name`,resource.`last_name`,') ',resource.`yash_emp_id`) AS resource_yash_emp_id,user_activity.employee_id,
0 AS activity_id ,
'' AS activity_name ,
project_name,
week_start_date,
week_end_date,
DATE_ADD(user_activity.`week_start_date`,INTERVAL 0 DAY ) weekdate,
IFNULL(d1_hours,0) AS user_activity_hours,
d1_comment AS user_activity_comments,'' AS module
 FROM
     `resource_allocation` resource_allocation 
     INNER JOIN `resource` resource ON resource_allocation.`employee_id` = resource.`employee_id`
     INNER JOIN `user_activity` user_activity ON resource_allocation.`id` = user_activity.`resource_alloc_id`
     INNER JOIN  `activity` ON user_activity.activity_id=activity.id
     INNER JOIN `project` project ON resource.`current_project_id` = project.`id`
     INNER JOIN `designations` designations ON resource.`designation_id` = designations.`id`
)
   
UNION
 
 (SELECT CONCAT('(',resource.`first_name`,resource.`last_name`,') ',resource.`yash_emp_id`) AS resource_yash_emp_id,user_activity.employee_id,
     user_activity.activity_id,
activity_name,
project_name,
week_start_date,
week_end_date,
DATE_ADD(user_activity.`week_start_date`,INTERVAL 1 DAY ) weekdate,
IFNULL(d2_hours,0) AS user_activity_hours,
d2_comment AS user_activity_comments,module
 FROM
     `resource_allocation` resource_allocation 
     INNER JOIN `resource` resource ON resource_allocation.`employee_id` = resource.`employee_id`
     INNER JOIN `user_activity` user_activity ON resource_allocation.`id` = user_activity.`resource_alloc_id`
     INNER JOIN  `activity` ON user_activity.activity_id=activity.id
     INNER JOIN `project` project ON resource.`current_project_id` = project.`id`
     INNER JOIN `designations` designations ON resource.`designation_id` = designations.`id`
WHERE 
 d2_hours IS NOT NULL
)
UNION 
(SELECT CONCAT('(',resource.`first_name`,resource.`last_name`,') ',resource.`yash_emp_id`) AS resource_yash_emp_id,user_activity.employee_id,
user_activity.activity_id,
activity_name,
project_name,
week_start_date,
week_end_date,
DATE_ADD(user_activity.`week_start_date`,INTERVAL 2 DAY ) weekdate,
IFNULL(d3_hours,0) AS user_activity_hours,
d3_comment AS user_activity_comments,module
 FROM
     `resource_allocation` resource_allocation 
     INNER JOIN `resource` resource ON resource_allocation.`employee_id` = resource.`employee_id`
     INNER JOIN `user_activity` user_activity ON resource_allocation.`id` = user_activity.`resource_alloc_id`
     INNER JOIN  `activity` ON user_activity.activity_id=activity.id
     INNER JOIN `project` project ON resource.`current_project_id` = project.`id`
     INNER JOIN `designations` designations ON resource.`designation_id` = designations.`id`
WHERE 
 d3_hours IS NOT NULL
)
     
    UNION
     
     
     (SELECT CONCAT('(',resource.`first_name`,resource.`last_name`,') ',resource.`yash_emp_id`) AS resource_yash_emp_id,user_activity.employee_id,
user_activity.activity_id,
activity_name,
project_name,
week_start_date,
week_end_date,
DATE_ADD(user_activity.`week_start_date`,INTERVAL 3 DAY ) weekdate,
IFNULL(d4_hours,0) AS user_activity_hours,
d4_comment AS user_activity_comments,module
 FROM
     `resource_allocation` resource_allocation 
     INNER JOIN `resource` resource ON resource_allocation.`employee_id` = resource.`employee_id`
     INNER JOIN `user_activity` user_activity ON resource_allocation.`id` = user_activity.`resource_alloc_id`
     INNER JOIN  `activity` ON user_activity.activity_id=activity.id
     INNER JOIN `project` project ON resource.`current_project_id` = project.`id`
     INNER JOIN `designations` designations ON resource.`designation_id` = designations.`id`
WHERE 
 d4_hours IS NOT NULL
)
     
   
     
    UNION
     
     
     
     (SELECT CONCAT('(',resource.`first_name`,resource.`last_name`,') ',resource.`yash_emp_id`) AS resource_yash_emp_id,user_activity.employee_id,
user_activity.activity_id,
activity_name,
project_name,
week_start_date,
week_end_date,
DATE_ADD(user_activity.`week_start_date`,INTERVAL 4 DAY ) weekdate,
IFNULL(d5_hours,0) AS user_activity_hours,
d5_comment AS user_activity_comments,module
 FROM
     `resource_allocation` resource_allocation 
     INNER JOIN `resource` resource ON resource_allocation.`employee_id` = resource.`employee_id`
     INNER JOIN `user_activity` user_activity ON resource_allocation.`id` = user_activity.`resource_alloc_id`
     INNER JOIN  `activity` ON user_activity.activity_id=activity.id
     INNER JOIN `project` project ON resource.`current_project_id` = project.`id`
     INNER JOIN `designations` designations ON resource.`designation_id` = designations.`id`
WHERE 
 d5_hours IS NOT NULL
)
     
    UNION
     
     
     
     (SELECT CONCAT('(',resource.`first_name`,resource.`last_name`,') ',resource.`yash_emp_id`) AS resource_yash_emp_id,user_activity.employee_id,
user_activity.activity_id,
activity_name,
project_name,
week_start_date,
week_end_date,
DATE_ADD(user_activity.`week_start_date`,INTERVAL 5 DAY ) weekdate,
IFNULL(d6_hours,0) AS user_activity_hours,
d6_comment AS user_activity_comments,module
 FROM
     `resource_allocation` resource_allocation 
     INNER JOIN `resource` resource ON resource_allocation.`employee_id` = resource.`employee_id`
     INNER JOIN `user_activity` user_activity ON resource_allocation.`id` = user_activity.`resource_alloc_id`
     INNER JOIN  `activity` ON user_activity.activity_id=activity.id
     INNER JOIN `project` project ON resource.`current_project_id` = project.`id`
     INNER JOIN `designations` designations ON resource.`designation_id` = designations.`id`
WHERE 
 d6_hours IS NOT NULL
)
     
    UNION
     
     
     
     (SELECT CONCAT('(',resource.`first_name`,resource.`last_name`,') ',resource.`yash_emp_id`) AS resource_yash_emp_id,user_activity.employee_id,
user_activity.activity_id,
activity_name,
project_name,
week_start_date,
week_end_date,
DATE_ADD(user_activity.`week_start_date`,INTERVAL 6 DAY ) weekdate,
IFNULL(d7_hours,0) AS user_activity_hours,
d7_comment AS user_activity_comments,module
 FROM
     `resource_allocation` resource_allocation 
     INNER JOIN `resource` resource ON resource_allocation.`employee_id` = resource.`employee_id`
     INNER JOIN `user_activity` user_activity ON resource_allocation.`id` = user_activity.`resource_alloc_id`
     INNER JOIN  `activity` ON user_activity.activity_id=activity.id
     INNER JOIN `project` project ON resource.`current_project_id` = project.`id`
     INNER JOIN `designations` designations ON resource.`designation_id` = designations.`id`
WHERE 
 d7_hours IS NOT NULL
)) tab
 
ORDER BY project_name,
     resource_yash_emp_id,week_end_date ASC;
END */$$
DELIMITER ;

/* Procedure structure for procedure `usp_set_current_project` */

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` PROCEDURE `usp_set_current_project`()
BEGIN 
SET @EndDate=CURDATE();
 DROP TEMPORARY TABLE IF EXISTS AllocationID_CurrentProject_PassedEndDate;
CREATE TEMPORARY TABLE AllocationID_CurrentProject_PassedEndDate
SELECT    ra.id allocation_id,ra.employee_id
FROM  resource_allocation ra,resource r,bu 
WHERE r.employee_id=ra.employee_id
AND IFNULL(curProj,0)=1  AND  IFNULL(ra.alloc_end_date ,@EndDate)  < @EndDate
AND bu.id=r.bu_id ;
UPDATE resource_allocation
SET curProj=0
WHERE id IN
(
SELECT allocation_id FROM AllocationID_CurrentProject_PassedEndDate
);
UPDATE resource
SET current_project_id=NULL
WHERE employee_id IN
(
SELECT employee_id FROM AllocationID_CurrentProject_PassedEndDate
);
DROP TEMPORARY TABLE IF EXISTS Tbl_CurrentProjectNotSet; 
DROP TEMPORARY TABLE IF EXISTS Tbl_SetCurrentProject; 
CREATE TEMPORARY TABLE Tbl_CurrentProjectNotSet
(employee_id INT(11));
CREATE TEMPORARY TABLE Tbl_SetCurrentProject
(employee_id INT(11),
allocation_id INT(11)
);
INSERT INTO Tbl_CurrentProjectNotSet(employee_id)
SELECT   DISTINCT r.employee_id 
FROM 
 resource_allocation ra,resource r,bu 
WHERE r.employee_id=ra.employee_id
 AND @EndDate BETWEEN  ra.alloc_start_date AND  IFNULL(ra.alloc_end_date ,@EndDate)  
AND bu.id=r.bu_id 
AND release_date IS  NULL
AND NOT EXISTS (SELECT 1 FROM resource_allocation raI,resource rI 
				  WHERE 
					rI.employee_id=raI.employee_id
					AND @EndDate BETWEEN  raI.alloc_start_date AND  IFNULL(raI.alloc_end_date ,@EndDate)  
					AND raI.employee_id=ra.employee_id AND IFNULL(curProj,0)=1
					AND rI.release_date IS  NULL
					
		              
			);
INSERT INTO Tbl_SetCurrentProject(employee_id ,allocation_id)
SELECT employee_id,
(SELECT ra.id  AS allocation_id 
FROM resource_allocation ra
INNER JOIN `allocationtype` a
ON ra.`allocation_type_id`=a.`id`
WHERE employee_id=tcp.employee_id
AND @EndDate BETWEEN  ra.alloc_start_date AND  IFNULL(ra.alloc_end_date ,@EndDate)  
ORDER BY priority ASC ,alloc_start_date DESC
LIMIT 1
) allocation_id
FROM Tbl_CurrentProjectNotSet tcp;
UPDATE resource_allocation,Tbl_SetCurrentProject
SET CurProj=1
WHERE resource_allocation.Id=Tbl_SetCurrentProject.allocation_id
AND resource_allocation.`employee_id`=Tbl_SetCurrentProject.employee_id;
UPDATE resource ,resource_allocation,Tbl_SetCurrentProject
SET current_project_id=project_id
WHERE resource_allocation.Id=Tbl_SetCurrentProject.allocation_id
AND resource_allocation.`employee_id`=Tbl_SetCurrentProject.employee_id
AND  resource_allocation.`employee_id`= resource.`employee_id`
AND Tbl_SetCurrentProject.employee_id=resource.`employee_id`;
END */$$
DELIMITER ;

/* Procedure structure for procedure `WeeklyTimeSheet` */

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `WeeklyTimeSheet`()
BEGIN 
SELECT resource_yash_emp_id,employee_id,activity_id,activity_name,project_name,week_start_date,week_end_date,weekdate,user_activity_hours,user_activity_comments,module,current_project_id FROM (
(SELECT DISTINCT CONCAT('(',resource.`first_name`,resource.`last_name`,') ',resource.`yash_emp_id`) AS resource_yash_emp_id,user_activity.employee_id,
0 AS activity_id ,
'' AS activity_name ,
project_name,
week_start_date,
week_end_date,
resource.`current_project_id`,
DATE_ADD(user_activity.`week_start_date`,INTERVAL 0 DAY ) weekdate,
IFNULL(d1_hours,0) AS user_activity_hours,
d1_comment AS user_activity_comments,'' AS module
 FROM
     `resource_allocation` resource_allocation 
     INNER JOIN `resource` resource ON resource_allocation.`employee_id` = resource.`employee_id`
     INNER JOIN `user_activity` user_activity ON resource_allocation.`id` = user_activity.`resource_alloc_id`
     INNER JOIN  `activity` ON user_activity.activity_id=activity.id
      INNER JOIN `project` project ON resource.`current_project_id` = project.`id` AND resource_allocation.project_id = project.id
     INNER JOIN `designations` designations ON resource.`designation_id` = designations.`id`
WHERE
YEAR(week_end_date) =2013
AND MONTH(week_end_date) =5 AND DAY(week_end_date)=25
)
   
UNION
 
 (SELECT CONCAT('(',resource.`first_name`,resource.`last_name`,') ',resource.`yash_emp_id`) AS resource_yash_emp_id,user_activity.employee_id,
     user_activity.activity_id,
activity_name,
project_name,
week_start_date,
week_end_date,
resource.`current_project_id`,
DATE_ADD(user_activity.`week_start_date`,INTERVAL 1 DAY ) weekdate,
IFNULL(d2_hours,0) AS user_activity_hours,
d2_comment AS user_activity_comments,module
 FROM
     `resource_allocation` resource_allocation 
     INNER JOIN `resource` resource ON resource_allocation.`employee_id` = resource.`employee_id`
     INNER JOIN `user_activity` user_activity ON resource_allocation.`id` = user_activity.`resource_alloc_id`
     INNER JOIN  `activity` ON user_activity.activity_id=activity.id
      INNER JOIN `project` project ON resource.`current_project_id` = project.`id` AND resource_allocation.project_id = project.id
     INNER JOIN `designations` designations ON resource.`designation_id` = designations.`id`
WHERE 
YEAR(week_end_date) =2013
AND MONTH(week_end_date) =5 AND DAY(week_end_date)=25 AND
 d2_hours IS NOT NULL
)
UNION 
(SELECT CONCAT('(',resource.`first_name`,resource.`last_name`,') ',resource.`yash_emp_id`) AS resource_yash_emp_id,user_activity.employee_id,
user_activity.activity_id,
activity_name,
project_name,
week_start_date,
week_end_date,
resource.`current_project_id`,
DATE_ADD(user_activity.`week_start_date`,INTERVAL 2 DAY ) weekdate,
IFNULL(d3_hours,0) AS user_activity_hours,
d3_comment AS user_activity_comments,module
 FROM
     `resource_allocation` resource_allocation 
     INNER JOIN `resource` resource ON resource_allocation.`employee_id` = resource.`employee_id`
     INNER JOIN `user_activity` user_activity ON resource_allocation.`id` = user_activity.`resource_alloc_id`
     INNER JOIN  `activity` ON user_activity.activity_id=activity.id
     INNER JOIN `project` project ON resource.`current_project_id` = project.`id` AND resource_allocation.project_id = project.id
     INNER JOIN `designations` designations ON resource.`designation_id` = designations.`id`
WHERE 
YEAR(week_end_date) =2013
AND MONTH(week_end_date) =5 AND DAY(week_end_date)=25 AND
 d3_hours IS NOT NULL
)
     
    UNION
     
     
     (SELECT CONCAT('(',resource.`first_name`,resource.`last_name`,') ',resource.`yash_emp_id`) AS resource_yash_emp_id,user_activity.employee_id,
user_activity.activity_id,
activity_name,
project_name,
week_start_date,
week_end_date,
resource.`current_project_id`,
DATE_ADD(user_activity.`week_start_date`,INTERVAL 3 DAY ) weekdate,
IFNULL(d4_hours,0) AS user_activity_hours,
d4_comment AS user_activity_comments,module
 FROM
     `resource_allocation` resource_allocation 
     INNER JOIN `resource` resource ON resource_allocation.`employee_id` = resource.`employee_id`
     INNER JOIN `user_activity` user_activity ON resource_allocation.`id` = user_activity.`resource_alloc_id`
     INNER JOIN  `activity` ON user_activity.activity_id=activity.id
      INNER JOIN `project` project ON resource.`current_project_id` = project.`id` AND resource_allocation.project_id = project.id
     INNER JOIN `designations` designations ON resource.`designation_id` = designations.`id`
WHERE 
YEAR(week_end_date) =2013
AND MONTH(week_end_date) =5 AND DAY(week_end_date)=25
 AND d4_hours IS NOT NULL
)
     
   
     
    UNION
     
     
     
     (SELECT CONCAT('(',resource.`first_name`,resource.`last_name`,') ',resource.`yash_emp_id`) AS resource_yash_emp_id,user_activity.employee_id,
user_activity.activity_id,
activity_name,
project_name,
week_start_date,
week_end_date,
resource.`current_project_id`,
DATE_ADD(user_activity.`week_start_date`,INTERVAL 4 DAY ) weekdate,
IFNULL(d5_hours,0) AS user_activity_hours,
d5_comment AS user_activity_comments,module
 FROM
     `resource_allocation` resource_allocation 
     INNER JOIN `resource` resource ON resource_allocation.`employee_id` = resource.`employee_id`
     INNER JOIN `user_activity` user_activity ON resource_allocation.`id` = user_activity.`resource_alloc_id`
     INNER JOIN  `activity` ON user_activity.activity_id=activity.id
      INNER JOIN `project` project ON resource.`current_project_id` = project.`id` AND resource_allocation.project_id = project.id
     INNER JOIN `designations` designations ON resource.`designation_id` = designations.`id`
WHERE 
YEAR(week_end_date) =2013
AND MONTH(week_end_date) =5 AND DAY(week_end_date)=25 AND
 d5_hours IS NOT NULL
)
     
    UNION
     
     
     
     (SELECT CONCAT('(',resource.`first_name`,resource.`last_name`,') ',resource.`yash_emp_id`) AS resource_yash_emp_id,user_activity.employee_id,
user_activity.activity_id,
activity_name,
project_name,
week_start_date,
week_end_date,
resource.`current_project_id`,
DATE_ADD(user_activity.`week_start_date`,INTERVAL 5 DAY ) weekdate,
IFNULL(d6_hours,0) AS user_activity_hours,
d6_comment AS user_activity_comments,module
 FROM
     `resource_allocation` resource_allocation 
     INNER JOIN `resource` resource ON resource_allocation.`employee_id` = resource.`employee_id`
     INNER JOIN `user_activity` user_activity ON resource_allocation.`id` = user_activity.`resource_alloc_id`
     INNER JOIN  `activity` ON user_activity.activity_id=activity.id
      INNER JOIN `project` project ON resource.`current_project_id` = project.`id` AND resource_allocation.project_id = project.id
     INNER JOIN `designations` designations ON resource.`designation_id` = designations.`id`
WHERE 
YEAR(week_end_date) =2013
AND MONTH(week_end_date) =5 AND DAY(week_end_date)=25 AND
 d6_hours IS NOT NULL
)
     
    UNION
     
     
     
     (SELECT CONCAT('(',resource.`first_name`,resource.`last_name`,') ',resource.`yash_emp_id`) AS resource_yash_emp_id,user_activity.employee_id,
user_activity.activity_id,
activity_name,
project_name,
week_start_date,
week_end_date,
resource.`current_project_id`,
DATE_ADD(user_activity.`week_start_date`,INTERVAL 6 DAY ) weekdate,
IFNULL(d7_hours,0) AS user_activity_hours,
d7_comment AS user_activity_comments,module
 FROM
     `resource_allocation` resource_allocation 
     INNER JOIN `resource` resource ON resource_allocation.`employee_id` = resource.`employee_id`
     INNER JOIN `user_activity` user_activity ON resource_allocation.`id` = user_activity.`resource_alloc_id`
     INNER JOIN  `activity` ON user_activity.activity_id=activity.id
      INNER JOIN `project` project ON resource.`current_project_id` = project.`id` AND resource_allocation.project_id = project.id
     INNER JOIN `designations` designations ON resource.`designation_id` = designations.`id`
WHERE 
YEAR(week_end_date) =2013
AND MONTH(week_end_date) =5 AND DAY(week_end_date)=25 AND
 d7_hours IS NOT NULL
)) tab
 
 WHERE current_project_id IN (19,11,3,55)
ORDER BY project_name,
     resource_yash_emp_id,week_end_date ASC;
END */$$
DELIMITER ;

/*Table structure for table `bu` */

DROP TABLE IF EXISTS `bu`;

/*!50001 CREATE TABLE  `bu`(
 `id` int(11) ,
 `name` text ,
 `CODE` text ,
 `BgName` varchar(256) ,
 `BuName` varchar(256) 
)*/;

/*Table structure for table `rephrs_weekenddate` */

DROP TABLE IF EXISTS `rephrs_weekenddate`;

/*!50001 CREATE TABLE  `rephrs_weekenddate`(
 `employee_id` int(11) ,
 `week_end_date` date ,
 `resource_alloc_id` int(11) ,
 `repHrsPro` double(19,2) 
)*/;

/*Table structure for table `resource_allocation_project` */

DROP TABLE IF EXISTS `resource_allocation_project`;

/*!50001 CREATE TABLE  `resource_allocation_project`(
 `id` int(11) ,
 `project_name` varchar(256) 
)*/;

/*Table structure for table `timehrs_admin_resource_data` */

DROP TABLE IF EXISTS `timehrs_admin_resource_data`;

/*!50001 CREATE TABLE  `timehrs_admin_resource_data`(
 `employee_id` int(11) ,
 `yash_emp_id` varchar(256) ,
 `full_name` text ,
 `designation_name` varchar(256) ,
 `grade` varchar(10) ,
 `planned_capacity` int(11) ,
 `actual_capacity` int(11) ,
 `date_of_joining` date ,
 `current_project` varchar(256) 
)*/;

/*Table structure for table `timehrs_admin_view` */

DROP TABLE IF EXISTS `timehrs_admin_view`;

/*!50001 CREATE TABLE  `timehrs_admin_view`(
 `employee_id` int(11) ,
 `yash_emp_id` varchar(256) ,
 `full_name` text ,
 `designation_name` varchar(256) ,
 `grade` varchar(10) ,
 `planned_capacity` int(11) ,
 `actual_capacity` int(11) ,
 `planned_hrs_sum` int(1) ,
 `rephrs` int(1) ,
 `billed_hrs_sum` int(1) ,
 `date_of_joining` date ,
 `current_project` varchar(256) 
)*/;

/*Table structure for table `timehrs_employeeid_projectname` */

DROP TABLE IF EXISTS `timehrs_employeeid_projectname`;

/*!50001 CREATE TABLE  `timehrs_employeeid_projectname`(
 `employee_id` int(11) ,
 `project_name` varchar(256) ,
 `alloc_end_date` date 
)*/;

/*Table structure for table `timehrs_total_billhrs_planhrs` */

DROP TABLE IF EXISTS `timehrs_total_billhrs_planhrs`;

/*!50001 CREATE TABLE  `timehrs_total_billhrs_planhrs`(
 `resource_id` int(11) ,
 `billed_hrs_sum` double(19,2) ,
 `planned_hrs_sum` double(19,2) 
)*/;

/*Table structure for table `timehrs_totalrephrs` */

DROP TABLE IF EXISTS `timehrs_totalrephrs`;

/*!50001 CREATE TABLE  `timehrs_totalrephrs`(
 `employee_id` int(11) ,
 `rephrs` double(19,2) 
)*/;

/*Table structure for table `user_activity_view` */

DROP TABLE IF EXISTS `user_activity_view`;

/*!50001 CREATE TABLE  `user_activity_view`(
 `employee_id` int(11) ,
 `week_start_date` date ,
 `week_end_date` date ,
 `resource_alloc_id` int(11) ,
 `approve_status` char(1) ,
 `project_name` varchar(256) ,
 `repHrsPro` double(19,2) 
)*/;

/*Table structure for table `vw_get_first_user_activity` */

DROP TABLE IF EXISTS `vw_get_first_user_activity`;

/*!50001 CREATE TABLE  `vw_get_first_user_activity`(
 `first_activity_date` date ,
 `resource_alloc_id` int(11) 
)*/;

/*Table structure for table `vw_get_last_user_activity` */

DROP TABLE IF EXISTS `vw_get_last_user_activity`;

/*!50001 CREATE TABLE  `vw_get_last_user_activity`(
 `last_activity_date` date ,
 `resource_alloc_id` int(11) 
)*/;

/*Table structure for table `vw_get_max_user_activity_date` */

DROP TABLE IF EXISTS `vw_get_max_user_activity_date`;

/*!50001 CREATE TABLE  `vw_get_max_user_activity_date`(
 `week_end_date` date ,
 `res_alloc_id` int(11) 
)*/;

/*Table structure for table `vw_get_min_max_user_acitivity_for_resource` */

DROP TABLE IF EXISTS `vw_get_min_max_user_acitivity_for_resource`;

/*!50001 CREATE TABLE  `vw_get_min_max_user_acitivity_for_resource`(
 `resource_alloc_id` int(11) ,
 `first_activity_date` date ,
 `last_activity_date` date 
)*/;

/*Table structure for table `vw_get_min_user_activity_date` */

DROP TABLE IF EXISTS `vw_get_min_user_activity_date`;

/*!50001 CREATE TABLE  `vw_get_min_user_activity_date`(
 `id` bigint(20) ,
 `week_start_date` date ,
 `res_alloc_id` int(11) 
)*/;

/*Table structure for table `vw_resource_alloc_activity_details` */

DROP TABLE IF EXISTS `vw_resource_alloc_activity_details`;

/*!50001 CREATE TABLE  `vw_resource_alloc_activity_details`(
 `project_id` int(11) ,
 `resource_alloc_id` int(11) ,
 `first_activity_date` date ,
 `last_activity_date` date 
)*/;

/*Table structure for table `vw_rm_report` */

DROP TABLE IF EXISTS `vw_rm_report`;

/*!50001 CREATE TABLE  `vw_rm_report`(
 `Yash_Emp_Id` varchar(256) ,
 `Employee_Name` text ,
 `Emp_Email_ID` varchar(256) ,
 `Designation` varchar(256) ,
 `Grade` varchar(10) ,
 `Date_Of_Joining` date ,
 `Release_Date` date ,
 `Base_Location` varchar(100) ,
 `Current_Location` varchar(100) ,
 `Parent_Bu` text ,
 `Currnet_Bu` text ,
 `Ownership` varchar(256) ,
 `Current_RM1` text ,
 `Current_RM2` text ,
 `Primary_Project` varchar(256) ,
 `Current_Project_Flag` tinyint(1) ,
 `Customer_Name` varchar(256) ,
 `Project_Bu` text ,
 `Allocation_Start_Date` date ,
 `Allocation_Type` varchar(256) ,
 `Transfer_Date` date ,
 `Visa` varchar(256) ,
 `Primary_skill` varchar(256) ,
 `Secondary_skill` varchar(256) ,
 `Customer_Id` varchar(256) ,
 `LastUpdated_ID` varchar(256) ,
 `Last_TimeStamp` timestamp ,
 `Bu_Id` int(11) ,
 `Current_Bu_Id` int(11) ,
 `Current_Project_Id` int(11) ,
 `Location_id` int(11) ,
 `Project_ID` int(11) ,
 `Project_Bu_ID` int(11) ,
 `Resource_Allocation_End_Date` date ,
 `Allocation_Type_Id` int(11) ,
 `Employee_Id` int(11) ,
 `Allocation_Priority` int(11) ,
 `RES_EMP_ID` int(11) ,
 `ALLOC_ID` int(11) 
)*/;

/*Table structure for table `vw_rm_support` */

DROP TABLE IF EXISTS `vw_rm_support`;

/*!50001 CREATE TABLE  `vw_rm_support`(
 `employee_id` int(11) ,
 `id` int(11) ,
 `Allocation_Type_Id` int(11) ,
 `curProj` tinyint(1) ,
 `Priority` int(11) 
)*/;

/*Table structure for table `vw_skill_ratings` */

DROP TABLE IF EXISTS `vw_skill_ratings`;

/*!50001 CREATE TABLE  `vw_skill_ratings`(
 `employee_id` int(11) ,
 `Primary_skill` text ,
 `Secondary_skill` text 
)*/;

/*Table structure for table `vw_skill_ratings_primaryskills` */

DROP TABLE IF EXISTS `vw_skill_ratings_primaryskills`;

/*!50001 CREATE TABLE  `vw_skill_ratings_primaryskills`(
 `resource_id` int(11) ,
 `SkillType` varchar(7) ,
 `Primary_skill` text 
)*/;

/*Table structure for table `vw_skill_ratings_secondaryskills` */

DROP TABLE IF EXISTS `vw_skill_ratings_secondaryskills`;

/*!50001 CREATE TABLE  `vw_skill_ratings_secondaryskills`(
 `resource_id` int(11) ,
 `SkillType` varchar(9) ,
 `Secondary_skill` text 
)*/;

/*Table structure for table `vw_user_activity` */

DROP TABLE IF EXISTS `vw_user_activity`;

/*!50001 CREATE TABLE  `vw_user_activity`(
 `ID` bigint(20) ,
 `employee_id` int(11) ,
 `ACTIVITY_ID` int(11) ,
 `MODULE` varchar(250) ,
 `resource_alloc_id` int(11) ,
 `D1_hours` double(10,2) ,
 `D2_hours` double(10,2) ,
 `D3_hours` double(10,2) ,
 `D4_hours` double(10,2) ,
 `D5_hours` double(10,2) ,
 `D6_hours` double(10,2) ,
 `D7_hours` double(10,2) ,
 `D1_comment` varchar(1000) ,
 `D2_comment` varchar(1000) ,
 `D3_comment` varchar(1000) ,
 `D4_comment` varchar(1000) ,
 `D5_comment` varchar(1000) ,
 `D6_comment` varchar(1000) ,
 `D7_comment` varchar(1000) ,
 `STATUS` char(1) ,
 `week_start_date` date ,
 `week_end_date` date ,
 `custom_activity_id` int(11) ,
 `approved_by` int(11) ,
 `approval_pending_from` int(11) 
)*/;

/*Table structure for table `vw_user_activity_rms_app` */

DROP TABLE IF EXISTS `vw_user_activity_rms_app`;

/*!50001 CREATE TABLE  `vw_user_activity_rms_app`(
 `ID` bigint(20) ,
 `employee_id` int(11) ,
 `ACTIVITY_ID` int(11) ,
 `CUSTOM_ACTIVITY_ID` int(11) ,
 `MODULE` varchar(250) ,
 `TICKET_NO` varchar(256) ,
 `resource_alloc_id` int(11) ,
 `D1_hours` double(10,2) ,
 `D2_hours` double(10,2) ,
 `D3_hours` double(10,2) ,
 `D4_hours` double(10,2) ,
 `D5_hours` double(10,2) ,
 `D6_hours` double(10,2) ,
 `D7_hours` double(10,2) ,
 `D1_comment` varchar(1000) ,
 `D2_comment` varchar(1000) ,
 `D3_comment` varchar(1000) ,
 `D4_comment` varchar(1000) ,
 `D5_comment` varchar(1000) ,
 `D6_comment` varchar(1000) ,
 `D7_comment` varchar(1000) ,
 `STATUS` char(1) ,
 `week_start_date` date ,
 `week_end_date` date ,
 `rejection_remarks` varchar(1000) ,
 `approve_code` varchar(20) ,
 `reject_code` varchar(20) 
)*/;

/*View structure for view bu */

/*!50001 DROP TABLE IF EXISTS `bu` */;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `bu` AS (select `o`.`id` AS `id`,concat(`p`.`name`,'-',`o`.`name`) AS `name`,concat(`p`.`name`,'-',`o`.`name`) AS `CODE`,`p`.`name` AS `BgName`,`o`.`name` AS `BuName` from (`org_hierarchy` `o` join `org_hierarchy` `p` on((`o`.`parent_id` = `p`.`id`))) where (`o`.`parent_id` <> (select `org_hierarchy`.`id` from `org_hierarchy` where (`org_hierarchy`.`name` = 'Organization')))) */;

/*View structure for view rephrs_weekenddate */

/*!50001 DROP TABLE IF EXISTS `rephrs_weekenddate` */;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `rephrs_weekenddate` AS select `ua`.`employee_id` AS `employee_id`,`ua`.`week_end_date` AS `week_end_date`,`ua`.`resource_alloc_id` AS `resource_alloc_id`,sum(((((((if(isnull(`ua`.`D1_hours`),0,`ua`.`D1_hours`) + if(isnull(`ua`.`D2_hours`),0,`ua`.`D2_hours`)) + if(isnull(`ua`.`D3_hours`),0,`ua`.`D3_hours`)) + if(isnull(`ua`.`D4_hours`),0,`ua`.`D4_hours`)) + if(isnull(`ua`.`D5_hours`),0,`ua`.`D5_hours`)) + if(isnull(`ua`.`D6_hours`),0,`ua`.`D6_hours`)) + if(isnull(`ua`.`D7_hours`),0,`ua`.`D7_hours`))) AS `repHrsPro` from `vw_user_activity` `ua` where ((`ua`.`week_end_date` = `ua`.`week_end_date`) and (`ua`.`resource_alloc_id` = `ua`.`resource_alloc_id`)) group by `ua`.`employee_id`,`ua`.`week_end_date`,`ua`.`resource_alloc_id` */;

/*View structure for view resource_allocation_project */

/*!50001 DROP TABLE IF EXISTS `resource_allocation_project` */;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `resource_allocation_project` AS (select `ra`.`id` AS `id`,`p`.`project_name` AS `project_name` from (`resource_allocation` `ra` join `project` `p`) where (`ra`.`project_id` = `p`.`id`)) */;

/*View structure for view timehrs_admin_resource_data */

/*!50001 DROP TABLE IF EXISTS `timehrs_admin_resource_data` */;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `timehrs_admin_resource_data` AS (select `res`.`employee_id` AS `employee_id`,`res`.`yash_emp_id` AS `yash_emp_id`,concat_ws(' ',`res`.`first_name`,`res`.`last_name`) AS `full_name`,`des`.`designation_name` AS `designation_name`,`g`.`grade` AS `grade`,`res`.`planned_capacity` AS `planned_capacity`,`res`.`actual_capacity` AS `actual_capacity`,`res`.`date_of_joining` AS `date_of_joining`,ifnull(`p`.`project_name`,'') AS `current_project` from (((`resource` `res` join `designations` `des` on((`res`.`designation_id` = `des`.`id`))) join `grade` `g` on((`res`.`grade_id` = `g`.`id`))) left join `project` `p` on((`res`.`current_project_id` = `p`.`id`)))) */;

/*View structure for view timehrs_admin_view */

/*!50001 DROP TABLE IF EXISTS `timehrs_admin_view` */;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `timehrs_admin_view` AS (select `res`.`employee_id` AS `employee_id`,`res`.`yash_emp_id` AS `yash_emp_id`,`res`.`full_name` AS `full_name`,`res`.`designation_name` AS `designation_name`,`res`.`grade` AS `grade`,`res`.`planned_capacity` AS `planned_capacity`,`res`.`actual_capacity` AS `actual_capacity`,0 AS `planned_hrs_sum`,0 AS `rephrs`,0 AS `billed_hrs_sum`,`res`.`date_of_joining` AS `date_of_joining`,`res`.`current_project` AS `current_project` from `timehrs_admin_resource_data` `res`) */;

/*View structure for view timehrs_employeeid_projectname */

/*!50001 DROP TABLE IF EXISTS `timehrs_employeeid_projectname` */;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `timehrs_employeeid_projectname` AS (select distinct `ra`.`employee_id` AS `employee_id`,`p`.`project_name` AS `project_name`,`ra`.`alloc_end_date` AS `alloc_end_date` from (`resource_allocation` `ra` join `project` `p`) where (`ra`.`project_id` = `p`.`id`)) */;

/*View structure for view timehrs_total_billhrs_planhrs */

/*!50001 DROP TABLE IF EXISTS `timehrs_total_billhrs_planhrs` */;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `timehrs_total_billhrs_planhrs` AS (select `th`.`resource_id` AS `resource_id`,sum(if(isnull(`th`.`billed_hrs`),0,`th`.`billed_hrs`)) AS `billed_hrs_sum`,sum(if(isnull(`th`.`planned_hrs`),0,`th`.`planned_hrs`)) AS `planned_hrs_sum` from `timehrs` `th` group by `th`.`resource_id`) */;

/*View structure for view timehrs_totalrephrs */

/*!50001 DROP TABLE IF EXISTS `timehrs_totalrephrs` */;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `timehrs_totalrephrs` AS (select `ua`.`employee_id` AS `employee_id`,sum(((((((if(isnull(`ua`.`D1_hours`),0,`ua`.`D1_hours`) + if(isnull(`ua`.`D2_hours`),0,`ua`.`D2_hours`)) + if(isnull(`ua`.`D3_hours`),0,`ua`.`D3_hours`)) + if(isnull(`ua`.`D4_hours`),0,`ua`.`D4_hours`)) + if(isnull(`ua`.`D5_hours`),0,`ua`.`D5_hours`)) + if(isnull(`ua`.`D6_hours`),0,`ua`.`D6_hours`)) + if(isnull(`ua`.`D7_hours`),0,`ua`.`D7_hours`))) AS `rephrs` from `vw_user_activity` `ua` group by `ua`.`employee_id`) */;

/*View structure for view user_activity_view */

/*!50001 DROP TABLE IF EXISTS `user_activity_view` */;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `user_activity_view` AS select `ua`.`employee_id` AS `employee_id`,`ua`.`week_start_date` AS `week_start_date`,`ua`.`week_end_date` AS `week_end_date`,`ua`.`resource_alloc_id` AS `resource_alloc_id`,`ua`.`STATUS` AS `approve_status`,`rap`.`project_name` AS `project_name`,`rephrs_weekenddate`.`repHrsPro` AS `repHrsPro` from ((`vw_user_activity` `ua` left join `rephrs_weekenddate` on(((`ua`.`employee_id` = `rephrs_weekenddate`.`employee_id`) and (`ua`.`week_end_date` = `rephrs_weekenddate`.`week_end_date`) and (`ua`.`resource_alloc_id` = `rephrs_weekenddate`.`resource_alloc_id`)))) left join `resource_allocation_project` `rap` on((`ua`.`resource_alloc_id` = `rap`.`id`))) */;

/*View structure for view vw_get_first_user_activity */

/*!50001 DROP TABLE IF EXISTS `vw_get_first_user_activity` */;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_get_first_user_activity` AS select (case when (`a`.`D1_hours` is not null) then `a`.`week_start_date` when (`a`.`D2_hours` is not null) then (`a`.`week_start_date` + interval 1 day) when (`a`.`D3_hours` is not null) then (`a`.`week_start_date` + interval 2 day) when (`a`.`D4_hours` is not null) then (`a`.`week_start_date` + interval 3 day) when (`a`.`D5_hours` is not null) then (`a`.`week_start_date` + interval 4 day) when (`a`.`D6_hours` is not null) then (`a`.`week_start_date` + interval 5 day) when (`a`.`D7_hours` is not null) then `a`.`week_end_date` end) AS `first_activity_date`,`b`.`res_alloc_id` AS `resource_alloc_id` from (`vw_user_activity` `a` join `vw_get_min_user_activity_date` `b`) where ((`a`.`resource_alloc_id` = `b`.`res_alloc_id`) and (`a`.`week_start_date` = `b`.`week_start_date`)) */;

/*View structure for view vw_get_last_user_activity */

/*!50001 DROP TABLE IF EXISTS `vw_get_last_user_activity` */;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_get_last_user_activity` AS select (case when (`a`.`D7_hours` is not null) then `a`.`week_end_date` when (`a`.`D6_hours` is not null) then (`a`.`week_end_date` - interval 1 day) when (`a`.`D5_hours` is not null) then (`a`.`week_end_date` - interval 2 day) when (`a`.`D4_hours` is not null) then (`a`.`week_end_date` - interval 3 day) when (`a`.`D3_hours` is not null) then (`a`.`week_end_date` - interval 4 day) when (`a`.`D2_hours` is not null) then (`a`.`week_end_date` - interval 5 day) when (`a`.`D1_hours` is not null) then `a`.`week_end_date` end) AS `last_activity_date`,`b`.`res_alloc_id` AS `resource_alloc_id` from (`vw_user_activity` `a` join `vw_get_max_user_activity_date` `b`) where ((`a`.`resource_alloc_id` = `b`.`res_alloc_id`) and (`a`.`week_end_date` = `b`.`week_end_date`)) */;

/*View structure for view vw_get_max_user_activity_date */

/*!50001 DROP TABLE IF EXISTS `vw_get_max_user_activity_date` */;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_get_max_user_activity_date` AS select max(`uad`.`week_end_date`) AS `week_end_date`,`ua`.`res_alloc_id` AS `res_alloc_id` from (`user_activity_detail` `uad` join `user_activity` `ua` on((`uad`.`time_sheet_id` = `ua`.`id`))) group by `ua`.`res_alloc_id` */;

/*View structure for view vw_get_min_max_user_acitivity_for_resource */

/*!50001 DROP TABLE IF EXISTS `vw_get_min_max_user_acitivity_for_resource` */;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_get_min_max_user_acitivity_for_resource` AS select `min_date`.`resource_alloc_id` AS `resource_alloc_id`,`min_date`.`first_activity_date` AS `first_activity_date`,`max_date`.`last_activity_date` AS `last_activity_date` from (`vw_get_first_user_activity` `min_date` join `vw_get_last_user_activity` `max_date` on((`min_date`.`resource_alloc_id` = `max_date`.`resource_alloc_id`))) */;

/*View structure for view vw_get_min_user_activity_date */

/*!50001 DROP TABLE IF EXISTS `vw_get_min_user_activity_date` */;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_get_min_user_activity_date` AS select `uad`.`time_sheet_id` AS `id`,min(`uad`.`week_start_date`) AS `week_start_date`,`ua`.`res_alloc_id` AS `res_alloc_id` from (`user_activity_detail` `uad` join `user_activity` `ua` on((`uad`.`time_sheet_id` = `ua`.`id`))) group by `ua`.`res_alloc_id` */;

/*View structure for view vw_resource_alloc_activity_details */

/*!50001 DROP TABLE IF EXISTS `vw_resource_alloc_activity_details` */;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_resource_alloc_activity_details` AS select `ra`.`project_id` AS `project_id`,`ua`.`resource_alloc_id` AS `resource_alloc_id`,`ua`.`first_activity_date` AS `first_activity_date`,`ua`.`last_activity_date` AS `last_activity_date` from (`resource_allocation` `ra` join `vw_get_min_max_user_acitivity_for_resource` `ua`) where (`ra`.`id` = `ua`.`resource_alloc_id`) */;

/*View structure for view vw_rm_report */

/*!50001 DROP TABLE IF EXISTS `vw_rm_report` */;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_rm_report` AS (select `r`.`yash_emp_id` AS `Yash_Emp_Id`,concat(trim(`r`.`first_name`),' ',trim(`r`.`last_name`)) AS `Employee_Name`,`r`.`email_id` AS `Emp_Email_ID`,`d`.`designation_name` AS `Designation`,`g`.`grade` AS `Grade`,`r`.`date_of_joining` AS `Date_Of_Joining`,`r`.`release_date` AS `Release_Date`,`l`.`location` AS `Base_Location`,`paylocation`.`location` AS `Current_Location`,`b`.`CODE` AS `Parent_Bu`,`cb`.`CODE` AS `Currnet_Bu`,`ow`.`ownership_name` AS `Ownership`,concat(trim(`irm1`.`first_name`),' ',trim(`irm1`.`last_name`)) AS `Current_RM1`,concat(trim(`irm2`.`first_name`),' ',trim(`irm2`.`last_name`)) AS `Current_RM2`,`p`.`project_name` AS `Primary_Project`,`ra`.`curProj` AS `Current_Project_Flag`,`cus`.`customer_name` AS `Customer_Name`,`pbu`.`CODE` AS `Project_Bu`,`ra`.`alloc_start_date` AS `Allocation_Start_Date`,`allo_ty`.`allocationtype` AS `Allocation_Type`,`r`.`transfer_date` AS `Transfer_Date`,`v`.`visa` AS `Visa`,`sk1`.`skill` AS `Primary_skill`,`sk2`.`skill` AS `Secondary_skill`,`r`.`customer_id_detail` AS `Customer_Id`,`r`.`lastupdated_id` AS `LastUpdated_ID`,`r`.`lastupdated_timestamp` AS `Last_TimeStamp`,`r`.`bu_id` AS `Bu_Id`,`r`.`current_bu_id` AS `Current_Bu_Id`,`r`.`current_project_id` AS `Current_Project_Id`,`r`.`location_id` AS `Location_id`,`p`.`id` AS `Project_ID`,`p`.`bu_id` AS `Project_Bu_ID`,`ra`.`alloc_end_date` AS `Resource_Allocation_End_Date`,`allo_ty`.`id` AS `Allocation_Type_Id`,`ra`.`employee_id` AS `Employee_Id`,`allo_ty`.`Priority` AS `Allocation_Priority`,`r`.`employee_id` AS `RES_EMP_ID`,`ra`.`id` AS `ALLOC_ID` from ((((((((((((((((((((`resource` `r` left join `designations` `d` on((`r`.`designation_id` = `d`.`id`))) left join `grade` `g` on((`r`.`grade_id` = `g`.`id`))) left join `location` `l` on((`r`.`location_id` = `l`.`id`))) left join `location` `paylocation` on((`r`.`payroll_location` = `paylocation`.`id`))) left join `bu` `b` on((`r`.`bu_id` = `b`.`id`))) left join `bu` `cb` on((`r`.`current_bu_id` = `cb`.`id`))) left join `ownership` `ow` on((`r`.`ownership` = `ow`.`id`))) left join `resource` `irm1` on((`r`.`current_reporting_manager` = `irm1`.`employee_id`))) left join `resource` `irm2` on((`r`.`current_reporting_manager_two` = `irm2`.`employee_id`))) left join `resource_allocation` `ra` on((`ra`.`employee_id` = `r`.`employee_id`))) left join `project` `p` on((`p`.`id` = `ra`.`project_id`))) left join `bu` `pbu` on((`p`.`bu_id` = `pbu`.`id`))) left join `customer` `cus` on((`p`.`customer_name_id` = `cus`.`id`))) left join `bu` `rbu` on((`p`.`bu_id` = `rbu`.`id`))) left join `allocationtype` `allo_ty` on((`ra`.`allocation_type_id` = `allo_ty`.`id`))) left join `visa` `v` on((`r`.`visa_id` = `v`.`id`))) left join `skill_resource_primary` `skp` on((`r`.`employee_id` = `skp`.`resource_id`))) left join `skill_resource_secondary` `skp2` on((`r`.`employee_id` = `skp2`.`resource_id`))) left join `skills` `sk1` on((`skp`.`skill_id` = `sk1`.`id`))) left join `skills` `sk2` on((`skp2`.`skill_id` = `sk2`.`id`)))) */;

/*View structure for view vw_rm_support */

/*!50001 DROP TABLE IF EXISTS `vw_rm_support` */;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_rm_support` AS (select `r`.`employee_id` AS `employee_id`,`r`.`id` AS `id`,`r`.`allocation_type_id` AS `Allocation_Type_Id`,`r`.`curProj` AS `curProj`,`a`.`Priority` AS `Priority` from (`resource_allocation` `r` join `allocationtype` `a`) where ((isnull(`r`.`alloc_end_date`) or (`r`.`alloc_end_date` >= curdate())) and (`r`.`allocation_type_id` = `a`.`id`))) */;

/*View structure for view vw_skill_ratings */

/*!50001 DROP TABLE IF EXISTS `vw_skill_ratings` */;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_skill_ratings` AS (select `primskill`.`resource_id` AS `employee_id`,`primskill`.`Primary_skill` AS `Primary_skill`,ifnull(`secskill`.`Secondary_skill`,'') AS `Secondary_skill` from (`vw_skill_ratings_primaryskills` `primskill` left join `vw_skill_ratings_secondaryskills` `secskill` on((`primskill`.`resource_id` = `secskill`.`resource_id`)))) */;

/*View structure for view vw_skill_ratings_primaryskills */

/*!50001 DROP TABLE IF EXISTS `vw_skill_ratings_primaryskills` */;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_skill_ratings_primaryskills` AS (select `srp`.`resource_id` AS `resource_id`,'Primary' AS `SkillType`,group_concat(`sk`.`skill` order by `sk`.`skill` ASC separator ',') AS `Primary_skill` from (`skill_resource_primary` `srp` join `skills` `sk`) where (`sk`.`id` = `srp`.`skill_id`) group by `srp`.`resource_id`,'Primary') */;

/*View structure for view vw_skill_ratings_secondaryskills */

/*!50001 DROP TABLE IF EXISTS `vw_skill_ratings_secondaryskills` */;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_skill_ratings_secondaryskills` AS (select `srs`.`resource_id` AS `resource_id`,'Secondary' AS `SkillType`,group_concat(`sk`.`skill` order by `sk`.`skill` ASC separator ',') AS `Secondary_skill` from (`skill_resource_secondary` `srs` join `skills` `sk`) where (`sk`.`id` = `srs`.`skill_id`) group by `srs`.`resource_id`,'Secondary') */;

/*View structure for view vw_user_activity */

/*!50001 DROP TABLE IF EXISTS `vw_user_activity` */;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_user_activity` AS select `ua`.`id` AS `ID`,`ra`.`employee_id` AS `employee_id`,`ua`.`activity_id` AS `ACTIVITY_ID`,`ua`.`module` AS `MODULE`,`ua`.`res_alloc_id` AS `resource_alloc_id`,`uad`.`d1_hours` AS `D1_hours`,`uad`.`d2_hours` AS `D2_hours`,`uad`.`d3_hours` AS `D3_hours`,`uad`.`d4_hours` AS `D4_hours`,`uad`.`d5_hours` AS `D5_hours`,`uad`.`d6_hours` AS `D6_hours`,`uad`.`d7_hours` AS `D7_hours`,`uad`.`d1_comment` AS `D1_comment`,`uad`.`d2_comment` AS `D2_comment`,`uad`.`d3_comment` AS `D3_comment`,`uad`.`d4_comment` AS `D4_comment`,`uad`.`d5_comment` AS `D5_comment`,`uad`.`d6_comment` AS `D6_comment`,`uad`.`d7_comment` AS `D7_comment`,`ua`.`status` AS `STATUS`,`uad`.`week_start_date` AS `week_start_date`,`uad`.`week_end_date` AS `week_end_date`,`ua`.`custom_activity_id` AS `custom_activity_id`,`ua`.`approved_by` AS `approved_by`,`ua`.`approval_pending_from` AS `approval_pending_from` from ((`resource_allocation` `ra` join `user_activity` `ua` on((`ra`.`id` = `ua`.`res_alloc_id`))) left join `user_activity_detail` `uad` on((`ua`.`id` = `uad`.`time_sheet_id`))) */;

/*View structure for view vw_user_activity_rms_app */

/*!50001 DROP TABLE IF EXISTS `vw_user_activity_rms_app` */;
/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_user_activity_rms_app` AS select `ua`.`id` AS `ID`,`ra`.`employee_id` AS `employee_id`,`ua`.`activity_id` AS `ACTIVITY_ID`,`ua`.`custom_activity_id` AS `CUSTOM_ACTIVITY_ID`,`ua`.`module` AS `MODULE`,`ua`.`ticket_no` AS `TICKET_NO`,`ua`.`res_alloc_id` AS `resource_alloc_id`,`uad`.`d1_hours` AS `D1_hours`,`uad`.`d2_hours` AS `D2_hours`,`uad`.`d3_hours` AS `D3_hours`,`uad`.`d4_hours` AS `D4_hours`,`uad`.`d5_hours` AS `D5_hours`,`uad`.`d6_hours` AS `D6_hours`,`uad`.`d7_hours` AS `D7_hours`,`uad`.`d1_comment` AS `D1_comment`,`uad`.`d2_comment` AS `D2_comment`,`uad`.`d3_comment` AS `D3_comment`,`uad`.`d4_comment` AS `D4_comment`,`uad`.`d5_comment` AS `D5_comment`,`uad`.`d6_comment` AS `D6_comment`,`uad`.`d7_comment` AS `D7_comment`,`ua`.`status` AS `STATUS`,`uad`.`week_start_date` AS `week_start_date`,`uad`.`week_end_date` AS `week_end_date`,`ua`.`rejection_remarks` AS `rejection_remarks`,`ua`.`approve_code` AS `approve_code`,`ua`.`reject_code` AS `reject_code` from ((`resource_allocation` `ra` join `user_activity` `ua` on((`ra`.`id` = `ua`.`res_alloc_id`))) left join `user_activity_detail` `uad` on((`ua`.`id` = `uad`.`time_sheet_id`))) */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

/*14-02-2016: ALTER query of resource_allocation table : added project_end_remarks column for Project Allocation End Feedback */

ALTER TABLE resource_allocation ADD COLUMN project_end_remarks VARCHAR(1000) DEFAULT 'NA';

/*06-04-2016: New view for Project Visiblity for Delivery Manager*/

DELIMITER $$

DROP VIEW IF EXISTS `vw_dm_project_visibilty`$$

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_dm_project_visibilty` AS (
SELECT DISTINCT
  `ra`.`employee_id`     AS `employee_id`,
  `p`.`project_name`     AS `project_name`,
  `ra`.`alloc_end_date`  AS `alloc_end_date`,
  `p`.`offshore_del_mgr` AS `offshore_del_mgr`,
  `p`.`project_end_date` AS `project_end_date`
FROM (`project` `p`
   LEFT JOIN `resource_allocation` `ra`
     ON ((`ra`.`project_id` = `p`.`id`))))$$

DELIMITER ;

/* Project Sub Module Table*/
DROP TABLE IF EXISTS `project_sub_module`;

CREATE TABLE `project_sub_module` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `sub_module_name` VARCHAR(100) DEFAULT NULL,
  `module_id` INT(11) DEFAULT NULL,
  `active` TINYINT(4) DEFAULT NULL,
  `created_id` VARCHAR(100) DEFAULT NULL,
  `creation_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastupdated_id` VARCHAR(100) DEFAULT NULL,
  `lastupdated_timestamp` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `project_sub_module_ibfk_1` (`module_id`),
  CONSTRAINT `project_sub_module_ibfk_1` FOREIGN KEY (`module_id`) REFERENCES `project_module` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/* View for User Activity */
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_user_activity_rms_app` AS SELECT `ua`.`id` AS `ID`, `ra`.`employee_id` AS `employee_id`, `ua`.`activity_id` AS `ACTIVITY_ID`, `ua`.`custom_activity_id` AS `CUSTOM_ACTIVITY_ID`, `ua`.`module` AS `MODULE`, `ua`.`ticket_no` AS `TICKET_NO`, `ua`.`res_alloc_id` AS `resource_alloc_id`, `uad`.`d1_hours` AS `D1_hours`, `uad`.`d2_hours` AS `D2_hours`, `uad`.`d3_hours` AS `D3_hours`, `uad`.`d4_hours` AS `D4_hours`, `uad`.`d5_hours` AS `D5_hours`, `uad`.`d6_hours` AS `D6_hours`, `uad`.`d7_hours` AS `D7_hours`, `uad`.`d1_comment` AS `D1_comment`, `uad`.`d2_comment` AS `D2_comment`, `uad`.`d3_comment` AS `D3_comment`, `uad`.`d4_comment` AS `D4_comment`, `uad`.`d5_comment` AS `D5_comment`, `uad`.`d6_comment` AS `D6_comment`, `uad`.`d7_comment` AS `D7_comment`, `ua`.`status` AS `STATUS`, `uad`.`week_start_date` AS `week_start_date`, `uad`.`week_end_date` AS `week_end_date`, `ua`.`rejection_remarks` AS `rejection_remarks`, `ua`.`approve_code` AS `approve_code`, `ua`.`reject_code` AS `reject_code`, `ua`.`sub_module` AS SUB_MODULE FROM ((`resource_allocation` `ra` JOIN `user_activity` `ua` ON ((`ra`.`id` = `ua`.`res_alloc_id`))) LEFT JOIN `user_activity_detail` `uad` ON ((`ua`.`id` = `uad`.`time_sheet_id`)));

/* Added Project Sub Module in User Activity */

ALTER TABLE `rms_3.1_test`.`user_activity` ADD COLUMN `sub_module` VARCHAR(250) NULL AFTER `approval_pending_from`;
/* Added columns in Resource table for Resume and TEF */
ALTER TABLE `rms_3.1_test`.`resource` 
ADD COLUMN `upload_resume_file_name` VARCHAR(250) NULL AFTER `resignation_date`,
ADD COLUMN `upload_Resume` LONGBLOB NULL AFTER `resignation_date`,
ADD COLUMN `upload_tef_file_name` VARCHAR(250) NULL AFTER `resignation_date`,
ADD COLUMN `upload_tef` LONGBLOB NULL
AFTER `resignation_date`;


/* Project Ticket Priority */

DROP TABLE IF EXISTS `project_ticket_priority`;

CREATE TABLE `project_ticket_priority` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `priority` VARCHAR(255) NOT NULL,
  `mandatory` TINYINT(1) NOT NULL DEFAULT '0',
  `active` TINYINT(1) NOT NULL DEFAULT '0',
  `projectId` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `priority` (`priority`),
  KEY `fk_ticket_priority_projectId` (`projectId`),
  CONSTRAINT `fk_ticket_priority_projectId` FOREIGN KEY (`projectId`) REFERENCES `project` (`id`)
) ENGINE=INNODB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8; 

/* Project Ticket Status */

DROP TABLE IF EXISTS `project_ticket_status`;

CREATE TABLE `project_ticket_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(256) NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '0',
  `projectId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ticket_status_projectId` (`projectId`),
  CONSTRAINT `fk_ticket_status_projectId` FOREIGN KEY (`projectId`) REFERENCES `project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;


/* Project Ticket Priority and status on Timesheet Submission screen*/

ALTER TABLE `user_activity` ADD COLUMN `ticket_priority` VARCHAR(256) NULL , ADD COLUMN `ticket_status` VARCHAR(255) NULL ;

/* Please Execute the below view alteration query Start*/
DELIMITER $$

USE `rms_3.1_test`$$

DROP VIEW IF EXISTS `vw_user_activity_rms_app`$$

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_user_activity_rms_app` AS 
SELECT
  `ua`.`id`                 AS `ID`,
  `ra`.`employee_id`        AS `employee_id`,
  `ua`.`activity_id`        AS `ACTIVITY_ID`,
  `ua`.`custom_activity_id` AS `CUSTOM_ACTIVITY_ID`,
  `ua`.`module`             AS `MODULE`,
  `ua`.`ticket_no`          AS `TICKET_NO`,
  `ua`.`res_alloc_id`       AS `resource_alloc_id`,
  `uad`.`d1_hours`          AS `D1_hours`,
  `uad`.`d2_hours`          AS `D2_hours`,
  `uad`.`d3_hours`          AS `D3_hours`,
  `uad`.`d4_hours`          AS `D4_hours`,
  `uad`.`d5_hours`          AS `D5_hours`,
  `uad`.`d6_hours`          AS `D6_hours`,
  `uad`.`d7_hours`          AS `D7_hours`,
  `uad`.`d1_comment`        AS `D1_comment`,
  `uad`.`d2_comment`        AS `D2_comment`,
  `uad`.`d3_comment`        AS `D3_comment`,
  `uad`.`d4_comment`        AS `D4_comment`,
  `uad`.`d5_comment`        AS `D5_comment`,
  `uad`.`d6_comment`        AS `D6_comment`,
  `uad`.`d7_comment`        AS `D7_comment`,
  `ua`.`status`             AS `STATUS`,
  `uad`.`week_start_date`   AS `week_start_date`,
  `uad`.`week_end_date`     AS `week_end_date`,
  `ua`.`rejection_remarks`  AS `rejection_remarks`,
  `ua`.`approve_code`       AS `approve_code`,
  `ua`.`reject_code`        AS `reject_code`,
  `ua`.`sub_module`         AS `SUB_MODULE`,
  `ua`.`ticket_priority`    AS `ticket_priority`,
  `ua`.`ticket_status`      AS `ticket_status`
FROM ((`resource_allocation` `ra`
    JOIN `user_activity` `ua`
      ON ((`ra`.`id` = `ua`.`res_alloc_id`)))
   LEFT JOIN `user_activity_detail` `uad`
     ON ((`ua`.`id` = `uad`.`time_sheet_id`)))$$

DELIMITER ;
/* Please Execute the below view alteration query END*/

/* 31st July - Added two columns in skill_request_requisition */
DELIMITER $$

USE `rms_3.1_test`$$

DROP VIEW IF EXISTS `vw_rm_report`$$

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `rms_3.1_test`.`vw_rm_report` AS (
SELECT
  `r`.`yash_emp_id`           AS `Yash_Emp_Id`,
  CONCAT(TRIM(`r`.`first_name`),' ',TRIM(`r`.`last_name`)) AS `Employee_Name`,
  `r`.`email_id`              AS `Emp_Email_ID`,
  `d`.`designation_name`      AS `Designation`,
  `g`.`grade`                 AS `Grade`,
  `r`.`date_of_joining`       AS `Date_Of_Joining`,
  `r`.`release_date`          AS `Release_Date`,
  `l`.`location`              AS `Base_Location`,
  `paylocation`.`location`    AS `Current_Location`,
  `b`.`CODE`                  AS `Parent_Bu`,
  `cb`.`CODE`                 AS `Currnet_Bu`,
  `ow`.`ownership_name`       AS `Ownership`,
  CONCAT(TRIM(`irm1`.`first_name`),' ',TRIM(`irm1`.`last_name`)) AS `Current_RM1`,
  CONCAT(TRIM(`irm2`.`first_name`),' ',TRIM(`irm2`.`last_name`)) AS `Current_RM2`,
  `p`.`project_name`          AS `Primary_Project`,
  `ra`.`curProj`              AS `Current_Project_Flag`,
  `cus`.`customer_name`       AS `Customer_Name`,
  `pbu`.`CODE`                AS `Project_Bu`,
  `ra`.`alloc_start_date`     AS `Allocation_Start_Date`,
  `allo_ty`.`allocationtype`  AS `Allocation_Type`,
  `r`.`transfer_date`         AS `Transfer_Date`,
  `v`.`visa`                  AS `Visa`,
  `sk1`.`skill`               AS `Primary_skill`,
  `sk2`.`skill`               AS `Secondary_skill`,
  `r`.`customer_id_detail`    AS `Customer_Id`,
  `r`.`lastupdated_id`        AS `LastUpdated_ID`,
  `r`.`lastupdated_timestamp` AS `Last_TimeStamp`,
  `r`.`bu_id`                 AS `Bu_Id`,
  `r`.`current_bu_id`         AS `Current_Bu_Id`,
  `r`.`current_project_id`    AS `Current_Project_Id`,
  `r`.`location_id`           AS `Location_id`,
  `p`.`id`                    AS `Project_ID`,
  `p`.`bu_id`                 AS `Project_Bu_ID`,
  `ra`.`alloc_end_date`       AS `Resource_Allocation_End_Date`,
  `allo_ty`.`id`              AS `Allocation_Type_Id`,
  `ra`.`employee_id`          AS `Employee_Id`,
  `allo_ty`.`Priority`        AS `Allocation_Priority`,
  `r`.`employee_id`           AS `RES_EMP_ID`,
  `ra`.`id`                   AS `ALLOC_ID`,
  `comp`.`skill`              AS `competency_skill`
FROM (((((((((((((((((((((`rms_3.1_test`.`resource` `r`
                       LEFT JOIN `rms_3.1_test`.`designations` `d`
                         ON ((`r`.`designation_id` = `d`.`id`)))
                      LEFT JOIN `rms_3.1_test`.`grade` `g`
                        ON ((`r`.`grade_id` = `g`.`id`)))
                     LEFT JOIN `rms_3.1_test`.`location` `l`
                       ON ((`r`.`location_id` = `l`.`id`)))
                    LEFT JOIN `rms_3.1_test`.`location` `paylocation`
                      ON ((`r`.`payroll_location` = `paylocation`.`id`)))
                   LEFT JOIN `rms_3.1_test`.`bu` `b`
                     ON ((`r`.`bu_id` = `b`.`id`)))
                  LEFT JOIN `rms_3.1_test`.`bu` `cb`
                    ON ((`r`.`current_bu_id` = `cb`.`id`)))
                 LEFT JOIN `rms_3.1_test`.`ownership` `ow`
                   ON ((`r`.`ownership` = `ow`.`id`)))
                LEFT JOIN `rms_3.1_test`.`resource` `irm1`
                  ON ((`r`.`current_reporting_manager` = `irm1`.`employee_id`)))
               LEFT JOIN `rms_3.1_test`.`resource` `irm2`
                 ON ((`r`.`current_reporting_manager_two` = `irm2`.`employee_id`)))
              LEFT JOIN `rms_3.1_test`.`resource_allocation` `ra`
                ON ((`ra`.`employee_id` = `r`.`employee_id`)))
             LEFT JOIN `rms_3.1_test`.`project` `p`
               ON ((`p`.`id` = `ra`.`project_id`)))
            LEFT JOIN `rms_3.1_test`.`bu` `pbu`
              ON ((`p`.`bu_id` = `pbu`.`id`)))
           LEFT JOIN `rms_3.1_test`.`customer` `cus`
             ON ((`p`.`customer_name_id` = `cus`.`id`)))
          LEFT JOIN `rms_3.1_test`.`bu` `rbu`
            ON ((`p`.`bu_id` = `rbu`.`id`)))
         LEFT JOIN `rms_3.1_test`.`allocationtype` `allo_ty`
           ON ((`ra`.`allocation_type_id` = `allo_ty`.`id`)))
        LEFT JOIN `rms_3.1_test`.`visa` `v`
          ON ((`r`.`visa_id` = `v`.`id`)))
       LEFT JOIN `rms_3.1_test`.`skill_resource_primary` `skp`
         ON ((`r`.`employee_id` = `skp`.`resource_id`)))
      LEFT JOIN `rms_3.1_test`.`skill_resource_secondary` `skp2`
        ON ((`r`.`employee_id` = `skp2`.`resource_id`)))
     LEFT JOIN `rms_3.1_test`.`skills` `sk1`
       ON ((`skp`.`skill_id` = `sk1`.`id`)))
    LEFT JOIN `rms_3.1_test`.`skills` `sk2`
      ON ((`skp2`.`skill_id` = `sk2`.`id`)))
   LEFT JOIN `rms_3.1_test`.`competency` `comp`
     ON ((`r`.`competency` = `comp`.`id`))))$$

DELIMITER ;

/* Please Execute the below view alteration query for RM Report END*/



ALTER TABLE `request_requisition` 
ADD COLUMN  `creation_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
ADD COLUMN `created_by`VARCHAR(1000),
ADD COLUMN `lastupdated_by`VARCHAR(1000),
ADD COLUMN `lastupdated_timestamp` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00';
ALTER TABLE `skill_request_resource` ADD COLUMN `location` VARCHAR(1000) NULL , ADD COLUMN `notice_period`int(100) NULL ;


/* added new field on profile submission page by Aakanksha on 12/12/2018*/

ALTER TABLE `skill_request_resource` ADD email_id  VARCHAR(256) DEFAULT NULL;
ALTER TABLE `skill_request_resource` ADD contact_number  VARCHAR(256) DEFAULT NULL;
ALTER TABLE `skill_request_resource` ADD total_experience  VARCHAR(256) DEFAULT NULL;
ALTER TABLE `skill_request_resource` ADD skills  VARCHAR(256) DEFAULT NULL;



