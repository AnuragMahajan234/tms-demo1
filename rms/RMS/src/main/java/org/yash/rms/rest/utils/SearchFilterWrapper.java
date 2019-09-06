package org.yash.rms.rest.utils;

import org.yash.rms.rest.utils.QueryObject.SearchMode;


/**
 * The Class SearchFilterWrapper.
 */
public class SearchFilterWrapper {

	/**
	 * Instantiates a new search filter wrapper.
	 *
	 * @param fieldName the field name
	 * @param mode the mode
	 * @param value the value
	 */
	public SearchFilterWrapper(String fieldName, SearchMode mode, Object value) {
		super();
		this.fieldName = fieldName;
		this.mode = mode;
		this.value = value;
	}

	/** The field name. */
	private String fieldName;
	
	/** The mode. */
	private SearchMode mode;
	
	/** The value. */
	private Object value;

	/**
	 * Gets the field name.
	 *
	 * @return the field name
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * Sets the field name.
	 *
	 * @param fieldName the new field name
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * Gets the mode.
	 *
	 * @return the mode
	 */
	public SearchMode getMode() {
		return mode;
	}

	/**
	 * Sets the mode.
	 *
	 * @param mode the new mode
	 */
	public void setMode(SearchMode mode) {
		this.mode = mode;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(Object value) {
		this.value = value;
	}
}
