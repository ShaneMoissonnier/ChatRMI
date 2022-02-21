package chatRMI.client.gui.widgets;

import chatRMI.client.Client;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class SendButtons extends JPanel implements ActionListener{

    SendMessageBar m_sendMessageBar;
    Client m_client;

    public SendButtons(SendMessageBar sendMessageBar, Client client) {

        this.m_client = client;
        this.m_sendMessageBar = sendMessageBar;

        BorderLayout borderLayout = new BorderLayout();

        this.setLayout(borderLayout);

        JButton sendButton = new JButton("Envoyer");

        add(sendButton, BorderLayout.EAST);

        sendButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        try {
            m_client.sendMessage(m_sendMessageBar.getSendBarText());
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}