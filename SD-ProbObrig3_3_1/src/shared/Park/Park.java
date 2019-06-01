package shared.Park;

import entities.Mechanic.Interfaces.IMechanicP;
import entities.Manager.Interfaces.IManagerP;
import entities.Customer.Interfaces.ICustomerP;
import entities.Customer.States.CustomerState;
import entities.Mechanic.States.MechanicState;
import interfaces.ParkInterface;
import interfaces.RepositoryInterface;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import settings.Constants;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public class Park implements ICustomerP, IMechanicP, IManagerP, ParkInterface {
    
    private int parkingSlots = 50;
    private int numcars = 0;
    private int numrepcars = 0;

    private final List<Integer> carsParked = new ArrayList<>();
    private final Queue<Integer> replacementCars = new LinkedList<>();
    private final HashMap<Integer, Integer> reserve = new HashMap<>();
    private final List<Integer> toReserve = new ArrayList<>();
    private int findCarId;

    String rmiRegHostName;
    int rmiRegPortNumb;
    private final RepositoryInterface repositoryInterface;
    
    public Park(RepositoryInterface repository, String rmiRegHostName, int rmiRegPortNumb) {
        
        int nReplacementCars = Constants.N_REPLACEMENT_CARS;
        
        for (int i = 1; i < nReplacementCars + 1; i++) {
            replacementCars.add(i);
        }
        numrepcars = nReplacementCars;
        updateNumRepCars(numrepcars);
        
        this.repositoryInterface = repository;
        this.rmiRegHostName = rmiRegHostName;
        this.rmiRegPortNumb = rmiRegPortNumb;
    }

    /**
     * Customer's method. Customer arrives to Repair Shop and parks is car in
     * the park.
     *
     * @param id the id of the car
     */
    @Override
    public synchronized void parkCar(int id) {
        setCustomerState(CustomerState.PARK, id);
        carsParked.add(id);
        parkingSlots--;
        numcars++;
        updateNumCars(numcars);        
    }

    /**
     * Customer's method. Customer goes into the park and finds the replacement
     * car that has been associated to him.
     *
     * @param id id of the customer
     * @param replacementCarId id of the replacement car
     */
    @Override
    public synchronized void findCar(int id, int replacementCarId) {
        setCustomerState(CustomerState.PARK, id);
        replacementCars.remove(replacementCarId);
        numrepcars--;
        updateNumRepCars(numrepcars);
    }

    /**
     * Mechanic's method. Mechanic goes into the park and gets the vehicle to
     * repair.
     *
     * @param idCar id of the car to repair
     * @param idMechanic id of the mechanic
     */
    @Override
    public synchronized void getVehicle(int idCar, int idMechanic) {
        setMechanicState(MechanicState.FIXING_CAR, idMechanic);
        carsParked.remove(new Integer(idCar));
        parkingSlots++;
        numcars--;
        updateNumCars(numcars);
    }

    /**
     * Customer's method. After being alerted that his own car is ready, the
     * customer goes into the park and parks his replacement car.
     *
     * @param idCar id of the car to return
     * @param idCustomer id of the customer
     */
    @Override
    public synchronized void returnReplacementCar(int idCar, int idCustomer) {
        replacementCars.add(idCar);
        numrepcars++;
        updateNumRepCars(numrepcars);
    }

    /**
     * Mechanic's method. Mechanic goes into the park and park the already
     * repaired vehicle.
     *
     * @param id id of the vehicle that has been worked on
     */
    @Override
    public synchronized void returnVehicle(int id) {
        carsParked.add(id);
        parkingSlots--;
        numcars++;
        updateNumCars(numcars);
    }

    /**
     * Method used for log. Retrieves the number of parking slots available.
     *
     * @return an Integer representing the number of parking slots available
     */
    public int getParkingSlots() {
        return this.parkingSlots;
    }

    /**
     * Manager's method. Manager checks if there is any replacement car
     * available in the park.
     *
     * @param idCustomer id of the customer
     * @return a boolean representing if a replacement car is available
     */
    @Override
    public synchronized boolean replacementCarAvailable(int idCustomer) {
        return !replacementCars.isEmpty();
    }

    /**
     * Manager's method. Reserves a replacement car for a customer.
     *
     * @param id customer's id
     * @return replacement car id reserved
     */
    @Override
    public synchronized int reserveCar(int id) {
        try {
            return replacementCars.poll();
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Manager's method. Manager waits for customer to get the replacement car
     * it was given to him.
     *
     * @param id id of the customer
     */
    @Override
    public synchronized void waitForCustomer(int id) {
    }
    
    private synchronized void setCustomerState(CustomerState state, int id) {
        try {
            repositoryInterface.setCustomerState(state.toString(), id);
        }
        catch(RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + e.getMessage() + "!");
            System.exit(1);
        }
    }
    
    private synchronized void setMechanicState(MechanicState state, int id) {
        try {
            repositoryInterface.setMechanicState(state.toString(), id);
        }
        catch(RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + e.getMessage() + "!");
            System.exit(1);
        }
    }
    
    private synchronized void updateNumCars(int numcars) {
        try {
            repositoryInterface.setCustomersParked(numcars);
        }
        catch(RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + e.getMessage() + "!");
            System.exit(1);
        }
    }
    
    private synchronized void updateNumRepCars(int numrepcars) {
        /*try {
            //repositoryInterface.setReplacementParked(numrepcars);
        }
        catch(RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + e.getMessage() + "!");
            System.exit(1);
        }*/
    }
}
