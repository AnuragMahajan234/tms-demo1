package org.yash.rms.dto;

import flexjson.JSONSerializer;

public class PWRReport
{
  private String projectName;
  private String employeeName;
  private int employeeID;
  private String BuName;
  private java.util.Date startDate;
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
  
  public PWRReport() {}
  
  private Long billableFullTimeCount = Long.valueOf(0L);
  private Long billablePartiaSharedpoolFixbidprojectsCount = Long.valueOf(0L);
  
  private Long nonBillableAbscondingCount = Long.valueOf(0L);
  private Long nonBillableAccountManagementCount = Long.valueOf(0L);
  private Long nonBillableBlockedCount = Long.valueOf(0L);
  private Long nonBillableContingentCount = Long.valueOf(0L);
  private Long nonBillableDeliveryManagementCount = Long.valueOf(0L);
  private Long nonBillableInsidesaleCount = Long.valueOf(0L);
  private Long nonBillableInvestmentCount = Long.valueOf(0L);
  private Long nonBillableLongleaveCount = Long.valueOf(0L);
  
  private Long nonBillableManagementCount = Long.valueOf(0L);
  private Long nonBillableOperationsCount = Long.valueOf(0L);
  private Long nonBillablePmoCount = Long.valueOf(0L);
  private Long nonBillablePresaleCount = Long.valueOf(0L);
  private Long nonBillableSalesCount = Long.valueOf(0L);
  private Long nonBillableShadowCount = Long.valueOf(0L);
  private Long nonBillableTraineeCount = Long.valueOf(0L);
  private Long nonBillableTransitionCount = Long.valueOf(0L);
  private Long nonBillableUnallocatedCount = Long.valueOf(0L);
  private Long nonBillableExitreleaseCount = Long.valueOf(0L);
  
  private Long pipCount = Long.valueOf(0L);
  
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
  
  public void setBillablePartiaSharedpoolFixbidprojects(String billablePartiaSharedpoolFixbidprojects)
  {
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
  
  public void setNonBillableAccountManagement(String nonBillableAccountManagement)
  {
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
  
  public void setNonBillableDeliveryManagement(String nonBillableDeliveryManagement)
  {
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
  
  public void setBillablePartiaSharedpoolFixbidprojectsCount(Long billablePartiaSharedpoolFixbidprojectsCount)
  {
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
  
  public void setNonBillableAccountManagementCount(Long nonBillableAccountManagementCount)
  {
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
  
  public void setNonBillableDeliveryManagementCount(Long nonBillableDeliveryManagementCount)
  {
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
  
  public java.util.Date getStartDate() {
    return startDate;
  }
  
  public void setStartDate(java.util.Date startDate) {
    this.startDate = startDate;
  }
  
  public static String toJsonArray(java.util.Collection<PWRReport> collection)
  {
    return 
    


      new JSONSerializer().include(new String[] { "projectName", "employeeID", "BuName", "startDate", "billableFullTime", "billablePartiaSharedpoolFixbidprojects", "nonBillableAbsconding", "nonBillableAccountManagement", "nonBillableBlocked", "nonBillableContingent", "nonBillableDeliveryManagement", "nonBillableInsidesale", "nonBillableInvestment", "nonBillableLongleave", "nonBillableManagement", "nonBillableOperations", "nonBillablePmo", "nonBillablePresale", "nonBillableSales", "nonBillableShadow", "nonBillableTrainee", "nonBillableTransition", "nonBillableUnallocated", "nonBillableExitrelease", "nonbillableExitrelease", "employeeName", "pip", "billableFullTimeCount", "billablePartiaSharedpoolFixbidprojectsCount", "nonBillableAbscondingCount", "nonBillableAccountManagementCount", "nonBillableBlockedCount", "nonBillableContingentCount", "nonBillableDeliveryManagementCount", "nonBillableInsidesaleCount", "nonBillableInvestmentCount", "nonBillableLongleaveCount", "nonBillableManagementCount", "nonBillableOperationsCount", "nonBillablePmoCount", "nonBillablePresaleCount", "nonBillableSalesCount", "nonBillableShadowCount", "nonBillableTraineeCount", "nonBillableTransitionCount", "nonBillableUnallocatedCount", "nonBillableExitreleaseCount", "nonbillableExitreleaseCount", "pipCount", "total" }).exclude(new String[] { "*" }).transform(new flexjson.transformer.DateTransformer("MM/dd/yyyy"), new Class[] { java.util.Date.class }).serialize(collection);
  }
  
  public static String toJson(PWRReport pwrReport)
  {
    return 
    


      new JSONSerializer().include(new String[] { "projectName", "employeeID", "BuName", "startDate", "billableFullTime", "billablePartiaSharedpoolFixbidprojects", "nonBillableAbsconding", "nonBillableAccountManagement", "nonBillableBlocked", "nonBillableContingent", "nonBillableDeliveryManagement", "nonBillableInsidesale", "nonBillableInvestment", "nonBillableLongleave", "nonBillableManagement", "nonBillableOperations", "nonBillablePmo", "nonBillablePresale", "nonBillableSales", "nonBillableShadow", "nonBillableTrainee", "nonBillableTransition", "nonBillableUnallocated", "nonBillableExitrelease", "nonbillableExitrelease", "employeeName", "pip", "billableFullTimeCount", "billablePartiaSharedpoolFixbidprojectsCount", "nonBillableAbscondingCount", "nonBillableAccountManagementCount", "nonBillableBlockedCount", "nonBillableContingentCount", "nonBillableDeliveryManagementCount", "nonBillableInsidesaleCount", "nonBillableInvestmentCount", "nonBillableLongleaveCount", "nonBillableManagementCount", "nonBillableOperationsCount", "nonBillablePmoCount", "nonBillablePresaleCount", "nonBillableSalesCount", "nonBillableShadowCount", "nonBillableTraineeCount", "nonBillableTransitionCount", "nonBillableUnallocatedCount", "nonBillableExitreleaseCount", "nonbillableExitreleaseCount", "pipCount", "total" }).exclude(new String[] { "*" }).transform(new flexjson.transformer.DateTransformer("MM/dd/yyyy"), new Class[] { java.util.Date.class }).serialize(pwrReport);
  }
  
  public int hashCode()
  {
    int prime = 31;
    int result = 1;
    
    result = 31 * result + (projectName == null ? 0 : projectName
      .hashCode());
    return result;
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof PWRReport))
      return false;
    PWRReport other = (PWRReport)obj;
    if (projectName == null) {
      if (projectName != null)
        return false;
    } else if (!projectName.equals(projectName))
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
