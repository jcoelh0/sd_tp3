package entities.Manager.Interfaces;

import settings.Piece;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public interface IManagerL {

	/**
	 * talkWithCustomer method.
	 * @return customer order.
	 */
	public String talkWithCustomer();

	/**
	 * handCarKey method
	 * @param replacementCarId id of the replacement car
	 * @param idCustomer id of the customer
	 */
	public void handCarKey(int replacementCarId, int idCustomer);

	/**
	 * addToReplacementQueue method
	 * @param idCustomer id of the customer
	 */
	public  void addToReplacementQueue(int idCustomer);

	/**
	 * currentCustoemr method
	 * @return id of the next customer
	 */
	public int currentCustomer();

	/**
	 * checkWhatToDo method.
	 */
	public void checkWhatToDo();

	/**
	 * getIdToCall method
	 * @return id of the customer to call
	 */
	public int getIdToCall();

	/**
	 * enoughWork method
	 * @return true if no more work to do
	 */
	public boolean enoughWork();

	/**
	 * alertCustomer method
	 * @param id id of the customer to call
	 * @return true if customer alerted successfully
	 */
	public boolean alertCustomer(int id);

	/**
	 * getNextTask method.
	 */
	public void getNextTask();

	/**
	 * receivePayment method.
	 */
	public void receivePayment();

	/**
	 * appraiseSite method
	 * @return queue to attend
	 */
	public int appraiseSit();

	/**
	 * getPieceToRestock method
	 * @return piece needed to restock
	 */
	public Piece getPieceToReStock();

	/**
	 * goReplenishStock method.
	 */
	public void goReplenishStock();
}
