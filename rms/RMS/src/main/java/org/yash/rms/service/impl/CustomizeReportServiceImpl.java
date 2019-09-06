package org.yash.rms.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.CustomizeReportDao;
import org.yash.rms.dao.impl.ReportDaoImpl;
import org.yash.rms.report.dto.BUResourcesPLReport;
import org.yash.rms.service.CustomizeReportService;

@Service("CustomizeReportService")
public class CustomizeReportServiceImpl implements CustomizeReportService {

	@Autowired
	@Qualifier("customizeReportDao")
	CustomizeReportDao customizeReportDao;

	private static final Logger logger = LoggerFactory
			.getLogger(ReportDaoImpl.class);
	
	public static HashMap<String, List<Integer>> countOfResources = new HashMap<String, List<Integer>>();

	public List<BUResourcesPLReport> getResourceOnBenchStartOfMonthReport(Integer year) {

		logger.info("--------CustomizeReportServiceImpl getResourceOnBenchStartOfMonthReport method start-------");
		List<BUResourcesPLReport> report= new ArrayList<BUResourcesPLReport>();
		List<BUResourcesPLReport> reportAll= new ArrayList<BUResourcesPLReport>();
		
		List<Integer> onBenchStartEachMonthCount= new ArrayList<Integer>();
		
		DateTime currentDate = new DateTime();
		
		Integer currentYear = currentDate.getYear();
		Integer month = currentDate.getMonthOfYear();
		
		
		for (int i = 1; i <= (currentYear.equals(year)? month: 12); i++) {
                  String currentDateString = "0"+i+"/01/"+year;
                  DateFormat inputDF  = new SimpleDateFormat("MM/dd/yy");
                  Date date = null;
                  try {
                         date = inputDF.parse(currentDateString);
                  } catch (ParseException e) {
                         // TODO Auto-generated catch block
                         e.printStackTrace();
                  }
                  DateTime dateTime = new DateTime(date);

                  Date startDate = dateTime.dayOfMonth().withMinimumValue().toDate();
                  report = customizeReportDao.getResourceOnBenchReport(startDate, startDate);
                  if(!report.isEmpty()){
  					onBenchStartEachMonthCount.add(report.size());
  				}
  				else{
  					onBenchStartEachMonthCount.add(0);
  				}
                  reportAll.addAll(report);	
            }
     

		
			countOfResources.put("OnBenchStart", onBenchStartEachMonthCount);
			logger.info("--------CustomizeReportServiceImpl getResourceOnBenchStartOfMonthReport method end-------");

		return reportAll;

	}
	
	public List<BUResourcesPLReport> getResourceOnBenchEndOfMonthReport(Integer year) {

		logger.info("--------CustomizeReportServiceImpl getResourceOnBenchEndOfMonthReport method start-------");
		List<BUResourcesPLReport> report= new ArrayList<BUResourcesPLReport>();
		List<BUResourcesPLReport> reportAll= new ArrayList<BUResourcesPLReport>();
		List<Integer> onBenchEndEachMonthCount = new ArrayList<Integer>();
		
		DateTime currentDate = new DateTime();
		
		Integer currentYear = currentDate.getYear();
		Integer month = currentDate.getMonthOfYear();
		
		for (int i = 1; i <= (currentYear.equals(year)? month: 12); i++) {
          
                  String currentDateString = "0"+i+"/01/"+year;
                  System.out.println("currentDateString = "+currentDateString);
                  DateFormat inputDF  = new SimpleDateFormat("MM/dd/yy");
                  Date date = null;
                  try {
                         date = inputDF.parse(currentDateString);
                  } catch (ParseException e) {
                         // TODO Auto-generated catch block
                         e.printStackTrace();
                  }
                  DateTime dateTime = new DateTime(date);

                  Date endDate = dateTime.dayOfMonth().withMaximumValue().toDate();
                  report = customizeReportDao.getResourceOnBenchReport(endDate, endDate);
                  if(!report.isEmpty()){
                	  onBenchEndEachMonthCount.add(report.size());
  				}
  				else{
  					onBenchEndEachMonthCount.add(0);
  				}
                  reportAll.addAll(report);	
           
     }
		countOfResources.put("OnBenchEnd", onBenchEndEachMonthCount);
			logger.info("--------CustomizeReportServiceImpl getResourceOnBenchEndOfMonthReport method end-------");

		return reportAll;

	}
	
	public List<BUResourcesPLReport> getResourceOnBenchMaxOfMonthReport(Integer year) {

		logger.info("--------CustomizeReportServiceImpl getResourceOnBenchMaxOfMonthReport method start-------");
		List<BUResourcesPLReport> report= new ArrayList<BUResourcesPLReport>();
		List<BUResourcesPLReport> reportAll= new ArrayList<BUResourcesPLReport>();
		List<Integer> onBenchMaxEachMonthCount= new ArrayList<Integer>();
		DateTime currentDate = new DateTime();
		
		Integer currentYear = currentDate.getYear();
		Integer month = currentDate.getMonthOfYear();

		for (int i = 1; i <= (currentYear.equals(year)? month: 12); i++) {
				String currentDateString = "0"+i+"/01/"+year;
				System.out.println("currentDateString = "+currentDateString);
				DateFormat inputDF  = new SimpleDateFormat("MM/dd/yy");
				Date date = null;
				try {
					date = inputDF.parse(currentDateString);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DateTime dateTime = new DateTime(date);

				Date startDate = dateTime.dayOfMonth().withMinimumValue().toDate();
				Date endDate = dateTime.dayOfMonth().withMaximumValue().toDate();
				report = customizeReportDao.getResourceOnBenchReport( startDate, endDate);
				
				if(!report.isEmpty()){
					onBenchMaxEachMonthCount.add(report.size());
				}
				else{
					onBenchMaxEachMonthCount.add(0);
				}
							
				reportAll.addAll(report);		
			}
		
		countOfResources.put("OnBenchMax", onBenchMaxEachMonthCount);
			logger.info("--------CustomizeReportServiceImpl getResourceOnBenchMaxOfMonthReport method end-------");

		return reportAll;

	}

	
	public List<BUResourcesPLReport> getNewJoineeDirectAllocatedForReport(Integer year) {

		logger.info("--------CustomizeReportServiceImpl getBillableResourceForReport method start-------");
		List<BUResourcesPLReport> report= new ArrayList<BUResourcesPLReport>();
		List<BUResourcesPLReport> reportAll= new ArrayList<BUResourcesPLReport>();
		List<Integer> newJoineeDirectAllocationEachMonthCount = new ArrayList<Integer>();
		
		String allocationType= "AND ra.`allocation_type_id` IN (2,3,5,7)";
		DateTime currentDate = new DateTime();
		
		Integer currentYear = currentDate.getYear();
		Integer month = currentDate.getMonthOfYear();
		
		for (int i = 1; i <= (currentYear.equals(year)? month: 12); i++) {	
				String currentDateString = "0"+i+"/01/"+year;
				System.out.println("currentDateString = "+currentDateString);
				DateFormat inputDF  = new SimpleDateFormat("MM/dd/yy");
				Date date = null;
				try {
					date = inputDF.parse(currentDateString);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DateTime dateTime = new DateTime(date);

				Date startDate = dateTime.dayOfMonth().withMinimumValue().toDate();
				Date endDate = dateTime.dayOfMonth().withMaximumValue().toDate();
				report = customizeReportDao.getNewJoinee(startDate, endDate,allocationType);
				
				if(!report.isEmpty()){
					newJoineeDirectAllocationEachMonthCount.add(report.size());
				}
				else{
					newJoineeDirectAllocationEachMonthCount.add(0);
				}				
				reportAll.addAll(report);		
			}
					
		countOfResources.put("newJoineeDirectAllocation", newJoineeDirectAllocationEachMonthCount);
			logger.info("--------CustomizeReportServiceImpl getBillableResourceForReport method end-------");

		return reportAll;

	}

	public List<BUResourcesPLReport> getNewJoineeOnBenchForReport(Integer year) {
		logger.info("--------CustomizeReportServiceImpl getBillableResourceForReport method start-------");
		List<BUResourcesPLReport> report= new ArrayList<BUResourcesPLReport>();
		List<BUResourcesPLReport> reportAll= new ArrayList<BUResourcesPLReport>();
		List<Integer> newJoineeOnBenchEachMonthCount= new ArrayList<Integer>();
		
		String allocationType= "AND ra.`allocation_type_id` IN (1,6,4)";
		DateTime currentDate = new DateTime();
		
		
		Integer currentYear = currentDate.getYear();
		Integer month = currentDate.getMonthOfYear();

		for (int i = 1; i <= (currentYear.equals(year)? month: 12); i++) {
				String currentDateString = "0"+i+"/01/"+year;
				System.out.println("currentDateString = "+currentDateString);
				DateFormat inputDF  = new SimpleDateFormat("MM/dd/yy");
				Date date = null;
				try {
					date = inputDF.parse(currentDateString);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DateTime dateTime = new DateTime(date);

				Date startDate = dateTime.dayOfMonth().withMinimumValue().toDate();
				Date endDate = dateTime.dayOfMonth().withMaximumValue().toDate();
				report = customizeReportDao.getNewJoinee(startDate, endDate,allocationType);
				
				if(!report.isEmpty()){
					newJoineeOnBenchEachMonthCount.add(report.size());
				}
				else{
					newJoineeOnBenchEachMonthCount.add(0);
				}
				
				reportAll.addAll(report);		
			}
		
		countOfResources.put("newJoineeOnBench", newJoineeOnBenchEachMonthCount);
		logger.info("--------CustomizeReportServiceImpl getBillableResourceForReport method end-------");

		return reportAll;
	}
	
	public List<BUResourcesPLReport> getResourceAllocatedFromBenchToProject(Integer year) {
		logger.info("--------CustomizeReportServiceImpl getBillableResourceForReport method start-------");
		List<BUResourcesPLReport> report= new ArrayList<BUResourcesPLReport>();
		List<BUResourcesPLReport> reportAll= new ArrayList<BUResourcesPLReport>();
		List<Integer> benchToProjectEachMonthCount= new ArrayList<Integer>();
		DateTime currentDate = new DateTime();
		
		Integer currentYear = currentDate.getYear();	
		Integer month = currentDate.getMonthOfYear();

		for (int i = 1; i <= (currentYear.equals(year)? month: 12); i++) {
				String currentDateString = "0"+i+"/01/"+year;
				System.out.println("currentDateString = "+currentDateString);
				DateFormat inputDF  = new SimpleDateFormat("MM/dd/yy");
				Date date = null;
				try {
					date = inputDF.parse(currentDateString);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DateTime dateTime = new DateTime(date);

				Date startDate = dateTime.dayOfMonth().withMinimumValue().toDate();
				Date endDate = dateTime.dayOfMonth().withMaximumValue().toDate();
				report = customizeReportDao.getResourcesBenchToProject( startDate, endDate);
				
				if(!report.isEmpty()){
					benchToProjectEachMonthCount.add(report.size());
				}
				else{
					benchToProjectEachMonthCount.add(0);
				}
				
				reportAll.addAll(report);		
			}
		countOfResources.put("benchToProject", benchToProjectEachMonthCount);
			logger.info("--------CustomizeReportServiceImpl getBillableResourceForReport method end-------");

		return reportAll;
	}
	
	public List<BUResourcesPLReport> getReleasedResource(Integer year) {
		logger.info("--------CustomizeReportServiceImpl getBillableResourceForReport method start-------");
		List<BUResourcesPLReport> report= new ArrayList<BUResourcesPLReport>();
		List<BUResourcesPLReport> reportAll= new ArrayList<BUResourcesPLReport>();
		List<Integer> releasedResourceMonthCount= new ArrayList<Integer>();
		DateTime currentDate = new DateTime();
		
		Integer currentYear = currentDate.getYear();
		Integer month = currentDate.getMonthOfYear();
		
		
		for (int i = 1; i <= (currentYear.equals(year)? month: 12); i++) {
				String currentDateString = "0"+i+"/01/"+year;
				System.out.println("currentDateString = "+currentDateString);
				DateFormat inputDF  = new SimpleDateFormat("MM/dd/yy");
				Date date = null;
				try {
					date = inputDF.parse(currentDateString);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DateTime dateTime = new DateTime(date);

				Date startDate = dateTime.dayOfMonth().withMinimumValue().toDate();
				Date endDate = dateTime.dayOfMonth().withMaximumValue().toDate();
				report = customizeReportDao.getReleasedResourcesOfBG4BU5(startDate, endDate);
				
				if(!report.isEmpty()){
					releasedResourceMonthCount.add(report.size());
				}
				else{
					releasedResourceMonthCount.add(0);
				}
				
				reportAll.addAll(report);		
			}
		countOfResources.put("releasedResource", releasedResourceMonthCount);
			logger.info("--------CustomizeReportServiceImpl getBillableResourceForReport method end-------");

		return reportAll;
	}

	private Object getResourceTransferToBG4BU5(Integer year) {

		logger.info("--------CustomizeReportServiceImpl getResourceTransferToBG4BU5 method start-------");
		List<BUResourcesPLReport> reportAll= new ArrayList<BUResourcesPLReport>();

		List<Integer> resourceCount= new ArrayList<Integer>();

		DateTime currentDate = new DateTime();

		Integer currentYear = currentDate.getYear();
		Integer month = currentDate.getMonthOfYear();
		
		for (int i = 1; i <= (currentYear.equals(year)? month: 12); i++) {
			String currentDateString = "0"+i+"/01/"+year;
				System.out.println("currentDateString = "+currentDateString);
				DateFormat inputDF  = new SimpleDateFormat("MM/dd/yy");
				Date date = null;
				try {
					date = inputDF.parse(currentDateString);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				DateTime dateTime = new DateTime(date);

				Date startDate = dateTime.dayOfMonth().withMinimumValue().toDate();
				Date endDate = dateTime.dayOfMonth().withMaximumValue().toDate();

				List<BUResourcesPLReport> report = customizeReportDao.getResourceTransferToBG4BU5(startDate, endDate);
				if(!report.isEmpty()){
					resourceCount.add(report.size());
				}
				else{
					resourceCount.add(0);
				}

				reportAll.addAll(report);	
			}
		countOfResources.put("resourceTransferToBG4BU5", resourceCount);
		logger.info("--------CustomizeReportServiceImpl getResourceTransferToBG4BU5 method end-------");
		return reportAll;

	}

	private Object getResourceTransferFromBG4BU5(Integer year) {

		logger.info("--------CustomizeReportServiceImpl getResourceTransferFromBG4BU5 method start-------");
		List<BUResourcesPLReport> reportAll= new ArrayList<BUResourcesPLReport>();

		List<Integer> resourceCount= new ArrayList<Integer>();

		DateTime currentDate = new DateTime();

		Integer currentYear = currentDate.getYear();

		Integer month = currentDate.getMonthOfYear();
		
		for (int i = 1; i <= (currentYear.equals(year)? month: 12); i++) {
				String currentDateString = "0"+i+"/01/"+year;
				System.out.println("currentDateString = "+currentDateString);
				DateFormat inputDF  = new SimpleDateFormat("MM/dd/yy");
				Date date = null;
				try {
					date = inputDF.parse(currentDateString);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				DateTime dateTime = new DateTime(date);

				Date startDate = dateTime.dayOfMonth().withMinimumValue().toDate();
				Date endDate = dateTime.dayOfMonth().withMaximumValue().toDate();
				
				List<BUResourcesPLReport> report = customizeReportDao.getResourceTransferFromBG4BU5(startDate, endDate);
				if(!report.isEmpty()){
					resourceCount.add(report.size());
				}
				else{
					resourceCount.add(0);
				}

				reportAll.addAll(report);	
			}
		countOfResources.put("resourceTransferFromBG4BU5", resourceCount);
		logger.info("--------CustomizeReportServiceImpl getResourceTransferFromBG4BU5 method end-------");
		return reportAll;

	}
	public Map<String, Object> getResourceForCustomizeReport(Integer year) {
		Map<String, Object> mapCustomizeReport = new HashMap<String, Object>();
		
		mapCustomizeReport.put("ResourceOnBenchStartOfMonthReport", getResourceOnBenchStartOfMonthReport(year));
		mapCustomizeReport.put("ResourceOnBenchEndOfMonthReport", getResourceOnBenchEndOfMonthReport(year));
		mapCustomizeReport.put("ResourceOnBenchMaxOfMonthReport", getResourceOnBenchMaxOfMonthReport(year));	
		mapCustomizeReport.put("ResourceAllocatedFromBenchToProject", getResourceAllocatedFromBenchToProject(year));
		mapCustomizeReport.put("BackToBackFullfillmentForReport", getNewJoineeDirectAllocatedForReport(year));
		mapCustomizeReport.put("NewJoineeOnBenchForReport", getNewJoineeOnBenchForReport(year));
		mapCustomizeReport.put("ReleasedResource", getReleasedResource(year));
		mapCustomizeReport.put("CountOfEachMonth", countOfResources );
		mapCustomizeReport.put("ResourceTransferFromBG4BU5", getResourceTransferFromBG4BU5(year));
		mapCustomizeReport.put("ResourceTransferToBG4BU5", getResourceTransferToBG4BU5(year));
		
		return mapCustomizeReport;
	}
	
}
