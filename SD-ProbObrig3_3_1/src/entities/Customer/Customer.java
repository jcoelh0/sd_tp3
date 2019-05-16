package entities.Customer;

import entities.Customer.States.CustomerState;
import communication.ChannelClient;
import static communication.ChannelPorts.*;
import messages.LoungeMessage.LoungeMessage;
import messages.OutsideWorldMessage.OutsideWorldMessage;
import messages.ParkMessage.ParkMessage;

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

    private ChannelClient cc_outside_world;
    private ChannelClient cc_park;
    private ChannelClient cc_lounge;

    /**
     * Instantiation of a customer.
     * @param i represents its id.
     */
    public Customer(int i) {
        this.id = i;
        this.cc_outside_world = new ChannelClient(NAME_OUTSIDE_WORLD, PORT_OUTSIDE_WORLD);
        this.cc_park = new ChannelClient(NAME_PARK, PORT_PARK);
        this.cc_lounge = new ChannelClient(NAME_LOUNGE, PORT_LOUNGE);
    }

    private void openChannel(ChannelClient cc, String name) {
        while (!cc.open()) {
            System.out.println(name + " not open.");
            try {
                Thread.sleep(1000);
            } catch (Exception ex) {

            }
        }
    }

    private boolean decideOnRepair() {
        OutsideWorldMessage response;
        openChannel(cc_outside_world, "Customer " + this.id + ": Outside World");
        cc_outside_world.writeObject(new OutsideWorldMessage(OutsideWorldMessage.DECIDE_ON_REPAIR, this.id));
        response = (OutsideWorldMessage) cc_outside_world.readObject();
        cc_outside_world.close();
        return response.getBoolResponse();
    }

    private boolean backToWorkByCar(boolean carRepaired, int replacementCar) {
        OutsideWorldMessage response;
        openChannel(cc_outside_world, "Customer " + this.id + ": Outside World");
        cc_outside_world.writeObject(new OutsideWorldMessage(OutsideWorldMessage.BACK_TO_WORK_BY_CAR, this.id, carRepaired, replacementCar));
        response = (OutsideWorldMessage) cc_outside_world.readObject();
        cc_outside_world.close();
        return response.getBoolResponse();
    }

    private void parkCar() {
        ParkMessage response;
        openChannel(cc_park, "Customer " + this.id + ": Park");
        cc_park.writeObject(new ParkMessage(ParkMessage.PARK_CAR, this.id));
        response = (ParkMessage) cc_park.readObject();
        cc_park.close();
    }

    private void returnReplacementCar(int replacementCar) {
        ParkMessage response;
        openChannel(cc_park, "Customer " + this.id + ": Park");
        cc_park.writeObject(new ParkMessage(ParkMessage.RETURN_REPLACEMENT_CAR, this.id, replacementCar));
        response = (ParkMessage) cc_park.readObject();
        cc_park.close();
    }

    private boolean collectKey() {
        LoungeMessage response;
        openChannel(cc_lounge, "Customer " + this.id + ": Lounge");
        cc_lounge.writeObject(new LoungeMessage(LoungeMessage.COLLECT_KEY, this.id));
        response = (LoungeMessage) cc_lounge.readObject();
        cc_lounge.close();
        return response.getBoolResponse();
    }

    private int getCarReplacementId(int id) {
        LoungeMessage response;
        openChannel(cc_lounge, "Customer " + this.id + ": Lounge");
        cc_lounge.writeObject(new LoungeMessage(LoungeMessage.GET_REPLACEMENT_CAR, id));
        response = (LoungeMessage) cc_lounge.readObject();
        cc_lounge.close();
        return response.getCustId();
    }

    private void findCar(int replacementCar) {
        ParkMessage response;
        openChannel(cc_park, "Customer " + this.id + ": Park");
        cc_park.writeObject(new ParkMessage(ParkMessage.FIND_CAR, this.id, replacementCar));
        response = (ParkMessage) cc_park.readObject();
        cc_park.close();
    }

    private void queueIn() {
        LoungeMessage response;
        openChannel(cc_lounge, "Customer " + this.id + ": Lounge");
        cc_lounge.writeObject(new LoungeMessage(LoungeMessage.QUEUE_IN, this.id));
        response = (LoungeMessage) cc_lounge.readObject();
        cc_lounge.close();
    }

    private void talkWithManager(boolean carRepaired, boolean requiresCar) {
        LoungeMessage response;
        openChannel(cc_lounge, "Customer " + this.id + ": Lounge");
        cc_lounge.writeObject(new LoungeMessage(LoungeMessage.TALK_WITH_MANAGER, this.id, carRepaired, requiresCar));
        response = (LoungeMessage) cc_lounge.readObject();
        cc_lounge.close();
    }

    private void payForTheService() {
        LoungeMessage response;
        openChannel(cc_lounge, "Customer " + this.id + ": Lounge");
        cc_lounge.writeObject(new LoungeMessage(LoungeMessage.PAY_FOR_THE_SERVICE, this.id));
        response = (LoungeMessage) cc_lounge.readObject();
        cc_lounge.close();
    }

    private boolean backToWorkByBus(boolean carRepaired) {
        OutsideWorldMessage response;
        openChannel(cc_outside_world, "Customer " + this.id + ": Outside World");
        cc_outside_world.writeObject(new OutsideWorldMessage(OutsideWorldMessage.BACK_TO_WORK_BY_BUS, this.id, carRepaired));
        response = (OutsideWorldMessage) cc_outside_world.readObject();
        cc_outside_world.close();
        return response.getBoolResponse();
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
                    }
                    else {
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

    /**
     * Customer's method. Retrieves customer's state.
     *
     * @return customer's state
     */
    private CustomerState getCustomerState() {
        return this.state;
    }

    /**
     * Customer's method. Retrieves customer's id.
     *
     * @return customer's id
     */
    public int getCustomerId() {
        return this.id;
    }

    /**
     * Method used for log. Retrieves the current car, replacement car or no car
     * of a customer
     *
     * @return a String representing the current car of a customer
     */
    public String getCustomerVehicle() {
        /*if (!haveReplacementCar && !haveCar) {
            return "--";
        } else if (!haveReplacementCar) {
            if (id < 10) {
                return "0" + Integer.toString(id);
            } else {
                return Integer.toString(id);
            }
        } else {
            return "R" + Integer.toString(replacementCar);
        }*/
        return "";
    }

    /**
     * Method used for log. Retrieves if the customer requires a replacement
     * car.
     *
     * @return a String representing if the customer requires a replacement car
     * or not
     */
    public String requiresReplacementCar() {
        if (requiresCar) {
            return "T";
        } else {
            return "F";
        }
    }

    /**
     * Method used for log. Retrieves if the customer's vehicle has already been
     * repaired.
     *
     * @return a String that represents if a car has already been repaired
     */
    public String vehicleRepaired() {
        if (carRepaired) {
            return "T";
        } else {
            return "F";
        }
    }
}
