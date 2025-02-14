import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    public static void main(String[] args) {
        try {
            // Create instances of remote objects
            RegistrationModule regModule = new RegistrationModule();
            ClientModule clientModule = new ClientModule();
            TransactionModule transactionModule = new TransactionModule();
            CancellationModule cancellationModule = new CancellationModule(clientModule.bookedTickets);

            // Create RMI registry
            Registry registry = LocateRegistry.createRegistry(1099);

            // Bind remote objects to the registry
            registry.rebind("RegistrationService", regModule);
            registry.rebind("ClientService", clientModule);
            registry.rebind("TransactionService", transactionModule);
            registry.rebind("CancellationService", cancellationModule);

            System.out.println("RMI Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
