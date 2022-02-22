package chatRMI.client;

import chatRMI.client.fc.ClientConsole;
import chatRMI.client.gui.ClientFrame;
import chatRMI.client.gui.ClientGUI;
import chatRMI.common.MyLogManager;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.logging.Logger;

public class Client {
    private static Logger logger;

    /**
     * Sets up everything so that the logger will function properly in every case
     */
    private static void setupLoggingProperties() {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%n");
        System.setProperty("java.util.logging.manager", MyLogManager.class.getName());
        logger = Logger.getLogger("client");
    }

    public static void main(String[] args) {
        if (args.length < 2 || args.length > 3) {
            System.out.println("Usage : java Client <rmiregistry host> <username> [-console]");
            return;
        }

        boolean useGUI = true;
        if (args.length == 3) {
            if (args[2].equals("-console")) {
                useGUI = false;
            } else {
                System.out.println("Unknown option '" + args[2] + "'");
                return;
            }
        }

        String host = args[0];
        String name = args[1];

        setupLoggingProperties();

        try {
            if (useGUI) {
                logger.info("Using GUI");
                ClientGUI client = new ClientGUI(host, name);
                ClientFrame frame = new ClientFrame("Chat Application", client);

                SwingUtilities.invokeLater(() -> frame.setVisible(true));
            } else {
                logger.info("Using console mode");
                new ClientConsole(host, name);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
