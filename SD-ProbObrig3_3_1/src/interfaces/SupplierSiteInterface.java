package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public interface LoungeInterface extends Remote {

    public int goToSupplier(Piece partNeeded) throws RemoteException;
    
}