package entities.Customer;

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
import static settings.Constants.N_CUSTOMERS;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public class CustomerClient {

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
		OutsideWorldInterface outsideWorldInt = null;
		LoungeInterface loungeInt = null;
		RepairAreaInterface repairAreaInt = null;
		SupplierSiteInterface supplierSiteInt = null;
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
			outsideWorldInt = (OutsideWorldInterface) registry.lookup(RegistryConfiguration.RMI_REGISTRY_OUTSIDEWORLD_NAME);

		} catch (RemoteException e) {
			System.out.println("Excepção na localização do Outside World: " + e.getMessage() + "!");
			System.exit(1);
		} catch (NotBoundException e) {
			System.out.println("O Outside World não está registada: " + e.getMessage() + "!");
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

		Customer[] customers = new Customer[N_CUSTOMERS];

		for (int i = 0; i < N_CUSTOMERS; i++) {
			customers[i] = new Customer(i, loungeInt, outsideWorldInt, supplierSiteInt, repairAreaInt, parkInt, repositoryInt);
			System.out.println("Customer " + i + " has started");
			customers[i].start();
		}

		for (int i = 0; i < N_CUSTOMERS; i++) {
			try {
				customers[i].join();
				System.out.println("Customer " + i + " has died");
			} catch (InterruptedException e) {
				System.out.println("Customer " + i + " has died - exeption");
			}
		}

		/*try {
			repositoryInt.finished();
		} catch (RemoteException ex) {
			System.out.println("Error closing all!");
			System.exit(1);
		}*/

		System.out.println("Customers Done!");

	}
}
