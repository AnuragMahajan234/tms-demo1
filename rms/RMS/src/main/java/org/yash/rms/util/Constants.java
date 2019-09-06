package org.yash.rms.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.yash.rms.domain.RequestRequisitionSkill;

public class Constants {
	public static final String DATE_PATTERN = "MM/dd/yyyy";
	public static final String DATE_PATTERN_NEW = "yyyy-MM-dd hh:mm:ss aa";
	public static final String DATE_PATTERN_2 = "MM/d/yyyy";
	public static final String DATE_PATTERN_3 = "E MM/d/yyyy";
	public static final String DATE_PATTERN_4 = "dd-MMM-yyyy";
	public static final String DATE_PATTERN_5 = "yyyy-MM-dd";
	public static final String DATE_PATTERN_6 = "dd-MMM-yyyy hh:mm:ss";
	public static final String RMG_PDL_EMAIL = "RMG-NonSap@yash.com";
	public static final String RMG_ERP_PDL_EMAIL = "RMG-ERP@yash.com";
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN_2);
	public static final String RESOURCE_TYPE_NEW ="New";
	public static final String RESOURCE_STATUS_ALLOK ="AllOk";
	public static final String RESOURCE_TYPE_OLD ="Old";
	public static final String RESOURCE_TYPE_EXISTING ="Existing";
			
	
	public static final SimpleDateFormat DF_READONLY_USER_ACTIVITY = new SimpleDateFormat(DATE_PATTERN_3);
	
	public static final SimpleDateFormat DATE_FORMAT_NEW = new SimpleDateFormat(DATE_PATTERN_4);
	
	// Date format for Reports
	
	public static final SimpleDateFormat DATE_FORMAT_REPORTS = new SimpleDateFormat(DATE_PATTERN_4);

	// Image
	public static byte[] IMAGE_UPLOAD;
	
	public static final String SUBJECT = "subject";
	public static final String FILE_NAME = "fileName";
	public static final String USER_NAME = "userName";
	public static final String USER_EMP_ID = "userEmpId";
	public static final String EMAIL_ADDRESS_LIST = "emailAddressList";
	public static final String CURRENT_DATE = "currentDate";

	/** User Activity */
	public static final String ACTIVITY_LIST = "activityList";
	public static final String WEEK_START_DATE = "weekStartDate";
	public static final String WEEK_END_DATE = "weekEndDate";
	public static final String USER_FIRST_NAME = "firstName";
	public static final String USER_LAST_NAME = "lastName";
	public static final String TIMESHEET_STATUS = "status";
	public static final String EMAIL_ID = "toEmailId";
	
	public static final String TIMESHEET_FTL = "timesheet.ftl";
	public static final String TIMESHEET_USER_FTL = "timesheetUser.ftl";
	public static final String ACK_CLOSING_ALLOCATION_FTL = "CloseAllocationTemplate.ftl";
	public static final String UNFILLED_TIMESHEET_FTL = "UnfilledTimesheetTemplate.ftl";
	public static final String TIMESHEET_APPROVAL_REJECT_FTL = "TimeSheetApprovalRejecttionTemplate.ftl";
	public static final String LOAN_TRANSFER_FTL = "LoanTransferAcknowlegment.ftl";
	
	public static String TIMESHEET_SUBJECT = Constants.getProperty("TIMESHEET_SUBJECT");
	public static String UNFILLED_TIMESHEET_SUBJECT = Constants.getProperty("UNFILLED_TIMESHEET_SUBJECT");
	public static String TIMESHEET_APPROVAL_SUBJECT = Constants.getProperty("TIMESHEET_APPROVAL_SUBJECT");
	public static String TIMESHEET_REJECTION_SUBJECT = Constants.getProperty("TIMESHEET_REJECTION_SUBJECT");
	public static String LOAN_TRANSFER_ACK_SUBJECT = Constants.getProperty("LOAN_TRANSFER_ACK_SUBJECT");
	public static final String ACK_CLOSING_ALLOCATION_SUBJECT = Constants.getProperty("ACK_CLOSE_ALLOCATION_PROFILE_SUBJECT");
	
	public static final String CC_EMAIL_ID = "ccEmailId";
	public static final String RRF_CLOSURE_MAIL = "RRF_CLOSURE_MAIL";
	public static final String SUBMITTED_TO_AM_TEAM = "SUBMITTED_TO_AM_TEAM";
	public static final String FORWARD_RRF = "FORWARD_RRF";
	public static final Long TOKEN_INCREAENT_ORDER = 10000L;
	// added
	public static final String BCC_EMAIL_ID = "bccEmailId";

	// added RM1, RM2 emailids constant Start
	public static final String MANAGER_IRM_EMAIL_ID = "ManagerIRMEmailId";

	public static final String MANAGER_SRM_EMAIL_ID = "ManagerSRMEmailId";

	public static final String IRM_EMAIL_ID = "IRMEmailId";

	public static final String SRM_EMAIL_ID = "SRMEmailId";
	// added RM1, RM2 emailids constant End

	public static final String REMARKS = "remarks";
	public static final String TIMESHEET_MAIL_DENY_STATUS = "Approve/Reject already done for this timesheet";

	/** Resource */
	public static final String APPROVAL_URL = "approvalURL";
//added for #3007 starts
	public static final String APPROVEIMAGE ="approveImageUrl";
	public static final String REJECTTIMESHEET ="rejectImageUrl";
	//added for #3007 ends
	public static final String DENY_URL = "denyURL";
	public static final String USER_PROFILE_FTL = "userProfile.ftl";
	public static String USER_PROFILE_SUBJECT = Constants
			.getProperty("USER_PROFILE_SUBJECT");
	public static final String RESOURCE_URL = "resourceURL";

	public static final String RESOURCE_ROLE = "ROLE_USER";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_DEL_MANAGER = "ROLE_DEL_MANAGER";
	public static final String ROLE_MANAGER = "ROLE_MANAGER";
	public static final String ROLE_BEHALF_MANAGER = "ROLE_BEHALF_MANAGER";
	public static final String ROLE_SEPG_USER = "ROLE_SEPG_USER";
	public static final String ROLE_HR = "ROLE_HR";

	public static final String ROLE_BG_ADMIN = "ROLE_BG_ADMIN";
	public static final String RESOURCEDATA = "resourceData";
	public static final String ACCOUNT_MANAGERS = "accountManagers";
	public static final String RESOURCE_UNIT = "resourceUnit";
	public static final String ALL_UNITS = "allUnits";

	/** Resource Allocation **/
	public static final String ALLOCATIONTYPE = "allocationtypes";
	public static final String OWNERSHIPS = "ownerships";
	public static final String PROJECTS = "projects";
	public static final String RATES = "rates";
	public static final String ELIGIBLE_RESOURCES = "eligibleResources";
	public static final String TEAMS = "teams";
	public static final String RESOURCE_NAME = "resourceName";
	public static final String SELECTED_RESOURCE_RM1 = "resourceRM1";
	public static final String SELECTED_RESOURCE_RM2 = "resourceRM2";//added by shreya
	public static final String SELECTED_RESOURCE_PARENT_BU = "resourceParentBu";
	public static final String SELECTED_RESOURCE_CURRENT_BU = "resourceCurrentBu";
	public static final String ALLOCATION_LIST = "allocationList";
	public static final String RESOURCE_COMPETANCY = "resourceCompetancy";
	public static final String RESOURCE_JOINING_DATE = "joiningDate";
	public static final String RESOURCE_AllOC_START_DATE = "allocStartDate";
	public static final String RESOURCE_AllOC_END_DATE = "allocEndDate";
	

	// Resource Controller
	public static final String RESOURCES = "resources";
	public static final String RESOURCES_RM = "resourcesRM";
	public static final String DESIGNATION = "designation";
	public static final String BGS = "bgs";
	public static final String LOCATIONS = "location";
	public static final String VISA = "visa";
	public static final String OWNER = "ownership";
	public static final String SECONDRY_SKILLS = "secondrySkills";

	public static final String ACTIVITYS = "activitys";
	public static final String BILLING_SCALES = "billingScales";
	public static final String ALL_CURRENCY = "currencys";
	public static final String ALL_CUSTOMERS = "customers";
	public static final String DESGINATIONS = "designationses";
	public static final String ENGAGEMENTMODEL = "engagementmodels";
	public static final String INVOICES = "invoicebys";
	public static final String LOCATION = "locations";
	public static final String MODULES = "modules";
	public static final String OWNERSHIP = "ownerships";
	public static final String EMPLOYEECATEGORY = "employeecategory";
	public static final String COMPETENCY = "competency";
	public static final String MAX_PAGES = "maxPages";
	// EDIT PROFILE CONTROLLER
	public static final String ALLOCATION_TYPE = "allocationtype";
	public static final String USER_RESUME_FILE_NAME = "userResumeFileName";
	public static final String USER_PROFILE_PRIMARY_SKILL_LIST = "userProfilePrimarySkillList";
	public static final String USER_PROFILE_SECONDARY_SKILL_LIST = "userProfileSecondarySkillList";
	public static final String CURRENT_LOGGED_IN_RESOURCE = "currentLoggedinResource";
	public static final String PRIMARY_SKILLS = "primarySkills";
	public static final String SECONDARY_SKILLS = "secondarySkills";
	public static final String RATING = "rating";
	public static final String PREFERRED_LOCATION="preferredLocation";
	
	// GRADE CONTROLLER
	public static final String GRADE = "grade";
	// EVENT CONTROLLER
	public static final String EVENT = "event";
	// PROJECTACTIVITY CONTROLLER
	public static final String PROJECT_ACTIVITY = "projectActivity";
	// Team Controller
	public static final String TEAM = "team";

	// ORGHIERARCHY CONTROLLER
	public static final String ORG_LIST = "orglist";
	public static final String ORG_MAP = "orgMap";
	public static final String MOVE_ID = "moveid";
	public static final String MOVE_NAME = "movename";
	public static final String OLD_PARENT = "oldParent";

	// PROJECT CATEGORY CONTROLLER
	public static final String PROJECT_CATEGORY = "projectcategorys";
	// PROJECT CONTROLLER
	public static final String INVOICES_BY = "invoicesBy";
	public static final String PROJECT_METHODOLOGYS = "projectmethodologys";
	public static final String BUS = "bus";
	public static final String OFFSHORE_DEL_MGR = "offshoreDelMgr";
	public static final String DEFAULNONSAPPROJECT = "NonSAP-Bench-BG4-BU 3-8";
    //DEFAULT PROJECTY CONTROLLER
	public static final String DEFFAULT_PROJECT_LIST = "defaultprojects";
	// RESOURCE ALLOCATION CONTROLLER
	public static final String RESOURCEALLOCATIONS = "RESOURCEALLOCATIONS";
	public static final String RESOURCE_ALLOCATION_ID = "resourceAllocationId";

	// SKILLS CONTROLLER
	public static final String SKILLS = "skillses";

	// TIME HOUR ENTRY CONTROLLER
	public static final String TIME_HRS = "timehrses";
	public static final String TIME_HRS_DATE_FORMAT = "timehrs_weekendingdate_date_format";
	public static final String FIND = "find";
	public static final String APPROVE_TIMESHEET_FROM_MAIL = "approveTimesheetFromMail";
	public static final String RES_ALLOC_ID = "resourceAllocId";
	public static final String RESOURCE_ID = "resourceId";
	public static final String REPORTED_HRS = "reportedHrs";
	public static final String USER_ACTIVITY_ID = "userActivityId";
	public static final String TIME_SHEET_STATUS = "timeSheetStatus";
	public static final String CODE = "code";
	public static final String FULL_URL = "fullUrl";

	// UPLOAD RESOURCE CONTROLLER
	public static final String ERROR_LIST = "errorList";

	// USER ACTIVITY CONTROLLER
	public static final String USER_ACTIVITY_VIEWS = "useractivityViews";
	public static final String ACTIVITIES = "activities";
	public static final String RESOURCE_ALLOCATION = "resourceAllocation";
	public static final String TODAY = "today";
	public static final String TIME_SHEET_COMMENT_OPTIONAL = "timesheetCommentOptional";
	public static final String USER_ACTIVITYS = "useractivitys";
	public static final String READ_ONLY_USER_ACTIVITIES = "readonlyuseractivities";
	public static final String EMP_NAME = "employeeName";
	public static final String EMP_DESINGNATION = "jobTitle";
	public static final String EMP_ID = "employeeId";
	public static final String D1_HEADER = "d1Header";
	public static final String D2_HEADER = "d2Header";
	public static final String D3_HEADER = "d3Header";
	public static final String D4_HEADER = "d4Header";
	public static final String D5_HEADER = "d5Header";
	public static final String D6_HEADER = "d6Header";
	public static final String D7_HEADER = "d7Header";
	public static final String PROJECT_NAME = "projectName";
	public static final String PROJECT_ID = "projectId";
	public static final String USER_ACTIVITY = "useractivity";
	public static final String TOTAL_VACATION_HOURS = "totalVacationHours";
	public static final String TOTAL_PRODUCTIVE_HOURS = "totalProductiveHours";
	public static final String TOTAL_NON_PRODUCTIVE_HOURS = "totalNonProductiveHours";
	public static final String ITEM_ID = "itemId";
	public static final String LIST = "list";
	public static final String USER_ACTIVITIES = "useractivities";
	public static final String ALL_RELASENOTES = "releasenotes";
	
	// VISA CONTROLLER
	public static final String VISAS = "visas";

	// Vacation Activity to be used for calculating vacation hours
	public static final String VACATION_ACTIVITY_TEXT = "LEAVE";

	public static final String SUBMIT = "submit";

	// EMAILS
	public static String TO_MAIL = Constants.getProperty("TO_MAIL");
	public static String CC_MAIL = Constants.getProperty("CC_MAIL");
	public static String BCC_MAIL = Constants.getProperty("BCC_MAIL");
	public static String FROM_MAIL = Constants.getProperty("FROM_MAIL");
	public static String CENTRAL_HR = Constants.getProperty("CENTRAL_HR");

	// NEW RESOURCE MAIL
	public static String FROM_MAIL_NEW_RESOURCE = Constants
			.getProperty("FROM_MAIL_NEW_RESOURCE");
	public static final Object NEW_RESOURCE_FTL = "NewResourceMail.ftl";

	// Enviornment variable
	public static String ENV_VARIABLE = Constants.getPropertyVariable(
			"/spring-configuration/rms.properties", "IafConfigSuffix");
	public static String USER_PROFILE = "editProfile";
	public static String ACTIVE_PROJECTS_OF_BU = "allProjects";
	public static String COMMENTS = "comments";
	public static String INDENTER = "sender";
	public static String REQ_LIST = "reqList";

	// Task #816
	public static final String LOAN_TRANSFER_FTL_HR = "LoanTransferAcknowlegmentHR.ftl";
	public static final String HR_NAME = "hrName";
	public static final String BGH_NAME = "bghName";
	public static final String BUH_NAME = "buhName";

	// Task Mail Configuration
	public static final String PROJECT_BUNAME = "projectBU";

	// Add new
	public static final String SEPG_PHASES = "sepgPhases";
	public static final String SEPG = "SEPG";
	public static final String SEPG_GET_LIST = "sepgGetList";
	public static final String ENGAGEMENTMODAL_FROM = "engagementModalFrom";
	public static final String ENGAGEMENTMODAL_To = "engagementModalTo";
	public static final String ACTIVITY_FROM = "activityFrom";
	public static final String ACTIVITY_TO = "activityTo";
	public static final String ENGAGEMENTMODAL_COUNT = "engagementModalCount";
	public static final String ACTIVITY_COUNT = "activityCount";
	// Request

	public static final String REQ_MAIL_LIST = "requestMailList";
	public static final String MAIL_LIST = "mailingList";
	public static final Object RESOURCE_REQUEST_HTML = "ResourceRequest.html";
	public static final Object RRF_CLOSURE_MAIL_HTML = "RRFClosureMail.html";
	public static final Object RESOURCE_REQUEST_UPDATION_HTML = "ResourceRequestUpdation.html";
	public static final Object Project_GoingTo_Close_HTML = "ProjectGoingToClose.html";
	public static final Object Project_GoingTo_Closed_HTML = "ProejectGoingtobeClosed.html";
	public static final String RESOURCE_STATUS_LIST = "resourceStatuslist";
	public static final Object RESOURCE_STATUS_UPDATION_HTML = "ResourceStatusUpdation.html";
	public static final String NEW_RESOURCE_STATUS_LIST = "resourceStatusNewlist";
	public static final Object RESOURCE_REQUEST_FTL = "ResourceRequest.ftl";
	public static final String REQUESTED_SKILLS = "skillRequired";
	public static final String REQUESTED_RESOURCE = "resourceRequired";
	public static final String OPEN = "Open";
	public static final String CLOSED = "Closed";
	public static final String FULFILLED = "Fulfilled";

	// Report
	public static final String RepotList = "reportList";
	public static final String Default = "Default";
	public static final String CUSTOM = "Custom";

	// ReportsController
	public static final String REPORT_PATH = Constants
			.getProperty("REPORT_PATH");
	public static final String REPORT_PATH_SEPG = Constants
			.getProperty("REPORT_PATH_SEPG");
	public static final String REPORT_PATH_CMMI = Constants
			.getProperty("REPORT_PATH_CMMI");
	// CA Ticket
	public static final String DISCREPENCYTICKETS = "discTickets";
	public static final String CATICKETS = "catickets";
	public static final String PROCESS = "process";
	public static final String SUBPROCESS = "subprocess";
	public static final String OPENDAYS = "opendays";
	public static final String LANDSCAPE = "landscapes";
	public static final String ROOTCAUSE = "rootcause";
	public static final String REGIONS = "regions";
	public static final String UNITS = "units";
	public static final String REASON_FOR_HOPPING = "reasonForHopping";
	public static final String REASON_FOR_REOPEN = "reasonForReopen";
	public static final String REASON_FOR_SLA_MISSED = "reasonForSLAMissed";
	public static final String CATicketMail = "caticketMail";
	public static final Object CA_TICKET_CREATE_FTL = "caTicketCreate.ftl";
	public static final String REGIONNAMES = "regionNames";
	public static final String ASSIGNEE_NAME = "assigneeName";
	public static final String REVIEWER_NAME = "reviewerName";
	public static final String UNIT_NAME = "unitName";
	public static final String REGION_NAME = "regionName";
	public static final String LANDSCAPE_NAME = "landscapeName";
	public static final String AGING = "aging";
	public static final String SOLUTION_READY_FOR_REVIEW = "solutionReadyForReview";
	public static final Object CA_TICKET_SOLUTION_READY_FOR_REVIEW_FTL = "solutionReadyForReview.ftl";
	public static final Object CA_TICKET_EDIT_FTL = "caTicketEdit.ftl";
	public static final String PROJECT_NAME_UPDATED = "projectNameUpdated";
	public static final String ASSIGNEE_NAME_UPDATED = "assigneeNameUpdated";
	public static final String REVIEWER_NAME_UPDATED = "reviewerNameUpdated";
	public static final String UNIT_NAME_UPDATED = "unitNameUpdated";
	public static final String REGION_NAME_UPDATED = "regionNameUpdated";
	public static final String LANDSCAPE_NAME_UPDATED = "landscapeNameUpdated";
	public static final String AGING_UPDATED = "agingUpdated";
	public static final String SOLUTION_READY_FOR_REVIEW_UPDATED = "solutionReadyForReviewUpdated";
	public static final String DESCRIPTION = "description";
	public static final String DESCRIPTION_UPDATED = "descriptionUpdated";
	public static final String SEPG_ID = "sepgID";
	public static final String CUSTOM_ACTIVITIES_IN_TIMESHEET = "customIDs";
	public static final String RESOURCE_INTERVIEW_TEMPLATE_HTML="ResourceInterviewTemplate.html";
	//RMReport
	public static final String Release_Date_IS_NULL = "Release_Date IS NULL";
	public static final String Release_Date_IS_Not_NULL = "Release_Date IS NOT NULL";
	public static final String Release_Date_IS_Not_NULL_OR_Null = "(Release_Date IS NULL OR Release_Date IS NOT NULL)";
	public static final String Future_Release_Date = "Release_Date>CURDATE()";
	public static final String Is_Released = "Release_Date<CURDATE()";
   //End RMReport
	
	// PWRRepot

	public static final String BILLABLE_FULL_TIME = "Billable (Full Time (FTE))";
	public static final String BILLABLE_PARTIA_SHAREDPOOL_FIXBIDPROJECTS = "Billable (Partial / Shared Pool / Fix Bid Projects)";

	public static final String NONBILLABLE_ABSCONDING = "Non-Billable (Absconding)";
	public static final String NONBILLABLE_ACCOUNT_MANAGEMENT = "Non-Billable (Account Management)";
	public static final String NONBILLABLE_BLOCKED = "Non-Billable (Blocked)";
	public static final String NONBILLABLE_CONTINGENT = "Non-Billable (Contingent)";
	public static final String NONBILLABLE_DELIVERY_MANAGEMENT = "Non-Billable (Delivery Management)";
	public static final String NONBILLABLE_INSIDESALE = "Non-Billable (InsideSale)";
	public static final String NONBILLABLE_INVESTMENT = "Non-Billable (Investment)";
	public static final String NONBILLABLE_LONGLEAVE = "Non-Billable (Long-leave)";

	public static final String NONBILLABLE_MANAGEMENT = "Non-Billable (Management)";
	public static final String NONBILLABLE_OPERATIONS = "Non-Billable (Operations)";
	public static final String NONBILLABLE_PMO = "Non-Billable (PMO)";
	public static final String NONBILLABLE_PRESALE = "Non-Billable (PreSale)";
	public static final String NONBILLABLE_SALES = "Non-Billable (Sales)";
	public static final String NONBILLABLE_SHADOW = "Non-Billable (Shadow)";
	public static final String NONBILLABLE_TRAINEE = "Non-Billable (Trainee)";
	public static final String NONBILLABLE_TRANSITION = "Non-Billable (Transition)";
	public static final String NONBILLABLE_UNALLOCATED = "Non-Billable (Unallocated)";
	public static final String NONBILLABLE_EXITRELEASE = "OUTBOUND (Exit/Release)";
	public static final String PIP = "PIP";
	public static final String PIP_STATUS = "pipStatus";
	// End PWRReport
	// Allocation Changes
	public static final String BILLABLE = "Billable";
	public static final String BILLABLE_PARTIAL = "Partial Billable";
	public static final String NON_BILLABLE = "Non-Billable";
	public static final String NEW_HIRED_RESOURCE = "New Hiring";
	public static final String INVESTMENT = "Investment";

	public static final String NON_BILLABLE_TO_BILLABLE = "NonBillable To Billable";
	public static final String BILLABLE_TO_NON_BILLABLE = "Billable To NonBillable";
	public static final String PARTIAL_TO_NON_BILLABLE = "Partial Billable To NonBillable";
	public static final String BILLABLE_TO_PARTIAL_BILLABLE = "Billable To Partial Billable";
	public static final String PARTIAL_BILLABLE_TO_BILLABLE = "Partial Billable To Billable";
	public static final String NON_BILLABLE_TO_PARTIAL_BILLABLE = "NonBillable To Partial Billable";
	
	public static final String INVESTMENT_TO_NON_BILLABLE = "Investment To NonBillable";
	public static final String NON_BILLABLE_TO_INVESTMENT = "NonBillable To Investment";
	public static final String INVESTMENT_TO_BILLABLE = "Investment To Billable";
	public static final String BILLABLE_TO_INVESTMENT = "Billable To Investment";
	public static final String INVESTMENT_TO_PARTIAL_BILLABLE = "Investment To Partial Billable";
	public static final String PARTIAL_BILLABLE_TO_INVESTMENT = "Partial Billable To Investment";
	public static final String EMPTY = "";
	public static final String ALLOCATIONREMARKS_FOR_BENCH_RESOURCE = "As allocation Set process of 30 Days, Hence Moving to Resource Pool";

	//Active and Inactive status
    public static final String ACTIVE = "1";
    public static final String INACTIVE = "0";
    public static final Integer ROLE_SEPG_USER_ID = 3;
    public static final Integer REPORT_ACCESS = 1;
    public static final Integer REPORT_ACCESS3 = 3;
    
    public static final String FEEDBACK_PREFIX="<b>Feedback given by: ";
    public static final String FEEDBACK_DATE="Date";
	public static final String SUB_MODULES = "submodules";
	public static final String EMP_YASH_ID = "employeeYashId";
	
	public static final String INTERNAL_USER = "Internal";
	public static final String EXTERNAL_USER = "External";
	public static final String REQUEST_RESOURCE_STATUS_LIST = "requestRequisitionResourceStatusList";;
	public static final String STAFFING = "Staffing";
	public static final String DATE = "Date";
	public static final String INTEGER = "Integer";
	public static final String BOOLEAN = "Boolean";
	public static final String PROCESSSTATUS = "processStatus";
	public static final String RECORDSTATUS = "recordStatus";
	public static final String PROCESSSTATUS1 = "processStatus1";
	public static final String PROCESSSTATUS2 = "processStatus2";
	public static final String ASC = "asc";
	public static final String DESC = "desc";	
	public static final String ALL = "All";

//scheduler
	
	public static final String SYSTEM = "SYSTEM";
	public static final String SYSTEM_UPDATE = "SYSTEM_UPDATE";
	public static final String SCHEDULAR_FOR_ALLOCATION_BLOCKED_STATUS= "0 0 14 * * ?";
	
	// constant for keyGenerator
	public static final String ASSIGNED = "assigned";
	public static final String UNASSIGNED = "unassigned";
	
	//Delete RRF Email
	public static final String DELETE_RRF = "DELETE_RRF";
	public static final Object RRF_DELETE_HTML = "ResourceRequestDelete.html";
	public static final String TOMAILIDSLIST="toMail";
	public static final String CCMAILIDSLIST="ccMail";
	public static final String SUBJECT_HEADING= "RRF Deleted For Request Requisition - ";
	public static final String CHILD_OBJECT = "CHILD_OBJECT";
	
	
	//New RRF Code constants - start
	public static final String IS_DELETED = "isDeleted";
	public static final String REQ_PROJ_ID = "projectId";
	public static final String SENDEMAILID = "sendEmailId";
	public static final String TONOTIFYID = "toNotifyId";
	public static final String PDLEMAILID = "pdlEmailId";
	public static final String RRFEMPLOYEEID = "employeeId";
	
	//New RRF Code constants - end
	
	
	//RRF resources status changed -start
	public static final Object RRF_RESOURCE_STATUS_MAIL_HTML = "RRFResourceStatusChangeMail.html";
	public static final String SUBJECT_HEADING_FOR_RESOURCE_STATUS = "Resource Status Update For Request Requisition - ";
	public static final String SUBJECT_HEADING_FOR_REJECTED_BY_POC = "Rejected By POC - ";
	public static final String SUBJECT_HEADING_FOR_SUBMITTED_TO_AM_TEAM = "Submitted To AM Team - ";
	public static final String SUBMITTEDTOAMTEAMSTATUS = "SUBMITTED TO AM TEAM";
	public static final String REJECTEDBYPOCSTATUS = "REJECTED BY POC";
	public static final String SUBMITTEDSTATUS = "SUBMITTED_TO_AM_TEAM";
	public static final String REJECTEDSTATUS = "REJECTED_BY_POC";
	//RRF resources status changed -end
	
	public static final String INTERVIEW_DATE_PATTERN = "[0-9]{1,2}-[a-zA-Z]{3}-[0-9]{4}";
	public static final String ALLOCATION_DATE_PATTERN = "[0-9]{1,2}-[a-zA-Z]{3}-[0-9]{4}";
	public static final String BGBU_NONSEP_BENCHPROJECT = getProperty("BGBU_NONSEP_BENCHPROJECT");
	public static final String NOT_AVAILABLE = "Not Available";
	public static final String BLOCKEDRESOURCE_SUBJECT = getProperty("BLOCKEDRESOURCE_SUBJECT");
	public static final String BLOCKEDRESOURCE_EMAILTEXT = getProperty("BLOCKEDRESOURCE_EMAILTEXT");
	public static final String NOBLOCKEDRESOURCE_EMAILTEXT = getProperty("NOBLOCKEDRESOURCE_EMAILTEXT");
	public static final String RESOURCE_BUUNITHEAD = getProperty("RESOURCE_BUUNITHEAD");
	public static final String BLOCKEDRESOURCE_TOEMAIL = getProperty("BLOCKEDRESOURCE_TOEMAIL");
	public static final String BLOCKEDRESOURCE_CCMAIL = getProperty("BLOCKEDRESOURCE_CCMAIL");
	public static final String BLOCKEDRESOURCE_BCCMAIL = getProperty("BLOCKEDRESOURCE_BCCMAIL");
	public static final String EMAIL_TEXT = "emailText";
	public static final String BLOCKEDRESOURCE_DAYS = getProperty("BLOCKEDRESOURCE_DAYS");
	public static final String COPYRRF = "Copy RRF";
	public static final String DELIVERYMANAGER = "DELIVERYMANAGER";
	public static final String MANAGER = "MANAGER";
	public static final String BUHEAD = "BUHEAD";
	
	
	//RRF CLOSING_PROJECT_RRF constants - Start
	public static final String CLOSING_PROJECT_RRF_SUBJECT = getProperty("CLOSING_PROJECT_RRF_SUBJECT");
	public static final String CLOSING_PROJECT_RRF_DAYS = getProperty("CLOSING_PROJECT_RRF_DAYS");
	public static final String CLOSING_PROJECT_RRF_CCMAIL = getProperty("CLOSING_PROJECT_RRF_CCMAIL");
	
	//RRF CLOSING_PROJECT_RRF constants - End
	
	//RRF Submit Resources constants - Start
	public static final String RRF_RESOURCE_SUBMIT_SUBJECT = "Profile Submission - ";
	//RRF Submit Resources constants - End
	
	//Requirement Area constants - Start
	public static final String NON_SAP = "NON_SAP";
	public static final String SAP = "SAP";
	public static final String NON_SAP_STRING = "NON SAP";
	//Requirement Area constants - End
	
	//Quartz keys
	
	public static final String QRTZ_THIRTYDAYSBLOCKEDRESOURCEREPORTEMAIL="thirtyDaysblockedResourceReportEmail";
	public static final String QRTZ_SETDEFAULTPROJECTFORBLOCKEDRESOURCE="setDefaultProjectforBlockedResource";
	public static final String QRTZ_RUNPROJECTGOINGTOCLOSESCHEDULER="runProjectGoingtoCloseScheduler";
	public static final String QRTZ_CHECKINGALLRESSTATUSSCHEDULER="checkingAllResStatusScheduler";
	public static final String QRTZ_GETALLINFOINACTIVERESOURCESCHEDULER="getAllInfoInactiveResourceScheduler";
	public static final String QRTZ_GETALLINFOACTIVERESOURCESCHEDULER="getAllInfoActiveResourceScheduler";
	
	public static final String SUCCESS="SUCCESS";
	public static final String FAILURE="FAILURE";
	
	//Message Board Constants - Start
	public static final String MESSAGESTATUS = "messageStatus";	
	
	//Message Board Constants - End
	public static final String getProperty(String property) {
		// TODO Auto-generated method stub
		String prop = "";
		String env = "";
		String enviornmentPropFileName = "/spring-configuration/rms.properties";

		env = getPropertyVariable(enviornmentPropFileName, "IafConfigSuffix");
		System.out.println("Enviornment: " + env);

		String propFileName = "/spring-configuration/rmsApp-" + env
				+ ".properties";
		prop = getPropertyVariable(propFileName, property);

		return prop;

	}

	public static String getPropertyVariable(String fileName, String property) {
		String value = "";
		Properties properties = new Properties();
		if (fileName != null && property != null) {
			InputStream inputStream = Constants.class
					.getResourceAsStream(fileName);
			try {
				if (inputStream == null) {
					throw new FileNotFoundException("property file '"
							+ fileName + "' not found in the classpath");
				}
				properties.load(inputStream);
				value = properties.getProperty(property);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

		}

		return value;
	}

	/**
	 * 
	 */
	public Constants() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public static void main(String[] args) throws UnknownHostException {
		System.out.println(InetAddress.getLocalHost().getHostAddress());
	}
}
