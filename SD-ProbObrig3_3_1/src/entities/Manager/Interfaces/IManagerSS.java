package entities.Manager.Interfaces;

import settings.Piece;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public interface IManagerSS {

	/**
	 * goToSupplier method.
	 * @param partNeeded piece needed
	 * @return quantity of the piece bought
	 */
	public int goToSupplier(Piece partNeeded);
}
