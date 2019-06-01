package entities.Mechanic;

import entities.Mechanic.States.MechanicState;
import interfaces.LoungeInterface;
import interfaces.ParkInterface;
import interfaces.RepairAreaInterface;
import interfaces.RepositoryInterface;
import java.rmi.RemoteException;
import java.util.HashMap;
import settings.Piece;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
@SuppressWarnings("unchecked")
public class Mechanic extends Thread {

    private MechanicState state;
    private final int id;

    HashMap<Integer, Piece> piecesToBeRepaired;
    private boolean noMoreWork = false;
    boolean repairConcluded = false;
    private boolean enoughWork = false;
    Piece pieceManagerReStock;
    int idCarToFix = 0;

    private final LoungeInterface loungeInt;
    private final RepairAreaInterface repairAreaInt;
    private final ParkInterface parkInt;
    private final RepositoryInterface repositoryInt;

    /**
     * Instantiates a mechanic.
     *
     * @param i is the mechanic id.
     * @param loungeInt
     * @param repairAreaInt
     * @param parkInt
     * @param repositoryInt
     */
    public Mechanic(int i, LoungeInterface loungeInt, RepairAreaInterface repairAreaInt, ParkInterface parkInt, RepositoryInterface repositoryInt) {
        this.id = i;
        this.loungeInt = loungeInt;
        this.repairAreaInt = repairAreaInt;
        this.parkInt = parkInt;
        this.repositoryInt = repositoryInt;
    }

    private boolean readThePaper() { //CHECK AGAIN IF IT'S RIGHT FUNCTION WITH "id" INPUT
        boolean temp = false;
        try {
            temp = repairAreaInt.readThePaper(id);
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
        return temp;
    }

    private boolean partAvailable(Piece piece) { //CHECK AGAIN IF IT'S RIGHT FUNCTION WITH "id" INPUT
        boolean temp = false;
        try {
            temp = repairAreaInt.partAvailable(piece, id);
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
        return temp;
    }

    private int startRepairProcedure() {
        int temp = 0;
        try {
            temp = repairAreaInt.startRepairProcedure();
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
        return temp;
    }

    private void getVehicle(int car) {
        try {
            parkInt.getVehicle(car, id);
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
    }

    private HashMap<Integer, Piece> getPiecesToBeRepaired() {
        HashMap<Integer, Piece> temp = null;
        try {
            temp = repairAreaInt.getPiecesToBeRepaired();
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
        return temp;
    }

    private void getRequiredPart(int car) {
        try {
            repairAreaInt.getRequiredPart(car);
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
    }

    private int fixIt(int car, Piece piece) {
        int temp = -1;
        try {
            temp = repairAreaInt.fixIt(car, piece);
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
        return temp;
    }

    private void returnVehicle(int car) {
        try {
            parkInt.returnVehicle(car);
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
    }

    private void alertManager(Piece piece, int cust_id) {
        try {
            loungeInt.alertManager(piece, cust_id, id);
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
    }

    private void letManagerKnow(Piece piece, int car_id) {
        try {
            repairAreaInt.letManagerKnow(piece, car_id);
        } catch (RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + getName() + ": " + e.getMessage() + "!");
            System.exit(1);
        }
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
