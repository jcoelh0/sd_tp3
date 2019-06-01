package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public interface OutsideWorldInterface extends Remote {

    public boolean decideOnRepair(int id) throws RemoteException;

    public boolean backToWorkByBus(boolean carRepaired, int id) throws RemoteException;

    public boolean backToWorkByCar(boolean carRepaired, int replacementCar, int id) throws RemoteException;
    
    public boolean phoneCustomer(int id) throws RemoteException;
    
    public void signalShutdown() throws RemoteException;
    
}