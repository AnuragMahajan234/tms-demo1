/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.6.30-log : Database - rms_4.0_prod
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`rms_4.0_prod` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `rms_4.0_prod`;

/* Trigger structure for table `bgadmin_access_rights` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `bgadmin_access_trigger_insert` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `bgadmin_access_trigger_insert` AFTER INSERT ON `bgadmin_access_rights` FOR EACH ROW BEGIN
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

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `bgadmin_access_trigger_update` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `bgadmin_access_trigger_update` AFTER UPDATE ON `bgadmin_access_rights` FOR EACH ROW BEGIN
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

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `bgadmin_access_trigger_delete` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `bgadmin_access_trigger_delete` AFTER DELETE ON `bgadmin_access_rights` FOR EACH ROW BEGIN
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

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `customer_trigger_insert` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `customer_trigger_insert` AFTER INSERT ON `customer` FOR EACH ROW BEGIN
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

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `customer_trigger_update` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `customer_trigger_update` AFTER UPDATE ON `customer` FOR EACH ROW BEGIN
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

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `customer_trigger_delete` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `customer_trigger_delete` AFTER DELETE ON `customer` FOR EACH ROW BEGIN
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

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `org_hierarchy_trigger_insert` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `org_hierarchy_trigger_insert` AFTER INSERT ON `org_hierarchy` FOR EACH ROW BEGIN
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

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `org_hierarchy_trigger_update` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `org_hierarchy_trigger_update` AFTER UPDATE ON `org_hierarchy` FOR EACH ROW BEGIN
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

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `org_hierarchy_trigger_delete` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `org_hierarchy_trigger_delete` AFTER DELETE ON `org_hierarchy` FOR EACH ROW BEGIN
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

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `project_trigger_insert` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `project_trigger_insert` AFTER INSERT ON `project` FOR EACH ROW BEGIN
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

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `project_trigger_update_before` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `project_trigger_update_before` BEFORE UPDATE ON `project` FOR EACH ROW BEGIN
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

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `project_trigger_update` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `project_trigger_update` AFTER UPDATE ON `project` FOR EACH ROW BEGIN
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

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `project_trigger_delete` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `project_trigger_delete` AFTER DELETE ON `project` FOR EACH ROW BEGIN
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

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `project_po_trigger_insert` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `project_po_trigger_insert` AFTER INSERT ON `project_po` FOR EACH ROW BEGIN
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

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `project_po_trigger_update` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `project_po_trigger_update` AFTER UPDATE ON `project_po` FOR EACH ROW BEGIN
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

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `project_po_trigger_delete` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `project_po_trigger_delete` AFTER DELETE ON `project_po` FOR EACH ROW BEGIN
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

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `resource_trigger_insert` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `resource_trigger_insert` AFTER INSERT ON `resource` FOR EACH ROW BEGIN
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

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `resource_trigger_update` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `resource_trigger_update` AFTER UPDATE ON `resource` FOR EACH ROW BEGIN
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

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `resource_trigger_delete` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `resource_trigger_delete` AFTER DELETE ON `resource` FOR EACH ROW BEGIN
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

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `resource_allocation_trigger_insert` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `resource_allocation_trigger_insert` AFTER INSERT ON `resource_allocation` FOR EACH ROW BEGIN
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
    lastupdated_timestamp,
	resource_type
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
   NEW . lastupdated_timestamp,
   NEW . resource_type
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `resource_allocation` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `resource_allocation_trigger_update` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `resource_allocation_trigger_update` AFTER UPDATE ON `resource_allocation` FOR EACH ROW BEGIN
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
    lastupdated_timestamp,
	resource_type
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
   NEW . lastupdated_timestamp,
   NEW . resource_type
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `resource_allocation` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `resource_allocation_trigger_delete` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `resource_allocation_trigger_delete` AFTER DELETE ON `resource_allocation` FOR EACH ROW BEGIN
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
    lastupdated_timestamp,
	resource_type
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
   OLD . lastupdated_timestamp,
   OLD . resource_type
    ) ;
END */$$


DELIMITER ;

/* Trigger structure for table `timehrs` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `timehrs_trigger_insert` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `timehrs_trigger_insert` AFTER INSERT ON `timehrs` FOR EACH ROW BEGIN
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

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `timehrs_trigger_update` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `timehrs_trigger_update` AFTER UPDATE ON `timehrs` FOR EACH ROW BEGIN
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

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `timehrs_trigger_delete` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `timehrs_trigger_delete` AFTER DELETE ON `timehrs` FOR EACH ROW BEGIN
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

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `user_activity_trigger_insert` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `user_activity_trigger_insert` AFTER INSERT ON `user_activity` FOR EACH ROW BEGIN
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

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `user_activity_trigger_update` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `user_activity_trigger_update` AFTER UPDATE ON `user_activity` FOR EACH ROW BEGIN
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

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `user_activity_trigger_delete` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `user_activity_trigger_delete` AFTER DELETE ON `user_activity` FOR EACH ROW BEGIN
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

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `user_activity_detail_trigger_insert` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `user_activity_detail_trigger_insert` AFTER INSERT ON `user_activity_detail` FOR EACH ROW BEGIN
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

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `user_activity_detail_trigger_update` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `user_activity_detail_trigger_update` AFTER UPDATE ON `user_activity_detail` FOR EACH ROW BEGIN
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

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `user_activity_detail_trigger_delete` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `user_activity_detail_trigger_delete` AFTER DELETE ON `user_activity_detail` FOR EACH ROW BEGIN
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

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
