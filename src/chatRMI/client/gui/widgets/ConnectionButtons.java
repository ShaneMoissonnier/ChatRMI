package chatRMI.client.gui.widgets;

import chatRMI.client.gui.ClientGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.rmi.RemoteException;

public class ConnectionButtons extends JPanel {
    private final ClientGUI client;

    private static JButton connectButton;
    private static JButton disconnectButton;

    private void createButtons() {
        connectButton = new JButton("Connexion");
        disconnectButton = new JButton("Déconnexion");

        this.add(connectButton);
        this.add(disconnectButton);

        connectButton.addActionListener(e -> {
            try {
                client.login();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });
        disconnectButton.addActionListener(e -> {
            try {
                client.logout();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });

        disconnectButton.setEnabled(false);
    }

    private void setupPanel() {
        this.setLayout(new FlowLayout());
        this.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

        this.setPreferredSize(new Dimension(getWidth(), 60));
    }

    public ConnectionButtons(ClientGUI client) {
        this.client = client;

        this.setupPanel();
        this.createButtons();
    }

    public static void setLoggedIn() {
        connectButton.setEnabled(false);
        disconnectButton.setEnabled(true);
    }

    public static void setLoggedOut() {
        connectButton.setEnabled(true);
        disconnectButton.setEnabled(false);
    }
}
