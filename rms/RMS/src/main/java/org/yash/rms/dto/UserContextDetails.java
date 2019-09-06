package org.yash.rms.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.yash.rms.domain.Grade;
import org.yash.rms.domain.Location;
import org.yash.rms.domain.OrgHierarchy;

@SuppressWarnings("serial")
public class UserContextDetails implements UserDetails {

	private Integer employeeId;

	private String userName;
	private String yashEmpId;

	private String firstName;
	private String lastName;
	private String emailId;

	private String userRole;

	private boolean isBehalfManager;

	private boolean isSEPGUser;

	private String employeeName;

	private Date releaseDate;

	private String ostVisibility;
		
	private String designation;

	private String deploymentLocation;
	
	private String location;
	
	private String middleName;

	private byte[] uploadImage;
	
	private String  currentBU;
	private String  parentBU;
	private String UploadResumeFileName;
	private String preferredLocation;
	private String grade;
	

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getPreferredLocation() {
		return preferredLocation;
	}

	public void setPreferredLocation(String preferredLocation) {
		this.preferredLocation = preferredLocation;
	}

	public String getUploadResumeFileName() {
		return UploadResumeFileName;
	}

	public void setUploadResumeFileName(String uploadResumeFileName) {
		UploadResumeFileName = uploadResumeFileName;
	}

	public String getCurrentBU() {
		return currentBU;
	}

	public void setCurrentBU(String currentBU) {
		this.currentBU = currentBU;
	}

	public String getParentBU() {
		return parentBU;
	}

	public void setParentBU(String parentBU) {
		this.parentBU = parentBU;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	private Date dateOfJoining;

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

	public String getDeploymentLocation() {
		return deploymentLocation;
	}

	public void setDeploymentLocation(String deploymentLocation) {
		this.deploymentLocation = deploymentLocation;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	private boolean isRRFAccess;
	
	private boolean isReportAccess;

	
	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public String getOstVisibility() {
		return ostVisibility;
	}

	public void setOstVisibility(String ostVisibility) {
		this.ostVisibility = ostVisibility;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public List<OrgHierarchy> getAccessRight() {
		return accessRight;
	}

	public void setAccessRight(List<OrgHierarchy> accessRight) {
		this.accessRight = accessRight;
	}

	private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

	private List<OrgHierarchy> accessRight = new ArrayList<OrgHierarchy>();

	public Collection<org.springframework.security.core.GrantedAuthority> getAuthorities() {
		// authorities.add(new SimpleGrantedAuthority(getUserRole()));
		return authorities;
	}

	public String getPassword() {
		if (this.getUserName() != null) {
			return this.getUserName().toLowerCase();
		}
		return this.getUserName();
	}

	public String getUsername() {
		// return this.getEmployeeName();
		return this.getUserName();

	}

	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public boolean isBehalfManager() {
		return isBehalfManager;
	}

	public boolean isSEPGUser() {
		return isSEPGUser;

	}

	public void setBehalfManager(boolean isBehalfManager) {
		this.isBehalfManager = isBehalfManager;
	}

	public void setSEPGUser(boolean isSEPGUser) {
		this.isSEPGUser = isSEPGUser;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public boolean isRRFAccess() {
		return isRRFAccess;
	}

	public void setRRFAccess(boolean isRRFAccess) {
		this.isRRFAccess = isRRFAccess;
	}

	public boolean isReportAccess() {
		return isReportAccess;
	}

	public void setReportAccess(boolean isReportAccess) {
		this.isReportAccess = isReportAccess;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public byte[] getUploadImage() {
		return uploadImage;
	}

	public void setUploadImage(byte[] uploadImage) {
		this.uploadImage = uploadImage;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	public String getYashEmpId() {
		return yashEmpId;
	}
	
	public void setYashEmpId(String yashEmpId) {
		this.yashEmpId = yashEmpId;
	}
	
	
}
