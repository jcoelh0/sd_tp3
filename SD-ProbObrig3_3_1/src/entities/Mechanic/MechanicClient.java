package entities.Mechanic;

import interfaces.LoungeInterface;
import interfaces.OutsideWorldInterface;
import interfaces.ParkInterface;
import interfaces.RepairAreaInterface;
import interfaces.RepositoryInterface;
import interfaces.SupplierSiteInterface;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import registry.RegistryConfiguration;
import settings.Constants;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public class MechanicClient {

    public static void main(String[] args) {
        // Initialize RMI Configurations
        String rmiRegHostName = args[0];
        int rmiRegPortNumb = Integer.parseInt(args[1]);

        /*
        String rmiRegHostName = "localhost";
        int rmiRegPortNumb = 1099;
         */
        Registry registry = null;

        // Initialize RMI Invocations
        RepositoryInterface repositoryInterface = null;
        LoungeInterface loungeInt = null;
        RepairAreaInterface repairAreaInt = null;
        ParkInterface parkInt = null;
        RepositoryInterface repositoryInt = null;
        //localização por nome do objecto remoto no serviço de registos RMI

        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        } catch (RemoteException e) {
            System.out.println("RMI registry creation exception: " + e.getMessage());
            System.exit(1);
        }

        //localização por nome do objecto remoto no serviço de registos RMI
        try {
            repositoryInterface = (RepositoryInterface) registry.lookup(RegistryConfiguration.RMI_REGISTRY_REPOSITORY_NAME);
        } catch (RemoteException e) {
            System.out.println("Excepção na localização do Repository: " + e.getMessage() + "!");
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("O Repository não está registada: " + e.getMessage() + "!");
            System.exit(1);
        }

        try {
            parkInt = (ParkInterface) registry.lookup(RegistryConfiguration.RMI_REGISTRY_PARK_NAME);

        } catch (RemoteException e) {
            System.out.println("Excepção na localização do Park: " + e.getMessage() + "!");
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("O Park não está registada: " + e.getMessage() + "!");
            System.exit(1);
        }

        try {
            loungeInt = (LoungeInterface) registry.lookup(RegistryConfiguration.RMI_REGISTRY_LOUNGE_NAME);

        } catch (RemoteException e) {
            System.out.println("Excepção na localização do Lounge: " + e.getMessage() + "!");
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("O Lounge não está registada: " + e.getMessage() + "!");
            System.exit(1);
        }

        try {
            repairAreaInt = (RepairAreaInterface) registry.lookup(RegistryConfiguration.RMI_REGISTRY_REPAIRAREA_NAME);

        } catch (RemoteException e) {
            System.out.println("Excepção na localização da Repair Area: " + e.getMessage() + "!");
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("A Repair Area não está registada: " + e.getMessage() + "!");
            System.exit(1);
        }

        Mechanic[] mechanics = new Mechanic[Constants.N_MECHANICS];

        for (int i = 0; i < Constants.N_MECHANICS; i++) {
            mechanics[i] = new Mechanic(i, loungeInt, repairAreaInt, parkInt, repositoryInt);
            System.out.println("Mechanic " + i + " has started");
            mechanics[i].start();
        }

        for (int i = 0; i < Constants.N_MECHANICS; i++) {
            try {
                mechanics[i].join();
                System.out.println("Mechanic " + i + " has died");
            } catch (InterruptedException e) {
                System.out.println("Mechanic " + i + " has died - exeption");
            }
        }
        
        try {
            repositoryInterface.finished();
        } catch (RemoteException ex) {
            System.out.println("Error closing all!");
            System.exit(1);
        }
        
        System.out.println("Mechanics Done!");

    }
}
