/**
 * 
 */
package org.yash.rms.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONArray;
import org.yash.rms.dto.TimehrsViewsDTO;
import org.yash.rms.service.ResourceAllocationService;
import org.yash.rms.util.AppContext;
/**
 * @author bhakti.barve
 * 
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "timehrs_admin_view")
public class TimehrsView implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_id")
	private Integer employeeId;

	@Column(name = "yash_emp_id")
	private String yashEmpId;

	@Column(name = "full_name")
	private String employeeName;

	@Column(name = "designation_name")
	private String designationName;

	@Column(name = "grade")
	private String grade;

	@Column(name = "planned_capacity")
	private Integer plannedCapacity;

	@Column(name = "actual_capacity")
	private Integer actualCapacity;

	@Column(name = "planned_hrs_sum")
	private Float totalPlannedHrs;

	@Column(name = "rephrs")
	private Float totalReportedHrs;

	@Column(name = "billed_hrs_sum")
	private Float totalBilledHrs;

	@Column(name = "date_of_joining")
	private Date dateOfJoining;

	@Column(name = "current_project")
	private String currentProject;
	
	@Column(name = "release_date")
	private Date releaseDate;
	
	@Column(name = "loc_name")
	private String location;
	
	@Column(name = "bg_bu")
	private String currentBgBu;
	


	//We added ReleaseDate as a variable in for identify the Active/InActive Resource in ResourceAllocation
	
	
		
	public Date getReleaseDate() {
		return releaseDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCurrentBgBu() {
		return currentBgBu;
	}

	public void setCurrentBgBu(String currentBgBu) {
		this.currentBgBu = currentBgBu;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getYashEmpId() {
		return yashEmpId;
	}

	public void setYashEmpId(String yashEmpId) {
		this.yashEmpId = yashEmpId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Integer getPlannedCapacity() {
		return plannedCapacity;
	}

	public void setPlannedCapacity(Integer plannedCapacity) {
		this.plannedCapacity = plannedCapacity;
	}

	public Integer getActualCapacity() {
		return actualCapacity;
	}

	public void setActualCapacity(Integer actualCapacity) {
		this.actualCapacity = actualCapacity;
	}

	public Float getTotalPlannedHrs() {
		return totalPlannedHrs;
	}

	public void setTotalPlannedHrs(Float totalPlannedHrs) {
		this.totalPlannedHrs = totalPlannedHrs;
	}

	public Float getTotalReportedHrs() {
		return totalReportedHrs;
	}

	public void setTotalReportedHrs(Float totalReportedHrs) {
		this.totalReportedHrs = totalReportedHrs;
	}

	public Float getTotalBilledHrs() {
		return totalBilledHrs;
	}

	public void setTotalBilledHrs(Float totalBilledHrs) {
		this.totalBilledHrs = totalBilledHrs;
	}
	

	public List<String> getProjectNames() {
		//logger.info("-----------InfogramActiveResource getIRMName method Start-----------------------");
		ResourceAllocationService resourceAllocationService = getResourceAllocationServiceObject();
		List<String> projectlist=new ArrayList<String>();
		if (resourceAllocationService != null) {
			
				return projectlist=resourceAllocationService.findProjectNamesByEmployeeId(this.employeeId);
			}
		
			return projectlist;
		
	}
	private ResourceAllocationService getResourceAllocationServiceObject(){
		ResourceAllocationService resourceAllocationService=null;
		resourceAllocationService=AppContext.getApplicationContext().getBean(ResourceAllocationService.class);
		return resourceAllocationService;
	}


	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public String getCurrentProject() {
		return currentProject;
	}

	public void setCurrentProject(String currentProject) {
		this.currentProject = currentProject;
	}

	public static List<JSONArray> toJsonArray(List<TimehrsViewsDTO> allResources) {
		List<JSONArray> dataArray = new ArrayList<JSONArray>();
		for (TimehrsViewsDTO resource : allResources) {
			JSONArray object = new JSONArray();
			object.put(0, resource.getEmployeeId() + "");
			object.put(1, resource.getYashEmpId() + "");
			object.put(2, resource.getEmployeeName());
			object.put(3, resource.getDesignation_name());
			object.put(4, resource.getGrade());
			object.put(5, resource.getCurrentProject() + "");
			object.put(6, resource.getTotalPlannedHrs() + "");
			object.put(7, resource.getTotalReportedHrs() + "");
			object.put(8, resource.getTotalBilledHrs() + "");
			object.put(9, resource.getDateOfJoining());
			object.put(10, resource.getReleaseDate());
		
			dataArray.add(object);
		}

		return dataArray;
	}
	public static List<JSONArray> toJsonArrayResAlloc(List<TimehrsViewsDTO> allResources) {
		List<JSONArray> dataArray = new ArrayList<JSONArray>();
		for (TimehrsViewsDTO resource : allResources) {
			JSONArray object = new JSONArray();
			object.put(0, resource.getEmployeeId() + "");
			object.put(1, resource.getYashEmpId() + "");
			object.put(2, resource.getEmployeeName());
			object.put(3, resource.getDesignation_name());
			object.put(4, resource.getGrade());
			object.put(5, resource.getCurBgBu());
			object.put(6, resource.getLocName());
			object.put(7, resource.getCurrentProject() + "");
			object.put(8, resource.getReleaseDate());
			
	
			dataArray.add(object);
		}

		return dataArray;
	}
}
