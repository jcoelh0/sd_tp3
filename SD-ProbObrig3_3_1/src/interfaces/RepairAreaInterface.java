package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import settings.Piece;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public interface RepairAreaInterface extends Remote {

    public void parkCar(int id) throws RemoteException;

    public HashMap getPieces() throws RemoteException;

    public boolean readThePaper(int idMechanic) throws RemoteException;

    public int startRepairProcedure() throws RemoteException;

    public int fixIt(int id, Piece piece) throws RemoteException;

    public void getRequiredPart(int id) throws RemoteException;

    public boolean partAvailable(Piece part, int idMechanic) throws RemoteException;

    public void letManagerKnow(Piece piece, int idCustomerNeedsPiece) throws RemoteException;

    public void registerService(int idCustomer) throws RemoteException;

    public int storePart(Piece part, int quant) throws RemoteException;

    public HashMap getPiecesToBeRepaired() throws RemoteException;

    public void enoughWork() throws RemoteException;
    
}