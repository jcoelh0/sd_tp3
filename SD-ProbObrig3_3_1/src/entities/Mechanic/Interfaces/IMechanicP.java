package entities.Mechanic.Interfaces;


/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public interface IMechanicP {

	/**
	 * getVehicle method
	 * @param id id of the car to repair
	 * @param idMechanic id of the mechanic
	 */
	public void getVehicle(int id, int idMechanic);

	/**
	 * returnVehicle method
	 * @param id id of the repaired car
	 */
	public void returnVehicle(int id);
}
