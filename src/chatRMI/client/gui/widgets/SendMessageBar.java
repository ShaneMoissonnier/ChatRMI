package chatRMI.client.gui.widgets;

import javax.swing.*;
import java.awt.*;

public class SendMessageBar extends JPanel{

    public SendMessageBar() {
        BorderLayout border_layout = new BorderLayout();
        setLayout(border_layout);

        JTextField barre_recherche = new JTextField();
        barre_recherche.putClientProperty("JTextField.placeholderText", " Votre message ici...");
        barre_recherche.setFont(new Font(barre_recherche.getFont().getName(), Font.PLAIN, 20));

        setPreferredSize(new Dimension(getWidth(), 60));

        add(barre_recherche, BorderLayout.CENTER);
        add(new SearchButtons(this), BorderLayout.EAST);
    }
}