package entities.Manager;

import entities.Manager.States.ManagerState;
import interfaces.LoungeInterface;
import interfaces.OutsideWorldInterface;
import interfaces.ParkInterface;
import interfaces.RepairAreaInterface;
import interfaces.SupplierSiteInterface;
import java.rmi.RemoteException;
import settings.Piece;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public class Manager extends Thread {

    private ManagerState state;
    private boolean customerWaiting = false;
    private int quant;
    Piece partNeeded;
    private boolean noMoreTasks = false;
    private int nCustomers;
    private int leftCustomers = 0;
    private boolean availableReplacementCar = false;
    private int idCustomer = 0;
    private int idToCall = 0;

    String rmiRegHostName;
    int rmiRegPortNumb;
    private final LoungeInterface loungeInt;
    private final OutsideWorldInterface outsideWorldInt;
    private final SupplierSiteInterface supplierSiteInt;
    private final RepairAreaInterface repairAreaInt;
    private final ParkInterface parkInt;

    /**
     * Instantiates the manager.
     *
     * @param nCustomers number of customers.
     * @param loungeInt
     * @param outsideWorldInt
     * @param supplierSiteInt
     * @param repairAreaInt
     * @param parkInt
     */
    public Manager(int nCustomers, LoungeInterface loungeInt, OutsideWorldInterface outsideWorldInt, SupplierSiteInterface supplierSiteInt, RepairAreaInterface repairAreaInt, ParkInterface parkInt) {
        this.nCustomers = nCustomers;
        this.loungeInt = loungeInt;
        this.outsideWorldInt = outsideWorldInt;
        this.supplierSiteInt = supplierSiteInt;
        this.repairAreaInt = repairAreaInt;
        this.parkInt = parkInt;

    }

    private void checkWhatToDo() {
        try {
            loungeInt.checkWhatToDo();
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
    }

    private void enoughWork() {
        try {
            repairAreaInt.enoughWork();
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
    }

    private void getNextTask() {
        try {
            loungeInt.getNextTask();
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
    }

    private int appraiseSit() {
        int temp = 0;
        try {
            temp = loungeInt.appraiseSit();
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
        return temp;
    }

    private int currentCustomer() {
        int temp = 0;
        try {
            temp = loungeInt.currentCustomer();
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
        return temp;
    }

    private String talkWithCustomer() {
        String temp = "";
        try {
            temp = loungeInt.talkWithCustomer();
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
        return temp;
    }

    private boolean replacementCarAvailable(int cust_id) {
        boolean temp = false;
        try {
            temp = parkInt.replacementCarAvailable(cust_id);
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
        return temp;
    }

    private int reserveCar(int cust_id) {
        int temp = 0;
        try {
            temp = parkInt.reserveCar(cust_id);
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
        return temp;
    }

    private void handCarKey(int car_id, int cust_id) {
        try {
            loungeInt.handCarKey(car_id, cust_id);
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
    }

    private void addToReplacementQueue(int cust_id) {
        try {
            loungeInt.addToReplacementQueue(cust_id);
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
    }

    private void receivePayment() {
        try {
            loungeInt.receivePayment();
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
    }

    private Piece getPieceToReStock() {
        Piece temp = null;
        try {
            temp = loungeInt.getPieceToReStock();
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
        return temp;
    }

    private void registerService(int cust_id) {
        try {
            repairAreaInt.registerService(cust_id);
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
    }

    private int getIdToCall() {
        int temp = 0;
        try {
            temp = loungeInt.getIdToCall();
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
        return temp;
    }

    private boolean alertCustomer(int cust_id) {
        boolean temp = false;
        try {
            temp = loungeInt.alertCustomer(cust_id);
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
        return temp;
    }

    private void phoneCustomer(int cust_id) {
        try {
            outsideWorldInt.phoneCustomer(cust_id);
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
    }

    private void goReplenishStock() {
        try {
            loungeInt.goReplenishStock();
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
    }

    private int goToSupplier(Piece partNeeded) {
        int temp = 0;
        try {
            temp = supplierSiteInt.goToSupplier(partNeeded);
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
        return temp;
    }

    private int storePart(Piece partNeeded, int quant) {
        int temp = 0;
        try {
            temp = repairAreaInt.storePart(partNeeded, quant);
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
        return temp;
    }

    @Override
    public void run() {
        this.setManagerState(ManagerState.CHECKING_WHAT_TO_DO);
        int nextTask = 0;
        while (!noMoreTasks) {
            switch (this.state) {
                case CHECKING_WHAT_TO_DO:
                    checkWhatToDo();
                    if (leftCustomers == nCustomers) {
                        enoughWork();
                        noMoreTasks = true;
                        break;
                    }
                    getNextTask();
                    nextTask = appraiseSit();
                    switch (nextTask) {
                        case 1:
                            this.setManagerState(ManagerState.GETTING_NEW_PARTS);
                            break;
                        case 2:
                            this.setManagerState(ManagerState.ALERTING_CUSTOMER);
                            break;
                        case 3:
                            this.setManagerState(ManagerState.ATTENDING_CUSTOMER);
                            break;
                        default:
                            System.out.println("ERROR GETTING NEXT TASK" + nextTask);
                            break;
                    }
                    break;

                case ATTENDING_CUSTOMER:
                    idCustomer = currentCustomer();
                    System.out.println("Manager - Attending customer " + idCustomer);
                    String action = talkWithCustomer();
                    if (action.equals("car")) {
                        availableReplacementCar = replacementCarAvailable(idCustomer);
                        if (availableReplacementCar) {
                            int replacementCarId = reserveCar(idCustomer);
                            handCarKey(replacementCarId, idCustomer);
                        } else {
                            addToReplacementQueue(idCustomer);
                        }
                        this.setManagerState(ManagerState.POSTING_JOB);
                    } else if (action.equals("nocar")) {
                        this.setManagerState(ManagerState.POSTING_JOB);
                    } else {
                        receivePayment();
                        leftCustomers++;
                        this.setManagerState(ManagerState.CHECKING_WHAT_TO_DO);
                    }

                    break;

                case GETTING_NEW_PARTS:
                    partNeeded = getPieceToReStock();
                    this.setManagerState(ManagerState.REPLENISH_STOCK);
                    break;

                case POSTING_JOB:
                    registerService(idCustomer);
                    this.setManagerState(ManagerState.CHECKING_WHAT_TO_DO);
                    break;

                case ALERTING_CUSTOMER:
                    idToCall = getIdToCall();
                    customerWaiting = alertCustomer(idToCall);
                    if (!customerWaiting) {
                        phoneCustomer(idToCall);
                    }
                    this.setManagerState(ManagerState.CHECKING_WHAT_TO_DO);
                    break;

                case REPLENISH_STOCK:
                    goReplenishStock();
                    quant = goToSupplier(partNeeded);
                    idCustomer = storePart(partNeeded, quant);
                    this.setManagerState(ManagerState.CHECKING_WHAT_TO_DO);
                    break;
            }
        }
    }

    /**
     * Manager's method. Change state of manager and report status to log.
     *
     * @param state state of manager
     */
    private void setManagerState(ManagerState state) {
        if (this.state == state) {
            return;
        }
        this.state = state;
    }

    /**
     * Manager's method. Retrieves manager's state.
     *
     * @return manager's state
     */
    private ManagerState getManagerState() {
        return this.state;
    }

}
