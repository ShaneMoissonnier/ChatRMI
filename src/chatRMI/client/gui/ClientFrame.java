package chatRMI.client.gui;

import chatRMI.client.gui.widgets.ContentPanel;
import chatRMI.client.gui.widgets.MenuBar;
import chatRMI.client.gui.widgets.SideBar;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkContrastIJTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class ClientFrame extends JFrame {
    private static ClientFrame instance;

    public ClientFrame(String title, ClientGUI client) {
        super(title);
        instance = this;

        FlatAtomOneDarkContrastIJTheme.setup();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1256, 860));

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                new SideBar(client),
                new ContentPanel(client)
        );
        splitPane.setDividerLocation(250);
        this.add(splitPane);

        this.setJMenuBar(new MenuBar());

        pack();
    }

    public static void close() {
        instance.dispatchEvent(new WindowEvent(instance, WindowEvent.WINDOW_CLOSING));
    }
}
