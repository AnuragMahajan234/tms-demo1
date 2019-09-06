package org.yash.rms.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
 * @author purva.bhate
 *
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "emp_report_access")
public class EmpReportAccess implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;


	@Column(name = "yash_emp_id")
	private String yashEmpId;

	@Column(name = "project_id")
	private String projectId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getYashEmpId() {
		return yashEmpId;
	}

	public void setYashEmpId(String yashEmpId) {
		this.yashEmpId = yashEmpId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	


}
