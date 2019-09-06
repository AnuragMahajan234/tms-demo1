package org.yash.rms.util;

/**
 * This enum is used to represent different process status that we can have
 * during conversion of Infogram resource to RMS resource and the delta changes.
 * 
 * @author samiksha.sant
 *
 */
public enum InfogramProcessStatusConstants {

	SUCCESS {
		public String toString() {
			return "SUCCESS";
		}
	},

	FAILURE {
		public String toString() {
			return "FAILURE";
		}
	},

	DISCARD {
		public String toString() {
			return "DISCARD";
		}
	},

	PENDING {
		public String toString() {
			return "PENDING";
		}
	}

}
