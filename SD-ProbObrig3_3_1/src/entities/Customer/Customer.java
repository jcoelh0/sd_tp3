package entities.Customer;

import entities.Customer.States.CustomerState;
import interfaces.LoungeInterface;
import interfaces.OutsideWorldInterface;
import interfaces.ParkInterface;
import java.rmi.RemoteException;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public class Customer extends Thread {

    private CustomerState state;

    private final int id;

    /**
     * A boolean that represents if a customer requires a replacement car.
     */
    private boolean requiresCar = false;
    /**
     * A boolean that represents if a car has already been repaired.
     */
    private boolean carRepaired = false;
    private boolean happyCustomer = false;
    private boolean haveReplacementCar = false;
    private int replacementCar;
    private final OutsideWorldInterface outsideWorldInt;
    private final LoungeInterface loungeInt;
    private final ParkInterface parkInt;

    /**
     * Instantiation of a customer.
     *
     * @param i represents its id.
     * @param loungeInt interface of lounge
     * @param outsideWorldInt interface of outsideworld
     * @param parkInt interface of park
     */
    public Customer(int i, LoungeInterface loungeInt, OutsideWorldInterface outsideWorldInt, ParkInterface parkInt) {
        this.id = i;
        this.loungeInt = loungeInt;
        this.outsideWorldInt = outsideWorldInt;
        this.parkInt = parkInt;
    }

    private boolean decideOnRepair() {
        boolean temp = false;
        try {
            temp = outsideWorldInt.decideOnRepair(id);
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
        return temp;
    }

    private boolean backToWorkByCar(boolean carRepaired, int replacementCar) {
        boolean temp = false;
        try {
            temp = outsideWorldInt.backToWorkByCar(carRepaired, replacementCar, id);
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
        return temp;
    }

    private void parkCar() {
        try {
            parkInt.parkCar(id);
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
    }

    private void returnReplacementCar(int replacementCar) {
        try {
            parkInt.returnReplacementCar(replacementCar, id);
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
    }

    private boolean collectKey() {
        boolean temp = false;
        try {
            temp = loungeInt.collectKey(id);
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
        return temp;
    }

    private int getCarReplacementId(int id) {
        int temp = 0;
        try {
            temp = loungeInt.getCarReplacementId(id);
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
        return temp;
    }

    private void findCar(int replacementCar) {
        try {
            parkInt.findCar(id, replacementCar);
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
    }

    private void queueIn() {
        try {
            loungeInt.queueIn(id);
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
    }

    private void talkWithManager(boolean carRepaired, boolean requiresCar) {
        try {
            loungeInt.talkWithManager(carRepaired, requiresCar, id);
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
    }

    private void payForTheService() {
        try {
            loungeInt.payForTheService();
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
    }

    private boolean backToWorkByBus(boolean carRepaired) {
        boolean temp = false;
        try {
            temp = outsideWorldInt.backToWorkByBus(carRepaired, id);
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
        return temp;
    }

    @Override
    public void run() {
        this.setCustomerState(CustomerState.NORMAL_LIFE_WITH_CAR);
        while (!this.happyCustomer) {
            switch (this.state) {
                case NORMAL_LIFE_WITH_CAR:
                    if (carRepaired) {
                        backToWorkByCar(carRepaired, -1);
                        happyCustomer = true;
                        break;
                    } else if (haveReplacementCar) {
                        requiresCar = false;
                        carRepaired = backToWorkByCar(false, replacementCar);
                        setCustomerState(CustomerState.PARK);
                        break;
                    } else {
                        requiresCar = decideOnRepair();
                    }
                    setCustomerState(CustomerState.PARK);
                    break;

                case PARK:
                    if (haveReplacementCar && carRepaired) {
                        haveReplacementCar = false;
                        returnReplacementCar(replacementCar);
                        setCustomerState(CustomerState.RECEPTION);
                        break;
                    }
                    parkCar();
                    setCustomerState(CustomerState.RECEPTION);
                    break;

                case WAITING_FOR_REPLACE_CAR:
                    carRepaired = collectKey();
                    if (carRepaired) {
                        backToWorkByCar(carRepaired, -1);
                        setCustomerState(CustomerState.RECEPTION);
                        break;
                    } else {
                        replacementCar = getCarReplacementId(id);
                        findCar(replacementCar);
                        haveReplacementCar = true;
                        setCustomerState(CustomerState.NORMAL_LIFE_WITH_CAR);
                    }
                    break;

                case RECEPTION:
                    queueIn();
                    talkWithManager(carRepaired, requiresCar);
                    if (!carRepaired) {
                        if (requiresCar) {
                            setCustomerState(CustomerState.WAITING_FOR_REPLACE_CAR);
                        } else {
                            setCustomerState(CustomerState.NORMAL_LIFE_WITHOUT_CAR);
                        }
                    } else {
                        payForTheService();
                        System.out.println("Customer " + this.id + " has car repaired and is going to die!");
                        setCustomerState(CustomerState.NORMAL_LIFE_WITH_CAR);
                    }
                    break;

                case NORMAL_LIFE_WITHOUT_CAR:
                    carRepaired = backToWorkByBus(carRepaired);
                    setCustomerState(CustomerState.RECEPTION);
                    break;
            }
        }
    }

    /**
     * Customer's method. Change state of customer and report status to log.
     *
     * @param state state of customer
     */
    private void setCustomerState(CustomerState state) {
        if (state == this.state) {
            return;
        }
        this.state = state;
    }
}
