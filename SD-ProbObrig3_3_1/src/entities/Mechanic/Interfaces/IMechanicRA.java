package entities.Mechanic.Interfaces;

import java.util.HashMap;
import settings.Piece;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public interface IMechanicRA {

	/**
	 * readThePaper method
	 * @param idMechanic id of the mechanic
	 * @return true if no more work
	 */
	public boolean readThePaper(int idMechanic);

	/**
	 * startRepairProcedure method
	 * @return car to repair
	 */
	public int startRepairProcedure();

	/**
	 * getRequiredPart method
	 * @param id car o repair
	 */
	public void getRequiredPart(int id);

	/**
	 * letManagerKnow method
	 * @param piece piece needed
	 * @param idCarToFix car in need of piece
	 */
	public void letManagerKnow(Piece piece, int idCarToFix);

	/**
	 * partAvailable method
	 * @param requiredPart part needed
	 * @param idMechanic id of the mechanic
	 * @return true if part is available
	 */
	public boolean partAvailable(Piece requiredPart, int idMechanic);

	/**
	 * fixIt method
	 * @param id id of the car
	 * @param p piece needed to repair
	 * @return 1 when successful repair
	 */
	public int fixIt(int id, Piece p);

        /**
	 * getPiecesToBeRepaired method
	 * @return pieces waiting
	 */
	public HashMap getPiecesToBeRepaired();

	/**
	 * getPieces method
	 * @return stock
	 */
	public HashMap getPieces();
}
