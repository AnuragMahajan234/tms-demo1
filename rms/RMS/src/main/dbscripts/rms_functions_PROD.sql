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

/* Function  structure for function  `FIRST_DAY_OF_WEEK` */

/*!50003 DROP FUNCTION IF EXISTS `FIRST_DAY_OF_WEEK` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` FUNCTION `FIRST_DAY_OF_WEEK`(DAY DATE) RETURNS date
    DETERMINISTIC
BEGIN
  RETURN SUBDATE(DAY, (WEEKDAY(DAY)+1));
END */$$
DELIMITER ;

/* Function  structure for function  `LAST_DAY_OF_WEEK` */

/*!50003 DROP FUNCTION IF EXISTS `LAST_DAY_OF_WEEK` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` FUNCTION `LAST_DAY_OF_WEEK`(DAY DATE) RETURNS date
    DETERMINISTIC
BEGIN
  RETURN ADDDATE(DAY, INTERVAL 5-WEEKDAY(DAY) DAY);
END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
