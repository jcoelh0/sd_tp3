package shared.RepairArea;

import communication.ChannelClient;
import static communication.ChannelPorts.NAME_GENERAL_REPOSITORY;
import static communication.ChannelPorts.PORT_GENERAL_REPOSITORY;
import entities.Mechanic.Interfaces.IMechanicRA;
import entities.Manager.Interfaces.IManagerRA;
import entities.Manager.States.ManagerState;
import entities.Mechanic.States.MechanicState;
import static settings.Constants.N_TYPE_PIECES;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import messages.RepositoryMessage.RepositoryMessage;
import settings.EnumPiece;
import settings.Piece;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public class RepairArea implements IMechanicRA, IManagerRA {

    private ChannelClient cc_repository;
    
    private final Queue<Integer> carsToRepair = new LinkedList<>();
    private final HashMap<Integer, Piece> carsWaitingForPieces = new HashMap<>();
    private final Queue<Integer> readyToRepair = new LinkedList<>();
    private final Queue<Integer> repaired = new LinkedList<>();
    private final HashMap<Integer, Piece> piecesToBeRepaired = new HashMap<>();
    private final List<Integer> alreadyAdded = new ArrayList<>();
    private final Queue<Integer> mechanicsQueue = new LinkedList<>();
    private final Queue<Integer> engineQueue = new LinkedList<>();
    private final Queue<Integer> wheelsQueue = new LinkedList<>();
    private final Queue<Integer> brakesQueue = new LinkedList<>();
    private boolean workMechanic = false;
    private int nRequestsManager = 0;
    private boolean enoughWork = false;
    private final boolean[] flagPartMissing;
    private String[] flag;

    static final int nPieces = (int) (Math.random() * 13) + 3;
    
    private static final HashMap<EnumPiece, Integer> stock = new HashMap<>();

    /**
     * RepairArea's constructor. Initialises the stock and adds random pieces to
     * stock.
     *
     * @param nTypePieces number of type of pieces
     */
    public RepairArea(int nTypePieces) {
        flagPartMissing = new boolean[nTypePieces];
        for (int i = 0; i < nTypePieces; i++) {
            stock.put(EnumPiece.values()[i], 0);
            flagPartMissing[i] = true;
        }
        for (int i = 0; i < nPieces; i++) {
            Piece pec = new Piece();
            stock.put(pec.getTypePiece(), stock.get(pec.getTypePiece()) + 1);
            flagPartMissing[pec.getIdTypePiece()] = false;
        }
        flag = new String[nTypePieces];
        Arrays.fill(flag, "F");
        this.cc_repository = new ChannelClient(NAME_GENERAL_REPOSITORY, PORT_GENERAL_REPOSITORY);
        updateStock(stock);
    }

    /**
     * Mechanic's method. Returns the current stock in Repair Area.
     *
     * @return HashMap stock of pieces in Repair Area
     */
    @Override
    public HashMap getPieces() {
        return stock;
    }

    private boolean pieceInStock(Piece p) {
        return stock.get(p.getTypePiece()) > 0;
    }

    private void removePieceFromStock(Piece p) {
        stock.put(p.getTypePiece(), stock.get(p.getTypePiece()) - 1);
        updateStock(stock);
    }

    private void addPieceToStock(Piece p) {
        stock.put(p.getTypePiece(), stock.get(p.getTypePiece()) + 1);
        updateStock(stock);
    }

    /**
     * Mechanic's method. Reads the paper while there is no work. When a he is
     * alerted by the manager, he starts to work.
     *
     * @param idMechanic id of the mechanic
     * @return a boolean representing if mechanic has more work or can go home
     */
    @Override
    public synchronized boolean readThePaper(int idMechanic) {
        setMechanicState(MechanicState.WAITING_FOR_WORK, idMechanic);
        int id = idMechanic;
        mechanicsQueue.add(id);
        while (readyToRepair.isEmpty() && carsToRepair.isEmpty() && !enoughWork) {
            try {
                wait();
            } catch (Exception e) {

            }
        }
        if (enoughWork) {
            return true;
        }
        mechanicsQueue.poll();
        return false;
    }

    /**
     * Mechanic's method. Change the state to start to fix the car.
     *
     * @return a Integer representing the id of the car to be checked/repaired
     * next
     */
    @Override
    public synchronized int startRepairProcedure() {
        if (readyToRepair.isEmpty() && carsToRepair.isEmpty()) {
            return -1;
        } else if (!carsToRepair.isEmpty()) {
            return carsToRepair.poll();
        } else {
            return readyToRepair.poll();
        }
    }

    /**
     * Mechanic's method. Mechanic removes one piece from stock to repair the
     * car in question
     *
     *
     * @param id the id of the car that is going to get repaired
     * @param piece the piece that car needs to be repaired
     * @return return 1 when car is successfully repaired
     */
    @Override
    public synchronized int fixIt(int id, Piece piece) {
        if (stock.get(piece.getTypePiece()) == 0) {
            return 0;
        }
        repaired.add(id);
        removePieceFromStock(piece);
        piecesToBeRepaired.remove(id, piece);
        updateStock(stock);
        updatePiecesToBeRepaired(piecesToBeRepaired);
        return 1;
    }

    /**
     * Mechanic's method. Mechanic checks the car and finds what piece it needs.
     *
     * @param id the car it needs to be checked
     */
    @Override
    public synchronized void getRequiredPart(int id) {
        piecesToBeRepaired.put(id, new Piece());
        updatePiecesToBeRepaired(piecesToBeRepaired);
    }

    /**
     * Mechanic's method. After receiving a new car to fix, checks if the
     * required piece is available in stock.
     *
     * @param part a piece
     * @param idMechanic id of the mechanic
     * @return returns true if the piece is available and false otherwise
     */
    @Override
    public synchronized boolean partAvailable(Piece part, int idMechanic) {
        setMechanicState(MechanicState.CHECKING_STOCK, idMechanic);
        return pieceInStock(part);
    }

    /**
     * Mechanic's method. Removes the car from the queue CarsToRepair and adds
     * it to the CarsWaitingForPieces. Changes state to alert the manager if it
     * needs a new piece.
     *
     * @param piece piece that is required to fix the car
     * @param idCustomerNeedsPiece the id of the card that needs to be fixed
     * associated to this piece
     */
    @Override
    public synchronized void letManagerKnow(Piece piece, int idCustomerNeedsPiece) {
        flagPartMissing[piece.getIdTypePiece()] = true;
        flag[piece.getIdTypePiece()] = "T";
        
        if(piece.toString().equals("Engine"))
            engineQueue.add(idCustomerNeedsPiece);
        else if(piece.toString().equals("Wheels"))
            wheelsQueue.add(idCustomerNeedsPiece);
        else if(piece.toString().equals("Brakes"))
            brakesQueue.add(idCustomerNeedsPiece);
        
        updatePartsMissing(flag);
        carsWaitingForPieces.put(idCustomerNeedsPiece, piece);
        carsToRepair.remove(idCustomerNeedsPiece);
    }

    /**
     * Manager's method. After receiving a request from a customer, the manager
     * registers it for further use by the mechanics.
     *
     * @param idCustomer the id of the car that the mechanic needs to repair
     */
    @Override
    public synchronized void registerService(int idCustomer) {
        setManagerState(ManagerState.POSTING_JOB);
        System.out.println("Manager - Customer " + idCustomer + " needs car repaired.");
        if (!alreadyAdded.contains(idCustomer)) {
            carsToRepair.add(idCustomer);
        }
        alreadyAdded.add(idCustomer);
        if (!mechanicsQueue.isEmpty()) {
            notify();
        }
        nRequestsManager++;
        updateRequests(nRequestsManager);
    }

    /**
     * Manager's method. The manager comes from supplier site with a type of
     * piece and its quantity, and stores them in Repair Area.
     *
     * @param part a type of piece
     * @param quant the quantity of the piece that is going to be added to stock
     * @return a Integer representing the id of the car that needed this type of
     * piece
     */
    @Override
    public synchronized int storePart(Piece part, int quant) {
        for (int i = 0; i < quant; i++) {
            addPieceToStock(part);
        }
        int n = -1;
        if(part.toString().equals("Brakes")) {
            for(int i = 0; i < quant; i++) {
                if(!brakesQueue.isEmpty()){
                    readyToRepair.add(brakesQueue.poll());
                }
            }
        }
        else if(part.toString().equals("Engine")) {
            for(int i = 0; i < quant; i++) {
                if(!engineQueue.isEmpty()){
                    readyToRepair.add(engineQueue.poll());
                }
            }
        }
        else {
            for(int i = 0; i < quant; i++) {
                if(!wheelsQueue.isEmpty()) {
                    readyToRepair.add(wheelsQueue.poll());                    
                }
            }
        }
        carsWaitingForPieces.remove(n);
        flagPartMissing[part.getIdTypePiece()] = false;
        flag[part.getIdTypePiece()] = "F";
        updatePartsMissing(flag);
        notifyAll();
        return n;
    }

    private Object getKeyFromValue(HashMap hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

    /**
     * Mechanic's method. Returns the pieces that are needed to be repair the
     * cars.
     *
     * @return a HashMap that contains the pieces that are needed to be repair
     * the cars
     */
    @Override
    public synchronized HashMap getPiecesToBeRepaired() {
        return piecesToBeRepaired;
    }

    /**
     * Method used for log. Returns the number of requests made by the manager.
     *
     * @return a Integer representing the number of requests made by the manager
     */
    public int getRequestsManagerSize() {
        return nRequestsManager;
    }

    /**
     * Manager's method. Notifies mechanics that work is done for the day.
     */
    @Override
    public synchronized void enoughWork() {
        enoughWork = true;
        notifyAll();
    }
    
    
    private synchronized void setMechanicState(MechanicState state, int id) {
        RepositoryMessage response;
        startCommunication(cc_repository);
        cc_repository.writeObject(new RepositoryMessage(RepositoryMessage.SET_MECHANIC_STATE, state.toString(), id));
        response = (RepositoryMessage) cc_repository.readObject();
        cc_repository.close();
    }
    
    private synchronized void setManagerState(ManagerState state) {
        RepositoryMessage response;
        startCommunication(cc_repository);
        cc_repository.writeObject(new RepositoryMessage(RepositoryMessage.SET_MANAGER_STATE, state.toString()));
        response = (RepositoryMessage) cc_repository.readObject();
        cc_repository.close();
    }
    
    private synchronized void updateStock(HashMap<EnumPiece,Integer> stock) {
        RepositoryMessage response;
        startCommunication(cc_repository);
        cc_repository.writeObject(new RepositoryMessage(RepositoryMessage.NUMBER_PARTS, stock));
        response = (RepositoryMessage) cc_repository.readObject();
        cc_repository.close();
    }
    
    private synchronized void updateRequests(int n) {
        RepositoryMessage response;
        startCommunication(cc_repository);
        cc_repository.writeObject(new RepositoryMessage(RepositoryMessage.NUMBER_SERVICES, n));
        response = (RepositoryMessage) cc_repository.readObject();
        cc_repository.close();
    }
    
    private synchronized void updatePartsMissing(String[] flag) {
        RepositoryMessage response;
        startCommunication(cc_repository);
        cc_repository.writeObject(new RepositoryMessage(RepositoryMessage.PART_NEEDED, flag));
        response = (RepositoryMessage) cc_repository.readObject();
        cc_repository.close();
    }
    
    private synchronized void updatePiecesToBeRepaired(HashMap<Integer,Piece> piecesToBeRepaired) {
        RepositoryMessage response;
        startCommunication(cc_repository);
        cc_repository.writeObject(new RepositoryMessage(piecesToBeRepaired, RepositoryMessage.NUMBER_VEHICLES_WAITING));
        response = (RepositoryMessage) cc_repository.readObject();
        cc_repository.close();
    }
    
    private void startCommunication(ChannelClient cc) {
        while(!cc.open()) {
            try {
                Thread.sleep(1000);
            }
            catch(Exception e) {
                
            }
        }
    }
}
