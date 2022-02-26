package chatRMI.client.gui.widgets;

import chatRMI.client.gui.ClientGUI;
import chatRMI.remoteInterfaces.ClientInfo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.List;

@SuppressWarnings("serial")
public class SideBar extends JPanel {
    private static DefaultListModel<String> model;

    public SideBar(ClientGUI client) {
        this.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

        model = new DefaultListModel<>();
        JList<String> list = new JList<>(model);

        JLabel label = new JLabel();
        label.setText("Utilisateurs actifs");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

        panel.add(label, BorderLayout.NORTH);
        panel.add(list, BorderLayout.CENTER);

        ConnectionButtons connectionButtons = new ConnectionButtons(client);

        this.add(panel, BorderLayout.CENTER);
        this.add(connectionButtons, BorderLayout.SOUTH);
    }

    public static void onSelfLogin(List<ClientInfo> clients) throws RemoteException {
        for (ClientInfo client : clients) {
            model.addElement(client.getName());
        }
    }

    public static void onOtherLogin(ClientInfo other) throws RemoteException {
        model.addElement(other.getName());
    }

    public static void onSelfLogout() {
        model.removeAllElements();
    }

    public static void onOtherLogout(ClientInfo other) throws RemoteException {
        model.removeElement(other.getName());
    }
}
