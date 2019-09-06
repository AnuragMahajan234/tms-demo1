package org.yash.rms.dto;




public class ResourceCommentDTO {
	
	private Integer resourceCommentId;

	private Integer resourceId;

	private String comment_Date;

	private String resourceComment;
	
	private String commentBy;
	
	private String resourceName;
	
	private String resourceType;

	public Integer getResourceCommentId() {
		return resourceCommentId;
	}

	public void setResourceCommentId(Integer resourceCommentId) {
		this.resourceCommentId = resourceCommentId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public String getComment_Date() {
		return comment_Date;
	}

	public void setComment_Date(String comment_Date) {
		this.comment_Date = comment_Date;
	}

	public String getResourceComment() {
		return resourceComment;
	}

	public void setResourceComment(String resourceComment) {
		this.resourceComment = resourceComment;
	}

	public String getCommentBy() {
		return commentBy;
	}

	public void setCommentBy(String commentBy) {
		this.commentBy = commentBy;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

}
