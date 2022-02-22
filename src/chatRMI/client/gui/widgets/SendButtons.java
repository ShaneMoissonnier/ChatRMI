package chatRMI.client.gui.widgets;

import chatRMI.client.gui.ClientGUI;
import chatRMI.common.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class SendButtons extends JPanel implements ActionListener {
    private final SendMessageBar m_sendMessageBar;
    private final ClientGUI m_client;

    public SendButtons(SendMessageBar sendMessageBar, ClientGUI client) {
        this.m_sendMessageBar = sendMessageBar;
        this.m_client = client;

        this.setLayout(new BorderLayout());

        JButton sendButton = new JButton("Envoyer");
        this.add(sendButton, BorderLayout.EAST);
        sendButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        try {
            Message message = new Message(m_sendMessageBar.getSendBarText(), m_client.getName());
            this.m_client.sendMessage(message);
            m_sendMessageBar.clearText();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}