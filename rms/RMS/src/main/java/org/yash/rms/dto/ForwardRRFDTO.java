package org.yash.rms.dto;

public class ForwardRRFDTO {

	private String userIdList;
	
	private String pdlList;
	
	private String skillReqId;
	
	private String commentForwardRRF;

	public String getUserIdList() {
		return userIdList;
	}

	public void setUserIdList(String userIdList) {
		this.userIdList = userIdList;
	}

	public String getPdlList() {
		return pdlList;
	}

	public void setPdlList(String pdlList) {
		this.pdlList = pdlList;
	}

	public String getSkillReqId() {
		return skillReqId;
	}

	public void setSkillReqId(String skillReqId) {
		this.skillReqId = skillReqId;
	}
	
	public void setCommentForwardRRF(String commentForwardRRF) {
		this.commentForwardRRF = commentForwardRRF;
	}
	
	public String getCommentForwardRRF() {
		return commentForwardRRF;
	}
}
