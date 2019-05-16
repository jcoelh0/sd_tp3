package entities.Manager;

import entities.Manager.States.ManagerState;
import settings.Piece;
import messages.LoungeMessage.LoungeMessage;
import messages.OutsideWorldMessage.OutsideWorldMessage;
import messages.ParkMessage.ParkMessage;
import messages.RepairAreaMessage.RepairAreaMessage;
import messages.SupplierSiteMessage.SupplierSiteMessage;

import static communication.ChannelPorts.*;
import communication.ChannelClient;
import messages.RepositoryMessage.RepositoryMessage;

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

    private ChannelClient cc_outsideworld;
    private ChannelClient cc_park;
    private ChannelClient cc_lounge;
    private ChannelClient cc_repairarea;
    private ChannelClient cc_suppliersite;
    private ChannelClient cc_repository;

    /**
     * Instantiates the manager.
     * @param nCustomers number of customers.
     */
    public Manager(int nCustomers) {
        this.nCustomers = nCustomers;
        this.cc_outsideworld = new ChannelClient(NAME_OUTSIDE_WORLD, PORT_OUTSIDE_WORLD);
        this.cc_park = new ChannelClient(NAME_PARK, PORT_PARK);
        this.cc_lounge = new ChannelClient(NAME_LOUNGE, PORT_LOUNGE);
        this.cc_repairarea = new ChannelClient(NAME_REPAIR_AREA, PORT_REPAIR_AREA);
        this.cc_suppliersite = new ChannelClient(NAME_SUPPLIER_SITE, PORT_SUPPLIER_SITE);
        this.cc_repository = new ChannelClient(NAME_GENERAL_REPOSITORY, PORT_GENERAL_REPOSITORY);
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

    private void checkWhatToDo() {
        LoungeMessage response;
        openChannel(cc_lounge, "Manager : Lounge");
        cc_lounge.writeObject(new LoungeMessage(LoungeMessage.CHECK_WHAT_TO_DO));
        response = (LoungeMessage) cc_lounge.readObject();
        cc_lounge.close();
    }

    private void enoughWork() {
        RepairAreaMessage response;
        openChannel(cc_repairarea, "Manager : RepairArea");
        cc_repairarea.writeObject(new RepairAreaMessage(RepairAreaMessage.ENOUGH_WORK));
        response = (RepairAreaMessage) cc_repairarea.readObject();
        cc_repairarea.close();
    }

    private void getNextTask() {
        LoungeMessage response;
        openChannel(cc_lounge, "Manager : Lounge");
        cc_lounge.writeObject(new LoungeMessage(LoungeMessage.GET_NEXT_TASK));
        response = (LoungeMessage) cc_lounge.readObject();
        cc_lounge.close();
    }

    private int appraiseSit() {
        LoungeMessage response;
        openChannel(cc_lounge, "Manager : Lounge");
        cc_lounge.writeObject(new LoungeMessage(LoungeMessage.APPRAISE_SIT));
        response = (LoungeMessage) cc_lounge.readObject();
        cc_lounge.close();
        return response.getCustId();
    }

    private int currentCustomer() {
        LoungeMessage response;
        openChannel(cc_lounge, "Manager : Lounge");
        cc_lounge.writeObject(new LoungeMessage(LoungeMessage.CURRENT_CUSTOMER));
        response = (LoungeMessage) cc_lounge.readObject();
        cc_lounge.close();
        return response.getCustId();
    }

    private String talkWithCustomer() {
        LoungeMessage response;
        openChannel(cc_lounge, "Manager : Lounge");
        cc_lounge.writeObject(new LoungeMessage(LoungeMessage.TALK_WITH_CUSTOMER));
        response = (LoungeMessage) cc_lounge.readObject();
        cc_lounge.close();
        return response.getStrResponse();
    }

    private boolean replacementCarAvailable(int cust_id) {
        ParkMessage response;
        openChannel(cc_park, "Manager : Park");
        cc_park.writeObject(new ParkMessage(ParkMessage.REPLACEMENT_CAR_AVAILABLE, cust_id));
        response = (ParkMessage) cc_park.readObject();
        cc_park.close();
        return response.getBoolResponse();
    }

    private int reserveCar(int cust_id) {
        ParkMessage response;
        openChannel(cc_park, "Manager : Park");
        cc_park.writeObject(new ParkMessage(ParkMessage.RESERVE_CAR, cust_id));
        response = (ParkMessage) cc_park.readObject();
        cc_park.close();
        return response.getId();
    }

    private void handCarKey(int car_id, int cust_id) {
        LoungeMessage response;
        openChannel(cc_lounge, "Manager : Lounge");
        cc_lounge.writeObject(new LoungeMessage(LoungeMessage.HAND_CAR_KEY, car_id, cust_id));
        response = (LoungeMessage) cc_lounge.readObject();
        cc_lounge.close();
    }

    private void addToReplacementQueue(int cust_id) {
        LoungeMessage response;
        openChannel(cc_lounge, "Manager : Lounge");
        cc_lounge.writeObject(new LoungeMessage(LoungeMessage.ADD_TO_REPLACEMENT_QUEUE, cust_id));
        response = (LoungeMessage) cc_lounge.readObject();
        cc_lounge.close();
    }

    private void receivePayment() {
        LoungeMessage response;
        openChannel(cc_lounge, "Manager : Lounge");
        cc_lounge.writeObject(new LoungeMessage(LoungeMessage.RECEIVE_PAYMENT));
        response = (LoungeMessage) cc_lounge.readObject();
        cc_lounge.close();
    }

    private Piece getPieceToReStock() {
        LoungeMessage response;
        openChannel(cc_lounge, "Manager : Lounge");
        cc_lounge.writeObject(new LoungeMessage(LoungeMessage.GET_PIECE_TO_RESTOCK));
        response = (LoungeMessage) cc_lounge.readObject();
        cc_lounge.close();
        return response.getPieceResponse();
    }

    private void registerService(int cust_id) {
        RepairAreaMessage response;
        openChannel(cc_repairarea, "Manager : RepairArea");
        cc_repairarea.writeObject(new RepairAreaMessage(RepairAreaMessage.REGISTER_SERVICE, cust_id));
        response = (RepairAreaMessage) cc_repairarea.readObject();
        cc_repairarea.close();
    }

    private int getIdToCall() {
        LoungeMessage response;
        openChannel(cc_lounge, "Manager : Lounge");
        cc_lounge.writeObject(new LoungeMessage(LoungeMessage.GET_ID_TO_CALL));
        response = (LoungeMessage) cc_lounge.readObject();
        cc_lounge.close();
        return response.getCustId();
    }

    private boolean alertCustomer(int cust_id) {
        LoungeMessage response;
        openChannel(cc_lounge, "Manager : Lounge");
        cc_lounge.writeObject(new LoungeMessage(LoungeMessage.ALERT_CUSTOMER, cust_id));
        response = (LoungeMessage) cc_lounge.readObject();
        cc_lounge.close();
        return response.getBoolResponse();
    }

    private void phoneCustomer(int cust_id) {
        OutsideWorldMessage response;
        openChannel(cc_outsideworld, "Manager : OutsideWorld");
        cc_outsideworld.writeObject(new OutsideWorldMessage(OutsideWorldMessage.PHONE_CUSTOMER, cust_id));
        response = (OutsideWorldMessage) cc_outsideworld.readObject();
        cc_outsideworld.close();
    }

    private void goReplenishStock() {
        LoungeMessage response;
        openChannel(cc_lounge, "Manager : Lounge");
        cc_lounge.writeObject(new LoungeMessage(LoungeMessage.GO_REPLENISH_STOCK));
        response = (LoungeMessage) cc_lounge.readObject();
        cc_lounge.close();
    }

    private int goToSupplier(Piece partNeeded) {
        SupplierSiteMessage response;
        openChannel(cc_suppliersite, "Manager : SupplierSite");
        cc_suppliersite.writeObject(new SupplierSiteMessage(SupplierSiteMessage.GO_TO_SUPPLIER, partNeeded));
        response = (SupplierSiteMessage) cc_suppliersite.readObject();
        cc_suppliersite.close();
        return response.getIntResponse();
    }

    private int storePart(Piece partNeeded, int quant) {
        RepairAreaMessage response;
        openChannel(cc_repairarea, "Manager : RepairArea");
        cc_repairarea.writeObject(new RepairAreaMessage(RepairAreaMessage.STORE_PART, partNeeded, quant));
        response = (RepairAreaMessage) cc_repairarea.readObject();
        cc_repairarea.close();
        return response.getId();
    }
    
    private void endProgram() {
        openChannel(cc_lounge, "Lounge");
        cc_lounge.writeObject(new LoungeMessage(LoungeMessage.END));
        cc_lounge.readObject();
        cc_lounge.close();
        
        openChannel(cc_repairarea, "RepairArea");
        cc_repairarea.writeObject(new RepairAreaMessage(RepairAreaMessage.END));
        cc_repairarea.readObject();
        cc_repairarea.close();
        
        openChannel(cc_park, "Park");
        cc_park.writeObject(new ParkMessage(ParkMessage.END));
        cc_park.readObject();
        cc_park.close();
        
        openChannel(cc_outsideworld, "OutsideWorld");
        cc_outsideworld.writeObject(new OutsideWorldMessage(OutsideWorldMessage.END));
        cc_outsideworld.readObject();
        cc_outsideworld.close();
        
        openChannel(cc_suppliersite, "SupplierSite");
        cc_suppliersite.writeObject(new SupplierSiteMessage(SupplierSiteMessage.END));
        cc_suppliersite.readObject();
        cc_suppliersite.close();
        
        openChannel(cc_repository, "Repository");
        cc_repository.writeObject(new RepositoryMessage(RepositoryMessage.END));
        cc_repository.readObject();
        cc_repository.close();
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
                        endProgram();
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
