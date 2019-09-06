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
@Table(name = "subprocess_ost")
public class CATicketSubProcess implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3605944613564803668L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "subprocess_name")
	private String subProcessName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "process_id", referencedColumnName = "id", nullable = false)
	private CATicketProcess processId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubProcessName() {
		return subProcessName;
	}

	public void setSubProcessName(String subProcessName) {
		this.subProcessName = subProcessName;
	}

	public CATicketProcess getProcessId() {
		return processId;
	}

	public void setProcessId(CATicketProcess processId) {
		this.processId = processId;
	}

	public static String toJsonArray(
			Collection<org.yash.rms.domain.CATicketSubProcess> collection) {
		return new JSONSerializer()
				.include("id", "subProcessName", "processId")
				.exclude("*")
				.transform(new DateTransformer(Constants.DATE_PATTERN),
						Date.class).serialize(collection);
	}

}
