package shared.Repository;

import entities.Customer.States.CustomerState;
import entities.Manager.States.ManagerState;
import entities.Mechanic.States.MechanicState;
import static settings.Constants.N_CUSTOMERS;
import static settings.Constants.N_REPLACEMENT_CARS;
import static settings.Constants.N_TYPE_PIECES;
import genclass.FileOp;
import genclass.GenericIO;
import genclass.TextFile;
import java.util.Arrays;
import java.util.HashMap;
import static settings.Constants.N_MECHANICS;
import settings.EnumPiece;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public class Repository {
    
    private final String file_name = "log.txt";
    TextFile log = new TextFile();
    
    private String managerState;  
    private String[] customerState = new String[N_CUSTOMERS];
    private String[] mechanicState = new String[N_MECHANICS];
    
    private String[] carsDriven = new String[N_CUSTOMERS];
    private String[] requiresCar = new String[N_CUSTOMERS];
    private String[] repairedCars = new String[N_CUSTOMERS];
    
    private int customersQueue = 0;
    private int replacementQueue = 0;
    private int numCarsRepaired = 0;
    
    private int customersParked = 0;
    private int replacementParked = 0;
    
    private int requests = 0;
    private int[] piecesStock = new int[N_TYPE_PIECES];
    private int[] piecesRequired = new int[N_TYPE_PIECES];
    private String[] managerNotified = new String[N_TYPE_PIECES];
    
    private int[] boughtPieces = new int[N_TYPE_PIECES];
	
	private final int rmiRegPortNumb;
	private final String rmiRegHostName;
    
    /**
     * Repository constructor. Creates the log file and initializes data.
	 * @param rmiRegHostName
	 * @param rmiRegPortNumb
     */
    public Repository(String rmiRegHostName, int rmiRegPortNumb) {
        if(FileOp.exists(".", file_name)) {
            FileOp.deleteFile(".", file_name);
        }
        
        Arrays.fill(mechanicState, MechanicState.WAITING_FOR_WORK.toString());
        Arrays.fill(customerState, CustomerState.NORMAL_LIFE_WITH_CAR.toString());
        managerState = ManagerState.CHECKING_WHAT_TO_DO.toString();
        
        for(int i = 0; i < carsDriven.length; i++) {
            carsDriven[i] = Integer.toString(i);
        }
        
        Arrays.fill(requiresCar, "F");
        Arrays.fill(repairedCars, "F");
        Arrays.fill(piecesStock, 10);
        Arrays.fill(piecesRequired, 0);
        Arrays.fill(managerNotified, "F");
        Arrays.fill(boughtPieces, 0);       
		
		this.rmiRegHostName = rmiRegHostName;
        this.rmiRegPortNumb = rmiRegPortNumb;
    }
    
    /**
     * Method that updates the log each time something changes.
     */
    public void updateLog() {
        if(!log.openForAppending(".", file_name)) {
            GenericIO.writelnString("Couldn't create " + file_name + "!");
            System.exit(1);
        }
        
        String s = managerState;
        
        s += String.format("\t%s %s\t", mechanicState[0], mechanicState[1]);
        
        for(int i = 0; i < customerState.length; i++) {
            s += String.format("\t%s %s %s %s", customerState[i], carsDriven[i], requiresCar[i], repairedCars[i]);
            if((i + 1) % 10 == 0)
                s += "\n\t\t";
        }
        
        s += String.format("\t%d %d %d", customersQueue, replacementQueue, numCarsRepaired);
        s += String.format("\t%d %d", customersParked, replacementParked);
        s += String.format("\t%d", requests);
        
        for(int i = 0; i < piecesStock.length; i++) {
            s += String.format("\t %d %d %s\t", piecesStock[i], piecesRequired[i], managerNotified[i]);
        }
        
        for(int i = 0; i < boughtPieces.length; i++) {
            s += String.format(" %d", boughtPieces[i]);
        }
        
        s += "\n";
        
        log.writelnString(s);
        if(!log.close()) {
            GenericIO.writelnString("Couldn't close " + file_name + "!");
            System.exit(1);
        }
    }
    
    /**
     * Method that updates the manager state.
     * @param state new manager state
     */
    public synchronized void setManagerState(String state) {
        managerState = state;
        updateLog();
    }
    
    /**
     * Method that updates a customer state.
     * @param state new customer state
     * @param i id of the customer
     */
    public synchronized void setCustomerState(String state, int i) {
        customerState[i] = state;
        updateLog();
    }
    
    /**
     * Method that updates a mechanic state.
     * @param state new mechanic state
     * @param i id of the mechanic
     */
    public synchronized void setMechanicState(String state, int i) {
        mechanicState[i] = state;
        updateLog();
    }
    
    /**
     * Method that updates the number of customers in queue.
     * @param size size of the queue.
     */
    public synchronized void setCustomersQueue(int size) {
        customersQueue = size;
        updateLog();
    }
    
    /**
     * Method that updates the number of customers waiting for replacement
     * car.
     * @param size number of customers waiting. 
     */
    public synchronized void setReplacementQueue(int size) {
        replacementQueue = size;
        updateLog();
    }
    
    /**
     * Method that updates the number of cars that have been repaired.
     * @param size number of cars repaired.
     */
    public synchronized void setNumCarsRepaired(int size) {
        numCarsRepaired = size;
        updateLog();
    }
    
    /**
     * Method that updates the number of customer cars parked.
     * @param n number of cars parked.
     */
    public synchronized void setCustomersParked(int n) {
        customersParked = n;
        updateLog();
    }
    
    /**
     * Method that updates the number of replacement cars parked.
     * @param n number of cars parked.
     */
    public synchronized void setReplacementParked(int n) {
        replacementParked = n;
        updateLog();
    }
    
    /**
     * Method that updates the number of requests made to the manager.
     * @param n number of requests.
     */
    public synchronized void setRequests(int n) {
        requests = n;
        updateLog();
    }
    
    /**
     * Method that updates the number of pieces in stock.
     * @param pieces current stock.
     */
    public synchronized void setPiecesStock(HashMap<EnumPiece, Integer> pieces) {
        int i = 0;
        for(EnumPiece key : pieces.keySet()) {
            piecesStock[i] = pieces.get(key);
            i++;
        }
        updateLog();
    }
    
    /**
     * Method that updates the pieces that are required.
     * @param pieces pieces required.
     */
    public synchronized void setPiecesRequired(int[] pieces) {
        piecesRequired = pieces;
        updateLog();
    }
    
    /**
     * Method that updates the pieces that the manager was notified
     * to go get.
     * @param not pieces notified. 
     */
    public synchronized void setManagerNotifed(String[] not) {
        managerNotified = not;
        updateLog();
    }
    
    /**
     * Method that updates the pieces that the manager has bought.
     * @param pieces pieces bought.
     */
    public synchronized void setBoughtPieces(int[] pieces) {
        for(int i = 0; i < boughtPieces.length; i++) {
            boughtPieces[i] += pieces[i];
        }
        updateLog();
    }
    
    /**
     * Method that updates if a customer requires a replacement car.
     * @param s "T" if true, "F" if false
     * @param i id of customer
     */
    public synchronized void setRequiresCar(String s, int i) {
        requiresCar[i] = s;
        updateLog();
    }
    
    /**
     * Method that updates what car a customer is driving.
     * @param s car driven
     * @param i id of customer
     */
    public synchronized void setVehicleDriven(String s, int i) {
        carsDriven[i] = s;
        updateLog();
    }
    
    /**
     * Method that updates the repair of a customer's car.
     * @param s car driven
     * @param i id of customer
     */
    public synchronized void setVehicleRepaired(String s, int i) {
        repairedCars[i] = s;
        updateLog();
    }
}
