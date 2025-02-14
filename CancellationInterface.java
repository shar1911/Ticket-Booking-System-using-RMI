import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CancellationInterface extends Remote {
    boolean cancelTicket(String username, int seatNumber) throws RemoteException;
}
