package shared.SupplierSite;

import interfaces.Register;
import interfaces.RepositoryInterface;
import interfaces.SupplierSiteInterface;
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
public class SupplierSiteServer {
    
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
        
        SupplierSite supplierSite = new SupplierSite(repositoryInterface, rmiRegHostName, rmiRegPortNumb);
        SupplierSiteInterface supplierSiteInterface = null;
        
        try {
            supplierSiteInterface = (SupplierSiteInterface) UnicastRemoteObject.exportObject((Remote) supplierSite, RegistryConfiguration.PORT_SUPPLIERSITE);
        }
        catch(RemoteException e) {
            System.out.println("Error generating SupplierSite's stub: " + e.getMessage());
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
            reg.bind(RegistryConfiguration.RMI_REGISTRY_SUPPLIERSITE_NAME, supplierSiteInterface);
        }
        catch(RemoteException e) {
            System.out.println("SupplierSite registration exception: " + e.getMessage());
            System.exit(1);
        }
        catch(AlreadyBoundException e) {
            System.out.println("SupplierSite already bound exception: " + e.getMessage());
            System.exit(1);
        }
        
    }
    
}
