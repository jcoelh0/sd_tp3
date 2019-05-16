package entities.Customer.Interfaces;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public interface ICustomerL {

	/**
	 * queueIn method.
	 * @param id id of the customer
	 */
	public void queueIn(int id);

	/**
	 * talkWithManager method.
	 * @param carRepaired tells if car is repaired
	 * @param requiresCar tells if the customer requires a car
	 * @param idCustomer if of the customer
	 */
	public void talkWithManager(boolean carRepaired, boolean requiresCar, int idCustomer);

	/**
	 * collectKey method.
	 * @param id of the customer
	 * @return true if car was repaired while in queue
	 */
	public boolean collectKey(int id);

	/**
	 * payForTheService method.
	 */
	public void payForTheService();

	/**
	 * getCarReplacementId method.
	 * @param id id of the customer
	 * @return id of the replacement car
	 */
	public int getCarReplacementId(int id);
}
