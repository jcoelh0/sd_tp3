package shared.Lounge;

import entities.Mechanic.Interfaces.IMechanicL;
import entities.Manager.Interfaces.IManagerL;
import entities.Customer.Interfaces.ICustomerL;
import entities.Customer.States.CustomerState;
import entities.Manager.States.ManagerState;
import entities.Mechanic.States.MechanicState;
import interfaces.RepositoryInterface;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import settings.Piece;
import settings.Constants;
import java.rmi.RemoteException;
import interfaces.*;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public class Lounge implements ICustomerL, IManagerL, IMechanicL, LoungeInterface {

    private final Queue<Integer> replacementQueue = new LinkedList<>();
    private final HashMap<Integer, Integer> customersWithRepCar = new HashMap<>();
    private final Queue<Integer> customersQueue = new LinkedList<>();
    private final Queue<Piece> piecesQueue = new LinkedList<>();
    private int nextCustomer = 0;
    private boolean managerAvailable = false;
    private boolean ordered = false;
    private boolean payed = false;
    private final boolean enoughWork = false;
    private final Queue<Integer> customersToCallQueue = new LinkedList<>();
    private final Queue<Integer> carsRepaired = new LinkedList<>();

    private boolean readyToReceive;
    private boolean[] requiresReplacementCar;

    private static HashMap<Integer, String> order = new HashMap<Integer, String>();
    
    String rmiRegHostName;
    int rmiRegPortNumb;
    private final RepositoryInterface repositoryInterface;
    
    public Lounge(RepositoryInterface repository, String rmiRegHostName, int rmiRegPortNumb) {
        requiresReplacementCar = new boolean[Constants.N_CUSTOMERS];
        Arrays.fill(requiresReplacementCar, false);
        
        this.repositoryInterface = repository;
        this.rmiRegHostName = rmiRegHostName;
        this.rmiRegHostName = rmiRegHostName;
    }

    /**
     * Customer's method. After parking the car in need of a repair, the
     * customer now has to wait in a queue to be attended by the manager.
     *
     * @param id customer's id
     */
    @Override
    public synchronized void queueIn(int id) {
        setCustomerState(CustomerState.RECEPTION, id);
        customersQueue.add(id);
        setCustomersQueueSize(customersQueue.size());
        notifyAll();
        while(customersQueue.contains(id) && !managerAvailable) {
            try {
                wait();
            } catch (InterruptedException e) {
                
            }
        }
    }
 
    /**
     * Customer's method. When the customer is talking to the manager he says if
     * he requires a replacement car or not.
     *
     * @param carRepaired represents if customers car is repaired
     * @param requiresCar represents if customer requires replacement car
     * @param idCustomer id of the customer
     */
    @Override
    public synchronized void talkWithManager(boolean carRepaired, boolean requiresCar, int idCustomer) {
        if (carRepaired) {
            order.put(nextCustomer, "pay");
        } else {
            if (requiresCar) {
                order.put(nextCustomer, "car");
                requiresReplacementCar[idCustomer] = true;
            } else {
                order.put(nextCustomer, "nocar");
            }
        }
        ordered = true;
        notifyAll();
        ordered = false;
    }

    /**
     * Manager's method. The manager awakes the customer next in the queue, and
     * then waits to see if the customer requires a replacement car.
     *
     * @return a String containing the information about what the customer needs
     * to do
     */
    @Override
    public synchronized String talkWithCustomer() {
        managerAvailable = true;
        notifyAll();
        managerAvailable = false;
        while (!(order.containsKey(nextCustomer)) && !ordered) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
        String s = order.get(nextCustomer);
        order.remove(nextCustomer);
        return s;

    }

    /**
     *
     * @param idCustomer id of the customer
     */
    @Override
    public synchronized void addToReplacementQueue(int idCustomer) {
        
    }

    /**
     * Manager's method. Manager gives to the customer the replacement car's
     * key.
     *
     * @param replacementCarId id of the replacement car
     * @param idCustomer id of the customer
     */
    @Override
    public synchronized void handCarKey(int replacementCarId, int idCustomer) {
        while (replacementQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
        customersWithRepCar.put(replacementQueue.peek(), replacementCarId);
        requiresReplacementCar[idCustomer] = false;
        notifyAll();
    }

    /**
     *
     * @param id id of the customer
     * @return id of the replacement car
     */
    @Override
    public synchronized int getCarReplacementId(int id) {
        while (!customersWithRepCar.containsKey(id)) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
        int n = customersWithRepCar.get(id);
        customersWithRepCar.remove(id);
        return n;
    }

    /**
     * Customer's method. Customer waits until manager is ready to receive
     * payment, and then proceeds to pay.
     */
    @Override
    public synchronized void payForTheService() {
        while (!readyToReceive) {
            try {
                wait();
            } catch (Exception e) {

            }
        }
        readyToReceive = false;
        payed = true;
        notifyAll();        
        setCustomersQueueSize(customersQueue.size());
    }

    /**
     * Manager's method. Receives payment from customer.
     */
    @Override
    public synchronized void receivePayment() {
        readyToReceive = true;
        notifyAll();
        while (!payed) {
            try {
                wait();
            } catch (Exception e) {

            }
        }
        payed = false;
    }

    /**
     * Manager's method. Manager chooses the customer with the highest priority.
     *
     * @return an Integer indicating the next customer's id to attend
     */
    @Override
    public synchronized int currentCustomer() {
        setManagerState(ManagerState.ATTENDING_CUSTOMER);
        nextCustomer = customersQueue.poll();
        notify();
        return nextCustomer;
    }

    /**
     * Customer's method. The customer waits for a replacement car's key and
     * goes get one if it is available. Eventually, if the customer's car is
     * repaired while waiting, he goes to the normal queue.
     *
     * @param id id of the customer
     * @return true if car was repaired while customer waited for replacement car
     */
    @Override
    public synchronized boolean collectKey(int id) {
        setCustomerState(CustomerState.WAITING_FOR_REPLACE_CAR, id);
        replacementQueue.add(id);
        setReplacementQueueSize(replacementQueue.size());
        notifyAll();
        while (!customersWithRepCar.containsKey(id) && !carsRepaired.contains(id)) { //&& !carsRepaired.contains(id)
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
        replacementQueue.remove(id);
        setReplacementQueueSize(replacementQueue.size());
        if (carsRepaired.contains(id)) {
            carsRepaired.remove(id);
            requiresReplacementCar[id] = false;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Manager's method. Manager waits while there is nothing to do.
     */
    @Override
    public synchronized void getNextTask() {
        while (customersQueue.isEmpty() && customersToCallQueue.isEmpty() && piecesQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
    }

    /**
     * Manager's method. Manager changes state to check what to do next.
     *
     */
    @Override
    public synchronized void checkWhatToDo() {
        setManagerState(ManagerState.CHECKING_WHAT_TO_DO);
    }

    /**
     * Manager's method. Manager goes into the queue of customers to call and
     * retrieves the head of the queue.
     *
     * @return an Integer indicating the customer's id
     */
    @Override
    public synchronized int getIdToCall() {
        setManagerState(ManagerState.ALERTING_CUSTOMER);
        int next = customersToCallQueue.poll();
        return next;
    }

    /**
     * Manager's method. When there is work to do, the manager chooses the task
     * with the highest priority and changes state, accordingly.
     *
     * @return int representing next task
     */
    @Override
    public synchronized int appraiseSit() {
        if (!piecesQueue.isEmpty()) {
            return 1;
        } else if (!customersToCallQueue.isEmpty()) {
            return 2;
        } else if (!customersQueue.isEmpty()) {
            return 3;
        } else {
            return 0;
        }
    }

    /**
     * Mechanic's method. The mechanic alerts the manager that the car is
     * repaired or that he needs a type of a piece re stocked in the repair
     * area.
     *
     * @param piece piece that needs to be re stocked
     * @param customerId customer that needs to be called because his car is
     * ready to be picked up
     * @param idMechanic id of the mechanic
     */
    @Override
    public synchronized void alertManager(Piece piece, int customerId, int idMechanic) {
        setMechanicState(MechanicState.ALERTING_MANAGER, idMechanic);
        if (piece == null) {
            System.out.println("Mechanic " + idMechanic + " - Customer " + customerId + " car is repaired!");
            customersToCallQueue.add(customerId);
            notifyAll();
        } else {
            System.out.println("Mechanic " + idMechanic + " - Piece " + piece.toString() + " is required.");
            piecesQueue.add(piece);
            notifyAll();
        }
    }

    /**
     * Manager's method. The manager retrieves the piece that needs to be re
     * stocked in the repair area.
     *
     * @return a piece that needs to be re stocked in the repair area
     */
    @Override
    public synchronized Piece getPieceToReStock() {
        setManagerState(ManagerState.GETTING_NEW_PARTS);
        Piece p = piecesQueue.poll();
        return p;
    }

    /**
     * Manager's method. Manager changes state to go replenish stock.
     *
     */
    @Override
    public synchronized void goReplenishStock() {
        setManagerState(ManagerState.REPLENISH_STOCK);
    }

    /**
     * Manager's method. Manager checks if there is any work left to do.
     *
     * @return a boolean that represents if there is any work left to do
     */
    @Override
    public synchronized boolean enoughWork() {
        return customersQueue.isEmpty() && replacementQueue.isEmpty() && enoughWork;
    }

    /**
     * Manager's method. Manager checks if the customer is in outside world or
     * in lounge and then calls customer and tells him that his car is ready to
     * be picked up.
     *
     * @param id customer's id to call
     * @return a boolean that returns true if customer is in lounge and false if
     * he's in outside world
     */
    @Override
    public synchronized boolean alertCustomer(int id) {
        carsRepaired.add(id);
        updateCarsRepaired(carsRepaired.size());
        if (replacementQueue.contains(id)) {
            customersToCallQueue.remove(id);
            notifyAll();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method used for log. Retrieves the size of the queue to talk to the
     * manager.
     *
     * @return an Integer
     */
    public int getCustomersQueueSize() {
        return customersQueue.size();
    }

    /**
     * Method used for log. Retrieves the size of the queue waiting for
     * replacement cars.
     *
     * @return an Integer that represents the size of the queue for replacement
     * cars
     */
    public int getCustomersReplacementQueueSize() {
        return replacementQueue.size();
    }

    /**
     * Method used for log. Retrieves the number of the cars already repaired.
     *
     * @return an Integer that represents the number of the cars repaired
     */
    public int getCarsRepairedSize() {
        return carsRepaired.size();
    }

    
    private synchronized void setManagerState(ManagerState state) {
        try {
            repositoryInterface.setManagerState(state.toString());
        }
        catch(RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + e.getMessage() + "!");
            System.exit(1);
        }
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
    
    private synchronized void setCustomersQueueSize(int size) {
        try {
            repositoryInterface.setCustomersQueue(size);
        }
        catch(RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + e.getMessage() + "!");
            System.exit(1);
        }
    }
    
    private synchronized void setReplacementQueueSize(int size) {
        try {
            repositoryInterface.setReplacementQueue(size);
        }
        catch(RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + e.getMessage() + "!");
            System.exit(1);
        }
    }
    
    private synchronized void updateCarsRepaired(int size) {
        try {
            repositoryInterface.setNumCarsRepaired(size);
        }
        catch(RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + e.getMessage() + "!");
            System.exit(1);
        }
    }
}
