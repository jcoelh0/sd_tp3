package shared.RepairArea;

import interfaces.Register;
import interfaces.RepairAreaInterface;
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
public class RepairAreaServer {
    
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
        
        RepairArea repairArea = new RepairArea(repositoryInterface, rmiRegHostName, rmiRegPortNumb);
        RepairAreaInterface repairAreaInterface = null;
        
        try {
            repairAreaInterface = (RepairAreaInterface) UnicastRemoteObject.exportObject((Remote) repairArea, RegistryConfiguration.PORT_REPAIRAREA);
        }
        catch(RemoteException e) {
            System.out.println("Error generating RepairArea's stub: " + e.getMessage());
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
            reg.bind(RegistryConfiguration.RMI_REGISTRY_REPAIRAREA_NAME, repairAreaInterface);
        }
        catch(RemoteException e) {
            System.out.println("RepairArea registration exception: " + e.getMessage());
            System.exit(1);
        }
        catch(AlreadyBoundException e) {
            System.out.println("RepairArea already bound exception: " + e.getMessage());
            System.exit(1);
        }
        
    }
    
}
