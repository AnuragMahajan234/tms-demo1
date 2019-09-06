package org.yash.rms.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;




@Entity
@Table(name ="phaseengactvty_mapping")
@NamedQueries({
	@NamedQuery(name = SEPGPhases.GETSEPG_BY_PHASEId, query = SEPGPhases.Query_BY_PHASEId)})
public class SEPGPhases implements Serializable {
	
	public static final String GETSEPG_BY_PHASEId = "GETSEPG_BY_PHASEId";
	public static final String Query_BY_PHASEId = "FROM SEPGPhases WHERE phase =:id";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne(cascade=CascadeType.PERSIST,fetch=FetchType.LAZY)
	@JoinColumn(name="phase_id",referencedColumnName = "phaseid",nullable=false)
	private Phase phase;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "engmt_id", referencedColumnName = "id",nullable=true)
	private EngagementModel engagementModel;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "acty_id", referencedColumnName = "id",nullable=true)
	private Activity activity;

	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Phase getPhase() {
		return phase;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	public EngagementModel getEngagementModel() {
		return engagementModel;
	}

	public void setEngagementModel(EngagementModel engagementModel) {
		this.engagementModel = engagementModel;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	/*@Override
	public String toString() {
		return "SEPGPhases [id=" + id + ", phase=" + phase
				+ ", engagementModel=" + engagementModel + ", activity="
				+ activity + "]";
	}*/
	
	
	
	
	
	

}
