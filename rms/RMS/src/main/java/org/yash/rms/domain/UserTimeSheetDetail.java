package org.yash.rms.domain;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "user_activity_detail")
public class UserTimeSheetDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private BigInteger id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "time_sheet_id", referencedColumnName = "id")
	private UserTimeSheet timeSheetId;

	@Column(name = "d1_hours", precision = 22, scale = 2)
	private Double d1Hours;

	@Column(name = "d2_hours", precision = 22, scale = 2)
	private Double d2Hours;

	@Column(name = "d3_hours", precision = 22, scale = 2)
	private Double d3Hours;

	@Column(name = "d4_hours", precision = 22, scale = 2)
	private Double d4Hours;

	@Column(name = "d5_hours", precision = 22, scale = 2)
	private Double d5Hours;

	@Column(name = "d6_hours", precision = 22, scale = 2)
	private Double d6Hours;

	@Column(name = "d7_hours", precision = 22, scale = 2)
	private Double d7Hours;

	@Column(name = "d1_comment", length = 256)
	private String d1Comment;

	@Column(name = "d2_comment", length = 256)
	private String d2Comment;

	@Column(name = "d3_comment", length = 256)
	private String d3Comment;

	@Column(name = "d4_comment", length = 256)
	private String d4Comment;

	@Column(name = "d5_comment", length = 256)
	private String d5Comment;

	@Column(name = "d6_comment", length = 256)
	private String d6Comment;

	@Column(name = "d7_comment", length = 256)
	private String d7Comment;
	@Column(name = "week_start_date")
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	private Date weekStartDate;

	public Double getD1Hours() {
		return d1Hours;
	}

	public void setD1Hours(Double d1Hours) {
		this.d1Hours = d1Hours;
	}

	public Double getD2Hours() {
		return d2Hours;
	}

	public void setD2Hours(Double d2Hours) {
		this.d2Hours = d2Hours;
	}

	public Double getD3Hours() {
		return d3Hours;
	}

	public void setD3Hours(Double d3Hours) {
		this.d3Hours = d3Hours;
	}

	public Double getD4Hours() {
		return d4Hours;
	}

	public void setD4Hours(Double d4Hours) {
		this.d4Hours = d4Hours;
	}

	public Double getD5Hours() {
		return d5Hours;
	}

	public void setD5Hours(Double d5Hours) {
		this.d5Hours = d5Hours;
	}

	public Double getD6Hours() {
		return d6Hours;
	}

	public void setD6Hours(Double d6Hours) {
		this.d6Hours = d6Hours;
	}

	public Double getD7Hours() {
		return d7Hours;
	}

	public void setD7Hours(Double d7Hours) {
		this.d7Hours = d7Hours;
	}

	public String getD1Comment() {
		return d1Comment;
	}

	public void setD1Comment(String d1Comment) {
		this.d1Comment = d1Comment;
	}

	public String getD2Comment() {
		return d2Comment;
	}

	public void setD2Comment(String d2Comment) {
		this.d2Comment = d2Comment;
	}

	public String getD3Comment() {
		return d3Comment;
	}

	public void setD3Comment(String d3Comment) {
		this.d3Comment = d3Comment;
	}

	public String getD4Comment() {
		return d4Comment;
	}

	public void setD4Comment(String d4Comment) {
		this.d4Comment = d4Comment;
	}

	public String getD5Comment() {
		return d5Comment;
	}

	public void setD5Comment(String d5Comment) {
		this.d5Comment = d5Comment;
	}

	public String getD6Comment() {
		return d6Comment;
	}

	public void setD6Comment(String d6Comment) {
		this.d6Comment = d6Comment;
	}

	public String getD7Comment() {
		return d7Comment;
	}

	public void setD7Comment(String d7Comment) {
		this.d7Comment = d7Comment;
	}

	public Date getWeekStartDate() {
		return weekStartDate;
	}

	public void setWeekStartDate(Date weekStartDate) {
		this.weekStartDate = weekStartDate;
	}

	public Date getWeekEndDate() {
		return weekEndDate;
	}

	public void setWeekEndDate(Date weekEndDate) {
		this.weekEndDate = weekEndDate;
	}

	@Column(name = "week_end_date")
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	private Date weekEndDate;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public UserTimeSheet getTimeSheetId() {
		return timeSheetId;
	}

	public void setTimeSheetId(UserTimeSheet timeSheetId) {
		this.timeSheetId = timeSheetId;
	}

}
