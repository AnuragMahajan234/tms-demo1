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

/*!50106 set global event_scheduler = 1*/;

/* Event structure for event `event_set_current_project` */

/*!50106 DROP EVENT IF EXISTS `event_set_current_project`*/;

DELIMITER $$

/*!50106 CREATE DEFINER=`root`@`localhost` EVENT `event_set_current_project` ON SCHEDULE EVERY 1 DAY STARTS '2018-02-14 02:00:00' ON COMPLETION NOT PRESERVE ENABLE DO BEGIN
	 CALL `usp_set_current_project`();
	END */$$
DELIMITER ;

/* Event structure for event `event_set_dashboard_data` */

/*!50106 DROP EVENT IF EXISTS `event_set_dashboard_data`*/;

DELIMITER $$

/*!50106 CREATE DEFINER=`root`@`%` EVENT `event_set_dashboard_data` ON SCHEDULE EVERY 1 DAY STARTS '2016-06-11 13:43:00' ON COMPLETION NOT PRESERVE ENABLE DO BEGIN
	 CALL `prepare_project_data`();
	 CALL `prepare_project_data_second`();
	 CALL `prepare_project_widget_data`();
	END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
