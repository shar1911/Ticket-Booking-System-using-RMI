import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.HashMap;

public class TransactionModule extends UnicastRemoteObject implements TransactionInterface {
    private HashMap<String, Double> balances;

    public TransactionModule() throws RemoteException {
        balances = new HashMap<>();
    }

    public boolean processPayment(String username, double amount) throws RemoteException {
        balances.put(username, balances.getOrDefault(username, 0.0) + amount);
        return true;
    }

    public boolean refund(String username, double amount) throws RemoteException {
        if (balances.containsKey(username) && balances.get(username) >= amount) {
            balances.put(username, balances.get(username) - amount);
            return true;
        }
        return false;
    }
}
