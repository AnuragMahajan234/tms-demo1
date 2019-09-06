package org.yash.rms.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.yash.rms.util.Constants;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

@Entity
@Table(name = "process_ost")
public class CATicketProcess implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3453690519681000746L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "process_name")
	private String processName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "module_id", referencedColumnName = "id", nullable = false)
	private Project moduleId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public Project getModuleId() {
		return moduleId;
	}

	public void setModuleId(Project moduleId) {
		this.moduleId = moduleId;
	}

	public static String toJsonArray(
			Collection<org.yash.rms.domain.CATicketProcess> collection) {
		return new JSONSerializer()
				.include("id", "processName", "moduleId")
				.exclude("*")
				.transform(new DateTransformer(Constants.DATE_PATTERN),
						Date.class).serialize(collection);
	}

}
