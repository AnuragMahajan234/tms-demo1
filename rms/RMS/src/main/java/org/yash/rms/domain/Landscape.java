package org.yash.rms.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "landscape")
public class Landscape implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 429127571296924014L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "landscapeName")
	private String landscapeName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLandscapeName() {
		return landscapeName;
	}

	public void setLandscapeName(String landscapeName) {
		this.landscapeName = landscapeName;
	}

}
