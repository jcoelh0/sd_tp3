package shared.SupplierSite;

import entities.Manager.Interfaces.IManagerSS;
import interfaces.Register;
import interfaces.RepositoryInterface;
import interfaces.SupplierSiteInterface;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import registry.RegistryConfiguration;
import settings.Piece;
import settings.Constants;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public class SupplierSite implements IManagerSS, SupplierSiteInterface {
    
    private Piece partNeeded;
    private int[] piecesBought;

    String rmiRegHostName;
    int rmiRegPortNumb;
    private final RepositoryInterface repositoryInterface;
    
    public SupplierSite(RepositoryInterface repository, String rmiRegHostName, int rmiRegPortNumb) {
        
        int nTypePieces = Constants.N_TYPE_PIECES;
        
        piecesBought = new int[nTypePieces];
        for (int i = 0; i < nTypePieces; i++) {
            piecesBought[i] = 0;
        }
        
        this.repositoryInterface = repository;
        this.rmiRegHostName = rmiRegHostName;
        this.rmiRegPortNumb = rmiRegPortNumb;
    }

    /**
     * Manager's method. Manager goes into supplier site and buys the required
     * pieces in a random amount.
     *
     * @param partNeeded a piece that the manager needs to buy
     * @return an Integer representing the amount of pieces he bought
     */
    @Override
    public synchronized int goToSupplier(Piece partNeeded) {
        int randomNum = 2;
        System.out.println("Manager - Bought " + randomNum + " of " + partNeeded);
        this.partNeeded = partNeeded;
        piecesBought[partNeeded.getTypePiece().ordinal()] += randomNum;
        updatePiecesBought(piecesBought);
        return randomNum;
    }

    /**
     * Method used for log. Retrieves the total number of pieces bought by the
     * manager for each type of piece.
     *
     * @return an Array with the total number of pieces bought for each type of
     * piece
     */
    public int[] getPiecesBought() {
        return piecesBought;
    }
    
    private synchronized void updatePiecesBought(int[] piecesBought) {
        try {
            repositoryInterface.setBoughtPieces(piecesBought);
        }
        catch(RemoteException e) {
            System.err.println("Excepção na invocação remota de método" + e.getMessage() + "!");
            System.exit(1);
        }
    }
    
    @Override
    public void signalShutdown() {
        Register reg = null;
        Registry registry = null;
        
        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        } 
        catch (RemoteException ex) {
            System.out.println("Erro ao localizar o registo");
            System.exit(1);
        }
        
        String nameEntryBase = RegistryConfiguration.RMI_REGISTER_NAME;
        String nameEntryObject = RegistryConfiguration.RMI_REGISTRY_SUPPLIERSITE_NAME;
        
        try {
            reg = (Register) registry.lookup(nameEntryBase);
        } catch (RemoteException e) {
            System.out.println("RegisterRemoteObject lookup exception: " + e.getMessage());
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("RegisterRemoteObject not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        
        // Unregister ourself
        try {
            reg.unbind(nameEntryObject);
        } catch (RemoteException e) {
            System.out.println("SupplierSite registration exception: " + e.getMessage());
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("SupplierSite not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        // Unexport; this will also remove us from the RMI runtime
        try {
            UnicastRemoteObject.unexportObject(this, true);
        } catch (NoSuchObjectException ex) {
            System.exit(1);
        }

        System.out.println("SupplierSite closed.");
    }
}
