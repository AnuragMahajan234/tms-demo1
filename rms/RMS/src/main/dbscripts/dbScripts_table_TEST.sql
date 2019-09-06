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

/*Table structure for table `123` */

DROP TABLE IF EXISTS `123`;

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

DROP TABLE IF EXISTS `activity`;

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

DROP TABLE IF EXISTS `all_timesheet_activity`;

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
) ENGINE=InnoDB AUTO_INCREMENT=41900 DEFAULT CHARSET=latin1;

/*Table structure for table `allocation_type_new` */

DROP TABLE IF EXISTS `allocation_type_new`;

CREATE TABLE `allocation_type_new` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `allocationtype_1` varchar(256) DEFAULT NULL,
  `new_allocationtype` varchar(500) DEFAULT NULL,
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

/*Table structure for table `allocationtype` */

DROP TABLE IF EXISTS `allocationtype`;

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
  `required_for_rrf` varchar(3) DEFAULT 'N' COMMENT 'required for RRf creation : Y or N',
  `bgh_mandatory_flag` varchar(3) DEFAULT 'Y',
  `alias_allocation_name` varchar(144) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique` (`allocationtype`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;

/*Table structure for table `bak_4nov_15_mails_configuration` */

DROP TABLE IF EXISTS `bak_4nov_15_mails_configuration`;

CREATE TABLE `bak_4nov_15_mails_configuration` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `project_id` int(10) DEFAULT NULL,
  `confg_id` int(10) DEFAULT NULL,
  `role_id` int(10) DEFAULT NULL,
  `mail_to` tinyint(1) DEFAULT '0',
  `mail_cc` tinyint(1) DEFAULT '0',
  `mail_bcc` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3667 DEFAULT CHARSET=latin1;

/*Table structure for table `bgadmin_access_rights` */

DROP TABLE IF EXISTS `bgadmin_access_rights`;

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
) ENGINE=InnoDB AUTO_INCREMENT=2287 DEFAULT CHARSET=latin1;

/*Table structure for table `bgadmin_access_rights_aud` */

DROP TABLE IF EXISTS `bgadmin_access_rights_aud`;

CREATE TABLE `bgadmin_access_rights_aud` (
  `id` int(11) NOT NULL,
  `REV` int(11) NOT NULL,
  `REVTYPE` tinyint(4) NOT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `org_id` int(11) DEFAULT NULL,
  `action` varchar(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `billingscale` */

DROP TABLE IF EXISTS `billingscale`;

CREATE TABLE `billingscale` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `billing_rate` int(5) NOT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `billingrate_unique` (`billing_rate`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Table structure for table `bu_old` */

DROP TABLE IF EXISTS `bu_old`;

CREATE TABLE `bu_old` (
  `id` int(11) NOT NULL DEFAULT '0',
  `name` text NOT NULL,
  `CODE` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `ca_ticket` */

DROP TABLE IF EXISTS `ca_ticket`;

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

DROP TABLE IF EXISTS `ca_ticket_discrepencies`;

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
) ENGINE=InnoDB AUTO_INCREMENT=705 DEFAULT CHARSET=utf8;

/*Table structure for table `client_type` */

DROP TABLE IF EXISTS `client_type`;

CREATE TABLE `client_type` (
  `type` varchar(50) NOT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `competency` */

DROP TABLE IF EXISTS `competency`;

CREATE TABLE `competency` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `skill` varchar(256) NOT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=193 DEFAULT CHARSET=latin1;

/*Table structure for table `configuration_detail` */

DROP TABLE IF EXISTS `configuration_detail`;

CREATE TABLE `configuration_detail` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `type_id` (`NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

/*Table structure for table `crop_ost` */

DROP TABLE IF EXISTS `crop_ost`;

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

DROP TABLE IF EXISTS `currency`;

CREATE TABLE `currency` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `currency_name` varchar(256) NOT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique` (`currency_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Table structure for table `custom_activity` */

DROP TABLE IF EXISTS `custom_activity`;

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
) ENGINE=InnoDB AUTO_INCREMENT=409 DEFAULT CHARSET=latin1;

/*Table structure for table `customer` */

DROP TABLE IF EXISTS `customer`;

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
  `pdl_email_id` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=195 DEFAULT CHARSET=latin1;

/*Table structure for table `customer_aud` */

DROP TABLE IF EXISTS `customer_aud`;

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

/*Table structure for table `customer_group` */

DROP TABLE IF EXISTS `customer_group`;

CREATE TABLE `customer_group` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) DEFAULT NULL,
  `group_name` varchar(256) NOT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `active` varchar(10) DEFAULT 'Y',
  `pdl_email_id` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`group_id`),
  KEY `customergroup_ibfk_1` (`customer_id`),
  CONSTRAINT `customergroup_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

/*Table structure for table `customer_po` */

DROP TABLE IF EXISTS `customer_po`;

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
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

/*Table structure for table `customer_po_aud` */

DROP TABLE IF EXISTS `customer_po_aud`;

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

DROP TABLE IF EXISTS `dashboard_temp`;

CREATE TABLE `dashboard_temp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_bg_bu` varchar(100) DEFAULT NULL,
  `project_id` varchar(100) DEFAULT NULL,
  `project_name` varchar(100) DEFAULT NULL,
  `billable_resources` varchar(100) DEFAULT '0',
  `non_billable_resources` varchar(100) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=746 DEFAULT CHARSET=utf8;

/*Table structure for table `dashboard_temp_second` */

DROP TABLE IF EXISTS `dashboard_temp_second`;

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
) ENGINE=InnoDB AUTO_INCREMENT=690 DEFAULT CHARSET=utf8;

/*Table structure for table `dashboard_widgets` */

DROP TABLE IF EXISTS `dashboard_widgets`;

CREATE TABLE `dashboard_widgets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `employee_id` int(11) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `week_end_date` date DEFAULT NULL,
  `STATUS` varchar(100) DEFAULT '0',
  `employee_count` int(100) DEFAULT '0',
  `manager_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1117 DEFAULT CHARSET=utf8;

/*Table structure for table `default_project` */

DROP TABLE IF EXISTS `default_project`;

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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Table structure for table `defect_log_ost` */

DROP TABLE IF EXISTS `defect_log_ost`;

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

DROP TABLE IF EXISTS `designations`;

CREATE TABLE `designations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `designation_name` varchar(256) NOT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique` (`designation_name`)
) ENGINE=InnoDB AUTO_INCREMENT=150 DEFAULT CHARSET=latin1;

/*Table structure for table `dt_temp` */

DROP TABLE IF EXISTS `dt_temp`;

CREATE TABLE `dt_temp` (
  `dt` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `employee_category` */

DROP TABLE IF EXISTS `employee_category`;

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

DROP TABLE IF EXISTS `engagementmodel`;

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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

/*Table structure for table `event` */

DROP TABLE IF EXISTS `event`;

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

/*Table structure for table `experience` */

DROP TABLE IF EXISTS `experience`;

CREATE TABLE `experience` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `experience_range` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Table structure for table `grade` */

DROP TABLE IF EXISTS `grade`;

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

DROP TABLE IF EXISTS `hopping_reason_ost`;

CREATE TABLE `hopping_reason_ost` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `reason` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

/*Table structure for table `invoice_by` */

DROP TABLE IF EXISTS `invoice_by`;

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

DROP TABLE IF EXISTS `job_post`;

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

DROP TABLE IF EXISTS `job_type`;

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

DROP TABLE IF EXISTS `jobpost_skill_primary`;

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

DROP TABLE IF EXISTS `jobpost_skill_secondary`;

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

DROP TABLE IF EXISTS `landscape`;

CREATE TABLE `landscape` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `landscapeName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;

/*Table structure for table `location` */

DROP TABLE IF EXISTS `location`;

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
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=latin1;

/*Table structure for table `mail_configuration_roles` */

DROP TABLE IF EXISTS `mail_configuration_roles`;

CREATE TABLE `mail_configuration_roles` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `role` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Table structure for table `mails_configuration` */

DROP TABLE IF EXISTS `mails_configuration`;

CREATE TABLE `mails_configuration` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `project_id` int(10) DEFAULT NULL,
  `confg_id` int(10) DEFAULT NULL,
  `role_id` int(10) DEFAULT NULL,
  `mail_to` tinyint(1) DEFAULT '0',
  `mail_cc` tinyint(1) DEFAULT '0',
  `mail_bcc` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15728 DEFAULT CHARSET=latin1;

/*Table structure for table `module` */

DROP TABLE IF EXISTS `module`;

CREATE TABLE `module` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `module_name` varchar(256) NOT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique` (`module_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Table structure for table `org_hierarchy` */

DROP TABLE IF EXISTS `org_hierarchy`;

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
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=latin1;

/*Table structure for table `org_hierarchy_aud` */

DROP TABLE IF EXISTS `org_hierarchy_aud`;

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

DROP TABLE IF EXISTS `ownership`;

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

/*Table structure for table `pdl_email_group` */

DROP TABLE IF EXISTS `pdl_email_group`;

CREATE TABLE `pdl_email_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pdl_email_id` varchar(256) NOT NULL,
  `PDL_NAME` varchar(20) DEFAULT NULL,
  `created_by` varchar(256) DEFAULT NULL,
  `created_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modified_by` varchar(256) DEFAULT NULL,
  `modified_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `active` char(1) DEFAULT 'Y',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Table structure for table `pdl_resource_mapping` */

DROP TABLE IF EXISTS `pdl_resource_mapping`;

CREATE TABLE `pdl_resource_mapping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pdl_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Unique` (`pdl_id`,`resource_id`),
  KEY `map_resource` (`resource_id`),
  CONSTRAINT `map_pdl` FOREIGN KEY (`pdl_id`) REFERENCES `pdl_email_group` (`id`),
  CONSTRAINT `map_resource` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`employee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;

/*Table structure for table `pdl_resource_mapping_19092018` */

DROP TABLE IF EXISTS `pdl_resource_mapping_19092018`;

CREATE TABLE `pdl_resource_mapping_19092018` (
  `id` int(11) NOT NULL DEFAULT '0',
  `pdl_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `phaseengactvty_mapping` */

DROP TABLE IF EXISTS `phaseengactvty_mapping`;

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

DROP TABLE IF EXISTS `phasemaster`;

CREATE TABLE `phasemaster` (
  `phaseid` int(11) NOT NULL AUTO_INCREMENT,
  `phasename` varchar(900) DEFAULT NULL,
  PRIMARY KEY (`phaseid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `process_ost` */

DROP TABLE IF EXISTS `process_ost`;

CREATE TABLE `process_ost` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `module_id` int(10) DEFAULT NULL,
  `process_name` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `module_id` (`module_id`),
  CONSTRAINT `process_ost_ibfk_1` FOREIGN KEY (`module_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=227 DEFAULT CHARSET=latin1;

/*Table structure for table `project` */

DROP TABLE IF EXISTS `project`;

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
  CONSTRAINT `FK_currentcy_id` FOREIGN KEY (`project_tracking_currency_id`) REFERENCES `currency` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `FK_del_mgr` FOREIGN KEY (`offshore_del_mgr`) REFERENCES `resource` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_eng_mod_id` FOREIGN KEY (`engagement_model_id`) REFERENCES `engagementmodel` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `FK_project` FOREIGN KEY (`customer_name_id`) REFERENCES `customer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_project_invoice_by` FOREIGN KEY (`invoice_by_id`) REFERENCES `invoice_by` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `fk_bu_id` FOREIGN KEY (`bu_id`) REFERENCES `org_hierarchy` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `project_ibfk_1` FOREIGN KEY (`project_category_id`) REFERENCES `project_category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `project_ibfk_2` FOREIGN KEY (`project_methodology_id`) REFERENCES `project_methodology` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `project_ibfk_3` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1127 DEFAULT CHARSET=latin1;

/*Table structure for table `project_aud` */

DROP TABLE IF EXISTS `project_aud`;

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

DROP TABLE IF EXISTS `project_category`;

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

DROP TABLE IF EXISTS `project_methodology`;

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

DROP TABLE IF EXISTS `project_module`;

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
) ENGINE=InnoDB AUTO_INCREMENT=139 DEFAULT CHARSET=utf8;

/*Table structure for table `project_po` */

DROP TABLE IF EXISTS `project_po`;

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Table structure for table `project_po_aud` */

DROP TABLE IF EXISTS `project_po_aud`;

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

/*Table structure for table `project_sub_module` */

DROP TABLE IF EXISTS `project_sub_module`;

CREATE TABLE `project_sub_module` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sub_module_name` varchar(100) DEFAULT NULL,
  `module_id` int(11) DEFAULT NULL,
  `active` tinyint(4) DEFAULT NULL,
  `created_id` varchar(100) DEFAULT NULL,
  `creation_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastupdated_id` varchar(100) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `project_sub_module_ibfk_1` (`module_id`),
  CONSTRAINT `project_sub_module_ibfk_1` FOREIGN KEY (`module_id`) REFERENCES `project_module` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `project_ticket_priority` */

DROP TABLE IF EXISTS `project_ticket_priority`;

CREATE TABLE `project_ticket_priority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `priority` varchar(256) NOT NULL,
  `active` int(11) NOT NULL DEFAULT '0',
  `projectId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ticket_priority_projectId` (`projectId`),
  CONSTRAINT `fk_ticket_priority_projectId` FOREIGN KEY (`projectId`) REFERENCES `project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

/*Table structure for table `project_ticket_status` */

DROP TABLE IF EXISTS `project_ticket_status`;

CREATE TABLE `project_ticket_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(256) NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '0',
  `projectId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ticket_status_projectId` (`projectId`),
  CONSTRAINT `fk_ticket_status_projectId` FOREIGN KEY (`projectId`) REFERENCES `project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

/*Table structure for table `project_type` */

DROP TABLE IF EXISTS `project_type`;

CREATE TABLE `project_type` (
  `type` varchar(50) NOT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `racfid` */

DROP TABLE IF EXISTS `racfid`;

CREATE TABLE `racfid` (
  `racfid` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `rateid` */

DROP TABLE IF EXISTS `rateid`;

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

DROP TABLE IF EXISTS `rating`;

CREATE TABLE `rating` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Table structure for table `reason_for_replacement` */

DROP TABLE IF EXISTS `reason_for_replacement`;

CREATE TABLE `reason_for_replacement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reason` varchar(255) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Table structure for table `region` */

DROP TABLE IF EXISTS `region`;

CREATE TABLE `region` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `unit_id` int(11) DEFAULT NULL,
  `region_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_unit` (`unit_id`)
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8;

/*Table structure for table `release_note` */

DROP TABLE IF EXISTS `release_note`;

CREATE TABLE `release_note` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ReleaseDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ReleaseNumber` varchar(50) DEFAULT NULL,
  `Description` varchar(10000) DEFAULT NULL,
  `Type` varchar(40) DEFAULT NULL COMMENT '1=Enhancement, 2=Defect',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=latin1;

/*Table structure for table `reopen_reason_ost` */

DROP TABLE IF EXISTS `reopen_reason_ost`;

CREATE TABLE `reopen_reason_ost` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `reopen_reason` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Table structure for table `reports_users` */

DROP TABLE IF EXISTS `reports_users`;

CREATE TABLE `reports_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Table structure for table `request` */

DROP TABLE IF EXISTS `request`;

CREATE TABLE `request` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sent_mail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `request_requisition` */

DROP TABLE IF EXISTS `request_requisition`;

CREATE TABLE `request_requisition` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `required_for` varchar(10) DEFAULT NULL COMMENT 'Staffing/ODC',
  `indentor_comments` varchar(255) DEFAULT NULL,
  `date_of_Indent` datetime DEFAULT NULL,
  `project_bu` varchar(255) DEFAULT NULL,
  `project_name` varchar(255) DEFAULT NULL,
  `emp_id` int(11) DEFAULT NULL COMMENT 'this is created by, from loggedin user',
  `project_id` int(11) DEFAULT NULL,
  `sent_mail` varchar(255) DEFAULT NULL,
  `notify_to` varchar(255) DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  `pdl_email` varchar(255) DEFAULT NULL,
  `requestor` int(11) DEFAULT '430' COMMENT 'data comes from resource masterdata.',
  `delivery_poc` int(11) DEFAULT '430' COMMENT 'data comes from resource masterdata.',
  `client_type` varchar(50) DEFAULT NULL COMMENT 'should be New or Exisiting',
  `project_type` varchar(50) DEFAULT NULL COMMENT 'should be New or Exisiting',
  `experience_id` int(11) DEFAULT '1' COMMENT 'FK from experience table',
  `BUH_approval_file` longblob,
  `BUH_approval_file_name` varchar(500) DEFAULT NULL,
  `BGH_approval_file` longblob,
  `BGH_approval_file_name` varchar(500) DEFAULT NULL,
  `project_duration` int(5) DEFAULT NULL COMMENT 'in months.',
  `resource_required_date` date DEFAULT NULL,
  `shift_type_id` int(11) DEFAULT '1' COMMENT 'FK from shift types.',
  `project_shift_other_details` varchar(1000) DEFAULT NULL,
  `isclient_interview_required` varchar(2) DEFAULT NULL COMMENT 'Y or N',
  `isBGV_required` varchar(2) DEFAULT NULL COMMENT 'Y or N',
  `exp_other_details` varchar(1000) DEFAULT NULL COMMENT 'Will be fed only in case of others in experience field',
  `hiring_bgbu` varchar(50) DEFAULT NULL COMMENT 'hiring bg/bg default bg4-bu5',
  `resourceid_for_replacement` int(11) DEFAULT NULL,
  `reasonid` int(11) DEFAULT NULL,
  `created_by` varchar(1000) DEFAULT NULL,
  `creation_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastupdated_by` varchar(1000) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `priorityid` int(11) DEFAULT NULL,
  `buh_id` int(11) DEFAULT NULL,
  `bgh_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK414EF28FB1E33D66D` (`emp_id`),
  KEY `FK414EF28F52A6800D` (`project_id`),
  KEY `customer_id` (`customer_id`),
  KEY `group_id` (`group_id`),
  KEY `requestor` (`requestor`),
  KEY `delivery_poc` (`delivery_poc`),
  KEY `experience_id` (`experience_id`),
  KEY `shift_type_id` (`shift_type_id`),
  KEY `resourceid_for_replacement` (`resourceid_for_replacement`),
  KEY `reasonid` (`reasonid`),
  KEY `priorityid` (`priorityid`),
  KEY `buh_id` (`buh_id`),
  KEY `bgh_id` (`bgh_id`),
  CONSTRAINT `request_requisition_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `request_requisition_ibfk_10` FOREIGN KEY (`reasonid`) REFERENCES `reason_for_replacement` (`id`),
  CONSTRAINT `request_requisition_ibfk_11` FOREIGN KEY (`priorityid`) REFERENCES `rrf_priority` (`id`),
  CONSTRAINT `request_requisition_ibfk_12` FOREIGN KEY (`buh_id`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `request_requisition_ibfk_13` FOREIGN KEY (`bgh_id`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `request_requisition_ibfk_2` FOREIGN KEY (`emp_id`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `request_requisition_ibfk_3` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `request_requisition_ibfk_4` FOREIGN KEY (`group_id`) REFERENCES `customer_group` (`group_id`),
  CONSTRAINT `request_requisition_ibfk_5` FOREIGN KEY (`requestor`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `request_requisition_ibfk_6` FOREIGN KEY (`delivery_poc`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `request_requisition_ibfk_7` FOREIGN KEY (`experience_id`) REFERENCES `experience` (`id`),
  CONSTRAINT `request_requisition_ibfk_8` FOREIGN KEY (`shift_type_id`) REFERENCES `shift_types` (`id`),
  CONSTRAINT `request_requisition_ibfk_9` FOREIGN KEY (`resourceid_for_replacement`) REFERENCES `resource` (`employee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=895 DEFAULT CHARSET=utf8;

/*Table structure for table `res_dt_alloc_temp` */

DROP TABLE IF EXISTS `res_dt_alloc_temp`;

CREATE TABLE `res_dt_alloc_temp` (
  `dt` date DEFAULT NULL,
  `employee_id` bigint(20) DEFAULT NULL,
  `allocationtype_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `res_dt_temp` */

DROP TABLE IF EXISTS `res_dt_temp`;

CREATE TABLE `res_dt_temp` (
  `dt` date DEFAULT NULL,
  `employee_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `resource` */

DROP TABLE IF EXISTS `resource`;

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
  `upload_tef` longblob,
  `upload_tef_file_name` varchar(250) DEFAULT NULL,
  `upload_Resume` longblob,
  `upload_resume_file_name` varchar(250) DEFAULT NULL,
  `total_experience` decimal(11,2) NOT NULL,
  `relevant_experience` decimal(11,2) NOT NULL,
  `rrfAccess` char(1) DEFAULT NULL,
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
  CONSTRAINT `FK_resource_rm2` FOREIGN KEY (`current_reporting_manager_two`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `fk_report_user_id` FOREIGN KEY (`report_user_id`) REFERENCES `reports_users` (`id`),
  CONSTRAINT `resource_ibfk_1` FOREIGN KEY (`bGH_Name`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `resource_ibfk_2` FOREIGN KEY (`bU_Name`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `resource_ibfk_3` FOREIGN KEY (`hR_Name`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `resource_ibfk_4` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`),
  CONSTRAINT `resource_ibfk_5` FOREIGN KEY (`employee_category`) REFERENCES `employee_category` (`id`),
  CONSTRAINT `resource_ibfk_6` FOREIGN KEY (`competency`) REFERENCES `competency` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5991 DEFAULT CHARSET=latin1;

/*Table structure for table `resource_allocation` */

DROP TABLE IF EXISTS `resource_allocation`;

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
  `project_end_remarks` varchar(1000) DEFAULT 'NA',
  `resource_release_summary_id` int(11) DEFAULT NULL,
  `resource_type` varchar(256) DEFAULT 'Internal',
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
  KEY `resource_allocation_resource_release_summary` (`resource_release_summary_id`),
  CONSTRAINT `FK_resource_allocation-RM1` FOREIGN KEY (`current_reporting_manager`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `FK_resource_allocation_RM2` FOREIGN KEY (`current_reporting_manager_two`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `FK_resource_allocation_by` FOREIGN KEY (`allocated_by`) REFERENCES `resource` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_resource_allocation_currentBu` FOREIGN KEY (`current_bu_id`) REFERENCES `org_hierarchy` (`id`),
  CONSTRAINT `FK_resource_allocation_designation` FOREIGN KEY (`designation_id`) REFERENCES `designations` (`id`),
  CONSTRAINT `FK_resource_allocation_grade` FOREIGN KEY (`grade_id`) REFERENCES `grade` (`id`),
  CONSTRAINT `FK_resource_allocation_location` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`),
  CONSTRAINT `FK_resource_allocation_parentBu` FOREIGN KEY (`bu_id`) REFERENCES `org_hierarchy` (`id`),
  CONSTRAINT `FK_resource_updated_by` FOREIGN KEY (`updated_by`) REFERENCES `resource` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `resource_allocation_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `resource` (`employee_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `resource_allocation_ibfk_2` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `resource_allocation_ibfk_4` FOREIGN KEY (`ownership_id`) REFERENCES `ownership` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `resource_allocation_ibfk_5` FOREIGN KEY (`allocation_type_id`) REFERENCES `allocationtype` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `resource_allocation_resource_release_summary` FOREIGN KEY (`resource_release_summary_id`) REFERENCES `resource_release_feedback` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14096 DEFAULT CHARSET=latin1;

/*Table structure for table `resource_allocation_aud` */

DROP TABLE IF EXISTS `resource_allocation_aud`;

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
  `resource_type` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`,`REV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `resource_aud` */

DROP TABLE IF EXISTS `resource_aud`;

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

/*Table structure for table `resource_comment` */

DROP TABLE IF EXISTS `resource_comment`;

CREATE TABLE `resource_comment` (
  `resource_Comment_id` int(20) NOT NULL AUTO_INCREMENT,
  `resource_id` int(20) NOT NULL,
  `comment_date` timestamp NULL DEFAULT NULL,
  `resource_comment` varchar(500) DEFAULT NULL,
  `comment_by` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`resource_Comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=204 DEFAULT CHARSET=utf8;

/*Table structure for table `resource_ln_tr_dtl` */

DROP TABLE IF EXISTS `resource_ln_tr_dtl`;

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
  CONSTRAINT `FK_Current_RM1_LT` FOREIGN KEY (`current_reporting_manager`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `FK_Current_RM2_LT` FOREIGN KEY (`current_reporting_two`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `FK_Current_bu_id_LT` FOREIGN KEY (`current_bu_id`) REFERENCES `org_hierarchy` (`id`),
  CONSTRAINT `FK_Designation_LT` FOREIGN KEY (`designation_id`) REFERENCES `designations` (`id`),
  CONSTRAINT `FK_Location` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`),
  CONSTRAINT `FK_Payroll_lcation_LT` FOREIGN KEY (`payroll_location`) REFERENCES `location` (`id`),
  CONSTRAINT `FK_Resource_LT` FOREIGN KEY (`employee_id`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `FK_grade_LT` FOREIGN KEY (`grade_id`) REFERENCES `grade` (`id`),
  CONSTRAINT `FK_ownership_LT` FOREIGN KEY (`ownership`) REFERENCES `ownership` (`id`),
  CONSTRAINT `resource_ln_tr_dtl_ibfk_1` FOREIGN KEY (`bGH_Name`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `resource_ln_tr_dtl_ibfk_2` FOREIGN KEY (`bU_Name`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `resource_ln_tr_dtl_ibfk_3` FOREIGN KEY (`hR_Name`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `resource_ln_tr_dtl_ibfk_4` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`),
  CONSTRAINT `resource_ln_tr_dtl_ibfk_5` FOREIGN KEY (`employee_category`) REFERENCES `employee_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15738 DEFAULT CHARSET=latin1;

/*Table structure for table `resource_release_feedback` */

DROP TABLE IF EXISTS `resource_release_feedback`;

CREATE TABLE `resource_release_feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `training_if_any` varchar(256) DEFAULT NULL,
  `team_handle_experiance` varchar(256) DEFAULT NULL,
  `kt_status` varchar(20) DEFAULT NULL,
  `reason_of_release` varchar(500) DEFAULT NULL,
  `pip_status` varchar(20) DEFAULT NULL,
  `job_knowledge_rating` int(11) DEFAULT NULL,
  `work_qlty_rating` int(11) DEFAULT NULL,
  `attandance_rating` int(11) DEFAULT NULL,
  `intiative_rating` int(11) DEFAULT NULL,
  `communication_rating` int(11) DEFAULT NULL,
  `listing_skills_rating` int(11) DEFAULT NULL,
  `dependability_rating` int(11) DEFAULT NULL,
  `over_all_rating` int(11) DEFAULT NULL,
  `primary_skills` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8;

/*Table structure for table `resource_required_for` */

DROP TABLE IF EXISTS `resource_required_for`;

CREATE TABLE `resource_required_for` (
  `type` varchar(50) NOT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `revinfo` */

DROP TABLE IF EXISTS `revinfo`;

CREATE TABLE `revinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` bigint(20) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=94805 DEFAULT CHARSET=utf8;

/*Table structure for table `rework_ost` */

DROP TABLE IF EXISTS `rework_ost`;

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Table structure for table `root_cause_ost` */

DROP TABLE IF EXISTS `root_cause_ost`;

CREATE TABLE `root_cause_ost` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `root_cause` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;

/*Table structure for table `root_cause_ost_old` */

DROP TABLE IF EXISTS `root_cause_ost_old`;

CREATE TABLE `root_cause_ost_old` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `root_cause` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;

/*Table structure for table `rrf_priority` */

DROP TABLE IF EXISTS `rrf_priority`;

CREATE TABLE `rrf_priority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `priority_type` varchar(100) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `service_request_audit` */

DROP TABLE IF EXISTS `service_request_audit`;

CREATE TABLE `service_request_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `employee_id` varchar(256) NOT NULL,
  `service_api` varchar(256) NOT NULL,
  `request_type` varchar(256) NOT NULL,
  `success` char(1) DEFAULT 'N',
  `time_for_request` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=latin1;

/*Table structure for table `seskill_resource_secondary` */

DROP TABLE IF EXISTS `seskill_resource_secondary`;

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

/*Table structure for table `shift_types` */

DROP TABLE IF EXISTS `shift_types`;

CREATE TABLE `shift_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `shift_timings` varchar(32) DEFAULT NULL,
  `timezone_name` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Table structure for table `skill_profile_primary` */

DROP TABLE IF EXISTS `skill_profile_primary`;

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
) ENGINE=InnoDB AUTO_INCREMENT=5562 DEFAULT CHARSET=utf8;

/*Table structure for table `skill_profile_secondary` */

DROP TABLE IF EXISTS `skill_profile_secondary`;

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
) ENGINE=InnoDB AUTO_INCREMENT=2423 DEFAULT CHARSET=utf8;

/*Table structure for table `skill_request_requisition` */

DROP TABLE IF EXISTS `skill_request_requisition`;

CREATE TABLE `skill_request_requisition` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `additional_comments` varchar(3000) DEFAULT NULL,
  `billable` int(11) DEFAULT NULL,
  `career_growth_plan` varchar(3000) DEFAULT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `desirable_skills` varchar(3000) DEFAULT NULL,
  `experience` varchar(255) DEFAULT NULL,
  `fulfilled` varchar(255) DEFAULT NULL,
  `key_interviewers_one` varchar(255) DEFAULT NULL,
  `key_interviewers_two` varchar(255) DEFAULT NULL,
  `key_scanners` varchar(255) DEFAULT NULL,
  `req_resources` int(11) DEFAULT NULL,
  `primary_skills` varchar(45000) DEFAULT NULL,
  `remaining` varchar(255) DEFAULT NULL,
  `responsibilities` varchar(3000) DEFAULT NULL,
  `target_companies` varchar(3000) DEFAULT NULL,
  `time_frame` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `designation` int(11) NOT NULL,
  `request_id` int(11) NOT NULL,
  `skills` int(11) NOT NULL,
  `req_id` varchar(255) DEFAULT NULL,
  `on_hold` int(5) DEFAULT '0',
  `open` int(5) DEFAULT NULL,
  `closed` int(5) DEFAULT '0',
  `not_fit_for_requirement` int(5) DEFAULT '0',
  `shortlisted` int(5) DEFAULT '0',
  `submissions` int(5) DEFAULT '0',
  `lost` int(5) DEFAULT '0',
  `sent_req_to` varchar(255) DEFAULT NULL,
  `pdl_list` varchar(255) DEFAULT NULL,
  `location_id` int(10) DEFAULT '1',
  `STATUS` varchar(100) DEFAULT 'Open',
  `skills_to_evaluate` varchar(1000) DEFAULT NULL,
  `closure_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `billable` (`billable`),
  KEY `designation` (`designation`),
  KEY `skill_request_requisition_ibfk_2` (`skills`),
  KEY `skill_request_requisition_ibfk_4` (`request_id`),
  KEY `skill_request_requisition_ibfk_5` (`location_id`),
  CONSTRAINT `skill_request_requisition_ibfk_1` FOREIGN KEY (`billable`) REFERENCES `allocationtype` (`id`),
  CONSTRAINT `skill_request_requisition_ibfk_2` FOREIGN KEY (`skills`) REFERENCES `competency` (`id`),
  CONSTRAINT `skill_request_requisition_ibfk_3` FOREIGN KEY (`designation`) REFERENCES `designations` (`id`),
  CONSTRAINT `skill_request_requisition_ibfk_4` FOREIGN KEY (`request_id`) REFERENCES `request_requisition` (`id`) ON DELETE CASCADE,
  CONSTRAINT `skill_request_requisition_ibfk_5` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=860 DEFAULT CHARSET=latin1;

/*Table structure for table `skill_request_resource` */

DROP TABLE IF EXISTS `skill_request_resource`;

CREATE TABLE `skill_request_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `external_resource_name` varchar(255) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `skill_request_id` int(11) NOT NULL,
  `status_id` int(11) DEFAULT NULL,
  `allocation_id` int(11) DEFAULT NULL,
  `allocation_date` date DEFAULT NULL,
  `interview_date` date DEFAULT NULL,
  `upload_resume` longblob,
  `resume_file_name` varchar(250) DEFAULT NULL,
  `upload_tac_file_name` varchar(255) DEFAULT NULL,
  `upload_Tac_File` longblob,
  `location` varchar(256) DEFAULT NULL,
  `notice_period` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `dt_submitted_to_poc` datetime DEFAULT NULL,
  `dt_submitted_to_am` datetime DEFAULT NULL,
  `dt_joined` datetime DEFAULT NULL,
  `dt_shortlisted` datetime DEFAULT NULL,
  `dt_rejection` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7D50D7EC48EAF467` (`resource_id`),
  KEY `FK7D50D7ECC1B8B964` (`skill_request_id`),
  KEY `FK7D50D7EC90A65CA6` (`status_id`),
  KEY `FK7D50D7ECCB7AF8C1` (`allocation_id`),
  CONSTRAINT `FK7D50D7EC48EAF467` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`employee_id`),
  CONSTRAINT `FK7D50D7EC90A65CA6` FOREIGN KEY (`status_id`) REFERENCES `skill_resource_status_type` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK7D50D7ECC1B8B964` FOREIGN KEY (`skill_request_id`) REFERENCES `skill_request_requisition` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK7D50D7ECCB7AF8C1` FOREIGN KEY (`allocation_id`) REFERENCES `allocationtype` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1386 DEFAULT CHARSET=latin1;

/*Table structure for table `skill_resource_primary` */

DROP TABLE IF EXISTS `skill_resource_primary`;

CREATE TABLE `skill_resource_primary` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `skill_id` int(11) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `primaryExperience` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_skill_resource_1` (`resource_id`),
  KEY `skill_id` (`skill_id`),
  CONSTRAINT `skill_resource_primary_ibfk_1` FOREIGN KEY (`skill_id`) REFERENCES `skills` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `skill_resource_primary_ibfk_2` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`employee_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20110 DEFAULT CHARSET=utf8;

/*Table structure for table `skill_resource_secondary` */

DROP TABLE IF EXISTS `skill_resource_secondary`;

CREATE TABLE `skill_resource_secondary` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `skill_id` int(11) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `secondaryExperience` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_skill_resource_2` (`resource_id`),
  KEY `skill_id` (`skill_id`),
  CONSTRAINT `skill_resource_secondary_ibfk_1` FOREIGN KEY (`skill_id`) REFERENCES `skills` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `skill_resource_secondary_ibfk_2` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`employee_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12784 DEFAULT CHARSET=utf8;

/*Table structure for table `skill_resource_status_type` */

DROP TABLE IF EXISTS `skill_resource_status_type`;

CREATE TABLE `skill_resource_status_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status_name` varchar(255) DEFAULT NULL,
  `status_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;

/*Table structure for table `skills` */

DROP TABLE IF EXISTS `skills`;

CREATE TABLE `skills` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `skill` varchar(256) NOT NULL,
  `skill_type` varchar(256) NOT NULL,
  `created_id` varchar(256) DEFAULT NULL,
  `creation_timestamp` timestamp NULL DEFAULT NULL,
  `lastupdated_id` varchar(256) DEFAULT NULL,
  `lastupdated_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1008 DEFAULT CHARSET=latin1;

/*Table structure for table `slamissed_reason_ost` */

DROP TABLE IF EXISTS `slamissed_reason_ost`;

CREATE TABLE `slamissed_reason_ost` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `sla_missed` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;

/*Table structure for table `solution_review_ost` */

DROP TABLE IF EXISTS `solution_review_ost`;

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
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8;

/*Table structure for table `subprocess_ost` */

DROP TABLE IF EXISTS `subprocess_ost`;

CREATE TABLE `subprocess_ost` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `process_id` int(10) DEFAULT NULL,
  `subprocess_name` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `process_id` (`process_id`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=770 DEFAULT CHARSET=latin1;

/*Table structure for table `t3_contribution_screen_ost` */

DROP TABLE IF EXISTS `t3_contribution_screen_ost`;

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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Table structure for table `team` */

DROP TABLE IF EXISTS `team`;

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
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=latin1;

/*Table structure for table `team_view_access` */

DROP TABLE IF EXISTS `team_view_access`;

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

DROP TABLE IF EXISTS `timehrs`;

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
) ENGINE=InnoDB AUTO_INCREMENT=82419 DEFAULT CHARSET=latin1;

/*Table structure for table `timehrs_aud` */

DROP TABLE IF EXISTS `timehrs_aud`;

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

DROP TABLE IF EXISTS `unit_ost`;

CREATE TABLE `unit_ost` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `unit_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8;

/*Table structure for table `user_activity` */

DROP TABLE IF EXISTS `user_activity`;

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
  `sub_module` varchar(250) DEFAULT NULL,
  `ticket_priority` varchar(255) DEFAULT NULL,
  `ticket_status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_user_activity` (`activity_id`),
  KEY `FK_user_activity_resalloc` (`res_alloc_id`),
  KEY `fk_custum_actvity` (`custom_activity_id`),
  CONSTRAINT `fk_custum_actvity` FOREIGN KEY (`custom_activity_id`) REFERENCES `custom_activity` (`id`),
  CONSTRAINT `fk_resalloc` FOREIGN KEY (`res_alloc_id`) REFERENCES `resource_allocation` (`id`),
  CONSTRAINT `user_activity_ibfk_1` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=313893 DEFAULT CHARSET=utf8;

/*Table structure for table `user_activity_aud` */

DROP TABLE IF EXISTS `user_activity_aud`;

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

DROP TABLE IF EXISTS `user_activity_detail`;

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
) ENGINE=InnoDB AUTO_INCREMENT=378631 DEFAULT CHARSET=utf8;

/*Table structure for table `user_activity_detail_aud` */

DROP TABLE IF EXISTS `user_activity_detail_aud`;

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

DROP TABLE IF EXISTS `user_notification`;

CREATE TABLE `user_notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `employee_id` int(11) NOT NULL,
  `msg` varchar(256) NOT NULL,
  `is_read` tinyint(1) NOT NULL DEFAULT '0',
  `msg_type` varchar(256) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_user_notification_resource` (`employee_id`),
  CONSTRAINT `FK_user_notification_resource` FOREIGN KEY (`employee_id`) REFERENCES `resource` (`employee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=225818 DEFAULT CHARSET=latin1;

/*Table structure for table `user_profile` */

DROP TABLE IF EXISTS `user_profile`;

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

DROP TABLE IF EXISTS `visa`;

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

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

/*Table structure for table `newjoinertracker` */

CREATE TABLE newjoinertracker (
 id INT (11) NOT NULL AUTO_INCREMENT,
  business_group VARCHAR (256),
  business_unit VARCHAR (256),
  business_group_head VARCHAR (256),
  business_unit_head VARCHAR (256),
  reporting_to VARCHAR (256),
  nature_of_hiring VARCHAR (256),
  client_hiring_for VARCHAR (256),
  hire_name VARCHAR (256) NOT NULL,
  skills VARCHAR (256),
  designation VARCHAR (256),
  grade VARCHAR (256),
  expected_date_of_joining DATE NOT NULL,
  base_location VARCHAR (256),
  joining_status VARCHAR (256) NOT NULL,
  reporting_location VARCHAR (256),
  lwd VARCHAR (256),
  remark VARCHAR (256),
  latest_resume LONGBLOB,
  PRIMARY KEY (id)
) ENGINE = INNODB AUTO_INCREMENT = 4927 DEFAULT CHARSET = latin1 ;
