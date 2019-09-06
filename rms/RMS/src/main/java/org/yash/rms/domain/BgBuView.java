package org.yash.rms.domain;

import java.io.Serializable;

import javax.persistence.FetchType;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.OneToMany;
@SuppressWarnings("serial")
@SQLDelete(sql = "UPDATE org_hierarchy SET active = '0' WHERE id= ?")
@FilterDef(name = "getActiverecord", parameters = @ParamDef(name = "active", type = "boolean"))
@Filter(name = "getActiverecord", condition = "active=:active")
@Entity
@Table(name = "bu")
public class BgBuView implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name", length = 256)
	private String name;
	
	@Column(name = "CODE", length = 256)
	private String code;
	
	@Column(name = "BgName", length = 256)
	private String bgName;
	
	@Column(name = "BuName", length = 256)
	private String buName;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the bgName
	 */
	public String getBgName() {
		return bgName;
	}

	/**
	 * @param bgName the bgName to set
	 */
	public void setBgName(String bgName) {
		this.bgName = bgName;
	}

	/**
	 * @return the buName
	 */
	public String getBuName() {
		return buName;
	}

	/**
	 * @param buName the buName to set
	 */
	public void setBuName(String buName) {
		this.buName = buName;
	}
	
	

}
