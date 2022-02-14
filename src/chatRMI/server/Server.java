package chatRMI.server;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    void bindService(Remote object, String name) {
        try {
            Remote stub = UnicastRemoteObject.exportObject(object, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.bind(name, stub);
            System.out.println("Service '" + name + "' bound to the registry");
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    Server() {
        // bind services
    }

    public static void main(String[] args) {
        new Server();
    }
}
