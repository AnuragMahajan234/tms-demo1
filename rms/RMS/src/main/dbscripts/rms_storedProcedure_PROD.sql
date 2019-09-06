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

/* Procedure structure for procedure `prepare_project_data` */

/*!50003 DROP PROCEDURE IF EXISTS  `prepare_project_data` */;

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

/*!50003 DROP PROCEDURE IF EXISTS  `prepare_project_data_second` */;

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

/*!50003 DROP PROCEDURE IF EXISTS  `prepare_project_widget_data` */;

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

/* Procedure structure for procedure `RMG_Bench_Grade_DaysWise_Report` */

/*!50003 DROP PROCEDURE IF EXISTS  `RMG_Bench_Grade_DaysWise_Report` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` PROCEDURE `RMG_Bench_Grade_DaysWise_Report`(IN start_date CHAR(2), 
      IN end_date CHAR(2),IN start_month VARCHAR(2),
	  IN end_month VARCHAR(2),IN start_year CHAR(4), IN end_year VARCHAR(4))
BEGIN
SET @STARTDATE = CONCAT(`start_year`,'-',`start_month`,'-',`start_date`);
SET @ENDDATE = CONCAT(`end_year`,'-',`end_month`,'-',`end_date`);
SELECT X.grade 'Grade',Days_on_Bench 'Days on Bench',Resources_on_Bench 'Resources on Bench',
    ROUND((Days_on_Bench/Resources_on_Bench),0) 'Average Bench Period' FROM
(SELECT A.grade,SUM(No_of_Days) 'Days_on_Bench' FROM 
(SELECT employee_id,alloc_start_date,alloc_end_date,g.grade,a.allocationtype,SUM(DATEDIFF(COALESCE(alloc_end_date,NOW()),alloc_start_date)) 'No_of_Days'
FROM resource_allocation r, grade g, allocationtype a
WHERE r.grade_id=g.id AND r.allocation_type_id=a.id AND @ENDDATE>alloc_start_date 
      AND SYSDATE()>alloc_end_date AND SYSDATE()>alloc_start_date 
      AND @ENDDATE BETWEEN alloc_start_date AND alloc_end_date
      AND allocation_old LIKE '%BENCH%'
GROUP BY employee_id) A
GROUP BY A.grade) X JOIN
(SELECT B.grade,COUNT(employee_id) 'Resources_on_Bench' FROM 
(SELECT employee_id,alloc_start_date,alloc_end_date,g.grade,a.allocationtype,SUM(DATEDIFF(COALESCE(alloc_end_date,NOW()),alloc_start_date)) 'No_of_Days'
FROM resource_allocation r, grade g, allocationtype a
WHERE r.grade_id=g.id AND r.allocation_type_id=a.id AND @ENDDATE>alloc_start_date 
      AND SYSDATE()>alloc_end_date
      AND @ENDDATE BETWEEN alloc_start_date AND alloc_end_date
      AND allocation_old LIKE '%BENCH%'
GROUP BY employee_id) B
GROUP BY B.grade)Y ON X.grade=Y.grade;
END */$$
DELIMITER ;

/* Procedure structure for procedure `RMG_Bench_Grade_Report` */

/*!50003 DROP PROCEDURE IF EXISTS  `RMG_Bench_Grade_Report` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` PROCEDURE `RMG_Bench_Grade_Report`(IN start_date CHAR(2), 
      IN end_date CHAR(2),IN start_month VARCHAR(2),
	  IN end_month VARCHAR(2),IN start_year CHAR(4), IN end_year VARCHAR(4))
BEGIN
SET @STARTDATE = CONCAT(`start_year`,'-',`start_month`,'-',`start_date`);
SET @ENDDATE = CONCAT(`end_year`,'-',`end_month`,'-',`end_date`);
SELECT Y1.grade,0_to_1_month_on_Bench '0 to 1 Month on Bench',1_to_2_month_on_Bench '1 to 2 Month on Bench',
  2_to_3_month_on_Bench '2 to 3 Month on Bench',More_than_3_Months_on_Bench 'More than 3 Months on Bench' FROM
(SELECT Z.grade,0_to_1_month_on_Bench,1_to_2_month_on_Bench,2_to_3_month_on_Bench FROM
(SELECT X.grade,0_to_1_month_on_Bench,1_to_2_month_on_Bench FROM
(SELECT TEMP.grade grade,0_to_1_month_on_Bench FROM
(SELECT DISTINCT(grade) Grade,0 FROM grade) TEMP LEFT OUTER JOIN
(SELECT grade,COUNT(A.employee_id)'0_to_1_month_on_Bench'  FROM
(SELECT employee_id,alloc_start_date,alloc_end_date,g.grade,a.allocationtype,SUM(DATEDIFF(COALESCE(alloc_end_date,NOW()),alloc_start_date)) 'No_of_Days'
FROM resource_allocation r, grade g, allocationtype a
WHERE r.grade_id=g.id AND r.allocation_type_id=a.id AND @ENDDATE>alloc_start_date 
      AND @ENDDATE BETWEEN alloc_start_date AND alloc_end_date
      AND allocation_old LIKE '%BENCH%'
GROUP BY employee_id) A
WHERE No_of_Days<=30
 GROUP BY grade) X1
ON TEMP.Grade=X1.grade) X LEFT OUTER JOIN
(SELECT grade,COUNT(A.employee_id) '1_to_2_month_on_Bench' FROM
(SELECT employee_id,alloc_start_date,alloc_end_date,g.grade,a.allocationtype,SUM(DATEDIFF(COALESCE(alloc_end_date,NOW()),alloc_start_date)) 'No_of_Days'
FROM resource_allocation r, grade g, allocationtype a
WHERE r.grade_id=g.id AND r.allocation_type_id=a.id AND @ENDDATE>alloc_start_date 
      AND @ENDDATE BETWEEN alloc_start_date AND alloc_end_date
      AND allocation_old LIKE '%BENCH%'
GROUP BY employee_id) A
WHERE No_of_Days>30 AND No_of_Days<61
 GROUP BY grade)Y
 ON X.grade=Y.grade) Z LEFT OUTER JOIN
 (SELECT grade,COUNT(A.employee_id) '2_to_3_month_on_Bench' FROM
(SELECT employee_id,alloc_start_date,alloc_end_date,g.grade,a.allocationtype,SUM(DATEDIFF(COALESCE(alloc_end_date,NOW()),alloc_start_date)) 'No_of_Days'
FROM resource_allocation r, grade g, allocationtype a
WHERE r.grade_id=g.id AND r.allocation_type_id=a.id AND @ENDDATE>alloc_start_date 
      AND @ENDDATE BETWEEN alloc_start_date AND alloc_end_date
      AND allocation_old LIKE '%BENCH%'
GROUP BY employee_id) A
WHERE No_of_Days>60 AND No_of_Days<=90
GROUP BY grade) X1 ON Z.grade=X1.grade) Y1 LEFT OUTER JOIN
 (SELECT grade,COUNT(A.employee_id) 'More_than_3_Months_on_Bench' FROM
(SELECT employee_id,alloc_start_date,alloc_end_date,g.grade,a.allocationtype,SUM(DATEDIFF(COALESCE(alloc_end_date,NOW()),alloc_start_date)) 'No_of_Days'
FROM resource_allocation r, grade g, allocationtype a
WHERE r.grade_id=g.id AND r.allocation_type_id=a.id AND @ENDDATE>alloc_start_date 
      AND @ENDDATE BETWEEN alloc_start_date AND alloc_end_date
      AND allocation_old LIKE '%BENCH%'
GROUP BY employee_id) A
WHERE No_of_Days>90
GROUP BY grade) Z1
ON Z1.grade=Y1.grade;
END */$$
DELIMITER ;

/* Procedure structure for procedure `RMG_Bench_Skill_Report` */

/*!50003 DROP PROCEDURE IF EXISTS  `RMG_Bench_Skill_Report` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` PROCEDURE `RMG_Bench_Skill_Report`(IN start_date CHAR(2), 
      IN end_date CHAR(2),IN start_month VARCHAR(2),
	  IN end_month VARCHAR(2),IN start_year CHAR(4), IN end_year VARCHAR(4))
BEGIN
SET @STARTDATE = CONCAT(`start_year`,'-',`start_month`,'-',`start_date`);
SET @ENDDATE = CONCAT(`end_year`,'-',`end_month`,'-',`end_date`);
SELECT rtrim(ltrim(Final.BG)),rtrim(ltrim(Final.BU)),Final.Skill,Final.A '0 To 1 Months on Bench',Final.B '1 To 2 Month on Bench',
Final.C '2 To 3 Month on Bench',Final.D 'More Than 3 Months on Bench' FROM
(SELECT  Z3.BG,Z3.BU,Z3.Skill,COALESCE(0_to_1_month_on_Bench,0) A,COALESCE(1_to_2_month_on_Bench,0) B,
COALESCE(2_to_3_month_on_Bench,0) C,COALESCE(More_than_3_months_on_bench,0) D FROM
(SELECT  Z1.BG,Z1.BU,Z1.Skill,0_to_1_month_on_Bench,1_to_2_month_on_Bench,2_to_3_month_on_Bench FROM
 (SELECT X.BG,X.BU,X.Skill,0_to_1_month_on_Bench,1_to_2_month_on_Bench FROM
 (SELECT Temp.BG,Temp.BU,Temp.Skill,0_to_1_month_on_Bench FROM
 (SELECT BG,BU,Skill FROM 
 (SELECT '1' T1,o1.name BG,o2.name BU
 FROM org_hierarchy o1, org_hierarchy o2, resource r
 WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id 
 GROUP BY o1.name,o2.name
 ORDER BY o1.name,o2.name) A, 
(SELECT '1' T2,skill FROM competency) B
WHERE T1=T2) Temp LEFT OUTER JOIN
 (SELECT BG,BU,skill,COUNT(A.employee_id)'0_to_1_month_on_Bench' FROM
(SELECT o1.name BG,o2.name BU,c.skill,ra.employee_id,alloc_start_date,alloc_end_date,
a.allocationtype,DATEDIFF(COALESCE(alloc_end_date,NOW()),alloc_start_date) 'No_of_Days'
FROM org_hierarchy o1, org_hierarchy o2, resource r, competency c,resource_allocation ra, 
allocationtype a
WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND r.competency=c.id AND ra.employee_id=r.employee_id
AND ra.employee_id=r.employee_id AND allocation_old LIKE '%BENCH%' 
AND ra.allocation_type_id=a.id AND @ENDDATE>alloc_start_date AND (@ENDDATE BETWEEN alloc_start_date AND alloc_end_date)
GROUP BY employee_id
ORDER BY  BG ASC,BU ASC,Skill DESC) A
WHERE No_of_Days>0 AND No_of_Days<=30
GROUP BY BG,BU,Skill) X1 ON 
(X1.BG=Temp.BG AND X1.BU=Temp.BU AND X1.Skill=Temp.Skill))X LEFT OUTER JOIN
(SELECT BG,BU,skill,COUNT(A.employee_id)'1_to_2_month_on_Bench' FROM
(SELECT o1.name BG,o2.name BU,c.skill,ra.employee_id,alloc_start_date,alloc_end_date,
a.allocationtype,DATEDIFF(COALESCE(alloc_end_date,NOW()),alloc_start_date) 'No_of_Days'
FROM org_hierarchy o1, org_hierarchy o2, resource r, competency c,resource_allocation ra, 
allocationtype a
WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND r.competency=c.id AND ra.employee_id=r.employee_id
AND ra.employee_id=r.employee_id AND allocation_old LIKE '%BENCH%' 
AND ra.allocation_type_id=a.id AND @ENDDATE>alloc_start_date AND (@ENDDATE BETWEEN alloc_start_date AND alloc_end_date)
GROUP BY employee_id
ORDER BY  BG ASC,BU ASC,Skill ASC) A
WHERE No_of_Days>30 AND No_of_Days<=60
GROUP BY BG,BU,Skill) Y ON (X.BG=Y.BG AND X.BU=Y.BU AND X.Skill=Y.Skill)) Z1 LEFT OUTER JOIN 
(SELECT BG,BU,skill,COUNT(A.employee_id)'2_to_3_month_on_Bench' FROM
(SELECT o1.name BG,o2.name BU,c.skill,ra.employee_id,alloc_start_date,alloc_end_date,
a.allocationtype,DATEDIFF(COALESCE(alloc_end_date,NOW()),alloc_start_date) 'No_of_Days'
FROM org_hierarchy o1, org_hierarchy o2, resource r, competency c,resource_allocation ra, 
allocationtype a
WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND r.competency=c.id AND ra.employee_id=r.employee_id
AND ra.employee_id=r.employee_id AND allocation_old LIKE '%BENCH%' 
AND ra.allocation_type_id=a.id AND @ENDDATE>alloc_start_date AND (@ENDDATE BETWEEN alloc_start_date AND alloc_end_date)
GROUP BY employee_id
ORDER BY  BG ASC,BU ASC,Skill ASC) A
WHERE No_of_Days>60 AND No_of_Days<=90
GROUP BY BG,BU,Skill)Z2 ON (Z1.BG=Z2.BG AND Z1.BU=Z2.BU AND Z1.Skill=Z2.Skill)) Z3 LEFT OUTER JOIN 
((SELECT BG,BU,skill,COUNT(A.employee_id)'More_than_3_months_on_bench' FROM
(SELECT o1.name BG,o2.name BU,c.skill,ra.employee_id,alloc_start_date,alloc_end_date,
a.allocationtype,DATEDIFF(COALESCE(alloc_end_date,NOW()),alloc_start_date) 'No_of_Days'
FROM org_hierarchy o1, org_hierarchy o2, resource r, competency c,resource_allocation ra, 
allocationtype a
WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND r.competency=c.id AND ra.employee_id=r.employee_id
AND ra.employee_id=r.employee_id AND allocation_old LIKE '%BENCH%' 
AND ra.allocation_type_id=a.id AND @ENDDATE>alloc_start_date AND (@ENDDATE BETWEEN alloc_start_date AND alloc_end_date)
GROUP BY employee_id
ORDER BY BG ASC,BU ASC,Skill ASC) A
WHERE No_of_Days>90
GROUP BY BG,BU,Skill)) Z4 ON (Z3.BG=Z4.BG AND Z3.BU=Z4.BU AND Z3.Skill=Z4.Skill)) Final
WHERE Final.A!=0 OR Final.B!=0 OR Final.C!=0 OR Final.D!=0
ORDER BY BG ASC,BU ASC,Skill ASC;
END */$$
DELIMITER ;

/* Procedure structure for procedure `RMG_BGBU_Metrics` */

/*!50003 DROP PROCEDURE IF EXISTS  `RMG_BGBU_Metrics` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` PROCEDURE `RMG_BGBU_Metrics`(IN start_date CHAR(2), 
      IN end_date CHAR(2),IN start_month VARCHAR(2),
	  IN end_month VARCHAR(2),IN start_year CHAR(4), IN end_year VARCHAR(4))
BEGIN
SET @STARTDATE = CONCAT(`start_year`,'-',`start_month`,'-',`start_date`);
SET @ENDDATE = CONCAT(`end_year`,'-',`end_month`,'-',`end_date`);
SELECT LTRIM(RTRIM(G.BG)),RTRIM(LTRIM(G.BU)),Total,Management_Resources,Billed_Resources,Unbilled_Resources,
        COALESCE(B1,0) AS 'Unallocated_Resources',CONCAT(ROUND((Billed_Resources/Total*100),2),'%') '%Billing',
        CONCAT(ROUND((((Management_Resources+Billed_Resources+Unbilled_Resources)/Total)*100),2),'%') '%Utilization'
FROM
(SELECT E.BG,E.BU,Total,Management_Resources,Billed_Resources,
        COALESCE(B1,0) AS 'Unbilled_Resources' FROM
(SELECT C.BG,C.BU,Total,Management_Resources,COALESCE(B1,0) AS 'Billed_Resources' FROM
(SELECT A.BG,A.BU,A1 'Total',COALESCE(B1,0) AS 'Management_Resources' FROM
(SELECT o1.name BG,o2.name BU,COUNT(r1.employee_id) A1
FROM org_hierarchy o1, org_hierarchy o2, resource r1
WHERE o1.id=o2.parent_id AND r1.current_bu_id=o2.id AND
(ISNULL(release_date) OR (YEAR(release_date)>=`end_year` AND MONTH(release_date)>=`end_month` 
AND DAY(release_date)>=`end_date`))
GROUP BY o1.name,o2.name) A LEFT OUTER JOIN
(SELECT o1.name BG,o2.name BU,COUNT(DISTINCT(r1.employee_id)) B1
FROM org_hierarchy o1, org_hierarchy o2, resource_allocation r, allocationtype a,resource r1
WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND r.allocation_type_id=a.id AND r.employee_id=r1.employee_id AND
(ISNULL(release_date) OR (YEAR(release_date)>=`end_year` AND MONTH(release_date)>=`end_month` 
AND DAY(release_date)>=`end_date`))
AND a.allocationtype LIKE '%Management%' 
GROUP BY o1.name,o2.name) B 
ON A.BG=B.BG AND A.BU=B.BU) C LEFT OUTER JOIN
(SELECT o1.name BG,o2.name BU,COUNT(DISTINCT(r1.employee_id)) B1
FROM org_hierarchy o1, org_hierarchy o2, resource_allocation r, allocationtype a,resource r1
WHERE o1.id=o2.parent_id AND r1.current_bu_id=o2.id AND r.allocation_type_id=a.id AND r.employee_id=r1.employee_id AND
(ISNULL(release_date) OR (YEAR(release_date)>=`end_year` AND MONTH(release_date)>=`end_month` 
AND DAY(release_date)>=`end_date`))
AND a.allocationtype LIKE 'Billable%' AND (ISNULL(alloc_end_date) OR
(YEAR(alloc_start_date)<=`start_year` AND (YEAR(alloc_end_date)>=`end_year`)) AND
(MONTH(alloc_start_date)<=`start_month` AND (MONTH(alloc_end_date)>=`end_month`)) AND
(DAY(alloc_start_date)<=`start_date` AND (DAY(alloc_end_date)>=`end_date`)))
GROUP BY o1.name,o2.name)D
ON C.BG=D.BG AND C.BU=D.BU) E LEFT OUTER JOIN
(SELECT o1.name BG,o2.name BU,COUNT(DISTINCT(r1.employee_id)) B1
FROM org_hierarchy o1, org_hierarchy o2, resource_allocation r, allocationtype a,resource r1
WHERE o1.id=o2.parent_id AND r1.current_bu_id=o2.id AND r.allocation_type_id=a.id AND r.employee_id=r1.employee_id AND
(ISNULL(release_date) OR (YEAR(release_date)>=`end_year` AND MONTH(release_date)>=`end_month` 
AND DAY(release_date)>=`end_date`))
AND a.allocationtype NOT LIKE 'Billable%' AND a.allocationtype NOT LIKE '%Management%' AND
a.allocationtype NOT LIKE '%Unallocated%' AND a.allocationtype NOT LIKE 'Non-Billable (Investment)'  
AND (ISNULL(alloc_end_date) OR
(YEAR(alloc_start_date)<=`start_year` AND (YEAR(alloc_end_date)>=`end_year`)) AND
(MONTH(alloc_start_date)<=`start_month` AND (MONTH(alloc_end_date)>=`end_month`)) AND
(DAY(alloc_start_date)<=`start_date` AND (DAY(alloc_end_date)>=`end_date`)))
GROUP BY o1.name,o2.name)F
ON E.BG=F.BG AND E.BU=F.BU) G LEFT OUTER JOIN 
(SELECT BG,BU,SUM(B1) B1 FROM
(SELECT o1.name BG,o2.name BU,COUNT(DISTINCT(r1.employee_id)) B1
FROM org_hierarchy o1, org_hierarchy o2, resource_allocation r, allocationtype a,resource r1
WHERE o1.id=o2.parent_id AND r1.current_bu_id=o2.id AND r.allocation_type_id=a.id AND r.employee_id=r1.employee_id AND
(ISNULL(release_date) OR (YEAR(release_date)>='2018' AND MONTH(release_date)>='10' 
AND DAY(release_date)>='31'))
AND (a.allocationtype LIKE '%Unallocated%' OR a.allocationtype LIKE'Non-Billable (Investment)') AND 
(ISNULL(alloc_end_date) OR
(YEAR(alloc_start_date)<=`start_year` AND (YEAR(alloc_end_date)>=`end_year`)) AND
(MONTH(alloc_start_date)<=`start_month` AND (MONTH(alloc_end_date)>=`end_month`)) AND
(DAY(alloc_start_date)<=`start_date` AND (DAY(alloc_end_date)>=`start_date`)))
GROUP BY o1.name,o2.name
UNION
(SELECT o1.name BG,o2.name BU,COUNT(DISTINCT(r1.employee_id)) B1
FROM org_hierarchy o1, org_hierarchy o2, resource_allocation r, allocationtype a,resource r1
WHERE o1.id=o2.parent_id AND r1.current_bu_id=o2.id AND r.allocation_type_id=a.id AND 
(r1.employee_id NOT IN (SELECT employee_id FROM resource_allocation)) AND
(ISNULL(release_date) OR (YEAR(release_date)>=`end_year` AND MONTH(release_date)>=`end_month` 
AND DAY(release_date)>=`end_date`))
AND (a.allocationtype LIKE '%Unallocated%' OR a.allocationtype LIKE'Non-Billable (Investment)') AND 
(ISNULL(alloc_end_date) OR
(YEAR(alloc_start_date)<=`start_year` AND (YEAR(alloc_end_date)>=`end_year`)) AND
(MONTH(alloc_start_date)<=`start_month` AND (MONTH(alloc_end_date)>=`end_month`)) AND
(DAY(alloc_start_date)<=`start_date`  AND (DAY(alloc_end_date)>=`end_date`)))
GROUP BY o1.name,o2.name)) H
GROUP BY H.BG,H.BU) H1
ON G.BG=H1.BG AND G.BU=H1.BU;
END */$$
DELIMITER ;

/* Procedure structure for procedure `RMG_GradeWise_Report` */

/*!50003 DROP PROCEDURE IF EXISTS  `RMG_GradeWise_Report` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` PROCEDURE `RMG_GradeWise_Report`(IN start_date CHAR(2), 
      IN end_date CHAR(2),IN start_month VARCHAR(2),
	  IN end_month VARCHAR(2),IN start_year CHAR(4), IN end_year VARCHAR(4))
BEGIN
SELECT A1 Grade,I.BG BG,I.BU BU,A2 'Number of Resources',B2 'Joined in Year',D2 'Joined in Month',
 F2 'Left in Year',H2 'Left in Month',J2 'Month Attrition' FROM
(SELECT  A1,G.BG,G.BU,A2,B2,D2,F2,H2 FROM 
(SELECT A1,E.BG,E.BU,A2,B2,D2,F2 FROM
(SELECT A1,C.BG,C.BU,A2,B2,D2 FROM 
(SELECT A1,A.BG,A.BU,A2,B2 FROM 
(SELECT g.grade A1,o1.name BG,o2.name BU,COUNT(*) A2
FROM  resource r, grade g,org_hierarchy o1, org_hierarchy o2
WHERE r.grade_id=g.id AND (r.release_date>SYSDATE() OR r.release_date IS NULL) AND
o1.id=o2.parent_id AND r.current_bu_id=o2.id 
GROUP BY r.grade_id,g.grade,g.grade,o1.name,o2.name) A LEFT OUTER JOIN
(SELECT g.grade B1,o1.name BG,o2.name BU,COUNT(*) B2
FROM  resource r, grade g,org_hierarchy o1, org_hierarchy o2
WHERE r.grade_id=g.id AND YEAR(date_of_joining)=`end_year` AND
o1.id=o2.parent_id AND r.current_bu_id=o2.id AND (r.release_date>SYSDATE() OR r.release_date IS NULL)
GROUP BY r.grade_id,g.grade,o1.name,o2.name
ORDER BY r.grade_id,g.grade,o1.name,o2.name
)B ON A1=B1 AND A.BG=B.BG AND A.BU=B.BU) C LEFT OUTER JOIN
(SELECT g.grade D1,o1.name BG,o2.name BU,COUNT(*) D2
FROM  resource r, grade g,org_hierarchy o1, org_hierarchy o2
WHERE r.grade_id=g.id AND o1.id=o2.parent_id AND r.current_bu_id=o2.id 
AND (r.release_date>SYSDATE() OR r.release_date IS NULL) AND
YEAR(date_of_joining)>=`start_year` AND MONTH(date_of_joining)>=`start_month` AND DAY(date_of_joining)>=`start_date` AND
YEAR(date_of_joining)<=`end_year` AND MONTH(date_of_joining)<=`end_month` AND DAY(date_of_joining)<=`end_date` 
GROUP BY r.grade_id,g.grade,o1.name,o2.name
) D ON A1=D1 AND C.BG=D.BG AND C.BU=D.BU)E LEFT OUTER JOIN
(SELECT g.grade F1,o1.name BG,o2.name BU,COUNT(*) F2
FROM  resource r, grade g,org_hierarchy o1, org_hierarchy o2
WHERE r.grade_id=g.id AND YEAR(release_date)=`end_year` AND
o1.id=o2.parent_id AND r.current_bu_id=o2.id AND 
(r.release_date>SYSDATE() OR r.release_date IS NULL)
GROUP BY r.grade_id,g.grade,o1.name,o2.name
)F ON A1=F1 AND E.BG=F.BG AND E.BU=F.BU) G LEFT OUTER JOIN
(SELECT g.grade H1,o1.name BG,o2.name BU,COUNT(*) H2
FROM  resource r, grade g,org_hierarchy o1, org_hierarchy o2
WHERE r.grade_id=g.id AND YEAR(release_date)=`end_year` AND
o1.id=o2.parent_id AND r.current_bu_id=o2.id AND (r.release_date>SYSDATE() OR r.release_date IS NULL) AND
YEAR(release_date)>=`start_year` AND MONTH(release_date)>=`start_month` AND DAY(release_date)>=`start_date` AND
YEAR(release_date)<=`end_year` AND MONTH(release_date)<=`end_month` AND DAY(release_date)<=`end_date` 
GROUP BY r.grade_id,g.grade,o1.name,o2.name
) H ON A1=H1 AND G.BG=H.BG AND G.BU=H.BU)I LEFT OUTER JOIN
( SELECT X.X1 J1,X.BG BG,X.BU BU,CONCAT(ROUND(Y.Y2/X.X2*100,2),'%') J2 FROM 
 (SELECT g.grade X1,o1.name BG,o2.name BU,COUNT(*) X2
FROM  resource r, grade g,org_hierarchy o1, org_hierarchy o2
WHERE r.grade_id=g.id AND(r.release_date>SYSDATE() OR r.release_date IS NULL) AND
o1.id=o2.parent_id AND r.current_bu_id=o2.id 
GROUP BY r.grade_id,g.grade,o1.name,o2.name) X LEFT OUTER JOIN 
 (SELECT g.grade Y1,o1.name BG,o2.name BU,COUNT(*) Y2
FROM  resource r, grade g,org_hierarchy o1, org_hierarchy o2
WHERE r.grade_id=g.id AND(r.release_date>SYSDATE() OR r.release_date IS NULL) AND
o1.id=o2.parent_id AND r.current_bu_id=o2.id AND
YEAR(release_date)>=`start_year` AND MONTH(release_date)>=`start_month` AND DAY(release_date)>=`start_date` AND
YEAR(release_date)<=`end_year` AND MONTH(release_date)<=`end_month` AND DAY(release_date)<=`end_date` 
GROUP BY r.grade_id,g.grade,o1.name,o2.name
) Y ON X.X1=Y.Y1 AND X.BG=Y.BG AND X.BU=Y.BU
) J ON A1=J1 AND I.BG=J.BG AND I.BU=J.BU
ORDER BY Grade,BG,BU;
END */$$
DELIMITER ;

/* Procedure structure for procedure `RMG_Projects_Closing_In_3_Months` */

/*!50003 DROP PROCEDURE IF EXISTS  `RMG_Projects_Closing_In_3_Months` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` PROCEDURE `RMG_Projects_Closing_In_3_Months`(IN start_date CHAR(2), 
      IN end_date CHAR(2),IN start_month VARCHAR(2),
	  IN end_month VARCHAR(2),IN start_year CHAR(4), IN end_year VARCHAR(4))
BEGIN
SET @STARTDATE = CONCAT(`start_year`,'-',`start_month`,'-',`start_date`);
SET @ENDDATE = CONCAT(`end_year`,'-',`end_month`,'-',`end_date`);
SELECT A.project_name 'Project',RTRIM(LTRIM(A.BG)) 'Parent BG',RTRIM(LTRIM(A.BU)) 'Parent BU',CNT1 'Resources in Projects ending in 0-1 month',
CNT2 'Resources in Projects ending in 1-2 Month',COALESCE(B.CNT,0) 'Resources in Projects ending in 2-3 Months' FROM
(SELECT A.project_name,A.BG,A.BU,CNT1,COALESCE(B.CNT,0) CNT2 FROM
(SELECT A.project_name,A.BG,A.BU, COALESCE(B.CNT,0) CNT1 FROM
(SELECT p.project_name,o1.name BG,o.name BU,COUNT(r.employee_id) CNT
FROM project p, org_hierarchy o,resource r,org_hierarchy o1
WHERE P.BU_ID=o.id AND r.current_project_id=p.id AND o1.id=o.parent_id AND
project_end_date<=DATE_ADD(@ENDDATE, INTERVAL 3 MONTH) AND project_end_date>=@ENDDATE
GROUP BY p.project_name) A LEFT OUTER JOIN 
(SELECT p.project_name,o1.name BG,o.name BU,COUNT(r.employee_id) CNT
FROM project p, org_hierarchy o,resource r,org_hierarchy o1
WHERE P.BU_ID=o.id AND r.current_project_id=p.id AND o1.id=o.parent_id AND
project_end_date<=DATE_ADD(@ENDDATE, INTERVAL 1 MONTH) AND project_end_date>=@ENDDATE
GROUP BY p.project_name) B ON (A.project_name=B.project_name)) A LEFT OUTER JOIN 
(SELECT p.project_name,o1.name BG,o.name BU,COUNT(r.employee_id) CNT
FROM project p, org_hierarchy o,resource r,org_hierarchy o1
WHERE P.BU_ID=o.id AND r.current_project_id=p.id AND o1.id=o.parent_id AND
project_end_date>DATE_ADD(@ENDDATE, INTERVAL 1 MONTH) AND project_end_date<=DATE_ADD(@ENDDATE, INTERVAL 2 MONTH)
GROUP BY p.project_name) B ON (A.project_name=B.project_name)) A LEFT OUTER JOIN 
(SELECT p.project_name,o1.name BG,o.name BU,COUNT(r.employee_id) CNT
FROM project p, org_hierarchy o,resource r,org_hierarchy o1
WHERE P.BU_ID=o.id AND r.current_project_id=p.id AND o1.id=o.parent_id AND
project_end_date>DATE_ADD(@ENDDATE, INTERVAL 2 MONTH) AND project_end_date<=DATE_ADD(@ENDDATE, INTERVAL 3 MONTH)
GROUP BY p.project_name) B ON (A.project_name=B.project_name);
END */$$
DELIMITER ;

/* Procedure structure for procedure `RMG_Resource_Metrics` */

/*!50003 DROP PROCEDURE IF EXISTS  `RMG_Resource_Metrics` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` PROCEDURE `RMG_Resource_Metrics`(IN start_date CHAR(2), 
      IN end_date CHAR(2),IN start_month VARCHAR(2),
	  IN end_month VARCHAR(2),IN start_year CHAR(4), IN end_year VARCHAR(4))
BEGIN
SET @STARTDATE = CONCAT(`start_year`,'-',`start_month`,'-',`start_date`);
SET @ENDDATE = CONCAT(`end_year`,'-',`end_month`,'-',`end_date`);
 SELECT 'Number of Active Resources' AS 'Resource Metrics', COUNT(*) AS ' '
 FROM resource WHERE release_date>SYSDATE() OR release_date IS NULL
 UNION
 SELECT CONCAT('Resources Joined in ',`start_year`),COUNT(*)
 FROM resource
 WHERE YEAR(date_of_joining)BETWEEN `start_year` AND `end_year`
 UNION
 SELECT CONCAT('Resources Joined in ',MONTHNAME(@STARTDATE),' ',`start_year`),COUNT(*) 
 FROM resource 
 WHERE (YEAR(date_of_joining)>=`start_year` AND YEAR(date_of_joining)<=`end_year`)
 AND (MONTH(date_of_joining)>=`start_month` AND MONTH(date_of_joining)<=`end_month`)
 AND (DAY(date_of_joining)>=`start_date` AND DAY(date_of_joining)<=`end_date`)
 UNION
 SELECT CONCAT('Resources Left in ',`start_year`),COUNT(*) FROM resource 
 WHERE YEAR(release_date) BETWEEN `start_year` AND `end_year`
 UNION
 SELECT CONCAT('Resources Left in ',MONTHNAME(@STARTDATE),' ',`start_year`),COUNT(*)
 FROM resource 
 WHERE (YEAR(release_date)>=`start_year` AND YEAR(release_date)<=`end_year`)
 AND (MONTH(release_date)>=`start_month` AND MONTH(release_date)<=`end_month`)
 AND (DAY(release_date)>=`start_date` AND DAY(release_date)<=`end_date`)
 UNION 
 SELECT CONCAT('Attrition in ',MONTHNAME(@STARTDATE),' ',`start_year`),CONCAT(ROUND(((SELECT COUNT(*) FROM resource 
 WHERE ((YEAR(release_date)>=`start_year` AND YEAR(release_date)<=`end_year`)
 AND (MONTH(release_date)>=`start_month` AND MONTH(release_date)<=`end_month`)
 AND (DAY(release_date)>=`start_date` AND DAY(release_date)<=`end_date`)))/
 (SELECT COUNT(*) FROM resource 
   WHERE  release_date>SYSDATE() OR release_date IS NULL)*100),2),'%')
 FROM DUAL
 UNION
 SELECT 'Management Ratio', 'NA'
 FROM DUAL
 UNION
 SELECT '% Billing', 'NA'
 FROM DUAL
 UNION
 SELECT '% Allocated',
 CONCAT(ROUND(((SELECT COUNT(DISTINCT(employee_id)) 
 FROM resource_allocation 
 WHERE alloc_end_date IS NULL OR alloc_end_date>=SYSDATE()
 )/(SELECT COUNT(*) FROM resource WHERE release_date>SYSDATE() OR release_date IS NULL
 )*100),2),'%')
 FROM DUAL 
 UNION
 SELECT 'Average Cost', 'NA'
 FROM DUAL;
END */$$
DELIMITER ;

/* Procedure structure for procedure `RMG_Resource_Metrics_byBG` */

/*!50003 DROP PROCEDURE IF EXISTS  `RMG_Resource_Metrics_byBG` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` PROCEDURE `RMG_Resource_Metrics_byBG`(IN start_date CHAR(2), 
      IN end_date CHAR(2),IN start_month VARCHAR(2),
	  IN end_month VARCHAR(2),IN start_year CHAR(4), IN end_year VARCHAR(4))
BEGIN
SELECT ABCDEFGH.BG 'BG',ABCDEFGH.A1 'Number of Resources',
ABCDEFGH.B1 'Joined in Year',ABCDEFGH.C1 'Joined in YearMonth',ABCDEFGH.D1 'Left in Year',
ABCDEFGH.E1 'Left in YearMonth',ABCDEFGH.F1 'YearMonth Attrition', ABCDEFGH.G1 'Management Ratio',
ABCDEFGH.H1 '% Billing' ,I.I1 '% Allocated' FROM 
(SELECT ABCDEFG.BG,ABCDEFG.A1,ABCDEFG.B1,ABCDEFG.C1,ABCDEFG.D1,ABCDEFG.E1,ABCDEFG.F1,
      ABCDEFG.G1,H.H1 FROM 
((SELECT ABCDEF.BG,ABCDEF.A1,ABCDEF.B1,ABCDEF.C1,ABCDEF.D1,ABCDEF.E1,ABCDEF.F1,G.G1 FROM 
(SELECT ABCDE.BG,ABCDE.A1,ABCDE.B1,ABCDE.C1,ABCDE.D1,ABCDE.E1,F.F1 FROM 
(SELECT ABCD.BG BG,ABCD.A1 A1,ABCD.B1 B1, ABCD.C1 C1,ABCD.D1 D1,E1 FROM 
(SELECT ABC.BG,ABC.A1 A1,ABC.B1 B1,ABC.C1 C1,D.D1 FROM
(SELECT AB.BG BG,AB.A1 A1,AB.B1 B1,C.C1 FROM 
((SELECT A.BG BG,A.A1 A1,B.B1 B1
FROM
(SELECT o1.name BG,COUNT(*) A1
FROM org_hierarchy o1, org_hierarchy o2, resource r
WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id and (r.release_date>SYSDATE() OR r.release_date IS NULL)
GROUP BY o1.name) A LEFT OUTER JOIN
(SELECT o1.name BG,COUNT(*) B1
FROM org_hierarchy o1, org_hierarchy o2, resource r
WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND YEAR(date_of_joining)=`end_year`
GROUP BY o1.name) B
ON A.BG=B.BG) AB LEFT OUTER JOIN
(SELECT o1.name BG,COUNT(*) C1
 FROM org_hierarchy o1, org_hierarchy o2, resource r
 WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND 
 YEAR(date_of_joining)>=`start_year` AND MONTH(date_of_joining)>=`start_month` AND DAY(date_of_joining)>=`start_date`
 AND YEAR(date_of_joining)<=`end_year` AND MONTH(date_of_joining)<=`end_month` AND DAY(date_of_joining)<=`end_date`
GROUP BY o1.name) C
ON AB.BG=c.BG)) ABC LEFT OUTER JOIN
(
 SELECT o1.name BG,COUNT(*) D1
 FROM org_hierarchy o1, org_hierarchy o2, resource r
 WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND YEAR(release_date)=`end_year`
 GROUP BY o1.name
) D
ON ABC.BG=D.BG) ABCD LEFT OUTER JOIN
(
 SELECT o1.name BG,COUNT(*) E1
 FROM org_hierarchy o1, org_hierarchy o2, resource r
 WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND 
 YEAR(release_date)>=`start_year` AND MONTH(release_date)>=`start_month` AND DAY(release_date)>=`start_date`
 AND YEAR(release_date)<=`end_year` AND MONTH(release_date)<=`end_month` AND DAY(release_date)<=`end_date`
 GROUP BY o1.name
) E
ON ABCD.BG=E.BG) ABCDE LEFT OUTER JOIN
( SELECT X.BG,CONCAT(ROUND(Y.Y1/X.X1*100,2),'%') F1 FROM 
 (SELECT o1.name BG,COUNT(*) X1
FROM org_hierarchy o1, org_hierarchy o2, resource r
WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id
GROUP BY o1.name) X LEFT OUTER JOIN 
 (SELECT o1.name BG,COUNT(*) Y1
 FROM org_hierarchy o1, org_hierarchy o2, resource r
 WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND 
 YEAR(release_date)>=`start_year` AND MONTH(release_date)>=`start_month` AND DAY(release_date)>=`start_date`
 AND YEAR(release_date)<=`end_year` AND MONTH(release_date)<=`end_month` AND DAY(release_date)<=`end_date`
 GROUP BY o1.name
 )Y ON X.BG=Y.BG
) F
ON ABCDE.BG=F.BG) ABCDEF LEFT OUTER JOIN
(SELECT o1.name BG,'NA' G1
 FROM org_hierarchy o1, org_hierarchy o2, resource r
 WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id
 GROUP BY o1.name
)G
ON ABCDEF.BG=G.BG) ABCDEFG LEFT OUTER JOIN
(SELECT o1.name BG,'NA' H1
 FROM org_hierarchy o1, org_hierarchy o2, resource r
 WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id
 GROUP BY o1.name
)H
ON ABCDEFG.BG=H.BG)) ABCDEFGH LEFT OUTER JOIN
( SELECT X.BG BG,CONCAT(ROUND(X.X1/Y.Y1*100,2),'%') I1 FROM 
(SELECT o1.name BG,COALESCE(COUNT(*),0) X1
 FROM org_hierarchy o1, org_hierarchy o2, resource_allocation ra
 WHERE o1.id=o2.parent_id AND ra.current_bu_id=o2.id AND
(ra.alloc_end_date IS NULL 
OR (YEAR(alloc_end_date)>=`start_year` AND MONTH(alloc_end_date)>=`start_month` AND DAY(alloc_end_date)>=`start_date` 
AND YEAR(alloc_end_date)<=`end_year` AND MONTH(alloc_end_date)<=`end_month` AND DAY(alloc_end_date)<=`end_date`))
 GROUP BY o1.name) X LEFT OUTER JOIN
 ( SELECT o1.name BG,COALESCE(COUNT(*),0) Y1
 FROM org_hierarchy o1, org_hierarchy o2, resource r
 WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND
 ((YEAR(release_date)>=`start_year` AND MONTH(release_date)>=`start_month` AND DAY(release_date)>=`start_date`
 AND YEAR(release_date)<=`end_year` AND MONTH(release_date)<=`end_month` AND DAY(release_date)<=`end_date`) OR release_date IS NULL)
 GROUP BY o1.name) Y ON X.BG=Y.BG
) I
ON ABCDEFGH.BG=I.BG;
END */$$
DELIMITER ;

/* Procedure structure for procedure `RMG_Resource_Metrics_byBGBU_Report` */

/*!50003 DROP PROCEDURE IF EXISTS  `RMG_Resource_Metrics_byBGBU_Report` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` PROCEDURE `RMG_Resource_Metrics_byBGBU_Report`(IN start_date CHAR(2), 
      IN end_date CHAR(2),IN start_month VARCHAR(2),
	  IN end_month VARCHAR(2),IN start_year CHAR(4), IN end_year VARCHAR(4))
BEGIN
SELECT RTRIM(LTRIM(ABCDE.BG)) Parent_BG, RTRIM(LTRIM(ABCDE.BU)) Parent_BU,Number_of_Resources,ABCDE.B1 Joined_in_Year,ABCDE.C1 Joined_in_YearMonth,
        ABCDE.D1 Left_in_Year,Left_in_YearMonth,CONCAT(ROUND((Left_in_YearMonth/Number_of_Resources*100),2),'%') YearMonth_Attrition
FROM  (SELECT ABCD.BG BG,ABCD.BU BU,ABCD.A1 Number_of_Resources,ABCD.B1 B1,ABCD.C1,ABCD.D1,E.E1 Left_in_YearMonth
       FROM  ((SELECT ABC.BG BG,ABC.BU BU,ABC.A1 A1,ABC.B1 B1,ABC.C1,D.D1 
       FROM (SELECT AB.BG BG,AB.BU BU,AB.A1 A1,AB.B1 B1,C.C1 
       FROM  ((SELECT A.BG BG,A.BU BU,A.A1 A1,B.B1 B1 
       FROM 
	(SELECT o1.name BG,o2.name BU,COUNT(*) A1 
       FROM org_hierarchy o1, org_hierarchy o2, resource r 
       WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND (r.release_date>SYSDATE() OR r.release_date IS NULL)
       GROUP BY o1.name,o2.name) A LEFT OUTER JOIN 
    (SELECT o1.name BG,o2.name BU,COUNT(*) B1 
       FROM org_hierarchy o1, org_hierarchy o2, resource r 
       WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id 
       AND YEAR(date_of_joining)=`end_year` 
       GROUP BY o1.name,o2.name) B 
	   ON A.BG=B.BG AND A.BU=B.BU ) AB LEFT OUTER JOIN 
	(SELECT o1.name BG,o2.name BU,COUNT(*) C1  
	   FROM org_hierarchy o1, org_hierarchy o2, resource r  
	   WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND 
	   YEAR(date_of_joining)>=`start_year`  AND MONTH(date_of_joining)>=`start_month` AND DAY(date_of_joining)>=`start_date` 
       AND YEAR(date_of_joining)<=`end_year`  AND MONTH(date_of_joining)<=`end_month` AND DAY(date_of_joining)<=`end_date`
	   GROUP BY o1.name,o2.name) C 
       ON AB.BG=C.BG AND AB.BU=C.BU))ABC LEFT OUTER JOIN 
	(SELECT o1.name BG,o2.name BU,COUNT(*) D1  
	   FROM org_hierarchy o1, org_hierarchy o2, resource r  
	   WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id  AND
	   YEAR(release_date)>=`start_year`
       AND YEAR(release_date)<=`end_year`
	   GROUP BY o1.name,o2.name ) D 
	   ON ABC.BG=D.BG AND ABC.BU=D.BU) ABCD LEFT OUTER JOIN 
	(SELECT o1.name BG,o2.name BU,COUNT(*) E1  
	  FROM org_hierarchy o1, org_hierarchy o2, resource r  
	  WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND 
	  YEAR(release_date)>=`start_year`  AND MONTH(release_date)>=`start_month` AND DAY(release_date)>=`start_date` 
       AND YEAR(release_date)<=`end_year`  AND MONTH(release_date)<=`end_month` AND DAY(release_date)<=`end_date` 
	  GROUP BY o1.name,o2.name ) E 
	  ON ABCD.BG=E.BG AND ABCD.BU=E.BU)) ABCDE;
END */$$
DELIMITER ;

/* Procedure structure for procedure `RMG_Skills_Releasing_In_3_Months` */

/*!50003 DROP PROCEDURE IF EXISTS  `RMG_Skills_Releasing_In_3_Months` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` PROCEDURE `RMG_Skills_Releasing_In_3_Months`(IN start_date CHAR(2), 
      IN end_date CHAR(2),IN start_month VARCHAR(2),
	  IN end_month VARCHAR(2),IN start_year CHAR(4), IN end_year VARCHAR(4))
BEGIN
SET @STARTDATE = CONCAT(`start_year`,'-',`start_month`,'-',`start_date`);
SET @ENDDATE = CONCAT(`end_year`,'-',`end_month`,'-',`end_date`);
SELECT A.BG,A.BU,A.Skill,COALESCE(CNT1,0) 'Resources in Projects ending in 0-1 Month',
COALESCE(CNT2,0) 'Resources in Projects ending in 1-2 Months',
COALESCE(B.CNT,0) 'Resources in Projects ending in 2-3 Months' FROM
(SELECT A.BG,A.BU,A.Skill,CNT1,B.CNT CNT2 FROM
(SELECT A.BG,A.BU,A.Skill,B.CNT 'CNT1' FROM 
(SELECT  BG,BU,Skill FROM
(SELECT 1 X1,o1.name BG,o.name BU
FROM org_hierarchy o,org_hierarchy o1
WHERE o1.id=o.parent_id
ORDER BY BG,BU) X LEFT OUTER JOIN
(SELECT 1 Y1,skill FROM competency) Y ON X1=Y1) A LEFT OUTER JOIN
(SELECT o1.name BG,o2.name BU,c.skill,COUNT(*) CNT
FROM org_hierarchy o1, org_hierarchy o2, resource r, resource_allocation ra,project p,competency c
WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND ra.employee_id=r.employee_id AND 
r.current_project_id=p.id AND r.competency=c.id AND 
((project_end_date<DATE_ADD(@ENDDATE, INTERVAL 1 MONTH) AND alloc_end_date>=@ENDDATE) OR 
(alloc_end_date<DATE_ADD(@ENDDATE, INTERVAL 1 MONTH) AND alloc_end_date>=@ENDDATE))
GROUP BY BG,BU) B ON (A.BG=B.BG AND A.BU=B.BU AND A.SKill=B.skill)) A LEFT OUTER JOIN
(SELECT o1.name BG,o2.name BU,c.skill,COUNT(*) CNT
FROM org_hierarchy o1, org_hierarchy o2, resource r, resource_allocation ra,project p,competency c
WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND ra.employee_id=r.employee_id AND 
r.current_project_id=p.id AND r.competency=c.id AND 
((project_end_date<DATE_ADD(@ENDDATE, INTERVAL 2 MONTH) AND alloc_end_date>=DATE_ADD(@ENDDATE, INTERVAL 1 MONTH)) OR 
(alloc_end_date<DATE_ADD(@ENDDATE, INTERVAL 2 MONTH) AND alloc_end_date>=DATE_ADD(@ENDDATE, INTERVAL 1 MONTH)))
GROUP BY BG,BU) B ON (A.BG=B.BG AND A.BU=B.BU AND A.SKill=B.skill)) A LEFT OUTER JOIN
(SELECT o1.name BG,o2.name BU,c.skill,COUNT(*) CNT
FROM org_hierarchy o1, org_hierarchy o2, resource r, resource_allocation ra,project p,competency c
WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND ra.employee_id=r.employee_id AND 
r.current_project_id=p.id AND r.competency=c.id AND 
((project_end_date<DATE_ADD(@ENDDATE, INTERVAL 2 MONTH) AND alloc_end_date>=DATE_ADD(@ENDDATE, INTERVAL 1 MONTH)) OR 
(alloc_end_date<DATE_ADD(@ENDDATE, INTERVAL 3 MONTH) AND alloc_end_date>=DATE_ADD(@ENDDATE, INTERVAL 2 MONTH)))
GROUP BY BG,BU) B ON (A.BG=B.BG AND A.BU=B.BU AND A.SKill=B.skill)
WHERE !ISNULL(CNT1) OR !ISNULL(CNT2) OR !ISNULL(B.CNT)
ORDER BY BG,BU,Skill;
END */$$
DELIMITER ;

/* Procedure structure for procedure `RRF_Metrics` */

/*!50003 DROP PROCEDURE IF EXISTS  `RRF_Metrics` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` PROCEDURE `RRF_Metrics`(IN start_date CHAR(2), 
      IN end_date CHAR(2),IN start_month VARCHAR(2),
	  IN end_month VARCHAR(2),IN start_year CHAR(4), IN end_year VARCHAR(4))
BEGIN
SELECT DISTINCT 'Positions','SAP','NON SAP' FROM request_requisition
UNION
SELECT 'Requested Resource',A.SAP_COUNT ,B.NON_SAP_COUNT
FROM
(SELECT COUNT(r.ID) SAP_COUNT
FROM skill_request_resource s, skill_request_requisition r, competency c,request_requisition rr
WHERE s.skill_request_id=r.id AND r.skills=c.id AND c.skill LIKE '%SAP%' 
AND r.request_id=rr.id
 AND YEAR(date_of_indent)>=`start_year` AND MONTH(date_of_indent)>=`start_month` AND DAY(date_of_indent)>=`start_date`
 AND YEAR(date_of_indent)<=`end_year` AND MONTH(date_of_indent)<=`end_month` AND DAY(date_of_indent)<=`end_date`) A,
 (SELECT COUNT(r.ID) NON_SAP_COUNT
FROM skill_request_resource s, skill_request_requisition r, competency c,request_requisition rr
WHERE s.skill_request_id=r.id AND r.skills=c.id AND c.skill NOT LIKE '%SAP%'
AND r.request_id=rr.id 
 AND YEAR(date_of_indent)>=`start_year` AND MONTH(date_of_indent)>=`start_month` AND DAY(date_of_indent)>=`start_date`
 AND YEAR(date_of_indent)<=`end_year` AND MONTH(date_of_indent)<=`end_month` AND DAY(date_of_indent)<=`end_date`) B
UNION
SELECT 'Fulfilled Internally',A.Internally_SAP,B.Internally_NON_SAP
FROM 
(SELECT COUNT(s.ID) Internally_SAP
 FROM skill_request_resource s, skill_request_requisition r, competency c,request_requisition rr
 WHERE s.skill_request_id=r.id AND r.skills=c.id AND c.skill LIKE '%SAP%' AND s.status_id='8'
 AND r.request_id=rr.id AND resource_id IS NOT NULL
 AND YEAR(date_of_indent)>=`start_year` AND MONTH(date_of_indent)>=`start_month` AND DAY(date_of_indent)>=`start_date`
 AND YEAR(date_of_indent)<=`end_year` AND MONTH(date_of_indent)<=`end_month` AND DAY(date_of_indent)<=`end_date`) A,
(SELECT COUNT(s.ID) Internally_NON_SAP
 FROM skill_request_resource s, skill_request_requisition r, competency c,request_requisition rr
 WHERE s.skill_request_id=r.id AND r.skills=c.id AND c.skill NOT LIKE '%SAP%' 
 AND r.request_id=rr.id AND s.status_id='8'
 AND resource_id IS NOT NULL
  AND YEAR(date_of_indent)>=`start_year` AND MONTH(date_of_indent)>=`start_month` AND DAY(date_of_indent)>=`start_date`
 AND YEAR(date_of_indent)<=`end_year` AND MONTH(date_of_indent)<=`end_month` AND DAY(date_of_indent)<=`end_date`) B
 
 UNION
 
 SELECT 'Fulfilled Externally',A.Externally_SAP,B.Externally_NON_SAP
 FROM 
 (SELECT COUNT(*) Externally_SAP
 FROM skill_request_resource s, skill_request_requisition r, competency c,
 request_requisition rr
 WHERE s.skill_request_id=r.id AND r.skills=c.id AND c.skill  LIKE '%SAP%' 
 AND r.request_id=rr.id AND external_resource_name IS NOT NULL AND s.status_id='8'
 AND YEAR(date_of_indent)>=`start_year` AND MONTH(date_of_indent)>=`start_month` AND DAY(date_of_indent)>=`start_date`
 AND YEAR(date_of_indent)<=`end_year` AND MONTH(date_of_indent)<=`end_month` AND DAY(date_of_indent)<=`end_date`) A,
 (SELECT COUNT(*) Externally_NON_SAP
 FROM skill_request_resource s, skill_request_requisition r, competency c,
 request_requisition rr
 WHERE s.skill_request_id=r.id AND r.skills=c.id AND c.skill NOT LIKE '%SAP%' 
 AND r.request_id=rr.id AND external_resource_name IS NOT NULL AND s.status_id='8'
 AND YEAR(date_of_indent)>=`start_year` AND MONTH(date_of_indent)>=`start_month` AND DAY(date_of_indent)>=`start_date`
 AND YEAR(date_of_indent)<=`end_year` AND MONTH(date_of_indent)<=`end_month` AND DAY(date_of_indent)<=`end_date`) B 
 UNION
 SELECT 'On Hold/Cancelled/Lost',A.SAP_COUNT ,B.NON_SAP_COUNT
 FROM
 (SELECT (SUM(on_hold)+SUM(lost)+SUM(not_fit_for_requirement)) SAP_COUNT
 FROM skill_request_resource s, skill_request_requisition r, competency c,request_requisition rr
 WHERE s.skill_request_id=r.id AND r.skills=c.id AND c.skill LIKE '%SAP%' 
 AND r.request_id=rr.id  
 AND YEAR(date_of_indent)>=`start_year` AND MONTH(date_of_indent)>=`start_month` AND DAY(date_of_indent)>=`start_date`
 AND YEAR(date_of_indent)<=`end_year` AND MONTH(date_of_indent)<=`end_month` AND DAY(date_of_indent)<=`end_date`) A,
 (SELECT (SUM(on_hold)+SUM(lost)+SUM(not_fit_for_requirement)) NON_SAP_COUNT
 FROM skill_request_resource s, skill_request_requisition r, competency c,request_requisition rr
 WHERE s.skill_request_id=r.id AND r.skills=c.id AND c.skill NOT LIKE '%SAP%' 
 AND r.request_id=rr.id
 AND YEAR(date_of_indent)>=`start_year` AND MONTH(date_of_indent)>=`start_month` AND DAY(date_of_indent)>=`start_date`
 AND YEAR(date_of_indent)<=`end_year` AND MONTH(date_of_indent)<=`end_month` AND DAY(date_of_indent)<=`end_date`) B
 
 UNION
 
 SELECT 'Open (over all)',A.SAP_COUNT ,B.NON_SAP_COUNT
 FROM
 (SELECT SUM(OPEN) SAP_COUNT
 FROM skill_request_resource s, skill_request_requisition r, competency c,request_requisition rr
 WHERE s.skill_request_id=r.id AND r.skills=c.id AND c.skill LIKE '%SAP%' 
 AND r.request_id=rr.id  
 AND YEAR(date_of_indent)>=`start_year` AND MONTH(date_of_indent)>=`start_month` AND DAY(date_of_indent)>=`start_date`
 AND YEAR(date_of_indent)<=`end_year` AND MONTH(date_of_indent)<=`end_month` AND DAY(date_of_indent)<=`end_date`) A,
 (SELECT SUM(OPEN) NON_SAP_COUNT
FROM skill_request_resource s, skill_request_requisition r, competency c,request_requisition rr
WHERE s.skill_request_id=r.id AND r.skills=c.id AND c.skill NOT LIKE '%SAP%' 
AND r.request_id=rr.id
AND YEAR(date_of_indent)>=`start_year` AND MONTH(date_of_indent)>=`start_month` AND DAY(date_of_indent)>=`start_date`
AND YEAR(date_of_indent)<=`end_year` AND MONTH(date_of_indent)<=`end_month` AND DAY(date_of_indent)<=`end_date`) B;
END */$$
DELIMITER ;

/* Procedure structure for procedure `usp_rpt_Employee_Planed_Actual_Bill_hrs` */

/*!50003 DROP PROCEDURE IF EXISTS  `usp_rpt_Employee_Planed_Actual_Bill_hrs` */;

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

/*!50003 DROP PROCEDURE IF EXISTS  `usp_rpt_WeeklyTimeSheet` */;

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

/*!50003 DROP PROCEDURE IF EXISTS  `usp_set_current_project` */;

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

/*!50003 DROP PROCEDURE IF EXISTS  `WeeklyTimeSheet` */;

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

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


/* Procedure structure for procedure `RMG_Bench_Grade_DaysWise_Report` */

DELIMITER $$

DROP PROCEDURE IF EXISTS `RMG_Bench_Grade_DaysWise_Report`$$

CREATE DEFINER=`root`@`%` PROCEDURE `RMG_Bench_Grade_DaysWise_Report`(IN start_date VARCHAR(2), 
      IN end_date VARCHAR(2),IN start_month VARCHAR(2),
	  IN end_month VARCHAR(2),IN start_year VARCHAR(4), IN end_year VARCHAR(4))
BEGIN
SET @STARTDATE = CONCAT(`start_year`,'-',`start_month`,'-',`start_date`);
SET @ENDDATE = CONCAT(`end_year`,'-',`end_month`,'-',`end_date`);
SELECT A.BG BG, A.BU BU, A.grade 'Grade', SUM(No_of_Days) 'Days on Bench', COUNT(A.employee_id) 'Resources on Bench' ,
  ROUND((SUM(No_of_Days)/COUNT(A.employee_id)),0) 'Average Bench Period' FROM 
 (
 
-- The below unioned query used to find out resources who is on 'Bench' or 'unallocated' in any project.
-- The first query finds those resourcs who is specially specified to be on bench.
-- The second query finds those resources who are only exist in resource table hence these all are marked as on bench resources.
-- The third query finds those resources who has assignedd default project. 
-- The fourth query finds those resources who exist in resource_allocation table without allocation with any project.
 (SELECT o1.name BG,o2.name BU, r1.employee_id, g.grade grade,
 SUM(DATEDIFF(
 CASE WHEN ISNULL(alloc_end_date)
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 WHEN alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 ELSE alloc_end_date
 END,alloc_start_date)) 'No_of_Days'
 FROM org_hierarchy o1, org_hierarchy o2, resource r1, grade g, resource_allocation r, allocationtype a
 WHERE o1.id=o2.parent_id AND r1.current_bu_id=o2.id AND r.employee_id=r1.employee_id AND g.id=r1.grade_id
 AND r.allocation_type_id=a.id AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') 
 AND (ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND alloc_start_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
 (ISNULL(alloc_end_date) OR alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND a.allocationtype IN ('Non-Billable (Unallocated)','Non-Billable (Shadow)' ,'Non-Billable (Blocked)')
 GROUP BY o1.name,o2.name,r1.employee_id) 
UNION 
 (SELECT DISTINCT o1.name BG,o2.name BU, r1.employee_id B1, g.grade grade,
 DATEDIFF(
 CASE WHEN ISNULL(release_date)
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 WHEN release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 ELSE release_date
 END,date_of_joining) 'No_of_Days'
FROM org_hierarchy o1 INNER JOIN org_hierarchy o2 ON o1.id=o2.parent_id
INNER JOIN resource r1 ON r1.current_bu_id=o2.id INNER JOIN grade g ON g.id=r1.grade_id
WHERE NOT EXISTS (SELECT DISTINCT r1.employee_id FROM resource_allocation r2 WHERE r2.employee_id=r1.employee_id)
AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')))
UNION
(SELECT DISTINCT o1.name BG, o2.name BU, r1.employee_id B1, g.grade grade,
 DATEDIFF(
 CASE WHEN ISNULL(release_date)
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 WHEN release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 ELSE release_date
 END,date_of_joining) 'No_of_Days'
FROM org_hierarchy o1 INNER JOIN org_hierarchy o2 ON o1.id=o2.parent_id
INNER JOIN default_project dp ON dp.bu_id=o2.id 
INNER JOIN resource r1 ON r1.current_bu_id=o2.id AND r1.current_bu_id=dp.bu_id
INNER JOIN grade g ON g.id=r1.grade_id
WHERE date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')))
UNION
(SELECT DISTINCT o1.name BG, o2.name BU, r1.employee_id B1, g.grade grade,
DATEDIFF(STR_TO_DATE(@ENDDATE, '%Y-%m-%d'),MAX(alloc_end_date)) 'No_of_Days'
FROM org_hierarchy o1 INNER JOIN org_hierarchy o2 ON o1.id=o2.parent_id 
INNER JOIN resource r1 ON r1.current_bu_id=o2.id INNER JOIN grade g ON g.id=r1.grade_id
INNER JOIN resource_allocation r ON r.employee_id=r1.employee_id
WHERE date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND r1.employee_id NOT IN
(SELECT DISTINCT r.employee_id B1
FROM resource_allocation r1 INNER JOIN resource r ON r1.employee_id=r.employee_id
WHERE date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(release_date) OR release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')) 
AND alloc_start_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND 
(ISNULL(alloc_end_date) OR alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')))
 GROUP BY o1.name, o2.name, r1.employee_id, g.grade)) 
 A GROUP BY BG,BU,grade;
 
END$$

DELIMITER ;


/* Procedure structure for procedure `RMG_Bench_Grade_Report` */

DELIMITER $$

DROP PROCEDURE IF EXISTS `RMG_Bench_Grade_Report`$$

CREATE DEFINER=`root`@`%` PROCEDURE `RMG_Bench_Grade_Report`(IN start_date CHAR(2), 
      IN end_date CHAR(2),IN start_month CHAR(2),
	  IN end_month CHAR(2),IN start_year CHAR(4), IN end_year CHAR(4))
BEGIN
SET @STARTDATE = CONCAT(`start_year`,'-',`start_month`,'-',`start_date`);
SET @ENDDATE = CONCAT(`end_year`,'-',`end_month`,'-',`end_date`);
SELECT RTRIM(LTRIM(Final.BG)) BG,RTRIM(LTRIM(Final.BU)) BU, Final.grade,Final.A '0 To 1 Months on Bench',Final.B '1 To 2 Month on Bench',
Final.C '2 To 3 Month on Bench',Final.D 'More Than 3 Months on Bench' FROM
(SELECT  Z3.BG,Z3.BU,Z3.grade,COALESCE(0_to_1_month_on_Bench,0) A, COALESCE(1_to_2_month_on_Bench,0) B,
COALESCE(2_to_3_month_on_Bench,0) C, COALESCE(More_than_3_months_on_bench,0) D FROM
(SELECT Z1.BG,Z1.BU,Z1.grade,0_to_1_month_on_Bench,1_to_2_month_on_Bench,2_to_3_month_on_Bench FROM
(SELECT X.BG,X.BU,X.grade,0_to_1_month_on_Bench,1_to_2_month_on_Bench FROM
(SELECT Temp.BG,Temp.BU,Temp.grade,0_to_1_month_on_Bench FROM
(SELECT BG,BU,grade FROM 
 (SELECT '1' T1, o1.name BG, o2.name BU
  FROM org_hierarchy o1, org_hierarchy o2, resource r
  WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id
  GROUP BY o1.name,o2.name) A, 
 (SELECT '1' T2, grade FROM grade) B
 WHERE T1=T2) Temp LEFT OUTER JOIN
 (SELECT A.BG, A.BU, A.grade, COUNT(A.employee_id) '0_to_1_month_on_Bench'  FROM (
 
-- The below unioned query used to find out resources who is on 'Bench' or 'unallocated' in any project.
-- The first query finds those resourcs who is specially specified to be on bench.
-- The second query finds those resources who are only exist in resource table hence these all are marked as on bench resources.
-- The third query finds those resources who has assignedd default project. 
-- The fourth query finds those resources who exist in resource_allocation table without allocation with any project.
 (SELECT o1.name BG,o2.name BU, r1.employee_id, g.grade grade,
 SUM(DATEDIFF(
 CASE WHEN ISNULL(alloc_end_date)
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 WHEN alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 ELSE alloc_end_date
 END,alloc_start_date)) 'No_of_Days'
 FROM org_hierarchy o1, org_hierarchy o2, resource r1, grade g, resource_allocation r, allocationtype a
 WHERE o1.id=o2.parent_id AND r1.current_bu_id=o2.id AND r.employee_id=r1.employee_id AND g.id=r1.grade_id
 AND r.allocation_type_id=a.id AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') 
 AND (ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND alloc_start_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
 (ISNULL(alloc_end_date) OR alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND a.allocationtype IN ('Non-Billable (Unallocated)','Non-Billable (Shadow)' ,'Non-Billable (Blocked)')
 GROUP BY o1.name,o2.name,r1.employee_id) 
UNION 
 (SELECT DISTINCT o1.name BG,o2.name BU, r1.employee_id B1, g.grade grade,
 DATEDIFF(
 CASE WHEN ISNULL(release_date)
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 WHEN release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 ELSE release_date
 END,date_of_joining) 'No_of_Days'
FROM org_hierarchy o1 INNER JOIN org_hierarchy o2 ON o1.id=o2.parent_id
INNER JOIN resource r1 ON r1.current_bu_id=o2.id INNER JOIN grade g ON g.id=r1.grade_id
WHERE NOT EXISTS (SELECT DISTINCT r1.employee_id FROM resource_allocation r2 WHERE r2.employee_id=r1.employee_id)
AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')))
UNION
(SELECT DISTINCT o1.name BG, o2.name BU, r1.employee_id B1, g.grade grade,
 DATEDIFF(
 CASE WHEN ISNULL(release_date)
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 WHEN release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 ELSE release_date
 END,date_of_joining) 'No_of_Days'
FROM org_hierarchy o1 INNER JOIN org_hierarchy o2 ON o1.id=o2.parent_id
INNER JOIN default_project dp ON dp.bu_id=o2.id 
INNER JOIN resource r1 ON r1.current_bu_id=o2.id AND r1.current_bu_id=dp.bu_id
INNER JOIN grade g ON g.id=r1.grade_id
WHERE date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')))
UNION
(SELECT DISTINCT o1.name BG, o2.name BU, r1.employee_id B1, g.grade grade,
DATEDIFF(STR_TO_DATE(@ENDDATE, '%Y-%m-%d'),MAX(alloc_end_date)) 'No_of_Days'
FROM org_hierarchy o1 INNER JOIN org_hierarchy o2 ON o1.id=o2.parent_id 
INNER JOIN resource r1 ON r1.current_bu_id=o2.id INNER JOIN grade g ON g.id=r1.grade_id
INNER JOIN resource_allocation r ON r.employee_id=r1.employee_id
WHERE date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND r1.employee_id NOT IN
(SELECT DISTINCT r.employee_id B1
FROM resource_allocation r1 INNER JOIN resource r ON r1.employee_id=r.employee_id
WHERE date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(release_date) OR release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')) 
AND alloc_start_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND 
(ISNULL(alloc_end_date) OR alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')))
 GROUP BY o1.name, o2.name, r1.employee_id, g.grade)) 
 A WHERE No_of_Days>0 AND No_of_Days<=30
 GROUP BY BG,BU,grade) X1 ON 
(X1.BG=Temp.BG AND X1.BU=Temp.BU AND X1.grade=Temp.grade)) X 
LEFT OUTER JOIN
(SELECT A.BG, A.BU, A.grade, COUNT(A.employee_id) '1_to_2_month_on_Bench'  FROM 
 ((SELECT o1.name BG,o2.name BU, r1.employee_id, g.grade grade,
 SUM(DATEDIFF(
 CASE WHEN ISNULL(alloc_end_date)
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 WHEN alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 ELSE alloc_end_date
 END,alloc_start_date)) 'No_of_Days'
 FROM org_hierarchy o1, org_hierarchy o2, resource r1, grade g, resource_allocation r, allocationtype a
 WHERE o1.id=o2.parent_id AND r1.current_bu_id=o2.id AND r.employee_id=r1.employee_id AND g.id=r1.grade_id
 AND r.allocation_type_id=a.id AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
 (ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND STR_TO_DATE(@ENDDATE, '%Y-%m-%d') >= alloc_start_date AND
 (ISNULL(alloc_end_date) OR alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND a.allocationtype IN ('Non-Billable (Unallocated)','Non-Billable (Shadow)' ,'Non-Billable (Blocked)')
 GROUP BY o1.name,o2.name,r1.employee_id)
UNION 
 (SELECT DISTINCT o1.name BG,o2.name BU, r1.employee_id B1, g.grade grade,
 DATEDIFF(
 CASE WHEN ISNULL(release_date)
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 WHEN release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 ELSE release_date
 END,date_of_joining) 'No_of_Days'
FROM org_hierarchy o1 INNER JOIN org_hierarchy o2 ON o1.id=o2.parent_id
INNER JOIN resource r1 ON r1.current_bu_id=o2.id INNER JOIN grade g ON g.id=r1.grade_id
WHERE NOT EXISTS (SELECT DISTINCT r1.employee_id FROM resource_allocation r2 WHERE r2.employee_id=r1.employee_id)
AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')))
UNION
(SELECT DISTINCT o1.name BG, o2.name BU, r1.employee_id B1, g.grade grade,
 DATEDIFF(
 CASE WHEN ISNULL(release_date)
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 WHEN release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 ELSE release_date
 END,date_of_joining) 'No_of_Days'
FROM org_hierarchy o1 INNER JOIN org_hierarchy o2 ON o1.id=o2.parent_id
INNER JOIN default_project dp ON dp.bu_id=o2.id 
INNER JOIN resource r1 ON r1.current_bu_id=o2.id AND r1.current_bu_id=dp.bu_id
INNER JOIN grade g ON g.id=r1.grade_id
WHERE date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')))
UNION
(SELECT DISTINCT o1.name BG, o2.name BU, r1.employee_id B1, g.grade grade,
DATEDIFF(STR_TO_DATE(@ENDDATE, '%Y-%m-%d'),MAX(alloc_end_date)) 'No_of_Days'
FROM org_hierarchy o1 INNER JOIN org_hierarchy o2 ON o1.id=o2.parent_id 
INNER JOIN resource r1 ON r1.current_bu_id=o2.id INNER JOIN grade g ON g.id=r1.grade_id
INNER JOIN resource_allocation r ON r.employee_id=r1.employee_id
WHERE date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND r1.employee_id NOT IN
(SELECT DISTINCT r.employee_id B1
FROM resource_allocation r1 INNER JOIN resource r ON r1.employee_id=r.employee_id
WHERE date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(release_date) OR release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')) 
AND alloc_start_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND 
(ISNULL(alloc_end_date) OR alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')))
 GROUP BY o1.name, o2.name, r1.employee_id, g.grade)) 
 A WHERE No_of_Days>30 AND No_of_Days<=60
 GROUP BY BG,BU,grade) Y ON (X.BG=Y.BG AND X.BU=Y.BU AND X.grade=Y.grade)) Z1 
 
 LEFT OUTER JOIN 
 
(SELECT A.BG, A.BU, A.grade, COUNT(A.employee_id) '2_to_3_month_on_Bench'  FROM 
 ((SELECT o1.name BG,o2.name BU, r1.employee_id, g.grade grade,
 SUM(DATEDIFF(
 CASE WHEN ISNULL(alloc_end_date)
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 WHEN alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 ELSE alloc_end_date
 END,alloc_start_date)) 'No_of_Days'
 FROM org_hierarchy o1, org_hierarchy o2, resource r1, grade g, resource_allocation r, allocationtype a
 WHERE o1.id=o2.parent_id AND r1.current_bu_id=o2.id AND r.employee_id=r1.employee_id AND g.id=r1.grade_id
 AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND r.allocation_type_id=a.id AND
 (ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND STR_TO_DATE(@ENDDATE, '%Y-%m-%d') >= alloc_start_date AND
 (ISNULL(alloc_end_date) OR alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND a.allocationtype IN ('Non-Billable (Unallocated)','Non-Billable (Shadow)' ,'Non-Billable (Blocked)')
 GROUP BY o1.name,o2.name,r1.employee_id)
UNION 
 (SELECT DISTINCT o1.name BG,o2.name BU, r1.employee_id B1, g.grade grade,
 DATEDIFF(
 CASE WHEN ISNULL(release_date)
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 WHEN release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 ELSE release_date
 END,date_of_joining) 'No_of_Days'
FROM org_hierarchy o1 INNER JOIN org_hierarchy o2 ON o1.id=o2.parent_id
INNER JOIN resource r1 ON r1.current_bu_id=o2.id INNER JOIN grade g ON g.id=r1.grade_id
WHERE NOT EXISTS (SELECT DISTINCT r1.employee_id FROM resource_allocation r2 WHERE r2.employee_id=r1.employee_id)
AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')  AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')))
UNION
(SELECT DISTINCT o1.name BG, o2.name BU, r1.employee_id B1, g.grade grade,
 DATEDIFF(
 CASE WHEN ISNULL(release_date)
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 WHEN release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 ELSE release_date
 END,date_of_joining) 'No_of_Days'
FROM org_hierarchy o1 INNER JOIN org_hierarchy o2 ON o1.id=o2.parent_id
INNER JOIN default_project dp ON dp.bu_id=o2.id 
INNER JOIN resource r1 ON r1.current_bu_id=o2.id AND r1.current_bu_id=dp.bu_id
INNER JOIN grade g ON g.id=r1.grade_id
WHERE date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')))
UNION
(SELECT DISTINCT o1.name BG, o2.name BU, r1.employee_id B1, g.grade grade,
DATEDIFF(STR_TO_DATE(@ENDDATE, '%Y-%m-%d'),MAX(alloc_end_date)) 'No_of_Days'
FROM org_hierarchy o1 INNER JOIN org_hierarchy o2 ON o1.id=o2.parent_id 
INNER JOIN resource r1 ON r1.current_bu_id=o2.id INNER JOIN grade g ON g.id=r1.grade_id
INNER JOIN resource_allocation r ON r.employee_id=r1.employee_id
WHERE date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND r1.employee_id NOT IN
(SELECT DISTINCT r.employee_id B1
FROM resource_allocation r1 INNER JOIN resource r ON r1.employee_id=r.employee_id
WHERE date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(release_date) OR release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')) 
AND alloc_start_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND 
(ISNULL(alloc_end_date) OR alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')))
 GROUP BY o1.name, o2.name, r1.employee_id, g.grade)
) 
A WHERE No_of_Days>60 AND No_of_Days<=90
 GROUP BY BG,BU,grade) Z2 ON (Z1.BG=Z2.BG AND Z1.BU=Z2.BU AND Z1.grade=Z2.grade)) Z3
 
LEFT OUTER JOIN 
(SELECT A.BG, A.BU, A.grade, COUNT(A.employee_id) 'More_than_3_months_on_bench'  FROM 
 ((SELECT o1.name BG,o2.name BU, r1.employee_id, g.grade grade,
 SUM(DATEDIFF(
 CASE WHEN ISNULL(alloc_end_date)
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 WHEN alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 ELSE alloc_end_date
 END,alloc_start_date)) 'No_of_Days'
 FROM org_hierarchy o1, org_hierarchy o2, resource r1, grade g, resource_allocation r, allocationtype a
 WHERE o1.id=o2.parent_id AND r1.current_bu_id=o2.id AND r.employee_id=r1.employee_id AND g.id=r1.grade_id AND
 r.allocation_type_id=a.id AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND 
 (ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND (ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND STR_TO_DATE(@ENDDATE, '%Y-%m-%d') >= alloc_start_date AND
 (ISNULL(alloc_end_date) OR alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND a.allocationtype IN ('Non-Billable (Unallocated)','Non-Billable (Shadow)' ,'Non-Billable (Blocked)')
 GROUP BY o1.name,o2.name,r1.employee_id)
UNION  
 (SELECT DISTINCT o1.name BG,o2.name BU, r1.employee_id B1, g.grade grade,
 DATEDIFF(
 CASE WHEN ISNULL(release_date)
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 WHEN release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 ELSE release_date
 END,date_of_joining) 'No_of_Days'
FROM org_hierarchy o1 INNER JOIN org_hierarchy o2 ON o1.id=o2.parent_id
INNER JOIN resource r1 ON r1.current_bu_id=o2.id INNER JOIN grade g ON g.id=r1.grade_id
WHERE NOT EXISTS (SELECT DISTINCT r1.employee_id FROM resource_allocation r2 WHERE r2.employee_id=r1.employee_id)
AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')))
UNION
(SELECT DISTINCT o1.name BG, o2.name BU, r1.employee_id B1, g.grade grade,
 DATEDIFF(
 CASE WHEN ISNULL(release_date)
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 WHEN release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 ELSE release_date
 END,date_of_joining) 'No_of_Days'
FROM org_hierarchy o1 INNER JOIN org_hierarchy o2 ON o1.id=o2.parent_id
INNER JOIN default_project dp ON dp.bu_id=o2.id 
INNER JOIN resource r1 ON r1.current_bu_id=o2.id AND r1.current_bu_id=dp.bu_id
INNER JOIN grade g ON g.id=r1.grade_id
WHERE date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')))
UNION
(SELECT DISTINCT o1.name BG, o2.name BU, r1.employee_id B1, g.grade grade,
DATEDIFF(STR_TO_DATE(@ENDDATE, '%Y-%m-%d'),MAX(alloc_end_date)) 'No_of_Days'
FROM org_hierarchy o1 INNER JOIN org_hierarchy o2 ON o1.id=o2.parent_id 
INNER JOIN resource r1 ON r1.current_bu_id=o2.id INNER JOIN grade g ON g.id=r1.grade_id
INNER JOIN resource_allocation r ON r.employee_id=r1.employee_id
WHERE date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND r1.employee_id NOT IN
(SELECT DISTINCT r.employee_id B1
FROM resource_allocation r1 INNER JOIN resource r ON r1.employee_id=r.employee_id
WHERE date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(release_date) OR release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')) 
AND alloc_start_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND 
(ISNULL(alloc_end_date) OR alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')))
 GROUP BY o1.name, o2.name, r1.employee_id, g.grade)
) 
 A WHERE No_of_Days>90
 GROUP BY BG,BU,grade) Z4 ON (Z3.BG=Z4.BG AND Z3.BU=Z4.BU AND Z3.grade=Z4.grade)) Final
 WHERE Final.A!=0 OR Final.B!=0 OR Final.C!=0 OR Final.D!=0
 ORDER BY BG ASC,BU ASC,grade ASC;
 
END$$

DELIMITER ;


/* Procedure structure for procedure `RMG_Bench_Skill_Report` */

DELIMITER $$

DROP PROCEDURE IF EXISTS `RMG_Bench_Skill_Report`$$

CREATE DEFINER=`root`@`%` PROCEDURE `RMG_Bench_Skill_Report`(IN start_date CHAR(2), 
      IN end_date CHAR(2),IN start_month CHAR(2),
	  IN end_month CHAR(2),IN start_year CHAR(4), IN end_year CHAR(4))
BEGIN
SET @STARTDATE = CONCAT(`start_year`,'-',`start_month`,'-',`start_date`);
SET @ENDDATE = CONCAT(`end_year`,'-',`end_month`,'-',`end_date`);
SELECT RTRIM(LTRIM(Final.BG)) BG,RTRIM(LTRIM(Final.BU)) BU, Final.Skill,Final.A '0 To 1 Months on Bench',Final.B '1 To 2 Month on Bench',
Final.C '2 To 3 Month on Bench',Final.D 'More Than 3 Months on Bench' FROM
(SELECT  Z3.BG,Z3.BU,Z3.Skill,COALESCE(0_to_1_month_on_Bench,0) A, COALESCE(1_to_2_month_on_Bench,0) B,
COALESCE(2_to_3_month_on_Bench,0) C, COALESCE(More_than_3_months_on_bench,0) D FROM
(SELECT  Z1.BG,Z1.BU,Z1.Skill,0_to_1_month_on_Bench,1_to_2_month_on_Bench,2_to_3_month_on_Bench FROM
(SELECT X.BG,X.BU,X.Skill,0_to_1_month_on_Bench,1_to_2_month_on_Bench FROM
(SELECT Temp.BG,Temp.BU,Temp.Skill,0_to_1_month_on_Bench FROM
 (SELECT BG,BU,Skill FROM 
 (SELECT '1' T1,o1.name BG,o2.name BU
 FROM org_hierarchy o1, org_hierarchy o2, resource r
 WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id 
 GROUP BY o1.name,o2.name
 ORDER BY o1.name,o2.name) A, 
 (SELECT '1' T2,skill FROM competency) B
 WHERE T1=T2) Temp LEFT OUTER JOIN
 (SELECT A.BG, A.BU, A.skill, COUNT(A.employee_id) '0_to_1_month_on_Bench'  FROM (
 
-- The below unioned query used to find out resources who is on 'Bench' or 'unallocated' in any project.
-- The first query finds those resourcs who is specially specified to be on bench.
-- The second query finds those resources who are only exist in resource table hence these all are marked as on bench resources.
-- The third query finds those resources who has assignedd default project. 
-- The fourth query finds those resources who exist in resource_allocation table without allocation with any project.
 (SELECT o1.name BG,o2.name BU,c.skill skill, r1.employee_id,
 SUM(DATEDIFF(
 CASE WHEN ISNULL(alloc_end_date)
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 WHEN alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 ELSE alloc_end_date
 END,alloc_start_date)) 'No_of_Days'
 FROM org_hierarchy o1, org_hierarchy o2, resource r1, competency c, resource_allocation r, allocationtype a
 WHERE o1.id=o2.parent_id AND r1.current_bu_id=o2.id AND r.employee_id=r1.employee_id AND r1.competency=c.id
 AND r.allocation_type_id=a.id AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') 
 AND (ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND alloc_start_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
 (ISNULL(alloc_end_date) OR alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND a.allocationtype IN ('Non-Billable (Unallocated)','Non-Billable (Shadow)' ,'Non-Billable (Blocked)')
 GROUP BY o1.name,o2.name,r1.employee_id) 
UNION 
 (SELECT DISTINCT o1.name BG,o2.name BU,c.skill skill, r1.employee_id B1, 
 DATEDIFF(
 CASE WHEN ISNULL(release_date)
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 WHEN release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 ELSE release_date
 END,date_of_joining) 'No_of_Days'
FROM org_hierarchy o1 INNER JOIN org_hierarchy o2 ON o1.id=o2.parent_id
INNER JOIN resource r1 ON r1.current_bu_id=o2.id INNER JOIN competency c ON r1.competency=c.id
WHERE NOT EXISTS (SELECT DISTINCT r1.employee_id FROM resource_allocation r2 WHERE r2.employee_id=r1.employee_id)
AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')))
UNION
(SELECT DISTINCT o1.name BG, o2.name BU, c.skill skill, r1.employee_id B1,
 DATEDIFF(
 CASE WHEN ISNULL(release_date)
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 WHEN release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 ELSE release_date
 END,date_of_joining) 'No_of_Days'
FROM org_hierarchy o1 INNER JOIN org_hierarchy o2 ON o1.id=o2.parent_id
INNER JOIN default_project dp ON dp.bu_id=o2.id 
INNER JOIN resource r1 ON r1.current_bu_id=o2.id AND r1.current_bu_id=dp.bu_id
INNER JOIN competency c ON r1.competency=c.id
WHERE date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')))
UNION
(SELECT DISTINCT o1.name BG, o2.name BU, c.skill skill, r1.employee_id B1,
 DATEDIFF(STR_TO_DATE(@ENDDATE, '%Y-%m-%d'),MAX(alloc_end_date)) 'No_of_Days'
FROM org_hierarchy o1 INNER JOIN org_hierarchy o2 ON o1.id=o2.parent_id 
INNER JOIN resource r1 ON r1.current_bu_id=o2.id INNER JOIN competency c ON r1.competency=c.id
INNER JOIN resource_allocation r ON r.employee_id=r1.employee_id
WHERE date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND r1.employee_id NOT IN
(SELECT DISTINCT r.employee_id B1
FROM resource_allocation r1 INNER JOIN resource r ON r1.employee_id=r.employee_id
WHERE date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(release_date) OR release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')) 
AND alloc_start_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND 
(ISNULL(alloc_end_date) OR alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')))
 GROUP BY o1.name, o2.name, c.skill, r1.employee_id)
) 
 A WHERE No_of_Days>0 AND No_of_Days<=30
 GROUP BY BG,BU,skill) X1 ON 
(X1.BG=Temp.BG AND X1.BU=Temp.BU AND X1.skill=Temp.skill)) X 
LEFT OUTER JOIN
(SELECT A.BG, A.BU, A.skill, COUNT(A.employee_id) '1_to_2_month_on_Bench'  FROM (
 (SELECT o1.name BG,o2.name BU,c.skill skill, r1.employee_id,
 SUM(DATEDIFF(
 CASE WHEN ISNULL(alloc_end_date)
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 WHEN alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 ELSE alloc_end_date
 END,alloc_start_date)) 'No_of_Days'
 FROM org_hierarchy o1, org_hierarchy o2, resource r1, competency c, resource_allocation r, allocationtype a
 WHERE o1.id=o2.parent_id AND r1.current_bu_id=o2.id AND r.employee_id=r1.employee_id AND r1.competency=c.id
 AND r.allocation_type_id=a.id AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') 
 AND (ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND alloc_start_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
 (ISNULL(alloc_end_date) OR alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND a.allocationtype IN ('Non-Billable (Unallocated)','Non-Billable (Shadow)' ,'Non-Billable (Blocked)')
 GROUP BY o1.name,o2.name,r1.employee_id) 
UNION 
 (SELECT DISTINCT o1.name BG,o2.name BU,c.skill skill, r1.employee_id B1, 
 DATEDIFF(
 CASE WHEN ISNULL(release_date)
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 WHEN release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 ELSE release_date
 END,date_of_joining) 'No_of_Days'
FROM org_hierarchy o1 INNER JOIN org_hierarchy o2 ON o1.id=o2.parent_id
INNER JOIN resource r1 ON r1.current_bu_id=o2.id INNER JOIN competency c ON r1.competency=c.id
WHERE NOT EXISTS (SELECT DISTINCT r1.employee_id FROM resource_allocation r2 WHERE r2.employee_id=r1.employee_id)
AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')))
UNION
(SELECT DISTINCT o1.name BG, o2.name BU,c.skill skill, r1.employee_id B1,
 DATEDIFF(
 CASE WHEN ISNULL(release_date)
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 WHEN release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 ELSE release_date
 END,date_of_joining) 'No_of_Days'
FROM org_hierarchy o1 INNER JOIN org_hierarchy o2 ON o1.id=o2.parent_id
INNER JOIN default_project dp ON dp.bu_id=o2.id 
INNER JOIN resource r1 ON r1.current_bu_id=o2.id AND r1.current_bu_id=dp.bu_id
INNER JOIN competency c ON r1.competency=c.id
WHERE date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')))
UNION
(SELECT DISTINCT o1.name BG, o2.name BU, c.skill skill, r1.employee_id B1,
 DATEDIFF(STR_TO_DATE(@ENDDATE, '%Y-%m-%d'),MAX(alloc_end_date)) 'No_of_Days'
FROM org_hierarchy o1 INNER JOIN org_hierarchy o2 ON o1.id=o2.parent_id 
INNER JOIN resource r1 ON r1.current_bu_id=o2.id INNER JOIN competency c ON r1.competency=c.id
INNER JOIN resource_allocation r ON r.employee_id=r1.employee_id
WHERE date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND r1.employee_id NOT IN
(SELECT DISTINCT r.employee_id B1
FROM resource_allocation r1 INNER JOIN resource r ON r1.employee_id=r.employee_id
WHERE date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(release_date) OR release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')) 
AND alloc_start_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND 
(ISNULL(alloc_end_date) OR alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')))
 GROUP BY o1.name, o2.name, c.skill, r1.employee_id)
) 
 A WHERE No_of_Days>30 AND No_of_Days<=60
 GROUP BY BG,BU,skill) Y ON (X.BG=Y.BG AND X.BU=Y.BU AND X.skill=Y.skill)) Z1 
 
 LEFT OUTER JOIN 
 
(SELECT A.BG, A.BU, A.skill, COUNT(A.employee_id) '2_to_3_month_on_Bench'  FROM 
 (
 
-- The below unioned query used to find out resources who is on 'Bench' or 'unallocated' in any project.
-- The first query finds those resourcs who is specially specified to be on bench.
-- The second query finds those resources who are only exist in resource table hence these all are marked as on bench resources.
-- The third query finds those resources who has assignedd default project. 
-- The fourth query finds those resources who exist in resource_allocation table without allocation with any project.
 (SELECT o1.name BG,o2.name BU,c.skill skill, r1.employee_id,
 SUM(DATEDIFF(
 CASE WHEN ISNULL(alloc_end_date)
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 WHEN alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 ELSE alloc_end_date
 END,alloc_start_date)) 'No_of_Days'
 FROM org_hierarchy o1, org_hierarchy o2, resource r1, competency c, resource_allocation r, allocationtype a
 WHERE o1.id=o2.parent_id AND r1.current_bu_id=o2.id AND r.employee_id=r1.employee_id AND r1.competency=c.id
 AND r.allocation_type_id=a.id AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') 
 AND (ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND alloc_start_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
 (ISNULL(alloc_end_date) OR alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND a.allocationtype IN ('Non-Billable (Unallocated)','Non-Billable (Shadow)' ,'Non-Billable (Blocked)')
 GROUP BY o1.name,o2.name,r1.employee_id) 
UNION 
 (SELECT DISTINCT o1.name BG,o2.name BU, c.skill skill, r1.employee_id B1, 
 DATEDIFF(
 CASE WHEN ISNULL(release_date)
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 WHEN release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 ELSE release_date
 END,date_of_joining) 'No_of_Days'
FROM org_hierarchy o1 INNER JOIN org_hierarchy o2 ON o1.id=o2.parent_id
INNER JOIN resource r1 ON r1.current_bu_id=o2.id INNER JOIN competency c ON r1.competency=c.id
WHERE NOT EXISTS (SELECT DISTINCT r1.employee_id FROM resource_allocation r2 WHERE r2.employee_id=r1.employee_id)
AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')))
UNION
(SELECT DISTINCT o1.name BG, o2.name BU, c.skill skill, r1.employee_id B1,
 DATEDIFF(
 CASE WHEN ISNULL(release_date)
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 WHEN release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 ELSE release_date
 END,date_of_joining) 'No_of_Days'
FROM org_hierarchy o1 INNER JOIN org_hierarchy o2 ON o1.id=o2.parent_id
INNER JOIN default_project dp ON dp.bu_id=o2.id 
INNER JOIN resource r1 ON r1.current_bu_id=o2.id AND r1.current_bu_id=dp.bu_id
INNER JOIN competency c ON r1.competency=c.id
WHERE date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')))
UNION
(SELECT DISTINCT o1.name BG, o2.name BU, c.skill skill, r1.employee_id B1,
 DATEDIFF(STR_TO_DATE(@ENDDATE, '%Y-%m-%d'),MAX(alloc_end_date)) 'No_of_Days'
FROM org_hierarchy o1 INNER JOIN org_hierarchy o2 ON o1.id=o2.parent_id 
INNER JOIN resource r1 ON r1.current_bu_id=o2.id INNER JOIN competency c ON r1.competency=c.id
INNER JOIN resource_allocation r ON r.employee_id=r1.employee_id
WHERE date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND r1.employee_id NOT IN
(SELECT DISTINCT r.employee_id B1
FROM resource_allocation r1 INNER JOIN resource r ON r1.employee_id=r.employee_id
WHERE date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(release_date) OR release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')) 
AND alloc_start_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND 
(ISNULL(alloc_end_date) OR alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')))
 GROUP BY o1.name, o2.name, c.skill, r1.employee_id)
) 
A WHERE No_of_Days>60 AND No_of_Days<=90
 GROUP BY BG,BU,skill) Z2 ON (Z1.BG=Z2.BG AND Z1.BU=Z2.BU AND Z1.skill=Z2.skill)) Z3
 
LEFT OUTER JOIN 
(SELECT A.BG, A.BU, A.skill, COUNT(A.employee_id) 'More_than_3_months_on_bench'  FROM 
 (
 
-- The below unioned query used to find out resources who is on 'Bench' or 'unallocated' in any project.
-- The first query finds those resourcs who is specially specified to be on bench.
-- The second query finds those resources who are only exist in resource table hence these all are marked as on bench resources.
-- The third query finds those resources who has assignedd default project. 
-- The fourth query finds those resources who exist in resource_allocation table without allocation with any project.
 (SELECT o1.name BG,o2.name BU,c.skill skill, r1.employee_id,
 SUM(DATEDIFF(
 CASE WHEN ISNULL(alloc_end_date)
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 WHEN alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 ELSE alloc_end_date
 END,alloc_start_date)) 'No_of_Days'
 FROM org_hierarchy o1, org_hierarchy o2, resource r1, competency c, resource_allocation r, allocationtype a
 WHERE o1.id=o2.parent_id AND r1.current_bu_id=o2.id AND r.employee_id=r1.employee_id AND r1.competency=c.id
 AND r.allocation_type_id=a.id AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') 
 AND (ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND alloc_start_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
 (ISNULL(alloc_end_date) OR alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND a.allocationtype IN ('Non-Billable (Unallocated)','Non-Billable (Shadow)' ,'Non-Billable (Blocked)')
 GROUP BY o1.name,o2.name,r1.employee_id) 
UNION 
 (SELECT DISTINCT o1.name BG,o2.name BU,c.skill skill, r1.employee_id B1, 
 DATEDIFF(
 CASE WHEN ISNULL(release_date)
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 WHEN release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 ELSE release_date
 END,date_of_joining) 'No_of_Days'
FROM org_hierarchy o1 INNER JOIN org_hierarchy o2 ON o1.id=o2.parent_id
INNER JOIN resource r1 ON r1.current_bu_id=o2.id INNER JOIN competency c ON r1.competency=c.id
WHERE NOT EXISTS (SELECT DISTINCT r1.employee_id FROM resource_allocation r2 WHERE r2.employee_id=r1.employee_id)
AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')))
UNION
(SELECT DISTINCT o1.name BG, o2.name BU, c.skill skill, r1.employee_id B1,
 DATEDIFF(
 CASE WHEN ISNULL(release_date)
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 WHEN release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 THEN STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 ELSE release_date
 END,date_of_joining) 'No_of_Days'
FROM org_hierarchy o1 INNER JOIN org_hierarchy o2 ON o1.id=o2.parent_id
INNER JOIN default_project dp ON dp.bu_id=o2.id 
INNER JOIN resource r1 ON r1.current_bu_id=o2.id AND r1.current_bu_id=dp.bu_id
INNER JOIN competency c ON r1.competency=c.id
WHERE date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')))
UNION
(SELECT DISTINCT o1.name BG, o2.name BU, c.skill skill, r1.employee_id B1,
 DATEDIFF(STR_TO_DATE(@ENDDATE, '%Y-%m-%d'),MAX(alloc_end_date)) 'No_of_Days'
FROM org_hierarchy o1 INNER JOIN org_hierarchy o2 ON o1.id=o2.parent_id 
INNER JOIN resource r1 ON r1.current_bu_id=o2.id INNER JOIN competency c ON r1.competency=c.id
INNER JOIN resource_allocation r ON r.employee_id=r1.employee_id
WHERE date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND r1.employee_id NOT IN
(SELECT DISTINCT r.employee_id B1
FROM resource_allocation r1 INNER JOIN resource r ON r1.employee_id=r.employee_id
WHERE date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(release_date) OR release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')) 
AND alloc_start_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND 
(ISNULL(alloc_end_date) OR alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')))
 GROUP BY o1.name, o2.name, c.skill, r1.employee_id)
) 
 A WHERE No_of_Days>90
 GROUP BY BG,BU,skill) Z4 ON (Z3.BG=Z4.BG AND Z3.BU=Z4.BU AND Z3.skill=Z4.skill)) Final
 WHERE Final.A!=0 OR Final.B!=0 OR Final.C!=0 OR Final.D!=0
 ORDER BY BG ASC,BU ASC,skill ASC;
 
END$$

DELIMITER ;




/* Procedure structure for procedure `RMG_BGBU_Metrics` */

DELIMITER $$

DROP PROCEDURE IF EXISTS `RMG_BGBU_Metrics`$$

CREATE DEFINER=`root`@`%` PROCEDURE `RMG_BGBU_Metrics`(IN start_date VARCHAR(2), 
      IN end_date VARCHAR(2),IN start_month VARCHAR(2),
	  IN end_month VARCHAR(2),IN start_year VARCHAR(4), IN end_year VARCHAR(4))
BEGIN
SET @STARTDATE = CONCAT(`start_year`,'-',`start_month`,'-',`start_date`);
SET @ENDDATE = CONCAT(`end_year`,'-',`end_month`,'-',`end_date`);
 
 SELECT Q.BG BG, Q.BU BU,Total,Management_Resources,Billed_Resources,Unbilled_Resources,
  Unallocated_Resources, CONCAT(ROUND(COALESCE(Bill_partial_bill/Total*100,0),2),'%') AS 'Bill_Percent',  
  CONCAT(ROUND(COALESCE(Bill_partial_bill/(Bill_partial_bill+non_billable)*100,0),2),'%') AS 'BNB_Percent',
  CONCAT(ROUND(COALESCE(Bill_partial_bill/(Bill_partial_bill+NB_except_investment)*100,0),2),'%')
  AS 'BNB_except_investment_Percent',
  CONCAT(ROUND(COALESCE(Bill_partial_bill/(Bill_partial_bill+BNB_except_investment_trainee)*100,0),2),'%')
  AS 'BNB_except_investment_trainee_Percent' ,
  CONCAT(ROUND(COALESCE(Unallocated_Resources/Total*100,0),2),'%') AS 'Un-Allocation_Percent',
  CONCAT(ROUND((((Management_Resources+Billed_Resources+Unbilled_Resources)/Total)*100),2),'%') 'Utilization_Percent'
  FROM
        
 (SELECT O.BG, O.BU,Total,Management_Resources,Billed_Resources,Unbilled_Resources,
        Unallocated_Resources, Bill_partial_bill, non_billable, NB_except_investment, 
        P.P1 AS 'BNB_except_investment_trainee' FROM  
        
(SELECT M.BG, M.BU,Total,Management_Resources,Billed_Resources,Unbilled_Resources,
        Unallocated_Resources, Bill_partial_bill, non_billable, N.N1 AS 'NB_except_investment' FROM  
        
(SELECT K.BG, K.BU,Total,Management_Resources,Billed_Resources,Unbilled_Resources,
        Unallocated_Resources, Bill_partial_bill, L.L1 AS 'non_billable' FROM  
       
(SELECT I.BG, I.BU,Total,Management_Resources,Billed_Resources,Unbilled_Resources,
        Unallocated_Resources, J.J1 AS 'Bill_partial_bill' FROM  
           
(SELECT G.BG, G.BU, Total,Management_Resources,Billed_Resources,Unbilled_Resources,COALESCE(H.B1,0) AS 'Unallocated_Resources' FROM
(SELECT E.BG,E.BU,Total,Management_Resources,Billed_Resources, COALESCE(B1,0) AS 'Unbilled_Resources' FROM
(SELECT C.BG,C.BU,Total,Management_Resources,COALESCE(B1,0) AS 'Billed_Resources' FROM
(SELECT A.BG,A.BU,A1 'Total',COALESCE(B1,0) AS 'Management_Resources' FROM
-- Below query finds Total resources present in Resource table and who is actively working in Org.
(SELECT DISTINCT o1.name BG,o2.name BU,COUNT(r1.employee_id) A1
FROM org_hierarchy o1, org_hierarchy o2, resource r1
WHERE o1.id=o2.parent_id AND r1.current_bu_id=o2.id AND
date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
GROUP BY o1.name,o2.name) A 
LEFT OUTER JOIN
-- Below query finds managment resources 
(SELECT DISTINCT o1.name BG,o2.name BU,COUNT(r1.employee_id) B1
FROM org_hierarchy o1, org_hierarchy o2, resource_allocation r, allocationtype a, resource r1
WHERE o1.id=o2.parent_id AND r1.current_bu_id=o2.id AND r.allocation_type_id=a.id AND r.employee_id=r1.employee_id 
AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')) AND
a.allocationtype LIKE '%Management%' AND alloc_start_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
AND (ISNULL(alloc_end_date) OR alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
GROUP BY o1.name,o2.name) B 
ON A.BG=B.BG AND A.BU=B.BU) C
LEFT OUTER JOIN
 
-- Below query finds Billable
(SELECT DISTINCT o1.name BG,o2.name BU,COUNT(DISTINCT(r1.employee_id)) B1
FROM org_hierarchy o1, org_hierarchy o2, resource_allocation r, allocationtype a,resource r1
WHERE o1.id=o2.parent_id AND r1.current_bu_id=o2.id AND r.allocation_type_id=a.id AND r.employee_id=r1.employee_id
AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
AND a.allocationtype LIKE 'Billable%' AND alloc_start_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
AND (ISNULL(alloc_end_date) OR alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
GROUP BY o1.name,o2.name) D
ON C.BG=D.BG AND C.BU=D.BU) E
LEFT OUTER JOIN
-- Below query finds Non-Billable resources 
(SELECT DISTINCT o1.name BG,o2.name BU,COUNT(DISTINCT(r1.employee_id)) B1
FROM org_hierarchy o1, org_hierarchy o2, resource_allocation r, allocationtype a,resource r1
WHERE o1.id=o2.parent_id AND r1.current_bu_id=o2.id AND r.allocation_type_id=a.id AND r.employee_id=r1.employee_id 
AND (ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')) AND
date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND alloc_start_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
AND a.allocationtype NOT LIKE 'Billable%' AND a.allocationtype NOT LIKE '%Management%' AND
a.allocationtype NOT LIKE '%Unallocated%' AND a.allocationtype NOT LIKE 'Non-Billable (Shadow)' 
AND a.allocationtype NOT LIKE 'Non-Billable (Blocked)'
AND (ISNULL(alloc_end_date) OR alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
GROUP BY o1.name,o2.name) F
ON E.BG=F.BG AND E.BU=F.BU) G
LEFT OUTER JOIN 
-- Below Query calculates unallocated resources for a particular BG-BU by following different criteria such as :
-- First sub query find out resources who is present only in Resources allocation table with matching allocaion type. 
-- Second unioned subquery find out resources who is available only in resource table.
-- Thired unioned query has written to find out resources from default-project table who has assigned default project
-- Default project assigned resource is marked as unaloocated resource.
-- Fourth unioned query find out resources who is not currently in any project assigned and still exist in 
-- resource allocation table with expired alloc_end_date(compared with argument 'end_date')
(SELECT DISTINCT t4.BG BG, t4.BU BU, COUNT(DISTINCT(B1)) B1 FROM (
(SELECT DISTINCT o1.name BG,o2.name BU, r1.employee_id B1
FROM org_hierarchy o1, org_hierarchy o2, resource_allocation r, allocationtype a,resource r1
WHERE o1.id=o2.parent_id AND r1.current_bu_id=o2.id AND r.allocation_type_id=a.id AND 
r.employee_id=r1.employee_id AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
AND alloc_start_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')) 
AND a.allocationtype IN ('Non-Billable (Unallocated)','Non-Billable (Shadow)' ,'Non-Billable (Blocked)')
AND (ISNULL(alloc_end_date) OR alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')))
UNION 
(SELECT DISTINCT o1.name BG,o2.name BU, r1.employee_id B1
FROM org_hierarchy o1 INNER JOIN org_hierarchy o2 ON o1.id=o2.parent_id
INNER JOIN resource r1 ON r1.current_bu_id=o2.id
WHERE NOT EXISTS (SELECT * FROM resource_allocation r2 WHERE r2.employee_id=r1.employee_id)
AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') 
AND (ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')))
UNION 
(SELECT DISTINCT o1.name BG, o2.name BU, r1.employee_id B1
FROM org_hierarchy o1 INNER JOIN org_hierarchy o2 ON o1.id=o2.parent_id
INNER JOIN default_project dp ON dp.bu_id=o2.id 
INNER JOIN resource r1 ON r1.current_bu_id=o2.id AND r1.current_bu_id=dp.bu_id
WHERE date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')))
UNION
(SELECT DISTINCT o1.name BG, o2.name BU, r1.employee_id B1
FROM org_hierarchy o1 INNER JOIN org_hierarchy o2 ON o1.id=o2.parent_id
INNER JOIN resource r1 ON r1.current_bu_id=o2.id 
INNER JOIN resource_allocation r ON r.employee_id=r1.employee_id
WHERE date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND r1.employee_id NOT IN
(SELECT DISTINCT r.employee_id B1
FROM resource_allocation r1 INNER JOIN resource r ON r1.employee_id=r.employee_id
WHERE date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(release_date) OR release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')) 
AND alloc_start_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND 
(ISNULL(alloc_end_date) OR alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))))) t4 
 GROUP BY t4.BG, t4.BU) H
 ON G.BG=H.BG AND G.BU=H.BU) I
 
LEFT OUTER JOIN 
-- Below query written to calculate Billable resources percant(%). if a resource is partial billable
-- then it will be half of billable.
 
(SELECT Y.BG BG, Y.BU BU, SUM(Y.X1) J1 FROM
((SELECT o1.name BG,o2.name BU,COALESCE(COUNT(DISTINCT r.employee_id),0) X1
 FROM org_hierarchy o1, org_hierarchy o2, resource r ,resource_allocation ra, allocationtype a
 WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND r.employee_id=ra.employee_id AND
 ra.allocation_type_id=a.id AND r.date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') 
 AND (ISNULL(r.release_date) OR r.release_date > STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND ra.alloc_start_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND 
 (ISNULL(ra.alloc_end_date) OR ra.alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND a.allocationtype LIKE 'Billable (Full Time (FTE))' GROUP BY o1.name,o2.name) 
 UNION
 (SELECT o1.name BG,o2.name BU, COUNT(DISTINCT r.employee_id)/2 X1
 FROM org_hierarchy o1, org_hierarchy o2, resource r ,resource_allocation ra, allocationtype a
 WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND r.employee_id=ra.employee_id AND
 ra.allocation_type_id=a.id AND r.date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') 
 AND (ISNULL(r.release_date) OR r.release_date > STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND ra.alloc_start_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND 
 (ISNULL(ra.alloc_end_date) OR ra.alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND a.allocationtype LIKE 'Billable (Partial%' GROUP BY o1.name,o2.name)) Y
 GROUP BY Y.BG, Y.BU) J
 ON I.BG=J.BG AND I.BU=J.BU) K
 
 LEFT OUTER JOIN
 
 (SELECT o1.name BG,o2.name BU,COUNT(DISTINCT(r1.employee_id)) L1
  FROM org_hierarchy o1, org_hierarchy o2, resource r1, resource_allocation r, allocationtype a
  WHERE o1.id=o2.parent_id AND r1.current_bu_id=o2.id AND r.employee_id=r1.employee_id AND r.allocation_type_id=a.id 
  AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
  (ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
  AND a.allocationtype LIKE 'Non-Billable%' AND alloc_start_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
  AND r.curProj=1  AND (ISNULL(alloc_end_date) OR alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')) 
  GROUP BY o1.name,o2.name) L
  ON K.BG=L.BG AND K.BU=L.BU) M
  
  LEFT OUTER JOIN
  
 (SELECT o1.name BG,o2.name BU,COUNT(DISTINCT(r1.employee_id)) N1
   FROM org_hierarchy o1, org_hierarchy o2, resource r1, resource_allocation r, allocationtype a
   WHERE o1.id=o2.parent_id AND r1.current_bu_id=o2.id AND r.employee_id=r1.employee_id AND r.allocation_type_id=a.id 
   AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
   (ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
   AND a.allocationtype LIKE 'Non-Billable%' AND a.allocationtype NOT LIKE 'Non-Billable (Investment)' AND
   alloc_start_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND r.curProj=1 AND 
   (ISNULL(alloc_end_date) OR alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')) 
   GROUP BY o1.name,o2.name) N
   ON M.BG=N.BG AND M.BU=N.BU) O
  
    LEFT OUTER JOIN
    
   (SELECT o1.name BG,o2.name BU,COUNT(DISTINCT(r1.employee_id)) P1
   FROM org_hierarchy o1, org_hierarchy o2, resource r1, resource_allocation r, allocationtype a
   WHERE o1.id=o2.parent_id AND r1.current_bu_id=o2.id AND r.employee_id=r1.employee_id AND r.allocation_type_id=a.id 
   AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
   (ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
   AND a.allocationtype LIKE 'Non-Billable%' AND
   a.allocationtype NOT LIKE 'Non-Billable (Investment)' AND a.allocationtype NOT LIKE 'Non-Billable (Trainee)'
   AND alloc_start_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND r.curProj=1
   AND (ISNULL(alloc_end_date) OR alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')) 
   GROUP BY o1.name,o2.name) P 
   ON O.BG=P.BG AND O.BU=P.BU) Q ;
 
END$$

DELIMITER ;


/* Procedure structure for procedure `RMG_GradeWise_Report` */

DELIMITER $$


DROP PROCEDURE IF EXISTS `RMG_GradeWise_Report`$$

CREATE DEFINER=`root`@`%` PROCEDURE `RMG_GradeWise_Report`(IN start_date CHAR(2), 
      IN end_date CHAR(2),IN start_month VARCHAR(2),
	  IN end_month VARCHAR(2),IN start_year CHAR(4), IN end_year VARCHAR(4))
BEGIN
SET @STARTDATE = CONCAT(`start_year`,'-',`start_month`,'-',`start_date`);
SET @ENDDATE = CONCAT(`end_year`,'-',`end_month`,'-',`end_date`);
SELECT G.A1 'Grade', G.BG 'BG', G.BU 'BU', COALESCE(G.A2,0) 'Number of Resources', COALESCE(G.B2,0) 'Joined in Year', 
COALESCE(G.D2,0) 'Joined in Month',COALESCE(G.F2,0) 'Left in Year',COALESCE(H.H2,0) 'Left in Month', 
CONCAT(ROUND((H.H2/G.A2*100),2),'%') 'Month Attrition'
FROM 
(SELECT A1,E.BG,E.BU ,A2,B2,D2,F2 FROM
(SELECT A1,C.BG,C.BU,A2,B2,D2 FROM 
(SELECT A.A1,A.BG,A.BU,A.A2,B.B2 FROM 
(SELECT Temp.grade A1,Temp.BG,Temp.BU,Z1.A2 FROM
(SELECT grade, BG, BU FROM 
 (SELECT '1' T1, o1.name BG, o2.name BU
  FROM org_hierarchy o1, org_hierarchy o2, resource r
  WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id
  GROUP BY o1.name,o2.name) M, 
 (SELECT '1' T2, grade FROM grade) N
 WHERE T1=T2) Temp
 
 LEFT OUTER JOIN
 -- query to find total active resources
 
 (SELECT g.grade A1,o1.name BG,o2.name BU,COUNT(r.employee_id) A2
  FROM org_hierarchy o1, org_hierarchy o2, resource r, grade g
  WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND 
  r.grade_id=g.id AND `date_of_joining` < STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
  (ISNULL(r.release_date) OR r.release_date> STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
  GROUP BY g.grade,o1.name,o2.name) Z1 ON (Temp.grade=Z1.A1 AND Temp.BG=Z1.BG AND Temp.BU=Z1.BU))
  A
  
  LEFT OUTER JOIN
  
  -- query to calculate resources who joined in respective year 
  
 (SELECT g.grade B1,o1.name BG,o2.name BU,COUNT(r.employee_id) B2
  FROM org_hierarchy o1,org_hierarchy o2,resource r,grade g
  WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND r.grade_id=g.id AND 
  YEAR(date_of_joining)=`end_year` AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
  GROUP BY g.grade,o1.name,o2.name)
  B ON A.A1=B.B1 AND A.BG=B.BG AND A.BU=B.BU) C
  
 LEFT OUTER JOIN
  
  -- query to calculate resources who joined in respective Year Month
   
 (SELECT g.grade D1,o1.name BG,o2.name BU,COUNT(r.employee_id) D2
  FROM org_hierarchy o1, org_hierarchy o2, resource r, grade g
  WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id  AND r.grade_id=g.id AND 
  date_of_joining BETWEEN STR_TO_DATE(@STARTDATE, '%Y-%m-%d') AND STR_TO_DATE(@ENDDATE, '%Y-%m-%d')  
  GROUP BY g.grade,o1.name,o2.name)
  D ON A1=D1 AND C.BG=D.BG AND C.BU=D.BU) E 
  LEFT OUTER JOIN
-- query to calculate resources who left in respective Year
 (SELECT g.grade F1,o1.name BG,o2.name BU,COUNT(r.employee_id) F2
  FROM org_hierarchy o1, org_hierarchy o2, resource r, grade g
  WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND r.grade_id=g.id AND 
  date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND YEAR(release_date)=`end_year` 
   AND release_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
  GROUP BY g.grade,o1.name,o2.name)
  F ON A1=F1 AND E.BG=F.BG AND E.BU=F.BU) G 
  
  LEFT OUTER JOIN
  
  -- query to calculate resources who left in respective Year Month
  
 (SELECT g.grade H1,o1.name BG,o2.name BU,COUNT(r.employee_id) H2
  FROM org_hierarchy o1, org_hierarchy o2, resource r, grade g
  WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND r.grade_id=g.id 
  AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND 
  release_date BETWEEN STR_TO_DATE(@STARTDATE, '%Y-%m-%d') AND STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
  GROUP BY g.grade,o1.name,o2.name)
  H ON A1=H1 AND G.BG=H.BG AND G.BU=H.BU 
  WHERE G.A2!=0 OR G.B2!=0 OR G.D2!=0 OR G.F2!=0 OR H.H2!=0  
  ORDER BY G.A1,G.BG,G.BU;
  
END$$

DELIMITER ;


/* Procedure structure for procedure `RMG_Projects_Closing_In_3_Months` */

DELIMITER $$


DROP PROCEDURE IF EXISTS `RMG_Projects_Closing_In_3_Months`$$

CREATE DEFINER=`root`@`%` PROCEDURE `RMG_Projects_Closing_In_3_Months`(IN start_date VARCHAR(2), 
      IN end_date VARCHAR(2),IN start_month VARCHAR(2),
	  IN end_month VARCHAR(2),IN start_year VARCHAR(4), IN end_year VARCHAR(4))
BEGIN
SET @STARTDATE = CONCAT(`start_year`,'-',`start_month`,'-',`start_date`);
SET @ENDDATE = CONCAT(`end_year`,'-',`end_month`,'-',`end_date`);
SELECT A.project_name 'Project', RTRIM(LTRIM(A.BG)) 'Parent BG', RTRIM(LTRIM(A.BU)) 'Parent BU',
CNT1 'Resources in Projects ending in 0-1 month', CNT2 'Resources in Projects ending in 1-2 Month',
COALESCE(B.CNT,0) 'Resources in Projects ending in 2-3 Months' FROM
(SELECT A.project_name,A.BG,A.BU,CNT1,COALESCE(B.CNT,0) CNT2 FROM
(SELECT A.project_name,A.BG,A.BU, COALESCE(B.CNT,0) CNT1 FROM
(SELECT p.project_name,o1.name BG,o2.name BU,COUNT(r.employee_id) CNT
FROM project p, org_hierarchy o1,resource r,org_hierarchy o2
WHERE o1.id=o2.parent_id AND p.bu_id=o2.id AND r.current_project_id=p.id AND 
project_end_date>@ENDDATE AND project_end_date<=DATE_ADD(@ENDDATE, INTERVAL 3 MONTH) 
GROUP BY p.project_name,o1.name,o2.name) A
 LEFT OUTER JOIN 
(SELECT p.project_name,o1.name BG,o2.name BU,COUNT(r.employee_id) CNT
FROM project p, org_hierarchy o1,resource r,org_hierarchy o2
WHERE p.bu_id=o2.id AND r.current_project_id=p.id AND o1.id=o2.parent_id AND
project_end_date>@ENDDATE AND project_end_date<=DATE_ADD(@ENDDATE, INTERVAL 1 MONTH) 
GROUP BY p.project_name,o1.name,o2.name) B ON (A.project_name=B.project_name AND A.BG=B.BG AND A.BU=B.BU)) A
LEFT OUTER JOIN 
(SELECT p.project_name,o1.name BG,o2.name BU,COUNT(r.employee_id) CNT
FROM  org_hierarchy o1, org_hierarchy o2, resource r,  project p
WHERE o1.id=o2.parent_id AND p.bu_id=o2.id AND r.current_project_id=p.id AND 
project_end_date>DATE_ADD(@ENDDATE, INTERVAL 1 MONTH) AND project_end_date<=DATE_ADD(@ENDDATE, INTERVAL 2 MONTH)
GROUP BY p.project_name,o1.name,o2.name) B ON (A.project_name=B.project_name AND A.BG=B.BG AND A.BU=B.BU)) A 
LEFT OUTER JOIN 
(SELECT p.project_name,o1.name BG,o2.name BU,COUNT(r.employee_id) CNT
FROM org_hierarchy o1, org_hierarchy o2, resource r, project p
WHERE o1.id=o2.parent_id AND p.bu_id=o2.id AND r.current_project_id=p.id AND 
project_end_date>DATE_ADD(@ENDDATE, INTERVAL 2 MONTH) AND project_end_date<=DATE_ADD(@ENDDATE, INTERVAL 3 MONTH)
GROUP BY p.project_name,o1.name,o2.name) B ON (A.project_name=B.project_name AND A.BG=B.BG AND A.BU=B.BU);
END$$

DELIMITER ;


/* Procedure structure for procedure `RMG_Resource_Metrics` */


DELIMITER $$

DROP PROCEDURE IF EXISTS `RMG_Resource_Metrics`$$

CREATE DEFINER=`root`@`%` PROCEDURE `RMG_Resource_Metrics`(IN start_date VARCHAR(2), 
      IN end_date VARCHAR(2),IN start_month VARCHAR(2),
	  IN end_month VARCHAR(2),IN start_year VARCHAR(4), IN end_year VARCHAR(4))
BEGIN
SET @STARTDATE = CONCAT(`start_year`,'-',`start_month`,'-',`start_date`);
SET @ENDDATE = CONCAT(`end_year`,'-',`end_month`,'-',`end_date`);
SELECT BG,BU, Total, Joined_in_Year,Joined_in_YearMonth, Left_in_Year,
 Left_in_YearMonth, YearMonth_Attrition, CONCAT(ROUND((total_billable/(total_billable+non_billable)*100),2),'%') 'billable_non_billable',
 CONCAT(ROUND((total_billable/(total_billable+non_billable_except_investment)*100),2),'%') 'billable_non_billable_except_investment',
 CONCAT(ROUND((total_billable/(total_billable+non_billable_except_investment_trainee)*100),2),'%') 
 'billable_non_billable_except_investment_trainee'
 FROM
(SELECT ABCDEFGH.BG BG, ABCDEFGH.BU BU, ABCDEFGH.Total, ABCDEFGH.Joined_in_Year, ABCDEFGH.Joined_in_YearMonth, ABCDEFGH.Left_in_Year,
 ABCDEFGH.Left_in_YearMonth, ABCDEFGH.YearMonth_Attrition, ABCDEFGH.total_billable,ABCDEFGH.non_billable, 
 ABCDEFGH.non_billable_except_investment,COALESCE(I.I1,0) AS 'non_billable_except_investment_trainee' 
 FROM
(SELECT ABCDEFG.BG, ABCDEFG.BU, ABCDEFG.Total, ABCDEFG.Joined_in_Year,ABCDEFG.Joined_in_YearMonth, ABCDEFG.Left_in_Year,
 ABCDEFG.Left_in_YearMonth, ABCDEFG.YearMonth_Attrition, ABCDEFG.total_billable,ABCDEFG.non_billable, 
 COALESCE(H.H1,0) AS 'non_billable_except_investment'
 FROM
(SELECT ABCDEF.BG, ABCDEF.BU, ABCDEF.Total, ABCDEF.Joined_in_Year,ABCDEF.Joined_in_YearMonth, ABCDEF.Left_in_Year,
 ABCDEF.Left_in_YearMonth, ABCDEF.YearMonth_Attrition, ABCDEF.total_billable, COALESCE(G.G1,0) AS 'non_billable'
 FROM
(SELECT ABCDE.BG, ABCDE.BU, ABCDE.Total, ABCDE.Joined_in_Year,ABCDE.Joined_in_YearMonth, ABCDE.Left_in_Year,
 ABCDE.Left_in_YearMonth, ABCDE.YearMonth_Attrition, COALESCE(F.F1,0) 'total_billable' 
 FROM
(SELECT ABCD.BG, ABCD.BU, ABCD.Total, ABCD.Joined_in_Year,ABCD.Joined_in_YearMonth, ABCD.Left_in_Year, 
 COALESCE(E.E1,0) AS 'Left_in_YearMonth', CONCAT(COALESCE(ROUND(E.E1/ABCD.Total*100,2),0),'%') 'YearMonth_Attrition'
 FROM
(SELECT ABC.BG,ABC.BU,ABC.Total,ABC.Joined_in_Year,ABC.Joined_in_YearMonth,COALESCE(D.D1,0) AS 'Left_in_Year' FROM
(SELECT AB.BG,AB.BU,Total,Joined_in_Year,COALESCE(C1,0) AS 'Joined_in_YearMonth' FROM
(SELECT A.BG,A.BU,A1 'Total',COALESCE(B1,0) AS 'Joined_in_Year' FROM
-- Below query finds Total Active resources present in Resource table and who is actively working in Org.
(SELECT DISTINCT o1.name BG, o2.name BU, COUNT(r1.employee_id) A1
FROM org_hierarchy o1, org_hierarchy o2, resource r1
WHERE o1.id=o2.parent_id AND r1.current_bu_id=o2.id AND
date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
(ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
GROUP BY o1.name,o2.name) A 
LEFT OUTER JOIN
-- Below query finds resources joined in year
(SELECT o1.name BG,o2.name BU,COUNT(*) B1 FROM 
 org_hierarchy o1, org_hierarchy o2, resource r 
 WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id 
 AND YEAR(date_of_joining)=`end_year` AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 GROUP BY o1.name,o2.name) B 
 ON A.BG=B.BG AND A.BU=B.BU) AB 
LEFT OUTER JOIN
-- Below query finds resources joined in year month
(SELECT o1.name BG,o2.name BU,COUNT(*) C1 FROM 
  org_hierarchy o1, org_hierarchy o2, resource r  
  WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND 
  date_of_joining BETWEEN STR_TO_DATE(@STARTDATE, '%Y-%m-%d') AND STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
  GROUP BY o1.name,o2.name) C
  ON C.BG=AB.BG AND C.BU=AB.BU) ABC
LEFT OUTER JOIN
-- Below query finds resources left in year
(SELECT o1.name BG,o2.name BU,COUNT(*) D1 FROM 
  org_hierarchy o1, org_hierarchy o2, resource r  
  WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id  AND
  YEAR(release_date)=`end_year` AND release_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
  GROUP BY o1.name,o2.name) D
  ON ABC.BG=D.BG AND ABC.BU=D.BU) ABCD 
  
 LEFT OUTER JOIN 
 
 -- Below query finds resources left in year month
 
(SELECT o1.name BG,o2.name BU,COUNT(*) E1 FROM 
  org_hierarchy o1, org_hierarchy o2, resource r  
  WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND 
  release_date BETWEEN STR_TO_DATE(@STARTDATE, '%Y-%m-%d') AND STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
  GROUP BY o1.name,o2.name) E
  ON ABCD.BG=E.BG AND ABCD.BU=E.BU) ABCDE
  
  LEFT OUTER JOIN
  
-- Below query written to calculate Billable resources percant(%). if a resource is partial billable
-- then it will be half of billable.
   
 (SELECT Y.BG BG , Y.BU BU , SUM(Y.X1) F1 FROM
 ((SELECT o1.name BG,o2.name BU,COALESCE(COUNT(DISTINCT r.employee_id),0) X1
 FROM org_hierarchy o1, org_hierarchy o2, resource r ,resource_allocation ra, allocationtype a
 WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND r.employee_id=ra.employee_id AND
 ra.allocation_type_id=a.id AND r.date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') 
 AND (ISNULL(r.release_date) OR r.release_date > STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND ra.alloc_start_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND ra.curProj=1 AND
 (ISNULL(ra.alloc_end_date) OR ra.alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND a.allocationtype LIKE 'Billable (Full Time (FTE))' GROUP BY o1.name,o2.name) 
 UNION
 (SELECT o1.name BG,o2.name BU, COUNT(DISTINCT r.employee_id)/2 X1
 FROM org_hierarchy o1, org_hierarchy o2, resource r ,resource_allocation ra, allocationtype a
 WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND r.employee_id=ra.employee_id AND
 ra.allocation_type_id=a.id AND r.date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') 
 AND (ISNULL(r.release_date) OR r.release_date > STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND ra.alloc_start_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND ra.curProj=1 AND 
 (ISNULL(ra.alloc_end_date) OR ra.alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 AND a.allocationtype LIKE 'Billable (Partial%' GROUP BY o1.name,o2.name)) Y
 GROUP BY Y.BG, Y.BU) F
 ON ABCDE.BG=F.BG AND ABCDE.BU=F.BU) ABCDEF
  
  LEFT OUTER JOIN
  
   -- Below query finds all non billable (including all non-billable type) resources.
   
  (SELECT o1.name BG,o2.name BU,COUNT(DISTINCT(r1.employee_id)) G1
  FROM org_hierarchy o1, org_hierarchy o2, resource r1, resource_allocation r, allocationtype a
  WHERE o1.id=o2.parent_id AND r1.current_bu_id=o2.id AND r.employee_id=r1.employee_id AND r.allocation_type_id=a.id 
  AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
  (ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
  AND a.allocationtype LIKE 'Non-Billable%' AND alloc_start_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
  AND r.curProj=1  AND (ISNULL(alloc_end_date) OR alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')) 
  GROUP BY o1.name,o2.name) G
  ON ABCDEF.BG=G.BG AND ABCDEF.BU=G.BU) ABCDEFG
  LEFT OUTER JOIN
  
   -- Below query finds all non billable (excluding non-billable investment) resources.
 
  (SELECT o1.name BG,o2.name BU,COUNT(DISTINCT(r1.employee_id)) H1
   FROM org_hierarchy o1, org_hierarchy o2, resource r1, resource_allocation r, allocationtype a
   WHERE o1.id=o2.parent_id AND r1.current_bu_id=o2.id AND r.employee_id=r1.employee_id AND r.allocation_type_id=a.id 
   AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
   (ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
   AND a.allocationtype LIKE 'Non-Billable%' AND a.allocationtype NOT LIKE 'Non-Billable (Investment)' AND
   alloc_start_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND r.curProj=1 AND 
   (ISNULL(alloc_end_date) OR alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')) 
   GROUP BY o1.name,o2.name) H
   ON ABCDEFG.BG=H.BG AND ABCDEFG.BU=H.BU) ABCDEFGH
  
   LEFT OUTER JOIN
  
   -- Below query finds all non billable (excluding non-billable(investment) and non-billable(Trainee)) resources.
 
  (SELECT o1.name BG,o2.name BU,COUNT(DISTINCT(r1.employee_id)) I1
   FROM org_hierarchy o1, org_hierarchy o2, resource r1, resource_allocation r, allocationtype a
   WHERE o1.id=o2.parent_id AND r1.current_bu_id=o2.id AND r.employee_id=r1.employee_id AND r.allocation_type_id=a.id 
   AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND
   (ISNULL(r1.release_date) OR r1.release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
   AND a.allocationtype LIKE 'Non-Billable%' AND
   a.allocationtype NOT LIKE 'Non-Billable (Investment)' AND a.allocationtype NOT LIKE 'Non-Billable (Trainee)'
   AND alloc_start_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND r.curProj=1
   AND (ISNULL(alloc_end_date) OR alloc_end_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')) 
   GROUP BY o1.name,o2.name) I
   ON ABCDEFGH.BG=I.BG AND ABCDEFGH.BU=I.BU ORDER BY ABCDEFGH.BG,ABCDEFGH.BU) ABCDEFGHI;
   
END$$

DELIMITER ;


/* Procedure structure for procedure `RMG_Resource_Metrics_by_BGBU_Report` */


DELIMITER $$

DROP PROCEDURE IF EXISTS `RMG_Resource_Metrics_by_BGBU_Report`$$

CREATE DEFINER=`root`@`%` PROCEDURE `RMG_Resource_Metrics_by_BGBU_Report`(IN start_date CHAR(2), 
      IN end_date CHAR(2),IN start_month CHAR(2),
	  IN end_month CHAR(2),IN start_year CHAR(4), IN end_year CHAR(4))
BEGIN
SET @STARTDATE = CONCAT(`start_year`,'-',`start_month`,'-',`start_date`);
SET @ENDDATE = CONCAT(`end_year`,'-',`end_month`,'-',`end_date`);
 
 SELECT ABCDEF.BG BG, ABCDEF.BU BU,COALESCE(ABCDEF.A1,0) 'total_active_resource',COALESCE(ABCDEF.B1,0) 'join_as_on_date',
 COALESCE(ABCDEF.C1,0) 'left_as_on_date', COALESCE(ABCDEF.D1,0) 'join_monthly', 
 COALESCE(ABCDEF.E1,0) 'left_monthly', COALESCE(ABCDEF.F1,0) 'join_yearly', COALESCE(G.G1,0) 'left_yearly',
 CONCAT(ROUND(COALESCE((ABCDEF.E1/(ABCDEF.E1+ABCDEF.A1))*100,0),2),'%') 'year_month_attrition' FROM
(SELECT ABCDE.BG ,ABCDE.BU ,ABCDE.A1 ,ABCDE.B1,ABCDE.C1, ABCDE.D1, ABCDE.E1, F.F1 FROM
(SELECT ABCD.BG ,ABCD.BU ,ABCD.A1 ,ABCD.B1,ABCD.C1, ABCD.D1, E.E1 FROM
(SELECT ABC.BG ,ABC.BU ,ABC.A1 ,ABC.B1,ABC.C1, D.D1 FROM
(SELECT AB.BG ,AB.BU ,AB.A1 ,AB.B1,C.C1 FROM
(SELECT A.BG ,A.BU ,A.A1,B.B1 FROM
 -- BG , BU and total resources
 (SELECT o1.name BG,o2.name BU,COUNT(*) A1
 FROM org_hierarchy o1, org_hierarchy o2, resource r
 WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND r.date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') 
 AND (ISNULL(r.release_date) OR r.release_date > STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
 GROUP BY o1.name,o2.name) A 
 LEFT OUTER JOIN
 -- resources who joined as on date
 (SELECT o1.name BG,o2.name BU, COUNT(*) B1 FROM org_hierarchy o1, org_hierarchy o2, resource r
 WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') 
 GROUP BY o1.name,o2.name) B
 ON A.BG=B.BG AND A.BU=B.BU) AB 
LEFT OUTER JOIN
-- Resources Who left as on date
(SELECT o1.name BG,o2.name BU ,COUNT(*) C1
 FROM org_hierarchy o1, org_hierarchy o2, resource r
 WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND release_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 GROUP BY o1.name,o2.name) C
 ON AB.BG=C.BG AND AB.BU=C.BU) ABC 
 LEFT OUTER JOIN
 -- Resources Who Joined in specified year month 
 (SELECT o1.name BG,o2.name BU ,COUNT(*) D1
 FROM org_hierarchy o1, org_hierarchy o2, resource r
 WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND
 date_of_joining >= STR_TO_DATE(@STARTDATE, '%Y-%m-%d') AND 
 date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 GROUP BY o1.name,o2.name) D
 ON ABC.BG=D.BG AND ABC.BU=D.BU) ABCD 
 LEFT OUTER JOIN
 -- Resources Who Left in specified year month 
(SELECT o1.name BG,o2.name BU ,COUNT(*) E1
 FROM org_hierarchy o1, org_hierarchy o2, resource r
 WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND 
 release_date >= STR_TO_DATE(@STARTDATE, '%Y-%m-%d') AND release_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 GROUP BY o1.name,o2.name) E
 ON ABCD.BG=E.BG AND ABCD.BU=E.BU) ABCDE 
 
 LEFT OUTER JOIN
 -- Resources Who Joined in specified year 	
(SELECT o1.name BG,o2.name BU,COUNT(*) F1
 FROM org_hierarchy o1, org_hierarchy o2, resource r
 WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND YEAR(date_of_joining)=`end_year`
 AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') 
 GROUP BY o1.name,o2.name) F
 ON ABCDE.BG=F.BG AND ABCDE.BU=F.BU) ABCDEF 
  
 LEFT OUTER JOIN
 -- Resources Who Left in specified year 
 (SELECT o1.name BG,o2.name BU ,COUNT(*) G1
 FROM org_hierarchy o1, org_hierarchy o2, resource r
 WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND YEAR(release_date)=`end_year`
 AND release_date <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
 GROUP BY o1.name,o2.name) G
 ON ABCDEF.BG=G.BG AND ABCDEF.BU=G.BU ;
 
 END$$

DELIMITER ;



/* Procedure structure for procedure `RMG_Skills_Releasing_In_3_Months` */


DELIMITER $$


DROP PROCEDURE IF EXISTS `RMG_Skills_Releasing_In_3_Months`$$

CREATE DEFINER=`root`@`%` PROCEDURE `RMG_Skills_Releasing_In_3_Months`(IN start_date VARCHAR(2), 
      IN end_date VARCHAR(2),IN start_month VARCHAR(2),
	  IN end_month VARCHAR(2),IN start_year VARCHAR(4), IN end_year VARCHAR(4))
BEGIN
SET @STARTDATE = CONCAT(`start_year`,'-',`start_month`,'-',`start_date`);
SET @ENDDATE = CONCAT(`end_year`,'-',`end_month`,'-',`end_date`);
SELECT ABC.BG,ABC.BU,ABC.skill,COALESCE(CNT1,0) 'Resources in Projects ending in 0-1 Month',
COALESCE(CNT2,0) 'Resources in Projects ending in 1-2 Months',
COALESCE(D.CNT,0) 'Resources in Projects ending in 2-3 Months' FROM
(SELECT AB.BG,AB.BU,AB.skill,CNT1,C.CNT CNT2 FROM
(SELECT A.BG,A.BU,A.skill,B.CNT 'CNT1' FROM 
(SELECT BG, BU, skill FROM
 (SELECT 1 X1,o1.name BG,o2.name BU
  FROM org_hierarchy o1,org_hierarchy o2
  WHERE o1.id=o2.parent_id) X ,
 (SELECT 1 Y1,skill FROM competency) Y WHERE X1=Y1) A 
  LEFT OUTER JOIN
 (SELECT o1.name BG, o2.name BU, c.skill, COUNT(*) CNT
  FROM org_hierarchy o1, org_hierarchy o2, resource r, project p, competency c, resource_allocation ra 
  WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND r.employee_id=ra.employee_id  
  AND r.current_project_id=p.id AND r.competency=c.id AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d') AND 
  (ISNULL(release_date) OR release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')) AND 
  (ISNULL(alloc_end_date) OR alloc_end_date>= STR_TO_DATE(@ENDDATE, '%Y-%m-%d'))
  AND (project_end_date <= DATE_ADD(@ENDDATE, INTERVAL 1 MONTH) OR alloc_end_date <= DATE_ADD(@ENDDATE, INTERVAL 1 MONTH)
  OR release_date >= DATE_ADD(@ENDDATE, INTERVAL 1 MONTH))
  GROUP BY BG, BU, skill) B ON (A.BG=B.BG AND A.BU=B.BU AND A.skill=B.skill)) AB 
  LEFT OUTER JOIN
 (SELECT o1.name BG, o2.name BU, c.skill, COUNT(*) CNT
  FROM org_hierarchy o1, org_hierarchy o2, resource r, project p, competency c, resource_allocation ra 
  WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND r.employee_id=ra.employee_id  
  AND r.current_project_id=p.id AND r.competency=c.id AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
  AND (ISNULL(release_date) OR release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')) 
  AND (ISNULL(alloc_end_date) OR alloc_end_date>= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')) AND 
 (project_end_date BETWEEN DATE_ADD(@ENDDATE, INTERVAL 1 MONTH) AND  DATE_ADD(@ENDDATE, INTERVAL 2 MONTH) OR 
  alloc_end_date BETWEEN  DATE_ADD(@ENDDATE, INTERVAL 1 MONTH) AND DATE_ADD(@ENDDATE, INTERVAL 2 MONTH) OR
  release_date BETWEEN DATE_ADD(@ENDDATE, INTERVAL 1 MONTH) AND  DATE_ADD(@ENDDATE, INTERVAL 2 MONTH))
  GROUP BY BG, BU, skill) C ON (AB.BG=C.BG AND AB.BU=C.BU AND AB.skill=C.skill)) ABC 
 LEFT OUTER JOIN
 (SELECT o1.name BG, o2.name BU, c.skill, COUNT(*) CNT
  FROM org_hierarchy o1, org_hierarchy o2, resource r, project p, competency c, resource_allocation ra 
  WHERE o1.id=o2.parent_id AND r.current_bu_id=o2.id AND r.employee_id=ra.employee_id  
  AND r.current_project_id=p.id AND r.competency=c.id AND date_of_joining <= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')
  AND (ISNULL(release_date) OR release_date >= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')) 
  AND (ISNULL(alloc_end_date) OR alloc_end_date>= STR_TO_DATE(@ENDDATE, '%Y-%m-%d')) AND 
  (project_end_date BETWEEN DATE_ADD(@ENDDATE, INTERVAL 2 MONTH) AND  DATE_ADD(@ENDDATE, INTERVAL 3 MONTH) OR 
  alloc_end_date BETWEEN  DATE_ADD(@ENDDATE, INTERVAL 2 MONTH) AND DATE_ADD(@ENDDATE, INTERVAL 3 MONTH) OR
  release_date BETWEEN DATE_ADD(@ENDDATE, INTERVAL 2 MONTH) AND  DATE_ADD(@ENDDATE, INTERVAL 3 MONTH))
  GROUP BY BG, BU, skill)  D ON (ABC.BG=D.BG AND ABC.BU=D.BU AND ABC.skill=D.skill)
 WHERE !ISNULL(CNT1) OR !ISNULL(CNT2) OR !ISNULL(D.CNT)
 ORDER BY BG, BU, skill;
END$$

DELIMITER ;