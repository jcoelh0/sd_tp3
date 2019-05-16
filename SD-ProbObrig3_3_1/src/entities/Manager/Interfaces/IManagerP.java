package entities.Manager.Interfaces;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public interface IManagerP {

	/**
	 *
	 * @param idCustomer id of the customer in need of replacement car
	 * @return true if replacement car available
	 */
	public boolean replacementCarAvailable(int idCustomer);

	/**
	 *
	 * @param id id of the customer
	 * @return id of the replacement car
	 */
	public int reserveCar(int id);

	/**
	 *
	 * @param id id of the customer
	 */
	public void waitForCustomer(int id);
}
