package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public interface ParkInterface extends Remote {


    public void parkCar(int id) throws RemoteException;

    public void findCar(int id, int replacementCarId) throws RemoteException;

    public void getVehicle(int idCar, int idMechanic) throws RemoteException;

    public void returnReplacementCar(int idCar, int idCustomer) throws RemoteException;
    
    public void returnVehicle(int id) throws RemoteException;

    public boolean replacementCarAvailable(int idCustomer) throws RemoteException;

    public int reserveCar(int id) throws RemoteException;
    
}