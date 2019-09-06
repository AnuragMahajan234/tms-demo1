package org.yash.rms.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.envers.Audited;
import org.yash.rms.exception.BusinessException;
import org.yash.rms.service.ResourceService;
import org.yash.rms.util.AppContext;
import org.yash.rms.util.UserUtil;

@MappedSuperclass
@Audited
public abstract class BaseEntity {

	
	  	@Basic
	    @Column(name = "modifiedTime", insertable = true, updatable = true)
	    private Date modifiedTime;
		
	    @Basic
		@Column(name = "createdTime", insertable = true, updatable = false)
	    private Date createdTime;
	    
	    @Basic
		@Column(name = "creator", insertable = true, updatable = false)
		private String creator;

	    @Basic
	    @Column(name="creator_id", updatable = false)
	    private Integer creatorId;
	    
		@Basic
		@Column(name="last_modifier_id")
	    private Integer lastModifierId;
		
		@Basic
    	@Column(name="lastModifier", updatable = false)
	    private String lastModifier;
	
	    	
	    public Integer getCreatorId() {
	    		return creatorId;
	   	}

	   	public void setCreatorId(Integer creatorId) {
	    		this.creatorId = creatorId;
	   	}

	    
		
	

		public String getCreator() {
    		return creator;
    	}

    	public void setCreator(String creator) {
    		this.creator = creator;
    	}
    	
		public String getLastModifier() {
	    		return lastModifier;
	    	}

	    	public void setLastModifier(String lastModifier) {
	    		this.lastModifier = lastModifier;
	    	}

	    	public Integer getLastModifierId() {
	    		return lastModifierId;
	    	}

	    	public void setLastModifierId(Integer lastModifierId) {
	    		this.lastModifierId = lastModifierId;
	    	}
		
		/**
		 * @return the modifiedTime
		 */
		
	    	public  Date getModifiedTime() {
			return modifiedTime;
			}

		/**
		 * @param modifiedTime
		 *            the modifiedTime to set
		 */
	    	public  void setModifiedTime(Date modifiedTime) {
			this.modifiedTime = modifiedTime;
			}

		/**
		 * @return the dateCreaTech
		 */
		
	    	public  Date getCreatedTime() {
			return createdTime;
			}

/**
* @param createdTime
*            the createdTime to set
*/
public  void setCreatedTime(Date createdTime) {
	this.createdTime = createdTime;
}

@PrePersist
void onCreate() {
ResourceService userService=AppContext.getApplicationContext().getBean(ResourceService.class);
 Resource userInContext = null;
 
try {
userInContext = userService.find(UserUtil.getUserContextDetails().getEmployeeId());
} catch (BusinessException e) {

e.printStackTrace();
}	
if(userInContext!=null) {
 this.setCreator(userInContext.getFirstName()+" "+userInContext.getLastName());
  this.setCreatorId(userInContext.getEmployeeId());
	this.setLastModifier(userInContext.getFirstName()+" "+userInContext.getLastName());
	this.setLastModifierId(userInContext.getEmployeeId());

}
Date date=new Date();
this.setCreatedTime(date);
this.setModifiedTime(date);


}



@PreUpdate
void onPersist() {

	ResourceService userService=AppContext.getApplicationContext().getBean(ResourceService.class);
Resource userInContext = null;
try {
	userInContext = userService.find(UserUtil.getUserContextDetails().getEmployeeId());
} catch (BusinessException e) {
	e.printStackTrace();
}	

if(userInContext!=null)
{
	this.setLastModifier(userInContext.getFirstName()+" "+userInContext.getLastName());
  
	this.setLastModifierId(userInContext.getEmployeeId());
}
this.setModifiedTime((new Date()));
}
}

	

