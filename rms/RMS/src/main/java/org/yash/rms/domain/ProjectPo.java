package org.yash.rms.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.springframework.format.annotation.DateTimeFormat;
@NamedQueries({
	
	@NamedQuery(name = ProjectPo.DELETE_ProjectPo_BASED_ON_ID, query = ProjectPo.QUERY_ProjectPo_DELETE_BASED_ON_ID) })

@Entity
@Table(name="project_po")
public class ProjectPo {
	
	public final static String DELETE_ProjectPo_BASED_ON_ID="DELETE_ProjectPo_BASED_ON_ID";
	public final static String QUERY_ProjectPo_DELETE_BASED_ON_ID="DELETE ProjectPo b where b.id = :id";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Project projectId;
    
    @Column(name = "cost")
    private Integer cost;
    
    @Column(name = "account_name", length = 256)
    private String accountName;
    
    @Column(name = "po_number", length = 256)
    private String poNumber;
    
    @Column(name = "issue_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "M-")
    private Date issueDate;
    
    @Column(name = "validUpto_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "M-")
    private Date validUptoDate;
    
    @Column(name="created_id", updatable = false)
	private String createdId;
	
	@Column(name="creation_timestamp",updatable = false, insertable = true)
	private Date creationTimestamp;
	
	@Column(name="lastupdated_id")
	private String	lastUpdatedId;
	
	@Column(name="lastupdated_timestamp")
	private Date lastupdatedTimestamp;
    
    public Project getProjectId() {
        return projectId;
    }
    
    public void setProjectId(Project projectId) {
        this.projectId = projectId;
    }
    
    public Integer getCost() {
        return cost;
    }
    
    public void setCost(Integer cost) {
        this.cost = cost;
    }
    
    public String getAccountName() {
        return accountName;
    }
    
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    
    public String getPoNumber() {
        return poNumber;
    }
    
    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }
    
    public Date getIssueDate() {
        return issueDate;
    }
    
    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }
    
    public Date getValidUptoDate() {
        return validUptoDate;
    }
    
    public void setValidUptoDate(Date validUptoDate) {
        this.validUptoDate = validUptoDate;
    }
    
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

	public String getCreatedId() {
		return createdId;
	}

	public void setCreatedId(String createdId) {
		this.createdId = createdId;
	}

	public Date getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public String getLastUpdatedId() {
		return lastUpdatedId;
	}

	public void setLastUpdatedId(String lastUpdatedId) {
		this.lastUpdatedId = lastUpdatedId;
	}

	public Date getLastupdatedTimestamp() {
		return lastupdatedTimestamp;
	}

	public void setLastupdatedTimestamp(Date lastupdatedTimestamp) {
		this.lastupdatedTimestamp = lastupdatedTimestamp;
	}
    
}
