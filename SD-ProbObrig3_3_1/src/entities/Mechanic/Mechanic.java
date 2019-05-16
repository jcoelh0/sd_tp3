package entities.Mechanic;

import entities.Mechanic.States.MechanicState;
import java.util.HashMap;
import settings.Piece;
import static communication.ChannelPorts.*;
import communication.ChannelClient;
import messages.LoungeMessage.LoungeMessage;
import messages.ParkMessage.ParkMessage;
import messages.RepairAreaMessage.RepairAreaMessage;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public class Mechanic extends Thread {

    private MechanicState state;
    private final int id;

    private ChannelClient cc_repairarea;
    private ChannelClient cc_lounge;
    private ChannelClient cc_park;

    HashMap<Integer, Piece> piecesToBeRepaired;
    private boolean noMoreWork = false;
    boolean repairConcluded = false;
    private boolean enoughWork = false;
    Piece pieceManagerReStock;
    int idCarToFix = 0;

    /**
     * Instantiates a mechanic.
     * @param i is the mechanic id.
     */
    public Mechanic(int i) {
        this.id = i;
        this.cc_repairarea = new ChannelClient(NAME_REPAIR_AREA, PORT_REPAIR_AREA);
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

    private boolean readThePaper() {
        RepairAreaMessage response;
        openChannel(cc_repairarea, "Mechanic " + this.id + " : RepairArea");
        cc_repairarea.writeObject(new RepairAreaMessage(RepairAreaMessage.READ_THE_PAPER, this.id));
        response = (RepairAreaMessage) cc_repairarea.readObject();
        cc_repairarea.close();
        return response.getBoolResponse();
    }

    private boolean partAvailable(Piece piece) {
        RepairAreaMessage response;
        openChannel(cc_repairarea, "Mechanic " + this.id + " : RepairArea");
        cc_repairarea.writeObject(new RepairAreaMessage(RepairAreaMessage.PART_AVAILABLE, this.id, piece));
        response = (RepairAreaMessage) cc_repairarea.readObject();
        cc_repairarea.close();
        return response.getBoolResponse();
    }

    private int startRepairProcedure() {
        RepairAreaMessage response;
        openChannel(cc_repairarea, "Mechanic " + this.id + " : RepairArea");
        cc_repairarea.writeObject(new RepairAreaMessage(RepairAreaMessage.START_REPAIR_PROCEDURE, this.id));
        response = (RepairAreaMessage) cc_repairarea.readObject();
        cc_repairarea.close();
        return response.getId();
    }

    private void getVehicle(int car) {
        ParkMessage response;
        openChannel(cc_park, "Mechanic " + this.id + " : Park");
        cc_park.writeObject(new ParkMessage(ParkMessage.GET_VEHICLE, this.id, car));
        response = (ParkMessage) cc_park.readObject();
        cc_park.close();
    }

    private HashMap<Integer, Piece> getPiecesToBeRepaired() {
        RepairAreaMessage response;
        openChannel(cc_repairarea, "Mechanic " + this.id + " : RepairArea");
        cc_repairarea.writeObject(new RepairAreaMessage(RepairAreaMessage.GET_PIECES_TO_BE_REPAIRED, this.id));
        response = (RepairAreaMessage) cc_repairarea.readObject();
        cc_repairarea.close();
        return response.getHashResponse();
    }

    private void getRequiredPart(int car) {
        RepairAreaMessage response;
        openChannel(cc_repairarea, "Mechanic " + this.id + " : RepairArea");
        cc_repairarea.writeObject(new RepairAreaMessage(RepairAreaMessage.GET_REQUIRED_PART, this.id, car));
        response = (RepairAreaMessage) cc_repairarea.readObject();
        cc_repairarea.close();
    }

    private int fixIt(int car, Piece piece) {
        RepairAreaMessage response;
        openChannel(cc_repairarea, "Mechanic " + this.id + " : RepairArea");
        cc_repairarea.writeObject(new RepairAreaMessage(RepairAreaMessage.FIX_IT, this.id, piece, car));
        response = (RepairAreaMessage) cc_repairarea.readObject();
        cc_repairarea.close();
        return response.getId();
    }

    private void returnVehicle(int car) {
        ParkMessage response;
        openChannel(cc_park, "Mechanic " + this.id + " : Park");
        cc_park.writeObject(new ParkMessage(ParkMessage.RETURN_VEHICLE, this.id, car));
        response = (ParkMessage) cc_park.readObject();
        cc_park.close();
    }

    private void repairConcluded() {
        RepairAreaMessage response;
        openChannel(cc_repairarea, "Mechanic " + this.id + " : RepairArea");
        cc_repairarea.writeObject(new RepairAreaMessage(RepairAreaMessage.REPAIR_CONCLUDED, this.id));
        response = (RepairAreaMessage) cc_repairarea.readObject();
        cc_repairarea.close();
    }

    private void alertManager(Piece piece, int cust_id) {
        LoungeMessage response;
        openChannel(cc_lounge, "Mechanic " + this.id + " : Lounge");
        cc_lounge.writeObject(new LoungeMessage(LoungeMessage.ALERT_MANAGER, this.id, piece, cust_id));
        response = (LoungeMessage) cc_lounge.readObject();
        cc_lounge.close();
    }

    private void letManagerKnow(Piece piece, int car_id) {
        RepairAreaMessage response;
        openChannel(cc_repairarea, "Mechanic " + this.id + " : RepairArea");
        cc_repairarea.writeObject(new RepairAreaMessage(RepairAreaMessage.LET_MANAGER_KNOW, this.id, piece, car_id));
        response = (RepairAreaMessage) cc_repairarea.readObject();
        cc_repairarea.close();
    }

    private void resumeRepairProcedure() {
        RepairAreaMessage response;
        openChannel(cc_repairarea, "Mechanic " + this.id + " : RepairArea");
        cc_repairarea.writeObject(new RepairAreaMessage(RepairAreaMessage.RESUME_REPAIR_PROCEDURE, this.id));
        response = (RepairAreaMessage) cc_repairarea.readObject();
        cc_repairarea.close();
    }

    @Override
    public void run() {
        this.setMechanicState(MechanicState.WAITING_FOR_WORK);

        while (!noMoreWork) {
            switch (this.state) {
                case WAITING_FOR_WORK:
                    enoughWork = readThePaper();
                    if (enoughWork) {
                        noMoreWork = true;
                        break;
                    }
                    idCarToFix = startRepairProcedure();
                    if (idCarToFix == -1) {
                        setMechanicState(MechanicState.WAITING_FOR_WORK);
                    } else {
                        setMechanicState(MechanicState.FIXING_CAR);
                    }
                    break;

                case FIXING_CAR:
                    getVehicle(idCarToFix);
                    piecesToBeRepaired = getPiecesToBeRepaired();
                    if (!piecesToBeRepaired.containsKey(idCarToFix)) {
                        getRequiredPart(idCarToFix);
                        piecesToBeRepaired = getPiecesToBeRepaired();
                        setMechanicState(MechanicState.CHECKING_STOCK);
                        break;
                    }
                    int fix = fixIt(idCarToFix, piecesToBeRepaired.get(idCarToFix));
                    if (fix == 0) {
                        setMechanicState(MechanicState.CHECKING_STOCK);
                        break;
                    }
                    returnVehicle(idCarToFix);
                    repairConcluded = true;
                    setMechanicState(MechanicState.ALERTING_MANAGER);
                    break;

                case ALERTING_MANAGER:
                    if (!repairConcluded) {
                        alertManager(piecesToBeRepaired.get(idCarToFix), idCarToFix);
                    } else {
                        alertManager(null, idCarToFix);
                    }
                    setMechanicState(MechanicState.WAITING_FOR_WORK);
                    repairConcluded = false;
                    break;

                case CHECKING_STOCK:
                    if (!partAvailable(piecesToBeRepaired.get(idCarToFix))) {
                        letManagerKnow(piecesToBeRepaired.get(idCarToFix), idCarToFix);
                        setMechanicState(MechanicState.ALERTING_MANAGER);
                        returnVehicle(idCarToFix);
                    } else {
                        setMechanicState(MechanicState.FIXING_CAR);
                    }
                    break;
            }
        }
    }

    /**
     * Mechanic's method. Change state of mechanic and report status to log.
     *
     * @param state state of mechanic
     */
    private void setMechanicState(MechanicState state) {
        if (this.state == state) {
            return;
        }
        this.state = state;
    }

    /**
     * Mechanic's method. Retrieves mechanic's state.
     *
     * @return mechanic's state
     */
    public MechanicState getMechanicState() {
        return this.state;
    }

    /**
     * Mechanic's method. Retrieves mechanic's id.
     *
     * @return mechanic's id
     */
    public int getMechanicId() {
        return this.id;
    }
}
