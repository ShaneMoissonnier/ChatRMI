package chatRMI.client.gui.widgets;

import chatRMI.client.gui.ClientGUI;
import chatRMI.common.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class SendButtons extends JPanel {
    private final SendMessageBar m_sendMessageBar;
    private final ClientGUI m_client;

    public SendButtons(SendMessageBar sendMessageBar, ClientGUI client) {
        this.m_sendMessageBar = sendMessageBar;
        this.m_client = client;

        this.setLayout(new BorderLayout());

        JButton sendButton = new JButton("Envoyer");
        this.add(sendButton, BorderLayout.EAST);
        sendButton.addActionListener(m_sendMessageBar.getTextFieldEnterAction());
    }
}