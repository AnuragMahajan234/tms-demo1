package org.yash.rms.util;

/**
 * This enum is used to represent different process status that we can have
 * during conversion of Infogram resource to RMS resource and the delta changes.
 * 
 * @author samiksha.sant
 *
 */
public enum messageBoardStatusConstants {

	APPROVED {
		public String toString() {
			return "APPROVED";
		}
	},

	REJECTED {
		public String toString() {
			return "REJECTED";
		}
	},

	NEW {
		public String toString() {
			return "NEW";
		}
	}

}
