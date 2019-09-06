package org.yash.rms.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.yash.rms.util.Constants;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

@Entity
@Table(name = "solution_review_ost")
public class SolutionReview implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3608801893156658311L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "ca_ticket_id")
	private CATicket caTicket;

	@Column(name = "landscape")
	private String landscape;

	@Column(name = "module")
	private Date module;

	@Column(name = "reviewer")
	private String reviewer;

	@Column(name = "review_date")
	private Date reviewDate;

	@Column(name = "ca_ticket_number")
	private BigInteger caTicketNumber;

	@Column(name = "assignee")
	private String assignee;

	@Column(name = "is_the_issue_understanding_correct")
	private String isTheIssueUnderstandingCorrect;

	@Column(name = "alternate_solution")
	private String alternateSolution;

	@Column(name = "is_sol_appropriate")
	private String isSolAppropriate;

	@Column(name = "agree_with_rca")
	private String agreeWithRca;

	@Column(name = "rating")
	private String rating;

	@Column(name = "comments")
	private String comments;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CATicket getCaTicket() {
		return caTicket;
	}

	public void setCaTicket(CATicket caTicket) {
		this.caTicket = caTicket;
	}

	public String getLandscape() {
		return landscape;
	}

	public void setLandscape(String landscape) {
		this.landscape = landscape;
	}

	public Date getModule() {
		return module;
	}

	public void setModule(Date module) {
		this.module = module;
	}

	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public BigInteger getCaTicketNumber() {
		return caTicketNumber;
	}

	public void setCaTicketNumber(BigInteger caTicketNumber) {
		this.caTicketNumber = caTicketNumber;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getIsTheIssueUnderstandingCorrect() {
		return isTheIssueUnderstandingCorrect;
	}

	public void setIsTheIssueUnderstandingCorrect(
			String isTheIssueUnderstandingCorrect) {
		this.isTheIssueUnderstandingCorrect = isTheIssueUnderstandingCorrect;
	}

	public String getAlternateSolution() {
		return alternateSolution;
	}

	public void setAlternateSolution(String alternateSolution) {
		this.alternateSolution = alternateSolution;
	}

	public String getIsSolAppropriate() {
		return isSolAppropriate;
	}

	public void setIsSolAppropriate(String isSolAppropriate) {
		this.isSolAppropriate = isSolAppropriate;
	}

	public String getAgreeWithRca() {
		return agreeWithRca;
	}

	public void setAgreeWithRca(String agreeWithRca) {
		this.agreeWithRca = agreeWithRca;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public static String toJsonArray(
			Collection<org.yash.rms.domain.SolutionReview> collection) {
		return new JSONSerializer()
				.include("id", "reviewDate", "isTheIssueUnderstandingCorrect",
						"alternateSolution", "isSolAppropriate",
						"agreeWithRca", "rating", "comments")
				.exclude("*")
				.transform(new DateTransformer(Constants.DATE_PATTERN_NEW),
						Date.class).serialize(collection);
	}
}
