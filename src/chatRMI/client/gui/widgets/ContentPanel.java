package chatRMI.client.gui.widgets;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import java.awt.*;

public class ContentPanel extends JPanel {

    private static JTextPane textArea = null;

    public ContentPanel()
    {
        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);

        textArea = new JTextPane();
        this.add(textArea, BorderLayout.CENTER);
        textArea.setEditable(false);
        textArea.setFocusable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);

        this.add(scrollPane, BorderLayout.CENTER);
        this.add(new SendMessageBar(), BorderLayout.SOUTH);
    }

    public static void addMessage(String message) throws BadLocationException {
        Document document = textArea.getDocument();
        int documentLength = document.getLength();
        document.insertString(documentLength, message, new SimpleAttributeSet());
        textArea.setCaretPosition(documentLength);
    }
}
