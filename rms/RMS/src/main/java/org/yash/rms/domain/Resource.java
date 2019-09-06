package org.yash.rms.domain;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.codehaus.jackson.map.annotate.JsonView;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.yash.rms.util.Constants;
import org.yash.rms.util.View;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;



@SuppressWarnings("serial")
@XmlRootElement(name="Resource")
@Entity
@Table(name = "resource")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Resource implements Comparable<Resource>, Serializable {

	public static final String FindSkill_BY_EmployeeId = "PrimarySkills.findByEmployeeIdNative";
	private static final Logger logger = LoggerFactory.getLogger(Resource.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_id")
	private Integer employeeId;

	@Column(name = "report_user_id")
	@JsonView(View.MyJSONVIEW.class)
	private Integer reportUserId;

	@Column(name = "resignation_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	@JsonView(View.Internal.class)
	private Date resignationDate;

	@Column(name = "actual_capacity")
	@JsonView(View.Internal.class)
	private Integer actualCapacity;

	@Column(name = "award_recognition", length = 256)
	@JsonView(View.Internal.class)
	private String awardRecognition;

	@Column(name = "confirmation_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	@JsonView(View.Internal.class)
	private Date confirmationDate;

	@Column(name = "contact_number", length = 256)
	@JsonView(View.Internal.class)
	private String contactNumber;

	@Column(name = "contact_number_three", length = 256)
	@JsonView(View.Internal.class)
	private String contactNumberThree;

	@Column(name = "contact_number_two", length = 256)
	@JsonView(View.Internal.class)
	private String contactNumberTwo;

	@Column(name = "customer_id_detail", length = 256)
	@JsonView(View.Internal.class)
	private String customerIdDetail;

	@Column(name = "date_of_joining")
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	@JsonView(View.Internal.class)
	private Date dateOfJoining;
	@Column(name = "total_experience")
	@NotNull
	@JsonView(View.Internal.class)
	private double totalExper;
	@Column(name = "relevant_experience")
	@NotNull
	@JsonView(View.Internal.class)
	private double relevantExper;

	@Column(name = "email_id", length = 256)
	@NotNull
	@JsonView(View.Internal.class)
	private String emailId;
	

	@Column(name = "last_appraisal")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	@JsonView(View.Internal.class)
	private Date lastAppraisal;

	@Column(name = "penultimate_appraisal")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	@JsonView(View.Internal.class)
	private Date penultimateAppraisal;

	@Column(name = "planned_capacity")
	@JsonView(View.Internal.class)
	private Integer plannedCapacity;

	@Column(name = "profit_centre", length = 256)
	@JsonView(View.Internal.class)
	private String profitCentre;

	@Column(name = "release_date")
	@JsonView(View.Internal.class)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	private Date releaseDate;

	@Column(name = "transfer_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	@JsonView(View.Internal.class)
	private Date transferDate;

	/* added field */

	@Column(name = "end_transfer_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	@JsonView(View.Internal.class)
	private Date endTransferDate;

	@Column(name = "visa_valid")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	@JsonView(View.Internal.class)
	private Date visaValid;

	@Column(name = "USER_ROLE", length = 256)
	@JsonView(View.Internal.class)
	private String userRole;

	@Column(name = "user_name", length = 256, updatable = true)
	@JsonView(View.Internal.class)
	private String userName;

	@Column(name = "first_name", length = 256)
	@NotNull
	@JsonView({View.Internal.class,View.MyJSONVIEW1.class})
	private String firstName;

	@Column(name = "last_name", length = 256)
	@NotNull
	@JsonView({View.Internal.class,View.MyJSONVIEW1.class})
	private String lastName;

	@Column(name = "timesheet_comment_optional")
	@JsonView(View.Internal.class)
	private Character timesheetCommentOptional;

	@Transient
	private CommonsMultipartFile file;

	@Transient
	private Double totalPlannedHrs;

	@Transient
	private Boolean isReleasedIndicator = true;
	
	@Transient
	private String yearDiff;

	public Boolean getIsReleasedIndicator() {
		Date date = new Date();
		if (releaseDate != null && releaseDate.compareTo(date) < 0) {
			isReleasedIndicator = false;
		}
		return isReleasedIndicator;
	}

	public void setIsReleasedIndicator(Boolean isReleasedIndicator) {
		this.isReleasedIndicator = isReleasedIndicator;
	}

	public CommonsMultipartFile getFile() {
		return file;
	}

	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}

	@Transient
	private Double totalReportedHrs;

	@Transient
	private Double totalBilledHrs;

	@Column(name = "middle_name", length = 256)
	@JsonView(View.Internal.class)
	private String middleName;

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	@Transient
	private List<SkillsMapping> skillsMappingList;

	@OneToMany(mappedBy = "employeeId", fetch = FetchType.LAZY)
	//@JsonIgnore
	//@JsonBackReference
	@JsonView(View.Internal.class)
	private Set<UserNotification> userNotifications;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonView(View.Internal.class)
	@JoinColumn(name = "current_reporting_manager", referencedColumnName = "employee_id")
	//@JsonIgnore
	//@JsonBackReference
	private org.yash.rms.domain.Resource currentReportingManager;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "current_reporting_manager_two", referencedColumnName = "employee_id")
	//@JsonIgnore
	@JsonBackReference
	@JsonView(View.Internal.class)
	private org.yash.rms.domain.Resource currentReportingManagerTwo;

	@Column(name = "resume_file_name", length = 256)
	@JsonView(View.Internal.class)
	private String resumeFileName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "current_bu_id", referencedColumnName = "id", nullable = false)
	//@JsonIgnore
	//@JsonBackReference
	@JsonView(View.Internal.class)
	private OrgHierarchy currentBuId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "grade_id", referencedColumnName = "id", nullable = false)
	//@JsonBackReference
	@JsonView(View.Internal.class)
	private Grade gradeId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event_id", referencedColumnName = "id")
	//@JsonIgnore
	//@JsonBackReference
	@JsonView(View.Internal.class)
	private Event eventId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "current_project_id", referencedColumnName = "id")
	@JsonBackReference
	@JsonView(View.Internal.class)
	private Project currentProjectId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visa_id", referencedColumnName = "id")
	@JsonManagedReference
	@JsonView(View.Internal.class)
	private Visa visaId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bu_id", referencedColumnName = "id", nullable = false)
	//@JsonIgnore
	@JsonManagedReference
	@JsonView(View.Internal.class)
	private OrgHierarchy buId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
	@JsonManagedReference
	@JsonView(View.Internal.class)
	private Location locationId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ownership", referencedColumnName = "id", nullable = false)
	@JsonManagedReference
	@JsonView(View.Internal.class)
	private Ownership ownership;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "competency", referencedColumnName = "id", nullable = false)
	@JsonManagedReference
	@JsonView(View.Internal.class)
	private Competency competency;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_category", referencedColumnName = "id", nullable = false)
	@JsonManagedReference
	@JsonView(View.Internal.class)
	private EmployeeCategory employeeCategory;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payroll_location", referencedColumnName = "id")
	@JsonManagedReference
	@JsonView(View.Internal.class)
	private Location deploymentLocation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "designation_id", referencedColumnName = "id", nullable = false)
	@JsonManagedReference
	@JsonView(View.Internal.class)
	private Designation designationId;

	public Designation getDesignationId() {
		return designationId;
	}

	public void setDesignationId(Designation designationId) {
		this.designationId = designationId;
	}

	@Column(name = "yash_emp_id", length = 256, unique = true)
	@NotNull
	@JsonView(View.Internal.class)
	private String yashEmpId;

	@OneToMany(mappedBy = "resourceId", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JsonBackReference
	@JsonView(View.Internal.class)
	private Set<SkillResourcePrimary> skillResourcePrimaries;

	@OneToMany(mappedBy = "resourceId", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JsonBackReference
	@JsonView(View.Internal.class)
	private Set<SkillResourceSecondary> skillResourceSecondaries;

	@OneToMany(mappedBy = "resourceId", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	//@JsonBackReference
	//@JsonIgnore
	@JsonView(View.Internal.class)
	private Set<BGAdmin_Access_Rights> bgAdminAccessRightlist;

	@OneToMany(mappedBy = "offshoreDelMgr", fetch = FetchType.LAZY)
	@JsonBackReference
	@JsonView(View.Internal.class)
	private Set<Project> projects;

	@Column(name = "created_id", updatable = false)
	@JsonView(View.Internal.class)
	private String createdId;

	@Column(name = "creation_timestamp", updatable = false, insertable = true)
	@JsonView(View.Internal.class)
	private Date creationTimestamp;

	@Column(name = "lastupdated_id")
	@JsonView(View.Internal.class)
	private String lastUpdatedId;

	@Column(name = "lastupdated_timestamp")
	@JsonView(View.Internal.class)
	private Date lastupdatedTimestamp;

	@Column(name = "bGHComments")
	@JsonView(View.Internal.class)
	private String bGHComments;

	@Column(name = "bUComments")
	@JsonView(View.Internal.class)
	private String bUHComments;

	@Column(name = "hRComments")
	@JsonView(View.Internal.class)
	private String hRComments;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bGH_Name", referencedColumnName = "employee_id")
	@JsonBackReference
	@JsonView(View.Internal.class)
	private org.yash.rms.domain.Resource bGHName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bU_Name", referencedColumnName = "employee_id")
	@JsonBackReference
	@JsonView(View.Internal.class)
	private org.yash.rms.domain.Resource bUHName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hR_Name", referencedColumnName = "employee_id")
	@JsonBackReference
	@JsonView(View.Internal.class)
	private org.yash.rms.domain.Resource hRName;

	@Column(name = "bGComment_timestamp")
	@JsonView(View.Internal.class)
	private Date bGCommentsTimestamp;

	@Column(name = "bUComment_timestamp")
	@JsonView(View.Internal.class)
	private Date bUCommentTimestamp;

	@Column(name = "hRComment_timestamp")
	@JsonView(View.Internal.class)
	private Date hRCommentTimestamp;

	@Column(name = "rejoining_flag")
	private Character rejoiningFlag;

	@Lob
	@Column(name = "upload_image", length = 100000000)
	// upload image
	@JsonIgnore
	private byte[] uploadImage;
	
	/**Start- Added By Anjana for Resume and TEF upload Download **/
	@Column(name = "upload_resume_file_name", length = 256)
	private String uploadResumeFileName;
	
	@Column(name = "upload_tef_file_name", length = 256)
	private String uploadTEFFileName;
	
	@Lob
    @Column(name ="upload_resume" , length = 100000000)   //upload Resume
	@JsonIgnore
	private  byte[] uploadResume;
	
	@Lob
    @Column(name ="upload_tef" , length = 100000000)   //upload TEF
	@JsonIgnore
	private  byte[] uploadTEF;
	/**End- Added By Anjana for Resume and TEF upload Download **/

	@Column(name = "rrfAccess")
	private Character rrfAccess;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "preferred_location", referencedColumnName = "id")
	private Location preferredLocation;

	public Location getPreferredLocation() {
		return preferredLocation;
	}

	public void setPreferredLocation(Location preferredLocation) {
		this.preferredLocation = preferredLocation;
	}
	
	public Character getRrfAccess() {
		return rrfAccess;
	}

	public void setRrfAccess(Character rrfAccess) {
		this.rrfAccess = rrfAccess;
	}

	@Transient
	private String projectIds;
	
	public String getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(String projectIds) {
		this.projectIds = projectIds;
	}

	public Competency getCompetency() {
		return competency;
	}

	public void setCompetency(Competency competency) {
		this.competency = competency;
	}

	public EmployeeCategory getEmployeeCategory() {
		return employeeCategory;
	}

	public void setEmployeeCategory(EmployeeCategory employeeCategory) {
		this.employeeCategory = employeeCategory;
	}

	/**
	 * @return the eventId
	 */
	public Event getEventId() {
		return eventId;
	}

	/**
	 * @param eventId
	 *            the eventId to set
	 */
	public void setEventId(Event eventId) {
		this.eventId = eventId;
	}

	/**
	 * @return the bUHName
	 */
	public org.yash.rms.domain.Resource getbUHName() {
		return bUHName;
	}

	/**
	 * @param bUHName
	 *            the bUHName to set
	 */
	public void setbUHName(org.yash.rms.domain.Resource bUHName) {
		this.bUHName = bUHName;
	}

	/**
	 * @return the hRName
	 */
	public org.yash.rms.domain.Resource gethRName() {
		return hRName;
	}

	/**
	 * @param hRName
	 *            the hRName to set
	 */
	public void sethRName(org.yash.rms.domain.Resource hRName) {
		this.hRName = hRName;
	}

	/**
	 * @return the bGHName
	 */
	public org.yash.rms.domain.Resource getbGHName() {
		return bGHName;
	}

	/**
	 * @param bGHName
	 *            the bGHName to set
	 */
	public void setbGHName(org.yash.rms.domain.Resource bGHName) {
		this.bGHName = bGHName;
	}

	/**
	 * @return the bUCommentTimestamp
	 */
	public Date getbUCommentTimestamp() {
		return bUCommentTimestamp;
	}

	/**
	 * @param bUCommentTimestamp
	 *            the bUCommentTimestamp to set
	 */
	public void setbUCommentTimestamp(Date bUCommentTimestamp) {
		this.bUCommentTimestamp = bUCommentTimestamp;
	}

	/**
	 * @return the hRCommentTimestamp
	 */
	public Date gethRCommentTimestamp() {
		return hRCommentTimestamp;
	}

	/**
	 * @param hRCommentTimestamp
	 *            the hRCommentTimestamp to set
	 */
	public void sethRCommentTimestamp(Date hRCommentTimestamp) {
		this.hRCommentTimestamp = hRCommentTimestamp;
	}

	public String getCreatedId() {
		return createdId;
	}

	/**
	 * @return the hRComments
	 */
	public String gethRComments() {
		return hRComments;
	}

	/**
	 * @param hRComments
	 *            the hRComments to set
	 */
	public void sethRComments(String hRComments) {
		this.hRComments = hRComments;
	}

	/**
	 * @return the bUHComments
	 */
	public String getbUHComments() {
		return bUHComments;
	}

	/**
	 * @param bUHComments
	 *            the bUHComments to set
	 */
	public void setbUHComments(String bUHComments) {
		this.bUHComments = bUHComments;
	}

	/**
	 * @return the bGCommentsTimestamp
	 */
	public Date getbGCommentsTimestamp() {
		return bGCommentsTimestamp;
	}

	/**
	 * @param bGCommentsTimestamp
	 *            the bGCommentsTimestamp to set
	 */
	public void setbGCommentsTimestamp(Date bGCommentsTimestamp) {
		this.bGCommentsTimestamp = bGCommentsTimestamp;
	}

	/**
	 * @return the bGHComments
	 */
	public String getbGHComments() {
		return bGHComments;
	}

	/**
	 * @param bGHComments
	 *            the bGHComments to set
	 */
	public void setbGHComments(String bGHComments) {
		this.bGHComments = bGHComments;
	}

	public void setCreatedId(String createdId) {
		this.createdId = createdId;
	}

	public Date getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public String getLastUpdatedId() {
		return lastUpdatedId;
	}

	public void setLastUpdatedId(String lastUpdatedId) {
		this.lastUpdatedId = lastUpdatedId;
	}

	public Date getLastupdatedTimestamp() {
		return lastupdatedTimestamp;
	}

	public void setLastupdatedTimestamp(Date lastupdatedTimestamp) {
		this.lastupdatedTimestamp = lastupdatedTimestamp;
	}

	public String getResumeFileName() {
		return resumeFileName;
	}

	public void setResumeFileName(String resumeFileName) {
		this.resumeFileName = resumeFileName;
	}

	public void setYashEmpId(String yashEmpId) {
		if (!yashEmpId.isEmpty())
			this.yashEmpId = yashEmpId.trim();
	}

	public String getYashEmpId() {
		if (yashEmpId != null && (!yashEmpId.isEmpty()))
			yashEmpId = yashEmpId.trim();
		return yashEmpId;
	}

	public Character getRejoiningFlag() {
		return rejoiningFlag;
	}

	public void setRejoiningFlag(Character rejoiningFlag) {
		this.rejoiningFlag = rejoiningFlag;
	}
	/** Added By Anjana for Resume and TEF upload Download **/
	public String toJson() {
		return new JSONSerializer()
				.include("skillResourcePrimaries", "resumeFileName", "currentReportingManager.employeeId","currentReportingManager.yashEmpId","currentReportingManager.employeeName", "currentReportingManagerTwo.employeeId","currentReportingManagerTwo.yashEmpId","currentReportingManagerTwo.employeeName", "userAction", "employeeId", "yashEmpId",
						"lastName", "firstName", "middleName", "emailId", "contactNumber", "contactNumberTwo", "contactNumberThree", "visaValid", "penultimateAppraisal", "resignationDate",
						"reportUserId", "lastAppraisal","yearDiff","totalExper","relevantExper",
						/* "plannedCapacity", "actualCapacity", */
						"skillResourceSecondaries", "profitCentre", "releaseDate", "userRole", "uploadResume", "uploadTEF", "customerIdDetail", "dateOfJoining", "confirmationDate", "awardRecognition", "gradeId.id", "visaId.id",
						"locationId.id", "preferredLocation.location","preferredLocation.id","ownership.id", "designationId.id", "employeeCategory.id", "employeeCategory.employeecategoryName", "competency.id", "competency.skill",
						"designationId.designationName", "buId.id", "currentBuId.id", "buId.parentId.name", "currentBuId.parentId.name", "deploymentLocation.id", "currentProjectId.id",
						"transferDate", "userName", "timesheetCommentOptional", "skillResourcePrimaries.resourceId", "skillResourcePrimaries.skillId", "skillResourcePrimaries.skillId.id",
						"skillResourceSecondaries.skillId", "skillResourceSecondaries.skillId.id", "bgAdminAccessRightlist", "bgAdminAccessRightlist.id", "bGHComments", "bGCommentsTimestamp",
						"bGHName.employeeId", "bUHComments", "bUCommentTimestamp", "bUHName.employeeId", "hRComments", "hRCommentTimestamp", "hRName.employeeId", "eventId.event", "endTransferDate",
						"rejoiningFlag", "rrfAccess","bGHName.userName","bUHName.userName","hRName.userName").exclude("*").transform(new DateTransformer(Constants.DATE_PATTERN_4), Date.class).serialize(this);
	}

	public static org.yash.rms.domain.Resource fromJsonToResource(String json) {
		return new JSONDeserializer<Resource>().use(null, Resource.class).use(Date.class, new DateTransformer(Constants.DATE_PATTERN_4)).deserialize(json);
	}

	public static List<JSONArray> toJsonArray(Collection<org.yash.rms.domain.Resource> resources) {
		List<JSONArray> dataArray = new ArrayList<JSONArray>();
		for (Resource resource : resources) {
			JSONArray object = new JSONArray();
			object.put(0, resource.getEmployeeId() + "");
			object.put(1, resource.getYashEmpId() + "");
			object.put(2, resource.getEmployeeName());
			object.put(3, resource.getDesignationId().getDesignationName());
			object.put(4, resource.getGradeId().getGrade());
			// object.put(5,resource.getEventId().getEvent());
			// code for new date format//

			DateFormat dateFormatOld = new SimpleDateFormat("yyyy-M-dd");

			DateFormat dateFormatNew = new SimpleDateFormat(Constants.DATE_PATTERN_4);

			Date dateOfJoining;
			Date releaseDate=null;
			String formattedDateOfJoining = "";
			String yearDiff="";
			double diff=0;
			

			try {
				dateOfJoining = dateFormatOld.parse(resource.getDateOfJoining().toString());
				formattedDateOfJoining = dateFormatNew.format(dateOfJoining);
				if(resource.getReleaseDate()!=null)
				{
					releaseDate=dateFormatOld.parse(resource.getReleaseDate().toString());
				}
				yearDiff=getCalYearDiff(dateOfJoining, releaseDate);
			} catch (Exception e) {
				formattedDateOfJoining = "";
			}
			diff=Double.parseDouble(yearDiff);
			object.put(5, formattedDateOfJoining);
			object.put(20, calcYear(resource.getTotalExper(), diff)+"");
			object.put(21,calcYear(resource.getRelevantExper(), diff)+"" );
			object.put(22, yearDiff);

			// code by RMSTeam for new date format//
			Date dateOfRelease;
			String formattedDateOfRelease = "";

			try {
				dateOfRelease = dateFormatOld.parse(resource.getReleaseDate().toString());
				formattedDateOfRelease = dateFormatNew.format(dateOfRelease);
			} catch (Exception e) {
				formattedDateOfRelease = "";
			}

			object.put(6, formattedDateOfRelease);

			if (resource.getReleaseDate() != null) {
				object.put(6, formattedDateOfRelease);
			} else {
				object.put(6, "N.A");
			}
			// object.put(7,resource.getEmployeeCategory().getEmployeecategoryName());
			object.put(7, resource.getOwnership().getOwnershipName());
			if (resource.getCurrentBuId().getParentId() != null) {
				object.put(8, resource.getCurrentBuId().getParentId().getName() + "-" + resource.getCurrentBuId().getName());
			} else {
				object.put(8, resource.getCurrentBuId().getName());
			}
			if (resource.getBuId().getParentId() != null) {
				object.put(9, resource.getBuId().getParentId().getName() + "-" + resource.getBuId().getName());
			} else {
				object.put(9, resource.getBuId().getName());
			}
			object.put(10, resource.getEmailId());
			// object.put(11 ,resource.getResumeFileName());
			object.put(11, resource.getContactNumber());
			object.put(12, resource.getLocationId().getLocation());
			object.put(13, (resource.getCurrentReportingManager() != null ? resource.getCurrentReportingManager().getEmployeeName() : "N/A"));
			object.put(14, (resource.getCurrentReportingManagerTwo() != null ? resource.getCurrentReportingManagerTwo().getEmployeeName() : "N/A"));
			object.put(15, resource.getUserRole());

			Date dateOfResignation;
			String formattedDateOfResignation = "";

			try {
				dateOfResignation = dateFormatOld.parse(resource.getResignationDate().toString());
				formattedDateOfResignation = dateFormatNew.format(dateOfResignation);
			} catch (Exception e) {
				formattedDateOfResignation = "";
			}

			object.put(16, formattedDateOfResignation);

			if (resource.getResignationDate() != null) {
				object.put(16, formattedDateOfResignation);
			} else {
				object.put(16, "N.A");
			}
			if (resource.getReportUserId() != null) {
				object.put(17, resource.getReportUserId().toString());	
			} else {
				object.put(17, "");
			}
			/**Start- Added By Anjana for Resume and TEF upload Download **/
			object.put(18, "Resume");
	        object.put(19, "TEF");
			/**End- Added By Anjana for Resume and TEF upload Download **/
	        
			dataArray.add(object);
		}

		return dataArray;
	}

	public static Collection<org.yash.rms.domain.Resource> fromJsonArrayToResources(String json) {
		return new JSONDeserializer<List<Resource>>().use(null, ArrayList.class).use(Date.class, new DateTransformer(Constants.DATE_PATTERN_4)).use("values", Resource.class).deserialize(json);
	}

	public Double getTotalPlannedHrs() {
		return totalPlannedHrs;
	}

	public void setTotalPlannedHrs(Double totalPlannedHrs) {
		this.totalPlannedHrs = totalPlannedHrs;
	}

	public Double getTotalReportedHrs() {
		return totalReportedHrs;
	}

	public void setTotalReportedHrs(Double totalReportedHrs) {
		this.totalReportedHrs = totalReportedHrs;
	}

	public Double getTotalBilledHrs() {
		return totalBilledHrs;
	}

	public void setTotalBilledHrs(Double totalBilledHrs) {
		this.totalBilledHrs = totalBilledHrs;
	}

	@JsonView(View.Internal.class)
	public String getPassword() {
		if (this.getUserName() != null) {
			return this.getUserName().toLowerCase();
		}
		return this.getUserName();
	}

	public String getUsername() {
		return this.getEmployeeName();

	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

	public String getEmployeeName() {
		if (this.getFirstName() != null && this.getMiddleName() != null && this.getLastName() != null) {
			return getFirstName().concat(" ").concat(getMiddleName()).concat(" ").concat(getLastName());
		} else {
			return getFirstName().concat(" ").concat(getLastName());
		}

	}

	public int compareTo(org.yash.rms.domain.Resource o) {
		return getEmployeeName().compareTo(o.getEmployeeName());
	}

	public List<org.yash.rms.domain.SkillsMapping> getSkillsMappingList() {
		return skillsMappingList;
	}

	public void setSkillsMappingList(List<org.yash.rms.domain.SkillsMapping> skillsMappingList) {
		this.skillsMappingList = skillsMappingList;
	}

	public Integer getActualCapacity() {
		return actualCapacity;
	}

	public void setActualCapacity(Integer actualCapacity) {
		this.actualCapacity = actualCapacity;
	}

	public String getAwardRecognition() {
		return awardRecognition;
	}

	public void setAwardRecognition(String awardRecognition) {
		this.awardRecognition = awardRecognition;
	}

	public Date getConfirmationDate() {
		return confirmationDate;
	}

	public void setConfirmationDate(Date confirmationDate) {
		this.confirmationDate = confirmationDate;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getContactNumberThree() {
		return contactNumberThree;
	}

	public void setContactNumberThree(String contactNumberThree) {
		this.contactNumberThree = contactNumberThree;
	}

	public String getContactNumberTwo() {
		return contactNumberTwo;
	}

	public void setContactNumberTwo(String contactNumberTwo) {
		this.contactNumberTwo = contactNumberTwo;
	}

	public String getCustomerIdDetail() {
		return customerIdDetail;
	}

	public void setCustomerIdDetail(String customerIdDetail) {
		this.customerIdDetail = customerIdDetail;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Date getLastAppraisal() {
		return lastAppraisal;
	}

	public void setLastAppraisal(Date lastAppraisal) {
		this.lastAppraisal = lastAppraisal;
	}

	public Date getPenultimateAppraisal() {
		return penultimateAppraisal;
	}

	public void setPenultimateAppraisal(Date penultimateAppraisal) {
		this.penultimateAppraisal = penultimateAppraisal;
	}

	public Integer getPlannedCapacity() {
		return plannedCapacity;
	}

	public void setPlannedCapacity(Integer plannedCapacity) {
		this.plannedCapacity = plannedCapacity;
	}

	public String getProfitCentre() {
		return profitCentre;
	}

	public void setProfitCentre(String profitCentre) {
		this.profitCentre = profitCentre;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Date getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}

	public Date getVisaValid() {
		return visaValid;
	}

	public void setVisaValid(Date visaValid) {
		this.visaValid = visaValid;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Character getTimesheetCommentOptional() {
		return timesheetCommentOptional;
	}

	public void setTimesheetCommentOptional(Character timesheetCommentOptional) {
		this.timesheetCommentOptional = timesheetCommentOptional;
	}

	public Set<UserNotification> getUserNotifications() {
		return userNotifications;
	}

	public void setUserNotifications(Set<UserNotification> userNotifications) {
		this.userNotifications = userNotifications;
	}

	public org.yash.rms.domain.Resource getCurrentReportingManager() {
		return currentReportingManager;
	}

	public void setCurrentReportingManager(org.yash.rms.domain.Resource currentReportingManager) {
		this.currentReportingManager = currentReportingManager;
	}

	public org.yash.rms.domain.Resource getCurrentReportingManagerTwo() {
		return currentReportingManagerTwo;
	}

	public void setCurrentReportingManagerTwo(org.yash.rms.domain.Resource currentReportingManagerTwo) {
		this.currentReportingManagerTwo = currentReportingManagerTwo;
	}

	public OrgHierarchy getCurrentBuId() {
		return currentBuId;
	}

	public void setCurrentBuId(OrgHierarchy currentBuId) {
		this.currentBuId = currentBuId;
	}

	public Grade getGradeId() {
		return gradeId;
	}

	public void setGradeId(Grade gradeId) {
		this.gradeId = gradeId;
	}

	public Project getCurrentProjectId() {
		return currentProjectId;
	}

	public void setCurrentProjectId(Project currentProjectId) {
		this.currentProjectId = currentProjectId;
	}

	public Visa getVisaId() {
		return visaId;
	}

	public void setVisaId(Visa visaId) {
		this.visaId = visaId;
	}

	public OrgHierarchy getBuId() {
		return buId;
	}

	public void setBuId(OrgHierarchy buId) {
		this.buId = buId;
	}

	public Location getLocationId() {
		return locationId;
	}

	public void setLocationId(Location locationId) {
		this.locationId = locationId;
	}

	public Ownership getOwnership() {
		return ownership;
	}

	public void setOwnership(Ownership ownership) {
		this.ownership = ownership;
	}

	public Location getDeploymentLocation() {
		return deploymentLocation;
	}

	public void setDeploymentLocation(Location deploymentLocation) {
		this.deploymentLocation = deploymentLocation;
	}

	public Set<SkillResourcePrimary> getSkillResourcePrimaries() {
		return skillResourcePrimaries;
	}

	public void setSkillResourcePrimaries(Set<SkillResourcePrimary> skillResourcePrimaries) {
		this.skillResourcePrimaries = skillResourcePrimaries;
	}

	public Set<SkillResourceSecondary> getSkillResourceSecondaries() {
		return skillResourceSecondaries;
	}

	public void setSkillResourceSecondaries(Set<SkillResourceSecondary> skillResourceSecondaries) {
		this.skillResourceSecondaries = skillResourceSecondaries;
	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Integer getReportUserId() {
		return reportUserId;
	}

	public void setReportUserId(Integer reportUserId) {
		this.reportUserId = reportUserId;
	}

	public Date getResignationDate() {
		return resignationDate;
	}

	public void setResignationDate(Date resignationDate) {
		this.resignationDate = resignationDate;
	}

	
	public Set<BGAdmin_Access_Rights> getBgAdminAccessRightlist() {
		return bgAdminAccessRightlist;
	}

	public void setBgAdminAccessRightlist(Set<BGAdmin_Access_Rights> bgAdminAccessRightlist) {
		this.bgAdminAccessRightlist = bgAdminAccessRightlist;
	}

	public Date getEndTransferDate() {
		return endTransferDate;
	}

	public void setEndTransferDate(Date endTransferDate) {
		this.endTransferDate = endTransferDate;
	}

	public byte[] getUploadImage() {
		return uploadImage;
	}

	public void setUploadImage(byte[] uploadImage) {
		this.uploadImage = uploadImage;
	}
	/**Start- Added By Anjana for Resume and TEF upload Download **/
	public byte[] getUploadResume() {
		return uploadResume;
	}

	public void setUploadResume(byte[] uploadResume) {
		this.uploadResume = uploadResume;
	}

	public byte[] getUploadTEF() {
		return uploadTEF;
	}

	public void setUploadTEF(byte[] uploadTEF) {
		this.uploadTEF = uploadTEF;
	}

	public String getUploadResumeFileName() {
		if(uploadResumeFileName == null)
			uploadResumeFileName = "";
		return uploadResumeFileName;
	}

	public void setUploadResumeFileName(String uploadResumeFileName) {
		this.uploadResumeFileName = uploadResumeFileName;
	}

	public String getUploadTEFFileName() {
		return uploadTEFFileName;
	}

	public void setUploadTEFFileName(String uploadTEFFileName) {
		this.uploadTEFFileName = uploadTEFFileName;
	}
	/**End- Added By Anjana for Resume and TEF upload Download **/

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Resource other = (Resource) obj;
		if (employeeId == null) {
			if (other.employeeId != null)
				return false;
		} else if (!employeeId.equals(other.employeeId))
			return false;
		return true;
	}

	public static String toJsonString(Set<Resource> resourceList) {
		    return new JSONSerializer().include("firstName", "lastName", "employeeId" ,"yashEmpId").exclude("*")
		        .transform(new DateTransformer(Constants.DATE_PATTERN), Date.class).serialize(resourceList);

	}

	public double getTotalExper() {
		return totalExper;
	}
	public void setTotalExper(double totalExper) {
		this.totalExper = totalExper;
	}
	public double getRelevantExper() {
		return relevantExper;
	}

	public void setRelevantExper(double relevantExper) {
		this.relevantExper = relevantExper;
	}

	public String getYearDiff() {
		return yearDiff;
	}

	public void setYearDiff(String yearDiff) {
		this.yearDiff = yearDiff;
	}
	
	public static String getCalYearDiff(Date dateOfJoining, Date releseDate)
	{
			
		int years=0;
		int months=0;
		long miliSec=0;
		String month="";
		
		
		Date currentdate =new Date();
		if(releseDate!=null)
			miliSec =releseDate.getTime()-dateOfJoining.getTime();
		else
			miliSec =currentdate.getTime()-dateOfJoining.getTime();
		Calendar c = Calendar.getInstance(); 
		c.setTimeInMillis(miliSec);
		years = c.get(Calendar.YEAR)-1970;
		months = c.get(Calendar.MONTH);
		if(months<10)
			month="0"+months+"";
		else
			month=months+"";
		return years+"."+month;
	}
	public static String calcYear(Double firstYear, Double secondYear)
	{
		String first=firstYear.toString();
		String second=secondYear.toString();
		String[] firsts=first.split("\\.");
		String[] seconds=second.split("\\.");
		int months=0;
		int noOfMonth=0;
		int total=0;
		String month="";
		int years=Integer.parseInt(firsts[0])+Integer.parseInt(seconds[0]);
		try {
			 if(firsts[1].equals("1"))
				 firsts[1]="10";
			 if(seconds[1].equals("1"))
				 seconds[1]="10";
			 months=(Integer.parseInt(firsts[1])+Integer.parseInt(seconds[1]))/12;
			 noOfMonth=(Integer.parseInt(firsts[1])+Integer.parseInt(seconds[1]))%12;
			 if(noOfMonth<10)
				 month="0"+noOfMonth;
			 else
				 month=noOfMonth+"";
		}
		catch(ArithmeticException ae){
			months=0;
		}
		total=years+months;
		return total+"."+month;
	}
	public static String getCalcDiffTotalAndYash(Double firstYear, Double secondYear) {
		   
        DecimalFormat df= new DecimalFormat("#0.00");
        String first=df.format(firstYear);
        String second=df.format(secondYear);
        String[] firsts=first.split("\\.");
        String[] seconds=second.split("\\.");
        int firstYearInMonths=Integer.parseInt(firsts[0])*12;
        logger.info("firstYearInMonths is"+firstYearInMonths);
        int firstYearFracMonths = Integer.parseInt(firsts[1]);
        logger.info("firstYearFracMonths is "+firstYearFracMonths);
        int firstYrTotalMon= firstYearInMonths+firstYearFracMonths;
        int secondYearInMonths=Integer.parseInt(seconds[0])*12;
        int secondYearFracMonths = Integer.parseInt(seconds[1]);
        int secondYrTotalMon= secondYearInMonths+secondYearFracMonths;
        logger.info("firstYrTotalMon"+firstYrTotalMon);
        logger.info("secondYrTotalMon"+secondYrTotalMon);
        int diffFirstSecond = firstYrTotalMon-secondYrTotalMon;
        logger.info("diffFirstSecond"+diffFirstSecond);
        diffFirstSecond=Math.abs(diffFirstSecond);
        int years = diffFirstSecond/12;
        int monthsInt = 0;
        monthsInt=diffFirstSecond%12;       
        String monthString="";
        if(monthsInt<10)
        {
            monthString="0"+monthsInt+"";
        }
        else
        {
            monthString=monthsInt+"";
        }
       
        return years+"."+monthString;
    }
	
	public static String toJsonString(List<Resource> resourceList) {
	    return new JSONSerializer().include("firstName", "lastName", "employeeId" ,"yashEmpId","emailId", "uploadResumeFileName", "contactNumber", "totalExper", "competency.skill").exclude("*")
	        .transform(new DateTransformer(Constants.DATE_PATTERN), Date.class).serialize(resourceList);

}
	
	public static String toJsonString(Resource resource) {
	    return new JSONSerializer().include("firstName", "lastName", "employeeId" ,"yashEmpId").exclude("*")
	        .transform(new DateTransformer(Constants.DATE_PATTERN), Date.class).serialize(resource);
	}
}
