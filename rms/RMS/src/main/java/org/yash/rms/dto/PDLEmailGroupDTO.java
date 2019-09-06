package org.yash.rms.dto;

public class PDLEmailGroupDTO {
	
	private int id;
	private String pdlEmailId;
	private String active;
	private String pdl_name;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPdlEmailId() {
		return pdlEmailId;
	}
	public void setPdlEmailId(String pdlEmailId) {
		this.pdlEmailId = pdlEmailId;
	}
	
	public String getPdlName() {
		return pdl_name;
	}
	public void setPdlName(String pdl_name) {
		this.pdl_name = pdl_name;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
}
