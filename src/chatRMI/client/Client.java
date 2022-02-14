package chatRMI.client;

import chatRMI.remoteInterfaces.ChatService;
import chatRMI.remoteInterfaces.ClientInfo;

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

    Client(String host, String name) {
        this.host = host;

        ClientInfo clientInfo = new ClientInfoImpl(name);

        ChatService chatService = (ChatService) this.lookUpService("chatService");
        try {
            chatService.login(clientInfo);
            Thread.sleep(2000);
        } catch (RemoteException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Client <rmiregistry host> <username>");
            return;
        }

        new Client(args[0], args[1]);
    }
}
