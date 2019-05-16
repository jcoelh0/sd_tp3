package entities.Customer.Interfaces;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public interface ICustomerP {

    /**
     * parkCar method.
     *
     * @param id id of the customer
     */
    public void parkCar(int id);

    /**
     * returnReplacementCar method.
     *
     * @param replacementCar id of the replacement car
     * @param id id of the customer
     */
    public void returnReplacementCar(int replacementCar, int id);

    /**
     * findCar method.
     *
     * @param id id of the customer
     * @param replacementCar id of the replacement car
     */
    public void findCar(int id, int replacementCar);
}
