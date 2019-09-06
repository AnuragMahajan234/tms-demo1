package org.yash.rms.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@SuppressWarnings("serial")
@Entity
@Table(name="CUSTOMER")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Customer implements Serializable {	
	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private int id; 
	
	//#1202- code revert
	/*@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "account_manager", referencedColumnName = "employee_id")
	@NotNull
	private Resource accountManager;*/
	
	 @Column(name = "account_manager", length = 256)
	    private String accountManager;
	    
	
	
    @Column(name = "account_manager_contact_number")
    private BigDecimal accountManagerContactNumber;
    
    @Column(name = "customer_address", length = 256)
    private String customerAddress;
    
    
    @Column(name = "customer_name", length = 256)
    @NotNull
    private String customerName;
    
	@Column(name = "geography", length = 256)
    @NotNull
    private String geography;
	
	//@Column(name = "stratagic",columnDefinition="boolean default false")
   
    //private Boolean stratagic;
    
	@Column(name = "code", length = 256, unique = true)
    @NotNull
    private String code;
	

    @OneToMany(mappedBy = "customerId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<CustomerPo> customerPoes;

    
    @Column(name="created_id", updatable = false)
	private String createdId;
	
	@Column(name="creation_timestamp",updatable = false, insertable = true)
	private Date creationTimestamp;
	
	@Column(name="lastupdated_id")
	private String	lastUpdatedId;
	
	@Column(name="lastupdated_timestamp")
	private Date lastupdatedTimestamp;

	@Column(name="pdl_email_id")
	private String customerEmail;

	@OneToMany(mappedBy = "customerId", fetch = FetchType.LAZY)
	@JsonBackReference
	private List<CustomerGroup> custGroups;
	
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/*public Boolean getStratagic() {
		return stratagic;
	}

	public void setStratagic(Boolean stratagic) {
		this.stratagic = stratagic;
	}*/

	public Set<CustomerPo> getCustomerPoes() {
		return customerPoes;
	}

	public void setCustomerPoes(Set<CustomerPo> customerPoes) {
		this.customerPoes = customerPoes;
	}
    
	public String getCustomerEmail() {
		return customerEmail;
	}
	
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public BigDecimal getAccountManagerContactNumber() {
		return accountManagerContactNumber;
	}

	public void setAccountManagerContactNumber(
			BigDecimal accountManagerContactNumber) {
		this.accountManagerContactNumber = accountManagerContactNumber;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getGeography() {
		return geography;
	}

	public void setGeography(String geography) {
		this.geography = geography;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getAccountManager() {
		return accountManager;
	}

	public void setAccountManager(String accountManager) {
		this.accountManager = accountManager;
	}

	public List<CustomerGroup> getCustGroups() {
		return custGroups;
	}

	public void setCustGroups(List<CustomerGroup> custGroups) {
		this.custGroups = custGroups;
	}

	
	/*public Resource getAccountManager() {
		return accountManager;
	}

	public void setAccountManager(Resource accountManager) {
		this.accountManager = accountManager;
	}*/

	
	
	

}
