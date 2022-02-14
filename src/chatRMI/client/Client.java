package chatRMI.client;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    private final String host;

    Remote lookUpService(String name) {
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            return registry.lookup(name);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    Client(String host) {
        this.host = host;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Client <rmiregistry host>");
            return;
        }

        new Client(args[0]);
    }
}
