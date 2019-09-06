package org.yash.rms.util;

/**
 * this class of enum constants is used for request requisition status list. 
 * @author samiksha.sant
 *
 */

public enum RequestRequisitionStatusConstants {

	SELECTED {
		public String toString() {
			return "SELECTED";
		}
	},

	REJECTED {
		public String toString() {
			return "REJECTED";
		}
	},

	HOLD {
		public String toString() {
			return "HOLD";
		}
	},

	SUBMITTED_TO_POC {
		public String toString() {
			return "SUBMITTED TO POC";
		}
	},

	BACKOFF {
		public String toString() {
			return "BACKOFF";
		}
	},

	NOT_SHORTLISTED {
		public String toString() {
			return "PROFILE NOT SHORTLISTED";
		}
	},

	LINED_UP {
		public String toString() {
			return "INTERVIEW LINE UP";
		}
	},

	NOT_AVAILABLE {
		public String toString() {
			return "CANDIDATE NOT AVAILABLE";
		}
	},

	NOT_INTERESTED {
		public String toString() {
			return "SELECTED BUT NOT INTERESTED";
		}
	},

	SUBMITTED_NOT_AVAILABLE {
		public String toString() {
			return "SUBMITTED BUT NOT AVAILABLE NOW";
		}
	},

	JOINED {
		public String toString() {
			return "JOINED";
		}
	},

	DUPLICATE {
		public String toString() {
			return "DUPLICATE PROFILE";
		}
	},

	FEEDBACK_PENDING {
		public String toString() {
			return "INTERVIEW DONE - FEEDBACK PENDING";
		}
	},

	UNREACHABLE {
		public String toString() {
			return "NOT RECHABLE";
		}
	},

	ROUND_2_PENDING {
		public String toString() {
			return "2ND ROUND PENDING";
		}
	},

	SUBMITTED_TO_AM_TEAM  {
		public String toString() {
			return "SUBMITTED TO AM TEAM";
		}
	},
	POC_NOT_FIT {
		public String toString() {
			return "POC - NOT FIT";
		}
	}
}
