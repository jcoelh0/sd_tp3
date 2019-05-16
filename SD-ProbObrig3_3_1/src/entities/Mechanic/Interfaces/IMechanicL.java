package entities.Mechanic.Interfaces;

import settings.Piece;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public interface IMechanicL {

	/**
	 * alertManager method
	 * @param piece piece needed
	 * @param idCar car in need of piece
	 * @param idMechanic id of the mechanic
	 */
	public void alertManager(Piece piece, int idCar, int idMechanic);
}
