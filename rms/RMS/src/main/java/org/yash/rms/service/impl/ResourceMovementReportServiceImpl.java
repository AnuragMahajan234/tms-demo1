package org.yash.rms.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.ResourceMovementReportDao;
import org.yash.rms.report.dto.ResourceMovementReport;
import org.yash.rms.report.dto.ResourceMovementReportGraphs;
import org.yash.rms.service.ResourceMovementReportService;
import org.yash.rms.util.Constants;

@Service("resourceMovementReportService")
public class ResourceMovementReportServiceImpl implements ResourceMovementReportService {

	private static final Logger logger = LoggerFactory.getLogger(ResourceMovementReportServiceImpl.class);
	@Autowired
	@Qualifier("resourceMovementReportDao")
	ResourceMovementReportDao resourceMovementReportDao;

	ResourceMovementReportGraphs resourceMovementReportGraphs = new ResourceMovementReportGraphs();
	Map<String, Integer> extranlMap = null;
	Map<String, Integer> internalMap = null;

	int count = 0;
	int countNBfromBF = 0;
	int countNBfromBPart = 0;
	int countBFTfromNB = 0;
	int countBFTfromBpart = 0;
	int countBPartFromNB = 0;
	int countBPartFromBF = 0;

	int countTotalNBPrevious = 0;
	int countTotalBFPrevious = 0;
	int countTotalBPartPrevious = 0;

	int countTotalInvestment = 0;
	int countNBtoInvestment = 0;
	int countBFtoInvestment = 0;
	int countBPartToInvestment = 0;

	int countBFTfromInvestment = 0;
	int countNBTfromInvestment = 0;
	int countBPartFromInvestment = 0;

	int countNewHiredResource = 0;
	int countBFfromnewHired = 0;
	int countBPartfromnewHired = 0;
	int countNBfromnewHired = 0;
	int countInvestmentfromnewHired = 0;

	int countTotalProject = 0;
	int countInternalfromProject = 0;
	int countExternalfromProject = 0;
	TimeUnit timesUnit = TimeUnit.DAYS;

	public List<ResourceMovementReport> getResourceMovementReport(List<Integer> orgIdList, List<Integer> locIdList, List<Integer> projIdList, Date startDate, Date endDate,
			List<Integer> allocationIDList) {

		return resourceMovementReportDao.getResourceMovementReport(orgIdList, locIdList, projIdList, startDate, endDate, allocationIDList);
	}

	public ResourceMovementReportGraphs getResourceMovementAnalysisReport(List<ResourceMovementReport> resourceMovementReports, List<Integer> orgIdList, List<Integer> locIdList,
			List<Integer> projIdList, Date startDate, Date endDate, List<Integer> allocationIDList) {

		ResourceMovementReportGraphs resourceMovementReportGraphs = new ResourceMovementReportGraphs();
		List<ResourceMovementReport> resourceMovementAnalysisList = new ArrayList<ResourceMovementReport>();

		resourceMovementAnalysisList = resourceMovementReportDao.getAnalysisResourceMovementReport(orgIdList, locIdList, projIdList, startDate, endDate, allocationIDList);
		resourceMovementReportGraphs.setResourceMovementAnalysedReportList(resourceMovementAnalysisList);

		return setResourceMovementReportGraphs(resourceMovementReportGraphs);
	}

	public ResourceMovementReportGraphs setResourceMovementReportGraphs(ResourceMovementReportGraphs resourceMovementReportGraphs) {

		/* From Analysis list */
		List<ResourceMovementReport> resourceMovementReportList = resourceMovementReportGraphs.getResourceMovementAnalysedReportList();
		Map<String, Integer> projectNameMap = new TreeMap<String, Integer>();

		for (ResourceMovementReport resourceMovementAnalysis : resourceMovementReportList) {

			if (resourceMovementAnalysis.getPreviousAllocationType() != null) {

				// Set to Billable allocation and count
				setBillableAllocationWithCount(resourceMovementAnalysis);

				// Set to Partial allocation and count
				setPartialAlloactionWithCount(resourceMovementAnalysis);

				// current list count and set allocation
				setAllocationChangeWithCurrentCount(resourceMovementAnalysis);

				/* Set to Non-Billable allocation and count of conversion. */
				if (resourceMovementAnalysis.getPreviousAllocationType().contains(Constants.NON_BILLABLE) || resourceMovementAnalysis.getAllocationType().contains(Constants.NON_BILLABLE)
						|| resourceMovementAnalysis.getAllocationType().contains(Constants.PIP) || resourceMovementAnalysis.getPreviousAllocationType().contains(Constants.PIP)) {
					setAlloactionChangeWithTotalCountForNBInvest(resourceMovementAnalysis);
				}

				// Set to INVESTMENT allocation and count of conversion
				setInvestmentAllTypeWithConversionCount(resourceMovementAnalysis);

			} else {
				System.out.println(" resourceMovementAnalysis getYashEmpID....." + resourceMovementAnalysis.getYashEmpID());
			}

			// For New Hiring conversion and count
			getNewHiringCount(resourceMovementAnalysis, timesUnit);
			getNewHiringConversion(resourceMovementAnalysis);

			// For Resource Type Column value
			Integer countProject = projectNameMap.get(resourceMovementAnalysis.getProjectName());
			projectNameMap.put(resourceMovementAnalysis.getProjectName(), (countProject == null) ? 1 : countProject + 1);

			// setResourceTypeColumn(resourceMovementAnalysis, timesUnit);

		}

		countprojectMap(projectNameMap, resourceMovementReportList);

		// Start conversion for Non-Billable
		resourceMovementReportGraphs.setTotalPreviousNBAllocationType(countTotalNBPrevious);
		resourceMovementReportGraphs.setTotalBFTfromNBAllocationType(countBFTfromNB);
		resourceMovementReportGraphs.setTotalBPartFromNBAllocationType(countBPartFromNB);
		resourceMovementReportGraphs.setTotalInvestmentfromNBAllocationType(countNBtoInvestment);
		// End conversion for Non-Billable

		// Start conversion for BillableFTE
		resourceMovementReportGraphs.setTotalpreviousFBillAllocationType(countTotalBFPrevious);
		resourceMovementReportGraphs.setTotalNBfromBFAllocationType(countNBfromBF);
		resourceMovementReportGraphs.setTotalBPartFromBFAllocationType(countBPartFromBF);
		resourceMovementReportGraphs.setTotalInvestmentfromBFAllocationType(countBFtoInvestment);
		// End conversion for BillableFTE

		// Start conversion for Billable Partial
		resourceMovementReportGraphs.setTotalBFTfromBpartAllocationType(countBFTfromBpart);
		resourceMovementReportGraphs.setTotalNBfromBPartAllocationType(countNBfromBPart);
		resourceMovementReportGraphs.setTotalpreviousPartBillAllocationType(countTotalBPartPrevious);
		resourceMovementReportGraphs.setTotalInvestmentfromBPartAllocationType(countBPartToInvestment);
		// End conversion for Billable Partial

		// Start conversion for Investment
		resourceMovementReportGraphs.setTotalBFTfromInvestmentAllocationType(countBFTfromInvestment);
		resourceMovementReportGraphs.setTotalNBfromInvestmentAllocationType(countNBTfromInvestment);
		resourceMovementReportGraphs.setTotalPartBillFromInvestmentAllocationType(countBPartFromInvestment);
		resourceMovementReportGraphs.setTotalInvestmentfromPreviousAllocationType(countTotalInvestment);
		// End conversion for Investment

		// Start conversion for New Hiring
		resourceMovementReportGraphs.setTotalNewHiredResourceAllocationType(countNewHiredResource);
		resourceMovementReportGraphs.setTotalBFTfromNewHiredAllocationType(countBFfromnewHired);
		resourceMovementReportGraphs.setTotalNBfromNewHiredAllocationType(countNBfromnewHired);
		resourceMovementReportGraphs.setTotalPartBillFromNewHiredAllocationType(countBPartfromnewHired);
		resourceMovementReportGraphs.setTotalInvestmentfromNewHiredAllocationType(countInvestmentfromnewHired);
		// End conversion for New Hiring

		// Start conversion for Resource Type
		resourceMovementReportGraphs.setTotalProject(projectNameMap);
		resourceMovementReportGraphs.setTotalInternalfromProject(internalMap);
		resourceMovementReportGraphs.setTotalExternalfromProject(extranlMap);
		// End conversion for Resource Type
		setAllCountVariableToZero();

		return resourceMovementReportGraphs;

	}

	private void setPartialAlloactionWithCount(ResourceMovementReport resourceMovementAnalysis) {
		if (resourceMovementAnalysis.getPreviousAllocationType().equalsIgnoreCase(Constants.BILLABLE_PARTIA_SHAREDPOOL_FIXBIDPROJECTS)) {
			countTotalBPartPrevious++;

		}
	}

	private void setBillableAllocationWithCount(ResourceMovementReport resourceMovementAnalysis) {
		if (resourceMovementAnalysis.getPreviousAllocationType().equalsIgnoreCase(Constants.BILLABLE_FULL_TIME)) {
			countTotalBFPrevious++;
		}
	}

	private void setAllocationChangeWithCurrentCount(ResourceMovementReport resourceMovementAnalysis) {

		if (((resourceMovementAnalysis.getAllocationType().contains(Constants.NON_BILLABLE) || resourceMovementAnalysis.getAllocationType().contains(Constants.PIP)) && !resourceMovementAnalysis
				.getAllocationType().contains(Constants.INVESTMENT)) && resourceMovementAnalysis.getPreviousAllocationType().equals(Constants.BILLABLE_FULL_TIME)) {
			countNBfromBF++;
		} else if (((resourceMovementAnalysis.getAllocationType().contains(Constants.NON_BILLABLE) || resourceMovementAnalysis.getAllocationType().contains(Constants.PIP)) && !resourceMovementAnalysis
				.getAllocationType().contains(Constants.INVESTMENT)) && resourceMovementAnalysis.getPreviousAllocationType().equals(Constants.BILLABLE_PARTIA_SHAREDPOOL_FIXBIDPROJECTS)) {
			countNBfromBPart++;
		} else if (resourceMovementAnalysis.getAllocationType().equals(Constants.BILLABLE_FULL_TIME)
				&& ((resourceMovementAnalysis.getPreviousAllocationType().contains(Constants.NON_BILLABLE) || resourceMovementAnalysis.getPreviousAllocationType().contains(Constants.PIP)) && !resourceMovementAnalysis
						.getPreviousAllocationType().contains(Constants.INVESTMENT))) {
			countBFTfromNB++;

		} else if (resourceMovementAnalysis.getAllocationType().equals(Constants.BILLABLE_FULL_TIME)
				&& resourceMovementAnalysis.getPreviousAllocationType().equals(Constants.BILLABLE_PARTIA_SHAREDPOOL_FIXBIDPROJECTS)) {
			countBFTfromBpart++;
		} else if (resourceMovementAnalysis.getAllocationType().equals(Constants.BILLABLE_PARTIA_SHAREDPOOL_FIXBIDPROJECTS)
				&& ((resourceMovementAnalysis.getPreviousAllocationType().contains(Constants.NON_BILLABLE) || resourceMovementAnalysis.getPreviousAllocationType().contains(Constants.PIP)) && !resourceMovementAnalysis
						.getPreviousAllocationType().contains(Constants.INVESTMENT))) {
			countBPartFromNB++;
		} else if (resourceMovementAnalysis.getAllocationType().equals(Constants.BILLABLE_PARTIA_SHAREDPOOL_FIXBIDPROJECTS)
				&& resourceMovementAnalysis.getPreviousAllocationType().equals(Constants.BILLABLE_FULL_TIME)) {
			countBPartFromBF++;
		}

	}

	private void setInvestmentAllTypeWithConversionCount(ResourceMovementReport resourceMovementAnalysis) {

		if (resourceMovementAnalysis.getAllocationType().equals(Constants.BILLABLE_FULL_TIME) && resourceMovementAnalysis.getPreviousAllocationType().contains(Constants.INVESTMENT)) {
			countBFTfromInvestment++;

		} else if (resourceMovementAnalysis.getAllocationType().contains(Constants.INVESTMENT) && resourceMovementAnalysis.getPreviousAllocationType().equals(Constants.BILLABLE_FULL_TIME)) {
			countBFtoInvestment++;

		} else if (resourceMovementAnalysis.getAllocationType().equals(Constants.BILLABLE_PARTIA_SHAREDPOOL_FIXBIDPROJECTS)
				&& resourceMovementAnalysis.getPreviousAllocationType().contains(Constants.INVESTMENT)) {
			countBPartFromInvestment++;

		} else if (resourceMovementAnalysis.getAllocationType().contains(Constants.INVESTMENT)
				&& resourceMovementAnalysis.getPreviousAllocationType().equals(Constants.BILLABLE_PARTIA_SHAREDPOOL_FIXBIDPROJECTS)) {
			countBPartToInvestment++;
		}

	}

	private void setAlloactionChangeWithTotalCountForNBInvest(ResourceMovementReport resourceMovementAnalysis) {

		if ((resourceMovementAnalysis.getPreviousAllocationType().contains(Constants.NON_BILLABLE) || resourceMovementAnalysis.getPreviousAllocationType().contains(Constants.PIP))
				&& (!resourceMovementAnalysis.getPreviousAllocationType().contains(Constants.INVESTMENT))) {

			countTotalNBPrevious++;

			if ((resourceMovementAnalysis.getAllocationType().contains(Constants.NON_BILLABLE) && (!resourceMovementAnalysis.getAllocationType().contains(Constants.INVESTMENT)))
					&& ((resourceMovementAnalysis.getPreviousAllocationType().contains(Constants.NON_BILLABLE) && (!resourceMovementAnalysis.getPreviousAllocationType().contains(Constants.INVESTMENT))))) {

			} else if (resourceMovementAnalysis.getAllocationType().contains(Constants.INVESTMENT)
					&& ((resourceMovementAnalysis.getPreviousAllocationType().contains(Constants.NON_BILLABLE) || resourceMovementAnalysis.getPreviousAllocationType().contains(Constants.PIP)) && !resourceMovementAnalysis
							.getPreviousAllocationType().contains(Constants.INVESTMENT))) {
				countNBtoInvestment++;
			}
		} else {
			if (resourceMovementAnalysis.getAllocationType().contains(Constants.INVESTMENT) || resourceMovementAnalysis.getPreviousAllocationType().contains(Constants.INVESTMENT)) {
				if (resourceMovementAnalysis.getPreviousAllocationType().contains(Constants.INVESTMENT)) {
					countTotalInvestment++;
					if (resourceMovementAnalysis.getPreviousAllocationType().contains(Constants.INVESTMENT) && resourceMovementAnalysis.getAllocationType().contains(Constants.INVESTMENT)) {

					} else if ((resourceMovementAnalysis.getAllocationType().contains(Constants.NON_BILLABLE) || resourceMovementAnalysis.getAllocationType().contains(Constants.PIP))
							&& resourceMovementAnalysis.getPreviousAllocationType().contains(Constants.INVESTMENT)) {
						countNBTfromInvestment++;
					}
				}

			}

		}
	}

	private void getNewHiringConversion(ResourceMovementReport resourceMovementAnalysis) {

		if (resourceMovementAnalysis.getAllocationChange() != null && resourceMovementAnalysis.getAllocationChange().equalsIgnoreCase(Constants.NEW_HIRED_RESOURCE)) {

			if (resourceMovementAnalysis.getAllocationType().equals(Constants.BILLABLE_FULL_TIME) && resourceMovementAnalysis.getAllocationChange().equalsIgnoreCase(Constants.NEW_HIRED_RESOURCE)) {
				countBFfromnewHired++;
			} else if (((resourceMovementAnalysis.getAllocationType().contains(Constants.NON_BILLABLE) || resourceMovementAnalysis.getAllocationType().contains(Constants.PIP)) && !resourceMovementAnalysis
					.getAllocationType().contains(Constants.INVESTMENT)) && resourceMovementAnalysis.getAllocationChange().equalsIgnoreCase(Constants.NEW_HIRED_RESOURCE)) {
				countNBfromnewHired++;
			} else if (resourceMovementAnalysis.getAllocationType().equals(Constants.BILLABLE_PARTIA_SHAREDPOOL_FIXBIDPROJECTS)
					&& resourceMovementAnalysis.getAllocationChange().equalsIgnoreCase(Constants.NEW_HIRED_RESOURCE)) {
				countBPartfromnewHired++;

			}
			if (resourceMovementAnalysis.getAllocationType().contains(Constants.INVESTMENT) && resourceMovementAnalysis.getAllocationChange().equalsIgnoreCase(Constants.NEW_HIRED_RESOURCE)) {
				countInvestmentfromnewHired++;

			}

		}
	}

	private void getNewHiringCount(ResourceMovementReport resourceMovementAnalysis, TimeUnit timesUnit) {

		try {
			SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = myFormat.parse(resourceMovementAnalysis.getStartDate().toString());
			Date date2 = myFormat.parse(resourceMovementAnalysis.getDateOfJoining().toString());

			long dateDiff = date1.getTime() - date2.getTime();
			// For New Hiring count
			long dateDifference = TimeUnit.DAYS.convert(dateDiff, TimeUnit.MILLISECONDS);

			if (resourceMovementAnalysis.getAllocationChange().equalsIgnoreCase(Constants.NEW_HIRED_RESOURCE)) {

				countNewHiredResource++;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			logger.debug("----------Inside ResourceMovementReportServiceImpl End ResourceMovementReport-----------");

		}
	}

	public void setResourceTypeColumn(ResourceMovementReport resourceMovementAnalysis, TimeUnit timesUnit) {

		Date date = resourceMovementAnalysis.getDateOfJoining();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 15); // add 15 days
		date = cal.getTime();

		if (resourceMovementAnalysis.getAllocationChange() != null && resourceMovementAnalysis.getAllocationChange().equalsIgnoreCase(Constants.NEW_HIRED_RESOURCE)) {

			if (resourceMovementAnalysis.getStartDate() != null || !resourceMovementAnalysis.getStartDate().equals("")) {
				if (!resourceMovementAnalysis.getStartDate().after(date)) {
					resourceMovementAnalysis.setResourceType("External");
				} else {
					resourceMovementAnalysis.setResourceType("Internal");
				}
			}

		} else if (resourceMovementAnalysis.getStartDate().after(date)) {
			resourceMovementAnalysis.setResourceType("Internal");
		} else {
			resourceMovementAnalysis.setResourceType("Internal");
		}

	}

	private void setAllCountVariableToZero() {

		countNBfromBF = 0;
		countNBfromBPart = 0;
		countBFTfromNB = 0;
		countBFTfromBpart = 0;
		countBPartFromNB = 0;
		countBPartFromBF = 0;

		countTotalNBPrevious = 0;
		countTotalBFPrevious = 0;
		countTotalBPartPrevious = 0;

		countTotalInvestment = 0;
		countNBtoInvestment = 0;
		countBFtoInvestment = 0;
		countBPartToInvestment = 0;

		countBFTfromInvestment = 0;
		countNBTfromInvestment = 0;
		countBPartFromInvestment = 0;

		countNewHiredResource = 0;
		countBFfromnewHired = 0;
		countBPartfromnewHired = 0;
		countNBfromnewHired = 0;
		countInvestmentfromnewHired = 0;

		countTotalProject = 0;
		countInternalfromProject = 0;
		countExternalfromProject = 0;

	}

	public void countprojectMap(Map<String, Integer> treeMap, List<ResourceMovementReport> resourceMovementReportList) {

		extranlMap = new TreeMap<String, Integer>();
		internalMap = new TreeMap<String, Integer>();

		for (Map.Entry<String, Integer> entry : treeMap.entrySet()) {

			for (ResourceMovementReport movementReport : resourceMovementReportList) {

				if (entry.getKey().equalsIgnoreCase(movementReport.getProjectName()) && movementReport.getResourceType().equalsIgnoreCase("External")) {
					countExternalfromProject++;

				} else if (entry.getKey().equalsIgnoreCase(movementReport.getProjectName()) && movementReport.getResourceType().equalsIgnoreCase("Internal")) {
					countInternalfromProject++;

				}
			}
			extranlMap.put(entry.getKey(), countExternalfromProject);
			internalMap.put(entry.getKey(), countInternalfromProject);

			countInternalfromProject = 0;
			countExternalfromProject = 0;
		}

	}

}
