package chatRMI.server;

import chatRMI.common.Message;
import chatRMI.common.MyLogManager;
import chatRMI.remoteInterfaces.ChatService;

import java.io.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

public class Server {
    private static Logger logger;
    private ChatService chatService;

    private final String HISTORY_PATH = System.getProperty("user.home") + "/.chat/history";
    /**
     * Sets up everything so that the logger will function properly in every case
     */
    private void setupLoggingProperties() {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%n");
        System.setProperty("java.util.logging.manager", MyLogManager.class.getName());
        logger = Logger.getLogger("chatServer");
    }

    /**
     * Binds a service to the RMI Registry
     * <p>
     * Warning : This method does not export the object, this is because our implementations of {@link Remote} all
     * extend {@link UnicastRemoteObject}, and are thus exported when they are instantiated
     *
     * @param object The stub of the service
     * @param name   The service's name
     */
    void bindService(Remote object, String name) {
        try {
            Registry registry = LocateRegistry.getRegistry();
            registry.bind(name, object);
            logger.info("Service '" + name + "' bound to the registry");
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Unbinds a service from the registry and unexports its stub.
     *
     * @param object The stub of the service
     * @param name   The service's name
     */
    void unbindService(Remote object, String name) {
        try {
            Registry registry = LocateRegistry.getRegistry();
            UnicastRemoteObject.unexportObject(object, false);
            registry.unbind(name);
            logger.info("Service '" + name + "' unbound to the registry");
        } catch (NotBoundException | RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method boots the server
     */
    private void boot() throws IOException, ClassNotFoundException {
        this.setupLoggingProperties();
        logger.info("Initializing Server...");

        this.chatService = new ChatServiceImpl();
        this.bindService(chatService, "chatService");

        this.loadHistory();

        logger.info("Server ready");
    }

    /**
     * Shuts the server down.
     * <p>
     * This is a shutdown hook, meaning that this method will be run when the server is interrupted via SIGINT (ctrl + c)
     */
    private void shutDown() {
        logger.info("Shutting down...");
        this.unbindService(chatService, "chatService");

        // save all messages in history
        try {
            this.saveHistory();
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("Shutdown complete");
        MyLogManager.resetFinally();
    }

    private void saveHistory() throws IOException {
        logger.info("Saving message history...");
        File historyFile = new File(HISTORY_PATH);

        if (historyFile.getParentFile().mkdirs()) {
            logger.info("Created folder '" + historyFile.getParentFile() + "'");
        }
        if (historyFile.createNewFile()) {
            logger.info("Created new history save file");
        }
        FileOutputStream oFile = new FileOutputStream(historyFile, false);

        ObjectOutputStream oOutStream = new ObjectOutputStream(oFile);

        oOutStream.writeObject(chatService.getMessageList());
        oOutStream.flush();
        oOutStream.close();
        logger.info("Saved message history at '" + historyFile + "'");
    }

    private void loadHistory() throws IOException, ClassNotFoundException {
        logger.info("Loading message history...");
        File historyFile = new File(HISTORY_PATH);

        if (!historyFile.exists()) {
            logger.info("No message history found");
            return;
        }

        FileInputStream iFile = new FileInputStream(historyFile);
        ObjectInputStream oInputStream = new ObjectInputStream(iFile);

        List<?> objectList = (List<?>) oInputStream.readObject();

        if (objectList == null) {
            logger.severe("Empty history file");
            return;
        }

        List<Message> messageList = new Vector<>();
        for (Object o : objectList) {
            if (!(o instanceof Message)) {
                logger.severe("Corrupt history file : deleting...");
                if (historyFile.delete()) {
                    logger.info("History file deleted");
                }
                return;
            }

            messageList.add((Message) o);
        }

        chatService.setMessageList(messageList);
        logger.info("Message history loaded");
    }

    Server() throws IOException, ClassNotFoundException {
        this.boot();

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutDown));
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new Server();
    }
}
