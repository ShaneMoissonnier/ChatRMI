package chatRMI.client.gui.widgets;

import chatRMI.client.gui.ClientGUI;
import chatRMI.common.Message;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import java.awt.*;

public class ContentPanel extends JPanel {
    private static final JTextPane textArea = new JTextPane();

    private void setupTextArea() {
        this.add(textArea, BorderLayout.CENTER);
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setMargin(new Insets(20, 20, 20, 20));
    }

    private void setupScrollPane(ClientGUI client) {
        JScrollPane scrollPane = new JScrollPane(textArea);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(new SendMessageBar(client), BorderLayout.SOUTH);
    }

    public ContentPanel(ClientGUI client) {
        this.setLayout(new BorderLayout());

        this.setupTextArea();
        this.setupScrollPane(client);
    }

    public static void addMessage(Message message) {
        try {
            Document document = textArea.getDocument();
            int documentLength = document.getLength();
            document.insertString(documentLength, message.toString() + "\n", new SimpleAttributeSet());
            textArea.setCaretPosition(documentLength);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public static void onDisconnect() {
        textArea.setText("");
    }
}
