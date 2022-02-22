package chatRMI.client.gui.widgets;

import chatRMI.client.gui.ClientGUI;

import javax.swing.*;
import java.awt.*;

public class SendMessageBar extends JPanel{
    private final JTextField m_sendBar;

    public SendMessageBar(ClientGUI client) {
        this.setLayout(new BorderLayout());

        this.m_sendBar = new JTextField();
        this.m_sendBar.putClientProperty("JTextField.placeholderText", " Votre message ici...");
        this.m_sendBar.setFont(new Font(m_sendBar.getFont().getName(), Font.PLAIN, 20));

        this.setPreferredSize(new Dimension(getWidth(), 60));
        this.add(m_sendBar, BorderLayout.CENTER);
        this.add(new SendButtons(this, client), BorderLayout.EAST);
    }

    public String getSendBarText() {
        return this.m_sendBar.getText();
    }

    public void clearText() {
        this.m_sendBar.setText("");
    }
}