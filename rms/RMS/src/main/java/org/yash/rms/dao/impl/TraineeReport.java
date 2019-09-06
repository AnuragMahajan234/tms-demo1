package org.yash.rms.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;

public class TraineeReport {
	
	public static Connection createConnection(){
		
		
		// Setup the connection with the DB
		try {
		
			Class.forName("com.mysql.jdbc.Driver");
			
			Connection connect = DriverManager
					.getConnection("jdbc:mysql://inidrrmsprod01:3306/rms_3.1_prod?"
							+ "user=root&password=root");
			
			return connect;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) throws Exception {
		// DateTime dateTime = new DateTime();
		
		Connection con = createConnection();
		
		DateTime startTime = new DateTime(2017, 01, 14, 12, 0, 0, 000);

		DateTime endTime = new DateTime(2018, 10, 13, 12, 0, 0, 000);

		ReportDaoImpl report = new ReportDaoImpl();

		while (startTime.isBefore(endTime)) {

			DateTime lastDate = startTime.dayOfMonth().withMaximumValue();

			Date startDate = lastDate.toDate();
			
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			String date = simpleDateFormat.format(startDate);
			
			System.out.println(date);

			readDataBase(con, date);
			
			startTime = startTime.plusMonths(1);

			//System.out.println(list.size());
		}

	}

	public static void readDataBase(Connection connection, String startDate) throws Exception {
		
		String Sql = "SELECT r.yash_emp_id, CONCAT( TRIM(r.first_name),' ',TRIM(r.last_name)) AS NAME,"
				+"d.designation_name, b.name AS bu_name,"
				 +"   p.project_name,"
				 +"   c.skill, l1.location AS base_location,l2.location AS current_location,cu.customer_name,r.date_of_joining,"
				 +"   g.grade, DATE((CASE WHEN ra.alloc_start_date < ('"+startDate+"')"
				 +"         THEN ('"+startDate+"')"
				 +"         ELSE ra.alloc_start_date" 
				 +"       END"
				 +"     )"
				 +"   ) AS DummyStartDate,"
				 +"   DATE("
				 +"     ("
				 +"       CASE"
				 +"         WHEN ra.alloc_end_date IS NULL" 
				 +"         THEN ('"+startDate+"')"
				 +"         WHEN ra.alloc_end_date < ('"+startDate+"')"
				 +"         THEN ra.alloc_end_date "
				 +"         ELSE ('"+startDate+"')"
				 +"       END"
				 +"     )"
				 +"   ) AS DummyEndDate,"
				 +"   a.allocationtype,"
				 +"   ra.alloc_start_date,"
				 +"   ra.alloc_end_date "
				 +" FROM"
				 +"   designations d,"
				 +"   Ownership ow,"
				 +"   bu b,"
				 +"   competency c,"
				 +"   grade g,"
				 +"   resource_allocation_aud ra,"
				 +"   allocationtype a,"
				 +"   project p,"
				 +"   customer cu,"
				 +"   resource r "
				 +"   INNER JOIN location l1" 
				 +"     ON l1.id = r.location_id" 
				 +"   INNER JOIN location l2 "
				 +"     ON l2.id = r.payroll_location" 
				 +" WHERE r.designation_id = d.id "
				 +"   AND r.competency = c.id "
				 +"   AND r.grade_id = g.id "
				 +"   AND r.ownership = ow.id" 
				 +"   AND r.employee_id = ra.employee_id" 
				 +"   AND ra.allocation_type_id = a.id "
				 +"   AND ra.project_id = p.id "
				 +"   AND p.customer_name_id = cu.id" 
				 +"   AND p.bu_id = b.id "
				 +"   AND ow.ownership_name <> 'Loan'" 
				 +"   AND r.employee_category <> 2 "
				 +"   AND r.yash_emp_id <> '%C%' "
				 +"   AND r.`yash_emp_id` IN (1004331,1004574,1004310,1004598,1003758,1003739,1004096,1003720,1004681,1003770,1004230,1004311,1003759,1004715,1003767,1003721,1004682,1003768,1003734,1004093,1004683,1004312,1004722,1003646,1003736,1004600,1004684,1003761,1003726,1004102,1004189,1004685,1003769,1004313,1004110,1004101,1003733,1004100,1004686,1003729,1004315,1004316,1003763,1003737,1004317,1003728,1004099,1003830,1004318,1004095,1003765,1004098,1004319,1003723,1004091,1004320,1004687,1004321,1004602,1004322,1004637,1004599,1004323,1003725,1003766,1004324,1004325,1003724,1003762,1003738,1003722,1004103,1004221,1004326,1004327,1004688,1004328,1004689,1004329,1004092,1004696,1004347,1004128,1004330,1003730,1004601,1003764,1004697,1004097,1003760,1004690,1004596,1004597,1003727,1004691,1003731,1003735,1004692,1004693,1005989,1007115,1005484,1005816,1005469,1005331,1006572,1005983,1005488,1006946,1005491,1005818,1005153,1006568,1006611,1007108,1005461,1006573,1005826,1006592,1005957,1006619,1005468,1005821,1005827,1005990,1004929,1005986,1005470,1005828,1005992,1006593,1005261,1006947,1006570,1005089,1005178,1005822,1006591,1006005,1006594,1005260,1006569,1005959,1005474,1006567,1004469,1005995,1005088,1005476,1005817,1006612,1005301,1005087,1005824,1005330,1008223,1008212,1008221,1008217,1008211,1008209,1008220,1007167,1008210,1008222,1008213,1008215,1008218,1008216,1008214,1006252,1007674,1006260,1007677,1008195,1007676,1007680,1007678,1007977,1007169,1007162,1007673,1007682 )"
				 +"   AND ("
				 +"     ("
				 +"       (ra.alloc_start_date <= ('"+startDate+"'))" 
				 +"       AND (ra.alloc_end_date >= ('"+startDate+"'))"
				 +"     ) "
				 +"     OR ("
				 +"       (ra.alloc_start_date <= ('"+startDate+"'))" 
				 +"       AND (ra.alloc_end_date IS NULL)"
				 +"     ) "
				 +"     OR ("
				 +"       ("
				 +"         ra.alloc_start_date >= ('"+startDate+"')"
				 +"       ) "
				 +"       AND (ra.alloc_start_date <= ('"+startDate+"'))"
				 +"     )"
				 +"   ) "
				 +" ORDER BY NAME ";

		

		try {
			// This will load the MySQL driver, each DB has its own driver
			System.out.println(Sql);
			
			PreparedStatement preparedStatement = connection.prepareStatement(Sql);
			
			ResultSet resultSet = preparedStatement.executeQuery(Sql);
			
			
			
			System.out.println(resultSet.getRow());
			
		} catch (Exception e) {
			
			e.printStackTrace();

		}
	}

}
