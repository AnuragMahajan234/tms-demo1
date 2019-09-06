-----------------------------------------------------------------
-----			DB SCRIPTS FOR MOBILE APPLICATION			-----
-----------------------------------------------------------------

-- new table for logging all the rest service requests from mobile --
-----				SERVICE_REQUEST_AUDIT					-----

CREATE TABLE `service_request_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `employee_id` varchar(256) NOT NULL,
  `service_api` varchar(256) NOT NULL,
  `request_type` varchar(256) NOT NULL,
  `success` char(1) DEFAULT 'N',
  `time_for_request` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;