package chatRMI.client.gui.widgets;

import javax.swing.*;
import java.awt.*;

public class SideBar extends JPanel {
    public SideBar()
    {
        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);

        DefaultListModel<String> model = new DefaultListModel();
        JList list = new JList(model);
        model.addElement("Utilisateur");

        ConnectionButtons connectionButtons = new ConnectionButtons();

        this.add(list, BorderLayout.CENTER);
        this.add(connectionButtons, BorderLayout.SOUTH);
    }
}
