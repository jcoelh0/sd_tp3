package entities.Customer.Interfaces;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public interface ICustomerOW {

	/**
	 * decideOnRepair method.
	 * @param id id of the customer
	 * @return true if customer requires car, false otherwise
	 */
	public boolean decideOnRepair(int id);

	/**
	 * backToWorkByBus method.
	 * @param carRepaired signalizes if customer car is repaired
	 * @param id id of the customer 
	 * @return true when car is repaired
	 */
	public boolean backToWorkByBus(boolean carRepaired, int id);

	/**
	 * backToWorkByCar method.
	 * @param b signalizes if customer car is repaired
	 * @param replacementCar id of the replacement car
	 * @param id customer id
	 * @return true when car is repaired
	 */
	public boolean backToWorkByCar(boolean b, int replacementCar, int id);
}
