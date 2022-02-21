package chatRMI.client.gui.widgets;

import chatRMI.client.Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SideBar extends JPanel {
    public SideBar(Client client)
    {
        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

        DefaultListModel<String> model = new DefaultListModel();
        JList list = new JList(model);
        model.addElement("Utilisateur");

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
}
