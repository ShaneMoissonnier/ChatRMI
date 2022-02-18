package chatRMI.client;

import chatRMI.common.MyLogManager;
import chatRMI.remoteInterfaces.ChatService;
import chatRMI.remoteInterfaces.ClientInfo;

import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    private final String host;

    private ClientInfo clientInfo;
    private ChatService chatService;

    /**
     * Sets up everything so that the logger will function properly in every case
     */
    private void setupLoggingProperties() {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%n");
        System.setProperty("java.util.logging.manager", MyLogManager.class.getName());
    }

    /**
     * Looks up a service in the RMI registry
     *
     * @param name The service's name
     * @return The service's stub
     */
    private Remote lookUpService(String name) {
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            return registry.lookup(name);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Retrieves and exports the necessary remote objects
     *
     * @param name The client's username
     */
    private void setupRemoteObjects(String name) throws RemoteException {
        this.clientInfo = new ClientInfoImpl(name);
        this.chatService = (ChatService) this.lookUpService("chatService");
    }

    /**
     * Unexports the previously exported objects
     */
    private void unexportRemoteObjects() throws NoSuchObjectException {
        UnicastRemoteObject.unexportObject(this.clientInfo, false);
    }

    /**
     * Used to log into the server
     */
    private void login() throws RemoteException {
        this.chatService.login(clientInfo);
    }

    /**
     * Used to log out of the server
     */
    private void logout() throws RemoteException {
        this.chatService.logout(clientInfo);
    }

    /**
     * The client's main loop. It is used to gather messages from the user and to send them to the server.
     * <p>
     * To cleanly exit the main loop, close stdin (CTRL-D)
     */
    private void run() throws RemoteException {
        String message;
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                message = sc.nextLine();
            } catch (NoSuchElementException ignored) {
                sc.close();
                break;
            }

            if (!message.isBlank() || !message.isEmpty()) {
                this.chatService.sendMessage(this.clientInfo, message);
            }
        }
    }

    private void shutDown() {
        try {
            this.logout();
            this.unexportRemoteObjects();
            MyLogManager.resetFinally();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private Client(String host, String name) throws RemoteException {
        this.setupLoggingProperties();
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutDown));

        this.host = host;
        this.setupRemoteObjects(name);

        this.login();
        this.run();

        System.exit(0);
    }

    public static void main(String[] args) throws RemoteException {
        if (args.length != 2) {
            System.out.println("Usage: java Client <rmiregistry host> <username>");
            return;
        }

        new Client(args[0], args[1]);
    }
}
