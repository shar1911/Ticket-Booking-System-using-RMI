import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.rmi.RemoteException;

public class ClientModule extends UnicastRemoteObject implements ClientInterface {
    public ArrayList<String> bookedTickets;

    public ClientModule() throws RemoteException {
        bookedTickets = new ArrayList<>();
    }

    public String bookTicket(String username, String event, int seatNumber) throws RemoteException {
        String ticket = username + " booked seat " + seatNumber + " for " + event;
        bookedTickets.add(ticket);
        return ticket;
    }
}


