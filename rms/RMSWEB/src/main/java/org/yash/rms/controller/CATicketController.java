package org.yash.rms.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yash.rms.domain.CATicket;
import org.yash.rms.domain.CATicketDiscrepencies;
import org.yash.rms.domain.CATicketProcess;
import org.yash.rms.domain.CATicketSubProcess;
import org.yash.rms.domain.Crop;
import org.yash.rms.domain.DefectLog;
import org.yash.rms.domain.Landscape;
import org.yash.rms.domain.MailConfiguration;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.Region;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.Rework;
import org.yash.rms.domain.RootCause;
import org.yash.rms.domain.SolutionReview;
import org.yash.rms.domain.T3Contribution;
import org.yash.rms.domain.Unit;
import org.yash.rms.dto.Status;
import org.yash.rms.dto.UserContextDetails;
import org.yash.rms.excel.ExcelUtil;
import org.yash.rms.form.CATicketForm;
import org.yash.rms.forms.FileUploadBean;
import org.yash.rms.helper.EmailHelper;
import org.yash.rms.helper.ResourceAllocationHelper;
import org.yash.rms.helper.ResourceHelper;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.proxy.CATicketExcelMapping;
import org.yash.rms.report.dto.dashboardFilter;
import org.yash.rms.service.CATicketService;
import org.yash.rms.service.MailConfigurationService;
import org.yash.rms.service.ProjectAllocationService;
import org.yash.rms.service.ProjectService;
import org.yash.rms.service.ResourceAllocationService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.service.UploadCATicketService;
import org.yash.rms.service.impl.ProjectAllocationServiceImpl;
import org.yash.rms.util.Constants;
import org.yash.rms.util.RMSUtil;
import org.yash.rms.util.UserUtil;

/**
 * 
 * @author nitin.singhal
 * @description This controller is reading excel file and updating record in
 *              database as per the column names in excel.
 */

@Controller
@RequestMapping(value = "/caticket")
public class CATicketController {

	private static final Logger logger = LoggerFactory
			.getLogger(CATicketController.class);

	@Autowired
	@Qualifier("CATicketService")
	CATicketService caTicketService;

	@Autowired
	@Qualifier("UploadCATicketService")
	UploadCATicketService uploadCATicketService;

	@Autowired
	@Qualifier("RMSUtil")
	RMSUtil rmsUtil;

	@Autowired
	@Qualifier("ResourceService")
	ResourceService resourceService;

	@Autowired
	@Qualifier("ResourceAllocationService")
	ResourceAllocationService resourceAllocationService;

	@Autowired
	@Qualifier("ProjectService")
	ProjectService projectService;

	@Autowired
	@Qualifier("ProjectAllocationService")
	ProjectAllocationService projectAllocationService;

	@Autowired
	JsonObjectMapper jsonObjectMapper;

	@Autowired
	ResourceHelper resourceHelper;

	@Autowired
	EmailHelper emailHelper;

	@Autowired
	MailConfigurationService mailConfgService;

	static String previousPage = "";

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String showUploadForm(HttpServletRequest request, Model uiModel) {
		uiModel.addAttribute("fileUploadBean", new FileUploadBean());
		return "upload";
	}

	@RequestMapping(value = "/createTicketHome", method = RequestMethod.GET)
	public String createTicketHome(HttpServletRequest request, Model uiModel) {
		System.out.println("createTicket start");
		UserContextDetails userContextDetails = UserUtil
				.getUserContextDetails();
		uiModel.addAttribute(Constants.RESOURCES,
				resourceService.findActiveResources());
		uiModel.addAttribute(Constants.PROCESS, caTicketService.findProcess());
		uiModel.addAttribute(Constants.PROJECTS,
				caTicketService.findModuleNameProjects());
		uiModel.addAttribute(Constants.UNITS, caTicketService.getAllUnit());
		uiModel.addAttribute(Constants.LANDSCAPE,
				caTicketService.getAllLandscape());
		uiModel.addAttribute(Constants.ROOTCAUSE,
				caTicketService.getAllRootCause());
		uiModel.addAttribute("caTicketForm", new CATicketForm());
		uiModel.addAttribute("role", userContextDetails.getUserRole());
		return "caticket/createticket";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard(HttpServletRequest request, Model uiModel) {
		try {
			dashboardFilter dashboardFilter = null;
			rmsUtil.dashboard(uiModel, dashboardFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		previousPage = "dashboard";
		return "caticket/dashboard";
	}

	@RequestMapping(value = "/queue", method = RequestMethod.GET)
	public String queue(HttpServletRequest request, Model uiModel) {
		return "caticket/queue";
	}

	@RequestMapping(value = "/viewTicket", method = RequestMethod.GET)
	public String viewTicket(HttpServletRequest request, Model uiModel)
			throws ParseException {

		List<org.yash.rms.domain.ResourceAllocation> resAlloc = null;
		int currentLoggedInUserId = UserUtil.getCurrentResource()
				.getEmployeeId();
		List<CATicket> list = new ArrayList<CATicket>();
		List<Integer> allocId = new ArrayList<Integer>();
		// List<CATicket> list =
		// caTicketService.findUserWiseTicket(currentLoggedInUserId);
		List<CATicket> caTicketList = new ArrayList<CATicket>();
		Resource resource = new Resource();
		resource.setEmployeeId(currentLoggedInUserId);
		resAlloc = resourceAllocationService
				.findResourceAllocationByActiveTypeEmployeeId(resource);
		if (resAlloc.size() != 0) {
			for (org.yash.rms.domain.ResourceAllocation alloc : resAlloc) {
				allocId.add(alloc.getProjectId().getId());

			}
			list = caTicketService.getEmployeeProjectName(allocId,
					currentLoggedInUserId);
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy/MM/dd hh:mm:ss");
		Calendar calobj = Calendar.getInstance();
		int diffInDays = 0;
		if (null != list && list.size() > 0) {

			for (CATicket ca : list) {
				Date creationDate = ca.getCreationDate();
				Date closeDate = ca.getClosePendingCustomerApprovalDate();
				Date aceeptedDate = ca.getSolutionAcceptedDate();
				if (ca.getCustomerApprovalFlag() != null) {
					if (ca.getCustomerApprovalFlag().equalsIgnoreCase("Yes")) {
						if (creationDate != null && closeDate != null)
							diffInDays = (int) ((closeDate.getTime() - creationDate
									.getTime()) / (1000 * 60 * 60 * 24));
					} else if (ca.getCustomerApprovalFlag().equalsIgnoreCase(
							"No")) {
						if (creationDate != null) {
							String currentDate = simpleDateFormat.format(calobj
									.getTime());
							Date now = simpleDateFormat.parse(currentDate);
							diffInDays = (int) ((now.getTime() - creationDate
									.getTime()) / (1000 * 60 * 60 * 24));
							// ca.setDaysOpenForPhase(diffInDays);
						}
					} else {
						if (ca.getCustomerApprovalFlag()
								.equalsIgnoreCase("N.A")) {
							if (ca.getSolutionAcceptedFlag() != null) {
								if (ca.getSolutionAcceptedFlag()
										.equalsIgnoreCase("Yes")) {
									if (creationDate != null
											&& aceeptedDate != null)
										diffInDays = (int) ((aceeptedDate
												.getTime() - creationDate
												.getTime()) / (1000 * 60 * 60 * 24));
								}
							}
						} else {
							if (creationDate != null) {
								String currentDate = simpleDateFormat
										.format(calobj.getTime());
								Date now = simpleDateFormat.parse(currentDate);
								diffInDays = (int) ((now.getTime() - creationDate
										.getTime()) / (1000 * 60 * 60 * 24));
							}
						}
					}
				}

				String currentDate = simpleDateFormat.format(calobj.getTime());
				Date now = simpleDateFormat.parse(currentDate);
				int diffInPhaseDays = 0;
				if (ca.getAcknowledgedDate() == null
						|| ca.getAcknowledgedDate().equals("")) {
					if (creationDate != null) {
						diffInPhaseDays = (int) ((now.getTime() - creationDate
								.getTime()) / (1000 * 60 * 60 * 24));
					}
					// ca.setDaysOpenForPhase(diffInDays);
				} else if ((ca.getRequiredCompletedDate() == null || ca
						.getRequiredCompletedDate().equals(""))
						&& ca.getReqCompleteFlag().equalsIgnoreCase("No")) {
					if (ca.getAcknowledgedDate() != null) {
						diffInPhaseDays = (int) ((now.getTime() - ca
								.getAcknowledgedDate().getTime()) / (1000 * 60 * 60 * 24));
					}
				} else if ((ca.getAnalysisCompletedDate() == null || ca
						.getAnalysisCompletedDate().equals(""))
						&& ca.getAnalysisCompleteFlag().equalsIgnoreCase("No")) {
					if (ca.getRequiredCompletedDate() != null) {
						diffInPhaseDays = (int) ((now.getTime() - ca
								.getRequiredCompletedDate().getTime()) / (1000 * 60 * 60 * 24));
					}
				} else if ((ca.getSolutiondevelopedDate() == null || ca
						.getSolutiondevelopedDate().equals(""))
						&& ca.getSolutionDevelopedFlag().equalsIgnoreCase("No")) {
					if (ca.getAnalysisCompletedDate() != null) {
						diffInPhaseDays = (int) ((now.getTime() - ca
								.getAnalysisCompletedDate().getTime()) / (1000 * 60 * 60 * 24));
					}
				} else if (ca.getSolutionreViewDate() == null
						|| ca.getSolutionreViewDate().equals("")) {
					if (ca.getSolutiondevelopedDate() != null) {
						diffInPhaseDays = (int) ((now.getTime() - ca
								.getSolutiondevelopedDate().getTime()) / (1000 * 60 * 60 * 24));
					}
				} else if ((ca.getSolutionAcceptedDate() == null || ca
						.getSolutionAcceptedDate().equals(""))
						&& ca.getSolutionAcceptedFlag().equalsIgnoreCase("No")) {
					if (ca.getSolutionreViewDate() != null) {
						diffInPhaseDays = (int) ((now.getTime() - ca
								.getSolutionreViewDate().getTime()) / (1000 * 60 * 60 * 24));
					}
				} else if (ca.getClosePendingCustomerApprovalDate() == null
						|| ca.getClosePendingCustomerApprovalDate().equals("")) {
					if (ca.getSolutionAcceptedDate() != null) {
						diffInPhaseDays = (int) ((now.getTime() - ca
								.getSolutionAcceptedDate().getTime()) / (1000 * 60 * 60 * 24));
					}
				}
				ca.setDaysOpenForPhase(diffInPhaseDays);
				ca.setDaysOpen(diffInDays + "");
				if (creationDate != null) {
					ca.setSlaMissed(RMSUtil.isSLAMissedOut(now, creationDate,
							ca.getPriority(), closeDate));
					ca.setAging(RMSUtil.isAgingCrossed(now, creationDate,
							ca.getPriority(), closeDate));
				}
				caTicketList.add(ca);
			}

		}
		// List<CATicket> list = caTicketService.findAllTickets();
		System.out.println("CA Ticket list size: " + list.size());
		uiModel.addAttribute(Constants.CATICKETS, caTicketList);

		/*
		 * List eligibleResources = ResourceAllocationHelper
		 * .getEligibleResourcesForProject(resourceAllocationService,
		 * resourceService);
		 */
		// uiModel.addAttribute(Constants.ELIGIBLE_RESOURCES,
		// eligibleResources);
		uiModel.addAttribute(Constants.RESOURCES,
				resourceService.findActiveResources());
		uiModel.addAttribute(Constants.PROJECTS,
				caTicketService.findModuleNameProjects());
		uiModel.addAttribute(Constants.REASON_FOR_HOPPING,
				caTicketService.getAllReasonForHopping());
		// uiModel.addAttribute(Constants.OPENDAYS, Ids);
		previousPage = "myTicketQueue";
		return "caticket/viewticket";
	}

	@RequestMapping(value = "/getTicketbyTicketNumber/{ticketNumber}", method = RequestMethod.GET)
	public String getTicketbyTicketNumber(
			@PathVariable("ticketNumber") int ticketNumber,
			HttpServletRequest request, Model uiModel) {
		List<CATicket> list = caTicketService.findAllTickets();
		System.out.println("CA Ticket list size: " + list.size());
		uiModel.addAttribute(Constants.CATICKETS, list);
		return "caticket/viewticket";
	}

	@RequestMapping(value = "/getTicketById/{id}", method = RequestMethod.GET)
	public String getTicketById(@PathVariable("id") int id,
			HttpServletRequest request, Model uiModel/*
													 * ,@RequestParam("previousPage"
													 * ) String previousPage
													 */) {

		try {
			System.out.println("previousPage name: " + previousPage);
			UserContextDetails userContextDetails = UserUtil
					.getUserContextDetails();
			uiModel.addAttribute("role", userContextDetails.getUserRole());
			CATicket caTicket = caTicketService.findTicketById(id);

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy/MM/dd hh:mm:ss");
			Calendar calobj = Calendar.getInstance();
			String currentDate = simpleDateFormat.format(calobj.getTime());
			System.out.println("currentDate: " + currentDate);
			Date now = simpleDateFormat.parse(currentDate);
			Date creationDate = caTicket.getCreationDate();
			Date closedDate = caTicket.getClosePendingCustomerApprovalDate();
			if (creationDate != null) {

				/*
				 * Updated on 18/Nov/2015 Added if condition for closure date
				 * Aging Aging should be calculated based on closure and
				 * creation date if closure date is not null
				 */

				int diffInDays = 0, diffInHours = 0;

				if (closedDate != null) {
					if (creationDate != null) {
						diffInDays = (int) ((closedDate.getTime() - creationDate
								.getTime()) / (1000 * 60 * 60 * 24));
						long secs = (closedDate.getTime() - creationDate
								.getTime()) / 1000;
						diffInHours = (int) secs / 3600;
					}
				} else {
					diffInDays = (int) ((now.getTime() - creationDate.getTime()) / (1000 * 60 * 60 * 24));
					long secs = (now.getTime() - creationDate.getTime()) / 1000;
					diffInHours = (int) secs / 3600;
				}
				String priority = caTicket.getPriority();
				System.out.println("diffInDays: " + diffInDays);

				System.out.println("diffInHours: " + diffInHours);
				if (priority.equals("5 - Project")) {
					caTicket.setAging("N.A.");
				} else if (diffInDays > 28) {
					System.out.println("Date difference: " + diffInDays);
					System.out.println("Creation date is greater then 28 days");
					caTicket.setAging("Yes");
				} else {
					System.out.println("Date difference: " + diffInDays);
					System.out.println("Creation date is less then 28 days");
					caTicket.setAging("No");
				}

				if (priority.equals("5 - Project")) {
					caTicket.setSlaMissed("N.A.");
				} else if (diffInDays > 5 && priority.equals("2 - High")) {
					caTicket.setSlaMissed("Yes");
				} else if (diffInDays > 28
						&& (priority.equals("3 - Medium") || priority
								.equals("4 - Low"))) {
					caTicket.setSlaMissed("Yes");
				} else if (diffInHours > 4 && priority.equals("1 - Urgent")) {
					caTicket.setSlaMissed("Yes");
				} else {
					caTicket.setSlaMissed("No");
				}
				Date closingDate = caTicket
						.getClosePendingCustomerApprovalDate();
				if (closingDate != null) {
					diffInDays = (int) ((closingDate.getTime() - creationDate
							.getTime()) / (1000 * 60 * 60 * 24));
					caTicket.setDaysOpen(diffInDays + "");
				} else {
					caTicket.setDaysOpen(diffInDays + "");
				}
			}

			List<CATicketProcess> processByModuleIdList = new ArrayList<CATicketProcess>();
			List<CATicketSubProcess> subprocessByProcessIdList = new ArrayList<CATicketSubProcess>();
			if (caTicket.getModuleId() != null) {
				processByModuleIdList = caTicketService
						.findProcessByModuleId(caTicket.getModuleId().getId());
			}
			if (caTicket.getProcess() != null) {
				subprocessByProcessIdList = caTicketService
						.findSubprocess(caTicket.getProcess().getId());
			}
			SolutionReview solutionReview = caTicketService
					.getLatestReviewDateById(caTicket.getId());
			if (solutionReview != null) {
				caTicket.setSolutionreViewDate(solutionReview.getReviewDate());
				System.out.println("Review date: "
						+ solutionReview.getReviewDate());
			} else {
				caTicket.setSolutionreViewDate(null);
			}
			uiModel.addAttribute(Constants.CATICKETS, caTicket);
			uiModel.addAttribute(Constants.PROJECTS,
					caTicketService.findModuleNameProjects());
			uiModel.addAttribute(Constants.RESOURCES,
					resourceService.findActiveResources());
			uiModel.addAttribute(Constants.LANDSCAPE,
					caTicketService.getAllLandscape());
			uiModel.addAttribute(Constants.UNITS, caTicketService.getAllUnit());
			uiModel.addAttribute(Constants.REASON_FOR_HOPPING,
					caTicketService.getAllReasonForHopping());
			uiModel.addAttribute(Constants.REASON_FOR_REOPEN,
					caTicketService.getAllReasonForReopen());
			uiModel.addAttribute(Constants.REASON_FOR_SLA_MISSED,
					caTicketService.getAllReasonForSLAMissed());
			uiModel.addAttribute(Constants.PROCESS, processByModuleIdList);
			uiModel.addAttribute(Constants.SUBPROCESS,
					subprocessByProcessIdList);
			uiModel.addAttribute(Constants.ROOTCAUSE,
					caTicketService.getAllRootCause());
			uiModel.addAttribute("caTicketForm", new CATicketForm());
			uiModel.addAttribute("role", userContextDetails.getUserRole());
			uiModel.addAttribute("loggedInId",
					userContextDetails.getEmployeeId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "caticket/editticket";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getTicketsByPhase")
	@ResponseBody
	public ResponseEntity<String> getTicketByPhase(
			@RequestParam(value = "empId") int empId,
			@RequestParam(value = "module") int moduleId,
			@RequestParam(value = "phase") String phase) throws Exception {
		System.out.println("In getTicketByPhase");
		logger.info("------CATicketController loadHistory method start------");
		ResponseEntity response = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		System.out.println("Employee Id: " + empId + " Module Id: " + moduleId);
		try {

			List<CATicket> list = caTicketService.getTicketDetailsbyPhase(
					empId, moduleId, phase, UserUtil.getCurrentResource()
							.getEmployeeId());
			System.out.println("List Size: " + list.size());

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy/MM/dd hh:mm:ss");
			Calendar calobj = Calendar.getInstance();
			String currentDate = simpleDateFormat.format(calobj.getTime());
			System.out.println("currentDate: " + currentDate);
			Date now = simpleDateFormat.parse(currentDate);
			List<CATicket> caTicketList = new ArrayList<CATicket>();
			for (CATicket caTicket : list) {
				String priority = caTicket.getPriority();
				Date creationDate = caTicket.getCreationDate();
				String aging = RMSUtil.isAgingCrossed(now, creationDate,
						priority,
						caTicket.getClosePendingCustomerApprovalDate());
				String sla = RMSUtil.isSLAMissedOut(now, creationDate,
						priority,
						caTicket.getClosePendingCustomerApprovalDate());

				int diffInDays = 0;
				/*
				 * Updated on 18/Nov/2015 Added if condition for closure date
				 * Days difference should be calculated based on closure and
				 * creation date if closure date is not null
				 */

				if (caTicket.getClosePendingCustomerApprovalDate() != null) {
					if (creationDate != null) {
						diffInDays = (int) ((caTicket
								.getClosePendingCustomerApprovalDate()
								.getTime() - creationDate.getTime()) / (1000 * 60 * 60 * 24));
					}
				} else {
					if (creationDate != null) {
						diffInDays = (int) ((now.getTime() - creationDate
								.getTime()) / (1000 * 60 * 60 * 24));
					}
				}
				caTicket.setAging(aging);
				caTicket.setSlaMissed(sla);
				caTicket.setDaysOpen(diffInDays + "");
				if (caTicket.getCustomerApprovalFlag() != null) {
					if (caTicket.getCustomerApprovalFlag().equalsIgnoreCase(
							"No")) {
						caTicket.setCustomerApprovalFlag("No");
					} else if (caTicket.getCustomerApprovalFlag()
							.equalsIgnoreCase("Yes")) {
						caTicket.setCustomerApprovalFlag("Yes");
					} else if (caTicket.getCustomerApprovalFlag()
							.equalsIgnoreCase("N.A.")) {
						caTicket.setCustomerApprovalFlag("N.A.");
					}
				}
				if (caTicket.getProblemManagementFlag() != null) {
					if (caTicket.getProblemManagementFlag().equalsIgnoreCase(
							"No")) {
						caTicket.setProblemManagementFlag("No");
					} else {
						caTicket.setProblemManagementFlag("Yes");
					}
				}
				caTicketList.add(caTicket);
			}
			System.out.println("Tickets json arrayc: "
					+ CATicket.toJsonArray(caTicketList));

			response = new ResponseEntity<String>(CATicket.toJsonArray(list),
					headers, HttpStatus.OK);
		} catch (RuntimeException exception) {
			logger.error("RuntimeException occured in loadHistory method of CATicketController:"
					+ exception);
			throw exception;
		} catch (Exception e) {
			logger.error("Exception occured in loadHistory method of CATicketController:"
					+ e);
			throw e;
		}
		return response;
	}

	@RequestMapping(value = "/downloadTicketExcel")
	protected void handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("------UploadCATicketController handleRequestInternal method start----");
		ServletContext servletContext = request.getSession()
				.getServletContext();
		String fullPath = servletContext
				.getRealPath("/WEB-INF//Upload CA Ticket Sample.xlsx");
		File file = new File(fullPath);
		response.setContentType("application/xlsx");
		response.setHeader("Content-Disposition",
				"attachment; filename=Upload CA Ticket Sample.xlsx");
		try {
			FileCopyUtils.copy(new FileInputStream(file),
					response.getOutputStream());
			logger.info("------UploadCATicketController handleRequestInternal method end----");
		} catch (IOException e) {
			logger.error("IOException occured in handleRequestInternal method of UploadCATicketController controller:"
					+ e);
			throw e;
		}
		return;

	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/createTicket", method = RequestMethod.POST)
	public String createTicket(
			@ModelAttribute(value = "caticketform") CATicketForm caticketform)
			throws Exception {
		System.out.println("In create form: " + caticketform.getId());
		CATicket caTicket = null;
		String subjectFlag = null;
		String methodName = "create";
		int confgId = 0;
		try {

			if (null == caticketform) {
				throw new IllegalArgumentException(
						"CATicket Form Object Cannot be null");
			}

			// if (caticketform.getProcess().getId() == -1) {
			// caticketform.setProcess(null);
			// }
			//
			// if (caticketform.getSubProcess().getId() == -1) {
			// caticketform.setSubProcess(null);
			// }
			//
			// if (caticketform.getRootCause().getId() == -1) {
			// caticketform.setRootCause(null);
			// }

			if (caticketform.getProblemManagementFlag() != null) {
				if (caticketform.getProblemManagementFlag().equals("No")) {
					caticketform.setProcess(null);
					caticketform.setSubProcess(null);
					caticketform.setRootCause(null);
					caticketform.setZREQNo(null);
					caticketform.setParentTicketNo(null);
					caticketform.setComment("");
					caticketform.setJustifiedProblemManagement("No");

				}
			}

			caticketform.setT3StatusFlag("No");
			caticketform.setDefectStatusFlag("No");
			caticketform.setCropStatusFlag("No");
			caticketform.setReworkStatusFlag("No");

			/*
			 * System.out.println("Creation date: " +
			 * caticketform.getCreationDate());
			 * System.out.println("Description: " +
			 * caticketform.getDescription() + " Assignee id: " +
			 * caticketform.getAssigneeId().getEmployeeId());
			 */
			caticketform.setUpdatedBy(UserUtil.getCurrentResource().getUsername());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy/MM/dd hh:mm:ss");
			Calendar calobj = Calendar.getInstance();
			String currentDate = simpleDateFormat.format(calobj.getTime());
			Date now = simpleDateFormat.parse(currentDate);
			caticketform.setUpdatedDate(now);
			caTicket = caTicketService.saveCATicket(caticketform,
					caticketform.getDiscrepencyId());

			/************ Mail Functionality ****************/
			if (caTicket != null) {
				if (caTicket.getSolutionReadyForReview() != null) {
					if (caTicket.getSolutionReadyForReview().equalsIgnoreCase(
							"No")) {
						subjectFlag = "Ticket_Create";
						confgId = Integer.parseInt(Constants
								.getProperty("Ticket_Creation"));

					} else {
						subjectFlag = "Soution_Ready_Ror_Review";
						confgId = Integer.parseInt(Constants
								.getProperty("Solution_Ready_Review"));
					}
				} else {
					subjectFlag = "Ticket_Create";
					confgId = Integer.parseInt(Constants
							.getProperty("Ticket_Creation"));
				}
				List<MailConfiguration> mailConfg = mailConfgService
						.findByProjectId(caTicket.getModuleId().getId(),
								confgId);

				if (caTicket != null && mailConfg != null
						&& mailConfg.size() > 0) {
					Resource assignee = resourceService.find(caTicket
							.getAssigneeId().getEmployeeId());
					Resource reviewer = resourceService.find(caTicket
							.getReviewer().getEmployeeId());
					String[] assigneeEmail = new String[2];
					assigneeEmail[0] = assignee.getEmailId();
					String[] ccMailID = new String[2];
					String reviewerEmail = reviewer.getEmailId();
					String IRMEmail = assignee.getCurrentReportingManager()
							.getEmailId();
					ccMailID[0] = reviewerEmail;
					// ccMailID[1] = IRMEmail;
					Map<String, Object> model = new HashMap<String, Object>();
					resourceHelper.setEmailContentforCATicket(model, caTicket,
							null, assigneeEmail, ccMailID, subjectFlag,
							methodName);

					emailHelper.sendEmailCATicket(model, assigneeEmail,
							ccMailID, mailConfg, caTicket);
				}
			}

		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in create method of CATicketController:"
					+ runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in create method of CATicketController:"
					+ exception);
			throw exception;
		}
		logger.info("------CATicketController create method end------");

		return "redirect:getTicketById/" + caTicket.getId();
	}

	@RequestMapping(value = "/editTicket", method = RequestMethod.POST)
	public String editTicket(
			@ModelAttribute(value = "caticketForm") CATicketForm caticketform,
			Model uiModel/* , BindingResult result */) throws Exception {
		String subjectFlag = null;
		int confgId;
		String methodName = "edit";
		System.out.println("In edit form: " + caticketform);

		if (null == caticketform) {
			throw new IllegalArgumentException(
					"Employee Form Object Cannot be null");
		}

		try {

			// if (caticketform.getProcess().getId() == -1) {
			// caticketform.setProcess(null);
			// }
			//
			// if (caticketform.getSubProcess().getId() == -1) {
			// caticketform.setSubProcess(null);
			// }
			//
			// if (caticketform.getRootCause().getId() == -1) {
			// caticketform.setRootCause(null);
			// }
			if (caticketform.getProblemManagementFlag() != null) {
				if (caticketform.getProblemManagementFlag().equals("No")) {
					caticketform.setProcess(null);
					caticketform.setSubProcess(null);
					caticketform.setRootCause(null);
					caticketform.setZREQNo(null);
					caticketform.setParentTicketNo(null);
					caticketform.setComment("");
					caticketform.setJustifiedProblemManagement("No");

				}
			}

			if (caticketform.getReasonForHopping() != null) {
				if (caticketform.getReasonForHopping().getId() == -1) {
					caticketform.setReasonForHopping(null);
				}
			}

			if (caticketform.getReasonForReopen() != null) {
				if (caticketform.getReasonForReopen().getId() == -1) {
					caticketform.setReasonForReopen(null);
				}
			}

			if (caticketform.getReasonForSLAMissed() != null) {
				if (caticketform.getReasonForSLAMissed().getId() == -1) {
					caticketform.setReasonForSLAMissed(null);
				}
			}

			String solutionReadyReviewFlag = caTicketService
					.getSolutionReadyReviewFlag(caticketform.getCaTicketNo());
			// CATicket caTicket = new CATicket();//
			// caTicketService.getTicketByTicketNumber(caticketform.getCaTicketNo());
			// caTicket.setUnitId(caTicketService.getUnit(caTicketService
			// .getUnit(caticketform.getCaTicketNo())));
			// caTicket.setLandscapeId(caTicketService
			// .getLandscape(caTicketService.getLandscape(caticketform
			// .getCaTicketNo())));
			// caTicket.setRegion(caTicketService.getRegion(caTicketService
			// .getRegion(caticketform.getCaTicketNo())));
			// caTicket.setSolutionReadyForReview(caTicketService
			// .getSolutionReadyReviewFlag(caticketform.getCaTicketNo()));
			// caTicket.setCaTicketNo(caticketform.getCaTicketNo());
			// caTicket.setDescription(caTicketService.getDescription(caticketform
			// .getCaTicketNo()));
			// caTicket.setModuleId(projectService.findProject(caTicketService
			// .getModule(caticketform.getCaTicketNo())));
			// caTicket.setAssigneeId(resourceService.find(caTicketService
			// .getAssingeeId(caticketform.getCaTicketNo())));
			// caTicket.setReviewer(resourceService.find(caTicketService
			// .getReviewerId(caticketform.getCaTicketNo())));
			// caTicket.setAging(caTicketService.getAging(caticketform
			// .getCaTicketNo()));
			// caTicket.setCreationDate(caTicketService
			// .getCreationDate(caticketform.getCaTicketNo()));

			/*
			 * Updated on 18/Nov/2015 saving latest review date if saving page
			 */
			if (caticketform.getId() != null && caticketform.getId() != "") {
				SolutionReview solutionReview = caTicketService
						.getLatestReviewDateById(Integer.parseInt(caticketform
								.getId()));
				if (solutionReview != null) {
					caticketform.setSolutionreViewDate(solutionReview
							.getReviewDate());
				}
			}

			/*
			 * End changes updation
			 */
			caticketform.setUpdatedBy(UserUtil.getCurrentResource().getUsername());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy/MM/dd hh:mm:ss");
			Calendar calobj = Calendar.getInstance();
			String currentDate = simpleDateFormat.format(calobj.getTime());
			Date now = simpleDateFormat.parse(currentDate);
			caticketform.setUpdatedDate(now);
			caTicketService.editCATicket(caticketform);

			// if (solutionReadyReviewFlag.equalsIgnoreCase(caticketform
			// .getSolutionReadyForReview())) {
			// confgId = Integer.parseInt(Constants
			// .getProperty("Ticket_Creation"));
			// subjectFlag = "edit_ticket";
			// } else {
			//
			// confgId = Integer.parseInt(Constants
			// .getProperty("Solution_Ready_Review"));
			// subjectFlag = "Soution_Ready_Ror_Review";
			// }
			// List<MailConfiguration> mailConfg = mailConfgService
			// .findByProjectId(caTicket.getModuleId().getId(), confgId);
			//
			// if (caTicket != null && mailConfg != null && mailConfg.size() >
			// 0) {
			//
			// Resource assignee = resourceService.find(caTicket
			// .getAssigneeId().getEmployeeId());
			// Resource reviewer = resourceService.find(caTicket.getReviewer()
			// .getEmployeeId());
			// String[] assigneeEmail = new String[2];
			// assigneeEmail[0] = assignee.getEmailId();
			// String[] ccMailID = new String[2];
			// String reviewerEmail = reviewer.getEmailId();
			// String IRMEmail = assignee.getCurrentReportingManager()
			// .getEmailId();
			// ccMailID[0] = reviewerEmail;
			// ccMailID[1] = IRMEmail;
			//
			// Map<String, Object> model = new HashMap<String, Object>();
			// resourceHelper.setEmailContentforCATicket(model, caTicket,
			// caticketform, assigneeEmail, ccMailID, subjectFlag,
			// methodName);
			//
			// emailHelper.sendEmailCATicket(model, assigneeEmail, ccMailID,
			// mailConfg, caTicket);
			// }

		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in create method of ResourceController:"
					+ runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in create method of ResourceController:"
					+ exception);
			throw exception;
		}
		logger.info("------ResourceController create method end------");
		// uiModel.addAttribute("caticketForm", caticketform);
		// return "redirect:viewTicket";
		return "redirect:getTicketById/" + caticketform.getId();
	}

	@RequestMapping(value = "getTicketById/saveT3", method = RequestMethod.POST, produces = "text/html", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> saveT3(@RequestBody String requestJSON)
			throws Exception {
		logger.info("------CATicketController savet3 method start------");

		ResponseEntity response1 = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		System.out.println("json:::::" + requestJSON);
		JSONObject jsonObject = new JSONObject(requestJSON);

		JSONArray jsonArray = jsonObject.getJSONArray("parameters");
		T3Contribution t3Contribution = new T3Contribution();
		String id = jsonArray.getJSONObject(0).getString("id");
		System.out.println("testing id: " + id);
		if (!id.equals("")) {
			t3Contribution.setId(Integer.parseInt(id));
		}
		/*
		 * t3Contribution.setTicketNumber(Integer.parseInt(jsonArray
		 * .getJSONObject(0).getString("ticketNumber")));
		 */
		/*
		 * t3Contribution
		 * .setModule(jsonArray.getJSONObject(0).getString("module"));
		 */
		t3Contribution.setJustified(jsonArray.getJSONObject(0).getString(
				"justified"));
		t3Contribution.setNoOfhoursTaken((Integer.parseInt(jsonArray
				.getJSONObject(0).getString("noOfhoursTaken"))));
		t3Contribution.setDescription(jsonArray.getJSONObject(0).getString(
				"description"));

		String dateCont = jsonArray.getJSONObject(0).getString("dateContacted");
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");

		t3Contribution.setDateContacted(sdf.parse(dateCont));

		t3Contribution.setReasonForHelp((jsonArray.getJSONObject(0)
				.getString("reasonForHelp")));
		CATicket ca = new CATicket();
		int ticketId = Integer.parseInt(jsonArray.getJSONObject(0).getString(
				"caTicket"));
		ca.setId((Integer.parseInt(jsonArray.getJSONObject(0).getString(
				"caTicket"))));
		caTicketService.findTicketById(ticketId).setT3StatusFlag("Yes");
		t3Contribution.setCaTicket(ca);

		List<T3Contribution> t3ContributionList = caTicketService
				.saveOrUpdateT3(t3Contribution);

		response1 = new ResponseEntity<String>(
				T3Contribution.toJsonArray(t3ContributionList), headers,
				HttpStatus.OK);
		System.out.println("response1.............. "
				+ T3Contribution.toJsonArray(t3ContributionList));

		logger.info("------CATicketController saveT3 method end------");
		return response1;
	}

	@RequestMapping(value = "getTicketById/saveSR", method = RequestMethod.POST, produces = "text/html", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> saveSolutionReview(
			@RequestBody String solReviewJSON) throws Exception {
		logger.info("------CATicketController saveSolReview method start------");

		ResponseEntity response1 = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		System.out.println("json:::::" + solReviewJSON);
		JSONObject jsonObject = new JSONObject(solReviewJSON);

		JSONArray jsonArray = jsonObject.getJSONArray("parameters");
		SolutionReview review = new SolutionReview();

		CATicket ca = new CATicket();
		int id = Integer.parseInt(jsonArray.getJSONObject(0).getString(
				"caTicket"));
		ca.setId(id);
		caTicketService.findTicketById(id).setSolutionReviewFlag("Yes");
		String resDate = jsonArray.getJSONObject(0).getString("reviewDate");
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");

		// ca.setSolutionreViewDate(sdf.parse(resDate));

		review.setCaTicket(ca);
		review.setReviewDate(sdf.parse(resDate));
		review.setIsTheIssueUnderstandingCorrect(jsonArray.getJSONObject(0)
				.getString("isTheIssueUnderstandingCorrect"));
		/*
		 * review.setCaTicketNumber((Integer.parseInt(jsonArray.getJSONObject(0)
		 * .getString("caTicketNumber"))));
		 */
		review.setAlternateSolution(jsonArray.getJSONObject(0).getString(
				"alternateSolution"));
		review.setIsSolAppropriate(jsonArray.getJSONObject(0).getString(
				"isSolAppropriate"));
		review.setAgreeWithRca(jsonArray.getJSONObject(0).getString(
				"agreeWithRca"));
		review.setRating(jsonArray.getJSONObject(0).getString("rating"));
		review.setComments((jsonArray.getJSONObject(0).getString("comments")));

		List<SolutionReview> solutionReviewList = caTicketService
				.saveOrUpdateSolutionReview(review);

		response1 = new ResponseEntity<String>(
				SolutionReview.toJsonArray(solutionReviewList), headers,
				HttpStatus.OK);
		System.out.println("response1.............. "
				+ SolutionReview.toJsonArray(solutionReviewList));

		logger.info("------CATicketController saveSolReview method end------");
		return response1;
	}

	@RequestMapping(value = "getTicketById/saveDL", method = RequestMethod.POST, produces = "text/html", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> saveDefectLog(
			@RequestBody String defectLogJSON) throws Exception {
		logger.info("------CATicketController savedefectLog method start------");

		ResponseEntity response1 = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		System.out.println("json:::::" + defectLogJSON);
		JSONObject jsonObject = new JSONObject(defectLogJSON);

		JSONArray jsonArray = jsonObject.getJSONArray("parameters");
		DefectLog log = new DefectLog();

		CATicket ca = new CATicket();
		int id = Integer.parseInt(jsonArray.getJSONObject(0).getString(
				"caTicket"));
		ca.setId(id);
		caTicketService.findTicketById(id).setDefectStatusFlag("Yes");

		log.setCaTicket(ca);
		log.setDefectType(jsonArray.getJSONObject(0).getString("defectType"));
		log.setDefectDescription(jsonArray.getJSONObject(0).getString(
				"defectDescription"));
		log.setInternalExternal(jsonArray.getJSONObject(0).getString(
				"internalExternal"));
		log.setDefectCategory(jsonArray.getJSONObject(0).getString(
				"defectCategory"));
		log.setSeverity(jsonArray.getJSONObject(0).getString("severity"));
		log.setDefectStatus(jsonArray.getJSONObject(0)
				.getString("defectStatus"));
		log.setIdentifiedPhase(jsonArray.getJSONObject(0).getString(
				"identifiedPhase"));
		log.setInjectedPhase(jsonArray.getJSONObject(0).getString(
				"injectedPhase"));
		log.setWorkProductName(jsonArray.getJSONObject(0).getString(
				"workProductName"));
		log.setReopened(jsonArray.getJSONObject(0).getString("reopened"));
		log.setDefectRootCause(jsonArray.getJSONObject(0).getString(
				"defectRootCause"));
		log.setCategoryOfRootCause(jsonArray.getJSONObject(0).getString(
				"categoryOfRootCause"));
		log.setResolvedBy(jsonArray.getJSONObject(0).getString("resolvedBy"));
		log.setIdentifiedBy(jsonArray.getJSONObject(0)
				.getString("identifiedBy"));
		String identDate = jsonArray.getJSONObject(0).getString(
				"identifiedDate");
		String closeDate = jsonArray.getJSONObject(0).getString("closedDate");
		String resDate = jsonArray.getJSONObject(0).getString("resolvedDate");
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
		log.setIdentifiedDate(sdf.parse(identDate));
		log.setClosedDate(sdf.parse(closeDate));
		log.setResolvedDate(sdf.parse(resDate));

		List<DefectLog> defectLogList = caTicketService
				.saveOrUpdateDefectLog(log);

		response1 = new ResponseEntity<String>(
				DefectLog.toJsonArray(defectLogList), headers, HttpStatus.OK);
		System.out.println("response1.............. "
				+ DefectLog.toJsonArray(defectLogList));

		logger.info("------CATicketController saveT3 method end------");
		return response1;
	}

	@RequestMapping(value = "getTicketById/saveCrop", method = RequestMethod.POST, produces = "text/html", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> saveCrop(@RequestBody String CropJSON)
			throws Exception {
		logger.info("------CATicketController saveCrop method start------");

		HttpHeaders headers = new HttpHeaders();
		ResponseEntity response1 = null;
		headers.add("Content-Type", "application/json; charset=utf-8");

		System.out.println("json:::::" + CropJSON);
		JSONObject jsonObject = new JSONObject(CropJSON);

		JSONArray jsonArray = jsonObject.getJSONArray("parameters");
		Crop crop = new Crop();

		CATicket ca = new CATicket();
		int id = Integer.parseInt(jsonArray.getJSONObject(0).getString(
				"caTicket"));
		ca.setId(id);
		caTicketService.findTicketById(id).setCropStatusFlag("Yes");

		crop.setCaTicket(ca);
		crop.setTitle(jsonArray.getJSONObject(0).getString("title"));
		crop.setDescription(jsonArray.getJSONObject(0).getString("description"));
		crop.setSource(jsonArray.getJSONObject(0).getString("source"));
		crop.setBenefitType(jsonArray.getJSONObject(0).getString("benefitType"));
		crop.setTotalBusinessHrsSaved(Integer.parseInt(jsonArray.getJSONObject(
				0).getString("totalBusinessHrsSaved")));
		crop.setTotalITHoursSaved(Integer.parseInt(jsonArray.getJSONObject(0)
				.getString("totalITHoursSaved")));
		crop.setSavingsInUSD(jsonArray.getJSONObject(0).getString(
				"savingsInUSD"));
		crop.setJustified(jsonArray.getJSONObject(0).getString("justified"));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy/MM/dd hh:mm:ss");
		Calendar calobj = Calendar.getInstance();
		String currentDate = simpleDateFormat.format(calobj.getTime());
		Date now = simpleDateFormat.parse(currentDate);
		crop.setUpdatedDate(now);

		List<Crop> cropList = caTicketService.saveOrUpdateCrop(crop);

		response1 = new ResponseEntity<String>(Crop.toJsonArray(cropList),
				headers, HttpStatus.OK);
		System.out.println("response1.............. "
				+ Crop.toJsonArray(cropList));

		logger.info("------CATicketController saveT3 method end------");
		return response1;
	}

	@RequestMapping(value = "getTicketById/saveRework", method = RequestMethod.POST, produces = "text/html", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> saveRework(@RequestBody String reWorkJSON)
			throws Exception {
		logger.info("------CATicketController saveCrop method start------");

		ResponseEntity response1 = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		System.out.println("reWorkJSON:::::" + reWorkJSON);
		JSONObject jsonObject = new JSONObject(reWorkJSON);

		JSONArray jsonArray = jsonObject.getJSONArray("parameters");
		Rework rework = new Rework();

		CATicket ca = new CATicket();
		int id = Integer.parseInt(jsonArray.getJSONObject(0).getString(
				"caTicket"));
		ca.setId(id);
		caTicketService.findTicketById(id).setReworkStatusFlag("Yes");

		rework.setCaTicket(ca);
		rework.setReworkType(jsonArray.getJSONObject(0).getString("reworkType"));

		String startDate = jsonArray.getJSONObject(0).getString(
				"startDateTimestamp");
		String endDate = jsonArray.getJSONObject(0).getString(
				"endDateTimestamp");
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");

		rework.setStartDateTimestamp(sdf.parse(startDate));
		rework.setEndDateTimestamp(sdf.parse(endDate));
		rework.setJustified(jsonArray.getJSONObject(0).getString("justified"));
		long secs = (sdf.parse(endDate).getTime() - sdf.parse(startDate)
				.getTime()) / 1000;
		int diffInHours = (int) secs / 3600;
		rework.setHourse(diffInHours);
		List<Rework> reworkList = caTicketService.saveOrUpdateRework(rework);

		response1 = new ResponseEntity<String>(Rework.toJsonArray(reworkList),
				headers, HttpStatus.OK);
		System.out.println("response1.............. "
				+ Rework.toJsonArray(reworkList));

		logger.info("------CATicketController saveT3 method end------");
		return response1;
	}

	@RequestMapping(value = "/loadProcessModule")
	@ResponseBody
	public ResponseEntity<String> loadProcessModule(
			@RequestParam(value = "moduleId") int moduleid,
			HttpServletResponse response) throws Exception {
		logger.info("------ResourceLoanAndTransferController loadResourceData method start------");
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity response1 = null;
		headers.add("Content-Type", "application/json; charset=utf-8");
		try {
			List<CATicketProcess> c = caTicketService
					.findProcessByModuleId(moduleid);
			response1 = new ResponseEntity<String>(
					CATicketProcess.toJsonArray(c), headers, HttpStatus.OK);
		} catch (RuntimeException exception) {
			logger.error("RuntimeException occured in loadResourceData method of ResourceLoanAndTransferController:"
					+ exception);
			throw exception;
		} catch (Exception e) {
			logger.error("Exception occured in loadResourceData method of ResourceLoanAndTransferController:"
					+ e);
			throw e;
		}
		return response1;

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getModuleByEmployee")
	@ResponseBody
	public ResponseEntity<String> getModuleByEmployee(
			@RequestParam(value = "employeeId") int employeeId,
			HttpServletResponse response) throws Exception {
		logger.info("------ResourceLoanAndTransferController loadResourceData method start------");
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity response1 = null;
		headers.add("Content-Type", "application/json; charset=utf-8");
		try {
			List<Project> c = caTicketService.findModuleByEmployee(employeeId);
			response1 = new ResponseEntity<String>(
					CATicket.toProjectJsonArray(c), headers, HttpStatus.OK);
		} catch (RuntimeException exception) {
			logger.error("RuntimeException occured in loadResourceData method of ResourceLoanAndTransferController:"
					+ exception);
			throw exception;
		} catch (Exception e) {
			logger.error("Exception occured in loadResourceData method of ResourceLoanAndTransferController:"
					+ e);
			throw e;
		}
		return response1;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadSubProcesses")
	@ResponseBody
	public ResponseEntity<String> loadSubProcesses(
			@RequestParam(value = "processId") int processId,
			HttpServletResponse response) throws Exception {
		logger.info("------ResourceLoanAndTransferController loadResourceData method start------");
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity response1 = null;
		headers.add("Content-Type", "application/json; charset=utf-8");
		try {
			List<CATicketSubProcess> c = caTicketService
					.findSubprocess(processId);
			response1 = new ResponseEntity<String>(
					CATicketSubProcess.toJsonArray(c), headers, HttpStatus.OK);
		} catch (RuntimeException exception) {
			logger.error("RuntimeException occured in loadResourceData method of ResourceLoanAndTransferController:"
					+ exception);
			throw exception;
		} catch (Exception e) {
			logger.error("Exception occured in loadResourceData method of ResourceLoanAndTransferController:"
					+ e);
			throw e;
		}
		return response1;

	}

	@RequestMapping(value = "/viewReviewTicket", method = RequestMethod.GET)
	public String viewReviewerTicket(HttpServletRequest request, Model uiModel)
			throws Exception {

		List<org.yash.rms.domain.ResourceAllocation> resAlloc = null;

		int currentLoggedInUserId = UserUtil.getCurrentResource()
				.getEmployeeId();
		UserContextDetails userContextDetails = UserUtil
				.getUserContextDetails();
		List<CATicket> list = new ArrayList<CATicket>();
		List<Integer> allocId = new ArrayList<Integer>();
		List<Integer> Ids = new ArrayList<Integer>();
		List<Integer> empIdList = new ArrayList<Integer>();
		Set<CATicket> reviwerIds = new HashSet<CATicket>();
		List<CATicket> l = new ArrayList<CATicket>();
		Resource resource = new Resource();
		resource.setEmployeeId(currentLoggedInUserId);
		String userRole = UserUtil.getCurrentResource().getUserRole();
		System.out.println("userROle :: " + userRole);
		try {
			if (Constants.ROLE_DEL_MANAGER.equals(userContextDetails
					.getUserRole())
					|| Constants.ROLE_ADMIN.equals(userContextDetails
							.getUserRole())
					|| Constants.ROLE_BG_ADMIN.equals(userContextDetails
							.getUserRole())
					|| Constants.ROLE_MANAGER.equals(userContextDetails
							.getUserRole())) {
				empIdList = resourceService
						.findActiveResourceIdByRM2RM1(userContextDetails
								.getEmployeeId());
				List<CATicket> caTicket = caTicketService
						.getIRMReveiwerProjectName(null, empIdList);

				reviwerIds.addAll(caTicket);
				List<Integer> projectIds = projectAllocationService
						.getProjectIdsForManager(currentLoggedInUserId,
								"active");
				l = caTicketService.getIRMAssigneeReviewerList(projectIds);
				resAlloc = resourceAllocationService
						.findResourceAllocationByActiveTypeEmployeeId(resource);
				if (resAlloc.size() != 0) {
					for (org.yash.rms.domain.ResourceAllocation alloc : resAlloc) {
						allocId.add(alloc.getProjectId().getId());
					}
					/*
					 * if (userRole.equalsIgnoreCase("ROLE_USER")) { list =
					 * caTicketService.getReveiwerProjectName(allocId,
					 * currentLoggedInUserId); } else { list = caTicketService
					 * .getTicketsForReview(currentLoggedInUserId); }
					 */
					/*
					 * list = caTicketService.getReveiwerProjectName(allocId,
					 * currentLoggedInUserId);
					 */
					list = caTicketService
							.getTicketsForReview(currentLoggedInUserId);
				}

				reviwerIds.addAll(l);
				reviwerIds.addAll(list);

			} else {
				resAlloc = resourceAllocationService
						.findResourceAllocationByActiveTypeEmployeeId(resource);
				if (resAlloc.size() != 0) {
					for (org.yash.rms.domain.ResourceAllocation alloc : resAlloc) {
						allocId.add(alloc.getProjectId().getId());
					}
					list = caTicketService.getReveiwerProjectName(allocId,
							currentLoggedInUserId);
				}
			}

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy/MM/dd hh:mm:ss");
			Calendar calobj = Calendar.getInstance();
			int diffInDays = 0;
			if (null != list && list.size() > 0) {

				for (CATicket ca : list) {
					Date creationDate = ca.getCreationDate();
					Date closeDate = ca.getClosePendingCustomerApprovalDate();
					Date aceeptedDate = ca.getSolutionAcceptedDate();
					if (ca.getCustomerApprovalFlag() != null) {
						if (ca.getCustomerApprovalFlag()
								.equalsIgnoreCase("Yes")) {
							if (creationDate != null)
								diffInDays = (int) ((closeDate.getTime() - creationDate
										.getTime()) / (1000 * 60 * 60 * 24));
						} else if (ca.getCustomerApprovalFlag()
								.equalsIgnoreCase("No")) {
							if (creationDate != null) {
								String currentDate = simpleDateFormat
										.format(calobj.getTime());
								Date now = simpleDateFormat.parse(currentDate);
								diffInDays = (int) ((now.getTime() - creationDate
										.getTime()) / (1000 * 60 * 60 * 24));
								// ca.setDaysOpenForPhase(diffInDays);
							}
						} else if (ca.getCustomerApprovalFlag()
								.equalsIgnoreCase("N.A")) {
							if (ca.getSolutionAcceptedFlag() != null) {
								if (ca.getSolutionAcceptedFlag()
										.equalsIgnoreCase("Yes")) {
									if (creationDate != null
											&& aceeptedDate != null)
										diffInDays = (int) ((aceeptedDate
												.getTime() - creationDate
												.getTime()) / (1000 * 60 * 60 * 24));
								}
							}
						} else {
							if (creationDate != null) {
								String currentDate = simpleDateFormat
										.format(calobj.getTime());
								Date now = simpleDateFormat.parse(currentDate);
								diffInDays = (int) ((now.getTime() - creationDate
										.getTime()) / (1000 * 60 * 60 * 24));
							}
						}
					} else {
						if (creationDate != null) {
							String currentDate = simpleDateFormat.format(calobj
									.getTime());
							Date now = simpleDateFormat.parse(currentDate);
							diffInDays = (int) ((now.getTime() - creationDate
									.getTime()) / (1000 * 60 * 60 * 24));
						}
					}

					Ids.add(diffInDays);
					ca.setDaysOpen(diffInDays + "");
					if (creationDate != null) {
						String currentDate = simpleDateFormat.format(calobj
								.getTime());
						Date now = simpleDateFormat.parse(currentDate);
						ca.setSlaMissed(RMSUtil.isSLAMissedOut(now,
								creationDate, ca.getPriority(), closeDate));
						ca.setAging(RMSUtil.isAgingCrossed(now, creationDate,
								ca.getPriority(), closeDate));
					}

					String currentDate = simpleDateFormat.format(calobj
							.getTime());
					Date now = simpleDateFormat.parse(currentDate);
					int diffInPhaseDays = 0;
					if (ca.getAcknowledgedDate() == null
							|| ca.getAcknowledgedDate().equals("")) {
						if (creationDate != null) {
							diffInPhaseDays = (int) ((now.getTime() - creationDate
									.getTime()) / (1000 * 60 * 60 * 24));
						}
						// ca.setDaysOpenForPhase(diffInDays);
					} else if ((ca.getRequiredCompletedDate() == null || ca
							.getRequiredCompletedDate().equals(""))
							&& ca.getReqCompleteFlag().equalsIgnoreCase("No")) {
						if (ca.getAcknowledgedDate() != null) {
							diffInPhaseDays = (int) ((now.getTime() - ca
									.getAcknowledgedDate().getTime()) / (1000 * 60 * 60 * 24));
						}
					} else if ((ca.getAnalysisCompletedDate() == null || ca
							.getAnalysisCompletedDate().equals(""))
							&& ca.getAnalysisCompleteFlag().equalsIgnoreCase(
									"No")) {
						if (ca.getRequiredCompletedDate() != null) {
							diffInPhaseDays = (int) ((now.getTime() - ca
									.getRequiredCompletedDate().getTime()) / (1000 * 60 * 60 * 24));
						}
					} else if ((ca.getSolutiondevelopedDate() == null || ca
							.getSolutiondevelopedDate().equals(""))
							&& ca.getSolutionDevelopedFlag().equalsIgnoreCase(
									"No")) {
						if (ca.getAnalysisCompletedDate() != null) {
							diffInPhaseDays = (int) ((now.getTime() - ca
									.getAnalysisCompletedDate().getTime()) / (1000 * 60 * 60 * 24));
						}
					} else if (ca.getSolutionreViewDate() == null
							|| ca.getSolutionreViewDate().equals("")) {
						if (ca.getSolutiondevelopedDate() != null) {
							diffInPhaseDays = (int) ((now.getTime() - ca
									.getSolutiondevelopedDate().getTime()) / (1000 * 60 * 60 * 24));
						}
					} else if ((ca.getSolutionAcceptedDate() == null || ca
							.getSolutionAcceptedDate().equals(""))
							&& ca.getSolutionAcceptedFlag().equalsIgnoreCase(
									"No")) {
						if (ca.getSolutionreViewDate() != null) {
							diffInPhaseDays = (int) ((now.getTime() - ca
									.getSolutionreViewDate().getTime()) / (1000 * 60 * 60 * 24));
						}
					} else if (ca.getClosePendingCustomerApprovalDate() == null
							|| ca.getClosePendingCustomerApprovalDate().equals(
									"")) {
						if (ca.getSolutionAcceptedDate() != null) {
							diffInPhaseDays = (int) ((now.getTime() - ca
									.getSolutionAcceptedDate().getTime()) / (1000 * 60 * 60 * 24));
						}
					}
					ca.setDaysOpenForPhase(diffInPhaseDays);

				}
			}
			List eligibleResources = ResourceAllocationHelper
					.getEligibleResourcesForProject(resourceAllocationService,
							resourceService);
			uiModel.addAttribute(Constants.ELIGIBLE_RESOURCES,
					eligibleResources);
			uiModel.addAttribute(Constants.PROJECTS,
					caTicketService.findModuleNameProjects());

			if (Constants.ROLE_DEL_MANAGER.equals(userContextDetails
					.getUserRole())
					|| Constants.ROLE_MANAGER.equals(userContextDetails
							.getUserRole())
					|| Constants.ROLE_ADMIN.equals(userContextDetails
							.getUserRole())
					|| Constants.ROLE_BG_ADMIN.equals(userContextDetails
							.getUserRole())) {
				uiModel.addAttribute(Constants.CATICKETS, reviwerIds);
				uiModel.addAttribute("reviewerPage", true);
				uiModel.addAttribute("LoginId", currentLoggedInUserId);
			} else {
				uiModel.addAttribute("reviewerPage", true);
				uiModel.addAttribute(Constants.CATICKETS, list);
			}
			uiModel.addAttribute("previousPage", "forMyReview");
			// uiModel.addAttribute(Constants.OPENDAYS, Ids);
		} catch (RuntimeException exception) {
			logger.error("RuntimeException occured in loadResourceData method of ResourceLoanAndTransferController:"
					+ exception);
			throw exception;
		} catch (Exception e) {
			logger.error("Exception occured in loadResourceData method of ResourceLoanAndTransferController:"
					+ e);
			throw e;
		}
		previousPage = "forMyReview";
		return "caticket/viewticket";
	}

	@RequestMapping(value = "/t3Update", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createT3FromJson(@RequestBody String json)
			throws Exception {
		System.out.println("Json testing: " + json);
		ResponseEntity response1 = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// T3Contribution t3Contribution = (T3Contribution) jsonObjectMapper
		// .fromJsonToObject(json, T3Contribution.class);
		JSONObject jsonObject = new JSONObject(json);

		T3Contribution t3Contribution = new T3Contribution();
		t3Contribution.setId(jsonObject.getInt("id"));
		t3Contribution.setJustified(jsonObject.getString("justified"));
		t3Contribution.setNoOfhoursTaken((Integer.parseInt(jsonObject
				.getString("noOfhoursTaken"))));
		t3Contribution.setDescription(jsonObject.getString("description"));

		String dateCont = jsonObject.getString("dateContacted");
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");

		t3Contribution.setDateContacted(sdf.parse(dateCont));

		t3Contribution
				.setReasonForHelp((jsonObject.getString("reasonForHelp")));
		CATicket ca = new CATicket();
		ca.setId((Integer.parseInt(jsonObject.getJSONObject("caTicket")
				.getString("id"))));
		t3Contribution.setCaTicket(ca);
		List<T3Contribution> t3ContributionsList = caTicketService
				.saveOrUpdateT3(t3Contribution);
		response1 = new ResponseEntity<String>(
				T3Contribution.toJsonArray(t3ContributionsList), headers,
				HttpStatus.OK);
		return response1;
	}

	@RequestMapping(value = "/solRevUpdate", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createSolutionReviewFromJson(
			@RequestBody String json) throws Exception {
		System.out.println("Solution Review Json testing: " + json);

		ResponseEntity response1 = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		// SolutionReview solutionReview = (SolutionReview) jsonObjectMapper
		// .fromJsonToObject(json, SolutionReview.class);
		JSONObject jsonObject = new JSONObject(json);
		SolutionReview review = new SolutionReview();
		review.setId(jsonObject.getInt("id"));
		CATicket ca = new CATicket();
		int id = Integer.parseInt(jsonObject.getJSONObject("caTicket")
				.getString("id"));
		ca.setId(id);
		review.setCaTicket(ca);

		String reviewDate = jsonObject.getString("reviewDate");
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
		review.setReviewDate(sdf.parse(reviewDate));
		review.setIsTheIssueUnderstandingCorrect(jsonObject
				.getString("isTheIssueUnderstandingCorrect"));
		/*
		 * review.setCaTicketNumber((Integer.parseInt(jsonObject
		 * .getString("caTicketNumber"))));
		 */
		review.setAlternateSolution(jsonObject.getString("alternateSolution"));
		review.setIsSolAppropriate(jsonObject.getString("isSolAppropriate"));
		review.setAgreeWithRca(jsonObject.getString("agreeWithRca"));
		review.setRating(jsonObject.getString("rating"));
		review.setComments((jsonObject.getString("comments")));
		List<SolutionReview> solutionReviewList = caTicketService
				.saveOrUpdateSolutionReview(review);

		response1 = new ResponseEntity<String>(
				SolutionReview.toJsonArray(solutionReviewList), headers,
				HttpStatus.OK);
		System.out.println("response1.............. "
				+ SolutionReview.toJsonArray(solutionReviewList));

		logger.info("------CATicketController saveSolReview method end------");
		return response1;
	}

	@RequestMapping(value = "/defectLogUpdate", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createDefectLogFromJson(
			@RequestBody String json) throws Exception {
		System.out.println("Defect Log Json testing: " + json);

		ResponseEntity response1 = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		// DefectLog defectLog = (DefectLog) jsonObjectMapper.fromJsonToObject(
		// json, DefectLog.class);
		JSONObject jsonObject = new JSONObject(json);

		DefectLog log = new DefectLog();
		log.setId(jsonObject.getInt("id"));
		CATicket ca = new CATicket();
		int id = Integer.parseInt(jsonObject.getJSONObject("caTicket")
				.getString("id"));
		ca.setId(id);

		log.setCaTicket(ca);
		log.setDefectType(jsonObject.getString("defectType"));
		log.setDefectDescription(jsonObject.getString("defectDescription"));
		log.setInternalExternal(jsonObject.getString("internalExternal"));
		log.setDefectCategory(jsonObject.getString("defectCategory"));
		log.setSeverity(jsonObject.getString("severity"));
		log.setDefectStatus(jsonObject.getString("defectStatus"));
		log.setIdentifiedPhase(jsonObject.getString("identifiedPhase"));
		log.setInjectedPhase(jsonObject.getString("injectedPhase"));
		log.setWorkProductName(jsonObject.getString("workProductName"));
		log.setReopened(jsonObject.getString("reopened"));
		log.setDefectRootCause(jsonObject.getString("defectRootCause"));
		log.setCategoryOfRootCause(jsonObject.getString("categoryOfRootCause"));
		log.setResolvedBy(jsonObject.getString("resolvedBy"));

		String identDate = jsonObject.getString("identifiedDate");
		String closeDate = jsonObject.getString("closedDate");
		String resDate = jsonObject.getString("resolvedDate");
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
		log.setIdentifiedDate(sdf.parse(identDate));
		log.setClosedDate(sdf.parse(closeDate));
		log.setResolvedDate(sdf.parse(resDate));
		List<DefectLog> defectLogList = caTicketService
				.saveOrUpdateDefectLog(log);

		response1 = new ResponseEntity<String>(
				DefectLog.toJsonArray(defectLogList), headers, HttpStatus.OK);
		System.out.println("response1.............. "
				+ DefectLog.toJsonArray(defectLogList));

		logger.info("------CATicketController saveT3 method end------");
		return response1;
	}

	@RequestMapping(value = "/cropUpdate", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createCropFromJson(@RequestBody String json)
			throws Exception {
		System.out.println("Crop Json testing: " + json);
		// Crop crop = (Crop) jsonObjectMapper.fromJsonToObject(json,
		// Crop.class);

		ResponseEntity response1 = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		// startDateTimestamp",value:oData[2]});
		// sData.push({name:"endDateTimestamp",value:oData[3]});
		// String startDateTimestamp json.
		JSONObject jsonObject = new JSONObject(json);
		// Rework rework = (Rework) jsonObjectMapper.fromJsonToObject(json,
		// Rework.class);
		Crop crop = new Crop();
		crop.setId(jsonObject.getInt("id"));
		CATicket ca = new CATicket();
		int id = Integer.parseInt(jsonObject.getJSONObject("caTicket")
				.getString("id"));
		ca.setId(id);

		crop.setCaTicket(ca);
		crop.setTitle(jsonObject.getString("title"));
		crop.setDescription(jsonObject.getString("description"));
		crop.setSource(jsonObject.getString("source"));
		crop.setBenefitType(jsonObject.getString("benefitType"));
		crop.setTotalBusinessHrsSaved(jsonObject
				.getInt("totalBusinessHrsSaved"));
		crop.setTotalITHoursSaved(jsonObject.getInt("totalITHoursSaved"));
		crop.setSavingsInUSD(jsonObject.getString("savingsInUSD"));
		crop.setJustified(jsonObject.getString("justified"));

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy/MM/dd hh:mm:ss");
		Calendar calobj = Calendar.getInstance();
		String currentDate = simpleDateFormat.format(calobj.getTime());
		Date now = simpleDateFormat.parse(currentDate);
		crop.setUpdatedDate(now);
		List<Crop> cropList = caTicketService.saveOrUpdateCrop(crop);

		response1 = new ResponseEntity<String>(Crop.toJsonArray(cropList),
				headers, HttpStatus.OK);
		System.out.println("response1.............. "
				+ Crop.toJsonArray(cropList));

		return response1;
	}

	@RequestMapping(value = "/reworkUpdate", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createReworkFromJson(@RequestBody String json)
			throws Exception {
		System.out.println("Rework Json testing: " + json);

		ResponseEntity response1 = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		// startDateTimestamp",value:oData[2]});
		// sData.push({name:"endDateTimestamp",value:oData[3]});
		// String startDateTimestamp json.
		JSONObject jsonObject = new JSONObject(json);
		// Rework rework = (Rework) jsonObjectMapper.fromJsonToObject(json,
		// Rework.class);
		Rework rework = new Rework();
		rework.setId(jsonObject.getInt("id"));
		CATicket ca = new CATicket();
		int id = Integer.parseInt(jsonObject.getJSONObject("caTicket")
				.getString("id"));
		ca.setId(id);

		rework.setCaTicket(ca);
		rework.setReworkType(jsonObject.getString("reworkType"));

		String startDate = jsonObject.getString("startDateTimestamp");
		String endDate = jsonObject.getString("endDateTimestamp");
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");

		rework.setStartDateTimestamp(sdf.parse(startDate));
		rework.setEndDateTimestamp(sdf.parse(endDate));
		rework.setJustified(jsonObject.getString("justified"));
		long secs = (sdf.parse(endDate).getTime() - sdf.parse(startDate)
				.getTime()) / 1000;
		int diffInHours = (int) secs / 3600;
		rework.setHourse(diffInHours);
		List<Rework> reworkList = caTicketService.saveOrUpdateRework(rework);
		response1 = new ResponseEntity<String>(Rework.toJsonArray(reworkList),
				headers, HttpStatus.OK);
		System.out.println("response1.............. "
				+ Rework.toJsonArray(reworkList));

		logger.info("------CATicketController saveT3 method end------");
		return response1;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadRegion")
	@ResponseBody
	public ResponseEntity<String> loadRegion(
			@RequestParam(value = "unitId") int unitId,
			HttpServletResponse response) throws Exception {
		logger.info("------CATicketController loadRegion method start------");
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity response1 = null;
		headers.add("Content-Type", "application/json; charset=utf-8");
		try {
			List<Region> c = caTicketService.findRegionByUnit(unitId);
			response1 = new ResponseEntity<String>(Region.toJsonArray(c),
					headers, HttpStatus.OK);
		} catch (RuntimeException exception) {
			logger.error("RuntimeException occured in CATicketController loadRegion method of ResourceLoanAndTransferController:"
					+ exception);
			throw exception;
		} catch (Exception e) {
			logger.error("Exception occured in CATicketController loadRegion method of ResourceLoanAndTransferController:"
					+ e);
			throw e;
		}
		return response1;

	}

	@RequestMapping(value = "/dashboardFilter", method = RequestMethod.POST, headers = "Accept=text/html")
	public String dashboardFilter(
			@ModelAttribute dashboardFilter dashboardFilter, Model uiModel,
			HttpServletRequest request, BindingResult result) throws Exception {
		try {
			if (null == dashboardFilter) {
				throw new IllegalArgumentException(
						"Filter Object Cannot be null");
			} else
				System.out.println("dashboardFilter.getToTime() :: "
						+ dashboardFilter.getToTime());
			System.out.println("dashboardFilter.getFromTime() :: "
					+ dashboardFilter.getFromTime());
			System.out.println("dashboardFilter.getAssignee() :: "
					+ dashboardFilter.getAssignee());
			System.out.println("dashboardFilter.getLandscape() :: "
					+ dashboardFilter.getLandscape());
			System.out.println("dashboardFilter.getModule() :: "
					+ dashboardFilter.getModule());
			System.out.println("dashboardFilter.getPriority() :: "
					+ dashboardFilter.getPriority());
			System.out.println("dashboardFilter.getRegion() :: "
					+ dashboardFilter.getRegion());
			System.out.println("dashboardFilter.getReviewer() :: "
					+ dashboardFilter.getReviewer());

			if (dashboardFilter.getAssignee() == null) {
				dashboardFilter.setAssignee("-1");
			}

			if (dashboardFilter.getReviewer() == null) {
				dashboardFilter.setReviewer("-1");
			}

			rmsUtil.dashboard(uiModel, dashboardFilter);

			/* SERVICE */

		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in create method of CATicketController:"
					+ runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in create method of CATicketController:"
					+ exception);
			throw exception;
		}
		logger.info("------CATicketController create method end------");

		return "caticket/dashboard";
	}

	@RequestMapping(value = "/getResource")
	@ResponseBody
	public ResponseEntity<String> getResource(
			@RequestParam(value = "employeeId") int employeeId) {
		logger.info("------ResourceController getResource method start------");

		Resource resource = resourceService.find(employeeId);
		String irm = resource.getCurrentReportingManager().getEmployeeName();
		/*
		 * Gson gson = new Gson(); String resourceString =
		 * gson.toJson(resourceService.find(employeeId));
		 */
		String data = "{\"resourceData\": [ {\"id\": \""
				+ resource.getCurrentReportingManager().getEmployeeId()
				+ "\",\"irmName\" : \""
				+ resource.getCurrentReportingManager().getEmployeeName()
				+ "\"} ]}";

		logger.info("------ResourceController validateReleaseDate method start------");
		return new ResponseEntity<String>(data, HttpStatus.OK);
	}

	@RequestMapping(value = "/getEmployeeByModule")
	@ResponseBody
	public ResponseEntity<String> getEmployeeByModule(
			@RequestParam(value = "projectId") int projectId) {
		logger.info("------CATicketController getEmployeeByModule method start------");
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity response1 = null;
		headers.add("Content-Type", "application/json; charset=utf-8");
		try {
			List<Resource> resList = caTicketService
					.getEmployeeByModule(projectId);
			response1 = new ResponseEntity<String>(
					CATicket.resourseJsonArray(resList), headers, HttpStatus.OK);
			System.out.println("CATicket.resourseJsonArray(resList) ::: "
					+ CATicket.resourseJsonArray(resList));
		} catch (Exception e) {
			e.printStackTrace();
			response1 = new ResponseEntity<String>(headers,
					HttpStatus.EXPECTATION_FAILED);
		}

		logger.info("------CATicketController getEmployeeByModule method end------");
		return response1;
	}

	@RequestMapping(value = "/saveChangeFunctionality", method = RequestMethod.GET)
	public String SaveChangeFunctionality(
			@RequestParam(value = "ticketId") int ticketId,
			@RequestParam(value = "assigneeName") int assigneeName,
			@RequestParam(value = "priority") String priority,
			@RequestParam(value = "reviewer") String reviewer,
			@RequestParam(value = "moduleId") String module) {

		caTicketService.saveChangeFunctionality(ticketId, assigneeName,
				priority, reviewer);

		CATicket caTicket = caTicketService.getTicketByTicketNumber(ticketId);

		CATicketForm caTicketForm = new CATicketForm();// caTicketService.getTicketByTicketNumber(caticketform.getCaTicketNo());
		caTicketForm.setUnitId(caTicketService.getUnit(caTicket.getUnitId()
				.getId()));
		caTicketForm.setLandscapeId(caTicketService.getLandscape(caTicket
				.getLandscapeId().getId()));
		caTicketForm.setRegion(caTicketService.getRegion(caTicket.getRegion()
				.getId()));
		caTicketForm.setSolutionReadyForReview(caTicketService
				.getSolutionReadyReviewFlag(caTicket.getCaTicketNo()));
		caTicketForm.setCaTicketNo(caTicket.getCaTicketNo());
		caTicketForm.setDescription(caTicketService.getDescription(caTicket
				.getCaTicketNo()));
		caTicketForm.setModuleId(projectService.findProject(caTicketService
				.getModule(caTicket.getCaTicketNo())));
		caTicketForm.setAssigneeId(resourceService.find(caTicketService
				.getAssingeeId(caTicket.getCaTicketNo())));
		caTicketForm.setReviewer(resourceService.find(caTicketService
				.getReviewerId(caTicket.getCaTicketNo())));
		caTicketForm
				.setAging(caTicketService.getAging(caTicket.getCaTicketNo()));
		caTicketForm.setCreationDate(caTicketService.getCreationDate(caTicket
				.getCaTicketNo()));

		String subjectFlag = "change_Fun";

		int confgId = Integer.parseInt(Constants
				.getProperty("Change_Functionality"));
		List<MailConfiguration> mailConfg = mailConfgService.findByProjectId(
				caTicket.getModuleId().getId(), confgId);

		if (caTicket != null && mailConfg != null && mailConfg.size() > 0) {
			Resource assignee = resourceService.find(assigneeName);
			Resource reviewerId = resourceService.find(caTicket.getReviewer()
					.getEmployeeId());
			String[] assigneeEmail = new String[2];
			assigneeEmail[0] = assignee.getEmailId();
			String[] ccMailID = new String[2];
			String reviewerEmail = reviewerId.getEmailId();
			String IRMEmail = assignee.getCurrentReportingManager()
					.getEmailId();
			ccMailID[0] = reviewerEmail;
			ccMailID[1] = IRMEmail;
			Map<String, Object> model = new HashMap<String, Object>();
			resourceHelper.setEmailContentforCATicket(model, caTicket,
					caTicketForm, assigneeEmail, ccMailID, subjectFlag, "");

			try {
				emailHelper.sendEmailCATicket(model, assigneeEmail, ccMailID,
						mailConfg, caTicket);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "caticket/viewticket";
	}

	@RequestMapping(value = "/saveTransferFunctionality", method = RequestMethod.GET)
	public String SaveTransferFunctionality(
			@RequestParam(value = "ticketId") int ticketId,
			@RequestParam(value = "moduleId") int moduleId,
			@RequestParam(value = "assigneeName") String assigneeName,
			@RequestParam(value = "reviewer") String reviewer,
			@RequestParam(value = "justified") String justified,
			@RequestParam(value = "reason") String reason) {

		CATicket caTicket = caTicketService.getTicketByTicketNumber(ticketId);
		caTicketService.saveTransferFunctionality(ticketId, assigneeName,
				moduleId, reviewer, justified, reason);

		CATicketForm caTicketForm = new CATicketForm();// caTicketService.getTicketByTicketNumber(caticketform.getCaTicketNo());
		caTicketForm.setUnitId(caTicketService.getUnit(caTicket.getUnitId()
				.getId()));
		caTicketForm.setLandscapeId(caTicketService.getLandscape(caTicket
				.getLandscapeId().getId()));
		caTicketForm.setRegion(caTicketService.getRegion(caTicket.getRegion()
				.getId()));
		caTicketForm.setSolutionReadyForReview(caTicketService
				.getSolutionReadyReviewFlag(caTicket.getCaTicketNo()));
		caTicketForm.setCaTicketNo(caTicket.getCaTicketNo());
		caTicketForm.setDescription(caTicketService.getDescription(caTicket
				.getCaTicketNo()));
		caTicketForm.setModuleId(projectService.findProject(caTicketService
				.getModule(caTicket.getCaTicketNo())));
		caTicketForm.setAssigneeId(resourceService.find(caTicketService
				.getAssingeeId(caTicket.getCaTicketNo())));
		caTicketForm.setReviewer(resourceService.find(caTicketService
				.getReviewerId(caTicket.getCaTicketNo())));
		caTicketForm
				.setAging(caTicketService.getAging(caTicket.getCaTicketNo()));
		caTicketForm.setCreationDate(caTicketService.getCreationDate(caTicket
				.getCaTicketNo()));

		int confgId = Integer.parseInt(Constants
				.getProperty("Transfer_Functionality"));
		List<MailConfiguration> mailConfg = mailConfgService.findByProjectId(
				caTicketForm.getModuleId().getId(), confgId);

		if (caTicket != null && mailConfg != null && mailConfg.size() > 0) {
			Resource assignee = resourceService.find(caTicketForm
					.getAssigneeId().getEmployeeId());
			Resource reviewerId = resourceService.find(caTicketForm
					.getReviewer().getEmployeeId());
			String[] assigneeEmail = new String[2];
			assigneeEmail[0] = assignee.getEmailId();
			String[] ccMailID = new String[2];
			String reviewerEmail = reviewerId.getEmailId();
			String IRMEmail = assignee.getCurrentReportingManager()
					.getEmailId();
			ccMailID[0] = reviewerEmail;
			ccMailID[1] = IRMEmail;
			Map<String, Object> model = new HashMap<String, Object>();
			resourceHelper.setEmailContentforCATicket(model, caTicket,
					caTicketForm, assigneeEmail, ccMailID, "trans_Fun", null);

			try {
				emailHelper.sendEmailCATicket(model, assigneeEmail, ccMailID,
						mailConfg, caTicket);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "caticket/viewticket";
	}

	@RequestMapping(value = "/t3Delete", method = RequestMethod.POST)
	public ResponseEntity<String> deleteT3(
			@RequestParam(value = "rowId") int rowId) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		boolean isDeletedSuccess = caTicketService.deleteT3(rowId);
		if (isDeletedSuccess) {
			return new ResponseEntity<String>(headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(headers,
					HttpStatus.EXPECTATION_FAILED);
		}
	}

	@RequestMapping(value = "/solutionReviewDeleteRow", method = RequestMethod.POST)
	public ResponseEntity<String> deleteSolutionReview(
			@RequestParam(value = "rowId") int rowId) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		boolean isDeletedSuccess = caTicketService.deleteSolutionReview(rowId);
		if (isDeletedSuccess) {
			return new ResponseEntity<String>(headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(headers,
					HttpStatus.EXPECTATION_FAILED);
		}
	}

	@RequestMapping(value = "/defectLogDeleteRow", method = RequestMethod.POST)
	public ResponseEntity<String> deleteDefectLog(
			@RequestParam(value = "rowId") int rowId) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		boolean isDeletedSuccess = caTicketService.deleteDefectLog(rowId);
		if (isDeletedSuccess) {
			return new ResponseEntity<String>(headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(headers,
					HttpStatus.EXPECTATION_FAILED);
		}
	}

	@RequestMapping(value = "/cropDeleteRow", method = RequestMethod.POST)
	public ResponseEntity<String> deleteCROP(
			@RequestParam(value = "rowId") int rowId) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		boolean isDeletedSuccess = caTicketService.deleteCrop(rowId);
		if (isDeletedSuccess) {
			return new ResponseEntity<String>(headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(headers,
					HttpStatus.EXPECTATION_FAILED);
		}
	}

	@RequestMapping(value = "/reworkDeleteRow", method = RequestMethod.POST)
	public ResponseEntity<String> deleteRework(
			@RequestParam(value = "rowId") int rowId) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		boolean isDeletedSuccess = caTicketService.deleteRework(rowId);
		if (isDeletedSuccess) {
			return new ResponseEntity<String>(headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(headers,
					HttpStatus.EXPECTATION_FAILED);
		}
	}

	@RequestMapping(value = "/isTicketAlreadyExist", method = RequestMethod.POST)
	public ResponseEntity<String> isTicketAlreadyExist(
			@RequestParam("ticketNumber") BigInteger ticketNumber)
			throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		Status status = new Status();
		List<Status> listStatus = new ArrayList<Status>();

		try {
			System.out.println("Ticket Number: " + ticketNumber);
			boolean isTicketAlreadyExist = caTicketService
					.isTicketAlreadyExist(ticketNumber);

			if (isTicketAlreadyExist) {
				status.setStatus("true");
				listStatus.add(status);
				System.out.println("Response Json: "
						+ Status.toJsonArray(listStatus));
				return new ResponseEntity<String>(
						Status.toJsonArray(listStatus), headers, HttpStatus.OK);
			} else {
				status.setStatus("false");
				listStatus.add(status);
				System.out.println("Response Json: "
						+ Status.toJsonArray(listStatus));
				return new ResponseEntity<String>(
						Status.toJsonArray(listStatus), headers, HttpStatus.OK);
			}
		} catch (Exception e) {
			status.setStatus("false");
			listStatus.add(status);
			e.printStackTrace();
			return new ResponseEntity<String>(Status.toJsonArray(listStatus),
					headers, HttpStatus.EXPECTATION_FAILED);

		}
	}

	@RequestMapping(value = "/doUpload", method = RequestMethod.POST)
	public String uploadCATickets(HttpServletRequest request,
			@Valid FileUploadBean fileUploadBean, BindingResult bindingResult,
			Model uiModel, FileUpload content,
			HttpServletRequest httpServletRequest) throws Exception {
		
		String subjectFlag = null;
		String methodName = "upload";
		int confgId = 0;
		
		logger.info("------UploadCATicketController uploadExcelCATickets method start------");
		List<String> errorList = new ArrayList<String>();
		// Added for defect #269
		String fileName = "";
		if (fileUploadBean != null) {
			fileName = fileUploadBean.getFile().getOriginalFilename();
			System.out.println("file Name ------------" + fileName);
		}
		/* fix for #314- check for file format */
		String ext = "";
		int mid = fileName.lastIndexOf(".");
		ext = fileName.substring(mid + 1, fileName.length());
		System.out.println("fileNAme.............." + fileName);

		try {
			if (ServletFileUpload.isMultipartContent(request)) {
				List<CATicketExcelMapping> list = null;
				// Added to solve Bugs #271 ****
				try {
					/* fix for #314- check for file format */
					if (ext != null && !ext.equals("")) {
						if (!ext.trim().equals("xlsx")
								&& !ext.trim().equals("xls")) {
							errorList
									.add("Please upload the file in Excel format.");
							uiModel.addAttribute(Constants.ERROR_LIST,
									errorList);
							uiModel.addAttribute(Constants.FILE_NAME, fileName);
							logger.info("------UploadCATicketController uploadExcelResources method end------");
							return "uploadresources/uploadresources";
						}
					}

					list = ExcelUtil.readExcel(CATicketExcelMapping.class,
							fileUploadBean.getFile().getInputStream(),
							fileUploadBean.getFile().getOriginalFilename(),
							errorList);
					System.out.println("Size: " + list.size());
					// Added for defect #306
					/*
					 * if (list == null || list.size() == 0 || list.isEmpty()) {
					 * errorList
					 * .add("This is a blank file, Please select a valid file."
					 * ); uiModel.addAttribute(Constants.ERROR_LIST, errorList);
					 * uiModel.addAttribute(Constants.FILE_NAME, fileName);
					 * logger.info(
					 * "------UploadCATicketController uploadExcelResources method end------"
					 * ); return "uploadresources/uploadresources"; }
					 */

				} catch (Exception e) {
					logger.error("Upload Exception" + e);
				}
				// ********//
				// System.out.println(" Resource List Size >" + list.size());
				// int count = 1;
				if (list != null && !list.isEmpty() && list.size() > 0) {
					System.out.println("Reading file");
					// int i = 2;
					int count = 0, count1 = 1;
					for (CATicketExcelMapping caTicketExcelMapping : list) {

						System.out.println("Ticket No is: "
								+ caTicketExcelMapping.getCaTicketNo());

						Date creationDate = caTicketExcelMapping
								.getCreationDate();
						Date updatedDate = caTicketExcelMapping
								.getUpdatedDate();
						// Date solutionDevelopedDate = caTicketExcelMapping
						// .getSolutionDevelopedDate();
						// Date closedPendingDate = caTicketExcelMapping
						// .getClosedPendingdate();
						String priority = "", unitName = "", landscapeName = "", moduleName = "", recfId = "", description = "", parentTicketNumber = "", reopenFrequency = "", rootCauseName = "";

						BigInteger caticketNumber = null;
						if (caTicketExcelMapping.getCaTicketNo() != null
								&& !caTicketExcelMapping.getCaTicketNo()
										.equals("")) {
							caticketNumber = new BigInteger(
									caTicketExcelMapping.getCaTicketNo().trim());
						}

						if (caTicketExcelMapping.getPriority() != null
								&& !caTicketExcelMapping.getPriority().equals(
										"")) {
							priority = caTicketExcelMapping.getPriority()
									.trim();
						}
						if (caTicketExcelMapping.getUnit() != null
								&& !caTicketExcelMapping.getUnit().equals("")) {
							unitName = caTicketExcelMapping.getUnit().trim();
						}
						if (caTicketExcelMapping.getLandscape() != null
								&& !caTicketExcelMapping.getLandscape().equals(
										"")) {
							landscapeName = caTicketExcelMapping.getLandscape()
									.trim();
						}
						if (caTicketExcelMapping.getModule() != null
								&& !caTicketExcelMapping.getModule().equals("")) {
							moduleName = caTicketExcelMapping.getModule()
									.trim();
						}

						if (moduleName.equals("") || moduleName == null) {
							moduleName = caTicketExcelMapping
									.getGroupAssigned().trim();
						}
						if (caTicketExcelMapping.getRecfId() != null
								&& !caTicketExcelMapping.getRecfId().equals("")) {
							recfId = caTicketExcelMapping.getRecfId().trim();
						}
						if (caTicketExcelMapping.getDescription() != null
								&& !caTicketExcelMapping.getDescription()
										.equals("")) {
							description = caTicketExcelMapping.getDescription()
									.trim();
						}
						if (caTicketExcelMapping.getParentTicketNumber() != null
								&& !caTicketExcelMapping
										.getParentTicketNumber().equals("")) {
							parentTicketNumber = caTicketExcelMapping
									.getParentTicketNumber().trim();
						}
						if (caTicketExcelMapping.getReopenFrequency() != null
								&& !caTicketExcelMapping.getReopenFrequency()
										.equals("")) {
							reopenFrequency = caTicketExcelMapping
									.getReopenFrequency().trim();
						}
						if (caTicketExcelMapping.getRootCause() != null
								&& !caTicketExcelMapping.getRootCause().equals(
										"")) {
							rootCauseName = caTicketExcelMapping.getRootCause()
									.trim();
						}

						Unit unit = uploadCATicketService
								.getUnitByUnitName(unitName);
						Region region = null;
						if (unit != null) {
							region = uploadCATicketService
									.findRegionByUnit(unit.getId());
						}
						Landscape landscape = uploadCATicketService
								.getLandscapeByName(landscapeName);

						Project module = uploadCATicketService
								.getModuleByModuleName(moduleName);

						Resource resource = uploadCATicketService
								.getEmployeeByRECFId(recfId);

						RootCause rootCause = uploadCATicketService
								.getRootCauseByRootCauseName(rootCauseName);

						boolean isAlreadyExist = caTicketService
								.isTicketAlreadyExist(caticketNumber);

						boolean isParentTicketNumberInteger = RMSUtil
								.isInteger(parentTicketNumber, 10);

						boolean isCATicketNumberInteger = RMSUtil
								.isInteger(caTicketExcelMapping.getCaTicketNo()
										.trim(), 10);

						boolean isReopenFreqNumberInteger = RMSUtil.isInteger(
								reopenFrequency, 10);

						if (resource == null) {
							if (module != null) {
								resource = module.getOffshoreDelMgr();
							}
						}

						if (resource != null && module != null
								&& creationDate != null /*
														 * && unit != null &&
														 * region != null
														 */&& !isAlreadyExist
								&& isParentTicketNumberInteger
								&& isCATicketNumberInteger
								&& isReopenFreqNumberInteger) {
							CATicket caTicket = new CATicket();
							caTicket.setCaTicketNo(caticketNumber);
							caTicket.setCreationDate(creationDate);
							caTicket.setUpdatedDate(updatedDate);
							// caTicket.setSolutiondevelopedDate(solutionDevelopedDate);
							// caTicket.setClosePendingCustomerApprovalDate(closedPendingDate);
							// if (closedPendingDate != null) {
							// caTicket.setCustomerApprovalFlag("Yes");
							// caTicket.setSolutionAcceptedFlag("N.A.");
							// caTicket.setSolutionReadyForReview("N.A.");
							// } else {
							// caTicket.setCustomerApprovalFlag("No");
							// caTicket.setSolutionAcceptedFlag("No");
							// caTicket.setSolutionReadyForReview("No");
							// }

							// if (solutionDevelopedDate != null) {
							// caTicket.setSolutionDevelopedFlag("Yes");
							// caTicket.setReqCompleteFlag("N.A.");
							// caTicket.setAnalysisCompleteFlag("N.A.");
							// } else {
							// caTicket.setSolutionDevelopedFlag("No");
							// caTicket.setReqCompleteFlag("No");
							// caTicket.setAnalysisCompleteFlag("No");
							// }
							caTicket.setPriority(priority);
							caTicket.setUnitId(unit);
							caTicket.setRegion(region);
							caTicket.setLandscapeId(landscape);
							caTicket.setModuleId(module);
							caTicket.setAssigneeId(resource);
							int irmId = resource.getCurrentReportingManager()
									.getEmployeeId();
							Resource resource2 = new Resource();
							resource2.setEmployeeId(irmId);
							caTicket.setReviewer(resource2);
							caTicket.setRootCause(rootCause);
							caTicket.setDescription(description);
							caTicket.setParentTicketNo(Integer
									.parseInt(parentTicketNumber));
							caTicket.setReopenFrequency(Integer
									.parseInt(reopenFrequency));
							caTicket.setSolutionReadyForReview("No");
							caTicket.setProblemManagementFlag("No");
							caTicket.setDefectStatusFlag("No");
							caTicket.setReworkStatusFlag("No");
							caTicket.setT3StatusFlag("No");
							caTicket.setCropStatusFlag("No");
							caTicket.setJustifiedProblemManagement("No");
							caTicket.setGroupName("Tier 2");
							caTicket.setAffectedEndUserId(caTicketExcelMapping
									.getAffectedEndUserId());
							caTicket.setAffectedEndUserName(caTicketExcelMapping
									.getAffectedEndUserName());
							caTicket.setUpdatedBy(UserUtil.getCurrentResource().getUsername());
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
									"yyyy/MM/dd hh:mm:ss");
							Calendar calobj = Calendar.getInstance();
							String currentDate = simpleDateFormat.format(calobj.getTime());
							Date now = simpleDateFormat.parse(currentDate);
							caTicket.setUpdatedDate(now);
							Serializable id = caTicketService.save(caTicket);
							if (id != null) {
								/************ Mail Functionality ****************/
										subjectFlag = "Ticket_Create";
										confgId = Integer.parseInt(Constants
												.getProperty("Ticket_Creation"));
									List<MailConfiguration> mailConfg = mailConfgService
											.findByProjectId(module.getId(),
													confgId);

									if (mailConfg != null
											&& mailConfg.size() > 0) {
										Resource assignee = resourceService.find(resource.getEmployeeId());
										// IRM is reviewer if uploading ticket
										Resource reviewer = resourceService.find(resource.getCurrentReportingManager()
												.getEmployeeId());
										String[] assigneeEmail = new String[2];
										assigneeEmail[0] = assignee.getEmailId();
										String[] ccMailID = new String[2];
										String reviewerEmail = reviewer.getEmailId();
										String IRMEmail = assignee.getCurrentReportingManager()
												.getEmailId();
										ccMailID[0] = reviewerEmail;
										// ccMailID[1] = IRMEmail;
										Map<String, Object> model = new HashMap<String, Object>();
										resourceHelper.setEmailContentforCATicket(model, caTicket,
												null, assigneeEmail, ccMailID, subjectFlag,
												methodName);

										emailHelper.sendEmailCATicket(model, assigneeEmail,
												ccMailID, mailConfg, caTicket);
									}
							}
							count = count + 1;
							System.out.println("Count: " + count);
						} else {
							System.out.println("Unit: " + unit + " landscape: "
									+ landscape + " module: " + module
									+ " resource: " + resource + " rootCause: "
									+ rootCause);
							count1 = count1 + 1;
							CATicketDiscrepencies caTicketDiscrepencies = new CATicketDiscrepencies();
							caTicketDiscrepencies
									.setCaTicketNo(caTicketExcelMapping
											.getCaTicketNo().trim());
							if (caTicketExcelMapping.getRecfId() != null
									&& !caTicketExcelMapping.getRecfId()
											.equals("")) {
								caTicketDiscrepencies
										.setRecfId(caTicketExcelMapping
												.getRecfId().trim());
							}
							caTicketDiscrepencies.setCreationDate(creationDate);
							caTicketDiscrepencies.setUpdatedDate(updatedDate);
							// caTicketDiscrepencies
							// .setSolutiondevelopedDate(solutionDevelopedDate);
							// caTicketDiscrepencies
							// .setClosePendingCustomerApprovalDate(closedPendingDate);
							caTicketDiscrepencies.setPriority(priority);
							caTicketDiscrepencies.setUnitId(unit);
							caTicketDiscrepencies.setRegion(region);
							caTicketDiscrepencies.setLandscapeId(landscape);
							caTicketDiscrepencies.setModuleId(module);
							caTicketDiscrepencies.setAssigneeId(resource);

							Resource resource2 = null;
							if (resource != null) {
								int irmId = resource
										.getCurrentReportingManager()
										.getEmployeeId();
								resource2 = new Resource();
								resource2.setEmployeeId(irmId);

							}
							caTicketDiscrepencies.setReviewer(resource2);
							caTicketDiscrepencies.setRootCause(rootCause);
							caTicketDiscrepencies.setDescription(description);
							caTicketDiscrepencies
									.setParentTicketNo(parentTicketNumber);
							caTicketDiscrepencies
									.setReopenFrequency(reopenFrequency);
							uploadCATicketService
									.saveDiscrepencies(caTicketDiscrepencies);
							System.out.println("Count1: " + count1);
						}

						// boolean isSuccess = caTicketService.save(caTicket);
						// System.out.println("Saved: " + isSuccess);
					}
				} else {
					errorList
							.add("This is a blank file, Please select a valid file.");
					uiModel.addAttribute(Constants.ERROR_LIST, errorList);
					uiModel.addAttribute(Constants.FILE_NAME, fileName);
					logger.info("------UploadCATicketController uploadExcelResources method end------");
					return "upload";
				}
			}
			logger.info("------UploadCATicketController uploadExcelResources method end------");
		} catch (NumberFormatException e) {
			logger.error("Exception occured in uploadExcelResources method of uploadResource controller:"
					+ e);
			throw e;
		} catch (Exception e) {
			logger.error("Exception occured in uploadExcelResources method of uploadResource controller:"
					+ e);
			throw e;
		}
		if (errorList.size() > 0) {
			uiModel.addAttribute(Constants.ERROR_LIST, errorList);
			return "upload";
		} else {
			return "redirect:/caticket/upload";
		}
	}

	@RequestMapping(value = "/discrepency", method = RequestMethod.GET)
	public String viewDiscrepencyTicket(HttpServletRequest request,
			Model uiModel) {

		List<CATicketDiscrepencies> discrepencyList = uploadCATicketService
				.findAllDiscrepencyTickets();
		System.out.println("CA Ticket list size: " + discrepencyList.size());
		uiModel.addAttribute(Constants.DISCREPENCYTICKETS, discrepencyList);

		return "caticket/discrepency";
	}

	@RequestMapping(value = "/getDiscrepenciesTicketById/{id}", method = RequestMethod.GET)
	public String getDiscrepenciesTicketById(@PathVariable("id") int id,
			HttpServletRequest request, Model uiModel) {

		try {
			CATicketDiscrepencies caTicketDiscrepencies = uploadCATicketService
					.findDiscrepenciesTicketById(id);

			List<CATicketProcess> processByModuleIdList = new ArrayList<CATicketProcess>();
			List<CATicketSubProcess> subprocessByProcessIdList = new ArrayList<CATicketSubProcess>();
			if (caTicketDiscrepencies.getModuleId() != null) {
				processByModuleIdList = caTicketService
						.findProcessByModuleId(caTicketDiscrepencies
								.getModuleId().getId());
			}

			uiModel.addAttribute(Constants.DISCREPENCYTICKETS,
					caTicketDiscrepencies);
			uiModel.addAttribute(Constants.PROJECTS,
					caTicketService.findModuleNameProjects());
			uiModel.addAttribute(Constants.RESOURCES,
					resourceService.findActiveResources());
			uiModel.addAttribute(Constants.LANDSCAPE,
					caTicketService.getAllLandscape());
			uiModel.addAttribute(Constants.UNITS, caTicketService.getAllUnit());
			uiModel.addAttribute(Constants.PROCESS, processByModuleIdList);
			uiModel.addAttribute(Constants.SUBPROCESS,
					subprocessByProcessIdList);
			uiModel.addAttribute(Constants.ROOTCAUSE,
					caTicketService.getAllRootCause());
			uiModel.addAttribute("caTicketForm", new CATicketForm());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "caticket/saveDiscrepency";
	}

	@RequestMapping(value = "/saveDiscrepencyTicket", method = RequestMethod.POST)
	public String saveDiscrepencyTicket(
			@ModelAttribute(value = "caticketForm") CATicketForm caticketform)
			throws Exception {
		System.out
				.println("In create form: " + caticketform.getDiscrepencyId());
		CATicket caTicket = null;
		try {

			if (null == caticketform) {
				throw new IllegalArgumentException(
						"CATicket Form Object Cannot be null");
			}

			if (caticketform.getProblemManagementFlag() != null) {
				if (caticketform.getProblemManagementFlag().equals("No")) {
					caticketform.setProcess(null);
					caticketform.setSubProcess(null);
					caticketform.setRootCause(null);
					caticketform.setZREQNo(null);
					caticketform.setParentTicketNo(null);
					caticketform.setComment("");
					caticketform.setJustifiedProblemManagement("No");

				}
			}

			caticketform.setT3StatusFlag("No");
			caticketform.setDefectStatusFlag("No");
			caticketform.setCropStatusFlag("No");
			caticketform.setReworkStatusFlag("No");

			System.out.println("Creation date: "
					+ caticketform.getCreationDate());
			// System.out.println("Employee Id: " +
			// caticketform.getDescription()
			// + " Assignee id: "
			// + caticketform.getAssigneeId().getEmployeeId());
			caTicket = caTicketService.saveCATicket(caticketform,
					caticketform.getDiscrepencyId());

			if (caTicket != null) {
				System.out.println("Successfull saved");

			} else {
				System.out.println("Failed saved");
			}

		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in create method of CATicketController:"
					+ runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in create method of CATicketController:"
					+ exception);
			throw exception;
		}
		logger.info("------CATicketController create method end------");

		return "redirect:discrepency";
	}

	@RequestMapping(value = "/getAllEmployee")
	@ResponseBody
	public ResponseEntity<String> getReviewer(
			@RequestParam(value = "employeeId") int employeeId) {
		logger.info("------ResourceController getResource method start------");
		List<Resource> resources = resourceService.findActiveResources();

		logger.info("------ResourceController validateReleaseDate method start------");
		return new ResponseEntity<String>(
				CATicket.resourseJsonArray(resources), HttpStatus.OK);
	}

	@RequestMapping(value = "/back")
	public String back(HttpServletRequest request, Model uiModel) {
		System.out.println("Back page");
		if (previousPage.equalsIgnoreCase("myTicketQueue")) {
			return "redirect:viewTicket";
		} else if (previousPage.equalsIgnoreCase("forMyReview")) {
			return "redirect:viewReviewTicket";
		} else {
			return "redirect:dashboard";
		}
	}
}
