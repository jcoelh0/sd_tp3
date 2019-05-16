package entities.Customer.States;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public enum CustomerState {

    //Customer stats: lwc, prk, wrc, rcp, lnc

	/**
	 * NORMAL_LIFE_WITH_CAR state.
	 */
    NORMAL_LIFE_WITH_CAR {
        @Override
        public String toString() {
            return "lwc"; //"Normal life with car.";
        }
    },

	/**
	 * PARK state.
	 */
	PARK {
        @Override
        public String toString() {
            return "prk"; // "Park.";
        }
    },

	/**
	 * WAITING_FOR_REPLACE_CAR state.
	 */
	WAITING_FOR_REPLACE_CAR {
        @Override
        public String toString() {
            return "wrc"; //return "Waiting for replace car.";
        }
    },

	/**
	 * RECEPTION state.
	 */
	RECEPTION {
        @Override
        public String toString() {
            return "rcp"; // return "Reception.";
        }
    },

	/**
	 * NORMAL_LIFE_WITHOUT_CAR state.
	 */
	NORMAL_LIFE_WITHOUT_CAR {
        @Override
        public String toString() {
            return "lnc"; // return "Normal life without car.";
        }
    };
}
