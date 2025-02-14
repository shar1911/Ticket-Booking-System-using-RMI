import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    String bookTicket(String username, String event, int seatNumber) throws RemoteException;
}
