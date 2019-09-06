package org.yash.rms.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "phasemaster")
public class Phase implements Serializable {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "phaseid")
	private int id;

	@Column(name="phaseName")
	private String phasesName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhasesName() {
		return phasesName;
	}

	public void setPhasesName(String phasesName) {
		this.phasesName = phasesName;
	}
	
	

	@Override
	public String toString() {
		return "Phase [id=" + id + ", phasesName=" + phasesName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result
				+ ((phasesName == null) ? 0 : phasesName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Phase other = (Phase) obj;
		if (id != other.id)
			return false;
		if (phasesName == null) {
			if (other.phasesName != null)
				return false;
		} else if (!phasesName.equals(other.phasesName))
			return false;
		return true;
	}
	
	
	
	

}
