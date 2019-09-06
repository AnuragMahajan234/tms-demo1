package org.yash.rms.rest.utils;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="result")
public class AdvanceSearchResult<Entity> {
  public static final String leftBrace="{";
	  public static final String rightBrace="}";
	
	/**
	 * represents the upper limit on the number of records fetched.
	 */
	private int upperBound;
	/**
	 * represents the lower limit on the number of records fetched.
	 */
	private int lowerBound;
	/**
	 * represents the total records found for the given query.
	 */
	private Long totalRecords;
	/**
	 * represents the list of actual records fetched from the persistence store.
	 */
	private List<Entity> results;
	/**
	 * @return returns the upper limit on the number of records fetched.
	 */
	public int getUpperBound() {
		return upperBound;
	}
	/**
	 * sets the the upper limit on the number of records fetched.
	 * @param upperBound
	 */
	public void setUpperBound(int upperBound) {
		this.upperBound = upperBound;
	}
	/**
	 * @return returns the lower limit on the number of records fetched.
	 */
	public int getLowerBound() {
		return lowerBound;
	}
	/**
	 * sets the lower limit on the number of records fetched.
	 * @param lowerBound 
	 */
	public void setLowerBound(int lowerBound) {
		this.lowerBound = lowerBound;
	}
	/**
	 * @return returns the total records found for the given query.
	 */
	public Long getTotalRecords() {
		return totalRecords;
	}
	/**
	 * sets the total records found for the given query.
	 * @param totalRecords
	 */
	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}
	/**
	 * @return returns the list of actual records fetched from the persistence store.
	 */
	public List<Entity> getResults() {
		return results;
	}
	/**
	 * sets the list of actual records fetched from the persistence store.
	 * @param results
	 */
	public void setResults(List<Entity> results) {
		this.results = results;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(leftBrace).
		append("UpperBound:").append(getUpperBound()).
		append(",LowerBound:").append(getLowerBound()).
		append(",TotalRecords:").append(getTotalRecords()).
		append(",Result:").append(getResults()).
		append(rightBrace);
		return builder.toString();
	}
}
