import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TransactionInterface extends Remote {
    boolean processPayment(String username, double amount) throws RemoteException;
    boolean refund(String username, double amount) throws RemoteException;
}
