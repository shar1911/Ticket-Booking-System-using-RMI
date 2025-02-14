import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class CancellationModule extends UnicastRemoteObject implements CancellationInterface {
    private ArrayList<String> bookedTickets;

    public CancellationModule(ArrayList<String> bookedTickets) throws RemoteException {
        this.bookedTickets = bookedTickets;
    }

    public boolean cancelTicket(String username, int seatNumber) throws RemoteException {
        String ticketToRemove = username + " booked seat " + seatNumber;
        return bookedTickets.remove(ticketToRemove);
    }
}
