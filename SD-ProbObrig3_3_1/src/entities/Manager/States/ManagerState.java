package entities.Manager.States;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public enum ManagerState {

    //Manager states: aCtm, cwtd, gnwp, pjob, aCtm, rSto

	/**
	 * CHECKING_WHAT_TO_DO state.
	 */
    CHECKING_WHAT_TO_DO {
        @Override
        public String toString() {
            return "cwtd";
        }
    },

	/**
	 * ATTENDING_CUSTOMER state.
	 */
	ATTENDING_CUSTOMER {
        @Override
        public String toString() {
            return "aCtm";
        }
    },

	/**
	 * GETTING_NEW_PARTS state.
	 */
	GETTING_NEW_PARTS {
        @Override
        public String toString() {
            return "gnwp";
        }
    },

	/**
	 * POSTING_JOB state.
	 */
	POSTING_JOB {
        @Override
        public String toString() {
            return "pjob";
        }
    },

	/**
	 * ALERTING_CUSTOMER state.
	 */
	ALERTING_CUSTOMER {
        @Override
        public String toString() {
            return "aCtm";
        }
    },

	/**
	 * REPLENISH_STOCK state.
	 */
	REPLENISH_STOCK {
        @Override
        public String toString() {
            return "rSto";
        }
    };
}
