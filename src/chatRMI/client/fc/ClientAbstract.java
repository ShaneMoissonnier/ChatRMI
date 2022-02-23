package chatRMI.client.fc;

import chatRMI.common.MyLogManager;
import chatRMI.remoteInterfaces.ChatService;
import chatRMI.remoteInterfaces.ClientInfo;
import chatRMI.common.Message;

import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Logger;

public abstract class ClientAbstract extends UnicastRemoteObject implements ClientInfo {
    private static Logger logger;

    private final String host;
    private String name;

    protected ChatService chatService;

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
     */
    private void setupRemoteObjects() throws RemoteException {
        this.chatService = (ChatService) this.lookUpService("chatService");
    }

    /**
     * Unexports the previously exported objects
     */
    private void unexportRemoteObjects() throws NoSuchObjectException {
        UnicastRemoteObject.unexportObject(this, false);
    }

    /**
     * Used to log into the server
     */
    public void login() throws RemoteException {
        logger.info("Logging in...");
        this.chatService.login(this);
    }

    /**
     * Used to log out of the server
     */
    public void logout() throws RemoteException {
        logger.info("Logging out...");
        this.chatService.logout(this);
    }

    /**
     * Used to send a message to the server
     *
     * @param message the content of the message to be sent
     */
    public void sendMessage(Message message) throws RemoteException {
        this.chatService.sendMessage(message);
    }

    protected abstract void shutDown() throws RemoteException;

    private void shutDownWrapper() {
        try {
            this.shutDown();
            this.unexportRemoteObjects();
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            MyLogManager.resetFinally();
        }
    }

    public ClientAbstract(String host, String name) throws RemoteException {
        logger = Logger.getLogger("client");

        this.host = host;
        this.name = name;

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutDownWrapper));

        this.initClient();
    }

    /**
     * Called to init client
     */
    public void initClient() throws RemoteException {
        this.setupRemoteObjects();
    }

    protected abstract void loadHistory(List<Message> history) throws RemoteException;

    public abstract boolean isLoggedIn() throws RemoteException;

    /* **************************************
     * CLIENT INFO INTERFACE IMPLEMENTATION
     ***************************************/

    /**
     * Called once, when the client logs into the server
     *
     * @param status Whether the login is successful or not
     */
    @Override
    public void loginCallback(boolean status, List<Message> history) throws RemoteException {
        if (status) {
            logger.info("Loading message history");
            this.loadHistory(history);
            logger.info("Message history loaded");
            logger.info("Login successful");
        } else {
            logger.severe("Error");
        }
    }

    /**
     * Called when someone else joins the server
     *
     * @param other The client that just logged in
     */
    @Override
    public void otherLoginCallback(ClientInfo other) throws RemoteException {
        logger.info(other.getName() + " joined the chat");
    }

    /**
     * Called when someone (self included) sends a message to the server.
     *
     * @param message The message
     */
    @Override
    public void messageReceivedCallback(Message message) throws RemoteException {
        System.out.println(message.getAuthor() + " : " + message.getContent());
    }

    /**
     * Called once, when the client logs out of the server
     *
     * @param status Whether the logout is successful or not
     */
    @Override
    public void logoutCallback(boolean status) throws RemoteException {
        if (status) {
            logger.info("Logout successful");
        } else {
            logger.severe("Error");
            System.exit(1);
        }
    }

    /**
     * Called when someone else leaves the server
     *
     * @param other The client that just logged out
     */
    @Override
    public void otherLogoutCallback(ClientInfo other) throws RemoteException {
        logger.info(other.getName() + " left the chat");
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) throws RemoteException { this.name = name; }
}
