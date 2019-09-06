package org.yash.rms.domain;

import java.io.UnsupportedEncodingException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;
import org.yash.rms.service.ResourceService;
import org.yash.rms.util.AppContext;

@Entity
@Table(name = "message_board")
@FilterDefs({ @FilterDef(name = MessageBoard.WHICH_STATUS,parameters=@ParamDef(name="messageStatus", type="string")),
	@FilterDef(name = MessageBoard.IS_DELETED, parameters=@ParamDef(name="isDeleted", type="boolean"))})
@Filters({@Filter(name = MessageBoard.WHICH_STATUS, condition = "message_status=:messageStatus"),
	@Filter(name = RequestRequisitionSkill.IS_DELETED, condition = "is_deleted=:isDeleted")})
public class MessageBoard extends BaseEntity{

	public final static String MESSAGEBOARD = "id";
	public static final String CREATEDTIME = "createdTime";
	public static final String MODIFIEDTIME = "modifiedTime";
	public final static String WHICH_STATUS = "MessageStatus";
	public final static String IS_DELETED = "IS_DELETED";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(length = 65535, columnDefinition = "text")
	private String text;
	
	@Column(name = "message_status")
	private String messageStatus;
	
	@Column(name = "approved_by")
	private Integer approvedBy;
	
	@Column(name = "approved_name")
	private String approvedName;
	
	@Column(name = "modified_by")
	private Integer modifiedBy;
	
	@Column(name = "modified_name")
	private String modifiedName;
	

	



	@Column(name="is_deleted", columnDefinition = "boolean default false", nullable = false)
	private Boolean isDeleted =false;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {		
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}

	public Integer getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(Integer approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedName() {
		return modifiedName;
	}

	public void setModifiedName(String modifiedName) {
		this.modifiedName = modifiedName;
	}
 
	

	

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	
	public String getCurrentBgBU() {
		String currentBgBu = "";
		
		return currentBgBu;
		
	}

	public String getDesignation() {
		String designation = "";
		ResourceService resourceService = AppContext.getApplicationContext().getBean(ResourceService.class);
		designation = resourceService.find(getCreatorId()).getDesignationId().getDesignationName();
		return designation;
		
	}
 
	public String getCreatorProfileImage() {
		String base64EncodedUser ="";
		ResourceService resourceService = AppContext.getApplicationContext().getBean(ResourceService.class);
		Resource resource=resourceService.find(getCreatorId());
		if (resource.getUploadImage() != null && resource.getUploadImage().length > 0) {
			byte[] encodeBase64UserImage = Base64.encodeBase64(resource.getUploadImage());
			try {
				base64EncodedUser = new String(encodeBase64UserImage, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}		
		}
		return base64EncodedUser; 
	}

	public String getApprovedName() {
		return approvedName;
	}

	public void setApprovedName(String approvedName) {
		this.approvedName = approvedName;
	}
}
