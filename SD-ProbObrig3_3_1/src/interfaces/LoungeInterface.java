package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import settings.Piece;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public interface LoungeInterface extends Remote {

    public void queueIn(int id) throws RemoteException;
    
    public void talkWithManager(boolean carRepaired, boolean requiresCar, int idCustomer) throws RemoteException;
    
    public String talkWithCustomer() throws RemoteException;
    
    public void handCarKey(int replacementCarId, int idCustomer) throws RemoteException;
    
    public int getCarReplacementId(int id) throws RemoteException;
    
    public void payForTheService() throws RemoteException;
    
    public void receivePayment() throws RemoteException;
    
    public int currentCustomer() throws RemoteException;
    
    public boolean collectKey(int id) throws RemoteException;
    
    public void getNextTask() throws RemoteException;
    
    public void checkWhatToDo() throws RemoteException;
    
    public int getIdToCall() throws RemoteException;
    
    public int appraiseSit() throws RemoteException;
    
    public void alertManager(Piece piece, int customerId, int idMechanic) throws RemoteException;
    
    public Piece getPieceToReStock() throws RemoteException;
    
    public void goReplenishStock() throws RemoteException;
    
    public boolean enoughWork() throws RemoteException;
    
    public boolean alertCustomer(int id) throws RemoteException;
    
}