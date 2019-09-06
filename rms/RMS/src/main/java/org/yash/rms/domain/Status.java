package org.yash.rms.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Status extends BaseEntity {
	
	public final static String Request="Request";
	public final static String Training="Training";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "type")
	private String type;
	
	
//	private Request request;
	
	@Column(name = "action")
	private String action;
	

}
