package org.yash.rms.domain;

import java.util.ArrayList;
import java.util.List;

public class User {
	/**
	 * Enum for your required details
	 */
	public enum USER_DATA{
		MEMBER_OF,
		MAIL,
		DEPARTMENT,
		OFFICE,
		MOBILE,
		MANAGER,
		TELEPHONE_NUMBER
	}
	
	private String username;
	private ArrayList<String> member_of;
	private String mail;
	private String department;
	private String office;
	private String mobile;
	private String manager;
	private String telephone_number;
	private boolean dataFromLDAPExist;
	

	/**
	 * @return the member_of
	 */
	public List<String> getMember_of() {
		return member_of;
	}
	/**
	 * @param member_of the member_of to set
	 */
	public void setMember_of(ArrayList<String> member_of) {
		this.member_of = member_of;
	}
	/**
	 * @return the telephone_number
	 */
	public String getTelephone_number() {
		return telephone_number;
	}
	/**
	 * @param telephone_number the telephone_number to set
	 */
	public void setTelephone_number(String telephone_number) {
		this.telephone_number = telephone_number;
	}
	/**
	 * @return the dataFromLDAPExist
	 */
	public boolean isDataFromLDAPExist() {
		return dataFromLDAPExist;
	}
	/**
	 * @param dataFromLDAPExist the dataFromLDAPExist to set
	 */
	public void setDataFromLDAPExist(boolean dataFromLDAPExist) {
		this.dataFromLDAPExist = dataFromLDAPExist;
	}
	/**
	 * @return the memberOf
	 */
	public List<String> getMemberOf() {
		return member_of;
	}
	/**
	 * @param memberOf the memberOf to set
	 */
	public void setMemberOf(ArrayList<String> member_of) {
		this.member_of = member_of;
	}
	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}
	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}
	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}
	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	/**
	 * @return the office
	 */
	public String getOffice() {
		return office;
	}
	/**
	 * @param office the office to set
	 */
	public void setOffice(String office) {
		this.office = office;
	}
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the manager
	 */
	public String getManager() {
		return manager;
	}
	/**
	 * @param manager the manager to set
	 */
	public void setManager(String manager) {
		this.manager = manager;
	}
	/**
	 * @return the telephoneNumber
	 */
	public String getTelephoneNumber() {
		return telephone_number;
	}
	/**
	 * @param telephoneNumber the telephoneNumber to set
	 */
	public void setTelephoneNumber(String telephone_number) {
		this.telephone_number = telephone_number;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}