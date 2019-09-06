/**
 * 
 */
package org.yash.rms.util;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.yash.rms.dao.CATicketDao;
import org.yash.rms.domain.CATicket;
import org.yash.rms.domain.CATicketNamedQuery;
import org.yash.rms.domain.Crop;
import org.yash.rms.report.dto.DashboardPerformance;
import org.yash.rms.report.dto.dashboardFilter;
import org.yash.rms.service.CATicketService;
import org.yash.rms.service.ProjectService;
import org.yash.rms.service.ResourceService;

/**
 * @author arpan.badjatiya
 * 
 */
@Component
public class RMSUtil {
	private static final Logger logger = LoggerFactory.getLogger(RMSUtil.class);

	@Autowired
	@Qualifier("CATicketService")
	CATicketService caTicketService;

	@Autowired
	@Qualifier("ResourceService")
	ResourceService resourceService;

	@Autowired
	@Qualifier("ProjectService")
	ProjectService projectService;

	@Autowired
	CATicketDao caTicketDao;

	public static String NullTOEmptyString(String input) {
		return null != input ? input.trim() : "";
	}

	public static boolean validateMobileNumber(String mobileNumber) {
		logger.info("------UploadResourceController validateMobileNumber method start------");
		Pattern p = Pattern.compile("^(?:[0-9]{5}|[0-9]{10}|)$");
		Matcher m = p.matcher(mobileNumber);
		if (m.matches()) {
			logger.info("------UploadResourceController validateMobileNumber method end------");
			return true;
		}
		logger.info("------UploadResourceController validateMobileNumber method end------");
		return false;
	}

	public static boolean validateIntegerDigits(Integer integerValue) {
		logger.info("------UploadResourceController validateIntegerDigits method start------");
		String str = integerValue.toString();
		if (str.matches("^\\d+$")) {
			logger.info("------UploadResourceController validateIntegerDigits method end------");
			return true;
		}
		logger.info("------UploadResourceController validateIntegerDigits method end------");
		return false;
	}

	public static boolean validateIntegerDigits(String stringValue) {
		logger.info("------UploadResourceController validateIntegerDigits method start------");
		String str = stringValue.toString();
		if (str.matches("^\\d+$")) {
			logger.info("------UploadResourceController validateIntegerDigits method end------");
			return true;
		}
		logger.info("------UploadResourceController validateIntegerDigits method end------");
		return false;
	}

	public static boolean validateDate(Date dateToValidate) {
		logger.info("------UploadResourceController validateDate method start------");
		String currentDate = dateToValidate.toString();
		SimpleDateFormat sdf = new SimpleDateFormat("mm-dd-yyyy");
		sdf.setLenient(false);
		try {
			// if not valid, it will throw ParseException
			Date date = sdf.parse(currentDate);
			// System.out.println(date);
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error("RuntimeException occured in showJson method of UploadResourceController controller:"
					+ e);
			return false;
		}
		logger.info("------UploadResourceController validateDate method end------");
		return true;
	}

	public static boolean validateString(String stringValue) {
		logger.info("------UploadResourceController validateString method start------");
		String str = stringValue.toString();
		if (str.matches("[a-zA-Z]+")) {
			return true;
		}
		logger.info("------UploadResourceController validateStringDigits method end------");
		return false;
	}

	public static boolean validateAlphaNumeric(String stringValue) {
		logger.info("------UploadResourceController validateAlphaNumeric method start------");
		String str = stringValue.toString();
		if (str.matches("[a-zA-Z0-9]+")) {
			return true;
		}
		logger.info("------UploadResourceController validateAlphaNumeric method end------");
		return false;
	}

	public void dashboard(Model uiModel, dashboardFilter dashboardFilter)
			throws ParseException {

		System.out.println("Region size: "
				+ caTicketService.getAllRegion().size());
		System.out.println("Employee Id: "
				+ UserUtil.getCurrentResource().getEmployeeId() + "");

		/**** My Ticket Pending Status ****/
		List<Object[]> list = caTicketService
				.findTicketStatusCountByEmployeeId(UserUtil
						.getCurrentResource().getEmployeeId() + "",
						dashboardFilter);

		/**** Team Ticket Pending Status ****/
		List<Object[]> teamList = caTicketService
				.findTeamTicketStatusCountByEmployeeId(UserUtil
						.getCurrentResource().getEmployeeId() + "",
						dashboardFilter);

		/*
		 * List<Object[]> myperformanceList = caTicketService
		 * .findMyPerformance(UserUtil.getCurrentResource() .getEmployeeId() +
		 * "");
		 * 
		 * List<Object[]> myTeamPerformanceList = caTicketService
		 * .findMyTeamPerformance(UserUtil.getCurrentResource() .getEmployeeId()
		 * + "");
		 */

		/******** My Performance *************/
		List<CATicket> myTicketsList = caTicketService.findMyTickets(UserUtil
				.getCurrentResource().getEmployeeId(), dashboardFilter);

		/******** My Team Performance *************/
		List<CATicket> myTeamTicketsList = caTicketService.findMyTeamTickets(
				UserUtil.getCurrentResource().getEmployeeId(), dashboardFilter);

		System.out.println("myTeamTicketsList: " + myTeamTicketsList.size());
		List<CATicketNamedQuery> caTicketNamedQueries = new ArrayList<CATicketNamedQuery>();
		for (Object[] object : list) {
			CATicketNamedQuery caTicketNamedQuery = new CATicketNamedQuery();
			caTicketNamedQuery.setEmpId((Integer) object[0]);
			System.out.println("Assignee Name testing: "
					+ resourceService.find((Integer) object[0])
							.getEmployeeName());
			caTicketNamedQuery.setAssigneeName(resourceService.find(
					(Integer) object[0]).getEmployeeName());
			caTicketNamedQuery.setModuleId((Integer) object[1]);
			caTicketNamedQuery.setModule(projectService.findProject(
					(Integer) object[1]).getModuleName());
			// caTicketNamedQuery.setAssigneeName((String) object[2]);
			caTicketNamedQuery.setAcknowledgedCount(((BigInteger) object[3])
					.intValue());
			caTicketNamedQuery
					.setProblemManagementCount(((BigInteger) object[4])
							.intValue());
			caTicketNamedQuery.setReqCompleteCount(((BigInteger) object[5])
					.intValue());
			caTicketNamedQuery
					.setAnalysisCompleteCount(((BigInteger) object[6])
							.intValue());
			caTicketNamedQuery
					.setSolutionDevelopedCount(((BigInteger) object[7])
							.intValue());
			// caTicketNamedQuery.setSolutionReviewCount(((BigInteger)
			// object[8])
			// .intValue());
			caTicketNamedQuery
					.setSolutionAcceptedCount(((BigInteger) object[9])
							.intValue());
			caTicketNamedQuery
					.setCustomerApprovalCount(((BigInteger) object[10])
							.intValue());
			caTicketNamedQuery.setGroupCount(((BigInteger) object[11])
					.intValue());
			caTicketNamedQuery.setForMyReviewCount(((BigInteger) object[12])
					.intValue());
			caTicketNamedQuery.setSentForReviewCount(((BigInteger) object[13])
					.intValue());
			caTicketNamedQueries.add(caTicketNamedQuery);
			// projectService.findProject(Integer.parseInt(object[0]));
			/* findProject(); */
		}

		System.out.println("caTicketNamedQuery Size Testing: "
				+ caTicketNamedQueries.size());
		List<Object[]> forMyReviewList = caTicketService
				.findForMyReviewTicketStatusCountByEmployeeId(UserUtil
						.getCurrentResource().getEmployeeId() + "",
						dashboardFilter);
		for (Object[] object : forMyReviewList) {
			if (((BigInteger) object[2]).intValue() > 0) {
				CATicketNamedQuery caTicketNamedQuery = new CATicketNamedQuery();
				caTicketNamedQuery.setEmpId((Integer) object[0]);
				if ((Integer) object[0] != null) {
					caTicketNamedQuery.setAssigneeName(resourceService.find(
							(Integer) object[0]).getEmployeeName());
				}
				caTicketNamedQuery.setModuleId((Integer) object[1]);
				if ((Integer) object[1] != null) {
					caTicketNamedQuery.setModule(projectService.findProject(
							(Integer) object[1]).getModuleName());
				}
				caTicketNamedQuery.setForMyReviewCount(((BigInteger) object[2])
						.intValue());
				caTicketNamedQuery.setAcknowledgedCount(0);
				caTicketNamedQuery.setProblemManagementCount(0);
				caTicketNamedQuery.setReqCompleteCount(0);
				caTicketNamedQuery.setAnalysisCompleteCount(0);
				caTicketNamedQuery.setSolutionDevelopedCount(0);
				caTicketNamedQuery.setSolutionAcceptedCount(0);
				caTicketNamedQuery.setCustomerApprovalCount(0);
				caTicketNamedQuery.setGroupCount(0);
				caTicketNamedQuery.setSentForReviewCount(0);
				caTicketNamedQueries.add(caTicketNamedQuery);
			}
			// projectService.findProject(Integer.parseInt(object[0]));
			/* findProject(); */
		}

		List<CATicketNamedQuery> teamTicketNamedQueries = new ArrayList<CATicketNamedQuery>();
		for (Object[] object : teamList) {
			CATicketNamedQuery teamTicketNamedQuery = new CATicketNamedQuery();
			teamTicketNamedQuery.setEmpId((Integer) object[0]);
			if ((Integer) object[0] != null) {
				teamTicketNamedQuery.setAssigneeName(resourceService.find(
						(Integer) object[0]).getEmployeeName());
			}
			teamTicketNamedQuery.setModuleId((Integer) object[1]);
			if ((Integer) object[1] != null) {
				teamTicketNamedQuery.setModule(projectService.findProject(
						(Integer) object[1]).getModuleName());
			}
			/* teamTicketNamedQuery.setAssigneeName((String) object[2]); */
			teamTicketNamedQuery.setAcknowledgedCount(((BigInteger) object[3])
					.intValue());
			teamTicketNamedQuery
					.setProblemManagementCount(((BigInteger) object[4])
							.intValue());
			teamTicketNamedQuery.setReqCompleteCount(((BigInteger) object[5])
					.intValue());
			teamTicketNamedQuery
					.setAnalysisCompleteCount(((BigInteger) object[6])
							.intValue());
			teamTicketNamedQuery
					.setSolutionDevelopedCount(((BigInteger) object[7])
							.intValue());
			// teamTicketNamedQuery
			// .setSolutionReviewCount(((BigInteger) object[8]).intValue());
			teamTicketNamedQuery
					.setSolutionAcceptedCount(((BigInteger) object[9])
							.intValue());
			teamTicketNamedQuery
					.setCustomerApprovalCount(((BigInteger) object[10])
							.intValue());
			teamTicketNamedQuery.setGroupCount(((BigInteger) object[11])
					.intValue());
			teamTicketNamedQuery.setForMyReviewCount(((BigInteger) object[12])
					.intValue());
			teamTicketNamedQueries.add(teamTicketNamedQuery);
		}

		// My Performance Status
		int myTicketCount = 0, totalOpen = 0, totalResolvedYes = 0, totalResolvedNo = 0, totalClosed = 0, slaMissed = 0, slaMissedForClosed = 0, agingResolved = 0, agingOpen = 0, projectResolved = 0, projectOpen = 0, problemManagementProposed = 0, problemManagementJustified = 0, problemManagementJustifiedCrop = 0;
		float slaPercentage = 0, urgenResolutionTime = 0, highResolutionTime = 0, mediumResolutionTime = 0;
		int urgentResolvedTicketCount = 0, highResolvedTicketCount = 0, mediumResolvedTicketCount = 0;
		int urgentResolvedTicketDaysDiff = 0, highResolvedTicketDaysDiff = 0, mediumResolvedTicketDaysDiff = 0;

		for (CATicket caTicket : myTicketsList) {
			Date closedDate = caTicket.getClosePendingCustomerApprovalDate();
			Date creationDate = caTicket.getCreationDate();
			String priority = caTicket.getPriority();

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy/MM/dd hh:mm:ss");
			Calendar calobj = Calendar.getInstance();
			String currentDate = simpleDateFormat.format(calobj.getTime());
			Date now = simpleDateFormat.parse(currentDate);

			// Assigned Ticket
			myTicketCount = myTicketCount + 1;

			// Open Ticket
			if (closedDate == null) {
				totalOpen = totalOpen + 1;
			}

			// Total Resolved(Excluding project priority tickets):
			// Yes: If SLA missed = No with closure date
			// No: If SLA missed = Yes with closure date
			if (priority != null) {
				if (!priority.equalsIgnoreCase("5 - Project")) {
					if (caTicket.getGroupName() != null) {
						if (caTicket.getGroupName().equalsIgnoreCase("Tier 2")) {
							if (RMSUtil.isSLAMissedOut(now, creationDate,
									priority, closedDate).equals("No")
									&& closedDate != null) {
								totalResolvedYes = totalResolvedYes + 1;
							} else if (RMSUtil.isSLAMissedOut(now,
									creationDate, priority, closedDate).equals(
									"Yes")
									&& closedDate != null) {
								totalResolvedNo = totalResolvedNo + 1;
							}
						}
					}
				}
			}

			// Aging(Excluding project priority ticket):
			// Resolved: Closure date and aging = yes
			// Open: No closure date and aging=yes
			if (priority != null) {
				if (!priority.equalsIgnoreCase("5 - Project")) {
					if (caTicket.getGroupName() != null) {
						if (caTicket.getGroupName().equalsIgnoreCase("Tier 2")) {
							if (RMSUtil.isAgingCrossed(now, creationDate,
									priority, closedDate).equals("Yes")
									&& closedDate != null) {
								agingResolved = agingResolved + 1;
							} else if (RMSUtil.isAgingCrossed(now,
									creationDate, priority, closedDate).equals(
									"Yes")
									&& closedDate == null) {
								agingOpen = agingOpen + 1;
							}
						}
					}
				}
			}

			// Project(Include only project priority tickets):
			// Resolved: closure date + priority = project
			// Open: No closure date + priority = project
			if (priority != null) {
				if (priority.equalsIgnoreCase("5 - Project")
						&& closedDate != null) {
					projectResolved = projectResolved + 1;
				} else if (priority.equalsIgnoreCase("5 - Project")
						&& closedDate == null) {
					projectOpen = projectOpen + 1;
				}
			}

			// Problem Management :
			// Proposed: Problem management flag = yes
			if (caTicket.getProblemManagementFlag() != null) {
				if (caTicket.getProblemManagementFlag().equalsIgnoreCase("Yes")) {
					problemManagementProposed = problemManagementProposed + 1;
				}
			}
			// Justified: justified problem management = yes
			if (caTicket.getJustifiedProblemManagement() != null) {
				if (caTicket.getJustifiedProblemManagement().equalsIgnoreCase(
						"Yes")) {
					problemManagementJustified = problemManagementJustified + 1;
				}
			}
			// Justified Crop: justified should be yes for latest record in crop
			// section..
			Crop crop = caTicketService.getLatestCropById(caTicket.getId());
			if (crop != null) {
				if (crop.getJustified() != null) {
					if (crop.getJustified().equalsIgnoreCase("Yes")) {
						problemManagementJustifiedCrop = problemManagementJustifiedCrop + 1;
					}
				}
			}
			// Days and Hours difference between closed date and creation date
			int diffInHours = 0, diffInDays = 0;
			if (closedDate != null && creationDate != null) {
				diffInDays = (int) ((closedDate.getTime() - creationDate
						.getTime()) / (1000 * 60 * 60 * 24));
				System.out.println("diffInDays: " + diffInDays);
				long secs = (closedDate.getTime() - creationDate.getTime()) / 1000;
				diffInHours = (int) secs / 3600;
				System.out.println("diffInHours: " + diffInHours);
			}

			// Resolution Time:
			// Urgent: Closure date with urgent priority: hours average (diff =
			// closure-creation)
			if (closedDate != null && priority.equalsIgnoreCase("1 - Urgent")) {
				if (caTicket.getGroupName() != null) {
					if (caTicket.getGroupName().equalsIgnoreCase("Tier 2")) {
						if(diffInHours<=4){
							urgentResolvedTicketDaysDiff = urgentResolvedTicketDaysDiff
								+ diffInDays;
						urgentResolvedTicketCount = urgentResolvedTicketCount + 1;
						}
					}
				}
			}
			// High: Closure date with high priority: days average (diff =
			// closure-creation)
			if (closedDate != null && priority.equalsIgnoreCase("2 - High")) {
				if (caTicket.getGroupName() != null) {
					if (caTicket.getGroupName().equalsIgnoreCase("Tier 2")) {
						highResolvedTicketDaysDiff = highResolvedTicketDaysDiff
								+ diffInDays;
						highResolvedTicketCount = highResolvedTicketCount + 1;
					}
				}
			}
			// Medium/Low: Closure date with Medium/Low priority: days average
			// (diff =
			// closure-creation)
			if (closedDate != null
					&& (priority.equalsIgnoreCase("3 - Medium") || priority
							.equalsIgnoreCase("4 - Low"))) {
				if (caTicket.getGroupName() != null) {
					if (caTicket.getGroupName().equalsIgnoreCase("Tier 2")) {
						mediumResolvedTicketDaysDiff = mediumResolvedTicketDaysDiff
								+ diffInDays;
						mediumResolvedTicketCount = mediumResolvedTicketCount + 1;
					}
				}
			}
		}

		// SLA%: = (resolved_yes)/total resolved(yes+no)
		if ((totalResolvedYes + totalResolvedNo) > 0) {
			slaPercentage = (totalResolvedYes * 100)
					/ (totalResolvedYes + totalResolvedNo);
		}
		if (urgentResolvedTicketCount > 0) {
			urgenResolutionTime = urgentResolvedTicketDaysDiff
					/ urgentResolvedTicketCount;
		}
		if (highResolvedTicketCount > 0) {
			highResolutionTime = highResolvedTicketDaysDiff
					/ highResolvedTicketCount;
		}
		if (mediumResolvedTicketCount > 0) {
			mediumResolutionTime = mediumResolvedTicketDaysDiff
					/ mediumResolvedTicketCount;
		}

		DashboardPerformance dashboardPerformance = new DashboardPerformance();
		dashboardPerformance.setTotalAssigned(myTicketCount);
		dashboardPerformance.setTotalOpen(totalOpen);
		dashboardPerformance.setTotalResolvedYes(totalResolvedYes);
		dashboardPerformance.setTotalResolvedNo(totalResolvedNo);
		dashboardPerformance.setSlaMissed(slaPercentage);
		;
		dashboardPerformance.setAgingResolved(agingResolved);
		dashboardPerformance.setAgingOpen(agingOpen);
		dashboardPerformance.setProjectResolved(projectResolved);
		dashboardPerformance.setProjectOpen(projectOpen);
		dashboardPerformance
				.setProblemManagementProposed(problemManagementProposed);
		dashboardPerformance
				.setProblemManagementJustified(problemManagementJustified);
		dashboardPerformance
				.setProblemManagementJustifiedCrop(problemManagementJustifiedCrop);
//		dashboardPerformance.setUrgenResolutionTime(urgenResolutionTime);
		dashboardPerformance.setUrgenResolutionTime(String.format("%.3f", urgenResolutionTime));
		dashboardPerformance.setHighResolutionTime(highResolutionTime);
		dashboardPerformance.setMediumResolutionTime(mediumResolutionTime);
		List<DashboardPerformance> myDashboardPerformanceList = new ArrayList<DashboardPerformance>();
		myDashboardPerformanceList.add(dashboardPerformance);

		// My Team Performance Status
		myTicketCount = 0;
		totalOpen = 0;
		totalResolvedYes = 0;
		totalResolvedNo = 0;
		slaMissed = 0;
		slaPercentage = 0;
		slaMissedForClosed = 0;
		totalClosed = 0;
		agingResolved = 0;
		agingOpen = 0;
		projectResolved = 0;
		projectOpen = 0;
		problemManagementProposed = 0;
		problemManagementJustified = 0;
		problemManagementJustifiedCrop = 0;
		urgenResolutionTime = 0;
		highResolutionTime = 0;
		mediumResolutionTime = 0;
		urgentResolvedTicketCount = 0;
		highResolvedTicketCount = 0;
		mediumResolvedTicketCount = 0;
		urgentResolvedTicketDaysDiff = 0;
		highResolvedTicketDaysDiff = 0;
		mediumResolvedTicketDaysDiff = 0;
		for (CATicket caTicket : myTeamTicketsList) {
			Date closedDate = caTicket.getClosePendingCustomerApprovalDate();
			Date creationDate = caTicket.getCreationDate();
			String priority = caTicket.getPriority();

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy/MM/dd hh:mm:ss");
			Calendar calobj = Calendar.getInstance();
			String currentDate = simpleDateFormat.format(calobj.getTime());
			Date now = simpleDateFormat.parse(currentDate);

			// Assigned Ticket
			myTicketCount = myTicketCount + 1;

			// Open Ticket
			if (closedDate == null) {
				totalOpen = totalOpen + 1;
			}

			// Total Resolved(Excluding project priority tickets):
			// Yes: If SLA missed = No with closure date
			// No: If SLA missed = Yes with closure date
			if (priority != null) {
				if (!priority.equalsIgnoreCase("5 - Project")) {
					if (caTicket.getGroupName() != null) {
						if (caTicket.getGroupName().equalsIgnoreCase("Tier 2")) {
							if (RMSUtil.isSLAMissedOut(now, creationDate,
									priority, closedDate).equals("No")
									&& closedDate != null) {
								totalResolvedYes = totalResolvedYes + 1;
							} else if (RMSUtil.isSLAMissedOut(now,
									creationDate, priority, closedDate).equals(
									"Yes")
									&& closedDate != null) {
								totalResolvedNo = totalResolvedNo + 1;
							}
						}
					}
				}
			}

			// Aging(Excluding project priority ticket):
			// Resolved: Closure date and aging = yes
			// Open: No closure date and aging=yes
			if (priority != null) {
				if (!priority.equalsIgnoreCase("5 - Project")) {
					if (caTicket.getGroupName() != null) {
						if (caTicket.getGroupName().equalsIgnoreCase("Tier 2")) {
							if (RMSUtil.isAgingCrossed(now, creationDate,
									priority, closedDate).equals("Yes")
									&& closedDate != null) {
								agingResolved = agingResolved + 1;
							} else if (RMSUtil.isAgingCrossed(now,
									creationDate, priority, closedDate).equals(
									"Yes")
									&& closedDate == null) {
								agingOpen = agingOpen + 1;
							}
						}
					}
				}
			}

			// Project(Include only project priority tickets):
			// Resolved: closure date + priority = project
			// Open: No closure date + priority = project
			if (priority != null) {
				if (priority.equalsIgnoreCase("5 - Project")
						&& closedDate != null) {
					projectResolved = projectResolved + 1;
				} else if (priority.equalsIgnoreCase("5 - Project")
						&& closedDate == null) {
					projectOpen = projectOpen + 1;
				}
			}

			// Problem Management :
			// Proposed: Problem management flag = yes
			if (caTicket.getProblemManagementFlag() != null) {
				if (caTicket.getProblemManagementFlag().equalsIgnoreCase("Yes")) {
					problemManagementProposed = problemManagementProposed + 1;
				}
			}
			// Justified: justified problem management = yes
			if (caTicket.getJustifiedProblemManagement() != null) {
				if (caTicket.getJustifiedProblemManagement().equalsIgnoreCase(
						"Yes")) {
					problemManagementJustified = problemManagementJustified + 1;
				}
			}
			// Justified Crop: justified should be yes for latest record in crop
			// section..
			Crop crop = caTicketService.getLatestCropById(caTicket.getId());
			if (crop != null) {
				if (crop.getJustified() != null) {
					if (crop.getJustified().equalsIgnoreCase("Yes")) {
						problemManagementJustifiedCrop = problemManagementJustifiedCrop + 1;
					}
				}
			}
			// Days and Hours difference between closed date and creation date
			int diffInHours = 0, diffInDays = 0;
			if (closedDate != null && creationDate != null) {
				diffInDays = (int) ((closedDate.getTime() - creationDate
						.getTime()) / (1000 * 60 * 60 * 24));
				System.out.println("diffInDays: " + diffInDays);
				long secs = (now.getTime() - creationDate.getTime()) / 1000;
				diffInHours = (int) secs / 3600;
				System.out.println("diffInHours: " + diffInHours);
			}

			// Resolution Time:
			// Urgent: Closure date with urgent priority: hours average (diff =
			// closure-creation)
			if (closedDate != null && priority.equalsIgnoreCase("1 - Urgent")) {
				if (caTicket.getGroupName() != null) {
					if (caTicket.getGroupName().equalsIgnoreCase("Tier 2")) {
						if(diffInHours<=4){
							urgentResolvedTicketDaysDiff = urgentResolvedTicketDaysDiff
								+ diffInDays;
						urgentResolvedTicketCount = urgentResolvedTicketCount + 1;
						}
					}
				}
			}
			// High: Closure date with high priority: days average (diff =
			// closure-creation)
			if (closedDate != null && priority.equalsIgnoreCase("2 - High")) {
				if (caTicket.getGroupName() != null) {
					if (caTicket.getGroupName().equalsIgnoreCase("Tier 2")) {
						highResolvedTicketDaysDiff = highResolvedTicketDaysDiff
								+ diffInDays;
						highResolvedTicketCount = highResolvedTicketCount + 1;
					}
				}
			}
			// Medium/Low: Closure date with Medium/Low priority: days average
			// (diff =
			// closure-creation)
			if (closedDate != null
					&& (priority.equalsIgnoreCase("3 - Medium") || priority
							.equalsIgnoreCase("4 - Low"))) {
				if (caTicket.getGroupName() != null) {
					if (caTicket.getGroupName().equalsIgnoreCase("Tier 2")) {
						mediumResolvedTicketDaysDiff = mediumResolvedTicketDaysDiff
								+ diffInDays;
						mediumResolvedTicketCount = mediumResolvedTicketCount + 1;
					}
				}
			}
		}

		// SLA%: = (resolved_yes)/total resolved(yes+no)
		if ((totalResolvedYes + totalResolvedNo) > 0) {
			slaPercentage = (totalResolvedYes * 100)
					/ (totalResolvedYes + totalResolvedNo);
		}
		if (urgentResolvedTicketCount > 0) {
			urgenResolutionTime = urgentResolvedTicketDaysDiff
					/ urgentResolvedTicketCount;
		}
		if (highResolvedTicketCount > 0) {
			highResolutionTime = highResolvedTicketDaysDiff
					/ highResolvedTicketCount;
		}
		if (mediumResolvedTicketCount > 0) {
			mediumResolutionTime = mediumResolvedTicketDaysDiff
					/ mediumResolvedTicketCount;
		}
		DashboardPerformance teamDashboardPerformance = new DashboardPerformance();
		teamDashboardPerformance.setTotalAssigned(myTicketCount);
		teamDashboardPerformance.setTotalOpen(totalOpen);
		teamDashboardPerformance.setTotalResolvedYes(totalResolvedYes);
		teamDashboardPerformance.setTotalResolvedNo(totalResolvedNo);
		teamDashboardPerformance.setSlaMissed(slaPercentage);
		teamDashboardPerformance.setAgingResolved(agingResolved);
		teamDashboardPerformance.setAgingOpen(agingOpen);
		teamDashboardPerformance.setProjectResolved(projectResolved);
		teamDashboardPerformance.setProjectOpen(projectOpen);
		teamDashboardPerformance
				.setProblemManagementProposed(problemManagementProposed);
		teamDashboardPerformance
				.setProblemManagementJustified(problemManagementJustified);
		teamDashboardPerformance
				.setProblemManagementJustifiedCrop(problemManagementJustifiedCrop);
//		teamDashboardPerformance.setUrgenResolutionTime(urgenResolutionTime);
		teamDashboardPerformance.setUrgenResolutionTime(String.format("%.3f", urgenResolutionTime));
		teamDashboardPerformance.setHighResolutionTime(highResolutionTime);
		teamDashboardPerformance.setMediumResolutionTime(mediumResolutionTime);
		List<DashboardPerformance> myTeamDashboardPerformanceList = new ArrayList<DashboardPerformance>();
		myTeamDashboardPerformanceList.add(teamDashboardPerformance);

		uiModel.addAttribute("myDashboardFlagCountList", caTicketNamedQueries);
		uiModel.addAttribute("myTeamDashboardFlagCountList",
				teamTicketNamedQueries);
		uiModel.addAttribute(Constants.RESOURCES,
				resourceService.findActiveResources());
		uiModel.addAttribute(Constants.PROCESS, caTicketService.findProcess());
		uiModel.addAttribute(Constants.PROJECTS,
				caTicketService.findModuleNameProjects());
		uiModel.addAttribute(Constants.LANDSCAPE,
				caTicketService.getAllLandscape());
		/*
		 * uiModel.addAttribute(Constants.REGIONS,
		 * caTicketService.getAllRegion());
		 */
		uiModel.addAttribute(Constants.REGIONNAMES,
				caTicketService.getDistinctRegionNames());
		uiModel.addAttribute("myDashboardPerformanceList",
				myDashboardPerformanceList);
		uiModel.addAttribute("myTeamDashboardPerformanceList",
				myTeamDashboardPerformanceList);

		/*
		 * if(dashboardFilter != null){
		 * System.out.println("shikhi shikhi :: "+dashboardFilter
		 * .getAssignee()); caTicketDao.getFilterDashboard(dashboardFilter); }
		 */

	}

	public static String isAgingCrossed(Date currentDate, Date creationDate,
			String priority, Date closedDate) {
		System.out.println("currentDate: " + currentDate + " creationDate: "
				+ creationDate);
		int diffInDays = 0;

		/*
		 * Updated on 18/Nov/2015 Added if condition for closure date Aging
		 * Aging should be calculated based on closure and creation date if
		 * closure date is not null
		 */

		if (closedDate != null) {
			if (creationDate != null) {
				diffInDays = (int) ((closedDate.getTime() - creationDate
						.getTime()) / (1000 * 60 * 60 * 24));
			}
		} else {
			if (creationDate != null) {
				diffInDays = (int) ((currentDate.getTime() - creationDate
						.getTime()) / (1000 * 60 * 60 * 24));
			}
		}
		System.out.println("diffInDays: " + diffInDays);
		if (priority.equals("5 - Project")) {
			return "N.A.";
		} else if (diffInDays > 28) {
			System.out.println("Date difference: " + diffInDays);
			System.out.println("Creation date is greater then 28 days");
			return "Yes";
		} else {
			System.out.println("Date difference: " + diffInDays);
			System.out.println("Creation date is less then 28 days");
			return "No";
		}
	}

	public static String isSLAMissedOut(Date currentDate, Date creationDate,
			String priority, Date closedDate) {
		int diffInDays = 0;
		long secs = 0;

		/*
		 * Updated on 18/Nov/2015 Added if condition for closure date SLA Missed
		 * SLA Missed should be calculated based on closure and creation date if
		 * closure date is not null
		 */

		if (closedDate != null) {
			if (creationDate != null) {
				diffInDays = (int) ((closedDate.getTime() - creationDate
						.getTime()) / (1000 * 60 * 60 * 24));
				secs = (closedDate.getTime() - creationDate.getTime()) / 1000;
			}
		} else {
			if (creationDate != null) {
				diffInDays = (int) ((currentDate.getTime() - creationDate
						.getTime()) / (1000 * 60 * 60 * 24));
				secs = (currentDate.getTime() - creationDate.getTime()) / 1000;
			}
		}
		System.out.println("diffInDays: " + diffInDays);

		int diffInHours = (int) secs / 3600;
		System.out.println("diffInHours: " + diffInHours);
		if (priority.equals("5 - Project")) {
			return "N.A.";
		} else if (diffInDays > 5 && priority.equals("2 - High")) {
			return "Yes";
		} else if (diffInDays > 28
				&& (priority.equals("3 - Medium") || priority.equals("4 - Low"))) {
			return "Yes";
		} else if (diffInHours > 4 && priority.equals("1 - Urgent")) {
			return "Yes";
		} else {
			return "No";
		}
	}

	public static boolean isInteger(String s, int radix) {
		if (s.isEmpty())
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1)
					return false;
				else
					continue;
			}
			if (Character.digit(s.charAt(i), radix) < 0)
				return false;
		}
		return true;
	}
}
