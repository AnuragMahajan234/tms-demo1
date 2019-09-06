package org.yash.rms.domain;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.NotAudited;

@Entity
@Table(name="user_profile")
public class UserProfile {
	
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @OneToMany(mappedBy = "profileId", cascade = CascadeType.ALL)
    @NotAudited
    private Set<SkillProfilePrimary> skillProfilePrimaries;
    
    @OneToMany(mappedBy = "profileId", cascade = CascadeType.ALL)
    @NotAudited
    private Set<SkillProfileSecondary> skillProfileSecondaries;

    @Column(name = "middle_name", length = 256)
  	private String middleName;
    
    @Column(name="lastupdated_id", length=256)
    private String lastUpdatedIid;
    
    @Column(name="lastupdated_timestamp")
    private Timestamp lastUpdatedTimestamp;
    
    @Column(name="created_id", length=256)
    private String createdId;
    
    @Column(name="creation_timestamp")
    private Timestamp creationTimestamp;
    
    @Column(name = "yash_emp_id", length = 256)
    @NotNull
    private String yashEmpId;
    
    @Column(name = "first_name", length = 256)
    private String firstName;
    
    @Column(name = "last_name", length = 256)
    private String lastName;
    
    @Column(name = "contact_number_one", length = 256)
    private String contactNumberOne;
    
    @Column(name = "contact_number_two", length = 256)
    private String contactNumberTwo;
    
    @Column(name = "email_id", length = 256)
    private String emailId;
    
    @Column(name = "customer_id_detail", length = 256)
    private String customerIdDetail;
    
    @Column(name = "resume_file_name", length = 256)
    private String resumeFileName;
    
    @Column(name = "deny_code", length = 20)
    private String denyCode;
    
    @Column(name = "approval_code", length = 20)
    private String approvalCode;
    
    @Lob
    @Column(name ="upload_image" , length = 100000000)   //upload image
    private  byte[] uploadImage;
    
    @Column(name = "logical_delete", length = 10)
    private String logicalDelete;
    
    @Transient
    private Integer resourceEmployeeId;
    
    @Transient
    private byte[] resume;
    
    @Transient
    private double relevantExp;
    
    @Transient
    private double totalExp;
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    

   
    public Set<SkillProfilePrimary> getSkillProfilePrimaries() {
		return skillProfilePrimaries;
	}

	public void setSkillProfilePrimaries(
			Set<SkillProfilePrimary> skillProfilePrimaries) {
		this.skillProfilePrimaries = skillProfilePrimaries;
	}

	public Set<SkillProfileSecondary> getSkillProfileSecondaries() {
		return skillProfileSecondaries;
	}

	public void setSkillProfileSecondaries(
			Set<SkillProfileSecondary> skillProfileSecondaries) {
		this.skillProfileSecondaries = skillProfileSecondaries;
	}


    
   
    
    
  //added middle name
    
    public String getLastUpdatedIid() {
		return lastUpdatedIid;
	}

	public void setLastUpdatedIid(String lastUpdatedIid) {
		this.lastUpdatedIid = lastUpdatedIid;
	}

	public Timestamp getLastUpdatedTimestamp() {
		return lastUpdatedTimestamp;
	}

	public void setLastUpdatedTimestamp(Timestamp lastUpdatedTimestamp) {
		this.lastUpdatedTimestamp = lastUpdatedTimestamp;
	}

	public Timestamp getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Timestamp creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

    public String getCreatedId() {
		return createdId;
	}

	public void setCreatedId(String createdId) {
		this.createdId = createdId;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public Integer getResourceEmployeeId() {
        return resourceEmployeeId;
    }

    public void setResourceEmployeeId(Integer resourceEmployeeId) {
        this.resourceEmployeeId = resourceEmployeeId;
    }
	
    
	public byte[] getResume() {
		return resume;
	}

	public void setResume(byte[] resume) {
		this.resume = resume;
	}

    
    public String getYashEmpId() {
        return yashEmpId;
    }
    
    public void setYashEmpId(String yashEmpId) {
        this.yashEmpId = yashEmpId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getContactNumberOne() {
        return contactNumberOne;
    }
    
    public void setContactNumberOne(String contactNumberOne) {
        this.contactNumberOne = contactNumberOne;
    }
    
    public String getContactNumberTwo() {
        return contactNumberTwo;
    }
    
    public void setContactNumberTwo(String contactNumberTwo) {
        this.contactNumberTwo = contactNumberTwo;
    }
    
    public String getEmailId() {
        return emailId;
    }
    
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    
    public String getCustomerIdDetail() {
        return customerIdDetail;
    }
    
    public void setCustomerIdDetail(String customerIdDetail) {
        this.customerIdDetail = customerIdDetail;
    }
    
    public String getResumeFileName() {
        return resumeFileName;
    }
    
    public void setResumeFileName(String resumeFileName) {
        this.resumeFileName = resumeFileName;
    }
    
    public String getDenyCode() {
        return denyCode;
    }
    
    public void setDenyCode(String denyCode) {
        this.denyCode = denyCode;
    }
    
    public String getApprovalCode() {
        return approvalCode;
    }
    
    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }
    
    public String getLogicalDelete() {
        return logicalDelete;
    }
    
    public void setLogicalDelete(String logicalDelete) {
        this.logicalDelete = logicalDelete;
    }

	public byte[] getUploadImage() {
		return uploadImage;
	}

	public void setUploadImage(byte[] uploadImage) {
		this.uploadImage = uploadImage;
	}

	public double getRelevantExp() {
		return relevantExp;
	}

	public void setRelevantExp(double relevantExp) {
		this.relevantExp = relevantExp;
	}

	public double getTotalExp() {
		return totalExp;
	}

	public void setTotalExp(double totalExp) {
		this.totalExp = totalExp;
	}
}
