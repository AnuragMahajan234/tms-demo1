package org.yash.rms.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "project_ticket_priority")
public class ProjectTicketPriority implements Serializable {
	
	private static final long serialVersionUID = 3057853700521515095L;

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    private Integer id;

	    @Column(name = "priority", length = 256)
	    @NotNull
	    private String priority;
	    
	  /*  @Column(name = "mandatory")
		@NotNull
		private boolean mandatory;*/
	    
	    @Column(name = "active")
	    @NotNull
	    private int active;
	    
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "projectId", referencedColumnName = "id", nullable = false)
	    private Project projectId;
	    
	    public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getPriority() {
			return priority;
		}

		public void setPriority(String priority) {
			this.priority = priority;
		}

		/*public boolean isMandatory() {
			return mandatory;
		}

		public void setMandatory(boolean mandatory) {
			this.mandatory = mandatory;
		}
*/
		public int getActive() {
			return active;
		}

		public void setActive(int active) {
			this.active = active;
		}

		public Project getProjectId() {
			return projectId;
		}

		public void setProjectId(Project projectId) {
			this.projectId = projectId;
		}

	    @Override
		public String toString() {
			return "ProjectTicketPriority [id=" + id + ", priority=" + priority
					+ ", active=" + active + ", projectId=" + projectId + "]";
		}
	    
}
