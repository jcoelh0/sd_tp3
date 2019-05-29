package shared.OutsideWorld;

import entities.Manager.Interfaces.IManagerOW;
import entities.Customer.Interfaces.ICustomerOW;
import entities.Customer.States.CustomerState;
import interfaces.RepositoryInterface;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import settings.Constants;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public class OutsideWorld implements ICustomerOW, IManagerOW {
    
    private final List<Integer> repairedCars;
    private final List<Integer> waitingForCar;
    private final String[] vehicleDriven;

    String rmiRegHostName;
    int rmiRegPortNumb;
    private final RepositoryInterface repositoryInterface;
    
    public OutsideWorld(RepositoryInterface repository, String rmiRegHostName, int rmiRegPortNumb) {
        
        int nCustomers = Constants.N_CUSTOMERS;
        
        this.waitingForCar = new ArrayList<>();
        this.repairedCars = new ArrayList<>();
        vehicleDriven = new String[nCustomers];
        for (int i = 0; i < nCustomers; i++) {
            if (i < 10) {
                vehicleDriven[i] = "0" + Integer.toString(i);
            } else {
                vehicleDriven[i] = Integer.toString(i);
            }
        }
        
        this.repositoryInterface = repository;
        this.rmiRegHostName = rmiRegHostName;
        this.rmiRegPortNumb = rmiRegPortNumb;
    }
    
    /**
     * Customer's method. The customer starts his life span in the outside world
     * until he decides do repair his car. Furthermore, he also decides if he is
     * going to need a replacement car or not.
     *
     * @param id id of the customer
     * @return boolean indicating if customer requires car
     */
    @Override
    public synchronized boolean decideOnRepair(int id) {
        setCustomerState(CustomerState.NORMAL_LIFE_WITH_CAR, id);
        Random requires = new Random();
        Random n = new Random();
        int randomNum = 0;
        while (randomNum != 1) {
            randomNum = n.nextInt((100 - 1) + 1) + 1;
        }
        boolean req = requires.nextBoolean();
        if(req == true) {
            updateRequiresCar("T", id);
        }
        else {
            updateRequiresCar("F", id);
        }
        return req;
    }

    /**
     * Customer's method. After going back to work by bus, the customer waits
     * for the manager to tell him that his car has been repaired.
     *
     * @param carRepaired indicates if car is repaired
     * @param id id of the customer
     * @return true when car is repaired
     */
    @Override
    public synchronized boolean backToWorkByBus(boolean carRepaired, int id) {
        setCustomerState(CustomerState.NORMAL_LIFE_WITHOUT_CAR, id);
        updateVehicleDriven("--", id);
        vehicleDriven[id] = "--";
        if (!carRepaired) {
            waitingForCar.add(id);
            notifyAll();
            while (!repairedCars.contains(id)) {
                try {
                    wait();
                } catch (InterruptedException e) {

                }
            }
            repairedCars.remove(new Integer(id));
            return true;
        }
        return true;
    }

    /**
     * Customer's method. After going back to work by car (with replacement
     * car), the customer waits for the manager to tell him that his car has
     * been repaired.
     *
     * @param carRepaired indicates if car is repaired
     * @param replacementCar id of replacement car
     * @param id id of the customer
     * @return true when car is repaired
     */
    @Override
    public synchronized boolean backToWorkByCar(boolean carRepaired, int replacementCar, int id) {
        setCustomerState(CustomerState.NORMAL_LIFE_WITH_CAR, id);
        if (replacementCar == -1) {
            if (id < 10) {
                updateVehicleDriven("0"+Integer.toString(id), id);
            } else {
                updateVehicleDriven(Integer.toString(id), id);
            }
        } else {
            updateVehicleDriven("R" + Integer.toString(replacementCar), id);
        }
        if (!carRepaired) {
            waitingForCar.add(id);
            notifyAll();
            while (!repairedCars.contains(id)) {
                try {
                    wait();
                } catch (InterruptedException e) {

                }
            }
            updateVehicleDriven("--", id);
            return true;
        }
        else {
            updateRepairedCar("T", id);
        }
        return true;
    }

    /**
     * Manager's method. After knowing that the car is ready by the mechanic,
     * the manager calls the customer to get his car back.
     *
     * @param id customer's id which car is ready to be picked up
     * @return a boolean representing if the customer is ready to pick up the
     * car
     */
    @Override
    public synchronized boolean phoneCustomer(int id) {
        while (!waitingForCar.contains(id)) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
        if (waitingForCar.contains(id)) {
            repairedCars.add(id);
            notifyAll();
            waitingForCar.remove(new Integer(id));
            return true;
        } else {
            return false;
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
    
    private synchronized void updateVehicleDriven(String s, int i) {
        try {
            repositoryInterface.setVehicleDriven(s, i);
        }
        catch(RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + e.getMessage() + "!");
            System.exit(1);
        }
    }
    
    private synchronized void updateRequiresCar(String s, int i) {
        try {
            repositoryInterface.setRequiresCar(s, i);
        }
        catch(RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + e.getMessage() + "!");
            System.exit(1);
        }
    }
    
    private synchronized void updateRepairedCar(String s, int i) {
        try {
            repositoryInterface.setVehicleRepaired(s, i);
        }
        catch(RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + e.getMessage() + "!");
            System.exit(1);
        }
    }
}
