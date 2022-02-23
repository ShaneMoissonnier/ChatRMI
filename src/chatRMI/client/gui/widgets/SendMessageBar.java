package chatRMI.client.gui.widgets;

import chatRMI.client.Client;
import chatRMI.client.gui.ClientGUI;
import chatRMI.common.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;

public class SendMessageBar extends JPanel{
    private final JTextField m_sendBar;
    private Action m_textFieldEnterAction;

    public SendMessageBar(ClientGUI client) {
        setupTextFieldEnterAction(client);

        this.setLayout(new BorderLayout());

        this.m_sendBar = new JTextField();
        this.m_sendBar.putClientProperty("JTextField.placeholderText", " Votre message ici...");
        this.m_sendBar.setFont(new Font(m_sendBar.getFont().getName(), Font.PLAIN, 20));
        this.m_sendBar.addActionListener(m_textFieldEnterAction);

        this.setPreferredSize(new Dimension(getWidth(), 60));
        this.add(m_sendBar, BorderLayout.CENTER);
        this.add(new SendButtons(this, client), BorderLayout.EAST);
    }

    public void setupTextFieldEnterAction(ClientGUI client) {
        m_textFieldEnterAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    Message message = new Message(getSendBarText(), client.getName());
                    client.sendMessage(message);
                    clearText();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public Action getTextFieldEnterAction() {
        return m_textFieldEnterAction;
    }

    public String getSendBarText() {
        return this.m_sendBar.getText();
    }

    public void clearText() {
        this.m_sendBar.setText("");
    }
}