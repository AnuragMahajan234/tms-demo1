package org.yash.rms.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.ParamDef;
import org.hibernate.exception.GenericJDBCException;
import org.yash.rms.dao.ProjectAllocationDao;
import org.yash.rms.service.ResourceAllocationService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.util.AppContext;
import org.yash.rms.util.Constants;

/**
 * This class represents java entity for Infogram Table in RMS for active
 * resource.
 * 
 * @author samiksha.sant
 *
 */

@Entity
@Table(name = "info_active_employee")

@NamedQueries({
		@NamedQuery(name = InfogramActiveResource.COUNT_INFOGRAMRESOURCE_EXISTINGRESOURCE, query = "SELECT COUNT(id) FROM InfogramActiveResource inf,Resource r  where  inf.employeeId=r.yashEmpId and r.releaseDate is null"),
		@NamedQuery(name = InfogramActiveResource.COUNT_INFOGRAMRESOURCE_ALLRESOURCE, query = "SELECT COUNT(id) FROM InfogramActiveResource "),
		@NamedQuery(name = InfogramActiveResource.COUNT_INFOGRAMRESOURCE_NEWRESOURCE, query = "SELECT COUNT(id) FROM InfogramActiveResource inf,Resource r where  inf.employeeId!=r.yashEmpId and r.releaseDate is null") })

@FilterDefs({ @FilterDef(name = InfogramActiveResource.EXISTINGRESOURCE), @FilterDef(name = InfogramActiveResource.NEWRESOURCE),
	@FilterDef(name = InfogramActiveResource.DISCARD_SUCCESS_PENDING_FAILURE,parameters=@ParamDef(name="processStatus", type="string")),
			@FilterDef(name = InfogramActiveResource.RESOURCE_STATUS_OK, parameters=@ParamDef(name="recordStatus", type="boolean"))
})
@Filters({
		@Filter(name = InfogramActiveResource.EXISTINGRESOURCE, condition = "( emp_id in (SELECT inf.emp_id FROM info_active_employee inf INNER JOIN resource r  ON inf.emp_id=r.yash_emp_id))"),
		@Filter(name = InfogramActiveResource.NEWRESOURCE, condition = "( emp_id in (SELECT inf.emp_id FROM info_active_employee inf LEFT JOIN resource r  ON inf.emp_id=r.yash_emp_id  WHERE r.yash_emp_id is null))"),
		@Filter(name = InfogramActiveResource.DISCARD_SUCCESS_PENDING_FAILURE, condition = "process_status=:processStatus"),
		@Filter(name = InfogramActiveResource.RESOURCE_STATUS_OK, condition = "record_status=:recordStatus")
		
})


public class InfogramActiveResource {
	public final static String COUNT_INFOGRAMRESOURCE_EXISTINGRESOURCE = "COUNT_INFOGRAMRESOURCE_EXISTINGRESOURCE";
	public final static String COUNT_INFOGRAMRESOURCE_ALLRESOURCE = "COUNT_INFOGRAMRESOURCE_ALLRESOURCE";
	public final static String COUNT_INFOGRAMRESOURCE_NEWRESOURCE = "COUNT_INFOGRAMRESOURCE_NEWRESOURCE";
	public final static String NEWRESOURCE = "newResources";
	public final static String EXISTINGRESOURCE = "existingResources";
	public final static String DISCARD_SUCCESS_PENDING_FAILURE = "DiscardOrSuccessOrPendingOrFailure";
	public final static String INFOID = "id";
	public final static String CREATEDTIMESTAMP = "creationTimestamp";
	public final static String BGBU = "bgbu";
	public final static String BG = "bg";
	public final static String BU = "bu";
	public final static String NONE = "none";
	public final static String RESOURCE_STATUS_OK="AllOk";

	//private static final Logger logger = LoggerFactory.getLogger(InfogramActiveResource.class);

	transient Resource resource = null;
	
	transient Resource irmResource = null;
	
	transient Resource srmResource = null;
	
	transient ResourceAllocation resourceAllocation=null;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "emp_id")
	private String employeeId;

	@Column(name = "emp_name")
	private String name;

	@Column(name = "emp_first_name")
	private String firstName;

	@Column(name = "emp_middle_name")
	private String middleName;

	@Column(name = "emp_last_name")
	private String lastName;

	@Column(name = "emp_status")
	private String status;

	@Column(name = "date_of_joining",nullable=false)
	private Date dateOfJoining;

	@Column(name = "date_of_birth")
	private Date dateOfBirth;

	@Column(name = "emp_type")
	private String employeeType;

	@Column(name = "emp_category")
	private String employeeCategory;

	@Column(name = "grade")
	private String grade;

	@Column(name = "designation")
	private String designation;

	@Column(name = "gender")
	private String gender;

	@Column(name = "email_id")
	private String emailId;

	@Column(name = "base_location")
	private String baseLocation;

	@Column(name = "base_location_division")
	private String locationDivision;

	@Column(name = "current_location")
	private String currentLocation;

	@Column(name = "business_group")
	private String businessGroup;

	@Column(name = "business_unit")
	private String businessUnit;

	@Column(name = "IRM_name")
	private String irmName;

	@Column(name = "SRM_name")
	private String srmName;

	@Column(name = "BUH_name")
	private String buhName;

	@Column(name = "BGH_name")
	private String bghName;

	@Column(name = "HRBP_name")
	private String hrbpName;

	@Column(name = "IRM_code")
	private String irmEmployeeId;

	@Column(name = "SRM_code")
	private String srmEmployeeId;

	@Column(name = "BUH_code")
	private String buhEmployeeId;

	@Column(name = "BGH_code")
	private String bghEmployeeId;

	@Column(name = "HRBP_code")
	private String hrbpEmployeeId;

	@Column(name = "IRM_email_id")
	private String irmEmailId;

	@Column(name = "SRM_email_id")
	private String srmEmailId;

	@Column(name = "BUH_email_id")
	private String buhEmailId;

	@Column(name = "BHG_email_id")
	private String bghEmailId;

	@Column(name = "HRBP_email_id")
	private String hrbpEmailId;

	@Column(name = "resigned_date")
	private Date resignedDate;

	@Column(name = "insertion_timestamp", updatable = false, insertable = true)
	private Date creationTimestamp;

	@Column(name = "process_status")
	private String processStatus;

	@Column(name = "failure_reason")
	private String failureReason;
	
	@Column(name = "modified_by")
	private String modifiedBy;
	
	@Column(name = "modified_name")
	private String modifiedName;
	
	@Column(name = "modified_time")
	private Date modifiedTime;
	
	@Column(name = "record_status", nullable = true)
	private Boolean recordStatus;

	

	public Boolean getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(Boolean recordStatus) {
		this.recordStatus = recordStatus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public String getEmployeeCategory() {
		return employeeCategory;
	}

	public void setEmployeeCategory(String employeeCategory) {
		this.employeeCategory = employeeCategory;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getBaseLocation() {
		return baseLocation;
	}

	public void setBaseLocation(String baseLocation) {
		this.baseLocation = baseLocation;
	}

	public String getLocationDivision() {
		return locationDivision;
	}

	public void setLocationDivision(String locationDivision) {
		this.locationDivision = locationDivision;
	}

	public String getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}

	public String getBusinessGroup() {
		return businessGroup;
	}

	public void setBusinessGroup(String businessGroup) {
		this.businessGroup = businessGroup;
	}

	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	
	public void setIrmName(String irmName) {
		this.irmName = irmName;
	}

	
	public void setSrmName(String srmName) {
		this.srmName = srmName;
	}

	public String getBuhName() {
		return buhName;
	}

	public void setBuhName(String buhName) {
		this.buhName = buhName;
	}

	public String getBghName() {
		return bghName;
	}

	public void setBghName(String bghName) {
		this.bghName = bghName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getHrbpName() {
		return hrbpName;
	}

	public void setHrbpName(String hrbpName) {
		this.hrbpName = hrbpName;
	}

	public String getIrmEmployeeId() {
		return irmEmployeeId;
	}

	public void setIrmEmployeeId(String irmEmployeeId) {
		this.irmEmployeeId = irmEmployeeId;
	}

	public String getSrmEmployeeId() {
		return srmEmployeeId;
	}

	public void setSrmEmployeeId(String srmEmployeeId) {
		this.srmEmployeeId = srmEmployeeId;
	}

	public String getBuhEmployeeId() {
		return buhEmployeeId;
	}

	public void setBuhEmployeeId(String buhEmployeeId) {
		this.buhEmployeeId = buhEmployeeId;
	}

	public String getBghEmployeeId() {
		return bghEmployeeId;
	}

	public void setBghEmployeeId(String bghEmployeeId) {
		this.bghEmployeeId = bghEmployeeId;
	}

	public String getHrbpEmployeeId() {
		return hrbpEmployeeId;
	}

	public void setHrbpEmployeeId(String hrbpEmployeeId) {
		this.hrbpEmployeeId = hrbpEmployeeId;
	}

	public String getIrmEmailId() {
		return irmEmailId;
	}

	public void setIrmEmailId(String irmEmailId) {
		this.irmEmailId = irmEmailId;
	}

	public String getSrmEmailId() {
		return srmEmailId;
	}

	public void setSrmEmailId(String srmEmailId) {
		this.srmEmailId = srmEmailId;
	}

	public String getBuhEmailId() {
		return buhEmailId;
	}

	public void setBuhEmailId(String buhEmailId) {
		this.buhEmailId = buhEmailId;
	}

	public String getBghEmailId() {
		return bghEmailId;
	}

	public void setBghEmailId(String bghEmailId) {
		this.bghEmailId = bghEmailId;
	}

	public String getHrbpEmailId() {
		return hrbpEmailId;
	}

	public void setHrbpEmailId(String hrbpEmailId) {
		this.hrbpEmailId = hrbpEmailId;
	}

	public Date getResignedDate() {
		return resignedDate;
	}

	public void setResignedDate(Date resignedDate) {
		this.resignedDate = resignedDate;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public Date getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Date creationTimestamp) {
		if(creationTimestamp!=null)
			this.creationTimestamp = creationTimestamp;
		else
			this.creationTimestamp = new Date();
			
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}
	
	public String getIrmName() {
		return irmName;
	}
	
	public String getSrmName() {
		return srmName;
	}

	public String getSrmInRMS() {
		resource = getResourceObject();
		if (resource != null) {
			if (resource.getCurrentReportingManagerTwo() != null) {
				return resource.getCurrentReportingManagerTwo().getEmployeeName();
			} else {
				return "NA";
			}
		} else {
			return "";
		}
	}

	public String getIrmInRMS() {
		//logger.info("-----------InfogramActiveResource getIRMName method Start-----------------------");
		resource = getResourceObject();
		if (resource != null) {
			if (resource.getCurrentReportingManager() != null) {
				return resource.getCurrentReportingManager().getEmployeeName();
			} else {
				return "NA";
			}
		} else {
			return "";
		}
	}

	public String getLocationInRMS() {
	//	logger.info("-----------getLocationInRMS method start---------------");
		resource = getResourceObject();
		if(resource != null) {
			if(resource.getLocationId()!=null){
				return resource.getLocationId().getLocation();
			}else {
				return "NA";
			}
		} else {
			return " ";
		}
	}
	
	public String getCurrentLocInRMS() {
	//	logger.info("-----------getLocationInRMS method start---------------");
		resource = getResourceObject();
		if(resource != null) {
			if(resource.getDeploymentLocation()!=null){
				return resource.getDeploymentLocation().getLocation();
			}else {
				return "NA";
			}
		} else {
			return " ";
		}
	}
	
	
	
	public String getRmsBg() {
	//.info("-----------getRmsBg method start---------------");
		resource = getResourceObject();
		if(resource != null) {
			if(resource.getBuId()!=null){
				return resource.getBuId().getParentId().getName();
			}else {
				return "NA";
			}
		} else {
			return " ";
		}
	}
	public String getIRMBgBu(){
		String irmBu="";
		String irmBg="";
		//String rmsBg=getRmsBg();
		//String rmsBu=getRmsBu();
		
		String projectBu="";
		String projectBg="";
		
		resourceAllocation=getResouceAllocationObj();
		if(resourceAllocation!=null){
			if(resourceAllocation.getProjectId()!=null){
				projectBu=resourceAllocation.getProjectId().getOrgHierarchy().getName();
				projectBg=resourceAllocation.getProjectId().getOrgHierarchy().getParentId().getName();
			}
			else
			{
				return "NA";
			}
		}
		else{
			return "";
		}
		
		resource=getResourceObject();
		
		if(resource!=null){
			if(null!=resource.getCurrentReportingManager()){
				if(null!=resource.getCurrentReportingManager().getBuId())
					irmBu=resource.getCurrentReportingManager().getBuId().getName();
				else
					irmBu="NA";
				
				if(null!=resource.getCurrentReportingManager().getBuId().getParentId())
					irmBg=resource.getCurrentReportingManager().getBuId().getParentId().getName();
				else
					irmBg="NA";
			}
			else
			{
				return "";
			}
		}
		else
		{
			return "";
		}
		
		return compareBGBU(projectBg,  projectBu,  irmBg, irmBu);
	}
	
	public String getSRMBgBu(){
		String srmBu="";
		String srmBg="";
		//String rmsBg=getRmsBg();
		//String rmsBu=getRmsBu();
		
		String projectBu="";
		String projectBg="";
		
		resourceAllocation=getResouceAllocationObj();
		if(resourceAllocation!=null){
			if(resourceAllocation.getProjectId()!=null){
				projectBu=resourceAllocation.getProjectId().getOrgHierarchy().getName();
				projectBg=resourceAllocation.getProjectId().getOrgHierarchy().getParentId().getName();
			}
			else
			{
				return "NA";
			}
		}
		else{
			return "";
		}
		
		resource=getResourceObject();
		if(resource!=null){
			if(null!=resource.getCurrentReportingManagerTwo()){
				if(null!=resource.getCurrentReportingManagerTwo().getBuId())
					srmBu=resource.getCurrentReportingManagerTwo().getBuId().getName();
				else
					srmBu="NA";
				
				if(null!=resource.getCurrentReportingManagerTwo().getBuId().getParentId())
					srmBg=resource.getCurrentReportingManagerTwo().getBuId().getParentId().getName();
				else
					srmBg="NA";
			}
			else 
				return "";
		}
		else
			return "";
		
		return compareBGBU(projectBg,  projectBu,  srmBg, srmBu);
	}
	public String getInfoIRMBgBu(){
		//String irmBu="";
		//String irmBg="";
		//String rmsBg=getBusinessGroup();
		//String rmsBu=getBusinessUnit();
		String projectBu="";
		String projectBg="";
		
		String irmBu=getInfoIRMBu();
		String irmBg=getInfoIRMBg();;
		
		resourceAllocation=getResouceAllocationObj();
		if(resourceAllocation!=null){
			if(resourceAllocation.getProjectId()!=null){
				projectBu=resourceAllocation.getProjectId().getOrgHierarchy().getName();
				projectBg=resourceAllocation.getProjectId().getOrgHierarchy().getParentId().getName();
			}
			else
			{
				return "NA";
			}
		}
		else{
			return "";
		}
		
	/*	resource=getResourceObject();
		
		if(resource!=null){
			if(null!=resource.getCurrentReportingManager()){
				if(null!=resource.getCurrentReportingManager().getBuId())
					irmBu=resource.getCurrentReportingManager().getBuId().getName();
				else
					irmBu="NA";
				
				if(null!=resource.getCurrentReportingManager().getBuId().getParentId())
					irmBg=resource.getCurrentReportingManager().getBuId().getParentId().getName();
				else
					irmBg="NA";
			}
			else
			{
				return "";
			}
		}
		else
		{
			return "";
		}*/
		
		return compareBGBU(projectBg,  projectBu,  irmBg, irmBu);
	}
	public String getInfoSRMBgBu(){
		//String srmBu="";
		//String srmBg="";
		//String rmsBg=getBusinessGroup();
		//String rmsBu=getBusinessUnit();
		String projectBu="";
		String projectBg="";
		
		String srmBu=getInfoSRMBu();
		String srmBg=getInfoSRMBg();
		
		resourceAllocation=getResouceAllocationObj();
		if(resourceAllocation!=null){
			if(resourceAllocation.getProjectId()!=null){
				projectBu=resourceAllocation.getProjectId().getOrgHierarchy().getName();
				projectBg=resourceAllocation.getProjectId().getOrgHierarchy().getParentId().getName();
			}
			else
			{
				return "NA";
			}
		}
		else{
			return "";
		}
		
		/*resource=getResourceObject();
		if(resource!=null){
			if(null!=resource.getCurrentReportingManagerTwo()){
				if(null!=resource.getCurrentReportingManagerTwo().getBuId())
					srmBu=resource.getCurrentReportingManagerTwo().getBuId().getName();
				else
					srmBu="NA";
				
				if(null!=resource.getCurrentReportingManagerTwo().getBuId().getParentId())
					srmBg=resource.getCurrentReportingManagerTwo().getBuId().getParentId().getName();
				else
					srmBg="NA";
			}
			else 
				return "";
		}
		else
			return "";*/
		
		return compareBGBU(projectBg,  projectBu,  srmBg, srmBu);
	}
	public String getInfoProjectBgBu()
	{
		String projectBg="";
		String projectBu="";
		String infoBg=getBusinessGroup();
		String infoBu=getBusinessUnit();
		
		resourceAllocation=getResouceAllocationObj();
		if(resourceAllocation!=null){
			if(resourceAllocation.getProjectId()!=null){
				projectBu=resourceAllocation.getProjectId().getOrgHierarchy().getName();
				projectBg=resourceAllocation.getProjectId().getOrgHierarchy().getParentId().getName();
			}
			else
			{
				return "NA";
			}
		}
		else{
			return "";
		}
			
		return compareBGBU(infoBg,  infoBu,  projectBg, projectBu);
		
		
	}

	public String getRMSProjectBgBu()
	{
		String projectBg="";
		String projectBu="";
		String rmsBg=getRmsBg();
		String rmsBu=getRmsBu();
		
		resourceAllocation=getResouceAllocationObj();
		if(resourceAllocation!=null){
			if(resourceAllocation.getProjectId()!=null){
				projectBu=resourceAllocation.getProjectId().getOrgHierarchy().getName();
				projectBg=resourceAllocation.getProjectId().getOrgHierarchy().getParentId().getName();
			}
			else
			{
				return "NA";
			}
		}
		else{
			return "";
		}
			
		return compareBGBU(rmsBg,  rmsBu,  projectBg, projectBu);
		
		
	}
	private String compareBGBU(String rmsBg, String rmsBu, String Bg, String Bu )
	{
		String projectBgBu="";
		if(rmsBg!=null &&  rmsBu!=null)
		{
		if(rmsBg.equalsIgnoreCase(Bg)&& rmsBu.equalsIgnoreCase(Bu)){
			
			projectBgBu=InfogramActiveResource.BGBU;
			return projectBgBu;
		}
		
		else if(rmsBg.equalsIgnoreCase(Bg)&&!rmsBu.equalsIgnoreCase(Bu)){
			
			projectBgBu=InfogramActiveResource.BG;
			return projectBgBu;
		}
		else if(!rmsBg.equalsIgnoreCase(Bg)&&rmsBu.equalsIgnoreCase(Bu)){
		
			projectBgBu=InfogramActiveResource.BU;
			return projectBgBu;
		}
		
		else{
			projectBgBu=InfogramActiveResource.NONE;
			return projectBgBu;
		}
		}else
		{
			projectBgBu=InfogramActiveResource.NONE;
		return projectBgBu;
			
		}
		
	}
	public String getRmsBu() {
		//logger.info("-----------getRmsBu method start---------------");
		resource = getResourceObject();
		if(resource != null) {
			if(resource.getBuId()!=null){
				return resource.getBuId().getName();
			}else {
				return "NA";
			}
		} else {
			return " ";
		}
	}
	public String getInfoSRMBg() {
	//.info("-----------getRmsBg method start---------------");
		srmResource = getSRMResourceObject();
		if(srmResource != null) {
			if(srmResource.getBuId()!=null){
				return srmResource.getBuId().getParentId().getName();
			}else {
				return "NA";
			}
		} else {
			return " ";
		}
	}
	public String getInfoIRMBg() {
	//.info("-----------getRmsBg method start---------------");
		irmResource = getIRMResourceObject();
		if(irmResource != null) {
			if(irmResource.getBuId()!=null){
				return irmResource.getBuId().getParentId().getName();
			}else {
				return "NA";
			}
		} else {
			return " ";
		}
	}
	public String getInfoIRMBu() {
		//logger.info("-----------getRmsBu method start---------------");
		irmResource = getIRMResourceObject();
		if(irmResource != null) {
			if(irmResource.getBuId()!=null){
				return irmResource.getBuId().getName();
			}else {
				return "NA";
			}
		} else {
			return " ";
		}
	}
	public String getInfoSRMBu() {
		//logger.info("-----------getRmsBu method start---------------");
		srmResource = getSRMResourceObject();
		if(srmResource != null) {
			if(srmResource.getBuId()!=null){
				return srmResource.getBuId().getName();
			}else {
				return "NA";
			}
		} else {
			return " ";
		}
	}
	
	public String getRmsIrmYashEmpId() {
		//logger.info("-----------getRmsIrmYashEmpId method start---------------");
		resource = getResourceObject();
		if(resource != null) {
			if(resource.getCurrentReportingManager()!=null){
				return resource.getCurrentReportingManager().getYashEmpId();
			}else {
				return "NA";
			}
		} else {
			return " ";
		}
	}
	
	public String getRmsSrmYashEmpId() {
		//logger.info("-----------getRmsSrmYashEmpId method start---------------");
		resource = getResourceObject();
		if(resource != null) {
			if(resource.getCurrentReportingManagerTwo()!=null){
				return resource.getCurrentReportingManagerTwo().getYashEmpId();
			}else {
				return "NA";
			}
		} else {
			return " ";
		}
	}
	
	public String getRmsDesignation() {
		//logger.info("-----------getRmsDesignation method start---------------");
		resource = getResourceObject();
		if(resource != null) {
			
			if(resource.getDesignationId()!=null){
					return resource.getDesignationId().getDesignationName();
			}else {
				return "NA";
			}
		} else {
			return " ";
		}
	}
	
	public String getResourceType() {
		if(getResourceObject() == null) {
			return Constants.RESOURCE_TYPE_NEW;
		} else {
			return Constants.RESOURCE_TYPE_EXISTING;
		}
	} 
	
	private Resource getResourceObject() {
		//logger.info("-----------InfogramActiveResource getResourceObject method Start-----------------------");
		if (resource == null) {
			ResourceService resourceService = AppContext.getApplicationContext().getBean(ResourceService.class);
			resource = resourceService.findResourcesByYashEmpIdEquals(this.employeeId);
		}
		return resource;
	}
	private Resource getIRMResourceObject() {
		//logger.info("-----------InfogramActiveResource getIRMResourceObject method Start-----------------------");
		if (irmResource == null) {
			ResourceService resourceService = AppContext.getApplicationContext().getBean(ResourceService.class);
			if(this.irmEmployeeId!=null)
				irmResource = resourceService.findResourcesByYashEmpIdEquals(this.irmEmployeeId);
		}
		return irmResource;
	}
	private Resource getSRMResourceObject() {
		//logger.info("-----------InfogramActiveResource getSRMResourceObject method Start-----------------------");
		if (srmResource == null) {
			ResourceService resourceService = AppContext.getApplicationContext().getBean(ResourceService.class);
			if(this.srmEmployeeId!=null)
				srmResource = resourceService.findResourcesByYashEmpIdEquals(this.srmEmployeeId);
		}
		return srmResource;
	}
	private ResourceAllocation getResouceAllocationObj()
	{
		if(resourceAllocation==null)
		{
			ResourceAllocationService resourceAllocationService = AppContext.getApplicationContext().getBean(ResourceAllocationService.class);
			resourceAllocation=resourceAllocationService.findPrimaryProject(Integer.parseInt(this.employeeId));
			
		}
		return resourceAllocation;
		
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedName() {
		return modifiedName;
	}

	public void setModifiedName(String modifiedName) {
		this.modifiedName = modifiedName;
	}
	
	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	
	
	

	
}
