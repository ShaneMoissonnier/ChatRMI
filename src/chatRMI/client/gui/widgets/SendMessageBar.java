package chatRMI.client.gui.widgets;

import chatRMI.client.Client;

import javax.swing.*;
import java.awt.*;

public class SendMessageBar extends JPanel{

    JTextField m_sendBar;

    public SendMessageBar(Client client) {
        BorderLayout border_layout = new BorderLayout();
        setLayout(border_layout);

        JTextField sendBar = new JTextField();
        sendBar.putClientProperty("JTextField.placeholderText", " Votre message ici...");
        sendBar.setFont(new Font(sendBar.getFont().getName(), Font.PLAIN, 20));

        this.m_sendBar = sendBar;

        setPreferredSize(new Dimension(getWidth(), 60));

        add(sendBar, BorderLayout.CENTER);
        add(new SendButtons(this, client), BorderLayout.EAST);
    }

    public String getSendBarText()
    {
        return this.m_sendBar.getText();
    }
}