package org.yash.rms.report.dto;

import java.util.Collection;
import java.util.Date;
import org.yash.rms.util.Constants;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

public class PWRReport {

	private String projectName;
	private String employeeName;
	private int employeeID;
	private String BuName;
	private Date startDate;
	private String allocationType;
	private Long total;

	private String billableFullTime;
	private String billablePartiaSharedpoolFixbidprojects;

	private String nonBillableAbsconding;
	private String nonBillableAccountManagement;
	private String nonBillableBlocked;
	private String nonBillableContingent;
	private String nonBillableDeliveryManagement;
	private String nonBillableInsidesale;
	private String nonBillableInvestment;
	private String nonBillableLongleave;

	private String nonBillableManagement;
	private String nonBillableOperations;
	private String nonBillablePmo;
	private String nonBillablePresale;
	private String nonBillableSales;
	private String nonBillableShadow;
	private String nonBillableTrainee;
	private String nonBillableTransition;
	private String nonBillableUnallocated;
	private String nonBillableExitrelease;

	private String pip;

	private Long billableFullTimeCount = (long) 0;
	private Long billablePartiaSharedpoolFixbidprojectsCount = (long) 0;

	private Long nonBillableAbscondingCount = (long) 0;
	private Long nonBillableAccountManagementCount = (long) 0;
	private Long nonBillableBlockedCount = (long) 0;
	private Long nonBillableContingentCount = (long) 0;
	private Long nonBillableDeliveryManagementCount = (long) 0;
	private Long nonBillableInsidesaleCount = (long) 0;
	private Long nonBillableInvestmentCount = (long) 0;
	private Long nonBillableLongleaveCount = (long) 0;

	private Long nonBillableManagementCount = (long) 0;
	private Long nonBillableOperationsCount = (long) 0;
	private Long nonBillablePmoCount = (long) 0;
	private Long nonBillablePresaleCount = (long) 0;
	private Long nonBillableSalesCount = (long) 0;
	private Long nonBillableShadowCount = (long) 0;
	private Long nonBillableTraineeCount = (long) 0;
	private Long nonBillableTransitionCount = (long) 0;
	private Long nonBillableUnallocatedCount = (long) 0;
	private Long nonBillableExitreleaseCount = (long) 0;

	private Long pipCount = (long) 0;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getAllocationType() {
		return allocationType;
	}

	public void setAllocationType(String allocationType) {
		this.allocationType = allocationType;
	}

	public String getBillableFullTime() {
		return billableFullTime;
	}

	public void setBillableFullTime(String billableFullTime) {
		this.billableFullTime = billableFullTime;
	}

	public String getBillablePartiaSharedpoolFixbidprojects() {
		return billablePartiaSharedpoolFixbidprojects;
	}

	public void setBillablePartiaSharedpoolFixbidprojects(
			String billablePartiaSharedpoolFixbidprojects) {
		this.billablePartiaSharedpoolFixbidprojects = billablePartiaSharedpoolFixbidprojects;
	}

	public String getNonBillableAbsconding() {
		return nonBillableAbsconding;
	}

	public void setNonBillableAbsconding(String nonBillableAbsconding) {
		this.nonBillableAbsconding = nonBillableAbsconding;
	}

	public String getNonBillableAccountManagement() {
		return nonBillableAccountManagement;
	}

	public void setNonBillableAccountManagement(
			String nonBillableAccountManagement) {
		this.nonBillableAccountManagement = nonBillableAccountManagement;
	}

	public String getNonBillableBlocked() {
		return nonBillableBlocked;
	}

	public void setNonBillableBlocked(String nonBillableBlocked) {
		this.nonBillableBlocked = nonBillableBlocked;
	}

	public String getNonBillableContingent() {
		return nonBillableContingent;
	}

	public void setNonBillableContingent(String nonBillableContingent) {
		this.nonBillableContingent = nonBillableContingent;
	}

	public String getNonBillableDeliveryManagement() {
		return nonBillableDeliveryManagement;
	}

	public void setNonBillableDeliveryManagement(
			String nonBillableDeliveryManagement) {
		this.nonBillableDeliveryManagement = nonBillableDeliveryManagement;
	}

	public String getNonBillableInsidesale() {
		return nonBillableInsidesale;
	}

	public void setNonBillableInsidesale(String nonBillableInsidesale) {
		this.nonBillableInsidesale = nonBillableInsidesale;
	}

	public String getNonBillableInvestment() {
		return nonBillableInvestment;
	}

	public void setNonBillableInvestment(String nonBillableInvestment) {
		this.nonBillableInvestment = nonBillableInvestment;
	}

	public String getNonBillableLongleave() {
		return nonBillableLongleave;
	}

	public void setNonBillableLongleave(String nonBillableLongleave) {
		this.nonBillableLongleave = nonBillableLongleave;
	}

	public String getNonBillableManagement() {
		return nonBillableManagement;
	}

	public void setNonBillableManagement(String nonBillableManagement) {
		this.nonBillableManagement = nonBillableManagement;
	}

	public String getNonBillableOperations() {
		return nonBillableOperations;
	}

	public void setNonBillableOperations(String nonBillableOperations) {
		this.nonBillableOperations = nonBillableOperations;
	}

	public String getNonBillablePmo() {
		return nonBillablePmo;
	}

	public void setNonBillablePmo(String nonBillablePmo) {
		this.nonBillablePmo = nonBillablePmo;
	}

	public String getNonBillablePresale() {
		return nonBillablePresale;
	}

	public void setNonBillablePresale(String nonBillablePresale) {
		this.nonBillablePresale = nonBillablePresale;
	}

	public String getNonBillableSales() {
		return nonBillableSales;
	}

	public void setNonBillableSales(String nonBillableSales) {
		this.nonBillableSales = nonBillableSales;
	}

	public String getNonBillableShadow() {
		return nonBillableShadow;
	}

	public void setNonBillableShadow(String nonBillableShadow) {
		this.nonBillableShadow = nonBillableShadow;
	}

	public String getNonBillableTrainee() {
		return nonBillableTrainee;
	}

	public void setNonBillableTrainee(String nonBillableTrainee) {
		this.nonBillableTrainee = nonBillableTrainee;
	}

	public String getNonBillableTransition() {
		return nonBillableTransition;
	}

	public void setNonBillableTransition(String nonBillableTransition) {
		this.nonBillableTransition = nonBillableTransition;
	}

	public String getNonBillableUnallocated() {
		return nonBillableUnallocated;
	}

	public void setNonBillableUnallocated(String nonBillableUnallocated) {
		this.nonBillableUnallocated = nonBillableUnallocated;
	}

	public String getNonBillableExitrelease() {
		return nonBillableExitrelease;
	}

	public void setNonBillableExitrelease(String nonBillableExitrelease) {
		this.nonBillableExitrelease = nonBillableExitrelease;
	}

	public String getPip() {
		return pip;
	}

	public void setPip(String pip) {
		this.pip = pip;
	}

	public Long getBillableFullTimeCount() {
		return billableFullTimeCount;
	}

	public void setBillableFullTimeCount(Long billableFullTimeCount) {
		this.billableFullTimeCount = billableFullTimeCount;
	}

	public Long getBillablePartiaSharedpoolFixbidprojectsCount() {
		return billablePartiaSharedpoolFixbidprojectsCount;
	}

	public void setBillablePartiaSharedpoolFixbidprojectsCount(
			Long billablePartiaSharedpoolFixbidprojectsCount) {
		this.billablePartiaSharedpoolFixbidprojectsCount = billablePartiaSharedpoolFixbidprojectsCount;
	}

	public Long getNonBillableAbscondingCount() {
		return nonBillableAbscondingCount;
	}

	public void setNonBillableAbscondingCount(Long nonBillableAbscondingCount) {
		this.nonBillableAbscondingCount = nonBillableAbscondingCount;
	}

	public Long getNonBillableAccountManagementCount() {
		return nonBillableAccountManagementCount;
	}

	public void setNonBillableAccountManagementCount(
			Long nonBillableAccountManagementCount) {
		this.nonBillableAccountManagementCount = nonBillableAccountManagementCount;
	}

	public Long getNonBillableBlockedCount() {
		return nonBillableBlockedCount;
	}

	public void setNonBillableBlockedCount(Long nonBillableBlockedCount) {
		this.nonBillableBlockedCount = nonBillableBlockedCount;
	}

	public Long getNonBillableContingentCount() {
		return nonBillableContingentCount;
	}

	public void setNonBillableContingentCount(Long nonBillableContingentCount) {
		this.nonBillableContingentCount = nonBillableContingentCount;
	}

	public Long getNonBillableDeliveryManagementCount() {
		return nonBillableDeliveryManagementCount;
	}

	public void setNonBillableDeliveryManagementCount(
			Long nonBillableDeliveryManagementCount) {
		this.nonBillableDeliveryManagementCount = nonBillableDeliveryManagementCount;
	}

	public Long getNonBillableInsidesaleCount() {
		return nonBillableInsidesaleCount;
	}

	public void setNonBillableInsidesaleCount(Long nonBillableInsidesaleCount) {
		this.nonBillableInsidesaleCount = nonBillableInsidesaleCount;
	}

	public Long getNonBillableInvestmentCount() {
		return nonBillableInvestmentCount;
	}

	public void setNonBillableInvestmentCount(Long nonBillableInvestmentCount) {
		this.nonBillableInvestmentCount = nonBillableInvestmentCount;
	}

	public Long getNonBillableLongleaveCount() {
		return nonBillableLongleaveCount;
	}

	public void setNonBillableLongleaveCount(Long nonBillableLongleaveCount) {
		this.nonBillableLongleaveCount = nonBillableLongleaveCount;
	}

	public Long getNonBillableManagementCount() {
		return nonBillableManagementCount;
	}

	public void setNonBillableManagementCount(Long nonBillableManagementCount) {
		this.nonBillableManagementCount = nonBillableManagementCount;
	}

	public Long getNonBillableOperationsCount() {
		return nonBillableOperationsCount;
	}

	public void setNonBillableOperationsCount(Long nonBillableOperationsCount) {
		this.nonBillableOperationsCount = nonBillableOperationsCount;
	}

	public Long getNonBillablePmoCount() {
		return nonBillablePmoCount;
	}

	public void setNonBillablePmoCount(Long nonBillablePmoCount) {
		this.nonBillablePmoCount = nonBillablePmoCount;
	}

	public Long getNonBillablePresaleCount() {
		return nonBillablePresaleCount;
	}

	public void setNonBillablePresaleCount(Long nonBillablePresaleCount) {
		this.nonBillablePresaleCount = nonBillablePresaleCount;
	}

	public Long getNonBillableSalesCount() {
		return nonBillableSalesCount;
	}

	public void setNonBillableSalesCount(Long nonBillableSalesCount) {
		this.nonBillableSalesCount = nonBillableSalesCount;
	}

	public Long getNonBillableShadowCount() {
		return nonBillableShadowCount;
	}

	public void setNonBillableShadowCount(Long nonBillableShadowCount) {
		this.nonBillableShadowCount = nonBillableShadowCount;
	}

	public Long getNonBillableTraineeCount() {
		return nonBillableTraineeCount;
	}

	public void setNonBillableTraineeCount(Long nonBillableTraineeCount) {
		this.nonBillableTraineeCount = nonBillableTraineeCount;
	}

	public Long getNonBillableTransitionCount() {
		return nonBillableTransitionCount;
	}

	public void setNonBillableTransitionCount(Long nonBillableTransitionCount) {
		this.nonBillableTransitionCount = nonBillableTransitionCount;
	}

	public Long getNonBillableUnallocatedCount() {
		return nonBillableUnallocatedCount;
	}

	public void setNonBillableUnallocatedCount(Long nonBillableUnallocatedCount) {
		this.nonBillableUnallocatedCount = nonBillableUnallocatedCount;
	}

	public Long getNonBillableExitreleaseCount() {
		return nonBillableExitreleaseCount;
	}

	public void setNonBillableExitreleaseCount(Long nonBillableExitreleaseCount) {
		this.nonBillableExitreleaseCount = nonBillableExitreleaseCount;
	}

	public Long getPipCount() {
		return pipCount;
	}

	public void setPipCount(Long pipCount) {
		this.pipCount = pipCount;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public String getBuName() {
		return BuName;
	}

	public void setBuName(String buName) {
		BuName = buName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public static String toJsonArray(Collection<PWRReport> collection) {
		return new JSONSerializer()
				.include("projectName", "employeeID", "BuName", "startDate",
						"billableFullTime",
						"billablePartiaSharedpoolFixbidprojects",
						"nonBillableAbsconding",
						"nonBillableAccountManagement", "nonBillableBlocked",
						"nonBillableContingent",
						"nonBillableDeliveryManagement",
						"nonBillableInsidesale", "nonBillableInvestment",
						"nonBillableLongleave", "nonBillableManagement",
						"nonBillableOperations", "nonBillablePmo",
						"nonBillablePresale", "nonBillableSales",
						"nonBillableShadow", "nonBillableTrainee",
						"nonBillableTransition", "nonBillableUnallocated",
						"nonBillableExitrelease", "nonbillableExitrelease",
						"employeeName", "pip", "billableFullTimeCount",
						"billablePartiaSharedpoolFixbidprojectsCount",

						"nonBillableAbscondingCount",
						"nonBillableAccountManagementCount",
						"nonBillableBlockedCount",
						"nonBillableContingentCount",
						"nonBillableDeliveryManagementCount",
						"nonBillableInsidesaleCount",
						"nonBillableInvestmentCount",
						"nonBillableLongleaveCount",

						"nonBillableManagementCount",
						"nonBillableOperationsCount", "nonBillablePmoCount",
						"nonBillablePresaleCount", "nonBillableSalesCount",
						"nonBillableShadowCount", "nonBillableTraineeCount",
						"nonBillableTransitionCount",
						"nonBillableUnallocatedCount",
						"nonBillableExitreleaseCount",
						"nonbillableExitreleaseCount", "pipCount", "total")
				.exclude("*")
				.transform(new DateTransformer(Constants.DATE_PATTERN),
						Date.class).serialize(collection);
	}

	public static String toJson(PWRReport pwrReport) {
		return new JSONSerializer()
				.include("projectName", "employeeID", "BuName", "startDate",
						"billableFullTime",
						"billablePartiaSharedpoolFixbidprojects",

						"nonBillableAbsconding",
						"nonBillableAccountManagement", "nonBillableBlocked",
						"nonBillableContingent",
						"nonBillableDeliveryManagement",
						"nonBillableInsidesale", "nonBillableInvestment",
						"nonBillableLongleave", "nonBillableManagement",
						"nonBillableOperations", "nonBillablePmo",
						"nonBillablePresale", "nonBillableSales",
						"nonBillableShadow", "nonBillableTrainee",
						"nonBillableTransition", "nonBillableUnallocated",
						"nonBillableExitrelease", "nonbillableExitrelease",
						"employeeName", "pip", "billableFullTimeCount",
						"billablePartiaSharedpoolFixbidprojectsCount",

						"nonBillableAbscondingCount",
						"nonBillableAccountManagementCount",
						"nonBillableBlockedCount",
						"nonBillableContingentCount",
						"nonBillableDeliveryManagementCount",
						"nonBillableInsidesaleCount",
						"nonBillableInvestmentCount",
						"nonBillableLongleaveCount",

						"nonBillableManagementCount",
						"nonBillableOperationsCount", "nonBillablePmoCount",
						"nonBillablePresaleCount", "nonBillableSalesCount",
						"nonBillableShadowCount", "nonBillableTraineeCount",
						"nonBillableTransitionCount",
						"nonBillableUnallocatedCount",
						"nonBillableExitreleaseCount",
						"nonbillableExitreleaseCount", "pipCount", "total")
				.exclude("*")
				.transform(new DateTransformer(Constants.DATE_PATTERN),
						Date.class).serialize(pwrReport);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((projectName == null) ? 0 : projectName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PWRReport))
			return false;
		PWRReport other = (PWRReport) obj;
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
			return false;
		return true;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

}
