package shared.Repository;

import interfaces.Register;
import interfaces.RepositoryInterface;
import java.io.FileNotFoundException;
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
public class RepositoryServer {
    
    public static void main(String[] args) throws FileNotFoundException {
        
        String rmiRegHostName = args[0];
        int rmiRegPortNumb = Integer.parseInt(args[1]);
        
        Repository repository = new Repository(rmiRegHostName, rmiRegPortNumb);
        RepositoryInterface repositoryInterface = null;
        
        try {
            repositoryInterface = (RepositoryInterface) UnicastRemoteObject.exportObject((Remote) repository, RegistryConfiguration.PORT_REPOSITORY);
        }
        catch(RemoteException e) {
            System.out.println("Error generating Repository's stub: " + e.getMessage());
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
            reg.bind(RegistryConfiguration.RMI_REGISTRY_REPOSITORY_NAME, repositoryInterface);
        }
        catch(RemoteException e) {
            System.out.println("Repository registration exception: " + e.getMessage());
            System.exit(1);
        }
        catch(AlreadyBoundException e) {
            System.out.println("Repository already bound exception: " + e.getMessage());
            System.exit(1);
        }
        
        System.out.println("Repository up!");
    }
    
}
