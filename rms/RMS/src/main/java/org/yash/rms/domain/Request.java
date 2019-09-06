package org.yash.rms.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author arpan.badjatiya
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "request_requisition")
public class Request implements Serializable {

	//public final static String SEARCH_ACTIVITY_BASED_ON_ID = "SEARCH_ACTIVITY_BASED_ON_ID";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "emp_id", referencedColumnName="employee_id", updatable = false)
	private Resource employeeId;

	@Column(name = "project_bu")
	private String projectBU;

	/*@Column(name = "project_name")
	private String projectName;*/
	
	@Column(name = "date_of_Indent")
	private Date date;
	
	@Column(name = "indentor_comments")
	private String comments;
	
	@OneToMany(mappedBy = "requestId", cascade = CascadeType.ALL)
	private List<SkillRequest> skillRequest;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id", referencedColumnName="id")
	private Project project;
	
	@Column(name = "sent_mail")
	private String sentMailTo;
	
	@Column(name = "notify_to")
	private String notifyMailTo;
	
	public String getNotifyMailTo() {
		return notifyMailTo;
	}

	public void setNotifyMailTo(String notifyMailTo) {
		this.notifyMailTo = notifyMailTo;
	}

	public String getSentMailTo() {
		return sentMailTo;
	}

	public void setSentMailTo(String sentMailTo) {
		this.sentMailTo = sentMailTo;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Resource getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Resource employeeId) {
		this.employeeId = employeeId;
	}

	public String getProjectBU() {
		return projectBU;
	}

	public void setProjectBU(String projectBU) {
		this.projectBU = projectBU;
	}

	/*public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projects) {
		this.projectName = projects;
	}*/

	public List<SkillRequest> getSkillRequest() {
		return skillRequest;
	}

	public void setSkillRequest(List<SkillRequest> skillRequest) {
		this.skillRequest = skillRequest;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	

}
