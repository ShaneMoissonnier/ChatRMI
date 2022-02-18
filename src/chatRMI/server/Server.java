package chatRMI.server;

import chatRMI.remoteInterfaces.ChatService;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
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

    Server() throws MalformedURLException, RemoteException {
        System.out.println("Init Server");
        ChatService chatService = new ChatServiceImpl();
        Naming.rebind("chatService", chatService);
        System.out.println("Server ready");
    }

    public static void main(String[] args) throws MalformedURLException, RemoteException {
        new Server();
    }
}
