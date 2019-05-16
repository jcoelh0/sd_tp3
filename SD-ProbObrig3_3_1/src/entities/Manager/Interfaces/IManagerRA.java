package entities.Manager.Interfaces;

import settings.Piece;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public interface IManagerRA {

	/**
	 *
	 * @param idCustomer id of the customer
	 */
	public void registerService(int idCustomer);

	/**
	 *
	 * @param part piece to store
	 * @param quant quantity of the piece to store
	 * @return id of the car that needed this type of piece
	 */
	public int storePart(Piece part, int quant);

	/**
	 * enoughWork method.
	 */
	public void enoughWork();
}
