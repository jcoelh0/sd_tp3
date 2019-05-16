package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import settings.Piece;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public interface SupplierSiteInterface extends Remote {

    public int goToSupplier(Piece partNeeded) throws RemoteException;
    
}