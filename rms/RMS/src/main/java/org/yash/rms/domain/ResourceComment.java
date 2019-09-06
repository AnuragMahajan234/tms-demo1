package org.yash.rms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "resource_Comment")
public class ResourceComment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "resource_Comment_id")
	private Integer resourceCommentId;

	@Column(name = "resource_id")
	private Integer resourceId;

	@Column(name="comment_date",updatable = false, insertable = true)
	private Date comment_Date;
	
	/*@Column(name = "comment_date")
	private String comment_Date;*/
	
	@Column(name = "resource_comment")
	private String resourceComment;
	
	@Column(name = "comment_by")
	private String commentBy;


	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public Date getComment_Date() {
		return comment_Date;
	}

	public void setComment_Date(Date comment_Date) {
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
	
	public Integer getResourceCommentId() {
		return resourceCommentId;
	}

	public void setResourceCommentId(Integer resourceCommentId) {
		this.resourceCommentId = resourceCommentId;
	}

	@Override
	public String toString() {
		return "ResourceComment [resourceCommentId=" + resourceCommentId + ", resourceId=" + resourceId
				+ ", comment_Date=" + comment_Date + ", resourceComment=" + resourceComment + ", commentBy=" + commentBy
				+ "]";
	}

}
