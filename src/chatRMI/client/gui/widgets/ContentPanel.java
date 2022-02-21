package chatRMI.client.gui.widgets;

import chatRMI.client.Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import java.awt.*;

public class ContentPanel extends JPanel {

    private static JTextPane textArea = null;

    public ContentPanel(Client client)
    {
        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);

        textArea = new JTextPane();
        this.add(textArea, BorderLayout.CENTER);
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setMargin(new Insets(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(textArea);

        this.add(scrollPane, BorderLayout.CENTER);
        this.add(new SendMessageBar(client), BorderLayout.SOUTH);
    }

    public static void addMessage(String message) throws BadLocationException {
        Document document = textArea.getDocument();
        int documentLength = document.getLength();
        document.insertString(documentLength, message, new SimpleAttributeSet());
        textArea.setCaretPosition(documentLength);
    }
}
