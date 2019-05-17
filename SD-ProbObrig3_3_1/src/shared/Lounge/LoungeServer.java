package shared.Lounge;

import interfaces.LoungeInterface;
import interfaces.Register;
import interfaces.RepositoryInterface;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import registry.RegistryConfiguration;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public class LoungeServer {
    
    public static void main(String[] args) {
        
        String rmiRegHostName = args[0];
        int rmiRegPortNumb = Integer.parseInt(args[1]);
        
        RepositoryInterface repositoryInterface = null;
        
        try {
            Registry registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
            repositoryInterface = (RepositoryInterface) registry.lookup(RegistryConfiguration.RMI_REGISTRY_REPOSITORY_NAME);
        }
        catch (RemoteException e) {
            System.out.println("Error getting repository's location: " + e.getMessage());
            System.exit(1);
        }
        catch(NotBoundException e) {
            System.out.println("Repository isn't registred: " + e.getMessage());
            System.exit(1);
        }
        
        Lounge lounge = new Lounge(repositoryInterface, rmiRegHostName, rmiRegPortNumb);
        LoungeInterface loungeInterface = null;
        
        try {
            loungeInterface = (LoungeInterface) UnicastRemoteObject.exportObject((Remote) lounge, RegistryConfiguration.PORT_LOUNGE);
        }
        catch(RemoteException e) {
            System.out.println("Error generating Lounge's stub: " + e.getMessage());
            System.exit(1);
        }
        
        Registry registry = null;
        Register reg = null;
        
        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        }
        catch(RemoteException e) {
            System.out.println("Error while creating RMI registry: " + e.getMessage());
            System.exit(1);
        }
        
        try {
            reg = (Register) registry.lookup(RegistryConfiguration.RMI_REGISTER_NAME);
        }
        catch(RemoteException e) {
            System.out.println("RegisterRemoteObject lookup exception: " + e.getMessage());
            System.exit(1);
        }
        catch(NotBoundException e) {
            System.out.println("RegisterRemoteObject not bound exception: " + e.getMessage());
            System.exit(1);
        }
        
        try {
            reg.bind(RegistryConfiguration.RMI_REGISTRY_LOUNGE_NAME, loungeInterface);
        }
        catch(RemoteException e) {
            System.out.println("Lounge registration exception: " + e.getMessage());
            System.exit(1);
        }
        catch(AlreadyBoundException e) {
            System.out.println("Lounge already bound exception: " + e.getMessage());
            System.exit(1);
        }
        
    }
}
