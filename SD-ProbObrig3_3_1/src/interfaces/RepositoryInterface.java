package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author André Oliveira
 * @author João Coelho
 */
public interface ParkInterface extends Remote {

    public void updateLog() throws RemoteException;

    public void setManagerState(String state) throws RemoteException;

    public void setCustomerState(String state, int i) throws RemoteException;

    public void setMechanicState(String state, int i) throws RemoteException;

    public void setCustomersQueue(int size) throws RemoteException;

    public void setReplacementQueue(int size) throws RemoteException;

    public void setNumCarsRepaired(int size) throws RemoteException;

    public void setCustomersParked(int n) throws RemoteException;

    public void setReplacementParked(int n) throws RemoteException;

    public void setRequests(int n) throws RemoteException;

    public void setPiecesStock(HashMap<EnumPiece, Integer> pieces) throws RemoteException;

    public void setPiecesRequired(int[] pieces) throws RemoteException;

    public void setManagerNotifed(String[] not) throws RemoteException;

    public void setBoughtPieces(int[] pieces) throws RemoteException;

    public void setRequiresCar(String s, int i) throws RemoteException;

    public void setVehicleDriven(String s, int i) throws RemoteException;

    public void setVehicleRepaired(String s, int i) throws RemoteException;

}