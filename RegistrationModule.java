import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.rmi.RemoteException;

public class RegistrationModule extends UnicastRemoteObject implements RegistrationInterface {
    private HashMap<String, String> users;

    public RegistrationModule() throws RemoteException {
        users = new HashMap<>();
    }

    public boolean register(String username, String password) throws RemoteException {
        if (users.containsKey(username)) {
            return false; // User already exists
        }
        users.put(username, password);
        return true;
    }

    public boolean login(String username, String password) throws RemoteException {
        return users.containsKey(username) && users.get(username).equals(password);
    }
}
