package chatRMI.client.gui.widgets;

import chatRMI.client.gui.ClientGUI;
import chatRMI.common.Message;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.io.IOException;

public class ContentPanel extends JPanel {
    private static final JTextPane textArea = new JTextPane();

    private void setupTextArea() {
        this.add(textArea, BorderLayout.CENTER);
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setMargin(new Insets(20, 20, 20, 20));
        textArea.setContentType("text/html");
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
            HTMLDocument document = (HTMLDocument)textArea.getDocument();
            HTMLEditorKit editorKit = (HTMLEditorKit)textArea.getEditorKit();
            editorKit.insertHTML(document, document.getLength(), message.toString() + "\n",0, 0, null);
            textArea.setCaretPosition(document.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void onDisconnect() {
        textArea.setText("");
    }
}
