package chatRMI.client;

import chatRMI.client.gui.ClientGUI;
import chatRMI.common.MyLogManager;
import chatRMI.remoteInterfaces.ChatService;
import chatRMI.remoteInterfaces.ClientInfo;
import chatRMI.server.Message;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

public class Client extends UnicastRemoteObject implements ClientInfo {

    private static Logger logger;

    private final String host;
    private final String name;

    private ChatService chatService;
    private ClientGUI clientGUI;

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
        this.chatService.login(this);
    }

    /**
     * Used to log out of the server
     */
    public void logout() throws RemoteException {
        this.chatService.logout(this);
    }

    /**
     * Used to send a message to the server
     *
     * @param message the content of the message to be sent
     */
    public void sendMessage(String message) throws RemoteException, BadLocationException {
        this.chatService.sendMessage(this, message);
    }

    private void shutDown() {
        try {
            this.logout();
            this.unexportRemoteObjects();
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            // cause a nullException error (TODO)
            //MyLogManager.resetFinally();
        }
    }

    private Client(String host, String name) throws RemoteException {
        this.setupLoggingProperties();

        logger = Logger.getLogger("clientInfo");

        this.host = host;
        this.name = name;

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutDown));

        this.initClient();
    }

    /**
     * Called to init client
     */
    public void initClient() throws RemoteException{
        this.setupRemoteObjects(name);
    }

    /**
     * Called when clientGui is finally initialized
     *
     * @param gui client gui
     */
    public void setGui(ClientGUI gui) throws RemoteException {
        this.clientGUI = gui;
    }

    public static void main(String[] args) throws RemoteException, BadLocationException {
        if (args.length != 2) {
            System.out.println("Usage: java Client <rmiregistry host> <username>");
            return;
        }

        Client client = new Client(args[0], args[1]);

        ClientGUI gui = new ClientGUI("Chat Application", client);

        // Launch Client GUI
        SwingUtilities.invokeLater(() -> gui.setVisible(true));
    }

    /***************************************
     * CLIENTINFO INTERFACE IMPLEMENTATION
     ***************************************/

    /**
     * Called once, when the client logs into the server
     *
     * @param status Whether the login is successful or not
     */
    @Override
    public void loginCallback(boolean status) {
        if (status) {
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
    public void messageReceivedCallback(Message message) throws RemoteException, BadLocationException {
        clientGUI.addChatMessage(message.getAuthor() + " : " + message.getContent() + "\n");
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
}
