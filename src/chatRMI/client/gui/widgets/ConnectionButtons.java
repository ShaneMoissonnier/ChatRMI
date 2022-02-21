package chatRMI.client.gui.widgets;

import chatRMI.client.Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class ConnectionButtons extends JPanel {
    private Client m_client;

    public ConnectionButtons(Client client)
    {
        this.m_client = client;

        FlowLayout flowLayout = new FlowLayout();
        this.setLayout(flowLayout);
        this.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

        setPreferredSize(new Dimension(getWidth(), 60));

        JButton connectButton = new JButton("Connexion");
        JButton disconnectButton = new JButton("Deconnexion");

        this.add(connectButton);
        this.add(disconnectButton);

        connectButton.addActionListener(e -> {
            try {
                m_client.login();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });
        disconnectButton.addActionListener(e -> {
            try {
                m_client.logout();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });
    }
}
