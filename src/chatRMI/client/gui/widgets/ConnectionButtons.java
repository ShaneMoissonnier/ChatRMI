package chatRMI.client.gui.widgets;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Flow;

public class ConnectionButtons extends JPanel {
    public ConnectionButtons()
    {
        FlowLayout layout = new FlowLayout();
        setLayout(layout);

        setPreferredSize(new Dimension(getWidth(), 60));

        JButton connectButton = new JButton("Connexion");
        JButton disconnectButton = new JButton("Deconnexion");

        this.add(connectButton);
        this.add(disconnectButton);
    }
}
