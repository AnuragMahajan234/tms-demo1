package org.yash.rms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;


@SuppressWarnings("serial")
@Entity
@Table(name = "BGAdmin_Access_Rights")
public class BGAdmin_Access_Rights {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
		public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

		/*@Column(name = "org_id")
	    private OrgHierarchy orgId;
	    
	    
		@Column(name = "resource_id")
	    private Resource resourceId;*/
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "org_id", referencedColumnName = "id")
	  @JsonBackReference
    private OrgHierarchy orgId;
    
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resource_id", referencedColumnName = "employee_id")
	  @JsonBackReference
    private Resource resourceId;
	
	@Column(name = "status")
	private int status;
	
	    public OrgHierarchy getOrgId() {
	        return orgId;
	    }
	    
	    public void setOrgId(OrgHierarchy orgId) {
	        this.orgId = orgId;
	    }
	    
	    public Resource getResourceId() {
	        return resourceId;
	    }
	    
	    public void setResourceId(Resource resourceId) {
	        this.resourceId = resourceId;
	    }

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

}
