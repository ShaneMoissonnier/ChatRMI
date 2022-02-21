package chatRMI.client.gui.widgets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchButtons extends JPanel implements ActionListener{

    SendMessageBar m_sendMessageBar;

    public SearchButtons(SendMessageBar sendMessageBar) {
        BorderLayout borderLayout = new BorderLayout();

        this.setLayout(borderLayout);

        JButton sendButton = new JButton("Envoyer");
        sendButton.addActionListener(this);

        add(sendButton, BorderLayout.EAST);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}