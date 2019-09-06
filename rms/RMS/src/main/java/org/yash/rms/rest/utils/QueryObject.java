package org.yash.rms.rest.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.jaxrs.ext.search.ConditionType;


public class QueryObject implements Cloneable {
	/**
	 * represents the sorting order ascending or descending.
	 */
	public static enum SortOrder{
 /** The asc. */
 ASC,
 /** The desc. */
 DESC}
	/**
	 * represents the mode of comparing when searching.
	 */
	public static enum SearchMode{
/** The like. */
LIKE, 
/** The equal. */
EQUAL,
/** The not equal. */
 NOT_EQUAL, 
/** The is null. */
IS_NULL, 
/** The between. */
BETWEEN, 
/** The in. */
IN,
/** The greater than. */
GREATER_THAN
	
, /** The less than. */
 LESS_THAN,
/** The greater or equal. */
GREATER_OR_EQUAL

,/** The less or equal. */ 
LESS_OR_EQUAL;

   /**
	 * Gets the search mode.
	 *
	 * @param conditionType the condition type
	 * @return the search mode
	 */
	public static SearchMode getSearchMode(ConditionType conditionType){
		SearchMode mode = EQUAL;	
		switch(conditionType){
			 case EQUALS:
				 mode = EQUAL;
				 break;
			 case NOT_EQUALS:
				 mode = NOT_EQUAL;
				 break;
			 case LESS_THAN:
				 mode = LESS_THAN;
				 break;
			 case GREATER_THAN:
				 mode = GREATER_THAN;
				 break;
			 case LESS_OR_EQUALS:
				 mode = LESS_OR_EQUAL;
				 break;
			 case GREATER_OR_EQUALS:
				 mode = GREATER_OR_EQUAL;
			 break;
			 case OR:
			 case AND:
			 case CUSTOM:
			}
		return mode ;
		}
	}
	/**
	 * represents the search mode for specified field name.
	 */
	private Map<String, SearchMode> fieldNameModeMapping = new HashMap<String, QueryObject.SearchMode>();
	/**
	 * represents the place holder for searched values mapped with respective field name. 
	 */
	private Map<String, Object> fieldNameValueMapping = new HashMap<String, Object>();
	
	/** The filter wrapper. */
	private List<SearchFilterWrapper> filterWrapper = new ArrayList<SearchFilterWrapper>();
	
	/**
	 * Adds the value for filter wrapper.
	 *
	 * @param wrapper the wrapper
	 */
	public void addValueForFilterWrapper(SearchFilterWrapper wrapper){
		filterWrapper.add(wrapper);
	}
	
	/**
	 * Gets the search filter wrapper list.
	 *
	 * @return the search filter wrapper list
	 */
	public List<SearchFilterWrapper> getSearchFilterWrapperList(){
		return filterWrapper;
	}
	/**
	 * represents the upper limit for pagination.
	 */
	private int paginationUpperLimit = -1;
	/**
	 * represents the lower limit for pagination.
	 */
	private int paginationLowerLimit = -1;
	/**
	 * represents the ordering of results for different column.
	 */
	private Map<String, SortOrder> orderByMode;
	/**
	 * This query depends on isPaged() API. The property of this API 
	 * shall only be used when isPaged returns true.
	 * @return returns the last item index. 
	 */
	public int getPaginationUpperLimit(){
		return paginationUpperLimit;
	}
	/**
	 * This query depends on isPaged() API. The property of this API 
	 * shall only be used when isPaged returns true.
	 * @return returns the first item index.
	 */
	public int getPaginationLowerLimit(){
		return paginationLowerLimit;
	}
	/**
	 * represents an order pair of min and max value.
	 */
	public static class MinMax implements Cloneable{
		
		/** minimum value. */
		private long min;
		
		/** maximum value. */
		private long max;
		
		/**
		 * Gets the min.
		 *
		 * @return the min
		 */
		
		public long getMin() {
			return min;
		}
		
		/**
		 * Sets the min.
		 *
		 * @param min the new min
		 */
		public void setMin(long min) {
			this.min = min;
		}
		
		/**
		 * Gets the max.
		 *
		 * @return the max
		 */
		public long getMax() {
			return max;
		}
		
		/**
		 * Sets the max.
		 *
		 * @param max the new max
		 */
		public void setMax(long max) {
			this.max = max;
		}
		
		/**
		 * Instantiates a new min max.
		 *
		 * @param min the min
		 * @param max the max
		 */
		public MinMax(long min, long max) {
			super();
			this.min = min;
			this.max = max;
		}

	}
	
	/**
	 * Gets the value by field name.
	 *
	 * @param key represents the field name.
	 * @return returns search value for the given field name
	 */
	public Object getValueByFieldName(String key){
		if(this.fieldNameValueMapping == null)
		{
			return null;
			}
		else
		{
			return this.fieldNameValueMapping.get(key);
			}
	}
	/**
	 * Sets the value to be looked up for a given field.
	 * @param fieldName represents the field name on which result needs to be filtered.
	 * @param object represents the associated value to be looked up.
	 */
	public void setValueForFieldName(String fieldName, Object object){
		if(this.fieldNameValueMapping == null)
		{
			this.fieldNameValueMapping = new HashMap<String, Object>();
		}
		this.fieldNameValueMapping.put(fieldName, object);
	}
	
	/**
	 * Gets the mode by field name.
	 *
	 * @param key represents the field name.
	 * @return returns the search mode by which the value needs to be compared.
	 */
	public SearchMode getModeByFieldName(String key){
		if(this.fieldNameModeMapping == null)
		{
			return null;
			}
		else
		{
			return this.fieldNameModeMapping.get(key);
			}
	}
	/**
	 * sets the search mode for the field.
	 *
	 * @param fieldName the field name
	 * @param mode the mode
	 */
	public void setModeForFieldName(String fieldName, SearchMode mode){
		if(this.fieldNameModeMapping == null)
		{
			this.fieldNameModeMapping = new HashMap<String, SearchMode>();
			}
		this.fieldNameModeMapping.put(fieldName, mode);
	}
	/**
	 * adds an order by clause for a particular field.
	 *
	 * @param fieldName the field name
	 * @param order the order
	 */
	public void addOrderingClause(String fieldName, SortOrder order){
		if(getOrderByMode()==null){
			setOrderByMode(new HashMap<String, QueryObject.SortOrder>());
		}
		getOrderByMode().put(fieldName, order);
	}
	
	/**
	 * Gets the field name mode mapping.
	 *
	 * @return returns the field and search mode mapping as Map.
	 */
	public Map<String, SearchMode> getFieldNameModeMapping() {
		return fieldNameModeMapping;
	}
	
	/**
	 * sets the field search mode mapping for this query object.
	 *
	 * @param fieldNameModeMapping the field name mode mapping
	 */
	public void setFieldNameModeMapping(Map<String, SearchMode> fieldNameModeMapping) {
		this.fieldNameModeMapping = fieldNameModeMapping;
	}
	
	/**
	 * Gets the field name value mapping.
	 *
	 * @return returns the field and there respective values as Map.
	 */
	public Map<String, Object> getFieldNameValueMapping() {
		return fieldNameValueMapping;
	}
	
	/**
	 * sets the field and with there respective values.
	 *
	 * @param fieldNameValueMapping the field name value mapping
	 */
	public void setFieldNameValueMapping(Map<String, Object> fieldNameValueMapping) {
		this.fieldNameValueMapping = fieldNameValueMapping;
	}
	
	/**
	 * Gets the order by mode.
	 *
	 * @return the order by mode
	 */
	public Map<String, SortOrder> getOrderByMode() {
		return orderByMode;
	}
	
	/**
	 * sets the upper bound limit of pagination.
	 *
	 * @param paginationUpperLimit the new pagination upper limit
	 */
	public void setPaginationUpperLimit(int paginationUpperLimit) {
		this.paginationUpperLimit = paginationUpperLimit;
	}
	
	/**
	 * sets the lower bound limit of pagination.
	 *
	 * @param paginationLowerLimit the new pagination lower limit
	 */
	public void setPaginationLowerLimit(int paginationLowerLimit) {
		this.paginationLowerLimit = paginationLowerLimit;
	}
	
	/**
	 * sets the field and its respective order.
	 *
	 * @param orderByMode the order by mode
	 */
	public void setOrderByMode(Map<String, SortOrder> orderByMode) {
		this.orderByMode = orderByMode;
	}
	
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		String eol = System.getProperty("line.separator");
		return "FieldName-Mode Mapping " + getFieldNameModeMapping() +eol+
		"FieldName-Value Mapping " + getFieldNameValueMapping() +eol+
		"Pagination Lower Limit"+getPaginationLowerLimit() +eol+
		"Pagination Upper Limit"+getPaginationUpperLimit() +eol;
	}
}
