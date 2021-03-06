package org.yash.rms.util;

import java.util.HashMap;
import java.util.Map;

public class ProjectAllocationSearchCriteria {

  Map<String, String> colandTableNameMap = new HashMap<String, String>();

  String[] cols;

  String refTableName;

  String refColName;

  String searchValue;

  String sortTableName;

  String tableColumnForSort;

  String baseTableCol;

  String orderStyle;

  String orederBy;
  
  public ProjectAllocationSearchCriteria() {

    colandTableNameMap.put("offshoreDelMgr", "offshoreManager");
    colandTableNameMap.put("deliveryMgr", "deliveryManager");
    colandTableNameMap.put("customerName", "customer");
    colandTableNameMap.put("BuCode", "BuCode");
    colandTableNameMap.put("engagementMode", "engagementModel");
    colandTableNameMap.put("plannedProjectSize", "plannedProjectSize");
    cols = new String[] {"id", "projectCode", "projectName", "customerName", "offshoreDelMgr",
        "onsiteDelMgr", "BuCode", "engagementMode", "projectKickOff", "plannedProjectSize",
        "projectEndDate","deliveryMgr"};
  }

  public Map<String, String> getColandTableNameMap() {
    return colandTableNameMap;
  }

  public void setColandTableNameMap(Map<String, String> colandTableNameMap) {
    this.colandTableNameMap = colandTableNameMap;
  }

  public String[] getCols() {
    return cols;
  }

  public void setCols(String[] cols) {
    this.cols = cols;
  }

  public String getRefTableName() {
    return refTableName;
  }

  public void setRefTableName(String refTableName) {
    this.refTableName = refTableName;
  }

  public String getRefColName() {
    return refColName;
  }

  public void setRefColName(String refColName) {
    this.refColName = refColName;
  }

  public String getSearchValue() {
    return searchValue;
  }

  public void setSearchValue(String searchValue) {
    this.searchValue = searchValue;
  }

  public String getSortTableName() {
    return sortTableName;
  }

  public void setSortTableName(String sortTableName) {
    this.sortTableName = sortTableName;
  }

  public String getTableColumnForSort() {
    return tableColumnForSort + "Id";
  }

  public void setTableColumnForSort(String tableColumnForSort) {
    this.tableColumnForSort = tableColumnForSort;
  }

  public String getBaseTableCol() {
    if ("offshoreManager".equals(refTableName)) {
      return "offshoreDelMgr";
    } else if ("customer".equals(refTableName)) {
      return "customerName";
    } else if ("BuCode".equals(refTableName)) {
      return "BuCode";
    } else if ("engagementMode".equals(refTableName)) {
      return "engagementModelId";
    } else if ("plannedProjectSize".equals(refTableName)) {
      return "plannedProjSize";
    } else if ("deliveryManager".equals(refTableName)) {
        return "deliveryMgr";
    }else {
      return refTableName + "Id";
    }
  }

  public void setBaseTableCol(String baseTableCol) {
    this.baseTableCol = baseTableCol;
  }

  public String getOrderStyle() {
    return orderStyle;
  }

  public void setOrderStyle(String orderStyle) {
    this.orderStyle = orderStyle;
  }

  public String getOrederBy() {
    return orederBy;
  }

  public void setOrederBy(String orederBy) {
    this.orederBy = orederBy;
  }

  public Integer getIntegerValue() {
    Integer intValue = 0;
    if (refColName.equals("plannedCapacity") || refColName.equals("actualCapacity")) {
      intValue = Integer.parseInt(getSearchValue());
    }
    return intValue;
  }


}
